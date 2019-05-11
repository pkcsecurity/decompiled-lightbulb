package com.facebook.drawee.backends.pipeline;

import android.content.res.Resources;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableList;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.DrawableFactory;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.components.DeferredReleaser;
import com.facebook.imagepipeline.animated.factory.AnimatedDrawableFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class PipelineDraweeControllerFactory {

   private AnimatedDrawableFactory mAnimatedDrawableFactory;
   @Nullable
   private Supplier<Boolean> mDebugOverlayEnabledSupplier;
   private DeferredReleaser mDeferredReleaser;
   @Nullable
   private ImmutableList<DrawableFactory> mDrawableFactories;
   private MemoryCache<CacheKey, CloseableImage> mMemoryCache;
   private Resources mResources;
   private Executor mUiThreadExecutor;


   public void init(Resources var1, DeferredReleaser var2, AnimatedDrawableFactory var3, Executor var4, MemoryCache<CacheKey, CloseableImage> var5, @Nullable ImmutableList<DrawableFactory> var6, @Nullable Supplier<Boolean> var7) {
      this.mResources = var1;
      this.mDeferredReleaser = var2;
      this.mAnimatedDrawableFactory = var3;
      this.mUiThreadExecutor = var4;
      this.mMemoryCache = var5;
      this.mDrawableFactories = var6;
      this.mDebugOverlayEnabledSupplier = var7;
   }

   protected PipelineDraweeController internalCreateController(Resources var1, DeferredReleaser var2, AnimatedDrawableFactory var3, Executor var4, MemoryCache<CacheKey, CloseableImage> var5, @Nullable ImmutableList<DrawableFactory> var6, Supplier<DataSource<CloseableReference<CloseableImage>>> var7, String var8, CacheKey var9, Object var10) {
      return new PipelineDraweeController(var1, var2, var3, var4, var5, var7, var8, var9, var10, var6);
   }

   public PipelineDraweeController newController(Supplier<DataSource<CloseableReference<CloseableImage>>> var1, String var2, CacheKey var3, Object var4) {
      boolean var5;
      if(this.mResources != null) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkState(var5, "init() not called");
      PipelineDraweeController var6 = this.internalCreateController(this.mResources, this.mDeferredReleaser, this.mAnimatedDrawableFactory, this.mUiThreadExecutor, this.mMemoryCache, this.mDrawableFactories, var1, var2, var3, var4);
      if(this.mDebugOverlayEnabledSupplier != null) {
         var6.setDrawDebugOverlay(((Boolean)this.mDebugOverlayEnabledSupplier.get()).booleanValue());
      }

      return var6;
   }
}
