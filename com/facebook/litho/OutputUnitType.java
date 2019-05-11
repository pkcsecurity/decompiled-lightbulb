package com.facebook.litho;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface OutputUnitType {

   int BACKGROUND = 1;
   int BORDER = 4;
   int CONTENT = 0;
   int FOREGROUND = 2;
   int HOST = 3;

}
