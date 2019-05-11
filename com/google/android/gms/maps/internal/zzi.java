package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.internal.zzh;

public abstract class zzi extends com.google.android.gms.internal.maps.zzb implements zzh {

   public zzi() {
      super("com.google.android.gms.maps.internal.IInfoWindowAdapter");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      IObjectWrapper var5;
      switch(var1) {
      case 1:
         var5 = this.a(com.google.android.gms.internal.maps.zzu.a(var2.readStrongBinder()));
         var3.writeNoException();
         hs.a(var3, var5);
         break;
      case 2:
         var5 = this.b(com.google.android.gms.internal.maps.zzu.a(var2.readStrongBinder()));
         var3.writeNoException();
         hs.a(var3, var5);
         break;
      default:
         return false;
      }

      return true;
   }
}
