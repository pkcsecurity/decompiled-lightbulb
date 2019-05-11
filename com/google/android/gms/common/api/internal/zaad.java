package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.internal.zaab;
import com.google.android.gms.tasks.OnCompleteListener;

final class zaad implements OnCompleteListener<TResult> {

   // $FF: synthetic field
   private final zaab zafm;
   // $FF: synthetic field
   private final li zafn;


   zaad(zaab var1, li var2) {
      this.zafm = var1;
      this.zafn = var2;
   }

   public final void onComplete(@NonNull lh<TResult> var1) {
      zaab.zab(this.zafm).remove(this.zafn);
   }
}
