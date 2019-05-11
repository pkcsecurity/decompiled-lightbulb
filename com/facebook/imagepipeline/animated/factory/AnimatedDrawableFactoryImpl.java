package com.facebook.imagepipeline.animated.factory;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import com.facebook.common.time.MonotonicClock;
import com.facebook.imagepipeline.animated.base.AnimatedDrawable;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableDiagnostics;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableOptions;
import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.facebook.imagepipeline.animated.base.AnimatedImageResult;
import com.facebook.imagepipeline.animated.factory.AnimatedDrawableFactory;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableBackendProvider;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableCachingBackendImpl;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableCachingBackendImplProvider;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableDiagnosticsImpl;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableDiagnosticsNoop;
import com.facebook.imagepipeline.animated.util.AnimatedDrawableUtil;
import com.facebook.imagepipeline.image.CloseableAnimatedImage;
import com.facebook.imagepipeline.image.CloseableImage;
import java.util.concurrent.ScheduledExecutorService;

public class AnimatedDrawableFactoryImpl implements AnimatedDrawableFactory {

   private final AnimatedDrawableBackendProvider mAnimatedDrawableBackendProvider;
   private final AnimatedDrawableCachingBackendImplProvider mAnimatedDrawableCachingBackendProvider;
   private final AnimatedDrawableUtil mAnimatedDrawableUtil;
   private final MonotonicClock mMonotonicClock;
   private final Resources mResources;
   private final ScheduledExecutorService mScheduledExecutorServiceForUiThread;


   public AnimatedDrawableFactoryImpl(AnimatedDrawableBackendProvider var1, AnimatedDrawableCachingBackendImplProvider var2, AnimatedDrawableUtil var3, ScheduledExecutorService var4, Resources var5) {
      this.mAnimatedDrawableBackendProvider = var1;
      this.mAnimatedDrawableCachingBackendProvider = var2;
      this.mAnimatedDrawableUtil = var3;
      this.mScheduledExecutorServiceForUiThread = var4;
      this.mMonotonicClock = new MonotonicClock() {
         public long now() {
            return SystemClock.uptimeMillis();
         }
      };
      this.mResources = var5;
   }

   private AnimatedDrawable create(AnimatedImageResult var1, AnimatedDrawableOptions var2) {
      AnimatedImage var3 = var1.getImage();
      Rect var4 = new Rect(0, 0, var3.getWidth(), var3.getHeight());
      return this.createAnimatedDrawable(var2, this.mAnimatedDrawableBackendProvider.get(var1, var4));
   }

   private AnimatedDrawable createAnimatedDrawable(AnimatedDrawableOptions var1, AnimatedDrawableBackend var2) {
      DisplayMetrics var3 = this.mResources.getDisplayMetrics();
      AnimatedDrawableCachingBackendImpl var5 = this.mAnimatedDrawableCachingBackendProvider.get(var2, var1);
      Object var4;
      if(var1.enableDebugging) {
         var4 = new AnimatedDrawableDiagnosticsImpl(this.mAnimatedDrawableUtil, var3);
      } else {
         var4 = AnimatedDrawableDiagnosticsNoop.getInstance();
      }

      return new AnimatedDrawable(this.mScheduledExecutorServiceForUiThread, var5, (AnimatedDrawableDiagnostics)var4, this.mMonotonicClock);
   }

   private AnimatedImageResult getImageIfCloseableAnimatedImage(CloseableImage var1) {
      return var1 instanceof CloseableAnimatedImage?((CloseableAnimatedImage)var1).getImageResult():null;
   }

   public Drawable create(CloseableImage var1) {
      if(var1 instanceof CloseableAnimatedImage) {
         return this.create(((CloseableAnimatedImage)var1).getImageResult(), AnimatedDrawableOptions.DEFAULTS);
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Unrecognized image class: ");
         var2.append(var1);
         throw new UnsupportedOperationException(var2.toString());
      }
   }
}
