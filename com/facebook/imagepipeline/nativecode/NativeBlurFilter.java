package com.facebook.imagepipeline.nativecode;

import android.graphics.Bitmap;
import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.nativecode.ImagePipelineNativeLoader;

@DoNotStrip
public class NativeBlurFilter {

   static {
      ImagePipelineNativeLoader.load();
   }

   public static void iterativeBoxBlur(Bitmap var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      boolean var4 = false;
      boolean var3;
      if(var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      var3 = var4;
      if(var2 > 0) {
         var3 = true;
      }

      Preconditions.checkArgument(var3);
      nativeIterativeBoxBlur(var0, var1, var2);
   }

   @DoNotStrip
   private static native void nativeIterativeBoxBlur(Bitmap var0, int var1, int var2);
}
