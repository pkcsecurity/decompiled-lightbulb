package android.support.v4.graphics;

import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

public final class BitmapCompat {

   public static int getAllocationByteCount(@NonNull Bitmap var0) {
      return VERSION.SDK_INT >= 19?var0.getAllocationByteCount():var0.getByteCount();
   }

   public static boolean hasMipMap(@NonNull Bitmap var0) {
      return VERSION.SDK_INT >= 18?var0.hasMipMap():false;
   }

   public static void setHasMipMap(@NonNull Bitmap var0, boolean var1) {
      if(VERSION.SDK_INT >= 18) {
         var0.setHasMipMap(var1);
      }

   }
}
