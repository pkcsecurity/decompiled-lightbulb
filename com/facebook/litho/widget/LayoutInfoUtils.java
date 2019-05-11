package com.facebook.litho.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.facebook.litho.widget.ComponentTreeHolder;
import java.util.List;

public class LayoutInfoUtils {

   public static int computeLinearLayoutWrappedHeight(LinearLayoutManager var0, int var1, List<ComponentTreeHolder> var2) {
      int var5 = var2.size();
      if(var0.getOrientation() != 1) {
         throw new IllegalStateException("This method should only be called when orientation is vertical");
      } else {
         int var3 = 0;

         int var4;
         for(var4 = 0; var3 < var5; ++var3) {
            var4 = var4 + ((ComponentTreeHolder)var2.get(var3)).getMeasuredHeight() + getTopDecorationHeight(var0, var3) + getBottomDecorationHeight(var0, var3);
            if(var4 > var1) {
               return var1;
            }
         }

         return var4;
      }
   }

   public static int getBottomDecorationHeight(RecyclerView.LayoutManager var0, int var1) {
      View var2 = var0.getChildAt(var1);
      return var2 != null?var0.getBottomDecorationHeight(var2):0;
   }

   public static int getMaxHeightInRow(int var0, int var1, List<ComponentTreeHolder> var2) {
      int var4 = var2.size();

      int var3;
      for(var3 = 0; var0 < var1 && var0 < var4; ++var0) {
         var3 = Math.max(var3, ((ComponentTreeHolder)var2.get(var0)).getMeasuredHeight());
      }

      return var3;
   }

   public static int getTopDecorationHeight(RecyclerView.LayoutManager var0, int var1) {
      View var2 = var0.getChildAt(var1);
      return var2 != null?var0.getTopDecorationHeight(var2):0;
   }
}
