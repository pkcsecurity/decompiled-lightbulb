package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.ContextWrapper;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
public class LifecycleActivity {

   private final Object zzbc;


   public LifecycleActivity(Activity var1) {
      Preconditions.checkNotNull(var1, "Activity must not be null");
      this.zzbc = var1;
   }

   @KeepForSdk
   public LifecycleActivity(ContextWrapper var1) {
      throw new UnsupportedOperationException();
   }

   @KeepForSdk
   public Activity asActivity() {
      return (Activity)this.zzbc;
   }

   @KeepForSdk
   public FragmentActivity asFragmentActivity() {
      return (FragmentActivity)this.zzbc;
   }

   @KeepForSdk
   public Object asObject() {
      return this.zzbc;
   }

   @KeepForSdk
   public boolean isChimera() {
      return false;
   }

   @KeepForSdk
   public boolean isSupport() {
      return this.zzbc instanceof FragmentActivity;
   }

   public final boolean zzh() {
      return this.zzbc instanceof Activity;
   }
}
