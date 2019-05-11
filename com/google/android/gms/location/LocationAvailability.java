package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.zzaj;
import java.util.Arrays;

@SafeParcelable.Class(
   creator = "LocationAvailabilityCreator"
)
@SafeParcelable.Reserved({1000})
public final class LocationAvailability extends AbstractSafeParcelable implements ReflectedParcelable {

   public static final Creator<LocationAvailability> CREATOR = new hx();
   @Deprecated
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationAvailability.STATUS_UNKNOWN",
      id = 1
   )
   private int a;
   @Deprecated
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationAvailability.STATUS_UNKNOWN",
      id = 2
   )
   private int b;
   @SafeParcelable.Field(
      defaultValueUnchecked = "0",
      id = 3
   )
   private long c;
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationAvailability.STATUS_UNSUCCESSFUL",
      id = 4
   )
   private int d;
   @SafeParcelable.Field(
      id = 5
   )
   private zzaj[] e;


   @SafeParcelable.Constructor
   public LocationAvailability(
      @SafeParcelable.Param(
         id = 4
      ) int var1, 
      @SafeParcelable.Param(
         id = 1
      ) int var2, 
      @SafeParcelable.Param(
         id = 2
      ) int var3, 
      @SafeParcelable.Param(
         id = 3
      ) long var4, 
      @SafeParcelable.Param(
         id = 5
      ) zzaj[] var6) {
      this.d = var1;
      this.a = var2;
      this.b = var3;
      this.c = var4;
      this.e = var6;
   }

   public final boolean a() {
      return this.d < 1000;
   }

   public final boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else {
         if(var1 != null) {
            if(this.getClass() != var1.getClass()) {
               return false;
            }

            LocationAvailability var2 = (LocationAvailability)var1;
            if(this.a == var2.a && this.b == var2.b && this.c == var2.c && this.d == var2.d && Arrays.equals(this.e, var2.e)) {
               return true;
            }
         }

         return false;
      }
   }

   public final int hashCode() {
      return Objects.hashCode(new Object[]{Integer.valueOf(this.d), Integer.valueOf(this.a), Integer.valueOf(this.b), Long.valueOf(this.c), this.e});
   }

   public final String toString() {
      boolean var1 = this.a();
      StringBuilder var2 = new StringBuilder(48);
      var2.append("LocationAvailability[isLocationAvailable: ");
      var2.append(var1);
      var2.append("]");
      return var2.toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.a);
      SafeParcelWriter.writeInt(var1, 2, this.b);
      SafeParcelWriter.writeLong(var1, 3, this.c);
      SafeParcelWriter.writeInt(var1, 4, this.d);
      SafeParcelWriter.writeTypedArray(var1, 5, this.e, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
