package com.facebook.litho;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.LayoutOutput;
import com.facebook.litho.VisibilityOutput;
import com.facebook.litho.config.ComponentsConfiguration;
import java.util.ArrayList;
import java.util.List;

class DiffNode implements Cloneable {

   static final int UNSPECIFIED = -1;
   private LayoutOutput mBackground;
   private LayoutOutput mBorder;
   private final List<DiffNode> mChildren = new ArrayList(4);
   private Component mComponent;
   private LayoutOutput mContent;
   private LayoutOutput mForeground;
   private LayoutOutput mHost;
   private int mLastHeightSpec;
   private float mLastMeasuredHeight;
   private float mLastMeasuredWidth;
   private int mLastWidthSpec;
   private VisibilityOutput mVisibilityOutput;


   void addChild(DiffNode var1) {
      this.mChildren.add(var1);
   }

   LayoutOutput getBackground() {
      return this.mBackground;
   }

   LayoutOutput getBorder() {
      return this.mBorder;
   }

   DiffNode getChildAt(int var1) {
      return (DiffNode)this.mChildren.get(var1);
   }

   int getChildCount() {
      return this.mChildren == null?0:this.mChildren.size();
   }

   List<DiffNode> getChildren() {
      return this.mChildren;
   }

   Component getComponent() {
      return this.mComponent;
   }

   LayoutOutput getContent() {
      return this.mContent;
   }

   LayoutOutput getForeground() {
      return this.mForeground;
   }

   LayoutOutput getHost() {
      return this.mHost;
   }

   int getLastHeightSpec() {
      return this.mLastHeightSpec;
   }

   float getLastMeasuredHeight() {
      return this.mLastMeasuredHeight;
   }

   float getLastMeasuredWidth() {
      return this.mLastMeasuredWidth;
   }

   int getLastWidthSpec() {
      return this.mLastWidthSpec;
   }

   VisibilityOutput getVisibilityOutput() {
      return this.mVisibilityOutput;
   }

   void release() {
      if(!ComponentsConfiguration.disablePools) {
         this.mComponent = null;
         this.mContent = null;
         this.mBackground = null;
         this.mForeground = null;
         this.mBorder = null;
         this.mHost = null;
         this.mVisibilityOutput = null;
         this.mLastMeasuredWidth = -1.0F;
         this.mLastMeasuredHeight = -1.0F;
         this.mLastWidthSpec = -1;
         this.mLastHeightSpec = -1;
         int var1 = 0;

         for(int var2 = this.mChildren.size(); var1 < var2; ++var1) {
            ComponentsPools.release((DiffNode)this.mChildren.get(var1));
         }

         this.mChildren.clear();
      }
   }

   void setBackground(LayoutOutput var1) {
      this.mBackground = var1;
   }

   void setBorder(LayoutOutput var1) {
      this.mBorder = var1;
   }

   void setComponent(Component var1) {
      this.mComponent = var1;
   }

   void setContent(LayoutOutput var1) {
      this.mContent = var1;
   }

   void setForeground(LayoutOutput var1) {
      this.mForeground = var1;
   }

   void setHost(LayoutOutput var1) {
      this.mHost = var1;
   }

   void setLastHeightSpec(int var1) {
      this.mLastHeightSpec = var1;
   }

   void setLastMeasuredHeight(float var1) {
      this.mLastMeasuredHeight = var1;
   }

   void setLastMeasuredWidth(float var1) {
      this.mLastMeasuredWidth = var1;
   }

   void setLastWidthSpec(int var1) {
      this.mLastWidthSpec = var1;
   }

   void setVisibilityOutput(VisibilityOutput var1) {
      this.mVisibilityOutput = var1;
   }
}
