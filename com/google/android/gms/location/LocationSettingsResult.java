package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.LocationSettingsStates;

@SafeParcelable.Class(
   creator = "LocationSettingsResultCreator"
)
@SafeParcelable.Reserved({1000})
public final class LocationSettingsResult extends AbstractSafeParcelable implements Result {

   public static final Creator<LocationSettingsResult> CREATOR = new id();
   @SafeParcelable.Field(
      getter = "getStatus",
      id = 1
   )
   private final Status a;
   @SafeParcelable.Field(
      getter = "getLocationSettingsStates",
      id = 2
   )
   private final LocationSettingsStates b;


   public LocationSettingsResult(Status var1) {
      this(var1, (LocationSettingsStates)null);
   }

   @SafeParcelable.Constructor
   public LocationSettingsResult(
      @SafeParcelable.Param(
         id = 1
      ) Status var1, 
      @SafeParcelable.Param(
         id = 2
      ) LocationSettingsStates var2) {
      this.a = var1;
      this.b = var2;
   }

   public final LocationSettingsStates a() {
      return this.b;
   }

   public final Status getStatus() {
      return this.a;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.getStatus(), var2, false);
      SafeParcelWriter.writeParcelable(var1, 2, this.a(), var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
