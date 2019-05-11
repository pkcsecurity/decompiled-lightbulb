package com.facebook.react.shell;

import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class MainPackageConfig {

   private ImagePipelineConfig mFrescoConfig;


   private MainPackageConfig(MainPackageConfig.Builder var1) {
      this.mFrescoConfig = var1.mFrescoConfig;
   }

   // $FF: synthetic method
   MainPackageConfig(MainPackageConfig.Builder var1, Object var2) {
      this(var1);
   }

   public ImagePipelineConfig getFrescoConfig() {
      return this.mFrescoConfig;
   }

   public static class Builder {

      private ImagePipelineConfig mFrescoConfig;


      public MainPackageConfig build() {
         return new MainPackageConfig(this, null);
      }

      public MainPackageConfig.Builder setFrescoConfig(ImagePipelineConfig var1) {
         this.mFrescoConfig = var1;
         return this;
      }
   }
}
