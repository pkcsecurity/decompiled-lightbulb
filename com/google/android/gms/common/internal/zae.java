package com.google.android.gms.common.internal;

import android.content.Intent;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.internal.DialogRedirect;

final class zae extends DialogRedirect {

   // $FF: synthetic field
   private final int val$requestCode;
   // $FF: synthetic field
   private final Intent zaog;
   // $FF: synthetic field
   private final LifecycleFragment zaoh;


   zae(Intent var1, LifecycleFragment var2, int var3) {
      this.zaog = var1;
      this.zaoh = var2;
      this.val$requestCode = var3;
   }

   public final void redirect() {
      if(this.zaog != null) {
         this.zaoh.startActivityForResult(this.zaog, this.val$requestCode);
      }

   }
}
