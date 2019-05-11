package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.LatLng;

@SafeParcelable.Class(
   creator = "PointOfInterestCreator"
)
@SafeParcelable.Reserved({1})
public final class PointOfInterest extends AbstractSafeParcelable {

   public static final Creator<PointOfInterest> CREATOR = new js();
   @SafeParcelable.Field(
      id = 2
   )
   public final LatLng a;
   @SafeParcelable.Field(
      id = 3
   )
   public final String b;
   @SafeParcelable.Field(
      id = 4
   )
   public final String c;


   @SafeParcelable.Constructor
   public PointOfInterest(
      @SafeParcelable.Param(
         id = 2
      ) LatLng var1, 
      @SafeParcelable.Param(
         id = 3
      ) String var2, 
      @SafeParcelable.Param(
         id = 4
      ) String var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.a, var2, false);
      SafeParcelWriter.writeString(var1, 3, this.b, false);
      SafeParcelWriter.writeString(var1, 4, this.c, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
