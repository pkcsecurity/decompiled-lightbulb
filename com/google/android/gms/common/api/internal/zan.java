package com.google.android.gms.common.api.internal;

import android.app.Dialog;
import android.content.DialogInterface.OnCancelListener;
import android.support.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.internal.zabr;
import com.google.android.gms.common.api.internal.zal;
import com.google.android.gms.common.api.internal.zam;
import com.google.android.gms.common.api.internal.zao;

final class zan implements Runnable {

   private final zam zadi;
   // $FF: synthetic field
   final zal zadj;


   zan(zal var1, zam var2) {
      this.zadj = var1;
      this.zadi = var2;
   }

   @MainThread
   public final void run() {
      if(this.zadj.mStarted) {
         ConnectionResult var1 = this.zadi.getConnectionResult();
         if(var1.hasResolution()) {
            this.zadj.mLifecycleFragment.startActivityForResult(GoogleApiActivity.zaa(this.zadj.getActivity(), var1.getResolution(), this.zadi.zar(), false), 1);
         } else if(this.zadj.zacc.isUserResolvableError(var1.getErrorCode())) {
            this.zadj.zacc.zaa(this.zadj.getActivity(), this.zadj.mLifecycleFragment, var1.getErrorCode(), 2, this.zadj);
         } else if(var1.getErrorCode() == 18) {
            Dialog var2 = GoogleApiAvailability.zaa(this.zadj.getActivity(), (OnCancelListener)this.zadj);
            this.zadj.zacc.zaa(this.zadj.getActivity().getApplicationContext(), (zabr)(new zao(this, var2)));
         } else {
            this.zadj.zaa(var1, this.zadi.zar());
         }
      }
   }
}
