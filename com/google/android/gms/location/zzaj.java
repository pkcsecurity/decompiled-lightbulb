package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "NetworkLocationStatusCreator"
)
public final class zzaj extends AbstractSafeParcelable {

   public static final Creator<zzaj> CREATOR = new if();
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationAvailability.STATUS_UNKNOWN",
      id = 1
   )
   private final int a;
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationAvailability.STATUS_UNKNOWN",
      id = 2
   )
   private final int b;
   @SafeParcelable.Field(
      defaultValueUnchecked = "NetworkLocationStatus.STATUS_INVALID_TIMESTAMP",
      id = 3
   )
   private final long c;
   @SafeParcelable.Field(
      defaultValueUnchecked = "NetworkLocationStatus.STATUS_INVALID_TIMESTAMP",
      id = 4
   )
   private final long d;


   @SafeParcelable.Constructor
   public zzaj(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) int var2, 
      @SafeParcelable.Param(
         id = 3
      ) long var3, 
      @SafeParcelable.Param(
         id = 4
      ) long var5) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var5;
   }

   public final boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else {
         if(var1 != null) {
            if(this.getClass() != var1.getClass()) {
               return false;
            }

            zzaj var2 = (zzaj)var1;
            if(this.a == var2.a && this.b == var2.b && this.c == var2.c && this.d == var2.d) {
               return true;
            }
         }

         return false;
      }
   }

   public final int hashCode() {
      return Objects.hashCode(new Object[]{Integer.valueOf(this.b), Integer.valueOf(this.a), Long.valueOf(this.d), Long.valueOf(this.c)});
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder("NetworkLocationStatus:");
      var1.append(" Wifi status: ");
      var1.append(this.a);
      var1.append(" Cell status: ");
      var1.append(this.b);
      var1.append(" elapsed time NS: ");
      var1.append(this.d);
      var1.append(" system time ms: ");
      var1.append(this.c);
      return var1.toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.a);
      SafeParcelWriter.writeInt(var1, 2, this.b);
      SafeParcelWriter.writeLong(var1, 3, this.c);
      SafeParcelWriter.writeLong(var1, 4, this.d);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
