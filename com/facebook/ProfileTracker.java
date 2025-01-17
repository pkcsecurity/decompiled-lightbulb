package com.facebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.internal.Validate;

public abstract class ProfileTracker {

   private final LocalBroadcastManager broadcastManager;
   private boolean isTracking = false;
   private final BroadcastReceiver receiver;


   public ProfileTracker() {
      Validate.sdkInitialized();
      this.receiver = new ProfileTracker.ProfileBroadcastReceiver(null);
      this.broadcastManager = LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext());
      this.startTracking();
   }

   private void addBroadcastReceiver() {
      IntentFilter var1 = new IntentFilter();
      var1.addAction("com.facebook.sdk.ACTION_CURRENT_PROFILE_CHANGED");
      this.broadcastManager.registerReceiver(this.receiver, var1);
   }

   public boolean isTracking() {
      return this.isTracking;
   }

   protected abstract void onCurrentProfileChanged(Profile var1, Profile var2);

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

   class ProfileBroadcastReceiver extends BroadcastReceiver {

      private ProfileBroadcastReceiver() {}

      // $FF: synthetic method
      ProfileBroadcastReceiver(Object var2) {
         this();
      }

      public void onReceive(Context var1, Intent var2) {
         if("com.facebook.sdk.ACTION_CURRENT_PROFILE_CHANGED".equals(var2.getAction())) {
            Profile var3 = (Profile)var2.getParcelableExtra("com.facebook.sdk.EXTRA_OLD_PROFILE");
            Profile var4 = (Profile)var2.getParcelableExtra("com.facebook.sdk.EXTRA_NEW_PROFILE");
            ProfileTracker.this.onCurrentProfileChanged(var3, var4);
         }

      }
   }
}
