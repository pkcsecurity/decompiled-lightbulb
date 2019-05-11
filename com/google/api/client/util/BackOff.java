package com.google.api.client.util;

import java.io.IOException;

public interface BackOff {

   BackOff a = new BackOff() {
      public long a() throws IOException {
         return 0L;
      }
   };
   BackOff b = new BackOff() {
      public long a() throws IOException {
         return -1L;
      }
   };


   long a() throws IOException;
}
