package com.google.api.client.util;


public interface Clock {

   Clock a = new Clock() {
      public long a() {
         return System.currentTimeMillis();
      }
   };


   long a();
}
