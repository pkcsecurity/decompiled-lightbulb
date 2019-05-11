package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.internal.maps.zzz;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public final class zzab extends zza implements zzz {

   zzab(IBinder var1) {
      super(var1, "com.google.android.gms.maps.model.internal.IPolylineDelegate");
   }

   public final void a(float var1) throws RemoteException {
      Parcel var2 = this.B_();
      var2.writeFloat(var1);
      this.b(5, var2);
   }

   public final void a(int var1) throws RemoteException {
      Parcel var2 = this.B_();
      var2.writeInt(var1);
      this.b(7, var2);
   }

   public final void a(List<LatLng> var1) throws RemoteException {
      Parcel var2 = this.B_();
      var2.writeTypedList(var1);
      this.b(3, var2);
   }

   public final void a(boolean var1) throws RemoteException {
      Parcel var2 = this.B_();
      hs.a(var2, var1);
      this.b(13, var2);
   }

   public final boolean a(zzz var1) throws RemoteException {
      Parcel var3 = this.B_();
      hs.a(var3, var1);
      Parcel var4 = this.a(15, var3);
      boolean var2 = hs.a(var4);
      var4.recycle();
      return var2;
   }

   public final void b() throws RemoteException {
      this.b(1, this.B_());
   }

   public final void b(float var1) throws RemoteException {
      Parcel var2 = this.B_();
      var2.writeFloat(var1);
      this.b(9, var2);
   }

   public final int c() throws RemoteException {
      Parcel var2 = this.a(16, this.B_());
      int var1 = var2.readInt();
      var2.recycle();
      return var1;
   }
}
