package com.tuyasmart.stencil.app;


public class ApiConfig {

   private ApiConfig.EnvConfig mEnv;


   public ApiConfig(ApiConfig.EnvConfig var1) {
      this.mEnv = var1;
   }

   public ApiConfig.EnvConfig getEnv() {
      return this.mEnv;
   }

   public static enum EnvConfig {

      // $FF: synthetic field
      private static final ApiConfig.EnvConfig[] $VALUES = new ApiConfig.EnvConfig[]{ONLINE, PREVIEW, DAILY};
      DAILY("DAILY", 2),
      ONLINE("ONLINE", 0),
      PREVIEW("PREVIEW", 1);


      private EnvConfig(String var1, int var2) {}
   }
}
