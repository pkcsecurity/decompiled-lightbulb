package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.NumberOutput;
import com.fasterxml.jackson.core.json.JsonGeneratorImpl;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class UTF8JsonGenerator extends JsonGeneratorImpl {

   private static final byte BYTE_0 = 48;
   private static final byte BYTE_BACKSLASH = 92;
   private static final byte BYTE_COLON = 58;
   private static final byte BYTE_COMMA = 44;
   private static final byte BYTE_LBRACKET = 91;
   private static final byte BYTE_LCURLY = 123;
   private static final byte BYTE_QUOTE = 34;
   private static final byte BYTE_RBRACKET = 93;
   private static final byte BYTE_RCURLY = 125;
   private static final byte BYTE_u = 117;
   private static final byte[] FALSE_BYTES = new byte[]{(byte)102, (byte)97, (byte)108, (byte)115, (byte)101};
   static final byte[] HEX_CHARS = CharTypes.copyHexBytes();
   private static final int MAX_BYTES_TO_BUFFER = 512;
   private static final byte[] NULL_BYTES = new byte[]{(byte)110, (byte)117, (byte)108, (byte)108};
   protected static final int SURR1_FIRST = 55296;
   protected static final int SURR1_LAST = 56319;
   protected static final int SURR2_FIRST = 56320;
   protected static final int SURR2_LAST = 57343;
   private static final byte[] TRUE_BYTES = new byte[]{(byte)116, (byte)114, (byte)117, (byte)101};
   protected boolean _bufferRecyclable;
   protected char[] _charBuffer;
   protected final int _charBufferLength;
   protected byte[] _entityBuffer;
   protected byte[] _outputBuffer;
   protected final int _outputEnd;
   protected final int _outputMaxContiguous;
   protected final OutputStream _outputStream;
   protected int _outputTail = 0;


   public UTF8JsonGenerator(IOContext var1, int var2, ObjectCodec var3, OutputStream var4) {
      super(var1, var2, var3);
      this._outputStream = var4;
      this._bufferRecyclable = true;
      this._outputBuffer = var1.allocWriteEncodingBuffer();
      this._outputEnd = this._outputBuffer.length;
      this._outputMaxContiguous = this._outputEnd >> 3;
      this._charBuffer = var1.allocConcatBuffer();
      this._charBufferLength = this._charBuffer.length;
      if(this.isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII)) {
         this.setHighestNonEscapedChar(127);
      }

   }

   public UTF8JsonGenerator(IOContext var1, int var2, ObjectCodec var3, OutputStream var4, byte[] var5, int var6, boolean var7) {
      super(var1, var2, var3);
      this._outputStream = var4;
      this._bufferRecyclable = var7;
      this._outputTail = var6;
      this._outputBuffer = var5;
      this._outputEnd = this._outputBuffer.length;
      this._outputMaxContiguous = this._outputEnd >> 3;
      this._charBuffer = var1.allocConcatBuffer();
      this._charBufferLength = this._charBuffer.length;
   }

   private int _handleLongCustomEscape(byte[] var1, int var2, int var3, byte[] var4, int var5) throws IOException, JsonGenerationException {
      int var7 = var4.length;
      int var6 = var2;
      if(var2 + var7 > var3) {
         this._outputTail = var2;
         this._flushBuffer();
         var2 = this._outputTail;
         if(var7 > var1.length) {
            this._outputStream.write(var4, 0, var7);
            return var2;
         }

         System.arraycopy(var4, 0, var1, var2, var7);
         var6 = var2 + var7;
      }

      if(var5 * 6 + var6 > var3) {
         this._flushBuffer();
         return this._outputTail;
      } else {
         return var6;
      }
   }

   private int _outputMultiByteChar(int var1, int var2) throws IOException {
      byte[] var4 = this._outputBuffer;
      int var3;
      if(var1 >= '\ud800' && var1 <= '\udfff') {
         var3 = var2 + 1;
         var4[var2] = 92;
         var2 = var3 + 1;
         var4[var3] = 117;
         var3 = var2 + 1;
         var4[var2] = HEX_CHARS[var1 >> 12 & 15];
         var2 = var3 + 1;
         var4[var3] = HEX_CHARS[var1 >> 8 & 15];
         var3 = var2 + 1;
         var4[var2] = HEX_CHARS[var1 >> 4 & 15];
         var4[var3] = HEX_CHARS[var1 & 15];
         return var3 + 1;
      } else {
         var3 = var2 + 1;
         var4[var2] = (byte)(var1 >> 12 | 224);
         var2 = var3 + 1;
         var4[var3] = (byte)(var1 >> 6 & 63 | 128);
         var4[var2] = (byte)(var1 & 63 | 128);
         return var2 + 1;
      }
   }

   private int _outputRawMultiByteChar(int var1, char[] var2, int var3, int var4) throws IOException {
      if(var1 >= '\ud800' && var1 <= '\udfff') {
         if(var3 >= var4) {
            this._reportError("Split surrogate on writeRaw() input (last character)");
         }

         this._outputSurrogates(var1, var2[var3]);
         return var3 + 1;
      } else {
         byte[] var5 = this._outputBuffer;
         var4 = this._outputTail;
         this._outputTail = var4 + 1;
         var5[var4] = (byte)(var1 >> 12 | 224);
         var4 = this._outputTail;
         this._outputTail = var4 + 1;
         var5[var4] = (byte)(var1 >> 6 & 63 | 128);
         var4 = this._outputTail;
         this._outputTail = var4 + 1;
         var5[var4] = (byte)(var1 & 63 | 128);
         return var3;
      }
   }

   private int _readMore(InputStream var1, byte[] var2, int var3, int var4, int var5) throws IOException {
      byte var7 = 0;
      int var6 = var3;

      for(var3 = var7; var6 < var4; ++var6) {
         var2[var3] = var2[var6];
         ++var3;
      }

      var5 = Math.min(var5, var2.length);

      do {
         var4 = var5 - var3;
         if(var4 == 0) {
            return var3;
         }

         var4 = var1.read(var2, var3, var4);
         if(var4 < 0) {
            return var3;
         }

         var4 += var3;
         var3 = var4;
      } while(var4 < 3);

      return var4;
   }

   private final void _writeBytes(byte[] var1) throws IOException {
      int var2 = var1.length;
      if(this._outputTail + var2 > this._outputEnd) {
         this._flushBuffer();
         if(var2 > 512) {
            this._outputStream.write(var1, 0, var2);
            return;
         }
      }

      System.arraycopy(var1, 0, this._outputBuffer, this._outputTail, var2);
      this._outputTail += var2;
   }

   private final void _writeBytes(byte[] var1, int var2, int var3) throws IOException {
      if(this._outputTail + var3 > this._outputEnd) {
         this._flushBuffer();
         if(var3 > 512) {
            this._outputStream.write(var1, var2, var3);
            return;
         }
      }

      System.arraycopy(var1, var2, this._outputBuffer, this._outputTail, var3);
      this._outputTail += var3;
   }

   private int _writeCustomEscape(byte[] var1, int var2, SerializableString var3, int var4) throws IOException, JsonGenerationException {
      byte[] var6 = var3.asUnquotedUTF8();
      int var5 = var6.length;
      if(var5 > 6) {
         return this._handleLongCustomEscape(var1, var2, this._outputEnd, var6, var4);
      } else {
         System.arraycopy(var6, 0, var1, var2, var5);
         return var2 + var5;
      }
   }

   private void _writeCustomStringSegment2(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      if(this._outputTail + (var3 - var2) * 6 > this._outputEnd) {
         this._flushBuffer();
      }

      int var6 = this._outputTail;
      byte[] var8 = this._outputBuffer;
      int[] var9 = this._outputEscapes;
      int var5;
      if(this._maximumNonEscapedChar <= 0) {
         var5 = '\uffff';
      } else {
         var5 = this._maximumNonEscapedChar;
      }

      CharacterEscapes var10 = this._characterEscapes;
      int var4 = var2;
      var2 = var6;

      while(var4 < var3) {
         var6 = var4 + 1;
         char var13 = var1[var4];
         int var7;
         SerializableString var11;
         if(var13 <= 127) {
            if(var9[var13] == 0) {
               var8[var2] = (byte)var13;
               var4 = var6;
               ++var2;
               continue;
            }

            var7 = var9[var13];
            if(var7 > 0) {
               var4 = var2 + 1;
               var8[var2] = 92;
               var2 = var4 + 1;
               var8[var4] = (byte)var7;
            } else if(var7 == -2) {
               var11 = var10.getEscapeSequence(var13);
               if(var11 == null) {
                  StringBuilder var12 = new StringBuilder();
                  var12.append("Invalid custom escape definitions; custom escape not found for character code 0x");
                  var12.append(Integer.toHexString(var13));
                  var12.append(", although was supposed to have one");
                  this._reportError(var12.toString());
               }

               var2 = this._writeCustomEscape(var8, var2, var11, var3 - var6);
            } else {
               var2 = this._writeGenericEscape(var13, var2);
            }
         } else if(var13 > var5) {
            var2 = this._writeGenericEscape(var13, var2);
         } else {
            var11 = var10.getEscapeSequence(var13);
            if(var11 != null) {
               var2 = this._writeCustomEscape(var8, var2, var11, var3 - var6);
            } else if(var13 <= 2047) {
               var7 = var2 + 1;
               var8[var2] = (byte)(var13 >> 6 | 192);
               var2 = var7 + 1;
               var8[var7] = (byte)(var13 & 63 | 128);
            } else {
               var2 = this._outputMultiByteChar(var13, var2);
            }
         }

         var4 = var6;
      }

      this._outputTail = var2;
   }

   private int _writeGenericEscape(int var1, int var2) throws IOException {
      byte[] var5 = this._outputBuffer;
      int var3 = var2 + 1;
      var5[var2] = 92;
      var2 = var3 + 1;
      var5[var3] = 117;
      if(var1 > 255) {
         var3 = 255 & var1 >> 8;
         int var4 = var2 + 1;
         var5[var2] = HEX_CHARS[var3 >> 4];
         var2 = var4 + 1;
         var5[var4] = HEX_CHARS[var3 & 15];
         var1 &= 255;
      } else {
         var3 = var2 + 1;
         var5[var2] = 48;
         var2 = var3 + 1;
         var5[var3] = 48;
      }

      var3 = var2 + 1;
      var5[var2] = HEX_CHARS[var1 >> 4];
      var5[var3] = HEX_CHARS[var1 & 15];
      return var3 + 1;
   }

   private void _writeLongString(String var1) throws IOException, JsonGenerationException {
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var3 = this._outputBuffer;
      int var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var3[var2] = 34;
      this._writeStringSegments(var1);
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var4 = this._outputBuffer;
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var4[var2] = 34;
   }

   private void _writeLongString(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var4 = this._outputBuffer;
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var4[var2] = 34;
      this._writeStringSegments(this._charBuffer, 0, var3);
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      var4 = this._outputBuffer;
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var4[var2] = 34;
   }

   private void _writeNull() throws IOException {
      if(this._outputTail + 4 >= this._outputEnd) {
         this._flushBuffer();
      }

      System.arraycopy(NULL_BYTES, 0, this._outputBuffer, this._outputTail, 4);
      this._outputTail += 4;
   }

   private void _writeQuotedInt(int var1) throws IOException {
      if(this._outputTail + 13 >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var3 = this._outputBuffer;
      int var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var3[var2] = 34;
      this._outputTail = NumberOutput.outputInt(var1, this._outputBuffer, this._outputTail);
      var3 = this._outputBuffer;
      var1 = this._outputTail;
      this._outputTail = var1 + 1;
      var3[var1] = 34;
   }

   private void _writeQuotedLong(long var1) throws IOException {
      if(this._outputTail + 23 >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var4 = this._outputBuffer;
      int var3 = this._outputTail;
      this._outputTail = var3 + 1;
      var4[var3] = 34;
      this._outputTail = NumberOutput.outputLong(var1, this._outputBuffer, this._outputTail);
      var4 = this._outputBuffer;
      var3 = this._outputTail;
      this._outputTail = var3 + 1;
      var4[var3] = 34;
   }

   private void _writeQuotedRaw(Object var1) throws IOException {
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var3 = this._outputBuffer;
      int var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var3[var2] = 34;
      this.writeRaw(var1.toString());
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var4 = this._outputBuffer;
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var4[var2] = 34;
   }

   private void _writeQuotedShort(short var1) throws IOException {
      if(this._outputTail + 8 >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var3 = this._outputBuffer;
      int var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var3[var2] = 34;
      this._outputTail = NumberOutput.outputInt(var1, this._outputBuffer, this._outputTail);
      var3 = this._outputBuffer;
      int var4 = this._outputTail;
      this._outputTail = var4 + 1;
      var3[var4] = 34;
   }

   private final void _writeSegmentedRaw(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int var5 = this._outputEnd;

      int var9;
      label32:
      for(byte[] var7 = this._outputBuffer; var2 < var3; var2 = var9) {
         do {
            char var4 = var1[var2];
            int var6;
            if(var4 >= 128) {
               if(this._outputTail + 3 >= this._outputEnd) {
                  this._flushBuffer();
               }

               var9 = var2 + 1;
               char var8 = var1[var2];
               if(var8 < 2048) {
                  var6 = this._outputTail;
                  this._outputTail = var6 + 1;
                  var7[var6] = (byte)(var8 >> 6 | 192);
                  var6 = this._outputTail;
                  this._outputTail = var6 + 1;
                  var7[var6] = (byte)(var8 & 63 | 128);
               } else {
                  this._outputRawMultiByteChar(var8, var1, var9, var3);
               }
               continue label32;
            }

            if(this._outputTail >= var5) {
               this._flushBuffer();
            }

            var6 = this._outputTail;
            this._outputTail = var6 + 1;
            var7[var6] = (byte)var4;
            var9 = var2 + 1;
            var2 = var9;
         } while(var9 < var3);

         return;
      }

   }

   private final void _writeStringSegment(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int var5 = var3 + var2;
      int var4 = this._outputTail;
      byte[] var6 = this._outputBuffer;
      int[] var7 = this._outputEscapes;
      var3 = var2;

      for(var2 = var4; var3 < var5; ++var2) {
         char var8 = var1[var3];
         if(var8 > 127 || var7[var8] != 0) {
            break;
         }

         var6[var2] = (byte)var8;
         ++var3;
      }

      this._outputTail = var2;
      if(var3 < var5) {
         if(this._characterEscapes != null) {
            this._writeCustomStringSegment2(var1, var3, var5);
            return;
         }

         if(this._maximumNonEscapedChar == 0) {
            this._writeStringSegment2(var1, var3, var5);
            return;
         }

         this._writeStringSegmentASCII2(var1, var3, var5);
      }

   }

   private final void _writeStringSegment2(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      if(this._outputTail + (var3 - var2) * 6 > this._outputEnd) {
         this._flushBuffer();
      }

      int var5 = this._outputTail;
      byte[] var7 = this._outputBuffer;
      int[] var8 = this._outputEscapes;
      int var4 = var2;
      var2 = var5;

      while(var4 < var3) {
         var5 = var4 + 1;
         char var9 = var1[var4];
         int var6;
         if(var9 <= 127) {
            if(var8[var9] == 0) {
               var7[var2] = (byte)var9;
               var4 = var5;
               ++var2;
               continue;
            }

            var6 = var8[var9];
            if(var6 > 0) {
               var4 = var2 + 1;
               var7[var2] = 92;
               var2 = var4 + 1;
               var7[var4] = (byte)var6;
            } else {
               var2 = this._writeGenericEscape(var9, var2);
            }
         } else if(var9 <= 2047) {
            var6 = var2 + 1;
            var7[var2] = (byte)(var9 >> 6 | 192);
            var2 = var6 + 1;
            var7[var6] = (byte)(var9 & 63 | 128);
         } else {
            var2 = this._outputMultiByteChar(var9, var2);
         }

         var4 = var5;
      }

      this._outputTail = var2;
   }

   private final void _writeStringSegmentASCII2(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      if(this._outputTail + (var3 - var2) * 6 > this._outputEnd) {
         this._flushBuffer();
      }

      int var5 = this._outputTail;
      byte[] var8 = this._outputBuffer;
      int[] var9 = this._outputEscapes;
      int var6 = this._maximumNonEscapedChar;
      int var4 = var2;
      var2 = var5;

      while(var4 < var3) {
         var5 = var4 + 1;
         char var10 = var1[var4];
         int var7;
         if(var10 <= 127) {
            if(var9[var10] == 0) {
               var8[var2] = (byte)var10;
               var4 = var5;
               ++var2;
               continue;
            }

            var7 = var9[var10];
            if(var7 > 0) {
               var4 = var2 + 1;
               var8[var2] = 92;
               var2 = var4 + 1;
               var8[var4] = (byte)var7;
            } else {
               var2 = this._writeGenericEscape(var10, var2);
            }
         } else if(var10 > var6) {
            var2 = this._writeGenericEscape(var10, var2);
         } else if(var10 <= 2047) {
            var7 = var2 + 1;
            var8[var2] = (byte)(var10 >> 6 | 192);
            var2 = var7 + 1;
            var8[var7] = (byte)(var10 & 63 | 128);
         } else {
            var2 = this._outputMultiByteChar(var10, var2);
         }

         var4 = var5;
      }

      this._outputTail = var2;
   }

   private final void _writeStringSegments(String var1) throws IOException, JsonGenerationException {
      int var2 = var1.length();
      char[] var6 = this._charBuffer;

      int var4;
      for(int var3 = 0; var2 > 0; var3 = var4) {
         int var5 = Math.min(this._outputMaxContiguous, var2);
         var4 = var3 + var5;
         var1.getChars(var3, var4, var6, 0);
         if(this._outputTail + var5 > this._outputEnd) {
            this._flushBuffer();
         }

         this._writeStringSegment(var6, 0, var5);
         var2 -= var5;
      }

   }

   private final void _writeStringSegments(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int var4;
      do {
         var4 = Math.min(this._outputMaxContiguous, var3);
         if(this._outputTail + var4 > this._outputEnd) {
            this._flushBuffer();
         }

         this._writeStringSegment(var1, var2, var4);
         var2 += var4;
         var4 = var3 - var4;
         var3 = var4;
      } while(var4 > 0);

   }

   private void _writeUTF8Segment(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int[] var6 = this._outputEscapes;

      for(int var4 = var2; var4 < var2 + var3; ++var4) {
         byte var5 = var1[var4];
         if(var5 >= 0 && var6[var5] != 0) {
            this._writeUTF8Segment2(var1, var2, var3);
            return;
         }
      }

      if(this._outputTail + var3 > this._outputEnd) {
         this._flushBuffer();
      }

      System.arraycopy(var1, var2, this._outputBuffer, this._outputTail, var3);
      this._outputTail += var3;
   }

   private void _writeUTF8Segment2(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int var5 = this._outputTail;
      int var6 = var5;
      if(var3 * 6 + var5 > this._outputEnd) {
         this._flushBuffer();
         var6 = this._outputTail;
      }

      byte[] var9 = this._outputBuffer;
      int[] var10 = this._outputEscapes;
      var5 = var2;

      while(true) {
         int var7 = var5;
         if(var5 >= var3 + var2) {
            this._outputTail = var6;
            return;
         }

         ++var5;
         byte var4 = var1[var7];
         if(var4 >= 0 && var10[var4] != 0) {
            var7 = var10[var4];
            if(var7 > 0) {
               int var8 = var6 + 1;
               var9[var6] = 92;
               var6 = var8 + 1;
               var9[var8] = (byte)var7;
            } else {
               var6 = this._writeGenericEscape(var4, var6);
            }
         } else {
            var9[var6] = var4;
            ++var6;
         }
      }
   }

   private void _writeUTF8Segments(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int var4;
      do {
         var4 = Math.min(this._outputMaxContiguous, var3);
         this._writeUTF8Segment(var1, var2, var4);
         var2 += var4;
         var4 = var3 - var4;
         var3 = var4;
      } while(var4 > 0);

   }

   protected final int _decodeSurrogate(int var1, int var2) throws IOException {
      if(var2 < '\udc00' || var2 > '\udfff') {
         StringBuilder var3 = new StringBuilder();
         var3.append("Incomplete surrogate pair: first char 0x");
         var3.append(Integer.toHexString(var1));
         var3.append(", second 0x");
         var3.append(Integer.toHexString(var2));
         this._reportError(var3.toString());
      }

      return (var1 - '\ud800' << 10) + 65536 + (var2 - '\udc00');
   }

   protected final void _flushBuffer() throws IOException {
      int var1 = this._outputTail;
      if(var1 > 0) {
         this._outputTail = 0;
         this._outputStream.write(this._outputBuffer, 0, var1);
      }

   }

   protected final void _outputSurrogates(int var1, int var2) throws IOException {
      var1 = this._decodeSurrogate(var1, var2);
      if(this._outputTail + 4 > this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var3 = this._outputBuffer;
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var3[var2] = (byte)(var1 >> 18 | 240);
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var3[var2] = (byte)(var1 >> 12 & 63 | 128);
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var3[var2] = (byte)(var1 >> 6 & 63 | 128);
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var3[var2] = (byte)(var1 & 63 | 128);
   }

   protected void _releaseBuffers() {
      byte[] var1 = this._outputBuffer;
      if(var1 != null && this._bufferRecyclable) {
         this._outputBuffer = null;
         this._ioContext.releaseWriteEncodingBuffer(var1);
      }

      char[] var2 = this._charBuffer;
      if(var2 != null) {
         this._charBuffer = null;
         this._ioContext.releaseConcatBuffer(var2);
      }

   }

   protected final void _verifyPrettyValueWrite(String var1, int var2) throws IOException, JsonGenerationException {
      switch(var2) {
      case 0:
         if(this._writeContext.inArray()) {
            this._cfgPrettyPrinter.beforeArrayValues(this);
            return;
         }

         if(this._writeContext.inObject()) {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
         }

         return;
      case 1:
         this._cfgPrettyPrinter.writeArrayValueSeparator(this);
         return;
      case 2:
         this._cfgPrettyPrinter.writeObjectFieldValueSeparator(this);
         return;
      case 3:
         this._cfgPrettyPrinter.writeRootValueSeparator(this);
         return;
      default:
         this._throwInternal();
      }
   }

   protected final void _verifyValueWrite(String var1) throws IOException, JsonGenerationException {
      int var3 = this._writeContext.writeValue();
      if(var3 == 5) {
         StringBuilder var4 = new StringBuilder();
         var4.append("Can not ");
         var4.append(var1);
         var4.append(", expecting field name");
         this._reportError(var4.toString());
      }

      if(this._cfgPrettyPrinter == null) {
         byte var2;
         switch(var3) {
         case 1:
            var2 = 44;
            break;
         case 2:
            var2 = 58;
            break;
         case 3:
            if(this._rootValueSeparator != null) {
               byte[] var5 = this._rootValueSeparator.asUnquotedUTF8();
               if(var5.length > 0) {
                  this._writeBytes(var5);
               }
            }

            return;
         default:
            return;
         }

         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail] = var2;
         ++this._outputTail;
      } else {
         this._verifyPrettyValueWrite(var1, var3);
      }
   }

   protected int _writeBinary(Base64Variant var1, InputStream var2, byte[] var3) throws IOException, JsonGenerationException {
      int var14 = this._outputEnd - 6;
      int var4 = var1.getMaxLineLength();
      int var10 = -3;
      int var5 = var4 >> 2;
      var4 = 0;
      int var6 = 0;
      int var9 = 0;

      while(true) {
         int var8 = var10;
         int var11 = var6;
         int var7 = var9;
         if(var6 > var10) {
            var8 = this._readMore(var2, var3, var6, var9, var3.length);
            if(var8 < 3) {
               var5 = var4;
               if(var8 > 0) {
                  if(this._outputTail > var14) {
                     this._flushBuffer();
                  }

                  var7 = var3[0] << 16;
                  byte var17 = 1;
                  var5 = var7;
                  if(1 < var8) {
                     var5 = var7 | (var3[1] & 255) << 8;
                     var17 = 2;
                  }

                  var4 += var17;
                  this._outputTail = var1.encodeBase64Partial(var5, var17, this._outputBuffer, this._outputTail);
                  var5 = var4;
               }

               return var5;
            }

            var7 = var8;
            var8 -= 3;
            var11 = 0;
         }

         if(this._outputTail > var14) {
            this._flushBuffer();
         }

         var9 = var11 + 1;
         byte var16 = var3[var11];
         var10 = var9 + 1;
         byte var18 = var3[var9];
         var11 = var10 + 1;
         byte var19 = var3[var10];
         int var12 = var4 + 3;
         this._outputTail = var1.encodeBase64Chunk((var18 & 255 | var16 << 8) << 8 | var19 & 255, this._outputBuffer, this._outputTail);
         int var13 = var5 - 1;
         var4 = var12;
         var10 = var8;
         var6 = var11;
         var9 = var7;
         var5 = var13;
         if(var13 <= 0) {
            byte[] var15 = this._outputBuffer;
            var4 = this._outputTail;
            this._outputTail = var4 + 1;
            var15[var4] = 92;
            var15 = this._outputBuffer;
            var4 = this._outputTail;
            this._outputTail = var4 + 1;
            var15[var4] = 110;
            var5 = var1.getMaxLineLength() >> 2;
            var4 = var12;
            var10 = var8;
            var6 = var11;
            var9 = var7;
         }
      }
   }

   protected int _writeBinary(Base64Variant var1, InputStream var2, byte[] var3, int var4) throws IOException, JsonGenerationException {
      int var14 = this._outputEnd - 6;
      int var6 = var1.getMaxLineLength() >> 2;
      int var10 = -3;
      byte var8 = 0;
      int var7 = 0;
      int var5 = var4;
      var4 = var8;

      int var9;
      int var19;
      while(true) {
         var19 = var4;
         var9 = var7;
         if(var5 <= 2) {
            break;
         }

         var9 = var10;
         int var11 = var4;
         var19 = var7;
         if(var4 > var10) {
            var4 = this._readMore(var2, var3, var4, var7, var5);
            if(var4 < 3) {
               var19 = 0;
               var9 = var4;
               break;
            }

            var19 = var4;
            var9 = var4 - 3;
            var11 = 0;
         }

         if(this._outputTail > var14) {
            this._flushBuffer();
         }

         var7 = var11 + 1;
         byte var16 = var3[var11];
         var10 = var7 + 1;
         byte var18 = var3[var7];
         var11 = var10 + 1;
         byte var20 = var3[var10];
         int var12 = var5 - 3;
         this._outputTail = var1.encodeBase64Chunk((var18 & 255 | var16 << 8) << 8 | var20 & 255, this._outputBuffer, this._outputTail);
         int var13 = var6 - 1;
         var6 = var13;
         var10 = var9;
         var4 = var11;
         var7 = var19;
         var5 = var12;
         if(var13 <= 0) {
            byte[] var15 = this._outputBuffer;
            var4 = this._outputTail;
            this._outputTail = var4 + 1;
            var15[var4] = 92;
            var15 = this._outputBuffer;
            var4 = this._outputTail;
            this._outputTail = var4 + 1;
            var15[var4] = 110;
            var6 = var1.getMaxLineLength() >> 2;
            var10 = var9;
            var4 = var11;
            var7 = var19;
            var5 = var12;
         }
      }

      var4 = var5;
      if(var5 > 0) {
         var19 = this._readMore(var2, var3, var19, var9, var5);
         var4 = var5;
         if(var19 > 0) {
            if(this._outputTail > var14) {
               this._flushBuffer();
            }

            var7 = var3[0] << 16;
            byte var17 = 1;
            var4 = var7;
            if(1 < var19) {
               var4 = var7 | (var3[1] & 255) << 8;
               var17 = 2;
            }

            this._outputTail = var1.encodeBase64Partial(var4, var17, this._outputBuffer, this._outputTail);
            var4 = var5 - var17;
         }
      }

      return var4;
   }

   protected void _writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException {
      int var7 = this._outputEnd - 6;

      int var5;
      int var6;
      for(var5 = var1.getMaxLineLength() >> 2; var3 <= var4 - 3; var3 = var6) {
         if(this._outputTail > var7) {
            this._flushBuffer();
         }

         int var8 = var3 + 1;
         byte var10 = var2[var3];
         var6 = var8 + 1;
         this._outputTail = var1.encodeBase64Chunk((var10 << 8 | var2[var8] & 255) << 8 | var2[var6] & 255, this._outputBuffer, this._outputTail);
         --var5;
         var3 = var5;
         if(var5 <= 0) {
            byte[] var9 = this._outputBuffer;
            var3 = this._outputTail;
            this._outputTail = var3 + 1;
            var9[var3] = 92;
            var9 = this._outputBuffer;
            var3 = this._outputTail;
            this._outputTail = var3 + 1;
            var9[var3] = 110;
            var3 = var1.getMaxLineLength() >> 2;
         }

         ++var6;
         var5 = var3;
      }

      var6 = var4 - var3;
      if(var6 > 0) {
         if(this._outputTail > var7) {
            this._flushBuffer();
         }

         var5 = var2[var3] << 16;
         var4 = var5;
         if(var6 == 2) {
            var4 = var5 | (var2[var3 + 1] & 255) << 8;
         }

         this._outputTail = var1.encodeBase64Partial(var4, var6, this._outputBuffer, this._outputTail);
      }

   }

   protected final void _writeFieldName(SerializableString var1) throws IOException, JsonGenerationException {
      int var2;
      if(!this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
         var2 = var1.appendQuotedUTF8(this._outputBuffer, this._outputTail);
         if(var2 < 0) {
            this._writeBytes(var1.asQuotedUTF8());
         } else {
            this._outputTail += var2;
         }
      } else {
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var3 = this._outputBuffer;
         var2 = this._outputTail;
         this._outputTail = var2 + 1;
         var3[var2] = 34;
         var2 = var1.appendQuotedUTF8(this._outputBuffer, this._outputTail);
         if(var2 < 0) {
            this._writeBytes(var1.asQuotedUTF8());
         } else {
            this._outputTail += var2;
         }

         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var4 = this._outputBuffer;
         var2 = this._outputTail;
         this._outputTail = var2 + 1;
         var4[var2] = 34;
      }
   }

   protected final void _writeFieldName(String var1) throws IOException, JsonGenerationException {
      if(!this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
         this._writeStringSegments(var1);
      } else {
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var3 = this._outputBuffer;
         int var2 = this._outputTail;
         this._outputTail = var2 + 1;
         var3[var2] = 34;
         var2 = var1.length();
         if(var2 <= this._charBufferLength) {
            var1.getChars(0, var2, this._charBuffer, 0);
            if(var2 <= this._outputMaxContiguous) {
               if(this._outputTail + var2 > this._outputEnd) {
                  this._flushBuffer();
               }

               this._writeStringSegment(this._charBuffer, 0, var2);
            } else {
               this._writeStringSegments(this._charBuffer, 0, var2);
            }
         } else {
            this._writeStringSegments(var1);
         }

         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var4 = this._outputBuffer;
         var2 = this._outputTail;
         this._outputTail = var2 + 1;
         var4[var2] = 34;
      }
   }

   protected final void _writePPFieldName(SerializableString var1, boolean var2) throws IOException, JsonGenerationException {
      if(var2) {
         this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
      } else {
         this._cfgPrettyPrinter.beforeObjectEntries(this);
      }

      var2 = this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES);
      int var3;
      if(var2) {
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var4 = this._outputBuffer;
         var3 = this._outputTail;
         this._outputTail = var3 + 1;
         var4[var3] = 34;
      }

      this._writeBytes(var1.asQuotedUTF8());
      if(var2) {
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var5 = this._outputBuffer;
         var3 = this._outputTail;
         this._outputTail = var3 + 1;
         var5[var3] = 34;
      }

   }

   protected final void _writePPFieldName(String var1, boolean var2) throws IOException, JsonGenerationException {
      if(var2) {
         this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
      } else {
         this._cfgPrettyPrinter.beforeObjectEntries(this);
      }

      if(this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var4 = this._outputBuffer;
         int var3 = this._outputTail;
         this._outputTail = var3 + 1;
         var4[var3] = 34;
         var3 = var1.length();
         if(var3 <= this._charBufferLength) {
            var1.getChars(0, var3, this._charBuffer, 0);
            if(var3 <= this._outputMaxContiguous) {
               if(this._outputTail + var3 > this._outputEnd) {
                  this._flushBuffer();
               }

               this._writeStringSegment(this._charBuffer, 0, var3);
            } else {
               this._writeStringSegments(this._charBuffer, 0, var3);
            }
         } else {
            this._writeStringSegments(var1);
         }

         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var5 = this._outputBuffer;
         var3 = this._outputTail;
         this._outputTail = var3 + 1;
         var5[var3] = 34;
      } else {
         this._writeStringSegments(var1);
      }
   }

   public void close() throws IOException {
      super.close();
      if(this._outputBuffer != null && this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT)) {
         label33:
         while(true) {
            while(true) {
               JsonWriteContext var1 = this.getOutputContext();
               if(!var1.inArray()) {
                  if(!var1.inObject()) {
                     break label33;
                  }

                  this.writeEndObject();
               } else {
                  this.writeEndArray();
               }
            }
         }
      }

      this._flushBuffer();
      if(this._outputStream != null) {
         if(!this._ioContext.isResourceManaged() && !this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
            if(this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
               this._outputStream.flush();
            }
         } else {
            this._outputStream.close();
         }
      }

      this._releaseBuffers();
   }

   public final void flush() throws IOException {
      this._flushBuffer();
      if(this._outputStream != null && this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
         this._outputStream.flush();
      }

   }

   public Object getOutputTarget() {
      return this._outputStream;
   }

   public int writeBinary(Base64Variant param1, InputStream param2, int param3) throws IOException, JsonGenerationException {
      // $FF: Couldn't be decompiled
   }

   public void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write binary value");
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var6 = this._outputBuffer;
      int var5 = this._outputTail;
      this._outputTail = var5 + 1;
      var6[var5] = 34;
      this._writeBinary(var1, var2, var3, var4 + var3);
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var7 = this._outputBuffer;
      var3 = this._outputTail;
      this._outputTail = var3 + 1;
      var7[var3] = 34;
   }

   public void writeBoolean(boolean var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write boolean value");
      if(this._outputTail + 5 >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var3;
      if(var1) {
         var3 = TRUE_BYTES;
      } else {
         var3 = FALSE_BYTES;
      }

      int var2 = var3.length;
      System.arraycopy(var3, 0, this._outputBuffer, this._outputTail, var2);
      this._outputTail += var2;
   }

   public final void writeEndArray() throws IOException, JsonGenerationException {
      if(!this._writeContext.inArray()) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Current context not an ARRAY but ");
         var2.append(this._writeContext.getTypeDesc());
         this._reportError(var2.toString());
      }

      if(this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
      } else {
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var3 = this._outputBuffer;
         int var1 = this._outputTail;
         this._outputTail = var1 + 1;
         var3[var1] = 93;
      }

      this._writeContext = this._writeContext.getParent();
   }

   public final void writeEndObject() throws IOException, JsonGenerationException {
      if(!this._writeContext.inObject()) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Current context not an object but ");
         var2.append(this._writeContext.getTypeDesc());
         this._reportError(var2.toString());
      }

      if(this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
      } else {
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var3 = this._outputBuffer;
         int var1 = this._outputTail;
         this._outputTail = var1 + 1;
         var3[var1] = 125;
      }

      this._writeContext = this._writeContext.getParent();
   }

   public final void writeFieldName(SerializableString var1) throws IOException, JsonGenerationException {
      int var2 = this._writeContext.writeFieldName(var1.getValue());
      if(var2 == 4) {
         this._reportError("Can not write a field name, expecting a value");
      }

      PrettyPrinter var4 = this._cfgPrettyPrinter;
      boolean var3 = true;
      if(var4 != null) {
         if(var2 != 1) {
            var3 = false;
         }

         this._writePPFieldName(var1, var3);
      } else {
         if(var2 == 1) {
            if(this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            byte[] var5 = this._outputBuffer;
            var2 = this._outputTail;
            this._outputTail = var2 + 1;
            var5[var2] = 44;
         }

         this._writeFieldName(var1);
      }
   }

   public final void writeFieldName(String var1) throws IOException, JsonGenerationException {
      int var2 = this._writeContext.writeFieldName(var1);
      if(var2 == 4) {
         this._reportError("Can not write a field name, expecting a value");
      }

      PrettyPrinter var4 = this._cfgPrettyPrinter;
      boolean var3 = true;
      if(var4 != null) {
         if(var2 != 1) {
            var3 = false;
         }

         this._writePPFieldName(var1, var3);
      } else {
         if(var2 == 1) {
            if(this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            byte[] var5 = this._outputBuffer;
            var2 = this._outputTail;
            this._outputTail = var2 + 1;
            var5[var2] = 44;
         }

         this._writeFieldName(var1);
      }
   }

   public void writeNull() throws IOException, JsonGenerationException {
      this._verifyValueWrite("write null value");
      this._writeNull();
   }

   public void writeNumber(double var1) throws IOException, JsonGenerationException {
      if(!this._cfgNumbersAsStrings && (!Double.isNaN(var1) && !Double.isInfinite(var1) || !this.isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS))) {
         this._verifyValueWrite("write number");
         this.writeRaw(String.valueOf(var1));
      } else {
         this.writeString(String.valueOf(var1));
      }
   }

   public void writeNumber(float var1) throws IOException, JsonGenerationException {
      if(!this._cfgNumbersAsStrings && (!Float.isNaN(var1) && !Float.isInfinite(var1) || !this.isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS))) {
         this._verifyValueWrite("write number");
         this.writeRaw(String.valueOf(var1));
      } else {
         this.writeString(String.valueOf(var1));
      }
   }

   public void writeNumber(int var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write number");
      if(this._outputTail + 11 >= this._outputEnd) {
         this._flushBuffer();
      }

      if(this._cfgNumbersAsStrings) {
         this._writeQuotedInt(var1);
      } else {
         this._outputTail = NumberOutput.outputInt(var1, this._outputBuffer, this._outputTail);
      }
   }

   public void writeNumber(long var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write number");
      if(this._cfgNumbersAsStrings) {
         this._writeQuotedLong(var1);
      } else {
         if(this._outputTail + 21 >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputTail = NumberOutput.outputLong(var1, this._outputBuffer, this._outputTail);
      }
   }

   public void writeNumber(String var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write number");
      if(this._cfgNumbersAsStrings) {
         this._writeQuotedRaw(var1);
      } else {
         this.writeRaw(var1);
      }
   }

   public void writeNumber(BigDecimal var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write number");
      if(var1 == null) {
         this._writeNull();
      } else if(this._cfgNumbersAsStrings) {
         this._writeQuotedRaw(var1);
      } else {
         this.writeRaw(var1.toString());
      }
   }

   public void writeNumber(BigInteger var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write number");
      if(var1 == null) {
         this._writeNull();
      } else if(this._cfgNumbersAsStrings) {
         this._writeQuotedRaw(var1);
      } else {
         this.writeRaw(var1.toString());
      }
   }

   public void writeNumber(short var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write number");
      if(this._outputTail + 6 >= this._outputEnd) {
         this._flushBuffer();
      }

      if(this._cfgNumbersAsStrings) {
         this._writeQuotedShort(var1);
      } else {
         this._outputTail = NumberOutput.outputInt(var1, this._outputBuffer, this._outputTail);
      }
   }

   public void writeRaw(char var1) throws IOException, JsonGenerationException {
      if(this._outputTail + 3 >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var3 = this._outputBuffer;
      int var2;
      if(var1 <= 127) {
         var2 = this._outputTail;
         this._outputTail = var2 + 1;
         var3[var2] = (byte)var1;
      } else if(var1 < 2048) {
         var2 = this._outputTail;
         this._outputTail = var2 + 1;
         var3[var2] = (byte)(var1 >> 6 | 192);
         var2 = this._outputTail;
         this._outputTail = var2 + 1;
         var3[var2] = (byte)(var1 & 63 | 128);
      } else {
         this._outputRawMultiByteChar(var1, (char[])null, 0, 0);
      }
   }

   public void writeRaw(SerializableString var1) throws IOException, JsonGenerationException {
      byte[] var2 = var1.asUnquotedUTF8();
      if(var2.length > 0) {
         this._writeBytes(var2);
      }

   }

   public void writeRaw(String var1) throws IOException, JsonGenerationException {
      int var2 = var1.length();

      int var5;
      for(int var3 = 0; var2 > 0; var3 = var5) {
         char[] var6 = this._charBuffer;
         var5 = var6.length;
         int var4 = var5;
         if(var2 < var5) {
            var4 = var2;
         }

         var5 = var3 + var4;
         var1.getChars(var3, var5, var6, 0);
         this.writeRaw(var6, 0, var4);
         var2 -= var4;
      }

   }

   public void writeRaw(String var1, int var2, int var3) throws IOException, JsonGenerationException {
      while(var3 > 0) {
         char[] var6 = this._charBuffer;
         int var5 = var6.length;
         int var4 = var5;
         if(var3 < var5) {
            var4 = var3;
         }

         var5 = var2 + var4;
         var1.getChars(var2, var5, var6, 0);
         this.writeRaw(var6, 0, var4);
         var3 -= var4;
         var2 = var5;
      }

   }

   public final void writeRaw(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int var4 = var3 + var3 + var3;
      if(this._outputTail + var4 > this._outputEnd) {
         if(this._outputEnd < var4) {
            this._writeSegmentedRaw(var1, var2, var3);
            return;
         }

         this._flushBuffer();
      }

      var4 = var3 + var2;

      while(var2 < var4) {
         while(true) {
            char var8 = var1[var2];
            int var5;
            byte[] var6;
            if(var8 > 127) {
               var3 = var2 + 1;
               char var7 = var1[var2];
               if(var7 < 2048) {
                  var6 = this._outputBuffer;
                  var5 = this._outputTail;
                  this._outputTail = var5 + 1;
                  var6[var5] = (byte)(var7 >> 6 | 192);
                  var6 = this._outputBuffer;
                  var5 = this._outputTail;
                  this._outputTail = var5 + 1;
                  var6[var5] = (byte)(var7 & 63 | 128);
               } else {
                  this._outputRawMultiByteChar(var7, var1, var3, var4);
               }

               var2 = var3;
            } else {
               var6 = this._outputBuffer;
               var5 = this._outputTail;
               this._outputTail = var5 + 1;
               var6[var5] = (byte)var8;
               var3 = var2 + 1;
               var2 = var3;
               if(var3 >= var4) {
                  return;
               }
            }
         }
      }

   }

   public void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write text value");
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var5 = this._outputBuffer;
      int var4 = this._outputTail;
      this._outputTail = var4 + 1;
      var5[var4] = 34;
      this._writeBytes(var1, var2, var3);
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      var1 = this._outputBuffer;
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var1[var2] = 34;
   }

   public final void writeStartArray() throws IOException, JsonGenerationException {
      this._verifyValueWrite("start an array");
      this._writeContext = this._writeContext.createChildArrayContext();
      if(this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartArray(this);
      } else {
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var2 = this._outputBuffer;
         int var1 = this._outputTail;
         this._outputTail = var1 + 1;
         var2[var1] = 91;
      }
   }

   public final void writeStartObject() throws IOException, JsonGenerationException {
      this._verifyValueWrite("start an object");
      this._writeContext = this._writeContext.createChildObjectContext();
      if(this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartObject(this);
      } else {
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var2 = this._outputBuffer;
         int var1 = this._outputTail;
         this._outputTail = var1 + 1;
         var2[var1] = 123;
      }
   }

   public final void writeString(SerializableString var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write text value");
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var3 = this._outputBuffer;
      int var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var3[var2] = 34;
      var2 = var1.appendQuotedUTF8(this._outputBuffer, this._outputTail);
      if(var2 < 0) {
         this._writeBytes(var1.asQuotedUTF8());
      } else {
         this._outputTail += var2;
      }

      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var4 = this._outputBuffer;
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var4[var2] = 34;
   }

   public void writeString(String var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write text value");
      if(var1 == null) {
         this._writeNull();
      } else {
         int var2 = var1.length();
         if(var2 > this._charBufferLength) {
            this._writeLongString(var1);
         } else {
            var1.getChars(0, var2, this._charBuffer, 0);
            if(var2 > this._outputMaxContiguous) {
               this._writeLongString(this._charBuffer, 0, var2);
            } else {
               if(this._outputTail + var2 >= this._outputEnd) {
                  this._flushBuffer();
               }

               byte[] var4 = this._outputBuffer;
               int var3 = this._outputTail;
               this._outputTail = var3 + 1;
               var4[var3] = 34;
               this._writeStringSegment(this._charBuffer, 0, var2);
               if(this._outputTail >= this._outputEnd) {
                  this._flushBuffer();
               }

               var4 = this._outputBuffer;
               var2 = this._outputTail;
               this._outputTail = var2 + 1;
               var4[var2] = 34;
            }
         }
      }
   }

   public void writeString(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write text value");
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var5 = this._outputBuffer;
      int var4 = this._outputTail;
      this._outputTail = var4 + 1;
      var5[var4] = 34;
      if(var3 <= this._outputMaxContiguous) {
         if(this._outputTail + var3 > this._outputEnd) {
            this._flushBuffer();
         }

         this._writeStringSegment(var1, var2, var3);
      } else {
         this._writeStringSegments(var1, var2, var3);
      }

      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var6 = this._outputBuffer;
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var6[var2] = 34;
   }

   public void writeUTF8String(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write text value");
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var5 = this._outputBuffer;
      int var4 = this._outputTail;
      this._outputTail = var4 + 1;
      var5[var4] = 34;
      if(var3 <= this._outputMaxContiguous) {
         this._writeUTF8Segment(var1, var2, var3);
      } else {
         this._writeUTF8Segments(var1, var2, var3);
      }

      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      var1 = this._outputBuffer;
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var1[var2] = 34;
   }
}
