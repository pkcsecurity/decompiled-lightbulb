package com.facebook.react.bridge;

import android.os.Handler;
import android.os.Looper;
import com.facebook.react.bridge.SoftAssertions;
import javax.annotation.Nullable;

public class UiThreadUtil {

   @Nullable
   private static Handler sMainHandler;


   public static void assertNotOnUiThread() {
      SoftAssertions.assertCondition(isOnUiThread() ^ true, "Expected not to run on UI thread!");
   }

   public static void assertOnUiThread() {
      SoftAssertions.assertCondition(isOnUiThread(), "Expected to run on UI thread!");
   }

   public static boolean isOnUiThread() {
      return Looper.getMainLooper().getThread() == Thread.currentThread();
   }

   public static void runOnUiThread(Runnable param0) {
      // $FF: Couldn't be decompiled
   }
}
