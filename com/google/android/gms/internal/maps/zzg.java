package com.google.android.gms.internal.maps;

import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.internal.maps.zze;

public final class zzg extends zza implements zze {

   zzg(IBinder var1) {
      super(var1, "com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
   }

   public final IObjectWrapper a(float var1) throws RemoteException {
      Parcel var2 = this.B_();
      var2.writeFloat(var1);
      var2 = this.a(5, var2);
      IObjectWrapper var3 = IObjectWrapper.Stub.a(var2.readStrongBinder());
      var2.recycle();
      return var3;
   }

   public final IObjectWrapper a(int var1) throws RemoteException {
      Parcel var2 = this.B_();
      var2.writeInt(var1);
      var2 = this.a(1, var2);
      IObjectWrapper var3 = IObjectWrapper.Stub.a(var2.readStrongBinder());
      var2.recycle();
      return var3;
   }

   public final IObjectWrapper a(Bitmap var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      Parcel var3 = this.a(6, var2);
      IObjectWrapper var4 = IObjectWrapper.Stub.a(var3.readStrongBinder());
      var3.recycle();
      return var4;
   }
}
