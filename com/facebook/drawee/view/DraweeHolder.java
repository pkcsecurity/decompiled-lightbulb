package com.facebook.drawee.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.components.DraweeEventTracker;
import com.facebook.drawee.drawable.VisibilityAwareDrawable;
import com.facebook.drawee.drawable.VisibilityCallback;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import javax.annotation.Nullable;

public class DraweeHolder<DH extends Object & DraweeHierarchy> implements VisibilityCallback {

   private DraweeController mController = null;
   private final DraweeEventTracker mEventTracker = DraweeEventTracker.newInstance();
   private DH mHierarchy;
   private boolean mIsControllerAttached = false;
   private boolean mIsHolderAttached = false;
   private boolean mIsVisible = true;


   public DraweeHolder(@Nullable DH var1) {
      if(var1 != null) {
         this.setHierarchy(var1);
      }

   }

   private void attachController() {
      if(!this.mIsControllerAttached) {
         this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_ATTACH_CONTROLLER);
         this.mIsControllerAttached = true;
         if(this.mController != null && this.mController.getHierarchy() != null) {
            this.mController.onAttach();
         }

      }
   }

   private void attachOrDetachController() {
      if(this.mIsHolderAttached && this.mIsVisible) {
         this.attachController();
      } else {
         this.detachController();
      }
   }

   public static <DH extends Object & DraweeHierarchy> DraweeHolder<DH> create(@Nullable DH var0, Context var1) {
      DraweeHolder var2 = new DraweeHolder(var0);
      var2.registerWithContext(var1);
      return var2;
   }

   private void detachController() {
      if(this.mIsControllerAttached) {
         this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_DETACH_CONTROLLER);
         this.mIsControllerAttached = false;
         if(this.isControllerValid()) {
            this.mController.onDetach();
         }

      }
   }

   private boolean isControllerValid() {
      return this.mController != null && this.mController.getHierarchy() == this.mHierarchy;
   }

   private void setVisibilityCallback(@Nullable VisibilityCallback var1) {
      Drawable var2 = this.getTopLevelDrawable();
      if(var2 instanceof VisibilityAwareDrawable) {
         ((VisibilityAwareDrawable)var2).setVisibilityCallback(var1);
      }

   }

   @Nullable
   public DraweeController getController() {
      return this.mController;
   }

   protected DraweeEventTracker getDraweeEventTracker() {
      return this.mEventTracker;
   }

   public DH getHierarchy() {
      return (DraweeHierarchy)Preconditions.checkNotNull(this.mHierarchy);
   }

   public Drawable getTopLevelDrawable() {
      return this.mHierarchy == null?null:this.mHierarchy.getTopLevelDrawable();
   }

   public boolean hasHierarchy() {
      return this.mHierarchy != null;
   }

   public boolean isAttached() {
      return this.mIsHolderAttached;
   }

   public void onAttach() {
      this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_HOLDER_ATTACH);
      this.mIsHolderAttached = true;
      this.attachOrDetachController();
   }

   public void onDetach() {
      this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_HOLDER_DETACH);
      this.mIsHolderAttached = false;
      this.attachOrDetachController();
   }

   public void onDraw() {
      if(!this.mIsControllerAttached) {
         FLog.wtf(DraweeEventTracker.class, "%x: Draw requested for a non-attached controller %x. %s", new Object[]{Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(System.identityHashCode(this.mController)), this.toString()});
         this.mIsHolderAttached = true;
         this.mIsVisible = true;
         this.attachOrDetachController();
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      return !this.isControllerValid()?false:this.mController.onTouchEvent(var1);
   }

   public void onVisibilityChange(boolean var1) {
      if(this.mIsVisible != var1) {
         DraweeEventTracker var3 = this.mEventTracker;
         DraweeEventTracker.Event var2;
         if(var1) {
            var2 = DraweeEventTracker.Event.ON_DRAWABLE_SHOW;
         } else {
            var2 = DraweeEventTracker.Event.ON_DRAWABLE_HIDE;
         }

         var3.recordEvent(var2);
         this.mIsVisible = var1;
         this.attachOrDetachController();
      }
   }

   public void registerWithContext(Context var1) {}

   public void setController(@Nullable DraweeController var1) {
      boolean var2 = this.mIsControllerAttached;
      if(var2) {
         this.detachController();
      }

      if(this.isControllerValid()) {
         this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_CLEAR_OLD_CONTROLLER);
         this.mController.setHierarchy((DraweeHierarchy)null);
      }

      this.mController = var1;
      if(this.mController != null) {
         this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_SET_CONTROLLER);
         this.mController.setHierarchy(this.mHierarchy);
      } else {
         this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_CLEAR_CONTROLLER);
      }

      if(var2) {
         this.attachController();
      }

   }

   public void setHierarchy(DH var1) {
      this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_SET_HIERARCHY);
      boolean var3 = this.isControllerValid();
      this.setVisibilityCallback((VisibilityCallback)null);
      this.mHierarchy = (DraweeHierarchy)Preconditions.checkNotNull(var1);
      Drawable var4 = this.mHierarchy.getTopLevelDrawable();
      boolean var2;
      if(var4 != null && !var4.isVisible()) {
         var2 = false;
      } else {
         var2 = true;
      }

      this.onVisibilityChange(var2);
      this.setVisibilityCallback(this);
      if(var3) {
         this.mController.setHierarchy(var1);
      }

   }

   public String toString() {
      return Objects.toStringHelper((Object)this).add("controllerAttached", this.mIsControllerAttached).add("holderAttached", this.mIsHolderAttached).add("drawableVisible", this.mIsVisible).add("events", this.mEventTracker.toString()).toString();
   }
}
