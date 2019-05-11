package com.facebook.litho.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Path.FillType;
import android.graphics.drawable.Drawable;

class CardClipDrawable extends Drawable {

   static final int BOTTOM_LEFT = 4;
   static final int BOTTOM_RIGHT = 8;
   static final int NONE = 0;
   static final int TOP_LEFT = 1;
   static final int TOP_RIGHT = 2;
   private final Paint mCornerPaint = new Paint(5);
   private final Path mCornerPath = new Path();
   private float mCornerRadius;
   private boolean mDirty = true;
   private int mDisableClipCorners = 0;


   private void buildClippingCorners() {
      this.mCornerPath.reset();
      RectF var1 = new RectF(0.0F, 0.0F, this.mCornerRadius * 2.0F, this.mCornerRadius * 2.0F);
      this.mCornerPath.setFillType(FillType.EVEN_ODD);
      this.mCornerPath.moveTo(0.0F, 0.0F);
      this.mCornerPath.lineTo(0.0F, this.mCornerRadius);
      this.mCornerPath.arcTo(var1, 180.0F, 90.0F, true);
      this.mCornerPath.lineTo(0.0F, 0.0F);
      this.mCornerPath.close();
   }

   public void draw(Canvas var1) {
      if(this.mDirty) {
         this.buildClippingCorners();
         this.mDirty = false;
      }

      Rect var3 = this.getBounds();
      int var2;
      if((this.mDisableClipCorners & 1) == 0) {
         var2 = var1.save();
         var1.translate((float)var3.left, (float)var3.top);
         var1.drawPath(this.mCornerPath, this.mCornerPaint);
         var1.restoreToCount(var2);
      }

      if((this.mDisableClipCorners & 8) == 0) {
         var2 = var1.save();
         var1.translate((float)var3.right, (float)var3.bottom);
         var1.rotate(180.0F);
         var1.drawPath(this.mCornerPath, this.mCornerPaint);
         var1.restoreToCount(var2);
      }

      if((this.mDisableClipCorners & 4) == 0) {
         var2 = var1.save();
         var1.translate((float)var3.left, (float)var3.bottom);
         var1.rotate(270.0F);
         var1.drawPath(this.mCornerPath, this.mCornerPaint);
         var1.restoreToCount(var2);
      }

      if((this.mDisableClipCorners & 2) == 0) {
         var2 = var1.save();
         var1.translate((float)var3.right, (float)var3.top);
         var1.rotate(90.0F);
         var1.drawPath(this.mCornerPath, this.mCornerPaint);
         var1.restoreToCount(var2);
      }

   }

   public int getOpacity() {
      return -3;
   }

   public void setAlpha(int var1) {
      this.mCornerPaint.setAlpha(var1);
   }

   void setClippingColor(int var1) {
      if(this.mCornerPaint.getColor() != var1) {
         this.mCornerPaint.setColor(var1);
         this.mDirty = true;
         this.invalidateSelf();
      }
   }

   public void setColorFilter(ColorFilter var1) {
      this.mCornerPaint.setColorFilter(var1);
   }

   void setCornerRadius(float var1) {
      var1 = (float)((int)(var1 + 0.5F));
      if(this.mCornerRadius != var1) {
         this.mCornerRadius = var1;
         this.mDirty = true;
         this.invalidateSelf();
      }
   }

   void setDisableClip(int var1) {
      if((this.mDisableClipCorners & var1) == 0) {
         this.mDisableClipCorners = var1;
         this.mDirty = true;
         this.invalidateSelf();
      }
   }
}
