package com.facebook.react.bridge;


public enum MemoryPressure {

   // $FF: synthetic field
   private static final MemoryPressure[] $VALUES = new MemoryPressure[]{UI_HIDDEN, MODERATE, CRITICAL};
   CRITICAL("CRITICAL", 2),
   MODERATE("MODERATE", 1),
   UI_HIDDEN("UI_HIDDEN", 0);


   private MemoryPressure(String var1, int var2) {}
}
