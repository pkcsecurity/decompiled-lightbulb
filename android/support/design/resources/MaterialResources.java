package android.support.design.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleableRes;
import android.support.design.resources.TextAppearance;
import android.support.v7.content.res.AppCompatResources;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class MaterialResources {

   @Nullable
   public static ColorStateList getColorStateList(Context var0, TypedArray var1, @StyleableRes int var2) {
      if(var1.hasValue(var2)) {
         int var3 = var1.getResourceId(var2, 0);
         if(var3 != 0) {
            ColorStateList var4 = AppCompatResources.getColorStateList(var0, var3);
            if(var4 != null) {
               return var4;
            }
         }
      }

      return var1.getColorStateList(var2);
   }

   @Nullable
   public static Drawable getDrawable(Context var0, TypedArray var1, @StyleableRes int var2) {
      if(var1.hasValue(var2)) {
         int var3 = var1.getResourceId(var2, 0);
         if(var3 != 0) {
            Drawable var4 = AppCompatResources.getDrawable(var0, var3);
            if(var4 != null) {
               return var4;
            }
         }
      }

      return var1.getDrawable(var2);
   }

   @StyleableRes
   static int getIndexWithValue(TypedArray var0, @StyleableRes int var1, @StyleableRes int var2) {
      return var0.hasValue(var1)?var1:var2;
   }

   @Nullable
   public static TextAppearance getTextAppearance(Context var0, TypedArray var1, @StyleableRes int var2) {
      if(var1.hasValue(var2)) {
         var2 = var1.getResourceId(var2, 0);
         if(var2 != 0) {
            return new TextAppearance(var0, var2);
         }
      }

      return null;
   }
}
