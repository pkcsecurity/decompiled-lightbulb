package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;

public final class ArrayCodec implements ObjectDeserializer, ObjectSerializer {

   public static final ArrayCodec instance = new ArrayCodec();


   private <T extends Object> T toObjectArray(DefaultJSONParser var1, Class<?> var2, JSONArray var3) {
      if(var3 == null) {
         return null;
      } else {
         int var5 = var3.size();
         Object var7 = Array.newInstance(var2, var5);

         for(int var4 = 0; var4 < var5; ++var4) {
            Object var6 = var3.get(var4);
            if(var6 == var3) {
               Array.set(var7, var4, var7);
            } else {
               if(var2.isArray()) {
                  if(!var2.isInstance(var6)) {
                     var6 = this.toObjectArray(var1, var2, (JSONArray)var6);
                  }
               } else {
                  var6 = TypeUtils.cast(var6, var2, var1.config);
               }

               Array.set(var7, var4, var6);
            }
         }

         var3.setRelatedArray(var7);
         var3.setComponentType(var2);
         return var7;
      }
   }

   public <T extends Object> T deserialze(DefaultJSONParser var1, Type var2, Object var3) {
      JSONLexer var5 = var1.lexer;
      int var4 = var5.token();
      if(var4 == 8) {
         var5.nextToken(16);
         return null;
      } else if(var2 == char[].class) {
         if(var4 == 4) {
            String var8 = var5.stringVal();
            var5.nextToken(16);
            return var8.toCharArray();
         } else if(var4 == 2) {
            Number var7 = var5.integerValue();
            var5.nextToken(16);
            return var7.toString().toCharArray();
         } else {
            return JSON.toJSONString(var1.parse()).toCharArray();
         }
      } else if(var4 == 4) {
         byte[] var6 = var5.bytesValue();
         var5.nextToken(16);
         return var6;
      } else {
         Class var9 = ((Class)var2).getComponentType();
         JSONArray var10 = new JSONArray();
         var1.parseArray(var9, var10, var3);
         return this.toObjectArray(var1, var9, var10);
      }
   }

   public final void write(JSONSerializer param1, Object param2, Object param3, Type param4) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
