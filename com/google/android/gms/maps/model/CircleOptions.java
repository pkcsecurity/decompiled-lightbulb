package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import java.util.List;

@SafeParcelable.Class(
   creator = "CircleOptionsCreator"
)
@SafeParcelable.Reserved({1})
public final class CircleOptions extends AbstractSafeParcelable {

   public static final Creator<CircleOptions> CREATOR = new jl();
   @SafeParcelable.Field(
      getter = "getCenter",
      id = 2
   )
   private LatLng a = null;
   @SafeParcelable.Field(
      getter = "getRadius",
      id = 3
   )
   private double b = 0.0D;
   @SafeParcelable.Field(
      getter = "getStrokeWidth",
      id = 4
   )
   private float c = 10.0F;
   @SafeParcelable.Field(
      getter = "getStrokeColor",
      id = 5
   )
   private int d = -16777216;
   @SafeParcelable.Field(
      getter = "getFillColor",
      id = 6
   )
   private int e = 0;
   @SafeParcelable.Field(
      getter = "getZIndex",
      id = 7
   )
   private float f = 0.0F;
   @SafeParcelable.Field(
      getter = "isVisible",
      id = 8
   )
   private boolean g = true;
   @SafeParcelable.Field(
      getter = "isClickable",
      id = 9
   )
   private boolean h = false;
   @Nullable
   @SafeParcelable.Field(
      getter = "getStrokePattern",
      id = 10
   )
   private List<PatternItem> i = null;


   public CircleOptions() {}

   @SafeParcelable.Constructor
   public CircleOptions(
      @SafeParcelable.Param(
         id = 2
      ) LatLng var1, 
      @SafeParcelable.Param(
         id = 3
      ) double var2, 
      @SafeParcelable.Param(
         id = 4
      ) float var4, 
      @SafeParcelable.Param(
         id = 5
      ) int var5, 
      @SafeParcelable.Param(
         id = 6
      ) int var6, 
      @SafeParcelable.Param(
         id = 7
      ) float var7, 
      @SafeParcelable.Param(
         id = 8
      ) boolean var8, 
      @SafeParcelable.Param(
         id = 9
      ) boolean var9, @Nullable 
      @SafeParcelable.Param(
         id = 10
      ) List<PatternItem> var10) {
      this.a = var1;
      this.b = var2;
      this.c = var4;
      this.d = var5;
      this.e = var6;
      this.f = var7;
      this.g = var8;
      this.h = var9;
      this.i = var10;
   }

   public final CircleOptions a(double var1) {
      this.b = var1;
      return this;
   }

   public final CircleOptions a(float var1) {
      this.c = var1;
      return this;
   }

   public final CircleOptions a(int var1) {
      this.d = var1;
      return this;
   }

   public final CircleOptions a(LatLng var1) {
      this.a = var1;
      return this;
   }

   public final LatLng a() {
      return this.a;
   }

   public final double b() {
      return this.b;
   }

   public final CircleOptions b(float var1) {
      this.f = var1;
      return this;
   }

   public final CircleOptions b(int var1) {
      this.e = var1;
      return this;
   }

   public final float c() {
      return this.c;
   }

   public final int d() {
      return this.d;
   }

   @Nullable
   public final List<PatternItem> e() {
      return this.i;
   }

   public final int f() {
      return this.e;
   }

   public final float g() {
      return this.f;
   }

   public final boolean h() {
      return this.g;
   }

   public final boolean i() {
      return this.h;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.a(), var2, false);
      SafeParcelWriter.writeDouble(var1, 3, this.b());
      SafeParcelWriter.writeFloat(var1, 4, this.c());
      SafeParcelWriter.writeInt(var1, 5, this.d());
      SafeParcelWriter.writeInt(var1, 6, this.f());
      SafeParcelWriter.writeFloat(var1, 7, this.g());
      SafeParcelWriter.writeBoolean(var1, 8, this.h());
      SafeParcelWriter.writeBoolean(var1, 9, this.i());
      SafeParcelWriter.writeTypedList(var1, 10, this.e(), false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
