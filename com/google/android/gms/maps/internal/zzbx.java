package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.maps.internal.IUiSettingsDelegate;

public final class zzbx extends zza implements IUiSettingsDelegate {

   zzbx(IBinder var1) {
      super(var1, "com.google.android.gms.maps.internal.IUiSettingsDelegate");
   }

   public final void a(boolean var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(2, var2);
   }

   public final void b(boolean var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(3, var2);
   }

   public final void c(boolean var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(4, var2);
   }

   public final void d(boolean var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(5, var2);
   }

   public final void e(boolean var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(6, var2);
   }

   public final void f(boolean var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(7, var2);
   }

   public final void g(boolean var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(18, var2);
   }
}
