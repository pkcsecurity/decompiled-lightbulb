package com.google.android.gms.common;

import android.content.Intent;
import com.google.android.gms.common.UserRecoverableException;

public class GooglePlayServicesRepairableException extends UserRecoverableException {

   private final int zzaf;


   public GooglePlayServicesRepairableException(int var1, String var2, Intent var3) {
      super(var2, var3);
      this.zzaf = var1;
   }

   public int getConnectionStatusCode() {
      return this.zzaf;
   }
}
