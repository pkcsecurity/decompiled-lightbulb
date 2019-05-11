package com.google.firebase;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.events.Publisher;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.concurrent.GuardedBy;
import tf.1;

@PublicApi
public class FirebaseApp {

   @GuardedBy
   static final Map<String, FirebaseApp> a = new ArrayMap();
   private static final List<String> b = Arrays.asList(new String[]{"com.google.firebase.auth.FirebaseAuth", "com.google.firebase.iid.FirebaseInstanceId"});
   private static final List<String> c = Collections.singletonList("com.google.firebase.crash.FirebaseCrash");
   private static final List<String> d = Arrays.asList(new String[]{"com.google.android.gms.measurement.AppMeasurement"});
   private static final List<String> e = Arrays.asList(new String[0]);
   private static final Set<String> f = Collections.emptySet();
   private static final Object g = new Object();
   private static final Executor h = new FirebaseApp.b((byte)0);
   private final Context i;
   private final String j;
   private final te k;
   private final tn l;
   private final SharedPreferences m;
   private final Publisher n;
   private final AtomicBoolean o = new AtomicBoolean(false);
   private final AtomicBoolean p = new AtomicBoolean();
   private final AtomicBoolean q;
   private final List<FirebaseApp.IdTokenListener> r = new CopyOnWriteArrayList();
   private final List<FirebaseApp.BackgroundStateChangeListener> s = new CopyOnWriteArrayList();
   private final List<FirebaseAppLifecycleListener> t = new CopyOnWriteArrayList();
   private FirebaseApp.IdTokenListenersCountChangedListener u;


   private FirebaseApp(Context var1, String var2, te var3) {
      this.i = (Context)Preconditions.checkNotNull(var1);
      this.j = Preconditions.checkNotEmpty(var2);
      this.k = (te)Preconditions.checkNotNull(var3);
      this.u = new vr();
      this.m = var1.getSharedPreferences("com.google.firebase.common.prefs", 0);
      this.q = new AtomicBoolean(this.h());
      List var4 = 1.a(var1).a();
      this.l = new tn(h, var4, new tf[]{tf.a(var1, Context.class, new Class[0]), tf.a(this, FirebaseApp.class, new Class[0]), tf.a(var3, te.class, new Class[0])});
      this.n = (Publisher)this.l.a(Publisher.class);
   }

   @Nullable
   @PublicApi
   public static FirebaseApp a(Context param0) {
      // $FF: Couldn't be decompiled
   }

   @PublicApi
   public static FirebaseApp a(Context var0, te var1) {
      return a(var0, var1, "[DEFAULT]");
   }

   @PublicApi
   public static FirebaseApp a(Context param0, te param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static void a(FirebaseApp var0) {
      var0.j();
   }

   // $FF: synthetic method
   static void a(FirebaseApp var0, boolean var1) {
      var0.a(var1);
   }

   private static <T extends Object> void a(Class<T> param0, T param1, Iterable<String> param2, boolean param3) {
      // $FF: Couldn't be decompiled
   }

   private void a(boolean var1) {
      Log.d("FirebaseApp", "Notifying background state change listeners.");
      Iterator var2 = this.s.iterator();

      while(var2.hasNext()) {
         ((FirebaseApp.BackgroundStateChangeListener)var2.next()).a(var1);
      }

   }

   // $FF: synthetic method
   static AtomicBoolean b(FirebaseApp var0) {
      return var0.o;
   }

   @Nullable
   @PublicApi
   public static FirebaseApp d() {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static Object g() {
      return g;
   }

   private boolean h() {
      // $FF: Couldn't be decompiled
   }

   private void i() {
      Preconditions.checkState(this.p.get() ^ true, "FirebaseApp was deleted");
   }

   private void j() {
      boolean var1 = ContextCompat.isDeviceProtectedStorage(this.i);
      if(var1) {
         FirebaseApp.c.a(this.i);
      } else {
         this.l.a(this.f());
      }

      a(FirebaseApp.class, this, b, var1);
      if(this.f()) {
         a(FirebaseApp.class, this, c, var1);
         a(Context.class, this.i, d, var1);
      }

   }

   @NonNull
   @PublicApi
   public Context a() {
      this.i();
      return this.i;
   }

   @KeepForSdk
   public <T extends Object> T a(Class<T> var1) {
      this.i();
      return this.l.a(var1);
   }

   @NonNull
   @PublicApi
   public String b() {
      this.i();
      return this.j;
   }

   @NonNull
   @PublicApi
   public te c() {
      this.i();
      return this.k;
   }

   @KeepForSdk
   public boolean e() {
      this.i();
      return this.q.get();
   }

   public boolean equals(Object var1) {
      return !(var1 instanceof FirebaseApp)?false:this.j.equals(((FirebaseApp)var1).b());
   }

   @VisibleForTesting
   @KeepForSdk
   public boolean f() {
      return "[DEFAULT]".equals(this.b());
   }

   public int hashCode() {
      return this.j.hashCode();
   }

   public String toString() {
      return Objects.toStringHelper(this).add("name", this.j).add("options", this.k).toString();
   }

   static final class b implements Executor {

      private static final Handler a = new Handler(Looper.getMainLooper());


      private b() {}

      // $FF: synthetic method
      b(byte var1) {
         this();
      }

      public final void execute(@NonNull Runnable var1) {
         a.post(var1);
      }
   }

   @KeepForSdk
   public interface BackgroundStateChangeListener {

      @KeepForSdk
      void a(boolean var1);
   }

   @TargetApi(24)
   static final class c extends BroadcastReceiver {

      private static AtomicReference<FirebaseApp.c> a = new AtomicReference();
      private final Context b;


      private c(Context var1) {
         this.b = var1;
      }

      // $FF: synthetic method
      static void a(Context var0) {
         if(a.get() == null) {
            FirebaseApp.c var1 = new FirebaseApp.c(var0);
            if(a.compareAndSet((Object)null, var1)) {
               var0.registerReceiver(var1, new IntentFilter("android.intent.action.USER_UNLOCKED"));
            }
         }

      }

      public final void onReceive(Context param1, Intent param2) {
         // $FF: Couldn't be decompiled
      }
   }

   @Deprecated
   @KeepForSdk
   public interface IdTokenListenersCountChangedListener {
   }

   @Deprecated
   @KeepForSdk
   public interface IdTokenListener {
   }

   @TargetApi(14)
   static final class a implements BackgroundDetector.BackgroundStateChangeListener {

      private static AtomicReference<FirebaseApp.a> a = new AtomicReference();


      // $FF: synthetic method
      static void a(Context var0) {
         if(PlatformVersion.isAtLeastIceCreamSandwich()) {
            if(var0.getApplicationContext() instanceof Application) {
               Application var2 = (Application)var0.getApplicationContext();
               if(a.get() == null) {
                  FirebaseApp.a var1 = new FirebaseApp.a();
                  if(a.compareAndSet((Object)null, var1)) {
                     BackgroundDetector.initialize(var2);
                     BackgroundDetector.getInstance().addListener(var1);
                  }
               }

            }
         }
      }

      public final void onBackgroundStateChanged(boolean param1) {
         // $FF: Couldn't be decompiled
      }
   }
}
