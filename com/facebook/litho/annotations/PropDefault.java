package com.facebook.litho.annotations;

import com.facebook.litho.annotations.ResType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface PropDefault {

   int resId() default 0;

   ResType resType() default ResType.NONE;
}
