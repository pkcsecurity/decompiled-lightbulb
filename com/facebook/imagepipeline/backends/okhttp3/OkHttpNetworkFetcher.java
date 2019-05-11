package com.facebook.imagepipeline.backends.okhttp3;

import android.net.Uri;
import android.os.Looper;
import android.os.SystemClock;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.BaseNetworkFetcher;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.FetchState;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.producers.ProducerContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Call.Factory;
import okhttp3.Request.Builder;

public class OkHttpNetworkFetcher extends BaseNetworkFetcher<OkHttpNetworkFetcher.OkHttpNetworkFetchState> {

   private static final String FETCH_TIME = "fetch_time";
   private static final String IMAGE_SIZE = "image_size";
   private static final String QUEUE_TIME = "queue_time";
   private static final String TAG = "OkHttpNetworkFetchProducer";
   private static final String TOTAL_TIME = "total_time";
   private final Factory mCallFactory;
   private Executor mCancellationExecutor;


   public OkHttpNetworkFetcher(Factory var1, Executor var2) {
      this.mCallFactory = var1;
      this.mCancellationExecutor = var2;
   }

   public OkHttpNetworkFetcher(OkHttpClient var1) {
      this(var1, var1.dispatcher().executorService());
   }

   private void handleException(Call var1, Exception var2, NetworkFetcher.Callback var3) {
      if(var1.isCanceled()) {
         var3.onCancellation();
      } else {
         var3.onFailure(var2);
      }
   }

   public OkHttpNetworkFetcher.OkHttpNetworkFetchState createFetchState(Consumer<EncodedImage> var1, ProducerContext var2) {
      return new OkHttpNetworkFetcher.OkHttpNetworkFetchState(var1, var2);
   }

   public void fetch(OkHttpNetworkFetcher.OkHttpNetworkFetchState var1, NetworkFetcher.Callback var2) {
      var1.submitTime = SystemClock.elapsedRealtime();
      Uri var3 = var1.getUri();

      try {
         this.fetchWithRequest(var1, var2, (new Builder()).cacheControl((new okhttp3.CacheControl.Builder()).noStore().build()).url(var3.toString()).get().build());
      } catch (Exception var4) {
         var2.onFailure(var4);
      }
   }

   protected void fetchWithRequest(final OkHttpNetworkFetcher.OkHttpNetworkFetchState var1, final NetworkFetcher.Callback var2, Request var3) {
      final Call var4 = this.mCallFactory.newCall(var3);
      var1.getContext().addCallbacks(new BaseProducerContextCallbacks() {
         public void onCancellationRequested() {
            if(Looper.myLooper() != Looper.getMainLooper()) {
               var4.cancel();
            } else {
               OkHttpNetworkFetcher.this.mCancellationExecutor.execute(new Runnable() {
                  public void run() {
                     var4.cancel();
                  }
               });
            }
         }
      });
      var4.enqueue(new Callback() {
         public void onFailure(Call var1x, IOException var2x) {
            OkHttpNetworkFetcher.this.handleException(var1x, var2x, var2);
         }
         public void onResponse(Call param1, Response param2) throws IOException {
            // $FF: Couldn't be decompiled
         }
      });
   }

   public Map<String, String> getExtraMap(OkHttpNetworkFetcher.OkHttpNetworkFetchState var1, int var2) {
      HashMap var3 = new HashMap(4);
      var3.put("queue_time", Long.toString(var1.responseTime - var1.submitTime));
      var3.put("fetch_time", Long.toString(var1.fetchCompleteTime - var1.responseTime));
      var3.put("total_time", Long.toString(var1.fetchCompleteTime - var1.submitTime));
      var3.put("image_size", Integer.toString(var2));
      return var3;
   }

   public void onFetchCompletion(OkHttpNetworkFetcher.OkHttpNetworkFetchState var1, int var2) {
      var1.fetchCompleteTime = SystemClock.elapsedRealtime();
   }

   public static class OkHttpNetworkFetchState extends FetchState {

      public long fetchCompleteTime;
      public long responseTime;
      public long submitTime;


      public OkHttpNetworkFetchState(Consumer<EncodedImage> var1, ProducerContext var2) {
         super(var1, var2);
      }
   }
}
