package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.common.api.internal.SignInConnectionListener;
import com.google.android.gms.common.api.internal.zai;
import com.google.android.gms.common.api.internal.zaw;
import com.google.android.gms.common.api.internal.zax;
import com.google.android.gms.tasks.OnCompleteListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

final class zaaa implements OnCompleteListener<Map<zai<?>, String>> {

   // $FF: synthetic field
   private final zax zafh;
   private SignInConnectionListener zafi;


   zaaa(zax var1, SignInConnectionListener var2) {
      this.zafh = var1;
      this.zafi = var2;
   }

   final void cancel() {
      this.zafi.onComplete();
   }

   public final void onComplete(@NonNull lh<Map<zai<?>, String>> var1) {
      zax.zaa(this.zafh).lock();

      try {
         if(!zax.zab(this.zafh)) {
            this.zafi.onComplete();
            return;
         }

         if(var1.b()) {
            zax.zab(this.zafh, new ArrayMap(zax.zam(this.zafh).size()));
            Iterator var8 = zax.zam(this.zafh).values().iterator();

            while(var8.hasNext()) {
               zaw var2 = (zaw)var8.next();
               zax.zag(this.zafh).put(var2.zak(), ConnectionResult.RESULT_SUCCESS);
            }
         } else if(var1.e() instanceof AvailabilityException) {
            AvailabilityException var9 = (AvailabilityException)var1.e();
            if(zax.zae(this.zafh)) {
               zax.zab(this.zafh, new ArrayMap(zax.zam(this.zafh).size()));
               Iterator var10 = zax.zam(this.zafh).values().iterator();

               while(var10.hasNext()) {
                  zaw var3 = (zaw)var10.next();
                  zai var4 = var3.zak();
                  ConnectionResult var5 = var9.getConnectionResult(var3);
                  if(zax.zaa(this.zafh, var3, var5)) {
                     zax.zag(this.zafh).put(var4, new ConnectionResult(16));
                  } else {
                     zax.zag(this.zafh).put(var4, var5);
                  }
               }
            } else {
               zax.zab(this.zafh, var9.zaj());
            }
         } else {
            Log.e("ConnectionlessGAC", "Unexpected availability exception", var1.e());
            zax.zab(this.zafh, Collections.emptyMap());
         }

         if(this.zafh.isConnected()) {
            zax.zad(this.zafh).putAll(zax.zag(this.zafh));
            if(zax.zaf(this.zafh) == null) {
               zax.zai(this.zafh);
               zax.zaj(this.zafh);
               zax.zal(this.zafh).signalAll();
            }
         }

         this.zafi.onComplete();
      } finally {
         zax.zaa(this.zafh).unlock();
      }

   }
}
