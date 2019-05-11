package com.google.api.client.json;

import com.google.api.client.util.Beta;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Beta
public @interface JsonPolymorphicTypeMap {

   JsonPolymorphicTypeMap.TypeDef[] a();

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD})
   public @interface TypeDef {

      String a();

      Class<?> b();
   }
}
