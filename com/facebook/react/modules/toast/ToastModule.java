package com.facebook.react.modules.toast;

import android.widget.Toast;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import java.util.HashMap;
import java.util.Map;

@ReactModule(
   name = "ToastAndroid"
)
public class ToastModule extends ReactContextBaseJavaModule {

   private static final String DURATION_LONG_KEY = "LONG";
   private static final String DURATION_SHORT_KEY = "SHORT";
   private static final String GRAVITY_BOTTOM_KEY = "BOTTOM";
   private static final String GRAVITY_CENTER = "CENTER";
   private static final String GRAVITY_TOP_KEY = "TOP";


   public ToastModule(ReactApplicationContext var1) {
      super(var1);
   }

   public Map<String, Object> getConstants() {
      HashMap var1 = MapBuilder.newHashMap();
      var1.put("SHORT", Integer.valueOf(0));
      var1.put("LONG", Integer.valueOf(1));
      var1.put("TOP", Integer.valueOf(49));
      var1.put("BOTTOM", Integer.valueOf(81));
      var1.put("CENTER", Integer.valueOf(17));
      return var1;
   }

   public String getName() {
      return "ToastAndroid";
   }

   @ReactMethod
   public void show(final String var1, final int var2) {
      UiThreadUtil.runOnUiThread(new Runnable() {
         public void run() {
            Toast.makeText(ToastModule.this.getReactApplicationContext(), var1, var2).show();
         }
      });
   }

   @ReactMethod
   public void showWithGravity(final String var1, final int var2, final int var3) {
      UiThreadUtil.runOnUiThread(new Runnable() {
         public void run() {
            Toast var1x = Toast.makeText(ToastModule.this.getReactApplicationContext(), var1, var2);
            var1x.setGravity(var3, 0, 0);
            var1x.show();
         }
      });
   }

   @ReactMethod
   public void showWithGravityAndOffset(final String var1, final int var2, final int var3, final int var4, final int var5) {
      UiThreadUtil.runOnUiThread(new Runnable() {
         public void run() {
            Toast var1x = Toast.makeText(ToastModule.this.getReactApplicationContext(), var1, var2);
            var1x.setGravity(var3, var4, var5);
            var1x.show();
         }
      });
   }
}
