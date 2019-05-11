package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SafeParcelable.Class(
   creator = "PolygonOptionsCreator"
)
@SafeParcelable.Reserved({1})
public final class PolygonOptions extends AbstractSafeParcelable {

   public static final Creator<PolygonOptions> CREATOR = new jt();
   @SafeParcelable.Field(
      getter = "getPoints",
      id = 2
   )
   private final List<LatLng> a;
   @SafeParcelable.Field(
      getter = "getHolesForParcel",
      id = 3,
      type = "java.util.List"
   )
   private final List<List<LatLng>> b;
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
      getter = "isGeodesic",
      id = 9
   )
   private boolean h = false;
   @SafeParcelable.Field(
      getter = "isClickable",
      id = 10
   )
   private boolean i = false;
   @SafeParcelable.Field(
      getter = "getStrokeJointType",
      id = 11
   )
   private int j = 0;
   @Nullable
   @SafeParcelable.Field(
      getter = "getStrokePattern",
      id = 12
   )
   private List<PatternItem> k = null;


   public PolygonOptions() {
      this.a = new ArrayList();
      this.b = new ArrayList();
   }

   @SafeParcelable.Constructor
   public PolygonOptions(
      @SafeParcelable.Param(
         id = 2
      ) List<LatLng> var1, 
      @SafeParcelable.Param(
         id = 3
      ) List var2, 
      @SafeParcelable.Param(
         id = 4
      ) float var3, 
      @SafeParcelable.Param(
         id = 5
      ) int var4, 
      @SafeParcelable.Param(
         id = 6
      ) int var5, 
      @SafeParcelable.Param(
         id = 7
      ) float var6, 
      @SafeParcelable.Param(
         id = 8
      ) boolean var7, 
      @SafeParcelable.Param(
         id = 9
      ) boolean var8, 
      @SafeParcelable.Param(
         id = 10
      ) boolean var9, 
      @SafeParcelable.Param(
         id = 11
      ) int var10, @Nullable 
      @SafeParcelable.Param(
         id = 12
      ) List<PatternItem> var11) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
      this.f = var6;
      this.g = var7;
      this.h = var8;
      this.i = var9;
      this.j = var10;
      this.k = var11;
   }

   public final PolygonOptions a(float var1) {
      this.c = var1;
      return this;
   }

   public final PolygonOptions a(int var1) {
      this.d = var1;
      return this;
   }

   public final PolygonOptions a(Iterable<LatLng> var1) {
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         LatLng var2 = (LatLng)var3.next();
         this.a.add(var2);
      }

      return this;
   }

   public final PolygonOptions a(boolean var1) {
      this.h = var1;
      return this;
   }

   public final List<LatLng> a() {
      return this.a;
   }

   public final float b() {
      return this.c;
   }

   public final PolygonOptions b(float var1) {
      this.f = var1;
      return this;
   }

   public final PolygonOptions b(int var1) {
      this.e = var1;
      return this;
   }

   public final int c() {
      return this.d;
   }

   public final int d() {
      return this.j;
   }

   @Nullable
   public final List<PatternItem> e() {
      return this.k;
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

   public final boolean j() {
      return this.i;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 2, this.a(), false);
      SafeParcelWriter.writeList(var1, 3, this.b, false);
      SafeParcelWriter.writeFloat(var1, 4, this.b());
      SafeParcelWriter.writeInt(var1, 5, this.c());
      SafeParcelWriter.writeInt(var1, 6, this.f());
      SafeParcelWriter.writeFloat(var1, 7, this.g());
      SafeParcelWriter.writeBoolean(var1, 8, this.h());
      SafeParcelWriter.writeBoolean(var1, 9, this.i());
      SafeParcelWriter.writeBoolean(var1, 10, this.j());
      SafeParcelWriter.writeInt(var1, 11, this.d());
      SafeParcelWriter.writeTypedList(var1, 12, this.e(), false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
