package com.facebook.react.views.scroll;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.HorizontalScrollView;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.uimanager.MeasureSpecAssertions;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import com.facebook.react.uimanager.events.NativeGestureUtil;
import com.facebook.react.views.scroll.FpsListener;
import com.facebook.react.views.scroll.OnScrollDispatchHelper;
import com.facebook.react.views.scroll.ReactScrollViewHelper;
import com.facebook.react.views.scroll.VelocityHelper;
import com.facebook.react.views.view.ReactViewBackgroundManager;
import javax.annotation.Nullable;

public class ReactHorizontalScrollView extends HorizontalScrollView implements ReactClippingViewGroup {

   private boolean mActivelyScrolling;
   @Nullable
   private Rect mClippingRect;
   private boolean mDragging;
   @Nullable
   private Drawable mEndBackground;
   private int mEndFillColor;
   @Nullable
   private FpsListener mFpsListener;
   private final OnScrollDispatchHelper mOnScrollDispatchHelper;
   private boolean mPagingEnabled;
   @Nullable
   private Runnable mPostTouchRunnable;
   private ReactViewBackgroundManager mReactBackgroundManager;
   private boolean mRemoveClippedSubviews;
   private boolean mScrollEnabled;
   @Nullable
   private String mScrollPerfTag;
   private boolean mSendMomentumEvents;
   private final VelocityHelper mVelocityHelper;


   public ReactHorizontalScrollView(Context var1) {
      this(var1, (FpsListener)null);
   }

   public ReactHorizontalScrollView(Context var1, @Nullable FpsListener var2) {
      super(var1);
      this.mOnScrollDispatchHelper = new OnScrollDispatchHelper();
      this.mVelocityHelper = new VelocityHelper();
      this.mPagingEnabled = false;
      this.mScrollEnabled = true;
      this.mFpsListener = null;
      this.mEndFillColor = 0;
      this.mReactBackgroundManager = new ReactViewBackgroundManager(this);
      this.mFpsListener = var2;
   }

   private void disableFpsListener() {
      if(this.isScrollPerfLoggingEnabled()) {
         Assertions.assertNotNull(this.mFpsListener);
         Assertions.assertNotNull(this.mScrollPerfTag);
         this.mFpsListener.disable(this.mScrollPerfTag);
      }

   }

   private void enableFpsListener() {
      if(this.isScrollPerfLoggingEnabled()) {
         Assertions.assertNotNull(this.mFpsListener);
         Assertions.assertNotNull(this.mScrollPerfTag);
         this.mFpsListener.enable(this.mScrollPerfTag);
      }

   }

   @TargetApi(16)
   private void handlePostTouchScrolling() {
      if(this.mSendMomentumEvents || this.mPagingEnabled || this.isScrollPerfLoggingEnabled()) {
         if(this.mPostTouchRunnable == null) {
            if(this.mSendMomentumEvents) {
               ReactScrollViewHelper.emitScrollMomentumBeginEvent(this);
            }

            this.mActivelyScrolling = false;
            this.mPostTouchRunnable = new Runnable() {

               private boolean mSnappingToPage = false;

               public void run() {
                  if(ReactHorizontalScrollView.this.mActivelyScrolling) {
                     ReactHorizontalScrollView.this.mActivelyScrolling = false;
                     ReactHorizontalScrollView.this.postOnAnimationDelayed(this, 20L);
                  } else if(ReactHorizontalScrollView.this.mPagingEnabled && !this.mSnappingToPage) {
                     this.mSnappingToPage = true;
                     ReactHorizontalScrollView.this.smoothScrollToPage(0);
                     ReactHorizontalScrollView.this.postOnAnimationDelayed(this, 20L);
                  } else {
                     if(ReactHorizontalScrollView.this.mSendMomentumEvents) {
                        ReactScrollViewHelper.emitScrollMomentumEndEvent(ReactHorizontalScrollView.this);
                     }

                     ReactHorizontalScrollView.this.mPostTouchRunnable = null;
                     ReactHorizontalScrollView.this.disableFpsListener();
                  }
               }
            };
            this.postOnAnimationDelayed(this.mPostTouchRunnable, 20L);
         }
      }
   }

   private boolean isScrollPerfLoggingEnabled() {
      return this.mFpsListener != null && this.mScrollPerfTag != null && !this.mScrollPerfTag.isEmpty();
   }

   private void smoothScrollToPage(int var1) {
      int var4 = this.getWidth();
      int var5 = this.getScrollX();
      int var3 = var5 / var4;
      int var2 = var3;
      if(var1 + var5 > var3 * var4 + var4 / 2) {
         var2 = var3 + 1;
      }

      this.smoothScrollTo(var2 * var4, this.getScrollY());
   }

   public void draw(Canvas var1) {
      if(this.mEndFillColor != 0) {
         View var2 = this.getChildAt(0);
         if(this.mEndBackground != null && var2 != null && var2.getRight() < this.getWidth()) {
            this.mEndBackground.setBounds(var2.getRight(), 0, this.getWidth(), this.getHeight());
            this.mEndBackground.draw(var1);
         }
      }

      super.draw(var1);
   }

   public void flashScrollIndicators() {
      this.awakenScrollBars();
   }

   public void fling(int var1) {
      if(this.mPagingEnabled) {
         this.smoothScrollToPage(var1);
      } else {
         super.fling(var1);
      }

      this.handlePostTouchScrolling();
   }

   public void getClippingRect(Rect var1) {
      var1.set((Rect)Assertions.assertNotNull(this.mClippingRect));
   }

   public boolean getRemoveClippedSubviews() {
      return this.mRemoveClippedSubviews;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      if(this.mRemoveClippedSubviews) {
         this.updateClippingRect();
      }

   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      if(!this.mScrollEnabled) {
         return false;
      } else if(super.onInterceptTouchEvent(var1)) {
         NativeGestureUtil.notifyNativeGestureStarted(this, var1);
         ReactScrollViewHelper.emitScrollBeginDragEvent(this);
         this.mDragging = true;
         this.enableFpsListener();
         return true;
      } else {
         return false;
      }
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      this.scrollTo(this.getScrollX(), this.getScrollY());
   }

   protected void onMeasure(int var1, int var2) {
      MeasureSpecAssertions.assertExplicitMeasureSpec(var1, var2);
      this.setMeasuredDimension(MeasureSpec.getSize(var1), MeasureSpec.getSize(var2));
   }

   protected void onScrollChanged(int var1, int var2, int var3, int var4) {
      super.onScrollChanged(var1, var2, var3, var4);
      this.mActivelyScrolling = true;
      if(this.mOnScrollDispatchHelper.onScrollChanged(var1, var2)) {
         if(this.mRemoveClippedSubviews) {
            this.updateClippingRect();
         }

         ReactScrollViewHelper.emitScrollEvent(this, this.mOnScrollDispatchHelper.getXFlingVelocity(), this.mOnScrollDispatchHelper.getYFlingVelocity());
      }

   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      super.onSizeChanged(var1, var2, var3, var4);
      if(this.mRemoveClippedSubviews) {
         this.updateClippingRect();
      }

   }

   public boolean onTouchEvent(MotionEvent var1) {
      if(!this.mScrollEnabled) {
         return false;
      } else {
         this.mVelocityHelper.calculateVelocity(var1);
         if((var1.getAction() & 255) == 1 && this.mDragging) {
            ReactScrollViewHelper.emitScrollEndDragEvent(this, this.mVelocityHelper.getXVelocity(), this.mVelocityHelper.getYVelocity());
            this.mDragging = false;
            this.handlePostTouchScrolling();
         }

         return super.onTouchEvent(var1);
      }
   }

   public void setBackgroundColor(int var1) {
      this.mReactBackgroundManager.setBackgroundColor(var1);
   }

   public void setBorderColor(int var1, float var2, float var3) {
      this.mReactBackgroundManager.setBorderColor(var1, var2, var3);
   }

   public void setBorderRadius(float var1) {
      this.mReactBackgroundManager.setBorderRadius(var1);
   }

   public void setBorderRadius(float var1, int var2) {
      this.mReactBackgroundManager.setBorderRadius(var1, var2);
   }

   public void setBorderStyle(@Nullable String var1) {
      this.mReactBackgroundManager.setBorderStyle(var1);
   }

   public void setBorderWidth(int var1, float var2) {
      this.mReactBackgroundManager.setBorderWidth(var1, var2);
   }

   public void setEndFillColor(int var1) {
      if(var1 != this.mEndFillColor) {
         this.mEndFillColor = var1;
         this.mEndBackground = new ColorDrawable(this.mEndFillColor);
      }

   }

   public void setPagingEnabled(boolean var1) {
      this.mPagingEnabled = var1;
   }

   public void setRemoveClippedSubviews(boolean var1) {
      if(var1 && this.mClippingRect == null) {
         this.mClippingRect = new Rect();
      }

      this.mRemoveClippedSubviews = var1;
      this.updateClippingRect();
   }

   public void setScrollEnabled(boolean var1) {
      this.mScrollEnabled = var1;
   }

   public void setScrollPerfTag(@Nullable String var1) {
      this.mScrollPerfTag = var1;
   }

   public void setSendMomentumEvents(boolean var1) {
      this.mSendMomentumEvents = var1;
   }

   public void updateClippingRect() {
      if(this.mRemoveClippedSubviews) {
         Assertions.assertNotNull(this.mClippingRect);
         ReactClippingViewGroupHelper.calculateClippingRect(this, this.mClippingRect);
         View var1 = this.getChildAt(0);
         if(var1 instanceof ReactClippingViewGroup) {
            ((ReactClippingViewGroup)var1).updateClippingRect();
         }

      }
   }
}
