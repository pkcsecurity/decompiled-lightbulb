package com.facebook.react.modules.vibration;

import android.os.Vibrator;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(
   name = "Vibration"
)
public class VibrationModule extends ReactContextBaseJavaModule {

   public VibrationModule(ReactApplicationContext var1) {
      super(var1);
   }

   @ReactMethod
   public void cancel() {
      Vibrator var1 = (Vibrator)this.getReactApplicationContext().getSystemService("vibrator");
      if(var1 != null) {
         var1.cancel();
      }

   }

   public String getName() {
      return "Vibration";
   }

   @ReactMethod
   public void vibrate(int var1) {
      Vibrator var2 = (Vibrator)this.getReactApplicationContext().getSystemService("vibrator");
      if(var2 != null) {
         var2.vibrate((long)var1);
      }

   }

   @ReactMethod
   public void vibrateByPattern(ReadableArray var1, int var2) {
      long[] var4 = new long[var1.size()];

      for(int var3 = 0; var3 < var1.size(); ++var3) {
         var4[var3] = (long)var1.getInt(var3);
      }

      Vibrator var5 = (Vibrator)this.getReactApplicationContext().getSystemService("vibrator");
      if(var5 != null) {
         var5.vibrate(var4, var2);
      }

   }
}
