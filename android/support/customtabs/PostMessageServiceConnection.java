package android.support.customtabs;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.customtabs.CustomTabsSessionToken;
import android.support.customtabs.ICustomTabsCallback;
import android.support.customtabs.IPostMessageService;
import android.support.customtabs.PostMessageService;

public abstract class PostMessageServiceConnection implements ServiceConnection {

   private final Object mLock = new Object();
   private IPostMessageService mService;
   private final ICustomTabsCallback mSessionBinder;


   public PostMessageServiceConnection(CustomTabsSessionToken var1) {
      this.mSessionBinder = ICustomTabsCallback.Stub.asInterface(var1.getCallbackBinder());
   }

   public boolean bindSessionToPostMessageService(Context var1, String var2) {
      Intent var3 = new Intent();
      var3.setClassName(var2, PostMessageService.class.getName());
      return var1.bindService(var3, this, 1);
   }

   public final boolean notifyMessageChannelReady(Bundle param1) {
      // $FF: Couldn't be decompiled
   }

   public void onPostMessageServiceConnected() {}

   public void onPostMessageServiceDisconnected() {}

   public final void onServiceConnected(ComponentName var1, IBinder var2) {
      this.mService = IPostMessageService.Stub.asInterface(var2);
      this.onPostMessageServiceConnected();
   }

   public final void onServiceDisconnected(ComponentName var1) {
      this.mService = null;
      this.onPostMessageServiceDisconnected();
   }

   public final boolean postMessage(String param1, Bundle param2) {
      // $FF: Couldn't be decompiled
   }

   public void unbindFromContext(Context var1) {
      var1.unbindService(this);
   }
}
