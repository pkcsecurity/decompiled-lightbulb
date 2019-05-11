package com.facebook.litho;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentHost;
import com.facebook.litho.DrawableComponent;
import com.facebook.litho.MountItem;
import com.facebook.litho.MountState;
import com.facebook.litho.TouchExpansionDelegate;
import com.facebook.litho.config.ComponentsConfiguration;

class DebugDraw {

   private static final int INTERACTIVE_VIEW_COLOR = 1724029951;
   private static final int MOUNT_BORDER_COLOR = -1711341568;
   private static final int MOUNT_BORDER_COLOR_HOST = -1711341313;
   private static final int MOUNT_CORNER_COLOR = -16776961;
   private static final int MOUNT_CORNER_COLOR_HOST = -16711681;
   private static final int TOUCH_DELEGATE_COLOR = 1154744270;
   private static Paint sInteractiveViewPaint;
   private static Paint sMountBoundsBorderPaint;
   private static Paint sMountBoundsCornerPaint;
   private static Rect sMountBoundsRect;
   private static Paint sTouchDelegatePaint;


   private static int dipToPixels(Resources var0, int var1) {
      float var2 = var0.getDisplayMetrics().density;
      return (int)((float)var1 * var2 + 0.5F);
   }

   static void draw(ComponentHost var0, Canvas var1) {
      if(ComponentsConfiguration.debugHighlightInteractiveBounds) {
         highlightInteractiveBounds(var0, var1);
      }

      if(ComponentsConfiguration.debugHighlightMountBounds) {
         highlightMountBounds(var0, var1);
      }

   }

   private static void drawCorner(Canvas var0, Paint var1, int var2, int var3, int var4, int var5, int var6) {
      drawCornerLine(var0, var1, var2, var3, var2 + var4, var3 + sign((float)var5) * var6);
      drawCornerLine(var0, var1, var2, var3, var2 + var6 * sign((float)var4), var3 + var5);
   }

   private static void drawCornerLine(Canvas var0, Paint var1, int var2, int var3, int var4, int var5) {
      int var7 = var2;
      int var6 = var4;
      if(var2 > var4) {
         var6 = var2;
         var7 = var4;
      }

      var4 = var3;
      var2 = var5;
      if(var3 > var5) {
         var2 = var3;
         var4 = var5;
      }

      var0.drawRect((float)var7, (float)var4, (float)var6, (float)var2, var1);
   }

   private static void drawMountBoundsBorder(Canvas var0, Paint var1, Rect var2) {
      int var3 = (int)var1.getStrokeWidth() / 2;
      var0.drawRect((float)(var2.left + var3), (float)(var2.top + var3), (float)(var2.right - var3), (float)(var2.bottom - var3), var1);
   }

   private static void drawMountBoundsCorners(Canvas var0, Paint var1, Rect var2, int var3, int var4) {
      drawCorner(var0, var1, var2.left, var2.top, var3, var3, var4);
      int var5 = var2.left;
      int var6 = var2.bottom;
      int var7 = -var3;
      drawCorner(var0, var1, var5, var6, var3, var7, var4);
      drawCorner(var0, var1, var2.right, var2.top, var7, var3, var4);
      drawCorner(var0, var1, var2.right, var2.bottom, var7, var7, var4);
   }

   private static int getBorderColor(Component var0) {
      return Component.isHostSpec(var0)?-1711341313:-1711341568;
   }

   private static int getCornerColor(Component var0) {
      return Component.isHostSpec(var0)?-16711681:-16776961;
   }

   private static void highlightInteractiveBounds(ComponentHost var0, Canvas var1) {
      if(sInteractiveViewPaint == null) {
         sInteractiveViewPaint = new Paint();
         sInteractiveViewPaint.setColor(1724029951);
      }

      if(sTouchDelegatePaint == null) {
         sTouchDelegatePaint = new Paint();
         sTouchDelegatePaint.setColor(1154744270);
      }

      if(isInteractive(var0)) {
         var1.drawRect(0.0F, 0.0F, (float)var0.getWidth(), (float)var0.getHeight(), sInteractiveViewPaint);
      }

      for(int var2 = var0.getMountItemCount() - 1; var2 >= 0; --var2) {
         MountItem var3 = var0.getMountItemAt(var2);
         Component var4 = var3.getComponent();
         if(Component.isMountViewSpec(var4) && !Component.isHostSpec(var4)) {
            View var6 = (View)var3.getMountableContent();
            if(isInteractive(var6)) {
               var1.drawRect((float)var6.getLeft(), (float)var6.getTop(), (float)var6.getRight(), (float)var6.getBottom(), sTouchDelegatePaint);
            }
         }
      }

      TouchExpansionDelegate var5 = var0.getTouchExpansionDelegate();
      if(var5 != null) {
         var5.draw(var1, sTouchDelegatePaint);
      }

   }

   private static void highlightMountBounds(ComponentHost var0, Canvas var1) {
      Resources var3 = var0.getResources();
      if(sMountBoundsRect == null) {
         sMountBoundsRect = new Rect();
      }

      if(sMountBoundsBorderPaint == null) {
         sMountBoundsBorderPaint = new Paint();
         sMountBoundsBorderPaint.setStyle(Style.STROKE);
         sMountBoundsBorderPaint.setStrokeWidth((float)dipToPixels(var3, 1));
      }

      if(sMountBoundsCornerPaint == null) {
         sMountBoundsCornerPaint = new Paint();
         sMountBoundsCornerPaint.setStyle(Style.FILL);
         sMountBoundsCornerPaint.setStrokeWidth((float)dipToPixels(var3, 2));
      }

      for(int var2 = var0.getMountItemCount() - 1; var2 >= 0; --var2) {
         MountItem var5 = var0.getMountItemAt(var2);
         Component var4 = var5.getComponent();
         Object var6 = var5.getMountableContent();
         if(shouldHighlight(var4)) {
            if(var6 instanceof View) {
               View var7 = (View)var6;
               sMountBoundsRect.left = var7.getLeft();
               sMountBoundsRect.top = var7.getTop();
               sMountBoundsRect.right = var7.getRight();
               sMountBoundsRect.bottom = var7.getBottom();
            } else if(var6 instanceof Drawable) {
               Drawable var8 = (Drawable)var6;
               sMountBoundsRect.set(var8.getBounds());
            }

            sMountBoundsBorderPaint.setColor(getBorderColor(var4));
            drawMountBoundsBorder(var1, sMountBoundsBorderPaint, sMountBoundsRect);
            sMountBoundsCornerPaint.setColor(getCornerColor(var4));
            drawMountBoundsCorners(var1, sMountBoundsCornerPaint, sMountBoundsRect, (int)sMountBoundsCornerPaint.getStrokeWidth(), Math.min(Math.min(sMountBoundsRect.width(), sMountBoundsRect.height()) / 3, dipToPixels(var3, 12)));
         }
      }

   }

   private static boolean isInteractive(View var0) {
      return MountState.getComponentClickListener(var0) != null || MountState.getComponentLongClickListener(var0) != null || MountState.getComponentTouchListener(var0) != null;
   }

   private static boolean shouldHighlight(Component var0) {
      return !(var0 instanceof DrawableComponent);
   }

   private static int sign(float var0) {
      return var0 >= 0.0F?1:-1;
   }
}
