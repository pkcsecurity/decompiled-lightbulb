package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonGeneratorDelegate extends JsonGenerator {

   protected JsonGenerator delegate;


   public JsonGeneratorDelegate(JsonGenerator var1) {
      this.delegate = var1;
   }

   public boolean canUseSchema(FormatSchema var1) {
      return this.delegate.canUseSchema(var1);
   }

   public void close() throws IOException {
      this.delegate.close();
   }

   public void copyCurrentEvent(JsonParser var1) throws IOException, JsonProcessingException {
      this.delegate.copyCurrentEvent(var1);
   }

   public void copyCurrentStructure(JsonParser var1) throws IOException, JsonProcessingException {
      this.delegate.copyCurrentStructure(var1);
   }

   public JsonGenerator disable(JsonGenerator.Feature var1) {
      this.delegate.disable(var1);
      return this;
   }

   public JsonGenerator enable(JsonGenerator.Feature var1) {
      this.delegate.enable(var1);
      return this;
   }

   public void flush() throws IOException {
      this.delegate.flush();
   }

   public CharacterEscapes getCharacterEscapes() {
      return this.delegate.getCharacterEscapes();
   }

   public ObjectCodec getCodec() {
      return this.delegate.getCodec();
   }

   public int getHighestEscapedChar() {
      return this.delegate.getHighestEscapedChar();
   }

   public JsonStreamContext getOutputContext() {
      return this.delegate.getOutputContext();
   }

   public Object getOutputTarget() {
      return this.delegate.getOutputTarget();
   }

   public PrettyPrinter getPrettyPrinter() {
      return this.delegate.getPrettyPrinter();
   }

   public FormatSchema getSchema() {
      return this.delegate.getSchema();
   }

   public boolean isClosed() {
      return this.delegate.isClosed();
   }

   public boolean isEnabled(JsonGenerator.Feature var1) {
      return this.delegate.isEnabled(var1);
   }

   public JsonGenerator setCharacterEscapes(CharacterEscapes var1) {
      this.delegate.setCharacterEscapes(var1);
      return this;
   }

   public JsonGenerator setCodec(ObjectCodec var1) {
      this.delegate.setCodec(var1);
      return this;
   }

   public JsonGenerator setHighestNonEscapedChar(int var1) {
      this.delegate.setHighestNonEscapedChar(var1);
      return this;
   }

   public JsonGenerator setPrettyPrinter(PrettyPrinter var1) {
      this.delegate.setPrettyPrinter(var1);
      return this;
   }

   public JsonGenerator setRootValueSeparator(SerializableString var1) {
      this.delegate.setRootValueSeparator(var1);
      return this;
   }

   public void setSchema(FormatSchema var1) {
      this.delegate.setSchema(var1);
   }

   public JsonGenerator useDefaultPrettyPrinter() {
      this.delegate.useDefaultPrettyPrinter();
      return this;
   }

   public Version version() {
      return this.delegate.version();
   }

   public int writeBinary(Base64Variant var1, InputStream var2, int var3) throws IOException, JsonGenerationException {
      return this.delegate.writeBinary(var1, var2, var3);
   }

   public void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException {
      this.delegate.writeBinary(var1, var2, var3, var4);
   }

   public void writeBoolean(boolean var1) throws IOException, JsonGenerationException {
      this.delegate.writeBoolean(var1);
   }

   public void writeEndArray() throws IOException, JsonGenerationException {
      this.delegate.writeEndArray();
   }

   public void writeEndObject() throws IOException, JsonGenerationException {
      this.delegate.writeEndObject();
   }

   public void writeFieldName(SerializableString var1) throws IOException, JsonGenerationException {
      this.delegate.writeFieldName(var1);
   }

   public void writeFieldName(String var1) throws IOException, JsonGenerationException {
      this.delegate.writeFieldName(var1);
   }

   public void writeNull() throws IOException, JsonGenerationException {
      this.delegate.writeNull();
   }

   public void writeNumber(double var1) throws IOException, JsonGenerationException {
      this.delegate.writeNumber(var1);
   }

   public void writeNumber(float var1) throws IOException, JsonGenerationException {
      this.delegate.writeNumber(var1);
   }

   public void writeNumber(int var1) throws IOException, JsonGenerationException {
      this.delegate.writeNumber(var1);
   }

   public void writeNumber(long var1) throws IOException, JsonGenerationException {
      this.delegate.writeNumber(var1);
   }

   public void writeNumber(String var1) throws IOException, JsonGenerationException, UnsupportedOperationException {
      this.delegate.writeNumber(var1);
   }

   public void writeNumber(BigDecimal var1) throws IOException, JsonGenerationException {
      this.delegate.writeNumber(var1);
   }

   public void writeNumber(BigInteger var1) throws IOException, JsonGenerationException {
      this.delegate.writeNumber(var1);
   }

   public void writeNumber(short var1) throws IOException, JsonGenerationException {
      this.delegate.writeNumber(var1);
   }

   public void writeObject(Object var1) throws IOException, JsonProcessingException {
      this.delegate.writeObject(var1);
   }

   public void writeRaw(char var1) throws IOException, JsonGenerationException {
      this.delegate.writeRaw(var1);
   }

   public void writeRaw(SerializableString var1) throws IOException, JsonGenerationException {
      this.delegate.writeRaw(var1);
   }

   public void writeRaw(String var1) throws IOException, JsonGenerationException {
      this.delegate.writeRaw(var1);
   }

   public void writeRaw(String var1, int var2, int var3) throws IOException, JsonGenerationException {
      this.delegate.writeRaw(var1, var2, var3);
   }

   public void writeRaw(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this.delegate.writeRaw(var1, var2, var3);
   }

   public void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this.delegate.writeRawUTF8String(var1, var2, var3);
   }

   public void writeRawValue(String var1) throws IOException, JsonGenerationException {
      this.delegate.writeRawValue(var1);
   }

   public void writeRawValue(String var1, int var2, int var3) throws IOException, JsonGenerationException {
      this.delegate.writeRawValue(var1, var2, var3);
   }

   public void writeRawValue(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this.delegate.writeRawValue(var1, var2, var3);
   }

   public void writeStartArray() throws IOException, JsonGenerationException {
      this.delegate.writeStartArray();
   }

   public void writeStartObject() throws IOException, JsonGenerationException {
      this.delegate.writeStartObject();
   }

   public void writeString(SerializableString var1) throws IOException, JsonGenerationException {
      this.delegate.writeString(var1);
   }

   public void writeString(String var1) throws IOException, JsonGenerationException {
      this.delegate.writeString(var1);
   }

   public void writeString(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this.delegate.writeString(var1, var2, var3);
   }

   public void writeTree(TreeNode var1) throws IOException, JsonProcessingException {
      this.delegate.writeTree(var1);
   }

   public void writeUTF8String(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this.delegate.writeUTF8String(var1, var2, var3);
   }
}
