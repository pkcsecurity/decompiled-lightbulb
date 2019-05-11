package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public final class zzb extends zza implements ICameraUpdateFactoryDelegate {

   zzb(IBinder var1) {
      super(var1, "com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
   }

   public final IObjectWrapper a(LatLng var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      Parcel var3 = this.a(8, var2);
      IObjectWrapper var4 = IObjectWrapper.Stub.a(var3.readStrongBinder());
      var3.recycle();
      return var4;
   }

   public final IObjectWrapper a(LatLng var1, float var2) throws RemoteException {
      Parcel var3 = this.B_();
      hs.a(var3, var1);
      var3.writeFloat(var2);
      Parcel var4 = this.a(9, var3);
      IObjectWrapper var5 = IObjectWrapper.Stub.a(var4.readStrongBinder());
      var4.recycle();
      return var5;
   }

   public final IObjectWrapper a(LatLngBounds var1, int var2) throws RemoteException {
      Parcel var3 = this.B_();
      hs.a(var3, var1);
      var3.writeInt(var2);
      Parcel var4 = this.a(10, var3);
      IObjectWrapper var5 = IObjectWrapper.Stub.a(var4.readStrongBinder());
      var4.recycle();
      return var5;
   }

   public final IObjectWrapper a(LatLngBounds var1, int var2, int var3, int var4) throws RemoteException {
      Parcel var5 = this.B_();
      hs.a(var5, var1);
      var5.writeInt(var2);
      var5.writeInt(var3);
      var5.writeInt(var4);
      Parcel var7 = this.a(11, var5);
      IObjectWrapper var6 = IObjectWrapper.Stub.a(var7.readStrongBinder());
      var7.recycle();
      return var6;
   }
}
