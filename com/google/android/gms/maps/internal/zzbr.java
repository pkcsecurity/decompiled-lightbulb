package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.maps.internal.IProjectionDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;

public final class zzbr extends zza implements IProjectionDelegate {

   zzbr(IBinder var1) {
      super(var1, "com.google.android.gms.maps.internal.IProjectionDelegate");
   }

   public final IObjectWrapper a(LatLng var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      Parcel var3 = this.a(2, var2);
      IObjectWrapper var4 = IObjectWrapper.Stub.a(var3.readStrongBinder());
      var3.recycle();
      return var4;
   }

   public final LatLng a(IObjectWrapper var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      Parcel var3 = this.a(1, var2);
      LatLng var4 = (LatLng)hs.a(var3, LatLng.CREATOR);
      var3.recycle();
      return var4;
   }

   public final VisibleRegion a() throws RemoteException {
      Parcel var1 = this.a(3, this.B_());
      VisibleRegion var2 = (VisibleRegion)hs.a(var1, VisibleRegion.CREATOR);
      var1.recycle();
      return var2;
   }
}
