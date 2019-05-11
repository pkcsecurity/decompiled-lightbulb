package com.facebook.imagepipeline.image;

import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.facebook.imagepipeline.animated.base.AnimatedImageResult;
import com.facebook.imagepipeline.image.CloseableImage;

public class CloseableAnimatedImage extends CloseableImage {

   private AnimatedImageResult mImageResult;


   public CloseableAnimatedImage(AnimatedImageResult var1) {
      this.mImageResult = var1;
   }

   public void close() {
      // $FF: Couldn't be decompiled
   }

   public int getHeight() {
      synchronized(this){}
      boolean var4 = false;

      int var1;
      try {
         var4 = true;
         if(!this.isClosed()) {
            var1 = this.mImageResult.getImage().getHeight();
            var4 = false;
            return var1;
         }

         var4 = false;
      } finally {
         if(var4) {
            ;
         }
      }

      var1 = 0;
      return var1;
   }

   public AnimatedImage getImage() {
      synchronized(this){}
      boolean var3 = false;

      AnimatedImage var1;
      try {
         var3 = true;
         if(!this.isClosed()) {
            var1 = this.mImageResult.getImage();
            var3 = false;
            return var1;
         }

         var3 = false;
      } finally {
         if(var3) {
            ;
         }
      }

      var1 = null;
      return var1;
   }

   public AnimatedImageResult getImageResult() {
      synchronized(this){}

      AnimatedImageResult var1;
      try {
         var1 = this.mImageResult;
      } finally {
         ;
      }

      return var1;
   }

   public int getSizeInBytes() {
      synchronized(this){}
      boolean var4 = false;

      int var1;
      try {
         var4 = true;
         if(!this.isClosed()) {
            var1 = this.mImageResult.getImage().getSizeInBytes();
            var4 = false;
            return var1;
         }

         var4 = false;
      } finally {
         if(var4) {
            ;
         }
      }

      var1 = 0;
      return var1;
   }

   public int getWidth() {
      synchronized(this){}
      boolean var4 = false;

      int var1;
      try {
         var4 = true;
         if(!this.isClosed()) {
            var1 = this.mImageResult.getImage().getWidth();
            var4 = false;
            return var1;
         }

         var4 = false;
      } finally {
         if(var4) {
            ;
         }
      }

      var1 = 0;
      return var1;
   }

   public boolean isClosed() {
      synchronized(this){}
      boolean var4 = false;

      AnimatedImageResult var2;
      try {
         var4 = true;
         var2 = this.mImageResult;
         var4 = false;
      } finally {
         if(var4) {
            ;
         }
      }

      boolean var1;
      if(var2 == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isStateful() {
      return true;
   }
}
