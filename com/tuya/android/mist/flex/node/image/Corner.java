package com.tuya.android.mist.flex.node.image;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Corner {

   int BOTTOM_LEFT = 3;
   int BOTTOM_RIGHT = 2;
   int TOP_LEFT = 0;
   int TOP_RIGHT = 1;

}
