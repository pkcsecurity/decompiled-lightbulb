package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.Path.FillType;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.drawable.CloneableDrawable;
import com.facebook.drawee.drawable.DrawableUtils;

public class ProgressBarDrawable extends Drawable implements CloneableDrawable {

   private int mBackgroundColor = Integer.MIN_VALUE;
   private int mBarWidth = 20;
   private int mColor = -2147450625;
   private boolean mHideWhenZero = false;
   private boolean mIsVertical = false;
   private int mLevel = 0;
   private int mPadding = 10;
   private final Paint mPaint = new Paint(1);
   private final Path mPath = new Path();
   private int mRadius = 0;
   private final RectF mRect = new RectF();


   private void drawBar(Canvas var1, int var2) {
      this.mPaint.setColor(var2);
      this.mPaint.setStyle(Style.FILL_AND_STROKE);
      this.mPath.reset();
      this.mPath.setFillType(FillType.EVEN_ODD);
      this.mPath.addRoundRect(this.mRect, (float)Math.min(this.mRadius, this.mBarWidth / 2), (float)Math.min(this.mRadius, this.mBarWidth / 2), Direction.CW);
      var1.drawPath(this.mPath, this.mPaint);
   }

   private void drawHorizontalBar(Canvas var1, int var2, int var3) {
      Rect var6 = this.getBounds();
      var2 = (var6.width() - this.mPadding * 2) * var2 / 10000;
      int var4 = var6.left + this.mPadding;
      int var5 = var6.bottom - this.mPadding - this.mBarWidth;
      this.mRect.set((float)var4, (float)var5, (float)(var4 + var2), (float)(var5 + this.mBarWidth));
      this.drawBar(var1, var3);
   }

   private void drawVerticalBar(Canvas var1, int var2, int var3) {
      Rect var6 = this.getBounds();
      var2 = (var6.height() - this.mPadding * 2) * var2 / 10000;
      int var4 = var6.left + this.mPadding;
      int var5 = var6.top + this.mPadding;
      this.mRect.set((float)var4, (float)var5, (float)(var4 + this.mBarWidth), (float)(var5 + var2));
      this.drawBar(var1, var3);
   }

   public Drawable cloneDrawable() {
      ProgressBarDrawable var1 = new ProgressBarDrawable();
      var1.mBackgroundColor = this.mBackgroundColor;
      var1.mColor = this.mColor;
      var1.mPadding = this.mPadding;
      var1.mBarWidth = this.mBarWidth;
      var1.mLevel = this.mLevel;
      var1.mRadius = this.mRadius;
      var1.mHideWhenZero = this.mHideWhenZero;
      var1.mIsVertical = this.mIsVertical;
      return var1;
   }

   public void draw(Canvas var1) {
      if(!this.mHideWhenZero || this.mLevel != 0) {
         if(this.mIsVertical) {
            this.drawVerticalBar(var1, 10000, this.mBackgroundColor);
            this.drawVerticalBar(var1, this.mLevel, this.mColor);
         } else {
            this.drawHorizontalBar(var1, 10000, this.mBackgroundColor);
            this.drawHorizontalBar(var1, this.mLevel, this.mColor);
         }
      }
   }

   public int getBackgroundColor() {
      return this.mBackgroundColor;
   }

   public int getBarWidth() {
      return this.mBarWidth;
   }

   public int getColor() {
      return this.mColor;
   }

   public boolean getHideWhenZero() {
      return this.mHideWhenZero;
   }

   public boolean getIsVertical() {
      return this.mIsVertical;
   }

   public int getOpacity() {
      return DrawableUtils.getOpacityFromColor(this.mPaint.getColor());
   }

   public boolean getPadding(Rect var1) {
      var1.set(this.mPadding, this.mPadding, this.mPadding, this.mPadding);
      return this.mPadding != 0;
   }

   public int getRadius() {
      return this.mRadius;
   }

   protected boolean onLevelChange(int var1) {
      this.mLevel = var1;
      this.invalidateSelf();
      return true;
   }

   public void setAlpha(int var1) {
      this.mPaint.setAlpha(var1);
   }

   public void setBackgroundColor(int var1) {
      if(this.mBackgroundColor != var1) {
         this.mBackgroundColor = var1;
         this.invalidateSelf();
      }

   }

   public void setBarWidth(int var1) {
      if(this.mBarWidth != var1) {
         this.mBarWidth = var1;
         this.invalidateSelf();
      }

   }

   public void setColor(int var1) {
      if(this.mColor != var1) {
         this.mColor = var1;
         this.invalidateSelf();
      }

   }

   public void setColorFilter(ColorFilter var1) {
      this.mPaint.setColorFilter(var1);
   }

   public void setHideWhenZero(boolean var1) {
      this.mHideWhenZero = var1;
   }

   public void setIsVertical(boolean var1) {
      if(this.mIsVertical != var1) {
         this.mIsVertical = var1;
         this.invalidateSelf();
      }

   }

   public void setPadding(int var1) {
      if(this.mPadding != var1) {
         this.mPadding = var1;
         this.invalidateSelf();
      }

   }

   public void setRadius(int var1) {
      if(this.mRadius != var1) {
         this.mRadius = var1;
         this.invalidateSelf();
      }

   }
}
