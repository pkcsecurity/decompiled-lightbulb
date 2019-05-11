package android.support.design.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.design.R;
import android.support.design.card.MaterialCardViewHelper;
import android.support.design.internal.ThemeEnforcement;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

public class MaterialCardView extends CardView {

   private final MaterialCardViewHelper cardViewHelper;


   public MaterialCardView(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public MaterialCardView(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.materialCardViewStyle);
   }

   public MaterialCardView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      TypedArray var4 = ThemeEnforcement.obtainStyledAttributes(var1, var2, R.styleable.MaterialCardView, var3, R.style.Widget_MaterialComponents_CardView, new int[0]);
      this.cardViewHelper = new MaterialCardViewHelper(this);
      this.cardViewHelper.loadFromAttributes(var4);
      var4.recycle();
   }

   @ColorInt
   public int getStrokeColor() {
      return this.cardViewHelper.getStrokeColor();
   }

   @Dimension
   public int getStrokeWidth() {
      return this.cardViewHelper.getStrokeWidth();
   }

   public void setRadius(float var1) {
      super.setRadius(var1);
      this.cardViewHelper.updateForeground();
   }

   public void setStrokeColor(@ColorInt int var1) {
      this.cardViewHelper.setStrokeColor(var1);
   }

   public void setStrokeWidth(@Dimension int var1) {
      this.cardViewHelper.setStrokeWidth(var1);
   }
}
