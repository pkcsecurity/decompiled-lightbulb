package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;

public class SimpleClientAdapter<T extends Object & IInterface> extends GmsClient<T> {

   private final Api.SimpleClient<T> zapf;


   public SimpleClientAdapter(Context var1, Looper var2, int var3, GoogleApiClient.ConnectionCallbacks var4, GoogleApiClient.OnConnectionFailedListener var5, ClientSettings var6, Api.SimpleClient<T> var7) {
      super(var1, var2, var3, var6, var4, var5);
      this.zapf = var7;
   }

   protected T createServiceInterface(IBinder var1) {
      return this.zapf.createServiceInterface(var1);
   }

   public Api.SimpleClient<T> getClient() {
      return this.zapf;
   }

   public int getMinApkVersion() {
      return super.getMinApkVersion();
   }

   protected String getServiceDescriptor() {
      return this.zapf.getServiceDescriptor();
   }

   protected String getStartServiceAction() {
      return this.zapf.getStartServiceAction();
   }

   protected void onSetConnectState(int var1, T var2) {
      this.zapf.setState(var1, var2);
   }
}
