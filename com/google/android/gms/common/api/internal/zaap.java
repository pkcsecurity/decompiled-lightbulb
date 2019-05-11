package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.zaan;
import com.google.android.gms.common.api.internal.zabd;
import com.google.android.gms.common.api.internal.zabf;
import com.google.android.gms.common.internal.BaseGmsClient;
import javax.annotation.concurrent.GuardedBy;

final class zaap extends zabf {

   // $FF: synthetic field
   private final BaseGmsClient.ConnectionProgressReportCallbacks zagn;


   zaap(zaan var1, zabd var2, BaseGmsClient.ConnectionProgressReportCallbacks var3) {
      super(var2);
      this.zagn = var3;
   }

   @GuardedBy
   public final void zaan() {
      this.zagn.onReportServiceBinding(new ConnectionResult(16, (PendingIntent)null));
   }
}
