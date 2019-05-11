package com.facebook.react.uimanager;

import android.util.SparseBooleanArray;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ShadowNodeRegistry;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.ViewAtIndex;
import com.facebook.react.uimanager.ViewProps;
import javax.annotation.Nullable;

public class NativeViewHierarchyOptimizer {

   private static final boolean ENABLED = true;
   private final ShadowNodeRegistry mShadowNodeRegistry;
   private final SparseBooleanArray mTagsWithLayoutVisited = new SparseBooleanArray();
   private final UIViewOperationQueue mUIViewOperationQueue;


   public NativeViewHierarchyOptimizer(UIViewOperationQueue var1, ShadowNodeRegistry var2) {
      this.mUIViewOperationQueue = var1;
      this.mShadowNodeRegistry = var2;
   }

   private void addGrandchildren(ReactShadowNode var1, ReactShadowNode var2, int var3) {
      Assertions.assertCondition(var1.isLayoutOnly() ^ true);

      for(int var4 = 0; var4 < var2.getChildCount(); ++var4) {
         ReactShadowNode var7 = var2.getChildAt(var4);
         boolean var6;
         if(var7.getNativeParent() == null) {
            var6 = true;
         } else {
            var6 = false;
         }

         Assertions.assertCondition(var6);
         if(var7.isLayoutOnly()) {
            int var5 = var1.getNativeChildCount();
            this.addLayoutOnlyNode(var1, var7, var3);
            var3 += var1.getNativeChildCount() - var5;
         } else {
            this.addNonLayoutNode(var1, var7, var3);
            ++var3;
         }
      }

   }

   private void addLayoutOnlyNode(ReactShadowNode var1, ReactShadowNode var2, int var3) {
      this.addGrandchildren(var1, var2, var3);
   }

   private void addNodeToNode(ReactShadowNode var1, ReactShadowNode var2, int var3) {
      int var4 = var1.getNativeOffsetForChild(var1.getChildAt(var3));
      ReactShadowNode var5 = var1;
      var3 = var4;
      if(var1.isLayoutOnly()) {
         NativeViewHierarchyOptimizer.NodeIndexPair var6 = this.walkUpUntilNonLayoutOnly(var1, var4);
         if(var6 == null) {
            return;
         }

         var5 = var6.node;
         var3 = var6.index;
      }

      if(!var2.isLayoutOnly()) {
         this.addNonLayoutNode(var5, var2, var3);
      } else {
         this.addLayoutOnlyNode(var5, var2, var3);
      }
   }

   private void addNonLayoutNode(ReactShadowNode var1, ReactShadowNode var2, int var3) {
      var1.addNativeChildAt(var2, var3);
      this.mUIViewOperationQueue.enqueueManageChildren(var1.getReactTag(), (int[])null, new ViewAtIndex[]{new ViewAtIndex(var2.getReactTag(), var3)}, (int[])null);
   }

   private void applyLayoutBase(ReactShadowNode var1) {
      int var2 = var1.getReactTag();
      if(!this.mTagsWithLayoutVisited.get(var2)) {
         this.mTagsWithLayoutVisited.put(var2, true);
         ReactShadowNode var4 = var1.getParent();
         int var3 = var1.getScreenX();

         for(var2 = var1.getScreenY(); var4 != null && var4.isLayoutOnly(); var4 = var4.getParent()) {
            var3 += Math.round(var4.getLayoutX());
            var2 += Math.round(var4.getLayoutY());
         }

         this.applyLayoutRecursive(var1, var3, var2);
      }
   }

   private void applyLayoutRecursive(ReactShadowNode var1, int var2, int var3) {
      int var4;
      if(!var1.isLayoutOnly() && var1.getNativeParent() != null) {
         var4 = var1.getReactTag();
         this.mUIViewOperationQueue.enqueueUpdateLayout(var1.getNativeParent().getReactTag(), var4, var2, var3, var1.getScreenWidth(), var1.getScreenHeight());
      } else {
         for(var4 = 0; var4 < var1.getChildCount(); ++var4) {
            ReactShadowNode var6 = var1.getChildAt(var4);
            int var5 = var6.getReactTag();
            if(!this.mTagsWithLayoutVisited.get(var5)) {
               this.mTagsWithLayoutVisited.put(var5, true);
               this.applyLayoutRecursive(var6, var6.getScreenX() + var2, var6.getScreenY() + var3);
            }
         }

      }
   }

   public static void handleRemoveNode(ReactShadowNode var0) {
      var0.removeAllNativeChildren();
   }

   private static boolean isLayoutOnlyAndCollapsable(@Nullable ReactStylesDiffMap var0) {
      if(var0 == null) {
         return true;
      } else if(var0.hasKey("collapsable") && !var0.getBoolean("collapsable", true)) {
         return false;
      } else {
         ReadableMapKeySetIterator var1 = var0.mBackingMap.keySetIterator();

         do {
            if(!var1.hasNextKey()) {
               return true;
            }
         } while(ViewProps.isLayoutOnly(var0.mBackingMap, var1.nextKey()));

         return false;
      }
   }

   private void removeNodeFromParent(ReactShadowNode var1, boolean var2) {
      ReactShadowNode var5 = var1.getNativeParent();
      int var3;
      if(var5 != null) {
         var3 = var5.indexOfNativeChild(var1);
         var5.removeNativeChildAt(var3);
         UIViewOperationQueue var6 = this.mUIViewOperationQueue;
         int var4 = var5.getReactTag();
         int[] var7;
         if(var2) {
            int[] var8 = new int[]{var1.getReactTag()};
            var7 = var8;
         } else {
            var7 = null;
         }

         var6.enqueueManageChildren(var4, new int[]{var3}, (ViewAtIndex[])null, var7);
      } else {
         for(var3 = var1.getChildCount() - 1; var3 >= 0; --var3) {
            this.removeNodeFromParent(var1.getChildAt(var3), var2);
         }

      }
   }

   private void transitionLayoutOnlyViewToNativeView(ReactShadowNode var1, @Nullable ReactStylesDiffMap var2) {
      ReactShadowNode var6 = var1.getParent();
      byte var4 = 0;
      if(var6 == null) {
         var1.setIsLayoutOnly(false);
      } else {
         int var3 = var6.indexOf(var1);
         var6.removeChildAt(var3);
         this.removeNodeFromParent(var1, false);
         var1.setIsLayoutOnly(false);
         this.mUIViewOperationQueue.enqueueCreateView(var1.getRootNode().getThemedContext(), var1.getReactTag(), var1.getViewClass(), var2);
         var6.addChildAt(var1, var3);
         this.addNodeToNode(var6, var1, var3);

         for(var3 = 0; var3 < var1.getChildCount(); ++var3) {
            this.addNodeToNode(var1, var1.getChildAt(var3), var3);
         }

         boolean var5;
         if(this.mTagsWithLayoutVisited.size() == 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         Assertions.assertCondition(var5);
         this.applyLayoutBase(var1);

         for(var3 = var4; var3 < var1.getChildCount(); ++var3) {
            this.applyLayoutBase(var1.getChildAt(var3));
         }

         this.mTagsWithLayoutVisited.clear();
      }
   }

   private NativeViewHierarchyOptimizer.NodeIndexPair walkUpUntilNonLayoutOnly(ReactShadowNode var1, int var2) {
      while(var1.isLayoutOnly()) {
         ReactShadowNode var3 = var1.getParent();
         if(var3 == null) {
            return null;
         }

         var2 += var3.getNativeOffsetForChild(var1);
         var1 = var3;
      }

      return new NativeViewHierarchyOptimizer.NodeIndexPair(var1, var2);
   }

   public void handleCreateView(ReactShadowNode var1, ThemedReactContext var2, @Nullable ReactStylesDiffMap var3) {
      boolean var4;
      if(var1.getViewClass().equals("RCTView") && isLayoutOnlyAndCollapsable(var3)) {
         var4 = true;
      } else {
         var4 = false;
      }

      var1.setIsLayoutOnly(var4);
      if(!var4) {
         this.mUIViewOperationQueue.enqueueCreateView(var2, var1.getReactTag(), var1.getViewClass(), var3);
      }

   }

   public void handleManageChildren(ReactShadowNode var1, int[] var2, int[] var3, ViewAtIndex[] var4, int[] var5) {
      byte var8 = 0;
      int var6 = 0;

      while(true) {
         int var7 = var8;
         if(var6 >= var3.length) {
            while(var7 < var4.length) {
               ViewAtIndex var11 = var4[var7];
               this.addNodeToNode(var1, this.mShadowNodeRegistry.getNode(var11.mTag), var11.mIndex);
               ++var7;
            }

            return;
         }

         int var9 = var3[var6];
         var7 = 0;

         while(true) {
            boolean var10;
            if(var7 < var5.length) {
               if(var5[var7] != var9) {
                  ++var7;
                  continue;
               }

               var10 = true;
            } else {
               var10 = false;
            }

            this.removeNodeFromParent(this.mShadowNodeRegistry.getNode(var9), var10);
            ++var6;
            break;
         }
      }
   }

   public void handleSetChildren(ReactShadowNode var1, ReadableArray var2) {
      for(int var3 = 0; var3 < var2.size(); ++var3) {
         this.addNodeToNode(var1, this.mShadowNodeRegistry.getNode(var2.getInt(var3)), var3);
      }

   }

   public void handleUpdateLayout(ReactShadowNode var1) {
      this.applyLayoutBase(var1);
   }

   public void handleUpdateView(ReactShadowNode var1, String var2, ReactStylesDiffMap var3) {
      boolean var4;
      if(var1.isLayoutOnly() && !isLayoutOnlyAndCollapsable(var3)) {
         var4 = true;
      } else {
         var4 = false;
      }

      if(var4) {
         this.transitionLayoutOnlyViewToNativeView(var1, var3);
      } else {
         if(!var1.isLayoutOnly()) {
            this.mUIViewOperationQueue.enqueueUpdateProperties(var1.getReactTag(), var2, var3);
         }

      }
   }

   public void onBatchComplete() {
      this.mTagsWithLayoutVisited.clear();
   }

   static class NodeIndexPair {

      public final int index;
      public final ReactShadowNode node;


      NodeIndexPair(ReactShadowNode var1, int var2) {
         this.node = var1;
         this.index = var2;
      }
   }
}
