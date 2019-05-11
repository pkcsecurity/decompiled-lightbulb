package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserMinimalBase;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class ParserBase extends ParserMinimalBase {

   static final BigDecimal BD_MAX_INT = new BigDecimal(BI_MAX_INT);
   static final BigDecimal BD_MAX_LONG = new BigDecimal(BI_MAX_LONG);
   static final BigDecimal BD_MIN_INT = new BigDecimal(BI_MIN_INT);
   static final BigDecimal BD_MIN_LONG = new BigDecimal(BI_MIN_LONG);
   static final BigInteger BI_MAX_INT = BigInteger.valueOf(2147483647L);
   static final BigInteger BI_MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
   static final BigInteger BI_MIN_INT = BigInteger.valueOf(-2147483648L);
   static final BigInteger BI_MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
   protected static final char CHAR_NULL = '\u0000';
   protected static final int INT_0 = 48;
   protected static final int INT_1 = 49;
   protected static final int INT_2 = 50;
   protected static final int INT_3 = 51;
   protected static final int INT_4 = 52;
   protected static final int INT_5 = 53;
   protected static final int INT_6 = 54;
   protected static final int INT_7 = 55;
   protected static final int INT_8 = 56;
   protected static final int INT_9 = 57;
   protected static final int INT_DECIMAL_POINT = 46;
   protected static final int INT_E = 69;
   protected static final int INT_MINUS = 45;
   protected static final int INT_PLUS = 43;
   protected static final int INT_e = 101;
   static final double MAX_INT_D = 2.147483647E9D;
   static final long MAX_INT_L = 2147483647L;
   static final double MAX_LONG_D = 9.223372036854776E18D;
   static final double MIN_INT_D = -2.147483648E9D;
   static final long MIN_INT_L = -2147483648L;
   static final double MIN_LONG_D = -9.223372036854776E18D;
   protected static final int NR_BIGDECIMAL = 16;
   protected static final int NR_BIGINT = 4;
   protected static final int NR_DOUBLE = 8;
   protected static final int NR_INT = 1;
   protected static final int NR_LONG = 2;
   protected static final int NR_UNKNOWN = 0;
   protected byte[] _binaryValue;
   protected ByteArrayBuilder _byteArrayBuilder = null;
   protected boolean _closed;
   protected long _currInputProcessed = 0L;
   protected int _currInputRow = 1;
   protected int _currInputRowStart = 0;
   protected int _expLength;
   protected int _fractLength;
   protected int _inputEnd = 0;
   protected int _inputPtr = 0;
   protected int _intLength;
   protected final IOContext _ioContext;
   protected boolean _nameCopied = false;
   protected char[] _nameCopyBuffer = null;
   protected JsonToken _nextToken;
   protected int _numTypesValid = 0;
   protected BigDecimal _numberBigDecimal;
   protected BigInteger _numberBigInt;
   protected double _numberDouble;
   protected int _numberInt;
   protected long _numberLong;
   protected boolean _numberNegative;
   protected JsonReadContext _parsingContext;
   protected final TextBuffer _textBuffer;
   protected int _tokenInputCol = 0;
   protected int _tokenInputRow = 1;
   protected long _tokenInputTotal = 0L;


   protected ParserBase(IOContext var1, int var2) {
      this._features = var2;
      this._ioContext = var1;
      this._textBuffer = var1.constructTextBuffer();
      this._parsingContext = JsonReadContext.createRootContext();
   }

   private void _parseSlowFloatValue(int param1) throws IOException, JsonParseException {
      // $FF: Couldn't be decompiled
   }

   private void _parseSlowIntValue(int var1, char[] var2, int var3, int var4) throws IOException, JsonParseException {
      String var5 = this._textBuffer.contentsAsString();

      try {
         if(NumberInput.inLongRange(var2, var3, var4, this._numberNegative)) {
            this._numberLong = Long.parseLong(var5);
            this._numTypesValid = 2;
         } else {
            this._numberBigInt = new BigInteger(var5);
            this._numTypesValid = 4;
         }
      } catch (NumberFormatException var7) {
         StringBuilder var6 = new StringBuilder();
         var6.append("Malformed numeric value \'");
         var6.append(var5);
         var6.append("\'");
         this._wrapError(var6.toString(), var7);
      }
   }

   public abstract void _closeInput() throws IOException;

   protected final int _decodeBase64Escape(Base64Variant var1, char var2, int var3) throws IOException, JsonParseException {
      if(var2 != 92) {
         throw this.reportInvalidBase64Char(var1, var2, var3);
      } else {
         char var4 = this._decodeEscaped();
         if(var4 <= 32 && var3 == 0) {
            return -1;
         } else {
            int var5 = var1.decodeBase64Char(var4);
            if(var5 < 0) {
               throw this.reportInvalidBase64Char(var1, var4, var3);
            } else {
               return var5;
            }
         }
      }
   }

   protected final int _decodeBase64Escape(Base64Variant var1, int var2, int var3) throws IOException, JsonParseException {
      if(var2 != 92) {
         throw this.reportInvalidBase64Char(var1, var2, var3);
      } else {
         char var5 = this._decodeEscaped();
         if(var5 <= 32 && var3 == 0) {
            return -1;
         } else {
            int var4 = var1.decodeBase64Char((int)var5);
            if(var4 < 0) {
               throw this.reportInvalidBase64Char(var1, var5, var3);
            } else {
               return var4;
            }
         }
      }
   }

   public char _decodeEscaped() throws IOException, JsonParseException {
      throw new UnsupportedOperationException();
   }

   public abstract void _finishString() throws IOException, JsonParseException;

   public ByteArrayBuilder _getByteArrayBuilder() {
      if(this._byteArrayBuilder == null) {
         this._byteArrayBuilder = new ByteArrayBuilder();
      } else {
         this._byteArrayBuilder.reset();
      }

      return this._byteArrayBuilder;
   }

   protected void _handleEOF() throws JsonParseException {
      if(!this._parsingContext.inRoot()) {
         StringBuilder var1 = new StringBuilder();
         var1.append(": expected close marker for ");
         var1.append(this._parsingContext.getTypeDesc());
         var1.append(" (from ");
         var1.append(this._parsingContext.getStartLocation(this._ioContext.getSourceReference()));
         var1.append(")");
         this._reportInvalidEOF(var1.toString());
      }

   }

   protected void _parseNumericValue(int var1) throws IOException, JsonParseException {
      if(this._currToken == JsonToken.VALUE_NUMBER_INT) {
         char[] var10 = this._textBuffer.getTextBuffer();
         int var3 = this._textBuffer.getTextOffset();
         int var4 = this._intLength;
         int var2 = var3;
         if(this._numberNegative) {
            var2 = var3 + 1;
         }

         if(var4 <= 9) {
            var2 = NumberInput.parseInt(var10, var2, var4);
            var1 = var2;
            if(this._numberNegative) {
               var1 = -var2;
            }

            this._numberInt = var1;
            this._numTypesValid = 1;
         } else if(var4 <= 18) {
            long var7 = NumberInput.parseLong(var10, var2, var4);
            long var5 = var7;
            if(this._numberNegative) {
               var5 = -var7;
            }

            if(var4 == 10) {
               if(this._numberNegative) {
                  if(var5 >= -2147483648L) {
                     this._numberInt = (int)var5;
                     this._numTypesValid = 1;
                     return;
                  }
               } else if(var5 <= 2147483647L) {
                  this._numberInt = (int)var5;
                  this._numTypesValid = 1;
                  return;
               }
            }

            this._numberLong = var5;
            this._numTypesValid = 2;
         } else {
            this._parseSlowIntValue(var1, var10, var2, var4);
         }
      } else if(this._currToken == JsonToken.VALUE_NUMBER_FLOAT) {
         this._parseSlowFloatValue(var1);
      } else {
         StringBuilder var9 = new StringBuilder();
         var9.append("Current token (");
         var9.append(this._currToken);
         var9.append(") not numeric, can not use numeric value accessors");
         this._reportError(var9.toString());
      }
   }

   public void _releaseBuffers() throws IOException {
      this._textBuffer.releaseBuffers();
      char[] var1 = this._nameCopyBuffer;
      if(var1 != null) {
         this._nameCopyBuffer = null;
         this._ioContext.releaseNameCopyBuffer(var1);
      }

   }

   protected void _reportMismatchedEndMarker(int var1, char var2) throws JsonParseException {
      StringBuilder var3 = new StringBuilder();
      var3.append("");
      var3.append(this._parsingContext.getStartLocation(this._ioContext.getSourceReference()));
      String var5 = var3.toString();
      StringBuilder var4 = new StringBuilder();
      var4.append("Unexpected close marker \'");
      var4.append((char)var1);
      var4.append("\': expected \'");
      var4.append(var2);
      var4.append("\' (for ");
      var4.append(this._parsingContext.getTypeDesc());
      var4.append(" starting at ");
      var4.append(var5);
      var4.append(")");
      this._reportError(var4.toString());
   }

   public void close() throws IOException {
      if(!this._closed) {
         this._closed = true;

         try {
            this._closeInput();
         } finally {
            this._releaseBuffers();
         }

      }
   }

   protected void convertNumberToBigDecimal() throws IOException, JsonParseException {
      if((this._numTypesValid & 8) != 0) {
         this._numberBigDecimal = new BigDecimal(this.getText());
      } else if((this._numTypesValid & 4) != 0) {
         this._numberBigDecimal = new BigDecimal(this._numberBigInt);
      } else if((this._numTypesValid & 2) != 0) {
         this._numberBigDecimal = BigDecimal.valueOf(this._numberLong);
      } else if((this._numTypesValid & 1) != 0) {
         this._numberBigDecimal = BigDecimal.valueOf((long)this._numberInt);
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 16;
   }

   protected void convertNumberToBigInteger() throws IOException, JsonParseException {
      if((this._numTypesValid & 16) != 0) {
         this._numberBigInt = this._numberBigDecimal.toBigInteger();
      } else if((this._numTypesValid & 2) != 0) {
         this._numberBigInt = BigInteger.valueOf(this._numberLong);
      } else if((this._numTypesValid & 1) != 0) {
         this._numberBigInt = BigInteger.valueOf((long)this._numberInt);
      } else if((this._numTypesValid & 8) != 0) {
         this._numberBigInt = BigDecimal.valueOf(this._numberDouble).toBigInteger();
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 4;
   }

   protected void convertNumberToDouble() throws IOException, JsonParseException {
      if((this._numTypesValid & 16) != 0) {
         this._numberDouble = this._numberBigDecimal.doubleValue();
      } else if((this._numTypesValid & 4) != 0) {
         this._numberDouble = this._numberBigInt.doubleValue();
      } else if((this._numTypesValid & 2) != 0) {
         this._numberDouble = (double)this._numberLong;
      } else if((this._numTypesValid & 1) != 0) {
         this._numberDouble = (double)this._numberInt;
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 8;
   }

   protected void convertNumberToInt() throws IOException, JsonParseException {
      if((this._numTypesValid & 2) != 0) {
         int var1 = (int)this._numberLong;
         if((long)var1 != this._numberLong) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Numeric value (");
            var2.append(this.getText());
            var2.append(") out of range of int");
            this._reportError(var2.toString());
         }

         this._numberInt = var1;
      } else if((this._numTypesValid & 4) != 0) {
         if(BI_MIN_INT.compareTo(this._numberBigInt) > 0 || BI_MAX_INT.compareTo(this._numberBigInt) < 0) {
            this.reportOverflowInt();
         }

         this._numberInt = this._numberBigInt.intValue();
      } else if((this._numTypesValid & 8) != 0) {
         if(this._numberDouble < -2.147483648E9D || this._numberDouble > 2.147483647E9D) {
            this.reportOverflowInt();
         }

         this._numberInt = (int)this._numberDouble;
      } else if((this._numTypesValid & 16) != 0) {
         if(BD_MIN_INT.compareTo(this._numberBigDecimal) > 0 || BD_MAX_INT.compareTo(this._numberBigDecimal) < 0) {
            this.reportOverflowInt();
         }

         this._numberInt = this._numberBigDecimal.intValue();
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 1;
   }

   protected void convertNumberToLong() throws IOException, JsonParseException {
      if((this._numTypesValid & 1) != 0) {
         this._numberLong = (long)this._numberInt;
      } else if((this._numTypesValid & 4) != 0) {
         if(BI_MIN_LONG.compareTo(this._numberBigInt) > 0 || BI_MAX_LONG.compareTo(this._numberBigInt) < 0) {
            this.reportOverflowLong();
         }

         this._numberLong = this._numberBigInt.longValue();
      } else if((this._numTypesValid & 8) != 0) {
         if(this._numberDouble < -9.223372036854776E18D || this._numberDouble > 9.223372036854776E18D) {
            this.reportOverflowLong();
         }

         this._numberLong = (long)this._numberDouble;
      } else if((this._numTypesValid & 16) != 0) {
         if(BD_MIN_LONG.compareTo(this._numberBigDecimal) > 0 || BD_MAX_LONG.compareTo(this._numberBigDecimal) < 0) {
            this.reportOverflowLong();
         }

         this._numberLong = this._numberBigDecimal.longValue();
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 2;
   }

   public BigInteger getBigIntegerValue() throws IOException, JsonParseException {
      if((this._numTypesValid & 4) == 0) {
         if(this._numTypesValid == 0) {
            this._parseNumericValue(4);
         }

         if((this._numTypesValid & 4) == 0) {
            this.convertNumberToBigInteger();
         }
      }

      return this._numberBigInt;
   }

   public JsonLocation getCurrentLocation() {
      int var1 = this._inputPtr;
      int var2 = this._currInputRowStart;
      return new JsonLocation(this._ioContext.getSourceReference(), this._currInputProcessed + (long)this._inputPtr - 1L, this._currInputRow, var1 - var2 + 1);
   }

   public String getCurrentName() throws IOException, JsonParseException {
      return this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY?this._parsingContext.getCurrentName():this._parsingContext.getParent().getCurrentName();
   }

   public BigDecimal getDecimalValue() throws IOException, JsonParseException {
      if((this._numTypesValid & 16) == 0) {
         if(this._numTypesValid == 0) {
            this._parseNumericValue(16);
         }

         if((this._numTypesValid & 16) == 0) {
            this.convertNumberToBigDecimal();
         }
      }

      return this._numberBigDecimal;
   }

   public double getDoubleValue() throws IOException, JsonParseException {
      if((this._numTypesValid & 8) == 0) {
         if(this._numTypesValid == 0) {
            this._parseNumericValue(8);
         }

         if((this._numTypesValid & 8) == 0) {
            this.convertNumberToDouble();
         }
      }

      return this._numberDouble;
   }

   public Object getEmbeddedObject() throws IOException, JsonParseException {
      return null;
   }

   public float getFloatValue() throws IOException, JsonParseException {
      return (float)this.getDoubleValue();
   }

   public int getIntValue() throws IOException, JsonParseException {
      if((this._numTypesValid & 1) == 0) {
         if(this._numTypesValid == 0) {
            this._parseNumericValue(1);
         }

         if((this._numTypesValid & 1) == 0) {
            this.convertNumberToInt();
         }
      }

      return this._numberInt;
   }

   public long getLongValue() throws IOException, JsonParseException {
      if((this._numTypesValid & 2) == 0) {
         if(this._numTypesValid == 0) {
            this._parseNumericValue(2);
         }

         if((this._numTypesValid & 2) == 0) {
            this.convertNumberToLong();
         }
      }

      return this._numberLong;
   }

   public JsonParser.NumberType getNumberType() throws IOException, JsonParseException {
      if(this._numTypesValid == 0) {
         this._parseNumericValue(0);
      }

      return this._currToken == JsonToken.VALUE_NUMBER_INT?((this._numTypesValid & 1) != 0?JsonParser.NumberType.INT:((this._numTypesValid & 2) != 0?JsonParser.NumberType.LONG:JsonParser.NumberType.BIG_INTEGER)):((this._numTypesValid & 16) != 0?JsonParser.NumberType.BIG_DECIMAL:JsonParser.NumberType.DOUBLE);
   }

   public Number getNumberValue() throws IOException, JsonParseException {
      if(this._numTypesValid == 0) {
         this._parseNumericValue(0);
      }

      if(this._currToken == JsonToken.VALUE_NUMBER_INT) {
         return (Number)((this._numTypesValid & 1) != 0?Integer.valueOf(this._numberInt):((this._numTypesValid & 2) != 0?Long.valueOf(this._numberLong):((this._numTypesValid & 4) != 0?this._numberBigInt:this._numberBigDecimal)));
      } else if((this._numTypesValid & 16) != 0) {
         return this._numberBigDecimal;
      } else {
         if((this._numTypesValid & 8) == 0) {
            this._throwInternal();
         }

         return Double.valueOf(this._numberDouble);
      }
   }

   public JsonReadContext getParsingContext() {
      return this._parsingContext;
   }

   public long getTokenCharacterOffset() {
      return this._tokenInputTotal;
   }

   public int getTokenColumnNr() {
      int var1 = this._tokenInputCol;
      return var1 < 0?var1:var1 + 1;
   }

   public int getTokenLineNr() {
      return this._tokenInputRow;
   }

   public JsonLocation getTokenLocation() {
      return new JsonLocation(this._ioContext.getSourceReference(), this.getTokenCharacterOffset(), this.getTokenLineNr(), this.getTokenColumnNr());
   }

   public boolean hasTextCharacters() {
      return this._currToken == JsonToken.VALUE_STRING?true:(this._currToken == JsonToken.FIELD_NAME?this._nameCopied:false);
   }

   public boolean isClosed() {
      return this._closed;
   }

   public abstract boolean loadMore() throws IOException;

   protected final void loadMoreGuaranteed() throws IOException {
      if(!this.loadMore()) {
         this._reportInvalidEOF();
      }

   }

   public void overrideCurrentName(String var1) {
      JsonReadContext var2;
      label11: {
         JsonReadContext var3 = this._parsingContext;
         if(this._currToken != JsonToken.START_OBJECT) {
            var2 = var3;
            if(this._currToken != JsonToken.START_ARRAY) {
               break label11;
            }
         }

         var2 = var3.getParent();
      }

      var2.setCurrentName(var1);
   }

   protected IllegalArgumentException reportInvalidBase64Char(Base64Variant var1, int var2, int var3) throws IllegalArgumentException {
      return this.reportInvalidBase64Char(var1, var2, var3, (String)null);
   }

   protected IllegalArgumentException reportInvalidBase64Char(Base64Variant var1, int var2, int var3, String var4) throws IllegalArgumentException {
      StringBuilder var5;
      StringBuilder var6;
      String var7;
      if(var2 <= 32) {
         var6 = new StringBuilder();
         var6.append("Illegal white space character (code 0x");
         var6.append(Integer.toHexString(var2));
         var6.append(") as character #");
         var6.append(var3 + 1);
         var6.append(" of 4-char base64 unit: can only used between units");
         var7 = var6.toString();
      } else if(var1.usesPaddingChar(var2)) {
         var5 = new StringBuilder();
         var5.append("Unexpected padding character (\'");
         var5.append(var1.getPaddingChar());
         var5.append("\') as character #");
         var5.append(var3 + 1);
         var5.append(" of 4-char base64 unit: padding only legal as 3rd or 4th character");
         var7 = var5.toString();
      } else if(Character.isDefined(var2) && !Character.isISOControl(var2)) {
         var6 = new StringBuilder();
         var6.append("Illegal character \'");
         var6.append((char)var2);
         var6.append("\' (code 0x");
         var6.append(Integer.toHexString(var2));
         var6.append(") in base64 content");
         var7 = var6.toString();
      } else {
         var6 = new StringBuilder();
         var6.append("Illegal character (code 0x");
         var6.append(Integer.toHexString(var2));
         var6.append(") in base64 content");
         var7 = var6.toString();
      }

      String var8 = var7;
      if(var4 != null) {
         var5 = new StringBuilder();
         var5.append(var7);
         var5.append(": ");
         var5.append(var4);
         var8 = var5.toString();
      }

      return new IllegalArgumentException(var8);
   }

   protected void reportInvalidNumber(String var1) throws JsonParseException {
      StringBuilder var2 = new StringBuilder();
      var2.append("Invalid numeric value: ");
      var2.append(var1);
      this._reportError(var2.toString());
   }

   protected void reportOverflowInt() throws IOException, JsonParseException {
      StringBuilder var1 = new StringBuilder();
      var1.append("Numeric value (");
      var1.append(this.getText());
      var1.append(") out of range of int (");
      var1.append(Integer.MIN_VALUE);
      var1.append(" - ");
      var1.append(Integer.MAX_VALUE);
      var1.append(")");
      this._reportError(var1.toString());
   }

   protected void reportOverflowLong() throws IOException, JsonParseException {
      StringBuilder var1 = new StringBuilder();
      var1.append("Numeric value (");
      var1.append(this.getText());
      var1.append(") out of range of long (");
      var1.append(Long.MIN_VALUE);
      var1.append(" - ");
      var1.append(Long.MAX_VALUE);
      var1.append(")");
      this._reportError(var1.toString());
   }

   protected void reportUnexpectedNumberChar(int var1, String var2) throws JsonParseException {
      StringBuilder var3 = new StringBuilder();
      var3.append("Unexpected character (");
      var3.append(_getCharDesc(var1));
      var3.append(") in numeric value");
      String var4 = var3.toString();
      String var5 = var4;
      if(var2 != null) {
         var3 = new StringBuilder();
         var3.append(var4);
         var3.append(": ");
         var3.append(var2);
         var5 = var3.toString();
      }

      this._reportError(var5);
   }

   protected final JsonToken reset(boolean var1, int var2, int var3, int var4) {
      return var3 < 1 && var4 < 1?this.resetInt(var1, var2):this.resetFloat(var1, var2, var3, var4);
   }

   protected final JsonToken resetAsNaN(String var1, double var2) {
      this._textBuffer.resetWithString(var1);
      this._numberDouble = var2;
      this._numTypesValid = 8;
      return JsonToken.VALUE_NUMBER_FLOAT;
   }

   protected final JsonToken resetFloat(boolean var1, int var2, int var3, int var4) {
      this._numberNegative = var1;
      this._intLength = var2;
      this._fractLength = var3;
      this._expLength = var4;
      this._numTypesValid = 0;
      return JsonToken.VALUE_NUMBER_FLOAT;
   }

   protected final JsonToken resetInt(boolean var1, int var2) {
      this._numberNegative = var1;
      this._intLength = var2;
      this._fractLength = 0;
      this._expLength = 0;
      this._numTypesValid = 0;
      return JsonToken.VALUE_NUMBER_INT;
   }

   public Version version() {
      return PackageVersion.VERSION;
   }
}
