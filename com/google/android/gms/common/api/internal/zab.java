package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.zaab;
import com.google.android.gms.common.util.PlatformVersion;

public abstract class zab {

   private final int type;


   public zab(int var1) {
      this.type = var1;
   }

   private static Status zaa(RemoteException var0) {
      StringBuilder var1 = new StringBuilder();
      if(PlatformVersion.isAtLeastIceCreamSandwichMR1() && var0 instanceof TransactionTooLargeException) {
         var1.append("TransactionTooLargeException: ");
      }

      var1.append(var0.getLocalizedMessage());
      return new Status(8, var1.toString());
   }

   // $FF: synthetic method
   static Status zab(RemoteException var0) {
      return zaa(var0);
   }

   public abstract void zaa(@NonNull Status var1);

   public abstract void zaa(GoogleApiManager.zaa<?> var1) throws DeadObjectException;

   public abstract void zaa(@NonNull zaab var1, boolean var2);

   public abstract void zaa(@NonNull RuntimeException var1);
}
