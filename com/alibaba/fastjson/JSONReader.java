package com.alibaba.fastjson;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamContext;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

public class JSONReader implements Closeable {

   private JSONStreamContext context;
   private final DefaultJSONParser parser;
   private Reader reader;


   public JSONReader(DefaultJSONParser var1) {
      this.parser = var1;
   }

   public JSONReader(JSONLexer var1) {
      this(new DefaultJSONParser(var1));
   }

   public JSONReader(Reader var1) {
      this(new JSONLexer(readAll(var1)));
      this.reader = var1;
   }

   private void endStructure() {
      this.context = this.context.parent;
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
         default:
            var1 = -1;
         }

         if(var1 != -1) {
            this.context.state = var1;
         }

      }
   }

   private void readAfter() {
      int var2 = this.context.state;
      short var1 = 1002;
      switch(var2) {
      case 1001:
      case 1003:
         break;
      case 1002:
         var1 = 1003;
         break;
      case 1004:
         var1 = 1005;
         break;
      case 1005:
         var1 = -1;
         break;
      default:
         StringBuilder var3 = new StringBuilder();
         var3.append("illegal state : ");
         var3.append(var2);
         throw new JSONException(var3.toString());
      }

      if(var1 != -1) {
         this.context.state = var1;
      }

   }

   static String readAll(Reader param0) {
      // $FF: Couldn't be decompiled
   }

   private void readBefore() {
      int var1 = this.context.state;
      switch(var1) {
      case 1002:
         this.parser.accept(17);
      case 1001:
      case 1004:
         return;
      case 1003:
      case 1005:
         this.parser.accept(16);
         return;
      default:
         StringBuilder var2 = new StringBuilder();
         var2.append("illegal state : ");
         var2.append(var1);
         throw new JSONException(var2.toString());
      }
   }

   private void startStructure() {
      switch(this.context.state) {
      case 1002:
         this.parser.accept(17);
      case 1001:
      case 1004:
         return;
      case 1003:
      case 1005:
         this.parser.accept(16);
         return;
      default:
         StringBuilder var1 = new StringBuilder();
         var1.append("illegal state : ");
         var1.append(this.context.state);
         throw new JSONException(var1.toString());
      }
   }

   public void close() {
      this.parser.lexer.close();
      if(this.reader != null) {
         try {
            this.reader.close();
         } catch (IOException var2) {
            throw new JSONException("closed reader error", var2);
         }
      }
   }

   public void config(Feature var1, boolean var2) {
      this.parser.config(var1, var2);
   }

   public void endArray() {
      this.parser.accept(15);
      this.endStructure();
   }

   public void endObject() {
      this.parser.accept(13);
      this.endStructure();
   }

   public boolean hasNext() {
      if(this.context == null) {
         throw new JSONException("context is null");
      } else {
         int var1 = this.parser.lexer.token();
         int var2 = this.context.state;
         boolean var4 = false;
         boolean var3 = false;
         switch(var2) {
         case 1001:
         case 1003:
            var3 = var4;
            if(var1 != 13) {
               var3 = true;
            }

            return var3;
         case 1002:
         default:
            StringBuilder var5 = new StringBuilder();
            var5.append("illegal state : ");
            var5.append(var2);
            throw new JSONException(var5.toString());
         case 1004:
         case 1005:
            if(var1 != 15) {
               var3 = true;
            }

            return var3;
         }
      }
   }

   public int peek() {
      return this.parser.lexer.token();
   }

   public Integer readInteger() {
      Object var1;
      if(this.context == null) {
         var1 = this.parser.parse();
      } else {
         this.readBefore();
         var1 = this.parser.parse();
         this.readAfter();
      }

      return TypeUtils.castToInt(var1);
   }

   public Long readLong() {
      Object var1;
      if(this.context == null) {
         var1 = this.parser.parse();
      } else {
         this.readBefore();
         var1 = this.parser.parse();
         this.readAfter();
      }

      return TypeUtils.castToLong(var1);
   }

   public Object readObject() {
      if(this.context == null) {
         return this.parser.parse();
      } else {
         this.readBefore();
         Object var1 = this.parser.parse();
         this.readAfter();
         return var1;
      }
   }

   public <T extends Object> T readObject(TypeReference<T> var1) {
      return this.readObject(var1.type);
   }

   public <T extends Object> T readObject(Class<T> var1) {
      if(this.context == null) {
         return this.parser.parseObject(var1);
      } else {
         this.readBefore();
         Object var2 = this.parser.parseObject(var1);
         this.readAfter();
         return var2;
      }
   }

   public <T extends Object> T readObject(Type var1) {
      if(this.context == null) {
         return this.parser.parseObject(var1);
      } else {
         this.readBefore();
         Object var2 = this.parser.parseObject(var1);
         this.readAfter();
         return var2;
      }
   }

   public Object readObject(Map var1) {
      if(this.context == null) {
         return this.parser.parseObject(var1);
      } else {
         this.readBefore();
         Object var2 = this.parser.parseObject(var1);
         this.readAfter();
         return var2;
      }
   }

   public void readObject(Object var1) {
      if(this.context == null) {
         this.parser.parseObject(var1);
      } else {
         this.readBefore();
         this.parser.parseObject(var1);
         this.readAfter();
      }
   }

   public String readString() {
      Object var1;
      if(this.context == null) {
         var1 = this.parser.parse();
      } else {
         this.readBefore();
         var1 = this.parser.parse();
         this.readAfter();
      }

      return TypeUtils.castToString(var1);
   }

   public void startArray() {
      if(this.context == null) {
         this.context = new JSONStreamContext((JSONStreamContext)null, 1004);
      } else {
         this.startStructure();
         this.context = new JSONStreamContext(this.context, 1004);
      }

      this.parser.accept(14);
   }

   public void startObject() {
      if(this.context == null) {
         this.context = new JSONStreamContext((JSONStreamContext)null, 1001);
      } else {
         this.startStructure();
         this.context = new JSONStreamContext(this.context, 1001);
      }

      this.parser.accept(12);
   }
}
