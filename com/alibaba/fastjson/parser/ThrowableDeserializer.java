package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.ParserConfig;
import java.lang.reflect.Type;

public class ThrowableDeserializer extends JavaBeanDeserializer {

   public ThrowableDeserializer(ParserConfig var1, Class<?> var2) {
      super(var1, var2, var2);
   }

   public <T extends Object> T deserialze(DefaultJSONParser param1, Type param2, Object param3) {
      // $FF: Couldn't be decompiled
   }
}
