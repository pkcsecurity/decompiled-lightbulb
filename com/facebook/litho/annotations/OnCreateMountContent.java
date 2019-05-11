package com.facebook.litho.annotations;

import com.facebook.litho.annotations.MountingType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface OnCreateMountContent {

   MountingType mountingType() default MountingType.INFER;
}
