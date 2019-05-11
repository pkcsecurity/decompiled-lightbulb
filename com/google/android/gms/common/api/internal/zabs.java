package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.SignInConnectionListener;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public interface zabs {

   ConnectionResult blockingConnect();

   ConnectionResult blockingConnect(long var1, TimeUnit var3);

   void connect();

   void disconnect();

   void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

   <A extends Object & Api.AnyClient, R extends Object & Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T var1);

   <A extends Object & Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T var1);

   @Nullable
   ConnectionResult getConnectionResult(@NonNull Api<?> var1);

   boolean isConnected();

   boolean isConnecting();

   boolean maybeSignIn(SignInConnectionListener var1);

   void maybeSignOut();

   void zaw();
}
