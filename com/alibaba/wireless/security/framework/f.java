package com.alibaba.wireless.security.framework;

import android.app.Service;
import android.content.Context;
import android.content.ServiceConnection;
import com.alibaba.wireless.security.framework.b;

class f implements b.a {

   // $FF: synthetic field
   final Context a;
   // $FF: synthetic field
   final ServiceConnection b;
   // $FF: synthetic field
   final b c;


   f(b var1, Context var2, ServiceConnection var3) {
      this.c = var1;
      this.a = var2;
      this.b = var3;
   }

   public void a(int var1, Class<? extends Service> var2) {
      if(var1 == 0) {
         this.a.unbindService(this.b);
      }

      b.a(this.c, var1);
   }
}
