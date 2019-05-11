package android.support.design.circularreveal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.circularreveal.CircularRevealHelper;
import android.support.design.circularreveal.CircularRevealWidget;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class CircularRevealFrameLayout extends FrameLayout implements CircularRevealWidget {

   private final CircularRevealHelper helper;


   public CircularRevealFrameLayout(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public CircularRevealFrameLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.helper = new CircularRevealHelper(this);
   }

   public void actualDraw(Canvas var1) {
      super.draw(var1);
   }

   public boolean actualIsOpaque() {
      return super.isOpaque();
   }

   public void buildCircularRevealCache() {
      this.helper.buildCircularRevealCache();
   }

   public void destroyCircularRevealCache() {
      this.helper.destroyCircularRevealCache();
   }

   @SuppressLint({"MissingSuperCall"})
   public void draw(Canvas var1) {
      if(this.helper != null) {
         this.helper.draw(var1);
      } else {
         super.draw(var1);
      }
   }

   @Nullable
   public Drawable getCircularRevealOverlayDrawable() {
      return this.helper.getCircularRevealOverlayDrawable();
   }

   public int getCircularRevealScrimColor() {
      return this.helper.getCircularRevealScrimColor();
   }

   @Nullable
   public CircularRevealWidget.RevealInfo getRevealInfo() {
      return this.helper.getRevealInfo();
   }

   public boolean isOpaque() {
      return this.helper != null?this.helper.isOpaque():super.isOpaque();
   }

   public void setCircularRevealOverlayDrawable(@Nullable Drawable var1) {
      this.helper.setCircularRevealOverlayDrawable(var1);
   }

   public void setCircularRevealScrimColor(@ColorInt int var1) {
      this.helper.setCircularRevealScrimColor(var1);
   }

   public void setRevealInfo(@Nullable CircularRevealWidget.RevealInfo var1) {
      this.helper.setRevealInfo(var1);
   }
}
