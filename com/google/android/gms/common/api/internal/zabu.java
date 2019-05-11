package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.PendingIntent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zal;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import java.util.concurrent.CancellationException;

public class zabu extends zal {

   private li<Void> zajo = new li();


   private zabu(LifecycleFragment var1) {
      super(var1);
      this.mLifecycleFragment.addCallback("GmsAvailabilityHelper", this);
   }

   public static zabu zac(Activity var0) {
      LifecycleFragment var2 = getFragment(var0);
      zabu var1 = (zabu)var2.getCallbackOrNull("GmsAvailabilityHelper", zabu.class);
      if(var1 != null) {
         if(var1.zajo.a().a()) {
            var1.zajo = new li();
         }

         return var1;
      } else {
         return new zabu(var2);
      }
   }

   public final lh<Void> getTask() {
      return this.zajo.a();
   }

   public void onDestroy() {
      super.onDestroy();
      this.zajo.b(new CancellationException("Host activity was destroyed before Google Play services could be made available."));
   }

   protected final void zaa(ConnectionResult var1, int var2) {
      this.zajo.a(ApiExceptionUtil.fromStatus(new Status(var1.getErrorCode(), var1.getErrorMessage(), var1.getResolution())));
   }

   protected final void zao() {
      int var1 = this.zacc.isGooglePlayServicesAvailable(this.mLifecycleFragment.getLifecycleActivity());
      if(var1 == 0) {
         this.zajo.a((Object)null);
      } else {
         if(!this.zajo.a().a()) {
            this.zab(new ConnectionResult(var1, (PendingIntent)null), 0);
         }

      }
   }
}
