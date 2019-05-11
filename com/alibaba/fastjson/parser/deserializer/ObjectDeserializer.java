package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;

public interface ObjectDeserializer {

   <T extends Object> T deserialze(DefaultJSONParser var1, Type var2, Object var3);
}
