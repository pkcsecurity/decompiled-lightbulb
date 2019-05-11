package com.google.android.gms.common.api.internal;

import android.app.Activity;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.zaa;

@KeepForSdk
public abstract class ActivityLifecycleObserver {

   @KeepForSdk
   public static final ActivityLifecycleObserver of(Activity var0) {
      return new zaa(var0);
   }

   @KeepForSdk
   public abstract ActivityLifecycleObserver onStopCallOnce(Runnable var1);
}
