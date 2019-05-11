package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "LatLngCreator"
)
@SafeParcelable.Reserved({1})
public final class LatLng extends AbstractSafeParcelable implements ReflectedParcelable {

   @KeepForSdk
   public static final Creator<LatLng> CREATOR = new jo();
   @SafeParcelable.Field(
      id = 2
   )
   public final double a;
   @SafeParcelable.Field(
      id = 3
   )
   public final double b;


   @SafeParcelable.Constructor
   public LatLng(
      @SafeParcelable.Param(
         id = 2
      ) double var1, 
      @SafeParcelable.Param(
         id = 3
      ) double var3) {
      if(-180.0D <= var3 && var3 < 180.0D) {
         this.b = var3;
      } else {
         this.b = ((var3 - 180.0D) % 360.0D + 360.0D) % 360.0D - 180.0D;
      }

      this.a = Math.max(-90.0D, Math.min(90.0D, var1));
   }

   public final boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof LatLng)) {
         return false;
      } else {
         LatLng var2 = (LatLng)var1;
         return Double.doubleToLongBits(this.a) == Double.doubleToLongBits(var2.a) && Double.doubleToLongBits(this.b) == Double.doubleToLongBits(var2.b);
      }
   }

   public final int hashCode() {
      long var2 = Double.doubleToLongBits(this.a);
      int var1 = (int)(var2 ^ var2 >>> 32);
      var2 = Double.doubleToLongBits(this.b);
      return (var1 + 31) * 31 + (int)(var2 ^ var2 >>> 32);
   }

   public final String toString() {
      double var1 = this.a;
      double var3 = this.b;
      StringBuilder var5 = new StringBuilder(60);
      var5.append("lat/lng: (");
      var5.append(var1);
      var5.append(",");
      var5.append(var3);
      var5.append(")");
      return var5.toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeDouble(var1, 2, this.a);
      SafeParcelWriter.writeDouble(var1, 3, this.b);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
