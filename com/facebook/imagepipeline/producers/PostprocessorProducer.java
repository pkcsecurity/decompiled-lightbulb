package com.facebook.imagepipeline.producers;

import android.graphics.Bitmap;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.Postprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessorRunner;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class PostprocessorProducer implements Producer<CloseableReference<CloseableImage>> {

   public static final String NAME = "PostprocessorProducer";
   @VisibleForTesting
   static final String POSTPROCESSOR = "Postprocessor";
   private final PlatformBitmapFactory mBitmapFactory;
   private final Executor mExecutor;
   private final Producer<CloseableReference<CloseableImage>> mInputProducer;


   public PostprocessorProducer(Producer<CloseableReference<CloseableImage>> var1, PlatformBitmapFactory var2, Executor var3) {
      this.mInputProducer = (Producer)Preconditions.checkNotNull(var1);
      this.mBitmapFactory = var2;
      this.mExecutor = (Executor)Preconditions.checkNotNull(var3);
   }

   public void produceResults(Consumer<CloseableReference<CloseableImage>> var1, ProducerContext var2) {
      ProducerListener var4 = var2.getListener();
      Postprocessor var3 = var2.getImageRequest().getPostprocessor();
      PostprocessorProducer.PostprocessorConsumer var5 = new PostprocessorProducer.PostprocessorConsumer(var1, var4, var2.getId(), var3, var2);
      Object var6;
      if(var3 instanceof RepeatedPostprocessor) {
         var6 = new PostprocessorProducer.RepeatedPostprocessorConsumer(var5, (RepeatedPostprocessor)var3, var2, null);
      } else {
         var6 = new PostprocessorProducer.SingleUsePostprocessorConsumer(var5, null);
      }

      this.mInputProducer.produceResults((Consumer)var6, var2);
   }

   class PostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {

      @GuardedBy
      private boolean mIsClosed;
      @GuardedBy
      private boolean mIsDirty = false;
      @GuardedBy
      private boolean mIsLast = false;
      @GuardedBy
      private boolean mIsPostProcessingRunning = false;
      private final ProducerListener mListener;
      private final Postprocessor mPostprocessor;
      private final String mRequestId;
      @Nullable
      @GuardedBy
      private CloseableReference<CloseableImage> mSourceImageRef = null;


      public PostprocessorConsumer(Consumer var2, ProducerListener var3, String var4, Postprocessor var5, ProducerContext var6) {
         super(var2);
         this.mListener = var3;
         this.mRequestId = var4;
         this.mPostprocessor = var5;
         var6.addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
               PostprocessorConsumer.this.maybeNotifyOnCancellation();
            }
         });
      }

      // $FF: synthetic method
      static CloseableReference access$300(PostprocessorProducer.PostprocessorConsumer var0) {
         return var0.mSourceImageRef;
      }

      // $FF: synthetic method
      static CloseableReference access$302(PostprocessorProducer.PostprocessorConsumer var0, CloseableReference var1) {
         var0.mSourceImageRef = var1;
         return var1;
      }

      // $FF: synthetic method
      static boolean access$400(PostprocessorProducer.PostprocessorConsumer var0) {
         return var0.mIsLast;
      }

      // $FF: synthetic method
      static boolean access$502(PostprocessorProducer.PostprocessorConsumer var0, boolean var1) {
         var0.mIsDirty = var1;
         return var1;
      }

      // $FF: synthetic method
      static void access$600(PostprocessorProducer.PostprocessorConsumer var0, CloseableReference var1, boolean var2) {
         var0.doPostprocessing(var1, var2);
      }

      // $FF: synthetic method
      static void access$700(PostprocessorProducer.PostprocessorConsumer var0) {
         var0.clearRunningAndStartIfDirty();
      }

      private void clearRunningAndStartIfDirty() {
         // $FF: Couldn't be decompiled
      }

      private boolean close() {
         // $FF: Couldn't be decompiled
      }

      private void doPostprocessing(CloseableReference<CloseableImage> param1, boolean param2) {
         // $FF: Couldn't be decompiled
      }

      private Map<String, String> getExtraMap(ProducerListener var1, String var2, Postprocessor var3) {
         return !var1.requiresExtraMap(var2)?null:ImmutableMap.of("Postprocessor", var3.getName());
      }

      private boolean isClosed() {
         synchronized(this){}

         boolean var1;
         try {
            var1 = this.mIsClosed;
         } finally {
            ;
         }

         return var1;
      }

      private void maybeNotifyOnCancellation() {
         if(this.close()) {
            this.getConsumer().onCancellation();
         }

      }

      private void maybeNotifyOnFailure(Throwable var1) {
         if(this.close()) {
            this.getConsumer().onFailure(var1);
         }

      }

      private void maybeNotifyOnNewResult(CloseableReference<CloseableImage> var1, boolean var2) {
         if(!var2 && !this.isClosed() || var2 && this.close()) {
            this.getConsumer().onNewResult(var1, var2);
         }

      }

      private CloseableReference<CloseableImage> postprocessInternal(CloseableImage var1) {
         CloseableStaticBitmap var4 = (CloseableStaticBitmap)var1;
         Bitmap var3 = var4.getUnderlyingBitmap();
         CloseableReference var8 = this.mPostprocessor.process(var3, PostprocessorProducer.this.mBitmapFactory);
         int var2 = var4.getRotationAngle();

         CloseableReference var7;
         try {
            var7 = CloseableReference.of(new CloseableStaticBitmap(var8, var1.getQualityInfo(), var2));
         } finally {
            CloseableReference.closeSafely(var8);
         }

         return var7;
      }

      private boolean setRunningIfDirtyAndNotRunning() {
         synchronized(this){}

         try {
            if(this.mIsClosed || !this.mIsDirty || this.mIsPostProcessingRunning || !CloseableReference.isValid(this.mSourceImageRef)) {
               return false;
            }

            this.mIsPostProcessingRunning = true;
         } finally {
            ;
         }

         return true;
      }

      private boolean shouldPostprocess(CloseableImage var1) {
         return var1 instanceof CloseableStaticBitmap;
      }

      private void submitPostprocessing() {
         PostprocessorProducer.this.mExecutor.execute(new Runnable() {
            public void run() {
               // $FF: Couldn't be decompiled
            }
         });
      }

      private void updateSourceImageRef(@Nullable CloseableReference<CloseableImage> param1, boolean param2) {
         // $FF: Couldn't be decompiled
      }

      protected void onCancellationImpl() {
         this.maybeNotifyOnCancellation();
      }

      protected void onFailureImpl(Throwable var1) {
         this.maybeNotifyOnFailure(var1);
      }

      protected void onNewResultImpl(CloseableReference<CloseableImage> var1, boolean var2) {
         if(!CloseableReference.isValid(var1)) {
            if(var2) {
               this.maybeNotifyOnNewResult((CloseableReference)null, true);
            }

         } else {
            this.updateSourceImageRef(var1, var2);
         }
      }
   }

   class RepeatedPostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> implements RepeatedPostprocessorRunner {

      @GuardedBy
      private boolean mIsClosed;
      @Nullable
      @GuardedBy
      private CloseableReference<CloseableImage> mSourceImageRef;


      private RepeatedPostprocessorConsumer(PostprocessorProducer.PostprocessorConsumer var2, RepeatedPostprocessor var3, ProducerContext var4) {
         super(var2);
         this.mIsClosed = false;
         this.mSourceImageRef = null;
         var3.setCallback(this);
         var4.addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
               if(RepeatedPostprocessorConsumer.this.close()) {
                  RepeatedPostprocessorConsumer.this.getConsumer().onCancellation();
               }

            }
         });
      }

      // $FF: synthetic method
      RepeatedPostprocessorConsumer(PostprocessorProducer.PostprocessorConsumer var2, RepeatedPostprocessor var3, ProducerContext var4, Object var5) {
         this(var2, var3, var4);
      }

      private boolean close() {
         // $FF: Couldn't be decompiled
      }

      private void setSourceImageRef(CloseableReference<CloseableImage> param1) {
         // $FF: Couldn't be decompiled
      }

      private void updateInternal() {
         // $FF: Couldn't be decompiled
      }

      protected void onCancellationImpl() {
         if(this.close()) {
            this.getConsumer().onCancellation();
         }

      }

      protected void onFailureImpl(Throwable var1) {
         if(this.close()) {
            this.getConsumer().onFailure(var1);
         }

      }

      protected void onNewResultImpl(CloseableReference<CloseableImage> var1, boolean var2) {
         if(var2) {
            this.setSourceImageRef(var1);
            this.updateInternal();
         }
      }

      public void update() {
         synchronized(this){}

         try {
            this.updateInternal();
         } finally {
            ;
         }

      }
   }

   class SingleUsePostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {

      private SingleUsePostprocessorConsumer(PostprocessorProducer.PostprocessorConsumer var2) {
         super(var2);
      }

      // $FF: synthetic method
      SingleUsePostprocessorConsumer(PostprocessorProducer.PostprocessorConsumer var2, Object var3) {
         this(var2);
      }

      protected void onNewResultImpl(CloseableReference<CloseableImage> var1, boolean var2) {
         if(var2) {
            this.getConsumer().onNewResult(var1, var2);
         }
      }
   }
}
