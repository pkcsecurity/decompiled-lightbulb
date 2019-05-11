package com.facebook.common.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.memory.PooledByteBuffer;
import java.io.InputStream;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class PooledByteBufferInputStream extends InputStream {

   @VisibleForTesting
   int mMark;
   @VisibleForTesting
   int mOffset;
   @VisibleForTesting
   final PooledByteBuffer mPooledByteBuffer;


   public PooledByteBufferInputStream(PooledByteBuffer var1) {
      Preconditions.checkArgument(var1.isClosed() ^ true);
      this.mPooledByteBuffer = (PooledByteBuffer)Preconditions.checkNotNull(var1);
      this.mOffset = 0;
      this.mMark = 0;
   }

   public int available() {
      return this.mPooledByteBuffer.size() - this.mOffset;
   }

   public void mark(int var1) {
      this.mMark = this.mOffset;
   }

   public boolean markSupported() {
      return true;
   }

   public int read() {
      if(this.available() <= 0) {
         return -1;
      } else {
         PooledByteBuffer var2 = this.mPooledByteBuffer;
         int var1 = this.mOffset;
         this.mOffset = var1 + 1;
         return var2.read(var1) & 255;
      }
   }

   public int read(byte[] var1) {
      return this.read(var1, 0, var1.length);
   }

   public int read(byte[] var1, int var2, int var3) {
      if(var2 >= 0 && var3 >= 0 && var2 + var3 <= var1.length) {
         int var4 = this.available();
         if(var4 <= 0) {
            return -1;
         } else if(var3 <= 0) {
            return 0;
         } else {
            var3 = Math.min(var4, var3);
            this.mPooledByteBuffer.read(this.mOffset, var1, var2, var3);
            this.mOffset += var3;
            return var3;
         }
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("length=");
         var5.append(var1.length);
         var5.append("; regionStart=");
         var5.append(var2);
         var5.append("; regionLength=");
         var5.append(var3);
         throw new ArrayIndexOutOfBoundsException(var5.toString());
      }
   }

   public void reset() {
      this.mOffset = this.mMark;
   }

   public long skip(long var1) {
      boolean var4;
      if(var1 >= 0L) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      int var3 = Math.min((int)var1, this.available());
      this.mOffset += var3;
      return (long)var3;
   }
}
