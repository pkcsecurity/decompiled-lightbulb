package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

@KeepForSdk
public class HttpUtils {

   private static final Pattern zzgy = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
   private static final Pattern zzgz = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
   private static final Pattern zzha = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");


   private static String decode(String var0, String var1) {
      if(var1 == null) {
         var1 = "ISO-8859-1";
      }

      try {
         var0 = URLDecoder.decode(var0, var1);
         return var0;
      } catch (UnsupportedEncodingException var2) {
         throw new IllegalArgumentException(var2);
      }
   }

   @KeepForSdk
   public static Map<String, String> parse(URI var0, String var1) {
      Map var2 = Collections.emptyMap();
      String var3 = var0.getRawQuery();
      Object var6 = var2;
      if(var3 != null) {
         var6 = var2;
         if(var3.length() > 0) {
            HashMap var8 = new HashMap();
            Scanner var9 = new Scanner(var3);
            var9.useDelimiter("&");

            while(true) {
               var6 = var8;
               if(!var9.hasNext()) {
                  break;
               }

               String[] var5 = var9.next().split("=");
               if(var5.length == 0 || var5.length > 2) {
                  throw new IllegalArgumentException("bad parameter");
               }

               String var4 = decode(var5[0], var1);
               String var7 = null;
               if(var5.length == 2) {
                  var7 = decode(var5[1], var1);
               }

               var8.put(var4, var7);
            }
         }
      }

      return (Map)var6;
   }
}
