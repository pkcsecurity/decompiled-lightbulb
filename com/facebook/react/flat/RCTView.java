package com.facebook.react.flat;

import android.graphics.Rect;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.flat.DrawBorder;
import com.facebook.react.flat.FlatShadowNode;
import com.facebook.react.flat.HitSlopNodeRegion;
import com.facebook.react.flat.NodeRegion;
import com.facebook.react.flat.StateBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import javax.annotation.Nullable;

final class RCTView extends FlatShadowNode {

   private static final int[] SPACING_TYPES = new int[]{8, 0, 2, 1, 3};
   @Nullable
   private DrawBorder mDrawBorder;
   @Nullable
   private Rect mHitSlop;
   boolean mHorizontal;
   boolean mRemoveClippedSubviews;


   private DrawBorder getMutableBorder() {
      if(this.mDrawBorder == null) {
         this.mDrawBorder = new DrawBorder();
      } else if(this.mDrawBorder.isFrozen()) {
         this.mDrawBorder = (DrawBorder)this.mDrawBorder.mutableCopy();
      }

      this.invalidate();
      return this.mDrawBorder;
   }

   public boolean clipsSubviews() {
      return this.mRemoveClippedSubviews;
   }

   protected void collectState(StateBuilder var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      super.collectState(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      if(this.mDrawBorder != null) {
         this.mDrawBorder = (DrawBorder)this.mDrawBorder.updateBoundsAndFreeze(var2, var3, var4, var5, var6, var7, var8, var9);
         var1.addDrawCommand(this.mDrawBorder);
      }

   }

   boolean doesDraw() {
      return this.mDrawBorder != null || super.doesDraw();
   }

   void handleUpdateProperties(ReactStylesDiffMap var1) {
      boolean var2 = this.mRemoveClippedSubviews;
      boolean var3 = true;
      if(!var2 && (!var1.hasKey("removeClippedSubviews") || !var1.getBoolean("removeClippedSubviews", false))) {
         var2 = false;
      } else {
         var2 = true;
      }

      this.mRemoveClippedSubviews = var2;
      if(this.mRemoveClippedSubviews) {
         var2 = var3;
         if(!this.mHorizontal) {
            if(var1.hasKey("horizontal") && var1.getBoolean("horizontal", false)) {
               var2 = var3;
            } else {
               var2 = false;
            }
         }

         this.mHorizontal = var2;
      }

      super.handleUpdateProperties(var1);
   }

   public void setBackgroundColor(int var1) {
      this.getMutableBorder().setBackgroundColor(var1);
   }

   @ReactPropGroup(
      customType = "Color",
      defaultDouble = Double.NaN,
      names = {"borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor"}
   )
   public void setBorderColor(int var1, double var2) {
      var1 = SPACING_TYPES[var1];
      if(Double.isNaN(var2)) {
         this.getMutableBorder().resetBorderColor(var1);
      } else {
         this.getMutableBorder().setBorderColor(var1, (int)var2);
      }
   }

   @ReactProp(
      name = "borderRadius"
   )
   public void setBorderRadius(float var1) {
      this.mClipRadius = var1;
      if(this.mClipToBounds && var1 > 0.5F) {
         this.forceMountToView();
      }

      this.getMutableBorder().setBorderRadius(PixelUtil.toPixelFromDIP(var1));
   }

   @ReactProp(
      name = "borderStyle"
   )
   public void setBorderStyle(@Nullable String var1) {
      this.getMutableBorder().setBorderStyle(var1);
   }

   public void setBorderWidths(int var1, float var2) {
      super.setBorderWidths(var1, var2);
      var1 = SPACING_TYPES[var1];
      this.getMutableBorder().setBorderWidth(var1, PixelUtil.toPixelFromDIP(var2));
   }

   @ReactProp(
      name = "hitSlop"
   )
   public void setHitSlop(@Nullable ReadableMap var1) {
      if(var1 == null) {
         this.mHitSlop = null;
      } else {
         this.mHitSlop = new Rect((int)PixelUtil.toPixelFromDIP(var1.getDouble("left")), (int)PixelUtil.toPixelFromDIP(var1.getDouble("top")), (int)PixelUtil.toPixelFromDIP(var1.getDouble("right")), (int)PixelUtil.toPixelFromDIP(var1.getDouble("bottom")));
      }
   }

   @ReactProp(
      name = "nativeBackgroundAndroid"
   )
   public void setHotspot(@Nullable ReadableMap var1) {
      if(var1 != null) {
         this.forceMountToView();
      }

   }

   @ReactProp(
      name = "pointerEvents"
   )
   public void setPointerEvents(@Nullable String var1) {
      this.forceMountToView();
   }

   void updateNodeRegion(float var1, float var2, float var3, float var4, boolean var5) {
      if(!this.getNodeRegion().matches(var1, var2, var3, var4, var5)) {
         Object var6;
         if(this.mHitSlop == null) {
            var6 = new NodeRegion(var1, var2, var3, var4, this.getReactTag(), var5);
         } else {
            var6 = new HitSlopNodeRegion(this.mHitSlop, var1, var2, var3, var4, this.getReactTag(), var5);
         }

         this.setNodeRegion((NodeRegion)var6);
      }

   }
}
