package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;

public abstract class ParserMinimalBase extends JsonParser {

   protected static final int INT_APOSTROPHE = 39;
   protected static final int INT_ASTERISK = 42;
   protected static final int INT_BACKSLASH = 92;
   protected static final int INT_COLON = 58;
   protected static final int INT_COMMA = 44;
   protected static final int INT_CR = 13;
   protected static final int INT_LBRACKET = 91;
   protected static final int INT_LCURLY = 123;
   protected static final int INT_LF = 10;
   protected static final int INT_QUOTE = 34;
   protected static final int INT_RBRACKET = 93;
   protected static final int INT_RCURLY = 125;
   protected static final int INT_SLASH = 47;
   protected static final int INT_SPACE = 32;
   protected static final int INT_TAB = 9;
   protected static final int INT_b = 98;
   protected static final int INT_f = 102;
   protected static final int INT_n = 110;
   protected static final int INT_r = 114;
   protected static final int INT_t = 116;
   protected static final int INT_u = 117;
   protected JsonToken _currToken;
   protected JsonToken _lastClearedToken;


   protected ParserMinimalBase() {}

   protected ParserMinimalBase(int var1) {
      super(var1);
   }

   protected static final String _getCharDesc(int var0) {
      char var1 = (char)var0;
      StringBuilder var2;
      if(Character.isISOControl(var1)) {
         var2 = new StringBuilder();
         var2.append("(CTRL-CHAR, code ");
         var2.append(var0);
         var2.append(")");
         return var2.toString();
      } else if(var0 > 255) {
         var2 = new StringBuilder();
         var2.append("\'");
         var2.append(var1);
         var2.append("\' (code ");
         var2.append(var0);
         var2.append(" / 0x");
         var2.append(Integer.toHexString(var0));
         var2.append(")");
         return var2.toString();
      } else {
         var2 = new StringBuilder();
         var2.append("\'");
         var2.append(var1);
         var2.append("\' (code ");
         var2.append(var0);
         var2.append(")");
         return var2.toString();
      }
   }

   protected final JsonParseException _constructError(String var1, Throwable var2) {
      return new JsonParseException(var1, this.getCurrentLocation(), var2);
   }

   protected void _decodeBase64(String var1, ByteArrayBuilder var2, Base64Variant var3) throws IOException, JsonParseException {
      try {
         var3.decode(var1, var2);
      } catch (IllegalArgumentException var4) {
         this._reportError(var4.getMessage());
      }
   }

   protected abstract void _handleEOF() throws JsonParseException;

   protected char _handleUnrecognizedCharacterEscape(char var1) throws JsonProcessingException {
      if(this.isEnabled(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)) {
         return var1;
      } else if(var1 == 39 && this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
         return var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Unrecognized character escape ");
         var2.append(_getCharDesc(var1));
         this._reportError(var2.toString());
         return var1;
      }
   }

   @Deprecated
   protected void _reportBase64EOF() throws JsonParseException {
      throw this._constructError("Unexpected end-of-String in base64 content");
   }

   protected final void _reportError(String var1) throws JsonParseException {
      throw this._constructError(var1);
   }

   @Deprecated
   protected void _reportInvalidBase64(Base64Variant var1, char var2, int var3, String var4) throws JsonParseException {
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
         var6.append(var2);
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

      throw this._constructError(var8);
   }

   protected void _reportInvalidEOF() throws JsonParseException {
      StringBuilder var1 = new StringBuilder();
      var1.append(" in ");
      var1.append(this._currToken);
      this._reportInvalidEOF(var1.toString());
   }

   protected void _reportInvalidEOF(String var1) throws JsonParseException {
      StringBuilder var2 = new StringBuilder();
      var2.append("Unexpected end-of-input");
      var2.append(var1);
      this._reportError(var2.toString());
   }

   protected void _reportInvalidEOFInValue() throws JsonParseException {
      this._reportInvalidEOF(" in a value");
   }

   protected void _reportUnexpectedChar(int var1, String var2) throws JsonParseException {
      StringBuilder var3 = new StringBuilder();
      var3.append("Unexpected character (");
      var3.append(_getCharDesc(var1));
      var3.append(")");
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

   protected final void _throwInternal() {
      VersionUtil.throwInternal();
   }

   protected void _throwInvalidSpace(int var1) throws JsonParseException {
      char var3 = (char)var1;
      StringBuilder var2 = new StringBuilder();
      var2.append("Illegal character (");
      var2.append(_getCharDesc(var3));
      var2.append("): only regular white space (\\r, \\n, \\t) is allowed between tokens");
      this._reportError(var2.toString());
   }

   protected void _throwUnquotedSpace(int var1, String var2) throws JsonParseException {
      if(!this.isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS) || var1 >= 32) {
         char var4 = (char)var1;
         StringBuilder var3 = new StringBuilder();
         var3.append("Illegal unquoted character (");
         var3.append(_getCharDesc(var4));
         var3.append("): has to be escaped using backslash to be included in ");
         var3.append(var2);
         this._reportError(var3.toString());
      }

   }

   protected final void _wrapError(String var1, Throwable var2) throws JsonParseException {
      throw this._constructError(var1, var2);
   }

   public void clearCurrentToken() {
      if(this._currToken != null) {
         this._lastClearedToken = this._currToken;
         this._currToken = null;
      }

   }

   public abstract void close() throws IOException;

   public abstract byte[] getBinaryValue(Base64Variant var1) throws IOException, JsonParseException;

   public abstract String getCurrentName() throws IOException, JsonParseException;

   public JsonToken getCurrentToken() {
      return this._currToken;
   }

   public JsonToken getLastClearedToken() {
      return this._lastClearedToken;
   }

   public abstract JsonStreamContext getParsingContext();

   public abstract String getText() throws IOException, JsonParseException;

   public abstract char[] getTextCharacters() throws IOException, JsonParseException;

   public abstract int getTextLength() throws IOException, JsonParseException;

   public abstract int getTextOffset() throws IOException, JsonParseException;

   public boolean getValueAsBoolean(boolean var1) throws IOException, JsonParseException {
      if(this._currToken != null) {
         int var2 = null.$SwitchMap$com$fasterxml$jackson$core$JsonToken[this._currToken.ordinal()];
         boolean var3 = false;
         switch(var2) {
         case 5:
            var1 = var3;
            if(this.getIntValue() != 0) {
               var1 = true;
            }

            return var1;
         case 6:
            return true;
         case 7:
         case 8:
            return false;
         case 9:
            Object var4 = this.getEmbeddedObject();
            if(var4 instanceof Boolean) {
               return ((Boolean)var4).booleanValue();
            }
         case 10:
            if("true".equals(this.getText().trim())) {
               return true;
            }
            break;
         default:
            return var1;
         }
      }

      return var1;
   }

   public double getValueAsDouble(double var1) throws IOException, JsonParseException {
      if(this._currToken != null) {
         switch(null.$SwitchMap$com$fasterxml$jackson$core$JsonToken[this._currToken.ordinal()]) {
         case 5:
         case 11:
            return this.getDoubleValue();
         case 6:
            return 1.0D;
         case 7:
         case 8:
            return 0.0D;
         case 9:
            Object var3 = this.getEmbeddedObject();
            if(var3 instanceof Number) {
               return ((Number)var3).doubleValue();
            }
            break;
         case 10:
            return NumberInput.parseAsDouble(this.getText(), var1);
         default:
            return var1;
         }
      }

      return var1;
   }

   public int getValueAsInt(int var1) throws IOException, JsonParseException {
      if(this._currToken != null) {
         switch(null.$SwitchMap$com$fasterxml$jackson$core$JsonToken[this._currToken.ordinal()]) {
         case 5:
         case 11:
            return this.getIntValue();
         case 6:
            return 1;
         case 7:
         case 8:
            return 0;
         case 9:
            Object var2 = this.getEmbeddedObject();
            if(var2 instanceof Number) {
               return ((Number)var2).intValue();
            }
            break;
         case 10:
            return NumberInput.parseAsInt(this.getText(), var1);
         default:
            return var1;
         }
      }

      return var1;
   }

   public long getValueAsLong(long var1) throws IOException, JsonParseException {
      if(this._currToken != null) {
         switch(null.$SwitchMap$com$fasterxml$jackson$core$JsonToken[this._currToken.ordinal()]) {
         case 5:
         case 11:
            return this.getLongValue();
         case 6:
            return 1L;
         case 7:
         case 8:
            return 0L;
         case 9:
            Object var3 = this.getEmbeddedObject();
            if(var3 instanceof Number) {
               return ((Number)var3).longValue();
            }
            break;
         case 10:
            return NumberInput.parseAsLong(this.getText(), var1);
         default:
            return var1;
         }
      }

      return var1;
   }

   public String getValueAsString(String var1) throws IOException, JsonParseException {
      return this._currToken != JsonToken.VALUE_STRING && (this._currToken == null || this._currToken == JsonToken.VALUE_NULL || !this._currToken.isScalarValue())?var1:this.getText();
   }

   public boolean hasCurrentToken() {
      return this._currToken != null;
   }

   public abstract boolean hasTextCharacters();

   public abstract boolean isClosed();

   public abstract JsonToken nextToken() throws IOException, JsonParseException;

   public JsonToken nextValue() throws IOException, JsonParseException {
      JsonToken var2 = this.nextToken();
      JsonToken var1 = var2;
      if(var2 == JsonToken.FIELD_NAME) {
         var1 = this.nextToken();
      }

      return var1;
   }

   public abstract void overrideCurrentName(String var1);

   public JsonParser skipChildren() throws IOException, JsonParseException {
      if(this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
         return this;
      } else {
         int var1 = 1;

         while(true) {
            JsonToken var3 = this.nextToken();
            if(var3 == null) {
               this._handleEOF();
               return this;
            }

            switch(null.$SwitchMap$com$fasterxml$jackson$core$JsonToken[var3.ordinal()]) {
            case 1:
            case 2:
               ++var1;
               break;
            case 3:
            case 4:
               int var2 = var1 - 1;
               var1 = var2;
               if(var2 == 0) {
                  return this;
               }
            }
         }
      }
   }

   public Version version() {
      return VersionUtil.versionFor(this.getClass());
   }
}
