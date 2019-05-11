package com.facebook.common.memory;


public enum MemoryTrimType {

   // $FF: synthetic field
   private static final MemoryTrimType[] $VALUES = new MemoryTrimType[]{OnCloseToDalvikHeapLimit, OnSystemLowMemoryWhileAppInForeground, OnSystemLowMemoryWhileAppInBackground, OnAppBackgrounded};
   OnAppBackgrounded("OnAppBackgrounded", 3, 1.0D),
   OnCloseToDalvikHeapLimit("OnCloseToDalvikHeapLimit", 0, 0.5D),
   OnSystemLowMemoryWhileAppInBackground("OnSystemLowMemoryWhileAppInBackground", 2, 1.0D),
   OnSystemLowMemoryWhileAppInForeground("OnSystemLowMemoryWhileAppInForeground", 1, 0.5D);
   private double mSuggestedTrimRatio;


   private MemoryTrimType(String var1, int var2, double var3) {
      this.mSuggestedTrimRatio = var3;
   }

   public double getSuggestedTrimRatio() {
      return this.mSuggestedTrimRatio;
   }
}
