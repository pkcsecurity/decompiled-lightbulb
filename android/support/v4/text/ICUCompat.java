package android.support.v4.text;

import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public final class ICUCompat {

   private static final String TAG = "ICUCompat";
   private static Method sAddLikelySubtagsMethod;
   private static Method sGetScriptMethod;


   static {
      // $FF: Couldn't be decompiled
   }

   private static String addLikelySubtags(Locale var0) {
      String var4 = var0.toString();

      try {
         if(sAddLikelySubtagsMethod != null) {
            String var1 = (String)sAddLikelySubtagsMethod.invoke((Object)null, new Object[]{var4});
            return var1;
         }
      } catch (IllegalAccessException var2) {
         Log.w("ICUCompat", var2);
      } catch (InvocationTargetException var3) {
         Log.w("ICUCompat", var3);
         return var4;
      }

      return var4;
   }

   private static String getScript(String var0) {
      try {
         if(sGetScriptMethod != null) {
            var0 = (String)sGetScriptMethod.invoke((Object)null, new Object[]{var0});
            return var0;
         }
      } catch (IllegalAccessException var1) {
         Log.w("ICUCompat", var1);
      } catch (InvocationTargetException var2) {
         Log.w("ICUCompat", var2);
         return null;
      }

      return null;
   }

   @Nullable
   public static String maximizeAndGetScript(Locale var0) {
      if(VERSION.SDK_INT >= 21) {
         try {
            String var1 = ((Locale)sAddLikelySubtagsMethod.invoke((Object)null, new Object[]{var0})).getScript();
            return var1;
         } catch (InvocationTargetException var2) {
            Log.w("ICUCompat", var2);
         } catch (IllegalAccessException var3) {
            Log.w("ICUCompat", var3);
         }

         return var0.getScript();
      } else {
         String var4 = addLikelySubtags(var0);
         return var4 != null?getScript(var4):null;
      }
   }
}
