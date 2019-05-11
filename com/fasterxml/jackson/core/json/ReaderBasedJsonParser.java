package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public final class ReaderBasedJsonParser extends ParserBase {

   protected final int _hashSeed;
   protected char[] _inputBuffer;
   protected ObjectCodec _objectCodec;
   protected Reader _reader;
   protected final CharsToNameCanonicalizer _symbols;
   protected boolean _tokenIncomplete = false;


   public ReaderBasedJsonParser(IOContext var1, int var2, Reader var3, ObjectCodec var4, CharsToNameCanonicalizer var5) {
      super(var1, var2);
      this._reader = var3;
      this._inputBuffer = var1.allocTokenBuffer();
      this._objectCodec = var4;
      this._symbols = var5;
      this._hashSeed = var5.hashSeed();
   }

   private JsonToken _nextAfterName() {
      this._nameCopied = false;
      JsonToken var1 = this._nextToken;
      this._nextToken = null;
      if(var1 == JsonToken.START_ARRAY) {
         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
      } else if(var1 == JsonToken.START_OBJECT) {
         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
      }

      this._currToken = var1;
      return var1;
   }

   private String _parseFieldName2(int var1, int var2, int var3) throws IOException, JsonParseException {
      this._textBuffer.resetWithShared(this._inputBuffer, var1, this._inputPtr - var1);
      char[] var7 = this._textBuffer.getCurrentSegment();
      var1 = this._textBuffer.getCurrentSegmentSize();

      while(true) {
         if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
            StringBuilder var8 = new StringBuilder();
            var8.append(": was expecting closing \'");
            var8.append((char)var3);
            var8.append("\' for name");
            this._reportInvalidEOF(var8.toString());
         }

         char var4;
         char var5;
         int var6;
         label32: {
            char[] var10 = this._inputBuffer;
            var6 = this._inputPtr;
            this._inputPtr = var6 + 1;
            var5 = var10[var6];
            if(var5 <= 92) {
               if(var5 == 92) {
                  var4 = this._decodeEscaped();
                  break label32;
               }

               if(var5 <= var3) {
                  if(var5 == var3) {
                     this._textBuffer.setCurrentLength(var1);
                     TextBuffer var9 = this._textBuffer;
                     var10 = var9.getTextBuffer();
                     var1 = var9.getTextOffset();
                     var3 = var9.size();
                     return this._symbols.findSymbol(var10, var1, var3, var2);
                  }

                  if(var5 < 32) {
                     this._throwUnquotedSpace(var5, "name");
                  }
               }
            }

            var4 = var5;
         }

         var2 = var2 * 33 + var5;
         var6 = var1 + 1;
         var7[var1] = var4;
         if(var6 >= var7.length) {
            var7 = this._textBuffer.finishCurrentSegment();
            var1 = 0;
         } else {
            var1 = var6;
         }
      }
   }

   private String _parseUnusualFieldName2(int var1, int var2, int[] var3) throws IOException, JsonParseException {
      this._textBuffer.resetWithShared(this._inputBuffer, var1, this._inputPtr - var1);
      char[] var7 = this._textBuffer.getCurrentSegment();
      var1 = this._textBuffer.getCurrentSegmentSize();
      int var6 = var3.length;

      int var5;
      while(this._inputPtr < this._inputEnd || this.loadMore()) {
         char var4 = this._inputBuffer[this._inputPtr];
         if(var4 <= var6) {
            if(var3[var4] != 0) {
               break;
            }
         } else if(!Character.isJavaIdentifierPart(var4)) {
            break;
         }

         ++this._inputPtr;
         var2 = var2 * 33 + var4;
         var5 = var1 + 1;
         var7[var1] = var4;
         if(var5 >= var7.length) {
            var7 = this._textBuffer.finishCurrentSegment();
            var1 = 0;
         } else {
            var1 = var5;
         }
      }

      this._textBuffer.setCurrentLength(var1);
      TextBuffer var8 = this._textBuffer;
      var7 = var8.getTextBuffer();
      var1 = var8.getTextOffset();
      var5 = var8.size();
      return this._symbols.findSymbol(var7, var1, var5, var2);
   }

   private void _skipCComment() throws IOException, JsonParseException {
      while(this._inputPtr < this._inputEnd || this.loadMore()) {
         char[] var2 = this._inputBuffer;
         int var1 = this._inputPtr;
         this._inputPtr = var1 + 1;
         char var3 = var2[var1];
         if(var3 <= 42) {
            if(var3 != 42) {
               if(var3 < 32) {
                  if(var3 == 10) {
                     this._skipLF();
                  } else if(var3 == 13) {
                     this._skipCR();
                  } else if(var3 != 9) {
                     this._throwInvalidSpace(var3);
                  }
               }
            } else {
               if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
                  break;
               }

               if(this._inputBuffer[this._inputPtr] == 47) {
                  ++this._inputPtr;
                  return;
               }
            }
         }
      }

      this._reportInvalidEOF(" in a comment");
   }

   private void _skipComment() throws IOException, JsonParseException {
      if(!this.isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
         this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature \'ALLOW_COMMENTS\' not enabled for parser)");
      }

      if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
         this._reportInvalidEOF(" in a comment");
      }

      char[] var2 = this._inputBuffer;
      int var1 = this._inputPtr;
      this._inputPtr = var1 + 1;
      char var3 = var2[var1];
      if(var3 == 47) {
         this._skipCppComment();
      } else if(var3 == 42) {
         this._skipCComment();
      } else {
         this._reportUnexpectedChar(var3, "was expecting either \'*\' or \'/\' for a comment");
      }
   }

   private void _skipCppComment() throws IOException, JsonParseException {
      while(this._inputPtr < this._inputEnd || this.loadMore()) {
         char[] var2 = this._inputBuffer;
         int var1 = this._inputPtr;
         this._inputPtr = var1 + 1;
         char var3 = var2[var1];
         if(var3 < 32) {
            if(var3 == 10) {
               this._skipLF();
               return;
            }

            if(var3 == 13) {
               this._skipCR();
               break;
            }

            if(var3 != 9) {
               this._throwInvalidSpace(var3);
            }
         }
      }

   }

   private int _skipWS() throws IOException, JsonParseException {
      while(this._inputPtr < this._inputEnd || this.loadMore()) {
         char[] var2 = this._inputBuffer;
         int var1 = this._inputPtr;
         this._inputPtr = var1 + 1;
         char var3 = var2[var1];
         if(var3 > 32) {
            if(var3 != 47) {
               return var3;
            }

            this._skipComment();
         } else if(var3 != 32) {
            if(var3 == 10) {
               this._skipLF();
            } else if(var3 == 13) {
               this._skipCR();
            } else if(var3 != 9) {
               this._throwInvalidSpace(var3);
            }
         }
      }

      StringBuilder var4 = new StringBuilder();
      var4.append("Unexpected end-of-input within/between ");
      var4.append(this._parsingContext.getTypeDesc());
      var4.append(" entries");
      throw this._constructError(var4.toString());
   }

   private int _skipWSOrEnd() throws IOException, JsonParseException {
      while(this._inputPtr < this._inputEnd || this.loadMore()) {
         char[] var2 = this._inputBuffer;
         int var1 = this._inputPtr;
         this._inputPtr = var1 + 1;
         char var3 = var2[var1];
         if(var3 > 32) {
            if(var3 != 47) {
               return var3;
            }

            this._skipComment();
         } else if(var3 != 32) {
            if(var3 == 10) {
               this._skipLF();
            } else if(var3 == 13) {
               this._skipCR();
            } else if(var3 != 9) {
               this._throwInvalidSpace(var3);
            }
         }
      }

      this._handleEOF();
      return -1;
   }

   private char _verifyNoLeadingZeroes() throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
         return '0';
      } else {
         char var1 = this._inputBuffer[this._inputPtr];
         if(var1 < 48) {
            return '0';
         } else if(var1 > 57) {
            return '0';
         } else {
            if(!this.isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
               this.reportInvalidNumber("Leading zeroes not allowed");
            }

            ++this._inputPtr;
            char var2 = var1;
            if(var1 == 48) {
               while(true) {
                  if(this._inputPtr >= this._inputEnd) {
                     var2 = var1;
                     if(!this.loadMore()) {
                        break;
                     }
                  }

                  var2 = this._inputBuffer[this._inputPtr];
                  if(var2 < 48) {
                     return '0';
                  }

                  if(var2 > 57) {
                     return '0';
                  }

                  ++this._inputPtr;
                  var1 = var2;
                  if(var2 != 48) {
                     return var2;
                  }
               }
            }

            return var2;
         }
      }
   }

   private JsonToken parseNumberText2(boolean var1) throws IOException, JsonParseException {
      char[] var12 = this._textBuffer.emptyAndGetCurrentSegment();
      int var10 = 0;
      int var5;
      if(var1) {
         var12[0] = 45;
         var5 = 1;
      } else {
         var5 = 0;
      }

      char var2;
      int var4;
      char[] var11;
      if(this._inputPtr < this._inputEnd) {
         var11 = this._inputBuffer;
         var4 = this._inputPtr;
         this._inputPtr = var4 + 1;
         var2 = var11[var4];
      } else {
         var2 = this.getNextChar("No digit following minus sign");
      }

      char var3 = var2;
      if(var2 == 48) {
         var3 = this._verifyNoLeadingZeroes();
      }

      int var6 = 0;
      var2 = var3;

      int var8;
      boolean var13;
      while(true) {
         if(var2 < 48 || var2 > 57) {
            var13 = false;
            var11 = var12;
            var8 = var6;
            break;
         }

         ++var6;
         var4 = var5;
         var11 = var12;
         if(var5 >= var12.length) {
            var11 = this._textBuffer.finishCurrentSegment();
            var4 = 0;
         }

         var5 = var4 + 1;
         var11[var4] = var2;
         if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
            var2 = 0;
            var13 = true;
            var8 = var6;
            break;
         }

         var12 = this._inputBuffer;
         var4 = this._inputPtr;
         this._inputPtr = var4 + 1;
         var2 = var12[var4];
         var12 = var11;
      }

      if(var8 == 0) {
         StringBuilder var19 = new StringBuilder();
         var19.append("Missing integer part (next char ");
         var19.append(_getCharDesc(var2));
         var19.append(")");
         this.reportInvalidNumber(var19.toString());
      }

      int var14;
      if(var2 == 46) {
         var6 = var5 + 1;
         var11[var5] = var2;
         byte var7 = 0;
         var5 = var6;
         var6 = var7;

         boolean var15;
         while(true) {
            if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
               var15 = true;
               break;
            }

            var12 = this._inputBuffer;
            var14 = this._inputPtr;
            this._inputPtr = var14 + 1;
            var3 = var12[var14];
            var2 = var3;
            var15 = var13;
            if(var3 < 48) {
               break;
            }

            if(var3 > 57) {
               var2 = var3;
               var15 = var13;
               break;
            }

            var14 = var6 + 1;
            var12 = var11;
            var6 = var5;
            if(var5 >= var11.length) {
               var12 = this._textBuffer.finishCurrentSegment();
               var6 = 0;
            }

            var12[var6] = var3;
            var5 = var6 + 1;
            var6 = var14;
            var2 = var3;
            var11 = var12;
         }

         if(var6 == 0) {
            this.reportUnexpectedNumberChar(var2, "Decimal point not followed by a digit");
         }

         var13 = var15;
         var12 = var11;
         var14 = var6;
      } else {
         var14 = 0;
         var12 = var11;
      }

      int var9;
      boolean var16;
      label146: {
         if(var2 != 101) {
            var9 = var5;
            var16 = var13;
            if(var2 != 69) {
               break label146;
            }
         }

         var9 = var5;
         var11 = var12;
         if(var5 >= var12.length) {
            var11 = this._textBuffer.finishCurrentSegment();
            var9 = 0;
         }

         var6 = var9 + 1;
         var11[var9] = var2;
         if(this._inputPtr < this._inputEnd) {
            var12 = this._inputBuffer;
            var5 = this._inputPtr;
            this._inputPtr = var5 + 1;
            var2 = var12[var5];
         } else {
            var2 = this.getNextChar("expected a digit for number exponent");
         }

         label147: {
            if(var2 != 45) {
               var3 = var2;
               var12 = var11;
               var5 = var6;
               if(var2 != 43) {
                  break label147;
               }
            }

            var12 = var11;
            var5 = var6;
            if(var6 >= var11.length) {
               var12 = this._textBuffer.finishCurrentSegment();
               var5 = 0;
            }

            var12[var5] = var2;
            if(this._inputPtr < this._inputEnd) {
               var11 = this._inputBuffer;
               var6 = this._inputPtr;
               this._inputPtr = var6 + 1;
               var3 = var11[var6];
            } else {
               var3 = this.getNextChar("expected a digit for number exponent");
            }

            ++var5;
         }

         var6 = 0;

         boolean var17;
         while(true) {
            if(var3 > 57 || var3 < 48) {
               var9 = var6;
               var6 = var5;
               var17 = var13;
               var4 = var9;
               break;
            }

            ++var6;
            var11 = var12;
            var9 = var5;
            if(var5 >= var12.length) {
               var11 = this._textBuffer.finishCurrentSegment();
               var9 = 0;
            }

            var5 = var9 + 1;
            var11[var9] = var3;
            if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
               var4 = var6;
               boolean var18 = true;
               var6 = var5;
               var17 = var18;
               break;
            }

            var12 = this._inputBuffer;
            var9 = this._inputPtr;
            this._inputPtr = var9 + 1;
            var3 = var12[var9];
            var12 = var11;
         }

         if(var4 == 0) {
            this.reportUnexpectedNumberChar(var3, "Exponent indicator not followed by a digit");
         }

         var9 = var6;
         var16 = var17;
         var10 = var4;
      }

      if(!var16) {
         --this._inputPtr;
      }

      this._textBuffer.setCurrentLength(var9);
      return this.reset(var1, var8, var14, var10);
   }

   protected void _closeInput() throws IOException {
      if(this._reader != null) {
         if(this._ioContext.isResourceManaged() || this.isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
            this._reader.close();
         }

         this._reader = null;
      }

   }

   protected byte[] _decodeBase64(Base64Variant var1) throws IOException, JsonParseException {
      ByteArrayBuilder var7 = this._getByteArrayBuilder();

      while(true) {
         if(this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
         }

         char[] var8 = this._inputBuffer;
         int var3 = this._inputPtr;
         this._inputPtr = var3 + 1;
         char var2 = var8[var3];
         if(var2 > 32) {
            int var4 = var1.decodeBase64Char(var2);
            var3 = var4;
            if(var4 < 0) {
               if(var2 == 34) {
                  return var7.toByteArray();
               }

               var4 = this._decodeBase64Escape(var1, var2, 0);
               var3 = var4;
               if(var4 < 0) {
                  continue;
               }
            }

            if(this._inputPtr >= this._inputEnd) {
               this.loadMoreGuaranteed();
            }

            var8 = this._inputBuffer;
            var4 = this._inputPtr;
            this._inputPtr = var4 + 1;
            var2 = var8[var4];
            int var5 = var1.decodeBase64Char(var2);
            var4 = var5;
            if(var5 < 0) {
               var4 = this._decodeBase64Escape(var1, var2, 1);
            }

            int var6 = var3 << 6 | var4;
            if(this._inputPtr >= this._inputEnd) {
               this.loadMoreGuaranteed();
            }

            var8 = this._inputBuffer;
            var3 = this._inputPtr;
            this._inputPtr = var3 + 1;
            var2 = var8[var3];
            var4 = var1.decodeBase64Char(var2);
            var5 = var4;
            if(var4 < 0) {
               var3 = var4;
               if(var4 != -2) {
                  if(var2 == 34 && !var1.usesPadding()) {
                     var7.append(var6 >> 4);
                     return var7.toByteArray();
                  }

                  var3 = this._decodeBase64Escape(var1, var2, 2);
               }

               var5 = var3;
               if(var3 == -2) {
                  if(this._inputPtr >= this._inputEnd) {
                     this.loadMoreGuaranteed();
                  }

                  var8 = this._inputBuffer;
                  var3 = this._inputPtr;
                  this._inputPtr = var3 + 1;
                  var2 = var8[var3];
                  if(!var1.usesPaddingChar(var2)) {
                     StringBuilder var9 = new StringBuilder();
                     var9.append("expected padding character \'");
                     var9.append(var1.getPaddingChar());
                     var9.append("\'");
                     throw this.reportInvalidBase64Char(var1, var2, 3, var9.toString());
                  }

                  var7.append(var6 >> 4);
                  continue;
               }
            }

            var6 = var6 << 6 | var5;
            if(this._inputPtr >= this._inputEnd) {
               this.loadMoreGuaranteed();
            }

            var8 = this._inputBuffer;
            var3 = this._inputPtr;
            this._inputPtr = var3 + 1;
            var2 = var8[var3];
            var4 = var1.decodeBase64Char(var2);
            var5 = var4;
            if(var4 < 0) {
               var3 = var4;
               if(var4 != -2) {
                  if(var2 == 34 && !var1.usesPadding()) {
                     var7.appendTwoBytes(var6 >> 2);
                     return var7.toByteArray();
                  }

                  var3 = this._decodeBase64Escape(var1, var2, 3);
               }

               var5 = var3;
               if(var3 == -2) {
                  var7.appendTwoBytes(var6 >> 2);
                  continue;
               }
            }

            var7.appendThreeBytes(var6 << 6 | var5);
         }
      }
   }

   protected char _decodeEscaped() throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
         this._reportInvalidEOF(" in character escape sequence");
      }

      char[] var6 = this._inputBuffer;
      int var2 = this._inputPtr;
      this._inputPtr = var2 + 1;
      char var1 = var6[var2];
      if(var1 != 34 && var1 != 47 && var1 != 92) {
         if(var1 != 98) {
            if(var1 != 102) {
               if(var1 != 110) {
                  if(var1 != 114) {
                     switch(var1) {
                     case 116:
                        return '\t';
                     case 117:
                        var2 = 0;

                        int var3;
                        for(var3 = 0; var2 < 4; ++var2) {
                           if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
                              this._reportInvalidEOF(" in character escape sequence");
                           }

                           var6 = this._inputBuffer;
                           int var4 = this._inputPtr;
                           this._inputPtr = var4 + 1;
                           char var7 = var6[var4];
                           int var5 = CharTypes.charToHex(var7);
                           if(var5 < 0) {
                              this._reportUnexpectedChar(var7, "expected a hex-digit for character escape sequence");
                           }

                           var3 = var3 << 4 | var5;
                        }

                        return (char)var3;
                     default:
                        return this._handleUnrecognizedCharacterEscape(var1);
                     }
                  } else {
                     return '\r';
                  }
               } else {
                  return '\n';
               }
            } else {
               return '\f';
            }
         } else {
            return '\b';
         }
      } else {
         return var1;
      }
   }

   protected void _finishString() throws IOException, JsonParseException {
      int var1 = this._inputPtr;
      int var3 = this._inputEnd;
      int var2 = var1;
      if(var1 < var3) {
         int[] var6 = CharTypes.getInputCodeLatin1();
         int var4 = var6.length;

         do {
            char var5 = this._inputBuffer[var1];
            if(var5 < var4 && var6[var5] != 0) {
               var2 = var1;
               if(var5 == 34) {
                  this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, var1 - this._inputPtr);
                  this._inputPtr = var1 + 1;
                  return;
               }
               break;
            }

            var2 = var1 + 1;
            var1 = var2;
         } while(var2 < var3);
      }

      this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, var2 - this._inputPtr);
      this._inputPtr = var2;
      this._finishString2();
   }

   protected void _finishString2() throws IOException, JsonParseException {
      char[] var5 = this._textBuffer.getCurrentSegment();
      int var3 = this._textBuffer.getCurrentSegmentSize();

      while(true) {
         if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(": was expecting closing quote for a string value");
         }

         char[] var6 = this._inputBuffer;
         int var4 = this._inputPtr;
         this._inputPtr = var4 + 1;
         char var2 = var6[var4];
         char var1 = var2;
         if(var2 <= 92) {
            if(var2 == 92) {
               var1 = this._decodeEscaped();
            } else {
               var1 = var2;
               if(var2 <= 34) {
                  if(var2 == 34) {
                     this._textBuffer.setCurrentLength(var3);
                     return;
                  }

                  var1 = var2;
                  if(var2 < 32) {
                     this._throwUnquotedSpace(var2, "string value");
                     var1 = var2;
                  }
               }
            }
         }

         var6 = var5;
         var4 = var3;
         if(var3 >= var5.length) {
            var6 = this._textBuffer.finishCurrentSegment();
            var4 = 0;
         }

         var6[var4] = var1;
         var3 = var4 + 1;
         var5 = var6;
      }
   }

   protected String _getText2(JsonToken var1) {
      if(var1 == null) {
         return null;
      } else {
         switch(null.$SwitchMap$com$fasterxml$jackson$core$JsonToken[var1.ordinal()]) {
         case 1:
            return this._parsingContext.getCurrentName();
         case 2:
         case 3:
         case 4:
            return this._textBuffer.contentsAsString();
         default:
            return var1.asString();
         }
      }
   }

   protected JsonToken _handleApostropheValue() throws IOException, JsonParseException {
      char[] var5 = this._textBuffer.emptyAndGetCurrentSegment();
      int var3 = this._textBuffer.getCurrentSegmentSize();

      while(true) {
         if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(": was expecting closing quote for a string value");
         }

         char[] var6 = this._inputBuffer;
         int var4 = this._inputPtr;
         this._inputPtr = var4 + 1;
         char var2 = var6[var4];
         char var1 = var2;
         if(var2 <= 92) {
            if(var2 == 92) {
               var1 = this._decodeEscaped();
            } else {
               var1 = var2;
               if(var2 <= 39) {
                  if(var2 == 39) {
                     this._textBuffer.setCurrentLength(var3);
                     return JsonToken.VALUE_STRING;
                  }

                  var1 = var2;
                  if(var2 < 32) {
                     this._throwUnquotedSpace(var2, "string value");
                     var1 = var2;
                  }
               }
            }
         }

         var6 = var5;
         var4 = var3;
         if(var3 >= var5.length) {
            var6 = this._textBuffer.finishCurrentSegment();
            var4 = 0;
         }

         var6[var4] = var1;
         var3 = var4 + 1;
         var5 = var6;
      }
   }

   protected JsonToken _handleInvalidNumberStart(int var1, boolean var2) throws IOException, JsonParseException {
      int var5 = var1;
      if(var1 == 73) {
         if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOFInValue();
         }

         char[] var6 = this._inputBuffer;
         var1 = this._inputPtr;
         this._inputPtr = var1 + 1;
         char var8 = var6[var1];
         double var3 = Double.POSITIVE_INFINITY;
         StringBuilder var7;
         String var9;
         if(var8 == 78) {
            if(var2) {
               var9 = "-INF";
            } else {
               var9 = "+INF";
            }

            this._matchToken(var9, 3);
            if(this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
               if(var2) {
                  var3 = Double.NEGATIVE_INFINITY;
               }

               return this.resetAsNaN(var9, var3);
            }

            var7 = new StringBuilder();
            var7.append("Non-standard token \'");
            var7.append(var9);
            var7.append("\': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            this._reportError(var7.toString());
            var5 = var8;
         } else {
            var5 = var8;
            if(var8 == 110) {
               if(var2) {
                  var9 = "-Infinity";
               } else {
                  var9 = "+Infinity";
               }

               this._matchToken(var9, 3);
               if(this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                  if(var2) {
                     var3 = Double.NEGATIVE_INFINITY;
                  }

                  return this.resetAsNaN(var9, var3);
               }

               var7 = new StringBuilder();
               var7.append("Non-standard token \'");
               var7.append(var9);
               var7.append("\': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
               this._reportError(var7.toString());
               var5 = var8;
            }
         }
      }

      this.reportUnexpectedNumberChar(var5, "expected digit (0-9) to follow minus sign, for valid numeric value");
      return null;
   }

   protected JsonToken _handleUnexpectedValue(int var1) throws IOException, JsonParseException {
      if(var1 != 39) {
         if(var1 == 43) {
            if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
               this._reportInvalidEOFInValue();
            }

            char[] var2 = this._inputBuffer;
            var1 = this._inputPtr;
            this._inputPtr = var1 + 1;
            return this._handleInvalidNumberStart(var2[var1], false);
         }

         if(var1 != 73) {
            if(var1 == 78) {
               this._matchToken("NaN", 1);
               if(this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                  return this.resetAsNaN("NaN", Double.NaN);
               }

               this._reportError("Non-standard token \'NaN\': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            }
         } else {
            this._matchToken("Infinity", 1);
            if(this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
               return this.resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
            }

            this._reportError("Non-standard token \'Infinity\': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
         }
      } else if(this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
         return this._handleApostropheValue();
      }

      this._reportUnexpectedChar(var1, "expected a valid value (number, String, array, object, \'true\', \'false\' or \'null\')");
      return null;
   }

   protected String _handleUnusualFieldName(int var1) throws IOException, JsonParseException {
      if(var1 == 39 && this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
         return this._parseApostropheFieldName();
      } else {
         if(!this.isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            this._reportUnexpectedChar(var1, "was expecting double-quote to start field name");
         }

         int[] var8 = CharTypes.getInputCodeLatin1JsNames();
         int var5 = var8.length;
         boolean var7;
         if(var1 < var5) {
            if(var8[var1] == 0 && (var1 < 48 || var1 > 57)) {
               var7 = true;
            } else {
               var7 = false;
            }
         } else {
            var7 = Character.isJavaIdentifierPart((char)var1);
         }

         if(!var7) {
            this._reportUnexpectedChar(var1, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
         }

         int var4 = this._inputPtr;
         int var2 = this._hashSeed;
         int var6 = this._inputEnd;
         int var3 = var2;
         var1 = var4;
         if(var4 < var6) {
            var1 = var4;

            do {
               char var9 = this._inputBuffer[var1];
               if(var9 < var5) {
                  if(var8[var9] != 0) {
                     var3 = this._inputPtr - 1;
                     this._inputPtr = var1;
                     return this._symbols.findSymbol(this._inputBuffer, var3, var1 - var3, var2);
                  }
               } else if(!Character.isJavaIdentifierPart((char)var9)) {
                  var3 = this._inputPtr - 1;
                  this._inputPtr = var1;
                  return this._symbols.findSymbol(this._inputBuffer, var3, var1 - var3, var2);
               }

               var3 = var2 * 33 + var9;
               var4 = var1 + 1;
               var2 = var3;
               var1 = var4;
            } while(var4 < var6);

            var1 = var4;
         }

         var2 = this._inputPtr;
         this._inputPtr = var1;
         return this._parseUnusualFieldName2(var2 - 1, var3, var8);
      }
   }

   protected void _matchToken(String var1, int var2) throws IOException, JsonParseException {
      int var5 = var1.length();

      int var4;
      do {
         if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidToken(var1.substring(0, var2));
         }

         if(this._inputBuffer[this._inputPtr] != var1.charAt(var2)) {
            this._reportInvalidToken(var1.substring(0, var2));
         }

         ++this._inputPtr;
         var4 = var2 + 1;
         var2 = var4;
      } while(var4 < var5);

      if(this._inputPtr < this._inputEnd || this.loadMore()) {
         char var3 = this._inputBuffer[this._inputPtr];
         if(var3 >= 48 && var3 != 93) {
            if(var3 != 125) {
               if(Character.isJavaIdentifierPart(var3)) {
                  this._reportInvalidToken(var1.substring(0, var4));
               }

            }
         }
      }
   }

   protected String _parseApostropheFieldName() throws IOException, JsonParseException {
      int var3 = this._inputPtr;
      int var4 = this._hashSeed;
      int var5 = this._inputEnd;
      int var2 = var3;
      int var1 = var4;
      if(var3 < var5) {
         int[] var7 = CharTypes.getInputCodeLatin1();
         int var6 = var7.length;
         var1 = var4;
         var2 = var3;

         while(true) {
            char var8 = this._inputBuffer[var2];
            if(var8 == 39) {
               var3 = this._inputPtr;
               this._inputPtr = var2 + 1;
               return this._symbols.findSymbol(this._inputBuffer, var3, var2 - var3, var1);
            }

            if(var8 < var6 && var7[var8] != 0) {
               break;
            }

            var4 = var1 * 33 + var8;
            var3 = var2 + 1;
            var2 = var3;
            var1 = var4;
            if(var3 >= var5) {
               var1 = var4;
               var2 = var3;
               break;
            }
         }
      }

      var3 = this._inputPtr;
      this._inputPtr = var2;
      return this._parseFieldName2(var3, var1, 39);
   }

   protected String _parseFieldName(int var1) throws IOException, JsonParseException {
      if(var1 != 34) {
         return this._handleUnusualFieldName(var1);
      } else {
         var1 = this._inputPtr;
         int var2 = this._hashSeed;
         int var5 = this._inputEnd;
         int var4 = var2;
         int var3 = var1;
         if(var1 < var5) {
            int[] var8 = CharTypes.getInputCodeLatin1();
            int var6 = var8.length;

            do {
               char var7 = this._inputBuffer[var1];
               if(var7 < var6 && var8[var7] != 0) {
                  var4 = var2;
                  var3 = var1;
                  if(var7 == 34) {
                     var3 = this._inputPtr;
                     this._inputPtr = var1 + 1;
                     return this._symbols.findSymbol(this._inputBuffer, var3, var1 - var3, var2);
                  }
                  break;
               }

               var4 = var2 * 33 + var7;
               var3 = var1 + 1;
               var2 = var4;
               var1 = var3;
            } while(var3 < var5);
         }

         var1 = this._inputPtr;
         this._inputPtr = var3;
         return this._parseFieldName2(var1, var4, 34);
      }
   }

   protected int _readBinary(Base64Variant var1, OutputStream var2, byte[] var3) throws IOException, JsonParseException {
      int var10 = var3.length;
      int var5 = 0;
      int var7 = 0;

      int var6;
      while(true) {
         char var4;
         char[] var12;
         do {
            if(this._inputPtr >= this._inputEnd) {
               this.loadMoreGuaranteed();
            }

            var12 = this._inputBuffer;
            var6 = this._inputPtr;
            this._inputPtr = var6 + 1;
            var4 = var12[var6];
         } while(var4 <= 32);

         var6 = var1.decodeBase64Char(var4);
         int var9 = var6;
         if(var6 < 0) {
            if(var4 == 34) {
               var6 = var7;
               break;
            }

            var6 = this._decodeBase64Escape(var1, var4, 0);
            var9 = var6;
            if(var6 < 0) {
               continue;
            }
         }

         int var8 = var5;
         var6 = var7;
         if(var5 > var10 - 3) {
            var6 = var7 + var5;
            var2.write(var3, 0, var5);
            var8 = 0;
         }

         if(this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
         }

         var12 = this._inputBuffer;
         var5 = this._inputPtr;
         this._inputPtr = var5 + 1;
         var4 = var12[var5];
         var7 = var1.decodeBase64Char(var4);
         var5 = var7;
         if(var7 < 0) {
            var5 = this._decodeBase64Escape(var1, var4, 1);
         }

         int var11 = var9 << 6 | var5;
         if(this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
         }

         label99: {
            var12 = this._inputBuffer;
            var5 = this._inputPtr;
            this._inputPtr = var5 + 1;
            var4 = var12[var5];
            var7 = var1.decodeBase64Char(var4);
            var9 = var7;
            if(var7 < 0) {
               var5 = var7;
               if(var7 != -2) {
                  if(var4 == 34 && !var1.usesPadding()) {
                     var3[var8] = (byte)(var11 >> 4);
                     var5 = var8 + 1;
                     break;
                  }

                  var5 = this._decodeBase64Escape(var1, var4, 2);
               }

               var9 = var5;
               if(var5 == -2) {
                  if(this._inputPtr >= this._inputEnd) {
                     this.loadMoreGuaranteed();
                  }

                  var12 = this._inputBuffer;
                  var5 = this._inputPtr;
                  this._inputPtr = var5 + 1;
                  var4 = var12[var5];
                  if(!var1.usesPaddingChar(var4)) {
                     StringBuilder var13 = new StringBuilder();
                     var13.append("expected padding character \'");
                     var13.append(var1.getPaddingChar());
                     var13.append("\'");
                     throw this.reportInvalidBase64Char(var1, var4, 3, var13.toString());
                  }

                  var5 = var8 + 1;
                  var3[var8] = (byte)(var11 >> 4);
                  break label99;
               }
            }

            var11 = var11 << 6 | var9;
            if(this._inputPtr >= this._inputEnd) {
               this.loadMoreGuaranteed();
            }

            var12 = this._inputBuffer;
            var5 = this._inputPtr;
            this._inputPtr = var5 + 1;
            var4 = var12[var5];
            var7 = var1.decodeBase64Char(var4);
            var9 = var7;
            if(var7 < 0) {
               var5 = var7;
               if(var7 != -2) {
                  if(var4 == 34 && !var1.usesPadding()) {
                     var7 = var11 >> 2;
                     var9 = var8 + 1;
                     var3[var8] = (byte)(var7 >> 8);
                     var5 = var9 + 1;
                     var3[var9] = (byte)var7;
                     break;
                  }

                  var5 = this._decodeBase64Escape(var1, var4, 3);
               }

               var9 = var5;
               if(var5 == -2) {
                  var7 = var11 >> 2;
                  var9 = var8 + 1;
                  var3[var8] = (byte)(var7 >> 8);
                  var5 = var9 + 1;
                  var3[var9] = (byte)var7;
                  var7 = var6;
                  continue;
               }
            }

            var7 = var11 << 6 | var9;
            var5 = var8 + 1;
            var3[var8] = (byte)(var7 >> 16);
            var8 = var5 + 1;
            var3[var5] = (byte)(var7 >> 8);
            var5 = var8 + 1;
            var3[var8] = (byte)var7;
         }

         var7 = var6;
      }

      this._tokenIncomplete = false;
      var7 = var6;
      if(var5 > 0) {
         var7 = var6 + var5;
         var2.write(var3, 0, var5);
      }

      return var7;
   }

   protected void _releaseBuffers() throws IOException {
      super._releaseBuffers();
      char[] var1 = this._inputBuffer;
      if(var1 != null) {
         this._inputBuffer = null;
         this._ioContext.releaseTokenBuffer(var1);
      }

   }

   protected void _reportInvalidToken(String var1) throws IOException, JsonParseException {
      this._reportInvalidToken(var1, "\'null\', \'true\', \'false\' or NaN");
   }

   protected void _reportInvalidToken(String var1, String var2) throws IOException, JsonParseException {
      StringBuilder var4 = new StringBuilder(var1);

      while(this._inputPtr < this._inputEnd || this.loadMore()) {
         char var3 = this._inputBuffer[this._inputPtr];
         if(!Character.isJavaIdentifierPart(var3)) {
            break;
         }

         ++this._inputPtr;
         var4.append(var3);
      }

      StringBuilder var5 = new StringBuilder();
      var5.append("Unrecognized token \'");
      var5.append(var4.toString());
      var5.append("\': was expecting ");
      this._reportError(var5.toString());
   }

   protected void _skipCR() throws IOException {
      if((this._inputPtr < this._inputEnd || this.loadMore()) && this._inputBuffer[this._inputPtr] == 10) {
         ++this._inputPtr;
      }

      ++this._currInputRow;
      this._currInputRowStart = this._inputPtr;
   }

   protected void _skipLF() throws IOException {
      ++this._currInputRow;
      this._currInputRowStart = this._inputPtr;
   }

   protected void _skipString() throws IOException, JsonParseException {
      this._tokenIncomplete = false;
      int var1 = this._inputPtr;
      int var2 = this._inputEnd;
      char[] var5 = this._inputBuffer;

      while(true) {
         int var4 = var1;
         int var3 = var2;
         if(var1 >= var2) {
            this._inputPtr = var1;
            if(!this.loadMore()) {
               this._reportInvalidEOF(": was expecting closing quote for a string value");
            }

            var4 = this._inputPtr;
            var3 = this._inputEnd;
         }

         var1 = var4 + 1;
         char var6 = var5[var4];
         if(var6 <= 92) {
            if(var6 == 92) {
               this._inputPtr = var1;
               this._decodeEscaped();
               var1 = this._inputPtr;
               var2 = this._inputEnd;
               continue;
            }

            if(var6 <= 34) {
               if(var6 == 34) {
                  this._inputPtr = var1;
                  return;
               }

               if(var6 < 32) {
                  this._inputPtr = var1;
                  this._throwUnquotedSpace(var6, "string value");
               }
            }
         }

         var2 = var3;
      }
   }

   public void close() throws IOException {
      super.close();
      this._symbols.release();
   }

   public byte[] getBinaryValue(Base64Variant var1) throws IOException, JsonParseException {
      if(this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Current token (");
         var2.append(this._currToken);
         var2.append(") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
         this._reportError(var2.toString());
      }

      if(this._tokenIncomplete) {
         try {
            this._binaryValue = this._decodeBase64(var1);
         } catch (IllegalArgumentException var4) {
            StringBuilder var3 = new StringBuilder();
            var3.append("Failed to decode VALUE_STRING as base64 (");
            var3.append(var1);
            var3.append("): ");
            var3.append(var4.getMessage());
            throw this._constructError(var3.toString());
         }

         this._tokenIncomplete = false;
      } else if(this._binaryValue == null) {
         ByteArrayBuilder var5 = this._getByteArrayBuilder();
         this._decodeBase64(this.getText(), var5, var1);
         this._binaryValue = var5.toByteArray();
      }

      return this._binaryValue;
   }

   public ObjectCodec getCodec() {
      return this._objectCodec;
   }

   public Object getInputSource() {
      return this._reader;
   }

   protected char getNextChar(String var1) throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
         this._reportInvalidEOF(var1);
      }

      char[] var3 = this._inputBuffer;
      int var2 = this._inputPtr;
      this._inputPtr = var2 + 1;
      return var3[var2];
   }

   public String getText() throws IOException, JsonParseException {
      JsonToken var1 = this._currToken;
      if(var1 == JsonToken.VALUE_STRING) {
         if(this._tokenIncomplete) {
            this._tokenIncomplete = false;
            this._finishString();
         }

         return this._textBuffer.contentsAsString();
      } else {
         return this._getText2(var1);
      }
   }

   public char[] getTextCharacters() throws IOException, JsonParseException {
      if(this._currToken != null) {
         switch(null.$SwitchMap$com$fasterxml$jackson$core$JsonToken[this._currToken.ordinal()]) {
         case 1:
            if(!this._nameCopied) {
               String var2 = this._parsingContext.getCurrentName();
               int var1 = var2.length();
               if(this._nameCopyBuffer == null) {
                  this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(var1);
               } else if(this._nameCopyBuffer.length < var1) {
                  this._nameCopyBuffer = new char[var1];
               }

               var2.getChars(0, var1, this._nameCopyBuffer, 0);
               this._nameCopied = true;
            }

            return this._nameCopyBuffer;
         case 2:
            if(this._tokenIncomplete) {
               this._tokenIncomplete = false;
               this._finishString();
            }
         case 3:
         case 4:
            return this._textBuffer.getTextBuffer();
         default:
            return this._currToken.asCharArray();
         }
      } else {
         return null;
      }
   }

   public int getTextLength() throws IOException, JsonParseException {
      if(this._currToken != null) {
         switch(null.$SwitchMap$com$fasterxml$jackson$core$JsonToken[this._currToken.ordinal()]) {
         case 1:
            return this._parsingContext.getCurrentName().length();
         case 2:
            if(this._tokenIncomplete) {
               this._tokenIncomplete = false;
               this._finishString();
            }
         case 3:
         case 4:
            return this._textBuffer.size();
         default:
            return this._currToken.asCharArray().length;
         }
      } else {
         return 0;
      }
   }

   public int getTextOffset() throws IOException, JsonParseException {
      if(this._currToken != null) {
         switch(null.$SwitchMap$com$fasterxml$jackson$core$JsonToken[this._currToken.ordinal()]) {
         case 1:
            return 0;
         case 2:
            if(this._tokenIncomplete) {
               this._tokenIncomplete = false;
               this._finishString();
            }
         case 3:
         case 4:
            return this._textBuffer.getTextOffset();
         default:
            return 0;
         }
      } else {
         return 0;
      }
   }

   public String getValueAsString() throws IOException, JsonParseException {
      if(this._currToken == JsonToken.VALUE_STRING) {
         if(this._tokenIncomplete) {
            this._tokenIncomplete = false;
            this._finishString();
         }

         return this._textBuffer.contentsAsString();
      } else {
         return super.getValueAsString((String)null);
      }
   }

   public String getValueAsString(String var1) throws IOException, JsonParseException {
      if(this._currToken == JsonToken.VALUE_STRING) {
         if(this._tokenIncomplete) {
            this._tokenIncomplete = false;
            this._finishString();
         }

         return this._textBuffer.contentsAsString();
      } else {
         return super.getValueAsString(var1);
      }
   }

   protected boolean loadMore() throws IOException {
      this._currInputProcessed += (long)this._inputEnd;
      this._currInputRowStart -= this._inputEnd;
      if(this._reader != null) {
         int var1 = this._reader.read(this._inputBuffer, 0, this._inputBuffer.length);
         if(var1 > 0) {
            this._inputPtr = 0;
            this._inputEnd = var1;
            return true;
         }

         this._closeInput();
         if(var1 == 0) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Reader returned 0 characters when trying to read ");
            var2.append(this._inputEnd);
            throw new IOException(var2.toString());
         }
      }

      return false;
   }

   public Boolean nextBooleanValue() throws IOException, JsonParseException {
      if(this._currToken == JsonToken.FIELD_NAME) {
         this._nameCopied = false;
         JsonToken var1 = this._nextToken;
         this._nextToken = null;
         this._currToken = var1;
         if(var1 == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
         } else if(var1 == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
         } else if(var1 == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            return null;
         } else {
            if(var1 == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }

            return null;
         }
      } else {
         switch(null.$SwitchMap$com$fasterxml$jackson$core$JsonToken[this.nextToken().ordinal()]) {
         case 5:
            return Boolean.TRUE;
         case 6:
            return Boolean.FALSE;
         default:
            return null;
         }
      }
   }

   public int nextIntValue(int var1) throws IOException, JsonParseException {
      if(this._currToken == JsonToken.FIELD_NAME) {
         this._nameCopied = false;
         JsonToken var2 = this._nextToken;
         this._nextToken = null;
         this._currToken = var2;
         if(var2 == JsonToken.VALUE_NUMBER_INT) {
            return this.getIntValue();
         } else if(var2 == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            return var1;
         } else {
            if(var2 == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }

            return var1;
         }
      } else {
         if(this.nextToken() == JsonToken.VALUE_NUMBER_INT) {
            var1 = this.getIntValue();
         }

         return var1;
      }
   }

   public long nextLongValue(long var1) throws IOException, JsonParseException {
      if(this._currToken == JsonToken.FIELD_NAME) {
         this._nameCopied = false;
         JsonToken var3 = this._nextToken;
         this._nextToken = null;
         this._currToken = var3;
         if(var3 == JsonToken.VALUE_NUMBER_INT) {
            return this.getLongValue();
         } else if(var3 == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            return var1;
         } else {
            if(var3 == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }

            return var1;
         }
      } else {
         if(this.nextToken() == JsonToken.VALUE_NUMBER_INT) {
            var1 = this.getLongValue();
         }

         return var1;
      }
   }

   public String nextTextValue() throws IOException, JsonParseException {
      JsonToken var2 = this._currToken;
      JsonToken var3 = JsonToken.FIELD_NAME;
      String var1 = null;
      if(var2 == var3) {
         this._nameCopied = false;
         JsonToken var4 = this._nextToken;
         this._nextToken = null;
         this._currToken = var4;
         if(var4 == JsonToken.VALUE_STRING) {
            if(this._tokenIncomplete) {
               this._tokenIncomplete = false;
               this._finishString();
            }

            return this._textBuffer.contentsAsString();
         } else if(var4 == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            return null;
         } else {
            if(var4 == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }

            return null;
         }
      } else {
         if(this.nextToken() == JsonToken.VALUE_STRING) {
            var1 = this.getText();
         }

         return var1;
      }
   }

   public JsonToken nextToken() throws IOException, JsonParseException {
      this._numTypesValid = 0;
      if(this._currToken == JsonToken.FIELD_NAME) {
         return this._nextAfterName();
      } else {
         if(this._tokenIncomplete) {
            this._skipString();
         }

         int var2 = this._skipWSOrEnd();
         if(var2 < 0) {
            this.close();
            this._currToken = null;
            return null;
         } else {
            this._tokenInputTotal = this._currInputProcessed + (long)this._inputPtr - 1L;
            this._tokenInputRow = this._currInputRow;
            this._tokenInputCol = this._inputPtr - this._currInputRowStart - 1;
            this._binaryValue = null;
            JsonToken var6;
            if(var2 == 93) {
               if(!this._parsingContext.inArray()) {
                  this._reportMismatchedEndMarker(var2, '}');
               }

               this._parsingContext = this._parsingContext.getParent();
               var6 = JsonToken.END_ARRAY;
               this._currToken = var6;
               return var6;
            } else if(var2 == 125) {
               if(!this._parsingContext.inObject()) {
                  this._reportMismatchedEndMarker(var2, ']');
               }

               this._parsingContext = this._parsingContext.getParent();
               var6 = JsonToken.END_OBJECT;
               this._currToken = var6;
               return var6;
            } else {
               int var1 = var2;
               if(this._parsingContext.expectComma()) {
                  if(var2 != 44) {
                     StringBuilder var4 = new StringBuilder();
                     var4.append("was expecting comma to separate ");
                     var4.append(this._parsingContext.getTypeDesc());
                     var4.append(" entries");
                     this._reportUnexpectedChar(var2, var4.toString());
                  }

                  var1 = this._skipWS();
               }

               boolean var3 = this._parsingContext.inObject();
               var2 = var1;
               if(var3) {
                  String var5 = this._parseFieldName(var1);
                  this._parsingContext.setCurrentName(var5);
                  this._currToken = JsonToken.FIELD_NAME;
                  var1 = this._skipWS();
                  if(var1 != 58) {
                     this._reportUnexpectedChar(var1, "was expecting a colon to separate field name and value");
                  }

                  var2 = this._skipWS();
               }

               if(var2 != 34) {
                  label94: {
                     if(var2 != 45) {
                        label116: {
                           if(var2 == 91) {
                              if(!var3) {
                                 this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                              }

                              var6 = JsonToken.START_ARRAY;
                              break label94;
                           }

                           label89: {
                              if(var2 != 93) {
                                 if(var2 == 102) {
                                    this._matchToken("false", 1);
                                    var6 = JsonToken.VALUE_FALSE;
                                    break label94;
                                 }

                                 if(var2 == 110) {
                                    this._matchToken("null", 1);
                                    var6 = JsonToken.VALUE_NULL;
                                    break label94;
                                 }

                                 if(var2 == 116) {
                                    break label89;
                                 }

                                 if(var2 == 123) {
                                    if(!var3) {
                                       this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                                    }

                                    var6 = JsonToken.START_OBJECT;
                                    break label94;
                                 }

                                 if(var2 != 125) {
                                    switch(var2) {
                                    case 48:
                                    case 49:
                                    case 50:
                                    case 51:
                                    case 52:
                                    case 53:
                                    case 54:
                                    case 55:
                                    case 56:
                                    case 57:
                                       break label116;
                                    default:
                                       var6 = this._handleUnexpectedValue(var2);
                                       break label94;
                                    }
                                 }
                              }

                              this._reportUnexpectedChar(var2, "expected a value");
                           }

                           this._matchToken("true", 1);
                           var6 = JsonToken.VALUE_TRUE;
                           break label94;
                        }
                     }

                     var6 = this.parseNumberText(var2);
                  }
               } else {
                  this._tokenIncomplete = true;
                  var6 = JsonToken.VALUE_STRING;
               }

               if(var3) {
                  this._nextToken = var6;
                  return this._currToken;
               } else {
                  this._currToken = var6;
                  return var6;
               }
            }
         }
      }
   }

   protected JsonToken parseNumberText(int var1) throws IOException, JsonParseException {
      byte var11 = 0;
      int var5 = 0;
      byte var9 = 0;
      byte var4 = 1;
      boolean var13;
      if(var1 == 45) {
         var13 = true;
      } else {
         var13 = false;
      }

      int var10;
      label120: {
         int var6 = this._inputPtr;
         var10 = var6 - 1;
         int var12 = this._inputEnd;
         int var2 = var6;
         int var3 = var1;
         char[] var15;
         if(var13) {
            if(var6 >= this._inputEnd) {
               break label120;
            }

            var15 = this._inputBuffer;
            var2 = var6 + 1;
            var3 = var15[var6];
            if(var3 > 57 || var3 < 48) {
               this._inputPtr = var2;
               return this._handleInvalidNumberStart(var3, true);
            }
         }

         var1 = var4;
         if(var3 != 48) {
            label110:
            while(var2 < this._inputEnd) {
               var15 = this._inputBuffer;
               var3 = var2 + 1;
               char var16 = var15[var2];
               if(var16 < 48 || var16 > 57) {
                  int var17;
                  if(var16 == 46) {
                     var2 = 0;
                     var17 = var3;

                     while(true) {
                        if(var17 >= var12) {
                           break label110;
                        }

                        var15 = this._inputBuffer;
                        var3 = var17 + 1;
                        var16 = var15[var17];
                        if(var16 < 48 || var16 > 57) {
                           if(var2 == 0) {
                              this.reportUnexpectedNumberChar(var16, "Decimal point not followed by a digit");
                           }
                           break;
                        }

                        ++var2;
                        var17 = var3;
                     }
                  } else {
                     var2 = 0;
                  }

                  int var7;
                  int var8;
                  boolean var14;
                  label133: {
                     if(var16 != 101) {
                        var6 = var1;
                        var14 = var13;
                        var7 = var2;
                        var8 = var3;
                        if(var16 != 69) {
                           break label133;
                        }
                     }

                     if(var3 >= var12) {
                        break;
                     }

                     var15 = this._inputBuffer;
                     var17 = var3 + 1;
                     char var18 = var15[var3];
                     char var19;
                     char var20;
                     if(var18 != 45 && var18 != 43) {
                        var3 = var17;
                        var17 = var11;
                        var20 = var18;
                     } else {
                        if(var17 >= var12) {
                           break;
                        }

                        var15 = this._inputBuffer;
                        var5 = var17 + 1;
                        var19 = var15[var17];
                        var17 = var9;
                        var20 = var19;
                        var3 = var5;
                     }

                     while(var20 <= 57 && var20 >= 48) {
                        ++var17;
                        if(var3 >= var12) {
                           break label110;
                        }

                        var15 = this._inputBuffer;
                        var5 = var3 + 1;
                        var19 = var15[var3];
                        var20 = var19;
                        var3 = var5;
                     }

                     var5 = var17;
                     var6 = var1;
                     var14 = var13;
                     var7 = var2;
                     var8 = var3;
                     if(var17 == 0) {
                        this.reportUnexpectedNumberChar(var20, "Exponent indicator not followed by a digit");
                        var8 = var3;
                        var7 = var2;
                        var14 = var13;
                        var6 = var1;
                        var5 = var17;
                     }
                  }

                  var1 = var8 - 1;
                  this._inputPtr = var1;
                  this._textBuffer.resetWithShared(this._inputBuffer, var10, var1 - var10);
                  return this.reset(var14, var6, var7, var5);
               }

               ++var1;
               var2 = var3;
            }
         }
      }

      var1 = var10;
      if(var13) {
         var1 = var10 + 1;
      }

      this._inputPtr = var1;
      return this.parseNumberText2(var13);
   }

   public int readBinaryValue(Base64Variant var1, OutputStream var2) throws IOException, JsonParseException {
      if(this._tokenIncomplete && this._currToken == JsonToken.VALUE_STRING) {
         byte[] var4 = this._ioContext.allocBase64Buffer();

         int var3;
         try {
            var3 = this._readBinary(var1, var2, var4);
         } finally {
            this._ioContext.releaseBase64Buffer(var4);
         }

         return var3;
      } else {
         byte[] var7 = this.getBinaryValue(var1);
         var2.write(var7);
         return var7.length;
      }
   }

   public int releaseBuffered(Writer var1) throws IOException {
      int var2 = this._inputEnd - this._inputPtr;
      if(var2 < 1) {
         return 0;
      } else {
         int var3 = this._inputPtr;
         var1.write(this._inputBuffer, var3, var2);
         return var2;
      }
   }

   public void setCodec(ObjectCodec var1) {
      this._objectCodec = var1;
   }
}
