package com.facebook.litho.widget;

import android.support.v7.widget.StaggeredGridLayoutManager;

public class StaggeredGridLayoutHelper {

   private static int[] mItemPositionsHolder;


   public static int findFirstFullyVisibleItemPosition(StaggeredGridLayoutManager var0) {
      if(mItemPositionsHolder == null) {
         mItemPositionsHolder = new int[var0.getSpanCount()];
      }

      return min(var0.findFirstCompletelyVisibleItemPositions(mItemPositionsHolder));
   }

   public static int findFirstVisibleItemPosition(StaggeredGridLayoutManager var0) {
      if(mItemPositionsHolder == null) {
         mItemPositionsHolder = new int[var0.getSpanCount()];
      }

      return min(var0.findFirstVisibleItemPositions(mItemPositionsHolder));
   }

   public static int findLastFullyVisibleItemPosition(StaggeredGridLayoutManager var0) {
      if(mItemPositionsHolder == null) {
         mItemPositionsHolder = new int[var0.getSpanCount()];
      }

      return max(var0.findLastCompletelyVisibleItemPositions(mItemPositionsHolder));
   }

   public static int findLastVisibleItemPosition(StaggeredGridLayoutManager var0) {
      if(mItemPositionsHolder == null) {
         mItemPositionsHolder = new int[var0.getSpanCount()];
      }

      return max(var0.findLastVisibleItemPositions(mItemPositionsHolder));
   }

   private static int max(int[] var0) {
      int var5 = var0.length;
      int var2 = Integer.MIN_VALUE;

      int var3;
      for(int var1 = 0; var1 < var5; var2 = var3) {
         int var4 = var0[var1];
         var3 = var2;
         if(var4 > var2) {
            var3 = var4;
         }

         ++var1;
      }

      return var2;
   }

   private static int min(int[] var0) {
      int var5 = var0.length;
      int var2 = Integer.MAX_VALUE;

      int var3;
      for(int var1 = 0; var1 < var5; var2 = var3) {
         int var4 = var0[var1];
         var3 = var2;
         if(var4 < var2) {
            var3 = var4;
         }

         ++var1;
      }

      return var2;
   }
}
