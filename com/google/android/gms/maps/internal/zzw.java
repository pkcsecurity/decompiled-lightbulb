package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzv;

public abstract class zzw extends com.google.android.gms.internal.maps.zzb implements zzv {

   public zzw() {
      super("com.google.android.gms.maps.internal.IOnCircleClickListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a(com.google.android.gms.internal.maps.zzi.a(var2.readStrongBinder()));
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
