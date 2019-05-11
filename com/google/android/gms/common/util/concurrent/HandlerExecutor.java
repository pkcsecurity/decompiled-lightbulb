package com.google.android.gms.common.util.concurrent;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.concurrent.Executor;

@KeepForSdk
public class HandlerExecutor implements Executor {

   private final Handler handler;


   @KeepForSdk
   public HandlerExecutor(Looper var1) {
      this.handler = new gj(var1);
   }

   public void execute(@NonNull Runnable var1) {
      this.handler.post(var1);
   }
}
