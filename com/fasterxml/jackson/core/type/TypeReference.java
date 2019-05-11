package com.fasterxml.jackson.core.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeReference<T extends Object> implements Comparable<TypeReference<T>> {

   protected final Type _type;


   protected TypeReference() {
      Type var1 = this.getClass().getGenericSuperclass();
      if(var1 instanceof Class) {
         throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
      } else {
         this._type = ((ParameterizedType)var1).getActualTypeArguments()[0];
      }
   }

   public int compareTo(TypeReference<T> var1) {
      return 0;
   }

   public Type getType() {
      return this._type;
   }
}
