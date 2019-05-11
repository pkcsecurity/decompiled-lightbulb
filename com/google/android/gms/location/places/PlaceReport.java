package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "PlaceReportCreator"
)
public class PlaceReport extends AbstractSafeParcelable implements ReflectedParcelable {

   public static final Creator<PlaceReport> CREATOR = new hw();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int a;
   @SafeParcelable.Field(
      getter = "getPlaceId",
      id = 2
   )
   private final String b;
   @SafeParcelable.Field(
      getter = "getTag",
      id = 3
   )
   private final String c;
   @SafeParcelable.Field(
      getter = "getSource",
      id = 4
   )
   private final String d;


   @SafeParcelable.Constructor
   public PlaceReport(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) String var2, 
      @SafeParcelable.Param(
         id = 3
      ) String var3, 
      @SafeParcelable.Param(
         id = 4
      ) String var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public String a() {
      return this.b;
   }

   public String b() {
      return this.c;
   }

   public boolean equals(Object var1) {
      if(!(var1 instanceof PlaceReport)) {
         return false;
      } else {
         PlaceReport var2 = (PlaceReport)var1;
         return Objects.equal(this.b, var2.b) && Objects.equal(this.c, var2.c) && Objects.equal(this.d, var2.d);
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.b, this.c, this.d});
   }

   public String toString() {
      Objects.ToStringHelper var1 = Objects.toStringHelper(this);
      var1.add("placeId", this.b);
      var1.add("tag", this.c);
      if(!"unknown".equals(this.d)) {
         var1.add("source", this.d);
      }

      return var1.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.a);
      SafeParcelWriter.writeString(var1, 2, this.a(), false);
      SafeParcelWriter.writeString(var1, 3, this.b(), false);
      SafeParcelWriter.writeString(var1, 4, this.d, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
