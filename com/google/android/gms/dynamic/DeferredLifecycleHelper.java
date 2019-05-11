package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ConnectionErrorMessages;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.OnDelegateCreatedListener;
import java.util.LinkedList;

@KeepForSdk
public abstract class DeferredLifecycleHelper<T extends Object & LifecycleDelegate> {

   private T a;
   private Bundle b;
   private LinkedList<DeferredLifecycleHelper.zaa> c;
   private final OnDelegateCreatedListener<T> d = new fm(this);


   // $FF: synthetic method
   public static Bundle a(DeferredLifecycleHelper var0, Bundle var1) {
      var0.b = null;
      return null;
   }

   // $FF: synthetic method
   public static LifecycleDelegate a(DeferredLifecycleHelper var0, LifecycleDelegate var1) {
      var0.a = var1;
      return var1;
   }

   // $FF: synthetic method
   public static LinkedList a(DeferredLifecycleHelper var0) {
      return var0.c;
   }

   private final void a(int var1) {
      while(!this.c.isEmpty() && ((DeferredLifecycleHelper.zaa)this.c.getLast()).a() >= var1) {
         this.c.removeLast();
      }

   }

   private final void a(Bundle var1, DeferredLifecycleHelper.zaa var2) {
      if(this.a != null) {
         var2.a(this.a);
      } else {
         if(this.c == null) {
            this.c = new LinkedList();
         }

         this.c.add(var2);
         if(var1 != null) {
            if(this.b == null) {
               this.b = (Bundle)var1.clone();
            } else {
               this.b.putAll(var1);
            }
         }

         this.a(this.d);
      }
   }

   // $FF: synthetic method
   public static LifecycleDelegate b(DeferredLifecycleHelper var0) {
      return var0.a;
   }

   @KeepForSdk
   public static void b(FrameLayout var0) {
      GoogleApiAvailability var5 = GoogleApiAvailability.getInstance();
      Context var2 = var0.getContext();
      int var1 = var5.isGooglePlayServicesAvailable(var2);
      String var6 = ConnectionErrorMessages.getErrorMessage(var2, var1);
      String var3 = ConnectionErrorMessages.getErrorDialogButtonMessage(var2, var1);
      LinearLayout var4 = new LinearLayout(var0.getContext());
      var4.setOrientation(1);
      var4.setLayoutParams(new LayoutParams(-2, -2));
      var0.addView(var4);
      TextView var7 = new TextView(var0.getContext());
      var7.setLayoutParams(new LayoutParams(-2, -2));
      var7.setText(var6);
      var4.addView(var7);
      Intent var8 = var5.getErrorResolutionIntent(var2, var1, (String)null);
      if(var8 != null) {
         Button var9 = new Button(var2);
         var9.setId(16908313);
         var9.setLayoutParams(new LayoutParams(-2, -2));
         var9.setText(var3);
         var4.addView(var9);
         var9.setOnClickListener(new fq(var2, var8));
      }

   }

   @KeepForSdk
   public View a(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      FrameLayout var4 = new FrameLayout(var1.getContext());
      this.a(var3, (DeferredLifecycleHelper.zaa)(new fp(this, var4, var1, var2, var3)));
      if(this.a == null) {
         this.a(var4);
      }

      return var4;
   }

   @KeepForSdk
   public T a() {
      return this.a;
   }

   @KeepForSdk
   public void a(Activity var1, Bundle var2, Bundle var3) {
      this.a(var3, (DeferredLifecycleHelper.zaa)(new fn(this, var1, var2, var3)));
   }

   @KeepForSdk
   public void a(Bundle var1) {
      this.a(var1, (DeferredLifecycleHelper.zaa)(new fo(this, var1)));
   }

   @KeepForSdk
   protected void a(FrameLayout var1) {
      b(var1);
   }

   @KeepForSdk
   public abstract void a(OnDelegateCreatedListener<T> var1);

   @KeepForSdk
   public void b() {
      this.a((Bundle)null, (DeferredLifecycleHelper.zaa)(new fr(this)));
   }

   @KeepForSdk
   public void b(Bundle var1) {
      if(this.a != null) {
         this.a.b(var1);
      } else {
         if(this.b != null) {
            var1.putAll(this.b);
         }

      }
   }

   @KeepForSdk
   public void c() {
      this.a((Bundle)null, (DeferredLifecycleHelper.zaa)(new fs(this)));
   }

   @KeepForSdk
   public void d() {
      if(this.a != null) {
         this.a.c();
      } else {
         this.a(5);
      }
   }

   @KeepForSdk
   public void e() {
      if(this.a != null) {
         this.a.d();
      } else {
         this.a(4);
      }
   }

   @KeepForSdk
   public void f() {
      if(this.a != null) {
         this.a.e();
      } else {
         this.a(2);
      }
   }

   @KeepForSdk
   public void g() {
      if(this.a != null) {
         this.a.f();
      } else {
         this.a(1);
      }
   }

   @KeepForSdk
   public void h() {
      if(this.a != null) {
         this.a.g();
      }

   }

   public interface zaa {

      int a();

      void a(LifecycleDelegate var1);
   }
}
