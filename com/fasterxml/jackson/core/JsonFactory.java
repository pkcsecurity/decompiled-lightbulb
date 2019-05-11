package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.InputDecorator;
import com.fasterxml.jackson.core.io.OutputDecorator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.io.UTF8Writer;
import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.core.json.ReaderBasedJsonParser;
import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
import com.fasterxml.jackson.core.json.WriterBasedJsonGenerator;
import com.fasterxml.jackson.core.sym.BytesToNameCanonicalizer;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.Writer;
import java.lang.ref.SoftReference;
import java.net.URL;

public class JsonFactory implements Versioned, Serializable {

   protected static final int DEFAULT_FACTORY_FEATURE_FLAGS = JsonFactory.Feature.collectDefaults();
   protected static final int DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator.Feature.collectDefaults();
   protected static final int DEFAULT_PARSER_FEATURE_FLAGS = JsonParser.Feature.collectDefaults();
   private static final SerializableString DEFAULT_ROOT_VALUE_SEPARATOR = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
   public static final String FORMAT_NAME_JSON = "JSON";
   protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef = new ThreadLocal();
   private static final long serialVersionUID = 8726401676402117450L;
   protected CharacterEscapes _characterEscapes;
   protected int _factoryFeatures;
   protected int _generatorFeatures;
   protected InputDecorator _inputDecorator;
   protected ObjectCodec _objectCodec;
   protected OutputDecorator _outputDecorator;
   protected int _parserFeatures;
   protected final transient BytesToNameCanonicalizer _rootByteSymbols;
   protected final transient CharsToNameCanonicalizer _rootCharSymbols;
   protected SerializableString _rootValueSeparator;


   public JsonFactory() {
      this((ObjectCodec)null);
   }

   protected JsonFactory(JsonFactory var1, ObjectCodec var2) {
      this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
      this._rootByteSymbols = BytesToNameCanonicalizer.createRoot();
      this._factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
      this._parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
      this._generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
      this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
      this._objectCodec = null;
      this._factoryFeatures = var1._factoryFeatures;
      this._parserFeatures = var1._parserFeatures;
      this._generatorFeatures = var1._generatorFeatures;
      this._characterEscapes = var1._characterEscapes;
      this._inputDecorator = var1._inputDecorator;
      this._outputDecorator = var1._outputDecorator;
      this._rootValueSeparator = var1._rootValueSeparator;
   }

   public JsonFactory(ObjectCodec var1) {
      this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
      this._rootByteSymbols = BytesToNameCanonicalizer.createRoot();
      this._factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
      this._parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
      this._generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
      this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
      this._objectCodec = var1;
   }

   protected void _checkInvalidCopy(Class<?> var1) {
      if(this.getClass() != var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Failed copy(): ");
         var2.append(this.getClass().getName());
         var2.append(" (version: ");
         var2.append(this.version());
         var2.append(") does not override copy(); it has to");
         throw new IllegalStateException(var2.toString());
      }
   }

   protected IOContext _createContext(Object var1, boolean var2) {
      return new IOContext(this._getBufferRecycler(), var1, var2);
   }

   protected JsonGenerator _createGenerator(Writer var1, IOContext var2) throws IOException {
      WriterBasedJsonGenerator var3 = new WriterBasedJsonGenerator(var2, this._generatorFeatures, this._objectCodec, var1);
      if(this._characterEscapes != null) {
         var3.setCharacterEscapes(this._characterEscapes);
      }

      SerializableString var4 = this._rootValueSeparator;
      if(var4 != DEFAULT_ROOT_VALUE_SEPARATOR) {
         var3.setRootValueSeparator(var4);
      }

      return var3;
   }

   @Deprecated
   protected JsonGenerator _createJsonGenerator(Writer var1, IOContext var2) throws IOException {
      return this._createGenerator(var1, var2);
   }

   @Deprecated
   protected JsonParser _createJsonParser(InputStream var1, IOContext var2) throws IOException, JsonParseException {
      return this._createParser(var1, var2);
   }

   @Deprecated
   protected JsonParser _createJsonParser(Reader var1, IOContext var2) throws IOException, JsonParseException {
      return this._createParser(var1, var2);
   }

   @Deprecated
   protected JsonParser _createJsonParser(byte[] var1, int var2, int var3, IOContext var4) throws IOException, JsonParseException {
      return this._createParser(var1, var2, var3, var4);
   }

   protected JsonParser _createParser(InputStream var1, IOContext var2) throws IOException, JsonParseException {
      return (new ByteSourceJsonBootstrapper(var2, var1)).constructParser(this._parserFeatures, this._objectCodec, this._rootByteSymbols, this._rootCharSymbols, this.isEnabled(JsonFactory.Feature.CANONICALIZE_FIELD_NAMES), this.isEnabled(JsonFactory.Feature.INTERN_FIELD_NAMES));
   }

   protected JsonParser _createParser(Reader var1, IOContext var2) throws IOException, JsonParseException {
      return new ReaderBasedJsonParser(var2, this._parserFeatures, var1, this._objectCodec, this._rootCharSymbols.makeChild(this.isEnabled(JsonFactory.Feature.CANONICALIZE_FIELD_NAMES), this.isEnabled(JsonFactory.Feature.INTERN_FIELD_NAMES)));
   }

   protected JsonParser _createParser(byte[] var1, int var2, int var3, IOContext var4) throws IOException, JsonParseException {
      return (new ByteSourceJsonBootstrapper(var4, var1, var2, var3)).constructParser(this._parserFeatures, this._objectCodec, this._rootByteSymbols, this._rootCharSymbols, this.isEnabled(JsonFactory.Feature.CANONICALIZE_FIELD_NAMES), this.isEnabled(JsonFactory.Feature.INTERN_FIELD_NAMES));
   }

   protected JsonGenerator _createUTF8Generator(OutputStream var1, IOContext var2) throws IOException {
      UTF8JsonGenerator var3 = new UTF8JsonGenerator(var2, this._generatorFeatures, this._objectCodec, var1);
      if(this._characterEscapes != null) {
         var3.setCharacterEscapes(this._characterEscapes);
      }

      SerializableString var4 = this._rootValueSeparator;
      if(var4 != DEFAULT_ROOT_VALUE_SEPARATOR) {
         var3.setRootValueSeparator(var4);
      }

      return var3;
   }

   @Deprecated
   protected JsonGenerator _createUTF8JsonGenerator(OutputStream var1, IOContext var2) throws IOException {
      return this._createUTF8Generator(var1, var2);
   }

   protected Writer _createWriter(OutputStream var1, JsonEncoding var2, IOContext var3) throws IOException {
      return (Writer)(var2 == JsonEncoding.UTF8?new UTF8Writer(var3, var1):new OutputStreamWriter(var1, var2.getJavaName()));
   }

   public BufferRecycler _getBufferRecycler() {
      SoftReference var1 = (SoftReference)_recyclerRef.get();
      BufferRecycler var3;
      if(var1 == null) {
         var3 = null;
      } else {
         var3 = (BufferRecycler)var1.get();
      }

      BufferRecycler var2 = var3;
      if(var3 == null) {
         var2 = new BufferRecycler();
         _recyclerRef.set(new SoftReference(var2));
      }

      return var2;
   }

   protected InputStream _optimizedStreamFromURL(URL var1) throws IOException {
      if("file".equals(var1.getProtocol())) {
         String var2 = var1.getHost();
         if((var2 == null || var2.length() == 0) && var1.getPath().indexOf(37) < 0) {
            return new FileInputStream(var1.getPath());
         }
      }

      return var1.openStream();
   }

   public boolean canUseSchema(FormatSchema var1) {
      String var2 = this.getFormatName();
      return var2 != null && var2.equals(var1.getSchemaType());
   }

   public final JsonFactory configure(JsonFactory.Feature var1, boolean var2) {
      return var2?this.enable(var1):this.disable(var1);
   }

   public final JsonFactory configure(JsonGenerator.Feature var1, boolean var2) {
      return var2?this.enable(var1):this.disable(var1);
   }

   public final JsonFactory configure(JsonParser.Feature var1, boolean var2) {
      return var2?this.enable(var1):this.disable(var1);
   }

   public JsonFactory copy() {
      this._checkInvalidCopy(JsonFactory.class);
      return new JsonFactory(this, (ObjectCodec)null);
   }

   public JsonGenerator createGenerator(File var1, JsonEncoding var2) throws IOException {
      FileOutputStream var3 = new FileOutputStream(var1);
      IOContext var4 = this._createContext(var3, true);
      var4.setEncoding(var2);
      if(var2 == JsonEncoding.UTF8) {
         Object var6 = var3;
         if(this._outputDecorator != null) {
            var6 = this._outputDecorator.decorate(var4, (OutputStream)var3);
         }

         return this._createUTF8Generator((OutputStream)var6, var4);
      } else {
         Writer var7 = this._createWriter(var3, var2, var4);
         Writer var5 = var7;
         if(this._outputDecorator != null) {
            var5 = this._outputDecorator.decorate(var4, var7);
         }

         return this._createGenerator(var5, var4);
      }
   }

   public JsonGenerator createGenerator(OutputStream var1) throws IOException {
      return this.createGenerator(var1, JsonEncoding.UTF8);
   }

   public JsonGenerator createGenerator(OutputStream var1, JsonEncoding var2) throws IOException {
      IOContext var3 = this._createContext(var1, false);
      var3.setEncoding(var2);
      if(var2 == JsonEncoding.UTF8) {
         OutputStream var6 = var1;
         if(this._outputDecorator != null) {
            var6 = this._outputDecorator.decorate(var3, var1);
         }

         return this._createUTF8Generator(var6, var3);
      } else {
         Writer var5 = this._createWriter(var1, var2, var3);
         Writer var4 = var5;
         if(this._outputDecorator != null) {
            var4 = this._outputDecorator.decorate(var3, var5);
         }

         return this._createGenerator(var4, var3);
      }
   }

   public JsonGenerator createGenerator(Writer var1) throws IOException {
      IOContext var3 = this._createContext(var1, false);
      Writer var2 = var1;
      if(this._outputDecorator != null) {
         var2 = this._outputDecorator.decorate(var3, var1);
      }

      return this._createGenerator(var2, var3);
   }

   @Deprecated
   public JsonGenerator createJsonGenerator(File var1, JsonEncoding var2) throws IOException {
      return this.createGenerator(var1, var2);
   }

   @Deprecated
   public JsonGenerator createJsonGenerator(OutputStream var1) throws IOException {
      return this.createGenerator(var1, JsonEncoding.UTF8);
   }

   @Deprecated
   public JsonGenerator createJsonGenerator(OutputStream var1, JsonEncoding var2) throws IOException {
      return this.createGenerator(var1, var2);
   }

   @Deprecated
   public JsonGenerator createJsonGenerator(Writer var1) throws IOException {
      return this.createGenerator(var1);
   }

   @Deprecated
   public JsonParser createJsonParser(File var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   @Deprecated
   public JsonParser createJsonParser(InputStream var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   @Deprecated
   public JsonParser createJsonParser(Reader var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   @Deprecated
   public JsonParser createJsonParser(String var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   @Deprecated
   public JsonParser createJsonParser(URL var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   @Deprecated
   public JsonParser createJsonParser(byte[] var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   @Deprecated
   public JsonParser createJsonParser(byte[] var1, int var2, int var3) throws IOException, JsonParseException {
      return this.createParser(var1, var2, var3);
   }

   public JsonParser createParser(File var1) throws IOException, JsonParseException {
      IOContext var3 = this._createContext(var1, true);
      FileInputStream var2 = new FileInputStream(var1);
      Object var4 = var2;
      if(this._inputDecorator != null) {
         var4 = this._inputDecorator.decorate(var3, (InputStream)var2);
      }

      return this._createParser((InputStream)var4, var3);
   }

   public JsonParser createParser(InputStream var1) throws IOException, JsonParseException {
      IOContext var3 = this._createContext(var1, false);
      InputStream var2 = var1;
      if(this._inputDecorator != null) {
         var2 = this._inputDecorator.decorate(var3, var1);
      }

      return this._createParser(var2, var3);
   }

   public JsonParser createParser(Reader var1) throws IOException, JsonParseException {
      IOContext var3 = this._createContext(var1, false);
      Reader var2 = var1;
      if(this._inputDecorator != null) {
         var2 = this._inputDecorator.decorate(var3, var1);
      }

      return this._createParser(var2, var3);
   }

   public JsonParser createParser(String var1) throws IOException, JsonParseException {
      StringReader var2 = new StringReader(var1);
      IOContext var3 = this._createContext(var2, true);
      Object var4 = var2;
      if(this._inputDecorator != null) {
         var4 = this._inputDecorator.decorate(var3, (Reader)var2);
      }

      return this._createParser((Reader)var4, var3);
   }

   public JsonParser createParser(URL var1) throws IOException, JsonParseException {
      IOContext var3 = this._createContext(var1, true);
      InputStream var2 = this._optimizedStreamFromURL(var1);
      InputStream var4 = var2;
      if(this._inputDecorator != null) {
         var4 = this._inputDecorator.decorate(var3, var2);
      }

      return this._createParser(var4, var3);
   }

   public JsonParser createParser(byte[] var1) throws IOException, JsonParseException {
      IOContext var2 = this._createContext(var1, true);
      if(this._inputDecorator != null) {
         InputStream var3 = this._inputDecorator.decorate(var2, var1, 0, var1.length);
         if(var3 != null) {
            return this._createParser(var3, var2);
         }
      }

      return this._createParser(var1, 0, var1.length, var2);
   }

   public JsonParser createParser(byte[] var1, int var2, int var3) throws IOException, JsonParseException {
      IOContext var4 = this._createContext(var1, true);
      if(this._inputDecorator != null) {
         InputStream var5 = this._inputDecorator.decorate(var4, var1, var2, var3);
         if(var5 != null) {
            return this._createParser(var5, var4);
         }
      }

      return this._createParser(var1, var2, var3, var4);
   }

   public JsonFactory disable(JsonFactory.Feature var1) {
      int var2 = this._factoryFeatures;
      this._factoryFeatures = ~var1.getMask() & var2;
      return this;
   }

   public JsonFactory disable(JsonGenerator.Feature var1) {
      int var2 = this._generatorFeatures;
      this._generatorFeatures = ~var1.getMask() & var2;
      return this;
   }

   public JsonFactory disable(JsonParser.Feature var1) {
      int var2 = this._parserFeatures;
      this._parserFeatures = ~var1.getMask() & var2;
      return this;
   }

   public JsonFactory enable(JsonFactory.Feature var1) {
      int var2 = this._factoryFeatures;
      this._factoryFeatures = var1.getMask() | var2;
      return this;
   }

   public JsonFactory enable(JsonGenerator.Feature var1) {
      int var2 = this._generatorFeatures;
      this._generatorFeatures = var1.getMask() | var2;
      return this;
   }

   public JsonFactory enable(JsonParser.Feature var1) {
      int var2 = this._parserFeatures;
      this._parserFeatures = var1.getMask() | var2;
      return this;
   }

   public CharacterEscapes getCharacterEscapes() {
      return this._characterEscapes;
   }

   public ObjectCodec getCodec() {
      return this._objectCodec;
   }

   public String getFormatName() {
      return this.getClass() == JsonFactory.class?"JSON":null;
   }

   public InputDecorator getInputDecorator() {
      return this._inputDecorator;
   }

   public OutputDecorator getOutputDecorator() {
      return this._outputDecorator;
   }

   public String getRootValueSeparator() {
      return this._rootValueSeparator == null?null:this._rootValueSeparator.getValue();
   }

   public MatchStrength hasFormat(InputAccessor var1) throws IOException {
      return this.getClass() == JsonFactory.class?this.hasJSONFormat(var1):null;
   }

   protected MatchStrength hasJSONFormat(InputAccessor var1) throws IOException {
      return ByteSourceJsonBootstrapper.hasJSONFormat(var1);
   }

   public final boolean isEnabled(JsonFactory.Feature var1) {
      int var2 = this._factoryFeatures;
      return (var1.getMask() & var2) != 0;
   }

   public final boolean isEnabled(JsonGenerator.Feature var1) {
      int var2 = this._generatorFeatures;
      return (var1.getMask() & var2) != 0;
   }

   public final boolean isEnabled(JsonParser.Feature var1) {
      int var2 = this._parserFeatures;
      return (var1.getMask() & var2) != 0;
   }

   protected Object readResolve() {
      return new JsonFactory(this, this._objectCodec);
   }

   public boolean requiresCustomCodec() {
      return false;
   }

   public JsonFactory setCharacterEscapes(CharacterEscapes var1) {
      this._characterEscapes = var1;
      return this;
   }

   public JsonFactory setCodec(ObjectCodec var1) {
      this._objectCodec = var1;
      return this;
   }

   public JsonFactory setInputDecorator(InputDecorator var1) {
      this._inputDecorator = var1;
      return this;
   }

   public JsonFactory setOutputDecorator(OutputDecorator var1) {
      this._outputDecorator = var1;
      return this;
   }

   public JsonFactory setRootValueSeparator(String var1) {
      SerializedString var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = new SerializedString(var1);
      }

      this._rootValueSeparator = var2;
      return this;
   }

   public Version version() {
      return PackageVersion.VERSION;
   }

   public static enum Feature {

      // $FF: synthetic field
      private static final JsonFactory.Feature[] $VALUES = new JsonFactory.Feature[]{INTERN_FIELD_NAMES, CANONICALIZE_FIELD_NAMES};
      CANONICALIZE_FIELD_NAMES("CANONICALIZE_FIELD_NAMES", 1, true),
      INTERN_FIELD_NAMES("INTERN_FIELD_NAMES", 0, true);
      private final boolean _defaultState;


      private Feature(String var1, int var2, boolean var3) {
         this._defaultState = var3;
      }

      public static int collectDefaults() {
         JsonFactory.Feature[] var4 = values();
         int var3 = var4.length;
         int var0 = 0;

         int var1;
         int var2;
         for(var1 = 0; var0 < var3; var1 = var2) {
            JsonFactory.Feature var5 = var4[var0];
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

      public boolean enabledIn(int var1) {
         return (var1 & this.getMask()) != 0;
      }

      public int getMask() {
         return 1 << this.ordinal();
      }
   }
}
