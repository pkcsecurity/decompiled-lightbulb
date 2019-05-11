package com.facebook.react.uimanager;

import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.uimanager.ViewGroupManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.annotation.Nullable;

public class ViewGroupDrawingOrderHelper {

   @Nullable
   private int[] mDrawingOrderIndices;
   private int mNumberOfChildrenWithZIndex = 0;
   private final ViewGroup mViewGroup;


   public ViewGroupDrawingOrderHelper(ViewGroup var1) {
      this.mViewGroup = var1;
   }

   public int getChildDrawingOrder(int var1, int var2) {
      if(this.mDrawingOrderIndices == null) {
         ArrayList var5 = new ArrayList();
         byte var4 = 0;

         int var3;
         for(var3 = 0; var3 < var1; ++var3) {
            var5.add(this.mViewGroup.getChildAt(var3));
         }

         Collections.sort(var5, new Comparator() {
            public int compare(View var1, View var2) {
               Integer var3 = ViewGroupManager.getViewZIndex(var1);
               Integer var4 = var3;
               if(var3 == null) {
                  var4 = Integer.valueOf(0);
               }

               var3 = ViewGroupManager.getViewZIndex(var2);
               Integer var5 = var3;
               if(var3 == null) {
                  var5 = Integer.valueOf(0);
               }

               return var4.intValue() - var5.intValue();
            }
         });
         this.mDrawingOrderIndices = new int[var1];

         for(var3 = var4; var3 < var1; ++var3) {
            View var6 = (View)var5.get(var3);
            this.mDrawingOrderIndices[var3] = this.mViewGroup.indexOfChild(var6);
         }
      }

      return this.mDrawingOrderIndices[var2];
   }

   public void handleAddView(View var1) {
      if(ViewGroupManager.getViewZIndex(var1) != null) {
         ++this.mNumberOfChildrenWithZIndex;
      }

      this.mDrawingOrderIndices = null;
   }

   public void handleRemoveView(View var1) {
      if(ViewGroupManager.getViewZIndex(var1) != null) {
         --this.mNumberOfChildrenWithZIndex;
      }

      this.mDrawingOrderIndices = null;
   }

   public boolean shouldEnableCustomDrawingOrder() {
      return this.mNumberOfChildrenWithZIndex > 0;
   }

   public void update() {
      int var1 = 0;

      for(this.mNumberOfChildrenWithZIndex = 0; var1 < this.mViewGroup.getChildCount(); ++var1) {
         if(ViewGroupManager.getViewZIndex(this.mViewGroup.getChildAt(var1)) != null) {
            ++this.mNumberOfChildrenWithZIndex;
         }
      }

      this.mDrawingOrderIndices = null;
   }
}
