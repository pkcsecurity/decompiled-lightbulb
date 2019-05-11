package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegistrationMethods;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;

final class zacb extends UnregisterListenerMethod<A, L> {

   // $FF: synthetic field
   private final RegistrationMethods.Builder zakg;


   zacb(RegistrationMethods.Builder var1, ListenerHolder.ListenerKey var2) {
      super(var2);
      this.zakg = var1;
   }

   protected final void unregisterListener(A var1, li<Boolean> var2) throws RemoteException {
      RegistrationMethods.Builder.zab(this.zakg).accept(var1, var2);
   }
}
