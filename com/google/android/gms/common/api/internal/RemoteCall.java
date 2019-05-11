package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
public interface RemoteCall<T extends Object, U extends Object> {

   @KeepForSdk
   void accept(T var1, U var2) throws RemoteException;
}
