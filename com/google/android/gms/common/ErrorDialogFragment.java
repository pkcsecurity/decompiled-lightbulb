package com.google.android.gms.common;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;

public class ErrorDialogFragment extends DialogFragment {

   private Dialog mDialog = null;
   private OnCancelListener zaan = null;


   public static ErrorDialogFragment newInstance(Dialog var0) {
      return newInstance(var0, (OnCancelListener)null);
   }

   public static ErrorDialogFragment newInstance(Dialog var0, OnCancelListener var1) {
      ErrorDialogFragment var2 = new ErrorDialogFragment();
      var0 = (Dialog)Preconditions.checkNotNull(var0, "Cannot display null dialog");
      var0.setOnCancelListener((OnCancelListener)null);
      var0.setOnDismissListener((OnDismissListener)null);
      var2.mDialog = var0;
      if(var1 != null) {
         var2.zaan = var1;
      }

      return var2;
   }

   public void onCancel(DialogInterface var1) {
      if(this.zaan != null) {
         this.zaan.onCancel(var1);
      }

   }

   public Dialog onCreateDialog(Bundle var1) {
      if(this.mDialog == null) {
         this.setShowsDialog(false);
      }

      return this.mDialog;
   }

   public void show(FragmentManager var1, String var2) {
      super.show(var1, var2);
   }
}
