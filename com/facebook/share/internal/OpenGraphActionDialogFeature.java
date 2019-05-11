package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;

public enum OpenGraphActionDialogFeature implements DialogFeature {

   // $FF: synthetic field
   private static final OpenGraphActionDialogFeature[] $VALUES = new OpenGraphActionDialogFeature[]{OG_ACTION_DIALOG};
   OG_ACTION_DIALOG("OG_ACTION_DIALOG", 0, 20130618);
   private int minVersion;


   private OpenGraphActionDialogFeature(String var1, int var2, int var3) {
      this.minVersion = var3;
   }

   public String getAction() {
      return "com.facebook.platform.action.request.OGACTIONPUBLISH_DIALOG";
   }

   public int getMinVersion() {
      return this.minVersion;
   }
}
