package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.RegistrationMethods;
import com.google.android.gms.common.api.internal.RemoteCall;

// $FF: synthetic class
final class zabz implements RemoteCall {

   private final RegistrationMethods.Builder zakf;


   zabz(RegistrationMethods.Builder var1) {
      this.zakf = var1;
   }

   public final void accept(Object var1, Object var2) {
      this.zakf.zaa((Api.AnyClient)var1, (li)var2);
   }
}
