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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.dynamic.DeferredLifecycleHelper;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.OnDelegateCreatedListener;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.android.gms.maps.internal.zzbp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SupportStreetViewPanoramaFragment extends Fragment {

   private final SupportStreetViewPanoramaFragment.b zzci = new SupportStreetViewPanoramaFragment.b(this);


   public static SupportStreetViewPanoramaFragment newInstance() {
      return new SupportStreetViewPanoramaFragment();
   }

   public static SupportStreetViewPanoramaFragment newInstance(StreetViewPanoramaOptions var0) {
      SupportStreetViewPanoramaFragment var1 = new SupportStreetViewPanoramaFragment();
      Bundle var2 = new Bundle();
      var2.putParcelable("StreetViewPanoramaOptions", var0);
      var1.setArguments(var2);
      return var1;
   }

   public void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback var1) {
      Preconditions.checkMainThread("getStreetViewPanoramaAsync() must be called on the main thread");
      this.zzci.a(var1);
   }

   public void onActivityCreated(Bundle var1) {
      if(var1 != null) {
         var1.setClassLoader(SupportStreetViewPanoramaFragment.class.getClassLoader());
      }

      super.onActivityCreated(var1);
   }

   public void onAttach(Activity var1) {
      super.onAttach(var1);
      this.zzci.a(var1);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.zzci.a(var1);
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      return this.zzci.a(var1, var2, var3);
   }

   public void onDestroy() {
      this.zzci.g();
      super.onDestroy();
   }

   public void onDestroyView() {
      this.zzci.f();
      super.onDestroyView();
   }

   public void onInflate(Activity var1, AttributeSet var2, Bundle var3) {
      ThreadPolicy var4 = StrictMode.getThreadPolicy();
      StrictMode.setThreadPolicy((new Builder(var4)).permitAll().build());

      try {
         super.onInflate(var1, var2, var3);
         this.zzci.a(var1);
         Bundle var7 = new Bundle();
         this.zzci.a(var1, var7, var3);
      } finally {
         StrictMode.setThreadPolicy(var4);
      }

   }

   public void onLowMemory() {
      this.zzci.h();
      super.onLowMemory();
   }

   public void onPause() {
      this.zzci.d();
      super.onPause();
   }

   public void onResume() {
      super.onResume();
      this.zzci.c();
   }

   public void onSaveInstanceState(Bundle var1) {
      if(var1 != null) {
         var1.setClassLoader(SupportStreetViewPanoramaFragment.class.getClassLoader());
      }

      super.onSaveInstanceState(var1);
      this.zzci.b(var1);
   }

   public void onStart() {
      super.onStart();
      this.zzci.b();
   }

   public void onStop() {
      this.zzci.e();
      super.onStop();
   }

   public void setArguments(Bundle var1) {
      super.setArguments(var1);
   }

   @VisibleForTesting
   public static final class a implements StreetViewLifecycleDelegate {

      private final Fragment a;
      private final IStreetViewPanoramaFragmentDelegate b;


      public a(Fragment var1, IStreetViewPanoramaFragmentDelegate var2) {
         this.b = (IStreetViewPanoramaFragmentDelegate)Preconditions.checkNotNull(var2);
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
            this.b.f();
         } catch (RemoteException var2) {
            throw new ji(var2);
         }
      }

      public final void a(Activity var1, Bundle var2, Bundle var3) {
         try {
            var2 = new Bundle();
            ja.a(var3, var2);
            this.b.a(ObjectWrapper.a((Object)var1), (StreetViewPanoramaOptions)null, var2);
            ja.a(var2, var3);
         } catch (RemoteException var4) {
            throw new ji(var4);
         }
      }

      public final void a(Bundle param1) {
         // $FF: Couldn't be decompiled
      }

      public final void a(OnStreetViewPanoramaReadyCallback var1) {
         try {
            this.b.a((zzbp)(new kl(this, var1)));
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

      public final void d() {
         try {
            this.b.g();
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
   }

   @VisibleForTesting
   static final class b extends DeferredLifecycleHelper<SupportStreetViewPanoramaFragment.a> {

      private final Fragment a;
      private OnDelegateCreatedListener<SupportStreetViewPanoramaFragment.a> b;
      private Activity c;
      private final List<OnStreetViewPanoramaReadyCallback> d = new ArrayList();


      @VisibleForTesting
      b(Fragment var1) {
         this.a = var1;
      }

      private final void a(Activity var1) {
         this.c = var1;
         this.i();
      }

      private final void i() {
         if(this.c != null && this.b != null && this.a() == null) {
            try {
               iw.a(this.c);
               IStreetViewPanoramaFragmentDelegate var1 = jb.a(this.c).b(ObjectWrapper.a((Object)this.c));
               this.b.a(new SupportStreetViewPanoramaFragment.a(this.a, var1));
               Iterator var5 = this.d.iterator();

               while(var5.hasNext()) {
                  OnStreetViewPanoramaReadyCallback var2 = (OnStreetViewPanoramaReadyCallback)var5.next();
                  ((SupportStreetViewPanoramaFragment.a)this.a()).a(var2);
               }

               this.d.clear();
            } catch (RemoteException var3) {
               throw new ji(var3);
            } catch (GooglePlayServicesNotAvailableException var4) {
               ;
            }
         }
      }

      protected final void a(OnDelegateCreatedListener<SupportStreetViewPanoramaFragment.a> var1) {
         this.b = var1;
         this.i();
      }

      public final void a(OnStreetViewPanoramaReadyCallback var1) {
         if(this.a() != null) {
            ((SupportStreetViewPanoramaFragment.a)this.a()).a(var1);
         } else {
            this.d.add(var1);
         }
      }
   }
}
