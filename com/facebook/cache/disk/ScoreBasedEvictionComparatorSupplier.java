package com.facebook.cache.disk;

import com.facebook.cache.disk.DiskStorage;
import com.facebook.cache.disk.EntryEvictionComparator;
import com.facebook.cache.disk.EntryEvictionComparatorSupplier;
import com.facebook.common.internal.VisibleForTesting;

public class ScoreBasedEvictionComparatorSupplier implements EntryEvictionComparatorSupplier {

   private final float mAgeWeight;
   private final float mSizeWeight;


   public ScoreBasedEvictionComparatorSupplier(float var1, float var2) {
      this.mAgeWeight = var1;
      this.mSizeWeight = var2;
   }

   @VisibleForTesting
   float calculateScore(DiskStorage.Entry var1, long var2) {
      long var4 = var1.getTimestamp();
      long var6 = var1.getSize();
      return this.mAgeWeight * (float)(var2 - var4) + this.mSizeWeight * (float)var6;
   }

   public EntryEvictionComparator get() {
      return new EntryEvictionComparator() {

         long now = System.currentTimeMillis();

         public int compare(DiskStorage.Entry var1, DiskStorage.Entry var2) {
            float var3 = ScoreBasedEvictionComparatorSupplier.this.calculateScore(var1, this.now);
            float var4 = ScoreBasedEvictionComparatorSupplier.this.calculateScore(var2, this.now);
            return var3 < var4?1:(var4 == var3?0:-1);
         }
      };
   }
}
