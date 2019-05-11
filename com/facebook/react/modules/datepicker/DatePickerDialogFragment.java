package com.facebook.react.modules.datepicker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.widget.DatePicker;
import com.facebook.react.modules.datepicker.DatePickerMode;
import com.facebook.react.modules.datepicker.DismissableDatePickerDialog;
import java.util.Calendar;
import java.util.Locale;
import javax.annotation.Nullable;

@SuppressLint({"ValidFragment"})
public class DatePickerDialogFragment extends DialogFragment {

   private static final long DEFAULT_MIN_DATE = -2208988800001L;
   @Nullable
   private OnDateSetListener mOnDateSetListener;
   @Nullable
   private OnDismissListener mOnDismissListener;


   static Dialog createDialog(Bundle var0, Context var1, @Nullable OnDateSetListener var2) {
      Calendar var8 = Calendar.getInstance();
      if(var0 != null && var0.containsKey("date")) {
         var8.setTimeInMillis(var0.getLong("date"));
      }

      int var3 = var8.get(1);
      int var4 = var8.get(2);
      int var5 = var8.get(5);
      DatePickerMode var7 = DatePickerMode.DEFAULT;
      DatePickerMode var6 = var7;
      if(var0 != null) {
         var6 = var7;
         if(var0.getString("mode", (String)null) != null) {
            var6 = DatePickerMode.valueOf(var0.getString("mode").toUpperCase(Locale.US));
         }
      }

      DismissableDatePickerDialog var9;
      if(VERSION.SDK_INT >= 21) {
         switch(null.$SwitchMap$com$facebook$react$modules$datepicker$DatePickerMode[var6.ordinal()]) {
         case 1:
            var9 = new DismissableDatePickerDialog(var1, var1.getResources().getIdentifier("CalendarDatePickerDialog", "style", var1.getPackageName()), var2, var3, var4, var5);
            break;
         case 2:
            var9 = new DismissableDatePickerDialog(var1, var1.getResources().getIdentifier("SpinnerDatePickerDialog", "style", var1.getPackageName()), var2, var3, var4, var5);
            break;
         case 3:
            var9 = new DismissableDatePickerDialog(var1, var2, var3, var4, var5);
            break;
         default:
            var9 = null;
         }
      } else {
         var9 = new DismissableDatePickerDialog(var1, var2, var3, var4, var5);
         switch(null.$SwitchMap$com$facebook$react$modules$datepicker$DatePickerMode[var6.ordinal()]) {
         case 1:
            var9.getDatePicker().setCalendarViewShown(true);
            var9.getDatePicker().setSpinnersShown(false);
            break;
         case 2:
            var9.getDatePicker().setCalendarViewShown(false);
         }
      }

      DatePicker var10 = var9.getDatePicker();
      if(var0 != null && var0.containsKey("minDate")) {
         var8.setTimeInMillis(var0.getLong("minDate"));
         var8.set(11, 0);
         var8.set(12, 0);
         var8.set(13, 0);
         var8.set(14, 0);
         var10.setMinDate(var8.getTimeInMillis());
      } else {
         var10.setMinDate(-2208988800001L);
      }

      if(var0 != null && var0.containsKey("maxDate")) {
         var8.setTimeInMillis(var0.getLong("maxDate"));
         var8.set(11, 23);
         var8.set(12, 59);
         var8.set(13, 59);
         var8.set(14, 999);
         var10.setMaxDate(var8.getTimeInMillis());
      }

      return var9;
   }

   public Dialog onCreateDialog(Bundle var1) {
      return createDialog(this.getArguments(), this.getActivity(), this.mOnDateSetListener);
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
