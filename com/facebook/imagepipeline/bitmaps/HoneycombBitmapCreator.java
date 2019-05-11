package com.facebook.imagepipeline.bitmaps;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import com.facebook.common.webp.BitmapCreator;
import com.facebook.imagepipeline.bitmaps.EmptyJpegGenerator;
import com.facebook.imagepipeline.memory.FlexByteArrayPool;
import com.facebook.imagepipeline.memory.PoolFactory;

public class HoneycombBitmapCreator implements BitmapCreator {

   private final FlexByteArrayPool mFlexByteArrayPool;
   private final EmptyJpegGenerator mJpegGenerator;


   public HoneycombBitmapCreator(PoolFactory var1) {
      this.mFlexByteArrayPool = var1.getFlexByteArrayPool();
      this.mJpegGenerator = new EmptyJpegGenerator(var1.getPooledByteBufferFactory());
   }

   private static Options getBitmapFactoryOptions(int var0, Config var1) {
      Options var2 = new Options();
      var2.inDither = true;
      var2.inPreferredConfig = var1;
      var2.inPurgeable = true;
      var2.inInputShareable = true;
      var2.inSampleSize = var0;
      if(VERSION.SDK_INT >= 11) {
         var2.inMutable = true;
      }

      return var2;
   }

   @TargetApi(12)
   public Bitmap createNakedBitmap(int param1, int param2, Config param3) {
      // $FF: Couldn't be decompiled
   }
}
