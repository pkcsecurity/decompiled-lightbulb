package com.facebook.drawee.controller;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.components.DeferredReleaser;
import com.facebook.drawee.components.DraweeEventTracker;
import com.facebook.drawee.components.RetryManager;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.controller.ControllerViewportVisibilityListener;
import com.facebook.drawee.controller.ForwardingControllerListener;
import com.facebook.drawee.gestures.GestureDetector;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.facebook.drawee.interfaces.SettableDraweeHierarchy;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public abstract class AbstractDraweeController<T extends Object, INFO extends Object> implements DeferredReleaser.Releasable, GestureDetector.ClickListener, DraweeController {

   private static final Class<?> TAG = AbstractDraweeController.class;
   private Object mCallerContext;
   @Nullable
   private String mContentDescription;
   @Nullable
   private ControllerListener<INFO> mControllerListener;
   @Nullable
   private Drawable mControllerOverlay;
   @Nullable
   private ControllerViewportVisibilityListener mControllerViewportVisibilityListener;
   @Nullable
   private DataSource<T> mDataSource;
   private final DeferredReleaser mDeferredReleaser;
   @Nullable
   private Drawable mDrawable;
   private final DraweeEventTracker mEventTracker = DraweeEventTracker.newInstance();
   @Nullable
   private T mFetchedImage;
   @Nullable
   private GestureDetector mGestureDetector;
   private boolean mHasFetchFailed;
   private String mId;
   private boolean mIsAttached;
   private boolean mIsRequestSubmitted;
   private boolean mIsVisibleInViewportHint;
   private boolean mRetainImageOnFailure;
   @Nullable
   private RetryManager mRetryManager;
   @Nullable
   private SettableDraweeHierarchy mSettableDraweeHierarchy;
   private final Executor mUiThreadImmediateExecutor;


   public AbstractDraweeController(DeferredReleaser var1, Executor var2, String var3, Object var4) {
      this.mDeferredReleaser = var1;
      this.mUiThreadImmediateExecutor = var2;
      this.init(var3, var4, true);
   }

   private void init(String var1, Object var2, boolean var3) {
      this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_INIT_CONTROLLER);
      if(!var3 && this.mDeferredReleaser != null) {
         this.mDeferredReleaser.cancelDeferredRelease(this);
      }

      this.mIsAttached = false;
      this.mIsVisibleInViewportHint = false;
      this.releaseFetch();
      this.mRetainImageOnFailure = false;
      if(this.mRetryManager != null) {
         this.mRetryManager.init();
      }

      if(this.mGestureDetector != null) {
         this.mGestureDetector.init();
         this.mGestureDetector.setClickListener(this);
      }

      if(this.mControllerListener instanceof AbstractDraweeController.InternalForwardingListener) {
         ((AbstractDraweeController.InternalForwardingListener)this.mControllerListener).clearListeners();
      } else {
         this.mControllerListener = null;
      }

      this.mControllerViewportVisibilityListener = null;
      if(this.mSettableDraweeHierarchy != null) {
         this.mSettableDraweeHierarchy.reset();
         this.mSettableDraweeHierarchy.setControllerOverlay((Drawable)null);
         this.mSettableDraweeHierarchy = null;
      }

      this.mControllerOverlay = null;
      if(FLog.isLoggable(2)) {
         FLog.v(TAG, "controller %x %s -> %s: initialize", Integer.valueOf(System.identityHashCode(this)), this.mId, var1);
      }

      this.mId = var1;
      this.mCallerContext = var2;
   }

   private boolean isExpectedDataSource(String var1, DataSource<T> var2) {
      return var2 == null && this.mDataSource == null?true:var1.equals(this.mId) && var2 == this.mDataSource && this.mIsRequestSubmitted;
   }

   private void logMessageAndFailure(String var1, Throwable var2) {
      if(FLog.isLoggable(2)) {
         FLog.v(TAG, "controller %x %s: %s: failure: %s", Integer.valueOf(System.identityHashCode(this)), this.mId, var1, var2);
      }

   }

   private void logMessageAndImage(String var1, T var2) {
      if(FLog.isLoggable(2)) {
         FLog.v(TAG, "controller %x %s: %s: image: %s %x", new Object[]{Integer.valueOf(System.identityHashCode(this)), this.mId, var1, this.getImageClass(var2), Integer.valueOf(this.getImageHash(var2))});
      }

   }

   private void onFailureInternal(String var1, DataSource<T> var2, Throwable var3, boolean var4) {
      if(!this.isExpectedDataSource(var1, var2)) {
         this.logMessageAndFailure("ignore_old_datasource @ onFailure", var3);
         var2.close();
      } else {
         DraweeEventTracker var6 = this.mEventTracker;
         DraweeEventTracker.Event var5;
         if(var4) {
            var5 = DraweeEventTracker.Event.ON_DATASOURCE_FAILURE;
         } else {
            var5 = DraweeEventTracker.Event.ON_DATASOURCE_FAILURE_INT;
         }

         var6.recordEvent(var5);
         if(!var4) {
            this.logMessageAndFailure("intermediate_failed @ onFailure", var3);
            this.getControllerListener().onIntermediateImageFailed(this.mId, var3);
         } else {
            this.logMessageAndFailure("final_failed @ onFailure", var3);
            this.mDataSource = null;
            this.mHasFetchFailed = true;
            if(this.mRetainImageOnFailure && this.mDrawable != null) {
               this.mSettableDraweeHierarchy.setImage(this.mDrawable, 1.0F, true);
            } else if(this.shouldRetryOnTap()) {
               this.mSettableDraweeHierarchy.setRetry(var3);
            } else {
               this.mSettableDraweeHierarchy.setFailure(var3);
            }

            this.getControllerListener().onFailure(this.mId, var3);
         }
      }
   }

   private void onNewResultInternal(String param1, DataSource<T> param2, @Nullable T param3, float param4, boolean param5, boolean param6) {
      // $FF: Couldn't be decompiled
   }

   private void onProgressUpdateInternal(String var1, DataSource<T> var2, float var3, boolean var4) {
      if(!this.isExpectedDataSource(var1, var2)) {
         this.logMessageAndFailure("ignore_old_datasource @ onProgress", (Throwable)null);
         var2.close();
      } else {
         if(!var4) {
            this.mSettableDraweeHierarchy.setProgress(var3, false);
         }

      }
   }

   private void releaseFetch() {
      boolean var1 = this.mIsRequestSubmitted;
      this.mIsRequestSubmitted = false;
      this.mHasFetchFailed = false;
      if(this.mDataSource != null) {
         this.mDataSource.close();
         this.mDataSource = null;
      }

      if(this.mDrawable != null) {
         this.releaseDrawable(this.mDrawable);
      }

      if(this.mContentDescription != null) {
         this.mContentDescription = null;
      }

      this.mDrawable = null;
      if(this.mFetchedImage != null) {
         this.logMessageAndImage("release", this.mFetchedImage);
         this.releaseImage(this.mFetchedImage);
         this.mFetchedImage = null;
      }

      if(var1) {
         this.getControllerListener().onRelease(this.mId);
      }

   }

   private boolean shouldRetryOnTap() {
      return this.mHasFetchFailed && this.mRetryManager != null && this.mRetryManager.shouldRetryOnTap();
   }

   public void addControllerListener(ControllerListener<? super INFO> var1) {
      Preconditions.checkNotNull(var1);
      if(this.mControllerListener instanceof AbstractDraweeController.InternalForwardingListener) {
         ((AbstractDraweeController.InternalForwardingListener)this.mControllerListener).addListener(var1);
      } else if(this.mControllerListener != null) {
         this.mControllerListener = AbstractDraweeController.InternalForwardingListener.createInternal(this.mControllerListener, var1);
      } else {
         this.mControllerListener = var1;
      }
   }

   protected abstract Drawable createDrawable(T var1);

   @Nullable
   public Animatable getAnimatable() {
      return this.mDrawable instanceof Animatable?(Animatable)this.mDrawable:null;
   }

   protected T getCachedImage() {
      return null;
   }

   public Object getCallerContext() {
      return this.mCallerContext;
   }

   @Nullable
   public String getContentDescription() {
      return this.mContentDescription;
   }

   protected ControllerListener<INFO> getControllerListener() {
      return this.mControllerListener == null?BaseControllerListener.getNoOpListener():this.mControllerListener;
   }

   @Nullable
   protected Drawable getControllerOverlay() {
      return this.mControllerOverlay;
   }

   protected abstract DataSource<T> getDataSource();

   @Nullable
   protected GestureDetector getGestureDetector() {
      return this.mGestureDetector;
   }

   @Nullable
   public DraweeHierarchy getHierarchy() {
      return this.mSettableDraweeHierarchy;
   }

   public String getId() {
      return this.mId;
   }

   protected String getImageClass(@Nullable T var1) {
      return var1 != null?var1.getClass().getSimpleName():"<null>";
   }

   protected int getImageHash(@Nullable T var1) {
      return System.identityHashCode(var1);
   }

   @Nullable
   protected abstract INFO getImageInfo(T var1);

   @Nullable
   protected RetryManager getRetryManager() {
      return this.mRetryManager;
   }

   protected void initialize(String var1, Object var2) {
      this.init(var1, var2, false);
   }

   public void onAttach() {
      if(FLog.isLoggable(2)) {
         Class var3 = TAG;
         int var1 = System.identityHashCode(this);
         String var4 = this.mId;
         String var2;
         if(this.mIsRequestSubmitted) {
            var2 = "request already submitted";
         } else {
            var2 = "request needs submit";
         }

         FLog.v(var3, "controller %x %s: onAttach: %s", Integer.valueOf(var1), var4, var2);
      }

      this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_ATTACH_CONTROLLER);
      Preconditions.checkNotNull(this.mSettableDraweeHierarchy);
      this.mDeferredReleaser.cancelDeferredRelease(this);
      this.mIsAttached = true;
      if(!this.mIsRequestSubmitted) {
         this.submitRequest();
      }

   }

   public boolean onClick() {
      if(FLog.isLoggable(2)) {
         FLog.v(TAG, "controller %x %s: onClick", (Object)Integer.valueOf(System.identityHashCode(this)), (Object)this.mId);
      }

      if(this.shouldRetryOnTap()) {
         this.mRetryManager.notifyTapToRetry();
         this.mSettableDraweeHierarchy.reset();
         this.submitRequest();
         return true;
      } else {
         return false;
      }
   }

   public void onDetach() {
      if(FLog.isLoggable(2)) {
         FLog.v(TAG, "controller %x %s: onDetach", (Object)Integer.valueOf(System.identityHashCode(this)), (Object)this.mId);
      }

      this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_DETACH_CONTROLLER);
      this.mIsAttached = false;
      this.mDeferredReleaser.scheduleDeferredRelease(this);
   }

   public boolean onTouchEvent(MotionEvent var1) {
      if(FLog.isLoggable(2)) {
         FLog.v(TAG, "controller %x %s: onTouchEvent %s", Integer.valueOf(System.identityHashCode(this)), this.mId, var1);
      }

      if(this.mGestureDetector == null) {
         return false;
      } else if(!this.mGestureDetector.isCapturingGesture() && !this.shouldHandleGesture()) {
         return false;
      } else {
         this.mGestureDetector.onTouchEvent(var1);
         return true;
      }
   }

   public void onViewportVisibilityHint(boolean var1) {
      ControllerViewportVisibilityListener var2 = this.mControllerViewportVisibilityListener;
      if(var2 != null) {
         if(var1 && !this.mIsVisibleInViewportHint) {
            var2.onDraweeViewportEntry(this.mId);
         } else if(!var1 && this.mIsVisibleInViewportHint) {
            var2.onDraweeViewportExit(this.mId);
         }
      }

      this.mIsVisibleInViewportHint = var1;
   }

   public void release() {
      this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_RELEASE_CONTROLLER);
      if(this.mRetryManager != null) {
         this.mRetryManager.reset();
      }

      if(this.mGestureDetector != null) {
         this.mGestureDetector.reset();
      }

      if(this.mSettableDraweeHierarchy != null) {
         this.mSettableDraweeHierarchy.reset();
      }

      this.releaseFetch();
   }

   protected abstract void releaseDrawable(@Nullable Drawable var1);

   protected abstract void releaseImage(@Nullable T var1);

   public void removeControllerListener(ControllerListener<? super INFO> var1) {
      Preconditions.checkNotNull(var1);
      if(this.mControllerListener instanceof AbstractDraweeController.InternalForwardingListener) {
         ((AbstractDraweeController.InternalForwardingListener)this.mControllerListener).removeListener(var1);
      } else {
         if(this.mControllerListener == var1) {
            this.mControllerListener = null;
         }

      }
   }

   public void setContentDescription(@Nullable String var1) {
      this.mContentDescription = var1;
   }

   protected void setControllerOverlay(@Nullable Drawable var1) {
      this.mControllerOverlay = var1;
      if(this.mSettableDraweeHierarchy != null) {
         this.mSettableDraweeHierarchy.setControllerOverlay(this.mControllerOverlay);
      }

   }

   public void setControllerViewportVisibilityListener(@Nullable ControllerViewportVisibilityListener var1) {
      this.mControllerViewportVisibilityListener = var1;
   }

   protected void setGestureDetector(@Nullable GestureDetector var1) {
      this.mGestureDetector = var1;
      if(this.mGestureDetector != null) {
         this.mGestureDetector.setClickListener(this);
      }

   }

   public void setHierarchy(@Nullable DraweeHierarchy var1) {
      if(FLog.isLoggable(2)) {
         FLog.v(TAG, "controller %x %s: setHierarchy: %s", Integer.valueOf(System.identityHashCode(this)), this.mId, var1);
      }

      DraweeEventTracker var3 = this.mEventTracker;
      DraweeEventTracker.Event var2;
      if(var1 != null) {
         var2 = DraweeEventTracker.Event.ON_SET_HIERARCHY;
      } else {
         var2 = DraweeEventTracker.Event.ON_CLEAR_HIERARCHY;
      }

      var3.recordEvent(var2);
      if(this.mIsRequestSubmitted) {
         this.mDeferredReleaser.cancelDeferredRelease(this);
         this.release();
      }

      if(this.mSettableDraweeHierarchy != null) {
         this.mSettableDraweeHierarchy.setControllerOverlay((Drawable)null);
         this.mSettableDraweeHierarchy = null;
      }

      if(var1 != null) {
         Preconditions.checkArgument(var1 instanceof SettableDraweeHierarchy);
         this.mSettableDraweeHierarchy = (SettableDraweeHierarchy)var1;
         this.mSettableDraweeHierarchy.setControllerOverlay(this.mControllerOverlay);
      }

   }

   protected void setRetainImageOnFailure(boolean var1) {
      this.mRetainImageOnFailure = var1;
   }

   protected void setRetryManager(@Nullable RetryManager var1) {
      this.mRetryManager = var1;
   }

   protected boolean shouldHandleGesture() {
      return this.shouldRetryOnTap();
   }

   protected void submitRequest() {
      Object var1 = this.getCachedImage();
      if(var1 != null) {
         this.mDataSource = null;
         this.mIsRequestSubmitted = true;
         this.mHasFetchFailed = false;
         this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_SUBMIT_CACHE_HIT);
         this.getControllerListener().onSubmit(this.mId, this.mCallerContext);
         this.onNewResultInternal(this.mId, this.mDataSource, var1, 1.0F, true, true);
      } else {
         this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_DATASOURCE_SUBMIT);
         this.getControllerListener().onSubmit(this.mId, this.mCallerContext);
         this.mSettableDraweeHierarchy.setProgress(0.0F, true);
         this.mIsRequestSubmitted = true;
         this.mHasFetchFailed = false;
         this.mDataSource = this.getDataSource();
         if(FLog.isLoggable(2)) {
            FLog.v(TAG, "controller %x %s: submitRequest: dataSource: %x", Integer.valueOf(System.identityHashCode(this)), this.mId, Integer.valueOf(System.identityHashCode(this.mDataSource)));
         }

         BaseDataSubscriber var2 = new BaseDataSubscriber() {

            // $FF: synthetic field
            final String val$id;
            // $FF: synthetic field
            final boolean val$wasImmediate;

            {
               this.val$id = var2;
               this.val$wasImmediate = var3;
            }
            public void onFailureImpl(DataSource<T> var1) {
               AbstractDraweeController.this.onFailureInternal(this.val$id, var1, var1.getFailureCause(), true);
            }
            public void onNewResultImpl(DataSource<T> var1) {
               boolean var3 = var1.isFinished();
               float var2 = var1.getProgress();
               Object var4 = var1.getResult();
               if(var4 != null) {
                  AbstractDraweeController.this.onNewResultInternal(this.val$id, var1, var4, var2, var3, this.val$wasImmediate);
               } else {
                  if(var3) {
                     AbstractDraweeController.this.onFailureInternal(this.val$id, var1, new NullPointerException(), true);
                  }

               }
            }
            public void onProgressUpdate(DataSource<T> var1) {
               boolean var3 = var1.isFinished();
               float var2 = var1.getProgress();
               AbstractDraweeController.this.onProgressUpdateInternal(this.val$id, var1, var2, var3);
            }
         };
         this.mDataSource.subscribe(var2, this.mUiThreadImmediateExecutor);
      }
   }

   public String toString() {
      return Objects.toStringHelper((Object)this).add("isAttached", this.mIsAttached).add("isRequestSubmitted", this.mIsRequestSubmitted).add("hasFetchFailed", this.mHasFetchFailed).add("fetchedImage", this.getImageHash(this.mFetchedImage)).add("events", this.mEventTracker.toString()).toString();
   }

   static class InternalForwardingListener<INFO extends Object> extends ForwardingControllerListener<INFO> {

      public static <INFO extends Object> AbstractDraweeController.InternalForwardingListener<INFO> createInternal(ControllerListener<? super INFO> var0, ControllerListener<? super INFO> var1) {
         AbstractDraweeController.InternalForwardingListener var2 = new AbstractDraweeController.InternalForwardingListener();
         var2.addListener(var0);
         var2.addListener(var1);
         return var2;
      }
   }
}
