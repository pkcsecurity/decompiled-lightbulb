package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;

public interface zabd {

   void begin();

   void connect();

   boolean disconnect();

   <A extends Object & Api.AnyClient, R extends Object & Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(T var1);

   <A extends Object & Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(T var1);

   void onConnected(Bundle var1);

   void onConnectionSuspended(int var1);

   void zaa(ConnectionResult var1, Api<?> var2, boolean var3);
}
