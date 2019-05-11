package com.facebook.drawee.backends.pipeline;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableList;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawable.base.DrawableWithCaches;
import com.facebook.drawee.backends.pipeline.DrawableFactory;
import com.facebook.drawee.components.DeferredReleaser;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.debug.DebugControllerOverlayDrawable;
import com.facebook.drawee.drawable.OrientedDrawable;
import com.facebook.drawee.drawable.ScaleTypeDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.facebook.imagepipeline.animated.factory.AnimatedDrawableFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImageInfo;
import java.util.Iterator;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class PipelineDraweeController extends AbstractDraweeController<CloseableReference<CloseableImage>, ImageInfo> {

   private static final Class<?> TAG = PipelineDraweeController.class;
   private final AnimatedDrawableFactory mAnimatedDrawableFactory;
   private CacheKey mCacheKey;
   private Supplier<DataSource<CloseableReference<CloseableImage>>> mDataSourceSupplier;
   private final DrawableFactory mDefaultDrawableFactory;
   private boolean mDrawDebugOverlay;
   @Nullable
   private final ImmutableList<DrawableFactory> mDrawableFactories;
   @Nullable
   private MemoryCache<CacheKey, CloseableImage> mMemoryCache;
   private final Resources mResources;


   public PipelineDraweeController(Resources var1, DeferredReleaser var2, AnimatedDrawableFactory var3, Executor var4, MemoryCache<CacheKey, CloseableImage> var5, Supplier<DataSource<CloseableReference<CloseableImage>>> var6, String var7, CacheKey var8, Object var9) {
      this(var1, var2, var3, var4, var5, var6, var7, var8, var9, (ImmutableList)null);
   }

   public PipelineDraweeController(Resources var1, DeferredReleaser var2, AnimatedDrawableFactory var3, Executor var4, MemoryCache<CacheKey, CloseableImage> var5, Supplier<DataSource<CloseableReference<CloseableImage>>> var6, String var7, CacheKey var8, Object var9, @Nullable ImmutableList<DrawableFactory> var10) {
      super(var2, var4, var7, var9);
      this.mDefaultDrawableFactory = new DrawableFactory() {
         public Drawable createDrawable(CloseableImage var1) {
            if(var1 instanceof CloseableStaticBitmap) {
               CloseableStaticBitmap var3 = (CloseableStaticBitmap)var1;
               BitmapDrawable var2 = new BitmapDrawable(PipelineDraweeController.this.mResources, var3.getUnderlyingBitmap());
               return (Drawable)(var3.getRotationAngle() != 0?(var3.getRotationAngle() == -1?var2:new OrientedDrawable(var2, var3.getRotationAngle())):var2);
            } else {
               return PipelineDraweeController.this.mAnimatedDrawableFactory != null?PipelineDraweeController.this.mAnimatedDrawableFactory.create(var1):null;
            }
         }
         public boolean supportsImageType(CloseableImage var1) {
            return true;
         }
      };
      this.mResources = var1;
      this.mAnimatedDrawableFactory = var3;
      this.mMemoryCache = var5;
      this.mCacheKey = var8;
      this.mDrawableFactories = var10;
      this.init(var6);
   }

   private void init(Supplier<DataSource<CloseableReference<CloseableImage>>> var1) {
      this.mDataSourceSupplier = var1;
      this.maybeUpdateDebugOverlay((CloseableImage)null);
   }

   private void maybeUpdateDebugOverlay(@Nullable CloseableImage var1) {
      if(this.mDrawDebugOverlay) {
         Drawable var3 = this.getControllerOverlay();
         Object var2 = var3;
         if(var3 == null) {
            var2 = new DebugControllerOverlayDrawable();
            this.setControllerOverlay((Drawable)var2);
         }

         if(var2 instanceof DebugControllerOverlayDrawable) {
            DebugControllerOverlayDrawable var4 = (DebugControllerOverlayDrawable)var2;
            var4.setControllerId(this.getId());
            DraweeHierarchy var5 = this.getHierarchy();
            var3 = null;
            ScalingUtils.ScaleType var7 = var3;
            if(var5 != null) {
               ScaleTypeDrawable var6 = ScalingUtils.getActiveScaleTypeDrawable(var5.getTopLevelDrawable());
               var7 = var3;
               if(var6 != null) {
                  var7 = var6.getScaleType();
               }
            }

            var4.setScaleType(var7);
            if(var1 != null) {
               var4.setDimensions(var1.getWidth(), var1.getHeight());
               var4.setImageSize(var1.getSizeInBytes());
               return;
            }

            var4.reset();
         }

      }
   }

   protected Drawable createDrawable(CloseableReference<CloseableImage> var1) {
      Preconditions.checkState(CloseableReference.isValid(var1));
      CloseableImage var4 = (CloseableImage)var1.get();
      this.maybeUpdateDebugOverlay(var4);
      if(this.mDrawableFactories != null) {
         Iterator var2 = this.mDrawableFactories.iterator();

         while(var2.hasNext()) {
            DrawableFactory var3 = (DrawableFactory)var2.next();
            if(var3.supportsImageType(var4)) {
               Drawable var7 = var3.createDrawable(var4);
               if(var7 != null) {
                  return var7;
               }
            }
         }
      }

      Drawable var5 = this.mDefaultDrawableFactory.createDrawable(var4);
      if(var5 != null) {
         return var5;
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Unrecognized image class: ");
         var6.append(var4);
         throw new UnsupportedOperationException(var6.toString());
      }
   }

   protected CloseableReference<CloseableImage> getCachedImage() {
      if(this.mMemoryCache != null) {
         if(this.mCacheKey == null) {
            return null;
         } else {
            CloseableReference var1 = this.mMemoryCache.get(this.mCacheKey);
            if(var1 != null && !((CloseableImage)var1.get()).getQualityInfo().isOfFullQuality()) {
               var1.close();
               return null;
            } else {
               return var1;
            }
         }
      } else {
         return null;
      }
   }

   protected DataSource<CloseableReference<CloseableImage>> getDataSource() {
      if(FLog.isLoggable(2)) {
         FLog.v(TAG, "controller %x: getDataSource", (Object)Integer.valueOf(System.identityHashCode(this)));
      }

      return (DataSource)this.mDataSourceSupplier.get();
   }

   protected int getImageHash(@Nullable CloseableReference<CloseableImage> var1) {
      return var1 != null?var1.getValueHash():0;
   }

   protected ImageInfo getImageInfo(CloseableReference<CloseableImage> var1) {
      Preconditions.checkState(CloseableReference.isValid(var1));
      return (ImageInfo)var1.get();
   }

   protected Resources getResources() {
      return this.mResources;
   }

   public void initialize(Supplier<DataSource<CloseableReference<CloseableImage>>> var1, String var2, CacheKey var3, Object var4) {
      super.initialize(var2, var4);
      this.init(var1);
      this.mCacheKey = var3;
   }

   protected void releaseDrawable(@Nullable Drawable var1) {
      if(var1 instanceof DrawableWithCaches) {
         ((DrawableWithCaches)var1).dropCaches();
      }

   }

   protected void releaseImage(@Nullable CloseableReference<CloseableImage> var1) {
      CloseableReference.closeSafely(var1);
   }

   public void setDrawDebugOverlay(boolean var1) {
      this.mDrawDebugOverlay = var1;
   }

   public void setHierarchy(@Nullable DraweeHierarchy var1) {
      super.setHierarchy(var1);
      this.maybeUpdateDebugOverlay((CloseableImage)null);
   }

   public String toString() {
      return Objects.toStringHelper((Object)this).add("super", super.toString()).add("dataSourceSupplier", this.mDataSourceSupplier).toString();
   }
}
