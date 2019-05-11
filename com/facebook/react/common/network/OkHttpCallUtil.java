package com.facebook.react.common.network;

import java.util.Iterator;
import okhttp3.Call;
import okhttp3.OkHttpClient;

public class OkHttpCallUtil {

   public static void cancelTag(OkHttpClient var0, Object var1) {
      Iterator var2 = var0.dispatcher().queuedCalls().iterator();

      Call var3;
      do {
         if(!var2.hasNext()) {
            Iterator var4 = var0.dispatcher().runningCalls().iterator();

            Call var5;
            do {
               if(!var4.hasNext()) {
                  return;
               }

               var5 = (Call)var4.next();
            } while(!var1.equals(var5.request().tag()));

            var5.cancel();
            return;
         }

         var3 = (Call)var2.next();
      } while(!var1.equals(var3.request().tag()));

      var3.cancel();
   }
}
