package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

@SafeParcelable.Class(
   creator = "VisibleRegionCreator"
)
@SafeParcelable.Reserved({1})
public final class VisibleRegion extends AbstractSafeParcelable {

   public static final Creator<VisibleRegion> CREATOR = new kd();
   @SafeParcelable.Field(
      id = 2
   )
   public final LatLng a;
   @SafeParcelable.Field(
      id = 3
   )
   public final LatLng b;
   @SafeParcelable.Field(
      id = 4
   )
   public final LatLng c;
   @SafeParcelable.Field(
      id = 5
   )
   public final LatLng d;
   @SafeParcelable.Field(
      id = 6
   )
   public final LatLngBounds e;


   @SafeParcelable.Constructor
   public VisibleRegion(
      @SafeParcelable.Param(
         id = 2
      ) LatLng var1, 
      @SafeParcelable.Param(
         id = 3
      ) LatLng var2, 
      @SafeParcelable.Param(
         id = 4
      ) LatLng var3, 
      @SafeParcelable.Param(
         id = 5
      ) LatLng var4, 
      @SafeParcelable.Param(
         id = 6
      ) LatLngBounds var5) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
   }

   public final boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof VisibleRegion)) {
         return false;
      } else {
         VisibleRegion var2 = (VisibleRegion)var1;
         return this.a.equals(var2.a) && this.b.equals(var2.b) && this.c.equals(var2.c) && this.d.equals(var2.d) && this.e.equals(var2.e);
      }
   }

   public final int hashCode() {
      return Objects.hashCode(new Object[]{this.a, this.b, this.c, this.d, this.e});
   }

   public final String toString() {
      return Objects.toStringHelper(this).add("nearLeft", this.a).add("nearRight", this.b).add("farLeft", this.c).add("farRight", this.d).add("latLngBounds", this.e).toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.a, var2, false);
      SafeParcelWriter.writeParcelable(var1, 3, this.b, var2, false);
      SafeParcelWriter.writeParcelable(var1, 4, this.c, var2, false);
      SafeParcelWriter.writeParcelable(var1, 5, this.d, var2, false);
      SafeParcelWriter.writeParcelable(var1, 6, this.e, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
