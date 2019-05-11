package com.facebook.react.modules.datepicker;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.datepicker.DatePickerDialogFragment;
import com.facebook.react.modules.datepicker.SupportDatePickerDialogFragment;
import javax.annotation.Nullable;

@ReactModule(
   name = "DatePickerAndroid"
)
public class DatePickerDialogModule extends ReactContextBaseJavaModule {

   static final String ACTION_DATE_SET = "dateSetAction";
   static final String ACTION_DISMISSED = "dismissedAction";
   static final String ARG_DATE = "date";
   static final String ARG_MAXDATE = "maxDate";
   static final String ARG_MINDATE = "minDate";
   static final String ARG_MODE = "mode";
   private static final String ERROR_NO_ACTIVITY = "E_NO_ACTIVITY";
   @VisibleForTesting
   public static final String FRAGMENT_TAG = "DatePickerAndroid";


   public DatePickerDialogModule(ReactApplicationContext var1) {
      super(var1);
   }

   private Bundle createFragmentArguments(ReadableMap var1) {
      Bundle var2 = new Bundle();
      if(var1.hasKey("date") && !var1.isNull("date")) {
         var2.putLong("date", (long)var1.getDouble("date"));
      }

      if(var1.hasKey("minDate") && !var1.isNull("minDate")) {
         var2.putLong("minDate", (long)var1.getDouble("minDate"));
      }

      if(var1.hasKey("maxDate") && !var1.isNull("maxDate")) {
         var2.putLong("maxDate", (long)var1.getDouble("maxDate"));
      }

      if(var1.hasKey("mode") && !var1.isNull("mode")) {
         var2.putString("mode", var1.getString("mode"));
      }

      return var2;
   }

   public String getName() {
      return "DatePickerAndroid";
   }

   @ReactMethod
   public void open(@Nullable ReadableMap var1, Promise var2) {
      Activity var3 = this.getCurrentActivity();
      if(var3 == null) {
         var2.reject("E_NO_ACTIVITY", "Tried to open a DatePicker dialog while not attached to an Activity");
      } else {
         DatePickerDialogModule.DatePickerDialogListener var5;
         if(var3 instanceof FragmentActivity) {
            FragmentManager var7 = ((FragmentActivity)var3).getSupportFragmentManager();
            DialogFragment var9 = (DialogFragment)var7.findFragmentByTag("DatePickerAndroid");
            if(var9 != null) {
               var9.dismiss();
            }

            SupportDatePickerDialogFragment var10 = new SupportDatePickerDialogFragment();
            if(var1 != null) {
               var10.setArguments(this.createFragmentArguments(var1));
            }

            var5 = new DatePickerDialogModule.DatePickerDialogListener(var2);
            var10.setOnDismissListener(var5);
            var10.setOnDateSetListener(var5);
            var10.show(var7, "DatePickerAndroid");
         } else {
            android.app.FragmentManager var6 = var3.getFragmentManager();
            android.app.DialogFragment var4 = (android.app.DialogFragment)var6.findFragmentByTag("DatePickerAndroid");
            if(var4 != null) {
               var4.dismiss();
            }

            DatePickerDialogFragment var8 = new DatePickerDialogFragment();
            if(var1 != null) {
               var8.setArguments(this.createFragmentArguments(var1));
            }

            var5 = new DatePickerDialogModule.DatePickerDialogListener(var2);
            var8.setOnDismissListener(var5);
            var8.setOnDateSetListener(var5);
            var8.show(var6, "DatePickerAndroid");
         }
      }
   }

   class DatePickerDialogListener implements OnDateSetListener, OnDismissListener {

      private final Promise mPromise;
      private boolean mPromiseResolved = false;


      public DatePickerDialogListener(Promise var2) {
         this.mPromise = var2;
      }

      public void onDateSet(DatePicker var1, int var2, int var3, int var4) {
         if(!this.mPromiseResolved && DatePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
            WritableNativeMap var5 = new WritableNativeMap();
            var5.putString("action", "dateSetAction");
            var5.putInt("year", var2);
            var5.putInt("month", var3);
            var5.putInt("day", var4);
            this.mPromise.resolve(var5);
            this.mPromiseResolved = true;
         }

      }

      public void onDismiss(DialogInterface var1) {
         if(!this.mPromiseResolved && DatePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
            WritableNativeMap var2 = new WritableNativeMap();
            var2.putString("action", "dismissedAction");
            this.mPromise.resolve(var2);
            this.mPromiseResolved = true;
         }

      }
   }
}
