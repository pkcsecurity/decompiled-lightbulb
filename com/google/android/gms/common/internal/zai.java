package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.common.internal.PendingResultUtil;

final class zai implements PendingResultUtil.zaa {

   public final ApiException zaf(Status var1) {
      return ApiExceptionUtil.fromStatus(var1);
   }
}
