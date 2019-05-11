package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.lang.reflect.Type;

class EnumSerializer implements ObjectSerializer {

   public void write(JSONSerializer var1, Object var2, Object var3, Type var4) throws IOException {
      SerializeWriter var6 = var1.out;
      if((var6.features & SerializerFeature.WriteEnumUsingToString.mask) != 0) {
         String var7 = ((Enum)var2).toString();
         boolean var5;
         if((var6.features & SerializerFeature.UseSingleQuotes.mask) != 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         if(var5) {
            var6.writeStringWithSingleQuote(var7);
         } else {
            var6.writeStringWithDoubleQuote(var7, '\u0000', false);
         }
      } else {
         var6.writeInt(((Enum)var2).ordinal());
      }
   }
}
