package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.ISignInButtonCreator;
import com.google.android.gms.common.internal.SignInButtonConfig;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class zah extends com.google.android.gms.internal.base.zaa implements ISignInButtonCreator {

   zah(IBinder var1) {
      super(var1, "com.google.android.gms.common.internal.ISignInButtonCreator");
   }

   public final IObjectWrapper newSignInButton(IObjectWrapper var1, int var2, int var3) throws RemoteException {
      Parcel var4 = this.zaa();
      gb.a(var4, var1);
      var4.writeInt(var2);
      var4.writeInt(var3);
      Parcel var5 = this.zaa(1, var4);
      IObjectWrapper var6 = IObjectWrapper.Stub.a(var5.readStrongBinder());
      var5.recycle();
      return var6;
   }

   public final IObjectWrapper newSignInButtonFromConfig(IObjectWrapper var1, SignInButtonConfig var2) throws RemoteException {
      Parcel var3 = this.zaa();
      gb.a(var3, var1);
      gb.a(var3, var2);
      Parcel var4 = this.zaa(2, var3);
      IObjectWrapper var5 = IObjectWrapper.Stub.a(var4.readStrongBinder());
      var4.recycle();
      return var5;
   }
}
