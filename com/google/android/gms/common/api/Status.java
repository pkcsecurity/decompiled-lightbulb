package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.zzb;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.VisibleForTesting;

@KeepForSdk
@SafeParcelable.Class(
   creator = "StatusCreator"
)
public final class Status extends AbstractSafeParcelable implements Result, ReflectedParcelable {

   public static final Creator<Status> CREATOR = new zzb();
   @KeepForSdk
   public static final Status RESULT_CANCELED = new Status(16);
   @KeepForSdk
   public static final Status RESULT_DEAD_CLIENT = new Status(18);
   @KeepForSdk
   public static final Status RESULT_INTERNAL_ERROR = new Status(8);
   @KeepForSdk
   public static final Status RESULT_INTERRUPTED = new Status(14);
   @KeepForSdk
   @VisibleForTesting
   public static final Status RESULT_SUCCESS = new Status(0);
   @KeepForSdk
   public static final Status RESULT_TIMEOUT = new Status(15);
   private static final Status zzaq = new Status(17);
   @SafeParcelable.VersionField(
      id = 1000
   )
   private final int zzg;
   @SafeParcelable.Field(
      getter = "getStatusCode",
      id = 1
   )
   private final int zzh;
   @Nullable
   @SafeParcelable.Field(
      getter = "getPendingIntent",
      id = 3
   )
   private final PendingIntent zzi;
   @Nullable
   @SafeParcelable.Field(
      getter = "getStatusMessage",
      id = 2
   )
   private final String zzj;


   @KeepForSdk
   public Status(int var1) {
      this(var1, (String)null);
   }

   @KeepForSdk
   @SafeParcelable.Constructor
   Status(
      @SafeParcelable.Param(
         id = 1000
      ) int var1, 
      @SafeParcelable.Param(
         id = 1
      ) int var2, @Nullable 
      @SafeParcelable.Param(
         id = 2
      ) String var3, @Nullable 
      @SafeParcelable.Param(
         id = 3
      ) PendingIntent var4) {
      this.zzg = var1;
      this.zzh = var2;
      this.zzj = var3;
      this.zzi = var4;
   }

   @KeepForSdk
   public Status(int var1, @Nullable String var2) {
      this(1, var1, var2, (PendingIntent)null);
   }

   @KeepForSdk
   public Status(int var1, @Nullable String var2, @Nullable PendingIntent var3) {
      this(1, var1, var2, var3);
   }

   public final boolean equals(Object var1) {
      if(!(var1 instanceof Status)) {
         return false;
      } else {
         Status var2 = (Status)var1;
         return this.zzg == var2.zzg && this.zzh == var2.zzh && Objects.equal(this.zzj, var2.zzj) && Objects.equal(this.zzi, var2.zzi);
      }
   }

   public final PendingIntent getResolution() {
      return this.zzi;
   }

   @KeepForSdk
   public final Status getStatus() {
      return this;
   }

   public final int getStatusCode() {
      return this.zzh;
   }

   @Nullable
   public final String getStatusMessage() {
      return this.zzj;
   }

   @VisibleForTesting
   public final boolean hasResolution() {
      return this.zzi != null;
   }

   public final int hashCode() {
      return Objects.hashCode(new Object[]{Integer.valueOf(this.zzg), Integer.valueOf(this.zzh), this.zzj, this.zzi});
   }

   public final boolean isCanceled() {
      return this.zzh == 16;
   }

   public final boolean isInterrupted() {
      return this.zzh == 14;
   }

   public final boolean isSuccess() {
      return this.zzh <= 0;
   }

   public final void startResolutionForResult(Activity var1, int var2) throws SendIntentException {
      if(this.hasResolution()) {
         var1.startIntentSenderForResult(this.zzi.getIntentSender(), var2, (Intent)null, 0, 0, 0);
      }
   }

   public final String toString() {
      return Objects.toStringHelper(this).add("statusCode", this.zzg()).add("resolution", this.zzi).toString();
   }

   @KeepForSdk
   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.getStatusCode());
      SafeParcelWriter.writeString(var1, 2, this.getStatusMessage(), false);
      SafeParcelWriter.writeParcelable(var1, 3, this.zzi, var2, false);
      SafeParcelWriter.writeInt(var1, 1000, this.zzg);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final String zzg() {
      return this.zzj != null?this.zzj:CommonStatusCodes.getStatusCodeString(this.zzh);
   }
}
