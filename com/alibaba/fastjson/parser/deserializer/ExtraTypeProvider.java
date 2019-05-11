package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import java.lang.reflect.Type;

public interface ExtraTypeProvider extends ParseProcess {

   Type getExtraType(Object var1, String var2);
}
