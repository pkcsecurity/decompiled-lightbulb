package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.internal.zaaw;
import com.google.android.gms.common.internal.GmsClientEventManager;

final class zaax implements GmsClientEventManager.GmsClientEventState {

   // $FF: synthetic field
   private final zaaw zahg;


   zaax(zaaw var1) {
      this.zahg = var1;
   }

   public final Bundle getConnectionHint() {
      return null;
   }

   public final boolean isConnected() {
      return this.zahg.isConnected();
   }
}
