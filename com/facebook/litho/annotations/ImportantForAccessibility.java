package com.facebook.litho.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ImportantForAccessibility {

   int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
   int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
   int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
   int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
   int IMPORTANT_FOR_ACCESSIBILITY_YES_HIDE_DESCENDANTS = 8;

}
