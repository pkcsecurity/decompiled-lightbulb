package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.RegistrationMethods;

final class zaca extends RegisterListenerMethod<A, L> {

   // $FF: synthetic field
   private final RegistrationMethods.Builder zakg;


   zaca(RegistrationMethods.Builder var1, ListenerHolder var2, Feature[] var3, boolean var4) {
      super(var2, var3, var4);
      this.zakg = var1;
   }

   protected final void registerListener(A var1, li<Void> var2) throws RemoteException {
      RegistrationMethods.Builder.zaa(this.zakg).accept(var1, var2);
   }
}
