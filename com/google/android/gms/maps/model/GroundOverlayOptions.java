package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

@SafeParcelable.Class(
   creator = "GroundOverlayOptionsCreator"
)
@SafeParcelable.Reserved({1})
public final class GroundOverlayOptions extends AbstractSafeParcelable {

   public static final Creator<GroundOverlayOptions> CREATOR = new jm();
   @NonNull
   @SafeParcelable.Field(
      getter = "getWrappedImageDescriptorImplBinder",
      id = 2,
      type = "android.os.IBinder"
   )
   private jc a;
   @SafeParcelable.Field(
      getter = "getLocation",
      id = 3
   )
   private LatLng b;
   @SafeParcelable.Field(
      getter = "getWidth",
      id = 4
   )
   private float c;
   @SafeParcelable.Field(
      getter = "getHeight",
      id = 5
   )
   private float d;
   @SafeParcelable.Field(
      getter = "getBounds",
      id = 6
   )
   private LatLngBounds e;
   @SafeParcelable.Field(
      getter = "getBearing",
      id = 7
   )
   private float f;
   @SafeParcelable.Field(
      getter = "getZIndex",
      id = 8
   )
   private float g;
   @SafeParcelable.Field(
      getter = "isVisible",
      id = 9
   )
   private boolean h = true;
   @SafeParcelable.Field(
      getter = "getTransparency",
      id = 10
   )
   private float i = 0.0F;
   @SafeParcelable.Field(
      getter = "getAnchorU",
      id = 11
   )
   private float j = 0.5F;
   @SafeParcelable.Field(
      getter = "getAnchorV",
      id = 12
   )
   private float k = 0.5F;
   @SafeParcelable.Field(
      getter = "isClickable",
      id = 13
   )
   private boolean l = false;


   public GroundOverlayOptions() {}

   @SafeParcelable.Constructor
   public GroundOverlayOptions(
      @SafeParcelable.Param(
         id = 2
      ) IBinder var1, 
      @SafeParcelable.Param(
         id = 3
      ) LatLng var2, 
      @SafeParcelable.Param(
         id = 4
      ) float var3, 
      @SafeParcelable.Param(
         id = 5
      ) float var4, 
      @SafeParcelable.Param(
         id = 6
      ) LatLngBounds var5, 
      @SafeParcelable.Param(
         id = 7
      ) float var6, 
      @SafeParcelable.Param(
         id = 8
      ) float var7, 
      @SafeParcelable.Param(
         id = 9
      ) boolean var8, 
      @SafeParcelable.Param(
         id = 10
      ) float var9, 
      @SafeParcelable.Param(
         id = 11
      ) float var10, 
      @SafeParcelable.Param(
         id = 12
      ) float var11, 
      @SafeParcelable.Param(
         id = 13
      ) boolean var12) {
      this.a = new jc(IObjectWrapper.Stub.a(var1));
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
      this.l = var12;
   }

   public final LatLng a() {
      return this.b;
   }

   public final float b() {
      return this.c;
   }

   public final float c() {
      return this.d;
   }

   public final LatLngBounds d() {
      return this.e;
   }

   public final float e() {
      return this.f;
   }

   public final float f() {
      return this.g;
   }

   public final float g() {
      return this.i;
   }

   public final float h() {
      return this.j;
   }

   public final float i() {
      return this.k;
   }

   public final boolean j() {
      return this.h;
   }

   public final boolean k() {
      return this.l;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeIBinder(var1, 2, this.a.a().asBinder(), false);
      SafeParcelWriter.writeParcelable(var1, 3, this.a(), var2, false);
      SafeParcelWriter.writeFloat(var1, 4, this.b());
      SafeParcelWriter.writeFloat(var1, 5, this.c());
      SafeParcelWriter.writeParcelable(var1, 6, this.d(), var2, false);
      SafeParcelWriter.writeFloat(var1, 7, this.e());
      SafeParcelWriter.writeFloat(var1, 8, this.f());
      SafeParcelWriter.writeBoolean(var1, 9, this.j());
      SafeParcelWriter.writeFloat(var1, 10, this.g());
      SafeParcelWriter.writeFloat(var1, 11, this.h());
      SafeParcelWriter.writeFloat(var1, 12, this.i());
      SafeParcelWriter.writeBoolean(var1, 13, this.k());
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
