package com.facebook.react.modules.timepicker;

import android.app.Dialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import com.facebook.react.modules.timepicker.TimePickerDialogFragment;
import javax.annotation.Nullable;

public class SupportTimePickerDialogFragment extends DialogFragment {

   @Nullable
   private OnDismissListener mOnDismissListener;
   @Nullable
   private OnTimeSetListener mOnTimeSetListener;


   public Dialog onCreateDialog(Bundle var1) {
      return TimePickerDialogFragment.createDialog(this.getArguments(), this.getActivity(), this.mOnTimeSetListener);
   }

   public void onDismiss(DialogInterface var1) {
      super.onDismiss(var1);
      if(this.mOnDismissListener != null) {
         this.mOnDismissListener.onDismiss(var1);
      }

   }

   public void setOnDismissListener(@Nullable OnDismissListener var1) {
      this.mOnDismissListener = var1;
   }

   public void setOnTimeSetListener(@Nullable OnTimeSetListener var1) {
      this.mOnTimeSetListener = var1;
   }
}
