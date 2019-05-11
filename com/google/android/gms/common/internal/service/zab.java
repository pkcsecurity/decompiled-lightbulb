package com.google.android.gms.common.internal.service;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.service.zai;

final class zab extends Api.AbstractClientBuilder<zai, Api.NoOptions> {

   // $FF: synthetic method
   public final Api.Client buildClient(Context var1, Looper var2, ClientSettings var3, Object var4, GoogleApiClient.ConnectionCallbacks var5, GoogleApiClient.OnConnectionFailedListener var6) {
      return new zai(var1, var2, var3, var5, var6);
   }
}
