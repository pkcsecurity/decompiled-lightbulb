package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.memory.PooledByteBufferOutputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.memory.NativeMemoryChunk;
import com.facebook.imagepipeline.memory.NativeMemoryChunkPool;
import com.facebook.imagepipeline.memory.NativePooledByteBuffer;
import java.io.IOException;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class NativePooledByteBufferOutputStream extends PooledByteBufferOutputStream {

   private CloseableReference<NativeMemoryChunk> mBufRef;
   private int mCount;
   private final NativeMemoryChunkPool mPool;


   public NativePooledByteBufferOutputStream(NativeMemoryChunkPool var1) {
      this(var1, var1.getMinBufferSize());
   }

   public NativePooledByteBufferOutputStream(NativeMemoryChunkPool var1, int var2) {
      boolean var3;
      if(var2 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      this.mPool = (NativeMemoryChunkPool)Preconditions.checkNotNull(var1);
      this.mCount = 0;
      this.mBufRef = CloseableReference.of(this.mPool.get(var2), this.mPool);
   }

   private void ensureValid() {
      if(!CloseableReference.isValid(this.mBufRef)) {
         throw new NativePooledByteBufferOutputStream.InvalidStreamException();
      }
   }

   public void close() {
      CloseableReference.closeSafely(this.mBufRef);
      this.mBufRef = null;
      this.mCount = -1;
      super.close();
   }

   @VisibleForTesting
   void realloc(int var1) {
      this.ensureValid();
      if(var1 > ((NativeMemoryChunk)this.mBufRef.get()).getSize()) {
         NativeMemoryChunk var2 = (NativeMemoryChunk)this.mPool.get(var1);
         ((NativeMemoryChunk)this.mBufRef.get()).copy(0, var2, 0, this.mCount);
         this.mBufRef.close();
         this.mBufRef = CloseableReference.of(var2, this.mPool);
      }
   }

   public int size() {
      return this.mCount;
   }

   public NativePooledByteBuffer toByteBuffer() {
      this.ensureValid();
      return new NativePooledByteBuffer(this.mBufRef, this.mCount);
   }

   public void write(int var1) throws IOException {
      this.write(new byte[]{(byte)var1});
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      if(var2 >= 0 && var3 >= 0 && var2 + var3 <= var1.length) {
         this.ensureValid();
         this.realloc(this.mCount + var3);
         ((NativeMemoryChunk)this.mBufRef.get()).write(this.mCount, var1, var2, var3);
         this.mCount += var3;
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("length=");
         var4.append(var1.length);
         var4.append("; regionStart=");
         var4.append(var2);
         var4.append("; regionLength=");
         var4.append(var3);
         throw new ArrayIndexOutOfBoundsException(var4.toString());
      }
   }

   public static class InvalidStreamException extends RuntimeException {

      public InvalidStreamException() {
         super("OutputStream no longer valid");
      }
   }
}
