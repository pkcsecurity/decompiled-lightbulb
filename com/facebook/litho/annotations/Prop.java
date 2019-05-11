package com.facebook.litho.annotations;

import com.facebook.litho.annotations.ResType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Prop {

   String docString() default "";

   boolean isCommonProp() default false;

   boolean optional() default false;

   boolean overrideCommonPropBehavior() default false;

   ResType resType() default ResType.NONE;

   String varArg() default "";
}
