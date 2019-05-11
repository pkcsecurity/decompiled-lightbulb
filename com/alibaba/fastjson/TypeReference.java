package com.alibaba.fastjson;

import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TypeReference<T extends Object> {

   static ConcurrentMap<Type, Type> classTypeCache = new ConcurrentHashMap(16, 0.75F, 1);
   protected final Type type;


   protected TypeReference() {
      Type var3 = ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
      if(var3 instanceof Class) {
         this.type = var3;
      } else {
         Type var2 = (Type)classTypeCache.get(var3);
         Type var1 = var2;
         if(var2 == null) {
            classTypeCache.putIfAbsent(var3, var3);
            var1 = (Type)classTypeCache.get(var3);
         }

         this.type = var1;
      }
   }

   protected TypeReference(Type ... var1) {
      Class var5 = this.getClass();
      Type[] var6 = ((ParameterizedType)var5.getGenericSuperclass()).getActualTypeArguments();
      int var2 = 0;
      ParameterizedType var7 = (ParameterizedType)var6[0];
      Type var10 = var7.getRawType();
      Type[] var12 = var7.getActualTypeArguments();

      int var4;
      for(int var3 = 0; var2 < var12.length; var3 = var4) {
         var4 = var3;
         if(var12[var2] instanceof TypeVariable) {
            var4 = var3 + 1;
            var12[var2] = var1[var3];
            if(var4 >= var1.length) {
               break;
            }
         }

         ++var2;
      }

      ParameterizedTypeImpl var11 = new ParameterizedTypeImpl(var12, var5, var10);
      Type var9 = (Type)classTypeCache.get(var11);
      Type var8 = var9;
      if(var9 == null) {
         classTypeCache.putIfAbsent(var11, var11);
         var8 = (Type)classTypeCache.get(var11);
      }

      this.type = var8;
   }

   public Type getType() {
      return this.type;
   }
}
