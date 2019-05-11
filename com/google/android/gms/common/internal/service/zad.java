package com.google.android.gms.common.internal.service;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.service.zac;
import com.google.android.gms.common.internal.service.zae;

public final class zad implements zac {

   public final PendingResult<Status> zaa(GoogleApiClient var1) {
      return var1.execute(new zae(this, var1));
   }
}
