package com.facebook.drawee.controller;

import android.content.Context;
import android.graphics.drawable.Animatable;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSources;
import com.facebook.datasource.FirstAvailableDataSourceSupplier;
import com.facebook.datasource.IncreasingQualityDataSourceSupplier;
import com.facebook.drawee.components.RetryManager;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.controller.ControllerViewportVisibilityListener;
import com.facebook.drawee.gestures.GestureDetector;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.infer.annotation.ReturnsOwnership;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nullable;

public abstract class AbstractDraweeControllerBuilder<BUILDER extends AbstractDraweeControllerBuilder<BUILDER, REQUEST, IMAGE, INFO>, REQUEST extends Object, IMAGE extends Object, INFO extends Object> implements SimpleDraweeControllerBuilder {

   private static final NullPointerException NO_REQUEST_EXCEPTION = new NullPointerException("No image request was specified!");
   private static final ControllerListener<Object> sAutoPlayAnimationsListener = new BaseControllerListener() {
      public void onFinalImageSet(String var1, @Nullable Object var2, @Nullable Animatable var3) {
         if(var3 != null) {
            var3.start();
         }

      }
   };
   private static final AtomicLong sIdCounter = new AtomicLong();
   private boolean mAutoPlayAnimations;
   private final Set<ControllerListener> mBoundControllerListeners;
   @Nullable
   private Object mCallerContext;
   private String mContentDescription;
   private final Context mContext;
   @Nullable
   private ControllerListener<? super INFO> mControllerListener;
   @Nullable
   private ControllerViewportVisibilityListener mControllerViewportVisibilityListener;
   @Nullable
   private Supplier<DataSource<IMAGE>> mDataSourceSupplier;
   @Nullable
   private REQUEST mImageRequest;
   @Nullable
   private REQUEST mLowResImageRequest;
   @Nullable
   private REQUEST[] mMultiImageRequests;
   @Nullable
   private DraweeController mOldController;
   private boolean mRetainImageOnFailure;
   private boolean mTapToRetryEnabled;
   private boolean mTryCacheOnlyFirst;


   protected AbstractDraweeControllerBuilder(Context var1, Set<ControllerListener> var2) {
      this.mContext = var1;
      this.mBoundControllerListeners = var2;
      this.init();
   }

   protected static String generateUniqueControllerId() {
      return String.valueOf(sIdCounter.getAndIncrement());
   }

   private void init() {
      this.mCallerContext = null;
      this.mImageRequest = null;
      this.mLowResImageRequest = null;
      this.mMultiImageRequests = null;
      this.mTryCacheOnlyFirst = true;
      this.mControllerListener = null;
      this.mControllerViewportVisibilityListener = null;
      this.mTapToRetryEnabled = false;
      this.mAutoPlayAnimations = false;
      this.mOldController = null;
      this.mContentDescription = null;
   }

   public AbstractDraweeController build() {
      this.validate();
      if(this.mImageRequest == null && this.mMultiImageRequests == null && this.mLowResImageRequest != null) {
         this.mImageRequest = this.mLowResImageRequest;
         this.mLowResImageRequest = null;
      }

      return this.buildController();
   }

   protected AbstractDraweeController buildController() {
      AbstractDraweeController var1 = this.obtainController();
      var1.setRetainImageOnFailure(this.getRetainImageOnFailure());
      var1.setContentDescription(this.getContentDescription());
      var1.setControllerViewportVisibilityListener(this.getControllerViewportVisibilityListener());
      this.maybeBuildAndSetRetryManager(var1);
      this.maybeAttachListeners(var1);
      return var1;
   }

   public boolean getAutoPlayAnimations() {
      return this.mAutoPlayAnimations;
   }

   @Nullable
   public Object getCallerContext() {
      return this.mCallerContext;
   }

   @Nullable
   public String getContentDescription() {
      return this.mContentDescription;
   }

   protected Context getContext() {
      return this.mContext;
   }

   @Nullable
   public ControllerListener<? super INFO> getControllerListener() {
      return this.mControllerListener;
   }

   @Nullable
   public ControllerViewportVisibilityListener getControllerViewportVisibilityListener() {
      return this.mControllerViewportVisibilityListener;
   }

   protected abstract DataSource<IMAGE> getDataSourceForRequest(REQUEST var1, Object var2, AbstractDraweeControllerBuilder.CacheLevel var3);

   @Nullable
   public Supplier<DataSource<IMAGE>> getDataSourceSupplier() {
      return this.mDataSourceSupplier;
   }

   protected Supplier<DataSource<IMAGE>> getDataSourceSupplierForRequest(REQUEST var1) {
      return this.getDataSourceSupplierForRequest(var1, AbstractDraweeControllerBuilder.CacheLevel.FULL_FETCH);
   }

   protected Supplier<DataSource<IMAGE>> getDataSourceSupplierForRequest(final REQUEST var1, final AbstractDraweeControllerBuilder.CacheLevel var2) {
      return new Supplier() {

         // $FF: synthetic field
         final Object val$callerContext;

         {
            this.val$callerContext = var3;
         }
         public DataSource<IMAGE> get() {
            return AbstractDraweeControllerBuilder.this.getDataSourceForRequest(var1, this.val$callerContext, var2);
         }
         public String toString() {
            return Objects.toStringHelper((Object)this).add("request", var1.toString()).toString();
         }
      };
   }

   protected Supplier<DataSource<IMAGE>> getFirstAvailableDataSourceSupplier(REQUEST[] var1, boolean var2) {
      ArrayList var6 = new ArrayList(var1.length * 2);
      byte var5 = 0;
      int var4 = var5;
      if(var2) {
         int var3 = 0;

         while(true) {
            var4 = var5;
            if(var3 >= var1.length) {
               break;
            }

            var6.add(this.getDataSourceSupplierForRequest(var1[var3], AbstractDraweeControllerBuilder.CacheLevel.BITMAP_MEMORY_CACHE));
            ++var3;
         }
      }

      while(var4 < var1.length) {
         var6.add(this.getDataSourceSupplierForRequest(var1[var4]));
         ++var4;
      }

      return FirstAvailableDataSourceSupplier.create(var6);
   }

   @Nullable
   public REQUEST[] getFirstAvailableImageRequests() {
      return this.mMultiImageRequests;
   }

   @Nullable
   public REQUEST getImageRequest() {
      return this.mImageRequest;
   }

   @Nullable
   public REQUEST getLowResImageRequest() {
      return this.mLowResImageRequest;
   }

   @Nullable
   public DraweeController getOldController() {
      return this.mOldController;
   }

   public boolean getRetainImageOnFailure() {
      return this.mRetainImageOnFailure;
   }

   public boolean getTapToRetryEnabled() {
      return this.mTapToRetryEnabled;
   }

   protected abstract BUILDER getThis();

   protected void maybeAttachListeners(AbstractDraweeController var1) {
      if(this.mBoundControllerListeners != null) {
         Iterator var2 = this.mBoundControllerListeners.iterator();

         while(var2.hasNext()) {
            var1.addControllerListener((ControllerListener)var2.next());
         }
      }

      if(this.mControllerListener != null) {
         var1.addControllerListener(this.mControllerListener);
      }

      if(this.mAutoPlayAnimations) {
         var1.addControllerListener(sAutoPlayAnimationsListener);
      }

   }

   protected void maybeBuildAndSetGestureDetector(AbstractDraweeController var1) {
      if(var1.getGestureDetector() == null) {
         var1.setGestureDetector(GestureDetector.newInstance(this.mContext));
      }

   }

   protected void maybeBuildAndSetRetryManager(AbstractDraweeController var1) {
      if(this.mTapToRetryEnabled) {
         RetryManager var3 = var1.getRetryManager();
         RetryManager var2 = var3;
         if(var3 == null) {
            var2 = new RetryManager();
            var1.setRetryManager(var2);
         }

         var2.setTapToRetryEnabled(this.mTapToRetryEnabled);
         this.maybeBuildAndSetGestureDetector(var1);
      }
   }

   @ReturnsOwnership
   protected abstract AbstractDraweeController obtainController();

   protected Supplier<DataSource<IMAGE>> obtainDataSourceSupplier() {
      if(this.mDataSourceSupplier != null) {
         return this.mDataSourceSupplier;
      } else {
         Supplier var1 = null;
         if(this.mImageRequest != null) {
            var1 = this.getDataSourceSupplierForRequest(this.mImageRequest);
         } else if(this.mMultiImageRequests != null) {
            var1 = this.getFirstAvailableDataSourceSupplier(this.mMultiImageRequests, this.mTryCacheOnlyFirst);
         }

         Object var2 = var1;
         if(var1 != null) {
            var2 = var1;
            if(this.mLowResImageRequest != null) {
               ArrayList var4 = new ArrayList(2);
               var4.add(var1);
               var4.add(this.getDataSourceSupplierForRequest(this.mLowResImageRequest));
               var2 = IncreasingQualityDataSourceSupplier.create(var4);
            }
         }

         Object var3 = var2;
         if(var2 == null) {
            var3 = DataSources.getFailedDataSourceSupplier(NO_REQUEST_EXCEPTION);
         }

         return (Supplier)var3;
      }
   }

   public BUILDER reset() {
      this.init();
      return this.getThis();
   }

   public BUILDER setAutoPlayAnimations(boolean var1) {
      this.mAutoPlayAnimations = var1;
      return this.getThis();
   }

   public BUILDER setCallerContext(Object var1) {
      this.mCallerContext = var1;
      return this.getThis();
   }

   public BUILDER setContentDescription(String var1) {
      this.mContentDescription = var1;
      return this.getThis();
   }

   public BUILDER setControllerListener(ControllerListener<? super INFO> var1) {
      this.mControllerListener = var1;
      return this.getThis();
   }

   public BUILDER setControllerViewportVisibilityListener(@Nullable ControllerViewportVisibilityListener var1) {
      this.mControllerViewportVisibilityListener = var1;
      return this.getThis();
   }

   public void setDataSourceSupplier(@Nullable Supplier<DataSource<IMAGE>> var1) {
      this.mDataSourceSupplier = var1;
   }

   public BUILDER setFirstAvailableImageRequests(REQUEST[] var1) {
      return this.setFirstAvailableImageRequests(var1, true);
   }

   public BUILDER setFirstAvailableImageRequests(REQUEST[] var1, boolean var2) {
      this.mMultiImageRequests = var1;
      this.mTryCacheOnlyFirst = var2;
      return this.getThis();
   }

   public BUILDER setImageRequest(REQUEST var1) {
      this.mImageRequest = var1;
      return this.getThis();
   }

   public BUILDER setLowResImageRequest(REQUEST var1) {
      this.mLowResImageRequest = var1;
      return this.getThis();
   }

   public BUILDER setOldController(@Nullable DraweeController var1) {
      this.mOldController = var1;
      return this.getThis();
   }

   public BUILDER setRetainImageOnFailure(boolean var1) {
      this.mRetainImageOnFailure = var1;
      return this.getThis();
   }

   public BUILDER setTapToRetryEnabled(boolean var1) {
      this.mTapToRetryEnabled = var1;
      return this.getThis();
   }

   protected void validate() {
      Object[] var3 = this.mMultiImageRequests;
      boolean var2 = true;
      boolean var1;
      if(var3 != null && this.mImageRequest != null) {
         var1 = false;
      } else {
         var1 = true;
      }

      Preconditions.checkState(var1, "Cannot specify both ImageRequest and FirstAvailableImageRequests!");
      var1 = var2;
      if(this.mDataSourceSupplier != null) {
         if(this.mMultiImageRequests == null && this.mImageRequest == null && this.mLowResImageRequest == null) {
            var1 = var2;
         } else {
            var1 = false;
         }
      }

      Preconditions.checkState(var1, "Cannot specify DataSourceSupplier with other ImageRequests! Use one or the other.");
   }

   public static enum CacheLevel {

      // $FF: synthetic field
      private static final AbstractDraweeControllerBuilder.CacheLevel[] $VALUES = new AbstractDraweeControllerBuilder.CacheLevel[]{FULL_FETCH, DISK_CACHE, BITMAP_MEMORY_CACHE};
      BITMAP_MEMORY_CACHE("BITMAP_MEMORY_CACHE", 2),
      DISK_CACHE("DISK_CACHE", 1),
      FULL_FETCH("FULL_FETCH", 0);


      private CacheLevel(String var1, int var2) {}
   }
}
