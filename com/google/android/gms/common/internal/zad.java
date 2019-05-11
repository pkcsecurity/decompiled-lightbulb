package com.google.android.gms.common.internal;

import android.content.Intent;
import android.support.v4.app.Fragment;
import com.google.android.gms.common.internal.DialogRedirect;

final class zad extends DialogRedirect {

   // $FF: synthetic field
   private final Fragment val$fragment;
   // $FF: synthetic field
   private final int val$requestCode;
   // $FF: synthetic field
   private final Intent zaog;


   zad(Intent var1, Fragment var2, int var3) {
      this.zaog = var1;
      this.val$fragment = var2;
      this.val$requestCode = var3;
   }

   public final void redirect() {
      if(this.zaog != null) {
         this.val$fragment.startActivityForResult(this.zaog, this.val$requestCode);
      }

   }
}
