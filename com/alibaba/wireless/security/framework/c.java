package com.alibaba.wireless.security.framework;

import android.app.Service;
import android.content.Context;
import com.alibaba.wireless.security.framework.a;
import com.alibaba.wireless.security.framework.b;

class c implements b.a {

   // $FF: synthetic field
   final a a;
   // $FF: synthetic field
   final Context b;
   // $FF: synthetic field
   final b c;


   c(b var1, a var2, Context var3) {
      this.c = var1;
      this.a = var2;
      this.b = var3;
   }

   public void a(int var1, Class<? extends Service> var2) {
      if(var1 == 0) {
         this.a.setClass(this.b, var2);
         this.b.startService(this.a);
      }

      b.a(this.c, var1);
   }
}
