package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.ResolveAccountResponse;

public interface IResolveAccountCallbacks extends IInterface {

   void onAccountResolutionComplete(ResolveAccountResponse var1) throws RemoteException;

   public abstract static class Stub extends com.google.android.gms.internal.base.zab implements IResolveAccountCallbacks {

      public Stub() {
         super("com.google.android.gms.common.internal.IResolveAccountCallbacks");
      }

      public static IResolveAccountCallbacks asInterface(IBinder var0) {
         if(var0 == null) {
            return null;
         } else {
            IInterface var1 = var0.queryLocalInterface("com.google.android.gms.common.internal.IResolveAccountCallbacks");
            return (IResolveAccountCallbacks)(var1 instanceof IResolveAccountCallbacks?(IResolveAccountCallbacks)var1:new IResolveAccountCallbacks.Proxy(var0));
         }
      }

      protected boolean dispatchTransaction(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         if(var1 == 2) {
            this.onAccountResolutionComplete((ResolveAccountResponse)gb.a(var2, ResolveAccountResponse.CREATOR));
            var3.writeNoException();
            return true;
         } else {
            return false;
         }
      }
   }

   public static class Proxy extends com.google.android.gms.internal.base.zaa implements IResolveAccountCallbacks {

      Proxy(IBinder var1) {
         super(var1, "com.google.android.gms.common.internal.IResolveAccountCallbacks");
      }

      public void onAccountResolutionComplete(ResolveAccountResponse var1) throws RemoteException {
         Parcel var2 = this.zaa();
         gb.a(var2, var1);
         this.zab(2, var2);
      }
   }
}
