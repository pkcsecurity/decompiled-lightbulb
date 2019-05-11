package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.zaab;
import com.google.android.gms.common.api.internal.zab;
import com.google.android.gms.common.api.internal.zac;

abstract class zad<T extends Object> extends zac {

   protected final li<T> zacm;


   public zad(int var1, li<T> var2) {
      super(var1);
      this.zacm = var2;
   }

   public void zaa(@NonNull Status var1) {
      this.zacm.b(new ApiException(var1));
   }

   public final void zaa(GoogleApiManager.zaa<?> var1) throws DeadObjectException {
      try {
         this.zad(var1);
      } catch (DeadObjectException var2) {
         this.zaa(zab.zab(var2));
         throw var2;
      } catch (RemoteException var3) {
         this.zaa(zab.zab(var3));
      } catch (RuntimeException var4) {
         this.zaa(var4);
      }
   }

   public void zaa(@NonNull zaab var1, boolean var2) {}

   public void zaa(@NonNull RuntimeException var1) {
      this.zacm.b(var1);
   }

   protected abstract void zad(GoogleApiManager.zaa<?> var1) throws RemoteException;
}
