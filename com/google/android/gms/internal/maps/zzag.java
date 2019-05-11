package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.maps.zzaf;
import com.google.android.gms.internal.maps.zzah;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.maps.model.Tile;

public abstract class zzag extends zzb implements zzaf {

   public zzag() {
      super("com.google.android.gms.maps.model.internal.ITileProviderDelegate");
   }

   public static zzaf a(IBinder var0) {
      if(var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.maps.model.internal.ITileProviderDelegate");
         return (zzaf)(var1 instanceof zzaf?(zzaf)var1:new zzah(var0));
      }
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         Tile var5 = this.a(var2.readInt(), var2.readInt(), var2.readInt());
         var3.writeNoException();
         hs.b(var3, var5);
         return true;
      } else {
         return false;
      }
   }
}
