package com.facebook.react;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.facebook.react.jstasks.HeadlessJsTaskContext;
import com.facebook.react.jstasks.HeadlessJsTaskEventListener;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;

public abstract class HeadlessJsTaskService extends Service implements HeadlessJsTaskEventListener {

   @Nullable
   private static WakeLock sWakeLock;
   private final Set<Integer> mActiveTasks = new CopyOnWriteArraySet();


   public static void acquireWakeLockNow(Context var0) {
      if(sWakeLock == null || !sWakeLock.isHeld()) {
         sWakeLock = ((PowerManager)Assertions.assertNotNull((PowerManager)var0.getSystemService("power"))).newWakeLock(1, HeadlessJsTaskService.class.getSimpleName());
         sWakeLock.setReferenceCounted(false);
         sWakeLock.acquire();
      }

   }

   private void invokeStartTask(ReactContext var1, final HeadlessJsTaskConfig var2) {
      final HeadlessJsTaskContext var3 = HeadlessJsTaskContext.getInstance(var1);
      var3.addTaskEventListener(this);
      UiThreadUtil.runOnUiThread(new Runnable() {
         public void run() {
            int var1 = var3.startTask(var2);
            HeadlessJsTaskService.this.mActiveTasks.add(Integer.valueOf(var1));
         }
      });
   }

   protected ReactNativeHost getReactNativeHost() {
      return ((ReactApplication)this.getApplication()).getReactNativeHost();
   }

   @Nullable
   protected HeadlessJsTaskConfig getTaskConfig(Intent var1) {
      return null;
   }

   @Nullable
   public IBinder onBind(Intent var1) {
      return null;
   }

   public void onDestroy() {
      super.onDestroy();
      if(this.getReactNativeHost().hasInstance()) {
         ReactContext var1 = this.getReactNativeHost().getReactInstanceManager().getCurrentReactContext();
         if(var1 != null) {
            HeadlessJsTaskContext.getInstance(var1).removeTaskEventListener(this);
         }
      }

      if(sWakeLock != null) {
         sWakeLock.release();
      }

   }

   public void onHeadlessJsTaskFinish(int var1) {
      this.mActiveTasks.remove(Integer.valueOf(var1));
      if(this.mActiveTasks.size() == 0) {
         this.stopSelf();
      }

   }

   public void onHeadlessJsTaskStart(int var1) {}

   public int onStartCommand(Intent var1, int var2, int var3) {
      HeadlessJsTaskConfig var4 = this.getTaskConfig(var1);
      if(var4 != null) {
         this.startTask(var4);
         return 3;
      } else {
         return 2;
      }
   }

   protected void startTask(final HeadlessJsTaskConfig var1) {
      UiThreadUtil.assertOnUiThread();
      acquireWakeLockNow(this);
      final ReactInstanceManager var2 = this.getReactNativeHost().getReactInstanceManager();
      ReactContext var3 = var2.getCurrentReactContext();
      if(var3 == null) {
         var2.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
            public void onReactContextInitialized(ReactContext var1x) {
               HeadlessJsTaskService.this.invokeStartTask(var1x, var1);
               var2.removeReactInstanceEventListener(this);
            }
         });
         if(!var2.hasStartedCreatingInitialContext()) {
            var2.createReactContextInBackground();
            return;
         }
      } else {
         this.invokeStartTask(var3, var1);
      }

   }
}
