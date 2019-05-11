package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;

@KeepForSdk
@ShowFirstParty
public interface Clock {

   @KeepForSdk
   long currentThreadTimeMillis();

   @KeepForSdk
   long currentTimeMillis();

   @KeepForSdk
   long elapsedRealtime();

   @KeepForSdk
   long nanoTime();
}