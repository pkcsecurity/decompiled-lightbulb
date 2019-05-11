package android.arch.lifecycle;

import android.app.Service;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

public class LifecycleService extends Service implements LifecycleOwner {

   private final l mDispatcher = new l(this);


   public f getLifecycle() {
      return this.mDispatcher.e();
   }

   @CallSuper
   @Nullable
   public IBinder onBind(Intent var1) {
      this.mDispatcher.b();
      return null;
   }

   @CallSuper
   public void onCreate() {
      this.mDispatcher.a();
      super.onCreate();
   }

   @CallSuper
   public void onDestroy() {
      this.mDispatcher.d();
      super.onDestroy();
   }

   @CallSuper
   public void onStart(Intent var1, int var2) {
      this.mDispatcher.c();
      super.onStart(var1, var2);
   }

   @CallSuper
   public int onStartCommand(Intent var1, int var2, int var3) {
      return super.onStartCommand(var1, var2, var3);
   }
}
