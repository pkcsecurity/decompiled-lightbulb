package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.sym.BytesToNameCanonicalizer;
import com.fasterxml.jackson.core.sym.Name;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class UTF8StreamJsonParser extends ParserBase {

   static final byte BYTE_LF = 10;
   private static final int[] sInputCodesLatin1 = CharTypes.getInputCodeLatin1();
   private static final int[] sInputCodesUtf8 = CharTypes.getInputCodeUtf8();
   protected boolean _bufferRecyclable;
   protected byte[] _inputBuffer;
   protected InputStream _inputStream;
   protected ObjectCodec _objectCodec;
   private int _quad1;
   protected int[] _quadBuffer = new int[16];
   protected final BytesToNameCanonicalizer _symbols;
   protected boolean _tokenIncomplete = false;


   public UTF8StreamJsonParser(IOContext var1, int var2, InputStream var3, ObjectCodec var4, BytesToNameCanonicalizer var5, byte[] var6, int var7, int var8, boolean var9) {
      super(var1, var2);
      this._inputStream = var3;
      this._objectCodec = var4;
      this._symbols = var5;
      this._inputBuffer = var6;
      this._inputPtr = var7;
      this._inputEnd = var8;
      this._bufferRecyclable = var9;
   }

   private int _decodeUtf8_2(int var1) throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd) {
         this.loadMoreGuaranteed();
      }

      byte[] var3 = this._inputBuffer;
      int var2 = this._inputPtr;
      this._inputPtr = var2 + 1;
      byte var4 = var3[var2];
      if((var4 & 192) != 128) {
         this._reportInvalidOther(var4 & 255, this._inputPtr);
      }

      return (var1 & 31) << 6 | var4 & 63;
   }

   private int _decodeUtf8_3(int var1) throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd) {
         this.loadMoreGuaranteed();
      }

      byte[] var4 = this._inputBuffer;
      int var2 = this._inputPtr;
      this._inputPtr = var2 + 1;
      byte var5 = var4[var2];
      if((var5 & 192) != 128) {
         this._reportInvalidOther(var5 & 255, this._inputPtr);
      }

      if(this._inputPtr >= this._inputEnd) {
         this.loadMoreGuaranteed();
      }

      var4 = this._inputBuffer;
      int var3 = this._inputPtr;
      this._inputPtr = var3 + 1;
      byte var6 = var4[var3];
      if((var6 & 192) != 128) {
         this._reportInvalidOther(var6 & 255, this._inputPtr);
      }

      return ((var1 & 15) << 6 | var5 & 63) << 6 | var6 & 63;
   }

   private int _decodeUtf8_3fast(int var1) throws IOException, JsonParseException {
      byte[] var4 = this._inputBuffer;
      int var2 = this._inputPtr;
      this._inputPtr = var2 + 1;
      byte var5 = var4[var2];
      if((var5 & 192) != 128) {
         this._reportInvalidOther(var5 & 255, this._inputPtr);
      }

      var4 = this._inputBuffer;
      int var3 = this._inputPtr;
      this._inputPtr = var3 + 1;
      byte var6 = var4[var3];
      if((var6 & 192) != 128) {
         this._reportInvalidOther(var6 & 255, this._inputPtr);
      }

      return ((var1 & 15) << 6 | var5 & 63) << 6 | var6 & 63;
   }

   private int _decodeUtf8_4(int var1) throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd) {
         this.loadMoreGuaranteed();
      }

      byte[] var5 = this._inputBuffer;
      int var2 = this._inputPtr;
      this._inputPtr = var2 + 1;
      byte var6 = var5[var2];
      if((var6 & 192) != 128) {
         this._reportInvalidOther(var6 & 255, this._inputPtr);
      }

      if(this._inputPtr >= this._inputEnd) {
         this.loadMoreGuaranteed();
      }

      var5 = this._inputBuffer;
      int var3 = this._inputPtr;
      this._inputPtr = var3 + 1;
      byte var7 = var5[var3];
      if((var7 & 192) != 128) {
         this._reportInvalidOther(var7 & 255, this._inputPtr);
      }

      if(this._inputPtr >= this._inputEnd) {
         this.loadMoreGuaranteed();
      }

      var5 = this._inputBuffer;
      int var4 = this._inputPtr;
      this._inputPtr = var4 + 1;
      byte var8 = var5[var4];
      if((var8 & 192) != 128) {
         this._reportInvalidOther(var8 & 255, this._inputPtr);
      }

      return ((((var1 & 7) << 6 | var6 & 63) << 6 | var7 & 63) << 6 | var8 & 63) - 65536;
   }

   private void _finishString2(char[] var1, int var2) throws IOException, JsonParseException {
      int[] var7 = sInputCodesUtf8;
      byte[] var8 = this._inputBuffer;
      char[] var6 = var1;

      label52:
      while(true) {
         int var3 = this._inputPtr;
         int var4 = var3;
         if(var3 >= this._inputEnd) {
            this.loadMoreGuaranteed();
            var4 = this._inputPtr;
         }

         var1 = var6;
         var3 = var2;
         if(var2 >= var6.length) {
            var1 = this._textBuffer.finishCurrentSegment();
            var3 = 0;
         }

         for(int var5 = Math.min(this._inputEnd, var1.length - var3 + var4); var4 < var5; ++var3) {
            var2 = var4 + 1;
            var4 = var8[var4] & 255;
            if(var7[var4] != 0) {
               this._inputPtr = var2;
               if(var4 == 34) {
                  this._textBuffer.setCurrentLength(var3);
                  return;
               }

               switch(var7[var4]) {
               case 1:
                  var2 = this._decodeEscaped();
                  break;
               case 2:
                  var2 = this._decodeUtf8_2(var4);
                  break;
               case 3:
                  if(this._inputEnd - this._inputPtr >= 2) {
                     var2 = this._decodeUtf8_3fast(var4);
                  } else {
                     var2 = this._decodeUtf8_3(var4);
                  }
                  break;
               case 4:
                  var5 = this._decodeUtf8_4(var4);
                  var4 = var3 + 1;
                  var1[var3] = (char)('\ud800' | var5 >> 10);
                  var2 = var4;
                  var6 = var1;
                  if(var4 >= var1.length) {
                     var6 = this._textBuffer.finishCurrentSegment();
                     var2 = 0;
                  }

                  var4 = var5 & 1023 | '\udc00';
                  var3 = var2;
                  var2 = var4;
                  var1 = var6;
                  break;
               default:
                  if(var4 < 32) {
                     this._throwUnquotedSpace(var4, "string value");
                     var2 = var4;
                  } else {
                     this._reportInvalidChar(var4);
                     var2 = var4;
                  }
               }

               var6 = var1;
               var4 = var3;
               if(var3 >= var1.length) {
                  var6 = this._textBuffer.finishCurrentSegment();
                  var4 = 0;
               }

               var6[var4] = (char)var2;
               var2 = var4 + 1;
               continue label52;
            }

            var1[var3] = (char)var4;
            var4 = var2;
         }

         this._inputPtr = var4;
         var6 = var1;
         var2 = var3;
      }
   }

   private boolean _isNextTokenNameMaybe(int var1, SerializableString var2) throws IOException, JsonParseException {
      String var4 = this._parseFieldName(var1).getName();
      this._parsingContext.setCurrentName(var4);
      boolean var3 = var4.equals(var2.getValue());
      this._currToken = JsonToken.FIELD_NAME;
      var1 = this._skipWS();
      if(var1 != 58) {
         this._reportUnexpectedChar(var1, "was expecting a colon to separate field name and value");
      }

      var1 = this._skipWS();
      if(var1 == 34) {
         this._tokenIncomplete = true;
         this._nextToken = JsonToken.VALUE_STRING;
         return var3;
      } else {
         JsonToken var5;
         label50: {
            if(var1 != 45) {
               label56: {
                  if(var1 == 91) {
                     var5 = JsonToken.START_ARRAY;
                     break label50;
                  }

                  label46: {
                     if(var1 != 93) {
                        if(var1 == 102) {
                           this._matchToken("false", 1);
                           var5 = JsonToken.VALUE_FALSE;
                           break label50;
                        }

                        if(var1 == 110) {
                           this._matchToken("null", 1);
                           var5 = JsonToken.VALUE_NULL;
                           break label50;
                        }

                        if(var1 == 116) {
                           break label46;
                        }

                        if(var1 == 123) {
                           var5 = JsonToken.START_OBJECT;
                           break label50;
                        }

                        if(var1 != 125) {
                           switch(var1) {
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
                              break label56;
                           default:
                              var5 = this._handleUnexpectedValue(var1);
                              break label50;
                           }
                        }
                     }

                     this._reportUnexpectedChar(var1, "expected a value");
                  }

                  this._matchToken("true", 1);
                  var5 = JsonToken.VALUE_TRUE;
                  break label50;
               }
            }

            var5 = this.parseNumberText(var1);
         }

         this._nextToken = var5;
         return var3;
      }
   }

   private void _isNextTokenNameYes() throws IOException, JsonParseException {
      int var1;
      if(this._inputPtr < this._inputEnd - 1 && this._inputBuffer[this._inputPtr] == 58) {
         label77: {
            byte[] var3 = this._inputBuffer;
            var1 = this._inputPtr + 1;
            this._inputPtr = var1;
            byte var4 = var3[var1];
            ++this._inputPtr;
            if(var4 == 34) {
               this._tokenIncomplete = true;
               this._nextToken = JsonToken.VALUE_STRING;
               return;
            }

            if(var4 == 123) {
               this._nextToken = JsonToken.START_OBJECT;
               return;
            }

            if(var4 == 91) {
               this._nextToken = JsonToken.START_ARRAY;
               return;
            }

            int var2 = var4 & 255;
            if(var2 > 32) {
               var1 = var2;
               if(var2 != 47) {
                  break label77;
               }
            }

            --this._inputPtr;
            var1 = this._skipWS();
         }
      } else {
         var1 = this._skipColon();
      }

      if(var1 == 34) {
         this._tokenIncomplete = true;
         this._nextToken = JsonToken.VALUE_STRING;
      } else {
         if(var1 != 45) {
            label84: {
               if(var1 == 91) {
                  this._nextToken = JsonToken.START_ARRAY;
                  return;
               }

               label56: {
                  if(var1 != 93) {
                     if(var1 == 102) {
                        this._matchToken("false", 1);
                        this._nextToken = JsonToken.VALUE_FALSE;
                        return;
                     }

                     if(var1 == 110) {
                        this._matchToken("null", 1);
                        this._nextToken = JsonToken.VALUE_NULL;
                        return;
                     }

                     if(var1 == 116) {
                        break label56;
                     }

                     if(var1 == 123) {
                        this._nextToken = JsonToken.START_OBJECT;
                        return;
                     }

                     if(var1 != 125) {
                        switch(var1) {
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
                           break label84;
                        default:
                           this._nextToken = this._handleUnexpectedValue(var1);
                           return;
                        }
                     }
                  }

                  this._reportUnexpectedChar(var1, "expected a value");
               }

               this._matchToken("true", 1);
               this._nextToken = JsonToken.VALUE_TRUE;
               return;
            }
         }

         this._nextToken = this.parseNumberText(var1);
      }
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

   private JsonToken _nextTokenNotInObject(int var1) throws IOException, JsonParseException {
      JsonToken var2;
      if(var1 == 34) {
         this._tokenIncomplete = true;
         var2 = JsonToken.VALUE_STRING;
         this._currToken = var2;
         return var2;
      } else {
         if(var1 != 45) {
            label49: {
               if(var1 == 91) {
                  this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                  var2 = JsonToken.START_ARRAY;
                  this._currToken = var2;
                  return var2;
               }

               label42: {
                  if(var1 != 93) {
                     if(var1 == 102) {
                        this._matchToken("false", 1);
                        var2 = JsonToken.VALUE_FALSE;
                        this._currToken = var2;
                        return var2;
                     }

                     if(var1 == 110) {
                        this._matchToken("null", 1);
                        var2 = JsonToken.VALUE_NULL;
                        this._currToken = var2;
                        return var2;
                     }

                     if(var1 == 116) {
                        break label42;
                     }

                     if(var1 == 123) {
                        this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                        var2 = JsonToken.START_OBJECT;
                        this._currToken = var2;
                        return var2;
                     }

                     if(var1 != 125) {
                        switch(var1) {
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
                           break label49;
                        default:
                           var2 = this._handleUnexpectedValue(var1);
                           this._currToken = var2;
                           return var2;
                        }
                     }
                  }

                  this._reportUnexpectedChar(var1, "expected a value");
               }

               this._matchToken("true", 1);
               var2 = JsonToken.VALUE_TRUE;
               this._currToken = var2;
               return var2;
            }
         }

         var2 = this.parseNumberText(var1);
         this._currToken = var2;
         return var2;
      }
   }

   private JsonToken _parseFloatText(char[] var1, int var2, int var3, boolean var4, int var5) throws IOException, JsonParseException {
      int var10 = 0;
      int var6;
      int var8;
      byte[] var11;
      boolean var13;
      int var16;
      char[] var20;
      if(var3 == 46) {
         var1[var2] = (char)var3;
         ++var2;
         var6 = var3;
         byte var7 = 0;
         var3 = var2;
         var2 = var7;

         boolean var15;
         while(true) {
            if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
               boolean var17 = true;
               var16 = var6;
               var15 = var17;
               break;
            }

            var11 = this._inputBuffer;
            var6 = this._inputPtr;
            this._inputPtr = var6 + 1;
            var16 = var11[var6] & 255;
            if(var16 < 48 || var16 > 57) {
               var15 = false;
               break;
            }

            var8 = var2 + 1;
            var2 = var3;
            var20 = var1;
            if(var3 >= var1.length) {
               var20 = this._textBuffer.finishCurrentSegment();
               var2 = 0;
            }

            var20[var2] = (char)var16;
            var3 = var2 + 1;
            var6 = var16;
            var2 = var8;
            var1 = var20;
         }

         if(var2 == 0) {
            this.reportUnexpectedNumberChar(var16, "Decimal point not followed by a digit");
         }

         var8 = var2;
         var13 = var15;
         var20 = var1;
         var6 = var16;
      } else {
         var8 = 0;
         boolean var18 = false;
         var6 = var3;
         var3 = var2;
         var20 = var1;
         var13 = var18;
      }

      boolean var9;
      label108: {
         if(var6 != 101) {
            var9 = var13;
            var16 = var3;
            if(var6 != 69) {
               break label108;
            }
         }

         var1 = var20;
         var16 = var3;
         if(var3 >= var20.length) {
            var1 = this._textBuffer.finishCurrentSegment();
            var16 = 0;
         }

         var3 = var16 + 1;
         var1[var16] = (char)var6;
         if(this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
         }

         var11 = this._inputBuffer;
         var6 = this._inputPtr;
         this._inputPtr = var6 + 1;
         var16 = var11[var6] & 255;
         if(var16 == 45 || var16 == 43) {
            var6 = var3;
            var20 = var1;
            if(var3 >= var1.length) {
               var20 = this._textBuffer.finishCurrentSegment();
               var6 = 0;
            }

            var3 = var6 + 1;
            var20[var6] = (char)var16;
            if(this._inputPtr >= this._inputEnd) {
               this.loadMoreGuaranteed();
            }

            byte[] var12 = this._inputBuffer;
            var6 = this._inputPtr;
            this._inputPtr = var6 + 1;
            var16 = var12[var6] & 255;
            var1 = var20;
         }

         var6 = 0;
         var20 = var1;

         boolean var14;
         while(true) {
            int var19;
            if(var16 > 57 || var16 < 48) {
               var19 = var6;
               var6 = var3;
               var14 = var13;
               var2 = var19;
               break;
            }

            ++var6;
            var1 = var20;
            var19 = var3;
            if(var3 >= var20.length) {
               var1 = this._textBuffer.finishCurrentSegment();
               var19 = 0;
            }

            var3 = var19 + 1;
            var1[var19] = (char)var16;
            if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
               var2 = var6;
               var6 = var3;
               var14 = true;
               break;
            }

            var11 = this._inputBuffer;
            var16 = this._inputPtr;
            this._inputPtr = var16 + 1;
            var16 = var11[var16] & 255;
            var20 = var1;
         }

         if(var2 == 0) {
            this.reportUnexpectedNumberChar(var16, "Exponent indicator not followed by a digit");
         }

         var16 = var6;
         var9 = var14;
         var10 = var2;
      }

      if(!var9) {
         --this._inputPtr;
      }

      this._textBuffer.setCurrentLength(var16);
      return this.resetFloat(var4, var5, var8, var10);
   }

   private JsonToken _parserNumber2(char[] var1, int var2, boolean var3, int var4) throws IOException, JsonParseException {
      while(this._inputPtr < this._inputEnd || this.loadMore()) {
         byte[] var7 = this._inputBuffer;
         int var5 = this._inputPtr;
         this._inputPtr = var5 + 1;
         int var6 = var7[var5] & 255;
         if(var6 > 57 || var6 < 48) {
            if(var6 != 46 && var6 != 101 && var6 != 69) {
               --this._inputPtr;
               this._textBuffer.setCurrentLength(var2);
               return this.resetInt(var3, var4);
            }

            return this._parseFloatText(var1, var2, var6, var3, var4);
         }

         char[] var8 = var1;
         var5 = var2;
         if(var2 >= var1.length) {
            var8 = this._textBuffer.finishCurrentSegment();
            var5 = 0;
         }

         var8[var5] = (char)var6;
         ++var4;
         var2 = var5 + 1;
         var1 = var8;
      }

      this._textBuffer.setCurrentLength(var2);
      return this.resetInt(var3, var4);
   }

   private void _skipCComment() throws IOException, JsonParseException {
      int[] var3 = CharTypes.getInputCodeComment();

      while(this._inputPtr < this._inputEnd || this.loadMore()) {
         byte[] var4 = this._inputBuffer;
         int var1 = this._inputPtr;
         this._inputPtr = var1 + 1;
         var1 = var4[var1] & 255;
         int var2 = var3[var1];
         if(var2 != 0) {
            if(var2 != 10) {
               if(var2 != 13) {
                  if(var2 != 42) {
                     switch(var2) {
                     case 2:
                        this._skipUtf8_2(var1);
                        break;
                     case 3:
                        this._skipUtf8_3(var1);
                        break;
                     case 4:
                        this._skipUtf8_4(var1);
                        break;
                     default:
                        this._reportInvalidChar(var1);
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
               } else {
                  this._skipCR();
               }
            } else {
               this._skipLF();
            }
         }
      }

      this._reportInvalidEOF(" in a comment");
   }

   private int _skipColon() throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd) {
         this.loadMoreGuaranteed();
      }

      byte[] var2 = this._inputBuffer;
      int var1 = this._inputPtr;
      this._inputPtr = var1 + 1;
      byte var3 = var2[var1];
      if(var3 == 58) {
         if(this._inputPtr < this._inputEnd) {
            var1 = this._inputBuffer[this._inputPtr] & 255;
            if(var1 > 32 && var1 != 47) {
               ++this._inputPtr;
               return var1;
            }
         }
      } else {
         var1 = var3 & 255;

         label71:
         while(true) {
            if(var1 != 13) {
               if(var1 != 32) {
                  if(var1 != 47) {
                     switch(var1) {
                     case 9:
                        break;
                     case 10:
                        this._skipLF();
                        break;
                     default:
                        if(var1 < 32) {
                           this._throwInvalidSpace(var1);
                        }

                        if(var1 != 58) {
                           this._reportUnexpectedChar(var1, "was expecting a colon to separate field name and value");
                        }
                        break label71;
                     }
                  } else {
                     this._skipComment();
                  }
               }
            } else {
               this._skipCR();
            }

            if(this._inputPtr >= this._inputEnd) {
               this.loadMoreGuaranteed();
            }

            var2 = this._inputBuffer;
            var1 = this._inputPtr;
            this._inputPtr = var1 + 1;
            var1 = var2[var1] & 255;
         }
      }

      while(this._inputPtr < this._inputEnd || this.loadMore()) {
         var2 = this._inputBuffer;
         var1 = this._inputPtr;
         this._inputPtr = var1 + 1;
         var1 = var2[var1] & 255;
         if(var1 > 32) {
            if(var1 != 47) {
               return var1;
            }

            this._skipComment();
         } else if(var1 != 32) {
            if(var1 == 10) {
               this._skipLF();
            } else if(var1 == 13) {
               this._skipCR();
            } else if(var1 != 9) {
               this._throwInvalidSpace(var1);
            }
         }
      }

      StringBuilder var4 = new StringBuilder();
      var4.append("Unexpected end-of-input within/between ");
      var4.append(this._parsingContext.getTypeDesc());
      var4.append(" entries");
      throw this._constructError(var4.toString());
   }

   private void _skipComment() throws IOException, JsonParseException {
      if(!this.isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
         this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature \'ALLOW_COMMENTS\' not enabled for parser)");
      }

      if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
         this._reportInvalidEOF(" in a comment");
      }

      byte[] var2 = this._inputBuffer;
      int var1 = this._inputPtr;
      this._inputPtr = var1 + 1;
      var1 = var2[var1] & 255;
      if(var1 == 47) {
         this._skipCppComment();
      } else if(var1 == 42) {
         this._skipCComment();
      } else {
         this._reportUnexpectedChar(var1, "was expecting either \'*\' or \'/\' for a comment");
      }
   }

   private void _skipCppComment() throws IOException, JsonParseException {
      int[] var3 = CharTypes.getInputCodeComment();

      while(this._inputPtr < this._inputEnd || this.loadMore()) {
         byte[] var4 = this._inputBuffer;
         int var1 = this._inputPtr;
         this._inputPtr = var1 + 1;
         var1 = var4[var1] & 255;
         int var2 = var3[var1];
         if(var2 != 0) {
            if(var2 == 10) {
               this._skipLF();
               return;
            }

            if(var2 == 13) {
               this._skipCR();
               return;
            }

            if(var2 != 42) {
               switch(var2) {
               case 2:
                  this._skipUtf8_2(var1);
                  break;
               case 3:
                  this._skipUtf8_3(var1);
                  break;
               case 4:
                  this._skipUtf8_4(var1);
                  break;
               default:
                  this._reportInvalidChar(var1);
               }
            }
         }
      }

   }

   private void _skipUtf8_2(int var1) throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd) {
         this.loadMoreGuaranteed();
      }

      byte[] var2 = this._inputBuffer;
      var1 = this._inputPtr;
      this._inputPtr = var1 + 1;
      byte var3 = var2[var1];
      if((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

   }

   private void _skipUtf8_3(int var1) throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd) {
         this.loadMoreGuaranteed();
      }

      byte[] var2 = this._inputBuffer;
      var1 = this._inputPtr;
      this._inputPtr = var1 + 1;
      byte var3 = var2[var1];
      if((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

      if(this._inputPtr >= this._inputEnd) {
         this.loadMoreGuaranteed();
      }

      var2 = this._inputBuffer;
      var1 = this._inputPtr;
      this._inputPtr = var1 + 1;
      var3 = var2[var1];
      if((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

   }

   private void _skipUtf8_4(int var1) throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd) {
         this.loadMoreGuaranteed();
      }

      byte[] var2 = this._inputBuffer;
      var1 = this._inputPtr;
      this._inputPtr = var1 + 1;
      byte var3 = var2[var1];
      if((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

      if(this._inputPtr >= this._inputEnd) {
         this.loadMoreGuaranteed();
      }

      var2 = this._inputBuffer;
      var1 = this._inputPtr;
      this._inputPtr = var1 + 1;
      var3 = var2[var1];
      if((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

      if(this._inputPtr >= this._inputEnd) {
         this.loadMoreGuaranteed();
      }

      var2 = this._inputBuffer;
      var1 = this._inputPtr;
      this._inputPtr = var1 + 1;
      var3 = var2[var1];
      if((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

   }

   private int _skipWS() throws IOException, JsonParseException {
      while(this._inputPtr < this._inputEnd || this.loadMore()) {
         byte[] var2 = this._inputBuffer;
         int var1 = this._inputPtr;
         this._inputPtr = var1 + 1;
         var1 = var2[var1] & 255;
         if(var1 > 32) {
            if(var1 != 47) {
               return var1;
            }

            this._skipComment();
         } else if(var1 != 32) {
            if(var1 == 10) {
               this._skipLF();
            } else if(var1 == 13) {
               this._skipCR();
            } else if(var1 != 9) {
               this._throwInvalidSpace(var1);
            }
         }
      }

      StringBuilder var3 = new StringBuilder();
      var3.append("Unexpected end-of-input within/between ");
      var3.append(this._parsingContext.getTypeDesc());
      var3.append(" entries");
      throw this._constructError(var3.toString());
   }

   private int _skipWSOrEnd() throws IOException, JsonParseException {
      while(this._inputPtr < this._inputEnd || this.loadMore()) {
         byte[] var2 = this._inputBuffer;
         int var1 = this._inputPtr;
         this._inputPtr = var1 + 1;
         var1 = var2[var1] & 255;
         if(var1 > 32) {
            if(var1 != 47) {
               return var1;
            }

            this._skipComment();
         } else if(var1 != 32) {
            if(var1 == 10) {
               this._skipLF();
            } else if(var1 == 13) {
               this._skipCR();
            } else if(var1 != 9) {
               this._throwInvalidSpace(var1);
            }
         }
      }

      this._handleEOF();
      return -1;
   }

   private int _verifyNoLeadingZeroes() throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
         return 48;
      } else {
         int var1 = this._inputBuffer[this._inputPtr] & 255;
         if(var1 < 48) {
            return 48;
         } else if(var1 > 57) {
            return 48;
         } else {
            if(!this.isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
               this.reportInvalidNumber("Leading zeroes not allowed");
            }

            ++this._inputPtr;
            int var2 = var1;
            if(var1 == 48) {
               while(true) {
                  if(this._inputPtr >= this._inputEnd) {
                     var2 = var1;
                     if(!this.loadMore()) {
                        break;
                     }
                  }

                  var2 = this._inputBuffer[this._inputPtr] & 255;
                  if(var2 < 48) {
                     return 48;
                  }

                  if(var2 > 57) {
                     return 48;
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

   private Name addName(int[] var1, int var2, int var3) throws JsonParseException {
      int var11 = (var2 << 2) - 4 + var3;
      int var4;
      int var8;
      if(var3 < 4) {
         var4 = var2 - 1;
         var8 = var1[var4];
         var1[var4] = var8 << (4 - var3 << 3);
      } else {
         var8 = 0;
      }

      char[] var12 = this._textBuffer.emptyAndGetCurrentSegment();
      int var5 = 0;

      int var6;
      int var7;
      for(var6 = 0; var5 < var11; var6 = var7 + 1) {
         int var10 = var1[var5 >> 2] >> (3 - (var5 & 3) << 3) & 255;
         int var9 = var5 + 1;
         var5 = var9;
         char[] var13 = var12;
         var7 = var6;
         var4 = var10;
         if(var10 > 127) {
            byte var14;
            if((var10 & 224) == 192) {
               var5 = var10 & 31;
               var14 = 1;
            } else if((var10 & 240) == 224) {
               var5 = var10 & 15;
               var14 = 2;
            } else if((var10 & 248) == 240) {
               var5 = var10 & 7;
               var14 = 3;
            } else {
               this._reportInvalidInitial(var10);
               var14 = 1;
               var5 = 1;
            }

            if(var9 + var14 > var11) {
               this._reportInvalidEOF(" in field name");
            }

            var7 = var1[var9 >> 2] >> (3 - (var9 & 3) << 3);
            ++var9;
            if((var7 & 192) != 128) {
               this._reportInvalidOther(var7);
            }

            var7 = var5 << 6 | var7 & 63;
            if(var14 > 1) {
               var5 = var1[var9 >> 2] >> (3 - (var9 & 3) << 3);
               ++var9;
               if((var5 & 192) != 128) {
                  this._reportInvalidOther(var5);
               }

               var10 = var5 & 63 | var7 << 6;
               var7 = var10;
               var5 = var9;
               if(var14 > 2) {
                  var7 = var1[var9 >> 2] >> (3 - (var9 & 3) << 3);
                  var5 = var9 + 1;
                  if((var7 & 192) != 128) {
                     this._reportInvalidOther(var7 & 255);
                  }

                  var7 = var10 << 6 | var7 & 63;
               }
            } else {
               var5 = var9;
            }

            if(var14 > 2) {
               var4 = var7 - 65536;
               var13 = var12;
               if(var6 >= var12.length) {
                  var13 = this._textBuffer.expandCurrentSegment();
               }

               var13[var6] = (char)((var4 >> 10) + '\ud800');
               var4 = var4 & 1023 | '\udc00';
               var7 = var6 + 1;
            } else {
               var4 = var7;
               var7 = var6;
               var13 = var12;
            }
         }

         var12 = var13;
         if(var7 >= var13.length) {
            var12 = this._textBuffer.expandCurrentSegment();
         }

         var12[var7] = (char)var4;
      }

      String var15 = new String(var12, 0, var6);
      if(var3 < 4) {
         var1[var2 - 1] = var8;
      }

      return this._symbols.addName(var15, var1, var2);
   }

   private Name findName(int var1, int var2) throws JsonParseException {
      Name var3 = this._symbols.findName(var1);
      if(var3 != null) {
         return var3;
      } else {
         this._quadBuffer[0] = var1;
         return this.addName(this._quadBuffer, 1, var2);
      }
   }

   private Name findName(int var1, int var2, int var3) throws JsonParseException {
      Name var4 = this._symbols.findName(var1, var2);
      if(var4 != null) {
         return var4;
      } else {
         this._quadBuffer[0] = var1;
         this._quadBuffer[1] = var2;
         return this.addName(this._quadBuffer, 2, var3);
      }
   }

   private Name findName(int[] var1, int var2, int var3, int var4) throws JsonParseException {
      int[] var6 = var1;
      if(var2 >= var1.length) {
         var6 = growArrayBy(var1, var1.length);
         this._quadBuffer = var6;
      }

      int var5 = var2 + 1;
      var6[var2] = var3;
      Name var7 = this._symbols.findName(var6, var5);
      return var7 == null?this.addName(var6, var5, var4):var7;
   }

   public static int[] growArrayBy(int[] var0, int var1) {
      if(var0 == null) {
         return new int[var1];
      } else {
         int var2 = var0.length;
         int[] var3 = new int[var1 + var2];
         System.arraycopy(var0, 0, var3, 0, var2);
         return var3;
      }
   }

   private int nextByte() throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd) {
         this.loadMoreGuaranteed();
      }

      byte[] var2 = this._inputBuffer;
      int var1 = this._inputPtr;
      this._inputPtr = var1 + 1;
      return var2[var1] & 255;
   }

   private Name parseFieldName(int var1, int var2, int var3) throws IOException, JsonParseException {
      return this.parseEscapedFieldName(this._quadBuffer, 0, var1, var2, var3);
   }

   private Name parseFieldName(int var1, int var2, int var3, int var4) throws IOException, JsonParseException {
      this._quadBuffer[0] = var1;
      return this.parseEscapedFieldName(this._quadBuffer, 1, var2, var3, var4);
   }

   protected void _closeInput() throws IOException {
      if(this._inputStream != null) {
         if(this._ioContext.isResourceManaged() || this.isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
            this._inputStream.close();
         }

         this._inputStream = null;
      }

   }

   protected byte[] _decodeBase64(Base64Variant var1) throws IOException, JsonParseException {
      ByteArrayBuilder var7 = this._getByteArrayBuilder();

      while(true) {
         if(this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
         }

         byte[] var8 = this._inputBuffer;
         int var2 = this._inputPtr;
         this._inputPtr = var2 + 1;
         int var4 = var8[var2] & 255;
         if(var4 > 32) {
            int var3 = var1.decodeBase64Char(var4);
            var2 = var3;
            if(var3 < 0) {
               if(var4 == 34) {
                  return var7.toByteArray();
               }

               var3 = this._decodeBase64Escape(var1, var4, 0);
               var2 = var3;
               if(var3 < 0) {
                  continue;
               }
            }

            if(this._inputPtr >= this._inputEnd) {
               this.loadMoreGuaranteed();
            }

            var8 = this._inputBuffer;
            var3 = this._inputPtr;
            this._inputPtr = var3 + 1;
            int var5 = var8[var3] & 255;
            var4 = var1.decodeBase64Char(var5);
            var3 = var4;
            if(var4 < 0) {
               var3 = this._decodeBase64Escape(var1, var5, 1);
            }

            var5 = var2 << 6 | var3;
            if(this._inputPtr >= this._inputEnd) {
               this.loadMoreGuaranteed();
            }

            var8 = this._inputBuffer;
            var2 = this._inputPtr;
            this._inputPtr = var2 + 1;
            int var6 = var8[var2] & 255;
            var3 = var1.decodeBase64Char(var6);
            var4 = var3;
            if(var3 < 0) {
               var2 = var3;
               if(var3 != -2) {
                  if(var6 == 34 && !var1.usesPadding()) {
                     var7.append(var5 >> 4);
                     return var7.toByteArray();
                  }

                  var2 = this._decodeBase64Escape(var1, var6, 2);
               }

               var4 = var2;
               if(var2 == -2) {
                  if(this._inputPtr >= this._inputEnd) {
                     this.loadMoreGuaranteed();
                  }

                  var8 = this._inputBuffer;
                  var2 = this._inputPtr;
                  this._inputPtr = var2 + 1;
                  var2 = var8[var2] & 255;
                  if(!var1.usesPaddingChar(var2)) {
                     StringBuilder var9 = new StringBuilder();
                     var9.append("expected padding character \'");
                     var9.append(var1.getPaddingChar());
                     var9.append("\'");
                     throw this.reportInvalidBase64Char(var1, var2, 3, var9.toString());
                  }

                  var7.append(var5 >> 4);
                  continue;
               }
            }

            var5 = var5 << 6 | var4;
            if(this._inputPtr >= this._inputEnd) {
               this.loadMoreGuaranteed();
            }

            var8 = this._inputBuffer;
            var2 = this._inputPtr;
            this._inputPtr = var2 + 1;
            var6 = var8[var2] & 255;
            var3 = var1.decodeBase64Char(var6);
            var4 = var3;
            if(var3 < 0) {
               var2 = var3;
               if(var3 != -2) {
                  if(var6 == 34 && !var1.usesPadding()) {
                     var7.appendTwoBytes(var5 >> 2);
                     return var7.toByteArray();
                  }

                  var2 = this._decodeBase64Escape(var1, var6, 3);
               }

               var4 = var2;
               if(var2 == -2) {
                  var7.appendTwoBytes(var5 >> 2);
                  continue;
               }
            }

            var7.appendThreeBytes(var5 << 6 | var4);
         }
      }
   }

   protected int _decodeCharForError(int var1) throws IOException, JsonParseException {
      int var2 = var1;
      if(var1 < 0) {
         byte var4;
         label37: {
            if((var1 & 224) == 192) {
               var2 = var1 & 31;
            } else {
               if((var1 & 240) == 224) {
                  var2 = var1 & 15;
                  var4 = 2;
                  break label37;
               }

               if((var1 & 248) == 240) {
                  var2 = var1 & 7;
                  var4 = 3;
                  break label37;
               }

               this._reportInvalidInitial(var1 & 255);
               var2 = var1;
            }

            var4 = 1;
         }

         int var3 = this.nextByte();
         if((var3 & 192) != 128) {
            this._reportInvalidOther(var3 & 255);
         }

         var3 = var2 << 6 | var3 & 63;
         var2 = var3;
         if(var4 > 1) {
            var2 = this.nextByte();
            if((var2 & 192) != 128) {
               this._reportInvalidOther(var2 & 255);
            }

            var3 = var3 << 6 | var2 & 63;
            var2 = var3;
            if(var4 > 2) {
               var1 = this.nextByte();
               if((var1 & 192) != 128) {
                  this._reportInvalidOther(var1 & 255);
               }

               var2 = var3 << 6 | var1 & 63;
            }
         }
      }

      return var2;
   }

   protected char _decodeEscaped() throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
         this._reportInvalidEOF(" in character escape sequence");
      }

      byte[] var5 = this._inputBuffer;
      int var1 = this._inputPtr;
      this._inputPtr = var1 + 1;
      byte var6 = var5[var1];
      if(var6 != 34 && var6 != 47 && var6 != 92) {
         if(var6 != 98) {
            if(var6 != 102) {
               if(var6 != 110) {
                  if(var6 != 114) {
                     switch(var6) {
                     case 116:
                        return '\t';
                     case 117:
                        var1 = 0;

                        int var2;
                        for(var2 = 0; var1 < 4; ++var1) {
                           if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
                              this._reportInvalidEOF(" in character escape sequence");
                           }

                           var5 = this._inputBuffer;
                           int var3 = this._inputPtr;
                           this._inputPtr = var3 + 1;
                           byte var7 = var5[var3];
                           int var4 = CharTypes.charToHex(var7);
                           if(var4 < 0) {
                              this._reportUnexpectedChar(var7, "expected a hex-digit for character escape sequence");
                           }

                           var2 = var2 << 4 | var4;
                        }

                        return (char)var2;
                     default:
                        return this._handleUnrecognizedCharacterEscape((char)this._decodeCharForError(var6));
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
         return (char)var6;
      }
   }

   protected void _finishString() throws IOException, JsonParseException {
      int var2 = this._inputPtr;
      int var1 = var2;
      if(var2 >= this._inputEnd) {
         this.loadMoreGuaranteed();
         var1 = this._inputPtr;
      }

      var2 = 0;
      char[] var5 = this._textBuffer.emptyAndGetCurrentSegment();
      int[] var6 = sInputCodesUtf8;
      int var3 = Math.min(this._inputEnd, var5.length + var1);

      for(byte[] var7 = this._inputBuffer; var1 < var3; ++var2) {
         int var4 = var7[var1] & 255;
         if(var6[var4] != 0) {
            if(var4 == 34) {
               this._inputPtr = var1 + 1;
               this._textBuffer.setCurrentLength(var2);
               return;
            }
            break;
         }

         ++var1;
         var5[var2] = (char)var4;
      }

      this._inputPtr = var1;
      this._finishString2(var5, var2);
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
      char[] var7 = this._textBuffer.emptyAndGetCurrentSegment();
      int[] var8 = sInputCodesUtf8;
      byte[] var9 = this._inputBuffer;
      int var1 = 0;

      while(true) {
         if(this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
         }

         int var3 = var1;
         char[] var6 = var7;
         if(var1 >= var7.length) {
            var6 = this._textBuffer.finishCurrentSegment();
            var3 = 0;
         }

         var1 = this._inputEnd;
         int var5 = this._inputPtr + (var6.length - var3);
         int var2 = var3;
         int var4 = var1;
         if(var5 < var1) {
            var4 = var5;
            var2 = var3;
         }

         while(true) {
            var1 = var2;
            var7 = var6;
            if(this._inputPtr >= var4) {
               break;
            }

            var1 = this._inputPtr;
            this._inputPtr = var1 + 1;
            var5 = var9[var1] & 255;
            if(var5 == 39 || var8[var5] != 0) {
               if(var5 == 39) {
                  this._textBuffer.setCurrentLength(var2);
                  return JsonToken.VALUE_STRING;
               }

               switch(var8[var5]) {
               case 1:
                  var3 = var2;
                  var7 = var6;
                  var1 = var5;
                  if(var5 != 34) {
                     var1 = this._decodeEscaped();
                     var3 = var2;
                     var7 = var6;
                  }
                  break;
               case 2:
                  var1 = this._decodeUtf8_2(var5);
                  var3 = var2;
                  var7 = var6;
                  break;
               case 3:
                  if(this._inputEnd - this._inputPtr >= 2) {
                     var1 = this._decodeUtf8_3fast(var5);
                     var3 = var2;
                     var7 = var6;
                  } else {
                     var1 = this._decodeUtf8_3(var5);
                     var3 = var2;
                     var7 = var6;
                  }
                  break;
               case 4:
                  var4 = this._decodeUtf8_4(var5);
                  var3 = var2 + 1;
                  var6[var2] = (char)('\ud800' | var4 >> 10);
                  var7 = var6;
                  var1 = var3;
                  if(var3 >= var6.length) {
                     var7 = this._textBuffer.finishCurrentSegment();
                     var1 = 0;
                  }

                  var2 = '\udc00' | var4 & 1023;
                  var3 = var1;
                  var1 = var2;
                  break;
               default:
                  if(var5 < 32) {
                     this._throwUnquotedSpace(var5, "string value");
                  }

                  this._reportInvalidChar(var5);
                  var1 = var5;
                  var7 = var6;
                  var3 = var2;
               }

               var2 = var3;
               var6 = var7;
               if(var3 >= var7.length) {
                  var6 = this._textBuffer.finishCurrentSegment();
                  var2 = 0;
               }

               var6[var2] = (char)var1;
               var1 = var2 + 1;
               var7 = var6;
               break;
            }

            var6[var2] = (char)var5;
            ++var2;
         }
      }
   }

   protected JsonToken _handleInvalidNumberStart(int var1, boolean var2) throws IOException, JsonParseException {
      while(true) {
         int var5 = var1;
         if(var1 == 73) {
            label40: {
               if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
                  this._reportInvalidEOFInValue();
               }

               byte[] var6 = this._inputBuffer;
               var1 = this._inputPtr;
               this._inputPtr = var1 + 1;
               var1 = var6[var1];
               String var8;
               if(var1 == 78) {
                  if(var2) {
                     var8 = "-INF";
                  } else {
                     var8 = "+INF";
                  }
               } else {
                  var5 = var1;
                  if(var1 != 110) {
                     break label40;
                  }

                  if(var2) {
                     var8 = "-Infinity";
                  } else {
                     var8 = "+Infinity";
                  }
               }

               this._matchToken(var8, 3);
               if(this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                  double var3;
                  if(var2) {
                     var3 = Double.NEGATIVE_INFINITY;
                  } else {
                     var3 = Double.POSITIVE_INFINITY;
                  }

                  return this.resetAsNaN(var8, var3);
               }

               StringBuilder var7 = new StringBuilder();
               var7.append("Non-standard token \'");
               var7.append(var8);
               var7.append("\': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
               this._reportError(var7.toString());
               continue;
            }
         }

         this.reportUnexpectedNumberChar(var5, "expected digit (0-9) to follow minus sign, for valid numeric value");
         return null;
      }
   }

   protected JsonToken _handleUnexpectedValue(int var1) throws IOException, JsonParseException {
      if(var1 != 39) {
         if(var1 == 43) {
            if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
               this._reportInvalidEOFInValue();
            }

            byte[] var2 = this._inputBuffer;
            var1 = this._inputPtr;
            this._inputPtr = var1 + 1;
            return this._handleInvalidNumberStart(var2[var1] & 255, false);
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

   protected Name _handleUnusualFieldName(int var1) throws IOException, JsonParseException {
      if(var1 == 39 && this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
         return this._parseApostropheFieldName();
      } else {
         if(!this.isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            this._reportUnexpectedChar(var1, "was expecting double-quote to start field name");
         }

         int[] var8 = CharTypes.getInputCodeUtf8JsNames();
         if(var8[var1] != 0) {
            this._reportUnexpectedChar(var1, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
         }

         int[] var6 = this._quadBuffer;
         int var5 = 0;
         int var3 = 0;
         int var2 = 0;
         int var4 = var1;

         while(true) {
            int[] var7;
            if(var5 < 4) {
               var1 = var5 + 1;
               var2 = var2 << 8 | var4;
            } else {
               var7 = var6;
               if(var3 >= var6.length) {
                  var7 = growArrayBy(var6, var6.length);
                  this._quadBuffer = var7;
               }

               var7[var3] = var2;
               var2 = var4;
               ++var3;
               var1 = 1;
               var6 = var7;
            }

            if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
               this._reportInvalidEOF(" in field name");
            }

            var4 = this._inputBuffer[this._inputPtr] & 255;
            if(var8[var4] != 0) {
               if(var1 > 0) {
                  var7 = var6;
                  if(var3 >= var6.length) {
                     var7 = growArrayBy(var6, var6.length);
                     this._quadBuffer = var7;
                  }

                  var4 = var3 + 1;
                  var7[var3] = var2;
                  var3 = var4;
                  var6 = var7;
               }

               Name var9 = this._symbols.findName(var6, var3);
               Name var10 = var9;
               if(var9 == null) {
                  var10 = this.addName(var6, var3, var1);
               }

               return var10;
            }

            ++this._inputPtr;
            var5 = var1;
         }
      }
   }

   protected boolean _loadToHaveAtLeast(int var1) throws IOException {
      if(this._inputStream == null) {
         return false;
      } else {
         int var2 = this._inputEnd - this._inputPtr;
         if(var2 > 0 && this._inputPtr > 0) {
            this._currInputProcessed += (long)this._inputPtr;
            this._currInputRowStart -= this._inputPtr;
            System.arraycopy(this._inputBuffer, this._inputPtr, this._inputBuffer, 0, var2);
            this._inputEnd = var2;
         } else {
            this._inputEnd = 0;
         }

         int var3;
         for(this._inputPtr = 0; this._inputEnd < var1; this._inputEnd += var3) {
            var3 = this._inputStream.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
            if(var3 < 1) {
               this._closeInput();
               if(var3 == 0) {
                  StringBuilder var4 = new StringBuilder();
                  var4.append("InputStream.read() returned 0 characters when trying to read ");
                  var4.append(var2);
                  var4.append(" bytes");
                  throw new IOException(var4.toString());
               }

               return false;
            }
         }

         return true;
      }
   }

   protected void _matchToken(String var1, int var2) throws IOException, JsonParseException {
      int var4 = var1.length();

      int var3;
      do {
         if(this._inputPtr >= this._inputEnd && !this.loadMore() || this._inputBuffer[this._inputPtr] != var1.charAt(var2)) {
            this._reportInvalidToken(var1.substring(0, var2));
         }

         ++this._inputPtr;
         var3 = var2 + 1;
         var2 = var3;
      } while(var3 < var4);

      if(this._inputPtr < this._inputEnd || this.loadMore()) {
         var2 = this._inputBuffer[this._inputPtr] & 255;
         if(var2 >= 48 && var2 != 93) {
            if(var2 != 125) {
               if(Character.isJavaIdentifierPart((char)this._decodeCharForError(var2))) {
                  this._reportInvalidToken(var1.substring(0, var3));
               }

            }
         }
      }
   }

   protected Name _parseApostropheFieldName() throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
         this._reportInvalidEOF(": was expecting closing \'\'\' for name");
      }

      byte[] var10 = this._inputBuffer;
      int var1 = this._inputPtr;
      this._inputPtr = var1 + 1;
      int var4 = var10[var1] & 255;
      if(var4 == 39) {
         return BytesToNameCanonicalizer.getEmptyName();
      } else {
         int[] var13 = this._quadBuffer;
         int[] var12 = sInputCodesLatin1;
         int var2 = 0;
         var1 = 0;

         int var3;
         int[] var11;
         byte[] var15;
         for(var3 = 0; var4 != 39; var4 = var15[var4] & 255) {
            int var5 = var4;
            int var9 = var2;
            int var7 = var1;
            var11 = var13;
            int var8 = var3;
            if(var4 != 34) {
               var5 = var4;
               var9 = var2;
               var7 = var1;
               var11 = var13;
               var8 = var3;
               if(var12[var4] != 0) {
                  int var6;
                  if(var4 != 92) {
                     this._throwUnquotedSpace(var4, "name");
                     var6 = var4;
                  } else {
                     var6 = this._decodeEscaped();
                  }

                  var5 = var6;
                  var9 = var2;
                  var7 = var1;
                  var11 = var13;
                  var8 = var3;
                  if(var6 > 127) {
                     var5 = var2;
                     var4 = var1;
                     var11 = var13;
                     var7 = var3;
                     if(var2 >= 4) {
                        var11 = var13;
                        if(var1 >= var13.length) {
                           var11 = growArrayBy(var13, var13.length);
                           this._quadBuffer = var11;
                        }

                        var11[var1] = var3;
                        var4 = var1 + 1;
                        var5 = 0;
                        var7 = 0;
                     }

                     if(var6 < 2048) {
                        var2 = var7 << 8 | var6 >> 6 | 192;
                        var1 = var5 + 1;
                        var13 = var11;
                     } else {
                        var7 = var7 << 8 | var6 >> 12 | 224;
                        ++var5;
                        var3 = var5;
                        var1 = var4;
                        var13 = var11;
                        var2 = var7;
                        if(var5 >= 4) {
                           var13 = var11;
                           if(var4 >= var11.length) {
                              var13 = growArrayBy(var11, var11.length);
                              this._quadBuffer = var13;
                           }

                           var13[var4] = var7;
                           var1 = var4 + 1;
                           var3 = 0;
                           var2 = 0;
                        }

                        var2 = var2 << 8 | var6 >> 6 & 63 | 128;
                        ++var3;
                        var4 = var1;
                        var1 = var3;
                     }

                     var5 = var6 & 63 | 128;
                     var8 = var2;
                     var11 = var13;
                     var7 = var4;
                     var9 = var1;
                  }
               }
            }

            if(var9 < 4) {
               var2 = var9 + 1;
               var3 = var5 | var8 << 8;
               var1 = var7;
               var13 = var11;
            } else {
               var13 = var11;
               if(var7 >= var11.length) {
                  var13 = growArrayBy(var11, var11.length);
                  this._quadBuffer = var13;
               }

               var13[var7] = var8;
               var3 = var5;
               var1 = var7 + 1;
               var2 = 1;
            }

            if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
               this._reportInvalidEOF(" in field name");
            }

            var15 = this._inputBuffer;
            var4 = this._inputPtr;
            this._inputPtr = var4 + 1;
         }

         if(var2 > 0) {
            var11 = var13;
            if(var1 >= var13.length) {
               var11 = growArrayBy(var13, var13.length);
               this._quadBuffer = var11;
            }

            var4 = var1 + 1;
            var11[var1] = var3;
            var1 = var4;
            var13 = var11;
         }

         Name var14 = this._symbols.findName(var13, var1);
         Name var16 = var14;
         if(var14 == null) {
            var16 = this.addName(var13, var1, var2);
         }

         return var16;
      }
   }

   protected Name _parseFieldName(int var1) throws IOException, JsonParseException {
      if(var1 != 34) {
         return this._handleUnusualFieldName(var1);
      } else if(this._inputPtr + 9 > this._inputEnd) {
         return this.slowParseFieldName();
      } else {
         byte[] var3 = this._inputBuffer;
         int[] var4 = sInputCodesLatin1;
         var1 = this._inputPtr;
         this._inputPtr = var1 + 1;
         var1 = var3[var1] & 255;
         if(var4[var1] == 0) {
            int var2 = this._inputPtr;
            this._inputPtr = var2 + 1;
            var2 = var3[var2] & 255;
            if(var4[var2] == 0) {
               var1 = var1 << 8 | var2;
               var2 = this._inputPtr;
               this._inputPtr = var2 + 1;
               var2 = var3[var2] & 255;
               if(var4[var2] == 0) {
                  var1 = var1 << 8 | var2;
                  var2 = this._inputPtr;
                  this._inputPtr = var2 + 1;
                  var2 = var3[var2] & 255;
                  if(var4[var2] == 0) {
                     var1 = var1 << 8 | var2;
                     var2 = this._inputPtr;
                     this._inputPtr = var2 + 1;
                     var2 = var3[var2] & 255;
                     if(var4[var2] == 0) {
                        this._quad1 = var1;
                        return this.parseMediumFieldName(var2, var4);
                     } else {
                        return var2 == 34?this.findName(var1, 4):this.parseFieldName(var1, var2, 4);
                     }
                  } else {
                     return var2 == 34?this.findName(var1, 3):this.parseFieldName(var1, var2, 3);
                  }
               } else {
                  return var2 == 34?this.findName(var1, 2):this.parseFieldName(var1, var2, 2);
               }
            } else {
               return var2 == 34?this.findName(var1, 1):this.parseFieldName(var1, var2, 1);
            }
         } else {
            return var1 == 34?BytesToNameCanonicalizer.getEmptyName():this.parseFieldName(0, var1, 0);
         }
      }
   }

   protected int _readBinary(Base64Variant var1, OutputStream var2, byte[] var3) throws IOException, JsonParseException {
      int var9 = var3.length;
      int var4 = 0;
      int var6 = 0;

      int var5;
      while(true) {
         int var7;
         byte[] var12;
         do {
            if(this._inputPtr >= this._inputEnd) {
               this.loadMoreGuaranteed();
            }

            var12 = this._inputBuffer;
            var5 = this._inputPtr;
            this._inputPtr = var5 + 1;
            var7 = var12[var5] & 255;
         } while(var7 <= 32);

         var5 = var1.decodeBase64Char(var7);
         int var8 = var5;
         if(var5 < 0) {
            if(var7 == 34) {
               var5 = var6;
               break;
            }

            var5 = this._decodeBase64Escape(var1, var7, 0);
            var8 = var5;
            if(var5 < 0) {
               continue;
            }
         }

         var7 = var4;
         var5 = var6;
         if(var4 > var9 - 3) {
            var5 = var6 + var4;
            var2.write(var3, 0, var4);
            var7 = 0;
         }

         if(this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
         }

         var12 = this._inputBuffer;
         var4 = this._inputPtr;
         this._inputPtr = var4 + 1;
         int var10 = var12[var4] & 255;
         var6 = var1.decodeBase64Char(var10);
         var4 = var6;
         if(var6 < 0) {
            var4 = this._decodeBase64Escape(var1, var10, 1);
         }

         var10 = var8 << 6 | var4;
         if(this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
         }

         label99: {
            var12 = this._inputBuffer;
            var4 = this._inputPtr;
            this._inputPtr = var4 + 1;
            int var11 = var12[var4] & 255;
            var6 = var1.decodeBase64Char(var11);
            var8 = var6;
            if(var6 < 0) {
               var4 = var6;
               if(var6 != -2) {
                  if(var11 == 34 && !var1.usesPadding()) {
                     var3[var7] = (byte)(var10 >> 4);
                     var4 = var7 + 1;
                     break;
                  }

                  var4 = this._decodeBase64Escape(var1, var11, 2);
               }

               var8 = var4;
               if(var4 == -2) {
                  if(this._inputPtr >= this._inputEnd) {
                     this.loadMoreGuaranteed();
                  }

                  var12 = this._inputBuffer;
                  var4 = this._inputPtr;
                  this._inputPtr = var4 + 1;
                  var4 = var12[var4] & 255;
                  if(!var1.usesPaddingChar(var4)) {
                     StringBuilder var13 = new StringBuilder();
                     var13.append("expected padding character \'");
                     var13.append(var1.getPaddingChar());
                     var13.append("\'");
                     throw this.reportInvalidBase64Char(var1, var4, 3, var13.toString());
                  }

                  var4 = var7 + 1;
                  var3[var7] = (byte)(var10 >> 4);
                  break label99;
               }
            }

            var10 = var10 << 6 | var8;
            if(this._inputPtr >= this._inputEnd) {
               this.loadMoreGuaranteed();
            }

            var12 = this._inputBuffer;
            var4 = this._inputPtr;
            this._inputPtr = var4 + 1;
            var11 = var12[var4] & 255;
            var6 = var1.decodeBase64Char(var11);
            var8 = var6;
            if(var6 < 0) {
               var4 = var6;
               if(var6 != -2) {
                  if(var11 == 34 && !var1.usesPadding()) {
                     var6 = var10 >> 2;
                     var8 = var7 + 1;
                     var3[var7] = (byte)(var6 >> 8);
                     var4 = var8 + 1;
                     var3[var8] = (byte)var6;
                     break;
                  }

                  var4 = this._decodeBase64Escape(var1, var11, 3);
               }

               var8 = var4;
               if(var4 == -2) {
                  var6 = var10 >> 2;
                  var8 = var7 + 1;
                  var3[var7] = (byte)(var6 >> 8);
                  var4 = var8 + 1;
                  var3[var8] = (byte)var6;
                  var6 = var5;
                  continue;
               }
            }

            var6 = var10 << 6 | var8;
            var4 = var7 + 1;
            var3[var7] = (byte)(var6 >> 16);
            var7 = var4 + 1;
            var3[var4] = (byte)(var6 >> 8);
            var4 = var7 + 1;
            var3[var7] = (byte)var6;
         }

         var6 = var5;
      }

      this._tokenIncomplete = false;
      var6 = var5;
      if(var4 > 0) {
         var6 = var5 + var4;
         var2.write(var3, 0, var4);
      }

      return var6;
   }

   protected void _releaseBuffers() throws IOException {
      super._releaseBuffers();
      if(this._bufferRecyclable) {
         byte[] var1 = this._inputBuffer;
         if(var1 != null) {
            this._inputBuffer = null;
            this._ioContext.releaseReadIOBuffer(var1);
         }
      }

   }

   protected void _reportInvalidChar(int var1) throws JsonParseException {
      if(var1 < 32) {
         this._throwInvalidSpace(var1);
      }

      this._reportInvalidInitial(var1);
   }

   protected void _reportInvalidInitial(int var1) throws JsonParseException {
      StringBuilder var2 = new StringBuilder();
      var2.append("Invalid UTF-8 start byte 0x");
      var2.append(Integer.toHexString(var1));
      this._reportError(var2.toString());
   }

   protected void _reportInvalidOther(int var1) throws JsonParseException {
      StringBuilder var2 = new StringBuilder();
      var2.append("Invalid UTF-8 middle byte 0x");
      var2.append(Integer.toHexString(var1));
      this._reportError(var2.toString());
   }

   protected void _reportInvalidOther(int var1, int var2) throws JsonParseException {
      this._inputPtr = var2;
      this._reportInvalidOther(var1);
   }

   protected void _reportInvalidToken(String var1) throws IOException, JsonParseException {
      this._reportInvalidToken(var1, "\'null\', \'true\', \'false\' or NaN");
   }

   protected void _reportInvalidToken(String var1, String var2) throws IOException, JsonParseException {
      StringBuilder var7 = new StringBuilder(var1);

      while(this._inputPtr < this._inputEnd || this.loadMore()) {
         byte[] var5 = this._inputBuffer;
         int var4 = this._inputPtr;
         this._inputPtr = var4 + 1;
         char var3 = (char)this._decodeCharForError(var5[var4]);
         if(!Character.isJavaIdentifierPart(var3)) {
            break;
         }

         var7.append(var3);
      }

      StringBuilder var6 = new StringBuilder();
      var6.append("Unrecognized token \'");
      var6.append(var7.toString());
      var6.append("\': was expecting ");
      var6.append(var2);
      this._reportError(var6.toString());
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
      int[] var5 = sInputCodesUtf8;
      byte[] var6 = this._inputBuffer;

      label33:
      while(true) {
         int var3 = this._inputPtr;
         int var4 = this._inputEnd;
         int var1 = var3;
         int var2 = var4;
         if(var3 >= var4) {
            this.loadMoreGuaranteed();
            var1 = this._inputPtr;
            var2 = this._inputEnd;
         }

         while(var1 < var2) {
            var3 = var1 + 1;
            var1 = var6[var1] & 255;
            if(var5[var1] != 0) {
               this._inputPtr = var3;
               if(var1 == 34) {
                  return;
               }

               switch(var5[var1]) {
               case 1:
                  this._decodeEscaped();
                  continue label33;
               case 2:
                  this._skipUtf8_2(var1);
                  continue label33;
               case 3:
                  this._skipUtf8_3(var1);
                  continue label33;
               case 4:
                  this._skipUtf8_4(var1);
                  continue label33;
               default:
                  if(var1 < 32) {
                     this._throwUnquotedSpace(var1, "string value");
                  } else {
                     this._reportInvalidChar(var1);
                  }
                  continue label33;
               }
            }

            var1 = var3;
         }

         this._inputPtr = var1;
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
      return this._inputStream;
   }

   public String getText() throws IOException, JsonParseException {
      if(this._currToken == JsonToken.VALUE_STRING) {
         if(this._tokenIncomplete) {
            this._tokenIncomplete = false;
            this._finishString();
         }

         return this._textBuffer.contentsAsString();
      } else {
         return this._getText2(this._currToken);
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
      if(this._inputStream != null) {
         int var1 = this._inputStream.read(this._inputBuffer, 0, this._inputBuffer.length);
         if(var1 > 0) {
            this._inputPtr = 0;
            this._inputEnd = var1;
            return true;
         }

         this._closeInput();
         if(var1 == 0) {
            StringBuilder var2 = new StringBuilder();
            var2.append("InputStream.read() returned 0 characters when trying to read ");
            var2.append(this._inputBuffer.length);
            var2.append(" bytes");
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

   public boolean nextFieldName(SerializableString var1) throws IOException, JsonParseException {
      int var3 = 0;
      this._numTypesValid = 0;
      if(this._currToken == JsonToken.FIELD_NAME) {
         this._nextAfterName();
         return false;
      } else {
         if(this._tokenIncomplete) {
            this._skipString();
         }

         int var4 = this._skipWSOrEnd();
         if(var4 < 0) {
            this.close();
            this._currToken = null;
            return false;
         } else {
            this._tokenInputTotal = this._currInputProcessed + (long)this._inputPtr - 1L;
            this._tokenInputRow = this._currInputRow;
            this._tokenInputCol = this._inputPtr - this._currInputRowStart - 1;
            this._binaryValue = null;
            if(var4 == 93) {
               if(!this._parsingContext.inArray()) {
                  this._reportMismatchedEndMarker(var4, '}');
               }

               this._parsingContext = this._parsingContext.getParent();
               this._currToken = JsonToken.END_ARRAY;
               return false;
            } else if(var4 == 125) {
               if(!this._parsingContext.inObject()) {
                  this._reportMismatchedEndMarker(var4, ']');
               }

               this._parsingContext = this._parsingContext.getParent();
               this._currToken = JsonToken.END_OBJECT;
               return false;
            } else {
               int var2 = var4;
               if(this._parsingContext.expectComma()) {
                  if(var4 != 44) {
                     StringBuilder var7 = new StringBuilder();
                     var7.append("was expecting comma to separate ");
                     var7.append(this._parsingContext.getTypeDesc());
                     var7.append(" entries");
                     this._reportUnexpectedChar(var4, var7.toString());
                  }

                  var2 = this._skipWS();
               }

               if(!this._parsingContext.inObject()) {
                  this._nextTokenNotInObject(var2);
                  return false;
               } else {
                  if(var2 == 34) {
                     byte[] var8 = var1.asQuotedUTF8();
                     var4 = var8.length;
                     if(this._inputPtr + var4 < this._inputEnd) {
                        int var5 = this._inputPtr + var4;
                        if(this._inputBuffer[var5] == 34) {
                           int var6 = this._inputPtr;

                           while(true) {
                              if(var3 == var4) {
                                 this._inputPtr = var5 + 1;
                                 this._parsingContext.setCurrentName(var1.getValue());
                                 this._currToken = JsonToken.FIELD_NAME;
                                 this._isNextTokenNameYes();
                                 return true;
                              }

                              if(var8[var3] != this._inputBuffer[var6 + var3]) {
                                 break;
                              }

                              ++var3;
                           }
                        }
                     }
                  }

                  return this._isNextTokenNameMaybe(var2, var1);
               }
            }
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
            JsonToken var5;
            if(var2 == 93) {
               if(!this._parsingContext.inArray()) {
                  this._reportMismatchedEndMarker(var2, '}');
               }

               this._parsingContext = this._parsingContext.getParent();
               var5 = JsonToken.END_ARRAY;
               this._currToken = var5;
               return var5;
            } else if(var2 == 125) {
               if(!this._parsingContext.inObject()) {
                  this._reportMismatchedEndMarker(var2, ']');
               }

               this._parsingContext = this._parsingContext.getParent();
               var5 = JsonToken.END_OBJECT;
               this._currToken = var5;
               return var5;
            } else {
               int var1 = var2;
               if(this._parsingContext.expectComma()) {
                  if(var2 != 44) {
                     StringBuilder var3 = new StringBuilder();
                     var3.append("was expecting comma to separate ");
                     var3.append(this._parsingContext.getTypeDesc());
                     var3.append(" entries");
                     this._reportUnexpectedChar(var2, var3.toString());
                  }

                  var1 = this._skipWS();
               }

               if(!this._parsingContext.inObject()) {
                  return this._nextTokenNotInObject(var1);
               } else {
                  Name var4 = this._parseFieldName(var1);
                  this._parsingContext.setCurrentName(var4.getName());
                  this._currToken = JsonToken.FIELD_NAME;
                  var1 = this._skipWS();
                  if(var1 != 58) {
                     this._reportUnexpectedChar(var1, "was expecting a colon to separate field name and value");
                  }

                  var1 = this._skipWS();
                  if(var1 == 34) {
                     this._tokenIncomplete = true;
                     this._nextToken = JsonToken.VALUE_STRING;
                     return this._currToken;
                  } else {
                     label79: {
                        if(var1 != 45) {
                           label95: {
                              if(var1 == 91) {
                                 var5 = JsonToken.START_ARRAY;
                                 break label79;
                              }

                              label75: {
                                 if(var1 != 93) {
                                    if(var1 == 102) {
                                       this._matchToken("false", 1);
                                       var5 = JsonToken.VALUE_FALSE;
                                       break label79;
                                    }

                                    if(var1 == 110) {
                                       this._matchToken("null", 1);
                                       var5 = JsonToken.VALUE_NULL;
                                       break label79;
                                    }

                                    if(var1 == 116) {
                                       break label75;
                                    }

                                    if(var1 == 123) {
                                       var5 = JsonToken.START_OBJECT;
                                       break label79;
                                    }

                                    if(var1 != 125) {
                                       switch(var1) {
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
                                          break label95;
                                       default:
                                          var5 = this._handleUnexpectedValue(var1);
                                          break label79;
                                       }
                                    }
                                 }

                                 this._reportUnexpectedChar(var1, "expected a value");
                              }

                              this._matchToken("true", 1);
                              var5 = JsonToken.VALUE_TRUE;
                              break label79;
                           }
                        }

                        var5 = this.parseNumberText(var1);
                     }

                     this._nextToken = var5;
                     return this._currToken;
                  }
               }
            }
         }
      }
   }

   protected Name parseEscapedFieldName(int[] var1, int var2, int var3, int var4, int var5) throws IOException, JsonParseException {
      int[] var11 = sInputCodesLatin1;
      int var6 = var4;

      while(true) {
         int[] var10 = var1;
         int var7 = var2;
         int var9 = var3;
         var4 = var6;
         int var8 = var5;
         if(var11[var6] != 0) {
            if(var6 == 34) {
               var10 = var1;
               var4 = var2;
               if(var5 > 0) {
                  var10 = var1;
                  if(var2 >= var1.length) {
                     var10 = growArrayBy(var1, var1.length);
                     this._quadBuffer = var10;
                  }

                  var10[var2] = var3;
                  var4 = var2 + 1;
               }

               Name var13 = this._symbols.findName(var10, var4);
               Name var12 = var13;
               if(var13 == null) {
                  var12 = this.addName(var10, var4, var5);
               }

               return var12;
            }

            if(var6 != 92) {
               this._throwUnquotedSpace(var6, "name");
            } else {
               var6 = this._decodeEscaped();
            }

            var10 = var1;
            var7 = var2;
            var9 = var3;
            var4 = var6;
            var8 = var5;
            if(var6 > 127) {
               var10 = var1;
               var4 = var2;
               var8 = var3;
               var7 = var5;
               if(var5 >= 4) {
                  var10 = var1;
                  if(var2 >= var1.length) {
                     var10 = growArrayBy(var1, var1.length);
                     this._quadBuffer = var10;
                  }

                  var10[var2] = var3;
                  var4 = var2 + 1;
                  var8 = 0;
                  var7 = 0;
               }

               if(var6 < 2048) {
                  var3 = var8 << 8 | var6 >> 6 | 192;
                  var5 = var7 + 1;
                  var1 = var10;
                  var2 = var4;
                  var4 = var5;
               } else {
                  var8 = var8 << 8 | var6 >> 12 | 224;
                  ++var7;
                  var1 = var10;
                  var2 = var4;
                  var5 = var8;
                  var3 = var7;
                  if(var7 >= 4) {
                     var1 = var10;
                     if(var4 >= var10.length) {
                        var1 = growArrayBy(var10, var10.length);
                        this._quadBuffer = var1;
                     }

                     var1[var4] = var8;
                     var2 = var4 + 1;
                     var5 = 0;
                     var3 = 0;
                  }

                  var5 = var5 << 8 | var6 >> 6 & 63 | 128;
                  var4 = var3 + 1;
                  var3 = var5;
               }

               var5 = var6 & 63 | 128;
               var8 = var4;
               var4 = var5;
               var9 = var3;
               var7 = var2;
               var10 = var1;
            }
         }

         if(var8 < 4) {
            var5 = var8 + 1;
            var3 = var9 << 8 | var4;
            var1 = var10;
            var2 = var7;
         } else {
            var1 = var10;
            if(var7 >= var10.length) {
               var1 = growArrayBy(var10, var10.length);
               this._quadBuffer = var1;
            }

            var1[var7] = var9;
            var3 = var4;
            var2 = var7 + 1;
            var5 = 1;
         }

         if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(" in field name");
         }

         byte[] var14 = this._inputBuffer;
         var4 = this._inputPtr;
         this._inputPtr = var4 + 1;
         var6 = var14[var4] & 255;
      }
   }

   protected Name parseLongFieldName(int var1) throws IOException, JsonParseException {
      int[] var4 = sInputCodesLatin1;
      byte var3 = 2;
      int var2 = var1;

      for(var1 = var3; this._inputEnd - this._inputPtr >= 4; ++var1) {
         byte[] var5 = this._inputBuffer;
         int var6 = this._inputPtr;
         this._inputPtr = var6 + 1;
         var6 = var5[var6] & 255;
         if(var4[var6] != 0) {
            if(var6 == 34) {
               return this.findName(this._quadBuffer, var1, var2, 1);
            }

            return this.parseEscapedFieldName(this._quadBuffer, var1, var2, var6, 1);
         }

         var2 = var2 << 8 | var6;
         var5 = this._inputBuffer;
         var6 = this._inputPtr;
         this._inputPtr = var6 + 1;
         var6 = var5[var6] & 255;
         if(var4[var6] != 0) {
            if(var6 == 34) {
               return this.findName(this._quadBuffer, var1, var2, 2);
            }

            return this.parseEscapedFieldName(this._quadBuffer, var1, var2, var6, 2);
         }

         var2 = var2 << 8 | var6;
         var5 = this._inputBuffer;
         var6 = this._inputPtr;
         this._inputPtr = var6 + 1;
         var6 = var5[var6] & 255;
         if(var4[var6] != 0) {
            if(var6 == 34) {
               return this.findName(this._quadBuffer, var1, var2, 3);
            }

            return this.parseEscapedFieldName(this._quadBuffer, var1, var2, var6, 3);
         }

         var6 |= var2 << 8;
         var5 = this._inputBuffer;
         var2 = this._inputPtr;
         this._inputPtr = var2 + 1;
         var2 = var5[var2] & 255;
         if(var4[var2] != 0) {
            if(var2 == 34) {
               return this.findName(this._quadBuffer, var1, var6, 4);
            }

            return this.parseEscapedFieldName(this._quadBuffer, var1, var6, var2, 4);
         }

         if(var1 >= this._quadBuffer.length) {
            this._quadBuffer = growArrayBy(this._quadBuffer, var1);
         }

         this._quadBuffer[var1] = var6;
      }

      return this.parseEscapedFieldName(this._quadBuffer, var1, 0, var2, 0);
   }

   protected Name parseMediumFieldName(int var1, int[] var2) throws IOException, JsonParseException {
      byte[] var4 = this._inputBuffer;
      int var3 = this._inputPtr;
      this._inputPtr = var3 + 1;
      var3 = var4[var3] & 255;
      if(var2[var3] != 0) {
         return var3 == 34?this.findName(this._quad1, var1, 1):this.parseFieldName(this._quad1, var1, var3, 1);
      } else {
         var1 = var1 << 8 | var3;
         var4 = this._inputBuffer;
         var3 = this._inputPtr;
         this._inputPtr = var3 + 1;
         var3 = var4[var3] & 255;
         if(var2[var3] != 0) {
            return var3 == 34?this.findName(this._quad1, var1, 2):this.parseFieldName(this._quad1, var1, var3, 2);
         } else {
            var1 = var1 << 8 | var3;
            var4 = this._inputBuffer;
            var3 = this._inputPtr;
            this._inputPtr = var3 + 1;
            var3 = var4[var3] & 255;
            if(var2[var3] != 0) {
               return var3 == 34?this.findName(this._quad1, var1, 3):this.parseFieldName(this._quad1, var1, var3, 3);
            } else {
               var1 = var1 << 8 | var3;
               var4 = this._inputBuffer;
               var3 = this._inputPtr;
               this._inputPtr = var3 + 1;
               var3 = var4[var3] & 255;
               if(var2[var3] != 0) {
                  return var3 == 34?this.findName(this._quad1, var1, 4):this.parseFieldName(this._quad1, var1, var3, 4);
               } else {
                  this._quadBuffer[0] = this._quad1;
                  this._quadBuffer[1] = var1;
                  return this.parseLongFieldName(var3);
               }
            }
         }
      }
   }

   protected JsonToken parseNumberText(int var1) throws IOException, JsonParseException {
      char[] var7 = this._textBuffer.emptyAndGetCurrentSegment();
      boolean var6;
      if(var1 == 45) {
         var6 = true;
      } else {
         var6 = false;
      }

      byte var2;
      byte[] var8;
      if(var6) {
         var7[0] = 45;
         if(this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
         }

         var8 = this._inputBuffer;
         var1 = this._inputPtr;
         this._inputPtr = var1 + 1;
         var1 = var8[var1] & 255;
         if(var1 < 48 || var1 > 57) {
            return this._handleInvalidNumberStart(var1, true);
         }

         var2 = 1;
      } else {
         var2 = 0;
      }

      int var3 = var1;
      if(var1 == 48) {
         var3 = this._verifyNoLeadingZeroes();
      }

      var1 = var2 + 1;
      var7[var2] = (char)var3;
      var3 = this._inputPtr + var7.length;
      int var9 = var3;
      if(var3 > this._inputEnd) {
         var9 = this._inputEnd;
      }

      int var4;
      for(var3 = 1; this._inputPtr < var9; var3 = var4) {
         var8 = this._inputBuffer;
         var4 = this._inputPtr;
         this._inputPtr = var4 + 1;
         int var5 = var8[var4] & 255;
         if(var5 < 48 || var5 > 57) {
            if(var5 != 46 && var5 != 101 && var5 != 69) {
               --this._inputPtr;
               this._textBuffer.setCurrentLength(var1);
               return this.resetInt(var6, var3);
            } else {
               return this._parseFloatText(var7, var1, var5, var6, var3);
            }
         }

         var4 = var3 + 1;
         char[] var10 = var7;
         var3 = var1;
         if(var1 >= var7.length) {
            var10 = this._textBuffer.finishCurrentSegment();
            var3 = 0;
         }

         var10[var3] = (char)var5;
         var1 = var3 + 1;
         var7 = var10;
      }

      return this._parserNumber2(var7, var1, var6, var3);
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

   public int releaseBuffered(OutputStream var1) throws IOException {
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

   protected Name slowParseFieldName() throws IOException, JsonParseException {
      if(this._inputPtr >= this._inputEnd && !this.loadMore()) {
         this._reportInvalidEOF(": was expecting closing \'\"\' for name");
      }

      byte[] var2 = this._inputBuffer;
      int var1 = this._inputPtr;
      this._inputPtr = var1 + 1;
      var1 = var2[var1] & 255;
      return var1 == 34?BytesToNameCanonicalizer.getEmptyName():this.parseEscapedFieldName(this._quadBuffer, 0, 0, var1, 0);
   }
}
