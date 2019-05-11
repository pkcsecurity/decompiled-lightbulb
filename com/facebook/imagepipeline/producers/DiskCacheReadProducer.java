package com.facebook.imagepipeline.producers;

import bolts.Continuation;
import bolts.Task;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.imagepipeline.cache.DiskCachePolicy;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;

public class DiskCacheReadProducer implements Producer<EncodedImage> {

   public static final String ENCODED_IMAGE_SIZE = "encodedImageSize";
   public static final String EXTRA_CACHED_VALUE_FOUND = "cached_value_found";
   public static final String PRODUCER_NAME = "DiskCacheProducer";
   private final DiskCachePolicy mDiskCachePolicy;
   private final Producer<EncodedImage> mInputProducer;


   public DiskCacheReadProducer(Producer<EncodedImage> var1, DiskCachePolicy var2) {
      this.mInputProducer = var1;
      this.mDiskCachePolicy = var2;
   }

   @VisibleForTesting
   static Map<String, String> getExtraMap(ProducerListener var0, String var1, boolean var2, int var3) {
      return !var0.requiresExtraMap(var1)?null:(var2?ImmutableMap.of("cached_value_found", String.valueOf(var2), "encodedImageSize", String.valueOf(var3)):ImmutableMap.of("cached_value_found", String.valueOf(var2)));
   }

   private static boolean isTaskCancelled(Task<?> var0) {
      return var0.c() || var0.d() && var0.f() instanceof CancellationException;
   }

   private void maybeStartInputProducer(Consumer<EncodedImage> var1, ProducerContext var2) {
      if(var2.getLowestPermittedRequestLevel().getValue() >= ImageRequest.RequestLevel.DISK_CACHE.getValue()) {
         var1.onNewResult((Object)null, true);
      } else {
         this.mInputProducer.produceResults(var1, var2);
      }
   }

   private Continuation<EncodedImage, Void> onFinishDiskReads(final Consumer<EncodedImage> var1, final ProducerContext var2) {
      final String var3 = var2.getId();
      return new Continuation() {

         // $FF: synthetic field
         final ProducerListener val$listener;

         {
            this.val$listener = var2x;
         }
         public Void then(Task<EncodedImage> var1x) throws Exception {
            if(DiskCacheReadProducer.isTaskCancelled(var1x)) {
               this.val$listener.onProducerFinishWithCancellation(var3, "DiskCacheProducer", (Map)null);
               var1.onCancellation();
               return null;
            } else if(var1x.d()) {
               this.val$listener.onProducerFinishWithFailure(var3, "DiskCacheProducer", var1x.f(), (Map)null);
               DiskCacheReadProducer.this.mInputProducer.produceResults(var1, var2);
               return null;
            } else {
               EncodedImage var2x = (EncodedImage)var1x.e();
               if(var2x != null) {
                  this.val$listener.onProducerFinishWithSuccess(var3, "DiskCacheProducer", DiskCacheReadProducer.getExtraMap(this.val$listener, var3, true, var2x.getSize()));
                  this.val$listener.onUltimateProducerReached(var3, "DiskCacheProducer", true);
                  var1.onProgressUpdate(1.0F);
                  var1.onNewResult(var2x, true);
                  var2x.close();
                  return null;
               } else {
                  this.val$listener.onProducerFinishWithSuccess(var3, "DiskCacheProducer", DiskCacheReadProducer.getExtraMap(this.val$listener, var3, false, 0));
                  DiskCacheReadProducer.this.mInputProducer.produceResults(var1, var2);
                  return null;
               }
            }
         }
      };
   }

   private void subscribeTaskForRequestCancellation(final AtomicBoolean var1, ProducerContext var2) {
      var2.addCallbacks(new BaseProducerContextCallbacks() {
         public void onCancellationRequested() {
            var1.set(true);
         }
      });
   }

   public void produceResults(Consumer<EncodedImage> var1, ProducerContext var2) {
      ImageRequest var3 = var2.getImageRequest();
      if(!var3.isDiskCacheEnabled()) {
         this.maybeStartInputProducer(var1, var2);
      } else {
         var2.getListener().onProducerStart(var2.getId(), "DiskCacheProducer");
         AtomicBoolean var4 = new AtomicBoolean(false);
         this.mDiskCachePolicy.createAndStartCacheReadTask(var3, var2.getCallerContext(), var4).a(this.onFinishDiskReads(var1, var2));
         this.subscribeTaskForRequestCancellation(var4, var2);
      }
   }
}
