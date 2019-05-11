package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.internal.zzbp;

public final class zzbw extends zza implements IStreetViewPanoramaViewDelegate {

   zzbw(IBinder var1) {
      super(var1, "com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
   }

   public final void a() throws RemoteException {
      this.b(3, this.B_());
   }

   public final void a(Bundle var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(2, var2);
   }

   public final void a(zzbp var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(9, var2);
   }

   public final void b() throws RemoteException {
      this.b(4, this.B_());
   }

   public final void b(Bundle var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      var2 = this.a(7, var2);
      if(var2.readInt() != 0) {
         var1.readFromParcel(var2);
      }

      var2.recycle();
   }

   public final void c() throws RemoteException {
      this.b(5, this.B_());
   }

   public final void d() throws RemoteException {
      this.b(6, this.B_());
   }

   public final IObjectWrapper e() throws RemoteException {
      Parcel var1 = this.a(8, this.B_());
      IObjectWrapper var2 = IObjectWrapper.Stub.a(var1.readStrongBinder());
      var1.recycle();
      return var2;
   }

   public final void f() throws RemoteException {
      this.b(10, this.B_());
   }

   public final void g() throws RemoteException {
      this.b(11, this.B_());
   }
}
