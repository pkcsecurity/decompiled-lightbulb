package com.facebook.react.uimanager;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.facebook.react.common.SingleThreadAsserter;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.ReactShadowNode;

public class ShadowNodeRegistry {

   private final SparseBooleanArray mRootTags = new SparseBooleanArray();
   private final SparseArray<ReactShadowNode> mTagsToCSSNodes = new SparseArray();
   private final SingleThreadAsserter mThreadAsserter = new SingleThreadAsserter();


   public void addNode(ReactShadowNode var1) {
      this.mThreadAsserter.assertNow();
      this.mTagsToCSSNodes.put(var1.getReactTag(), var1);
   }

   public void addRootNode(ReactShadowNode var1) {
      int var2 = var1.getReactTag();
      this.mTagsToCSSNodes.put(var2, var1);
      this.mRootTags.put(var2, true);
   }

   public ReactShadowNode getNode(int var1) {
      this.mThreadAsserter.assertNow();
      return (ReactShadowNode)this.mTagsToCSSNodes.get(var1);
   }

   public int getRootNodeCount() {
      this.mThreadAsserter.assertNow();
      return this.mRootTags.size();
   }

   public int getRootTag(int var1) {
      this.mThreadAsserter.assertNow();
      return this.mRootTags.keyAt(var1);
   }

   public boolean isRootNode(int var1) {
      this.mThreadAsserter.assertNow();
      return this.mRootTags.get(var1);
   }

   public void removeNode(int var1) {
      this.mThreadAsserter.assertNow();
      if(this.mRootTags.get(var1)) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Trying to remove root node ");
         var2.append(var1);
         var2.append(" without using removeRootNode!");
         throw new IllegalViewOperationException(var2.toString());
      } else {
         this.mTagsToCSSNodes.remove(var1);
      }
   }

   public void removeRootNode(int var1) {
      this.mThreadAsserter.assertNow();
      if(!this.mRootTags.get(var1)) {
         StringBuilder var2 = new StringBuilder();
         var2.append("View with tag ");
         var2.append(var1);
         var2.append(" is not registered as a root view");
         throw new IllegalViewOperationException(var2.toString());
      } else {
         this.mTagsToCSSNodes.remove(var1);
         this.mRootTags.delete(var1);
      }
   }
}
