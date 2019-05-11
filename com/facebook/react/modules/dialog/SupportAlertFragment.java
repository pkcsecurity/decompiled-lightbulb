package com.facebook.react.modules.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import com.facebook.react.modules.dialog.AlertFragment;
import com.facebook.react.modules.dialog.DialogModule;
import javax.annotation.Nullable;

public class SupportAlertFragment extends DialogFragment implements OnClickListener {

   @Nullable
   private final DialogModule.AlertFragmentListener mListener;


   public SupportAlertFragment() {
      this.mListener = null;
   }

   public SupportAlertFragment(@Nullable DialogModule.AlertFragmentListener var1, Bundle var2) {
      this.mListener = var1;
      this.setArguments(var2);
   }

   public void onClick(DialogInterface var1, int var2) {
      if(this.mListener != null) {
         this.mListener.onClick(var1, var2);
      }

   }

   public Dialog onCreateDialog(Bundle var1) {
      return AlertFragment.createDialog(this.getActivity(), this.getArguments(), this);
   }

   public void onDismiss(DialogInterface var1) {
      super.onDismiss(var1);
      if(this.mListener != null) {
         this.mListener.onDismiss(var1);
      }

   }
}
