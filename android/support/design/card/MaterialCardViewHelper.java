package android.support.design.card;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.RestrictTo;
import android.support.design.R;
import android.support.design.card.MaterialCardView;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class MaterialCardViewHelper {

   private static final int DEFAULT_STROKE_VALUE = -1;
   private final MaterialCardView materialCardView;
   private int strokeColor;
   private int strokeWidth;


   public MaterialCardViewHelper(MaterialCardView var1) {
      this.materialCardView = var1;
   }

   private void adjustContentPadding() {
      int var1 = this.materialCardView.getContentPaddingLeft();
      int var2 = this.strokeWidth;
      int var3 = this.materialCardView.getContentPaddingTop();
      int var4 = this.strokeWidth;
      int var5 = this.materialCardView.getContentPaddingRight();
      int var6 = this.strokeWidth;
      int var7 = this.materialCardView.getContentPaddingBottom();
      int var8 = this.strokeWidth;
      this.materialCardView.setContentPadding(var1 + var2, var3 + var4, var5 + var6, var7 + var8);
   }

   private Drawable createForegroundDrawable() {
      GradientDrawable var1 = new GradientDrawable();
      var1.setCornerRadius(this.materialCardView.getRadius());
      if(this.strokeColor != -1) {
         var1.setStroke(this.strokeWidth, this.strokeColor);
      }

      return var1;
   }

   @ColorInt
   int getStrokeColor() {
      return this.strokeColor;
   }

   @Dimension
   int getStrokeWidth() {
      return this.strokeWidth;
   }

   public void loadFromAttributes(TypedArray var1) {
      this.strokeColor = var1.getColor(R.styleable.MaterialCardView_strokeColor, -1);
      this.strokeWidth = var1.getDimensionPixelSize(R.styleable.MaterialCardView_strokeWidth, 0);
      this.updateForeground();
      this.adjustContentPadding();
   }

   void setStrokeColor(@ColorInt int var1) {
      this.strokeColor = var1;
      this.updateForeground();
   }

   void setStrokeWidth(@Dimension int var1) {
      this.strokeWidth = var1;
      this.updateForeground();
      this.adjustContentPadding();
   }

   void updateForeground() {
      this.materialCardView.setForeground(this.createForegroundDrawable());
   }
}
