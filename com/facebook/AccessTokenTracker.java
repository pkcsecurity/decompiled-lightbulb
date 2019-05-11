package com.facebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;

public abstract class AccessTokenTracker {

   private static final String TAG = "AccessTokenTracker";
   private final LocalBroadcastManager broadcastManager;
   private boolean isTracking = false;
   private final BroadcastReceiver receiver;


   public AccessTokenTracker() {
      Validate.sdkInitialized();
      this.receiver = new AccessTokenTracker.CurrentAccessTokenBroadcastReceiver(null);
      this.broadcastManager = LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext());
      this.startTracking();
   }

   private void addBroadcastReceiver() {
      IntentFilter var1 = new IntentFilter();
      var1.addAction("com.facebook.sdk.ACTION_CURRENT_ACCESS_TOKEN_CHANGED");
      this.broadcastManager.registerReceiver(this.receiver, var1);
   }

   public boolean isTracking() {
      return this.isTracking;
   }

   public abstract void onCurrentAccessTokenChanged(AccessToken var1, AccessToken var2);

   public void startTracking() {
      if(!this.isTracking) {
         this.addBroadcastReceiver();
         this.isTracking = true;
      }
   }

   public void stopTracking() {
      if(this.isTracking) {
         this.broadcastManager.unregisterReceiver(this.receiver);
         this.isTracking = false;
      }
   }

   class CurrentAccessTokenBroadcastReceiver extends BroadcastReceiver {

      private CurrentAccessTokenBroadcastReceiver() {}

      // $FF: synthetic method
      CurrentAccessTokenBroadcastReceiver(Object var2) {
         this();
      }

      public void onReceive(Context var1, Intent var2) {
         if("com.facebook.sdk.ACTION_CURRENT_ACCESS_TOKEN_CHANGED".equals(var2.getAction())) {
            Utility.logd(AccessTokenTracker.TAG, "AccessTokenChanged");
            AccessToken var3 = (AccessToken)var2.getParcelableExtra("com.facebook.sdk.EXTRA_OLD_ACCESS_TOKEN");
            AccessToken var4 = (AccessToken)var2.getParcelableExtra("com.facebook.sdk.EXTRA_NEW_ACCESS_TOKEN");
            AccessTokenTracker.this.onCurrentAccessTokenChanged(var3, var4);
         }

      }
   }
}
