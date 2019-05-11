package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.internal.zaaw;

final class zabb extends gh {

   // $FF: synthetic field
   private final zaaw zahg;


   zabb(zaaw var1, Looper var2) {
      super(var2);
      this.zahg = var1;
   }

   public final void handleMessage(Message var1) {
      switch(var1.what) {
      case 1:
         zaaw.zab(this.zahg);
         return;
      case 2:
         zaaw.zaa(this.zahg);
         return;
      default:
         int var2 = var1.what;
         StringBuilder var3 = new StringBuilder(31);
         var3.append("Unknown message id: ");
         var3.append(var2);
         Log.w("GoogleApiClientImpl", var3.toString());
      }
   }
}
