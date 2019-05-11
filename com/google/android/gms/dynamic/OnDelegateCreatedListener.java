package com.google.android.gms.dynamic;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.dynamic.LifecycleDelegate;

@KeepForSdk
public interface OnDelegateCreatedListener<T extends Object & LifecycleDelegate> {

   @KeepForSdk
   void a(T var1);
}
