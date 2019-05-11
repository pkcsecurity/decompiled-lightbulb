package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.ButtCap;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SafeParcelable.Class(
   creator = "PolylineOptionsCreator"
)
@SafeParcelable.Reserved({1})
public final class PolylineOptions extends AbstractSafeParcelable {

   public static final Creator<PolylineOptions> CREATOR = new ju();
   @SafeParcelable.Field(
      getter = "getPoints",
      id = 2
   )
   private final List<LatLng> a;
   @SafeParcelable.Field(
      getter = "getWidth",
      id = 3
   )
   private float b = 10.0F;
   @SafeParcelable.Field(
      getter = "getColor",
      id = 4
   )
   private int c = -16777216;
   @SafeParcelable.Field(
      getter = "getZIndex",
      id = 5
   )
   private float d = 0.0F;
   @SafeParcelable.Field(
      getter = "isVisible",
      id = 6
   )
   private boolean e = true;
   @SafeParcelable.Field(
      getter = "isGeodesic",
      id = 7
   )
   private boolean f = false;
   @SafeParcelable.Field(
      getter = "isClickable",
      id = 8
   )
   private boolean g = false;
   @NonNull
   @SafeParcelable.Field(
      getter = "getStartCap",
      id = 9
   )
   private Cap h = new ButtCap();
   @NonNull
   @SafeParcelable.Field(
      getter = "getEndCap",
      id = 10
   )
   private Cap i = new ButtCap();
   @SafeParcelable.Field(
      getter = "getJointType",
      id = 11
   )
   private int j = 0;
   @Nullable
   @SafeParcelable.Field(
      getter = "getPattern",
      id = 12
   )
   private List<PatternItem> k = null;


   public PolylineOptions() {
      this.a = new ArrayList();
   }

   @SafeParcelable.Constructor
   public PolylineOptions(
      @SafeParcelable.Param(
         id = 2
      ) List var1, 
      @SafeParcelable.Param(
         id = 3
      ) float var2, 
      @SafeParcelable.Param(
         id = 4
      ) int var3, 
      @SafeParcelable.Param(
         id = 5
      ) float var4, 
      @SafeParcelable.Param(
         id = 6
      ) boolean var5, 
      @SafeParcelable.Param(
         id = 7
      ) boolean var6, 
      @SafeParcelable.Param(
         id = 8
      ) boolean var7, @Nullable 
      @SafeParcelable.Param(
         id = 9
      ) Cap var8, @Nullable 
      @SafeParcelable.Param(
         id = 10
      ) Cap var9, 
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
      if(var8 != null) {
         this.h = var8;
      }

      if(var9 != null) {
         this.i = var9;
      }

      this.j = var10;
      this.k = var11;
   }

   public final PolylineOptions a(float var1) {
      this.b = var1;
      return this;
   }

   public final PolylineOptions a(int var1) {
      this.c = var1;
      return this;
   }

   public final PolylineOptions a(Iterable<LatLng> var1) {
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         LatLng var2 = (LatLng)var3.next();
         this.a.add(var2);
      }

      return this;
   }

   public final PolylineOptions a(boolean var1) {
      this.f = var1;
      return this;
   }

   public final List<LatLng> a() {
      return this.a;
   }

   public final float b() {
      return this.b;
   }

   public final PolylineOptions b(float var1) {
      this.d = var1;
      return this;
   }

   public final int c() {
      return this.c;
   }

   @NonNull
   public final Cap d() {
      return this.h;
   }

   @NonNull
   public final Cap e() {
      return this.i;
   }

   public final int f() {
      return this.j;
   }

   @Nullable
   public final List<PatternItem> g() {
      return this.k;
   }

   public final float h() {
      return this.d;
   }

   public final boolean i() {
      return this.e;
   }

   public final boolean j() {
      return this.f;
   }

   public final boolean k() {
      return this.g;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 2, this.a(), false);
      SafeParcelWriter.writeFloat(var1, 3, this.b());
      SafeParcelWriter.writeInt(var1, 4, this.c());
      SafeParcelWriter.writeFloat(var1, 5, this.h());
      SafeParcelWriter.writeBoolean(var1, 6, this.i());
      SafeParcelWriter.writeBoolean(var1, 7, this.j());
      SafeParcelWriter.writeBoolean(var1, 8, this.k());
      SafeParcelWriter.writeParcelable(var1, 9, this.d(), var2, false);
      SafeParcelWriter.writeParcelable(var1, 10, this.e(), var2, false);
      SafeParcelWriter.writeInt(var1, 11, this.f());
      SafeParcelWriter.writeTypedList(var1, 12, this.g(), false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
