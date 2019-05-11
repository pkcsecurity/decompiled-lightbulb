package com.facebook.imagepipeline.animated.base;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.facebook.imagepipeline.animated.base.AnimatedImageResultBuilder;
import java.util.List;
import javax.annotation.Nullable;

public class AnimatedImageResult {

   @Nullable
   private List<CloseableReference<Bitmap>> mDecodedFrames;
   private final int mFrameForPreview;
   private final AnimatedImage mImage;
   @Nullable
   private CloseableReference<Bitmap> mPreviewBitmap;


   private AnimatedImageResult(AnimatedImage var1) {
      this.mImage = (AnimatedImage)Preconditions.checkNotNull(var1);
      this.mFrameForPreview = 0;
   }

   AnimatedImageResult(AnimatedImageResultBuilder var1) {
      this.mImage = (AnimatedImage)Preconditions.checkNotNull(var1.getImage());
      this.mFrameForPreview = var1.getFrameForPreview();
      this.mPreviewBitmap = var1.getPreviewBitmap();
      this.mDecodedFrames = var1.getDecodedFrames();
   }

   public static AnimatedImageResult forAnimatedImage(AnimatedImage var0) {
      return new AnimatedImageResult(var0);
   }

   public static AnimatedImageResultBuilder newBuilder(AnimatedImage var0) {
      return new AnimatedImageResultBuilder(var0);
   }

   public void dispose() {
      synchronized(this){}

      try {
         CloseableReference.closeSafely(this.mPreviewBitmap);
         this.mPreviewBitmap = null;
         CloseableReference.closeSafely((Iterable)this.mDecodedFrames);
         this.mDecodedFrames = null;
      } finally {
         ;
      }

   }

   @Nullable
   public CloseableReference<Bitmap> getDecodedFrame(int var1) {
      synchronized(this){}

      CloseableReference var2;
      try {
         if(this.mDecodedFrames == null) {
            return null;
         }

         var2 = CloseableReference.cloneOrNull((CloseableReference)this.mDecodedFrames.get(var1));
      } finally {
         ;
      }

      return var2;
   }

   public int getFrameForPreview() {
      return this.mFrameForPreview;
   }

   public AnimatedImage getImage() {
      return this.mImage;
   }

   public CloseableReference<Bitmap> getPreviewBitmap() {
      synchronized(this){}

      CloseableReference var1;
      try {
         var1 = CloseableReference.cloneOrNull(this.mPreviewBitmap);
      } finally {
         ;
      }

      return var1;
   }

   public boolean hasDecodedFrame(int var1) {
      synchronized(this){}
      boolean var5 = false;

      boolean var2;
      label45: {
         Object var3;
         try {
            var5 = true;
            if(this.mDecodedFrames == null) {
               var5 = false;
               break label45;
            }

            var3 = this.mDecodedFrames.get(var1);
            var5 = false;
         } finally {
            if(var5) {
               ;
            }
         }

         if(var3 != null) {
            var2 = true;
            return var2;
         }
      }

      var2 = false;
      return var2;
   }
}
