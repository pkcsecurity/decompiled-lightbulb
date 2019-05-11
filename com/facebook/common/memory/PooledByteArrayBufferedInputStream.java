package com.facebook.common.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.ResourceReleaser;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class PooledByteArrayBufferedInputStream extends InputStream {

   private static final String TAG = "PooledByteInputStream";
   private int mBufferOffset;
   private int mBufferedSize;
   private final byte[] mByteArray;
   private boolean mClosed;
   private final InputStream mInputStream;
   private final ResourceReleaser<byte[]> mResourceReleaser;


   public PooledByteArrayBufferedInputStream(InputStream var1, byte[] var2, ResourceReleaser<byte[]> var3) {
      this.mInputStream = (InputStream)Preconditions.checkNotNull(var1);
      this.mByteArray = (byte[])Preconditions.checkNotNull(var2);
      this.mResourceReleaser = (ResourceReleaser)Preconditions.checkNotNull(var3);
      this.mBufferedSize = 0;
      this.mBufferOffset = 0;
      this.mClosed = false;
   }

   private boolean ensureDataInBuffer() throws IOException {
      if(this.mBufferOffset < this.mBufferedSize) {
         return true;
      } else {
         int var1 = this.mInputStream.read(this.mByteArray);
         if(var1 <= 0) {
            return false;
         } else {
            this.mBufferedSize = var1;
            this.mBufferOffset = 0;
            return true;
         }
      }
   }

   private void ensureNotClosed() throws IOException {
      if(this.mClosed) {
         throw new IOException("stream already closed");
      }
   }

   public int available() throws IOException {
      boolean var1;
      if(this.mBufferOffset <= this.mBufferedSize) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1);
      this.ensureNotClosed();
      return this.mBufferedSize - this.mBufferOffset + this.mInputStream.available();
   }

   public void close() throws IOException {
      if(!this.mClosed) {
         this.mClosed = true;
         this.mResourceReleaser.release(this.mByteArray);
         super.close();
      }

   }

   protected void finalize() throws Throwable {
      if(!this.mClosed) {
         FLog.e("PooledByteInputStream", "Finalized without closing");
         this.close();
      }

      super.finalize();
   }

   public int read() throws IOException {
      boolean var2;
      if(this.mBufferOffset <= this.mBufferedSize) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkState(var2);
      this.ensureNotClosed();
      if(!this.ensureDataInBuffer()) {
         return -1;
      } else {
         byte[] var3 = this.mByteArray;
         int var1 = this.mBufferOffset;
         this.mBufferOffset = var1 + 1;
         return var3[var1] & 255;
      }
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      boolean var4;
      if(this.mBufferOffset <= this.mBufferedSize) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkState(var4);
      this.ensureNotClosed();
      if(!this.ensureDataInBuffer()) {
         return -1;
      } else {
         var3 = Math.min(this.mBufferedSize - this.mBufferOffset, var3);
         System.arraycopy(this.mByteArray, this.mBufferOffset, var1, var2, var3);
         this.mBufferOffset += var3;
         return var3;
      }
   }

   public long skip(long var1) throws IOException {
      boolean var5;
      if(this.mBufferOffset <= this.mBufferedSize) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkState(var5);
      this.ensureNotClosed();
      long var3 = (long)(this.mBufferedSize - this.mBufferOffset);
      if(var3 >= var1) {
         this.mBufferOffset = (int)((long)this.mBufferOffset + var1);
         return var1;
      } else {
         this.mBufferOffset = this.mBufferedSize;
         return var3 + this.mInputStream.skip(var1 - var3);
      }
   }
}
