package com.facebook.react.uimanager.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.Nullable;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ReactProp {

   String USE_DEFAULT_TYPE = "__default_type__";


   @Nullable
   String customType() default "__default_type__";

   boolean defaultBoolean() default false;

   double defaultDouble() default 0.0D;

   float defaultFloat() default 0.0F;

   int defaultInt() default 0;

   String name();
}
