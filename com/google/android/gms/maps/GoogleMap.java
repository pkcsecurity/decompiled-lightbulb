package com.google.android.gms.maps;

import android.graphics.Bitmap;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.view.View;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.zzd;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public final class GoogleMap {

   private final IGoogleMapDelegate a;
   private iy b;


   public GoogleMap(IGoogleMapDelegate var1) {
      this.a = (IGoogleMapDelegate)Preconditions.checkNotNull(var1);
   }

   public final CameraPosition a() {
      try {
         CameraPosition var1 = this.a.a();
         return var1;
      } catch (RemoteException var2) {
         throw new ji(var2);
      }
   }

   public final je a(CircleOptions var1) {
      try {
         je var3 = new je(this.a.a(var1));
         return var3;
      } catch (RemoteException var2) {
         throw new ji(var2);
      }
   }

   public final jf a(MarkerOptions param1) {
      // $FF: Couldn't be decompiled
   }

   public final jg a(PolygonOptions var1) {
      try {
         jg var3 = new jg(this.a.a(var1));
         return var3;
      } catch (RemoteException var2) {
         throw new ji(var2);
      }
   }

   public final jh a(PolylineOptions var1) {
      try {
         jh var3 = new jh(this.a.a(var1));
         return var3;
      } catch (RemoteException var2) {
         throw new ji(var2);
      }
   }

   public final void a(int var1) {
      try {
         this.a.a(var1);
      } catch (RemoteException var3) {
         throw new ji(var3);
      }
   }

   public final void a(GoogleMap.InfoWindowAdapter param1) {
      // $FF: Couldn't be decompiled
   }

   @Deprecated
   public final void a(@Nullable GoogleMap.OnCameraChangeListener param1) {
      // $FF: Couldn't be decompiled
   }

   public final void a(@Nullable GoogleMap.OnCameraIdleListener param1) {
      // $FF: Couldn't be decompiled
   }

   public final void a(@Nullable GoogleMap.OnCameraMoveListener param1) {
      // $FF: Couldn't be decompiled
   }

   public final void a(@Nullable GoogleMap.OnInfoWindowClickListener param1) {
      // $FF: Couldn't be decompiled
   }

   public final void a(@Nullable GoogleMap.OnMapClickListener param1) {
      // $FF: Couldn't be decompiled
   }

   public final void a(GoogleMap.OnMapLoadedCallback param1) {
      // $FF: Couldn't be decompiled
   }

   public final void a(@Nullable GoogleMap.OnMapLongClickListener param1) {
      // $FF: Couldn't be decompiled
   }

   public final void a(@Nullable GoogleMap.OnMarkerClickListener param1) {
      // $FF: Couldn't be decompiled
   }

   public final void a(@Nullable GoogleMap.OnMarkerDragListener param1) {
      // $FF: Couldn't be decompiled
   }

   public final void a(GoogleMap.SnapshotReadyCallback var1) {
      this.a(var1, (Bitmap)null);
   }

   public final void a(GoogleMap.SnapshotReadyCallback var1, Bitmap var2) {
      IObjectWrapper var4;
      if(var2 != null) {
         var4 = ObjectWrapper.a((Object)var2);
      } else {
         var4 = null;
      }

      ObjectWrapper var5 = (ObjectWrapper)var4;

      try {
         this.a.a(new kr(this, var1), var5);
      } catch (RemoteException var3) {
         throw new ji(var3);
      }
   }

   public final void a(iu var1) {
      try {
         this.a.a(var1.a());
      } catch (RemoteException var2) {
         throw new ji(var2);
      }
   }

   public final void a(iu param1, int param2, GoogleMap.CancelableCallback param3) {
      // $FF: Couldn't be decompiled
   }

   public final void a(boolean var1) {
      try {
         this.a.a(var1);
      } catch (RemoteException var3) {
         throw new ji(var3);
      }
   }

   public final iy b() {
      try {
         if(this.b == null) {
            this.b = new iy(this.a.b());
         }

         iy var1 = this.b;
         return var1;
      } catch (RemoteException var2) {
         throw new ji(var2);
      }
   }

   public final void b(iu var1) {
      try {
         this.a.b(var1.a());
      } catch (RemoteException var2) {
         throw new ji(var2);
      }
   }

   public final boolean b(boolean var1) {
      try {
         var1 = this.a.b(var1);
         return var1;
      } catch (RemoteException var3) {
         throw new ji(var3);
      }
   }

   public final ix c() {
      try {
         ix var1 = new ix(this.a.c());
         return var1;
      } catch (RemoteException var2) {
         throw new ji(var2);
      }
   }

   public final void c(boolean var1) {
      try {
         this.a.d(var1);
      } catch (RemoteException var3) {
         throw new ji(var3);
      }
   }

   @RequiresPermission(
      anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}
   )
   public final void d(boolean var1) {
      try {
         this.a.c(var1);
      } catch (RemoteException var3) {
         throw new ji(var3);
      }
   }

   public interface OnGroundOverlayClickListener {
   }

   public interface OnMapLoadedCallback {

      void a();
   }

   @Deprecated
   public interface OnMyLocationChangeListener {
   }

   public interface OnInfoWindowClickListener {

      void a(jf var1);
   }

   public interface OnMyLocationClickListener {
   }

   public interface OnCameraMoveStartedListener {
   }

   public interface OnInfoWindowCloseListener {
   }

   public interface OnMarkerClickListener {

      boolean a(jf var1);
   }

   public interface OnMyLocationButtonClickListener {
   }

   public interface InfoWindowAdapter {

      View getInfoContents(jf var1);

      View getInfoWindow(jf var1);
   }

   @Deprecated
   public interface OnCameraChangeListener {

      void a(CameraPosition var1);
   }

   public interface OnCameraIdleListener {

      void a();
   }

   public interface OnMapLongClickListener {

      void a(LatLng var1);
   }

   public interface OnCameraMoveCanceledListener {
   }

   public interface OnMarkerDragListener {

      void onMarkerDrag(jf var1);

      void onMarkerDragEnd(jf var1);

      void onMarkerDragStart(jf var1);
   }

   public interface OnIndoorStateChangeListener {
   }

   public interface OnCameraMoveListener {

      void b();
   }

   public interface OnInfoWindowLongClickListener {
   }

   public interface OnPolygonClickListener {
   }

   public interface OnCircleClickListener {
   }

   public interface CancelableCallback {

      void a();

      void b();
   }

   public interface OnMapClickListener {

      void a(LatLng var1);
   }

   public interface OnPolylineClickListener {
   }

   static final class a extends zzd {

      private final GoogleMap.CancelableCallback a;


      a(GoogleMap.CancelableCallback var1) {
         this.a = var1;
      }

      public final void a() {
         this.a.a();
      }

      public final void b() {
         this.a.b();
      }
   }

   public interface OnPoiClickListener {
   }

   public interface SnapshotReadyCallback {

      void a(Bitmap var1);
   }
}
