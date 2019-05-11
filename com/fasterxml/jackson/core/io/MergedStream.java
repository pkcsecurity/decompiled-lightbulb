package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.IOContext;
import java.io.IOException;
import java.io.InputStream;

public final class MergedStream extends InputStream {

   byte[] _buffer;
   protected final IOContext _context;
   final int _end;
   final InputStream _in;
   int _ptr;


   public MergedStream(IOContext var1, InputStream var2, byte[] var3, int var4, int var5) {
      this._context = var1;
      this._in = var2;
      this._buffer = var3;
      this._ptr = var4;
      this._end = var5;
   }

   private void freeMergedBuffer() {
      byte[] var1 = this._buffer;
      if(var1 != null) {
         this._buffer = null;
         if(this._context != null) {
            this._context.releaseReadIOBuffer(var1);
         }
      }

   }

   public int available() throws IOException {
      return this._buffer != null?this._end - this._ptr:this._in.available();
   }

   public void close() throws IOException {
      this.freeMergedBuffer();
      this._in.close();
   }

   public void mark(int var1) {
      if(this._buffer == null) {
         this._in.mark(var1);
      }

   }

   public boolean markSupported() {
      return this._buffer == null && this._in.markSupported();
   }

   public int read() throws IOException {
      if(this._buffer != null) {
         byte[] var2 = this._buffer;
         int var1 = this._ptr;
         this._ptr = var1 + 1;
         byte var3 = var2[var1];
         if(this._ptr >= this._end) {
            this.freeMergedBuffer();
         }

         return var3 & 255;
      } else {
         return this._in.read();
      }
   }

   public int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if(this._buffer != null) {
         int var5 = this._end - this._ptr;
         int var4 = var3;
         if(var3 > var5) {
            var4 = var5;
         }

         System.arraycopy(this._buffer, this._ptr, var1, var2, var4);
         this._ptr += var4;
         if(this._ptr >= this._end) {
            this.freeMergedBuffer();
         }

         return var4;
      } else {
         return this._in.read(var1, var2, var3);
      }
   }

   public void reset() throws IOException {
      if(this._buffer == null) {
         this._in.reset();
      }

   }

   public long skip(long var1) throws IOException {
      long var3;
      long var5;
      if(this._buffer != null) {
         var5 = (long)(this._end - this._ptr);
         if(var5 > var1) {
            this._ptr += (int)var1;
            return var1;
         }

         this.freeMergedBuffer();
         var3 = var5 + 0L;
         var5 = var1 - var5;
         var1 = var3;
         var3 = var5;
      } else {
         var5 = 0L;
         var3 = var1;
         var1 = var5;
      }

      return var3 > 0L?var1 + this._in.skip(var3):var1;
   }
}
