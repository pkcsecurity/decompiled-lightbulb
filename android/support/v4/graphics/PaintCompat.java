package android.support.v4.graphics;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

public final class PaintCompat {

   private static final String EM_STRING = "m";
   private static final String TOFU_STRING = "󟿽";
   private static final ThreadLocal<Pair<Rect, Rect>> sRectThreadLocal = new ThreadLocal();


   public static boolean hasGlyph(@NonNull Paint var0, @NonNull String var1) {
      if(VERSION.SDK_INT >= 23) {
         return var0.hasGlyph(var1);
      } else {
         int var8 = var1.length();
         if(var8 == 1 && Character.isWhitespace(var1.charAt(0))) {
            return true;
         } else {
            float var3 = var0.measureText("󟿽");
            float var5 = var0.measureText("m");
            float var4 = var0.measureText(var1);
            float var2 = 0.0F;
            if(var4 == 0.0F) {
               return false;
            } else {
               if(var1.codePointCount(0, var1.length()) > 1) {
                  if(var4 > var5 * 2.0F) {
                     return false;
                  }

                  int var7;
                  for(int var6 = 0; var6 < var8; var6 = var7) {
                     var7 = Character.charCount(var1.codePointAt(var6)) + var6;
                     var2 += var0.measureText(var1, var6, var7);
                  }

                  if(var4 >= var2) {
                     return false;
                  }
               }

               if(var4 != var3) {
                  return true;
               } else {
                  Pair var9 = obtainEmptyRects();
                  var0.getTextBounds("󟿽", 0, "󟿽".length(), (Rect)var9.first);
                  var0.getTextBounds(var1, 0, var8, (Rect)var9.second);
                  return ((Rect)var9.first).equals(var9.second) ^ true;
               }
            }
         }
      }
   }

   private static Pair<Rect, Rect> obtainEmptyRects() {
      Pair var0 = (Pair)sRectThreadLocal.get();
      if(var0 == null) {
         var0 = new Pair(new Rect(), new Rect());
         sRectThreadLocal.set(var0);
         return var0;
      } else {
         ((Rect)var0.first).setEmpty();
         ((Rect)var0.second).setEmpty();
         return var0;
      }
   }
}
