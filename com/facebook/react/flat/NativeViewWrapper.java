package com.facebook.react.flat;

import com.facebook.react.flat.AndroidView;
import com.facebook.react.flat.FlatShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaUnit;
import com.facebook.yoga.YogaValue;
import javax.annotation.Nullable;

final class NativeViewWrapper extends FlatShadowNode implements AndroidView {

   private boolean mForceMountGrandChildrenToView;
   private final boolean mNeedsCustomLayoutForChildren;
   private boolean mPaddingChanged = false;
   @Nullable
   private final ReactShadowNode mReactShadowNode;


   NativeViewWrapper(ViewManager var1) {
      ReactShadowNode var2 = var1.createShadowNodeInstance();
      if(var2 instanceof YogaMeasureFunction) {
         this.mReactShadowNode = var2;
         this.setMeasureFunction((YogaMeasureFunction)var2);
      } else {
         this.mReactShadowNode = null;
      }

      if(var1 instanceof ViewGroupManager) {
         ViewGroupManager var3 = (ViewGroupManager)var1;
         this.mNeedsCustomLayoutForChildren = var3.needsCustomLayoutForChildren();
         this.mForceMountGrandChildrenToView = var3.shouldPromoteGrandchildren();
      } else {
         this.mNeedsCustomLayoutForChildren = false;
      }

      this.forceMountToView();
      this.forceMountChildrenToView();
   }

   public void addChildAt(ReactShadowNodeImpl var1, int var2) {
      super.addChildAt(var1, var2);
      if(this.mForceMountGrandChildrenToView && var1 instanceof FlatShadowNode) {
         ((FlatShadowNode)var1).forceMountChildrenToView();
      }

   }

   void handleUpdateProperties(ReactStylesDiffMap var1) {
      if(this.mReactShadowNode != null) {
         this.mReactShadowNode.updateProperties(var1);
      }

   }

   public boolean isPaddingChanged() {
      return this.mPaddingChanged;
   }

   public boolean needsCustomLayoutForChildren() {
      return this.mNeedsCustomLayoutForChildren;
   }

   public void onCollectExtraUpdates(UIViewOperationQueue var1) {
      if(this.mReactShadowNode != null && this.mReactShadowNode.hasUnseenUpdates()) {
         this.mReactShadowNode.onCollectExtraUpdates(var1);
         this.markUpdateSeen();
      }

   }

   public void resetPaddingChanged() {
      this.mPaddingChanged = false;
   }

   public void setBackgroundColor(int var1) {}

   public void setPadding(int var1, float var2) {
      YogaValue var3 = this.getStylePadding(var1);
      if(var3.unit != YogaUnit.POINT || var3.value != var2) {
         super.setPadding(var1, var2);
         this.mPaddingChanged = true;
         this.markUpdated();
      }

   }

   public void setPaddingPercent(int var1, float var2) {
      YogaValue var3 = this.getStylePadding(var1);
      if(var3.unit != YogaUnit.PERCENT || var3.value != var2) {
         super.setPadding(var1, var2);
         this.mPaddingChanged = true;
         this.markUpdated();
      }

   }

   public void setReactTag(int var1) {
      super.setReactTag(var1);
      if(this.mReactShadowNode != null) {
         this.mReactShadowNode.setReactTag(var1);
      }

   }

   public void setThemedContext(ThemedReactContext var1) {
      super.setThemedContext(var1);
      if(this.mReactShadowNode != null) {
         this.mReactShadowNode.setThemedContext(var1);
      }

   }
}
