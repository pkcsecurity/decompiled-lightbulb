package com.facebook.react.bridge;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.Deque;

public class JsonWriter implements Closeable {

   private final Deque<JsonWriter.Scope> mScopes;
   private final Writer mWriter;


   public JsonWriter(Writer var1) {
      this.mWriter = var1;
      this.mScopes = new ArrayDeque();
   }

   private void beforeName() throws IOException {
      JsonWriter.Scope var1 = (JsonWriter.Scope)this.mScopes.peek();
      switch(null.$SwitchMap$com$facebook$react$bridge$JsonWriter$Scope[var1.ordinal()]) {
      case 1:
      case 3:
         throw new IllegalStateException("name not allowed in array");
      case 2:
         this.replace(JsonWriter.Scope.OBJECT);
         return;
      case 4:
         this.mWriter.write(44);
         return;
      default:
         StringBuilder var2 = new StringBuilder();
         var2.append("Unknown scope: ");
         var2.append(var1);
         throw new IllegalStateException(var2.toString());
      }
   }

   private void beforeValue() throws IOException {
      JsonWriter.Scope var1 = (JsonWriter.Scope)this.mScopes.peek();
      switch(null.$SwitchMap$com$facebook$react$bridge$JsonWriter$Scope[var1.ordinal()]) {
      case 1:
         this.replace(JsonWriter.Scope.ARRAY);
      case 2:
         throw new IllegalArgumentException(JsonWriter.Scope.EMPTY_OBJECT.name());
      case 3:
         this.mWriter.write(44);
         return;
      case 4:
         return;
      default:
         StringBuilder var2 = new StringBuilder();
         var2.append("Unknown scope: ");
         var2.append(var1);
         throw new IllegalStateException(var2.toString());
      }
   }

   private void close(char var1) throws IOException {
      this.mScopes.pop();
      this.mWriter.write(var1);
   }

   private void open(JsonWriter.Scope var1, char var2) throws IOException {
      this.mScopes.push(var1);
      this.mWriter.write(var2);
   }

   private void replace(JsonWriter.Scope var1) {
      this.mScopes.pop();
      this.mScopes.push(var1);
   }

   private void string(String var1) throws IOException {
      this.mWriter.write(34);
      int var3 = var1.length();

      for(int var2 = 0; var2 < var3; ++var2) {
         char var4 = var1.charAt(var2);
         switch(var4) {
         case 8:
            this.mWriter.write("\\b");
            break;
         case 9:
            this.mWriter.write("\\t");
            break;
         case 10:
            this.mWriter.write("\\n");
            break;
         case 12:
            this.mWriter.write("\\f");
            break;
         case 13:
            this.mWriter.write("\\r");
            break;
         case 34:
         case 92:
            this.mWriter.write(92);
            this.mWriter.write(var4);
            break;
         case 8232:
         case 8233:
            this.mWriter.write(String.format("\\u%04x", new Object[]{Integer.valueOf(var4)}));
            break;
         default:
            if(var4 <= 31) {
               this.mWriter.write(String.format("\\u%04x", new Object[]{Integer.valueOf(var4)}));
            } else {
               this.mWriter.write(var4);
            }
         }
      }

      this.mWriter.write(34);
   }

   public JsonWriter beginArray() throws IOException {
      this.open(JsonWriter.Scope.EMPTY_ARRAY, '[');
      return this;
   }

   public JsonWriter beginObject() throws IOException {
      this.open(JsonWriter.Scope.EMPTY_OBJECT, '{');
      return this;
   }

   public void close() throws IOException {
      this.mWriter.close();
   }

   public JsonWriter endArray() throws IOException {
      this.close(']');
      return this;
   }

   public JsonWriter endObject() throws IOException {
      this.close('}');
      return this;
   }

   public JsonWriter name(String var1) throws IOException {
      if(var1 == null) {
         throw new NullPointerException("name can not be null");
      } else {
         this.beforeName();
         this.string(var1);
         this.mWriter.write(58);
         return this;
      }
   }

   public JsonWriter nullValue() throws IOException {
      this.beforeValue();
      this.mWriter.write("null");
      return this;
   }

   public JsonWriter rawValue(String var1) throws IOException {
      this.beforeValue();
      this.mWriter.write(var1);
      return this;
   }

   public JsonWriter value(double var1) throws IOException {
      this.beforeValue();
      this.mWriter.append(Double.toString(var1));
      return this;
   }

   public JsonWriter value(long var1) throws IOException {
      this.beforeValue();
      this.mWriter.write(Long.toString(var1));
      return this;
   }

   public JsonWriter value(Number var1) throws IOException {
      if(var1 == null) {
         return this.nullValue();
      } else {
         this.beforeValue();
         this.mWriter.append(var1.toString());
         return this;
      }
   }

   public JsonWriter value(String var1) throws IOException {
      if(var1 == null) {
         return this.nullValue();
      } else {
         this.beforeValue();
         this.string(var1);
         return this;
      }
   }

   public JsonWriter value(boolean var1) throws IOException {
      this.beforeValue();
      Writer var3 = this.mWriter;
      String var2;
      if(var1) {
         var2 = "true";
      } else {
         var2 = "false";
      }

      var3.write(var2);
      return this;
   }

   static enum Scope {

      // $FF: synthetic field
      private static final JsonWriter.Scope[] $VALUES = new JsonWriter.Scope[]{EMPTY_OBJECT, OBJECT, EMPTY_ARRAY, ARRAY};
      ARRAY("ARRAY", 3),
      EMPTY_ARRAY("EMPTY_ARRAY", 2),
      EMPTY_OBJECT("EMPTY_OBJECT", 0),
      OBJECT("OBJECT", 1);


      private Scope(String var1, int var2) {}
   }
}
