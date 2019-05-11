package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

final class ArraySerializer implements ObjectSerializer {

   private final ObjectSerializer compObjectSerializer;
   private final Class<?> componentType;


   ArraySerializer(Class<?> var1, ObjectSerializer var2) {
      this.componentType = var1;
      this.compObjectSerializer = var2;
   }

   public final void write(JSONSerializer param1, Object param2, Object param3, Type param4) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
