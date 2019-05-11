package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zacq;
import com.google.android.gms.common.api.internal.zacs;
import java.lang.ref.WeakReference;
import java.util.NoSuchElementException;

final class zacr implements DeathRecipient, zacs {

   private final WeakReference<BasePendingResult<?>> zalb;
   private final WeakReference<com.google.android.gms.common.api.zac> zalc;
   private final WeakReference<IBinder> zald;


   private zacr(BasePendingResult<?> var1, com.google.android.gms.common.api.zac var2, IBinder var3) {
      this.zalc = new WeakReference(var2);
      this.zalb = new WeakReference(var1);
      this.zald = new WeakReference(var3);
   }

   // $FF: synthetic method
   zacr(BasePendingResult var1, com.google.android.gms.common.api.zac var2, IBinder var3, zacq var4) {
      this(var1, (com.google.android.gms.common.api.zac)null, var3);
   }

   private final void zaby() {
      BasePendingResult var1 = (BasePendingResult)this.zalb.get();
      com.google.android.gms.common.api.zac var2 = (com.google.android.gms.common.api.zac)this.zalc.get();
      if(var2 != null && var1 != null) {
         var2.remove(var1.zam().intValue());
      }

      IBinder var4 = (IBinder)this.zald.get();
      if(var4 != null) {
         try {
            var4.unlinkToDeath(this, 0);
         } catch (NoSuchElementException var3) {
            ;
         }
      }
   }

   public final void binderDied() {
      this.zaby();
   }

   public final void zac(BasePendingResult<?> var1) {
      this.zaby();
   }
}
