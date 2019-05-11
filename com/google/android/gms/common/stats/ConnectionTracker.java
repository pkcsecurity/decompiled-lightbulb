package com.google.android.gms.common.stats;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Collections;
import java.util.List;

@KeepForSdk
public class ConnectionTracker {

   private static final Object zzdp = new Object();
   private static volatile ConnectionTracker zzfa;
   @VisibleForTesting
   private static boolean zzfb;
   private final List<String> zzfc;
   private final List<String> zzfd;
   private final List<String> zzfe;
   private final List<String> zzff;


   private ConnectionTracker() {
      this.zzfc = Collections.EMPTY_LIST;
      this.zzfd = Collections.EMPTY_LIST;
      this.zzfe = Collections.EMPTY_LIST;
      this.zzff = Collections.EMPTY_LIST;
   }

   @KeepForSdk
   public static ConnectionTracker getInstance() {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   public boolean bindService(Context var1, Intent var2, ServiceConnection var3, int var4) {
      return this.zza(var1, var1.getClass().getName(), var2, var3, var4);
   }

   @SuppressLint({"UntrackedBindService"})
   @KeepForSdk
   public void unbindService(Context var1, ServiceConnection var2) {
      var1.unbindService(var2);
   }

   public final boolean zza(Context var1, String var2, Intent var3, ServiceConnection var4, int var5) {
      ComponentName var7 = var3.getComponent();
      boolean var6;
      if(var7 == null) {
         var6 = false;
      } else {
         var6 = ClientLibraryUtils.zzc(var1, var7.getPackageName());
      }

      if(var6) {
         Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
         return false;
      } else {
         return var1.bindService(var3, var4, var5);
      }
   }
}
