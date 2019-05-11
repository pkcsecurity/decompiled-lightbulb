package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.support.v4.util.ArraySet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zai;
import com.google.android.gms.common.api.internal.zal;
import com.google.android.gms.common.internal.Preconditions;

public class zaae extends zal {

   private GoogleApiManager zabm;
   private final ArraySet<zai<?>> zafo = new ArraySet();


   private zaae(LifecycleFragment var1) {
      super(var1);
      this.mLifecycleFragment.addCallback("ConnectionlessLifecycleHelper", this);
   }

   public static void zaa(Activity var0, GoogleApiManager var1, zai<?> var2) {
      LifecycleFragment var4 = getFragment(var0);
      zaae var3 = (zaae)var4.getCallbackOrNull("ConnectionlessLifecycleHelper", zaae.class);
      zaae var5 = var3;
      if(var3 == null) {
         var5 = new zaae(var4);
      }

      var5.zabm = var1;
      Preconditions.checkNotNull(var2, "ApiKey cannot be null");
      var5.zafo.add(var2);
      var1.zaa(var5);
   }

   private final void zaak() {
      if(!this.zafo.isEmpty()) {
         this.zabm.zaa(this);
      }

   }

   public void onResume() {
      super.onResume();
      this.zaak();
   }

   public void onStart() {
      super.onStart();
      this.zaak();
   }

   public void onStop() {
      super.onStop();
      this.zabm.zab(this);
   }

   protected final void zaa(ConnectionResult var1, int var2) {
      this.zabm.zaa(var1, var2);
   }

   final ArraySet<zai<?>> zaaj() {
      return this.zafo;
   }

   protected final void zao() {
      this.zabm.zao();
   }
}
