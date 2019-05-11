package com.facebook.react.modules.network;

import android.os.Build.VERSION;
import com.facebook.common.logging.FLog;
import com.facebook.react.modules.network.ReactCookieJarContainer;
import com.facebook.react.modules.network.TLSSocketFactory;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.OkHttpClient.Builder;

public class OkHttpClientProvider {

   @Nullable
   private static OkHttpClient sClient;


   public static OkHttpClient createClient() {
      return enableTls12OnPreLollipop((new Builder()).connectTimeout(0L, TimeUnit.MILLISECONDS).readTimeout(0L, TimeUnit.MILLISECONDS).writeTimeout(0L, TimeUnit.MILLISECONDS).cookieJar(new ReactCookieJarContainer())).build();
   }

   public static Builder enableTls12OnPreLollipop(Builder var0) {
      if(VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 19) {
         try {
            var0.sslSocketFactory(new TLSSocketFactory());
            ConnectionSpec var1 = (new okhttp3.ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)).tlsVersions(new TlsVersion[]{TlsVersion.TLS_1_2}).build();
            ArrayList var2 = new ArrayList();
            var2.add(var1);
            var2.add(ConnectionSpec.COMPATIBLE_TLS);
            var2.add(ConnectionSpec.CLEARTEXT);
            var0.connectionSpecs(var2);
            return var0;
         } catch (Exception var3) {
            FLog.e("OkHttpClientProvider", "Error while enabling TLS 1.2", (Throwable)var3);
         }
      }

      return var0;
   }

   public static OkHttpClient getOkHttpClient() {
      if(sClient == null) {
         sClient = createClient();
      }

      return sClient;
   }

   public static void replaceOkHttpClient(OkHttpClient var0) {
      sClient = var0;
   }
}
