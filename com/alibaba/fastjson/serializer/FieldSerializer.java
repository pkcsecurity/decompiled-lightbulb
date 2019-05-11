package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.FieldInfo;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.Collection;

public final class FieldSerializer implements Comparable<FieldSerializer> {

   protected final int features;
   public final FieldInfo fieldInfo;
   protected final String format;
   protected char[] name_chars;
   private FieldSerializer.RuntimeSerializerInfo runtimeInfo;
   protected final boolean writeNull;


   public FieldSerializer(FieldInfo var1) {
      this.fieldInfo = var1;
      JSONField var7 = var1.getAnnotation();
      SerializerFeature[] var6 = null;
      String var5 = null;
      int var2;
      boolean var4;
      if(var7 != null) {
         var6 = var7.serialzeFeatures();
         int var3 = var6.length;
         var2 = 0;

         for(var4 = false; var2 < var3; ++var2) {
            if(var6[var2] == SerializerFeature.WriteMapNullValue) {
               var4 = true;
            }
         }

         String var9 = var7.format().trim();
         if(var9.length() != 0) {
            var5 = var9;
         }

         this.features = SerializerFeature.of(var7.serialzeFeatures());
      } else {
         this.features = 0;
         var4 = false;
         var5 = var6;
      }

      this.writeNull = var4;
      this.format = var5;
      String var8 = var1.name;
      var2 = var8.length();
      this.name_chars = new char[var2 + 3];
      var8.getChars(0, var8.length(), this.name_chars, 1);
      this.name_chars[0] = 34;
      this.name_chars[var2 + 1] = 34;
      this.name_chars[var2 + 2] = 58;
   }

   public int compareTo(FieldSerializer var1) {
      return this.fieldInfo.compareTo(var1.fieldInfo);
   }

   public Object getPropertyValue(Object var1) throws Exception {
      try {
         var1 = this.fieldInfo.get(var1);
         return var1;
      } catch (Exception var4) {
         if(this.fieldInfo.method != null) {
            var1 = this.fieldInfo.method;
         } else {
            var1 = this.fieldInfo.field;
         }

         Member var5 = (Member)var1;
         StringBuilder var3 = new StringBuilder();
         var3.append(var5.getDeclaringClass().getName());
         var3.append(".");
         var3.append(var5.getName());
         String var6 = var3.toString();
         var3 = new StringBuilder();
         var3.append("get property error。 ");
         var3.append(var6);
         throw new JSONException(var3.toString(), var4);
      }
   }

   public void writePrefix(JSONSerializer var1) throws IOException {
      SerializeWriter var3 = var1.out;
      int var2 = var3.features;
      if((SerializerFeature.QuoteFieldNames.mask & var2) != 0) {
         if((var2 & SerializerFeature.UseSingleQuotes.mask) != 0) {
            var3.writeFieldName(this.fieldInfo.name, true);
         } else {
            var3.write(this.name_chars, 0, this.name_chars.length);
         }
      } else {
         var3.writeFieldName(this.fieldInfo.name, true);
      }
   }

   public void writeValue(JSONSerializer var1, Object var2) throws Exception {
      if(this.format != null) {
         var1.writeWithFormat(var2, this.format);
      } else {
         if(this.runtimeInfo == null) {
            Class var3;
            if(var2 == null) {
               var3 = this.fieldInfo.fieldClass;
            } else {
               var3 = var2.getClass();
            }

            this.runtimeInfo = new FieldSerializer.RuntimeSerializerInfo(var1.config.get(var3), var3);
         }

         FieldSerializer.RuntimeSerializerInfo var5 = this.runtimeInfo;
         if(var2 == null) {
            if((this.features & SerializerFeature.WriteNullNumberAsZero.mask) != 0 && Number.class.isAssignableFrom(var5.runtimeFieldClass)) {
               var1.out.write(48);
            } else if((this.features & SerializerFeature.WriteNullBooleanAsFalse.mask) != 0 && Boolean.class == var5.runtimeFieldClass) {
               var1.out.write("false");
            } else if((this.features & SerializerFeature.WriteNullListAsEmpty.mask) != 0 && Collection.class.isAssignableFrom(var5.runtimeFieldClass)) {
               var1.out.write("[]");
            } else {
               var5.fieldSerializer.write(var1, (Object)null, this.fieldInfo.name, var5.runtimeFieldClass);
            }
         } else {
            Class var4 = var2.getClass();
            if(var4 == var5.runtimeFieldClass) {
               var5.fieldSerializer.write(var1, var2, this.fieldInfo.name, this.fieldInfo.fieldType);
            } else {
               var1.config.get(var4).write(var1, var2, this.fieldInfo.name, this.fieldInfo.fieldType);
            }
         }
      }
   }

   static class RuntimeSerializerInfo {

      ObjectSerializer fieldSerializer;
      Class<?> runtimeFieldClass;


      public RuntimeSerializerInfo(ObjectSerializer var1, Class<?> var2) {
         this.fieldSerializer = var1;
         this.runtimeFieldClass = var2;
      }
   }
}
