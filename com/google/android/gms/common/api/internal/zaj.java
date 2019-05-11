package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.LifecycleActivity;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zal;
import com.google.android.gms.common.api.internal.zam;
import com.google.android.gms.common.internal.Preconditions;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zaj extends zal {

   private final SparseArray<zaj.zaa> zacv = new SparseArray();


   private zaj(LifecycleFragment var1) {
      super(var1);
      this.mLifecycleFragment.addCallback("AutoManageHelper", this);
   }

   public static zaj zaa(LifecycleActivity var0) {
      LifecycleFragment var2 = getFragment(var0);
      zaj var1 = (zaj)var2.getCallbackOrNull("AutoManageHelper", zaj.class);
      return var1 != null?var1:new zaj(var2);
   }

   @Nullable
   private final zaj.zaa zab(int var1) {
      return this.zacv.size() <= var1?null:(zaj.zaa)this.zacv.get(this.zacv.keyAt(var1));
   }

   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      for(int var5 = 0; var5 < this.zacv.size(); ++var5) {
         zaj.zaa var6 = this.zab(var5);
         if(var6 != null) {
            var3.append(var1).append("GoogleApiClient #").print(var6.zacw);
            var3.println(":");
            var6.zacx.dump(String.valueOf(var1).concat("  "), var2, var3, var4);
         }
      }

   }

   public void onStart() {
      super.onStart();
      boolean var2 = this.mStarted;
      String var3 = String.valueOf(this.zacv);
      StringBuilder var4 = new StringBuilder(String.valueOf(var3).length() + 14);
      var4.append("onStart ");
      var4.append(var2);
      var4.append(" ");
      var4.append(var3);
      Log.d("AutoManageHelper", var4.toString());
      if(this.zade.get() == null) {
         for(int var1 = 0; var1 < this.zacv.size(); ++var1) {
            zaj.zaa var5 = this.zab(var1);
            if(var5 != null) {
               var5.zacx.connect();
            }
         }
      }

   }

   public void onStop() {
      super.onStop();

      for(int var1 = 0; var1 < this.zacv.size(); ++var1) {
         zaj.zaa var2 = this.zab(var1);
         if(var2 != null) {
            var2.zacx.disconnect();
         }
      }

   }

   public final void zaa(int var1) {
      zaj.zaa var2 = (zaj.zaa)this.zacv.get(var1);
      this.zacv.remove(var1);
      if(var2 != null) {
         var2.zacx.unregisterConnectionFailedListener(var2);
         var2.zacx.disconnect();
      }

   }

   public final void zaa(int var1, GoogleApiClient var2, GoogleApiClient.OnConnectionFailedListener var3) {
      Preconditions.checkNotNull(var2, "GoogleApiClient instance cannot be null");
      boolean var4;
      if(this.zacv.indexOfKey(var1) < 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      StringBuilder var5 = new StringBuilder(54);
      var5.append("Already managing a GoogleApiClient with id ");
      var5.append(var1);
      Preconditions.checkState(var4, var5.toString());
      zam var10 = (zam)this.zade.get();
      var4 = this.mStarted;
      String var6 = String.valueOf(var10);
      StringBuilder var7 = new StringBuilder(String.valueOf(var6).length() + 49);
      var7.append("starting AutoManage for client ");
      var7.append(var1);
      var7.append(" ");
      var7.append(var4);
      var7.append(" ");
      var7.append(var6);
      Log.d("AutoManageHelper", var7.toString());
      zaj.zaa var8 = new zaj.zaa(var1, var2, var3);
      this.zacv.put(var1, var8);
      if(this.mStarted && var10 == null) {
         String var9 = String.valueOf(var2);
         var5 = new StringBuilder(String.valueOf(var9).length() + 11);
         var5.append("connecting ");
         var5.append(var9);
         Log.d("AutoManageHelper", var5.toString());
         var2.connect();
      }

   }

   protected final void zaa(ConnectionResult var1, int var2) {
      Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
      if(var2 < 0) {
         Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
      } else {
         zaj.zaa var3 = (zaj.zaa)this.zacv.get(var2);
         if(var3 != null) {
            this.zaa(var2);
            GoogleApiClient.OnConnectionFailedListener var4 = var3.zacy;
            if(var4 != null) {
               var4.onConnectionFailed(var1);
            }
         }

      }
   }

   protected final void zao() {
      for(int var1 = 0; var1 < this.zacv.size(); ++var1) {
         zaj.zaa var2 = this.zab(var1);
         if(var2 != null) {
            var2.zacx.connect();
         }
      }

   }

   final class zaa implements GoogleApiClient.OnConnectionFailedListener {

      public final int zacw;
      public final GoogleApiClient zacx;
      public final GoogleApiClient.OnConnectionFailedListener zacy;


      public zaa(int var2, GoogleApiClient var3, GoogleApiClient.OnConnectionFailedListener var4) {
         this.zacw = var2;
         this.zacx = var3;
         this.zacy = var4;
         var3.registerConnectionFailedListener(this);
      }

      public final void onConnectionFailed(@NonNull ConnectionResult var1) {
         String var2 = String.valueOf(var1);
         StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 27);
         var3.append("beginFailureResolution for ");
         var3.append(var2);
         Log.d("AutoManageHelper", var3.toString());
         zaj.this.zab(var1, this.zacw);
      }
   }
}
