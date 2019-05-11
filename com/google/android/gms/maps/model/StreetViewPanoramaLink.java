package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "StreetViewPanoramaLinkCreator"
)
@SafeParcelable.Reserved({1})
public class StreetViewPanoramaLink extends AbstractSafeParcelable {

   public static final Creator<StreetViewPanoramaLink> CREATOR = new jw();
   @SafeParcelable.Field(
      id = 2
   )
   public final String a;
   @SafeParcelable.Field(
      id = 3
   )
   public final float b;


   @SafeParcelable.Constructor
   public StreetViewPanoramaLink(
      @SafeParcelable.Param(
         id = 2
      ) String var1, 
      @SafeParcelable.Param(
         id = 3
      ) float var2) {
      this.a = var1;
      float var3 = var2;
      if((double)var2 <= 0.0D) {
         var3 = var2 % 360.0F + 360.0F;
      }

      this.b = var3 % 360.0F;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof StreetViewPanoramaLink)) {
         return false;
      } else {
         StreetViewPanoramaLink var2 = (StreetViewPanoramaLink)var1;
         return this.a.equals(var2.a) && Float.floatToIntBits(this.b) == Float.floatToIntBits(var2.b);
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.a, Float.valueOf(this.b)});
   }

   public String toString() {
      return Objects.toStringHelper(this).add("panoId", this.a).add("bearing", Float.valueOf(this.b)).toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 2, this.a, false);
      SafeParcelWriter.writeFloat(var1, 3, this.b);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
