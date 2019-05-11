package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.LifecycleActivity;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzc;
import java.io.FileDescriptor;
import java.io.PrintWriter;

@KeepForSdk
public class LifecycleCallback {

   @KeepForSdk
   protected final LifecycleFragment mLifecycleFragment;


   @KeepForSdk
   protected LifecycleCallback(LifecycleFragment var1) {
      this.mLifecycleFragment = var1;
   }

   @Keep
   private static LifecycleFragment getChimeraLifecycleFragmentImpl(LifecycleActivity var0) {
      throw new IllegalStateException("Method not available in SDK.");
   }

   @KeepForSdk
   public static LifecycleFragment getFragment(Activity var0) {
      return getFragment(new LifecycleActivity(var0));
   }

   @KeepForSdk
   public static LifecycleFragment getFragment(ContextWrapper var0) {
      throw new UnsupportedOperationException();
   }

   @KeepForSdk
   protected static LifecycleFragment getFragment(LifecycleActivity var0) {
      if(var0.isSupport()) {
         return zzc.zza(var0.asFragmentActivity());
      } else if(var0.zzh()) {
         return zza.zza(var0.asActivity());
      } else {
         throw new IllegalArgumentException("Can\'t get fragment for unexpected activity.");
      }
   }

   @MainThread
   @KeepForSdk
   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {}

   @KeepForSdk
   public Activity getActivity() {
      return this.mLifecycleFragment.getLifecycleActivity();
   }

   @MainThread
   @KeepForSdk
   public void onActivityResult(int var1, int var2, Intent var3) {}

   @MainThread
   @KeepForSdk
   public void onCreate(Bundle var1) {}

   @MainThread
   @KeepForSdk
   public void onDestroy() {}

   @MainThread
   @KeepForSdk
   public void onResume() {}

   @MainThread
   @KeepForSdk
   public void onSaveInstanceState(Bundle var1) {}

   @MainThread
   @KeepForSdk
   public void onStart() {}

   @MainThread
   @KeepForSdk
   public void onStop() {}
}
