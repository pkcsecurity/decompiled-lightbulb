package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.VisibleForTesting;
import com.google.android.gms.common.api.internal.ActivityLifecycleObserver;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class zaa extends ActivityLifecycleObserver {

   private final WeakReference<zaa.zaa> zack;


   public zaa(Activity var1) {
      this(zaa.zaa.zaa(var1));
   }

   @VisibleForTesting(
      otherwise = 2
   )
   private zaa(zaa.zaa var1) {
      this.zack = new WeakReference(var1);
   }

   public final ActivityLifecycleObserver onStopCallOnce(Runnable var1) {
      zaa.zaa var2 = (zaa.zaa)this.zack.get();
      if(var2 == null) {
         throw new IllegalStateException("The target activity has already been GC\'d");
      } else {
         var2.zaa(var1);
         return this;
      }
   }

   @VisibleForTesting(
      otherwise = 2
   )
   static class zaa extends LifecycleCallback {

      private List<Runnable> zacl = new ArrayList();


      private zaa(LifecycleFragment var1) {
         super(var1);
         this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
      }

      private static zaa.zaa zaa(Activity param0) {
         // $FF: Couldn't be decompiled
      }

      private final void zaa(Runnable var1) {
         synchronized(this){}

         try {
            this.zacl.add(var1);
         } finally {
            ;
         }

      }

      @MainThread
      public void onStop() {
         // $FF: Couldn't be decompiled
      }
   }
}
