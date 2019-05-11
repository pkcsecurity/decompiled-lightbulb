package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.drawee.drawable.ScalingUtils;

public class ScaleTypeDrawable extends ForwardingDrawable {

   @VisibleForTesting
   Matrix mDrawMatrix;
   @VisibleForTesting
   PointF mFocusPoint = null;
   @VisibleForTesting
   ScalingUtils.ScaleType mScaleType;
   @VisibleForTesting
   Object mScaleTypeState;
   private Matrix mTempMatrix = new Matrix();
   @VisibleForTesting
   int mUnderlyingHeight = 0;
   @VisibleForTesting
   int mUnderlyingWidth = 0;


   public ScaleTypeDrawable(Drawable var1, ScalingUtils.ScaleType var2) {
      super((Drawable)Preconditions.checkNotNull(var1));
      this.mScaleType = var2;
   }

   private void configureBoundsIfUnderlyingChanged() {
      boolean var4 = this.mScaleType instanceof ScalingUtils.StatefulScaleType;
      boolean var3 = true;
      boolean var1;
      if(var4) {
         Object var5 = ((ScalingUtils.StatefulScaleType)this.mScaleType).getState();
         if(var5 != null && var5.equals(this.mScaleTypeState)) {
            var1 = false;
         } else {
            var1 = true;
         }

         this.mScaleTypeState = var5;
      } else {
         var1 = false;
      }

      boolean var2 = var3;
      if(this.mUnderlyingWidth == this.getCurrent().getIntrinsicWidth()) {
         if(this.mUnderlyingHeight != this.getCurrent().getIntrinsicHeight()) {
            var2 = var3;
         } else {
            var2 = false;
         }
      }

      if(var2 || var1) {
         this.configureBounds();
      }

   }

   @VisibleForTesting
   void configureBounds() {
      Drawable var8 = this.getCurrent();
      Rect var7 = this.getBounds();
      int var3 = var7.width();
      int var4 = var7.height();
      int var5 = var8.getIntrinsicWidth();
      this.mUnderlyingWidth = var5;
      int var6 = var8.getIntrinsicHeight();
      this.mUnderlyingHeight = var6;
      if(var5 > 0 && var6 > 0) {
         if(var5 == var3 && var6 == var4) {
            var8.setBounds(var7);
            this.mDrawMatrix = null;
         } else if(this.mScaleType == ScalingUtils.ScaleType.FIT_XY) {
            var8.setBounds(var7);
            this.mDrawMatrix = null;
         } else {
            var8.setBounds(0, 0, var5, var6);
            ScalingUtils.ScaleType var10 = this.mScaleType;
            Matrix var9 = this.mTempMatrix;
            float var1;
            if(this.mFocusPoint != null) {
               var1 = this.mFocusPoint.x;
            } else {
               var1 = 0.5F;
            }

            float var2;
            if(this.mFocusPoint != null) {
               var2 = this.mFocusPoint.y;
            } else {
               var2 = 0.5F;
            }

            var10.getTransform(var9, var7, var5, var6, var1, var2);
            this.mDrawMatrix = this.mTempMatrix;
         }
      } else {
         var8.setBounds(var7);
         this.mDrawMatrix = null;
      }
   }

   public void draw(Canvas var1) {
      this.configureBoundsIfUnderlyingChanged();
      if(this.mDrawMatrix != null) {
         int var2 = var1.save();
         var1.clipRect(this.getBounds());
         var1.concat(this.mDrawMatrix);
         super.draw(var1);
         var1.restoreToCount(var2);
      } else {
         super.draw(var1);
      }
   }

   public PointF getFocusPoint() {
      return this.mFocusPoint;
   }

   public ScalingUtils.ScaleType getScaleType() {
      return this.mScaleType;
   }

   public void getTransform(Matrix var1) {
      this.getParentTransform(var1);
      this.configureBoundsIfUnderlyingChanged();
      if(this.mDrawMatrix != null) {
         var1.preConcat(this.mDrawMatrix);
      }

   }

   protected void onBoundsChange(Rect var1) {
      this.configureBounds();
   }

   public Drawable setCurrent(Drawable var1) {
      var1 = super.setCurrent(var1);
      this.configureBounds();
      return var1;
   }

   public void setFocusPoint(PointF var1) {
      if(!Objects.equal(this.mFocusPoint, var1)) {
         if(this.mFocusPoint == null) {
            this.mFocusPoint = new PointF();
         }

         this.mFocusPoint.set(var1);
         this.configureBounds();
         this.invalidateSelf();
      }
   }

   public void setScaleType(ScalingUtils.ScaleType var1) {
      if(!Objects.equal(this.mScaleType, var1)) {
         this.mScaleType = var1;
         this.mScaleTypeState = null;
         this.configureBounds();
         this.invalidateSelf();
      }
   }
}
