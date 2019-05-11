package com.facebook.drawee.backends.pipeline;

import android.content.Context;
import android.content.res.Resources;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.internal.ImmutableList;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.DraweeConfig;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerFactory;
import com.facebook.drawee.components.DeferredReleaser;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.imagepipeline.animated.factory.AnimatedDrawableFactory;
import com.facebook.imagepipeline.animated.factory.AnimatedFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import java.util.Set;
import javax.annotation.Nullable;

public class PipelineDraweeControllerBuilderSupplier implements Supplier<PipelineDraweeControllerBuilder> {

   private final Set<ControllerListener> mBoundControllerListeners;
   private final Context mContext;
   private final ImagePipeline mImagePipeline;
   private final PipelineDraweeControllerFactory mPipelineDraweeControllerFactory;


   public PipelineDraweeControllerBuilderSupplier(Context var1) {
      this(var1, (DraweeConfig)null);
   }

   public PipelineDraweeControllerBuilderSupplier(Context var1, @Nullable DraweeConfig var2) {
      this(var1, ImagePipelineFactory.getInstance(), var2);
   }

   public PipelineDraweeControllerBuilderSupplier(Context var1, ImagePipelineFactory var2, @Nullable DraweeConfig var3) {
      this(var1, var2, (Set)null, var3);
   }

   public PipelineDraweeControllerBuilderSupplier(Context var1, ImagePipelineFactory var2, Set<ControllerListener> var3, @Nullable DraweeConfig var4) {
      this.mContext = var1;
      this.mImagePipeline = var2.getImagePipeline();
      AnimatedFactory var12 = var2.getAnimatedFactory();
      Supplier var5 = null;
      AnimatedDrawableFactory var13;
      if(var12 != null) {
         var13 = var12.getAnimatedDrawableFactory(var1);
      } else {
         var13 = null;
      }

      if(var4 != null && var4.getPipelineDraweeControllerFactory() != null) {
         this.mPipelineDraweeControllerFactory = var4.getPipelineDraweeControllerFactory();
      } else {
         this.mPipelineDraweeControllerFactory = new PipelineDraweeControllerFactory();
      }

      PipelineDraweeControllerFactory var6 = this.mPipelineDraweeControllerFactory;
      Resources var7 = var1.getResources();
      DeferredReleaser var8 = DeferredReleaser.getInstance();
      UiThreadImmediateExecutorService var9 = UiThreadImmediateExecutorService.getInstance();
      MemoryCache var10 = this.mImagePipeline.getBitmapMemoryCache();
      ImmutableList var11;
      if(var4 != null) {
         var11 = var4.getCustomDrawableFactories();
      } else {
         var11 = null;
      }

      if(var4 != null) {
         var5 = var4.getDebugOverlayEnabledSupplier();
      }

      var6.init(var7, var8, var13, var9, var10, var11, var5);
      this.mBoundControllerListeners = var3;
   }

   public PipelineDraweeControllerBuilder get() {
      return new PipelineDraweeControllerBuilder(this.mContext, this.mPipelineDraweeControllerFactory, this.mImagePipeline, this.mBoundControllerListeners);
   }
}
