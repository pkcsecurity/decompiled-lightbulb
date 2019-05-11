package com.facebook.react.views.scroll;

import android.os.SystemClock;

public class OnScrollDispatchHelper {

   private static final int MIN_EVENT_SEPARATION_MS = 10;
   private long mLastScrollEventTimeMs = -11L;
   private int mPrevX = Integer.MIN_VALUE;
   private int mPrevY = Integer.MIN_VALUE;
   private float mXFlingVelocity = 0.0F;
   private float mYFlingVelocity = 0.0F;


   public float getXFlingVelocity() {
      return this.mXFlingVelocity;
   }

   public float getYFlingVelocity() {
      return this.mYFlingVelocity;
   }

   public boolean onScrollChanged(int var1, int var2) {
      long var3 = SystemClock.uptimeMillis();
      boolean var5;
      if(var3 - this.mLastScrollEventTimeMs <= 10L && this.mPrevX == var1 && this.mPrevY == var2) {
         var5 = false;
      } else {
         var5 = true;
      }

      if(var3 - this.mLastScrollEventTimeMs != 0L) {
         this.mXFlingVelocity = (float)(var1 - this.mPrevX) / (float)(var3 - this.mLastScrollEventTimeMs);
         this.mYFlingVelocity = (float)(var2 - this.mPrevY) / (float)(var3 - this.mLastScrollEventTimeMs);
      }

      this.mLastScrollEventTimeMs = var3;
      this.mPrevX = var1;
      this.mPrevY = var2;
      return var5;
   }
}
