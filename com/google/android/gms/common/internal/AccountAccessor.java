package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.IAccountAccessor;

public class AccountAccessor extends IAccountAccessor.Stub {

   @KeepForSdk
   public static Account getAccountBinderSafe(IAccountAccessor var0) {
      if(var0 != null) {
         long var1 = Binder.clearCallingIdentity();

         Account var7;
         try {
            var7 = var0.getAccount();
         } catch (RemoteException var5) {
            Log.w("AccountAccessor", "Remote account accessor probably died");
            return null;
         } finally {
            Binder.restoreCallingIdentity(var1);
         }

         return var7;
      } else {
         return null;
      }
   }

   public boolean equals(Object var1) {
      throw new NoSuchMethodError();
   }

   public final Account getAccount() {
      throw new NoSuchMethodError();
   }
}
