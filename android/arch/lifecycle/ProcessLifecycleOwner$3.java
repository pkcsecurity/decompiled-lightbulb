package android.arch.lifecycle;

import android.app.Activity;
import android.arch.lifecycle.EmptyActivityLifecycleCallbacks;
import android.arch.lifecycle.ReportFragment;
import android.os.Bundle;

public class ProcessLifecycleOwner$3 extends EmptyActivityLifecycleCallbacks {

   // $FF: synthetic field
   final k this$0;


   public ProcessLifecycleOwner$3(k var1) {
      this.this$0 = var1;
   }

   public void onActivityCreated(Activity var1, Bundle var2) {
      ReportFragment.get(var1).setProcessListener(k.c(this.this$0));
   }

   public void onActivityPaused(Activity var1) {
      this.this$0.c();
   }

   public void onActivityStopped(Activity var1) {
      this.this$0.d();
   }
}
