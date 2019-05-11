package com.facebook.react.views.scroll;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.widget.OverScroller;
import android.widget.ScrollView;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.MeasureSpecAssertions;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import com.facebook.react.uimanager.events.NativeGestureUtil;
import com.facebook.react.views.scroll.FpsListener;
import com.facebook.react.views.scroll.OnScrollDispatchHelper;
import com.facebook.react.views.scroll.ReactScrollViewHelper;
import com.facebook.react.views.scroll.VelocityHelper;
import com.facebook.react.views.view.ReactViewBackgroundManager;
import java.lang.reflect.Field;
import javax.annotation.Nullable;

public class ReactScrollView extends ScrollView implements OnLayoutChangeListener, OnHierarchyChangeListener, ReactClippingViewGroup {

   private static Field sScrollerField;
   private static boolean sTriedToGetScrollerField;
   @Nullable
   private Rect mClippingRect;
   private View mContentView;
   private boolean mDoneFlinging;
   private boolean mDragging;
   @Nullable
   private Drawable mEndBackground;
   private int mEndFillColor;
   private boolean mFlinging;
   @Nullable
   private FpsListener mFpsListener;
   private final OnScrollDispatchHelper mOnScrollDispatchHelper;
   private ReactViewBackgroundManager mReactBackgroundManager;
   private boolean mRemoveClippedSubviews;
   private boolean mScrollEnabled;
   @Nullable
   private String mScrollPerfTag;
   private final OverScroller mScroller;
   private boolean mSendMomentumEvents;
   private final VelocityHelper mVelocityHelper;


   public ReactScrollView(ReactContext var1) {
      this(var1, (FpsListener)null);
   }

   public ReactScrollView(ReactContext var1, @Nullable FpsListener var2) {
      super(var1);
      this.mOnScrollDispatchHelper = new OnScrollDispatchHelper();
      this.mVelocityHelper = new VelocityHelper();
      this.mScrollEnabled = true;
      this.mFpsListener = null;
      this.mEndFillColor = 0;
      this.mFpsListener = var2;
      this.mReactBackgroundManager = new ReactViewBackgroundManager(this);
      if(!sTriedToGetScrollerField) {
         sTriedToGetScrollerField = true;

         try {
            sScrollerField = ScrollView.class.getDeclaredField("mScroller");
            sScrollerField.setAccessible(true);
         } catch (NoSuchFieldException var4) {
            Log.w("ReactNative", "Failed to get mScroller field for ScrollView! This app will exhibit the bounce-back scrolling bug :(");
         }
      }

      if(sScrollerField != null) {
         try {
            Object var5 = sScrollerField.get(this);
            if(var5 instanceof OverScroller) {
               this.mScroller = (OverScroller)var5;
            } else {
               Log.w("ReactNative", "Failed to cast mScroller field in ScrollView (probably due to OEM changes to AOSP)! This app will exhibit the bounce-back scrolling bug :(");
               this.mScroller = null;
            }
         } catch (IllegalAccessException var3) {
            throw new RuntimeException("Failed to get mScroller from ScrollView!", var3);
         }
      } else {
         this.mScroller = null;
      }

      this.setOnHierarchyChangeListener(this);
      this.setScrollBarStyle(33554432);
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

   private int getMaxScrollY() {
      return Math.max(0, this.mContentView.getHeight() - (this.getHeight() - this.getPaddingBottom() - this.getPaddingTop()));
   }

   private boolean isScrollPerfLoggingEnabled() {
      return this.mFpsListener != null && this.mScrollPerfTag != null && !this.mScrollPerfTag.isEmpty();
   }

   public void draw(Canvas var1) {
      if(this.mEndFillColor != 0) {
         View var2 = this.getChildAt(0);
         if(this.mEndBackground != null && var2 != null && var2.getBottom() < this.getHeight()) {
            this.mEndBackground.setBounds(0, var2.getBottom(), this.getWidth(), this.getHeight());
            this.mEndBackground.draw(var1);
         }
      }

      super.draw(var1);
   }

   public void flashScrollIndicators() {
      this.awakenScrollBars();
   }

   public void fling(int var1) {
      if(this.mScroller != null) {
         int var2 = this.getHeight();
         int var3 = this.getPaddingBottom();
         int var4 = this.getPaddingTop();
         this.mScroller.fling(this.getScrollX(), this.getScrollY(), 0, var1, 0, 0, 0, Integer.MAX_VALUE, 0, (var2 - var3 - var4) / 2);
         this.postInvalidateOnAnimation();
      } else {
         super.fling(var1);
      }

      if(this.mSendMomentumEvents || this.isScrollPerfLoggingEnabled()) {
         this.mFlinging = true;
         this.enableFpsListener();
         ReactScrollViewHelper.emitScrollMomentumBeginEvent(this);
         this.postOnAnimationDelayed(new Runnable() {
            public void run() {
               if(ReactScrollView.this.mDoneFlinging) {
                  ReactScrollView.this.mFlinging = false;
                  ReactScrollView.this.disableFpsListener();
                  ReactScrollViewHelper.emitScrollMomentumEndEvent(ReactScrollView.this);
               } else {
                  ReactScrollView.this.mDoneFlinging = true;
                  ReactScrollView.this.postOnAnimationDelayed(this, 20L);
               }
            }
         }, 20L);
      }

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

   public void onChildViewAdded(View var1, View var2) {
      this.mContentView = var2;
      this.mContentView.addOnLayoutChangeListener(this);
   }

   public void onChildViewRemoved(View var1, View var2) {
      this.mContentView.removeOnLayoutChangeListener(this);
      this.mContentView = null;
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

   public void onLayoutChange(View var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      if(this.mContentView != null) {
         var2 = this.getScrollY();
         var3 = this.getMaxScrollY();
         if(var2 > var3) {
            this.scrollTo(this.getScrollX(), var3);
         }

      }
   }

   protected void onMeasure(int var1, int var2) {
      MeasureSpecAssertions.assertExplicitMeasureSpec(var1, var2);
      this.setMeasuredDimension(MeasureSpec.getSize(var1), MeasureSpec.getSize(var2));
   }

   protected void onOverScrolled(int var1, int var2, boolean var3, boolean var4) {
      int var5 = var2;
      if(this.mScroller != null) {
         var5 = var2;
         if(!this.mScroller.isFinished()) {
            var5 = var2;
            if(this.mScroller.getCurrY() != this.mScroller.getFinalY()) {
               int var6 = this.getMaxScrollY();
               var5 = var2;
               if(var2 >= var6) {
                  this.mScroller.abortAnimation();
                  var5 = var6;
               }
            }
         }
      }

      super.onOverScrolled(var1, var5, var3, var4);
   }

   protected void onScrollChanged(int var1, int var2, int var3, int var4) {
      super.onScrollChanged(var1, var2, var3, var4);
      if(this.mOnScrollDispatchHelper.onScrollChanged(var1, var2)) {
         if(this.mRemoveClippedSubviews) {
            this.updateClippingRect();
         }

         if(this.mFlinging) {
            this.mDoneFlinging = false;
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
            this.disableFpsListener();
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

   public void setScrollPerfTag(String var1) {
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
