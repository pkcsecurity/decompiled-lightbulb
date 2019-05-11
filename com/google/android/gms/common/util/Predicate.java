package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
public interface Predicate<T extends Object> {

   @KeepForSdk
   boolean apply(T var1);
}
