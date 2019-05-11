package com.tuyasmart.stencil.global.lifecycle;

import android.app.Activity;
import android.os.Handler;
import com.tuya.smart.android.common.task.Coordinator;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.android.network.util.TimeStampManager;
import com.tuya.smart.api.start.LauncherApplicationAgent;
import com.tuyasmart.stencil.app.TuyaUnInitializer;
import com.tuyasmart.stencil.event.EventSender;
import com.tuyasmart.stencil.global.lifecycle.CrossActivityLifecycleObserver.1;
import com.tuyasmart.stencil.global.lifecycle.CrossActivityLifecycleObserver.2;

public class CrossActivityLifecycleObserver implements LauncherApplicationAgent.CrossActivityLifecycleCallback {

   private static final String TAG = "CrossActivityLifecycleObserver ggg";
   private static final int WHAT_KILL_PROCESS = 1001;
   private int appCount;
   private Handler mHandler = new Handler(new 1(this));


   public void onCreated(Activity var1) {
      bpj.a("CrossActivityLifecycleObserver ggg", "observer create");
      Coordinator.postTask(new 2(this, "adjustTime"));
   }

   public void onDestroyed(Activity var1) {
      bpj.a("CrossActivityLifecycleObserver ggg", "observer destroy");
      L.e("Exithuohuo", "cross life cycle onDestroyed....");
      L.logInLocal("Exithuohuo", "cross life cycle onDestroyed....");
      this.mHandler.removeMessages(1001);
      TuyaUnInitializer.getInstance().destroy();
   }

   public void onStarted(Activity var1) {
      bpj.a("CrossActivityLifecycleObserver ggg", "observer start");
      L.e("Exithuohuo", "onStarted ....");
      L.logInLocal("Exithuohuo", "onStarted removeMessages WHAT_KILL_PROCESS ....");
      ++this.appCount;
      this.mHandler.removeMessages(1001);
   }

   public void onStopped(Activity var1) {
      bpj.a("CrossActivityLifecycleObserver ggg", "observer stop");
      TimeStampManager.instance().onStop();
      --this.appCount;
      if(this.appCount == 0) {
         L.e("Exithuohuo", "appCount  is 0 onStopped....");
         L.logInLocal("Exithuohuo", "appCount  is 0 onStopped....");
         this.mHandler.sendEmptyMessageDelayed(1001, 180000L);
         EventSender.sendBackground();
      }

   }
}
