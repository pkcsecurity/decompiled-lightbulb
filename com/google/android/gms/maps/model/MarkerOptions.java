package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.LatLng;

@SafeParcelable.Class(
   creator = "MarkerOptionsCreator"
)
@SafeParcelable.Reserved({1})
public final class MarkerOptions extends AbstractSafeParcelable {

   public static final Creator<MarkerOptions> CREATOR = new jq();
   @SafeParcelable.Field(
      getter = "getPosition",
      id = 2
   )
   private LatLng a;
   @SafeParcelable.Field(
      getter = "getTitle",
      id = 3
   )
   private String b;
   @SafeParcelable.Field(
      getter = "getSnippet",
      id = 4
   )
   private String c;
   @SafeParcelable.Field(
      getter = "getWrappedIconDescriptorImplBinder",
      id = 5,
      type = "android.os.IBinder"
   )
   private jc d;
   @SafeParcelable.Field(
      getter = "getAnchorU",
      id = 6
   )
   private float e = 0.5F;
   @SafeParcelable.Field(
      getter = "getAnchorV",
      id = 7
   )
   private float f = 1.0F;
   @SafeParcelable.Field(
      getter = "isDraggable",
      id = 8
   )
   private boolean g;
   @SafeParcelable.Field(
      getter = "isVisible",
      id = 9
   )
   private boolean h = true;
   @SafeParcelable.Field(
      getter = "isFlat",
      id = 10
   )
   private boolean i = false;
   @SafeParcelable.Field(
      getter = "getRotation",
      id = 11
   )
   private float j = 0.0F;
   @SafeParcelable.Field(
      defaultValue = "0.5f",
      getter = "getInfoWindowAnchorU",
      id = 12
   )
   private float k = 0.5F;
   @SafeParcelable.Field(
      getter = "getInfoWindowAnchorV",
      id = 13
   )
   private float l = 0.0F;
   @SafeParcelable.Field(
      defaultValue = "1.0f",
      getter = "getAlpha",
      id = 14
   )
   private float m = 1.0F;
   @SafeParcelable.Field(
      getter = "getZIndex",
      id = 15
   )
   private float n;


   public MarkerOptions() {}

   @SafeParcelable.Constructor
   public MarkerOptions(
      @SafeParcelable.Param(
         id = 2
      ) LatLng var1, 
      @SafeParcelable.Param(
         id = 3
      ) String var2, 
      @SafeParcelable.Param(
         id = 4
      ) String var3, 
      @SafeParcelable.Param(
         id = 5
      ) IBinder var4, 
      @SafeParcelable.Param(
         id = 6
      ) float var5, 
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
      ) float var10, 
      @SafeParcelable.Param(
         id = 12
      ) float var11, 
      @SafeParcelable.Param(
         id = 13
      ) float var12, 
      @SafeParcelable.Param(
         id = 14
      ) float var13, 
      @SafeParcelable.Param(
         id = 15
      ) float var14) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      if(var4 == null) {
         this.d = null;
      } else {
         this.d = new jc(IObjectWrapper.Stub.a(var4));
      }

      this.e = var5;
      this.f = var6;
      this.g = var7;
      this.h = var8;
      this.i = var9;
      this.j = var10;
      this.k = var11;
      this.l = var12;
      this.m = var13;
      this.n = var14;
   }

   public final LatLng a() {
      return this.a;
   }

   public final MarkerOptions a(float var1) {
      this.j = var1;
      return this;
   }

   public final MarkerOptions a(float var1, float var2) {
      this.e = var1;
      this.f = var2;
      return this;
   }

   public final MarkerOptions a(@NonNull LatLng var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("latlng cannot be null - a position is required.");
      } else {
         this.a = var1;
         return this;
      }
   }

   public final MarkerOptions a(@Nullable String var1) {
      this.b = var1;
      return this;
   }

   public final MarkerOptions a(@Nullable jc var1) {
      this.d = var1;
      return this;
   }

   public final MarkerOptions a(boolean var1) {
      this.g = var1;
      return this;
   }

   public final MarkerOptions b(float var1, float var2) {
      this.k = var1;
      this.l = var2;
      return this;
   }

   public final MarkerOptions b(@Nullable String var1) {
      this.c = var1;
      return this;
   }

   public final MarkerOptions b(boolean var1) {
      this.i = var1;
      return this;
   }

   public final String b() {
      return this.b;
   }

   public final String c() {
      return this.c;
   }

   public final float d() {
      return this.e;
   }

   public final float e() {
      return this.f;
   }

   public final boolean f() {
      return this.g;
   }

   public final boolean g() {
      return this.h;
   }

   public final boolean h() {
      return this.i;
   }

   public final float i() {
      return this.j;
   }

   public final float j() {
      return this.k;
   }

   public final float k() {
      return this.l;
   }

   public final float l() {
      return this.m;
   }

   public final float m() {
      return this.n;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.a(), var2, false);
      SafeParcelWriter.writeString(var1, 3, this.b(), false);
      SafeParcelWriter.writeString(var1, 4, this.c(), false);
      IBinder var4;
      if(this.d == null) {
         var4 = null;
      } else {
         var4 = this.d.a().asBinder();
      }

      SafeParcelWriter.writeIBinder(var1, 5, var4, false);
      SafeParcelWriter.writeFloat(var1, 6, this.d());
      SafeParcelWriter.writeFloat(var1, 7, this.e());
      SafeParcelWriter.writeBoolean(var1, 8, this.f());
      SafeParcelWriter.writeBoolean(var1, 9, this.g());
      SafeParcelWriter.writeBoolean(var1, 10, this.h());
      SafeParcelWriter.writeFloat(var1, 11, this.i());
      SafeParcelWriter.writeFloat(var1, 12, this.j());
      SafeParcelWriter.writeFloat(var1, 13, this.k());
      SafeParcelWriter.writeFloat(var1, 14, this.l());
      SafeParcelWriter.writeFloat(var1, 15, this.m());
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
