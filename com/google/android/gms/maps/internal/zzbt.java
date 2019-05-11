package com.google.android.gms.maps.internal;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.internal.zzbs;

public abstract class zzbt extends com.google.android.gms.internal.maps.zzb implements zzbs {

   public zzbt() {
      super("com.google.android.gms.maps.internal.ISnapshotReadyCallback");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      switch(var1) {
      case 1:
         this.a((Bitmap)hs.a(var2, Bitmap.CREATOR));
         break;
      case 2:
         this.a(IObjectWrapper.Stub.a(var2.readStrongBinder()));
         break;
      default:
         return false;
      }

      var3.writeNoException();
      return true;
   }
}
