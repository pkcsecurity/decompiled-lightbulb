package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Status;

public class ApiException extends Exception {

   protected final Status mStatus;


   public ApiException(@NonNull Status var1) {
      int var2 = var1.getStatusCode();
      String var3;
      if(var1.getStatusMessage() != null) {
         var3 = var1.getStatusMessage();
      } else {
         var3 = "";
      }

      StringBuilder var4 = new StringBuilder(String.valueOf(var3).length() + 13);
      var4.append(var2);
      var4.append(": ");
      var4.append(var3);
      super(var4.toString());
      this.mStatus = var1;
   }

   public int getStatusCode() {
      return this.mStatus.getStatusCode();
   }

   @Deprecated
   @Nullable
   public String getStatusMessage() {
      return this.mStatus.getStatusMessage();
   }
}
