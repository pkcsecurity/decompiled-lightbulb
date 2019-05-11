package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.util.BiConsumer;

// $FF: synthetic class
final class zacj implements RemoteCall {

   private final BiConsumer zake;


   zacj(BiConsumer var1) {
      this.zake = var1;
   }

   public final void accept(Object var1, Object var2) {
      this.zake.accept((Api.AnyClient)var1, (li)var2);
   }
}
