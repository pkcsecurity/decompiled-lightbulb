package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.internal.zzb;
import com.google.android.gms.maps.internal.zzbv;
import com.google.android.gms.maps.internal.zzbw;
import com.google.android.gms.maps.internal.zze;
import com.google.android.gms.maps.internal.zzj;
import com.google.android.gms.maps.internal.zzk;

public final class zzf extends zza implements zze {

   public zzf(IBinder var1) {
      super(var1, "com.google.android.gms.maps.internal.ICreator");
   }

   public final ICameraUpdateFactoryDelegate a() throws RemoteException {
      Parcel var2 = this.a(4, this.B_());
      IBinder var1 = var2.readStrongBinder();
      Object var4;
      if(var1 == null) {
         var4 = null;
      } else {
         IInterface var3 = var1.queryLocalInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
         if(var3 instanceof ICameraUpdateFactoryDelegate) {
            var4 = (ICameraUpdateFactoryDelegate)var3;
         } else {
            var4 = new zzb(var1);
         }
      }

      var2.recycle();
      return (ICameraUpdateFactoryDelegate)var4;
   }

   public final IMapFragmentDelegate a(IObjectWrapper var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      var2 = this.a(2, var2);
      IBinder var4 = var2.readStrongBinder();
      Object var5;
      if(var4 == null) {
         var5 = null;
      } else {
         IInterface var3 = var4.queryLocalInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
         if(var3 instanceof IMapFragmentDelegate) {
            var5 = (IMapFragmentDelegate)var3;
         } else {
            var5 = new zzj(var4);
         }
      }

      var2.recycle();
      return (IMapFragmentDelegate)var5;
   }

   public final IMapViewDelegate a(IObjectWrapper var1, GoogleMapOptions var2) throws RemoteException {
      Parcel var3 = this.B_();
      hs.a(var3, var1);
      hs.a(var3, var2);
      Parcel var6 = this.a(3, var3);
      IBinder var4 = var6.readStrongBinder();
      Object var5;
      if(var4 == null) {
         var5 = null;
      } else {
         IInterface var7 = var4.queryLocalInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
         if(var7 instanceof IMapViewDelegate) {
            var5 = (IMapViewDelegate)var7;
         } else {
            var5 = new zzk(var4);
         }
      }

      var6.recycle();
      return (IMapViewDelegate)var5;
   }

   public final IStreetViewPanoramaViewDelegate a(IObjectWrapper var1, StreetViewPanoramaOptions var2) throws RemoteException {
      Parcel var3 = this.B_();
      hs.a(var3, var1);
      hs.a(var3, var2);
      Parcel var6 = this.a(7, var3);
      IBinder var4 = var6.readStrongBinder();
      Object var5;
      if(var4 == null) {
         var5 = null;
      } else {
         IInterface var7 = var4.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
         if(var7 instanceof IStreetViewPanoramaViewDelegate) {
            var5 = (IStreetViewPanoramaViewDelegate)var7;
         } else {
            var5 = new zzbw(var4);
         }
      }

      var6.recycle();
      return (IStreetViewPanoramaViewDelegate)var5;
   }

   public final void a(IObjectWrapper var1, int var2) throws RemoteException {
      Parcel var3 = this.B_();
      hs.a(var3, var1);
      var3.writeInt(var2);
      this.b(6, var3);
   }

   public final com.google.android.gms.internal.maps.zze b() throws RemoteException {
      Parcel var1 = this.a(5, this.B_());
      com.google.android.gms.internal.maps.zze var2 = com.google.android.gms.internal.maps.zzf.a(var1.readStrongBinder());
      var1.recycle();
      return var2;
   }

   public final IStreetViewPanoramaFragmentDelegate b(IObjectWrapper var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      var2 = this.a(8, var2);
      IBinder var4 = var2.readStrongBinder();
      Object var5;
      if(var4 == null) {
         var5 = null;
      } else {
         IInterface var3 = var4.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate");
         if(var3 instanceof IStreetViewPanoramaFragmentDelegate) {
            var5 = (IStreetViewPanoramaFragmentDelegate)var3;
         } else {
            var5 = new zzbv(var4);
         }
      }

      var2.recycle();
      return (IStreetViewPanoramaFragmentDelegate)var5;
   }
}
