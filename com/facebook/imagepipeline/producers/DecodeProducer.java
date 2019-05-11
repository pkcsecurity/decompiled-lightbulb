package com.facebook.imagepipeline.producers;

import android.graphics.Bitmap;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.ExceptionWithNoStacktrace;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegParser;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.DownsampleUtil;
import com.facebook.imagepipeline.producers.JobScheduler;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class DecodeProducer implements Producer<CloseableReference<CloseableImage>> {

   public static final String ENCODED_IMAGE_SIZE = "encodedImageSize";
   public static final String EXTRA_BITMAP_SIZE = "bitmapSize";
   public static final String EXTRA_HAS_GOOD_QUALITY = "hasGoodQuality";
   public static final String EXTRA_IMAGE_FORMAT_NAME = "imageFormat";
   public static final String EXTRA_IS_FINAL = "isFinal";
   public static final String PRODUCER_NAME = "DecodeProducer";
   public static final String REQUESTED_IMAGE_SIZE = "requestedImageSize";
   public static final String SAMPLE_SIZE = "sampleSize";
   private final ByteArrayPool mByteArrayPool;
   private final boolean mDecodeCancellationEnabled;
   private final boolean mDownsampleEnabled;
   private final boolean mDownsampleEnabledForNetwork;
   private final Executor mExecutor;
   private final ImageDecoder mImageDecoder;
   private final Producer<EncodedImage> mInputProducer;
   private final ProgressiveJpegConfig mProgressiveJpegConfig;


   public DecodeProducer(ByteArrayPool var1, Executor var2, ImageDecoder var3, ProgressiveJpegConfig var4, boolean var5, boolean var6, boolean var7, Producer<EncodedImage> var8) {
      this.mByteArrayPool = (ByteArrayPool)Preconditions.checkNotNull(var1);
      this.mExecutor = (Executor)Preconditions.checkNotNull(var2);
      this.mImageDecoder = (ImageDecoder)Preconditions.checkNotNull(var3);
      this.mProgressiveJpegConfig = (ProgressiveJpegConfig)Preconditions.checkNotNull(var4);
      this.mDownsampleEnabled = var5;
      this.mDownsampleEnabledForNetwork = var6;
      this.mInputProducer = (Producer)Preconditions.checkNotNull(var8);
      this.mDecodeCancellationEnabled = var7;
   }

   // $FF: synthetic method
   static ImageDecoder access$700(DecodeProducer var0) {
      return var0.mImageDecoder;
   }

   public void produceResults(Consumer<CloseableReference<CloseableImage>> var1, ProducerContext var2) {
      Object var3;
      if(!UriUtil.isNetworkUri(var2.getImageRequest().getSourceUri())) {
         var3 = new DecodeProducer.LocalImagesProgressiveDecoder(var1, var2, this.mDecodeCancellationEnabled);
      } else {
         var3 = new DecodeProducer.NetworkImagesProgressiveDecoder(var1, var2, new ProgressiveJpegParser(this.mByteArrayPool), this.mProgressiveJpegConfig, this.mDecodeCancellationEnabled);
      }

      this.mInputProducer.produceResults((Consumer)var3, var2);
   }

   class LocalImagesProgressiveDecoder extends DecodeProducer.ProgressiveDecoder {

      public LocalImagesProgressiveDecoder(Consumer var2, ProducerContext var3, boolean var4) {
         super(var2, var3, var4);
      }

      protected int getIntermediateImageEndOffset(EncodedImage var1) {
         return var1.getSize();
      }

      protected QualityInfo getQualityInfo() {
         return ImmutableQualityInfo.of(0, false, false);
      }

      protected boolean updateDecodeJob(EncodedImage var1, boolean var2) {
         synchronized(this){}
         if(!var2) {
            return false;
         } else {
            try {
               var2 = super.updateDecodeJob(var1, var2);
            } finally {
               ;
            }

            return var2;
         }
      }
   }

   class NetworkImagesProgressiveDecoder extends DecodeProducer.ProgressiveDecoder {

      private int mLastScheduledScanNumber;
      private final ProgressiveJpegConfig mProgressiveJpegConfig;
      private final ProgressiveJpegParser mProgressiveJpegParser;


      public NetworkImagesProgressiveDecoder(Consumer var2, ProducerContext var3, ProgressiveJpegParser var4, ProgressiveJpegConfig var5, boolean var6) {
         super(var2, var3, var6);
         this.mProgressiveJpegParser = (ProgressiveJpegParser)Preconditions.checkNotNull(var4);
         this.mProgressiveJpegConfig = (ProgressiveJpegConfig)Preconditions.checkNotNull(var5);
         this.mLastScheduledScanNumber = 0;
      }

      protected int getIntermediateImageEndOffset(EncodedImage var1) {
         return this.mProgressiveJpegParser.getBestScanEndOffset();
      }

      protected QualityInfo getQualityInfo() {
         return this.mProgressiveJpegConfig.getQualityInfo(this.mProgressiveJpegParser.getBestScanNumber());
      }

      protected boolean updateDecodeJob(EncodedImage param1, boolean param2) {
         // $FF: Couldn't be decompiled
      }
   }

   abstract class ProgressiveDecoder extends DelegatingConsumer<EncodedImage, CloseableReference<CloseableImage>> {

      private final ImageDecodeOptions mImageDecodeOptions;
      @GuardedBy
      private boolean mIsFinished;
      private final JobScheduler mJobScheduler;
      private final ProducerContext mProducerContext;
      private final ProducerListener mProducerListener;


      public ProgressiveDecoder(Consumer var2, final ProducerContext var3, final boolean var4) {
         super(var2);
         this.mProducerContext = var3;
         this.mProducerListener = var3.getListener();
         this.mImageDecodeOptions = var3.getImageRequest().getImageDecodeOptions();
         this.mIsFinished = false;
         JobScheduler.JobRunnable var5 = new JobScheduler.JobRunnable() {
            public void run(EncodedImage var1, boolean var2) {
               if(var1 != null) {
                  if(DecodeProducer.this.mDownsampleEnabled) {
                     ImageRequest var3x = var3.getImageRequest();
                     if(DecodeProducer.this.mDownsampleEnabledForNetwork || !UriUtil.isNetworkUri(var3x.getSourceUri())) {
                        var1.setSampleSize(DownsampleUtil.determineSampleSize(var3x, var1));
                     }
                  }

                  ProgressiveDecoder.this.doDecode(var1, var2);
               }

            }
         };
         this.mJobScheduler = new JobScheduler(DecodeProducer.this.mExecutor, var5, this.mImageDecodeOptions.minDecodeIntervalMs);
         this.mProducerContext.addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
               if(var4) {
                  ProgressiveDecoder.this.handleCancellation();
               }

            }
            public void onIsIntermediateResultExpectedChanged() {
               if(ProgressiveDecoder.this.mProducerContext.isIntermediateResultExpected()) {
                  ProgressiveDecoder.this.mJobScheduler.scheduleJob();
               }

            }
         });
      }

      private void doDecode(EncodedImage param1, boolean param2) {
         // $FF: Couldn't be decompiled
      }

      private Map<String, String> getExtraMap(@Nullable CloseableImage var1, long var2, QualityInfo var4, boolean var5, String var6, String var7, String var8, String var9) {
         if(!this.mProducerListener.requiresExtraMap(this.mProducerContext.getId())) {
            return null;
         } else {
            String var10 = String.valueOf(var2);
            String var17 = String.valueOf(var4.isOfGoodEnoughQuality());
            String var11 = String.valueOf(var5);
            if(var1 instanceof CloseableStaticBitmap) {
               Bitmap var14 = ((CloseableStaticBitmap)var1).getUnderlyingBitmap();
               StringBuilder var12 = new StringBuilder();
               var12.append(var14.getWidth());
               var12.append("x");
               var12.append(var14.getHeight());
               String var16 = var12.toString();
               HashMap var15 = new HashMap(8);
               var15.put("bitmapSize", var16);
               var15.put("queueTime", var10);
               var15.put("hasGoodQuality", var17);
               var15.put("isFinal", var11);
               var15.put("encodedImageSize", var7);
               var15.put("imageFormat", var6);
               var15.put("requestedImageSize", var8);
               var15.put("sampleSize", var9);
               return ImmutableMap.copyOf(var15);
            } else {
               HashMap var13 = new HashMap(7);
               var13.put("queueTime", var10);
               var13.put("hasGoodQuality", var17);
               var13.put("isFinal", var11);
               var13.put("encodedImageSize", var7);
               var13.put("imageFormat", var6);
               var13.put("requestedImageSize", var8);
               var13.put("sampleSize", var9);
               return ImmutableMap.copyOf(var13);
            }
         }
      }

      private void handleCancellation() {
         this.maybeFinish(true);
         this.getConsumer().onCancellation();
      }

      private void handleError(Throwable var1) {
         this.maybeFinish(true);
         this.getConsumer().onFailure(var1);
      }

      private void handleResult(CloseableImage var1, boolean var2) {
         CloseableReference var6 = CloseableReference.of(var1);

         try {
            this.maybeFinish(var2);
            this.getConsumer().onNewResult(var6, var2);
         } finally {
            CloseableReference.closeSafely(var6);
         }

      }

      private boolean isFinished() {
         synchronized(this){}

         boolean var1;
         try {
            var1 = this.mIsFinished;
         } finally {
            ;
         }

         return var1;
      }

      private void maybeFinish(boolean param1) {
         // $FF: Couldn't be decompiled
      }

      protected abstract int getIntermediateImageEndOffset(EncodedImage var1);

      protected abstract QualityInfo getQualityInfo();

      public void onCancellationImpl() {
         this.handleCancellation();
      }

      public void onFailureImpl(Throwable var1) {
         this.handleError(var1);
      }

      public void onNewResultImpl(EncodedImage var1, boolean var2) {
         if(var2 && !EncodedImage.isValid(var1)) {
            this.handleError(new ExceptionWithNoStacktrace("Encoded image is not valid."));
         } else if(this.updateDecodeJob(var1, var2)) {
            if(var2 || this.mProducerContext.isIntermediateResultExpected()) {
               this.mJobScheduler.scheduleJob();
            }

         }
      }

      protected void onProgressUpdateImpl(float var1) {
         super.onProgressUpdateImpl(var1 * 0.99F);
      }

      protected boolean updateDecodeJob(EncodedImage var1, boolean var2) {
         return this.mJobScheduler.updateJob(var1, var2);
      }
   }
}
