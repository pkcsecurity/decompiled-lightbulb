package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.lang.reflect.Type;

public final class IntegerCodec implements ObjectDeserializer, ObjectSerializer {

   public static IntegerCodec instance = new IntegerCodec();


   public <T extends Object> T deserialze(DefaultJSONParser param1, Type param2, Object param3) {
      // $FF: Couldn't be decompiled
   }

   public void write(JSONSerializer var1, Object var2, Object var3, Type var4) throws IOException {
      SerializeWriter var7 = var1.out;
      Number var9 = (Number)var2;
      if(var9 == null) {
         if((var7.features & SerializerFeature.WriteNullNumberAsZero.mask) != 0) {
            var7.write(48);
         } else {
            var7.writeNull();
         }
      } else {
         if(var2 instanceof Long) {
            var7.writeLong(var9.longValue());
         } else {
            var7.writeInt(var9.intValue());
         }

         if((var7.features & SerializerFeature.WriteClassName.mask) != 0) {
            Class var8 = var9.getClass();
            if(var8 == Byte.class) {
               var7.write(66);
               return;
            }

            if(var8 == Short.class) {
               var7.write(83);
               return;
            }

            if(var8 == Long.class) {
               long var5 = var9.longValue();
               if(var5 <= 2147483647L && var5 >= -2147483648L && var4 != Long.class) {
                  var7.write(76);
               }
            }
         }

      }
   }
}
