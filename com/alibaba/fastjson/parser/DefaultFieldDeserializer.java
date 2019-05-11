package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.DateCodec;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class DefaultFieldDeserializer extends FieldDeserializer {

   protected ObjectDeserializer fieldValueDeserilizer;


   public DefaultFieldDeserializer(ParserConfig var1, Class<?> var2, FieldInfo var3) {
      super(var2, var3, 2);
   }

   public ObjectDeserializer getFieldValueDeserilizer(ParserConfig var1) {
      if(this.fieldValueDeserilizer == null) {
         this.fieldValueDeserilizer = var1.getDeserializer(this.fieldInfo.fieldClass, this.fieldInfo.fieldType);
      }

      return this.fieldValueDeserilizer;
   }

   public void parseField(DefaultJSONParser var1, Object var2, Type var3, Map<String, Object> var4) {
      if(this.fieldValueDeserilizer == null) {
         this.fieldValueDeserilizer = var1.config.getDeserializer(this.fieldInfo.fieldClass, this.fieldInfo.fieldType);
      }

      Type var7 = this.fieldInfo.fieldType;
      boolean var5 = var3 instanceof ParameterizedType;
      Type var6 = var7;
      if(var5) {
         ParseContext var16 = var1.contex;
         if(var16 != null) {
            var16.type = var3;
         }

         var6 = FieldInfo.getFieldType(this.clazz, var3, var7);
         this.fieldValueDeserilizer = var1.config.getDeserializer(var6);
      }

      Object var17 = var6;
      if(var6 instanceof ParameterizedType) {
         var17 = var6;
         if(var5) {
            ParameterizedType var8 = (ParameterizedType)var6;
            ParameterizedType var13 = (ParameterizedType)var3;
            Type[] var9 = var8.getActualTypeArguments();
            Type var10 = var13.getRawType();
            var17 = var6;
            if(var10 instanceof Class) {
               var17 = var6;
               if(TypeUtils.getArgument(var9, ((Class)var10).getTypeParameters(), var13.getActualTypeArguments())) {
                  var17 = new ParameterizedTypeImpl(var9, var8.getOwnerType(), var8.getRawType());
               }
            }
         }
      }

      String var14 = this.fieldInfo.format;
      Object var15;
      if(var14 != null && this.fieldValueDeserilizer instanceof DateCodec) {
         var15 = ((DateCodec)this.fieldValueDeserilizer).deserialze(var1, (Type)var17, this.fieldInfo.name, var14);
      } else {
         var15 = this.fieldValueDeserilizer.deserialze(var1, (Type)var17, this.fieldInfo.name);
      }

      if(var1.resolveStatus == 1) {
         DefaultJSONParser.ResolveTask var12 = var1.getLastResolveTask();
         var12.fieldDeserializer = this;
         var12.ownerContext = var1.contex;
         var1.resolveStatus = 0;
      } else if(var2 == null) {
         var4.put(this.fieldInfo.name, var15);
      } else {
         if(var15 == null) {
            Class var11 = this.fieldInfo.fieldClass;
            if(var11 == Byte.TYPE || var11 == Short.TYPE || var11 == Float.TYPE || var11 == Double.TYPE) {
               return;
            }
         }

         this.setValue(var2, var15);
      }
   }
}
