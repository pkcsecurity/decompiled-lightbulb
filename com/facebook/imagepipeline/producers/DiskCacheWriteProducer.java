package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.cache.DiskCachePolicy;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.request.ImageRequest;

public class DiskCacheWriteProducer implements Producer<EncodedImage> {

   private final DiskCachePolicy mDiskCachePolicy;
   private final Producer<EncodedImage> mInputProducer;


   public DiskCacheWriteProducer(Producer<EncodedImage> var1, DiskCachePolicy var2) {
      this.mInputProducer = var1;
      this.mDiskCachePolicy = var2;
   }

   private void maybeStartInputProducer(Consumer<EncodedImage> var1, ProducerContext var2) {
      if(var2.getLowestPermittedRequestLevel().getValue() >= ImageRequest.RequestLevel.DISK_CACHE.getValue()) {
         var1.onNewResult((Object)null, true);
      } else {
         Object var3 = var1;
         if(var2.getImageRequest().isDiskCacheEnabled()) {
            var3 = new DiskCacheWriteProducer.DiskCacheWriteConsumer(var1, var2, this.mDiskCachePolicy, null);
         }

         this.mInputProducer.produceResults((Consumer)var3, var2);
      }
   }

   public void produceResults(Consumer<EncodedImage> var1, ProducerContext var2) {
      this.maybeStartInputProducer(var1, var2);
   }

   static class DiskCacheWriteConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {

      private final DiskCachePolicy mDiskCachePolicy;
      private final ProducerContext mProducerContext;


      private DiskCacheWriteConsumer(Consumer<EncodedImage> var1, ProducerContext var2, DiskCachePolicy var3) {
         super(var1);
         this.mProducerContext = var2;
         this.mDiskCachePolicy = var3;
      }

      // $FF: synthetic method
      DiskCacheWriteConsumer(Consumer var1, ProducerContext var2, DiskCachePolicy var3, Object var4) {
         this(var1, var2, var3);
      }

      public void onNewResultImpl(EncodedImage var1, boolean var2) {
         if(var1 != null && var2) {
            this.mDiskCachePolicy.writeToCache(var1, this.mProducerContext.getImageRequest(), this.mProducerContext.getCallerContext());
         }

         this.getConsumer().onNewResult(var1, var2);
      }
   }
}
