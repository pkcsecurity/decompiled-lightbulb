package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzah;
import com.google.android.gms.maps.internal.zzai;

public interface ILocationSourceDelegate extends IInterface {

   void a() throws RemoteException;

   void a(zzah var1) throws RemoteException;

   public abstract static class zza extends com.google.android.gms.internal.maps.zzb implements ILocationSourceDelegate {

      public zza() {
         super("com.google.android.gms.maps.internal.ILocationSourceDelegate");
      }

      protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         switch(var1) {
         case 1:
            IBinder var6 = var2.readStrongBinder();
            Object var7;
            if(var6 == null) {
               var7 = null;
            } else {
               IInterface var5 = var6.queryLocalInterface("com.google.android.gms.maps.internal.IOnLocationChangeListener");
               if(var5 instanceof zzah) {
                  var7 = (zzah)var5;
               } else {
                  var7 = new zzai(var6);
               }
            }

            this.a((zzah)var7);
            break;
         case 2:
            this.a();
            break;
         default:
            return false;
         }

         var3.writeNoException();
         return true;
      }
   }
}
