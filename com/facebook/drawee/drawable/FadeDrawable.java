package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.drawee.drawable.ArrayDrawable;
import java.util.Arrays;

public class FadeDrawable extends ArrayDrawable {

   @VisibleForTesting
   public static final int TRANSITION_NONE = 2;
   @VisibleForTesting
   public static final int TRANSITION_RUNNING = 1;
   @VisibleForTesting
   public static final int TRANSITION_STARTING = 0;
   @VisibleForTesting
   int mAlpha;
   @VisibleForTesting
   int[] mAlphas;
   @VisibleForTesting
   int mDurationMs;
   @VisibleForTesting
   boolean[] mIsLayerOn;
   private final Drawable[] mLayers;
   @VisibleForTesting
   int mPreventInvalidateCount;
   @VisibleForTesting
   int[] mStartAlphas;
   @VisibleForTesting
   long mStartTimeMs;
   @VisibleForTesting
   int mTransitionState;


   public FadeDrawable(Drawable[] var1) {
      super(var1);
      int var2 = var1.length;
      boolean var3 = true;
      if(var2 < 1) {
         var3 = false;
      }

      Preconditions.checkState(var3, "At least one layer required!");
      this.mLayers = var1;
      this.mStartAlphas = new int[var1.length];
      this.mAlphas = new int[var1.length];
      this.mAlpha = 255;
      this.mIsLayerOn = new boolean[var1.length];
      this.mPreventInvalidateCount = 0;
      this.resetInternal();
   }

   private void drawDrawableWithAlpha(Canvas var1, Drawable var2, int var3) {
      if(var2 != null && var3 > 0) {
         ++this.mPreventInvalidateCount;
         var2.mutate().setAlpha(var3);
         --this.mPreventInvalidateCount;
         var2.draw(var1);
      }

   }

   private void resetInternal() {
      this.mTransitionState = 2;
      Arrays.fill(this.mStartAlphas, 0);
      this.mStartAlphas[0] = 255;
      Arrays.fill(this.mAlphas, 0);
      this.mAlphas[0] = 255;
      Arrays.fill(this.mIsLayerOn, false);
      this.mIsLayerOn[0] = true;
   }

   private boolean updateAlphas(float var1) {
      int var2 = 0;

      boolean var5;
      for(var5 = true; var2 < this.mLayers.length; ++var2) {
         byte var3;
         if(this.mIsLayerOn[var2]) {
            var3 = 1;
         } else {
            var3 = -1;
         }

         this.mAlphas[var2] = (int)((float)this.mStartAlphas[var2] + (float)(var3 * 255) * var1);
         if(this.mAlphas[var2] < 0) {
            this.mAlphas[var2] = 0;
         }

         if(this.mAlphas[var2] > 255) {
            this.mAlphas[var2] = 255;
         }

         boolean var4 = var5;
         if(this.mIsLayerOn[var2]) {
            var4 = var5;
            if(this.mAlphas[var2] < 255) {
               var4 = false;
            }
         }

         var5 = var4;
         if(!this.mIsLayerOn[var2]) {
            var5 = var4;
            if(this.mAlphas[var2] > 0) {
               var5 = false;
            }
         }
      }

      return var5;
   }

   public void beginBatchMode() {
      ++this.mPreventInvalidateCount;
   }

   public void draw(Canvas var1) {
      int var5 = this.mTransitionState;
      byte var3 = 2;
      byte var4 = 0;
      boolean var6;
      int var7;
      switch(var5) {
      case 0:
         System.arraycopy(this.mAlphas, 0, this.mStartAlphas, 0, this.mLayers.length);
         this.mStartTimeMs = this.getCurrentTimeMs();
         float var2;
         if(this.mDurationMs == 0) {
            var2 = 1.0F;
         } else {
            var2 = 0.0F;
         }

         var6 = this.updateAlphas(var2);
         if(!var6) {
            var3 = 1;
         }

         this.mTransitionState = var3;
         var7 = var4;
         break;
      case 1:
         if(this.mDurationMs > 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         Preconditions.checkState(var6);
         var6 = this.updateAlphas((float)(this.getCurrentTimeMs() - this.mStartTimeMs) / (float)this.mDurationMs);
         if(!var6) {
            var3 = 1;
         }

         this.mTransitionState = var3;
         var7 = var4;
         break;
      case 2:
      default:
         var6 = true;
         var7 = var4;
      }

      while(var7 < this.mLayers.length) {
         this.drawDrawableWithAlpha(var1, this.mLayers[var7], this.mAlphas[var7] * this.mAlpha / 255);
         ++var7;
      }

      if(!var6) {
         this.invalidateSelf();
      }

   }

   public void endBatchMode() {
      --this.mPreventInvalidateCount;
      this.invalidateSelf();
   }

   public void fadeInAllLayers() {
      this.mTransitionState = 0;
      Arrays.fill(this.mIsLayerOn, true);
      this.invalidateSelf();
   }

   public void fadeInLayer(int var1) {
      this.mTransitionState = 0;
      this.mIsLayerOn[var1] = true;
      this.invalidateSelf();
   }

   public void fadeOutAllLayers() {
      this.mTransitionState = 0;
      Arrays.fill(this.mIsLayerOn, false);
      this.invalidateSelf();
   }

   public void fadeOutLayer(int var1) {
      this.mTransitionState = 0;
      this.mIsLayerOn[var1] = false;
      this.invalidateSelf();
   }

   public void fadeToLayer(int var1) {
      this.mTransitionState = 0;
      Arrays.fill(this.mIsLayerOn, false);
      this.mIsLayerOn[var1] = true;
      this.invalidateSelf();
   }

   public void fadeUpToLayer(int var1) {
      this.mTransitionState = 0;
      boolean[] var2 = this.mIsLayerOn;
      ++var1;
      Arrays.fill(var2, 0, var1, true);
      Arrays.fill(this.mIsLayerOn, var1, this.mLayers.length, false);
      this.invalidateSelf();
   }

   public void finishTransitionImmediately() {
      this.mTransitionState = 2;

      for(int var1 = 0; var1 < this.mLayers.length; ++var1) {
         int[] var3 = this.mAlphas;
         short var2;
         if(this.mIsLayerOn[var1]) {
            var2 = 255;
         } else {
            var2 = 0;
         }

         var3[var1] = var2;
      }

      this.invalidateSelf();
   }

   public int getAlpha() {
      return this.mAlpha;
   }

   protected long getCurrentTimeMs() {
      return SystemClock.uptimeMillis();
   }

   public int getTransitionDuration() {
      return this.mDurationMs;
   }

   @VisibleForTesting
   public int getTransitionState() {
      return this.mTransitionState;
   }

   public void invalidateSelf() {
      if(this.mPreventInvalidateCount == 0) {
         super.invalidateSelf();
      }

   }

   public boolean isLayerOn(int var1) {
      return this.mIsLayerOn[var1];
   }

   public void reset() {
      this.resetInternal();
      this.invalidateSelf();
   }

   public void setAlpha(int var1) {
      if(this.mAlpha != var1) {
         this.mAlpha = var1;
         this.invalidateSelf();
      }

   }

   public void setTransitionDuration(int var1) {
      this.mDurationMs = var1;
      if(this.mTransitionState == 1) {
         this.mTransitionState = 0;
      }

   }
}
