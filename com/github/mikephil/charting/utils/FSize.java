package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.utils.ObjectPool;
import java.util.List;

public final class FSize extends ObjectPool.Poolable {

   private static ObjectPool<FSize> pool = ObjectPool.create(256, new FSize(0.0F, 0.0F));
   public float height;
   public float width;


   static {
      pool.setReplenishPercentage(0.5F);
   }

   public FSize() {}

   public FSize(float var1, float var2) {
      this.width = var1;
      this.height = var2;
   }

   public static FSize getInstance(float var0, float var1) {
      FSize var2 = (FSize)pool.get();
      var2.width = var0;
      var2.height = var1;
      return var2;
   }

   public static void recycleInstance(FSize var0) {
      pool.recycle((ObjectPool.Poolable)var0);
   }

   public static void recycleInstances(List<FSize> var0) {
      pool.recycle(var0);
   }

   public boolean equals(Object var1) {
      boolean var3 = false;
      if(var1 == null) {
         return false;
      } else if(this == var1) {
         return true;
      } else if(var1 instanceof FSize) {
         FSize var4 = (FSize)var1;
         boolean var2 = var3;
         if(this.width == var4.width) {
            var2 = var3;
            if(this.height == var4.height) {
               var2 = true;
            }
         }

         return var2;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Float.floatToIntBits(this.width) ^ Float.floatToIntBits(this.height);
   }

   protected ObjectPool.Poolable instantiate() {
      return new FSize(0.0F, 0.0F);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.width);
      var1.append("x");
      var1.append(this.height);
      return var1.toString();
   }
}
