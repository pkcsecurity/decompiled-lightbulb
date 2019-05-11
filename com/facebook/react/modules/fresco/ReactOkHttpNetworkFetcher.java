package com.facebook.react.modules.fresco;

import android.net.Uri;
import android.os.SystemClock;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.modules.fresco.ReactNetworkImageRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;

class ReactOkHttpNetworkFetcher extends OkHttpNetworkFetcher {

   private static final String TAG = "ReactOkHttpNetworkFetcher";
   private final Executor mCancellationExecutor;
   private final OkHttpClient mOkHttpClient;


   public ReactOkHttpNetworkFetcher(OkHttpClient var1) {
      super(var1);
      this.mOkHttpClient = var1;
      this.mCancellationExecutor = var1.dispatcher().executorService();
   }

   private Map<String, String> getHeaders(ReadableMap var1) {
      if(var1 == null) {
         return null;
      } else {
         ReadableMapKeySetIterator var2 = var1.keySetIterator();
         HashMap var3 = new HashMap();

         while(var2.hasNextKey()) {
            String var4 = var2.nextKey();
            var3.put(var4, var1.getString(var4));
         }

         return var3;
      }
   }

   public void fetch(OkHttpNetworkFetcher.OkHttpNetworkFetchState var1, NetworkFetcher.Callback var2) {
      var1.submitTime = SystemClock.elapsedRealtime();
      Uri var5 = var1.getUri();
      Map var3;
      if(var1.getContext().getImageRequest() instanceof ReactNetworkImageRequest) {
         var3 = this.getHeaders(((ReactNetworkImageRequest)var1.getContext().getImageRequest()).getHeaders());
      } else {
         var3 = null;
      }

      Map var4 = var3;
      if(var3 == null) {
         var4 = Collections.emptyMap();
      }

      this.fetchWithRequest(var1, var2, (new Builder()).cacheControl((new okhttp3.CacheControl.Builder()).noStore().build()).url(var5.toString()).headers(Headers.of(var4)).get().build());
   }
}
