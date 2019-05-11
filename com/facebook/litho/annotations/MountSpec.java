package com.facebook.litho.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MountSpec {

   boolean canPreallocate() default false;

   Class<?>[] events() default {};

   boolean hasChildLithoViews() default false;

   boolean isPublic() default true;

   boolean isPureRender() default false;

   int poolSize() default 3;

   boolean shouldUseDisplayList() default false;

   Class<?>[] triggers() default {};

   String value() default "";
}
