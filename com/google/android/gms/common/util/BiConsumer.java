package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
public interface BiConsumer<T extends Object, U extends Object> {

   @KeepForSdk
   void accept(T var1, U var2);
}
