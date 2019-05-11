package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.internal.zac;
import com.google.android.gms.common.internal.zad;
import com.google.android.gms.common.internal.zae;

public abstract class DialogRedirect implements OnClickListener {

   public static DialogRedirect getInstance(Activity var0, Intent var1, int var2) {
      return new zac(var1, var0, var2);
   }

   public static DialogRedirect getInstance(@NonNull Fragment var0, Intent var1, int var2) {
      return new zad(var1, var0, var2);
   }

   public static DialogRedirect getInstance(@NonNull LifecycleFragment var0, Intent var1, int var2) {
      return new zae(var1, var0, var2);
   }

   public void onClick(DialogInterface var1, int var2) {
      try {
         this.redirect();
         return;
      } catch (ActivityNotFoundException var6) {
         Log.e("DialogRedirect", "Failed to start resolution intent", var6);
      } finally {
         var1.dismiss();
      }

   }

   protected abstract void redirect();
}
