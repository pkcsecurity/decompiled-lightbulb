package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.MergedStream;
import com.fasterxml.jackson.core.io.UTF32Reader;
import com.fasterxml.jackson.core.json.ReaderBasedJsonParser;
import com.fasterxml.jackson.core.json.UTF8StreamJsonParser;
import com.fasterxml.jackson.core.sym.BytesToNameCanonicalizer;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import java.io.ByteArrayInputStream;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class ByteSourceJsonBootstrapper {

   static final byte UTF8_BOM_1 = -17;
   static final byte UTF8_BOM_2 = -69;
   static final byte UTF8_BOM_3 = -65;
   protected boolean _bigEndian = true;
   private final boolean _bufferRecyclable;
   protected int _bytesPerChar = 0;
   protected final IOContext _context;
   protected final InputStream _in;
   protected final byte[] _inputBuffer;
   private int _inputEnd;
   protected int _inputProcessed;
   private int _inputPtr;


   public ByteSourceJsonBootstrapper(IOContext var1, InputStream var2) {
      this._context = var1;
      this._in = var2;
      this._inputBuffer = var1.allocReadIOBuffer();
      this._inputPtr = 0;
      this._inputEnd = 0;
      this._inputProcessed = 0;
      this._bufferRecyclable = true;
   }

   public ByteSourceJsonBootstrapper(IOContext var1, byte[] var2, int var3, int var4) {
      this._context = var1;
      this._in = null;
      this._inputBuffer = var2;
      this._inputPtr = var3;
      this._inputEnd = var4 + var3;
      this._inputProcessed = -var3;
      this._bufferRecyclable = false;
   }

   private boolean checkUTF16(int var1) {
      if(('\uff00' & var1) == 0) {
         this._bigEndian = true;
      } else {
         if((var1 & 255) != 0) {
            return false;
         }

         this._bigEndian = false;
      }

      this._bytesPerChar = 2;
      return true;
   }

   private boolean checkUTF32(int var1) throws IOException {
      if(var1 >> 8 == 0) {
         this._bigEndian = true;
      } else if((16777215 & var1) == 0) {
         this._bigEndian = false;
      } else if((-16711681 & var1) == 0) {
         this.reportWeirdUCS4("3412");
      } else {
         if((var1 & -65281) != 0) {
            return false;
         }

         this.reportWeirdUCS4("2143");
      }

      this._bytesPerChar = 4;
      return true;
   }

   private boolean handleBOM(int var1) throws IOException {
      label35: {
         if(var1 != -16842752) {
            if(var1 == -131072) {
               this._inputPtr += 4;
               this._bytesPerChar = 4;
               this._bigEndian = false;
               return true;
            }

            if(var1 == '\ufeff') {
               this._bigEndian = true;
               this._inputPtr += 4;
               this._bytesPerChar = 4;
               return true;
            }

            if(var1 != '\ufffe') {
               break label35;
            }

            this.reportWeirdUCS4("2143");
         }

         this.reportWeirdUCS4("3412");
      }

      int var2 = var1 >>> 16;
      if(var2 == '\ufeff') {
         this._inputPtr += 2;
         this._bytesPerChar = 2;
         this._bigEndian = true;
         return true;
      } else if(var2 == '\ufffe') {
         this._inputPtr += 2;
         this._bytesPerChar = 2;
         this._bigEndian = false;
         return true;
      } else if(var1 >>> 8 == 15711167) {
         this._inputPtr += 3;
         this._bytesPerChar = 1;
         this._bigEndian = true;
         return true;
      } else {
         return false;
      }
   }

   public static MatchStrength hasJSONFormat(InputAccessor var0) throws IOException {
      if(!var0.hasMoreBytes()) {
         return MatchStrength.INCONCLUSIVE;
      } else {
         byte var2 = var0.nextByte();
         byte var1 = var2;
         if(var2 == -17) {
            if(!var0.hasMoreBytes()) {
               return MatchStrength.INCONCLUSIVE;
            }

            if(var0.nextByte() != -69) {
               return MatchStrength.NO_MATCH;
            }

            if(!var0.hasMoreBytes()) {
               return MatchStrength.INCONCLUSIVE;
            }

            if(var0.nextByte() != -65) {
               return MatchStrength.NO_MATCH;
            }

            if(!var0.hasMoreBytes()) {
               return MatchStrength.INCONCLUSIVE;
            }

            var1 = var0.nextByte();
         }

         int var3 = skipSpace(var0, var1);
         if(var3 < 0) {
            return MatchStrength.INCONCLUSIVE;
         } else if(var3 == 123) {
            var3 = skipSpace(var0);
            return var3 < 0?MatchStrength.INCONCLUSIVE:(var3 != 34 && var3 != 125?MatchStrength.NO_MATCH:MatchStrength.SOLID_MATCH);
         } else if(var3 == 91) {
            var3 = skipSpace(var0);
            return var3 < 0?MatchStrength.INCONCLUSIVE:(var3 != 93 && var3 != 91?MatchStrength.SOLID_MATCH:MatchStrength.SOLID_MATCH);
         } else {
            MatchStrength var4 = MatchStrength.WEAK_MATCH;
            if(var3 == 34) {
               return var4;
            } else if(var3 <= 57 && var3 >= 48) {
               return var4;
            } else if(var3 == 45) {
               var3 = skipSpace(var0);
               return var3 < 0?MatchStrength.INCONCLUSIVE:(var3 <= 57 && var3 >= 48?var4:MatchStrength.NO_MATCH);
            } else {
               return var3 == 110?tryMatch(var0, "ull", var4):(var3 == 116?tryMatch(var0, "rue", var4):(var3 == 102?tryMatch(var0, "alse", var4):MatchStrength.NO_MATCH));
            }
         }
      }
   }

   private void reportWeirdUCS4(String var1) throws IOException {
      StringBuilder var2 = new StringBuilder();
      var2.append("Unsupported UCS-4 endianness (");
      var2.append(var1);
      var2.append(") detected");
      throw new CharConversionException(var2.toString());
   }

   private static int skipSpace(InputAccessor var0) throws IOException {
      return !var0.hasMoreBytes()?-1:skipSpace(var0, var0.nextByte());
   }

   private static int skipSpace(InputAccessor var0, byte var1) throws IOException {
      while(true) {
         int var2 = var1 & 255;
         if(var2 != 32 && var2 != 13 && var2 != 10 && var2 != 9) {
            return var2;
         }

         if(!var0.hasMoreBytes()) {
            return -1;
         }

         var1 = var0.nextByte();
      }
   }

   private static MatchStrength tryMatch(InputAccessor var0, String var1, MatchStrength var2) throws IOException {
      int var4 = var1.length();

      for(int var3 = 0; var3 < var4; ++var3) {
         if(!var0.hasMoreBytes()) {
            return MatchStrength.INCONCLUSIVE;
         }

         if(var0.nextByte() != var1.charAt(var3)) {
            return MatchStrength.NO_MATCH;
         }
      }

      return var2;
   }

   public JsonParser constructParser(int var1, ObjectCodec var2, BytesToNameCanonicalizer var3, CharsToNameCanonicalizer var4, boolean var5, boolean var6) throws IOException, JsonParseException {
      if(this.detectEncoding() == JsonEncoding.UTF8 && var5) {
         var3 = var3.makeChild(var5, var6);
         return new UTF8StreamJsonParser(this._context, var1, this._in, var2, var3, this._inputBuffer, this._inputPtr, this._inputEnd, this._bufferRecyclable);
      } else {
         return new ReaderBasedJsonParser(this._context, var1, this.constructReader(), var2, var4.makeChild(var5, var6));
      }
   }

   public Reader constructReader() throws IOException {
      JsonEncoding var3 = this._context.getEncoding();
      switch(null.$SwitchMap$com$fasterxml$jackson$core$JsonEncoding[var3.ordinal()]) {
      case 1:
      case 2:
         return new UTF32Reader(this._context, this._in, this._inputBuffer, this._inputPtr, this._inputEnd, this._context.getEncoding().isBigEndian());
      case 3:
      case 4:
      case 5:
         InputStream var2 = this._in;
         Object var1;
         if(var2 == null) {
            var1 = new ByteArrayInputStream(this._inputBuffer, this._inputPtr, this._inputEnd);
         } else {
            var1 = var2;
            if(this._inputPtr < this._inputEnd) {
               var1 = new MergedStream(this._context, var2, this._inputBuffer, this._inputPtr, this._inputEnd);
            }
         }

         return new InputStreamReader((InputStream)var1, var3.getJavaName());
      default:
         throw new RuntimeException("Internal error");
      }
   }

   public JsonEncoding detectEncoding() throws IOException, JsonParseException {
      boolean var1;
      label43: {
         boolean var3 = this.ensureLoaded(4);
         var1 = true;
         if(var3) {
            int var2 = this._inputBuffer[this._inputPtr] << 24 | (this._inputBuffer[this._inputPtr + 1] & 255) << 16 | (this._inputBuffer[this._inputPtr + 2] & 255) << 8 | this._inputBuffer[this._inputPtr + 3] & 255;
            if(this.handleBOM(var2) || this.checkUTF32(var2) || this.checkUTF16(var2 >>> 16)) {
               break label43;
            }
         } else if(this.ensureLoaded(2) && this.checkUTF16((this._inputBuffer[this._inputPtr] & 255) << 8 | this._inputBuffer[this._inputPtr + 1] & 255)) {
            break label43;
         }

         var1 = false;
      }

      JsonEncoding var4;
      if(!var1) {
         var4 = JsonEncoding.UTF8;
      } else {
         int var5 = this._bytesPerChar;
         if(var5 != 4) {
            switch(var5) {
            case 1:
               var4 = JsonEncoding.UTF8;
               break;
            case 2:
               if(this._bigEndian) {
                  var4 = JsonEncoding.UTF16_BE;
               } else {
                  var4 = JsonEncoding.UTF16_LE;
               }
               break;
            default:
               throw new RuntimeException("Internal error");
            }
         } else if(this._bigEndian) {
            var4 = JsonEncoding.UTF32_BE;
         } else {
            var4 = JsonEncoding.UTF32_LE;
         }
      }

      this._context.setEncoding(var4);
      return var4;
   }

   protected boolean ensureLoaded(int var1) throws IOException {
      int var3;
      for(int var2 = this._inputEnd - this._inputPtr; var2 < var1; var2 += var3) {
         if(this._in == null) {
            var3 = -1;
         } else {
            var3 = this._in.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
         }

         if(var3 < 1) {
            return false;
         }

         this._inputEnd += var3;
      }

      return true;
   }
}
