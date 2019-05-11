package com.google.firebase.components;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.inject.Provider;

@KeepForSdk
public interface ComponentContainer {

   @KeepForSdk
   <T extends Object> T a(Class<T> var1);

   @KeepForSdk
   <T extends Object> Provider<T> b(Class<T> var1);
}
