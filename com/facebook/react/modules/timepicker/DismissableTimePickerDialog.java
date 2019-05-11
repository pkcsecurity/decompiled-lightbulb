package com.facebook.react.modules.timepicker;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Build.VERSION;
import javax.annotation.Nullable;

public class DismissableTimePickerDialog extends TimePickerDialog {

   public DismissableTimePickerDialog(Context var1, int var2, @Nullable OnTimeSetListener var3, int var4, int var5, boolean var6) {
      super(var1, var2, var3, var4, var5, var6);
   }

   public DismissableTimePickerDialog(Context var1, @Nullable OnTimeSetListener var2, int var3, int var4, boolean var5) {
      super(var1, var2, var3, var4, var5);
   }

   protected void onStop() {
      if(VERSION.SDK_INT > 19) {
         super.onStop();
      }

   }
}
