package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamite.zzi;
import com.google.android.gms.internal.common.zza;

public final class zzj extends zza implements zzi {

   zzj(IBinder var1) {
      super(var1, "com.google.android.gms.dynamite.IDynamiteLoader");
   }

   public final int a() throws RemoteException {
      Parcel var2 = this.zza(6, this.zza());
      int var1 = var2.readInt();
      var2.recycle();
      return var1;
   }

   public final int a(IObjectWrapper var1, String var2, boolean var3) throws RemoteException {
      Parcel var5 = this.zza();
      gi.a(var5, var1);
      var5.writeString(var2);
      gi.a(var5, var3);
      Parcel var6 = this.zza(3, var5);
      int var4 = var6.readInt();
      var6.recycle();
      return var4;
   }

   public final IObjectWrapper a(IObjectWrapper var1, String var2, int var3) throws RemoteException {
      Parcel var4 = this.zza();
      gi.a(var4, var1);
      var4.writeString(var2);
      var4.writeInt(var3);
      Parcel var5 = this.zza(2, var4);
      IObjectWrapper var6 = IObjectWrapper.Stub.a(var5.readStrongBinder());
      var5.recycle();
      return var6;
   }

   public final int b(IObjectWrapper var1, String var2, boolean var3) throws RemoteException {
      Parcel var5 = this.zza();
      gi.a(var5, var1);
      var5.writeString(var2);
      gi.a(var5, var3);
      Parcel var6 = this.zza(5, var5);
      int var4 = var6.readInt();
      var6.recycle();
      return var4;
   }

   public final IObjectWrapper b(IObjectWrapper var1, String var2, int var3) throws RemoteException {
      Parcel var4 = this.zza();
      gi.a(var4, var1);
      var4.writeString(var2);
      var4.writeInt(var3);
      Parcel var5 = this.zza(4, var4);
      IObjectWrapper var6 = IObjectWrapper.Stub.a(var5.readStrongBinder());
      var5.recycle();
      return var6;
   }
}
