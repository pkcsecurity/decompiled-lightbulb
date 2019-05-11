package com.facebook.react.uimanager;

import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.LayoutShadowNode;
import java.util.List;
import java.util.WeakHashMap;
import javax.annotation.Nullable;

public abstract class ViewGroupManager<T extends ViewGroup> extends BaseViewManager<T, LayoutShadowNode> {

   private static WeakHashMap<View, Integer> mZIndexHash = new WeakHashMap();


   @Nullable
   public static Integer getViewZIndex(View var0) {
      return (Integer)mZIndexHash.get(var0);
   }

   public static void setViewZIndex(View var0, int var1) {
      mZIndexHash.put(var0, Integer.valueOf(var1));
   }

   public void addView(T var1, View var2, int var3) {
      var1.addView(var2, var3);
   }

   public void addViews(T var1, List<View> var2) {
      int var4 = var2.size();

      for(int var3 = 0; var3 < var4; ++var3) {
         this.addView(var1, (View)var2.get(var3), var3);
      }

   }

   public LayoutShadowNode createShadowNodeInstance() {
      return new LayoutShadowNode();
   }

   public View getChildAt(T var1, int var2) {
      return var1.getChildAt(var2);
   }

   public int getChildCount(T var1) {
      return var1.getChildCount();
   }

   public Class<? extends LayoutShadowNode> getShadowNodeClass() {
      return LayoutShadowNode.class;
   }

   public boolean needsCustomLayoutForChildren() {
      return false;
   }

   public void removeAllViews(T var1) {
      for(int var2 = this.getChildCount(var1) - 1; var2 >= 0; --var2) {
         this.removeViewAt(var1, var2);
      }

   }

   public void removeView(T var1, View var2) {
      for(int var3 = 0; var3 < this.getChildCount(var1); ++var3) {
         if(this.getChildAt(var1, var3) == var2) {
            this.removeViewAt(var1, var3);
            return;
         }
      }

   }

   public void removeViewAt(T var1, int var2) {
      var1.removeViewAt(var2);
   }

   public boolean shouldPromoteGrandchildren() {
      return false;
   }

   public void updateExtraData(T var1, Object var2) {}
}
