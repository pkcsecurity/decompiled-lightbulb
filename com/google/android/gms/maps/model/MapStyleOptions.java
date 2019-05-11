package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "MapStyleOptionsCreator"
)
@SafeParcelable.Reserved({1})
public final class MapStyleOptions extends AbstractSafeParcelable {

   public static final Creator<MapStyleOptions> CREATOR = new jp();
   private static final String a = "MapStyleOptions";
   @SafeParcelable.Field(
      getter = "getJson",
      id = 2
   )
   private String b;


   @SafeParcelable.Constructor
   public MapStyleOptions(
      @SafeParcelable.Param(
         id = 2
      ) String var1) {
      this.b = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 2, this.b, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
