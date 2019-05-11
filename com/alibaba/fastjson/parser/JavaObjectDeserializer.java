package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;

class JavaObjectDeserializer implements ObjectDeserializer {

   public static final JavaObjectDeserializer instance = new JavaObjectDeserializer();


   public <T extends Object> T deserialze(DefaultJSONParser var1, Type var2, Object var3) {
      if(var2 instanceof GenericArrayType) {
         Type var5 = ((GenericArrayType)var2).getGenericComponentType();
         var2 = var5;
         if(var5 instanceof TypeVariable) {
            var2 = ((TypeVariable)var5).getBounds()[0];
         }

         ArrayList var6 = new ArrayList();
         var1.parseArray(var2, (Collection)var6);
         if(var2 instanceof Class) {
            Object[] var4 = (Object[])Array.newInstance((Class)var2, var6.size());
            var6.toArray(var4);
            return var4;
         } else {
            return var6.toArray();
         }
      } else {
         return var1.parse(var3);
      }
   }
}
