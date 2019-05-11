package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Path.Direction;
import com.facebook.react.flat.AbstractDrawCommand;
import com.facebook.react.flat.FlatViewGroup;
import javax.annotation.Nullable;

final class DrawView extends AbstractDrawCommand {

   public static final DrawView[] EMPTY_ARRAY = new DrawView[0];
   static final float MINIMUM_ROUNDED_CLIPPING_VALUE = 0.5F;
   private final RectF TMP_RECT = new RectF();
   private float mClipRadius;
   float mLogicalBottom;
   float mLogicalLeft;
   float mLogicalRight;
   float mLogicalTop;
   @Nullable
   private Path mPath;
   boolean mWasMounted;
   final int reactTag;


   public DrawView(int var1) {
      this.reactTag = var1;
   }

   private boolean logicalBoundsMatch(float var1, float var2, float var3, float var4) {
      return var1 == this.mLogicalLeft && var2 == this.mLogicalTop && var3 == this.mLogicalRight && var4 == this.mLogicalBottom;
   }

   private void setLogicalBounds(float var1, float var2, float var3, float var4) {
      this.mLogicalLeft = var1;
      this.mLogicalTop = var2;
      this.mLogicalRight = var3;
      this.mLogicalBottom = var4;
   }

   private void updateClipPath() {
      this.mPath = new Path();
      this.TMP_RECT.set(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
      this.mPath.addRoundRect(this.TMP_RECT, this.mClipRadius, this.mClipRadius, Direction.CW);
   }

   protected void applyClipping(Canvas var1) {
      if(this.mClipRadius > 0.5F) {
         var1.clipPath(this.mPath);
      } else {
         super.applyClipping(var1);
      }
   }

   public DrawView collectDrawView(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13) {
      if(!this.isFrozen()) {
         this.setBounds(var1, var2, var3, var4);
         this.setClipBounds(var9, var10, var11, var12);
         this.setClipRadius(var13);
         this.setLogicalBounds(var5, var6, var7, var8);
         this.freeze();
         return this;
      } else {
         boolean var15 = this.boundsMatch(var1, var2, var3, var4);
         boolean var16 = this.clipBoundsMatch(var9, var10, var11, var12);
         boolean var14;
         if(this.mClipRadius == var13) {
            var14 = true;
         } else {
            var14 = false;
         }

         boolean var17 = this.logicalBoundsMatch(var5, var6, var7, var8);
         if(var15 && var16 && var14 && var17) {
            return this;
         } else {
            DrawView var18 = (DrawView)this.mutableCopy();
            if(!var15) {
               var18.setBounds(var1, var2, var3, var4);
            }

            if(!var16) {
               var18.setClipBounds(var9, var10, var11, var12);
            }

            if(!var17) {
               var18.setLogicalBounds(var5, var6, var7, var8);
            }

            if(!var14 || !var15) {
               var18.setClipRadius(var13);
            }

            var18.mWasMounted = false;
            var18.freeze();
            return var18;
         }
      }
   }

   public void draw(FlatViewGroup var1, Canvas var2) {
      this.onPreDraw(var1, var2);
      if(!this.mNeedsClipping && this.mClipRadius <= 0.5F) {
         var1.drawNextChild(var2);
      } else {
         var2.save(2);
         this.applyClipping(var2);
         var1.drawNextChild(var2);
         var2.restore();
      }
   }

   protected void onDebugDraw(FlatViewGroup var1, Canvas var2) {
      var1.debugDrawNextChild(var2);
   }

   protected void onDebugDrawHighlight(Canvas var1) {
      StringBuilder var3;
      if(this.mPath != null) {
         var3 = new StringBuilder();
         var3.append("borderRadius: ");
         var3.append(this.mClipRadius);
         this.debugDrawWarningHighlight(var1, var3.toString());
      } else {
         if(!this.boundsMatch(this.mLogicalLeft, this.mLogicalTop, this.mLogicalRight, this.mLogicalBottom)) {
            var3 = new StringBuilder("Overflow: { ");
            int var2 = 0;

            for(float[] var4 = new float[]{this.getLeft() - this.mLogicalLeft, this.getTop() - this.mLogicalTop, this.mLogicalRight - this.getRight(), this.mLogicalBottom - this.getBottom()}; var2 < 4; ++var2) {
               if(var4[var2] != 0.0F) {
                  var3.append((new String[]{"left: ", "top: ", "right: ", "bottom: "})[var2]);
                  var3.append(var4[var2]);
                  var3.append(", ");
               }
            }

            var3.append("}");
            this.debugDrawCautionHighlight(var1, var3.toString());
         }

      }
   }

   protected void onDraw(Canvas var1) {}

   void setClipRadius(float var1) {
      this.mClipRadius = var1;
      if(var1 > 0.5F) {
         this.updateClipPath();
      } else {
         this.mPath = null;
      }
   }
}
