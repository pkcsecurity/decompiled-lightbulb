package com.alibaba.fastjson;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamContext;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class JSONWriter implements Closeable, Flushable {

   private JSONStreamContext context;
   private JSONSerializer serializer;
   private SerializeWriter writer;


   public JSONWriter(Writer var1) {
      this.writer = new SerializeWriter(var1);
      this.serializer = new JSONSerializer(this.writer);
   }

   private void afterWriter() {
      if(this.context != null) {
         short var1;
         switch(this.context.state) {
         case 1001:
         case 1003:
            var1 = 1002;
            break;
         case 1002:
            var1 = 1003;
            break;
         case 1004:
            var1 = 1005;
            break;
         case 1005:
         default:
            var1 = -1;
         }

         if(var1 != -1) {
            this.context.state = var1;
         }

      }
   }

   private void beforeWrite() {
      if(this.context != null) {
         switch(this.context.state) {
         case 1002:
            this.writer.write(58);
         case 1001:
         case 1004:
            return;
         case 1003:
            this.writer.write(44);
            return;
         case 1005:
            this.writer.write(44);
            return;
         default:
         }
      }
   }

   private void beginStructure() {
      int var1 = this.context.state;
      switch(this.context.state) {
      case 1002:
         this.writer.write(58);
      case 1001:
      case 1004:
         return;
      case 1003:
      default:
         StringBuilder var2 = new StringBuilder();
         var2.append("illegal state : ");
         var2.append(var1);
         throw new JSONException(var2.toString());
      case 1005:
         this.writer.write(44);
      }
   }

   private void endStructure() {
      this.context = this.context.parent;
      if(this.context != null) {
         short var1;
         switch(this.context.state) {
         case 1001:
            var1 = 1002;
            break;
         case 1002:
            var1 = 1003;
            break;
         case 1003:
         case 1005:
         default:
            var1 = -1;
            break;
         case 1004:
            var1 = 1005;
         }

         if(var1 != -1) {
            this.context.state = var1;
         }

      }
   }

   public void close() throws IOException {
      this.writer.close();
   }

   public void config(SerializerFeature var1, boolean var2) {
      this.writer.config(var1, var2);
   }

   public void endArray() {
      this.writer.write(93);
      this.endStructure();
   }

   public void endObject() {
      this.writer.write(125);
      this.endStructure();
   }

   public void flush() throws IOException {
      this.writer.flush();
   }

   public void startArray() {
      if(this.context != null) {
         this.beginStructure();
      }

      this.context = new JSONStreamContext(this.context, 1004);
      this.writer.write(91);
   }

   public void startObject() {
      if(this.context != null) {
         this.beginStructure();
      }

      this.context = new JSONStreamContext(this.context, 1001);
      this.writer.write(123);
   }

   public void writeKey(String var1) {
      this.writeObject(var1);
   }

   public void writeObject(Object var1) {
      this.beforeWrite();
      this.serializer.write(var1);
      this.afterWriter();
   }

   public void writeObject(String var1) {
      this.beforeWrite();
      this.serializer.write(var1);
      this.afterWriter();
   }

   public void writeValue(Object var1) {
      this.writeObject(var1);
   }
}
