package com.google.android.gms.signin.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.signin.internal.zad;
import com.google.android.gms.signin.internal.zaf;
import com.google.android.gms.signin.internal.zah;

public final class zag extends com.google.android.gms.internal.base.zaa implements zaf {

   public zag(IBinder var1) {
      super(var1, "com.google.android.gms.signin.internal.ISignInService");
   }

   public final void a(int var1) throws RemoteException {
      Parcel var2 = this.zaa();
      var2.writeInt(var1);
      this.zab(7, var2);
   }

   public final void a(IAccountAccessor var1, int var2, boolean var3) throws RemoteException {
      Parcel var4 = this.zaa();
      gb.a(var4, var1);
      var4.writeInt(var2);
      gb.a(var4, var3);
      this.zab(9, var4);
   }

   public final void a(zah var1, zad var2) throws RemoteException {
      Parcel var3 = this.zaa();
      gb.a(var3, var1);
      gb.a(var3, var2);
      this.zab(12, var3);
   }
}
