package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import com.google.android.gms.common.internal.GmsClientSupervisor;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.stats.ConnectionTracker;
import java.util.HashMap;
import javax.annotation.concurrent.GuardedBy;

final class zze extends GmsClientSupervisor implements Callback {

   private final Handler mHandler;
   @GuardedBy
   private final HashMap<GmsClientSupervisor.zza, zzf> zzdu = new HashMap();
   private final Context zzdv;
   private final ConnectionTracker zzdw;
   private final long zzdx;
   private final long zzdy;


   zze(Context var1) {
      this.zzdv = var1.getApplicationContext();
      this.mHandler = new gj(var1.getMainLooper(), this);
      this.zzdw = ConnectionTracker.getInstance();
      this.zzdx = 5000L;
      this.zzdy = 300000L;
   }

   // $FF: synthetic method
   static HashMap zza(zze var0) {
      return var0.zzdu;
   }

   // $FF: synthetic method
   static Handler zzb(zze var0) {
      return var0.mHandler;
   }

   // $FF: synthetic method
   static Context zzc(zze var0) {
      return var0.zzdv;
   }

   // $FF: synthetic method
   static ConnectionTracker zzd(zze var0) {
      return var0.zzdw;
   }

   // $FF: synthetic method
   static long zze(zze var0) {
      return var0.zzdy;
   }

   public final boolean handleMessage(Message param1) {
      // $FF: Couldn't be decompiled
   }

   protected final boolean zza(GmsClientSupervisor.zza param1, ServiceConnection param2, String param3) {
      // $FF: Couldn't be decompiled
   }

   protected final void zzb(GmsClientSupervisor.zza param1, ServiceConnection param2, String param3) {
      // $FF: Couldn't be decompiled
   }
}
