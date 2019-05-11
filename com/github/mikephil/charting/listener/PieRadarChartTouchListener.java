package com.github.mikephil.charting.listener;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;

public class PieRadarChartTouchListener extends ChartTouchListener<PieRadarChartBase<?>> {

   private ArrayList<PieRadarChartTouchListener.AngularVelocitySample> _velocitySamples = new ArrayList();
   private float mDecelerationAngularVelocity = 0.0F;
   private long mDecelerationLastTime = 0L;
   private float mStartAngle = 0.0F;
   private MPPointF mTouchStartPoint = MPPointF.getInstance(0.0F, 0.0F);


   public PieRadarChartTouchListener(PieRadarChartBase<?> var1) {
      super(var1);
   }

   private float calculateVelocity() {
      if(this._velocitySamples.isEmpty()) {
         return 0.0F;
      } else {
         ArrayList var5 = this._velocitySamples;
         boolean var4 = false;
         PieRadarChartTouchListener.AngularVelocitySample var6 = (PieRadarChartTouchListener.AngularVelocitySample)var5.get(0);
         PieRadarChartTouchListener.AngularVelocitySample var7 = (PieRadarChartTouchListener.AngularVelocitySample)this._velocitySamples.get(this._velocitySamples.size() - 1);
         int var3 = this._velocitySamples.size() - 1;

         PieRadarChartTouchListener.AngularVelocitySample var9;
         for(var9 = var6; var3 >= 0; --var3) {
            var9 = (PieRadarChartTouchListener.AngularVelocitySample)this._velocitySamples.get(var3);
            if(var9.angle != var7.angle) {
               break;
            }
         }

         float var2 = (float)(var7.time - var6.time) / 1000.0F;
         float var1 = var2;
         if(var2 == 0.0F) {
            var1 = 0.1F;
         }

         boolean var8 = var4;
         if(var7.angle >= var9.angle) {
            var8 = true;
         }

         var4 = var8;
         if((double)Math.abs(var7.angle - var9.angle) > 270.0D) {
            var4 = var8 ^ true;
         }

         if((double)(var7.angle - var6.angle) > 180.0D) {
            var6.angle = (float)((double)var6.angle + 360.0D);
         } else if((double)(var6.angle - var7.angle) > 180.0D) {
            var7.angle = (float)((double)var7.angle + 360.0D);
         }

         var2 = Math.abs((var7.angle - var6.angle) / var1);
         var1 = var2;
         if(!var4) {
            var1 = -var2;
         }

         return var1;
      }
   }

   private void resetVelocity() {
      this._velocitySamples.clear();
   }

   private void sampleVelocity(float var1, float var2) {
      long var4 = AnimationUtils.currentAnimationTimeMillis();
      this._velocitySamples.add(new PieRadarChartTouchListener.AngularVelocitySample(var4, ((PieRadarChartBase)this.mChart).getAngleForPoint(var1, var2)));

      for(int var3 = this._velocitySamples.size(); var3 - 2 > 0 && var4 - ((PieRadarChartTouchListener.AngularVelocitySample)this._velocitySamples.get(0)).time > 1000L; --var3) {
         this._velocitySamples.remove(0);
      }

   }

   public void computeScroll() {
      if(this.mDecelerationAngularVelocity != 0.0F) {
         long var2 = AnimationUtils.currentAnimationTimeMillis();
         this.mDecelerationAngularVelocity *= ((PieRadarChartBase)this.mChart).getDragDecelerationFrictionCoef();
         float var1 = (float)(var2 - this.mDecelerationLastTime) / 1000.0F;
         ((PieRadarChartBase)this.mChart).setRotationAngle(((PieRadarChartBase)this.mChart).getRotationAngle() + this.mDecelerationAngularVelocity * var1);
         this.mDecelerationLastTime = var2;
         if((double)Math.abs(this.mDecelerationAngularVelocity) >= 0.001D) {
            Utils.postInvalidateOnAnimation(this.mChart);
         } else {
            this.stopDeceleration();
         }
      }
   }

   public void onLongPress(MotionEvent var1) {
      this.mLastGesture = ChartTouchListener.ChartGesture.LONG_PRESS;
      OnChartGestureListener var2 = ((PieRadarChartBase)this.mChart).getOnChartGestureListener();
      if(var2 != null) {
         var2.onChartLongPressed(var1);
      }

   }

   public boolean onSingleTapConfirmed(MotionEvent var1) {
      return true;
   }

   public boolean onSingleTapUp(MotionEvent var1) {
      this.mLastGesture = ChartTouchListener.ChartGesture.SINGLE_TAP;
      OnChartGestureListener var2 = ((PieRadarChartBase)this.mChart).getOnChartGestureListener();
      if(var2 != null) {
         var2.onChartSingleTapped(var1);
      }

      if(!((PieRadarChartBase)this.mChart).isHighlightPerTapEnabled()) {
         return false;
      } else {
         this.performHighlight(((PieRadarChartBase)this.mChart).getHighlightByTouchPoint(var1.getX(), var1.getY()), var1);
         return true;
      }
   }

   @SuppressLint({"ClickableViewAccessibility"})
   public boolean onTouch(View var1, MotionEvent var2) {
      if(this.mGestureDetector.onTouchEvent(var2)) {
         return true;
      } else {
         if(((PieRadarChartBase)this.mChart).isRotationEnabled()) {
            float var3 = var2.getX();
            float var4 = var2.getY();
            switch(var2.getAction()) {
            case 0:
               this.startAction(var2);
               this.stopDeceleration();
               this.resetVelocity();
               if(((PieRadarChartBase)this.mChart).isDragDecelerationEnabled()) {
                  this.sampleVelocity(var3, var4);
               }

               this.setGestureStartAngle(var3, var4);
               this.mTouchStartPoint.x = var3;
               this.mTouchStartPoint.y = var4;
               break;
            case 1:
               if(((PieRadarChartBase)this.mChart).isDragDecelerationEnabled()) {
                  this.stopDeceleration();
                  this.sampleVelocity(var3, var4);
                  this.mDecelerationAngularVelocity = this.calculateVelocity();
                  if(this.mDecelerationAngularVelocity != 0.0F) {
                     this.mDecelerationLastTime = AnimationUtils.currentAnimationTimeMillis();
                     Utils.postInvalidateOnAnimation(this.mChart);
                  }
               }

               ((PieRadarChartBase)this.mChart).enableScroll();
               this.mTouchMode = 0;
               this.endAction(var2);
               return true;
            case 2:
               if(((PieRadarChartBase)this.mChart).isDragDecelerationEnabled()) {
                  this.sampleVelocity(var3, var4);
               }

               if(this.mTouchMode == 0 && distance(var3, this.mTouchStartPoint.x, var4, this.mTouchStartPoint.y) > Utils.convertDpToPixel(8.0F)) {
                  this.mLastGesture = ChartTouchListener.ChartGesture.ROTATE;
                  this.mTouchMode = 6;
                  ((PieRadarChartBase)this.mChart).disableScroll();
               } else if(this.mTouchMode == 6) {
                  this.updateGestureRotation(var3, var4);
                  ((PieRadarChartBase)this.mChart).invalidate();
               }

               this.endAction(var2);
               return true;
            default:
               return true;
            }
         }

         return true;
      }
   }

   public void setGestureStartAngle(float var1, float var2) {
      this.mStartAngle = ((PieRadarChartBase)this.mChart).getAngleForPoint(var1, var2) - ((PieRadarChartBase)this.mChart).getRawRotationAngle();
   }

   public void stopDeceleration() {
      this.mDecelerationAngularVelocity = 0.0F;
   }

   public void updateGestureRotation(float var1, float var2) {
      ((PieRadarChartBase)this.mChart).setRotationAngle(((PieRadarChartBase)this.mChart).getAngleForPoint(var1, var2) - this.mStartAngle);
   }

   class AngularVelocitySample {

      public float angle;
      public long time;


      public AngularVelocitySample(long var2, float var4) {
         this.time = var2;
         this.angle = var4;
      }
   }
}
