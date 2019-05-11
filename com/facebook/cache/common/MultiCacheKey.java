package com.facebook.cache.common;

import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Preconditions;
import java.util.List;

public class MultiCacheKey implements CacheKey {

   final List<CacheKey> mCacheKeys;


   public MultiCacheKey(List<CacheKey> var1) {
      this.mCacheKeys = (List)Preconditions.checkNotNull(var1);
   }

   public boolean containsUri(Uri var1) {
      for(int var2 = 0; var2 < this.mCacheKeys.size(); ++var2) {
         if(((CacheKey)this.mCacheKeys.get(var2)).containsUri(var1)) {
            return true;
         }
      }

      return false;
   }

   public boolean equals(Object var1) {
      if(var1 == this) {
         return true;
      } else if(var1 instanceof MultiCacheKey) {
         MultiCacheKey var2 = (MultiCacheKey)var1;
         return this.mCacheKeys.equals(var2.mCacheKeys);
      } else {
         return false;
      }
   }

   public List<CacheKey> getCacheKeys() {
      return this.mCacheKeys;
   }

   public String getUriString() {
      return ((CacheKey)this.mCacheKeys.get(0)).getUriString();
   }

   public int hashCode() {
      return this.mCacheKeys.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("MultiCacheKey:");
      var1.append(this.mCacheKeys.toString());
      return var1.toString();
   }
}
