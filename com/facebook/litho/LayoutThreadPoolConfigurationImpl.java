package com.facebook.litho;

import com.facebook.litho.ComponentsLogger;
import com.facebook.litho.config.DeviceInfoUtils;
import com.facebook.litho.config.LayoutThreadPoolConfiguration;

public class LayoutThreadPoolConfigurationImpl implements LayoutThreadPoolConfiguration {

   private int mCorePoolSize;
   private int mMaxPoolSize;
   private int mThreadPriority;


   public LayoutThreadPoolConfigurationImpl(int var1, int var2, int var3) {
      this.mCorePoolSize = var1;
      this.mMaxPoolSize = var2;
      this.mThreadPriority = var3;
   }

   public int getCorePoolSize() {
      return this.mCorePoolSize;
   }

   public int getMaxPoolSize() {
      return this.mMaxPoolSize;
   }

   public int getThreadPriority() {
      return this.mThreadPriority;
   }

   public static class Builder {

      private int corePoolSize = 1;
      private int corePoolSizeIncrement = 0;
      private double corePoolSizeMultiplier = 1.0D;
      private boolean hasFixedSizePool;
      private ComponentsLogger logger;
      private int maxPoolSize = 1;
      private int maxPoolSizeIncrement = 0;
      private double maxPoolSizeMultiplier = 1.0D;
      private int threadPriority = 5;


      public LayoutThreadPoolConfigurationImpl build() {
         if(this.hasFixedSizePool) {
            return new LayoutThreadPoolConfigurationImpl(this.corePoolSize, this.maxPoolSize, this.threadPriority);
         } else {
            int var3;
            byte var4;
            int var5;
            label30: {
               var5 = DeviceInfoUtils.getNumberOfCPUCores();
               var4 = 1;
               if(var5 != -1) {
                  var3 = var5;
                  if(var5 != 0) {
                     break label30;
                  }
               }

               if(this.logger != null) {
                  this.logger.emitMessage(ComponentsLogger.LogLevel.WARNING, "Could not read number of cores from device");
               }

               var3 = 1;
            }

            double var1 = (double)var3;
            var3 = (int)Math.ceil(this.corePoolSizeMultiplier * var1 + (double)this.corePoolSizeIncrement);
            var5 = (int)Math.ceil(var1 * this.maxPoolSizeMultiplier + (double)this.maxPoolSizeIncrement);
            if(var3 == 0) {
               var3 = var4;
            }

            int var6 = var5;
            if(var5 < var3) {
               var6 = var3;
            }

            return new LayoutThreadPoolConfigurationImpl(var3, var6, this.threadPriority);
         }
      }

      public LayoutThreadPoolConfigurationImpl.Builder coreDependentPoolConfiguration(double var1, int var3, double var4, int var6) {
         this.corePoolSizeMultiplier = var1;
         this.corePoolSizeIncrement = var3;
         this.maxPoolSizeMultiplier = var4;
         this.maxPoolSizeIncrement = var6;
         return this;
      }

      public LayoutThreadPoolConfigurationImpl.Builder fixedSizePoolConfiguration(int var1, int var2) {
         this.corePoolSize = var1;
         this.maxPoolSize = var2;
         return this;
      }

      public LayoutThreadPoolConfigurationImpl.Builder hasFixedSizePool(boolean var1) {
         this.hasFixedSizePool = var1;
         return this;
      }

      public LayoutThreadPoolConfigurationImpl.Builder logger(ComponentsLogger var1) {
         this.logger = var1;
         return this;
      }

      public LayoutThreadPoolConfigurationImpl.Builder threadPriority(int var1) {
         this.threadPriority = var1;
         return this;
      }
   }
}
