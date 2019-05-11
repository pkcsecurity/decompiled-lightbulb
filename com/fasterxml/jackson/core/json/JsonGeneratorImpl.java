package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.GeneratorBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;

public abstract class JsonGeneratorImpl extends GeneratorBase {

   protected static final int[] sOutputEscapes = CharTypes.get7BitOutputEscapes();
   protected CharacterEscapes _characterEscapes;
   protected final IOContext _ioContext;
   protected int _maximumNonEscapedChar;
   protected int[] _outputEscapes;
   protected SerializableString _rootValueSeparator;


   public JsonGeneratorImpl(IOContext var1, int var2, ObjectCodec var3) {
      super(var2, var3);
      this._outputEscapes = sOutputEscapes;
      this._rootValueSeparator = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
      this._ioContext = var1;
      if(this.isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII)) {
         this.setHighestNonEscapedChar(127);
      }

   }

   public CharacterEscapes getCharacterEscapes() {
      return this._characterEscapes;
   }

   public int getHighestEscapedChar() {
      return this._maximumNonEscapedChar;
   }

   public JsonGenerator setCharacterEscapes(CharacterEscapes var1) {
      this._characterEscapes = var1;
      if(var1 == null) {
         this._outputEscapes = sOutputEscapes;
         return this;
      } else {
         this._outputEscapes = var1.getEscapeCodesForAscii();
         return this;
      }
   }

   public JsonGenerator setHighestNonEscapedChar(int var1) {
      int var2 = var1;
      if(var1 < 0) {
         var2 = 0;
      }

      this._maximumNonEscapedChar = var2;
      return this;
   }

   public JsonGenerator setRootValueSeparator(SerializableString var1) {
      this._rootValueSeparator = var1;
      return this;
   }

   public Version version() {
      return VersionUtil.versionFor(this.getClass());
   }

   public final void writeStringField(String var1, String var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeString(var2);
   }
}
