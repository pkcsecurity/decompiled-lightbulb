package com.facebook.imagepipeline.producers;

import android.net.Uri;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.BaseNetworkFetcher;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.FetchState;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.producers.ProducerContext;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpUrlConnectionNetworkFetcher extends BaseNetworkFetcher<FetchState> {

   public static final int HTTP_PERMANENT_REDIRECT = 308;
   public static final int HTTP_TEMPORARY_REDIRECT = 307;
   private static final int MAX_REDIRECTS = 5;
   private static final int NUM_NETWORK_THREADS = 3;
   private final ExecutorService mExecutorService;


   public HttpUrlConnectionNetworkFetcher() {
      this(Executors.newFixedThreadPool(3));
   }

   @VisibleForTesting
   HttpUrlConnectionNetworkFetcher(ExecutorService var1) {
      this.mExecutorService = var1;
   }

   private HttpURLConnection downloadFrom(Uri var1, int var2) throws IOException {
      HttpURLConnection var4 = openConnectionTo(var1);
      int var3 = var4.getResponseCode();
      if(isHttpSuccess(var3)) {
         return var4;
      } else if(isHttpRedirect(var3)) {
         String var5 = var4.getHeaderField("Location");
         var4.disconnect();
         Uri var7;
         if(var5 == null) {
            var7 = null;
         } else {
            var7 = Uri.parse(var5);
         }

         var5 = var1.getScheme();
         if(var2 > 0 && var7 != null && !var7.getScheme().equals(var5)) {
            return this.downloadFrom(var7, var2 - 1);
         } else {
            String var6;
            if(var2 == 0) {
               var6 = error("URL %s follows too many redirects", new Object[]{var1.toString()});
            } else {
               var6 = error("URL %s returned %d without a valid redirect", new Object[]{var1.toString(), Integer.valueOf(var3)});
            }

            throw new IOException(var6);
         }
      } else {
         var4.disconnect();
         throw new IOException(String.format("Image URL %s returned HTTP code %d", new Object[]{var1.toString(), Integer.valueOf(var3)}));
      }
   }

   private static String error(String var0, Object ... var1) {
      return String.format(Locale.getDefault(), var0, var1);
   }

   private static boolean isHttpRedirect(int var0) {
      switch(var0) {
      case 300:
      case 301:
      case 302:
      case 303:
      case 307:
      case 308:
         return true;
      case 304:
      case 305:
      case 306:
      default:
         return false;
      }
   }

   private static boolean isHttpSuccess(int var0) {
      return var0 >= 200 && var0 < 300;
   }

   @VisibleForTesting
   static HttpURLConnection openConnectionTo(Uri var0) throws IOException {
      return (HttpURLConnection)(new URL(var0.toString())).openConnection();
   }

   public FetchState createFetchState(Consumer<EncodedImage> var1, ProducerContext var2) {
      return new FetchState(var1, var2);
   }

   public void fetch(final FetchState var1, final NetworkFetcher.Callback var2) {
      final Future var3 = this.mExecutorService.submit(new Runnable() {
         public void run() {
            HttpUrlConnectionNetworkFetcher.this.fetchSync(var1, var2);
         }
      });
      var1.getContext().addCallbacks(new BaseProducerContextCallbacks() {
         public void onCancellationRequested() {
            if(var3.cancel(false)) {
               var2.onCancellation();
            }

         }
      });
   }

   @VisibleForTesting
   void fetchSync(FetchState param1, NetworkFetcher.Callback param2) {
      // $FF: Couldn't be decompiled
   }
}
