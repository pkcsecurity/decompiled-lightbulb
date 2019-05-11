package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzt;

public abstract class zzu extends com.google.android.gms.internal.maps.zzb implements zzt {

   public zzu() {
      super("com.google.android.gms.maps.internal.IOnCameraMoveStartedListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a(var2.readInt());
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
