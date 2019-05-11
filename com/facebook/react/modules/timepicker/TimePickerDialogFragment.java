package com.facebook.react.modules.timepicker;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.text.format.DateFormat;
import com.facebook.react.modules.timepicker.DismissableTimePickerDialog;
import com.facebook.react.modules.timepicker.TimePickerMode;
import java.util.Calendar;
import java.util.Locale;
import javax.annotation.Nullable;

public class TimePickerDialogFragment extends DialogFragment {

   @Nullable
   private OnDismissListener mOnDismissListener;
   @Nullable
   private OnTimeSetListener mOnTimeSetListener;


   static Dialog createDialog(Bundle var0, Context var1, @Nullable OnTimeSetListener var2) {
      Calendar var8 = Calendar.getInstance();
      int var3 = var8.get(11);
      int var4 = var8.get(12);
      boolean var5 = DateFormat.is24HourFormat(var1);
      TimePickerMode var7 = TimePickerMode.DEFAULT;
      TimePickerMode var6 = var7;
      if(var0 != null) {
         var6 = var7;
         if(var0.getString("mode", (String)null) != null) {
            var6 = TimePickerMode.valueOf(var0.getString("mode").toUpperCase(Locale.US));
         }
      }

      if(var0 != null) {
         var3 = var0.getInt("hour", var8.get(11));
         var4 = var0.getInt("minute", var8.get(12));
         var5 = var0.getBoolean("is24Hour", DateFormat.is24HourFormat(var1));
      }

      if(VERSION.SDK_INT >= 21) {
         if(var6 == TimePickerMode.CLOCK) {
            return new DismissableTimePickerDialog(var1, var1.getResources().getIdentifier("ClockTimePickerDialog", "style", var1.getPackageName()), var2, var3, var4, var5);
         }

         if(var6 == TimePickerMode.SPINNER) {
            return new DismissableTimePickerDialog(var1, var1.getResources().getIdentifier("SpinnerTimePickerDialog", "style", var1.getPackageName()), var2, var3, var4, var5);
         }
      }

      return new DismissableTimePickerDialog(var1, var2, var3, var4, var5);
   }

   public Dialog onCreateDialog(Bundle var1) {
      return createDialog(this.getArguments(), this.getActivity(), this.mOnTimeSetListener);
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
