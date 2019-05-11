package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "StreetViewPanoramaOrientationCreator"
)
@SafeParcelable.Reserved({1})
public class StreetViewPanoramaOrientation extends AbstractSafeParcelable {

   public static final Creator<StreetViewPanoramaOrientation> CREATOR = new jy();
   @SafeParcelable.Field(
      id = 2
   )
   public final float a;
   @SafeParcelable.Field(
      id = 3
   )
   public final float b;


   @SafeParcelable.Constructor
   public StreetViewPanoramaOrientation(
      @SafeParcelable.Param(
         id = 2
      ) float var1, 
      @SafeParcelable.Param(
         id = 3
      ) float var2) {
      boolean var3;
      if(-90.0F <= var1 && var1 <= 90.0F) {
         var3 = true;
      } else {
         var3 = false;
      }

      StringBuilder var4 = new StringBuilder(62);
      var4.append("Tilt needs to be between -90 and 90 inclusive: ");
      var4.append(var1);
      Preconditions.checkArgument(var3, var4.toString());
      this.a = var1 + 0.0F;
      var1 = var2;
      if((double)var2 <= 0.0D) {
         var1 = var2 % 360.0F + 360.0F;
      }

      this.b = var1 % 360.0F;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof StreetViewPanoramaOrientation)) {
         return false;
      } else {
         StreetViewPanoramaOrientation var2 = (StreetViewPanoramaOrientation)var1;
         return Float.floatToIntBits(this.a) == Float.floatToIntBits(var2.a) && Float.floatToIntBits(this.b) == Float.floatToIntBits(var2.b);
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{Float.valueOf(this.a), Float.valueOf(this.b)});
   }

   public String toString() {
      return Objects.toStringHelper(this).add("tilt", Float.valueOf(this.a)).add("bearing", Float.valueOf(this.b)).toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeFloat(var1, 2, this.a);
      SafeParcelWriter.writeFloat(var1, 3, this.b);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }

   public static final class a {

      public float a;
      public float b;


      public final StreetViewPanoramaOrientation.a a(float var1) {
         this.b = var1;
         return this;
      }

      public final StreetViewPanoramaOrientation a() {
         return new StreetViewPanoramaOrientation(this.b, this.a);
      }

      public final StreetViewPanoramaOrientation.a b(float var1) {
         this.a = var1;
         return this;
      }
   }
}
