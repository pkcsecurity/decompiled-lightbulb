package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.R;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

@SafeParcelable.Class(
   creator = "GoogleMapOptionsCreator"
)
@SafeParcelable.Reserved({1})
public final class GoogleMapOptions extends AbstractSafeParcelable implements ReflectedParcelable {

   public static final Creator<GoogleMapOptions> CREATOR = new ke();
   @SafeParcelable.Field(
      defaultValue = "-1",
      getter = "getZOrderOnTopForParcel",
      id = 2,
      type = "byte"
   )
   private Boolean a;
   @SafeParcelable.Field(
      defaultValue = "-1",
      getter = "getUseViewLifecycleInFragmentForParcel",
      id = 3,
      type = "byte"
   )
   private Boolean b;
   @SafeParcelable.Field(
      getter = "getMapType",
      id = 4
   )
   private int c = -1;
   @SafeParcelable.Field(
      getter = "getCamera",
      id = 5
   )
   private CameraPosition d;
   @SafeParcelable.Field(
      defaultValue = "-1",
      getter = "getZoomControlsEnabledForParcel",
      id = 6,
      type = "byte"
   )
   private Boolean e;
   @SafeParcelable.Field(
      defaultValue = "-1",
      getter = "getCompassEnabledForParcel",
      id = 7,
      type = "byte"
   )
   private Boolean f;
   @SafeParcelable.Field(
      defaultValue = "-1",
      getter = "getScrollGesturesEnabledForParcel",
      id = 8,
      type = "byte"
   )
   private Boolean g;
   @SafeParcelable.Field(
      defaultValue = "-1",
      getter = "getZoomGesturesEnabledForParcel",
      id = 9,
      type = "byte"
   )
   private Boolean h;
   @SafeParcelable.Field(
      defaultValue = "-1",
      getter = "getTiltGesturesEnabledForParcel",
      id = 10,
      type = "byte"
   )
   private Boolean i;
   @SafeParcelable.Field(
      defaultValue = "-1",
      getter = "getRotateGesturesEnabledForParcel",
      id = 11,
      type = "byte"
   )
   private Boolean j;
   @SafeParcelable.Field(
      defaultValue = "-1",
      getter = "getLiteModeForParcel",
      id = 12,
      type = "byte"
   )
   private Boolean k;
   @SafeParcelable.Field(
      defaultValue = "-1",
      getter = "getMapToolbarEnabledForParcel",
      id = 14,
      type = "byte"
   )
   private Boolean l;
   @SafeParcelable.Field(
      defaultValue = "-1",
      getter = "getAmbientEnabledForParcel",
      id = 15,
      type = "byte"
   )
   private Boolean m;
   @SafeParcelable.Field(
      getter = "getMinZoomPreference",
      id = 16
   )
   private Float n = null;
   @SafeParcelable.Field(
      getter = "getMaxZoomPreference",
      id = 17
   )
   private Float o = null;
   @SafeParcelable.Field(
      getter = "getLatLngBoundsForCameraTarget",
      id = 18
   )
   private LatLngBounds p = null;
   @SafeParcelable.Field(
      defaultValue = "-1",
      getter = "getScrollGesturesEnabledDuringRotateOrZoomForParcel",
      id = 19,
      type = "byte"
   )
   private Boolean q;


   public GoogleMapOptions() {}

   @SafeParcelable.Constructor
   public GoogleMapOptions(
      @SafeParcelable.Param(
         id = 2
      ) byte var1, 
      @SafeParcelable.Param(
         id = 3
      ) byte var2, 
      @SafeParcelable.Param(
         id = 4
      ) int var3, 
      @SafeParcelable.Param(
         id = 5
      ) CameraPosition var4, 
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
      ) byte var10, 
      @SafeParcelable.Param(
         id = 12
      ) byte var11, 
      @SafeParcelable.Param(
         id = 14
      ) byte var12, 
      @SafeParcelable.Param(
         id = 15
      ) byte var13, 
      @SafeParcelable.Param(
         id = 16
      ) Float var14, 
      @SafeParcelable.Param(
         id = 17
      ) Float var15, 
      @SafeParcelable.Param(
         id = 18
      ) LatLngBounds var16, 
      @SafeParcelable.Param(
         id = 19
      ) byte var17) {
      this.a = iz.a(var1);
      this.b = iz.a(var2);
      this.c = var3;
      this.d = var4;
      this.e = iz.a(var5);
      this.f = iz.a(var6);
      this.g = iz.a(var7);
      this.h = iz.a(var8);
      this.i = iz.a(var9);
      this.j = iz.a(var10);
      this.k = iz.a(var11);
      this.l = iz.a(var12);
      this.m = iz.a(var13);
      this.n = var14;
      this.o = var15;
      this.p = var16;
      this.q = iz.a(var17);
   }

   public static GoogleMapOptions a(Context var0, AttributeSet var1) {
      if(var0 != null && var1 != null) {
         TypedArray var2 = var0.getResources().obtainAttributes(var1, R.styleable.MapAttrs);
         GoogleMapOptions var3 = new GoogleMapOptions();
         if(var2.hasValue(R.styleable.MapAttrs_mapType)) {
            var3.a(var2.getInt(R.styleable.MapAttrs_mapType, -1));
         }

         if(var2.hasValue(R.styleable.MapAttrs_zOrderOnTop)) {
            var3.a(var2.getBoolean(R.styleable.MapAttrs_zOrderOnTop, false));
         }

         if(var2.hasValue(R.styleable.MapAttrs_useViewLifecycle)) {
            var3.b(var2.getBoolean(R.styleable.MapAttrs_useViewLifecycle, false));
         }

         if(var2.hasValue(R.styleable.MapAttrs_uiCompass)) {
            var3.d(var2.getBoolean(R.styleable.MapAttrs_uiCompass, true));
         }

         if(var2.hasValue(R.styleable.MapAttrs_uiRotateGestures)) {
            var3.h(var2.getBoolean(R.styleable.MapAttrs_uiRotateGestures, true));
         }

         if(var2.hasValue(R.styleable.MapAttrs_uiScrollGesturesDuringRotateOrZoom)) {
            var3.i(var2.getBoolean(R.styleable.MapAttrs_uiScrollGesturesDuringRotateOrZoom, true));
         }

         if(var2.hasValue(R.styleable.MapAttrs_uiScrollGestures)) {
            var3.e(var2.getBoolean(R.styleable.MapAttrs_uiScrollGestures, true));
         }

         if(var2.hasValue(R.styleable.MapAttrs_uiTiltGestures)) {
            var3.g(var2.getBoolean(R.styleable.MapAttrs_uiTiltGestures, true));
         }

         if(var2.hasValue(R.styleable.MapAttrs_uiZoomGestures)) {
            var3.f(var2.getBoolean(R.styleable.MapAttrs_uiZoomGestures, true));
         }

         if(var2.hasValue(R.styleable.MapAttrs_uiZoomControls)) {
            var3.c(var2.getBoolean(R.styleable.MapAttrs_uiZoomControls, true));
         }

         if(var2.hasValue(R.styleable.MapAttrs_liteMode)) {
            var3.j(var2.getBoolean(R.styleable.MapAttrs_liteMode, false));
         }

         if(var2.hasValue(R.styleable.MapAttrs_uiMapToolbar)) {
            var3.k(var2.getBoolean(R.styleable.MapAttrs_uiMapToolbar, true));
         }

         if(var2.hasValue(R.styleable.MapAttrs_ambientEnabled)) {
            var3.l(var2.getBoolean(R.styleable.MapAttrs_ambientEnabled, false));
         }

         if(var2.hasValue(R.styleable.MapAttrs_cameraMinZoomPreference)) {
            var3.a(var2.getFloat(R.styleable.MapAttrs_cameraMinZoomPreference, Float.NEGATIVE_INFINITY));
         }

         if(var2.hasValue(R.styleable.MapAttrs_cameraMinZoomPreference)) {
            var3.b(var2.getFloat(R.styleable.MapAttrs_cameraMaxZoomPreference, Float.POSITIVE_INFINITY));
         }

         var3.a(b(var0, var1));
         var3.a(c(var0, var1));
         var2.recycle();
         return var3;
      } else {
         return null;
      }
   }

   public static LatLngBounds b(Context var0, AttributeSet var1) {
      if(var0 != null) {
         if(var1 == null) {
            return null;
         } else {
            TypedArray var4 = var0.getResources().obtainAttributes(var1, R.styleable.MapAttrs);
            Float var5;
            if(var4.hasValue(R.styleable.MapAttrs_latLngBoundsSouthWestLatitude)) {
               var5 = Float.valueOf(var4.getFloat(R.styleable.MapAttrs_latLngBoundsSouthWestLatitude, 0.0F));
            } else {
               var5 = null;
            }

            Float var6;
            if(var4.hasValue(R.styleable.MapAttrs_latLngBoundsSouthWestLongitude)) {
               var6 = Float.valueOf(var4.getFloat(R.styleable.MapAttrs_latLngBoundsSouthWestLongitude, 0.0F));
            } else {
               var6 = null;
            }

            Float var2;
            if(var4.hasValue(R.styleable.MapAttrs_latLngBoundsNorthEastLatitude)) {
               var2 = Float.valueOf(var4.getFloat(R.styleable.MapAttrs_latLngBoundsNorthEastLatitude, 0.0F));
            } else {
               var2 = null;
            }

            Float var3;
            if(var4.hasValue(R.styleable.MapAttrs_latLngBoundsNorthEastLongitude)) {
               var3 = Float.valueOf(var4.getFloat(R.styleable.MapAttrs_latLngBoundsNorthEastLongitude, 0.0F));
            } else {
               var3 = null;
            }

            var4.recycle();
            return var5 != null && var6 != null && var2 != null?(var3 == null?null:new LatLngBounds(new LatLng((double)var5.floatValue(), (double)var6.floatValue()), new LatLng((double)var2.floatValue(), (double)var3.floatValue()))):null;
         }
      } else {
         return null;
      }
   }

   public static CameraPosition c(Context var0, AttributeSet var1) {
      if(var0 != null && var1 != null) {
         TypedArray var5 = var0.getResources().obtainAttributes(var1, R.styleable.MapAttrs);
         float var2;
         if(var5.hasValue(R.styleable.MapAttrs_cameraTargetLat)) {
            var2 = var5.getFloat(R.styleable.MapAttrs_cameraTargetLat, 0.0F);
         } else {
            var2 = 0.0F;
         }

         float var3;
         if(var5.hasValue(R.styleable.MapAttrs_cameraTargetLng)) {
            var3 = var5.getFloat(R.styleable.MapAttrs_cameraTargetLng, 0.0F);
         } else {
            var3 = 0.0F;
         }

         LatLng var6 = new LatLng((double)var2, (double)var3);
         CameraPosition.a var4 = CameraPosition.a();
         var4.a(var6);
         if(var5.hasValue(R.styleable.MapAttrs_cameraZoom)) {
            var4.a(var5.getFloat(R.styleable.MapAttrs_cameraZoom, 0.0F));
         }

         if(var5.hasValue(R.styleable.MapAttrs_cameraBearing)) {
            var4.c(var5.getFloat(R.styleable.MapAttrs_cameraBearing, 0.0F));
         }

         if(var5.hasValue(R.styleable.MapAttrs_cameraTilt)) {
            var4.b(var5.getFloat(R.styleable.MapAttrs_cameraTilt, 0.0F));
         }

         var5.recycle();
         return var4.a();
      } else {
         return null;
      }
   }

   public final int a() {
      return this.c;
   }

   public final GoogleMapOptions a(float var1) {
      this.n = Float.valueOf(var1);
      return this;
   }

   public final GoogleMapOptions a(int var1) {
      this.c = var1;
      return this;
   }

   public final GoogleMapOptions a(CameraPosition var1) {
      this.d = var1;
      return this;
   }

   public final GoogleMapOptions a(LatLngBounds var1) {
      this.p = var1;
      return this;
   }

   public final GoogleMapOptions a(boolean var1) {
      this.a = Boolean.valueOf(var1);
      return this;
   }

   public final GoogleMapOptions b(float var1) {
      this.o = Float.valueOf(var1);
      return this;
   }

   public final GoogleMapOptions b(boolean var1) {
      this.b = Boolean.valueOf(var1);
      return this;
   }

   public final CameraPosition b() {
      return this.d;
   }

   public final GoogleMapOptions c(boolean var1) {
      this.e = Boolean.valueOf(var1);
      return this;
   }

   public final Float c() {
      return this.n;
   }

   public final GoogleMapOptions d(boolean var1) {
      this.f = Boolean.valueOf(var1);
      return this;
   }

   public final Float d() {
      return this.o;
   }

   public final GoogleMapOptions e(boolean var1) {
      this.g = Boolean.valueOf(var1);
      return this;
   }

   public final LatLngBounds e() {
      return this.p;
   }

   public final GoogleMapOptions f(boolean var1) {
      this.h = Boolean.valueOf(var1);
      return this;
   }

   public final GoogleMapOptions g(boolean var1) {
      this.i = Boolean.valueOf(var1);
      return this;
   }

   public final GoogleMapOptions h(boolean var1) {
      this.j = Boolean.valueOf(var1);
      return this;
   }

   public final GoogleMapOptions i(boolean var1) {
      this.q = Boolean.valueOf(var1);
      return this;
   }

   public final GoogleMapOptions j(boolean var1) {
      this.k = Boolean.valueOf(var1);
      return this;
   }

   public final GoogleMapOptions k(boolean var1) {
      this.l = Boolean.valueOf(var1);
      return this;
   }

   public final GoogleMapOptions l(boolean var1) {
      this.m = Boolean.valueOf(var1);
      return this;
   }

   public final String toString() {
      return Objects.toStringHelper(this).add("MapType", Integer.valueOf(this.c)).add("LiteMode", this.k).add("Camera", this.d).add("CompassEnabled", this.f).add("ZoomControlsEnabled", this.e).add("ScrollGesturesEnabled", this.g).add("ZoomGesturesEnabled", this.h).add("TiltGesturesEnabled", this.i).add("RotateGesturesEnabled", this.j).add("ScrollGesturesEnabledDuringRotateOrZoom", this.q).add("MapToolbarEnabled", this.l).add("AmbientEnabled", this.m).add("MinZoomPreference", this.n).add("MaxZoomPreference", this.o).add("LatLngBoundsForCameraTarget", this.p).add("ZOrderOnTop", this.a).add("UseViewLifecycleInFragment", this.b).toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeByte(var1, 2, iz.a(this.a));
      SafeParcelWriter.writeByte(var1, 3, iz.a(this.b));
      SafeParcelWriter.writeInt(var1, 4, this.a());
      SafeParcelWriter.writeParcelable(var1, 5, this.b(), var2, false);
      SafeParcelWriter.writeByte(var1, 6, iz.a(this.e));
      SafeParcelWriter.writeByte(var1, 7, iz.a(this.f));
      SafeParcelWriter.writeByte(var1, 8, iz.a(this.g));
      SafeParcelWriter.writeByte(var1, 9, iz.a(this.h));
      SafeParcelWriter.writeByte(var1, 10, iz.a(this.i));
      SafeParcelWriter.writeByte(var1, 11, iz.a(this.j));
      SafeParcelWriter.writeByte(var1, 12, iz.a(this.k));
      SafeParcelWriter.writeByte(var1, 14, iz.a(this.l));
      SafeParcelWriter.writeByte(var1, 15, iz.a(this.m));
      SafeParcelWriter.writeFloatObject(var1, 16, this.c(), false);
      SafeParcelWriter.writeFloatObject(var1, 17, this.d(), false);
      SafeParcelWriter.writeParcelable(var1, 18, this.e(), var2, false);
      SafeParcelWriter.writeByte(var1, 19, iz.a(this.q));
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
