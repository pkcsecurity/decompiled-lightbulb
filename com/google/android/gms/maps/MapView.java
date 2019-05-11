package com.google.android.gms.maps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.dynamic.DeferredLifecycleHelper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.OnDelegateCreatedListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.internal.zzap;
import java.util.ArrayList;
import java.util.List;

public class MapView extends FrameLayout {

   private final MapView.b zzbg;


   public MapView(Context var1) {
      super(var1);
      this.zzbg = new MapView.b(this, var1, (GoogleMapOptions)null);
      this.setClickable(true);
   }

   public MapView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.zzbg = new MapView.b(this, var1, GoogleMapOptions.a(var1, var2));
      this.setClickable(true);
   }

   public MapView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.zzbg = new MapView.b(this, var1, GoogleMapOptions.a(var1, var2));
      this.setClickable(true);
   }

   public MapView(Context var1, GoogleMapOptions var2) {
      super(var1);
      this.zzbg = new MapView.b(this, var1, var2);
      this.setClickable(true);
   }

   public void getMapAsync(OnMapReadyCallback var1) {
      Preconditions.checkMainThread("getMapAsync() must be called on the main thread");
      this.zzbg.a(var1);
   }

   public final void onCreate(Bundle var1) {
      ThreadPolicy var2 = StrictMode.getThreadPolicy();
      StrictMode.setThreadPolicy((new Builder(var2)).permitAll().build());

      try {
         this.zzbg.a(var1);
         if(this.zzbg.a() == null) {
            DeferredLifecycleHelper.b((FrameLayout)this);
         }
      } finally {
         StrictMode.setThreadPolicy(var2);
      }

   }

   public final void onDestroy() {
      this.zzbg.g();
   }

   public final void onEnterAmbient(Bundle var1) {
      Preconditions.checkMainThread("onEnterAmbient() must be called on the main thread");
      MapView.b var2 = this.zzbg;
      if(var2.a() != null) {
         ((MapView.a)var2.a()).c(var1);
      }

   }

   public final void onExitAmbient() {
      Preconditions.checkMainThread("onExitAmbient() must be called on the main thread");
      MapView.b var1 = this.zzbg;
      if(var1.a() != null) {
         ((MapView.a)var1.a()).h();
      }

   }

   public final void onLowMemory() {
      this.zzbg.h();
   }

   public final void onPause() {
      this.zzbg.d();
   }

   public final void onResume() {
      this.zzbg.c();
   }

   public final void onSaveInstanceState(Bundle var1) {
      this.zzbg.b(var1);
   }

   public final void onStart() {
      this.zzbg.b();
   }

   public final void onStop() {
      this.zzbg.e();
   }

   @VisibleForTesting
   public static final class a implements MapLifecycleDelegate {

      private final ViewGroup a;
      private final IMapViewDelegate b;
      private View c;


      public a(ViewGroup var1, IMapViewDelegate var2) {
         this.b = (IMapViewDelegate)Preconditions.checkNotNull(var2);
         this.a = (ViewGroup)Preconditions.checkNotNull(var1);
      }

      public final View a(LayoutInflater var1, ViewGroup var2, Bundle var3) {
         throw new UnsupportedOperationException("onCreateView not allowed on MapViewDelegate");
      }

      public final void a() {
         try {
            this.b.g();
         } catch (RemoteException var2) {
            throw new ji(var2);
         }
      }

      public final void a(Activity var1, Bundle var2, Bundle var3) {
         throw new UnsupportedOperationException("onInflate not allowed on MapViewDelegate");
      }

      public final void a(Bundle var1) {
         try {
            Bundle var2 = new Bundle();
            ja.a(var1, var2);
            this.b.a(var2);
            ja.a(var2, var1);
            this.c = (View)ObjectWrapper.a(this.b.e());
            this.a.removeAllViews();
            this.a.addView(this.c);
         } catch (RemoteException var3) {
            throw new ji(var3);
         }
      }

      public final void a(OnMapReadyCallback var1) {
         try {
            this.b.a((zzap)(new kg(this, var1)));
         } catch (RemoteException var2) {
            throw new ji(var2);
         }
      }

      public final void b() {
         try {
            this.b.a();
         } catch (RemoteException var2) {
            throw new ji(var2);
         }
      }

      public final void b(Bundle var1) {
         try {
            Bundle var2 = new Bundle();
            ja.a(var1, var2);
            this.b.b(var2);
            ja.a(var2, var1);
         } catch (RemoteException var3) {
            throw new ji(var3);
         }
      }

      public final void c() {
         try {
            this.b.b();
         } catch (RemoteException var2) {
            throw new ji(var2);
         }
      }

      public final void c(Bundle var1) {
         try {
            Bundle var2 = new Bundle();
            ja.a(var1, var2);
            this.b.c(var2);
            ja.a(var2, var1);
         } catch (RemoteException var3) {
            throw new ji(var3);
         }
      }

      public final void d() {
         try {
            this.b.h();
         } catch (RemoteException var2) {
            throw new ji(var2);
         }
      }

      public final void e() {
         throw new UnsupportedOperationException("onDestroyView not allowed on MapViewDelegate");
      }

      public final void f() {
         try {
            this.b.c();
         } catch (RemoteException var2) {
            throw new ji(var2);
         }
      }

      public final void g() {
         try {
            this.b.d();
         } catch (RemoteException var2) {
            throw new ji(var2);
         }
      }

      public final void h() {
         try {
            this.b.f();
         } catch (RemoteException var2) {
            throw new ji(var2);
         }
      }
   }

   @VisibleForTesting
   static final class b extends DeferredLifecycleHelper<MapView.a> {

      private final ViewGroup a;
      private final Context b;
      private OnDelegateCreatedListener<MapView.a> c;
      private final GoogleMapOptions d;
      private final List<OnMapReadyCallback> e = new ArrayList();


      @VisibleForTesting
      b(ViewGroup var1, Context var2, GoogleMapOptions var3) {
         this.a = var1;
         this.b = var2;
         this.d = var3;
      }

      protected final void a(OnDelegateCreatedListener<MapView.a> param1) {
         // $FF: Couldn't be decompiled
      }

      public final void a(OnMapReadyCallback var1) {
         if(this.a() != null) {
            ((MapView.a)this.a()).a(var1);
         } else {
            this.e.add(var1);
         }
      }
   }
}
