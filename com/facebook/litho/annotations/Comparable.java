package com.facebook.litho.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Comparable {

   int ARRAY = 2;
   int COLLECTION_COMPLEVEL_0 = 5;
   int COLLECTION_COMPLEVEL_1 = 6;
   int COLLECTION_COMPLEVEL_2 = 7;
   int COLLECTION_COMPLEVEL_3 = 8;
   int COLLECTION_COMPLEVEL_4 = 9;
   int COMPONENT = 10;
   int DOUBLE = 1;
   int EVENT_HANDLER = 11;
   int EVENT_HANDLER_IN_PARAMETERIZED_TYPE = 12;
   int FLOAT = 0;
   int OTHER = 13;
   int PRIMITIVE = 3;
   int REFERENCE = 4;
   int SECTION = 15;
   int STATE_CONTAINER = 14;


   int type();

   @Retention(RetentionPolicy.SOURCE)
   public @interface Type {
   }
}
