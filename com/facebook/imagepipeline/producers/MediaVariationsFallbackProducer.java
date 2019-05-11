package com.facebook.imagepipeline.producers;

import bolts.Continuation;
import bolts.Task;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.DiskCachePolicy;
import com.facebook.imagepipeline.cache.MediaIdExtractor;
import com.facebook.imagepipeline.cache.MediaVariationsIndex;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.MediaVariations;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;

public class MediaVariationsFallbackProducer implements Producer<EncodedImage> {

   public static final String EXTRA_CACHED_VALUE_FOUND = "cached_value_found";
   public static final String EXTRA_CACHED_VALUE_USED_AS_LAST = "cached_value_used_as_last";
   public static final String EXTRA_VARIANTS_COUNT = "variants_count";
   public static final String EXTRA_VARIANTS_SOURCE = "variants_source";
   public static final String PRODUCER_NAME = "MediaVariationsFallbackProducer";
   private final CacheKeyFactory mCacheKeyFactory;
   private final BufferedDiskCache mDefaultBufferedDiskCache;
   private final DiskCachePolicy mDiskCachePolicy;
   private final Producer<EncodedImage> mInputProducer;
   @Nullable
   private MediaIdExtractor mMediaIdExtractor;
   private final MediaVariationsIndex mMediaVariationsIndex;
   private final BufferedDiskCache mSmallImageBufferedDiskCache;


   public MediaVariationsFallbackProducer(BufferedDiskCache var1, BufferedDiskCache var2, CacheKeyFactory var3, MediaVariationsIndex var4, @Nullable MediaIdExtractor var5, DiskCachePolicy var6, Producer<EncodedImage> var7) {
      this.mDefaultBufferedDiskCache = var1;
      this.mSmallImageBufferedDiskCache = var2;
      this.mCacheKeyFactory = var3;
      this.mMediaVariationsIndex = var4;
      this.mMediaIdExtractor = var5;
      this.mDiskCachePolicy = var6;
      this.mInputProducer = var7;
   }

   private Task attemptCacheReadForVariant(Consumer<EncodedImage> var1, ProducerContext var2, ImageRequest var3, MediaVariations var4, List<MediaVariations.Variant> var5, int var6, AtomicBoolean var7) {
      MediaVariations.Variant var8 = (MediaVariations.Variant)var5.get(var6);
      CacheKey var9 = this.mCacheKeyFactory.getEncodedCacheKey(var3, var8.getUri(), var2.getCallerContext());
      ImageRequest.CacheChoice var10;
      if(var8.getCacheChoice() == null) {
         var10 = var3.getCacheChoice();
      } else {
         var10 = var8.getCacheChoice();
      }

      BufferedDiskCache var11;
      if(var10 == ImageRequest.CacheChoice.SMALL) {
         var11 = this.mSmallImageBufferedDiskCache;
      } else {
         var11 = this.mDefaultBufferedDiskCache;
      }

      return var11.get(var9, var7).a(this.onFinishDiskReads(var1, var2, var3, var4, var5, var6, var7));
   }

   private Task chooseFromVariants(Consumer<EncodedImage> var1, ProducerContext var2, ImageRequest var3, MediaVariations var4, ResizeOptions var5, AtomicBoolean var6) {
      if(var4.getVariantsCount() == 0) {
         Continuation var7 = this.onFinishDiskReads(var1, var2, var3, var4, Collections.emptyList(), 0, var6);
         return Task.a((Object)((EncodedImage)null)).a(var7);
      } else {
         return this.attemptCacheReadForVariant(var1, var2, var3, var4, var4.getSortedVariants(new MediaVariationsFallbackProducer.VariantComparator(var5)), 0, var6);
      }
   }

   @VisibleForTesting
   static Map<String, String> getExtraMap(ProducerListener var0, String var1, boolean var2, int var3, String var4, boolean var5) {
      return !var0.requiresExtraMap(var1)?null:(var2?ImmutableMap.of("cached_value_found", String.valueOf(true), "cached_value_used_as_last", String.valueOf(var5), "variants_count", String.valueOf(var3), "variants_source", var4):ImmutableMap.of("cached_value_found", String.valueOf(false), "variants_count", String.valueOf(var3), "variants_source", var4));
   }

   private static boolean isBigEnoughForRequestedSize(MediaVariations.Variant var0, ResizeOptions var1) {
      return var0.getWidth() >= var1.width && var0.getHeight() >= var1.height;
   }

   private static boolean isTaskCancelled(Task<?> var0) {
      return var0.c() || var0.d() && var0.f() instanceof CancellationException;
   }

   private Continuation<EncodedImage, Void> onFinishDiskReads(final Consumer<EncodedImage> var1, final ProducerContext var2, final ImageRequest var3, final MediaVariations var4, final List<MediaVariations.Variant> var5, final int var6, final AtomicBoolean var7) {
      final String var8 = var2.getId();
      return new Continuation() {

         // $FF: synthetic field
         final ProducerListener val$listener;

         {
            this.val$listener = var2x;
         }
         public Void then(Task<EncodedImage> var1x) throws Exception {
            boolean var3x = MediaVariationsFallbackProducer.isTaskCancelled(var1x);
            boolean var2x = false;
            boolean var4x = false;
            if(var3x) {
               this.val$listener.onProducerFinishWithCancellation(var8, "MediaVariationsFallbackProducer", (Map)null);
               var1.onCancellation();
            } else {
               label35: {
                  if(var1x.d()) {
                     this.val$listener.onProducerFinishWithFailure(var8, "MediaVariationsFallbackProducer", var1x.f(), (Map)null);
                     MediaVariationsFallbackProducer.this.startInputProducerWithWrappedConsumer(var1, var2, var4.getMediaId());
                  } else {
                     EncodedImage var5x = (EncodedImage)var1x.e();
                     if(var5x != null) {
                        var3x = var4x;
                        if(!var4.shouldForceRequestForSpecifiedUri()) {
                           var3x = var4x;
                           if(MediaVariationsFallbackProducer.isBigEnoughForRequestedSize((MediaVariations.Variant)var5.get(var6), var3.getResizeOptions())) {
                              var3x = true;
                           }
                        }

                        this.val$listener.onProducerFinishWithSuccess(var8, "MediaVariationsFallbackProducer", MediaVariationsFallbackProducer.getExtraMap(this.val$listener, var8, true, var5.size(), var4.getSource(), var3x));
                        if(var3x) {
                           this.val$listener.onUltimateProducerReached(var8, "MediaVariationsFallbackProducer", true);
                           var1.onProgressUpdate(1.0F);
                        }

                        var1.onNewResult(var5x, var3x);
                        var5x.close();
                        var2x = var3x ^ true;
                        break label35;
                     }

                     if(var6 < var5.size() - 1) {
                        MediaVariationsFallbackProducer.this.attemptCacheReadForVariant(var1, var2, var3, var4, var5, var6 + 1, var7);
                        break label35;
                     }

                     this.val$listener.onProducerFinishWithSuccess(var8, "MediaVariationsFallbackProducer", MediaVariationsFallbackProducer.getExtraMap(this.val$listener, var8, false, var5.size(), var4.getSource(), false));
                  }

                  var2x = true;
               }
            }

            if(var2x) {
               MediaVariationsFallbackProducer.this.startInputProducerWithWrappedConsumer(var1, var2, var4.getMediaId());
            }

            return null;
         }
      };
   }

   private void startInputProducerWithExistingConsumer(Consumer<EncodedImage> var1, ProducerContext var2) {
      this.mInputProducer.produceResults(var1, var2);
   }

   private void startInputProducerWithWrappedConsumer(Consumer<EncodedImage> var1, ProducerContext var2, String var3) {
      this.mInputProducer.produceResults(new MediaVariationsFallbackProducer.MediaVariationsConsumer(var1, var2, var3), var2);
   }

   private void subscribeTaskForRequestCancellation(final AtomicBoolean var1, ProducerContext var2) {
      var2.addCallbacks(new BaseProducerContextCallbacks() {
         public void onCancellationRequested() {
            var1.set(true);
         }
      });
   }

   public void produceResults(final Consumer<EncodedImage> var1, final ProducerContext var2) {
      final ImageRequest var7 = var2.getImageRequest();
      final ResizeOptions var8 = var7.getResizeOptions();
      MediaVariations var9 = var7.getMediaVariations();
      if(var7.isDiskCacheEnabled() && var8 != null && var8.height > 0 && var8.width > 0) {
         String var5;
         final String var6;
         if(var9 == null) {
            if(this.mMediaIdExtractor == null) {
               var5 = null;
               var6 = var5;
            } else {
               var6 = this.mMediaIdExtractor.getMediaIdFrom(var7.getSourceUri());
               var5 = "id_extractor";
            }
         } else {
            var6 = var9.getMediaId();
            var5 = "index_db";
         }

         if(var9 == null && var6 == null) {
            this.startInputProducerWithExistingConsumer(var1, var2);
         } else {
            var2.getListener().onProducerStart(var2.getId(), "MediaVariationsFallbackProducer");
            boolean var4 = false;
            final AtomicBoolean var10 = new AtomicBoolean(false);
            if(var9 != null && var9.getVariantsCount() > 0) {
               this.chooseFromVariants(var1, var2, var7, var9, var8, var10);
            } else {
               MediaVariations.Builder var11 = MediaVariations.newBuilderForMediaId(var6);
               boolean var3 = var4;
               if(var9 != null) {
                  var3 = var4;
                  if(var9.shouldForceRequestForSpecifiedUri()) {
                     var3 = true;
                  }
               }

               MediaVariations.Builder var12 = var11.setForceRequestForSpecifiedUri(var3).setSource(var5);
               this.mMediaVariationsIndex.getCachedVariants(var6, var12).a(new Continuation() {
                  public Object then(Task<MediaVariations> var1x) throws Exception {
                     if(!var1x.c()) {
                        if(var1x.d()) {
                           return var1x;
                        } else {
                           try {
                              if(var1x.e() == null) {
                                 MediaVariationsFallbackProducer.this.startInputProducerWithWrappedConsumer(var1, var2, var6);
                                 return null;
                              } else {
                                 var1x = MediaVariationsFallbackProducer.this.chooseFromVariants(var1, var2, var7, (MediaVariations)var1x.e(), var8, var10);
                                 return var1x;
                              }
                           } catch (Exception var2x) {
                              return null;
                           }
                        }
                     } else {
                        return var1x;
                     }
                  }
               });
            }

            this.subscribeTaskForRequestCancellation(var10, var2);
         }
      } else {
         this.startInputProducerWithExistingConsumer(var1, var2);
      }
   }

   @VisibleForTesting
   class MediaVariationsConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {

      private final String mMediaId;
      private final ProducerContext mProducerContext;


      public MediaVariationsConsumer(Consumer var2, ProducerContext var3, String var4) {
         super(var2);
         this.mProducerContext = var3;
         this.mMediaId = var4;
      }

      private void storeResultInDatabase(EncodedImage var1) {
         ImageRequest var3 = this.mProducerContext.getImageRequest();
         if(var3.isDiskCacheEnabled()) {
            if(this.mMediaId != null) {
               ImageRequest.CacheChoice var2 = MediaVariationsFallbackProducer.this.mDiskCachePolicy.getCacheChoiceForResult(var3, var1);
               CacheKey var4 = MediaVariationsFallbackProducer.this.mCacheKeyFactory.getEncodedCacheKey(var3, this.mProducerContext.getCallerContext());
               MediaVariationsFallbackProducer.this.mMediaVariationsIndex.saveCachedVariant(this.mMediaId, var2, var4, var1);
            }
         }
      }

      protected void onNewResultImpl(EncodedImage var1, boolean var2) {
         if(var2 && var1 != null) {
            this.storeResultInDatabase(var1);
         }

         this.getConsumer().onNewResult(var1, var2);
      }
   }

   @VisibleForTesting
   static class VariantComparator implements Comparator<MediaVariations.Variant> {

      private final ResizeOptions mResizeOptions;


      VariantComparator(ResizeOptions var1) {
         this.mResizeOptions = var1;
      }

      public int compare(MediaVariations.Variant var1, MediaVariations.Variant var2) {
         boolean var3 = MediaVariationsFallbackProducer.isBigEnoughForRequestedSize(var1, this.mResizeOptions);
         boolean var4 = MediaVariationsFallbackProducer.isBigEnoughForRequestedSize(var2, this.mResizeOptions);
         return var3 && var4?var1.getWidth() - var2.getWidth():(var3?-1:(var4?1:var2.getWidth() - var1.getWidth()));
      }
   }
}
