package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import com.facebook.react.flat.AbstractDrawCommand;
import javax.annotation.Nullable;

abstract class AbstractDrawBorder extends AbstractDrawCommand {

   private static final int BORDER_PATH_DIRTY = 1;
   private static final Paint PAINT = new Paint(1);
   private static final RectF TMP_RECT = new RectF();
   private int mBorderColor = -16777216;
   private float mBorderRadius;
   private float mBorderWidth;
   @Nullable
   private Path mPathForBorderRadius;
   private int mSetPropertiesFlag;


   static {
      PAINT.setStyle(Style.STROKE);
   }

   protected final void drawBorders(Canvas var1) {
      if(this.mBorderWidth >= 0.5F) {
         if(this.mBorderColor != 0) {
            PAINT.setColor(this.mBorderColor);
            PAINT.setStrokeWidth(this.mBorderWidth);
            PAINT.setPathEffect(this.getPathEffectForBorderStyle());
            var1.drawPath(this.getPathForBorderRadius(), PAINT);
         }
      }
   }

   public final int getBorderColor() {
      return this.mBorderColor;
   }

   public final float getBorderRadius() {
      return this.mBorderRadius;
   }

   public final float getBorderWidth() {
      return this.mBorderWidth;
   }

   @Nullable
   protected PathEffect getPathEffectForBorderStyle() {
      return null;
   }

   protected final Path getPathForBorderRadius() {
      if(this.isFlagSet(1)) {
         if(this.mPathForBorderRadius == null) {
            this.mPathForBorderRadius = new Path();
         }

         this.updatePath(this.mPathForBorderRadius, this.mBorderWidth * 0.5F);
         this.resetFlag(1);
      }

      return this.mPathForBorderRadius;
   }

   protected final boolean isFlagSet(int var1) {
      return (this.mSetPropertiesFlag & var1) == var1;
   }

   protected void onBoundsChanged() {
      this.setFlag(1);
   }

   protected final void resetFlag(int var1) {
      this.mSetPropertiesFlag &= ~var1;
   }

   public final void setBorderColor(int var1) {
      this.mBorderColor = var1;
   }

   public void setBorderRadius(float var1) {
      this.mBorderRadius = var1;
      this.setFlag(1);
   }

   public final void setBorderWidth(float var1) {
      this.mBorderWidth = var1;
      this.setFlag(1);
   }

   protected final void setFlag(int var1) {
      this.mSetPropertiesFlag |= var1;
   }

   protected final void updatePath(Path var1, float var2) {
      var1.reset();
      TMP_RECT.set(this.getLeft() + var2, this.getTop() + var2, this.getRight() - var2, this.getBottom() - var2);
      var1.addRoundRect(TMP_RECT, this.mBorderRadius, this.mBorderRadius, Direction.CW);
   }
}
