package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonParserDelegate extends JsonParser {

   protected JsonParser delegate;


   public JsonParserDelegate(JsonParser var1) {
      this.delegate = var1;
   }

   public boolean canUseSchema(FormatSchema var1) {
      return this.delegate.canUseSchema(var1);
   }

   public void clearCurrentToken() {
      this.delegate.clearCurrentToken();
   }

   public void close() throws IOException {
      this.delegate.close();
   }

   public JsonParser disable(JsonParser.Feature var1) {
      this.delegate.disable(var1);
      return this;
   }

   public JsonParser enable(JsonParser.Feature var1) {
      this.delegate.enable(var1);
      return this;
   }

   public BigInteger getBigIntegerValue() throws IOException, JsonParseException {
      return this.delegate.getBigIntegerValue();
   }

   public byte[] getBinaryValue(Base64Variant var1) throws IOException, JsonParseException {
      return this.delegate.getBinaryValue(var1);
   }

   public boolean getBooleanValue() throws IOException, JsonParseException {
      return this.delegate.getBooleanValue();
   }

   public byte getByteValue() throws IOException, JsonParseException {
      return this.delegate.getByteValue();
   }

   public ObjectCodec getCodec() {
      return this.delegate.getCodec();
   }

   public JsonLocation getCurrentLocation() {
      return this.delegate.getCurrentLocation();
   }

   public String getCurrentName() throws IOException, JsonParseException {
      return this.delegate.getCurrentName();
   }

   public JsonToken getCurrentToken() {
      return this.delegate.getCurrentToken();
   }

   public BigDecimal getDecimalValue() throws IOException, JsonParseException {
      return this.delegate.getDecimalValue();
   }

   public double getDoubleValue() throws IOException, JsonParseException {
      return this.delegate.getDoubleValue();
   }

   public Object getEmbeddedObject() throws IOException, JsonParseException {
      return this.delegate.getEmbeddedObject();
   }

   public float getFloatValue() throws IOException, JsonParseException {
      return this.delegate.getFloatValue();
   }

   public Object getInputSource() {
      return this.delegate.getInputSource();
   }

   public int getIntValue() throws IOException, JsonParseException {
      return this.delegate.getIntValue();
   }

   public JsonToken getLastClearedToken() {
      return this.delegate.getLastClearedToken();
   }

   public long getLongValue() throws IOException, JsonParseException {
      return this.delegate.getLongValue();
   }

   public JsonParser.NumberType getNumberType() throws IOException, JsonParseException {
      return this.delegate.getNumberType();
   }

   public Number getNumberValue() throws IOException, JsonParseException {
      return this.delegate.getNumberValue();
   }

   public JsonStreamContext getParsingContext() {
      return this.delegate.getParsingContext();
   }

   public FormatSchema getSchema() {
      return this.delegate.getSchema();
   }

   public short getShortValue() throws IOException, JsonParseException {
      return this.delegate.getShortValue();
   }

   public String getText() throws IOException, JsonParseException {
      return this.delegate.getText();
   }

   public char[] getTextCharacters() throws IOException, JsonParseException {
      return this.delegate.getTextCharacters();
   }

   public int getTextLength() throws IOException, JsonParseException {
      return this.delegate.getTextLength();
   }

   public int getTextOffset() throws IOException, JsonParseException {
      return this.delegate.getTextOffset();
   }

   public JsonLocation getTokenLocation() {
      return this.delegate.getTokenLocation();
   }

   public boolean getValueAsBoolean() throws IOException, JsonParseException {
      return this.delegate.getValueAsBoolean();
   }

   public boolean getValueAsBoolean(boolean var1) throws IOException, JsonParseException {
      return this.delegate.getValueAsBoolean(var1);
   }

   public double getValueAsDouble() throws IOException, JsonParseException {
      return this.delegate.getValueAsDouble();
   }

   public double getValueAsDouble(double var1) throws IOException, JsonParseException {
      return this.delegate.getValueAsDouble(var1);
   }

   public int getValueAsInt() throws IOException, JsonParseException {
      return this.delegate.getValueAsInt();
   }

   public int getValueAsInt(int var1) throws IOException, JsonParseException {
      return this.delegate.getValueAsInt(var1);
   }

   public long getValueAsLong() throws IOException, JsonParseException {
      return this.delegate.getValueAsLong();
   }

   public long getValueAsLong(long var1) throws IOException, JsonParseException {
      return this.delegate.getValueAsLong(var1);
   }

   public String getValueAsString() throws IOException, JsonParseException {
      return this.delegate.getValueAsString();
   }

   public String getValueAsString(String var1) throws IOException, JsonParseException {
      return this.delegate.getValueAsString(var1);
   }

   public boolean hasCurrentToken() {
      return this.delegate.hasCurrentToken();
   }

   public boolean hasTextCharacters() {
      return this.delegate.hasTextCharacters();
   }

   public boolean isClosed() {
      return this.delegate.isClosed();
   }

   public boolean isEnabled(JsonParser.Feature var1) {
      return this.delegate.isEnabled(var1);
   }

   public JsonToken nextToken() throws IOException, JsonParseException {
      return this.delegate.nextToken();
   }

   public JsonToken nextValue() throws IOException, JsonParseException {
      return this.delegate.nextValue();
   }

   public void overrideCurrentName(String var1) {
      this.delegate.overrideCurrentName(var1);
   }

   public int readBinaryValue(Base64Variant var1, OutputStream var2) throws IOException, JsonParseException {
      return this.delegate.readBinaryValue(var1, var2);
   }

   public boolean requiresCustomCodec() {
      return this.delegate.requiresCustomCodec();
   }

   public void setCodec(ObjectCodec var1) {
      this.delegate.setCodec(var1);
   }

   public void setSchema(FormatSchema var1) {
      this.delegate.setSchema(var1);
   }

   public JsonParser skipChildren() throws IOException, JsonParseException {
      this.delegate.skipChildren();
      return this;
   }

   public Version version() {
      return this.delegate.version();
   }
}
