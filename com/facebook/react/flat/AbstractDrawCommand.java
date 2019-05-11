package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import com.facebook.react.flat.DrawCommand;
import com.facebook.react.flat.FlatViewGroup;

abstract class AbstractDrawCommand extends DrawCommand implements Cloneable {

   private static Paint sDebugHighlightOverlayText;
   private static Paint sDebugHighlightRed;
   private static Paint sDebugHighlightYellow;
   private float mBottom;
   private float mClipBottom;
   private float mClipLeft;
   private float mClipRight;
   private float mClipTop;
   private boolean mFrozen;
   private float mLeft;
   protected boolean mNeedsClipping;
   private float mRight;
   private float mTop;


   private void debugDrawHighlightRect(Canvas var1, Paint var2, String var3) {
      var1.drawRect(this.getLeft(), this.getTop(), this.getRight(), this.getBottom(), var2);
      var1.drawText(var3, this.getRight() - 5.0F, this.getBottom() - 5.0F, sDebugHighlightOverlayText);
   }

   protected static int getDebugBorderColor() {
      return -16711681;
   }

   private void initDebugHighlightResources(FlatViewGroup var1) {
      if(sDebugHighlightRed == null) {
         sDebugHighlightRed = new Paint();
         sDebugHighlightRed.setARGB(75, 255, 0, 0);
      }

      if(sDebugHighlightYellow == null) {
         sDebugHighlightYellow = new Paint();
         sDebugHighlightYellow.setARGB(100, 255, 204, 0);
      }

      if(sDebugHighlightOverlayText == null) {
         sDebugHighlightOverlayText = new Paint();
         sDebugHighlightOverlayText.setAntiAlias(true);
         sDebugHighlightOverlayText.setARGB(200, 50, 50, 50);
         sDebugHighlightOverlayText.setTextAlign(Align.RIGHT);
         sDebugHighlightOverlayText.setTypeface(Typeface.MONOSPACE);
         sDebugHighlightOverlayText.setTextSize((float)var1.dipsToPixels(9));
      }

   }

   protected void applyClipping(Canvas var1) {
      var1.clipRect(this.mClipLeft, this.mClipTop, this.mClipRight, this.mClipBottom);
   }

   protected final boolean boundsMatch(float var1, float var2, float var3, float var4) {
      return this.mLeft == var1 && this.mTop == var2 && this.mRight == var3 && this.mBottom == var4;
   }

   public final boolean clipBoundsMatch(float var1, float var2, float var3, float var4) {
      return this.mClipLeft == var1 && this.mClipTop == var2 && this.mClipRight == var3 && this.mClipBottom == var4;
   }

   public final void debugDraw(FlatViewGroup var1, Canvas var2) {
      this.onDebugDraw(var1, var2);
   }

   protected void debugDrawCautionHighlight(Canvas var1, String var2) {
      this.debugDrawHighlightRect(var1, sDebugHighlightYellow, var2);
   }

   protected void debugDrawWarningHighlight(Canvas var1, String var2) {
      this.debugDrawHighlightRect(var1, sDebugHighlightRed, var2);
   }

   public void draw(FlatViewGroup var1, Canvas var2) {
      this.onPreDraw(var1, var2);
      if(this.mNeedsClipping && this.shouldClip()) {
         var2.save(2);
         this.applyClipping(var2);
         this.onDraw(var2);
         var2.restore();
      } else {
         this.onDraw(var2);
      }
   }

   public final void freeze() {
      this.mFrozen = true;
   }

   public final float getBottom() {
      return this.mBottom;
   }

   public final float getClipBottom() {
      return this.mClipBottom;
   }

   public final float getClipLeft() {
      return this.mClipLeft;
   }

   public final float getClipRight() {
      return this.mClipRight;
   }

   public final float getClipTop() {
      return this.mClipTop;
   }

   protected String getDebugName() {
      return this.getClass().getSimpleName().substring(4);
   }

   public final float getLeft() {
      return this.mLeft;
   }

   public final float getRight() {
      return this.mRight;
   }

   public final float getTop() {
      return this.mTop;
   }

   public final boolean isFrozen() {
      return this.mFrozen;
   }

   public final AbstractDrawCommand mutableCopy() {
      try {
         AbstractDrawCommand var1 = (AbstractDrawCommand)super.clone();
         var1.mFrozen = false;
         return var1;
      } catch (CloneNotSupportedException var2) {
         throw new RuntimeException(var2);
      }
   }

   protected void onBoundsChanged() {}

   protected void onDebugDraw(FlatViewGroup var1, Canvas var2) {
      var1.debugDrawNamedRect(var2, getDebugBorderColor(), this.getDebugName(), this.mLeft, this.mTop, this.mRight, this.mBottom);
   }

   protected void onDebugDrawHighlight(Canvas var1) {}

   protected abstract void onDraw(Canvas var1);

   protected void onPreDraw(FlatViewGroup var1, Canvas var2) {}

   protected final void setBounds(float var1, float var2, float var3, float var4) {
      this.mLeft = var1;
      this.mTop = var2;
      this.mRight = var3;
      this.mBottom = var4;
      this.onBoundsChanged();
   }

   protected final void setClipBounds(float var1, float var2, float var3, float var4) {
      this.mClipLeft = var1;
      this.mClipTop = var2;
      this.mClipRight = var3;
      this.mClipBottom = var4;
      boolean var5;
      if(this.mClipLeft != Float.NEGATIVE_INFINITY) {
         var5 = true;
      } else {
         var5 = false;
      }

      this.mNeedsClipping = var5;
   }

   protected boolean shouldClip() {
      return this.mLeft < this.getClipLeft() || this.mTop < this.getClipTop() || this.mRight > this.getClipRight() || this.mBottom > this.getClipBottom();
   }

   public AbstractDrawCommand updateBoundsAndFreeze(float param1, float param2, float param3, float param4, float param5, float param6, float param7, float param8) {
      // $FF: Couldn't be decompiled
   }
}
