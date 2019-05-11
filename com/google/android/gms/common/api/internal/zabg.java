package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.internal.zabe;
import com.google.android.gms.common.api.internal.zabf;

final class zabg extends gh {

   // $FF: synthetic field
   private final zabe zahu;


   zabg(zabe var1, Looper var2) {
      super(var2);
      this.zahu = var1;
   }

   public final void handleMessage(Message var1) {
      switch(var1.what) {
      case 1:
         ((zabf)var1.obj).zac(this.zahu);
         return;
      case 2:
         throw (RuntimeException)var1.obj;
      default:
         int var2 = var1.what;
         StringBuilder var3 = new StringBuilder(31);
         var3.append("Unknown message id: ");
         var3.append(var2);
         Log.w("GACStateManager", var3.toString());
      }
   }
}
