package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.zabw;
import com.google.android.gms.common.api.internal.zad;

public final class zah extends zad<Boolean> {

   private final ListenerHolder.ListenerKey<?> zacs;


   public zah(ListenerHolder.ListenerKey<?> var1, li<Boolean> var2) {
      super(4, var2);
      this.zacs = var1;
   }

   @Nullable
   public final Feature[] zab(GoogleApiManager.zaa<?> var1) {
      zabw var2 = (zabw)var1.zabk().get(this.zacs);
      return var2 == null?null:var2.zajw.getRequiredFeatures();
   }

   public final boolean zac(GoogleApiManager.zaa<?> var1) {
      zabw var2 = (zabw)var1.zabk().get(this.zacs);
      return var2 != null && var2.zajw.shouldAutoResolveMissingFeatures();
   }

   public final void zad(GoogleApiManager.zaa<?> var1) throws RemoteException {
      zabw var2 = (zabw)var1.zabk().remove(this.zacs);
      if(var2 != null) {
         var2.zajx.unregisterListener(var1.zaab(), this.zacm);
         var2.zajw.clearListener();
      } else {
         this.zacm.b(Boolean.valueOf(false));
      }
   }
}
