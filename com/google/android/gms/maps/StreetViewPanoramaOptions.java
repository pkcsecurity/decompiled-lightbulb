package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewSource;

@SafeParcelable.Class(
   creator = "StreetViewPanoramaOptionsCreator"
)
@SafeParcelable.Reserved({1})
public final class StreetViewPanoramaOptions extends AbstractSafeParcelable implements ReflectedParcelable {

   public static final Creator<StreetViewPanoramaOptions> CREATOR = new ki();
   @SafeParcelable.Field(
      getter = "getStreetViewPanoramaCamera",
      id = 2
   )
   private StreetViewPanoramaCamera a;
   @SafeParcelable.Field(
      getter = "getPanoramaId",
      id = 3
   )
   private String b;
   @SafeParcelable.Field(
      getter = "getPosition",
      id = 4
   )
   private LatLng c;
   @SafeParcelable.Field(
      getter = "getRadius",
      id = 5
   )
   private Integer d;
   @SafeParcelable.Field(
      getter = "getUserNavigationEnabledForParcel",
      id = 6,
      type = "byte"
   )
   private Boolean e = Boolean.valueOf(true);
   @SafeParcelable.Field(
      getter = "getZoomGesturesEnabledForParcel",
      id = 7,
      type = "byte"
   )
   private Boolean f = Boolean.valueOf(true);
   @SafeParcelable.Field(
      getter = "getPanningGesturesEnabledForParcel",
      id = 8,
      type = "byte"
   )
   private Boolean g = Boolean.valueOf(true);
   @SafeParcelable.Field(
      getter = "getStreetNamesEnabledForParcel",
      id = 9,
      type = "byte"
   )
   private Boolean h = Boolean.valueOf(true);
   @SafeParcelable.Field(
      getter = "getUseViewLifecycleInFragmentForParcel",
      id = 10,
      type = "byte"
   )
   private Boolean i;
   @SafeParcelable.Field(
      getter = "getSource",
      id = 11
   )
   private StreetViewSource j;


   public StreetViewPanoramaOptions() {
      this.j = StreetViewSource.a;
   }

   @SafeParcelable.Constructor
   public StreetViewPanoramaOptions(
      @SafeParcelable.Param(
         id = 2
      ) StreetViewPanoramaCamera var1, 
      @SafeParcelable.Param(
         id = 3
      ) String var2, 
      @SafeParcelable.Param(
         id = 4
      ) LatLng var3, 
      @SafeParcelable.Param(
         id = 5
      ) Integer var4, 
      @SafeParcelable.Param(
         id = 6
      ) byte var5, 
      @SafeParcelable.Param(
         id = 7
      ) byte var6, 
      @SafeParcelable.Param(
         id = 8
      ) byte var7, 
      @SafeParcelable.Param(
         id = 9
      ) byte var8, 
      @SafeParcelable.Param(
         id = 10
      ) byte var9, 
      @SafeParcelable.Param(
         id = 11
      ) StreetViewSource var10) {
      this.j = StreetViewSource.a;
      this.a = var1;
      this.c = var3;
      this.d = var4;
      this.b = var2;
      this.e = iz.a(var5);
      this.f = iz.a(var6);
      this.g = iz.a(var7);
      this.h = iz.a(var8);
      this.i = iz.a(var9);
      this.j = var10;
   }

   public final StreetViewPanoramaCamera a() {
      return this.a;
   }

   public final LatLng b() {
      return this.c;
   }

   public final Integer c() {
      return this.d;
   }

   public final StreetViewSource d() {
      return this.j;
   }

   public final String e() {
      return this.b;
   }

   public final String toString() {
      return Objects.toStringHelper(this).add("PanoramaId", this.b).add("Position", this.c).add("Radius", this.d).add("Source", this.j).add("StreetViewPanoramaCamera", this.a).add("UserNavigationEnabled", this.e).add("ZoomGesturesEnabled", this.f).add("PanningGesturesEnabled", this.g).add("StreetNamesEnabled", this.h).add("UseViewLifecycleInFragment", this.i).toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.a(), var2, false);
      SafeParcelWriter.writeString(var1, 3, this.e(), false);
      SafeParcelWriter.writeParcelable(var1, 4, this.b(), var2, false);
      SafeParcelWriter.writeIntegerObject(var1, 5, this.c(), false);
      SafeParcelWriter.writeByte(var1, 6, iz.a(this.e));
      SafeParcelWriter.writeByte(var1, 7, iz.a(this.f));
      SafeParcelWriter.writeByte(var1, 8, iz.a(this.g));
      SafeParcelWriter.writeByte(var1, 9, iz.a(this.h));
      SafeParcelWriter.writeByte(var1, 10, iz.a(this.i));
      SafeParcelWriter.writeParcelable(var1, 11, this.d(), var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
