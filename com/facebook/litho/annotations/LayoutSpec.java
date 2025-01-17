package com.facebook.litho.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LayoutSpec {

   Class<?>[] events() default {};

   boolean isPublic() default true;

   boolean isPureRender() default false;

   Class<?>[] triggers() default {};

   String value() default "";
}
