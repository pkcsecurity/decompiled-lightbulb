package com.facebook.litho;

import android.os.Looper;
import android.os.Process;
import android.support.annotation.VisibleForTesting;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ThreadUtils {

   public static final int OVERRIDE_DISABLED = 0;
   public static final int OVERRIDE_MAIN_THREAD_FALSE = 2;
   public static final int OVERRIDE_MAIN_THREAD_TRUE = 1;
   private static int sMainThreadOverride;


   public static void assertDoesntHoldLock(Object var0) {}

   public static void assertHoldsLock(Object var0) {}

   public static void assertMainThread() {}

   public static boolean isMainThread() {
      switch(sMainThreadOverride) {
      case 1:
         return true;
      case 2:
         return false;
      default:
         return Looper.getMainLooper().getThread() == Thread.currentThread();
      }
   }

   @VisibleForTesting
   public static void setMainThreadOverride(int var0) {
      sMainThreadOverride = var0;
   }

   public static int tryInheritThreadPriorityFromCurrentThread(int var0) {
      return tryRaiseThreadPriority(var0, Process.getThreadPriority(Process.myTid()));
   }

   public static int tryRaiseThreadPriority(int var0, int var1) {
      int var3 = Process.getThreadPriority(var0);
      boolean var2 = false;

      while(!var2 && var1 < var3) {
         try {
            Process.setThreadPriority(var0, var1);
         } catch (SecurityException var5) {
            ++var1;
            continue;
         }

         var2 = true;
      }

      return var3;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface MainThreadOverride {
   }
}
