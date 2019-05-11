package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.IOContext;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public final class UTF8Writer extends Writer {

   static final int SURR1_FIRST = 55296;
   static final int SURR1_LAST = 56319;
   static final int SURR2_FIRST = 56320;
   static final int SURR2_LAST = 57343;
   private final IOContext _context;
   private OutputStream _out;
   private byte[] _outBuffer;
   private final int _outBufferEnd;
   private int _outPtr;
   private int _surrogate = 0;


   public UTF8Writer(IOContext var1, OutputStream var2) {
      this._context = var1;
      this._out = var2;
      this._outBuffer = var1.allocWriteEncodingBuffer();
      this._outBufferEnd = this._outBuffer.length - 4;
      this._outPtr = 0;
   }

   protected static void illegalSurrogate(int var0) throws IOException {
      throw new IOException(illegalSurrogateDesc(var0));
   }

   protected static String illegalSurrogateDesc(int var0) {
      StringBuilder var1;
      if(var0 > 1114111) {
         var1 = new StringBuilder();
         var1.append("Illegal character point (0x");
         var1.append(Integer.toHexString(var0));
         var1.append(") to output; max is 0x10FFFF as per RFC 4627");
         return var1.toString();
      } else if(var0 >= '\ud800') {
         if(var0 <= '\udbff') {
            var1 = new StringBuilder();
            var1.append("Unmatched first part of surrogate pair (0x");
            var1.append(Integer.toHexString(var0));
            var1.append(")");
            return var1.toString();
         } else {
            var1 = new StringBuilder();
            var1.append("Unmatched second part of surrogate pair (0x");
            var1.append(Integer.toHexString(var0));
            var1.append(")");
            return var1.toString();
         }
      } else {
         var1 = new StringBuilder();
         var1.append("Illegal character point (0x");
         var1.append(Integer.toHexString(var0));
         var1.append(") to output");
         return var1.toString();
      }
   }

   public Writer append(char var1) throws IOException {
      this.write(var1);
      return this;
   }

   public void close() throws IOException {
      if(this._out != null) {
         if(this._outPtr > 0) {
            this._out.write(this._outBuffer, 0, this._outPtr);
            this._outPtr = 0;
         }

         OutputStream var2 = this._out;
         this._out = null;
         byte[] var3 = this._outBuffer;
         if(var3 != null) {
            this._outBuffer = null;
            this._context.releaseWriteEncodingBuffer(var3);
         }

         var2.close();
         int var1 = this._surrogate;
         this._surrogate = 0;
         if(var1 > 0) {
            illegalSurrogate(var1);
         }
      }

   }

   protected int convertSurrogate(int var1) throws IOException {
      int var2 = this._surrogate;
      this._surrogate = 0;
      if(var1 >= '\udc00' && var1 <= '\udfff') {
         return (var2 - '\ud800' << 10) + 65536 + (var1 - '\udc00');
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Broken surrogate pair: first char 0x");
         var3.append(Integer.toHexString(var2));
         var3.append(", second 0x");
         var3.append(Integer.toHexString(var1));
         var3.append("; illegal combination");
         throw new IOException(var3.toString());
      }
   }

   public void flush() throws IOException {
      if(this._out != null) {
         if(this._outPtr > 0) {
            this._out.write(this._outBuffer, 0, this._outPtr);
            this._outPtr = 0;
         }

         this._out.flush();
      }

   }

   public void write(int var1) throws IOException {
      int var2;
      if(this._surrogate > 0) {
         var2 = this.convertSurrogate(var1);
      } else {
         var2 = var1;
         if(var1 >= '\ud800') {
            var2 = var1;
            if(var1 <= '\udfff') {
               if(var1 > '\udbff') {
                  illegalSurrogate(var1);
               }

               this._surrogate = var1;
               return;
            }
         }
      }

      if(this._outPtr >= this._outBufferEnd) {
         this._out.write(this._outBuffer, 0, this._outPtr);
         this._outPtr = 0;
      }

      byte[] var4;
      if(var2 < 128) {
         var4 = this._outBuffer;
         var1 = this._outPtr;
         this._outPtr = var1 + 1;
         var4[var1] = (byte)var2;
      } else {
         var1 = this._outPtr;
         int var3;
         if(var2 < 2048) {
            var4 = this._outBuffer;
            var3 = var1 + 1;
            var4[var1] = (byte)(var2 >> 6 | 192);
            var4 = this._outBuffer;
            var1 = var3 + 1;
            var4[var3] = (byte)(var2 & 63 | 128);
         } else if(var2 <= '\uffff') {
            var4 = this._outBuffer;
            var3 = var1 + 1;
            var4[var1] = (byte)(var2 >> 12 | 224);
            var4 = this._outBuffer;
            var1 = var3 + 1;
            var4[var3] = (byte)(var2 >> 6 & 63 | 128);
            this._outBuffer[var1] = (byte)(var2 & 63 | 128);
            ++var1;
         } else {
            if(var2 > 1114111) {
               illegalSurrogate(var2);
            }

            var4 = this._outBuffer;
            var3 = var1 + 1;
            var4[var1] = (byte)(var2 >> 18 | 240);
            var4 = this._outBuffer;
            var1 = var3 + 1;
            var4[var3] = (byte)(var2 >> 12 & 63 | 128);
            var4 = this._outBuffer;
            var3 = var1 + 1;
            var4[var1] = (byte)(var2 >> 6 & 63 | 128);
            var4 = this._outBuffer;
            var1 = var3 + 1;
            var4[var3] = (byte)(var2 & 63 | 128);
         }

         this._outPtr = var1;
      }
   }

   public void write(String var1) throws IOException {
      this.write(var1, 0, var1.length());
   }

   public void write(String var1, int var2, int var3) throws IOException {
      if(var3 < 2) {
         if(var3 == 1) {
            this.write(var1.charAt(var2));
         }

      } else {
         int var4 = var2;
         int var5 = var3;
         if(this._surrogate > 0) {
            char var12 = var1.charAt(var2);
            var5 = var3 - 1;
            this.write(this.convertSurrogate(var12));
            var4 = var2 + 1;
         }

         var2 = this._outPtr;
         byte[] var11 = this._outBuffer;
         int var9 = this._outBufferEnd;
         int var10 = var5 + var4;
         var3 = var4;

         label71:
         while(true) {
            var4 = var2;
            if(var3 >= var10) {
               break;
            }

            var4 = var2;
            if(var2 >= var9) {
               this._out.write(var11, 0, var2);
               var4 = 0;
            }

            var5 = var3 + 1;
            char var7 = var1.charAt(var3);
            var3 = var4;
            var2 = var5;
            char var6 = var7;
            int var13;
            if(var7 < 128) {
               var2 = var4 + 1;
               var11[var4] = (byte)var7;
               var3 = var10 - var5;
               var13 = var9 - var2;
               var4 = var3;
               if(var3 > var13) {
                  var4 = var13;
               }

               var3 = var5;

               while(true) {
                  if(var3 >= var4 + var5) {
                     continue label71;
                  }

                  var13 = var3 + 1;
                  var7 = var1.charAt(var3);
                  if(var7 >= 128) {
                     var3 = var2;
                     var2 = var13;
                     var6 = var7;
                     break;
                  }

                  int var8 = var2 + 1;
                  var11[var2] = (byte)var7;
                  var3 = var13;
                  var2 = var8;
               }
            }

            if(var6 < 2048) {
               var5 = var3 + 1;
               var11[var3] = (byte)(var6 >> 6 | 192);
               var4 = var5 + 1;
               var11[var5] = (byte)(var6 & 63 | 128);
               var3 = var2;
               var2 = var4;
            } else if(var6 >= '\ud800' && var6 <= '\udfff') {
               if(var6 > '\udbff') {
                  this._outPtr = var3;
                  illegalSurrogate(var6);
               }

               this._surrogate = var6;
               if(var2 >= var10) {
                  var4 = var3;
                  break;
               }

               var4 = var2 + 1;
               var5 = this.convertSurrogate(var1.charAt(var2));
               if(var5 > 1114111) {
                  this._outPtr = var3;
                  illegalSurrogate(var5);
               }

               var2 = var3 + 1;
               var11[var3] = (byte)(var5 >> 18 | 240);
               var3 = var2 + 1;
               var11[var2] = (byte)(var5 >> 12 & 63 | 128);
               var13 = var3 + 1;
               var11[var3] = (byte)(var5 >> 6 & 63 | 128);
               var2 = var13 + 1;
               var11[var13] = (byte)(var5 & 63 | 128);
               var3 = var4;
            } else {
               var4 = var3 + 1;
               var11[var3] = (byte)(var6 >> 12 | 224);
               var5 = var4 + 1;
               var11[var4] = (byte)(var6 >> 6 & 63 | 128);
               var11[var5] = (byte)(var6 & 63 | 128);
               var3 = var2;
               var2 = var5 + 1;
            }
         }

         this._outPtr = var4;
      }
   }

   public void write(char[] var1) throws IOException {
      this.write(var1, 0, var1.length);
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      if(var3 < 2) {
         if(var3 == 1) {
            this.write(var1[var2]);
         }

      } else {
         int var4 = var2;
         int var5 = var3;
         if(this._surrogate > 0) {
            char var12 = var1[var2];
            var5 = var3 - 1;
            this.write(this.convertSurrogate(var12));
            var4 = var2 + 1;
         }

         var2 = this._outPtr;
         byte[] var11 = this._outBuffer;
         int var9 = this._outBufferEnd;
         int var10 = var5 + var4;
         var3 = var4;

         label71:
         while(true) {
            var4 = var2;
            if(var3 >= var10) {
               break;
            }

            var4 = var2;
            if(var2 >= var9) {
               this._out.write(var11, 0, var2);
               var4 = 0;
            }

            var5 = var3 + 1;
            char var7 = var1[var3];
            var3 = var4;
            var2 = var5;
            char var6 = var7;
            int var13;
            if(var7 < 128) {
               var2 = var4 + 1;
               var11[var4] = (byte)var7;
               var3 = var10 - var5;
               var13 = var9 - var2;
               var4 = var3;
               if(var3 > var13) {
                  var4 = var13;
               }

               var3 = var5;

               while(true) {
                  if(var3 >= var4 + var5) {
                     continue label71;
                  }

                  var13 = var3 + 1;
                  var7 = var1[var3];
                  if(var7 >= 128) {
                     var3 = var2;
                     var2 = var13;
                     var6 = var7;
                     break;
                  }

                  int var8 = var2 + 1;
                  var11[var2] = (byte)var7;
                  var3 = var13;
                  var2 = var8;
               }
            }

            if(var6 < 2048) {
               var5 = var3 + 1;
               var11[var3] = (byte)(var6 >> 6 | 192);
               var4 = var5 + 1;
               var11[var5] = (byte)(var6 & 63 | 128);
               var3 = var2;
               var2 = var4;
            } else if(var6 >= '\ud800' && var6 <= '\udfff') {
               if(var6 > '\udbff') {
                  this._outPtr = var3;
                  illegalSurrogate(var6);
               }

               this._surrogate = var6;
               if(var2 >= var10) {
                  var4 = var3;
                  break;
               }

               var4 = var2 + 1;
               var5 = this.convertSurrogate(var1[var2]);
               if(var5 > 1114111) {
                  this._outPtr = var3;
                  illegalSurrogate(var5);
               }

               var2 = var3 + 1;
               var11[var3] = (byte)(var5 >> 18 | 240);
               var3 = var2 + 1;
               var11[var2] = (byte)(var5 >> 12 & 63 | 128);
               var13 = var3 + 1;
               var11[var3] = (byte)(var5 >> 6 & 63 | 128);
               var2 = var13 + 1;
               var11[var13] = (byte)(var5 & 63 | 128);
               var3 = var4;
            } else {
               var4 = var3 + 1;
               var11[var3] = (byte)(var6 >> 12 | 224);
               var5 = var4 + 1;
               var11[var4] = (byte)(var6 >> 6 & 63 | 128);
               var11[var5] = (byte)(var6 & 63 | 128);
               var3 = var2;
               var2 = var5 + 1;
            }
         }

         this._outPtr = var4;
      }
   }
}
