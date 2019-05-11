package android.support.design.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class DrawableUtils {

   @Nullable
   public static PorterDuffColorFilter updateTintFilter(Drawable var0, @Nullable ColorStateList var1, @Nullable Mode var2) {
      return var1 != null && var2 != null?new PorterDuffColorFilter(var1.getColorForState(var0.getState(), 0), var2):null;
   }
}
