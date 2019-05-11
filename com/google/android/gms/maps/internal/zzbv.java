package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.zzbp;

public final class zzbv extends zza implements IStreetViewPanoramaFragmentDelegate {

   zzbv(IBinder var1) {
      super(var1, "com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate");
   }

   public final IObjectWrapper a(IObjectWrapper var1, IObjectWrapper var2, Bundle var3) throws RemoteException {
      Parcel var4 = this.B_();
      hs.a(var4, var1);
      hs.a(var4, var2);
      hs.a(var4, var3);
      Parcel var5 = this.a(4, var4);
      var2 = IObjectWrapper.Stub.a(var5.readStrongBinder());
      var5.recycle();
      return var2;
   }

   public final void a() throws RemoteException {
      this.b(5, this.B_());
   }

   public final void a(Bundle var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(3, var2);
   }

   public final void a(IObjectWrapper var1, StreetViewPanoramaOptions var2, Bundle var3) throws RemoteException {
      Parcel var4 = this.B_();
      hs.a(var4, var1);
      hs.a(var4, var2);
      hs.a(var4, var3);
      this.b(2, var4);
   }

   public final void a(zzbp var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(12, var2);
   }

   public final void b() throws RemoteException {
      this.b(6, this.B_());
   }

   public final void b(Bundle var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      var2 = this.a(10, var2);
      if(var2.readInt() != 0) {
         var1.readFromParcel(var2);
      }

      var2.recycle();
   }

   public final void c() throws RemoteException {
      this.b(7, this.B_());
   }

   public final void d() throws RemoteException {
      this.b(8, this.B_());
   }

   public final void e() throws RemoteException {
      this.b(9, this.B_());
   }

   public final void f() throws RemoteException {
      this.b(13, this.B_());
   }

   public final void g() throws RemoteException {
      this.b(14, this.B_());
   }
}
