package com.alibaba.wireless.security.framework;

import android.app.Service;
import android.content.Context;
import android.content.ServiceConnection;
import com.alibaba.wireless.security.framework.a;
import com.alibaba.wireless.security.framework.b;

class e implements b.a {

   // $FF: synthetic field
   final a a;
   // $FF: synthetic field
   final Context b;
   // $FF: synthetic field
   final ServiceConnection c;
   // $FF: synthetic field
   final int d;
   // $FF: synthetic field
   final b e;


   e(b var1, a var2, Context var3, ServiceConnection var4, int var5) {
      this.e = var1;
      this.a = var2;
      this.b = var3;
      this.c = var4;
      this.d = var5;
   }

   public void a(int var1, Class<? extends Service> var2) {
      if(var1 == 0) {
         this.a.setClass(this.b, var2);
         this.b.bindService(this.a, this.c, this.d);
      }

      b.a(this.e, var1);
   }
}
