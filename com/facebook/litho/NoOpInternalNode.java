package com.facebook.litho;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.util.SparseArray;
import com.facebook.litho.Border;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.DiffNode;
import com.facebook.litho.EventHandler;
import com.facebook.litho.FocusChangedEvent;
import com.facebook.litho.FocusedVisibleEvent;
import com.facebook.litho.FullImpressionVisibleEvent;
import com.facebook.litho.InternalNode;
import com.facebook.litho.InvisibleEvent;
import com.facebook.litho.TouchEvent;
import com.facebook.litho.UnfocusedVisibleEvent;
import com.facebook.litho.VisibleEvent;
import com.facebook.litho.drawable.ComparableDrawable;
import com.facebook.litho.reference.Reference;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaBaselineFunction;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.YogaPositionType;
import com.facebook.yoga.YogaWrap;

class NoOpInternalNode extends InternalNode {

   public InternalNode alignContent(YogaAlign var1) {
      return this;
   }

   public InternalNode alignItems(YogaAlign var1) {
      return this;
   }

   public InternalNode alignSelf(YogaAlign var1) {
      return this;
   }

   void appendComponent(Component var1) {}

   void applyAttributes(TypedArray var1) {}

   InternalNode background(@Nullable ComparableDrawable var1) {
      return this;
   }

   public InternalNode background(Reference<? extends Drawable> var1) {
      return this;
   }

   public InternalNode border(Border var1) {
      return this;
   }

   public InternalNode child(Component.Builder<?> var1) {
      return this;
   }

   public InternalNode child(Component var1) {
      return this;
   }

   public InternalNode clickHandler(EventHandler<ClickEvent> var1) {
      return this;
   }

   public InternalNode contentDescription(CharSequence var1) {
      return this;
   }

   void copyInto(InternalNode var1) {}

   public InternalNode duplicateParentState(boolean var1) {
      return this;
   }

   public InternalNode flex(float var1) {
      return this;
   }

   public InternalNode flexBasisPercent(float var1) {
      return this;
   }

   public InternalNode flexBasisPx(@Px int var1) {
      return this;
   }

   public InternalNode flexDirection(YogaFlexDirection var1) {
      return this;
   }

   public InternalNode flexGrow(float var1) {
      return this;
   }

   public InternalNode flexShrink(float var1) {
      return this;
   }

   public InternalNode focusChangeHandler(EventHandler<FocusChangedEvent> var1) {
      return this;
   }

   public InternalNode focusedHandler(EventHandler<FocusedVisibleEvent> var1) {
      return this;
   }

   public InternalNode foreground(@Nullable Drawable var1) {
      return this;
   }

   public InternalNode foreground(@Nullable ComparableDrawable var1) {
      return this;
   }

   public InternalNode fullImpressionHandler(EventHandler<FullImpressionVisibleEvent> var1) {
      return this;
   }

   @Px
   public int getHeight() {
      return 0;
   }

   public int getLastHeightSpec() {
      return 0;
   }

   public int getLastWidthSpec() {
      return 0;
   }

   @Px
   public int getPaddingBottom() {
      return 0;
   }

   @Px
   public int getPaddingLeft() {
      return 0;
   }

   @Px
   public int getPaddingRight() {
      return 0;
   }

   @Px
   public int getPaddingTop() {
      return 0;
   }

   @Px
   public int getWidth() {
      return 0;
   }

   @Px
   public int getX() {
      return 0;
   }

   @Px
   public int getY() {
      return 0;
   }

   public InternalNode heightPercent(float var1) {
      return this;
   }

   public InternalNode heightPx(@Px int var1) {
      return this;
   }

   public InternalNode importantForAccessibility(int var1) {
      return this;
   }

   void init(YogaNode var1, ComponentContext var2) {}

   public InternalNode invisibleHandler(EventHandler<InvisibleEvent> var1) {
      return this;
   }

   public InternalNode justifyContent(YogaJustify var1) {
      return this;
   }

   public InternalNode layoutDirection(YogaDirection var1) {
      return this;
   }

   public InternalNode marginAuto(YogaEdge var1) {
      return this;
   }

   public InternalNode marginPercent(YogaEdge var1, float var2) {
      return this;
   }

   public InternalNode marginPx(YogaEdge var1, @Px int var2) {
      return this;
   }

   public InternalNode maxHeightPercent(float var1) {
      return this;
   }

   public InternalNode maxHeightPx(@Px int var1) {
      return this;
   }

   public InternalNode maxWidthPercent(float var1) {
      return this;
   }

   public InternalNode maxWidthPx(@Px int var1) {
      return this;
   }

   public InternalNode minHeightPercent(float var1) {
      return this;
   }

   public InternalNode minHeightPx(@Px int var1) {
      return this;
   }

   public InternalNode minWidthPercent(float var1) {
      return this;
   }

   public InternalNode minWidthPx(@Px int var1) {
      return this;
   }

   public InternalNode paddingPercent(YogaEdge var1, float var2) {
      return this;
   }

   public InternalNode paddingPx(YogaEdge var1, @Px int var2) {
      return this;
   }

   public InternalNode positionPercent(YogaEdge var1, float var2) {
      return this;
   }

   public InternalNode positionPx(YogaEdge var1, @Px int var2) {
      return this;
   }

   public InternalNode positionType(YogaPositionType var1) {
      return this;
   }

   void setBaselineFunction(YogaBaselineFunction var1) {}

   public void setCachedMeasuresValid(boolean var1) {}

   void setDiffNode(DiffNode var1) {}

   public void setLastHeightSpec(int var1) {}

   void setLastMeasuredHeight(float var1) {}

   void setLastMeasuredWidth(float var1) {}

   public void setLastWidthSpec(int var1) {}

   void setNestedTree(InternalNode var1) {}

   void setStyleHeightFromSpec(int var1) {}

   void setStyleWidthFromSpec(int var1) {}

   public InternalNode touchHandler(EventHandler<TouchEvent> var1) {
      return this;
   }

   public InternalNode transitionKey(String var1) {
      return this;
   }

   public InternalNode unfocusedHandler(EventHandler<UnfocusedVisibleEvent> var1) {
      return this;
   }

   public InternalNode viewTag(Object var1) {
      return this;
   }

   public InternalNode viewTags(SparseArray<Object> var1) {
      return this;
   }

   public InternalNode visibleHandler(EventHandler<VisibleEvent> var1) {
      return this;
   }

   public InternalNode widthPercent(float var1) {
      return this;
   }

   public InternalNode widthPx(@Px int var1) {
      return this;
   }

   public InternalNode wrap(YogaWrap var1) {
      return this;
   }

   public InternalNode wrapInView() {
      return this;
   }
}
