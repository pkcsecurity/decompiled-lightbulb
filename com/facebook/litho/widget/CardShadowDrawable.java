package com.facebook.litho.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Path.FillType;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

public class CardShadowDrawable extends Drawable {

   static final float SHADOW_MULTIPLIER = 1.5F;
   private float mCornerRadius;
   private final Path mCornerShadowBottomPath = new Path();
   private final Paint mCornerShadowPaint = new Paint(5);
   private final Path mCornerShadowTopPath = new Path();
   private boolean mDirty = true;
   private final Paint mEdgeShadowPaint;
   private boolean mHideBottomShadow;
   private boolean mHideTopShadow;
   private float mRawShadowSize;
   private int mShadowEndColor;
   private float mShadowSize;
   private int mShadowStartColor;


   CardShadowDrawable() {
      this.mCornerShadowPaint.setStyle(Style.FILL);
      this.mEdgeShadowPaint = new Paint(this.mCornerShadowPaint);
      this.mEdgeShadowPaint.setAntiAlias(false);
   }

   private void buildShadow() {
      int var6 = getShadowHorizontal(this.mRawShadowSize);
      int var7 = getShadowTop(this.mRawShadowSize);
      int var8 = getShadowBottom(this.mRawShadowSize);
      float var1 = (float)var6;
      float var2 = this.mCornerRadius + var1;
      Paint var12 = this.mCornerShadowPaint;
      int var9 = this.mShadowStartColor;
      int var10 = this.mShadowStartColor;
      int var11 = this.mShadowEndColor;
      TileMode var13 = TileMode.CLAMP;
      var12.setShader(new RadialGradient(var2, var2, var2, new int[]{var9, var10, var11}, new float[]{0.0F, 0.2F, 1.0F}, var13));
      float var4 = (float)var7;
      RectF var16 = new RectF(var1, var4, this.mCornerRadius * 2.0F + var1, this.mCornerRadius * 2.0F + var4);
      RectF var15 = new RectF(0.0F, 0.0F, this.mCornerRadius * 2.0F, this.mCornerRadius * 2.0F);
      this.mCornerShadowTopPath.reset();
      this.mCornerShadowTopPath.setFillType(FillType.EVEN_ODD);
      this.mCornerShadowTopPath.moveTo(this.mCornerRadius + var1, var4);
      this.mCornerShadowTopPath.arcTo(var16, 270.0F, -90.0F, true);
      Path var17 = this.mCornerShadowTopPath;
      float var3 = (float)(-var6);
      var17.rLineTo(var3, 0.0F);
      this.mCornerShadowTopPath.lineTo(0.0F, this.mCornerRadius);
      this.mCornerShadowTopPath.arcTo(var15, 180.0F, 90.0F, true);
      this.mCornerShadowTopPath.lineTo(this.mCornerRadius + var1, 0.0F);
      this.mCornerShadowTopPath.rLineTo(0.0F, var4);
      this.mCornerShadowTopPath.close();
      var15 = new RectF((float)getShadowHorizontal(this.mRawShadowSize), (float)getShadowBottom(this.mRawShadowSize), (float)getShadowHorizontal(this.mRawShadowSize) + this.mCornerRadius * 2.0F, (float)getShadowBottom(this.mRawShadowSize) + this.mCornerRadius * 2.0F);
      var16 = new RectF(0.0F, 0.0F, this.mCornerRadius * 2.0F, this.mCornerRadius * 2.0F);
      this.mCornerShadowBottomPath.reset();
      this.mCornerShadowBottomPath.setFillType(FillType.EVEN_ODD);
      Path var14 = this.mCornerShadowBottomPath;
      var4 = this.mCornerRadius;
      float var5 = (float)var8;
      var14.moveTo(var4 + var1, var5);
      this.mCornerShadowBottomPath.arcTo(var15, 270.0F, -90.0F, true);
      this.mCornerShadowBottomPath.rLineTo(var3, 0.0F);
      this.mCornerShadowBottomPath.lineTo(0.0F, this.mCornerRadius);
      this.mCornerShadowBottomPath.arcTo(var16, 180.0F, 90.0F, true);
      this.mCornerShadowBottomPath.lineTo(var1 + this.mCornerRadius, 0.0F);
      this.mCornerShadowBottomPath.rLineTo(0.0F, var5);
      this.mCornerShadowBottomPath.close();
      var12 = this.mEdgeShadowPaint;
      var6 = this.mShadowStartColor;
      var7 = this.mShadowStartColor;
      var8 = this.mShadowEndColor;
      var13 = TileMode.CLAMP;
      var12.setShader(new LinearGradient(0.0F, var2, 0.0F, 0.0F, new int[]{var6, var7, var8}, new float[]{0.0F, 0.2F, 1.0F}, var13));
      this.mEdgeShadowPaint.setAntiAlias(false);
   }

   private void drawShadowCorners(Canvas var1, Rect var2) {
      int var3 = var1.save();
      if(!this.mHideTopShadow) {
         var1.translate((float)var2.left, (float)var2.top);
         var1.drawPath(this.mCornerShadowTopPath, this.mCornerShadowPaint);
         var1.restoreToCount(var3);
         var3 = var1.save();
         var1.translate((float)var2.right, (float)var2.top);
         var1.scale(-1.0F, 1.0F);
         var1.drawPath(this.mCornerShadowTopPath, this.mCornerShadowPaint);
         var1.restoreToCount(var3);
      }

      if(!this.mHideBottomShadow) {
         var3 = var1.save();
         var1.translate((float)var2.right, (float)var2.bottom);
         var1.scale(-1.0F, -1.0F);
         var1.drawPath(this.mCornerShadowBottomPath, this.mCornerShadowPaint);
         var1.restoreToCount(var3);
         var3 = var1.save();
         var1.translate((float)var2.left, (float)var2.bottom);
         var1.scale(1.0F, -1.0F);
         var1.drawPath(this.mCornerShadowBottomPath, this.mCornerShadowPaint);
         var1.restoreToCount(var3);
      }

   }

   private void drawShadowEdges(Canvas var1, Rect var2) {
      int var10 = getShadowHorizontal(this.mRawShadowSize);
      int var7 = getShadowTop(this.mRawShadowSize);
      int var8 = getShadowRight(this.mRawShadowSize);
      int var9 = getShadowBottom(this.mRawShadowSize);
      int var11 = var1.save();
      if(!this.mHideTopShadow) {
         var1.translate((float)var2.left, (float)var2.top);
         var1.drawRect((float)var10 + this.mCornerRadius, 0.0F, (float)var2.width() - this.mCornerRadius - (float)var8, (float)var7, this.mEdgeShadowPaint);
         var1.restoreToCount(var11);
      }

      if(!this.mHideBottomShadow) {
         var11 = var1.save();
         var1.translate((float)var2.right, (float)var2.bottom);
         var1.rotate(180.0F);
         var1.drawRect((float)var8 + this.mCornerRadius, 0.0F, (float)var2.width() - this.mCornerRadius - (float)var10, (float)var9, this.mEdgeShadowPaint);
         var1.restoreToCount(var11);
      }

      var11 = var1.save();
      var1.translate((float)var2.left, (float)var2.bottom);
      var1.rotate(270.0F);
      boolean var12 = this.mHideBottomShadow;
      float var5 = 0.0F;
      float var3;
      if(var12) {
         var3 = 0.0F;
      } else {
         var3 = (float)var9 + this.mCornerRadius;
      }

      float var6 = (float)var2.height();
      float var4;
      if(this.mHideTopShadow) {
         var4 = 0.0F;
      } else {
         var4 = this.mCornerRadius + (float)var7;
      }

      var1.drawRect(var3, 0.0F, var6 - var4, (float)var10, this.mEdgeShadowPaint);
      var1.restoreToCount(var11);
      var10 = var1.save();
      var1.translate((float)var2.right, (float)var2.top);
      var1.rotate(90.0F);
      if(this.mHideTopShadow) {
         var3 = 0.0F;
      } else {
         var3 = (float)var7 + this.mCornerRadius;
      }

      var6 = (float)var2.height();
      if(this.mHideBottomShadow) {
         var4 = var5;
      } else {
         var4 = this.mCornerRadius + (float)var9;
      }

      var1.drawRect(var3, 0.0F, var6 - var4, (float)var8, this.mEdgeShadowPaint);
      var1.restoreToCount(var10);
   }

   public static int getShadowBottom(float var0) {
      return (int)Math.ceil((double)(var0 * 1.5F));
   }

   public static int getShadowHorizontal(float var0) {
      return (int)Math.ceil((double)var0);
   }

   public static int getShadowRight(float var0) {
      return (int)Math.ceil((double)var0);
   }

   public static int getShadowTop(float var0) {
      return (int)Math.ceil((double)(var0 / 2.0F));
   }

   private static int toEven(float var0) {
      int var1 = (int)(var0 + 0.5F);
      return var1 % 2 == 1?var1 - 1:var1;
   }

   public void draw(Canvas var1) {
      if(this.mDirty) {
         this.buildShadow();
         this.mDirty = false;
      }

      Rect var2 = this.getBounds();
      this.drawShadowCorners(var1, var2);
      this.drawShadowEdges(var1, var2);
   }

   public int getOpacity() {
      return -3;
   }

   public void setAlpha(int var1) {
      this.mCornerShadowPaint.setAlpha(var1);
      this.mEdgeShadowPaint.setAlpha(var1);
   }

   public void setColorFilter(ColorFilter var1) {
      this.mCornerShadowPaint.setColorFilter(var1);
      this.mEdgeShadowPaint.setColorFilter(var1);
   }

   void setCornerRadius(float var1) {
      var1 = (float)((int)(var1 + 0.5F));
      if(this.mCornerRadius != var1) {
         this.mCornerRadius = var1;
         this.mDirty = true;
         this.invalidateSelf();
      }
   }

   void setHideBottomShadow(boolean var1) {
      this.mHideBottomShadow = var1;
   }

   void setHideTopShadow(boolean var1) {
      this.mHideTopShadow = var1;
   }

   void setShadowEndColor(int var1) {
      if(this.mShadowEndColor != var1) {
         this.mShadowEndColor = var1;
         this.mDirty = true;
         this.invalidateSelf();
      }
   }

   void setShadowSize(float var1) {
      if(var1 < 0.0F) {
         throw new IllegalArgumentException("invalid shadow size");
      } else {
         var1 = (float)toEven(var1);
         if(this.mRawShadowSize != var1) {
            this.mRawShadowSize = var1;
            this.mShadowSize = (float)((int)(var1 * 1.5F + 0.5F));
            this.mDirty = true;
            this.invalidateSelf();
         }
      }
   }

   void setShadowStartColor(int var1) {
      if(this.mShadowStartColor != var1) {
         this.mShadowStartColor = var1;
         this.mDirty = true;
         this.invalidateSelf();
      }
   }
}
