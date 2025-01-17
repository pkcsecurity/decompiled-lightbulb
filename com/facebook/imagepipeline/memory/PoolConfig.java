package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.imagepipeline.memory.DefaultBitmapPoolParams;
import com.facebook.imagepipeline.memory.DefaultByteArrayPoolParams;
import com.facebook.imagepipeline.memory.DefaultFlexByteArrayPoolParams;
import com.facebook.imagepipeline.memory.DefaultNativeMemoryChunkPoolParams;
import com.facebook.imagepipeline.memory.NoOpPoolStatsTracker;
import com.facebook.imagepipeline.memory.PoolParams;
import com.facebook.imagepipeline.memory.PoolStatsTracker;
import javax.annotation.concurrent.Immutable;

@Immutable
public class PoolConfig {

   private final PoolParams mBitmapPoolParams;
   private final PoolStatsTracker mBitmapPoolStatsTracker;
   private final PoolParams mFlexByteArrayPoolParams;
   private final MemoryTrimmableRegistry mMemoryTrimmableRegistry;
   private final PoolParams mNativeMemoryChunkPoolParams;
   private final PoolStatsTracker mNativeMemoryChunkPoolStatsTracker;
   private final PoolParams mSmallByteArrayPoolParams;
   private final PoolStatsTracker mSmallByteArrayPoolStatsTracker;


   private PoolConfig(PoolConfig.Builder var1) {
      PoolParams var2;
      if(var1.mBitmapPoolParams == null) {
         var2 = DefaultBitmapPoolParams.get();
      } else {
         var2 = var1.mBitmapPoolParams;
      }

      this.mBitmapPoolParams = var2;
      Object var4;
      if(var1.mBitmapPoolStatsTracker == null) {
         var4 = NoOpPoolStatsTracker.getInstance();
      } else {
         var4 = var1.mBitmapPoolStatsTracker;
      }

      this.mBitmapPoolStatsTracker = (PoolStatsTracker)var4;
      if(var1.mFlexByteArrayPoolParams == null) {
         var2 = DefaultFlexByteArrayPoolParams.get();
      } else {
         var2 = var1.mFlexByteArrayPoolParams;
      }

      this.mFlexByteArrayPoolParams = var2;
      if(var1.mMemoryTrimmableRegistry == null) {
         var4 = NoOpMemoryTrimmableRegistry.getInstance();
      } else {
         var4 = var1.mMemoryTrimmableRegistry;
      }

      this.mMemoryTrimmableRegistry = (MemoryTrimmableRegistry)var4;
      if(var1.mNativeMemoryChunkPoolParams == null) {
         var2 = DefaultNativeMemoryChunkPoolParams.get();
      } else {
         var2 = var1.mNativeMemoryChunkPoolParams;
      }

      this.mNativeMemoryChunkPoolParams = var2;
      if(var1.mNativeMemoryChunkPoolStatsTracker == null) {
         var4 = NoOpPoolStatsTracker.getInstance();
      } else {
         var4 = var1.mNativeMemoryChunkPoolStatsTracker;
      }

      this.mNativeMemoryChunkPoolStatsTracker = (PoolStatsTracker)var4;
      if(var1.mSmallByteArrayPoolParams == null) {
         var2 = DefaultByteArrayPoolParams.get();
      } else {
         var2 = var1.mSmallByteArrayPoolParams;
      }

      this.mSmallByteArrayPoolParams = var2;
      Object var3;
      if(var1.mSmallByteArrayPoolStatsTracker == null) {
         var3 = NoOpPoolStatsTracker.getInstance();
      } else {
         var3 = var1.mSmallByteArrayPoolStatsTracker;
      }

      this.mSmallByteArrayPoolStatsTracker = (PoolStatsTracker)var3;
   }

   // $FF: synthetic method
   PoolConfig(PoolConfig.Builder var1, Object var2) {
      this(var1);
   }

   public static PoolConfig.Builder newBuilder() {
      return new PoolConfig.Builder(null);
   }

   public PoolParams getBitmapPoolParams() {
      return this.mBitmapPoolParams;
   }

   public PoolStatsTracker getBitmapPoolStatsTracker() {
      return this.mBitmapPoolStatsTracker;
   }

   public PoolParams getFlexByteArrayPoolParams() {
      return this.mFlexByteArrayPoolParams;
   }

   public MemoryTrimmableRegistry getMemoryTrimmableRegistry() {
      return this.mMemoryTrimmableRegistry;
   }

   public PoolParams getNativeMemoryChunkPoolParams() {
      return this.mNativeMemoryChunkPoolParams;
   }

   public PoolStatsTracker getNativeMemoryChunkPoolStatsTracker() {
      return this.mNativeMemoryChunkPoolStatsTracker;
   }

   public PoolParams getSmallByteArrayPoolParams() {
      return this.mSmallByteArrayPoolParams;
   }

   public PoolStatsTracker getSmallByteArrayPoolStatsTracker() {
      return this.mSmallByteArrayPoolStatsTracker;
   }

   public static class Builder {

      private PoolParams mBitmapPoolParams;
      private PoolStatsTracker mBitmapPoolStatsTracker;
      private PoolParams mFlexByteArrayPoolParams;
      private MemoryTrimmableRegistry mMemoryTrimmableRegistry;
      private PoolParams mNativeMemoryChunkPoolParams;
      private PoolStatsTracker mNativeMemoryChunkPoolStatsTracker;
      private PoolParams mSmallByteArrayPoolParams;
      private PoolStatsTracker mSmallByteArrayPoolStatsTracker;


      private Builder() {}

      // $FF: synthetic method
      Builder(Object var1) {
         this();
      }

      public PoolConfig build() {
         return new PoolConfig(this, null);
      }

      public PoolConfig.Builder setBitmapPoolParams(PoolParams var1) {
         this.mBitmapPoolParams = (PoolParams)Preconditions.checkNotNull(var1);
         return this;
      }

      public PoolConfig.Builder setBitmapPoolStatsTracker(PoolStatsTracker var1) {
         this.mBitmapPoolStatsTracker = (PoolStatsTracker)Preconditions.checkNotNull(var1);
         return this;
      }

      public PoolConfig.Builder setFlexByteArrayPoolParams(PoolParams var1) {
         this.mFlexByteArrayPoolParams = var1;
         return this;
      }

      public PoolConfig.Builder setMemoryTrimmableRegistry(MemoryTrimmableRegistry var1) {
         this.mMemoryTrimmableRegistry = var1;
         return this;
      }

      public PoolConfig.Builder setNativeMemoryChunkPoolParams(PoolParams var1) {
         this.mNativeMemoryChunkPoolParams = (PoolParams)Preconditions.checkNotNull(var1);
         return this;
      }

      public PoolConfig.Builder setNativeMemoryChunkPoolStatsTracker(PoolStatsTracker var1) {
         this.mNativeMemoryChunkPoolStatsTracker = (PoolStatsTracker)Preconditions.checkNotNull(var1);
         return this;
      }

      public PoolConfig.Builder setSmallByteArrayPoolParams(PoolParams var1) {
         this.mSmallByteArrayPoolParams = (PoolParams)Preconditions.checkNotNull(var1);
         return this;
      }

      public PoolConfig.Builder setSmallByteArrayPoolStatsTracker(PoolStatsTracker var1) {
         this.mSmallByteArrayPoolStatsTracker = (PoolStatsTracker)Preconditions.checkNotNull(var1);
         return this;
      }
   }
}
