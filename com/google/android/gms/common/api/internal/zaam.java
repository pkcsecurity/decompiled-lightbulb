package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zaak;
import com.google.android.gms.common.internal.BaseGmsClient;
import java.lang.ref.WeakReference;

final class zaam implements BaseGmsClient.ConnectionProgressReportCallbacks {

   private final Api<?> mApi;
   private final boolean zaeb;
   private final WeakReference<zaak> zagj;


   public zaam(zaak var1, Api<?> var2, boolean var3) {
      this.zagj = new WeakReference(var1);
      this.mApi = var2;
      this.zaeb = var3;
   }

   // $FF: synthetic method
   static boolean zaa(zaam var0) {
      return var0.zaeb;
   }

   public final void onReportServiceBinding(@NonNull ConnectionResult param1) {
      // $FF: Couldn't be decompiled
   }
}
