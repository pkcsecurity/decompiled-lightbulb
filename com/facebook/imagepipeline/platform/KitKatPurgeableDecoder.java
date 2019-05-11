package com.facebook.imagepipeline.platform;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.memory.FlexByteArrayPool;
import com.facebook.imagepipeline.platform.DalvikPurgeableDecoder;
import javax.annotation.concurrent.ThreadSafe;

@TargetApi(19)
@ThreadSafe
public class KitKatPurgeableDecoder extends DalvikPurgeableDecoder {

   private final FlexByteArrayPool mFlexByteArrayPool;


   public KitKatPurgeableDecoder(FlexByteArrayPool var1) {
      this.mFlexByteArrayPool = var1;
   }

   private static void putEOI(byte[] var0, int var1) {
      var0[var1] = -1;
      var0[var1 + 1] = -39;
   }

   protected Bitmap decodeByteArrayAsPurgeable(CloseableReference<PooledByteBuffer> var1, Options var2) {
      PooledByteBuffer var4 = (PooledByteBuffer)var1.get();
      int var3 = var4.size();
      var1 = this.mFlexByteArrayPool.get(var3);

      Bitmap var8;
      try {
         byte[] var5 = (byte[])var1.get();
         var4.read(0, var5, 0, var3);
         var8 = (Bitmap)Preconditions.checkNotNull(BitmapFactory.decodeByteArray(var5, 0, var3, var2), "BitmapFactory returned null");
      } finally {
         CloseableReference.closeSafely(var1);
      }

      return var8;
   }

   protected Bitmap decodeJPEGByteArrayAsPurgeable(CloseableReference<PooledByteBuffer> param1, int param2, Options param3) {
      // $FF: Couldn't be decompiled
   }
}
