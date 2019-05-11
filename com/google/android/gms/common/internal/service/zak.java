package com.google.android.gms.common.internal.service;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.service.zaj;

public abstract class zak extends com.google.android.gms.internal.base.zab implements zaj {

   public zak() {
      super("com.google.android.gms.common.internal.service.ICommonCallbacks");
   }

   protected boolean dispatchTransaction(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.zaj(var2.readInt());
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
