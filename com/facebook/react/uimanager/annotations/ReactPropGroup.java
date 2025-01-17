package com.facebook.react.uimanager.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.Nullable;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ReactPropGroup {

   String USE_DEFAULT_TYPE = "__default_type__";


   @Nullable
   String customType() default "__default_type__";

   double defaultDouble() default 0.0D;

   float defaultFloat() default 0.0F;

   int defaultInt() default 0;

   String[] names();
}
