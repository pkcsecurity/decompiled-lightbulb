package com.tuya.smart.api.start;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.tuya.smart.api.start.LauncherApplicationAgent.1;
import com.tuya.smart.api.start.LauncherApplicationAgent.CrossActivityLifecycleCallbacks;
import com.tuya.smart.api.start.LauncherApplicationAgent.a;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class LauncherApplicationAgent {

   private static volatile LauncherApplicationAgent k;
   private Application a;
   private String b;
   private String c;
   private volatile String d;
   private volatile String e;
   private final Handler f = new Handler();
   private final List<LauncherApplicationAgent.CrossActivityLifecycleCallback> g = new CopyOnWriteArrayList();
   private final AtomicInteger h = new AtomicInteger();
   private final AtomicInteger i = new AtomicInteger();
   private WeakReference<Activity> j;
   private volatile boolean l = false;


   public static LauncherApplicationAgent a() {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static WeakReference a(LauncherApplicationAgent var0) {
      return var0.j;
   }

   // $FF: synthetic method
   static WeakReference a(LauncherApplicationAgent var0, WeakReference var1) {
      var0.j = var1;
      return var1;
   }

   // $FF: synthetic method
   static AtomicInteger b(LauncherApplicationAgent var0) {
      return var0.h;
   }

   // $FF: synthetic method
   static List c(LauncherApplicationAgent var0) {
      return var0.g;
   }

   // $FF: synthetic method
   static AtomicInteger d(LauncherApplicationAgent var0) {
      return var0.i;
   }

   public void a(Application var1) {
      if(this.l) {
         aiy.c("LauncherApplication", "onCreate has been called");
      } else {
         this.l = true;
         this.a = var1;
         this.b = arg.a(var1);
         this.c = var1.getPackageName();
         StringBuilder var2 = new StringBuilder();
         var2.append("packageName/processName: ");
         var2.append(this.c);
         var2.append(":");
         var2.append(this.b);
         aiy.b("LauncherApplication", var2.toString());
         var1.registerActivityLifecycleCallbacks(new CrossActivityLifecycleCallbacks(this, (1)null));
      }
   }

   public void a(LauncherApplicationAgent.CrossActivityLifecycleCallback var1) {
      if(var1 == null) {
         RuntimeException var3 = new RuntimeException("registerCrossActivityLifecycleCallback must not be null");
         var3.fillInStackTrace();
         StringBuilder var2 = new StringBuilder();
         var2.append("Called: ");
         var2.append(this);
         aiy.a("LauncherApplication", var2.toString(), var3);
      } else {
         this.g.add(var1);
         if(this.h.get() > 0) {
            this.f.post(new a(this, var1, "onCreated"));
         }

         if(this.i.get() > 0) {
            this.f.post(new a(this, var1, "onStarted"));
         }

      }
   }

   public void a(Runnable var1) {
      this.f.post(var1);
   }

   public void b(LauncherApplicationAgent.CrossActivityLifecycleCallback var1) {
      this.g.remove(var1);
   }

   public boolean b() {
      return TextUtils.equals(this.b, this.c) || TextUtils.isEmpty(this.b);
   }

   public String c() {
      return this.c;
   }

   public String d() {
      return this.b;
   }

   public String e() {
      if(this.d == null) {
         this.d = arg.a(this.g());
      }

      return this.d;
   }

   public String f() {
      if(this.e == null) {
         this.e = arg.a(this.g(), this.c);
      }

      return this.e;
   }

   public Application g() {
      if(this.a == null) {
         throw new RuntimeException("Must call LauncherApplicationAgent.getInstance().onCreate(application) fiorst");
      } else {
         return this.a;
      }
   }

   public interface CrossActivityLifecycleCallback {

      void onCreated(Activity var1);

      void onDestroyed(Activity var1);

      void onStarted(Activity var1);

      void onStopped(Activity var1);
   }

   public static class AbstractActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

      public void onActivityCreated(Activity var1, Bundle var2) {}

      public void onActivityDestroyed(Activity var1) {}

      public void onActivityPaused(Activity var1) {}

      public void onActivityResumed(Activity var1) {}

      public void onActivitySaveInstanceState(Activity var1, Bundle var2) {}

      public void onActivityStarted(Activity var1) {}

      public void onActivityStopped(Activity var1) {}
   }
}
