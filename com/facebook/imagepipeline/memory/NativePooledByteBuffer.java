package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.memory.NativeMemoryChunk;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class NativePooledByteBuffer implements PooledByteBuffer {

   @VisibleForTesting
   @GuardedBy
   CloseableReference<NativeMemoryChunk> mBufRef;
   private final int mSize;


   public NativePooledByteBuffer(CloseableReference<NativeMemoryChunk> var1, int var2) {
      Preconditions.checkNotNull(var1);
      boolean var3;
      if(var2 >= 0 && var2 <= ((NativeMemoryChunk)var1.get()).getSize()) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      this.mBufRef = var1.clone();
      this.mSize = var2;
   }

   public void close() {
      synchronized(this){}

      try {
         CloseableReference.closeSafely(this.mBufRef);
         this.mBufRef = null;
      } finally {
         ;
      }

   }

   void ensureValid() {
      synchronized(this){}

      try {
         if(this.isClosed()) {
            throw new PooledByteBuffer.ClosedException();
         }
      } finally {
         ;
      }

   }

   public long getNativePtr() {
      synchronized(this){}

      long var1;
      try {
         this.ensureValid();
         var1 = ((NativeMemoryChunk)this.mBufRef.get()).getNativePtr();
      } finally {
         ;
      }

      return var1;
   }

   public boolean isClosed() {
      synchronized(this){}

      boolean var1;
      try {
         var1 = CloseableReference.isValid(this.mBufRef);
      } finally {
         ;
      }

      return var1 ^ true;
   }

   public byte read(int param1) {
      // $FF: Couldn't be decompiled
   }

   public void read(int param1, byte[] param2, int param3, int param4) {
      // $FF: Couldn't be decompiled
   }

   public int size() {
      synchronized(this){}

      int var1;
      try {
         this.ensureValid();
         var1 = this.mSize;
      } finally {
         ;
      }

      return var1;
   }
}
