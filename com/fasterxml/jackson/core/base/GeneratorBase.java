package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class GeneratorBase extends JsonGenerator {

   protected boolean _cfgNumbersAsStrings;
   protected boolean _closed;
   protected int _features;
   protected ObjectCodec _objectCodec;
   protected JsonWriteContext _writeContext;


   protected GeneratorBase(int var1, ObjectCodec var2) {
      this._features = var1;
      this._writeContext = JsonWriteContext.createRootContext();
      this._objectCodec = var2;
      this._cfgNumbersAsStrings = this.isEnabled(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
   }

   public abstract void _releaseBuffers();

   protected void _reportError(String var1) throws JsonGenerationException {
      throw new JsonGenerationException(var1);
   }

   protected void _reportUnsupportedOperation() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Operation not supported by generator of type ");
      var1.append(this.getClass().getName());
      throw new UnsupportedOperationException(var1.toString());
   }

   protected final void _throwInternal() {
      VersionUtil.throwInternal();
   }

   public abstract void _verifyValueWrite(String var1) throws IOException, JsonGenerationException;

   protected void _writeSimpleObject(Object var1) throws IOException, JsonGenerationException {
      if(var1 == null) {
         this.writeNull();
      } else if(var1 instanceof String) {
         this.writeString((String)var1);
      } else {
         if(var1 instanceof Number) {
            Number var2 = (Number)var1;
            if(var2 instanceof Integer) {
               this.writeNumber(var2.intValue());
               return;
            }

            if(var2 instanceof Long) {
               this.writeNumber(var2.longValue());
               return;
            }

            if(var2 instanceof Double) {
               this.writeNumber(var2.doubleValue());
               return;
            }

            if(var2 instanceof Float) {
               this.writeNumber(var2.floatValue());
               return;
            }

            if(var2 instanceof Short) {
               this.writeNumber(var2.shortValue());
               return;
            }

            if(var2 instanceof Byte) {
               this.writeNumber((short)var2.byteValue());
               return;
            }

            if(var2 instanceof BigInteger) {
               this.writeNumber((BigInteger)var2);
               return;
            }

            if(var2 instanceof BigDecimal) {
               this.writeNumber((BigDecimal)var2);
               return;
            }

            if(var2 instanceof AtomicInteger) {
               this.writeNumber(((AtomicInteger)var2).get());
               return;
            }

            if(var2 instanceof AtomicLong) {
               this.writeNumber(((AtomicLong)var2).get());
               return;
            }
         } else {
            if(var1 instanceof byte[]) {
               this.writeBinary((byte[])var1);
               return;
            }

            if(var1 instanceof Boolean) {
               this.writeBoolean(((Boolean)var1).booleanValue());
               return;
            }

            if(var1 instanceof AtomicBoolean) {
               this.writeBoolean(((AtomicBoolean)var1).get());
               return;
            }
         }

         StringBuilder var3 = new StringBuilder();
         var3.append("No ObjectCodec defined for the generator, can only serialize simple wrapper types (type passed ");
         var3.append(var1.getClass().getName());
         var3.append(")");
         throw new IllegalStateException(var3.toString());
      }
   }

   public void close() throws IOException {
      this._closed = true;
   }

   public final void copyCurrentEvent(JsonParser var1) throws IOException, JsonProcessingException {
      JsonToken var2 = var1.getCurrentToken();
      if(var2 == null) {
         this._reportError("No current event to copy");
      }

      switch(null.$SwitchMap$com$fasterxml$jackson$core$JsonToken[var2.ordinal()]) {
      case 1:
         this.writeStartObject();
         return;
      case 2:
         this.writeEndObject();
         return;
      case 3:
         this.writeStartArray();
         return;
      case 4:
         this.writeEndArray();
         return;
      case 5:
         this.writeFieldName(var1.getCurrentName());
         return;
      case 6:
         if(var1.hasTextCharacters()) {
            this.writeString(var1.getTextCharacters(), var1.getTextOffset(), var1.getTextLength());
            return;
         }

         this.writeString(var1.getText());
         return;
      case 7:
         switch(null.$SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType[var1.getNumberType().ordinal()]) {
         case 1:
            this.writeNumber(var1.getIntValue());
            return;
         case 2:
            this.writeNumber(var1.getBigIntegerValue());
            return;
         default:
            this.writeNumber(var1.getLongValue());
            return;
         }
      case 8:
         switch(null.$SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType[var1.getNumberType().ordinal()]) {
         case 3:
            this.writeNumber(var1.getDecimalValue());
            return;
         case 4:
            this.writeNumber(var1.getFloatValue());
            return;
         default:
            this.writeNumber(var1.getDoubleValue());
            return;
         }
      case 9:
         this.writeBoolean(true);
         return;
      case 10:
         this.writeBoolean(false);
         return;
      case 11:
         this.writeNull();
         return;
      case 12:
         this.writeObject(var1.getEmbeddedObject());
         return;
      default:
         this._throwInternal();
      }
   }

   public final void copyCurrentStructure(JsonParser var1) throws IOException, JsonProcessingException {
      JsonToken var4 = var1.getCurrentToken();
      JsonToken var3 = var4;
      if(var4 == JsonToken.FIELD_NAME) {
         this.writeFieldName(var1.getCurrentName());
         var3 = var1.nextToken();
      }

      int var2 = null.$SwitchMap$com$fasterxml$jackson$core$JsonToken[var3.ordinal()];
      if(var2 != 1) {
         if(var2 != 3) {
            this.copyCurrentEvent(var1);
         } else {
            this.writeStartArray();

            while(var1.nextToken() != JsonToken.END_ARRAY) {
               this.copyCurrentStructure(var1);
            }

            this.writeEndArray();
         }
      } else {
         this.writeStartObject();

         while(var1.nextToken() != JsonToken.END_OBJECT) {
            this.copyCurrentStructure(var1);
         }

         this.writeEndObject();
      }
   }

   public JsonGenerator disable(JsonGenerator.Feature var1) {
      this._features &= ~var1.getMask();
      if(var1 == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
         this._cfgNumbersAsStrings = false;
         return this;
      } else {
         if(var1 == JsonGenerator.Feature.ESCAPE_NON_ASCII) {
            this.setHighestNonEscapedChar(0);
         }

         return this;
      }
   }

   public JsonGenerator enable(JsonGenerator.Feature var1) {
      this._features |= var1.getMask();
      if(var1 == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
         this._cfgNumbersAsStrings = true;
         return this;
      } else {
         if(var1 == JsonGenerator.Feature.ESCAPE_NON_ASCII) {
            this.setHighestNonEscapedChar(127);
         }

         return this;
      }
   }

   public abstract void flush() throws IOException;

   public final ObjectCodec getCodec() {
      return this._objectCodec;
   }

   public final JsonWriteContext getOutputContext() {
      return this._writeContext;
   }

   public boolean isClosed() {
      return this._closed;
   }

   public final boolean isEnabled(JsonGenerator.Feature var1) {
      int var2 = this._features;
      return (var1.getMask() & var2) != 0;
   }

   public JsonGenerator setCodec(ObjectCodec var1) {
      this._objectCodec = var1;
      return this;
   }

   public JsonGenerator useDefaultPrettyPrinter() {
      return (JsonGenerator)(this.getPrettyPrinter() != null?this:this.setPrettyPrinter(new DefaultPrettyPrinter()));
   }

   public Version version() {
      return VersionUtil.versionFor(this.getClass());
   }

   public int writeBinary(Base64Variant var1, InputStream var2, int var3) throws IOException, JsonGenerationException {
      this._reportUnsupportedOperation();
      return 0;
   }

   public void writeFieldName(SerializableString var1) throws IOException, JsonGenerationException {
      this.writeFieldName(var1.getValue());
   }

   public void writeObject(Object var1) throws IOException, JsonProcessingException {
      if(var1 == null) {
         this.writeNull();
      } else if(this._objectCodec != null) {
         this._objectCodec.writeValue(this, var1);
      } else {
         this._writeSimpleObject(var1);
      }
   }

   public void writeRawValue(String var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write raw value");
      this.writeRaw(var1);
   }

   public void writeRawValue(String var1, int var2, int var3) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write raw value");
      this.writeRaw(var1, var2, var3);
   }

   public void writeRawValue(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write raw value");
      this.writeRaw(var1, var2, var3);
   }

   public void writeString(SerializableString var1) throws IOException, JsonGenerationException {
      this.writeString(var1.getValue());
   }

   public void writeTree(TreeNode var1) throws IOException, JsonProcessingException {
      if(var1 == null) {
         this.writeNull();
      } else if(this._objectCodec == null) {
         throw new IllegalStateException("No ObjectCodec defined");
      } else {
         this._objectCodec.writeValue(this, var1);
      }
   }
}
