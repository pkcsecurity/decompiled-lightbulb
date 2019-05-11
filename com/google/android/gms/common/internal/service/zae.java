package com.google.android.gms.common.internal.service;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.service.zad;
import com.google.android.gms.common.internal.service.zaf;
import com.google.android.gms.common.internal.service.zah;
import com.google.android.gms.common.internal.service.zai;
import com.google.android.gms.common.internal.service.zal;

final class zae extends zah {

   zae(zad var1, GoogleApiClient var2) {
      super(var2);
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zal)((zai)var1).getService()).zaa(new zaf(this));
   }
}
