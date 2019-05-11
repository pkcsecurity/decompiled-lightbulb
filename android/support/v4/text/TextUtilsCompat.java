package android.support.v4.text;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.text.ICUCompat;
import android.text.TextUtils;
import java.util.Locale;

public final class TextUtilsCompat {

   private static final String ARAB_SCRIPT_SUBTAG = "Arab";
   private static final String HEBR_SCRIPT_SUBTAG = "Hebr";
   private static final Locale ROOT = new Locale("", "");


   private static int getLayoutDirectionFromFirstChar(@NonNull Locale var0) {
      switch(Character.getDirectionality(var0.getDisplayName(var0).charAt(0))) {
      case 1:
      case 2:
         return 1;
      default:
         return 0;
      }
   }

   public static int getLayoutDirectionFromLocale(@Nullable Locale var0) {
      if(VERSION.SDK_INT >= 17) {
         return TextUtils.getLayoutDirectionFromLocale(var0);
      } else {
         if(var0 != null && !var0.equals(ROOT)) {
            String var1 = ICUCompat.maximizeAndGetScript(var0);
            if(var1 == null) {
               return getLayoutDirectionFromFirstChar(var0);
            }

            if(var1.equalsIgnoreCase("Arab") || var1.equalsIgnoreCase("Hebr")) {
               return 1;
            }
         }

         return 0;
      }
   }

   @NonNull
   public static String htmlEncode(@NonNull String var0) {
      if(VERSION.SDK_INT >= 17) {
         return TextUtils.htmlEncode(var0);
      } else {
         StringBuilder var3 = new StringBuilder();

         for(int var2 = 0; var2 < var0.length(); ++var2) {
            char var1 = var0.charAt(var2);
            if(var1 != 34) {
               if(var1 != 60) {
                  if(var1 != 62) {
                     switch(var1) {
                     case 38:
                        var3.append("&amp;");
                        break;
                     case 39:
                        var3.append("&#39;");
                        break;
                     default:
                        var3.append(var1);
                     }
                  } else {
                     var3.append("&gt;");
                  }
               } else {
                  var3.append("&lt;");
               }
            } else {
               var3.append("&quot;");
            }
         }

         return var3.toString();
      }
   }
}
