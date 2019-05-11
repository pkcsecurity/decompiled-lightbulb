package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamite.zzk;
import com.google.android.gms.internal.common.zza;

public final class zzl extends zza implements zzk {

   zzl(IBinder var1) {
      super(var1, "com.google.android.gms.dynamite.IDynamiteLoaderV2");
   }

   public final IObjectWrapper a(IObjectWrapper var1, String var2, int var3, IObjectWrapper var4) throws RemoteException {
      Parcel var5 = this.zza();
      gi.a(var5, var1);
      var5.writeString(var2);
      var5.writeInt(var3);
      gi.a(var5, var4);
      Parcel var6 = this.zza(2, var5);
      IObjectWrapper var7 = IObjectWrapper.Stub.a(var6.readStrongBinder());
      var6.recycle();
      return var7;
   }

   public final IObjectWrapper b(IObjectWrapper var1, String var2, int var3, IObjectWrapper var4) throws RemoteException {
      Parcel var5 = this.zza();
      gi.a(var5, var1);
      var5.writeString(var2);
      var5.writeInt(var3);
      gi.a(var5, var4);
      Parcel var6 = this.zza(3, var5);
      IObjectWrapper var7 = IObjectWrapper.Stub.a(var6.readStrongBinder());
      var6.recycle();
      return var7;
   }
}
