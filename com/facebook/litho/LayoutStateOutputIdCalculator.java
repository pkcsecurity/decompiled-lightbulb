package com.facebook.litho;

import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import com.facebook.litho.LayoutOutput;
import com.facebook.litho.VisibilityOutput;
import com.facebook.litho.config.ComponentsConfiguration;

class LayoutStateOutputIdCalculator {

   private static final int COMPONENT_ID_SHIFT = 26;
   private static final int LEVEL_SHIFT = 18;
   private static final int MAX_LEVEL = 255;
   private static final int MAX_SEQUENCE = 65535;
   private static final int TYPE_SHIFT = 16;
   @Nullable
   private LongSparseArray<Integer> mLayoutCurrentSequenceForBaseId;
   @Nullable
   private LongSparseArray<Integer> mVisibilityCurrentSequenceForBaseId;


   public LayoutStateOutputIdCalculator() {
      if(!ComponentsConfiguration.lazilyInitializeLayoutStateOutputIdCalculator) {
         this.mLayoutCurrentSequenceForBaseId = new LongSparseArray(8);
         this.mVisibilityCurrentSequenceForBaseId = new LongSparseArray(8);
      }

   }

   static long calculateId(long var0, int var2) {
      if(var2 >= 0 && var2 <= '\uffff') {
         return var0 | (long)var2;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Sequence must be non-negative and no greater than 65535 actual sequence ");
         var3.append(var2);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   private static long calculateLayoutOutputBaseId(LayoutOutput var0, int var1, int var2) {
      if(var1 >= 0 && var1 <= 255) {
         long var3;
         if(var0.getComponent() != null) {
            var3 = (long)var0.getComponent().getTypeId();
         } else {
            var3 = 0L;
         }

         return var3 << 26 | 0L | (long)var1 << 18 | (long)var2 << 16;
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("Level must be non-negative and no greater than 255 actual level ");
         var5.append(var1);
         throw new IllegalArgumentException(var5.toString());
      }
   }

   static long calculateLayoutOutputId(LayoutOutput var0, int var1, int var2, int var3) {
      return calculateId(calculateLayoutOutputBaseId(var0, var1, var2), var3);
   }

   private static long calculateVisibilityOutputBaseId(VisibilityOutput var0, int var1) {
      if(var1 >= 0 && var1 <= 255) {
         long var2;
         if(var0.getComponent() != null) {
            var2 = (long)var0.getComponent().getTypeId();
         } else {
            var2 = 0L;
         }

         return var2 << 26 | 0L | (long)var1 << 18;
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("Level must be non-negative and no greater than 255 actual level ");
         var4.append(var1);
         throw new IllegalArgumentException(var4.toString());
      }
   }

   static long calculateVisibilityOutputId(VisibilityOutput var0, int var1, int var2) {
      return calculateId(calculateVisibilityOutputBaseId(var0, var1), var2);
   }

   static int getLevelFromId(long var0) {
      return (int)(var0 >> 18 & 255L);
   }

   static int getSequenceFromId(long var0) {
      return (int)var0 & '\uffff';
   }

   static int getTypeFromId(long var0) {
      return var0 == 0L?3:(int)(var0 >> 16 & 3L);
   }

   void calculateAndSetLayoutOutputIdAndUpdateState(LayoutOutput var1, int var2, int var3, long var4, boolean var6) {
      if(this.mLayoutCurrentSequenceForBaseId == null) {
         this.mLayoutCurrentSequenceForBaseId = new LongSparseArray(2);
      }

      long var8 = calculateLayoutOutputBaseId(var1, var2, var3);
      if(var4 > 0L && getLevelFromId(var4) == var2) {
         var2 = getSequenceFromId(var4);
      } else {
         var2 = -1;
      }

      LongSparseArray var10 = this.mLayoutCurrentSequenceForBaseId;
      byte var11 = 0;
      int var7 = ((Integer)var10.get(var8, Integer.valueOf(0))).intValue();
      if(var2 < var7) {
         var2 = var7 + 1;
      } else if(var6) {
         var11 = 1;
      } else {
         var11 = 2;
      }

      var1.setUpdateState(var11);
      var1.setId(calculateId(var8, var2));
      this.mLayoutCurrentSequenceForBaseId.put(var8, Integer.valueOf(var2 + 1));
   }

   void calculateAndSetVisibilityOutputId(VisibilityOutput var1, int var2, long var3) {
      if(this.mVisibilityCurrentSequenceForBaseId == null) {
         this.mVisibilityCurrentSequenceForBaseId = new LongSparseArray(2);
      }

      long var7 = calculateVisibilityOutputBaseId(var1, var2);
      if(var3 > 0L && getLevelFromId(var3) == var2) {
         var2 = getSequenceFromId(var3);
      } else {
         var2 = -1;
      }

      int var6 = ((Integer)this.mVisibilityCurrentSequenceForBaseId.get(var7, Integer.valueOf(0))).intValue();
      int var5 = var2;
      if(var2 < var6) {
         var5 = var6 + 1;
      }

      var1.setId(calculateId(var7, var5));
      this.mVisibilityCurrentSequenceForBaseId.put(var7, Integer.valueOf(var5 + 1));
   }

   void clear() {
      if(this.mLayoutCurrentSequenceForBaseId != null) {
         this.mLayoutCurrentSequenceForBaseId.clear();
      }

      if(this.mVisibilityCurrentSequenceForBaseId != null) {
         this.mVisibilityCurrentSequenceForBaseId.clear();
      }

   }
}
