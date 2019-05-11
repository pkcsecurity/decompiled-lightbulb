package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.common.api.internal.GoogleApiManager;

final class zabi implements BackgroundDetector.BackgroundStateChangeListener {

   // $FF: synthetic field
   private final GoogleApiManager zail;


   zabi(GoogleApiManager var1) {
      this.zail = var1;
   }

   public final void onBackgroundStateChanged(boolean var1) {
      GoogleApiManager.zaa(this.zail).sendMessage(GoogleApiManager.zaa(this.zail).obtainMessage(1, Boolean.valueOf(var1)));
   }
}
