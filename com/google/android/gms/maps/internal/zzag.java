package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzaf;

public abstract class zzag extends com.google.android.gms.internal.maps.zzb implements zzaf {

   public zzag() {
      super("com.google.android.gms.maps.internal.IOnInfoWindowLongClickListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a(com.google.android.gms.internal.maps.zzu.a(var2.readStrongBinder()));
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
