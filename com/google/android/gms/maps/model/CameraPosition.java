package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.LatLng;

@SafeParcelable.Class(
   creator = "CameraPositionCreator"
)
@SafeParcelable.Reserved({1})
public final class CameraPosition extends AbstractSafeParcelable implements ReflectedParcelable {

   public static final Creator<CameraPosition> CREATOR = new jj();
   @SafeParcelable.Field(
      id = 2
   )
   public final LatLng a;
   @SafeParcelable.Field(
      id = 3
   )
   public final float b;
   @SafeParcelable.Field(
      id = 4
   )
   public final float c;
   @SafeParcelable.Field(
      id = 5
   )
   public final float d;


   @SafeParcelable.Constructor
   public CameraPosition(
      @SafeParcelable.Param(
         id = 2
      ) LatLng var1, 
      @SafeParcelable.Param(
         id = 3
      ) float var2, 
      @SafeParcelable.Param(
         id = 4
      ) float var3, 
      @SafeParcelable.Param(
         id = 5
      ) float var4) {
      Preconditions.checkNotNull(var1, "null camera target");
      boolean var5;
      if(0.0F <= var3 && var3 <= 90.0F) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5, "Tilt needs to be between 0 and 90 inclusive: %s", new Object[]{Float.valueOf(var3)});
      this.a = var1;
      this.b = var2;
      this.c = var3 + 0.0F;
      var2 = var4;
      if((double)var4 <= 0.0D) {
         var2 = var4 % 360.0F + 360.0F;
      }

      this.d = var2 % 360.0F;
   }

   public static CameraPosition.a a() {
      return new CameraPosition.a();
   }

   public final boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof CameraPosition)) {
         return false;
      } else {
         CameraPosition var2 = (CameraPosition)var1;
         return this.a.equals(var2.a) && Float.floatToIntBits(this.b) == Float.floatToIntBits(var2.b) && Float.floatToIntBits(this.c) == Float.floatToIntBits(var2.c) && Float.floatToIntBits(this.d) == Float.floatToIntBits(var2.d);
      }
   }

   public final int hashCode() {
      return Objects.hashCode(new Object[]{this.a, Float.valueOf(this.b), Float.valueOf(this.c), Float.valueOf(this.d)});
   }

   public final String toString() {
      return Objects.toStringHelper(this).add("target", this.a).add("zoom", Float.valueOf(this.b)).add("tilt", Float.valueOf(this.c)).add("bearing", Float.valueOf(this.d)).toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.a, var2, false);
      SafeParcelWriter.writeFloat(var1, 3, this.b);
      SafeParcelWriter.writeFloat(var1, 4, this.c);
      SafeParcelWriter.writeFloat(var1, 5, this.d);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public static final class a {

      private LatLng a;
      private float b;
      private float c;
      private float d;


      public final CameraPosition.a a(float var1) {
         this.b = var1;
         return this;
      }

      public final CameraPosition.a a(LatLng var1) {
         this.a = var1;
         return this;
      }

      public final CameraPosition a() {
         return new CameraPosition(this.a, this.b, this.c, this.d);
      }

      public final CameraPosition.a b(float var1) {
         this.c = var1;
         return this;
      }

      public final CameraPosition.a c(float var1) {
         this.d = var1;
         return this;
      }
   }
}
