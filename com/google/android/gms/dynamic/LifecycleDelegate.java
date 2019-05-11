package com.google.android.gms.dynamic;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
public interface LifecycleDelegate {

   @KeepForSdk
   View a(LayoutInflater var1, ViewGroup var2, Bundle var3);

   @KeepForSdk
   void a();

   @KeepForSdk
   void a(Activity var1, Bundle var2, Bundle var3);

   @KeepForSdk
   void a(Bundle var1);

   @KeepForSdk
   void b();

   @KeepForSdk
   void b(Bundle var1);

   @KeepForSdk
   void c();

   @KeepForSdk
   void d();

   @KeepForSdk
   void e();

   @KeepForSdk
   void f();

   @KeepForSdk
   void g();
}
