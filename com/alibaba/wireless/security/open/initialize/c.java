package com.alibaba.wireless.security.open.initialize;

import android.content.Context;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.initialize.b;

class c implements Runnable {

   // $FF: synthetic field
   final Context a;
   // $FF: synthetic field
   final String b;
   // $FF: synthetic field
   final boolean c;
   // $FF: synthetic field
   final b d;


   c(b var1, Context var2, String var3, boolean var4) {
      this.d = var1;
      this.a = var2;
      this.b = var3;
      this.c = var4;
   }

   public void run() {
      try {
         this.d.a(this.a, this.b, this.c);
         b.a(this.d);
      } catch (SecException var2) {
         var2.printStackTrace();
         b.b(this.d);
      }
   }
}
