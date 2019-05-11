package com.facebook.react.modules.timepicker;

import android.app.Activity;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.TimePicker;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.timepicker.SupportTimePickerDialogFragment;
import com.facebook.react.modules.timepicker.TimePickerDialogFragment;
import javax.annotation.Nullable;

@ReactModule(
   name = "TimePickerAndroid"
)
public class TimePickerDialogModule extends ReactContextBaseJavaModule {

   static final String ACTION_DISMISSED = "dismissedAction";
   static final String ACTION_TIME_SET = "timeSetAction";
   static final String ARG_HOUR = "hour";
   static final String ARG_IS24HOUR = "is24Hour";
   static final String ARG_MINUTE = "minute";
   static final String ARG_MODE = "mode";
   private static final String ERROR_NO_ACTIVITY = "E_NO_ACTIVITY";
   @VisibleForTesting
   public static final String FRAGMENT_TAG = "TimePickerAndroid";


   public TimePickerDialogModule(ReactApplicationContext var1) {
      super(var1);
   }

   private Bundle createFragmentArguments(ReadableMap var1) {
      Bundle var2 = new Bundle();
      if(var1.hasKey("hour") && !var1.isNull("hour")) {
         var2.putInt("hour", var1.getInt("hour"));
      }

      if(var1.hasKey("minute") && !var1.isNull("minute")) {
         var2.putInt("minute", var1.getInt("minute"));
      }

      if(var1.hasKey("is24Hour") && !var1.isNull("is24Hour")) {
         var2.putBoolean("is24Hour", var1.getBoolean("is24Hour"));
      }

      if(var1.hasKey("mode") && !var1.isNull("mode")) {
         var2.putString("mode", var1.getString("mode"));
      }

      return var2;
   }

   public String getName() {
      return "TimePickerAndroid";
   }

   @ReactMethod
   public void open(@Nullable ReadableMap var1, Promise var2) {
      Activity var3 = this.getCurrentActivity();
      if(var3 == null) {
         var2.reject("E_NO_ACTIVITY", "Tried to open a TimePicker dialog while not attached to an Activity");
      } else {
         TimePickerDialogModule.TimePickerDialogListener var5;
         if(var3 instanceof FragmentActivity) {
            FragmentManager var7 = ((FragmentActivity)var3).getSupportFragmentManager();
            DialogFragment var9 = (DialogFragment)var7.findFragmentByTag("TimePickerAndroid");
            if(var9 != null) {
               var9.dismiss();
            }

            SupportTimePickerDialogFragment var10 = new SupportTimePickerDialogFragment();
            if(var1 != null) {
               var10.setArguments(this.createFragmentArguments(var1));
            }

            var5 = new TimePickerDialogModule.TimePickerDialogListener(var2);
            var10.setOnDismissListener(var5);
            var10.setOnTimeSetListener(var5);
            var10.show(var7, "TimePickerAndroid");
         } else {
            android.app.FragmentManager var6 = var3.getFragmentManager();
            android.app.DialogFragment var4 = (android.app.DialogFragment)var6.findFragmentByTag("TimePickerAndroid");
            if(var4 != null) {
               var4.dismiss();
            }

            TimePickerDialogFragment var8 = new TimePickerDialogFragment();
            if(var1 != null) {
               var8.setArguments(this.createFragmentArguments(var1));
            }

            var5 = new TimePickerDialogModule.TimePickerDialogListener(var2);
            var8.setOnDismissListener(var5);
            var8.setOnTimeSetListener(var5);
            var8.show(var6, "TimePickerAndroid");
         }
      }
   }

   class TimePickerDialogListener implements OnTimeSetListener, OnDismissListener {

      private final Promise mPromise;
      private boolean mPromiseResolved = false;


      public TimePickerDialogListener(Promise var2) {
         this.mPromise = var2;
      }

      public void onDismiss(DialogInterface var1) {
         if(!this.mPromiseResolved && TimePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
            WritableNativeMap var2 = new WritableNativeMap();
            var2.putString("action", "dismissedAction");
            this.mPromise.resolve(var2);
            this.mPromiseResolved = true;
         }

      }

      public void onTimeSet(TimePicker var1, int var2, int var3) {
         if(!this.mPromiseResolved && TimePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
            WritableNativeMap var4 = new WritableNativeMap();
            var4.putString("action", "timeSetAction");
            var4.putInt("hour", var2);
            var4.putInt("minute", var3);
            this.mPromise.resolve(var4);
            this.mPromiseResolved = true;
         }

      }
   }
}
