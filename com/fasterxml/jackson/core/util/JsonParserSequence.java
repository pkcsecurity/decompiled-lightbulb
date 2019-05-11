package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParserSequence extends JsonParserDelegate {

   protected int _nextParser;
   protected final JsonParser[] _parsers;


   protected JsonParserSequence(JsonParser[] var1) {
      super(var1[0]);
      this._parsers = var1;
      this._nextParser = 1;
   }

   public static JsonParserSequence createFlattened(JsonParser var0, JsonParser var1) {
      boolean var2 = var0 instanceof JsonParserSequence;
      if(!var2 && !(var1 instanceof JsonParserSequence)) {
         return new JsonParserSequence(new JsonParser[]{var0, var1});
      } else {
         ArrayList var3 = new ArrayList();
         if(var2) {
            ((JsonParserSequence)var0).addFlattenedActiveParsers(var3);
         } else {
            var3.add(var0);
         }

         if(var1 instanceof JsonParserSequence) {
            ((JsonParserSequence)var1).addFlattenedActiveParsers(var3);
         } else {
            var3.add(var1);
         }

         return new JsonParserSequence((JsonParser[])var3.toArray(new JsonParser[var3.size()]));
      }
   }

   protected void addFlattenedActiveParsers(List<JsonParser> var1) {
      int var2 = this._nextParser - 1;

      for(int var3 = this._parsers.length; var2 < var3; ++var2) {
         JsonParser var4 = this._parsers[var2];
         if(var4 instanceof JsonParserSequence) {
            ((JsonParserSequence)var4).addFlattenedActiveParsers(var1);
         } else {
            var1.add(var4);
         }
      }

   }

   public void close() throws IOException {
      do {
         this.delegate.close();
      } while(this.switchToNext());

   }

   public int containedParsersCount() {
      return this._parsers.length;
   }

   public JsonToken nextToken() throws IOException, JsonParseException {
      JsonToken var1 = this.delegate.nextToken();
      if(var1 != null) {
         return var1;
      } else {
         do {
            if(!this.switchToNext()) {
               return null;
            }

            var1 = this.delegate.nextToken();
         } while(var1 == null);

         return var1;
      }
   }

   protected boolean switchToNext() {
      if(this._nextParser >= this._parsers.length) {
         return false;
      } else {
         JsonParser[] var2 = this._parsers;
         int var1 = this._nextParser;
         this._nextParser = var1 + 1;
         this.delegate = var2[var1];
         return true;
      }
   }
}
