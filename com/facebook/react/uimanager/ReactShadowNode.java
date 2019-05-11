package com.facebook.react.uimanager;

import com.facebook.react.uimanager.NativeViewHierarchyOptimizer;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaBaselineFunction;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaDisplay;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaOverflow;
import com.facebook.yoga.YogaPositionType;
import com.facebook.yoga.YogaValue;
import com.facebook.yoga.YogaWrap;
import javax.annotation.Nullable;

public interface ReactShadowNode<T extends Object & ReactShadowNode> {

   void addChildAt(T var1, int var2);

   void addNativeChildAt(T var1, int var2);

   void calculateLayout();

   void dirty();

   boolean dispatchUpdates(float var1, float var2, UIViewOperationQueue var3, NativeViewHierarchyOptimizer var4);

   void dispose();

   T getChildAt(int var1);

   int getChildCount();

   YogaDirection getLayoutDirection();

   float getLayoutHeight();

   float getLayoutWidth();

   float getLayoutX();

   float getLayoutY();

   int getNativeChildCount();

   int getNativeOffsetForChild(T var1);

   @Nullable
   T getNativeParent();

   float getPadding(int var1);

   @Nullable
   T getParent();

   int getReactTag();

   T getRootNode();

   int getScreenHeight();

   int getScreenWidth();

   int getScreenX();

   int getScreenY();

   YogaValue getStyleHeight();

   YogaValue getStylePadding(int var1);

   YogaValue getStyleWidth();

   ThemedReactContext getThemedContext();

   int getTotalNativeChildren();

   String getViewClass();

   boolean hasNewLayout();

   boolean hasUnseenUpdates();

   boolean hasUpdates();

   int indexOf(T var1);

   int indexOfNativeChild(T var1);

   boolean isDescendantOf(T var1);

   boolean isDirty();

   boolean isLayoutOnly();

   boolean isMeasureDefined();

   boolean isVirtual();

   boolean isVirtualAnchor();

   boolean isYogaLeafNode();

   void markLayoutSeen();

   void markUpdateSeen();

   void markUpdated();

   void onAfterUpdateTransaction();

   void onBeforeLayout();

   void onCollectExtraUpdates(UIViewOperationQueue var1);

   void removeAllNativeChildren();

   void removeAndDisposeAllChildren();

   T removeChildAt(int var1);

   T removeNativeChildAt(int var1);

   void setAlignContent(YogaAlign var1);

   void setAlignItems(YogaAlign var1);

   void setAlignSelf(YogaAlign var1);

   void setBaselineFunction(YogaBaselineFunction var1);

   void setBorder(int var1, float var2);

   void setDefaultPadding(int var1, float var2);

   void setDisplay(YogaDisplay var1);

   void setFlex(float var1);

   void setFlexBasis(float var1);

   void setFlexBasisAuto();

   void setFlexBasisPercent(float var1);

   void setFlexDirection(YogaFlexDirection var1);

   void setFlexGrow(float var1);

   void setFlexShrink(float var1);

   void setFlexWrap(YogaWrap var1);

   void setIsLayoutOnly(boolean var1);

   void setJustifyContent(YogaJustify var1);

   void setLayoutDirection(YogaDirection var1);

   void setLocalData(Object var1);

   void setMargin(int var1, float var2);

   void setMarginAuto(int var1);

   void setMarginPercent(int var1, float var2);

   void setMeasureFunction(YogaMeasureFunction var1);

   void setOverflow(YogaOverflow var1);

   void setPadding(int var1, float var2);

   void setPaddingPercent(int var1, float var2);

   void setPosition(int var1, float var2);

   void setPositionPercent(int var1, float var2);

   void setPositionType(YogaPositionType var1);

   void setReactTag(int var1);

   void setRootNode(T var1);

   void setShouldNotifyOnLayout(boolean var1);

   void setStyleAspectRatio(float var1);

   void setStyleHeight(float var1);

   void setStyleHeightAuto();

   void setStyleHeightPercent(float var1);

   void setStyleMaxHeight(float var1);

   void setStyleMaxHeightPercent(float var1);

   void setStyleMaxWidth(float var1);

   void setStyleMaxWidthPercent(float var1);

   void setStyleMinHeight(float var1);

   void setStyleMinHeightPercent(float var1);

   void setStyleMinWidth(float var1);

   void setStyleMinWidthPercent(float var1);

   void setStyleWidth(float var1);

   void setStyleWidthAuto();

   void setStyleWidthPercent(float var1);

   void setThemedContext(ThemedReactContext var1);

   void setViewClassName(String var1);

   boolean shouldNotifyOnLayout();

   void updateProperties(ReactStylesDiffMap var1);
}
