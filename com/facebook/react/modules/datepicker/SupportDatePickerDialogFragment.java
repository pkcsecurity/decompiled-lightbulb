package com.facebook.react.modules.datepicker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import com.facebook.react.modules.datepicker.DatePickerDialogFragment;
import javax.annotation.Nullable;

@SuppressLint({"ValidFragment"})
public class SupportDatePickerDialogFragment extends DialogFragment {

   @Nullable
   private OnDateSetListener mOnDateSetListener;
   @Nullable
   private OnDismissListener mOnDismissListener;


   public Dialog onCreateDialog(Bundle var1) {
      return DatePickerDialogFragment.createDialog(this.getArguments(), this.getActivity(), this.mOnDateSetListener);
   }

   public void onDismiss(DialogInterface var1) {
      super.onDismiss(var1);
      if(this.mOnDismissListener != null) {
         this.mOnDismissListener.onDismiss(var1);
      }

   }

   void setOnDateSetListener(@Nullable OnDateSetListener var1) {
      this.mOnDateSetListener = var1;
   }

   void setOnDismissListener(@Nullable OnDismissListener var1) {
      this.mOnDismissListener = var1;
   }
}
