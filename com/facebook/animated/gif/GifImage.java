package com.facebook.animated.gif;

import com.facebook.animated.gif.GifFrame;
import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.soloader.SoLoaderShim;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableFrameInfo;
import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.facebook.imagepipeline.animated.factory.AnimatedImageDecoder;
import java.nio.ByteBuffer;
import javax.annotation.concurrent.ThreadSafe;

@DoNotStrip
@ThreadSafe
public class GifImage implements AnimatedImage, AnimatedImageDecoder {

   private static final int LOOP_COUNT_FOREVER = 0;
   private static final int LOOP_COUNT_MISSING = -1;
   private static volatile boolean sInitialized;
   @DoNotStrip
   private long mNativeContext;


   @DoNotStrip
   public GifImage() {}

   @DoNotStrip
   GifImage(long var1) {
      this.mNativeContext = var1;
   }

   public static GifImage create(long var0, int var2) {
      ensure();
      boolean var3;
      if(var0 != 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      return nativeCreateFromNativeMemory(var0, var2);
   }

   public static GifImage create(byte[] var0) {
      ensure();
      Preconditions.checkNotNull(var0);
      ByteBuffer var1 = ByteBuffer.allocateDirect(var0.length);
      var1.put(var0);
      var1.rewind();
      return nativeCreateFromDirectByteBuffer(var1);
   }

   private static void ensure() {
      synchronized(GifImage.class){}

      try {
         if(!sInitialized) {
            sInitialized = true;
            SoLoaderShim.loadLibrary("gifimage");
         }
      } finally {
         ;
      }

   }

   private static AnimatedDrawableFrameInfo.DisposalMethod fromGifDisposalMethod(int var0) {
      return var0 == 0?AnimatedDrawableFrameInfo.DisposalMethod.DISPOSE_DO_NOT:(var0 == 1?AnimatedDrawableFrameInfo.DisposalMethod.DISPOSE_DO_NOT:(var0 == 2?AnimatedDrawableFrameInfo.DisposalMethod.DISPOSE_TO_BACKGROUND:(var0 == 3?AnimatedDrawableFrameInfo.DisposalMethod.DISPOSE_TO_PREVIOUS:AnimatedDrawableFrameInfo.DisposalMethod.DISPOSE_DO_NOT)));
   }

   private static native GifImage nativeCreateFromDirectByteBuffer(ByteBuffer var0);

   private static native GifImage nativeCreateFromNativeMemory(long var0, int var2);

   private native void nativeDispose();

   private native void nativeFinalize();

   private native int nativeGetDuration();

   private native GifFrame nativeGetFrame(int var1);

   private native int nativeGetFrameCount();

   private native int[] nativeGetFrameDurations();

   private native int nativeGetHeight();

   private native int nativeGetLoopCount();

   private native int nativeGetSizeInBytes();

   private native int nativeGetWidth();

   public AnimatedImage decode(long var1, int var3) {
      return create(var1, var3);
   }

   public void dispose() {
      this.nativeDispose();
   }

   public boolean doesRenderSupportScaling() {
      return false;
   }

   protected void finalize() {
      this.nativeFinalize();
   }

   public int getDuration() {
      return this.nativeGetDuration();
   }

   public GifFrame getFrame(int var1) {
      return this.nativeGetFrame(var1);
   }

   public int getFrameCount() {
      return this.nativeGetFrameCount();
   }

   public int[] getFrameDurations() {
      return this.nativeGetFrameDurations();
   }

   public AnimatedDrawableFrameInfo getFrameInfo(int var1) {
      GifFrame var2 = this.getFrame(var1);

      AnimatedDrawableFrameInfo var3;
      try {
         var3 = new AnimatedDrawableFrameInfo(var1, var2.getXOffset(), var2.getYOffset(), var2.getWidth(), var2.getHeight(), AnimatedDrawableFrameInfo.BlendOperation.BLEND_WITH_PREVIOUS, fromGifDisposalMethod(var2.getDisposalMode()));
      } finally {
         var2.dispose();
      }

      return var3;
   }

   public int getHeight() {
      return this.nativeGetHeight();
   }

   public int getLoopCount() {
      int var1 = this.nativeGetLoopCount();
      switch(var1) {
      case -1:
         return 1;
      case 0:
         return 0;
      default:
         return var1 + 1;
      }
   }

   public int getSizeInBytes() {
      return this.nativeGetSizeInBytes();
   }

   public int getWidth() {
      return this.nativeGetWidth();
   }
}
