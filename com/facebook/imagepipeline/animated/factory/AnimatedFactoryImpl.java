package com.facebook.imagepipeline.animated.factory;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import com.facebook.common.executors.DefaultSerialExecutorService;
import com.facebook.common.executors.SerialExecutorService;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.time.MonotonicClock;
import com.facebook.common.time.RealtimeSinceBootClock;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableOptions;
import com.facebook.imagepipeline.animated.base.AnimatedImageResult;
import com.facebook.imagepipeline.animated.factory.AnimatedDrawableFactory;
import com.facebook.imagepipeline.animated.factory.AnimatedDrawableFactoryImpl;
import com.facebook.imagepipeline.animated.factory.AnimatedFactory;
import com.facebook.imagepipeline.animated.factory.AnimatedImageFactory;
import com.facebook.imagepipeline.animated.factory.AnimatedImageFactoryImpl;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableBackendImpl;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableBackendProvider;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableCachingBackendImpl;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableCachingBackendImplProvider;
import com.facebook.imagepipeline.animated.util.AnimatedDrawableUtil;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.core.ExecutorSupplier;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.concurrent.NotThreadSafe;

@DoNotStrip
@NotThreadSafe
public class AnimatedFactoryImpl implements AnimatedFactory {

   private AnimatedDrawableBackendProvider mAnimatedDrawableBackendProvider;
   private AnimatedDrawableFactory mAnimatedDrawableFactory;
   private AnimatedDrawableUtil mAnimatedDrawableUtil;
   private AnimatedImageFactory mAnimatedImageFactory;
   private ExecutorSupplier mExecutorSupplier;
   private PlatformBitmapFactory mPlatformBitmapFactory;


   @DoNotStrip
   public AnimatedFactoryImpl(PlatformBitmapFactory var1, ExecutorSupplier var2) {
      this.mPlatformBitmapFactory = var1;
      this.mExecutorSupplier = var2;
   }

   private AnimatedDrawableFactory buildAnimatedDrawableFactory(final SerialExecutorService var1, final ActivityManager var2, final AnimatedDrawableUtil var3, AnimatedDrawableBackendProvider var4, ScheduledExecutorService var5, final MonotonicClock var6, Resources var7) {
      return this.createAnimatedDrawableFactory(var4, new AnimatedDrawableCachingBackendImplProvider() {
         public AnimatedDrawableCachingBackendImpl get(AnimatedDrawableBackend var1x, AnimatedDrawableOptions var2x) {
            return new AnimatedDrawableCachingBackendImpl(var1, var2, var3, var6, var1x, var2x);
         }
      }, var3, var5, var7);
   }

   private AnimatedImageFactory buildAnimatedImageFactory() {
      return new AnimatedImageFactoryImpl(new AnimatedDrawableBackendProvider() {
         public AnimatedDrawableBackend get(AnimatedImageResult var1, Rect var2) {
            return new AnimatedDrawableBackendImpl(AnimatedFactoryImpl.this.getAnimatedDrawableUtil(), var1, var2);
         }
      }, this.mPlatformBitmapFactory);
   }

   private AnimatedDrawableBackendProvider getAnimatedDrawableBackendProvider() {
      if(this.mAnimatedDrawableBackendProvider == null) {
         this.mAnimatedDrawableBackendProvider = new AnimatedDrawableBackendProvider() {
            public AnimatedDrawableBackend get(AnimatedImageResult var1, Rect var2) {
               return new AnimatedDrawableBackendImpl(AnimatedFactoryImpl.this.getAnimatedDrawableUtil(), var1, var2);
            }
         };
      }

      return this.mAnimatedDrawableBackendProvider;
   }

   private AnimatedDrawableUtil getAnimatedDrawableUtil() {
      if(this.mAnimatedDrawableUtil == null) {
         this.mAnimatedDrawableUtil = new AnimatedDrawableUtil();
      }

      return this.mAnimatedDrawableUtil;
   }

   protected AnimatedDrawableFactory createAnimatedDrawableFactory(AnimatedDrawableBackendProvider var1, AnimatedDrawableCachingBackendImplProvider var2, AnimatedDrawableUtil var3, ScheduledExecutorService var4, Resources var5) {
      return new AnimatedDrawableFactoryImpl(var1, var2, var3, var4, var5);
   }

   public AnimatedDrawableFactory getAnimatedDrawableFactory(Context var1) {
      if(this.mAnimatedDrawableFactory == null) {
         this.mAnimatedDrawableFactory = this.buildAnimatedDrawableFactory(new DefaultSerialExecutorService(this.mExecutorSupplier.forDecode()), (ActivityManager)var1.getSystemService("activity"), this.getAnimatedDrawableUtil(), this.getAnimatedDrawableBackendProvider(), UiThreadImmediateExecutorService.getInstance(), RealtimeSinceBootClock.get(), var1.getResources());
      }

      return this.mAnimatedDrawableFactory;
   }

   public AnimatedImageFactory getAnimatedImageFactory() {
      if(this.mAnimatedImageFactory == null) {
         this.mAnimatedImageFactory = this.buildAnimatedImageFactory();
      }

      return this.mAnimatedImageFactory;
   }
}
