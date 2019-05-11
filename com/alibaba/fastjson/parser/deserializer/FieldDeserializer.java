package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

public abstract class FieldDeserializer {

   public final Class<?> clazz;
   protected long[] enumNameHashCodes;
   protected Enum[] enums;
   public final FieldInfo fieldInfo;


   public FieldDeserializer(Class<?> var1, FieldInfo var2, int var3) {
      this.clazz = var1;
      this.fieldInfo = var2;
      if(var2 != null) {
         var1 = var2.fieldClass;
         if(var1.isEnum()) {
            Enum[] var8 = (Enum[])var1.getEnumConstants();
            long[] var9 = new long[var8.length];
            this.enumNameHashCodes = new long[var8.length];

            int var4;
            for(var3 = 0; var3 < var8.length; ++var3) {
               String var7 = var8[var3].name();
               long var5 = -3750763034362895579L;

               for(var4 = 0; var4 < var7.length(); ++var4) {
                  var5 = 1099511628211L * (var5 ^ (long)var7.charAt(var4));
               }

               var9[var3] = var5;
               this.enumNameHashCodes[var3] = var5;
            }

            Arrays.sort(this.enumNameHashCodes);
            this.enums = new Enum[var8.length];

            for(var3 = 0; var3 < this.enumNameHashCodes.length; ++var3) {
               for(var4 = 0; var4 < var9.length; ++var4) {
                  if(this.enumNameHashCodes[var3] == var9[var4]) {
                     this.enums[var3] = var8[var4];
                     break;
                  }
               }
            }
         }

      }
   }

   public Enum getEnumByHashCode(long var1) {
      if(this.enums == null) {
         return null;
      } else {
         int var3 = Arrays.binarySearch(this.enumNameHashCodes, var1);
         return var3 < 0?null:this.enums[var3];
      }
   }

   public abstract void parseField(DefaultJSONParser var1, Object var2, Type var3, Map<String, Object> var4);

   public void setValue(Object var1, double var2) throws IllegalAccessException {
      this.fieldInfo.field.setDouble(var1, var2);
   }

   public void setValue(Object var1, float var2) throws IllegalAccessException {
      this.fieldInfo.field.setFloat(var1, var2);
   }

   public void setValue(Object var1, int var2) throws IllegalAccessException {
      this.fieldInfo.field.setInt(var1, var2);
   }

   public void setValue(Object var1, long var2) throws IllegalAccessException {
      this.fieldInfo.field.setLong(var1, var2);
   }

   public void setValue(Object param1, Object param2) {
      // $FF: Couldn't be decompiled
   }
}
