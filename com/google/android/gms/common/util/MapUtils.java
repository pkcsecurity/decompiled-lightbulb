package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.HashMap;
import java.util.Iterator;

@KeepForSdk
public class MapUtils {

   @KeepForSdk
   public static void writeStringMapToJson(StringBuilder var0, HashMap<String, String> var1) {
      var0.append("{");
      Iterator var3 = var1.keySet().iterator();
      boolean var2 = true;

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         if(!var2) {
            var0.append(",");
         } else {
            var2 = false;
         }

         String var5 = (String)var1.get(var4);
         var0.append("\"");
         var0.append(var4);
         var0.append("\":");
         if(var5 == null) {
            var0.append("null");
         } else {
            var0.append("\"");
            var0.append(var5);
            var0.append("\"");
         }
      }

      var0.append("}");
   }
}
