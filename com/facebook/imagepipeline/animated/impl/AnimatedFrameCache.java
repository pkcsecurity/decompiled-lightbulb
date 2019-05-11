package com.facebook.imagepipeline.animated.impl;

import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import java.util.Iterator;
import java.util.LinkedHashSet;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class AnimatedFrameCache {

   private final CountingMemoryCache<CacheKey, CloseableImage> mBackingCache;
   private final CountingMemoryCache.EntryStateObserver<CacheKey> mEntryStateObserver;
   @GuardedBy
   private final LinkedHashSet<CacheKey> mFreeItemsPool;
   private final CacheKey mImageCacheKey;


   public AnimatedFrameCache(CacheKey var1, CountingMemoryCache<CacheKey, CloseableImage> var2) {
      this.mImageCacheKey = var1;
      this.mBackingCache = var2;
      this.mFreeItemsPool = new LinkedHashSet();
      this.mEntryStateObserver = new CountingMemoryCache.EntryStateObserver() {
         public void onExclusivityChanged(CacheKey var1, boolean var2) {
            AnimatedFrameCache.this.onReusabilityChange(var1, var2);
         }
      };
   }

   private AnimatedFrameCache.FrameKey keyFor(int var1) {
      return new AnimatedFrameCache.FrameKey(this.mImageCacheKey, var1);
   }

   @Nullable
   private CacheKey popFirstFreeItemKey() {
      synchronized(this){}
      CacheKey var1 = null;

      try {
         Iterator var2 = this.mFreeItemsPool.iterator();
         if(var2.hasNext()) {
            var1 = (CacheKey)var2.next();
            var2.remove();
         }
      } finally {
         ;
      }

      return var1;
   }

   @Nullable
   public CloseableReference<CloseableImage> cache(int var1, CloseableReference<CloseableImage> var2) {
      return this.mBackingCache.cache(this.keyFor(var1), var2, this.mEntryStateObserver);
   }

   public boolean contains(int var1) {
      return this.mBackingCache.contains((Object)this.keyFor(var1));
   }

   @Nullable
   public CloseableReference<CloseableImage> get(int var1) {
      return this.mBackingCache.get(this.keyFor(var1));
   }

   @Nullable
   public CloseableReference<CloseableImage> getForReuse() {
      CloseableReference var2;
      do {
         CacheKey var1 = this.popFirstFreeItemKey();
         if(var1 == null) {
            return null;
         }

         var2 = this.mBackingCache.reuse(var1);
      } while(var2 == null);

      return var2;
   }

   public void onReusabilityChange(CacheKey param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   @VisibleForTesting
   static class FrameKey implements CacheKey {

      private final int mFrameIndex;
      private final CacheKey mImageCacheKey;


      public FrameKey(CacheKey var1, int var2) {
         this.mImageCacheKey = var1;
         this.mFrameIndex = var2;
      }

      public boolean containsUri(Uri var1) {
         return this.mImageCacheKey.containsUri(var1);
      }

      public boolean equals(Object var1) {
         if(var1 == this) {
            return true;
         } else if(var1 instanceof AnimatedFrameCache.FrameKey) {
            AnimatedFrameCache.FrameKey var2 = (AnimatedFrameCache.FrameKey)var1;
            return this.mImageCacheKey == var2.mImageCacheKey && this.mFrameIndex == var2.mFrameIndex;
         } else {
            return false;
         }
      }

      public String getUriString() {
         return null;
      }

      public int hashCode() {
         return this.mImageCacheKey.hashCode() * 1013 + this.mFrameIndex;
      }

      public String toString() {
         return Objects.toStringHelper((Object)this).add("imageCacheKey", this.mImageCacheKey).add("frameIndex", this.mFrameIndex).toString();
      }
   }
}
