package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

@SafeParcelable.Class(
   creator = "StreetViewPanoramaCameraCreator"
)
@SafeParcelable.Reserved({1})
public class StreetViewPanoramaCamera extends AbstractSafeParcelable implements ReflectedParcelable {

   public static final Creator<StreetViewPanoramaCamera> CREATOR = new jv();
   @SafeParcelable.Field(
      id = 2
   )
   public final float a;
   @SafeParcelable.Field(
      id = 3
   )
   public final float b;
   @SafeParcelable.Field(
      id = 4
   )
   public final float c;
   @NonNull
   private final StreetViewPanoramaOrientation d;


   @SafeParcelable.Constructor
   public StreetViewPanoramaCamera(
      @SafeParcelable.Param(
         id = 2
      ) float var1, 
      @SafeParcelable.Param(
         id = 3
      ) float var2, 
      @SafeParcelable.Param(
         id = 4
      ) float var3) {
      boolean var5;
      if(-90.0F <= var2 && var2 <= 90.0F) {
         var5 = true;
      } else {
         var5 = false;
      }

      StringBuilder var6 = new StringBuilder(62);
      var6.append("Tilt needs to be between -90 and 90 inclusive: ");
      var6.append(var2);
      Preconditions.checkArgument(var5, var6.toString());
      float var4 = var1;
      if((double)var1 <= 0.0D) {
         var4 = 0.0F;
      }

      this.a = var4;
      this.b = 0.0F + var2;
      if((double)var3 <= 0.0D) {
         var1 = var3 % 360.0F + 360.0F;
      } else {
         var1 = var3;
      }

      this.c = var1 % 360.0F;
      this.d = (new StreetViewPanoramaOrientation.a()).a(var2).b(var3).a();
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof StreetViewPanoramaCamera)) {
         return false;
      } else {
         StreetViewPanoramaCamera var2 = (StreetViewPanoramaCamera)var1;
         return Float.floatToIntBits(this.a) == Float.floatToIntBits(var2.a) && Float.floatToIntBits(this.b) == Float.floatToIntBits(var2.b) && Float.floatToIntBits(this.c) == Float.floatToIntBits(var2.c);
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{Float.valueOf(this.a), Float.valueOf(this.b), Float.valueOf(this.c)});
   }

   public String toString() {
      return Objects.toStringHelper(this).add("zoom", Float.valueOf(this.a)).add("tilt", Float.valueOf(this.b)).add("bearing", Float.valueOf(this.c)).toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeFloat(var1, 2, this.a);
      SafeParcelWriter.writeFloat(var1, 3, this.b);
      SafeParcelWriter.writeFloat(var1, 4, this.c);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
