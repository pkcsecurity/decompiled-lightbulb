package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;

@Deprecated
public enum LikeDialogFeature implements DialogFeature {

   // $FF: synthetic field
   private static final LikeDialogFeature[] $VALUES = new LikeDialogFeature[]{LIKE_DIALOG};
   LIKE_DIALOG("LIKE_DIALOG", 0, 20140701);
   private int minVersion;


   private LikeDialogFeature(String var1, int var2, int var3) {
      this.minVersion = var3;
   }

   public String getAction() {
      return "com.facebook.platform.action.request.LIKE_DIALOG";
   }

   public int getMinVersion() {
      return this.minVersion;
   }
}
