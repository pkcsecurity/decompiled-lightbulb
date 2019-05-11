package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Continuation;

final class zacl implements Continuation<Boolean, Void> {

   // $FF: synthetic method
   public final Object then(@NonNull lh var1) throws Exception {
      if(!((Boolean)var1.d()).booleanValue()) {
         throw new ApiException(new Status(13, "listener already unregistered"));
      } else {
         return null;
      }
   }
}
