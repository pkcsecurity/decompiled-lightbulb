package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Result;

public class Response<T extends Object & Result> {

   private T zzao;


   public Response() {}

   protected Response(@NonNull T var1) {
      this.zzao = var1;
   }

   @NonNull
   protected T getResult() {
      return this.zzao;
   }

   public void setResult(@NonNull T var1) {
      this.zzao = var1;
   }
}
