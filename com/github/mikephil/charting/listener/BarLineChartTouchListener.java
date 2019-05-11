package com.github.mikephil.charting.listener;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class BarLineChartTouchListener extends ChartTouchListener<BarLineChartBase<? extends BarLineScatterCandleBubbleData<? extends IBarLineScatterCandleBubbleDataSet<? extends Entry>>>> {

   private IDataSet mClosestDataSetToTouch;
   private MPPointF mDecelerationCurrentPoint = MPPointF.getInstance(0.0F, 0.0F);
   private long mDecelerationLastTime = 0L;
   private MPPointF mDecelerationVelocity = MPPointF.getInstance(0.0F, 0.0F);
   private float mDragTriggerDist;
   private Matrix mMatrix = new Matrix();
   private float mMinScalePointerDistance;
   private float mSavedDist = 1.0F;
   private Matrix mSavedMatrix = new Matrix();
   private float mSavedXDist = 1.0F;
   private float mSavedYDist = 1.0F;
   private MPPointF mTouchPointCenter = MPPointF.getInstance(0.0F, 0.0F);
   private MPPointF mTouchStartPoint = MPPointF.getInstance(0.0F, 0.0F);
   private VelocityTracker mVelocityTracker;


   public BarLineChartTouchListener(BarLineChartBase<? extends BarLineScatterCandleBubbleData<? extends IBarLineScatterCandleBubbleDataSet<? extends Entry>>> var1, Matrix var2, float var3) {
      super(var1);
      this.mMatrix = var2;
      this.mDragTriggerDist = Utils.convertDpToPixel(var3);
      this.mMinScalePointerDistance = Utils.convertDpToPixel(3.5F);
   }

   private static float getXDist(MotionEvent var0) {
      return Math.abs(var0.getX(0) - var0.getX(1));
   }

   private static float getYDist(MotionEvent var0) {
      return Math.abs(var0.getY(0) - var0.getY(1));
   }

   private boolean inverted() {
      return this.mClosestDataSetToTouch == null && ((BarLineChartBase)this.mChart).isAnyAxisInverted() || this.mClosestDataSetToTouch != null && ((BarLineChartBase)this.mChart).isInverted(this.mClosestDataSetToTouch.getAxisDependency());
   }

   private static void midPoint(MPPointF var0, MotionEvent var1) {
      float var2 = var1.getX(0);
      float var3 = var1.getX(1);
      float var4 = var1.getY(0);
      float var5 = var1.getY(1);
      var0.x = (var2 + var3) / 2.0F;
      var0.y = (var4 + var5) / 2.0F;
   }

   private void performDrag(MotionEvent var1, float var2, float var3) {
      this.mLastGesture = ChartTouchListener.ChartGesture.DRAG;
      this.mMatrix.set(this.mSavedMatrix);
      OnChartGestureListener var6 = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
      float var4 = var2;
      float var5 = var3;
      if(this.inverted()) {
         if(this.mChart instanceof HorizontalBarChart) {
            var4 = -var2;
            var5 = var3;
         } else {
            var5 = -var3;
            var4 = var2;
         }
      }

      this.mMatrix.postTranslate(var4, var5);
      if(var6 != null) {
         var6.onChartTranslate(var1, var4, var5);
      }

   }

   private void performHighlightDrag(MotionEvent var1) {
      Highlight var2 = ((BarLineChartBase)this.mChart).getHighlightByTouchPoint(var1.getX(), var1.getY());
      if(var2 != null && !var2.equalTo(this.mLastHighlighted)) {
         this.mLastHighlighted = var2;
         ((BarLineChartBase)this.mChart).highlightValue(var2, true);
      }

   }

   private void performZoom(MotionEvent var1) {
      if(var1.getPointerCount() >= 2) {
         OnChartGestureListener var10 = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
         float var2 = spacing(var1);
         if(var2 > this.mMinScalePointerDistance) {
            MPPointF var11 = this.getTrans(this.mTouchPointCenter.x, this.mTouchPointCenter.y);
            ViewPortHandler var12 = ((BarLineChartBase)this.mChart).getViewPortHandler();
            int var7 = this.mTouchMode;
            boolean var5 = false;
            boolean var6 = false;
            boolean var4 = false;
            boolean var8;
            if(var7 == 4) {
               this.mLastGesture = ChartTouchListener.ChartGesture.PINCH_ZOOM;
               var2 /= this.mSavedDist;
               if(var2 < 1.0F) {
                  var4 = true;
               }

               if(var4) {
                  var8 = var12.canZoomOutMoreX();
               } else {
                  var8 = var12.canZoomInMoreX();
               }

               boolean var9;
               if(var4) {
                  var9 = var12.canZoomOutMoreY();
               } else {
                  var9 = var12.canZoomInMoreY();
               }

               float var3;
               if(((BarLineChartBase)this.mChart).isScaleXEnabled()) {
                  var3 = var2;
               } else {
                  var3 = 1.0F;
               }

               if(!((BarLineChartBase)this.mChart).isScaleYEnabled()) {
                  var2 = 1.0F;
               }

               if(var9 || var8) {
                  this.mMatrix.set(this.mSavedMatrix);
                  this.mMatrix.postScale(var3, var2, var11.x, var11.y);
                  if(var10 != null) {
                     var10.onChartScale(var1, var3, var2);
                  }
               }
            } else if(this.mTouchMode == 2 && ((BarLineChartBase)this.mChart).isScaleXEnabled()) {
               this.mLastGesture = ChartTouchListener.ChartGesture.X_ZOOM;
               var2 = getXDist(var1) / this.mSavedXDist;
               var4 = var5;
               if(var2 < 1.0F) {
                  var4 = true;
               }

               if(var4) {
                  var8 = var12.canZoomOutMoreX();
               } else {
                  var8 = var12.canZoomInMoreX();
               }

               if(var8) {
                  this.mMatrix.set(this.mSavedMatrix);
                  this.mMatrix.postScale(var2, 1.0F, var11.x, var11.y);
                  if(var10 != null) {
                     var10.onChartScale(var1, var2, 1.0F);
                  }
               }
            } else if(this.mTouchMode == 3 && ((BarLineChartBase)this.mChart).isScaleYEnabled()) {
               this.mLastGesture = ChartTouchListener.ChartGesture.Y_ZOOM;
               var2 = getYDist(var1) / this.mSavedYDist;
               var4 = var6;
               if(var2 < 1.0F) {
                  var4 = true;
               }

               if(var4) {
                  var8 = var12.canZoomOutMoreY();
               } else {
                  var8 = var12.canZoomInMoreY();
               }

               if(var8) {
                  this.mMatrix.set(this.mSavedMatrix);
                  this.mMatrix.postScale(1.0F, var2, var11.x, var11.y);
                  if(var10 != null) {
                     var10.onChartScale(var1, 1.0F, var2);
                  }
               }
            }

            MPPointF.recycleInstance(var11);
         }
      }

   }

   private void saveTouchStart(MotionEvent var1) {
      this.mSavedMatrix.set(this.mMatrix);
      this.mTouchStartPoint.x = var1.getX();
      this.mTouchStartPoint.y = var1.getY();
      this.mClosestDataSetToTouch = ((BarLineChartBase)this.mChart).getDataSetByTouchPoint(var1.getX(), var1.getY());
   }

   private static float spacing(MotionEvent var0) {
      float var1 = var0.getX(0) - var0.getX(1);
      float var2 = var0.getY(0) - var0.getY(1);
      return (float)Math.sqrt((double)(var1 * var1 + var2 * var2));
   }

   public void computeScroll() {
      float var1 = this.mDecelerationVelocity.x;
      float var2 = 0.0F;
      if(var1 != 0.0F || this.mDecelerationVelocity.y != 0.0F) {
         long var5 = AnimationUtils.currentAnimationTimeMillis();
         MPPointF var7 = this.mDecelerationVelocity;
         var7.x *= ((BarLineChartBase)this.mChart).getDragDecelerationFrictionCoef();
         var7 = this.mDecelerationVelocity;
         var7.y *= ((BarLineChartBase)this.mChart).getDragDecelerationFrictionCoef();
         var1 = (float)(var5 - this.mDecelerationLastTime) / 1000.0F;
         float var3 = this.mDecelerationVelocity.x;
         float var4 = this.mDecelerationVelocity.y;
         var7 = this.mDecelerationCurrentPoint;
         var7.x += var3 * var1;
         var7 = this.mDecelerationCurrentPoint;
         var7.y += var4 * var1;
         MotionEvent var8 = MotionEvent.obtain(var5, var5, 2, this.mDecelerationCurrentPoint.x, this.mDecelerationCurrentPoint.y, 0);
         if(((BarLineChartBase)this.mChart).isDragXEnabled()) {
            var1 = this.mDecelerationCurrentPoint.x - this.mTouchStartPoint.x;
         } else {
            var1 = 0.0F;
         }

         if(((BarLineChartBase)this.mChart).isDragYEnabled()) {
            var2 = this.mDecelerationCurrentPoint.y - this.mTouchStartPoint.y;
         }

         this.performDrag(var8, var1, var2);
         var8.recycle();
         this.mMatrix = ((BarLineChartBase)this.mChart).getViewPortHandler().refresh(this.mMatrix, this.mChart, false);
         this.mDecelerationLastTime = var5;
         if((double)Math.abs(this.mDecelerationVelocity.x) < 0.01D && (double)Math.abs(this.mDecelerationVelocity.y) < 0.01D) {
            ((BarLineChartBase)this.mChart).calculateOffsets();
            ((BarLineChartBase)this.mChart).postInvalidate();
            this.stopDeceleration();
         } else {
            Utils.postInvalidateOnAnimation(this.mChart);
         }
      }
   }

   public Matrix getMatrix() {
      return this.mMatrix;
   }

   public MPPointF getTrans(float var1, float var2) {
      ViewPortHandler var4 = ((BarLineChartBase)this.mChart).getViewPortHandler();
      float var3 = var4.offsetLeft();
      if(this.inverted()) {
         var2 = -(var2 - var4.offsetTop());
      } else {
         var2 = -((float)((BarLineChartBase)this.mChart).getMeasuredHeight() - var2 - var4.offsetBottom());
      }

      return MPPointF.getInstance(var1 - var3, var2);
   }

   public boolean onDoubleTap(MotionEvent var1) {
      this.mLastGesture = ChartTouchListener.ChartGesture.DOUBLE_TAP;
      OnChartGestureListener var5 = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
      if(var5 != null) {
         var5.onChartDoubleTapped(var1);
      }

      if(((BarLineChartBase)this.mChart).isDoubleTapToZoomEnabled() && ((BarLineScatterCandleBubbleData)((BarLineChartBase)this.mChart).getData()).getEntryCount() > 0) {
         MPPointF var7 = this.getTrans(var1.getX(), var1.getY());
         BarLineChartBase var6 = (BarLineChartBase)this.mChart;
         boolean var4 = ((BarLineChartBase)this.mChart).isScaleXEnabled();
         float var3 = 1.0F;
         float var2;
         if(var4) {
            var2 = 1.4F;
         } else {
            var2 = 1.0F;
         }

         if(((BarLineChartBase)this.mChart).isScaleYEnabled()) {
            var3 = 1.4F;
         }

         var6.zoom(var2, var3, var7.x, var7.y);
         if(((BarLineChartBase)this.mChart).isLogEnabled()) {
            StringBuilder var8 = new StringBuilder();
            var8.append("Double-Tap, Zooming In, x: ");
            var8.append(var7.x);
            var8.append(", y: ");
            var8.append(var7.y);
            Log.i("BarlineChartTouch", var8.toString());
         }

         MPPointF.recycleInstance(var7);
      }

      return super.onDoubleTap(var1);
   }

   public boolean onFling(MotionEvent var1, MotionEvent var2, float var3, float var4) {
      this.mLastGesture = ChartTouchListener.ChartGesture.FLING;
      OnChartGestureListener var5 = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
      if(var5 != null) {
         var5.onChartFling(var1, var2, var3, var4);
      }

      return super.onFling(var1, var2, var3, var4);
   }

   public void onLongPress(MotionEvent var1) {
      this.mLastGesture = ChartTouchListener.ChartGesture.LONG_PRESS;
      OnChartGestureListener var2 = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
      if(var2 != null) {
         var2.onChartLongPressed(var1);
      }

   }

   public boolean onSingleTapUp(MotionEvent var1) {
      this.mLastGesture = ChartTouchListener.ChartGesture.SINGLE_TAP;
      OnChartGestureListener var2 = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
      if(var2 != null) {
         var2.onChartSingleTapped(var1);
      }

      if(!((BarLineChartBase)this.mChart).isHighlightPerTapEnabled()) {
         return false;
      } else {
         this.performHighlight(((BarLineChartBase)this.mChart).getHighlightByTouchPoint(var1.getX(), var1.getY()), var1);
         return super.onSingleTapUp(var1);
      }
   }

   @SuppressLint({"ClickableViewAccessibility"})
   public boolean onTouch(View var1, MotionEvent var2) {
      if(this.mVelocityTracker == null) {
         this.mVelocityTracker = VelocityTracker.obtain();
      }

      this.mVelocityTracker.addMovement(var2);
      int var6 = var2.getActionMasked();
      byte var5 = 3;
      if(var6 == 3 && this.mVelocityTracker != null) {
         this.mVelocityTracker.recycle();
         this.mVelocityTracker = null;
      }

      if(this.mTouchMode == 0) {
         this.mGestureDetector.onTouchEvent(var2);
      }

      if(!((BarLineChartBase)this.mChart).isDragEnabled() && !((BarLineChartBase)this.mChart).isScaleXEnabled() && !((BarLineChartBase)this.mChart).isScaleYEnabled()) {
         return true;
      } else {
         int var7 = var2.getAction();
         boolean var10 = false;
         float var3;
         float var4;
         switch(var7 & 255) {
         case 0:
            this.startAction(var2);
            this.stopDeceleration();
            this.saveTouchStart(var2);
            break;
         case 1:
            VelocityTracker var9 = this.mVelocityTracker;
            int var12 = var2.getPointerId(0);
            var9.computeCurrentVelocity(1000, (float)Utils.getMaximumFlingVelocity());
            var3 = var9.getYVelocity(var12);
            var4 = var9.getXVelocity(var12);
            if((Math.abs(var4) > (float)Utils.getMinimumFlingVelocity() || Math.abs(var3) > (float)Utils.getMinimumFlingVelocity()) && this.mTouchMode == 1 && ((BarLineChartBase)this.mChart).isDragDecelerationEnabled()) {
               this.stopDeceleration();
               this.mDecelerationLastTime = AnimationUtils.currentAnimationTimeMillis();
               this.mDecelerationCurrentPoint.x = var2.getX();
               this.mDecelerationCurrentPoint.y = var2.getY();
               this.mDecelerationVelocity.x = var4;
               this.mDecelerationVelocity.y = var3;
               Utils.postInvalidateOnAnimation(this.mChart);
            }

            if(this.mTouchMode == 2 || this.mTouchMode == 3 || this.mTouchMode == 4 || this.mTouchMode == 5) {
               ((BarLineChartBase)this.mChart).calculateOffsets();
               ((BarLineChartBase)this.mChart).postInvalidate();
            }

            this.mTouchMode = 0;
            ((BarLineChartBase)this.mChart).enableScroll();
            if(this.mVelocityTracker != null) {
               this.mVelocityTracker.recycle();
               this.mVelocityTracker = null;
            }

            this.endAction(var2);
            break;
         case 2:
            if(this.mTouchMode == 1) {
               ((BarLineChartBase)this.mChart).disableScroll();
               boolean var8 = ((BarLineChartBase)this.mChart).isDragXEnabled();
               var4 = 0.0F;
               if(var8) {
                  var3 = var2.getX() - this.mTouchStartPoint.x;
               } else {
                  var3 = 0.0F;
               }

               if(((BarLineChartBase)this.mChart).isDragYEnabled()) {
                  var4 = var2.getY() - this.mTouchStartPoint.y;
               }

               this.performDrag(var2, var3, var4);
            } else if(this.mTouchMode != 2 && this.mTouchMode != 3 && this.mTouchMode != 4) {
               if(this.mTouchMode == 0 && Math.abs(distance(var2.getX(), this.mTouchStartPoint.x, var2.getY(), this.mTouchStartPoint.y)) > this.mDragTriggerDist && ((BarLineChartBase)this.mChart).isDragEnabled()) {
                  boolean var11;
                  label127: {
                     if(((BarLineChartBase)this.mChart).isFullyZoomedOut()) {
                        var11 = var10;
                        if(((BarLineChartBase)this.mChart).hasNoDragOffset()) {
                           break label127;
                        }
                     }

                     var11 = true;
                  }

                  if(var11) {
                     var3 = Math.abs(var2.getX() - this.mTouchStartPoint.x);
                     var4 = Math.abs(var2.getY() - this.mTouchStartPoint.y);
                     if((((BarLineChartBase)this.mChart).isDragXEnabled() || var4 >= var3) && (((BarLineChartBase)this.mChart).isDragYEnabled() || var4 <= var3)) {
                        this.mLastGesture = ChartTouchListener.ChartGesture.DRAG;
                        this.mTouchMode = 1;
                     }
                  } else if(((BarLineChartBase)this.mChart).isHighlightPerDragEnabled()) {
                     this.mLastGesture = ChartTouchListener.ChartGesture.DRAG;
                     if(((BarLineChartBase)this.mChart).isHighlightPerDragEnabled()) {
                        this.performHighlightDrag(var2);
                     }
                  }
               }
            } else {
               ((BarLineChartBase)this.mChart).disableScroll();
               if(((BarLineChartBase)this.mChart).isScaleXEnabled() || ((BarLineChartBase)this.mChart).isScaleYEnabled()) {
                  this.performZoom(var2);
               }
            }
            break;
         case 3:
            this.mTouchMode = 0;
            this.endAction(var2);
         case 4:
         default:
            break;
         case 5:
            if(var2.getPointerCount() >= 2) {
               ((BarLineChartBase)this.mChart).disableScroll();
               this.saveTouchStart(var2);
               this.mSavedXDist = getXDist(var2);
               this.mSavedYDist = getYDist(var2);
               this.mSavedDist = spacing(var2);
               if(this.mSavedDist > 10.0F) {
                  if(((BarLineChartBase)this.mChart).isPinchZoomEnabled()) {
                     this.mTouchMode = 4;
                  } else if(((BarLineChartBase)this.mChart).isScaleXEnabled() != ((BarLineChartBase)this.mChart).isScaleYEnabled()) {
                     if(((BarLineChartBase)this.mChart).isScaleXEnabled()) {
                        var5 = 2;
                     }

                     this.mTouchMode = var5;
                  } else {
                     if(this.mSavedXDist > this.mSavedYDist) {
                        var5 = 2;
                     }

                     this.mTouchMode = var5;
                  }
               }

               midPoint(this.mTouchPointCenter, var2);
            }
            break;
         case 6:
            Utils.velocityTrackerPointerUpCleanUpIfNecessary(var2, this.mVelocityTracker);
            this.mTouchMode = 5;
         }

         this.mMatrix = ((BarLineChartBase)this.mChart).getViewPortHandler().refresh(this.mMatrix, this.mChart, true);
         return true;
      }
   }

   public void setDragTriggerDist(float var1) {
      this.mDragTriggerDist = Utils.convertDpToPixel(var1);
   }

   public void stopDeceleration() {
      this.mDecelerationVelocity.x = 0.0F;
      this.mDecelerationVelocity.y = 0.0F;
   }
}
