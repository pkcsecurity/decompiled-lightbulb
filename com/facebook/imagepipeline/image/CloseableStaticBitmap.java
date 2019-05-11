package com.facebook.imagepipeline.image;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imageutils.BitmapUtil;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class CloseableStaticBitmap extends CloseableBitmap {

   private volatile Bitmap mBitmap;
   @GuardedBy
   private CloseableReference<Bitmap> mBitmapReference;
   private final QualityInfo mQualityInfo;
   private final int mRotationAngle;


   public CloseableStaticBitmap(Bitmap var1, ResourceReleaser<Bitmap> var2, QualityInfo var3, int var4) {
      this.mBitmap = (Bitmap)Preconditions.checkNotNull(var1);
      this.mBitmapReference = CloseableReference.of(this.mBitmap, (ResourceReleaser)Preconditions.checkNotNull(var2));
      this.mQualityInfo = var3;
      this.mRotationAngle = var4;
   }

   public CloseableStaticBitmap(CloseableReference<Bitmap> var1, QualityInfo var2, int var3) {
      this.mBitmapReference = (CloseableReference)Preconditions.checkNotNull(var1.cloneOrNull());
      this.mBitmap = (Bitmap)this.mBitmapReference.get();
      this.mQualityInfo = var2;
      this.mRotationAngle = var3;
   }

   private CloseableReference<Bitmap> detachBitmapReference() {
      synchronized(this){}

      CloseableReference var1;
      try {
         var1 = this.mBitmapReference;
         this.mBitmapReference = null;
         this.mBitmap = null;
      } finally {
         ;
      }

      return var1;
   }

   private static int getBitmapHeight(@Nullable Bitmap var0) {
      return var0 == null?0:var0.getHeight();
   }

   private static int getBitmapWidth(@Nullable Bitmap var0) {
      return var0 == null?0:var0.getWidth();
   }

   public void close() {
      CloseableReference var1 = this.detachBitmapReference();
      if(var1 != null) {
         var1.close();
      }

   }

   public CloseableReference<Bitmap> convertToBitmapReference() {
      synchronized(this){}

      CloseableReference var1;
      try {
         Preconditions.checkNotNull(this.mBitmapReference, "Cannot convert a closed static bitmap");
         var1 = this.detachBitmapReference();
      } finally {
         ;
      }

      return var1;
   }

   public int getHeight() {
      return this.mRotationAngle != 90 && this.mRotationAngle != 270?getBitmapHeight(this.mBitmap):getBitmapWidth(this.mBitmap);
   }

   public QualityInfo getQualityInfo() {
      return this.mQualityInfo;
   }

   public int getRotationAngle() {
      return this.mRotationAngle;
   }

   public int getSizeInBytes() {
      return BitmapUtil.getSizeInBytes(this.mBitmap);
   }

   public Bitmap getUnderlyingBitmap() {
      return this.mBitmap;
   }

   public int getWidth() {
      return this.mRotationAngle != 90 && this.mRotationAngle != 270?getBitmapWidth(this.mBitmap):getBitmapHeight(this.mBitmap);
   }

   public boolean isClosed() {
      synchronized(this){}
      boolean var4 = false;

      CloseableReference var2;
      try {
         var4 = true;
         var2 = this.mBitmapReference;
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
}
