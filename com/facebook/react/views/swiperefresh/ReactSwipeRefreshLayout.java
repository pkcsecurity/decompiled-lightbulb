package com.facebook.react.views.swiperefresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.events.NativeGestureUtil;

public class ReactSwipeRefreshLayout extends SwipeRefreshLayout {

   private static final float DEFAULT_CIRCLE_TARGET = 64.0F;
   private boolean mDidLayout = false;
   private boolean mIntercepted;
   private float mPrevTouchX;
   private float mProgressViewOffset = 0.0F;
   private boolean mRefreshing = false;
   private int mTouchSlop;


   public ReactSwipeRefreshLayout(ReactContext var1) {
      super(var1);
      this.mTouchSlop = ViewConfiguration.get(var1).getScaledTouchSlop();
   }

   private boolean shouldInterceptTouchEvent(MotionEvent var1) {
      int var3 = var1.getAction();
      if(var3 != 0) {
         if(var3 != 2) {
            return true;
         }

         float var2 = Math.abs(var1.getX() - this.mPrevTouchX);
         if(this.mIntercepted || var2 > (float)this.mTouchSlop) {
            this.mIntercepted = true;
            return false;
         }
      } else {
         this.mPrevTouchX = var1.getX();
         this.mIntercepted = false;
      }

      return true;
   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      if(this.shouldInterceptTouchEvent(var1) && super.onInterceptTouchEvent(var1)) {
         NativeGestureUtil.notifyNativeGestureStarted(this, var1);
         return true;
      } else {
         return false;
      }
   }

   public void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      super.onLayout(var1, var2, var3, var4, var5);
      if(!this.mDidLayout) {
         this.mDidLayout = true;
         this.setProgressViewOffset(this.mProgressViewOffset);
         this.setRefreshing(this.mRefreshing);
      }

   }

   public void requestDisallowInterceptTouchEvent(boolean var1) {
      if(this.getParent() != null) {
         this.getParent().requestDisallowInterceptTouchEvent(var1);
      }

   }

   public void setProgressViewOffset(float var1) {
      this.mProgressViewOffset = var1;
      if(this.mDidLayout) {
         int var2 = this.getProgressCircleDiameter();
         this.setProgressViewOffset(false, Math.round(PixelUtil.toPixelFromDIP(var1)) - var2, Math.round(PixelUtil.toPixelFromDIP(var1 + 64.0F) - (float)var2));
      }

   }

   public void setRefreshing(boolean var1) {
      this.mRefreshing = var1;
      if(this.mDidLayout) {
         super.setRefreshing(var1);
      }

   }
}
