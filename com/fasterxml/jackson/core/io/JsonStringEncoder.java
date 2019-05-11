package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.UTF8Writer;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.lang.ref.SoftReference;

public final class JsonStringEncoder {

   private static final byte[] HEX_BYTES = CharTypes.copyHexBytes();
   private static final char[] HEX_CHARS = CharTypes.copyHexChars();
   private static final int INT_0 = 48;
   private static final int INT_BACKSLASH = 92;
   private static final int INT_U = 117;
   private static final int SURR1_FIRST = 55296;
   private static final int SURR1_LAST = 56319;
   private static final int SURR2_FIRST = 56320;
   private static final int SURR2_LAST = 57343;
   protected static final ThreadLocal<SoftReference<JsonStringEncoder>> _threadEncoder = new ThreadLocal();
   protected ByteArrayBuilder _byteBuilder;
   protected final char[] _quoteBuffer = new char[6];
   protected TextBuffer _textBuffer;


   public JsonStringEncoder() {
      this._quoteBuffer[0] = 92;
      this._quoteBuffer[2] = 48;
      this._quoteBuffer[3] = 48;
   }

   private int _appendByteEscape(int var1, int var2, ByteArrayBuilder var3, int var4) {
      var3.setCurrentSegmentLength(var4);
      var3.append(92);
      if(var2 < 0) {
         var3.append(117);
         if(var1 > 255) {
            var2 = var1 >> 8;
            var3.append(HEX_BYTES[var2 >> 4]);
            var3.append(HEX_BYTES[var2 & 15]);
            var1 &= 255;
         } else {
            var3.append(48);
            var3.append(48);
         }

         var3.append(HEX_BYTES[var1 >> 4]);
         var3.append(HEX_BYTES[var1 & 15]);
      } else {
         var3.append((byte)var2);
      }

      return var3.getCurrentSegmentLength();
   }

   private int _appendNamedEscape(int var1, char[] var2) {
      var2[1] = (char)var1;
      return 2;
   }

   private int _appendNumericEscape(int var1, char[] var2) {
      var2[1] = 117;
      var2[4] = HEX_CHARS[var1 >> 4];
      var2[5] = HEX_CHARS[var1 & 15];
      return 6;
   }

   protected static int _convertSurrogate(int var0, int var1) {
      if(var1 >= '\udc00' && var1 <= '\udfff') {
         return (var0 - '\ud800' << 10) + 65536 + (var1 - '\udc00');
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Broken surrogate pair: first char 0x");
         var2.append(Integer.toHexString(var0));
         var2.append(", second 0x");
         var2.append(Integer.toHexString(var1));
         var2.append("; illegal combination");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   protected static void _illegalSurrogate(int var0) {
      throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(var0));
   }

   public static JsonStringEncoder getInstance() {
      SoftReference var0 = (SoftReference)_threadEncoder.get();
      JsonStringEncoder var2;
      if(var0 == null) {
         var2 = null;
      } else {
         var2 = (JsonStringEncoder)var0.get();
      }

      JsonStringEncoder var1 = var2;
      if(var2 == null) {
         var1 = new JsonStringEncoder();
         _threadEncoder.set(new SoftReference(var1));
      }

      return var1;
   }

   public byte[] encodeAsUTF8(String var1) {
      ByteArrayBuilder var10 = this._byteBuilder;
      ByteArrayBuilder var11 = var10;
      if(var10 == null) {
         var11 = new ByteArrayBuilder((BufferRecycler)null);
         this._byteBuilder = var11;
      }

      int var9 = var1.length();
      byte[] var14 = var11.resetAndGetFirstSegment();
      int var5 = var14.length;
      int var3 = 0;
      int var2 = 0;

      while(true) {
         int var4 = var2;
         if(var3 >= var9) {
            return this._byteBuilder.completeAndCoalesce(var4);
         }

         var4 = var3 + 1;
         char var7 = var1.charAt(var3);
         int var6 = var5;
         var3 = var2;

         char var12;
         int var13;
         for(var12 = var7; var12 <= 127; var6 = var2) {
            var13 = var3;
            var2 = var6;
            if(var3 >= var6) {
               var14 = var11.finishCurrentSegment();
               var2 = var14.length;
               var13 = 0;
            }

            var3 = var13 + 1;
            var14[var13] = (byte)var12;
            if(var4 >= var9) {
               var4 = var3;
               return this._byteBuilder.completeAndCoalesce(var4);
            }

            var12 = var1.charAt(var4);
            ++var4;
         }

         var13 = var3;
         var2 = var6;
         if(var3 >= var6) {
            var14 = var11.finishCurrentSegment();
            var2 = var14.length;
            var13 = 0;
         }

         if(var12 < 2048) {
            var14[var13] = (byte)(var12 >> 6 | 192);
            var3 = var13 + 1;
            var6 = var12;
         } else {
            int var8;
            if(var12 >= '\ud800' && var12 <= '\udfff') {
               if(var12 > '\udbff') {
                  _illegalSurrogate(var12);
               }

               if(var4 >= var9) {
                  _illegalSurrogate(var12);
               }

               var6 = _convertSurrogate(var12, var1.charAt(var4));
               if(var6 > 1114111) {
                  _illegalSurrogate(var6);
               }

               var8 = var13 + 1;
               var14[var13] = (byte)(var6 >> 18 | 240);
               var3 = var2;
               var5 = var8;
               if(var8 >= var2) {
                  var14 = var11.finishCurrentSegment();
                  var3 = var14.length;
                  var5 = 0;
               }

               var13 = var5 + 1;
               var14[var5] = (byte)(var6 >> 12 & 63 | 128);
               var5 = var13;
               var2 = var3;
               if(var13 >= var3) {
                  var14 = var11.finishCurrentSegment();
                  var2 = var14.length;
                  var5 = 0;
               }

               var14[var5] = (byte)(var6 >> 6 & 63 | 128);
               var3 = var5 + 1;
               ++var4;
            } else {
               var8 = var13 + 1;
               var14[var13] = (byte)(var12 >> 12 | 224);
               var3 = var2;
               var6 = var8;
               if(var8 >= var2) {
                  var14 = var11.finishCurrentSegment();
                  var3 = var14.length;
                  var6 = 0;
               }

               var13 = var6 + 1;
               var14[var6] = (byte)(var12 >> 6 & 63 | 128);
               var2 = var3;
               var3 = var13;
               var6 = var12;
            }
         }

         var13 = var3;
         var5 = var2;
         if(var3 >= var2) {
            var14 = var11.finishCurrentSegment();
            var5 = var14.length;
            var13 = 0;
         }

         var14[var13] = (byte)(var6 & 63 | 128);
         var3 = var4;
         var2 = var13 + 1;
      }
   }

   public char[] quoteAsString(String var1) {
      TextBuffer var9 = this._textBuffer;
      TextBuffer var10 = var9;
      if(var9 == null) {
         var10 = new TextBuffer((BufferRecycler)null);
         this._textBuffer = var10;
      }

      char[] var14 = var10.emptyAndGetCurrentSegment();
      int[] var12 = CharTypes.get7BitOutputEscapes();
      int var7 = var12.length;
      int var8 = var1.length();
      int var4 = 0;
      int var3 = 0;

      int var5;
      label46:
      while(true) {
         var5 = var3;
         if(var4 >= var8) {
            break;
         }

         while(true) {
            char var2 = var1.charAt(var4);
            if(var2 < var7 && var12[var2] != 0) {
               char var13 = var1.charAt(var4);
               int var6 = var12[var13];
               if(var6 < 0) {
                  var5 = this._appendNumericEscape(var13, this._quoteBuffer);
               } else {
                  var5 = this._appendNamedEscape(var6, this._quoteBuffer);
               }

               var6 = var3 + var5;
               if(var6 > var14.length) {
                  var6 = var14.length - var3;
                  if(var6 > 0) {
                     System.arraycopy(this._quoteBuffer, 0, var14, var3, var6);
                  }

                  var14 = var10.finishCurrentSegment();
                  var3 = var5 - var6;
                  System.arraycopy(this._quoteBuffer, var6, var14, 0, var3);
               } else {
                  System.arraycopy(this._quoteBuffer, 0, var14, var3, var5);
                  var3 = var6;
               }

               ++var4;
               break;
            }

            var5 = var3;
            char[] var11 = var14;
            if(var3 >= var14.length) {
               var11 = var10.finishCurrentSegment();
               var5 = 0;
            }

            var3 = var5 + 1;
            var11[var5] = var2;
            ++var4;
            if(var4 >= var8) {
               var5 = var3;
               break label46;
            }

            var14 = var11;
         }
      }

      var10.setCurrentLength(var5);
      return var10.contentsAsArray();
   }

   public byte[] quoteAsUTF8(String var1) {
      ByteArrayBuilder var8 = this._byteBuilder;
      ByteArrayBuilder var10 = var8;
      if(var8 == null) {
         var10 = new ByteArrayBuilder((BufferRecycler)null);
         this._byteBuilder = var10;
      }

      int var6 = var1.length();
      byte[] var9 = var10.resetAndGetFirstSegment();
      int var3 = 0;
      int var2 = 0;

      while(true) {
         int var4 = var2;
         if(var3 >= var6) {
            return this._byteBuilder.completeAndCoalesce(var4);
         }

         int[] var11 = CharTypes.get7BitOutputEscapes();

         while(true) {
            char var5 = var1.charAt(var3);
            byte[] var14;
            if(var5 > 127 || var11[var5] != 0) {
               int var12 = var2;
               var14 = var9;
               if(var2 >= var9.length) {
                  var14 = var10.finishCurrentSegment();
                  var12 = 0;
               }

               var4 = var3 + 1;
               char var7 = var1.charAt(var3);
               if(var7 <= 127) {
                  var2 = this._appendByteEscape(var7, var11[var7], var10, var12);
                  var9 = var10.getCurrentSegment();
               } else {
                  if(var7 <= 2047) {
                     var14[var12] = (byte)(var7 >> 6 | 192);
                     var3 = var7 & 63 | 128;
                     var2 = var12 + 1;
                  } else if(var7 >= '\ud800' && var7 <= '\udfff') {
                     if(var7 > '\udbff') {
                        _illegalSurrogate(var7);
                     }

                     if(var4 >= var6) {
                        _illegalSurrogate(var7);
                     }

                     int var13 = _convertSurrogate(var7, var1.charAt(var4));
                     if(var13 > 1114111) {
                        _illegalSurrogate(var13);
                     }

                     var3 = var12 + 1;
                     var14[var12] = (byte)(var13 >> 18 | 240);
                     var9 = var14;
                     var2 = var3;
                     if(var3 >= var14.length) {
                        var9 = var10.finishCurrentSegment();
                        var2 = 0;
                     }

                     var3 = var2 + 1;
                     var9[var2] = (byte)(var13 >> 12 & 63 | 128);
                     var2 = var3;
                     var14 = var9;
                     if(var3 >= var9.length) {
                        var14 = var10.finishCurrentSegment();
                        var2 = 0;
                     }

                     var14[var2] = (byte)(var13 >> 6 & 63 | 128);
                     var3 = var13 & 63 | 128;
                     ++var2;
                     ++var4;
                  } else {
                     var3 = var12 + 1;
                     var14[var12] = (byte)(var7 >> 12 | 224);
                     var9 = var14;
                     var2 = var3;
                     if(var3 >= var14.length) {
                        var9 = var10.finishCurrentSegment();
                        var2 = 0;
                     }

                     var3 = var2 + 1;
                     var9[var2] = (byte)(var7 >> 6 & 63 | 128);
                     var12 = var7 & 63 | 128;
                     var14 = var9;
                     var2 = var3;
                     var3 = var12;
                  }

                  var12 = var2;
                  var9 = var14;
                  if(var2 >= var14.length) {
                     var9 = var10.finishCurrentSegment();
                     var12 = 0;
                  }

                  var9[var12] = (byte)var3;
                  var2 = var12 + 1;
               }

               var3 = var4;
               break;
            }

            var4 = var2;
            var14 = var9;
            if(var2 >= var9.length) {
               var14 = var10.finishCurrentSegment();
               var4 = 0;
            }

            var2 = var4 + 1;
            var14[var4] = (byte)var5;
            ++var3;
            if(var3 >= var6) {
               var4 = var2;
               return this._byteBuilder.completeAndCoalesce(var4);
            }

            var9 = var14;
         }
      }
   }
}
