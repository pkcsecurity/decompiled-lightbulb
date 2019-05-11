package com.facebook.litho.drawable;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import com.facebook.litho.drawable.ComparableDrawable;
import com.facebook.litho.drawable.DefaultComparableDrawable;

public class ComparableColorDrawable extends DefaultComparableDrawable {

   @ColorInt
   private final int mColor;


   private ComparableColorDrawable(@ColorInt int var1) {
      super(new ColorDrawable(var1));
      this.mColor = var1;
   }

   public static ComparableColorDrawable create(@ColorInt int var0) {
      return new ComparableColorDrawable(var0);
   }

   public boolean isEquivalentTo(ComparableDrawable var1) {
      return this == var1?true:(!(var1 instanceof ComparableColorDrawable)?false:this.mColor == ((ComparableColorDrawable)var1).mColor);
   }
}
