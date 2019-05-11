package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.zaci;

final class zack extends TaskApiCall<A, ResultT> {

   // $FF: synthetic field
   private final TaskApiCall.Builder zakm;


   zack(TaskApiCall.Builder var1, Feature[] var2, boolean var3) {
      super(var2, var3, (zaci)null);
      this.zakm = var1;
   }

   protected final void doExecute(A var1, li<ResultT> var2) throws RemoteException {
      TaskApiCall.Builder.zaa(this.zakm).accept(var1, var2);
   }
}
