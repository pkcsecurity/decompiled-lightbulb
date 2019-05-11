package com.tuyasmart.stencil.global.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import com.tuya.smart.api.start.LauncherApplicationAgent;
import java.lang.reflect.Field;

public class TuyaActivityLifecycleObserver extends LauncherApplicationAgent.AbstractActivityLifecycleCallbacks {

   private Field layoutInflaterField;


   public TuyaActivityLifecycleObserver() {
      if(VERSION.SDK_INT >= 14) {
         try {
            this.layoutInflaterField = LayoutInflater.class.getDeclaredField("mPrivateFactory");
            this.layoutInflaterField.setAccessible(true);
         } catch (Exception var2) {
            return;
         }
      }

   }

   public void onActivityCreated(Activity var1, Bundle var2) {
      super.onActivityCreated(var1, var2);
      boz.b(var1);
   }

   public void onActivityDestroyed(Activity var1) {
      super.onActivityDestroyed(var1);
      if(VERSION.SDK_INT >= 14 && this.layoutInflaterField != null) {
         try {
            LayoutInflater var2 = var1.getLayoutInflater();
            this.layoutInflaterField.set(var2, (Object)null);
         } catch (Exception var3) {
            ;
         }
      }

      boz.a(var1);
   }

   public void onActivityStarted(Activity var1) {
      super.onActivityStarted(var1);
      boz.c(var1);
   }

   public void onActivityStopped(Activity var1) {
      super.onActivityStopped(var1);
      boz.d(var1);
   }
}
