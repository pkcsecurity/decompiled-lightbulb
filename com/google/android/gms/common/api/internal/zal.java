package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zam;
import com.google.android.gms.common.api.internal.zan;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.concurrent.atomic.AtomicReference;

public abstract class zal extends LifecycleCallback implements OnCancelListener {

   protected volatile boolean mStarted;
   protected final GoogleApiAvailability zacc;
   protected final AtomicReference<zam> zade;
   private final Handler zadf;


   protected zal(LifecycleFragment var1) {
      this(var1, GoogleApiAvailability.getInstance());
   }

   @VisibleForTesting
   private zal(LifecycleFragment var1, GoogleApiAvailability var2) {
      super(var1);
      this.zade = new AtomicReference((Object)null);
      this.zadf = new gh(Looper.getMainLooper());
      this.zacc = var2;
   }

   private static int zaa(@Nullable zam var0) {
      return var0 == null?-1:var0.zar();
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      zam var6;
      boolean var9;
      label43: {
         zam var7 = (zam)this.zade.get();
         boolean var5 = true;
         boolean var4 = true;
         switch(var1) {
         case 1:
            if(var2 == -1) {
               var6 = var7;
               var9 = var5;
               break label43;
            }

            var6 = var7;
            if(var2 == 0) {
               var1 = 13;
               if(var3 != null) {
                  var1 = var3.getIntExtra("<<ResolutionFailureErrorDetail>>", 13);
               }

               var6 = new zam(new ConnectionResult(var1, (PendingIntent)null), zaa(var7));
               this.zade.set(var6);
            }
            break;
         case 2:
            int var10 = this.zacc.isGooglePlayServicesAvailable(this.getActivity());
            boolean var8;
            if(var10 == 0) {
               var8 = var4;
            } else {
               var8 = false;
            }

            if(var7 == null) {
               return;
            }

            var6 = var7;
            var9 = var8;
            if(var7.getConnectionResult().getErrorCode() == 18) {
               var6 = var7;
               var9 = var8;
               if(var10 == 18) {
                  return;
               }
            }
            break label43;
         default:
            var6 = var7;
         }

         var9 = false;
      }

      if(var9) {
         this.zaq();
      } else {
         if(var6 != null) {
            this.zaa(var6.getConnectionResult(), var6.zar());
         }

      }
   }

   public void onCancel(DialogInterface var1) {
      this.zaa(new ConnectionResult(13, (PendingIntent)null), zaa((zam)this.zade.get()));
      this.zaq();
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(var1 != null) {
         AtomicReference var2 = this.zade;
         zam var3;
         if(var1.getBoolean("resolving_error", false)) {
            var3 = new zam(new ConnectionResult(var1.getInt("failed_status"), (PendingIntent)var1.getParcelable("failed_resolution")), var1.getInt("failed_client_id", -1));
         } else {
            var3 = null;
         }

         var2.set(var3);
      }

   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      zam var2 = (zam)this.zade.get();
      if(var2 != null) {
         var1.putBoolean("resolving_error", true);
         var1.putInt("failed_client_id", var2.zar());
         var1.putInt("failed_status", var2.getConnectionResult().getErrorCode());
         var1.putParcelable("failed_resolution", var2.getConnectionResult().getResolution());
      }

   }

   public void onStart() {
      super.onStart();
      this.mStarted = true;
   }

   public void onStop() {
      super.onStop();
      this.mStarted = false;
   }

   protected abstract void zaa(ConnectionResult var1, int var2);

   public final void zab(ConnectionResult var1, int var2) {
      zam var3 = new zam(var1, var2);
      if(this.zade.compareAndSet((Object)null, var3)) {
         this.zadf.post(new zan(this, var3));
      }

   }

   protected abstract void zao();

   protected final void zaq() {
      this.zade.set((Object)null);
      this.zao();
   }
}
