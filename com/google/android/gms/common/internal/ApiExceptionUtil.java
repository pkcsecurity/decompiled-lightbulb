package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;

@KeepForSdk
public class ApiExceptionUtil {

   @NonNull
   @KeepForSdk
   public static ApiException fromStatus(@NonNull Status var0) {
      return (ApiException)(var0.hasResolution()?new ResolvableApiException(var0):new ApiException(var0));
   }
}
