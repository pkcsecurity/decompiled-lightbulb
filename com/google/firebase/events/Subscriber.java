package com.google.firebase.events;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.events.EventHandler;

@KeepForSdk
public interface Subscriber {

   @KeepForSdk
   <T extends Object> void a(Class<T> var1, EventHandler<? super T> var2);
}
