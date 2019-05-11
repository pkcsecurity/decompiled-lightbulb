package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import java.lang.reflect.Type;

public interface FieldTypeResolver extends ParseProcess {

   Type resolve(Object var1, String var2);
}
