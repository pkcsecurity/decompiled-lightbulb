package com.facebook.react.uimanager;


public class ReactRootViewTagGenerator {

   private static final int ROOT_VIEW_TAG_INCREMENT = 10;
   private static int sNextRootViewTag;


   public static int getNextRootViewTag() {
      synchronized(ReactRootViewTagGenerator.class){}

      int var0;
      try {
         var0 = sNextRootViewTag;
         sNextRootViewTag += 10;
      } finally {
         ;
      }

      return var0;
   }
}
