package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@KeepForSdk
public class BlockingServiceConnection implements ServiceConnection {

   private boolean zze = false;
   private final BlockingQueue<IBinder> zzf = new LinkedBlockingQueue();


   @KeepForSdk
   public IBinder getService() throws InterruptedException {
      Preconditions.checkNotMainThread("BlockingServiceConnection.getService() called on main thread");
      if(this.zze) {
         throw new IllegalStateException("Cannot call get on this connection more than once");
      } else {
         this.zze = true;
         return (IBinder)this.zzf.take();
      }
   }

   @KeepForSdk
   public IBinder getServiceWithTimeout(long var1, TimeUnit var3) throws InterruptedException, TimeoutException {
      Preconditions.checkNotMainThread("BlockingServiceConnection.getServiceWithTimeout() called on main thread");
      if(this.zze) {
         throw new IllegalStateException("Cannot call get on this connection more than once");
      } else {
         this.zze = true;
         IBinder var4 = (IBinder)this.zzf.poll(var1, var3);
         if(var4 == null) {
            throw new TimeoutException("Timed out waiting for the service connection");
         } else {
            return var4;
         }
      }
   }

   public void onServiceConnected(ComponentName var1, IBinder var2) {
      this.zzf.add(var2);
   }

   public void onServiceDisconnected(ComponentName var1) {}
}
