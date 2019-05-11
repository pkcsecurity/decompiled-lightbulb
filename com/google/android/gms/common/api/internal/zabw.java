package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;

public final class zabw {

   public final RegisterListenerMethod<Api.AnyClient, ?> zajw;
   public final UnregisterListenerMethod<Api.AnyClient, ?> zajx;


   public zabw(@NonNull RegisterListenerMethod<Api.AnyClient, ?> var1, @NonNull UnregisterListenerMethod<Api.AnyClient, ?> var2) {
      this.zajw = var1;
      this.zajx = var2;
   }
}
