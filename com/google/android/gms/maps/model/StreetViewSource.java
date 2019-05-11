package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "StreetViewSourceCreator"
)
@SafeParcelable.Reserved({1})
public final class StreetViewSource extends AbstractSafeParcelable {

   public static final Creator<StreetViewSource> CREATOR = new jz();
   public static final StreetViewSource a = new StreetViewSource(0);
   public static final StreetViewSource b = new StreetViewSource(1);
   private static final String c = "StreetViewSource";
   @SafeParcelable.Field(
      getter = "getType",
      id = 2
   )
   private final int d;


   @SafeParcelable.Constructor
   public StreetViewSource(
      @SafeParcelable.Param(
         id = 2
      ) int var1) {
      this.d = var1;
   }

   public final boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof StreetViewSource)) {
         return false;
      } else {
         StreetViewSource var2 = (StreetViewSource)var1;
         return this.d == var2.d;
      }
   }

   public final int hashCode() {
      return Objects.hashCode(new Object[]{Integer.valueOf(this.d)});
   }

   public final String toString() {
      String var1;
      switch(this.d) {
      case 0:
         var1 = "DEFAULT";
         break;
      case 1:
         var1 = "OUTDOOR";
         break;
      default:
         var1 = String.format("UNKNOWN(%s)", new Object[]{Integer.valueOf(this.d)});
      }

      return String.format("StreetViewSource:%s", new Object[]{var1});
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 2, this.d);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
