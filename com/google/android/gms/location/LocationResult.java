package com.google.android.gms.location;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@SafeParcelable.Class(
   creator = "LocationResultCreator"
)
@SafeParcelable.Reserved({1000})
public final class LocationResult extends AbstractSafeParcelable implements ReflectedParcelable {

   public static final Creator<LocationResult> CREATOR = new hz();
   public static final List<Location> a = Collections.emptyList();
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationResult.DEFAULT_LOCATIONS",
      getter = "getLocations",
      id = 1
   )
   private final List<Location> b;


   @SafeParcelable.Constructor
   public LocationResult(
      @SafeParcelable.Param(
         id = 1
      ) List<Location> var1) {
      this.b = var1;
   }

   @NonNull
   public final List<Location> a() {
      return this.b;
   }

   public final boolean equals(Object var1) {
      if(!(var1 instanceof LocationResult)) {
         return false;
      } else {
         LocationResult var5 = (LocationResult)var1;
         if(var5.b.size() != this.b.size()) {
            return false;
         } else {
            Iterator var6 = var5.b.iterator();
            Iterator var2 = this.b.iterator();

            Location var3;
            Location var4;
            do {
               if(!var6.hasNext()) {
                  return true;
               }

               var3 = (Location)var2.next();
               var4 = (Location)var6.next();
            } while(var3.getTime() == var4.getTime());

            return false;
         }
      }
   }

   public final int hashCode() {
      Iterator var4 = this.b.iterator();

      int var1;
      long var2;
      for(var1 = 17; var4.hasNext(); var1 = var1 * 31 + (int)(var2 ^ var2 >>> 32)) {
         var2 = ((Location)var4.next()).getTime();
      }

      return var1;
   }

   public final String toString() {
      String var1 = String.valueOf(this.b);
      StringBuilder var2 = new StringBuilder(String.valueOf(var1).length() + 27);
      var2.append("LocationResult[locations: ");
      var2.append(var1);
      var2.append("]");
      return var2.toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 1, this.a(), false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
