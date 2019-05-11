package com.google.android.gms.common.api;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.annotation.KeepForSdk;

public final class UnsupportedApiCallException extends UnsupportedOperationException {

   private final Feature zzar;


   @KeepForSdk
   public UnsupportedApiCallException(Feature var1) {
      this.zzar = var1;
   }

   public final String getMessage() {
      String var1 = String.valueOf(this.zzar);
      StringBuilder var2 = new StringBuilder(String.valueOf(var1).length() + 8);
      var2.append("Missing ");
      var2.append(var1);
      return var2.toString();
   }
}
