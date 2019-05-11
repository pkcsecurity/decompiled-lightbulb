package com.facebook.litho;

import android.graphics.drawable.Drawable;
import com.facebook.litho.EventHandler;
import com.facebook.litho.InternalNode;
import com.facebook.litho.NodeInfo;
import com.facebook.litho.reference.Reference;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaPositionType;
import com.facebook.yoga.YogaValue;
import javax.annotation.Nullable;

public final class DebugLayoutNode {

   private InternalNode mNode;


   DebugLayoutNode(InternalNode var1) {
      this.mNode = var1;
   }

   public YogaAlign getAlignContent() {
      return this.mNode.mYogaNode.getAlignContent();
   }

   public YogaAlign getAlignItems() {
      return this.mNode.mYogaNode.getAlignItems();
   }

   public YogaAlign getAlignSelf() {
      return this.mNode.mYogaNode.getAlignSelf();
   }

   public float getAspectRatio() {
      return this.mNode.mYogaNode.getAspectRatio();
   }

   @Nullable
   public Reference<? extends Drawable> getBackground() {
      return this.mNode.getBackground();
   }

   public float getBorderWidth(YogaEdge var1) {
      return this.mNode.mYogaNode.getBorder(var1);
   }

   @Nullable
   public EventHandler getClickHandler() {
      return this.mNode.getClickHandler();
   }

   @Nullable
   public CharSequence getContentDescription() {
      NodeInfo var1 = this.mNode.getNodeInfo();
      return var1 != null?var1.getContentDescription():null;
   }

   public YogaValue getFlexBasis() {
      return this.mNode.mYogaNode.getFlexBasis();
   }

   public YogaFlexDirection getFlexDirection() {
      return this.mNode.mYogaNode.getFlexDirection();
   }

   public float getFlexGrow() {
      return this.mNode.mYogaNode.getFlexGrow();
   }

   public float getFlexShrink() {
      return this.mNode.mYogaNode.getFlexShrink();
   }

   public boolean getFocusable() {
      NodeInfo var2 = this.mNode.getNodeInfo();
      boolean var1 = false;
      if(var2 != null) {
         if(var2.getFocusState() == 1) {
            var1 = true;
         }

         return var1;
      } else {
         return false;
      }
   }

   @Nullable
   public Drawable getForeground() {
      return this.mNode.getForeground();
   }

   public YogaValue getHeight() {
      return this.mNode.mYogaNode.getHeight();
   }

   @Nullable
   public Integer getImportantForAccessibility() {
      return Integer.valueOf(this.mNode.getImportantForAccessibility());
   }

   public YogaJustify getJustifyContent() {
      return this.mNode.mYogaNode.getJustifyContent();
   }

   public YogaDirection getLayoutDirection() {
      return this.mNode.mYogaNode.getLayoutDirection();
   }

   public YogaValue getMargin(YogaEdge var1) {
      return this.mNode.mYogaNode.getMargin(var1);
   }

   public YogaValue getMaxHeight() {
      return this.mNode.mYogaNode.getMaxHeight();
   }

   public YogaValue getMaxWidth() {
      return this.mNode.mYogaNode.getMaxWidth();
   }

   public YogaValue getMinHeight() {
      return this.mNode.mYogaNode.getMinHeight();
   }

   public YogaValue getMinWidth() {
      return this.mNode.mYogaNode.getMinWidth();
   }

   public YogaValue getPadding(YogaEdge var1) {
      return this.mNode.mYogaNode.getPadding(var1);
   }

   public YogaValue getPosition(YogaEdge var1) {
      return this.mNode.mYogaNode.getPosition(var1);
   }

   public YogaPositionType getPositionType() {
      return this.mNode.mYogaNode.getPositionType();
   }

   public float getResultMargin(YogaEdge var1) {
      return this.mNode.mYogaNode.getLayoutMargin(var1);
   }

   public float getResultPadding(YogaEdge var1) {
      return this.mNode.mYogaNode.getLayoutPadding(var1);
   }

   public YogaValue getWidth() {
      return this.mNode.mYogaNode.getWidth();
   }

   public void setAlignContent(YogaAlign var1) {
      this.mNode.alignContent(var1);
   }

   public void setAlignItems(YogaAlign var1) {
      this.mNode.alignItems(var1);
   }

   public void setAlignSelf(YogaAlign var1) {
      this.mNode.alignSelf(var1);
   }

   public void setAspectRatio(float var1) {
      this.mNode.aspectRatio(var1);
   }

   public void setBackgroundColor(int var1) {
      this.mNode.backgroundColor(var1);
   }

   public void setBorderWidth(YogaEdge var1, float var2) {
      this.mNode.setBorderWidth(var1, (int)var2);
   }

   public void setContentDescription(CharSequence var1) {
      this.mNode.contentDescription(var1);
   }

   public void setFlexBasis(YogaValue var1) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[var1.unit.ordinal()]) {
      case 1:
      case 2:
         this.mNode.flexBasisAuto();
         return;
      case 3:
         this.mNode.flexBasisPercent(var1.value);
         return;
      case 4:
         this.mNode.flexBasisPx((int)var1.value);
         return;
      default:
      }
   }

   public void setFlexDirection(YogaFlexDirection var1) {
      this.mNode.flexDirection(var1);
   }

   public void setFlexGrow(float var1) {
      this.mNode.flexGrow(var1);
   }

   public void setFlexShrink(float var1) {
      this.mNode.flexShrink(var1);
   }

   public void setFocusable(boolean var1) {
      this.mNode.focusable(var1);
   }

   public void setForegroundColor(int var1) {
      this.mNode.foregroundColor(var1);
   }

   public void setHeight(YogaValue var1) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[var1.unit.ordinal()]) {
      case 1:
      case 2:
         this.mNode.heightAuto();
         return;
      case 3:
         this.mNode.heightPercent(var1.value);
         return;
      case 4:
         this.mNode.heightPx((int)var1.value);
         return;
      default:
      }
   }

   public void setImportantForAccessibility(int var1) {
      this.mNode.importantForAccessibility(var1);
   }

   public void setJustifyContent(YogaJustify var1) {
      this.mNode.justifyContent(var1);
   }

   public void setLayoutDirection(YogaDirection var1) {
      this.mNode.layoutDirection(var1);
   }

   public void setMargin(YogaEdge var1, YogaValue var2) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[var2.unit.ordinal()]) {
      case 1:
         this.mNode.marginPx(var1, 0);
         return;
      case 2:
         this.mNode.marginAuto(var1);
         return;
      case 3:
         this.mNode.marginPercent(var1, var2.value);
         return;
      case 4:
         this.mNode.marginPx(var1, (int)var2.value);
         return;
      default:
      }
   }

   public void setMaxHeight(YogaValue var1) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[var1.unit.ordinal()]) {
      case 1:
      case 2:
         this.mNode.maxHeightPx(Integer.MAX_VALUE);
         return;
      case 3:
         this.mNode.maxHeightPercent(var1.value);
         return;
      case 4:
         this.mNode.maxHeightPx((int)var1.value);
         return;
      default:
      }
   }

   public void setMaxWidth(YogaValue var1) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[var1.unit.ordinal()]) {
      case 1:
      case 2:
         this.mNode.maxWidthPx(Integer.MAX_VALUE);
         return;
      case 3:
         this.mNode.maxWidthPercent(var1.value);
         return;
      case 4:
         this.mNode.maxWidthPx((int)var1.value);
         return;
      default:
      }
   }

   public void setMinHeight(YogaValue var1) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[var1.unit.ordinal()]) {
      case 1:
      case 2:
         this.mNode.minHeightPx(Integer.MIN_VALUE);
         return;
      case 3:
         this.mNode.minHeightPercent(var1.value);
         return;
      case 4:
         this.mNode.minHeightPx((int)var1.value);
         return;
      default:
      }
   }

   public void setMinWidth(YogaValue var1) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[var1.unit.ordinal()]) {
      case 1:
      case 2:
         this.mNode.minWidthPx(Integer.MIN_VALUE);
         return;
      case 3:
         this.mNode.minWidthPercent(var1.value);
         return;
      case 4:
         this.mNode.minWidthPx((int)var1.value);
         return;
      default:
      }
   }

   public void setPadding(YogaEdge var1, YogaValue var2) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[var2.unit.ordinal()]) {
      case 1:
      case 2:
         this.mNode.paddingPx(var1, 0);
         return;
      case 3:
         this.mNode.paddingPercent(var1, var2.value);
         return;
      case 4:
         this.mNode.paddingPx(var1, (int)var2.value);
         return;
      default:
      }
   }

   public void setPosition(YogaEdge var1, YogaValue var2) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[var2.unit.ordinal()]) {
      case 1:
      case 2:
         this.mNode.positionPercent(var1, Float.NaN);
         return;
      case 3:
         this.mNode.positionPercent(var1, var2.value);
         return;
      case 4:
         this.mNode.positionPx(var1, (int)var2.value);
         return;
      default:
      }
   }

   public void setPositionType(YogaPositionType var1) {
      this.mNode.positionType(var1);
   }

   public void setWidth(YogaValue var1) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[var1.unit.ordinal()]) {
      case 1:
      case 2:
         this.mNode.widthAuto();
         return;
      case 3:
         this.mNode.widthPercent(var1.value);
         return;
      case 4:
         this.mNode.widthPx((int)var1.value);
         return;
      default:
      }
   }
}
