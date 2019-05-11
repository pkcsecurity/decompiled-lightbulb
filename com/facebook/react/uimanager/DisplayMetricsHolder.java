package com.facebook.react.uimanager;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.facebook.infer.annotation.Assertions;
import java.lang.reflect.Method;
import javax.annotation.Nullable;

public class DisplayMetricsHolder {

   @Nullable
   private static DisplayMetrics sScreenDisplayMetrics;
   @Nullable
   private static DisplayMetrics sWindowDisplayMetrics;


   public static DisplayMetrics getScreenDisplayMetrics() {
      return sScreenDisplayMetrics;
   }

   @Deprecated
   public static DisplayMetrics getWindowDisplayMetrics() {
      return sWindowDisplayMetrics;
   }

   public static void initDisplayMetrics(Context var0) {
      DisplayMetrics var2 = var0.getResources().getDisplayMetrics();
      setWindowDisplayMetrics(var2);
      DisplayMetrics var1 = new DisplayMetrics();
      var1.setTo(var2);
      WindowManager var4 = (WindowManager)var0.getSystemService("window");
      Assertions.assertNotNull(var4, "WindowManager is null!");
      Display var5 = var4.getDefaultDisplay();
      if(VERSION.SDK_INT >= 17) {
         var5.getRealMetrics(var1);
      } else {
         try {
            Method var6 = Display.class.getMethod("getRawHeight", new Class[0]);
            var1.widthPixels = ((Integer)Display.class.getMethod("getRawWidth", new Class[0]).invoke(var5, new Object[0])).intValue();
            var1.heightPixels = ((Integer)var6.invoke(var5, new Object[0])).intValue();
         } catch (NoSuchMethodException var3) {
            throw new RuntimeException("Error getting real dimensions for API level < 17", var3);
         }
      }

      setScreenDisplayMetrics(var1);
   }

   public static void initDisplayMetricsIfNotInitialized(Context var0) {
      if(getScreenDisplayMetrics() == null) {
         initDisplayMetrics(var0);
      }
   }

   public static void setScreenDisplayMetrics(DisplayMetrics var0) {
      sScreenDisplayMetrics = var0;
   }

   public static void setWindowDisplayMetrics(DisplayMetrics var0) {
      sWindowDisplayMetrics = var0;
   }
}
