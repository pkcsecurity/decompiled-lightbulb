package com.facebook.imagepipeline.platform;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.support.v4.util.Pools;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.streams.LimitedInputStream;
import com.facebook.common.streams.TailAppendingInputStream;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.BitmapPool;
import com.facebook.imagepipeline.platform.PlatformDecoder;
import com.facebook.imageutils.BitmapUtil;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.annotation.concurrent.ThreadSafe;

@TargetApi(21)
@ThreadSafe
public class ArtDecoder implements PlatformDecoder {

   private static final int DECODE_BUFFER_SIZE = 16384;
   private static final byte[] EOI_TAIL = new byte[]{(byte)-1, (byte)-39};
   private final BitmapPool mBitmapPool;
   @VisibleForTesting
   final Pools.SynchronizedPool<ByteBuffer> mDecodeBuffers;


   public ArtDecoder(BitmapPool var1, int var2, Pools.SynchronizedPool var3) {
      this.mBitmapPool = var1;
      this.mDecodeBuffers = var3;

      for(int var4 = 0; var4 < var2; ++var4) {
         this.mDecodeBuffers.release(ByteBuffer.allocate(16384));
      }

   }

   private static Options getDecodeOptionsForStream(EncodedImage var0, Config var1) {
      Options var2 = new Options();
      var2.inSampleSize = var0.getSampleSize();
      var2.inJustDecodeBounds = true;
      BitmapFactory.decodeStream(var0.getInputStream(), (Rect)null, var2);
      if(var2.outWidth != -1 && var2.outHeight != -1) {
         var2.inJustDecodeBounds = false;
         var2.inDither = true;
         var2.inPreferredConfig = var1;
         var2.inMutable = true;
         return var2;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public CloseableReference<Bitmap> decodeFromEncodedImage(EncodedImage var1, Config var2) {
      Options var5 = getDecodeOptionsForStream(var1, var2);
      boolean var3;
      if(var5.inPreferredConfig != Config.ARGB_8888) {
         var3 = true;
      } else {
         var3 = false;
      }

      try {
         CloseableReference var6 = this.decodeStaticImageFromStream(var1.getInputStream(), var5);
         return var6;
      } catch (RuntimeException var4) {
         if(var3) {
            return this.decodeFromEncodedImage(var1, Config.ARGB_8888);
         } else {
            throw var4;
         }
      }
   }

   public CloseableReference<Bitmap> decodeJPEGFromEncodedImage(EncodedImage var1, Config var2, int var3) {
      boolean var4 = var1.isCompleteAt(var3);
      Options var6 = getDecodeOptionsForStream(var1, var2);
      InputStream var5 = var1.getInputStream();
      Preconditions.checkNotNull(var5);
      Object var8 = var5;
      if(var1.getSize() > var3) {
         var8 = new LimitedInputStream(var5, var3);
      }

      if(!var4) {
         var8 = new TailAppendingInputStream((InputStream)var8, EOI_TAIL);
      }

      boolean var9;
      if(var6.inPreferredConfig != Config.ARGB_8888) {
         var9 = true;
      } else {
         var9 = false;
      }

      try {
         CloseableReference var10 = this.decodeStaticImageFromStream((InputStream)var8, var6);
         return var10;
      } catch (RuntimeException var7) {
         if(var9) {
            return this.decodeFromEncodedImage(var1, Config.ARGB_8888);
         } else {
            throw var7;
         }
      }
   }

   protected CloseableReference<Bitmap> decodeStaticImageFromStream(InputStream var1, Options var2) {
      Preconditions.checkNotNull(var1);
      int var3 = BitmapUtil.getSizeInByteForBitmap(var2.outWidth, var2.outHeight, var2.inPreferredConfig);
      Bitmap var6 = (Bitmap)this.mBitmapPool.get(var3);
      if(var6 == null) {
         throw new NullPointerException("BitmapPool.get returned null");
      } else {
         var2.inBitmap = var6;
         ByteBuffer var5 = (ByteBuffer)this.mDecodeBuffers.acquire();
         ByteBuffer var4 = var5;
         if(var5 == null) {
            var4 = ByteBuffer.allocate(16384);
         }

         Bitmap var11;
         try {
            var2.inTempStorage = var4.array();
            var11 = BitmapFactory.decodeStream(var1, (Rect)null, var2);
         } catch (RuntimeException var9) {
            this.mBitmapPool.release(var6);
            throw var9;
         } finally {
            this.mDecodeBuffers.release(var4);
         }

         if(var6 != var11) {
            this.mBitmapPool.release(var6);
            var11.recycle();
            throw new IllegalStateException();
         } else {
            return CloseableReference.of(var11, this.mBitmapPool);
         }
      }
   }
}
