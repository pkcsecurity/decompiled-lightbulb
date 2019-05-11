package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public abstract class ResultCallbacks<R extends Object & Result> implements ResultCallback<R> {

   public abstract void onFailure(@NonNull Status var1);

   @KeepForSdk
   public final void onResult(@NonNull R var1) {
      Status var2 = var1.getStatus();
      if(var2.isSuccess()) {
         this.onSuccess(var1);
      } else {
         this.onFailure(var2);
         if(var1 instanceof Releasable) {
            try {
               ((Releasable)var1).release();
               return;
            } catch (RuntimeException var4) {
               String var5 = String.valueOf(var1);
               StringBuilder var3 = new StringBuilder(String.valueOf(var5).length() + 18);
               var3.append("Unable to release ");
               var3.append(var5);
               Log.w("ResultCallbacks", var3.toString(), var4);
            }
         }

      }
   }

   public abstract void onSuccess(@NonNull R var1);
}
