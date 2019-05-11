package com.facebook.react.flat;


class NodeRegion {

   static final NodeRegion EMPTY = new NodeRegion(0.0F, 0.0F, 0.0F, 0.0F, -1, false);
   static final NodeRegion[] EMPTY_ARRAY = new NodeRegion[0];
   private final float mBottom;
   final boolean mIsVirtual;
   private final float mLeft;
   private final float mRight;
   final int mTag;
   private final float mTop;


   NodeRegion(float var1, float var2, float var3, float var4, int var5, boolean var6) {
      this.mLeft = var1;
      this.mTop = var2;
      this.mRight = var3;
      this.mBottom = var4;
      this.mTag = var5;
      this.mIsVirtual = var6;
   }

   final float getBottom() {
      return this.mBottom;
   }

   final float getLeft() {
      return this.mLeft;
   }

   int getReactTag(float var1, float var2) {
      return this.mTag;
   }

   final float getRight() {
      return this.mRight;
   }

   final float getTop() {
      return this.mTop;
   }

   float getTouchableBottom() {
      return this.getBottom();
   }

   float getTouchableLeft() {
      return this.getLeft();
   }

   float getTouchableRight() {
      return this.getRight();
   }

   float getTouchableTop() {
      return this.getTop();
   }

   final boolean matches(float var1, float var2, float var3, float var4, boolean var5) {
      return var1 == this.mLeft && var2 == this.mTop && var3 == this.mRight && var4 == this.mBottom && var5 == this.mIsVirtual;
   }

   boolean matchesTag(int var1) {
      return this.mTag == var1;
   }

   boolean withinBounds(float var1, float var2) {
      return this.mLeft <= var1 && var1 < this.mRight && this.mTop <= var2 && var2 < this.mBottom;
   }
}
