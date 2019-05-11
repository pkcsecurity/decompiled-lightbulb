package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.PrettyPrinter;
import java.io.IOException;
import java.io.Serializable;

public class MinimalPrettyPrinter implements PrettyPrinter, Serializable {

   public static final String DEFAULT_ROOT_VALUE_SEPARATOR = " ";
   private static final long serialVersionUID = -562765100295218442L;
   protected String _rootValueSeparator;


   public MinimalPrettyPrinter() {
      this(" ");
   }

   public MinimalPrettyPrinter(String var1) {
      this._rootValueSeparator = " ";
      this._rootValueSeparator = var1;
   }

   public void beforeArrayValues(JsonGenerator var1) throws IOException, JsonGenerationException {}

   public void beforeObjectEntries(JsonGenerator var1) throws IOException, JsonGenerationException {}

   public void setRootValueSeparator(String var1) {
      this._rootValueSeparator = var1;
   }

   public void writeArrayValueSeparator(JsonGenerator var1) throws IOException, JsonGenerationException {
      var1.writeRaw(',');
   }

   public void writeEndArray(JsonGenerator var1, int var2) throws IOException, JsonGenerationException {
      var1.writeRaw(']');
   }

   public void writeEndObject(JsonGenerator var1, int var2) throws IOException, JsonGenerationException {
      var1.writeRaw('}');
   }

   public void writeObjectEntrySeparator(JsonGenerator var1) throws IOException, JsonGenerationException {
      var1.writeRaw(',');
   }

   public void writeObjectFieldValueSeparator(JsonGenerator var1) throws IOException, JsonGenerationException {
      var1.writeRaw(':');
   }

   public void writeRootValueSeparator(JsonGenerator var1) throws IOException, JsonGenerationException {
      if(this._rootValueSeparator != null) {
         var1.writeRaw(this._rootValueSeparator);
      }

   }

   public void writeStartArray(JsonGenerator var1) throws IOException, JsonGenerationException {
      var1.writeRaw('[');
   }

   public void writeStartObject(JsonGenerator var1) throws IOException, JsonGenerationException {
      var1.writeRaw('{');
   }
}
