package com.google.android.gms.common.util;

import android.text.TextUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@VisibleForTesting
public final class zzd {

   private static final Pattern zzhg = Pattern.compile("\\\\u[0-9a-fA-F]{4}");


   public static String unescape(String var0) {
      if(!TextUtils.isEmpty(var0)) {
         Matcher var3 = zzhg.matcher(var0);

         StringBuffer var1;
         StringBuffer var2;
         for(var1 = null; var3.find(); var1 = var2) {
            var2 = var1;
            if(var1 == null) {
               var2 = new StringBuffer();
            }

            var3.appendReplacement(var2, new String(Character.toChars(Integer.parseInt(var3.group().substring(2), 16))));
         }

         if(var1 == null) {
            return var0;
         } else {
            var3.appendTail(var1);
            return var1.toString();
         }
      } else {
         return var0;
      }
   }
}
