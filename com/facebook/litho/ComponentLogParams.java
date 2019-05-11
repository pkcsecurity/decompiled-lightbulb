package com.facebook.litho;


public class ComponentLogParams {

   public final boolean failHarder;
   public final String logProductId;
   public final String logType;
   public final int samplingFrequency;


   public ComponentLogParams(String var1, String var2) {
      this(var1, var2, 0, false);
   }

   public ComponentLogParams(String var1, String var2, int var3, boolean var4) {
      this.logProductId = var1;
      this.logType = var2;
      this.samplingFrequency = var3;
      this.failHarder = var4;
   }
}
