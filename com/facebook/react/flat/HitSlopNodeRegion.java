package com.facebook.react.flat;

import android.graphics.Rect;
import com.facebook.react.flat.NodeRegion;

final class HitSlopNodeRegion extends NodeRegion {

   private final Rect mHitSlop;


   HitSlopNodeRegion(Rect var1, float var2, float var3, float var4, float var5, int var6, boolean var7) {
      super(var2, var3, var4, var5, var6, var7);
      this.mHitSlop = var1;
   }

   float getTouchableBottom() {
      return this.getBottom() + (float)this.mHitSlop.bottom;
   }

   float getTouchableLeft() {
      return this.getLeft() - (float)this.mHitSlop.left;
   }

   float getTouchableRight() {
      return this.getRight() + (float)this.mHitSlop.right;
   }

   float getTouchableTop() {
      return this.getTop() - (float)this.mHitSlop.top;
   }

   boolean withinBounds(float var1, float var2) {
      return this.getTouchableLeft() <= var1 && var1 < this.getTouchableRight() && this.getTouchableTop() <= var2 && var2 < this.getTouchableBottom();
   }
}
