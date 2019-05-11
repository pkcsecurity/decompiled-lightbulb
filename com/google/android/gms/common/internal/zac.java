package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.Intent;
import com.google.android.gms.common.internal.DialogRedirect;

final class zac extends DialogRedirect {

   // $FF: synthetic field
   private final Activity val$activity;
   // $FF: synthetic field
   private final int val$requestCode;
   // $FF: synthetic field
   private final Intent zaog;


   zac(Intent var1, Activity var2, int var3) {
      this.zaog = var1;
      this.val$activity = var2;
      this.val$requestCode = var3;
   }

   public final void redirect() {
      if(this.zaog != null) {
         this.val$activity.startActivityForResult(this.zaog, this.val$requestCode);
      }

   }
}
