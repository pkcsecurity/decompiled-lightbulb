package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;

public final class BooleanCodec implements ObjectDeserializer, ObjectSerializer {

   public static final BooleanCodec instance = new BooleanCodec();


   public <T extends Object> T deserialze(DefaultJSONParser var1, Type var2, Object var3) {
      JSONLexer var6 = var1.lexer;
      int var4 = var6.token();
      if(var4 == 6) {
         var6.nextToken(16);
         return Boolean.TRUE;
      } else if(var4 == 7) {
         var6.nextToken(16);
         return Boolean.FALSE;
      } else if(var4 == 2) {
         var4 = var6.intValue();
         var6.nextToken(16);
         return var4 == 1?Boolean.TRUE:Boolean.FALSE;
      } else {
         Object var5 = var1.parse();
         return var5 == null?null:TypeUtils.castToBoolean(var5);
      }
   }

   public void write(JSONSerializer var1, Object var2, Object var3, Type var4) throws IOException {
      SerializeWriter var5 = var1.out;
      Boolean var6 = (Boolean)var2;
      if(var6 == null) {
         if((var5.features & SerializerFeature.WriteNullBooleanAsFalse.mask) != 0) {
            var5.write("false");
         } else {
            var5.writeNull();
         }
      } else if(var6.booleanValue()) {
         var5.write("true");
      } else {
         var5.write("false");
      }
   }
}
