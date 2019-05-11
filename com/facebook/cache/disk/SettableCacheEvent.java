package com.facebook.cache.disk;

import com.facebook.cache.common.CacheEvent;
import com.facebook.cache.common.CacheEventListener;
import com.facebook.cache.common.CacheKey;
import com.facebook.infer.annotation.ReturnsOwnership;
import java.io.IOException;
import javax.annotation.Nullable;

public class SettableCacheEvent implements CacheEvent {

   private static final int MAX_RECYCLED = 5;
   private static final Object RECYCLER_LOCK = new Object();
   private static SettableCacheEvent sFirstRecycledEvent;
   private static int sRecycledCount;
   private CacheKey mCacheKey;
   private long mCacheLimit;
   private long mCacheSize;
   private CacheEventListener.EvictionReason mEvictionReason;
   private IOException mException;
   private long mItemSize;
   private SettableCacheEvent mNextRecycledEvent;
   private String mResourceId;


   @ReturnsOwnership
   public static SettableCacheEvent obtain() {
      // $FF: Couldn't be decompiled
   }

   private void reset() {
      this.mCacheKey = null;
      this.mResourceId = null;
      this.mItemSize = 0L;
      this.mCacheLimit = 0L;
      this.mCacheSize = 0L;
      this.mException = null;
      this.mEvictionReason = null;
   }

   @Nullable
   public CacheKey getCacheKey() {
      return this.mCacheKey;
   }

   public long getCacheLimit() {
      return this.mCacheLimit;
   }

   public long getCacheSize() {
      return this.mCacheSize;
   }

   @Nullable
   public CacheEventListener.EvictionReason getEvictionReason() {
      return this.mEvictionReason;
   }

   @Nullable
   public IOException getException() {
      return this.mException;
   }

   public long getItemSize() {
      return this.mItemSize;
   }

   @Nullable
   public String getResourceId() {
      return this.mResourceId;
   }

   public void recycle() {
      // $FF: Couldn't be decompiled
   }

   public SettableCacheEvent setCacheKey(CacheKey var1) {
      this.mCacheKey = var1;
      return this;
   }

   public SettableCacheEvent setCacheLimit(long var1) {
      this.mCacheLimit = var1;
      return this;
   }

   public SettableCacheEvent setCacheSize(long var1) {
      this.mCacheSize = var1;
      return this;
   }

   public SettableCacheEvent setEvictionReason(CacheEventListener.EvictionReason var1) {
      this.mEvictionReason = var1;
      return this;
   }

   public SettableCacheEvent setException(IOException var1) {
      this.mException = var1;
      return this;
   }

   public SettableCacheEvent setItemSize(long var1) {
      this.mItemSize = var1;
      return this;
   }

   public SettableCacheEvent setResourceId(String var1) {
      this.mResourceId = var1;
      return this;
   }
}
