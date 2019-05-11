package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.TintableImageSourceView;
import android.widget.ImageView;

public class ImageViewCompat {

   @Nullable
   public static ColorStateList getImageTintList(@NonNull ImageView var0) {
      return VERSION.SDK_INT >= 21?var0.getImageTintList():(var0 instanceof TintableImageSourceView?((TintableImageSourceView)var0).getSupportImageTintList():null);
   }

   @Nullable
   public static Mode getImageTintMode(@NonNull ImageView var0) {
      return VERSION.SDK_INT >= 21?var0.getImageTintMode():(var0 instanceof TintableImageSourceView?((TintableImageSourceView)var0).getSupportImageTintMode():null);
   }

   public static void setImageTintList(@NonNull ImageView var0, @Nullable ColorStateList var1) {
      if(VERSION.SDK_INT >= 21) {
         var0.setImageTintList(var1);
         if(VERSION.SDK_INT == 21) {
            Drawable var3 = var0.getDrawable();
            boolean var2;
            if(var0.getImageTintList() != null && var0.getImageTintMode() != null) {
               var2 = true;
            } else {
               var2 = false;
            }

            if(var3 != null && var2) {
               if(var3.isStateful()) {
                  var3.setState(var0.getDrawableState());
               }

               var0.setImageDrawable(var3);
               return;
            }
         }
      } else if(var0 instanceof TintableImageSourceView) {
         ((TintableImageSourceView)var0).setSupportImageTintList(var1);
      }

   }

   public static void setImageTintMode(@NonNull ImageView var0, @Nullable Mode var1) {
      if(VERSION.SDK_INT >= 21) {
         var0.setImageTintMode(var1);
         if(VERSION.SDK_INT == 21) {
            Drawable var3 = var0.getDrawable();
            boolean var2;
            if(var0.getImageTintList() != null && var0.getImageTintMode() != null) {
               var2 = true;
            } else {
               var2 = false;
            }

            if(var3 != null && var2) {
               if(var3.isStateful()) {
                  var3.setState(var0.getDrawableState());
               }

               var0.setImageDrawable(var3);
               return;
            }
         }
      } else if(var0 instanceof TintableImageSourceView) {
         ((TintableImageSourceView)var0).setSupportImageTintMode(var1);
      }

   }
}
