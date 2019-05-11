package com.facebook.litho;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentHost;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.LayoutOutput;
import com.facebook.litho.NodeInfo;
import com.facebook.litho.ViewNodeInfo;
import com.facebook.litho.config.ComponentsConfiguration;

class MountItem {

   private static final int FLAG_VIEW_CLICKABLE = 1;
   private static final int FLAG_VIEW_ENABLED = 8;
   private static final int FLAG_VIEW_FOCUSABLE = 4;
   private static final int FLAG_VIEW_LONG_CLICKABLE = 2;
   private static final int FLAG_VIEW_SELECTED = 16;
   static final int LAYOUT_FLAG_DISABLE_TOUCHABLE = 2;
   static final int LAYOUT_FLAG_DUPLICATE_PARENT_STATE = 1;
   static final int LAYOUT_FLAG_MATCH_HOST_BOUNDS = 4;
   private Object mBaseContent;
   private Component mComponent;
   private ComponentHost mHost;
   private int mImportantForAccessibility;
   private boolean mIsBound;
   private int mLayoutFlags;
   private int mMountViewFlags;
   private NodeInfo mNodeInfo;
   private int mOrientation;
   @Nullable
   private String mTransitionKey;
   private ViewNodeInfo mViewNodeInfo;
   private Object mWrappedContent;


   static boolean isDuplicateParentState(int var0) {
      return (var0 & 1) == 1;
   }

   static boolean isTouchableDisabled(int var0) {
      return (var0 & 2) == 2;
   }

   private void releaseNodeInfos() {
      if(this.mNodeInfo != null) {
         this.mNodeInfo.release();
         this.mNodeInfo = null;
      }

      if(this.mViewNodeInfo != null) {
         this.mViewNodeInfo.release();
         this.mViewNodeInfo = null;
      }

   }

   Object getBaseContent() {
      return this.mBaseContent;
   }

   @Nullable
   Component getComponent() {
      return this.mComponent;
   }

   ComponentHost getHost() {
      return this.mHost;
   }

   int getImportantForAccessibility() {
      return this.mImportantForAccessibility;
   }

   int getLayoutFlags() {
      return this.mLayoutFlags;
   }

   Object getMountableContent() {
      return this.mWrappedContent != null?this.mWrappedContent:this.mBaseContent;
   }

   NodeInfo getNodeInfo() {
      return this.mNodeInfo;
   }

   int getOrientation() {
      return this.mOrientation;
   }

   @Nullable
   String getTransitionKey() {
      return this.mTransitionKey;
   }

   ViewNodeInfo getViewNodeInfo() {
      return this.mViewNodeInfo;
   }

   boolean hasTransitionKey() {
      return this.mTransitionKey != null;
   }

   void init(Component var1, ComponentHost var2, Object var3, LayoutOutput var4) {
      this.init(var1, var2, var3, var4.getNodeInfo(), var4.getViewNodeInfo(), var4.getFlags(), var4.getImportantForAccessibility(), var4.getOrientation(), var4.getTransitionKey());
   }

   void init(Component var1, ComponentHost var2, Object var3, NodeInfo var4, ViewNodeInfo var5, int var6, int var7, int var8, String var9) {
      if(this.mHost != null) {
         throw new RuntimeException("Calling init() on a MountItem that has not been released!");
      } else if(var1 == null) {
         throw new RuntimeException("Calling init() on a MountItem with a null Component!");
      } else {
         this.mComponent = var1;
         this.mBaseContent = var3;
         this.mHost = var2;
         this.mLayoutFlags = var6;
         this.mImportantForAccessibility = var7;
         this.mOrientation = var8;
         this.mTransitionKey = var9;
         if(var4 != null) {
            this.mNodeInfo = var4.acquireRef();
         }

         if(var5 != null) {
            this.mViewNodeInfo = var5.acquireRef();
         }

         if(this.mBaseContent instanceof View) {
            View var10 = (View)this.mBaseContent;
            if(var10.isClickable()) {
               this.mMountViewFlags |= 1;
            }

            if(var10.isLongClickable()) {
               this.mMountViewFlags |= 2;
            }

            if(var10.isFocusable()) {
               this.mMountViewFlags |= 4;
            }

            if(var10.isEnabled()) {
               this.mMountViewFlags |= 8;
            }

            if(var10.isSelected()) {
               this.mMountViewFlags |= 16;
            }
         }

      }
   }

   boolean isAccessible() {
      Component var2 = this.mComponent;
      boolean var1 = false;
      if(var2 == null) {
         return false;
      } else if(this.mImportantForAccessibility == 2) {
         return false;
      } else {
         if(this.mNodeInfo != null && this.mNodeInfo.needsAccessibilityDelegate() || this.mComponent.implementsAccessibility()) {
            var1 = true;
         }

         return var1;
      }
   }

   boolean isBound() {
      return this.mIsBound;
   }

   boolean isViewClickable() {
      return (this.mMountViewFlags & 1) == 1;
   }

   boolean isViewEnabled() {
      return (this.mMountViewFlags & 8) == 8;
   }

   boolean isViewFocusable() {
      return (this.mMountViewFlags & 4) == 4;
   }

   boolean isViewLongClickable() {
      return (this.mMountViewFlags & 2) == 2;
   }

   boolean isViewSelected() {
      return (this.mMountViewFlags & 16) == 16;
   }

   void release(Context var1) {
      ComponentsPools.release(var1, this.mComponent, this.mBaseContent);
      if(!ComponentsConfiguration.disablePools) {
         this.releaseNodeInfos();
         this.mComponent = null;
         this.mHost = null;
         this.mBaseContent = null;
         this.mWrappedContent = null;
         this.mLayoutFlags = 0;
         this.mMountViewFlags = 0;
         this.mIsBound = false;
         this.mImportantForAccessibility = 0;
         this.mTransitionKey = null;
      }
   }

   void setHost(ComponentHost var1) {
      this.mHost = var1;
   }

   void setIsBound(boolean var1) {
      this.mIsBound = var1;
   }

   void setWrappedContent(Object var1) {
      this.mWrappedContent = var1;
   }

   void update(LayoutOutput var1) {
      this.mComponent = var1.getComponent();
      if(this.mComponent == null) {
         throw new RuntimeException("Trying to update a MountItem with a null Component!");
      } else {
         this.mLayoutFlags = var1.getFlags();
         this.mImportantForAccessibility = var1.getImportantForAccessibility();
         this.mOrientation = var1.getOrientation();
         this.mTransitionKey = var1.getTransitionKey();
         this.releaseNodeInfos();
         if(var1.getNodeInfo() != null) {
            this.mNodeInfo = var1.getNodeInfo().acquireRef();
         }

         if(var1.getViewNodeInfo() != null) {
            this.mViewNodeInfo = var1.getViewNodeInfo().acquireRef();
         }

      }
   }
}
