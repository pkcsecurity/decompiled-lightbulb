package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.LatLng;

@SafeParcelable.Class(
   creator = "LatLngBoundsCreator"
)
@SafeParcelable.Reserved({1})
public final class LatLngBounds extends AbstractSafeParcelable implements ReflectedParcelable {

   @KeepForSdk
   public static final Creator<LatLngBounds> CREATOR = new jn();
   @SafeParcelable.Field(
      id = 2
   )
   public final LatLng a;
   @SafeParcelable.Field(
      id = 3
   )
   public final LatLng b;


   @SafeParcelable.Constructor
   public LatLngBounds(
      @SafeParcelable.Param(
         id = 2
      ) LatLng var1, 
      @SafeParcelable.Param(
         id = 3
      ) LatLng var2) {
      Preconditions.checkNotNull(var1, "null southwest");
      Preconditions.checkNotNull(var2, "null northeast");
      boolean var3;
      if(var2.a >= var1.a) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "southern latitude exceeds northern latitude (%s > %s)", new Object[]{Double.valueOf(var1.a), Double.valueOf(var2.a)});
      this.a = var1;
      this.b = var2;
   }

   // $FF: synthetic method
   static double a(double var0, double var2) {
      return c(var0, var2);
   }

   // $FF: synthetic method
   static double b(double var0, double var2) {
      return d(var0, var2);
   }

   private static double c(double var0, double var2) {
      return (var0 - var2 + 360.0D) % 360.0D;
   }

   private static double d(double var0, double var2) {
      return (var2 - var0 + 360.0D) % 360.0D;
   }

   public final LatLng a() {
      double var3 = (this.a.a + this.b.a) / 2.0D;
      double var1 = this.b.b;
      double var5 = this.a.b;
      if(var5 <= var1) {
         var1 = (var1 + var5) / 2.0D;
      } else {
         var1 = (var1 + 360.0D + var5) / 2.0D;
      }

      return new LatLng(var3, var1);
   }

   public final boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof LatLngBounds)) {
         return false;
      } else {
         LatLngBounds var2 = (LatLngBounds)var1;
         return this.a.equals(var2.a) && this.b.equals(var2.b);
      }
   }

   public final int hashCode() {
      return Objects.hashCode(new Object[]{this.a, this.b});
   }

   public final String toString() {
      return Objects.toStringHelper(this).add("southwest", this.a).add("northeast", this.b).toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.a, var2, false);
      SafeParcelWriter.writeParcelable(var1, 3, this.b, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public static final class a {

      private double a = Double.POSITIVE_INFINITY;
      private double b = Double.NEGATIVE_INFINITY;
      private double c = Double.NaN;
      private double d = Double.NaN;


      public final LatLngBounds.a a(LatLng var1) {
         this.a = Math.min(this.a, var1.a);
         this.b = Math.max(this.b, var1.a);
         double var2 = var1.b;
         if(Double.isNaN(this.c)) {
            this.c = var2;
         } else {
            boolean var8;
            label26: {
               double var4 = this.c;
               double var6 = this.d;
               boolean var9 = false;
               if(var4 <= var6) {
                  var8 = var9;
                  if(this.c > var2) {
                     break label26;
                  }

                  var8 = var9;
                  if(var2 > this.d) {
                     break label26;
                  }
               } else if(this.c > var2) {
                  var8 = var9;
                  if(var2 > this.d) {
                     break label26;
                  }
               }

               var8 = true;
            }

            if(var8) {
               return this;
            }

            if(LatLngBounds.a(this.c, var2) < LatLngBounds.b(this.d, var2)) {
               this.c = var2;
               return this;
            }
         }

         this.d = var2;
         return this;
      }

      public final LatLngBounds a() {
         Preconditions.checkState(Double.isNaN(this.c) ^ true, "no included points");
         return new LatLngBounds(new LatLng(this.a, this.c), new LatLng(this.b, this.d));
      }
   }
}
