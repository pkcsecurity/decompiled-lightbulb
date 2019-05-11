package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;

public enum CameraEffectFeature implements DialogFeature {

   // $FF: synthetic field
   private static final CameraEffectFeature[] $VALUES = new CameraEffectFeature[]{SHARE_CAMERA_EFFECT};
   SHARE_CAMERA_EFFECT("SHARE_CAMERA_EFFECT", 0, 20170417);
   private int minVersion;


   private CameraEffectFeature(String var1, int var2, int var3) {
      this.minVersion = var3;
   }

   public String getAction() {
      return "com.facebook.platform.action.request.CAMERA_EFFECT";
   }

   public int getMinVersion() {
      return this.minVersion;
   }
}
