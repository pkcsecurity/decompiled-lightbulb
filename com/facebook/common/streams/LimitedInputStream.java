package com.facebook.common.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LimitedInputStream extends FilterInputStream {

   private int mBytesToRead;
   private int mBytesToReadWhenMarked;


   public LimitedInputStream(InputStream var1, int var2) {
      super(var1);
      if(var1 == null) {
         throw new NullPointerException();
      } else if(var2 < 0) {
         throw new IllegalArgumentException("limit must be >= 0");
      } else {
         this.mBytesToRead = var2;
         this.mBytesToReadWhenMarked = -1;
      }
   }

   public int available() throws IOException {
      return Math.min(this.in.available(), this.mBytesToRead);
   }

   public void mark(int var1) {
      if(this.in.markSupported()) {
         this.in.mark(var1);
         this.mBytesToReadWhenMarked = this.mBytesToRead;
      }

   }

   public int read() throws IOException {
      if(this.mBytesToRead == 0) {
         return -1;
      } else {
         int var1 = this.in.read();
         if(var1 != -1) {
            --this.mBytesToRead;
         }

         return var1;
      }
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if(this.mBytesToRead == 0) {
         return -1;
      } else {
         var3 = Math.min(var3, this.mBytesToRead);
         var2 = this.in.read(var1, var2, var3);
         if(var2 > 0) {
            this.mBytesToRead -= var2;
         }

         return var2;
      }
   }

   public void reset() throws IOException {
      if(!this.in.markSupported()) {
         throw new IOException("mark is not supported");
      } else if(this.mBytesToReadWhenMarked == -1) {
         throw new IOException("mark not set");
      } else {
         this.in.reset();
         this.mBytesToRead = this.mBytesToReadWhenMarked;
      }
   }

   public long skip(long var1) throws IOException {
      var1 = Math.min(var1, (long)this.mBytesToRead);
      var1 = this.in.skip(var1);
      this.mBytesToRead = (int)((long)this.mBytesToRead - var1);
      return var1;
   }
}
