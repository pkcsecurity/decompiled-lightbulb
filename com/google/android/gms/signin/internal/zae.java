package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.base.zab;
import com.google.android.gms.signin.internal.zaa;
import com.google.android.gms.signin.internal.zad;
import com.google.android.gms.signin.internal.zaj;

public abstract class zae extends zab implements zad {

   public zae() {
      super("com.google.android.gms.signin.internal.ISignInCallbacks");
   }

   protected boolean dispatchTransaction(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      switch(var1) {
      case 3:
         this.zaa((ConnectionResult)gb.a(var2, ConnectionResult.CREATOR), (zaa)gb.a(var2, zaa.CREATOR));
         break;
      case 4:
         this.zag((Status)gb.a(var2, Status.CREATOR));
         break;
      case 5:
      default:
         return false;
      case 6:
         this.zah((Status)gb.a(var2, Status.CREATOR));
         break;
      case 7:
         this.zaa((Status)gb.a(var2, Status.CREATOR), (GoogleSignInAccount)gb.a(var2, GoogleSignInAccount.CREATOR));
         break;
      case 8:
         this.zab((zaj)gb.a(var2, zaj.CREATOR));
      }

      var3.writeNoException();
      return true;
   }
}
