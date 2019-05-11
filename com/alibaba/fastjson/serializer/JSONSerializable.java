package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

public interface JSONSerializable {

   void write(JSONSerializer var1, Object var2, Type var3) throws IOException;
}
