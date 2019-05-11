package com.tuya.smart.home.sdk.anntation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface HomeStatus {

   int ACCEPT = 2;
   int REJECT = 3;
   int WAITING = 1;

}
