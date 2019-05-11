package com.facebook.litho.drawable;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import com.facebook.litho.drawable.ComparableDrawable;
import com.facebook.litho.drawable.DefaultComparableDrawable;

public class ComparableResDrawable extends DefaultComparableDrawable {

   private final Configuration mConfig;
   @DrawableRes
   private final int mResId;


   private ComparableResDrawable(@DrawableRes int var1, Configuration var2, Drawable var3) {
      super(var3);
      this.mResId = var1;
      this.mConfig = var2;
   }

   public static ComparableResDrawable create(Context var0, @DrawableRes int var1) {
      return new ComparableResDrawable(var1, new Configuration(var0.getResources().getConfiguration()), ContextCompat.getDrawable(var0, var1));
   }

   public boolean isEquivalentTo(ComparableDrawable var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof ComparableResDrawable)) {
         return false;
      } else {
         int var2 = this.mResId;
         ComparableResDrawable var3 = (ComparableResDrawable)var1;
         return var2 == var3.mResId && this.mConfig.equals(var3.mConfig);
      }
   }
}
