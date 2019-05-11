package com.facebook;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.Profile;
import com.facebook.ProfileCache;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;

public final class ProfileManager {

   public static final String ACTION_CURRENT_PROFILE_CHANGED = "com.facebook.sdk.ACTION_CURRENT_PROFILE_CHANGED";
   public static final String EXTRA_NEW_PROFILE = "com.facebook.sdk.EXTRA_NEW_PROFILE";
   public static final String EXTRA_OLD_PROFILE = "com.facebook.sdk.EXTRA_OLD_PROFILE";
   private static volatile ProfileManager instance;
   private Profile currentProfile;
   private final LocalBroadcastManager localBroadcastManager;
   private final ProfileCache profileCache;


   ProfileManager(LocalBroadcastManager var1, ProfileCache var2) {
      Validate.notNull(var1, "localBroadcastManager");
      Validate.notNull(var2, "profileCache");
      this.localBroadcastManager = var1;
      this.profileCache = var2;
   }

   static ProfileManager getInstance() {
      // $FF: Couldn't be decompiled
   }

   private void sendCurrentProfileChangedBroadcast(Profile var1, Profile var2) {
      Intent var3 = new Intent("com.facebook.sdk.ACTION_CURRENT_PROFILE_CHANGED");
      var3.putExtra("com.facebook.sdk.EXTRA_OLD_PROFILE", var1);
      var3.putExtra("com.facebook.sdk.EXTRA_NEW_PROFILE", var2);
      this.localBroadcastManager.sendBroadcast(var3);
   }

   private void setCurrentProfile(@Nullable Profile var1, boolean var2) {
      Profile var3 = this.currentProfile;
      this.currentProfile = var1;
      if(var2) {
         if(var1 != null) {
            this.profileCache.save(var1);
         } else {
            this.profileCache.clear();
         }
      }

      if(!Utility.areObjectsEqual(var3, var1)) {
         this.sendCurrentProfileChangedBroadcast(var3, var1);
      }

   }

   Profile getCurrentProfile() {
      return this.currentProfile;
   }

   boolean loadCurrentProfile() {
      Profile var1 = this.profileCache.load();
      if(var1 != null) {
         this.setCurrentProfile(var1, false);
         return true;
      } else {
         return false;
      }
   }

   void setCurrentProfile(@Nullable Profile var1) {
      this.setCurrentProfile(var1, true);
   }
}
