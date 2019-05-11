package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IProjectionDelegate;
import com.google.android.gms.maps.internal.IUiSettingsDelegate;
import com.google.android.gms.maps.internal.zzab;
import com.google.android.gms.maps.internal.zzaj;
import com.google.android.gms.maps.internal.zzal;
import com.google.android.gms.maps.internal.zzan;
import com.google.android.gms.maps.internal.zzar;
import com.google.android.gms.maps.internal.zzat;
import com.google.android.gms.maps.internal.zzbr;
import com.google.android.gms.maps.internal.zzbs;
import com.google.android.gms.maps.internal.zzbx;
import com.google.android.gms.maps.internal.zzc;
import com.google.android.gms.maps.internal.zzh;
import com.google.android.gms.maps.internal.zzl;
import com.google.android.gms.maps.internal.zzn;
import com.google.android.gms.maps.internal.zzr;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public final class zzg extends zza implements IGoogleMapDelegate {

   zzg(IBinder var1) {
      super(var1, "com.google.android.gms.maps.internal.IGoogleMapDelegate");
   }

   public final com.google.android.gms.internal.maps.zzh a(CircleOptions var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      Parcel var3 = this.a(35, var2);
      com.google.android.gms.internal.maps.zzh var4 = com.google.android.gms.internal.maps.zzi.a(var3.readStrongBinder());
      var3.recycle();
      return var4;
   }

   public final com.google.android.gms.internal.maps.zzt a(MarkerOptions var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      Parcel var3 = this.a(11, var2);
      com.google.android.gms.internal.maps.zzt var4 = com.google.android.gms.internal.maps.zzu.a(var3.readStrongBinder());
      var3.recycle();
      return var4;
   }

   public final com.google.android.gms.internal.maps.zzw a(PolygonOptions var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      Parcel var3 = this.a(10, var2);
      com.google.android.gms.internal.maps.zzw var4 = com.google.android.gms.internal.maps.zzx.a(var3.readStrongBinder());
      var3.recycle();
      return var4;
   }

   public final com.google.android.gms.internal.maps.zzz a(PolylineOptions var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      Parcel var3 = this.a(9, var2);
      com.google.android.gms.internal.maps.zzz var4 = com.google.android.gms.internal.maps.zzaa.a(var3.readStrongBinder());
      var3.recycle();
      return var4;
   }

   public final CameraPosition a() throws RemoteException {
      Parcel var1 = this.a(1, this.B_());
      CameraPosition var2 = (CameraPosition)hs.a(var1, CameraPosition.CREATOR);
      var1.recycle();
      return var2;
   }

   public final void a(int var1) throws RemoteException {
      Parcel var2 = this.B_();
      var2.writeInt(var1);
      this.b(16, var2);
   }

   public final void a(IObjectWrapper var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(4, var2);
   }

   public final void a(IObjectWrapper var1, int var2, zzc var3) throws RemoteException {
      Parcel var4 = this.B_();
      hs.a(var4, var1);
      var4.writeInt(var2);
      hs.a(var4, var3);
      this.b(7, var4);
   }

   public final void a(zzab var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(32, var2);
   }

   public final void a(zzaj var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(28, var2);
   }

   public final void a(zzal var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(42, var2);
   }

   public final void a(zzan var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(29, var2);
   }

   public final void a(zzar var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(30, var2);
   }

   public final void a(zzat var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(31, var2);
   }

   public final void a(zzbs var1, IObjectWrapper var2) throws RemoteException {
      Parcel var3 = this.B_();
      hs.a(var3, var1);
      hs.a(var3, var2);
      this.b(38, var3);
   }

   public final void a(zzh var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(33, var2);
   }

   public final void a(zzl var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(27, var2);
   }

   public final void a(zzn var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(99, var2);
   }

   public final void a(zzr var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(97, var2);
   }

   public final void a(boolean var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(18, var2);
   }

   public final IUiSettingsDelegate b() throws RemoteException {
      Parcel var2 = this.a(25, this.B_());
      IBinder var1 = var2.readStrongBinder();
      Object var4;
      if(var1 == null) {
         var4 = null;
      } else {
         IInterface var3 = var1.queryLocalInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
         if(var3 instanceof IUiSettingsDelegate) {
            var4 = (IUiSettingsDelegate)var3;
         } else {
            var4 = new zzbx(var1);
         }
      }

      var2.recycle();
      return (IUiSettingsDelegate)var4;
   }

   public final void b(IObjectWrapper var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(5, var2);
   }

   public final boolean b(boolean var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      var2 = this.a(20, var2);
      var1 = hs.a(var2);
      var2.recycle();
      return var1;
   }

   public final IProjectionDelegate c() throws RemoteException {
      Parcel var2 = this.a(26, this.B_());
      IBinder var1 = var2.readStrongBinder();
      Object var4;
      if(var1 == null) {
         var4 = null;
      } else {
         IInterface var3 = var1.queryLocalInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
         if(var3 instanceof IProjectionDelegate) {
            var4 = (IProjectionDelegate)var3;
         } else {
            var4 = new zzbr(var1);
         }
      }

      var2.recycle();
      return (IProjectionDelegate)var4;
   }

   public final void c(boolean var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(22, var2);
   }

   public final void d(boolean var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(41, var2);
   }
}
