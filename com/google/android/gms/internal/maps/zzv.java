package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.maps.model.LatLng;

public final class zzv extends zza implements zzt {

   zzv(IBinder var1) {
      super(var1, "com.google.android.gms.maps.model.internal.IMarkerDelegate");
   }

   public final void a() throws RemoteException {
      this.b(1, this.B_());
   }

   public final void a(float var1) throws RemoteException {
      Parcel var2 = this.B_();
      var2.writeFloat(var1);
      this.b(22, var2);
   }

   public final void a(float var1, float var2) throws RemoteException {
      Parcel var3 = this.B_();
      var3.writeFloat(var1);
      var3.writeFloat(var2);
      this.b(19, var3);
   }

   public final void a(IObjectWrapper var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(18, var2);
   }

   public final void a(LatLng var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(3, var2);
   }

   public final void a(String var1) throws RemoteException {
      Parcel var2 = this.B_();
      var2.writeString(var1);
      this.b(5, var2);
   }

   public final void a(boolean var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(9, var2);
   }

   public final boolean a(zzt var1) throws RemoteException {
      Parcel var3 = this.B_();
      hs.a(var3, var1);
      Parcel var4 = this.a(16, var3);
      boolean var2 = hs.a(var4);
      var4.recycle();
      return var2;
   }

   public final LatLng b() throws RemoteException {
      Parcel var1 = this.a(4, this.B_());
      LatLng var2 = (LatLng)hs.a(var1, LatLng.CREATOR);
      var1.recycle();
      return var2;
   }

   public final void b(float var1, float var2) throws RemoteException {
      Parcel var3 = this.B_();
      var3.writeFloat(var1);
      var3.writeFloat(var2);
      this.b(24, var3);
   }

   public final void b(String var1) throws RemoteException {
      Parcel var2 = this.B_();
      var2.writeString(var1);
      this.b(7, var2);
   }

   public final void b(boolean var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(20, var2);
   }

   public final void c() throws RemoteException {
      this.b(11, this.B_());
   }

   public final void d() throws RemoteException {
      this.b(12, this.B_());
   }

   public final int e() throws RemoteException {
      Parcel var2 = this.a(17, this.B_());
      int var1 = var2.readInt();
      var2.recycle();
      return var1;
   }
}
