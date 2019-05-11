package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzat;

public abstract class zzau extends com.google.android.gms.internal.maps.zzb implements zzat {

   public zzau() {
      super("com.google.android.gms.maps.internal.IOnMarkerDragListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      switch(var1) {
      case 1:
         this.a(com.google.android.gms.internal.maps.zzu.a(var2.readStrongBinder()));
         break;
      case 2:
         this.b(com.google.android.gms.internal.maps.zzu.a(var2.readStrongBinder()));
         break;
      case 3:
         this.c(com.google.android.gms.internal.maps.zzu.a(var2.readStrongBinder()));
         break;
      default:
         return false;
      }

      var3.writeNoException();
      return true;
   }
}
