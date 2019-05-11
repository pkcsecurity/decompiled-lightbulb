package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class JsonGenerator implements Versioned, Closeable, Flushable {

   protected PrettyPrinter _cfgPrettyPrinter;


   public boolean canUseSchema(FormatSchema var1) {
      return false;
   }

   public abstract void close() throws IOException;

   public final JsonGenerator configure(JsonGenerator.Feature var1, boolean var2) {
      if(var2) {
         this.enable(var1);
         return this;
      } else {
         this.disable(var1);
         return this;
      }
   }

   public abstract void copyCurrentEvent(JsonParser var1) throws IOException, JsonProcessingException;

   public abstract void copyCurrentStructure(JsonParser var1) throws IOException, JsonProcessingException;

   public abstract JsonGenerator disable(JsonGenerator.Feature var1);

   public abstract JsonGenerator enable(JsonGenerator.Feature var1);

   public abstract void flush() throws IOException;

   public CharacterEscapes getCharacterEscapes() {
      return null;
   }

   public abstract ObjectCodec getCodec();

   public int getHighestEscapedChar() {
      return 0;
   }

   public abstract JsonStreamContext getOutputContext();

   public Object getOutputTarget() {
      return null;
   }

   public PrettyPrinter getPrettyPrinter() {
      return this._cfgPrettyPrinter;
   }

   public FormatSchema getSchema() {
      return null;
   }

   public abstract boolean isClosed();

   public abstract boolean isEnabled(JsonGenerator.Feature var1);

   public JsonGenerator setCharacterEscapes(CharacterEscapes var1) {
      return this;
   }

   public abstract JsonGenerator setCodec(ObjectCodec var1);

   public JsonGenerator setHighestNonEscapedChar(int var1) {
      return this;
   }

   public JsonGenerator setPrettyPrinter(PrettyPrinter var1) {
      this._cfgPrettyPrinter = var1;
      return this;
   }

   public JsonGenerator setRootValueSeparator(SerializableString var1) {
      throw new UnsupportedOperationException();
   }

   public void setSchema(FormatSchema var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("Generator of type ");
      var2.append(this.getClass().getName());
      var2.append(" does not support schema of type \'");
      var2.append(var1.getSchemaType());
      var2.append("\'");
      throw new UnsupportedOperationException(var2.toString());
   }

   public abstract JsonGenerator useDefaultPrettyPrinter();

   public abstract Version version();

   public final void writeArrayFieldStart(String var1) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeStartArray();
   }

   public abstract int writeBinary(Base64Variant var1, InputStream var2, int var3) throws IOException, JsonGenerationException;

   public int writeBinary(InputStream var1, int var2) throws IOException, JsonGenerationException {
      return this.writeBinary(Base64Variants.getDefaultVariant(), var1, var2);
   }

   public abstract void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException;

   public void writeBinary(byte[] var1) throws IOException, JsonGenerationException {
      this.writeBinary(Base64Variants.getDefaultVariant(), var1, 0, var1.length);
   }

   public void writeBinary(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this.writeBinary(Base64Variants.getDefaultVariant(), var1, var2, var3);
   }

   public final void writeBinaryField(String var1, byte[] var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeBinary(var2);
   }

   public abstract void writeBoolean(boolean var1) throws IOException, JsonGenerationException;

   public final void writeBooleanField(String var1, boolean var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeBoolean(var2);
   }

   public abstract void writeEndArray() throws IOException, JsonGenerationException;

   public abstract void writeEndObject() throws IOException, JsonGenerationException;

   public abstract void writeFieldName(SerializableString var1) throws IOException, JsonGenerationException;

   public abstract void writeFieldName(String var1) throws IOException, JsonGenerationException;

   public abstract void writeNull() throws IOException, JsonGenerationException;

   public final void writeNullField(String var1) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeNull();
   }

   public abstract void writeNumber(double var1) throws IOException, JsonGenerationException;

   public abstract void writeNumber(float var1) throws IOException, JsonGenerationException;

   public abstract void writeNumber(int var1) throws IOException, JsonGenerationException;

   public abstract void writeNumber(long var1) throws IOException, JsonGenerationException;

   public abstract void writeNumber(String var1) throws IOException, JsonGenerationException, UnsupportedOperationException;

   public abstract void writeNumber(BigDecimal var1) throws IOException, JsonGenerationException;

   public abstract void writeNumber(BigInteger var1) throws IOException, JsonGenerationException;

   public void writeNumber(short var1) throws IOException, JsonGenerationException {
      this.writeNumber((int)var1);
   }

   public final void writeNumberField(String var1, double var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, float var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, int var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, long var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, BigDecimal var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public abstract void writeObject(Object var1) throws IOException, JsonProcessingException;

   public final void writeObjectField(String var1, Object var2) throws IOException, JsonProcessingException {
      this.writeFieldName(var1);
      this.writeObject(var2);
   }

   public final void writeObjectFieldStart(String var1) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeStartObject();
   }

   public abstract void writeRaw(char var1) throws IOException, JsonGenerationException;

   public void writeRaw(SerializableString var1) throws IOException, JsonGenerationException {
      this.writeRaw(var1.getValue());
   }

   public abstract void writeRaw(String var1) throws IOException, JsonGenerationException;

   public abstract void writeRaw(String var1, int var2, int var3) throws IOException, JsonGenerationException;

   public abstract void writeRaw(char[] var1, int var2, int var3) throws IOException, JsonGenerationException;

   public abstract void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException;

   public abstract void writeRawValue(String var1) throws IOException, JsonGenerationException;

   public abstract void writeRawValue(String var1, int var2, int var3) throws IOException, JsonGenerationException;

   public abstract void writeRawValue(char[] var1, int var2, int var3) throws IOException, JsonGenerationException;

   public abstract void writeStartArray() throws IOException, JsonGenerationException;

   public abstract void writeStartObject() throws IOException, JsonGenerationException;

   public abstract void writeString(SerializableString var1) throws IOException, JsonGenerationException;

   public abstract void writeString(String var1) throws IOException, JsonGenerationException;

   public abstract void writeString(char[] var1, int var2, int var3) throws IOException, JsonGenerationException;

   public void writeStringField(String var1, String var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeString(var2);
   }

   public abstract void writeTree(TreeNode var1) throws IOException, JsonProcessingException;

   public abstract void writeUTF8String(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException;

   public static enum Feature {

      // $FF: synthetic field
      private static final JsonGenerator.Feature[] $VALUES = new JsonGenerator.Feature[]{AUTO_CLOSE_TARGET, AUTO_CLOSE_JSON_CONTENT, QUOTE_FIELD_NAMES, QUOTE_NON_NUMERIC_NUMBERS, WRITE_NUMBERS_AS_STRINGS, FLUSH_PASSED_TO_STREAM, ESCAPE_NON_ASCII};
      AUTO_CLOSE_JSON_CONTENT("AUTO_CLOSE_JSON_CONTENT", 1, true),
      AUTO_CLOSE_TARGET("AUTO_CLOSE_TARGET", 0, true),
      ESCAPE_NON_ASCII("ESCAPE_NON_ASCII", 6, false),
      FLUSH_PASSED_TO_STREAM("FLUSH_PASSED_TO_STREAM", 5, true),
      QUOTE_FIELD_NAMES("QUOTE_FIELD_NAMES", 2, true),
      QUOTE_NON_NUMERIC_NUMBERS("QUOTE_NON_NUMERIC_NUMBERS", 3, true),
      WRITE_NUMBERS_AS_STRINGS("WRITE_NUMBERS_AS_STRINGS", 4, false);
      private final boolean _defaultState;
      private final int _mask = 1 << this.ordinal();


      private Feature(String var1, int var2, boolean var3) {
         this._defaultState = var3;
      }

      public static int collectDefaults() {
         JsonGenerator.Feature[] var4 = values();
         int var3 = var4.length;
         int var0 = 0;

         int var1;
         int var2;
         for(var1 = 0; var0 < var3; var1 = var2) {
            JsonGenerator.Feature var5 = var4[var0];
            var2 = var1;
            if(var5.enabledByDefault()) {
               var2 = var1 | var5.getMask();
            }

            ++var0;
         }

         return var1;
      }

      public boolean enabledByDefault() {
         return this._defaultState;
      }

      public int getMask() {
         return this._mask;
      }
   }
}
