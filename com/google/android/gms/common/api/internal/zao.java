package com.google.android.gms.common.api.internal;

import android.app.Dialog;
import com.google.android.gms.common.api.internal.zabr;
import com.google.android.gms.common.api.internal.zan;

final class zao extends zabr {

   // $FF: synthetic field
   private final Dialog zadk;
   // $FF: synthetic field
   private final zan zadl;


   zao(zan var1, Dialog var2) {
      this.zadl = var1;
      this.zadk = var2;
   }

   public final void zas() {
      this.zadl.zadj.zaq();
      if(this.zadk.isShowing()) {
         this.zadk.dismiss();
      }

   }
}
