package com.facebook.litho;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.MeasureSpec;
import com.facebook.litho.MatrixDrawable;

public class BoundsHelper {

   public static void applyBoundsToView(View var0, int var1, int var2, int var3, int var4, boolean var5) {
      int var6 = var3 - var1;
      int var7 = var4 - var2;
      if(var5 || var0.getMeasuredHeight() != var7 || var0.getMeasuredWidth() != var6) {
         var0.measure(MeasureSpec.makeMeasureSpec(var6, 1073741824), MeasureSpec.makeMeasureSpec(var7, 1073741824));
      }

      if(var5 || var0.getLeft() != var1 || var0.getTop() != var2 || var0.getRight() != var3 || var0.getBottom() != var4) {
         var0.layout(var1, var2, var3, var4);
      }

   }

   public static void applySizeToDrawableForAnimation(Drawable var0, int var1, int var2) {
      Rect var3 = var0.getBounds();
      var0.setBounds(var3.left, var3.top, var3.left + var1, var3.top + var2);
      if(var0 instanceof MatrixDrawable) {
         ((MatrixDrawable)var0).bind(var1, var2);
      }

   }

   public static void applyXYToDrawableForAnimation(Drawable var0, int var1, int var2) {
      Rect var3 = var0.getBounds();
      var0.setBounds(var1, var2, var3.width() + var1, var3.height() + var2);
   }
}
