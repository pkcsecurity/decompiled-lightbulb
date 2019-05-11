package com.facebook.litho;

import android.support.annotation.Nullable;
import com.facebook.litho.config.ComponentsConfiguration;
import java.lang.reflect.Array;

public class ArrayBatchAllocator {

   @Nullable
   private static int[][] arrays = (int[][])null;
   private static int batchSize;
   private static int index;
   private static boolean isStartup;


   public static int[] newArrayOfSize2() {
      if(isStartup) {
         isStartup = false;
         batchSize = ComponentsConfiguration.arrayBatchAllocatorStartupSize;
      } else {
         batchSize = ComponentsConfiguration.arrayBatchAllocationRuntimeSize;
      }

      if(arrays == null || arrays.length == index) {
         arrays = (int[][])Array.newInstance(Integer.TYPE, new int[]{batchSize, 2});
         index = 0;
      }

      int[] var1 = arrays[index];
      int[][] var2 = arrays;
      int var0 = index;
      index = var0 + 1;
      var2[var0] = null;
      return var1;
   }
}
