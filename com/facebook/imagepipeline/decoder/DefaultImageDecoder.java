package com.facebook.imagepipeline.decoder;

import android.graphics.Bitmap.Config;
import com.facebook.common.internal.Closeables;
import com.facebook.common.references.CloseableReference;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imagepipeline.animated.factory.AnimatedImageFactory;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.platform.PlatformDecoder;
import java.io.InputStream;
import java.util.Map;
import javax.annotation.Nullable;

public class DefaultImageDecoder implements ImageDecoder {

   private final AnimatedImageFactory mAnimatedImageFactory;
   private final Config mBitmapConfig;
   @Nullable
   private final Map<ImageFormat, ImageDecoder> mCustomDecoders;
   private final ImageDecoder mDefaultDecoder;
   private final PlatformDecoder mPlatformDecoder;


   public DefaultImageDecoder(AnimatedImageFactory var1, PlatformDecoder var2, Config var3) {
      this(var1, var2, var3, (Map)null);
   }

   public DefaultImageDecoder(AnimatedImageFactory var1, PlatformDecoder var2, Config var3, @Nullable Map<ImageFormat, ImageDecoder> var4) {
      this.mDefaultDecoder = new ImageDecoder() {
         public CloseableImage decode(EncodedImage var1, int var2, QualityInfo var3, ImageDecodeOptions var4) {
            ImageFormat var5 = var1.getImageFormat();
            if(var5 == DefaultImageFormats.JPEG) {
               return DefaultImageDecoder.this.decodeJpeg(var1, var2, var3, var4);
            } else if(var5 == DefaultImageFormats.GIF) {
               return DefaultImageDecoder.this.decodeGif(var1, var4);
            } else if(var5 == DefaultImageFormats.WEBP_ANIMATED) {
               return DefaultImageDecoder.this.decodeAnimatedWebp(var1, var4);
            } else if(var5 == ImageFormat.UNKNOWN) {
               throw new IllegalArgumentException("unknown image format");
            } else {
               return DefaultImageDecoder.this.decodeStaticImage(var1, var4);
            }
         }
      };
      this.mAnimatedImageFactory = var1;
      this.mBitmapConfig = var3;
      this.mPlatformDecoder = var2;
      this.mCustomDecoders = var4;
   }

   public CloseableImage decode(EncodedImage var1, int var2, QualityInfo var3, ImageDecodeOptions var4) {
      if(var4.customImageDecoder != null) {
         return var4.customImageDecoder.decode(var1, var2, var3, var4);
      } else {
         ImageFormat var5;
         label20: {
            ImageFormat var6 = var1.getImageFormat();
            if(var6 != null) {
               var5 = var6;
               if(var6 != ImageFormat.UNKNOWN) {
                  break label20;
               }
            }

            var5 = ImageFormatChecker.getImageFormat_WrapIOException(var1.getInputStream());
            var1.setImageFormat(var5);
         }

         if(this.mCustomDecoders != null) {
            ImageDecoder var7 = (ImageDecoder)this.mCustomDecoders.get(var5);
            if(var7 != null) {
               return var7.decode(var1, var2, var3, var4);
            }
         }

         return this.mDefaultDecoder.decode(var1, var2, var3, var4);
      }
   }

   public CloseableImage decodeAnimatedWebp(EncodedImage var1, ImageDecodeOptions var2) {
      return this.mAnimatedImageFactory.decodeWebP(var1, var2, this.mBitmapConfig);
   }

   public CloseableImage decodeGif(EncodedImage var1, ImageDecodeOptions var2) {
      InputStream var3 = var1.getInputStream();
      if(var3 == null) {
         return null;
      } else {
         CloseableImage var6;
         try {
            if(var2.forceStaticImage || this.mAnimatedImageFactory == null) {
               CloseableStaticBitmap var7 = this.decodeStaticImage(var1, var2);
               return var7;
            }

            var6 = this.mAnimatedImageFactory.decodeGif(var1, var2, this.mBitmapConfig);
         } finally {
            Closeables.closeQuietly(var3);
         }

         return var6;
      }
   }

   public CloseableStaticBitmap decodeJpeg(EncodedImage var1, int var2, QualityInfo var3, ImageDecodeOptions var4) {
      CloseableReference var8 = this.mPlatformDecoder.decodeJPEGFromEncodedImage(var1, var4.bitmapConfig, var2);

      CloseableStaticBitmap var7;
      try {
         var7 = new CloseableStaticBitmap(var8, var3, var1.getRotationAngle());
      } finally {
         var8.close();
      }

      return var7;
   }

   public CloseableStaticBitmap decodeStaticImage(EncodedImage var1, ImageDecodeOptions var2) {
      CloseableReference var6 = this.mPlatformDecoder.decodeFromEncodedImage(var1, var2.bitmapConfig);

      CloseableStaticBitmap var5;
      try {
         var5 = new CloseableStaticBitmap(var6, ImmutableQualityInfo.FULL_QUALITY, var1.getRotationAngle());
      } finally {
         var6.close();
      }

      return var5;
   }
}
