package com.google.android.gms.common.internal.service;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.service.zal;
import com.google.android.gms.common.internal.service.zam;

public final class zai extends GmsClient<zal> {

   public zai(Context var1, Looper var2, ClientSettings var3, GoogleApiClient.ConnectionCallbacks var4, GoogleApiClient.OnConnectionFailedListener var5) {
      super(var1, var2, 39, var3, var4, var5);
   }

   // $FF: synthetic method
   protected final IInterface createServiceInterface(IBinder var1) {
      if(var1 == null) {
         return null;
      } else {
         IInterface var2 = var1.queryLocalInterface("com.google.android.gms.common.internal.service.ICommonService");
         return (IInterface)(var2 instanceof zal?(zal)var2:new zam(var1));
      }
   }

   protected final String getServiceDescriptor() {
      return "com.google.android.gms.common.internal.service.ICommonService";
   }

   public final String getStartServiceAction() {
      return "com.google.android.gms.common.service.START";
   }
}
