package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "LocationSettingsConfigurationCreator"
)
@SafeParcelable.Reserved({3, 4, 1000})
public final class zzae extends AbstractSafeParcelable {

   public static final Creator<zzae> CREATOR = new ib();
   @SafeParcelable.Field(
      defaultValue = "",
      getter = "getJustificationText",
      id = 1
   )
   private final String a;
   @SafeParcelable.Field(
      defaultValue = "",
      getter = "getExperimentId",
      id = 2
   )
   private final String b;
   @SafeParcelable.Field(
      defaultValue = "",
      getter = "getTitleText",
      id = 5
   )
   private final String c;


   @SafeParcelable.Constructor
   public zzae(
      @SafeParcelable.Param(
         id = 5
      ) String var1, 
      @SafeParcelable.Param(
         id = 1
      ) String var2, 
      @SafeParcelable.Param(
         id = 2
      ) String var3) {
      this.c = var1;
      this.a = var2;
      this.b = var3;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 1, this.a, false);
      SafeParcelWriter.writeString(var1, 2, this.b, false);
      SafeParcelWriter.writeString(var1, 5, this.c, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
