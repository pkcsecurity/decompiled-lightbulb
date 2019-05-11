package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.BaseReader;
import com.fasterxml.jackson.core.io.IOContext;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;

public class UTF32Reader extends BaseReader {

   protected final boolean _bigEndian;
   protected int _byteCount;
   protected int _charCount;
   protected final boolean _managedBuffers;
   protected char _surrogate;


   public UTF32Reader(IOContext var1, InputStream var2, byte[] var3, int var4, int var5, boolean var6) {
      super(var1, var2, var3, var4, var5);
      boolean var7 = false;
      this._surrogate = 0;
      this._charCount = 0;
      this._byteCount = 0;
      this._bigEndian = var6;
      var6 = var7;
      if(var2 != null) {
         var6 = true;
      }

      this._managedBuffers = var6;
   }

   private boolean loadMore(int var1) throws IOException {
      this._byteCount += this._length - var1;
      if(var1 > 0) {
         if(this._ptr > 0) {
            for(int var2 = 0; var2 < var1; ++var2) {
               this._buffer[var2] = this._buffer[this._ptr + var2];
            }

            this._ptr = 0;
         }

         this._length = var1;
      } else {
         this._ptr = 0;
         if(this._in == null) {
            var1 = -1;
         } else {
            var1 = this._in.read(this._buffer);
         }

         if(var1 < 1) {
            this._length = 0;
            if(var1 < 0) {
               if(this._managedBuffers) {
                  this.freeBuffers();
               }

               return false;
            }

            this.reportStrangeStream();
         }

         this._length = var1;
      }

      for(; this._length < 4; this._length += var1) {
         if(this._in == null) {
            var1 = -1;
         } else {
            var1 = this._in.read(this._buffer, this._length, this._buffer.length - this._length);
         }

         if(var1 < 1) {
            if(var1 < 0) {
               if(this._managedBuffers) {
                  this.freeBuffers();
               }

               this.reportUnexpectedEOF(this._length, 4);
            }

            this.reportStrangeStream();
         }
      }

      return true;
   }

   private void reportInvalid(int var1, int var2, String var3) throws IOException {
      int var4 = this._byteCount;
      int var5 = this._ptr;
      int var6 = this._charCount;
      StringBuilder var7 = new StringBuilder();
      var7.append("Invalid UTF-32 character 0x");
      var7.append(Integer.toHexString(var1));
      var7.append(var3);
      var7.append(" at char #");
      var7.append(var6 + var2);
      var7.append(", byte #");
      var7.append(var4 + var5 - 1);
      var7.append(")");
      throw new CharConversionException(var7.toString());
   }

   private void reportUnexpectedEOF(int var1, int var2) throws IOException {
      int var3 = this._byteCount;
      int var4 = this._charCount;
      StringBuilder var5 = new StringBuilder();
      var5.append("Unexpected EOF in the middle of a 4-byte UTF-32 char: got ");
      var5.append(var1);
      var5.append(", needed ");
      var5.append(var2);
      var5.append(", at char #");
      var5.append(var4);
      var5.append(", byte #");
      var5.append(var3 + var1);
      var5.append(")");
      throw new CharConversionException(var5.toString());
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      if(this._buffer == null) {
         return -1;
      } else if(var3 < 1) {
         return var3;
      } else {
         if(var2 < 0 || var2 + var3 > var1.length) {
            this.reportBounds(var1, var2, var3);
         }

         int var7 = var3 + var2;
         if(this._surrogate != 0) {
            var3 = var2 + 1;
            var1[var2] = this._surrogate;
            this._surrogate = 0;
         } else {
            var3 = this._length - this._ptr;
            if(var3 < 4 && !this.loadMore(var3)) {
               return -1;
            }

            var3 = var2;
         }

         while(var3 < var7) {
            int var4 = this._ptr;
            byte var5;
            byte var6;
            byte var8;
            if(this._bigEndian) {
               var5 = this._buffer[var4];
               var6 = this._buffer[var4 + 1];
               var8 = this._buffer[var4 + 2];
               var4 = this._buffer[var4 + 3] & 255 | var5 << 24 | (var6 & 255) << 16 | (var8 & 255) << 8;
            } else {
               var5 = this._buffer[var4];
               var6 = this._buffer[var4 + 1];
               var8 = this._buffer[var4 + 2];
               var4 = this._buffer[var4 + 3] << 24 | var5 & 255 | (var6 & 255) << 8 | (var8 & 255) << 16;
            }

            this._ptr += 4;
            int var11 = var3;
            int var10 = var4;
            if(var4 > '\uffff') {
               if(var4 > 1114111) {
                  StringBuilder var9 = new StringBuilder();
                  var9.append("(above ");
                  var9.append(Integer.toHexString(1114111));
                  var9.append(") ");
                  this.reportInvalid(var4, var3 - var2, var9.toString());
               }

               var10 = var4 - 65536;
               var4 = var3 + 1;
               var1[var3] = (char)((var10 >> 10) + '\ud800');
               var10 = var10 & 1023 | '\udc00';
               if(var4 >= var7) {
                  this._surrogate = (char)var10;
                  var3 = var4;
                  break;
               }

               var11 = var4;
            }

            var3 = var11 + 1;
            var1[var11] = (char)var10;
            if(this._ptr >= this._length) {
               break;
            }
         }

         var2 = var3 - var2;
         this._charCount += var2;
         return var2;
      }
   }
}
