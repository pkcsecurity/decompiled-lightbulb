package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zaaw;
import com.google.android.gms.common.api.internal.zabr;
import java.lang.ref.WeakReference;

final class zabc extends zabr {

   private WeakReference<zaaw> zahl;


   zabc(zaaw var1) {
      this.zahl = new WeakReference(var1);
   }

   public final void zas() {
      zaaw var1 = (zaaw)this.zahl.get();
      if(var1 != null) {
         zaaw.zaa(var1);
      }
   }
}
