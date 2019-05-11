package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.IOContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

abstract class BaseReader extends Reader {

   protected static final int LAST_VALID_UNICODE_CHAR = 1114111;
   protected static final char NULL_BYTE = '\u0000';
   protected static final char NULL_CHAR = '\u0000';
   protected byte[] _buffer;
   protected final IOContext _context;
   protected InputStream _in;
   protected int _length;
   protected int _ptr;
   protected char[] _tmpBuf = null;


   protected BaseReader(IOContext var1, InputStream var2, byte[] var3, int var4, int var5) {
      this._context = var1;
      this._in = var2;
      this._buffer = var3;
      this._ptr = var4;
      this._length = var5;
   }

   public void close() throws IOException {
      InputStream var1 = this._in;
      if(var1 != null) {
         this._in = null;
         this.freeBuffers();
         var1.close();
      }

   }

   public final void freeBuffers() {
      byte[] var1 = this._buffer;
      if(var1 != null) {
         this._buffer = null;
         this._context.releaseReadIOBuffer(var1);
      }

   }

   public int read() throws IOException {
      if(this._tmpBuf == null) {
         this._tmpBuf = new char[1];
      }

      return this.read(this._tmpBuf, 0, 1) < 1?-1:this._tmpBuf[0];
   }

   protected void reportBounds(char[] var1, int var2, int var3) throws IOException {
      StringBuilder var4 = new StringBuilder();
      var4.append("read(buf,");
      var4.append(var2);
      var4.append(",");
      var4.append(var3);
      var4.append("), cbuf[");
      var4.append(var1.length);
      var4.append("]");
      throw new ArrayIndexOutOfBoundsException(var4.toString());
   }

   protected void reportStrangeStream() throws IOException {
      throw new IOException("Strange I/O stream, returned 0 bytes on read");
   }
}
