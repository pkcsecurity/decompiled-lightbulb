package com.facebook.react.flat;

import android.graphics.Rect;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.flat.AttachDetachListener;
import com.facebook.react.flat.DrawBackgroundColor;
import com.facebook.react.flat.DrawCommand;
import com.facebook.react.flat.DrawView;
import com.facebook.react.flat.NodeRegion;
import com.facebook.react.flat.StateBuilder;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.OnLayoutEvent;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.annotations.ReactProp;
import javax.annotation.Nullable;

class FlatShadowNode extends LayoutShadowNode {

   static final FlatShadowNode[] EMPTY_ARRAY = new FlatShadowNode[0];
   private static final DrawView EMPTY_DRAW_VIEW = new DrawView(0);
   private static final Rect LOGICAL_OFFSET_EMPTY = new Rect();
   private static final String PROP_ACCESSIBILITY_COMPONENT_TYPE = "accessibilityComponentType";
   private static final String PROP_ACCESSIBILITY_LABEL = "accessibilityLabel";
   private static final String PROP_ACCESSIBILITY_LIVE_REGION = "accessibilityLiveRegion";
   protected static final String PROP_HORIZONTAL = "horizontal";
   private static final String PROP_IMPORTANT_FOR_ACCESSIBILITY = "importantForAccessibility";
   private static final String PROP_OPACITY = "opacity";
   protected static final String PROP_REMOVE_CLIPPED_SUBVIEWS = "removeClippedSubviews";
   private static final String PROP_RENDER_TO_HARDWARE_TEXTURE = "renderToHardwareTextureAndroid";
   private static final String PROP_TEST_ID = "testID";
   private static final String PROP_TRANSFORM = "transform";
   private AttachDetachListener[] mAttachDetachListeners;
   private boolean mBackingViewIsCreated;
   private float mClipBottom;
   private float mClipLeft;
   float mClipRadius;
   private float mClipRight;
   boolean mClipToBounds;
   private float mClipTop;
   @Nullable
   private DrawBackgroundColor mDrawBackground;
   private DrawCommand[] mDrawCommands;
   @Nullable
   private DrawView mDrawView;
   private boolean mForceMountChildrenToView;
   private boolean mIsUpdated;
   private int mLayoutHeight;
   private int mLayoutWidth;
   private int mLayoutX;
   private int mLayoutY;
   private Rect mLogicalOffset;
   private FlatShadowNode[] mNativeChildren;
   private int mNativeParentTag;
   private NodeRegion mNodeRegion;
   private NodeRegion[] mNodeRegions;
   private boolean mOverflowsContainer;
   private int mViewBottom;
   private int mViewLeft;
   private int mViewRight;
   private int mViewTop;


   FlatShadowNode() {
      this.mDrawCommands = DrawCommand.EMPTY_ARRAY;
      this.mAttachDetachListeners = AttachDetachListener.EMPTY_ARRAY;
      this.mNodeRegions = NodeRegion.EMPTY_ARRAY;
      this.mNativeChildren = EMPTY_ARRAY;
      this.mNodeRegion = NodeRegion.EMPTY;
      this.mIsUpdated = true;
      this.mLogicalOffset = LOGICAL_OFFSET_EMPTY;
      this.mClipToBounds = false;
   }

   public void addChildAt(ReactShadowNodeImpl var1, int var2) {
      super.addChildAt(var1, var2);
      if(this.mForceMountChildrenToView && var1 instanceof FlatShadowNode) {
         ((FlatShadowNode)var1).forceMountToView();
      }

   }

   final boolean clipBoundsChanged(float var1, float var2, float var3, float var4) {
      return this.mClipLeft != var1 || this.mClipTop != var2 || this.mClipRight != var3 || this.mClipBottom != var4;
   }

   public final boolean clipToBounds() {
      return this.mClipToBounds;
   }

   public boolean clipsSubviews() {
      return false;
   }

   final DrawView collectDrawView(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      Assertions.assumeNotNull(this.mDrawView);
      if(this.mDrawView == EMPTY_DRAW_VIEW) {
         this.mDrawView = new DrawView(this.getReactTag());
      }

      float var9;
      if(this.mClipToBounds) {
         var9 = this.mClipRadius;
      } else {
         var9 = 0.0F;
      }

      this.mDrawView = this.mDrawView.collectDrawView(var1, var2, var3, var4, var1 + (float)this.mLogicalOffset.left, var2 + (float)this.mLogicalOffset.top, var3 + (float)this.mLogicalOffset.right, var4 + (float)this.mLogicalOffset.bottom, var5, var6, var7, var8, var9);
      return this.mDrawView;
   }

   protected void collectState(StateBuilder var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      if(this.mDrawBackground != null) {
         this.mDrawBackground = (DrawBackgroundColor)this.mDrawBackground.updateBoundsAndFreeze(var2, var3, var4, var5, var6, var7, var8, var9);
         var1.addDrawCommand(this.mDrawBackground);
      }

   }

   boolean doesDraw() {
      return this.mDrawView != null || this.mDrawBackground != null;
   }

   final void forceMountChildrenToView() {
      if(!this.mForceMountChildrenToView) {
         this.mForceMountChildrenToView = true;
         int var1 = 0;

         for(int var2 = this.getChildCount(); var1 != var2; ++var1) {
            ReactShadowNodeImpl var3 = this.getChildAt(var1);
            if(var3 instanceof FlatShadowNode) {
               ((FlatShadowNode)var3).forceMountToView();
            }
         }

      }
   }

   final void forceMountToView() {
      if(!this.isVirtual()) {
         if(this.mDrawView == null) {
            this.mDrawView = EMPTY_DRAW_VIEW;
            this.invalidate();
            this.mNodeRegion = NodeRegion.EMPTY;
         }

      }
   }

   final AttachDetachListener[] getAttachDetachListeners() {
      return this.mAttachDetachListeners;
   }

   final DrawCommand[] getDrawCommands() {
      return this.mDrawCommands;
   }

   final FlatShadowNode[] getNativeChildren() {
      return this.mNativeChildren;
   }

   final int getNativeParentTag() {
      return this.mNativeParentTag;
   }

   final NodeRegion getNodeRegion() {
      return this.mNodeRegion;
   }

   final NodeRegion[] getNodeRegions() {
      return this.mNodeRegions;
   }

   public final int getScreenHeight() {
      return this.mountsToView()?this.mViewBottom - this.mViewTop:Math.round(this.mNodeRegion.getBottom() - this.mNodeRegion.getTop());
   }

   public final int getScreenWidth() {
      return this.mountsToView()?this.mViewRight - this.mViewLeft:Math.round(this.mNodeRegion.getRight() - this.mNodeRegion.getLeft());
   }

   public final int getScreenX() {
      return this.mViewLeft;
   }

   public final int getScreenY() {
      return this.mViewTop;
   }

   final int getViewBottom() {
      return this.mViewBottom;
   }

   final int getViewLeft() {
      return this.mViewLeft;
   }

   final int getViewRight() {
      return this.mViewRight;
   }

   final int getViewTop() {
      return this.mViewTop;
   }

   void handleUpdateProperties(ReactStylesDiffMap var1) {
      if(!this.mountsToView() && (var1.hasKey("opacity") || var1.hasKey("renderToHardwareTextureAndroid") || var1.hasKey("testID") || var1.hasKey("accessibilityLabel") || var1.hasKey("accessibilityComponentType") || var1.hasKey("accessibilityLiveRegion") || var1.hasKey("transform") || var1.hasKey("importantForAccessibility") || var1.hasKey("removeClippedSubviews"))) {
         this.forceMountToView();
      }

   }

   protected final void invalidate() {
      FlatShadowNode var1 = this;

      while(true) {
         if(var1.mountsToView()) {
            if(var1.mIsUpdated) {
               return;
            }

            var1.mIsUpdated = true;
         }

         ReactShadowNodeImpl var2 = var1.getParent();
         if(var2 == null) {
            return;
         }

         var1 = (FlatShadowNode)var2;
      }
   }

   final boolean isBackingViewCreated() {
      return this.mBackingViewIsCreated;
   }

   public boolean isHorizontal() {
      return false;
   }

   final boolean isUpdated() {
      return this.mIsUpdated;
   }

   public void markUpdated() {
      super.markUpdated();
      this.mIsUpdated = true;
      this.invalidate();
   }

   final boolean mountsToView() {
      return this.mDrawView != null;
   }

   @Nullable
   final OnLayoutEvent obtainLayoutEvent(int var1, int var2, int var3, int var4) {
      if(this.mLayoutX == var1 && this.mLayoutY == var2 && this.mLayoutWidth == var3 && this.mLayoutHeight == var4) {
         return null;
      } else {
         this.mLayoutX = var1;
         this.mLayoutY = var2;
         this.mLayoutWidth = var3;
         this.mLayoutHeight = var4;
         return OnLayoutEvent.obtain(this.getReactTag(), var1, var2, var3, var4);
      }
   }

   final void resetUpdated() {
      this.mIsUpdated = false;
   }

   final void setAttachDetachListeners(AttachDetachListener[] var1) {
      this.mAttachDetachListeners = var1;
   }

   @ReactProp(
      name = "backgroundColor"
   )
   public void setBackgroundColor(int var1) {
      DrawBackgroundColor var2;
      if(var1 == 0) {
         var2 = null;
      } else {
         var2 = new DrawBackgroundColor(var1);
      }

      this.mDrawBackground = var2;
      this.invalidate();
   }

   final void setClipBounds(float var1, float var2, float var3, float var4) {
      this.mClipLeft = var1;
      this.mClipTop = var2;
      this.mClipRight = var3;
      this.mClipBottom = var4;
   }

   final void setDrawCommands(DrawCommand[] var1) {
      this.mDrawCommands = var1;
   }

   final void setNativeChildren(FlatShadowNode[] var1) {
      this.mNativeChildren = var1;
   }

   final void setNativeParentTag(int var1) {
      this.mNativeParentTag = var1;
   }

   protected final void setNodeRegion(NodeRegion var1) {
      this.mNodeRegion = var1;
      this.updateOverflowsContainer();
   }

   final void setNodeRegions(NodeRegion[] var1) {
      this.mNodeRegions = var1;
      this.updateOverflowsContainer();
   }

   public void setOverflow(String var1) {
      super.setOverflow(var1);
      this.mClipToBounds = "hidden".equals(var1);
      if(this.mClipToBounds) {
         this.mOverflowsContainer = false;
         if(this.mClipRadius > 0.5F) {
            this.forceMountToView();
         }
      } else {
         this.updateOverflowsContainer();
      }

      this.invalidate();
   }

   final void setViewBounds(int var1, int var2, int var3, int var4) {
      this.mViewLeft = var1;
      this.mViewTop = var2;
      this.mViewRight = var3;
      this.mViewBottom = var4;
   }

   final void signalBackingViewIsCreated() {
      this.mBackingViewIsCreated = true;
   }

   void updateNodeRegion(float var1, float var2, float var3, float var4, boolean var5) {
      if(!this.mNodeRegion.matches(var1, var2, var3, var4, var5)) {
         this.setNodeRegion(new NodeRegion(var1, var2, var3, var4, this.getReactTag(), var5));
      }

   }

   final void updateOverflowsContainer() {
      int var11 = (int)(this.mNodeRegion.getRight() - this.mNodeRegion.getLeft());
      int var13 = (int)(this.mNodeRegion.getBottom() - this.mNodeRegion.getTop());
      float var9 = (float)var11;
      float var10 = (float)var13;
      boolean var14 = this.mClipToBounds;
      byte var12 = 0;
      Rect var16 = null;
      boolean var15;
      if(!var14 && var13 > 0 && var11 > 0) {
         NodeRegion[] var17 = this.mNodeRegions;
         var13 = var17.length;
         float var4 = var9;
         float var1 = var10;
         var11 = 0;
         float var7 = 0.0F;
         var15 = false;

         float var2;
         float var8;
         for(var2 = 0.0F; var11 < var13; var1 = var8) {
            NodeRegion var18 = var17[var11];
            float var3 = var7;
            if(var18.getLeft() < var7) {
               var3 = var18.getLeft();
               var15 = true;
            }

            float var5 = var4;
            if(var18.getRight() > var4) {
               var5 = var18.getRight();
               var15 = true;
            }

            float var6 = var2;
            if(var18.getTop() < var2) {
               var6 = var18.getTop();
               var15 = true;
            }

            var8 = var1;
            if(var18.getBottom() > var1) {
               var8 = var18.getBottom();
               var15 = true;
            }

            ++var11;
            var7 = var3;
            var4 = var5;
            var2 = var6;
         }

         var14 = var15;
         if(var15) {
            var16 = new Rect((int)var7, (int)var2, (int)(var4 - var9), (int)(var1 - var10));
            var14 = var15;
         }
      } else {
         var14 = false;
      }

      Rect var19 = var16;
      var15 = var14;
      if(!var14) {
         var19 = var16;
         var15 = var14;
         if(this.mNodeRegion != NodeRegion.EMPTY) {
            var13 = this.getChildCount();
            var11 = var12;

            while(true) {
               var19 = var16;
               var15 = var14;
               if(var11 >= var13) {
                  break;
               }

               ReactShadowNodeImpl var20 = this.getChildAt(var11);
               var19 = var16;
               var15 = var14;
               if(var20 instanceof FlatShadowNode) {
                  FlatShadowNode var21 = (FlatShadowNode)var20;
                  var19 = var16;
                  var15 = var14;
                  if(var21.mOverflowsContainer) {
                     Rect var22 = var21.mLogicalOffset;
                     var19 = var16;
                     if(var16 == null) {
                        var19 = new Rect();
                     }

                     var19.union(var22);
                     var15 = true;
                  }
               }

               ++var11;
               var16 = var19;
               var14 = var15;
            }
         }
      }

      if(this.mOverflowsContainer != var15) {
         this.mOverflowsContainer = var15;
         var16 = var19;
         if(var19 == null) {
            var16 = LOGICAL_OFFSET_EMPTY;
         }

         this.mLogicalOffset = var16;
      }

   }
}
