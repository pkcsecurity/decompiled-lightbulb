package com.facebook.react.uimanager;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.touch.ReactHitSlopView;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.ReactCompoundView;
import com.facebook.react.uimanager.ReactCompoundViewGroup;
import com.facebook.react.uimanager.ReactPointerEventsView;
import com.facebook.react.uimanager.ReactZIndexedViewGroup;
import javax.annotation.Nullable;

public class TouchTargetHelper {

   private static final float[] mEventCoords = new float[2];
   private static final Matrix mInverseMatrix = new Matrix();
   private static final float[] mMatrixTransformCoords = new float[2];
   private static final PointF mTempPoint = new PointF();


   private static View findClosestReactAncestor(View var0) {
      while(var0 != null && var0.getId() <= 0) {
         var0 = (View)var0.getParent();
      }

      return var0;
   }

   public static int findTargetTagAndCoordinatesForTouch(float var0, float var1, ViewGroup var2, float[] var3, @Nullable int[] var4) {
      UiThreadUtil.assertOnUiThread();
      int var6 = var2.getId();
      var3[0] = var0;
      var3[1] = var1;
      View var7 = findTouchTargetView(var3, var2);
      int var5 = var6;
      if(var7 != null) {
         var7 = findClosestReactAncestor(var7);
         var5 = var6;
         if(var7 != null) {
            if(var4 != null) {
               var4[0] = var7.getId();
            }

            var5 = getTouchTargetForView(var7, var3[0], var3[1]);
         }
      }

      return var5;
   }

   public static int findTargetTagForTouch(float var0, float var1, ViewGroup var2) {
      return findTargetTagAndCoordinatesForTouch(var0, var1, var2, mEventCoords, (int[])null);
   }

   public static int findTargetTagForTouch(float var0, float var1, ViewGroup var2, @Nullable int[] var3) {
      return findTargetTagAndCoordinatesForTouch(var0, var1, var2, mEventCoords, var3);
   }

   private static View findTouchTargetView(float[] var0, ViewGroup var1) {
      int var4 = var1.getChildCount();
      ReactZIndexedViewGroup var6;
      if(var1 instanceof ReactZIndexedViewGroup) {
         var6 = (ReactZIndexedViewGroup)var1;
      } else {
         var6 = null;
      }

      --var4;

      for(; var4 >= 0; --var4) {
         int var5;
         if(var6 != null) {
            var5 = var6.getZIndexMappedChildIndex(var4);
         } else {
            var5 = var4;
         }

         View var7 = var1.getChildAt(var5);
         PointF var8 = mTempPoint;
         if(isTransformedTouchPointInView(var0[0], var0[1], var1, var7, var8)) {
            float var2 = var0[0];
            float var3 = var0[1];
            var0[0] = var8.x;
            var0[1] = var8.y;
            var7 = findTouchTargetViewWithPointerEvents(var0, var7);
            if(var7 != null) {
               return var7;
            }

            var0[0] = var2;
            var0[1] = var3;
         }
      }

      return var1;
   }

   @Nullable
   private static View findTouchTargetViewWithPointerEvents(float[] var0, View var1) {
      PointerEvents var3;
      if(var1 instanceof ReactPointerEventsView) {
         var3 = ((ReactPointerEventsView)var1).getPointerEvents();
      } else {
         var3 = PointerEvents.AUTO;
      }

      PointerEvents var2 = var3;
      if(!var1.isEnabled()) {
         if(var3 == PointerEvents.AUTO) {
            var2 = PointerEvents.BOX_NONE;
         } else {
            var2 = var3;
            if(var3 == PointerEvents.BOX_ONLY) {
               var2 = PointerEvents.NONE;
            }
         }
      }

      if(var2 == PointerEvents.NONE) {
         return null;
      } else if(var2 == PointerEvents.BOX_ONLY) {
         return var1;
      } else if(var2 == PointerEvents.BOX_NONE) {
         if(var1 instanceof ViewGroup) {
            View var5 = findTouchTargetView(var0, (ViewGroup)var1);
            if(var5 != var1) {
               return var5;
            }

            if(var1 instanceof ReactCompoundView && ((ReactCompoundView)var1).reactTagForTouch(var0[0], var0[1]) != var1.getId()) {
               return var1;
            }
         }

         return null;
      } else if(var2 == PointerEvents.AUTO) {
         return var1 instanceof ReactCompoundViewGroup && ((ReactCompoundViewGroup)var1).interceptsTouchEvent(var0[0], var0[1])?var1:(var1 instanceof ViewGroup?findTouchTargetView(var0, (ViewGroup)var1):var1);
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("Unknown pointer event type: ");
         var4.append(var2.toString());
         throw new JSApplicationIllegalArgumentException(var4.toString());
      }
   }

   private static int getTouchTargetForView(View var0, float var1, float var2) {
      return var0 instanceof ReactCompoundView?((ReactCompoundView)var0).reactTagForTouch(var1, var2):var0.getId();
   }

   private static boolean isTransformedTouchPointInView(float var0, float var1, ViewGroup var2, View var3, PointF var4) {
      float var5 = var0 + (float)var2.getScrollX() - (float)var3.getLeft();
      float var6 = var1 + (float)var2.getScrollY() - (float)var3.getTop();
      Matrix var9 = var3.getMatrix();
      var1 = var5;
      var0 = var6;
      if(!var9.isIdentity()) {
         float[] var7 = mMatrixTransformCoords;
         var7[0] = var5;
         var7[1] = var6;
         Matrix var8 = mInverseMatrix;
         var9.invert(var8);
         var8.mapPoints(var7);
         var1 = var7[0];
         var0 = var7[1];
      }

      if(var3 instanceof ReactHitSlopView) {
         ReactHitSlopView var10 = (ReactHitSlopView)var3;
         if(var10.getHitSlopRect() != null) {
            Rect var11 = var10.getHitSlopRect();
            if(var1 >= (float)(-var11.left) && var1 < (float)(var3.getRight() - var3.getLeft() + var11.right) && var0 >= (float)(-var11.top) && var0 < (float)(var3.getBottom() - var3.getTop() + var11.bottom)) {
               var4.set(var1, var0);
               return true;
            }

            return false;
         }
      }

      if(var1 >= 0.0F && var1 < (float)(var3.getRight() - var3.getLeft()) && var0 >= 0.0F && var0 < (float)(var3.getBottom() - var3.getTop())) {
         var4.set(var1, var0);
         return true;
      } else {
         return false;
      }
   }
}
