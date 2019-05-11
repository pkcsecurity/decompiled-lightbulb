package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;

public enum ShareStoryFeature implements DialogFeature {

   // $FF: synthetic field
   private static final ShareStoryFeature[] $VALUES = new ShareStoryFeature[]{SHARE_STORY_ASSET};
   SHARE_STORY_ASSET("SHARE_STORY_ASSET", 0, 20170417);
   private int minVersion;


   private ShareStoryFeature(String var1, int var2, int var3) {
      this.minVersion = var3;
   }

   public String getAction() {
      return "com.facebook.platform.action.request.SHARE_STORY";
   }

   public int getMinVersion() {
      return this.minVersion;
   }
}
