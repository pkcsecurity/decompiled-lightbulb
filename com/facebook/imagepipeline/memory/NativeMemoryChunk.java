package com.facebook.imagepipeline.memory;

import android.util.Log;
import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.imagepipeline.nativecode.ImagePipelineNativeLoader;
import java.io.Closeable;

@DoNotStrip
public class NativeMemoryChunk implements Closeable {

   private static final String TAG = "NativeMemoryChunk";
   private boolean mClosed;
   private final long mNativePtr;
   private final int mSize;


   static {
      ImagePipelineNativeLoader.load();
   }

   @VisibleForTesting
   public NativeMemoryChunk() {
      this.mSize = 0;
      this.mNativePtr = 0L;
      this.mClosed = true;
   }

   public NativeMemoryChunk(int var1) {
      boolean var2;
      if(var1 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      this.mSize = var1;
      this.mNativePtr = nativeAllocate(this.mSize);
      this.mClosed = false;
   }

   private int adjustByteCount(int var1, int var2) {
      return Math.min(Math.max(0, this.mSize - var1), var2);
   }

   private void checkBounds(int var1, int var2, int var3, int var4) {
      boolean var6 = false;
      boolean var5;
      if(var4 >= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5);
      if(var1 >= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5);
      if(var3 >= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5);
      if(var1 + var4 <= this.mSize) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5);
      var5 = var6;
      if(var3 + var4 <= var2) {
         var5 = true;
      }

      Preconditions.checkArgument(var5);
   }

   private void doCopy(int var1, NativeMemoryChunk var2, int var3, int var4) {
      Preconditions.checkState(this.isClosed() ^ true);
      Preconditions.checkState(var2.isClosed() ^ true);
      this.checkBounds(var1, var2.mSize, var3, var4);
      nativeMemcpy(var2.mNativePtr + (long)var3, this.mNativePtr + (long)var1, var4);
   }

   @DoNotStrip
   private static native long nativeAllocate(int var0);

   @DoNotStrip
   private static native void nativeCopyFromByteArray(long var0, byte[] var2, int var3, int var4);

   @DoNotStrip
   private static native void nativeCopyToByteArray(long var0, byte[] var2, int var3, int var4);

   @DoNotStrip
   private static native void nativeFree(long var0);

   @DoNotStrip
   private static native void nativeMemcpy(long var0, long var2, int var4);

   @DoNotStrip
   private static native byte nativeReadByte(long var0);

   public void close() {
      synchronized(this){}

      try {
         if(!this.mClosed) {
            this.mClosed = true;
            nativeFree(this.mNativePtr);
         }
      } finally {
         ;
      }

   }

   public void copy(int param1, NativeMemoryChunk param2, int param3, int param4) {
      // $FF: Couldn't be decompiled
   }

   protected void finalize() throws Throwable {
      if(!this.isClosed()) {
         StringBuilder var1 = new StringBuilder();
         var1.append("finalize: Chunk ");
         var1.append(Integer.toHexString(System.identityHashCode(this)));
         var1.append(" still active. Underlying address = ");
         var1.append(Long.toHexString(this.mNativePtr));
         Log.w("NativeMemoryChunk", var1.toString());

         try {
            this.close();
         } finally {
            super.finalize();
         }

      }
   }

   public long getNativePtr() {
      return this.mNativePtr;
   }

   public int getSize() {
      return this.mSize;
   }

   public boolean isClosed() {
      synchronized(this){}

      boolean var1;
      try {
         var1 = this.mClosed;
      } finally {
         ;
      }

      return var1;
   }

   public byte read(int param1) {
      // $FF: Couldn't be decompiled
   }

   public int read(int var1, byte[] var2, int var3, int var4) {
      synchronized(this){}

      try {
         Preconditions.checkNotNull(var2);
         Preconditions.checkState(this.isClosed() ^ true);
         var4 = this.adjustByteCount(var1, var4);
         this.checkBounds(var1, var2.length, var3, var4);
         nativeCopyToByteArray(this.mNativePtr + (long)var1, var2, var3, var4);
      } finally {
         ;
      }

      return var4;
   }

   public int write(int var1, byte[] var2, int var3, int var4) {
      synchronized(this){}

      try {
         Preconditions.checkNotNull(var2);
         Preconditions.checkState(this.isClosed() ^ true);
         var4 = this.adjustByteCount(var1, var4);
         this.checkBounds(var1, var2.length, var3, var4);
         nativeCopyFromByteArray(this.mNativePtr + (long)var1, var2, var3, var4);
      } finally {
         ;
      }

      return var4;
   }
}
