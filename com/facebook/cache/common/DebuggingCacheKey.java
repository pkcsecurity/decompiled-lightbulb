package com.facebook.cache.common;

import com.facebook.cache.common.SimpleCacheKey;
import javax.annotation.Nullable;

public class DebuggingCacheKey extends SimpleCacheKey {

   private final Object mCallerContext;


   public DebuggingCacheKey(String var1, @Nullable Object var2) {
      super(var1);
      this.mCallerContext = var2;
   }

   @Nullable
   public Object getCallerContext() {
      return this.mCallerContext;
   }
}
