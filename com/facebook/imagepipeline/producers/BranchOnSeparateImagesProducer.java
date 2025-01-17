package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ThumbnailSizeChecker;
import com.facebook.imagepipeline.request.ImageRequest;

public class BranchOnSeparateImagesProducer implements Producer<EncodedImage> {

   private final Producer<EncodedImage> mInputProducer1;
   private final Producer<EncodedImage> mInputProducer2;


   public BranchOnSeparateImagesProducer(Producer<EncodedImage> var1, Producer<EncodedImage> var2) {
      this.mInputProducer1 = var1;
      this.mInputProducer2 = var2;
   }

   public void produceResults(Consumer<EncodedImage> var1, ProducerContext var2) {
      BranchOnSeparateImagesProducer.OnFirstImageConsumer var3 = new BranchOnSeparateImagesProducer.OnFirstImageConsumer(var1, var2, null);
      this.mInputProducer1.produceResults(var3, var2);
   }

   class OnFirstImageConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {

      private ProducerContext mProducerContext;


      private OnFirstImageConsumer(Consumer var2, ProducerContext var3) {
         super(var2);
         this.mProducerContext = var3;
      }

      // $FF: synthetic method
      OnFirstImageConsumer(Consumer var2, ProducerContext var3, Object var4) {
         this(var2, var3);
      }

      protected void onFailureImpl(Throwable var1) {
         BranchOnSeparateImagesProducer.this.mInputProducer2.produceResults(this.getConsumer(), this.mProducerContext);
      }

      protected void onNewResultImpl(EncodedImage var1, boolean var2) {
         ImageRequest var5 = this.mProducerContext.getImageRequest();
         boolean var4 = ThumbnailSizeChecker.isImageBigEnough(var1, var5.getResizeOptions());
         if(var1 != null && (var4 || var5.getLocalThumbnailPreviewsEnabled())) {
            Consumer var6 = this.getConsumer();
            boolean var3;
            if(var2 && var4) {
               var3 = true;
            } else {
               var3 = false;
            }

            var6.onNewResult(var1, var3);
         }

         if(var2 && !var4) {
            EncodedImage.closeSafely(var1);
            BranchOnSeparateImagesProducer.this.mInputProducer2.produceResults(this.getConsumer(), this.mProducerContext);
         }

      }
   }
}
