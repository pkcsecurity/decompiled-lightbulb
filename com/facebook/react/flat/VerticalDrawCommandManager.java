package com.facebook.react.flat;

import android.util.SparseIntArray;
import com.facebook.react.flat.ClippingDrawCommandManager;
import com.facebook.react.flat.DrawCommand;
import com.facebook.react.flat.DrawView;
import com.facebook.react.flat.FlatViewGroup;
import com.facebook.react.flat.NodeRegion;
import java.util.Arrays;

final class VerticalDrawCommandManager extends ClippingDrawCommandManager {

   VerticalDrawCommandManager(FlatViewGroup var1, DrawCommand[] var2) {
      super(var1, var2);
   }

   public static void fillMaxMinArrays(DrawCommand[] var0, float[] var1, float[] var2, SparseIntArray var3) {
      float var4 = 0.0F;

      int var5;
      for(var5 = 0; var5 < var0.length; ++var5) {
         if(var0[var5] instanceof DrawView) {
            DrawView var6 = (DrawView)var0[var5];
            var3.append(var6.reactTag, var5);
            var4 = Math.max(var4, var6.mLogicalBottom);
         } else {
            var4 = Math.max(var4, var0[var5].getBottom());
         }

         var1[var5] = var4;
      }

      for(var5 = var0.length - 1; var5 >= 0; --var5) {
         if(var0[var5] instanceof DrawView) {
            var4 = Math.min(var4, ((DrawView)var0[var5]).mLogicalTop);
         } else {
            var4 = Math.min(var4, var0[var5].getTop());
         }

         var2[var5] = var4;
      }

   }

   public static void fillMaxMinArrays(NodeRegion[] var0, float[] var1, float[] var2) {
      float var3 = 0.0F;

      int var4;
      for(var4 = 0; var4 < var0.length; ++var4) {
         var3 = Math.max(var3, var0[var4].getTouchableBottom());
         var1[var4] = var3;
      }

      for(var4 = var0.length - 1; var4 >= 0; --var4) {
         var3 = Math.min(var3, var0[var4].getTouchableTop());
         var2[var4] = var3;
      }

   }

   int commandStartIndex() {
      int var2 = Arrays.binarySearch(this.mCommandMaxBottom, (float)this.mClippingRect.top);
      int var1 = var2;
      if(var2 < 0) {
         var1 = ~var2;
      }

      return var1;
   }

   int commandStopIndex(int var1) {
      int var2 = Arrays.binarySearch(this.mCommandMinTop, var1, this.mCommandMinTop.length, (float)this.mClippingRect.bottom);
      var1 = var2;
      if(var2 < 0) {
         var1 = ~var2;
      }

      return var1;
   }

   boolean regionAboveTouch(int var1, float var2, float var3) {
      return this.mRegionMaxBottom[var1] < var3;
   }

   int regionStopIndex(float var1, float var2) {
      int var4 = Arrays.binarySearch(this.mRegionMinTop, var2 + 1.0E-4F);
      int var3 = var4;
      if(var4 < 0) {
         var3 = ~var4;
      }

      return var3;
   }
}
