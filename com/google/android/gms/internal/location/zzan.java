package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.location.zzam;
import com.google.android.gms.internal.location.zzb;

public abstract class zzan extends zzb implements zzam {

   public zzan() {
      super("com.google.android.gms.location.internal.IGeofencerCallbacks");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      switch(var1) {
      case 1:
         this.a(var2.readInt(), var2.createStringArray());
         break;
      case 2:
         this.b(var2.readInt(), var2.createStringArray());
         break;
      case 3:
         this.a(var2.readInt(), (PendingIntent)hk.a(var2, PendingIntent.CREATOR));
         break;
      default:
         return false;
      }

      return true;
   }
}
