package com.facebook.common.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TailAppendingInputStream extends FilterInputStream {

   private int mMarkedTailOffset;
   private final byte[] mTail;
   private int mTailOffset;


   public TailAppendingInputStream(InputStream var1, byte[] var2) {
      super(var1);
      if(var1 == null) {
         throw new NullPointerException();
      } else if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.mTail = var2;
      }
   }

   private int readNextTailByte() {
      if(this.mTailOffset >= this.mTail.length) {
         return -1;
      } else {
         byte[] var2 = this.mTail;
         int var1 = this.mTailOffset;
         this.mTailOffset = var1 + 1;
         return var2[var1] & 255;
      }
   }

   public void mark(int var1) {
      if(this.in.markSupported()) {
         super.mark(var1);
         this.mMarkedTailOffset = this.mTailOffset;
      }

   }

   public int read() throws IOException {
      int var1 = this.in.read();
      return var1 != -1?var1:this.readNextTailByte();
   }

   public int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = this.in.read(var1, var2, var3);
      if(var4 != -1) {
         return var4;
      } else {
         var4 = 0;
         if(var3 == 0) {
            return 0;
         } else {
            while(var4 < var3) {
               int var5 = this.readNextTailByte();
               if(var5 == -1) {
                  break;
               }

               var1[var2 + var4] = (byte)var5;
               ++var4;
            }

            return var4 > 0?var4:-1;
         }
      }
   }

   public void reset() throws IOException {
      if(this.in.markSupported()) {
         this.in.reset();
         this.mTailOffset = this.mMarkedTailOffset;
      } else {
         throw new IOException("mark is not supported");
      }
   }
}
