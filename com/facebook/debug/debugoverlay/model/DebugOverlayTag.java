package com.facebook.debug.debugoverlay.model;

import javax.annotation.concurrent.Immutable;

@Immutable
public class DebugOverlayTag {

   public final int color;
   public final String description;
   public final String name;


   public DebugOverlayTag(String var1, String var2, int var3) {
      this.name = var1;
      this.description = var2;
      this.color = var3;
   }
}
