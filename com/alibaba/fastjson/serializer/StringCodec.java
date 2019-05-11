package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class StringCodec implements ObjectDeserializer, ObjectSerializer {

   public static StringCodec instance = new StringCodec();


   public <T extends Object> T deserialze(DefaultJSONParser var1, Type var2, Object var3) {
      return var1.parseString();
   }

   public void write(JSONSerializer var1, Object var2, Object var3, Type var4) throws IOException {
      String var6 = (String)var2;
      SerializeWriter var5 = var1.out;
      if(var6 == null) {
         var5.writeNull();
      } else {
         var5.writeString(var6);
      }
   }
}
