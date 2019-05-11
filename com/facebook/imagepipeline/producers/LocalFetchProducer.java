package com.facebook.imagepipeline.producers;

import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.producers.StatefulProducerRunnable;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

public abstract class LocalFetchProducer implements Producer<EncodedImage> {

   private final Executor mExecutor;
   private final PooledByteBufferFactory mPooledByteBufferFactory;


   protected LocalFetchProducer(Executor var1, PooledByteBufferFactory var2) {
      this.mExecutor = var1;
      this.mPooledByteBufferFactory = var2;
   }

   protected EncodedImage getByteBufferBackedEncodedImage(InputStream param1, int param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   protected abstract EncodedImage getEncodedImage(ImageRequest var1) throws IOException;

   protected EncodedImage getEncodedImage(InputStream var1, int var2) throws IOException {
      return this.getByteBufferBackedEncodedImage(var1, var2);
   }

   protected abstract String getProducerName();

   public void produceResults(final Consumer<EncodedImage> var1, ProducerContext var2) {
      final ProducerListener var3 = var2.getListener();
      final String var4 = var2.getId();
      final ImageRequest var5 = var2.getImageRequest();
      final StatefulProducerRunnable var6 = new StatefulProducerRunnable(var1, var3, this.getProducerName(), var4) {
         protected void disposeResult(EncodedImage var1) {
            EncodedImage.closeSafely(var1);
         }
         protected EncodedImage getResult() throws Exception {
            EncodedImage var1 = LocalFetchProducer.this.getEncodedImage(var5);
            if(var1 == null) {
               var3.onUltimateProducerReached(var4, LocalFetchProducer.this.getProducerName(), false);
               return null;
            } else {
               var1.parseMetaData();
               var3.onUltimateProducerReached(var4, LocalFetchProducer.this.getProducerName(), true);
               return var1;
            }
         }
      };
      var2.addCallbacks(new BaseProducerContextCallbacks() {
         public void onCancellationRequested() {
            var6.cancel();
         }
      });
      this.mExecutor.execute(var6);
   }
}
