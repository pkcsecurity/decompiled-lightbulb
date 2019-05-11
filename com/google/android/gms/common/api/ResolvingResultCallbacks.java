package com.google.android.gms.common.api;

import android.app.Activity;
import android.content.IntentSender.SendIntentException;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;

public abstract class ResolvingResultCallbacks<R extends Object & Result> extends ResultCallbacks<R> {

   private final Activity mActivity;
   private final int zzan;


   protected ResolvingResultCallbacks(@NonNull Activity var1, int var2) {
      this.mActivity = (Activity)Preconditions.checkNotNull(var1, "Activity must not be null");
      this.zzan = var2;
   }

   @KeepForSdk
   public final void onFailure(@NonNull Status var1) {
      if(var1.hasResolution()) {
         try {
            var1.startResolutionForResult(this.mActivity, this.zzan);
         } catch (SendIntentException var2) {
            Log.e("ResolvingResultCallback", "Failed to start resolution", var2);
            this.onUnresolvableFailure(new Status(8));
         }
      } else {
         this.onUnresolvableFailure(var1);
      }
   }

   public abstract void onSuccess(@NonNull R var1);

   public abstract void onUnresolvableFailure(@NonNull Status var1);
}
