package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;

public enum ShareDialogFeature implements DialogFeature {

   // $FF: synthetic field
   private static final ShareDialogFeature[] $VALUES = new ShareDialogFeature[]{SHARE_DIALOG, PHOTOS, VIDEO, MULTIMEDIA, HASHTAG, LINK_SHARE_QUOTES};
   HASHTAG("HASHTAG", 4, 20160327),
   LINK_SHARE_QUOTES("LINK_SHARE_QUOTES", 5, 20160327),
   MULTIMEDIA("MULTIMEDIA", 3, 20160327),
   PHOTOS("PHOTOS", 1, 20140204),
   SHARE_DIALOG("SHARE_DIALOG", 0, 20130618),
   VIDEO("VIDEO", 2, 20141028);
   private int minVersion;


   private ShareDialogFeature(String var1, int var2, int var3) {
      this.minVersion = var3;
   }

   public String getAction() {
      return "com.facebook.platform.action.request.FEED_DIALOG";
   }

   public int getMinVersion() {
      return this.minVersion;
   }
}
