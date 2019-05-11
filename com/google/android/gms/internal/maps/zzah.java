package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.internal.maps.zzaf;
import com.google.android.gms.maps.model.Tile;

public final class zzah extends zza implements zzaf {

   zzah(IBinder var1) {
      super(var1, "com.google.android.gms.maps.model.internal.ITileProviderDelegate");
   }

   public final Tile a(int var1, int var2, int var3) throws RemoteException {
      Parcel var4 = this.B_();
      var4.writeInt(var1);
      var4.writeInt(var2);
      var4.writeInt(var3);
      var4 = this.a(1, var4);
      Tile var5 = (Tile)hs.a(var4, Tile.CREATOR);
      var4.recycle();
      return var5;
   }
}
