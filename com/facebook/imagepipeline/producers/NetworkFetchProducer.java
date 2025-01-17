package com.facebook.imagepipeline.producers;

import android.os.SystemClock;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteBufferOutputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.FetchState;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.annotation.Nullable;

public class NetworkFetchProducer implements Producer<EncodedImage> {

   public static final String INTERMEDIATE_RESULT_PRODUCER_EVENT = "intermediate_result";
   public static final String PRODUCER_NAME = "NetworkFetchProducer";
   private static final int READ_SIZE = 16384;
   @VisibleForTesting
   static final long TIME_BETWEEN_PARTIAL_RESULTS_MS = 100L;
   private final ByteArrayPool mByteArrayPool;
   private final NetworkFetcher mNetworkFetcher;
   private final PooledByteBufferFactory mPooledByteBufferFactory;


   public NetworkFetchProducer(PooledByteBufferFactory var1, ByteArrayPool var2, NetworkFetcher var3) {
      this.mPooledByteBufferFactory = var1;
      this.mByteArrayPool = var2;
      this.mNetworkFetcher = var3;
   }

   private static float calculateProgress(int var0, int var1) {
      return var1 > 0?(float)var0 / (float)var1:1.0F - (float)Math.exp((double)(-var0) / 50000.0D);
   }

   @Nullable
   private Map<String, String> getExtraMap(FetchState var1, int var2) {
      return !var1.getListener().requiresExtraMap(var1.getId())?null:this.mNetworkFetcher.getExtraMap(var1, var2);
   }

   private void handleFinalResult(PooledByteBufferOutputStream var1, FetchState var2) {
      Map var3 = this.getExtraMap(var2, var1.size());
      ProducerListener var4 = var2.getListener();
      var4.onProducerFinishWithSuccess(var2.getId(), "NetworkFetchProducer", var3);
      var4.onUltimateProducerReached(var2.getId(), "NetworkFetchProducer", true);
      this.notifyConsumer(var1, true, var2.getConsumer());
   }

   private void maybeHandleIntermediateResult(PooledByteBufferOutputStream var1, FetchState var2) {
      long var3 = SystemClock.uptimeMillis();
      if(this.shouldPropagateIntermediateResults(var2) && var3 - var2.getLastIntermediateResultTimeMs() >= 100L) {
         var2.setLastIntermediateResultTimeMs(var3);
         var2.getListener().onProducerEvent(var2.getId(), "NetworkFetchProducer", "intermediate_result");
         this.notifyConsumer(var1, false, var2.getConsumer());
      }

   }

   private void notifyConsumer(PooledByteBufferOutputStream var1, boolean var2, Consumer<EncodedImage> var3) {
      CloseableReference var5 = CloseableReference.of(var1.toByteBuffer());
      var1 = null;
      boolean var10 = false;

      EncodedImage var4;
      try {
         var10 = true;
         var4 = new EncodedImage(var5);
         var10 = false;
      } finally {
         if(var10) {
            EncodedImage.closeSafely(var1);
            CloseableReference.closeSafely(var5);
         }
      }

      try {
         var4.parseMetaData();
         var3.onNewResult(var4, var2);
      } finally {
         ;
      }

      EncodedImage.closeSafely(var4);
      CloseableReference.closeSafely(var5);
   }

   private void onCancellation(FetchState var1) {
      var1.getListener().onProducerFinishWithCancellation(var1.getId(), "NetworkFetchProducer", (Map)null);
      var1.getConsumer().onCancellation();
   }

   private void onFailure(FetchState var1, Throwable var2) {
      var1.getListener().onProducerFinishWithFailure(var1.getId(), "NetworkFetchProducer", var2, (Map)null);
      var1.getListener().onUltimateProducerReached(var1.getId(), "NetworkFetchProducer", false);
      var1.getConsumer().onFailure(var2);
   }

   private void onResponse(FetchState param1, InputStream param2, int param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private boolean shouldPropagateIntermediateResults(FetchState var1) {
      return !var1.getContext().isIntermediateResultExpected()?false:this.mNetworkFetcher.shouldPropagate(var1);
   }

   public void produceResults(Consumer<EncodedImage> var1, ProducerContext var2) {
      var2.getListener().onProducerStart(var2.getId(), "NetworkFetchProducer");
      final FetchState var3 = this.mNetworkFetcher.createFetchState(var1, var2);
      this.mNetworkFetcher.fetch(var3, new NetworkFetcher.Callback() {
         public void onCancellation() {
            NetworkFetchProducer.this.onCancellation(var3);
         }
         public void onFailure(Throwable var1) {
            NetworkFetchProducer.this.onFailure(var3, var1);
         }
         public void onResponse(InputStream var1, int var2) throws IOException {
            NetworkFetchProducer.this.onResponse(var3, var1, var2);
         }
      });
   }
}
