package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zacl;

@KeepForSdk
public class TaskUtil {

   @KeepForSdk
   public static <TResult extends Object> void setResultOrApiException(Status var0, TResult var1, li<TResult> var2) {
      if(var0.isSuccess()) {
         var2.a(var1);
      } else {
         var2.a(new ApiException(var0));
      }
   }

   @KeepForSdk
   public static void setResultOrApiException(Status var0, li<Void> var1) {
      setResultOrApiException(var0, (Object)null, var1);
   }

   @Deprecated
   @KeepForSdk
   public static lh<Void> toVoidTaskThatFailsOnFalse(lh<Boolean> var0) {
      return var0.a(new zacl());
   }
}
