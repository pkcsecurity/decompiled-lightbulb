package android.support.design.internal;

import android.graphics.PorterDuff.Mode;
import android.support.annotation.RestrictTo;
import android.support.v4.view.ViewCompat;
import android.view.View;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ViewUtils {

   public static boolean isLayoutRtl(View var0) {
      return ViewCompat.getLayoutDirection(var0) == 1;
   }

   public static Mode parseTintMode(int var0, Mode var1) {
      if(var0 != 3) {
         if(var0 != 5) {
            if(var0 != 9) {
               switch(var0) {
               case 14:
                  return Mode.MULTIPLY;
               case 15:
                  return Mode.SCREEN;
               case 16:
                  return Mode.ADD;
               default:
                  return var1;
               }
            } else {
               return Mode.SRC_ATOP;
            }
         } else {
            return Mode.SRC_IN;
         }
      } else {
         return Mode.SRC_OVER;
      }
   }
}
