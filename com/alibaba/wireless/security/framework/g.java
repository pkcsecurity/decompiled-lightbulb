package com.alibaba.wireless.security.framework;

import com.alibaba.wireless.security.framework.b;
import com.alibaba.wireless.security.open.SecException;

class g implements Runnable {

   // $FF: synthetic field
   final String a;
   // $FF: synthetic field
   final String b;
   // $FF: synthetic field
   final b c;


   g(b var1, String var2, String var3) {
      this.c = var1;
      this.a = var2;
      this.b = var3;
   }

   public void run() {
      try {
         b.a(this.c, this.a, this.b, true);
      } catch (SecException var2) {
         com.alibaba.wireless.security.framework.b.a.a("load weak dependency", var2);
      }
   }
}
