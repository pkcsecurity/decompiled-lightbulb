package com.facebook.imagepipeline.nativecode;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.nativecode.ImagePipelineNativeLoader;
import com.facebook.imageutils.BitmapUtil;
import java.nio.ByteBuffer;

@DoNotStrip
public class Bitmaps {

   static {
      ImagePipelineNativeLoader.load();
   }

   public static void copyBitmap(Bitmap var0, Bitmap var1) {
      Config var4 = var1.getConfig();
      Config var5 = var0.getConfig();
      boolean var3 = false;
      boolean var2;
      if(var4 == var5) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      Preconditions.checkArgument(var0.isMutable());
      if(var0.getWidth() == var1.getWidth()) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      var2 = var3;
      if(var0.getHeight() == var1.getHeight()) {
         var2 = true;
      }

      Preconditions.checkArgument(var2);
      nativeCopyBitmap(var0, var0.getRowBytes(), var1, var1.getRowBytes(), var0.getHeight());
   }

   public static ByteBuffer getByteBuffer(Bitmap var0, long var1, long var3) {
      Preconditions.checkNotNull(var0);
      return nativeGetByteBuffer(var0, var1, var3);
   }

   @DoNotStrip
   private static native void nativeCopyBitmap(Bitmap var0, int var1, Bitmap var2, int var3, int var4);

   @DoNotStrip
   private static native ByteBuffer nativeGetByteBuffer(Bitmap var0, long var1, long var3);

   @DoNotStrip
   private static native void nativePinBitmap(Bitmap var0);

   @DoNotStrip
   private static native void nativeReleaseByteBuffer(Bitmap var0);

   public static void pinBitmap(Bitmap var0) {
      Preconditions.checkNotNull(var0);
      nativePinBitmap(var0);
   }

   @TargetApi(19)
   public static void reconfigureBitmap(Bitmap var0, int var1, int var2, Config var3) {
      boolean var4;
      if(var0.getAllocationByteCount() >= var1 * var2 * BitmapUtil.getPixelSizeForBitmapConfig(var3)) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      var0.reconfigure(var1, var2, var3);
   }

   public static void releaseByteBuffer(Bitmap var0) {
      Preconditions.checkNotNull(var0);
      nativeReleaseByteBuffer(var0);
   }
}
