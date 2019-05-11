package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.LifecycleCallback;

@KeepForSdk
public interface LifecycleFragment {

   @KeepForSdk
   void addCallback(String var1, @NonNull LifecycleCallback var2);

   @KeepForSdk
   <T extends LifecycleCallback> T getCallbackOrNull(String var1, Class<T> var2);

   @KeepForSdk
   Activity getLifecycleActivity();

   @KeepForSdk
   boolean isCreated();

   @KeepForSdk
   boolean isStarted();

   @KeepForSdk
   void startActivityForResult(Intent var1, int var2);
}
