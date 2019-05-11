package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.TintableCompoundButton;
import android.util.Log;
import android.widget.CompoundButton;
import java.lang.reflect.Field;

public final class CompoundButtonCompat {

   private static final String TAG = "CompoundButtonCompat";
   private static Field sButtonDrawableField;
   private static boolean sButtonDrawableFieldFetched;


   @Nullable
   public static Drawable getButtonDrawable(@NonNull CompoundButton var0) {
      if(VERSION.SDK_INT >= 23) {
         return var0.getButtonDrawable();
      } else {
         if(!sButtonDrawableFieldFetched) {
            try {
               sButtonDrawableField = CompoundButton.class.getDeclaredField("mButtonDrawable");
               sButtonDrawableField.setAccessible(true);
            } catch (NoSuchFieldException var2) {
               Log.i("CompoundButtonCompat", "Failed to retrieve mButtonDrawable field", var2);
            }

            sButtonDrawableFieldFetched = true;
         }

         if(sButtonDrawableField != null) {
            try {
               Drawable var4 = (Drawable)sButtonDrawableField.get(var0);
               return var4;
            } catch (IllegalAccessException var3) {
               Log.i("CompoundButtonCompat", "Failed to get button drawable via reflection", var3);
               sButtonDrawableField = null;
            }
         }

         return null;
      }
   }

   @Nullable
   public static ColorStateList getButtonTintList(@NonNull CompoundButton var0) {
      return VERSION.SDK_INT >= 21?var0.getButtonTintList():(var0 instanceof TintableCompoundButton?((TintableCompoundButton)var0).getSupportButtonTintList():null);
   }

   @Nullable
   public static Mode getButtonTintMode(@NonNull CompoundButton var0) {
      return VERSION.SDK_INT >= 21?var0.getButtonTintMode():(var0 instanceof TintableCompoundButton?((TintableCompoundButton)var0).getSupportButtonTintMode():null);
   }

   public static void setButtonTintList(@NonNull CompoundButton var0, @Nullable ColorStateList var1) {
      if(VERSION.SDK_INT >= 21) {
         var0.setButtonTintList(var1);
      } else {
         if(var0 instanceof TintableCompoundButton) {
            ((TintableCompoundButton)var0).setSupportButtonTintList(var1);
         }

      }
   }

   public static void setButtonTintMode(@NonNull CompoundButton var0, @Nullable Mode var1) {
      if(VERSION.SDK_INT >= 21) {
         var0.setButtonTintMode(var1);
      } else {
         if(var0 instanceof TintableCompoundButton) {
            ((TintableCompoundButton)var0).setSupportButtonTintMode(var1);
         }

      }
   }
}
