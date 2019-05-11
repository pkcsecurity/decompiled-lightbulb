package com.facebook.litho;

import android.animation.StateListAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import com.facebook.litho.CommonUtils;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.InternalNode;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.drawable.ComparableDrawable;
import com.facebook.litho.drawable.DefaultComparableDrawable;
import com.facebook.litho.reference.DrawableReference;
import com.facebook.litho.reference.Reference;
import com.facebook.yoga.YogaDirection;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

class ViewNodeInfo {

   private Reference<? extends Drawable> mBackground;
   private Rect mExpandedTouchBounds;
   private ComparableDrawable mForeground;
   private YogaDirection mLayoutDirection;
   private Rect mPadding;
   private final AtomicInteger mReferenceCount = new AtomicInteger(0);
   @Nullable
   private StateListAnimator mStateListAnimator;
   @DrawableRes
   private int mStateListAnimatorRes;


   static ViewNodeInfo acquire() {
      ViewNodeInfo var0 = ComponentsPools.acquireViewNodeInfo();
      if(var0.mReferenceCount.getAndSet(1) != 0) {
         throw new IllegalStateException("The ViewNodeInfo reference acquired from the pool  wasn\'t correctly released.");
      } else {
         return var0;
      }
   }

   ViewNodeInfo acquireRef() {
      if(this.mReferenceCount.getAndIncrement() < 1) {
         throw new IllegalStateException("The ViewNodeInfo being acquired wasn\'t correctly initialized.");
      } else {
         return this;
      }
   }

   Reference<? extends Drawable> getBackground() {
      return this.mBackground;
   }

   @Nullable
   Rect getExpandedTouchBounds() {
      return this.mExpandedTouchBounds != null && !this.mExpandedTouchBounds.isEmpty()?this.mExpandedTouchBounds:null;
   }

   ComparableDrawable getForeground() {
      return this.mForeground;
   }

   YogaDirection getLayoutDirection() {
      return this.mLayoutDirection;
   }

   int getPaddingBottom() {
      return this.mPadding != null?this.mPadding.bottom:0;
   }

   int getPaddingLeft() {
      return this.mPadding != null?this.mPadding.left:0;
   }

   int getPaddingRight() {
      return this.mPadding != null?this.mPadding.right:0;
   }

   int getPaddingTop() {
      return this.mPadding != null?this.mPadding.top:0;
   }

   @Nullable
   StateListAnimator getStateListAnimator() {
      return this.mStateListAnimator;
   }

   @DrawableRes
   int getStateListAnimatorRes() {
      return this.mStateListAnimatorRes;
   }

   boolean hasPadding() {
      return this.mPadding != null;
   }

   public boolean isEquivalentTo(ViewNodeInfo var1) {
      return this == var1?true:(var1 == null?false:(!DrawableReference.isEquivalentWithExperiment(this.mBackground, var1.mBackground)?false:(!DefaultComparableDrawable.isEquivalentToWithExperiment(this.mForeground, var1.mForeground)?false:(!CommonUtils.equals(this.mPadding, var1.mPadding)?false:(!CommonUtils.equals(this.mExpandedTouchBounds, var1.mExpandedTouchBounds)?false:(!CommonUtils.equals(this.mLayoutDirection, var1.mLayoutDirection)?false:(this.mStateListAnimatorRes != var1.mStateListAnimatorRes?false:CommonUtils.equals(this.mStateListAnimator, var1.mStateListAnimator))))))));
   }

   void release() {
      int var1 = this.mReferenceCount.decrementAndGet();
      if(var1 < 0) {
         throw new IllegalStateException("Trying to release a recycled ViewNodeInfo.");
      } else if(var1 <= 0) {
         if(!ComponentsConfiguration.disablePools) {
            this.mBackground = null;
            this.mForeground = null;
            this.mLayoutDirection = YogaDirection.INHERIT;
            this.mStateListAnimator = null;
            if(this.mPadding != null) {
               ComponentsPools.release(this.mPadding);
               this.mPadding = null;
            }

            if(this.mExpandedTouchBounds != null) {
               ComponentsPools.release(this.mExpandedTouchBounds);
               this.mExpandedTouchBounds = null;
            }

            ComponentsPools.release(this);
         }
      }
   }

   void setBackground(Reference<? extends Drawable> var1) {
      this.mBackground = var1;
   }

   void setExpandedTouchBounds(InternalNode var1, int var2, int var3, int var4, int var5) {
      if(var1.hasTouchExpansion()) {
         int var6 = var1.getTouchExpansionLeft();
         int var7 = var1.getTouchExpansionTop();
         int var8 = var1.getTouchExpansionRight();
         int var9 = var1.getTouchExpansionBottom();
         if(var6 != 0 || var7 != 0 || var8 != 0 || var9 != 0) {
            if(this.mExpandedTouchBounds != null) {
               throw new IllegalStateException("ExpandedTouchBounds already initialized for this ViewNodeInfo.");
            } else {
               this.mExpandedTouchBounds = ComponentsPools.acquireRect();
               this.mExpandedTouchBounds.set(var2 - var6, var3 - var7, var4 + var8, var5 + var9);
            }
         }
      }
   }

   void setForeground(ComparableDrawable var1) {
      this.mForeground = var1;
   }

   void setLayoutDirection(YogaDirection var1) {
      this.mLayoutDirection = var1;
   }

   void setPadding(int var1, int var2, int var3, int var4) {
      if(this.mPadding != null) {
         throw new IllegalStateException("Padding already initialized for this ViewNodeInfo.");
      } else {
         this.mPadding = ComponentsPools.acquireRect();
         this.mPadding.set(var1, var2, var3, var4);
      }
   }

   void setStateListAnimator(StateListAnimator var1) {
      this.mStateListAnimator = var1;
   }

   void setStateListAnimatorRes(@DrawableRes int var1) {
      this.mStateListAnimatorRes = var1;
   }
}
