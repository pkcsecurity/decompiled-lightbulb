package com.facebook.imagepipeline.image;

import android.util.Pair;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferInputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.SharedReference;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imageutils.JfifUtil;
import com.facebook.imageutils.WebpUtil;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class EncodedImage implements Closeable {

   public static final int DEFAULT_SAMPLE_SIZE = 1;
   public static final int UNKNOWN_HEIGHT = -1;
   public static final int UNKNOWN_ROTATION_ANGLE = -1;
   public static final int UNKNOWN_STREAM_SIZE = -1;
   public static final int UNKNOWN_WIDTH = -1;
   @Nullable
   private CacheKey mEncodedCacheKey;
   private int mHeight;
   private ImageFormat mImageFormat;
   @Nullable
   private final Supplier<FileInputStream> mInputStreamSupplier;
   @Nullable
   private final CloseableReference<PooledByteBuffer> mPooledByteBufferRef;
   private int mRotationAngle;
   private int mSampleSize;
   private int mStreamSize;
   private int mWidth;


   public EncodedImage(Supplier<FileInputStream> var1) {
      this.mImageFormat = ImageFormat.UNKNOWN;
      this.mRotationAngle = -1;
      this.mWidth = -1;
      this.mHeight = -1;
      this.mSampleSize = 1;
      this.mStreamSize = -1;
      Preconditions.checkNotNull(var1);
      this.mPooledByteBufferRef = null;
      this.mInputStreamSupplier = var1;
   }

   public EncodedImage(Supplier<FileInputStream> var1, int var2) {
      this(var1);
      this.mStreamSize = var2;
   }

   public EncodedImage(CloseableReference<PooledByteBuffer> var1) {
      this.mImageFormat = ImageFormat.UNKNOWN;
      this.mRotationAngle = -1;
      this.mWidth = -1;
      this.mHeight = -1;
      this.mSampleSize = 1;
      this.mStreamSize = -1;
      Preconditions.checkArgument(CloseableReference.isValid(var1));
      this.mPooledByteBufferRef = var1.clone();
      this.mInputStreamSupplier = null;
   }

   public static EncodedImage cloneOrNull(EncodedImage var0) {
      return var0 != null?var0.cloneOrNull():null;
   }

   public static void closeSafely(@Nullable EncodedImage var0) {
      if(var0 != null) {
         var0.close();
      }

   }

   public static boolean isMetaDataAvailable(EncodedImage var0) {
      return var0.mRotationAngle >= 0 && var0.mWidth >= 0 && var0.mHeight >= 0;
   }

   public static boolean isValid(@Nullable EncodedImage var0) {
      return var0 != null && var0.isValid();
   }

   private Pair<Integer, Integer> readImageSize() {
      // $FF: Couldn't be decompiled
   }

   private Pair<Integer, Integer> readWebPImageSize() {
      Pair var1 = WebpUtil.getSize(this.getInputStream());
      if(var1 != null) {
         this.mWidth = ((Integer)var1.first).intValue();
         this.mHeight = ((Integer)var1.second).intValue();
      }

      return var1;
   }

   public EncodedImage cloneOrNull() {
      EncodedImage var1;
      if(this.mInputStreamSupplier != null) {
         var1 = new EncodedImage(this.mInputStreamSupplier, this.mStreamSize);
      } else {
         CloseableReference var2 = CloseableReference.cloneOrNull(this.mPooledByteBufferRef);
         if(var2 == null) {
            var1 = null;
         } else {
            try {
               var1 = new EncodedImage(var2);
            } finally {
               CloseableReference.closeSafely(var2);
            }
         }
      }

      if(var1 != null) {
         var1.copyMetaDataFrom(this);
      }

      return var1;
   }

   public void close() {
      CloseableReference.closeSafely(this.mPooledByteBufferRef);
   }

   public void copyMetaDataFrom(EncodedImage var1) {
      this.mImageFormat = var1.getImageFormat();
      this.mWidth = var1.getWidth();
      this.mHeight = var1.getHeight();
      this.mRotationAngle = var1.getRotationAngle();
      this.mSampleSize = var1.getSampleSize();
      this.mStreamSize = var1.getSize();
      this.mEncodedCacheKey = var1.getEncodedCacheKey();
   }

   public CloseableReference<PooledByteBuffer> getByteBufferRef() {
      return CloseableReference.cloneOrNull(this.mPooledByteBufferRef);
   }

   @Nullable
   public CacheKey getEncodedCacheKey() {
      return this.mEncodedCacheKey;
   }

   public int getHeight() {
      return this.mHeight;
   }

   public ImageFormat getImageFormat() {
      return this.mImageFormat;
   }

   public InputStream getInputStream() {
      if(this.mInputStreamSupplier != null) {
         return (InputStream)this.mInputStreamSupplier.get();
      } else {
         CloseableReference var1 = CloseableReference.cloneOrNull(this.mPooledByteBufferRef);
         if(var1 != null) {
            PooledByteBufferInputStream var2;
            try {
               var2 = new PooledByteBufferInputStream((PooledByteBuffer)var1.get());
            } finally {
               CloseableReference.closeSafely(var1);
            }

            return var2;
         } else {
            return null;
         }
      }
   }

   public int getRotationAngle() {
      return this.mRotationAngle;
   }

   public int getSampleSize() {
      return this.mSampleSize;
   }

   public int getSize() {
      return this.mPooledByteBufferRef != null && this.mPooledByteBufferRef.get() != null?((PooledByteBuffer)this.mPooledByteBufferRef.get()).size():this.mStreamSize;
   }

   @VisibleForTesting
   public SharedReference<PooledByteBuffer> getUnderlyingReferenceTestOnly() {
      synchronized(this){}
      boolean var3 = false;

      SharedReference var1;
      try {
         var3 = true;
         if(this.mPooledByteBufferRef != null) {
            var1 = this.mPooledByteBufferRef.getUnderlyingReferenceTestOnly();
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

   public int getWidth() {
      return this.mWidth;
   }

   public boolean isCompleteAt(int var1) {
      if(this.mImageFormat != DefaultImageFormats.JPEG) {
         return true;
      } else if(this.mInputStreamSupplier != null) {
         return true;
      } else {
         Preconditions.checkNotNull(this.mPooledByteBufferRef);
         PooledByteBuffer var2 = (PooledByteBuffer)this.mPooledByteBufferRef.get();
         return var2.read(var1 - 2) == -1 && var2.read(var1 - 1) == -39;
      }
   }

   public boolean isValid() {
      synchronized(this){}
      boolean var4 = false;

      boolean var1;
      label46: {
         Supplier var2;
         try {
            var4 = true;
            if(CloseableReference.isValid(this.mPooledByteBufferRef)) {
               var4 = false;
               break label46;
            }

            var2 = this.mInputStreamSupplier;
            var4 = false;
         } finally {
            if(var4) {
               ;
            }
         }

         if(var2 == null) {
            var1 = false;
            return var1;
         }
      }

      var1 = true;
      return var1;
   }

   public void parseMetaData() {
      ImageFormat var2 = ImageFormatChecker.getImageFormat_WrapIOException(this.getInputStream());
      this.mImageFormat = var2;
      Pair var1;
      if(DefaultImageFormats.isWebpFormat(var2)) {
         var1 = this.readWebPImageSize();
      } else {
         var1 = this.readImageSize();
      }

      if(var2 == DefaultImageFormats.JPEG && this.mRotationAngle == -1) {
         if(var1 != null) {
            this.mRotationAngle = JfifUtil.getAutoRotateAngleFromOrientation(JfifUtil.getOrientation(this.getInputStream()));
            return;
         }
      } else {
         this.mRotationAngle = 0;
      }

   }

   public void setEncodedCacheKey(@Nullable CacheKey var1) {
      this.mEncodedCacheKey = var1;
   }

   public void setHeight(int var1) {
      this.mHeight = var1;
   }

   public void setImageFormat(ImageFormat var1) {
      this.mImageFormat = var1;
   }

   public void setRotationAngle(int var1) {
      this.mRotationAngle = var1;
   }

   public void setSampleSize(int var1) {
      this.mSampleSize = var1;
   }

   public void setStreamSize(int var1) {
      this.mStreamSize = var1;
   }

   public void setWidth(int var1) {
      this.mWidth = var1;
   }
}
