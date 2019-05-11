package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "FusedLocationProviderResultCreator"
)
@SafeParcelable.Reserved({1000})
public final class zzad extends AbstractSafeParcelable implements Result {

   public static final Creator<zzad> CREATOR = new gt();
   private static final zzad a = new zzad(Status.RESULT_SUCCESS);
   @SafeParcelable.Field(
      getter = "getStatus",
      id = 1
   )
   private final Status b;


   @SafeParcelable.Constructor
   public zzad(
      @SafeParcelable.Param(
         id = 1
      ) Status var1) {
      this.b = var1;
   }

   public final Status getStatus() {
      return this.b;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.getStatus(), var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
