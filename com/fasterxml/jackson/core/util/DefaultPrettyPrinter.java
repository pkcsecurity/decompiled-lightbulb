package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.util.Instantiatable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

public class DefaultPrettyPrinter implements PrettyPrinter, Instantiatable<DefaultPrettyPrinter>, Serializable {

   public static final SerializedString DEFAULT_ROOT_VALUE_SEPARATOR = new SerializedString(" ");
   private static final long serialVersionUID = -5512586643324525213L;
   protected DefaultPrettyPrinter.Indenter _arrayIndenter;
   protected transient int _nesting;
   protected DefaultPrettyPrinter.Indenter _objectIndenter;
   protected final SerializableString _rootSeparator;
   protected boolean _spacesInObjectEntries;


   public DefaultPrettyPrinter() {
      this((SerializableString)DEFAULT_ROOT_VALUE_SEPARATOR);
   }

   public DefaultPrettyPrinter(SerializableString var1) {
      this._arrayIndenter = DefaultPrettyPrinter.FixedSpaceIndenter.instance;
      this._objectIndenter = DefaultPrettyPrinter.Lf2SpacesIndenter.instance;
      this._spacesInObjectEntries = true;
      this._nesting = 0;
      this._rootSeparator = var1;
   }

   public DefaultPrettyPrinter(DefaultPrettyPrinter var1) {
      this(var1, var1._rootSeparator);
   }

   public DefaultPrettyPrinter(DefaultPrettyPrinter var1, SerializableString var2) {
      this._arrayIndenter = DefaultPrettyPrinter.FixedSpaceIndenter.instance;
      this._objectIndenter = DefaultPrettyPrinter.Lf2SpacesIndenter.instance;
      this._spacesInObjectEntries = true;
      this._nesting = 0;
      this._arrayIndenter = var1._arrayIndenter;
      this._objectIndenter = var1._objectIndenter;
      this._spacesInObjectEntries = var1._spacesInObjectEntries;
      this._nesting = var1._nesting;
      this._rootSeparator = var2;
   }

   public DefaultPrettyPrinter(String var1) {
      SerializedString var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = new SerializedString(var1);
      }

      this((SerializableString)var2);
   }

   public void beforeArrayValues(JsonGenerator var1) throws IOException, JsonGenerationException {
      this._arrayIndenter.writeIndentation(var1, this._nesting);
   }

   public void beforeObjectEntries(JsonGenerator var1) throws IOException, JsonGenerationException {
      this._objectIndenter.writeIndentation(var1, this._nesting);
   }

   public DefaultPrettyPrinter createInstance() {
      return new DefaultPrettyPrinter(this);
   }

   public void indentArraysWith(DefaultPrettyPrinter.Indenter var1) {
      Object var2 = var1;
      if(var1 == null) {
         var2 = DefaultPrettyPrinter.NopIndenter.instance;
      }

      this._arrayIndenter = (DefaultPrettyPrinter.Indenter)var2;
   }

   public void indentObjectsWith(DefaultPrettyPrinter.Indenter var1) {
      Object var2 = var1;
      if(var1 == null) {
         var2 = DefaultPrettyPrinter.NopIndenter.instance;
      }

      this._objectIndenter = (DefaultPrettyPrinter.Indenter)var2;
   }

   public void spacesInObjectEntries(boolean var1) {
      this._spacesInObjectEntries = var1;
   }

   public DefaultPrettyPrinter withRootSeparator(SerializableString var1) {
      return this._rootSeparator != var1?(var1 != null && var1.equals(this._rootSeparator)?this:new DefaultPrettyPrinter(this, var1)):this;
   }

   public void writeArrayValueSeparator(JsonGenerator var1) throws IOException, JsonGenerationException {
      var1.writeRaw(',');
      this._arrayIndenter.writeIndentation(var1, this._nesting);
   }

   public void writeEndArray(JsonGenerator var1, int var2) throws IOException, JsonGenerationException {
      if(!this._arrayIndenter.isInline()) {
         --this._nesting;
      }

      if(var2 > 0) {
         this._arrayIndenter.writeIndentation(var1, this._nesting);
      } else {
         var1.writeRaw(' ');
      }

      var1.writeRaw(']');
   }

   public void writeEndObject(JsonGenerator var1, int var2) throws IOException, JsonGenerationException {
      if(!this._objectIndenter.isInline()) {
         --this._nesting;
      }

      if(var2 > 0) {
         this._objectIndenter.writeIndentation(var1, this._nesting);
      } else {
         var1.writeRaw(' ');
      }

      var1.writeRaw('}');
   }

   public void writeObjectEntrySeparator(JsonGenerator var1) throws IOException, JsonGenerationException {
      var1.writeRaw(',');
      this._objectIndenter.writeIndentation(var1, this._nesting);
   }

   public void writeObjectFieldValueSeparator(JsonGenerator var1) throws IOException, JsonGenerationException {
      if(this._spacesInObjectEntries) {
         var1.writeRaw(" : ");
      } else {
         var1.writeRaw(':');
      }
   }

   public void writeRootValueSeparator(JsonGenerator var1) throws IOException, JsonGenerationException {
      if(this._rootSeparator != null) {
         var1.writeRaw(this._rootSeparator);
      }

   }

   public void writeStartArray(JsonGenerator var1) throws IOException, JsonGenerationException {
      if(!this._arrayIndenter.isInline()) {
         ++this._nesting;
      }

      var1.writeRaw('[');
   }

   public void writeStartObject(JsonGenerator var1) throws IOException, JsonGenerationException {
      var1.writeRaw('{');
      if(!this._objectIndenter.isInline()) {
         ++this._nesting;
      }

   }

   public static class Lf2SpacesIndenter extends DefaultPrettyPrinter.NopIndenter {

      static final char[] SPACES;
      static final int SPACE_COUNT = 64;
      private static final String SYS_LF;
      public static final DefaultPrettyPrinter.Lf2SpacesIndenter instance = new DefaultPrettyPrinter.Lf2SpacesIndenter();


      static {
         String var0;
         try {
            var0 = System.getProperty("line.separator");
         } catch (Throwable var2) {
            var0 = null;
         }

         String var1 = var0;
         if(var0 == null) {
            var1 = "\n";
         }

         SYS_LF = var1;
         SPACES = new char[64];
         Arrays.fill(SPACES, ' ');
      }

      public boolean isInline() {
         return false;
      }

      public void writeIndentation(JsonGenerator var1, int var2) throws IOException, JsonGenerationException {
         var1.writeRaw(SYS_LF);
         if(var2 > 0) {
            for(var2 += var2; var2 > 64; var2 -= SPACES.length) {
               var1.writeRaw(SPACES, 0, 64);
            }

            var1.writeRaw(SPACES, 0, var2);
         }

      }
   }

   public static class NopIndenter implements DefaultPrettyPrinter.Indenter, Serializable {

      public static final DefaultPrettyPrinter.NopIndenter instance = new DefaultPrettyPrinter.NopIndenter();


      public boolean isInline() {
         return true;
      }

      public void writeIndentation(JsonGenerator var1, int var2) throws IOException, JsonGenerationException {}
   }

   public interface Indenter {

      boolean isInline();

      void writeIndentation(JsonGenerator var1, int var2) throws IOException, JsonGenerationException;
   }

   public static class FixedSpaceIndenter extends DefaultPrettyPrinter.NopIndenter {

      public static final DefaultPrettyPrinter.FixedSpaceIndenter instance = new DefaultPrettyPrinter.FixedSpaceIndenter();


      public boolean isInline() {
         return true;
      }

      public void writeIndentation(JsonGenerator var1, int var2) throws IOException, JsonGenerationException {
         var1.writeRaw(' ');
      }
   }
}
