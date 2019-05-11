package com.facebook.react.modules.datepicker;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Build.VERSION;
import javax.annotation.Nullable;

public class DismissableDatePickerDialog extends DatePickerDialog {

   public DismissableDatePickerDialog(Context var1, int var2, @Nullable OnDateSetListener var3, int var4, int var5, int var6) {
      super(var1, var2, var3, var4, var5, var6);
   }

   public DismissableDatePickerDialog(Context var1, @Nullable OnDateSetListener var2, int var3, int var4, int var5) {
      super(var1, var2, var3, var4, var5);
   }

   protected void onStop() {
      if(VERSION.SDK_INT > 19) {
         super.onStop();
      }

   }
}
