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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.dynamic.DeferredLifecycleHelper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.OnDelegateCreatedListener;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.android.gms.maps.internal.zzbp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StreetViewPanoramaView extends FrameLayout {

   private final StreetViewPanoramaView.b zzcd;


   public StreetViewPanoramaView(Context var1) {
      super(var1);
      this.zzcd = new StreetViewPanoramaView.b(this, var1, (StreetViewPanoramaOptions)null);
   }

   public StreetViewPanoramaView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.zzcd = new StreetViewPanoramaView.b(this, var1, (StreetViewPanoramaOptions)null);
   }

   public StreetViewPanoramaView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.zzcd = new StreetViewPanoramaView.b(this, var1, (StreetViewPanoramaOptions)null);
   }

   public StreetViewPanoramaView(Context var1, StreetViewPanoramaOptions var2) {
      super(var1);
      this.zzcd = new StreetViewPanoramaView.b(this, var1, var2);
   }

   public void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback var1) {
      Preconditions.checkMainThread("getStreetViewPanoramaAsync() must be called on the main thread");
      this.zzcd.a(var1);
   }

   public final void onCreate(Bundle var1) {
      ThreadPolicy var2 = StrictMode.getThreadPolicy();
      StrictMode.setThreadPolicy((new Builder(var2)).permitAll().build());

      try {
         this.zzcd.a(var1);
         if(this.zzcd.a() == null) {
            DeferredLifecycleHelper.b((FrameLayout)this);
         }
      } finally {
         StrictMode.setThreadPolicy(var2);
      }

   }

   public void onDestroy() {
      this.zzcd.g();
   }

   public final void onLowMemory() {
      this.zzcd.h();
   }

   public final void onPause() {
      this.zzcd.d();
   }

   public void onResume() {
      this.zzcd.c();
   }

   public final void onSaveInstanceState(Bundle var1) {
      this.zzcd.b(var1);
   }

   public void onStart() {
      this.zzcd.b();
   }

   public void onStop() {
      this.zzcd.e();
   }

   @VisibleForTesting
   public static final class a implements StreetViewLifecycleDelegate {

      private final ViewGroup a;
      private final IStreetViewPanoramaViewDelegate b;
      private View c;


      public a(ViewGroup var1, IStreetViewPanoramaViewDelegate var2) {
         this.b = (IStreetViewPanoramaViewDelegate)Preconditions.checkNotNull(var2);
         this.a = (ViewGroup)Preconditions.checkNotNull(var1);
      }

      public final View a(LayoutInflater var1, ViewGroup var2, Bundle var3) {
         throw new UnsupportedOperationException("onCreateView not allowed on StreetViewPanoramaViewDelegate");
      }

      public final void a() {
         try {
            this.b.f();
         } catch (RemoteException var2) {
            throw new ji(var2);
         }
      }

      public final void a(Activity var1, Bundle var2, Bundle var3) {
         throw new UnsupportedOperationException("onInflate not allowed on StreetViewPanoramaViewDelegate");
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

      public final void a(OnStreetViewPanoramaReadyCallback var1) {
         try {
            this.b.a((zzbp)(new kj(this, var1)));
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
         throw new UnsupportedOperationException("onDestroyView not allowed on StreetViewPanoramaViewDelegate");
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
   }

   @VisibleForTesting
   static final class b extends DeferredLifecycleHelper<StreetViewPanoramaView.a> {

      private final ViewGroup a;
      private final Context b;
      private OnDelegateCreatedListener<StreetViewPanoramaView.a> c;
      private final StreetViewPanoramaOptions d;
      private final List<OnStreetViewPanoramaReadyCallback> e = new ArrayList();


      @VisibleForTesting
      b(ViewGroup var1, Context var2, StreetViewPanoramaOptions var3) {
         this.a = var1;
         this.b = var2;
         this.d = var3;
      }

      protected final void a(OnDelegateCreatedListener<StreetViewPanoramaView.a> var1) {
         this.c = var1;
         if(this.c != null && this.a() == null) {
            try {
               iw.a(this.b);
               IStreetViewPanoramaViewDelegate var5 = jb.a(this.b).a(ObjectWrapper.a((Object)this.b), this.d);
               this.c.a(new StreetViewPanoramaView.a(this.a, var5));
               Iterator var6 = this.e.iterator();

               while(var6.hasNext()) {
                  OnStreetViewPanoramaReadyCallback var2 = (OnStreetViewPanoramaReadyCallback)var6.next();
                  ((StreetViewPanoramaView.a)this.a()).a(var2);
               }

               this.e.clear();
            } catch (RemoteException var3) {
               throw new ji(var3);
            } catch (GooglePlayServicesNotAvailableException var4) {
               ;
            }
         }
      }

      public final void a(OnStreetViewPanoramaReadyCallback var1) {
         if(this.a() != null) {
            ((StreetViewPanoramaView.a)this.a()).a(var1);
         } else {
            this.e.add(var1);
         }
      }
   }
}
