package com.google.android.gms.maps;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.dynamic.DeferredLifecycleHelper;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.OnDelegateCreatedListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.internal.zzap;
import java.util.ArrayList;
import java.util.List;

public class SupportMapFragment extends Fragment {

   private final SupportMapFragment.b zzch = new SupportMapFragment.b(this);


   public static SupportMapFragment newInstance() {
      return new SupportMapFragment();
   }

   public static SupportMapFragment newInstance(GoogleMapOptions var0) {
      SupportMapFragment var1 = new SupportMapFragment();
      Bundle var2 = new Bundle();
      var2.putParcelable("MapOptions", var0);
      var1.setArguments(var2);
      return var1;
   }

   public void getMapAsync(OnMapReadyCallback var1) {
      Preconditions.checkMainThread("getMapAsync must be called on the main thread.");
      this.zzch.a(var1);
   }

   public void onActivityCreated(Bundle var1) {
      if(var1 != null) {
         var1.setClassLoader(SupportMapFragment.class.getClassLoader());
      }

      super.onActivityCreated(var1);
   }

   public void onAttach(Activity var1) {
      super.onAttach(var1);
      this.zzch.a(var1);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.zzch.a(var1);
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      View var4 = this.zzch.a(var1, var2, var3);
      var4.setClickable(true);
      return var4;
   }

   public void onDestroy() {
      this.zzch.g();
      super.onDestroy();
   }

   public void onDestroyView() {
      this.zzch.f();
      super.onDestroyView();
   }

   public final void onEnterAmbient(Bundle var1) {
      Preconditions.checkMainThread("onEnterAmbient must be called on the main thread.");
      SupportMapFragment.b var2 = this.zzch;
      if(var2.a() != null) {
         ((SupportMapFragment.a)var2.a()).c(var1);
      }

   }

   public final void onExitAmbient() {
      Preconditions.checkMainThread("onExitAmbient must be called on the main thread.");
      SupportMapFragment.b var1 = this.zzch;
      if(var1.a() != null) {
         ((SupportMapFragment.a)var1.a()).h();
      }

   }

   public void onInflate(Activity var1, AttributeSet var2, Bundle var3) {
      ThreadPolicy var4 = StrictMode.getThreadPolicy();
      StrictMode.setThreadPolicy((new Builder(var4)).permitAll().build());

      try {
         super.onInflate(var1, var2, var3);
         this.zzch.a(var1);
         GoogleMapOptions var8 = GoogleMapOptions.a(var1, var2);
         Bundle var5 = new Bundle();
         var5.putParcelable("MapOptions", var8);
         this.zzch.a(var1, var5, var3);
      } finally {
         StrictMode.setThreadPolicy(var4);
      }

   }

   public void onLowMemory() {
      this.zzch.h();
      super.onLowMemory();
   }

   public void onPause() {
      this.zzch.d();
      super.onPause();
   }

   public void onResume() {
      super.onResume();
      this.zzch.c();
   }

   public void onSaveInstanceState(Bundle var1) {
      if(var1 != null) {
         var1.setClassLoader(SupportMapFragment.class.getClassLoader());
      }

      super.onSaveInstanceState(var1);
      this.zzch.b(var1);
   }

   public void onStart() {
      super.onStart();
      this.zzch.b();
   }

   public void onStop() {
      this.zzch.e();
      super.onStop();
   }

   public void setArguments(Bundle var1) {
      super.setArguments(var1);
   }

   @VisibleForTesting
   static final class b extends DeferredLifecycleHelper<SupportMapFragment.a> {

      private final Fragment a;
      private OnDelegateCreatedListener<SupportMapFragment.a> b;
      private Activity c;
      private final List<OnMapReadyCallback> d = new ArrayList();


      @VisibleForTesting
      b(Fragment var1) {
         this.a = var1;
      }

      private final void a(Activity var1) {
         this.c = var1;
         this.i();
      }

      private final void i() {
         // $FF: Couldn't be decompiled
      }

      protected final void a(OnDelegateCreatedListener<SupportMapFragment.a> var1) {
         this.b = var1;
         this.i();
      }

      public final void a(OnMapReadyCallback var1) {
         if(this.a() != null) {
            ((SupportMapFragment.a)this.a()).a(var1);
         } else {
            this.d.add(var1);
         }
      }
   }

   @VisibleForTesting
   public static final class a implements MapLifecycleDelegate {

      private final Fragment a;
      private final IMapFragmentDelegate b;


      public a(Fragment var1, IMapFragmentDelegate var2) {
         this.b = (IMapFragmentDelegate)Preconditions.checkNotNull(var2);
         this.a = (Fragment)Preconditions.checkNotNull(var1);
      }

      public final View a(LayoutInflater var1, ViewGroup var2, Bundle var3) {
         IObjectWrapper var6;
         try {
            Bundle var4 = new Bundle();
            ja.a(var3, var4);
            var6 = this.b.a(ObjectWrapper.a((Object)var1), ObjectWrapper.a((Object)var2), var4);
            ja.a(var4, var3);
         } catch (RemoteException var5) {
            throw new ji(var5);
         }

         return (View)ObjectWrapper.a(var6);
      }

      public final void a() {
         try {
            this.b.g();
         } catch (RemoteException var2) {
            throw new ji(var2);
         }
      }

      public final void a(Activity var1, Bundle var2, Bundle var3) {
         GoogleMapOptions var6 = (GoogleMapOptions)var2.getParcelable("MapOptions");

         try {
            Bundle var4 = new Bundle();
            ja.a(var3, var4);
            this.b.a(ObjectWrapper.a((Object)var1), var6, var4);
            ja.a(var4, var3);
         } catch (RemoteException var5) {
            throw new ji(var5);
         }
      }

      public final void a(Bundle param1) {
         // $FF: Couldn't be decompiled
      }

      public final void a(OnMapReadyCallback var1) {
         try {
            this.b.a((zzap)(new kk(this, var1)));
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
         try {
            this.b.c();
         } catch (RemoteException var2) {
            throw new ji(var2);
         }
      }

      public final void f() {
         try {
            this.b.d();
         } catch (RemoteException var2) {
            throw new ji(var2);
         }
      }

      public final void g() {
         try {
            this.b.e();
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
}
