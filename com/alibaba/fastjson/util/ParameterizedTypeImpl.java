package com.alibaba.fastjson.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class ParameterizedTypeImpl implements ParameterizedType {

   private final Type[] actualTypeArguments;
   private final Type ownerType;
   private final Type rawType;


   public ParameterizedTypeImpl(Type[] var1, Type var2, Type var3) {
      this.actualTypeArguments = var1;
      this.ownerType = var2;
      this.rawType = var3;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            ParameterizedTypeImpl var2 = (ParameterizedTypeImpl)var1;
            if(!Arrays.equals(this.actualTypeArguments, var2.actualTypeArguments)) {
               return false;
            } else {
               if(this.ownerType != null) {
                  if(!this.ownerType.equals(var2.ownerType)) {
                     return false;
                  }
               } else if(var2.ownerType != null) {
                  return false;
               }

               return this.rawType != null?this.rawType.equals(var2.rawType):var2.rawType == null;
            }
         }
      } else {
         return false;
      }
   }

   public Type[] getActualTypeArguments() {
      return this.actualTypeArguments;
   }

   public Type getOwnerType() {
      return this.ownerType;
   }

   public Type getRawType() {
      return this.rawType;
   }

   public int hashCode() {
      Type[] var4 = this.actualTypeArguments;
      int var3 = 0;
      int var1;
      if(var4 != null) {
         var1 = Arrays.hashCode(this.actualTypeArguments);
      } else {
         var1 = 0;
      }

      int var2;
      if(this.ownerType != null) {
         var2 = this.ownerType.hashCode();
      } else {
         var2 = 0;
      }

      if(this.rawType != null) {
         var3 = this.rawType.hashCode();
      }

      return (var1 * 31 + var2) * 31 + var3;
   }
}
