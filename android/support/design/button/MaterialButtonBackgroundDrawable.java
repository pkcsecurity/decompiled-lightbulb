package android.support.design.button;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@TargetApi(21)
class MaterialButtonBackgroundDrawable extends RippleDrawable {

   MaterialButtonBackgroundDrawable(@NonNull ColorStateList var1, @Nullable InsetDrawable var2, @Nullable Drawable var3) {
      super(var1, var2, var3);
   }

   public void setColorFilter(ColorFilter var1) {
      if(this.getDrawable(0) != null) {
         ((GradientDrawable)((LayerDrawable)((InsetDrawable)this.getDrawable(0)).getDrawable()).getDrawable(0)).setColorFilter(var1);
      }

   }
}
