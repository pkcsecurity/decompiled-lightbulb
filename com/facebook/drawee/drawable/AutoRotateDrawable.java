package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.drawee.drawable.CloneableDrawable;
import com.facebook.drawee.drawable.DrawableUtils;
import com.facebook.drawee.drawable.ForwardingDrawable;

public class AutoRotateDrawable extends ForwardingDrawable implements CloneableDrawable, Runnable {

   private static final int DEGREES_IN_FULL_ROTATION = 360;
   private static final int FRAME_INTERVAL_MS = 20;
   private boolean mClockwise;
   private int mInterval;
   private boolean mIsScheduled;
   @VisibleForTesting
   float mRotationAngle;


   public AutoRotateDrawable(Drawable var1, int var2) {
      this(var1, var2, true);
   }

   public AutoRotateDrawable(Drawable var1, int var2, boolean var3) {
      super((Drawable)Preconditions.checkNotNull(var1));
      this.mRotationAngle = 0.0F;
      this.mIsScheduled = false;
      this.mInterval = var2;
      this.mClockwise = var3;
   }

   private int getIncrement() {
      return (int)(20.0F / (float)this.mInterval * 360.0F);
   }

   private void scheduleNextFrame() {
      if(!this.mIsScheduled) {
         this.mIsScheduled = true;
         this.scheduleSelf(this, SystemClock.uptimeMillis() + 20L);
      }

   }

   public AutoRotateDrawable cloneDrawable() {
      return new AutoRotateDrawable(DrawableUtils.cloneDrawable(this.getDrawable()), this.mInterval, this.mClockwise);
   }

   public void draw(Canvas var1) {
      int var3 = var1.save();
      Rect var8 = this.getBounds();
      int var4 = var8.right;
      int var5 = var8.left;
      int var6 = var8.bottom;
      int var7 = var8.top;
      float var2 = this.mRotationAngle;
      if(!this.mClockwise) {
         var2 = 360.0F - this.mRotationAngle;
      }

      var1.rotate(var2, (float)(var8.left + (var4 - var5) / 2), (float)(var8.top + (var6 - var7) / 2));
      super.draw(var1);
      var1.restoreToCount(var3);
      this.scheduleNextFrame();
   }

   public void reset() {
      this.mRotationAngle = 0.0F;
      this.mIsScheduled = false;
      this.unscheduleSelf(this);
      this.invalidateSelf();
   }

   public void run() {
      this.mIsScheduled = false;
      this.mRotationAngle += (float)this.getIncrement();
      this.invalidateSelf();
   }

   public void setClockwise(boolean var1) {
      this.mClockwise = var1;
   }
}
