package com.google.firebase.components;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.components.ComponentContainer;

@KeepForSdk
public interface ComponentFactory<T extends Object> {

   @KeepForSdk
   T a(ComponentContainer var1);
}
