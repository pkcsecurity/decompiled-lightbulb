package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.util.TriState;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.JobScheduler;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class ResizeAndRotateProducer implements Producer<EncodedImage> {

   @VisibleForTesting
   static final int DEFAULT_JPEG_QUALITY = 85;
   private static final String DOWNSAMPLE_ENUMERATOR_KEY = "downsampleEnumerator";
   private static final String FRACTION_KEY = "Fraction";
   private static final int FULL_ROUND = 360;
   @VisibleForTesting
   static final int MAX_JPEG_SCALE_NUMERATOR = 8;
   @VisibleForTesting
   static final int MIN_TRANSFORM_INTERVAL_MS = 100;
   private static final String ORIGINAL_SIZE_KEY = "Original size";
   public static final String PRODUCER_NAME = "ResizeAndRotateProducer";
   private static final String REQUESTED_SIZE_KEY = "Requested size";
   private static final String ROTATION_ANGLE_KEY = "rotationAngle";
   private static final String SOFTWARE_ENUMERATOR_KEY = "softwareEnumerator";
   private final Executor mExecutor;
   private final Producer<EncodedImage> mInputProducer;
   private final PooledByteBufferFactory mPooledByteBufferFactory;
   private final boolean mResizingEnabled;
   private final boolean mUseDownsamplingRatio;


   public ResizeAndRotateProducer(Executor var1, PooledByteBufferFactory var2, boolean var3, Producer<EncodedImage> var4, boolean var5) {
      this.mExecutor = (Executor)Preconditions.checkNotNull(var1);
      this.mPooledByteBufferFactory = (PooledByteBufferFactory)Preconditions.checkNotNull(var2);
      this.mResizingEnabled = var3;
      this.mInputProducer = (Producer)Preconditions.checkNotNull(var4);
      this.mUseDownsamplingRatio = var5;
   }

   // $FF: synthetic method
   static int access$1000(RotationOptions var0, EncodedImage var1) {
      return getRotationAngle(var0, var1);
   }

   // $FF: synthetic method
   static PooledByteBufferFactory access$700(ResizeAndRotateProducer var0) {
      return var0.mPooledByteBufferFactory;
   }

   // $FF: synthetic method
   static int access$800(ImageRequest var0, EncodedImage var1, boolean var2) {
      return getSoftwareNumerator(var0, var1, var2);
   }

   // $FF: synthetic method
   static boolean access$900(ResizeAndRotateProducer var0) {
      return var0.mUseDownsamplingRatio;
   }

   @VisibleForTesting
   static int calculateDownsampleNumerator(int var0) {
      return Math.max(1, 8 / var0);
   }

   @VisibleForTesting
   static float determineResizeRatio(ResizeOptions var0, int var1, int var2) {
      if(var0 == null) {
         return 1.0F;
      } else {
         float var3 = (float)var0.width;
         float var6 = (float)var1;
         var3 /= var6;
         float var4 = (float)var0.height;
         float var5 = (float)var2;
         var4 = Math.max(var3, var4 / var5);
         var3 = var4;
         if(var6 * var4 > var0.maxBitmapSize) {
            var3 = var0.maxBitmapSize / var6;
         }

         var4 = var3;
         if(var5 * var3 > var0.maxBitmapSize) {
            var4 = var0.maxBitmapSize / var5;
         }

         return var4;
      }
   }

   private static int extractOrientationFromMetadata(EncodedImage var0) {
      int var1 = var0.getRotationAngle();
      return var1 != 90 && var1 != 180 && var1 != 270?0:var0.getRotationAngle();
   }

   private static int getRotationAngle(RotationOptions var0, EncodedImage var1) {
      if(!var0.rotationEnabled()) {
         return 0;
      } else {
         int var2 = extractOrientationFromMetadata(var1);
         return var0.useImageMetadata()?var2:(var2 + var0.getForcedAngle()) % 360;
      }
   }

   private static int getSoftwareNumerator(ImageRequest var0, EncodedImage var1, boolean var2) {
      if(!var2) {
         return 8;
      } else {
         ResizeOptions var5 = var0.getResizeOptions();
         if(var5 == null) {
            return 8;
         } else {
            int var3 = getRotationAngle(var0.getRotationOptions(), var1);
            boolean var4;
            if(var3 != 90 && var3 != 270) {
               var4 = false;
            } else {
               var4 = true;
            }

            if(var4) {
               var3 = var1.getHeight();
            } else {
               var3 = var1.getWidth();
            }

            int var6;
            if(var4) {
               var6 = var1.getWidth();
            } else {
               var6 = var1.getHeight();
            }

            var6 = roundNumerator(determineResizeRatio(var5, var3, var6), var5.roundUpFraction);
            if(var6 > 8) {
               return 8;
            } else {
               var3 = var6;
               if(var6 < 1) {
                  var3 = 1;
               }

               return var3;
            }
         }
      }
   }

   @VisibleForTesting
   static int roundNumerator(float var0, float var1) {
      return (int)(var1 + var0 * 8.0F);
   }

   private static boolean shouldResize(int var0) {
      return var0 < 8;
   }

   private static boolean shouldRotate(RotationOptions var0, EncodedImage var1) {
      return !var0.canDeferUntilRendered() && getRotationAngle(var0, var1) != 0;
   }

   private static TriState shouldTransform(ImageRequest var0, EncodedImage var1, boolean var2) {
      if(var1 != null && var1.getImageFormat() != ImageFormat.UNKNOWN) {
         if(var1.getImageFormat() != DefaultImageFormats.JPEG) {
            return TriState.NO;
         } else {
            if(!shouldRotate(var0.getRotationOptions(), var1) && !shouldResize(getSoftwareNumerator(var0, var1, var2))) {
               var2 = false;
            } else {
               var2 = true;
            }

            return TriState.valueOf(var2);
         }
      } else {
         return TriState.UNSET;
      }
   }

   public void produceResults(Consumer<EncodedImage> var1, ProducerContext var2) {
      this.mInputProducer.produceResults(new ResizeAndRotateProducer.TransformingConsumer(var1, var2), var2);
   }

   class TransformingConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {

      private boolean mIsCancelled = false;
      private final JobScheduler mJobScheduler;
      private final ProducerContext mProducerContext;


      public TransformingConsumer(final Consumer var2, ProducerContext var3) {
         super(var2);
         this.mProducerContext = var3;
         JobScheduler.JobRunnable var4 = new JobScheduler.JobRunnable() {
            public void run(EncodedImage var1, boolean var2) {
               TransformingConsumer.this.doTransform(var1, var2);
            }
         };
         this.mJobScheduler = new JobScheduler(ResizeAndRotateProducer.this.mExecutor, var4, 100);
         this.mProducerContext.addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
               TransformingConsumer.this.mJobScheduler.clearJob();
               TransformingConsumer.this.mIsCancelled = true;
               var2.onCancellation();
            }
            public void onIsIntermediateResultExpectedChanged() {
               if(TransformingConsumer.this.mProducerContext.isIntermediateResultExpected()) {
                  TransformingConsumer.this.mJobScheduler.scheduleJob();
               }

            }
         });
      }

      private void doTransform(EncodedImage param1, boolean param2) {
         // $FF: Couldn't be decompiled
      }

      private Map<String, String> getExtraMap(EncodedImage var1, ImageRequest var2, int var3, int var4, int var5, int var6) {
         if(!this.mProducerContext.getListener().requiresExtraMap(this.mProducerContext.getId())) {
            return null;
         } else {
            StringBuilder var7 = new StringBuilder();
            var7.append(var1.getWidth());
            var7.append("x");
            var7.append(var1.getHeight());
            String var13 = var7.toString();
            String var10;
            if(var2.getResizeOptions() != null) {
               StringBuilder var9 = new StringBuilder();
               var9.append(var2.getResizeOptions().width);
               var9.append("x");
               var9.append(var2.getResizeOptions().height);
               var10 = var9.toString();
            } else {
               var10 = "Unspecified";
            }

            String var12;
            if(var3 > 0) {
               StringBuilder var11 = new StringBuilder();
               var11.append(var3);
               var11.append("/8");
               var12 = var11.toString();
            } else {
               var12 = "";
            }

            HashMap var8 = new HashMap();
            var8.put("Original size", var13);
            var8.put("Requested size", var10);
            var8.put("Fraction", var12);
            var8.put("queueTime", String.valueOf(this.mJobScheduler.getQueuedTime()));
            var8.put("downsampleEnumerator", Integer.toString(var4));
            var8.put("softwareEnumerator", Integer.toString(var5));
            var8.put("rotationAngle", Integer.toString(var6));
            return ImmutableMap.copyOf(var8);
         }
      }

      protected void onNewResultImpl(@Nullable EncodedImage var1, boolean var2) {
         if(!this.mIsCancelled) {
            if(var1 == null) {
               if(var2) {
                  this.getConsumer().onNewResult((Object)null, true);
               }

            } else {
               TriState var3 = ResizeAndRotateProducer.shouldTransform(this.mProducerContext.getImageRequest(), var1, ResizeAndRotateProducer.this.mResizingEnabled);
               if(var2 || var3 != TriState.UNSET) {
                  if(var3 != TriState.YES) {
                     this.getConsumer().onNewResult(var1, var2);
                  } else if(this.mJobScheduler.updateJob(var1, var2)) {
                     if(var2 || this.mProducerContext.isIntermediateResultExpected()) {
                        this.mJobScheduler.scheduleJob();
                     }

                  }
               }
            }
         }
      }
   }
}
