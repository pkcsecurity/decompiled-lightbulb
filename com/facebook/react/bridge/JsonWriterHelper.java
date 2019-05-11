package com.facebook.react.bridge;

import com.facebook.react.bridge.JsonWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class JsonWriterHelper {

   private static void listValue(JsonWriter var0, List<?> var1) throws IOException {
      var0.beginArray();
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         objectValue(var0, var2.next());
      }

      var0.endArray();
   }

   private static void mapValue(JsonWriter var0, Map<?, ?> var1) throws IOException {
      var0.beginObject();
      Iterator var3 = var1.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var2 = (Entry)var3.next();
         var0.name(var2.getKey().toString());
         value(var0, var2.getValue());
      }

      var0.endObject();
   }

   private static void objectValue(JsonWriter var0, Object var1) throws IOException {
      if(var1 == null) {
         var0.nullValue();
      } else if(var1 instanceof String) {
         var0.value((String)var1);
      } else if(var1 instanceof Number) {
         var0.value((Number)var1);
      } else if(var1 instanceof Boolean) {
         var0.value(((Boolean)var1).booleanValue());
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Unknown value: ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public static void value(JsonWriter var0, Object var1) throws IOException {
      if(var1 instanceof Map) {
         mapValue(var0, (Map)var1);
      } else if(var1 instanceof List) {
         listValue(var0, (List)var1);
      } else {
         objectValue(var0, var1);
      }
   }
}
