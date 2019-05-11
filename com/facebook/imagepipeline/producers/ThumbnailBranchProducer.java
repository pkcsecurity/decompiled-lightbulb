package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ThumbnailProducer;
import com.facebook.imagepipeline.producers.ThumbnailSizeChecker;

public class ThumbnailBranchProducer implements Producer<EncodedImage> {

   private final ThumbnailProducer<EncodedImage>[] mThumbnailProducers;


   public ThumbnailBranchProducer(ThumbnailProducer<EncodedImage> ... var1) {
      this.mThumbnailProducers = (ThumbnailProducer[])Preconditions.checkNotNull(var1);
      Preconditions.checkElementIndex(0, this.mThumbnailProducers.length);
   }

   private int findFirstProducerForSize(int var1, ResizeOptions var2) {
      while(var1 < this.mThumbnailProducers.length) {
         if(this.mThumbnailProducers[var1].canProvideImageForSize(var2)) {
            return var1;
         }

         ++var1;
      }

      return -1;
   }

   private boolean produceResultsFromThumbnailProducer(int var1, Consumer<EncodedImage> var2, ProducerContext var3) {
      var1 = this.findFirstProducerForSize(var1, var3.getImageRequest().getResizeOptions());
      if(var1 == -1) {
         return false;
      } else {
         this.mThumbnailProducers[var1].produceResults(new ThumbnailBranchProducer.ThumbnailConsumer(var2, var3, var1), var3);
         return true;
      }
   }

   public void produceResults(Consumer<EncodedImage> var1, ProducerContext var2) {
      if(var2.getImageRequest().getResizeOptions() == null) {
         var1.onNewResult((Object)null, true);
      } else {
         if(!this.produceResultsFromThumbnailProducer(0, var1, var2)) {
            var1.onNewResult((Object)null, true);
         }

      }
   }

   class ThumbnailConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {

      private final ProducerContext mProducerContext;
      private final int mProducerIndex;
      private final ResizeOptions mResizeOptions;


      public ThumbnailConsumer(Consumer var2, ProducerContext var3, int var4) {
         super(var2);
         this.mProducerContext = var3;
         this.mProducerIndex = var4;
         this.mResizeOptions = this.mProducerContext.getImageRequest().getResizeOptions();
      }

      protected void onFailureImpl(Throwable var1) {
         if(!ThumbnailBranchProducer.this.produceResultsFromThumbnailProducer(this.mProducerIndex + 1, this.getConsumer(), this.mProducerContext)) {
            this.getConsumer().onFailure(var1);
         }

      }

      protected void onNewResultImpl(EncodedImage var1, boolean var2) {
         if(var1 != null && (!var2 || ThumbnailSizeChecker.isImageBigEnough(var1, this.mResizeOptions))) {
            this.getConsumer().onNewResult(var1, var2);
         } else {
            if(var2) {
               EncodedImage.closeSafely(var1);
               if(!ThumbnailBranchProducer.this.produceResultsFromThumbnailProducer(this.mProducerIndex + 1, this.getConsumer(), this.mProducerContext)) {
                  this.getConsumer().onNewResult((Object)null, true);
               }
            }

         }
      }
   }
}
