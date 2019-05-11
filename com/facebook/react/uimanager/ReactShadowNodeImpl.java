package com.facebook.react.uimanager;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.NativeViewHierarchyOptimizer;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.Spacing;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.ViewManagerPropertyUpdater;
import com.facebook.react.uimanager.YogaNodePool;
import com.facebook.react.uimanager.annotations.ReactPropertyHolder;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaBaselineFunction;
import com.facebook.yoga.YogaConfig;
import com.facebook.yoga.YogaConstants;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaDisplay;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.YogaOverflow;
import com.facebook.yoga.YogaPositionType;
import com.facebook.yoga.YogaValue;
import com.facebook.yoga.YogaWrap;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.Nullable;

@ReactPropertyHolder
public class ReactShadowNodeImpl implements ReactShadowNode<ReactShadowNodeImpl> {

   private static YogaConfig sYogaConfig;
   @Nullable
   private ArrayList<ReactShadowNodeImpl> mChildren;
   private final Spacing mDefaultPadding = new Spacing(0.0F);
   private boolean mIsLayoutOnly;
   @Nullable
   private ArrayList<ReactShadowNodeImpl> mNativeChildren;
   @Nullable
   private ReactShadowNodeImpl mNativeParent;
   private boolean mNodeUpdated = true;
   private final float[] mPadding = new float[9];
   private final boolean[] mPaddingIsPercent = new boolean[9];
   @Nullable
   private ReactShadowNodeImpl mParent;
   private int mReactTag;
   @Nullable
   private ReactShadowNodeImpl mRootNode;
   private int mScreenHeight;
   private int mScreenWidth;
   private int mScreenX;
   private int mScreenY;
   private boolean mShouldNotifyOnLayout;
   @Nullable
   private ThemedReactContext mThemedContext;
   private int mTotalNativeChildren = 0;
   @Nullable
   private String mViewClassName;
   private final YogaNode mYogaNode;


   public ReactShadowNodeImpl() {
      if(!this.isVirtual()) {
         YogaNode var2 = (YogaNode)YogaNodePool.get().acquire();
         if(sYogaConfig == null) {
            sYogaConfig = new YogaConfig();
            sYogaConfig.setPointScaleFactor(0.0F);
            sYogaConfig.setUseLegacyStretchBehaviour(true);
         }

         YogaNode var1 = var2;
         if(var2 == null) {
            var1 = new YogaNode(sYogaConfig);
         }

         this.mYogaNode = var1;
         Arrays.fill(this.mPadding, Float.NaN);
      } else {
         this.mYogaNode = null;
      }
   }

   private void toStringWithIndentation(StringBuilder var1, int var2) {
      byte var4 = 0;

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         var1.append("__");
      }

      var1.append(this.getClass().getSimpleName());
      var1.append(" ");
      if(this.mYogaNode != null) {
         var1.append(this.getLayoutWidth());
         var1.append(",");
         var1.append(this.getLayoutHeight());
      } else {
         var1.append("(virtual node)");
      }

      var1.append("\n");
      var3 = var4;
      if(this.getChildCount() != 0) {
         while(var3 < this.getChildCount()) {
            this.getChildAt(var3).toStringWithIndentation(var1, var2 + 1);
            ++var3;
         }

      }
   }

   private void updateNativeChildrenCountInParent(int var1) {
      if(this.mIsLayoutOnly) {
         for(ReactShadowNodeImpl var2 = this.getParent(); var2 != null; var2 = var2.getParent()) {
            var2.mTotalNativeChildren += var1;
            if(!var2.isLayoutOnly()) {
               return;
            }
         }
      }

   }

   private void updatePadding() {
      for(int var1 = 0; var1 <= 8; ++var1) {
         if(var1 != 0 && var1 != 2 && var1 != 4 && var1 != 5) {
            if(var1 != 1 && var1 != 3) {
               if(YogaConstants.isUndefined(this.mPadding[var1])) {
                  this.mYogaNode.setPadding(YogaEdge.fromInt(var1), this.mDefaultPadding.getRaw(var1));
                  continue;
               }
            } else if(YogaConstants.isUndefined(this.mPadding[var1]) && YogaConstants.isUndefined(this.mPadding[7]) && YogaConstants.isUndefined(this.mPadding[8])) {
               this.mYogaNode.setPadding(YogaEdge.fromInt(var1), this.mDefaultPadding.getRaw(var1));
               continue;
            }
         } else if(YogaConstants.isUndefined(this.mPadding[var1]) && YogaConstants.isUndefined(this.mPadding[6]) && YogaConstants.isUndefined(this.mPadding[8])) {
            this.mYogaNode.setPadding(YogaEdge.fromInt(var1), this.mDefaultPadding.getRaw(var1));
            continue;
         }

         if(this.mPaddingIsPercent[var1]) {
            this.mYogaNode.setPaddingPercent(YogaEdge.fromInt(var1), this.mPadding[var1]);
         } else {
            this.mYogaNode.setPadding(YogaEdge.fromInt(var1), this.mPadding[var1]);
         }
      }

   }

   public void addChildAt(ReactShadowNodeImpl var1, int var2) {
      if(var1.getParent() != null) {
         throw new IllegalViewOperationException("Tried to add child that already has a parent! Remove it from its parent first.");
      } else {
         if(this.mChildren == null) {
            this.mChildren = new ArrayList(4);
         }

         this.mChildren.add(var2, var1);
         var1.mParent = this;
         if(this.mYogaNode != null && !this.isYogaLeafNode()) {
            YogaNode var3 = var1.mYogaNode;
            if(var3 == null) {
               StringBuilder var4 = new StringBuilder();
               var4.append("Cannot add a child that doesn\'t have a YogaNode to a parent without a measure function! (Trying to add a \'");
               var4.append(var1.getClass().getSimpleName());
               var4.append("\' to a \'");
               var4.append(this.getClass().getSimpleName());
               var4.append("\')");
               throw new RuntimeException(var4.toString());
            }

            this.mYogaNode.addChildAt(var3, var2);
         }

         this.markUpdated();
         if(var1.isLayoutOnly()) {
            var2 = var1.getTotalNativeChildren();
         } else {
            var2 = 1;
         }

         this.mTotalNativeChildren += var2;
         this.updateNativeChildrenCountInParent(var2);
      }
   }

   public final void addNativeChildAt(ReactShadowNodeImpl var1, int var2) {
      Assertions.assertCondition(this.mIsLayoutOnly ^ true);
      Assertions.assertCondition(var1.mIsLayoutOnly ^ true);
      if(this.mNativeChildren == null) {
         this.mNativeChildren = new ArrayList(4);
      }

      this.mNativeChildren.add(var2, var1);
      var1.mNativeParent = this;
   }

   public void calculateLayout() {
      this.mYogaNode.calculateLayout(Float.NaN, Float.NaN);
   }

   public void dirty() {
      if(!this.isVirtual()) {
         this.mYogaNode.dirty();
      }

   }

   public boolean dispatchUpdates(float var1, float var2, UIViewOperationQueue var3, NativeViewHierarchyOptimizer var4) {
      if(this.mNodeUpdated) {
         this.onCollectExtraUpdates(var3);
      }

      boolean var14 = this.hasNewLayout();
      boolean var13 = false;
      if(!var14) {
         return false;
      } else {
         float var5 = this.getLayoutX();
         float var6 = this.getLayoutY();
         var1 += var5;
         int var11 = Math.round(var1);
         var2 += var6;
         int var9 = Math.round(var2);
         int var12 = Math.round(var1 + this.getLayoutWidth());
         int var10 = Math.round(var2 + this.getLayoutHeight());
         int var7 = Math.round(var5);
         int var8 = Math.round(var6);
         var11 = var12 - var11;
         var9 = var10 - var9;
         if(var7 != this.mScreenX || var8 != this.mScreenY || var11 != this.mScreenWidth || var9 != this.mScreenHeight) {
            var13 = true;
         }

         this.mScreenX = var7;
         this.mScreenY = var8;
         this.mScreenWidth = var11;
         this.mScreenHeight = var9;
         if(var13) {
            var4.handleUpdateLayout(this);
         }

         return var13;
      }
   }

   public void dispose() {
      if(this.mYogaNode != null) {
         this.mYogaNode.reset();
         YogaNodePool.get().release(this.mYogaNode);
      }

   }

   public final ReactShadowNodeImpl getChildAt(int var1) {
      if(this.mChildren == null) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Index ");
         var2.append(var1);
         var2.append(" out of bounds: node has no children");
         throw new ArrayIndexOutOfBoundsException(var2.toString());
      } else {
         return (ReactShadowNodeImpl)this.mChildren.get(var1);
      }
   }

   public final int getChildCount() {
      return this.mChildren == null?0:this.mChildren.size();
   }

   public final YogaDirection getLayoutDirection() {
      return this.mYogaNode.getLayoutDirection();
   }

   public final float getLayoutHeight() {
      return this.mYogaNode.getLayoutHeight();
   }

   public final float getLayoutWidth() {
      return this.mYogaNode.getLayoutWidth();
   }

   public final float getLayoutX() {
      return this.mYogaNode.getLayoutX();
   }

   public final float getLayoutY() {
      return this.mYogaNode.getLayoutY();
   }

   public final int getNativeChildCount() {
      return this.mNativeChildren == null?0:this.mNativeChildren.size();
   }

   public final int getNativeOffsetForChild(ReactShadowNodeImpl var1) {
      boolean var5 = false;
      int var3 = 0;
      int var2 = 0;

      boolean var4;
      while(true) {
         int var7 = this.getChildCount();
         byte var6 = 1;
         var4 = var5;
         if(var3 >= var7) {
            break;
         }

         ReactShadowNodeImpl var8 = this.getChildAt(var3);
         if(var1 == var8) {
            var4 = true;
            break;
         }

         int var9 = var6;
         if(var8.isLayoutOnly()) {
            var9 = var8.getTotalNativeChildren();
         }

         var2 += var9;
         ++var3;
      }

      if(!var4) {
         StringBuilder var10 = new StringBuilder();
         var10.append("Child ");
         var10.append(var1.getReactTag());
         var10.append(" was not a child of ");
         var10.append(this.mReactTag);
         throw new RuntimeException(var10.toString());
      } else {
         return var2;
      }
   }

   @Nullable
   public final ReactShadowNodeImpl getNativeParent() {
      return this.mNativeParent;
   }

   public final float getPadding(int var1) {
      return this.mYogaNode.getLayoutPadding(YogaEdge.fromInt(var1));
   }

   @Nullable
   public final ReactShadowNodeImpl getParent() {
      return this.mParent;
   }

   public final int getReactTag() {
      return this.mReactTag;
   }

   public final ReactShadowNodeImpl getRootNode() {
      return (ReactShadowNodeImpl)Assertions.assertNotNull(this.mRootNode);
   }

   public int getScreenHeight() {
      return this.mScreenHeight;
   }

   public int getScreenWidth() {
      return this.mScreenWidth;
   }

   public int getScreenX() {
      return this.mScreenX;
   }

   public int getScreenY() {
      return this.mScreenY;
   }

   public final YogaValue getStyleHeight() {
      return this.mYogaNode.getHeight();
   }

   public final YogaValue getStylePadding(int var1) {
      return this.mYogaNode.getPadding(YogaEdge.fromInt(var1));
   }

   public final YogaValue getStyleWidth() {
      return this.mYogaNode.getWidth();
   }

   public final ThemedReactContext getThemedContext() {
      return (ThemedReactContext)Assertions.assertNotNull(this.mThemedContext);
   }

   public final int getTotalNativeChildren() {
      return this.mTotalNativeChildren;
   }

   public final String getViewClass() {
      return (String)Assertions.assertNotNull(this.mViewClassName);
   }

   public final boolean hasNewLayout() {
      return this.mYogaNode != null && this.mYogaNode.hasNewLayout();
   }

   public final boolean hasUnseenUpdates() {
      return this.mNodeUpdated;
   }

   public final boolean hasUpdates() {
      return this.mNodeUpdated || this.hasNewLayout() || this.isDirty();
   }

   public final int indexOf(ReactShadowNodeImpl var1) {
      return this.mChildren == null?-1:this.mChildren.indexOf(var1);
   }

   public final int indexOfNativeChild(ReactShadowNodeImpl var1) {
      Assertions.assertNotNull(this.mNativeChildren);
      return this.mNativeChildren.indexOf(var1);
   }

   public boolean isDescendantOf(ReactShadowNodeImpl var1) {
      for(ReactShadowNodeImpl var2 = this.getParent(); var2 != null; var2 = var2.getParent()) {
         if(var2 == var1) {
            return true;
         }
      }

      return false;
   }

   public final boolean isDirty() {
      return this.mYogaNode != null && this.mYogaNode.isDirty();
   }

   public final boolean isLayoutOnly() {
      return this.mIsLayoutOnly;
   }

   public boolean isMeasureDefined() {
      return this.mYogaNode.isMeasureDefined();
   }

   public boolean isVirtual() {
      return false;
   }

   public boolean isVirtualAnchor() {
      return false;
   }

   public boolean isYogaLeafNode() {
      return this.isMeasureDefined();
   }

   public final void markLayoutSeen() {
      if(this.mYogaNode != null) {
         this.mYogaNode.markLayoutSeen();
      }

   }

   public final void markUpdateSeen() {
      this.mNodeUpdated = false;
      if(this.hasNewLayout()) {
         this.markLayoutSeen();
      }

   }

   public void markUpdated() {
      if(!this.mNodeUpdated) {
         this.mNodeUpdated = true;
         ReactShadowNodeImpl var1 = this.getParent();
         if(var1 != null) {
            var1.markUpdated();
         }

      }
   }

   public void onAfterUpdateTransaction() {}

   public void onBeforeLayout() {}

   public void onCollectExtraUpdates(UIViewOperationQueue var1) {}

   public final void removeAllNativeChildren() {
      if(this.mNativeChildren != null) {
         for(int var1 = this.mNativeChildren.size() - 1; var1 >= 0; --var1) {
            ((ReactShadowNodeImpl)this.mNativeChildren.get(var1)).mNativeParent = null;
         }

         this.mNativeChildren.clear();
      }

   }

   public void removeAndDisposeAllChildren() {
      if(this.getChildCount() != 0) {
         int var2 = 0;

         for(int var1 = this.getChildCount() - 1; var1 >= 0; --var1) {
            if(this.mYogaNode != null && !this.isYogaLeafNode()) {
               this.mYogaNode.removeChildAt(var1);
            }

            ReactShadowNodeImpl var4 = this.getChildAt(var1);
            var4.mParent = null;
            var4.dispose();
            int var3;
            if(var4.isLayoutOnly()) {
               var3 = var4.getTotalNativeChildren();
            } else {
               var3 = 1;
            }

            var2 += var3;
         }

         ((ArrayList)Assertions.assertNotNull(this.mChildren)).clear();
         this.markUpdated();
         this.mTotalNativeChildren -= var2;
         this.updateNativeChildrenCountInParent(-var2);
      }
   }

   public ReactShadowNodeImpl removeChildAt(int var1) {
      if(this.mChildren == null) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Index ");
         var3.append(var1);
         var3.append(" out of bounds: node has no children");
         throw new ArrayIndexOutOfBoundsException(var3.toString());
      } else {
         ReactShadowNodeImpl var2 = (ReactShadowNodeImpl)this.mChildren.remove(var1);
         var2.mParent = null;
         if(this.mYogaNode != null && !this.isYogaLeafNode()) {
            this.mYogaNode.removeChildAt(var1);
         }

         this.markUpdated();
         if(var2.isLayoutOnly()) {
            var1 = var2.getTotalNativeChildren();
         } else {
            var1 = 1;
         }

         this.mTotalNativeChildren -= var1;
         this.updateNativeChildrenCountInParent(-var1);
         return var2;
      }
   }

   public final ReactShadowNodeImpl removeNativeChildAt(int var1) {
      Assertions.assertNotNull(this.mNativeChildren);
      ReactShadowNodeImpl var2 = (ReactShadowNodeImpl)this.mNativeChildren.remove(var1);
      var2.mNativeParent = null;
      return var2;
   }

   public void setAlignContent(YogaAlign var1) {
      this.mYogaNode.setAlignContent(var1);
   }

   public void setAlignItems(YogaAlign var1) {
      this.mYogaNode.setAlignItems(var1);
   }

   public void setAlignSelf(YogaAlign var1) {
      this.mYogaNode.setAlignSelf(var1);
   }

   public void setBaselineFunction(YogaBaselineFunction var1) {
      this.mYogaNode.setBaselineFunction(var1);
   }

   public void setBorder(int var1, float var2) {
      this.mYogaNode.setBorder(YogaEdge.fromInt(var1), var2);
   }

   public void setDefaultPadding(int var1, float var2) {
      this.mDefaultPadding.set(var1, var2);
      this.updatePadding();
   }

   public void setDisplay(YogaDisplay var1) {
      this.mYogaNode.setDisplay(var1);
   }

   public void setFlex(float var1) {
      this.mYogaNode.setFlex(var1);
   }

   public void setFlexBasis(float var1) {
      this.mYogaNode.setFlexBasis(var1);
   }

   public void setFlexBasisAuto() {
      this.mYogaNode.setFlexBasisAuto();
   }

   public void setFlexBasisPercent(float var1) {
      this.mYogaNode.setFlexBasisPercent(var1);
   }

   public void setFlexDirection(YogaFlexDirection var1) {
      this.mYogaNode.setFlexDirection(var1);
   }

   public void setFlexGrow(float var1) {
      this.mYogaNode.setFlexGrow(var1);
   }

   public void setFlexShrink(float var1) {
      this.mYogaNode.setFlexShrink(var1);
   }

   public void setFlexWrap(YogaWrap var1) {
      this.mYogaNode.setWrap(var1);
   }

   public final void setIsLayoutOnly(boolean var1) {
      ReactShadowNodeImpl var4 = this.getParent();
      boolean var3 = false;
      boolean var2;
      if(var4 == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Assertions.assertCondition(var2, "Must remove from no opt parent first");
      if(this.mNativeParent == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Assertions.assertCondition(var2, "Must remove from native parent first");
      var2 = var3;
      if(this.getNativeChildCount() == 0) {
         var2 = true;
      }

      Assertions.assertCondition(var2, "Must remove all native children first");
      this.mIsLayoutOnly = var1;
   }

   public void setJustifyContent(YogaJustify var1) {
      this.mYogaNode.setJustifyContent(var1);
   }

   public void setLayoutDirection(YogaDirection var1) {
      this.mYogaNode.setDirection(var1);
   }

   public void setLocalData(Object var1) {}

   public void setMargin(int var1, float var2) {
      this.mYogaNode.setMargin(YogaEdge.fromInt(var1), var2);
   }

   public void setMarginAuto(int var1) {
      this.mYogaNode.setMarginAuto(YogaEdge.fromInt(var1));
   }

   public void setMarginPercent(int var1, float var2) {
      this.mYogaNode.setMarginPercent(YogaEdge.fromInt(var1), var2);
   }

   public void setMeasureFunction(YogaMeasureFunction var1) {
      boolean var2;
      if(var1 == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      if(var2 ^ this.mYogaNode.isMeasureDefined() && this.getChildCount() != 0) {
         throw new RuntimeException("Since a node with a measure function does not add any native yoga children, it\'s not safe to transition to/from having a measure function unless a node has no children");
      } else {
         this.mYogaNode.setMeasureFunction(var1);
      }
   }

   public void setOverflow(YogaOverflow var1) {
      this.mYogaNode.setOverflow(var1);
   }

   public void setPadding(int var1, float var2) {
      this.mPadding[var1] = var2;
      this.mPaddingIsPercent[var1] = false;
      this.updatePadding();
   }

   public void setPaddingPercent(int var1, float var2) {
      this.mPadding[var1] = var2;
      this.mPaddingIsPercent[var1] = YogaConstants.isUndefined(var2) ^ true;
      this.updatePadding();
   }

   public void setPosition(int var1, float var2) {
      this.mYogaNode.setPosition(YogaEdge.fromInt(var1), var2);
   }

   public void setPositionPercent(int var1, float var2) {
      this.mYogaNode.setPositionPercent(YogaEdge.fromInt(var1), var2);
   }

   public void setPositionType(YogaPositionType var1) {
      this.mYogaNode.setPositionType(var1);
   }

   public void setReactTag(int var1) {
      this.mReactTag = var1;
   }

   public final void setRootNode(ReactShadowNodeImpl var1) {
      this.mRootNode = var1;
   }

   public void setShouldNotifyOnLayout(boolean var1) {
      this.mShouldNotifyOnLayout = var1;
   }

   public void setStyleAspectRatio(float var1) {
      this.mYogaNode.setAspectRatio(var1);
   }

   public void setStyleHeight(float var1) {
      this.mYogaNode.setHeight(var1);
   }

   public void setStyleHeightAuto() {
      this.mYogaNode.setHeightAuto();
   }

   public void setStyleHeightPercent(float var1) {
      this.mYogaNode.setHeightPercent(var1);
   }

   public void setStyleMaxHeight(float var1) {
      this.mYogaNode.setMaxHeight(var1);
   }

   public void setStyleMaxHeightPercent(float var1) {
      this.mYogaNode.setMaxHeightPercent(var1);
   }

   public void setStyleMaxWidth(float var1) {
      this.mYogaNode.setMaxWidth(var1);
   }

   public void setStyleMaxWidthPercent(float var1) {
      this.mYogaNode.setMaxWidthPercent(var1);
   }

   public void setStyleMinHeight(float var1) {
      this.mYogaNode.setMinHeight(var1);
   }

   public void setStyleMinHeightPercent(float var1) {
      this.mYogaNode.setMinHeightPercent(var1);
   }

   public void setStyleMinWidth(float var1) {
      this.mYogaNode.setMinWidth(var1);
   }

   public void setStyleMinWidthPercent(float var1) {
      this.mYogaNode.setMinWidthPercent(var1);
   }

   public void setStyleWidth(float var1) {
      this.mYogaNode.setWidth(var1);
   }

   public void setStyleWidthAuto() {
      this.mYogaNode.setWidthAuto();
   }

   public void setStyleWidthPercent(float var1) {
      this.mYogaNode.setWidthPercent(var1);
   }

   public void setThemedContext(ThemedReactContext var1) {
      this.mThemedContext = var1;
   }

   public final void setViewClassName(String var1) {
      this.mViewClassName = var1;
   }

   public final boolean shouldNotifyOnLayout() {
      return this.mShouldNotifyOnLayout;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      this.toStringWithIndentation(var1, 0);
      return var1.toString();
   }

   public final void updateProperties(ReactStylesDiffMap var1) {
      ViewManagerPropertyUpdater.updateProps(this, var1);
      this.onAfterUpdateTransaction();
   }
}
