package com.facebook.imagepipeline.animated.impl;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.SystemClock;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.facebook.common.logging.FLog;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableCachingBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableDiagnostics;
import com.facebook.imagepipeline.animated.impl.RollingStat;
import com.facebook.imagepipeline.animated.util.AnimatedDrawableUtil;

public class AnimatedDrawableDiagnosticsImpl implements AnimatedDrawableDiagnostics {

   private static final Class<?> TAG = AnimatedDrawableDiagnostics.class;
   private AnimatedDrawableCachingBackend mAnimatedDrawableBackend;
   private final AnimatedDrawableUtil mAnimatedDrawableUtil;
   private final TextPaint mDebugTextPaint;
   private final DisplayMetrics mDisplayMetrics;
   private final RollingStat mDrawnFrames;
   private final RollingStat mDroppedFramesStat;
   private long mLastTimeStamp;
   private final StringBuilder sbTemp;


   public AnimatedDrawableDiagnosticsImpl(AnimatedDrawableUtil var1, DisplayMetrics var2) {
      this.mAnimatedDrawableUtil = var1;
      this.mDisplayMetrics = var2;
      this.mDroppedFramesStat = new RollingStat();
      this.mDrawnFrames = new RollingStat();
      this.sbTemp = new StringBuilder();
      this.mDebugTextPaint = new TextPaint();
      this.mDebugTextPaint.setColor(-16776961);
      this.mDebugTextPaint.setTextSize((float)this.convertDpToPx(14));
   }

   private int convertDpToPx(int var1) {
      return (int)TypedValue.applyDimension(1, (float)var1, this.mDisplayMetrics);
   }

   public void drawDebugOverlay(Canvas var1, Rect var2) {
      int var6 = this.mDroppedFramesStat.getSum(10);
      int var7 = this.mDrawnFrames.getSum(10);
      int var8 = var6 + var7;
      var6 = this.convertDpToPx(10);
      int var10 = this.convertDpToPx(20);
      int var11 = this.convertDpToPx(5);
      float var3;
      StringBuilder var12;
      if(var8 > 0) {
         var7 = var7 * 100 / var8;
         this.sbTemp.setLength(0);
         this.sbTemp.append(var7);
         this.sbTemp.append("%");
         var12 = this.sbTemp;
         var7 = this.sbTemp.length();
         var3 = (float)var6;
         var1.drawText(var12, 0, var7, var3, (float)var10, this.mDebugTextPaint);
         var8 = (int)(var3 + this.mDebugTextPaint.measureText(this.sbTemp, 0, this.sbTemp.length())) + var11;
      } else {
         var8 = var6;
      }

      var7 = this.mAnimatedDrawableBackend.getMemoryUsage();
      this.sbTemp.setLength(0);
      this.mAnimatedDrawableUtil.appendMemoryString(this.sbTemp, var7);
      float var4 = this.mDebugTextPaint.measureText(this.sbTemp, 0, this.sbTemp.length());
      int var9 = var8;
      var7 = var10;
      if((float)var8 + var4 > (float)var2.width()) {
         var7 = (int)((float)var10 + this.mDebugTextPaint.getTextSize() + (float)var11);
         var9 = var6;
      }

      var12 = this.sbTemp;
      var8 = this.sbTemp.length();
      float var5 = (float)var9;
      var3 = (float)var7;
      var1.drawText(var12, 0, var8, var5, var3, this.mDebugTextPaint);
      var9 = (int)(var5 + var4) + var11;
      this.sbTemp.setLength(0);
      this.mAnimatedDrawableBackend.appendDebugOptionString(this.sbTemp);
      var4 = this.mDebugTextPaint.measureText(this.sbTemp, 0, this.sbTemp.length());
      var8 = var9;
      if((float)var9 + var4 > (float)var2.width()) {
         var7 = (int)(var3 + this.mDebugTextPaint.getTextSize() + (float)var11);
         var8 = var6;
      }

      var1.drawText(this.sbTemp, 0, this.sbTemp.length(), (float)var8, (float)var7, this.mDebugTextPaint);
   }

   public void incrementDrawnFrames(int var1) {
      this.mDrawnFrames.incrementStats(var1);
   }

   public void incrementDroppedFrames(int var1) {
      this.mDroppedFramesStat.incrementStats(var1);
      if(var1 > 0) {
         FLog.v(TAG, "Dropped %d frames", (Object)Integer.valueOf(var1));
      }

   }

   public void onDrawMethodBegin() {
      this.mLastTimeStamp = SystemClock.uptimeMillis();
   }

   public void onDrawMethodEnd() {
      long var1 = SystemClock.uptimeMillis();
      long var3 = this.mLastTimeStamp;
      FLog.v(TAG, "draw took %d", (Object)Long.valueOf(var1 - var3));
   }

   public void onNextFrameMethodBegin() {
      this.mLastTimeStamp = SystemClock.uptimeMillis();
   }

   public void onNextFrameMethodEnd() {
      long var1 = SystemClock.uptimeMillis() - this.mLastTimeStamp;
      if(var1 > 3L) {
         FLog.v(TAG, "onNextFrame took %d", (Object)Long.valueOf(var1));
      }

   }

   public void onStartMethodBegin() {
      this.mLastTimeStamp = SystemClock.uptimeMillis();
   }

   public void onStartMethodEnd() {
      long var1 = SystemClock.uptimeMillis() - this.mLastTimeStamp;
      if(var1 > 3L) {
         FLog.v(TAG, "onStart took %d", (Object)Long.valueOf(var1));
      }

   }

   public void setBackend(AnimatedDrawableCachingBackend var1) {
      this.mAnimatedDrawableBackend = var1;
   }
}
