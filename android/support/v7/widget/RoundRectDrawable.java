package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RoundRectDrawableWithShadow;

@RequiresApi(21)
class RoundRectDrawable extends Drawable {

   private ColorStateList mBackground;
   private final RectF mBoundsF;
   private final Rect mBoundsI;
   private boolean mInsetForPadding = false;
   private boolean mInsetForRadius = true;
   private float mPadding;
   private final Paint mPaint;
   private float mRadius;
   private ColorStateList mTint;
   private PorterDuffColorFilter mTintFilter;
   private Mode mTintMode;


   RoundRectDrawable(ColorStateList var1, float var2) {
      this.mTintMode = Mode.SRC_IN;
      this.mRadius = var2;
      this.mPaint = new Paint(5);
      this.setBackground(var1);
      this.mBoundsF = new RectF();
      this.mBoundsI = new Rect();
   }

   private PorterDuffColorFilter createTintFilter(ColorStateList var1, Mode var2) {
      return var1 != null && var2 != null?new PorterDuffColorFilter(var1.getColorForState(this.getState(), 0), var2):null;
   }

   private void setBackground(ColorStateList var1) {
      ColorStateList var2 = var1;
      if(var1 == null) {
         var2 = ColorStateList.valueOf(0);
      }

      this.mBackground = var2;
      this.mPaint.setColor(this.mBackground.getColorForState(this.getState(), this.mBackground.getDefaultColor()));
   }

   private void updateBounds(Rect var1) {
      Rect var4 = var1;
      if(var1 == null) {
         var4 = this.getBounds();
      }

      this.mBoundsF.set((float)var4.left, (float)var4.top, (float)var4.right, (float)var4.bottom);
      this.mBoundsI.set(var4);
      if(this.mInsetForPadding) {
         float var2 = RoundRectDrawableWithShadow.calculateVerticalPadding(this.mPadding, this.mRadius, this.mInsetForRadius);
         float var3 = RoundRectDrawableWithShadow.calculateHorizontalPadding(this.mPadding, this.mRadius, this.mInsetForRadius);
         this.mBoundsI.inset((int)Math.ceil((double)var3), (int)Math.ceil((double)var2));
         this.mBoundsF.set(this.mBoundsI);
      }

   }

   public void draw(Canvas var1) {
      Paint var3 = this.mPaint;
      boolean var2;
      if(this.mTintFilter != null && var3.getColorFilter() == null) {
         var3.setColorFilter(this.mTintFilter);
         var2 = true;
      } else {
         var2 = false;
      }

      var1.drawRoundRect(this.mBoundsF, this.mRadius, this.mRadius, var3);
      if(var2) {
         var3.setColorFilter((ColorFilter)null);
      }

   }

   public ColorStateList getColor() {
      return this.mBackground;
   }

   public int getOpacity() {
      return -3;
   }

   public void getOutline(Outline var1) {
      var1.setRoundRect(this.mBoundsI, this.mRadius);
   }

   float getPadding() {
      return this.mPadding;
   }

   public float getRadius() {
      return this.mRadius;
   }

   public boolean isStateful() {
      return this.mTint != null && this.mTint.isStateful() || this.mBackground != null && this.mBackground.isStateful() || super.isStateful();
   }

   protected void onBoundsChange(Rect var1) {
      super.onBoundsChange(var1);
      this.updateBounds(var1);
   }

   protected boolean onStateChange(int[] var1) {
      int var2 = this.mBackground.getColorForState(var1, this.mBackground.getDefaultColor());
      boolean var3;
      if(var2 != this.mPaint.getColor()) {
         var3 = true;
      } else {
         var3 = false;
      }

      if(var3) {
         this.mPaint.setColor(var2);
      }

      if(this.mTint != null && this.mTintMode != null) {
         this.mTintFilter = this.createTintFilter(this.mTint, this.mTintMode);
         return true;
      } else {
         return var3;
      }
   }

   public void setAlpha(int var1) {
      this.mPaint.setAlpha(var1);
   }

   public void setColor(@Nullable ColorStateList var1) {
      this.setBackground(var1);
      this.invalidateSelf();
   }

   public void setColorFilter(ColorFilter var1) {
      this.mPaint.setColorFilter(var1);
   }

   void setPadding(float var1, boolean var2, boolean var3) {
      if(var1 != this.mPadding || this.mInsetForPadding != var2 || this.mInsetForRadius != var3) {
         this.mPadding = var1;
         this.mInsetForPadding = var2;
         this.mInsetForRadius = var3;
         this.updateBounds((Rect)null);
         this.invalidateSelf();
      }
   }

   void setRadius(float var1) {
      if(var1 != this.mRadius) {
         this.mRadius = var1;
         this.updateBounds((Rect)null);
         this.invalidateSelf();
      }
   }

   public void setTintList(ColorStateList var1) {
      this.mTint = var1;
      this.mTintFilter = this.createTintFilter(this.mTint, this.mTintMode);
      this.invalidateSelf();
   }

   public void setTintMode(Mode var1) {
      this.mTintMode = var1;
      this.mTintFilter = this.createTintFilter(this.mTint, this.mTintMode);
      this.invalidateSelf();
   }
}
