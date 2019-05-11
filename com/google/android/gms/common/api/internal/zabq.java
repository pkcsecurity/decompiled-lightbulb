package com.google.android.gms.common.api.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.common.api.internal.zabr;

public final class zabq extends BroadcastReceiver {

   private Context mContext;
   private final zabr zajh;


   public zabq(zabr var1) {
      this.zajh = var1;
   }

   public final void onReceive(Context var1, Intent var2) {
      Uri var3 = var2.getData();
      String var4;
      if(var3 != null) {
         var4 = var3.getSchemeSpecificPart();
      } else {
         var4 = null;
      }

      if("com.google.android.gms".equals(var4)) {
         this.zajh.zas();
         this.unregister();
      }

   }

   public final void unregister() {
      synchronized(this){}

      try {
         if(this.mContext != null) {
            this.mContext.unregisterReceiver(this);
         }

         this.mContext = null;
      } finally {
         ;
      }

   }

   public final void zac(Context var1) {
      this.mContext = var1;
   }
}
