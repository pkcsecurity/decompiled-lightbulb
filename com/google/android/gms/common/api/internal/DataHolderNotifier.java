package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.data.DataHolder;

@KeepForSdk
public abstract class DataHolderNotifier<L extends Object> implements ListenerHolder.Notifier<L> {

   private final DataHolder mDataHolder;


   @KeepForSdk
   protected DataHolderNotifier(DataHolder var1) {
      this.mDataHolder = var1;
   }

   @KeepForSdk
   public final void notifyListener(L var1) {
      this.notifyListener(var1, this.mDataHolder);
   }

   @KeepForSdk
   protected abstract void notifyListener(L var1, DataHolder var2);

   @KeepForSdk
   public void onNotifyListenerFailed() {
      if(this.mDataHolder != null) {
         this.mDataHolder.close();
      }

   }
}
