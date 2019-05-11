package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.NumberOutput;
import com.fasterxml.jackson.core.json.JsonGeneratorImpl;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class WriterBasedJsonGenerator extends JsonGeneratorImpl {

   protected static final char[] HEX_CHARS = CharTypes.copyHexChars();
   protected static final int SHORT_WRITE = 32;
   protected SerializableString _currentEscape;
   protected char[] _entityBuffer;
   protected char[] _outputBuffer;
   protected int _outputEnd;
   protected int _outputHead = 0;
   protected int _outputTail = 0;
   protected final Writer _writer;


   public WriterBasedJsonGenerator(IOContext var1, int var2, ObjectCodec var3, Writer var4) {
      super(var1, var2, var3);
      this._writer = var4;
      this._outputBuffer = var1.allocConcatBuffer();
      this._outputEnd = this._outputBuffer.length;
   }

   private char[] _allocateEntityBuffer() {
      char[] var1 = new char[14];
      var1[0] = 92;
      var1[2] = 92;
      var1[3] = 117;
      var1[4] = 48;
      var1[5] = 48;
      var1[8] = 92;
      var1[9] = 117;
      this._entityBuffer = var1;
      return var1;
   }

   private void _appendCharacterEscape(char var1, int var2) throws IOException, JsonGenerationException {
      int var6;
      char[] var7;
      if(var2 >= 0) {
         if(this._outputTail + 2 > this._outputEnd) {
            this._flushBuffer();
         }

         var7 = this._outputBuffer;
         var6 = this._outputTail;
         this._outputTail = var6 + 1;
         var7[var6] = 92;
         var7 = this._outputBuffer;
         var6 = this._outputTail;
         this._outputTail = var6 + 1;
         var7[var6] = (char)var2;
      } else if(var2 != -2) {
         if(this._outputTail + 2 > this._outputEnd) {
            this._flushBuffer();
         }

         var2 = this._outputTail;
         var7 = this._outputBuffer;
         int var3 = var2 + 1;
         var7[var2] = 92;
         var2 = var3 + 1;
         var7[var3] = 117;
         if(var1 > 255) {
            var3 = 255 & var1 >> 8;
            int var4 = var2 + 1;
            var7[var2] = HEX_CHARS[var3 >> 4];
            var2 = var4 + 1;
            var7[var4] = HEX_CHARS[var3 & 15];
            var1 = (char)(var1 & 255);
         } else {
            var3 = var2 + 1;
            var7[var2] = 48;
            var2 = var3 + 1;
            var7[var3] = 48;
         }

         var3 = var2 + 1;
         var7[var2] = HEX_CHARS[var1 >> 4];
         var7[var3] = HEX_CHARS[var1 & 15];
         this._outputTail = var3;
      } else {
         String var5;
         if(this._currentEscape == null) {
            var5 = this._characterEscapes.getEscapeSequence(var1).getValue();
         } else {
            var5 = this._currentEscape.getValue();
            this._currentEscape = null;
         }

         var6 = var5.length();
         if(this._outputTail + var6 > this._outputEnd) {
            this._flushBuffer();
            if(var6 > this._outputEnd) {
               this._writer.write(var5);
               return;
            }
         }

         var5.getChars(0, var6, this._outputBuffer, this._outputTail);
         this._outputTail += var6;
      }
   }

   private int _prependOrWriteCharacterEscape(char[] var1, int var2, int var3, char var4, int var5) throws IOException, JsonGenerationException {
      char[] var8;
      if(var5 >= 0) {
         if(var2 > 1 && var2 < var3) {
            var2 -= 2;
            var1[var2] = 92;
            var1[var2 + 1] = (char)var5;
            return var2;
         } else {
            var8 = this._entityBuffer;
            var1 = var8;
            if(var8 == null) {
               var1 = this._allocateEntityBuffer();
            }

            var1[1] = (char)var5;
            this._writer.write(var1, 0, 2);
            return var2;
         }
      } else {
         int var7;
         if(var5 != -2) {
            if(var2 > 5 && var2 < var3) {
               var2 -= 6;
               var3 = var2 + 1;
               var1[var2] = 92;
               var2 = var3 + 1;
               var1[var3] = 117;
               if(var4 > 255) {
                  var3 = var4 >> 8 & 255;
                  var5 = var2 + 1;
                  var1[var2] = HEX_CHARS[var3 >> 4];
                  var2 = var5 + 1;
                  var1[var5] = HEX_CHARS[var3 & 15];
                  var4 = (char)(var4 & 255);
               } else {
                  var3 = var2 + 1;
                  var1[var2] = 48;
                  var2 = var3 + 1;
                  var1[var3] = 48;
               }

               var3 = var2 + 1;
               var1[var2] = HEX_CHARS[var4 >> 4];
               var1[var3] = HEX_CHARS[var4 & 15];
               return var3 - 5;
            } else {
               var8 = this._entityBuffer;
               var1 = var8;
               if(var8 == null) {
                  var1 = this._allocateEntityBuffer();
               }

               this._outputHead = this._outputTail;
               if(var4 > 255) {
                  var3 = var4 >> 8 & 255;
                  var7 = var4 & 255;
                  var1[10] = HEX_CHARS[var3 >> 4];
                  var1[11] = HEX_CHARS[var3 & 15];
                  var1[12] = HEX_CHARS[var7 >> 4];
                  var1[13] = HEX_CHARS[var7 & 15];
                  this._writer.write(var1, 8, 6);
                  return var2;
               } else {
                  var1[6] = HEX_CHARS[var4 >> 4];
                  var1[7] = HEX_CHARS[var4 & 15];
                  this._writer.write(var1, 2, 6);
                  return var2;
               }
            }
         } else {
            String var6;
            if(this._currentEscape == null) {
               var6 = this._characterEscapes.getEscapeSequence(var4).getValue();
            } else {
               var6 = this._currentEscape.getValue();
               this._currentEscape = null;
            }

            var7 = var6.length();
            if(var2 >= var7 && var2 < var3) {
               var2 -= var7;
               var6.getChars(0, var7, var1, var2);
               return var2;
            } else {
               this._writer.write(var6);
               return var2;
            }
         }
      }
   }

   private void _prependOrWriteCharacterEscape(char var1, int var2) throws IOException, JsonGenerationException {
      char[] var5;
      int var6;
      char[] var7;
      if(var2 >= 0) {
         if(this._outputTail >= 2) {
            var6 = this._outputTail - 2;
            this._outputHead = var6;
            this._outputBuffer[var6] = 92;
            this._outputBuffer[var6 + 1] = (char)var2;
         } else {
            var5 = this._entityBuffer;
            var7 = var5;
            if(var5 == null) {
               var7 = this._allocateEntityBuffer();
            }

            this._outputHead = this._outputTail;
            var7[1] = (char)var2;
            this._writer.write(var7, 0, 2);
         }
      } else if(var2 != -2) {
         if(this._outputTail >= 6) {
            var7 = this._outputBuffer;
            var2 = this._outputTail - 6;
            this._outputHead = var2;
            var7[var2] = 92;
            ++var2;
            var7[var2] = 117;
            if(var1 > 255) {
               int var3 = var1 >> 8 & 255;
               ++var2;
               var7[var2] = HEX_CHARS[var3 >> 4];
               ++var2;
               var7[var2] = HEX_CHARS[var3 & 15];
               var1 = (char)(var1 & 255);
            } else {
               ++var2;
               var7[var2] = 48;
               ++var2;
               var7[var2] = 48;
            }

            ++var2;
            var7[var2] = HEX_CHARS[var1 >> 4];
            var7[var2 + 1] = HEX_CHARS[var1 & 15];
         } else {
            var5 = this._entityBuffer;
            var7 = var5;
            if(var5 == null) {
               var7 = this._allocateEntityBuffer();
            }

            this._outputHead = this._outputTail;
            if(var1 > 255) {
               var2 = var1 >> 8 & 255;
               var6 = var1 & 255;
               var7[10] = HEX_CHARS[var2 >> 4];
               var7[11] = HEX_CHARS[var2 & 15];
               var7[12] = HEX_CHARS[var6 >> 4];
               var7[13] = HEX_CHARS[var6 & 15];
               this._writer.write(var7, 8, 6);
            } else {
               var7[6] = HEX_CHARS[var1 >> 4];
               var7[7] = HEX_CHARS[var1 & 15];
               this._writer.write(var7, 2, 6);
            }
         }
      } else {
         String var4;
         if(this._currentEscape == null) {
            var4 = this._characterEscapes.getEscapeSequence(var1).getValue();
         } else {
            var4 = this._currentEscape.getValue();
            this._currentEscape = null;
         }

         var6 = var4.length();
         if(this._outputTail >= var6) {
            var2 = this._outputTail - var6;
            this._outputHead = var2;
            var4.getChars(0, var6, this._outputBuffer, var2);
         } else {
            this._outputHead = this._outputTail;
            this._writer.write(var4);
         }
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

   private void _writeLongString(String var1) throws IOException, JsonGenerationException {
      this._flushBuffer();
      int var5 = var1.length();
      int var2 = 0;

      while(true) {
         int var4 = this._outputEnd;
         int var3 = var4;
         if(var2 + var4 > var5) {
            var3 = var5 - var2;
         }

         var4 = var2 + var3;
         var1.getChars(var2, var4, this._outputBuffer, 0);
         if(this._characterEscapes != null) {
            this._writeSegmentCustom(var3);
         } else if(this._maximumNonEscapedChar != 0) {
            this._writeSegmentASCII(var3, this._maximumNonEscapedChar);
         } else {
            this._writeSegment(var3);
         }

         if(var4 >= var5) {
            return;
         }

         var2 = var4;
      }
   }

   private void _writeNull() throws IOException {
      if(this._outputTail + 4 >= this._outputEnd) {
         this._flushBuffer();
      }

      int var1 = this._outputTail;
      char[] var2 = this._outputBuffer;
      var2[var1] = 110;
      ++var1;
      var2[var1] = 117;
      ++var1;
      var2[var1] = 108;
      ++var1;
      var2[var1] = 108;
      this._outputTail = var1 + 1;
   }

   private void _writeQuotedInt(int var1) throws IOException {
      if(this._outputTail + 13 >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var3 = this._outputBuffer;
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

      char[] var4 = this._outputBuffer;
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

      char[] var3 = this._outputBuffer;
      int var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var3[var2] = 34;
      this.writeRaw(var1.toString());
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var4 = this._outputBuffer;
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var4[var2] = 34;
   }

   private void _writeQuotedShort(short var1) throws IOException {
      if(this._outputTail + 8 >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var3 = this._outputBuffer;
      int var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var3[var2] = 34;
      this._outputTail = NumberOutput.outputInt(var1, this._outputBuffer, this._outputTail);
      var3 = this._outputBuffer;
      int var4 = this._outputTail;
      this._outputTail = var4 + 1;
      var3[var4] = 34;
   }

   private void _writeSegment(int var1) throws IOException, JsonGenerationException {
      int[] var7 = this._outputEscapes;
      int var6 = var7.length;
      int var3 = 0;

      char var2;
      for(int var4 = 0; var3 < var1; var4 = this._prependOrWriteCharacterEscape(this._outputBuffer, var3, var1, var2, var7[var2])) {
         int var5;
         while(true) {
            var2 = this._outputBuffer[var3];
            if(var2 < var6 && var7[var2] != 0) {
               break;
            }

            var5 = var3 + 1;
            var3 = var5;
            if(var5 >= var1) {
               var3 = var5;
               break;
            }
         }

         var5 = var3 - var4;
         if(var5 > 0) {
            this._writer.write(this._outputBuffer, var4, var5);
            if(var3 >= var1) {
               return;
            }
         }

         ++var3;
      }

   }

   private void _writeSegmentASCII(int var1, int var2) throws IOException, JsonGenerationException {
      int[] var10 = this._outputEscapes;
      int var9 = Math.min(var10.length, var2 + 1);
      int var5 = 0;
      int var6 = 0;

      char var3;
      for(int var4 = 0; var5 < var1; var6 = this._prependOrWriteCharacterEscape(this._outputBuffer, var5, var1, var3, var4)) {
         int var7 = var4;
         var4 = var5;

         int var8;
         while(true) {
            var3 = this._outputBuffer[var4];
            if(var3 < var9) {
               var7 = var10[var3];
               var5 = var7;
               if(var7 != 0) {
                  var8 = var4;
                  var4 = var7;
                  break;
               }
            } else {
               var5 = var7;
               if(var3 > var2) {
                  byte var11 = -1;
                  var8 = var4;
                  var4 = var11;
                  break;
               }
            }

            var8 = var4 + 1;
            var4 = var8;
            var7 = var5;
            if(var8 >= var1) {
               var4 = var5;
               break;
            }
         }

         var5 = var8 - var6;
         if(var5 > 0) {
            this._writer.write(this._outputBuffer, var6, var5);
            if(var8 >= var1) {
               return;
            }
         }

         var5 = var8 + 1;
      }

   }

   private void _writeSegmentCustom(int var1) throws IOException, JsonGenerationException {
      int[] var10 = this._outputEscapes;
      int var5;
      if(this._maximumNonEscapedChar < 1) {
         var5 = '\uffff';
      } else {
         var5 = this._maximumNonEscapedChar;
      }

      int var9 = Math.min(var10.length, var5 + 1);
      CharacterEscapes var11 = this._characterEscapes;
      int var4 = 0;
      int var6 = 0;

      char var2;
      for(int var3 = 0; var4 < var1; var6 = this._prependOrWriteCharacterEscape(this._outputBuffer, var4, var1, var2, var3)) {
         int var7 = var3;
         var3 = var4;

         while(true) {
            var2 = this._outputBuffer[var3];
            if(var2 < var9) {
               var7 = var10[var2];
               var4 = var7;
               if(var7 != 0) {
                  var4 = var3;
                  var3 = var7;
                  break;
               }
            } else {
               byte var13;
               if(var2 > var5) {
                  var13 = -1;
                  var4 = var3;
                  var3 = var13;
                  break;
               }

               SerializableString var12 = var11.getEscapeSequence(var2);
               this._currentEscape = var12;
               var4 = var7;
               if(var12 != null) {
                  var13 = -2;
                  var4 = var3;
                  var3 = var13;
                  break;
               }
            }

            int var8 = var3 + 1;
            var3 = var8;
            var7 = var4;
            if(var8 >= var1) {
               var3 = var4;
               var4 = var8;
               break;
            }
         }

         var7 = var4 - var6;
         if(var7 > 0) {
            this._writer.write(this._outputBuffer, var6, var7);
            if(var4 >= var1) {
               return;
            }
         }

         ++var4;
      }

   }

   private void _writeString(String var1) throws IOException, JsonGenerationException {
      int var2 = var1.length();
      if(var2 > this._outputEnd) {
         this._writeLongString(var1);
      } else {
         if(this._outputTail + var2 > this._outputEnd) {
            this._flushBuffer();
         }

         var1.getChars(0, var2, this._outputBuffer, this._outputTail);
         if(this._characterEscapes != null) {
            this._writeStringCustom(var2);
         } else if(this._maximumNonEscapedChar != 0) {
            this._writeStringASCII(var2, this._maximumNonEscapedChar);
         } else {
            this._writeString2(var2);
         }
      }
   }

   private void _writeString(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      if(this._characterEscapes != null) {
         this._writeStringCustom(var1, var2, var3);
      } else if(this._maximumNonEscapedChar != 0) {
         this._writeStringASCII(var1, var2, var3, this._maximumNonEscapedChar);
      } else {
         int var6 = var3 + var2;
         int[] var8 = this._outputEscapes;
         int var7 = var8.length;

         while(var2 < var6) {
            var3 = var2;

            int var9;
            while(true) {
               char var5 = var1[var3];
               if(var5 < var7 && var8[var5] != 0) {
                  break;
               }

               var9 = var3 + 1;
               var3 = var9;
               if(var9 >= var6) {
                  var3 = var9;
                  break;
               }
            }

            var9 = var3 - var2;
            if(var9 < 32) {
               if(this._outputTail + var9 > this._outputEnd) {
                  this._flushBuffer();
               }

               if(var9 > 0) {
                  System.arraycopy(var1, var2, this._outputBuffer, this._outputTail, var9);
                  this._outputTail += var9;
               }
            } else {
               this._flushBuffer();
               this._writer.write(var1, var2, var9);
            }

            if(var3 >= var6) {
               return;
            }

            var2 = var3 + 1;
            char var4 = var1[var3];
            this._appendCharacterEscape(var4, var8[var4]);
         }

      }
   }

   private void _writeString2(int var1) throws IOException, JsonGenerationException {
      var1 += this._outputTail;
      int[] var5 = this._outputEscapes;
      int var3 = var5.length;

      while(this._outputTail < var1) {
         while(true) {
            char var4 = this._outputBuffer[this._outputTail];
            int var7;
            if(var4 < var3 && var5[var4] != 0) {
               var7 = this._outputTail - this._outputHead;
               if(var7 > 0) {
                  this._writer.write(this._outputBuffer, this._outputHead, var7);
               }

               char[] var6 = this._outputBuffer;
               var7 = this._outputTail;
               this._outputTail = var7 + 1;
               char var2 = var6[var7];
               this._prependOrWriteCharacterEscape(var2, var5[var2]);
            } else {
               var7 = this._outputTail + 1;
               this._outputTail = var7;
               if(var7 >= var1) {
                  return;
               }
            }
         }
      }

   }

   private void _writeStringASCII(int var1, int var2) throws IOException, JsonGenerationException {
      int var4 = this._outputTail + var1;
      int[] var7 = this._outputEscapes;
      int var5 = Math.min(var7.length, var2 + 1);

      while(this._outputTail < var4) {
         char var3;
         while(true) {
            var3 = this._outputBuffer[this._outputTail];
            if(var3 < var5) {
               var1 = var7[var3];
               if(var1 != 0) {
                  break;
               }
            } else if(var3 > var2) {
               var1 = -1;
               break;
            }

            var1 = this._outputTail + 1;
            this._outputTail = var1;
            if(var1 >= var4) {
               return;
            }
         }

         int var6 = this._outputTail - this._outputHead;
         if(var6 > 0) {
            this._writer.write(this._outputBuffer, this._outputHead, var6);
         }

         ++this._outputTail;
         this._prependOrWriteCharacterEscape(var3, var1);
      }

   }

   private void _writeStringASCII(char[] var1, int var2, int var3, int var4) throws IOException, JsonGenerationException {
      int var9 = var3 + var2;
      int[] var11 = this._outputEscapes;
      int var10 = Math.min(var11.length, var4 + 1);
      byte var6 = 0;
      var3 = var2;
      var2 = var6;

      while(var3 < var9) {
         int var7 = var2;
         var2 = var3;

         char var5;
         int var8;
         int var12;
         while(true) {
            var5 = var1[var2];
            if(var5 < var10) {
               var7 = var11[var5];
               var12 = var7;
               if(var7 != 0) {
                  var8 = var2;
                  var2 = var7;
                  break;
               }
            } else {
               var12 = var7;
               if(var5 > var4) {
                  byte var13 = -1;
                  var8 = var2;
                  var2 = var13;
                  break;
               }
            }

            var8 = var2 + 1;
            var2 = var8;
            var7 = var12;
            if(var8 >= var9) {
               var2 = var12;
               break;
            }
         }

         var12 = var8 - var3;
         if(var12 < 32) {
            if(this._outputTail + var12 > this._outputEnd) {
               this._flushBuffer();
            }

            if(var12 > 0) {
               System.arraycopy(var1, var3, this._outputBuffer, this._outputTail, var12);
               this._outputTail += var12;
            }
         } else {
            this._flushBuffer();
            this._writer.write(var1, var3, var12);
         }

         if(var8 >= var9) {
            return;
         }

         var3 = var8 + 1;
         this._appendCharacterEscape(var5, var2);
      }

   }

   private void _writeStringCustom(int var1) throws IOException, JsonGenerationException {
      int var4 = this._outputTail + var1;
      int[] var7 = this._outputEscapes;
      int var3;
      if(this._maximumNonEscapedChar < 1) {
         var3 = '\uffff';
      } else {
         var3 = this._maximumNonEscapedChar;
      }

      int var5 = Math.min(var7.length, var3 + 1);
      CharacterEscapes var8 = this._characterEscapes;

      while(this._outputTail < var4) {
         char var2;
         while(true) {
            var2 = this._outputBuffer[this._outputTail];
            if(var2 < var5) {
               var1 = var7[var2];
               if(var1 != 0) {
                  break;
               }
            } else {
               if(var2 > var3) {
                  var1 = -1;
                  break;
               }

               SerializableString var9 = var8.getEscapeSequence(var2);
               this._currentEscape = var9;
               if(var9 != null) {
                  var1 = -2;
                  break;
               }
            }

            var1 = this._outputTail + 1;
            this._outputTail = var1;
            if(var1 >= var4) {
               return;
            }
         }

         int var6 = this._outputTail - this._outputHead;
         if(var6 > 0) {
            this._writer.write(this._outputBuffer, this._outputHead, var6);
         }

         ++this._outputTail;
         this._prependOrWriteCharacterEscape(var2, var1);
      }

   }

   private void _writeStringCustom(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int var9 = var3 + var2;
      int[] var11 = this._outputEscapes;
      int var6;
      if(this._maximumNonEscapedChar < 1) {
         var6 = '\uffff';
      } else {
         var6 = this._maximumNonEscapedChar;
      }

      int var10 = Math.min(var11.length, var6 + 1);
      CharacterEscapes var12 = this._characterEscapes;
      byte var5 = 0;
      var3 = var2;
      var2 = var5;

      while(var3 < var9) {
         int var7 = var2;
         var2 = var3;

         char var4;
         int var14;
         while(true) {
            var4 = var1[var2];
            if(var4 < var10) {
               var7 = var11[var4];
               var14 = var7;
               if(var7 != 0) {
                  var14 = var2;
                  var2 = var7;
                  break;
               }
            } else {
               byte var15;
               if(var4 > var6) {
                  var15 = -1;
                  var14 = var2;
                  var2 = var15;
                  break;
               }

               SerializableString var13 = var12.getEscapeSequence(var4);
               this._currentEscape = var13;
               var14 = var7;
               if(var13 != null) {
                  var15 = -2;
                  var14 = var2;
                  var2 = var15;
                  break;
               }
            }

            int var8 = var2 + 1;
            var2 = var8;
            var7 = var14;
            if(var8 >= var9) {
               var2 = var14;
               var14 = var8;
               break;
            }
         }

         var7 = var14 - var3;
         if(var7 < 32) {
            if(this._outputTail + var7 > this._outputEnd) {
               this._flushBuffer();
            }

            if(var7 > 0) {
               System.arraycopy(var1, var3, this._outputBuffer, this._outputTail, var7);
               this._outputTail += var7;
            }
         } else {
            this._flushBuffer();
            this._writer.write(var1, var3, var7);
         }

         if(var14 >= var9) {
            return;
         }

         var3 = var14 + 1;
         this._appendCharacterEscape(var4, var2);
      }

   }

   private void writeRawLong(String var1) throws IOException, JsonGenerationException {
      int var2 = this._outputEnd - this._outputTail;
      var1.getChars(0, var2, this._outputBuffer, this._outputTail);
      this._outputTail += var2;
      this._flushBuffer();

      int var3;
      int var4;
      for(var3 = var1.length() - var2; var3 > this._outputEnd; var2 = var4) {
         int var5 = this._outputEnd;
         var4 = var2 + var5;
         var1.getChars(var2, var4, this._outputBuffer, 0);
         this._outputHead = 0;
         this._outputTail = var5;
         this._flushBuffer();
         var3 -= var5;
      }

      var1.getChars(var2, var2 + var3, this._outputBuffer, 0);
      this._outputHead = 0;
      this._outputTail = var3;
   }

   protected void _flushBuffer() throws IOException {
      int var1 = this._outputTail - this._outputHead;
      if(var1 > 0) {
         int var2 = this._outputHead;
         this._outputHead = 0;
         this._outputTail = 0;
         this._writer.write(this._outputBuffer, var2, var1);
      }

   }

   protected void _releaseBuffers() {
      char[] var1 = this._outputBuffer;
      if(var1 != null) {
         this._outputBuffer = null;
         this._ioContext.releaseConcatBuffer(var1);
      }

   }

   protected void _verifyPrettyValueWrite(String var1, int var2) throws IOException, JsonGenerationException {
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

   protected void _verifyValueWrite(String var1) throws IOException, JsonGenerationException {
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
               this.writeRaw(this._rootValueSeparator.getValue());
            }

            return;
         default:
            return;
         }

         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail] = (char)var2;
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
            char[] var15 = this._outputBuffer;
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
            char[] var15 = this._outputBuffer;
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
            char[] var9 = this._outputBuffer;
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

   public void _writeFieldName(SerializableString var1, boolean var2) throws IOException, JsonGenerationException {
      if(this._cfgPrettyPrinter != null) {
         this._writePPFieldName(var1, var2);
      } else {
         if(this._outputTail + 1 >= this._outputEnd) {
            this._flushBuffer();
         }

         int var3;
         char[] var4;
         if(var2) {
            var4 = this._outputBuffer;
            var3 = this._outputTail;
            this._outputTail = var3 + 1;
            var4[var3] = 44;
         }

         char[] var5 = var1.asQuotedChars();
         if(!this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
            this.writeRaw(var5, 0, var5.length);
         } else {
            var4 = this._outputBuffer;
            var3 = this._outputTail;
            this._outputTail = var3 + 1;
            var4[var3] = 34;
            var3 = var5.length;
            if(this._outputTail + var3 + 1 >= this._outputEnd) {
               this.writeRaw(var5, 0, var3);
               if(this._outputTail >= this._outputEnd) {
                  this._flushBuffer();
               }

               var5 = this._outputBuffer;
               var3 = this._outputTail;
               this._outputTail = var3 + 1;
               var5[var3] = 34;
            } else {
               System.arraycopy(var5, 0, this._outputBuffer, this._outputTail, var3);
               this._outputTail += var3;
               var5 = this._outputBuffer;
               var3 = this._outputTail;
               this._outputTail = var3 + 1;
               var5[var3] = 34;
            }
         }
      }
   }

   protected void _writeFieldName(String var1, boolean var2) throws IOException, JsonGenerationException {
      if(this._cfgPrettyPrinter != null) {
         this._writePPFieldName(var1, var2);
      } else {
         if(this._outputTail + 1 >= this._outputEnd) {
            this._flushBuffer();
         }

         int var3;
         char[] var4;
         if(var2) {
            var4 = this._outputBuffer;
            var3 = this._outputTail;
            this._outputTail = var3 + 1;
            var4[var3] = 44;
         }

         if(!this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
            this._writeString(var1);
         } else {
            var4 = this._outputBuffer;
            var3 = this._outputTail;
            this._outputTail = var3 + 1;
            var4[var3] = 34;
            this._writeString(var1);
            if(this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            char[] var5 = this._outputBuffer;
            var3 = this._outputTail;
            this._outputTail = var3 + 1;
            var5[var3] = 34;
         }
      }
   }

   protected void _writePPFieldName(SerializableString var1, boolean var2) throws IOException, JsonGenerationException {
      if(var2) {
         this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
      } else {
         this._cfgPrettyPrinter.beforeObjectEntries(this);
      }

      char[] var5 = var1.asQuotedChars();
      if(this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var4 = this._outputBuffer;
         int var3 = this._outputTail;
         this._outputTail = var3 + 1;
         var4[var3] = 34;
         this.writeRaw(var5, 0, var5.length);
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         var5 = this._outputBuffer;
         var3 = this._outputTail;
         this._outputTail = var3 + 1;
         var5[var3] = 34;
      } else {
         this.writeRaw(var5, 0, var5.length);
      }
   }

   protected void _writePPFieldName(String var1, boolean var2) throws IOException, JsonGenerationException {
      if(var2) {
         this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
      } else {
         this._cfgPrettyPrinter.beforeObjectEntries(this);
      }

      if(this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var4 = this._outputBuffer;
         int var3 = this._outputTail;
         this._outputTail = var3 + 1;
         var4[var3] = 34;
         this._writeString(var1);
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var5 = this._outputBuffer;
         var3 = this._outputTail;
         this._outputTail = var3 + 1;
         var5[var3] = 34;
      } else {
         this._writeString(var1);
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
      if(this._writer != null) {
         if(!this._ioContext.isResourceManaged() && !this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
            if(this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
               this._writer.flush();
            }
         } else {
            this._writer.close();
         }
      }

      this._releaseBuffers();
   }

   public void flush() throws IOException {
      this._flushBuffer();
      if(this._writer != null && this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
         this._writer.flush();
      }

   }

   public Object getOutputTarget() {
      return this._writer;
   }

   public int writeBinary(Base64Variant param1, InputStream param2, int param3) throws IOException, JsonGenerationException {
      // $FF: Couldn't be decompiled
   }

   public void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write binary value");
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var6 = this._outputBuffer;
      int var5 = this._outputTail;
      this._outputTail = var5 + 1;
      var6[var5] = 34;
      this._writeBinary(var1, var2, var3, var4 + var3);
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var7 = this._outputBuffer;
      var3 = this._outputTail;
      this._outputTail = var3 + 1;
      var7[var3] = 34;
   }

   public void writeBoolean(boolean var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write boolean value");
      if(this._outputTail + 5 >= this._outputEnd) {
         this._flushBuffer();
      }

      int var2 = this._outputTail;
      char[] var3 = this._outputBuffer;
      if(var1) {
         var3[var2] = 116;
         ++var2;
         var3[var2] = 114;
         ++var2;
         var3[var2] = 117;
         ++var2;
         var3[var2] = 101;
      } else {
         var3[var2] = 102;
         ++var2;
         var3[var2] = 97;
         ++var2;
         var3[var2] = 108;
         ++var2;
         var3[var2] = 115;
         ++var2;
         var3[var2] = 101;
      }

      this._outputTail = var2 + 1;
   }

   public void writeEndArray() throws IOException, JsonGenerationException {
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

         char[] var3 = this._outputBuffer;
         int var1 = this._outputTail;
         this._outputTail = var1 + 1;
         var3[var1] = 93;
      }

      this._writeContext = this._writeContext.getParent();
   }

   public void writeEndObject() throws IOException, JsonGenerationException {
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

         char[] var3 = this._outputBuffer;
         int var1 = this._outputTail;
         this._outputTail = var1 + 1;
         var3[var1] = 125;
      }

      this._writeContext = this._writeContext.getParent();
   }

   public void writeFieldName(SerializableString var1) throws IOException, JsonGenerationException {
      int var2 = this._writeContext.writeFieldName(var1.getValue());
      if(var2 == 4) {
         this._reportError("Can not write a field name, expecting a value");
      }

      boolean var3 = true;
      if(var2 != 1) {
         var3 = false;
      }

      this._writeFieldName(var1, var3);
   }

   public void writeFieldName(String var1) throws IOException, JsonGenerationException {
      int var2 = this._writeContext.writeFieldName(var1);
      if(var2 == 4) {
         this._reportError("Can not write a field name, expecting a value");
      }

      boolean var3 = true;
      if(var2 != 1) {
         var3 = false;
      }

      this._writeFieldName(var1, var3);
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
      if(this._cfgNumbersAsStrings) {
         this._writeQuotedInt(var1);
      } else {
         if(this._outputTail + 11 >= this._outputEnd) {
            this._flushBuffer();
         }

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
      if(this._cfgNumbersAsStrings) {
         this._writeQuotedShort(var1);
      } else {
         if(this._outputTail + 6 >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputTail = NumberOutput.outputInt(var1, this._outputBuffer, this._outputTail);
      }
   }

   public void writeRaw(char var1) throws IOException, JsonGenerationException {
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var3 = this._outputBuffer;
      int var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var3[var2] = var1;
   }

   public void writeRaw(SerializableString var1) throws IOException, JsonGenerationException {
      this.writeRaw(var1.getValue());
   }

   public void writeRaw(String var1) throws IOException, JsonGenerationException {
      int var4 = var1.length();
      int var3 = this._outputEnd - this._outputTail;
      int var2 = var3;
      if(var3 == 0) {
         this._flushBuffer();
         var2 = this._outputEnd - this._outputTail;
      }

      if(var2 >= var4) {
         var1.getChars(0, var4, this._outputBuffer, this._outputTail);
         this._outputTail += var4;
      } else {
         this.writeRawLong(var1);
      }
   }

   public void writeRaw(String var1, int var2, int var3) throws IOException, JsonGenerationException {
      int var5 = this._outputEnd - this._outputTail;
      int var4 = var5;
      if(var5 < var3) {
         this._flushBuffer();
         var4 = this._outputEnd - this._outputTail;
      }

      if(var4 >= var3) {
         var1.getChars(var2, var2 + var3, this._outputBuffer, this._outputTail);
         this._outputTail += var3;
      } else {
         this.writeRawLong(var1.substring(var2, var3 + var2));
      }
   }

   public void writeRaw(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      if(var3 < 32) {
         if(var3 > this._outputEnd - this._outputTail) {
            this._flushBuffer();
         }

         System.arraycopy(var1, var2, this._outputBuffer, this._outputTail, var3);
         this._outputTail += var3;
      } else {
         this._flushBuffer();
         this._writer.write(var1, var2, var3);
      }
   }

   public void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this._reportUnsupportedOperation();
   }

   public void writeStartArray() throws IOException, JsonGenerationException {
      this._verifyValueWrite("start an array");
      this._writeContext = this._writeContext.createChildArrayContext();
      if(this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartArray(this);
      } else {
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var2 = this._outputBuffer;
         int var1 = this._outputTail;
         this._outputTail = var1 + 1;
         var2[var1] = 91;
      }
   }

   public void writeStartObject() throws IOException, JsonGenerationException {
      this._verifyValueWrite("start an object");
      this._writeContext = this._writeContext.createChildObjectContext();
      if(this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartObject(this);
      } else {
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var2 = this._outputBuffer;
         int var1 = this._outputTail;
         this._outputTail = var1 + 1;
         var2[var1] = 123;
      }
   }

   public void writeString(SerializableString var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write text value");
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var3 = this._outputBuffer;
      int var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var3[var2] = 34;
      char[] var4 = var1.asQuotedChars();
      var2 = var4.length;
      if(var2 < 32) {
         if(var2 > this._outputEnd - this._outputTail) {
            this._flushBuffer();
         }

         System.arraycopy(var4, 0, this._outputBuffer, this._outputTail, var2);
         this._outputTail += var2;
      } else {
         this._flushBuffer();
         this._writer.write(var4, 0, var2);
      }

      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      var4 = this._outputBuffer;
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var4[var2] = 34;
   }

   public void writeString(String var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write text value");
      if(var1 == null) {
         this._writeNull();
      } else {
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var3 = this._outputBuffer;
         int var2 = this._outputTail;
         this._outputTail = var2 + 1;
         var3[var2] = 34;
         this._writeString(var1);
         if(this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var4 = this._outputBuffer;
         var2 = this._outputTail;
         this._outputTail = var2 + 1;
         var4[var2] = 34;
      }
   }

   public void writeString(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write text value");
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var5 = this._outputBuffer;
      int var4 = this._outputTail;
      this._outputTail = var4 + 1;
      var5[var4] = 34;
      this._writeString(var1, var2, var3);
      if(this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      var1 = this._outputBuffer;
      var2 = this._outputTail;
      this._outputTail = var2 + 1;
      var1[var2] = 34;
   }

   public void writeUTF8String(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this._reportUnsupportedOperation();
   }
}
