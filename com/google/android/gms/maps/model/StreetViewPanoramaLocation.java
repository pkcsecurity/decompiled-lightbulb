package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLink;

@SafeParcelable.Class(
   creator = "StreetViewPanoramaLocationCreator"
)
@SafeParcelable.Reserved({1})
public class StreetViewPanoramaLocation extends AbstractSafeParcelable {

   public static final Creator<StreetViewPanoramaLocation> CREATOR = new jx();
   @SafeParcelable.Field(
      id = 2
   )
   public final StreetViewPanoramaLink[] a;
   @SafeParcelable.Field(
      id = 3
   )
   public final LatLng b;
   @SafeParcelable.Field(
      id = 4
   )
   public final String c;


   @SafeParcelable.Constructor
   public StreetViewPanoramaLocation(
      @SafeParcelable.Param(
         id = 2
      ) StreetViewPanoramaLink[] var1, 
      @SafeParcelable.Param(
         id = 3
      ) LatLng var2, 
      @SafeParcelable.Param(
         id = 4
      ) String var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof StreetViewPanoramaLocation)) {
         return false;
      } else {
         StreetViewPanoramaLocation var2 = (StreetViewPanoramaLocation)var1;
         return this.c.equals(var2.c) && this.b.equals(var2.b);
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.b, this.c});
   }

   public String toString() {
      return Objects.toStringHelper(this).add("panoId", this.c).add("position", this.b.toString()).toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedArray(var1, 2, this.a, var2, false);
      SafeParcelWriter.writeParcelable(var1, 3, this.b, var2, false);
      SafeParcelWriter.writeString(var1, 4, this.c, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
