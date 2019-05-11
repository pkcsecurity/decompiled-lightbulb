package com.google.api.client.util;


public interface Sleeper {

   Sleeper a = new Sleeper() {
      public void a(long var1) throws InterruptedException {
         Thread.sleep(var1);
      }
   };


   void a(long var1) throws InterruptedException;
}
