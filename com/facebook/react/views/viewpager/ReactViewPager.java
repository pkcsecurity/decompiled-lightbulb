package com.facebook.react.views.viewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.NativeGestureUtil;
import com.facebook.react.views.viewpager.PageScrollEvent;
import com.facebook.react.views.viewpager.PageScrollStateChangedEvent;
import com.facebook.react.views.viewpager.PageSelectedEvent;
import java.util.ArrayList;
import java.util.List;

public class ReactViewPager extends ViewPager {

   private final EventDispatcher mEventDispatcher;
   private boolean mIsCurrentItemFromJs;
   private boolean mScrollEnabled = true;


   public ReactViewPager(ReactContext var1) {
      super(var1);
      this.mEventDispatcher = ((UIManagerModule)var1.getNativeModule(UIManagerModule.class)).getEventDispatcher();
      this.mIsCurrentItemFromJs = false;
      this.setOnPageChangeListener(new ReactViewPager.PageChangeListener(null));
      this.setAdapter(new ReactViewPager.Adapter(null));
   }

   void addViewToAdapter(View var1, int var2) {
      this.getAdapter().addView(var1, var2);
   }

   public ReactViewPager.Adapter getAdapter() {
      return (ReactViewPager.Adapter)super.getAdapter();
   }

   int getViewCountInAdapter() {
      return this.getAdapter().getCount();
   }

   View getViewFromAdapter(int var1) {
      return this.getAdapter().getViewAt(var1);
   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      if(!this.mScrollEnabled) {
         return false;
      } else if(super.onInterceptTouchEvent(var1)) {
         NativeGestureUtil.notifyNativeGestureStarted(this, var1);
         return true;
      } else {
         return false;
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      return !this.mScrollEnabled?false:super.onTouchEvent(var1);
   }

   public void removeAllViewsFromAdapter() {
      this.getAdapter().removeAllViewsFromAdapter(this);
   }

   void removeViewFromAdapter(int var1) {
      this.getAdapter().removeViewAt(var1);
   }

   public void setCurrentItemFromJs(int var1, boolean var2) {
      this.mIsCurrentItemFromJs = true;
      this.setCurrentItem(var1, var2);
      this.mIsCurrentItemFromJs = false;
   }

   public void setScrollEnabled(boolean var1) {
      this.mScrollEnabled = var1;
   }

   public void setViews(List<View> var1) {
      this.getAdapter().setViews(var1);
   }

   class PageChangeListener implements ViewPager.OnPageChangeListener {

      private PageChangeListener() {}

      // $FF: synthetic method
      PageChangeListener(Object var2) {
         this();
      }

      public void onPageScrollStateChanged(int var1) {
         String var2;
         switch(var1) {
         case 0:
            var2 = "idle";
            break;
         case 1:
            var2 = "dragging";
            break;
         case 2:
            var2 = "settling";
            break;
         default:
            throw new IllegalStateException("Unsupported pageScrollState");
         }

         ReactViewPager.this.mEventDispatcher.dispatchEvent(new PageScrollStateChangedEvent(ReactViewPager.this.getId(), var2));
      }

      public void onPageScrolled(int var1, float var2, int var3) {
         ReactViewPager.this.mEventDispatcher.dispatchEvent(new PageScrollEvent(ReactViewPager.this.getId(), var1, var2));
      }

      public void onPageSelected(int var1) {
         if(!ReactViewPager.this.mIsCurrentItemFromJs) {
            ReactViewPager.this.mEventDispatcher.dispatchEvent(new PageSelectedEvent(ReactViewPager.this.getId(), var1));
         }

      }
   }

   class Adapter extends PagerAdapter {

      private boolean mIsViewPagerInIntentionallyInconsistentState;
      private final List<View> mViews;


      private Adapter() {
         this.mViews = new ArrayList();
         this.mIsViewPagerInIntentionallyInconsistentState = false;
      }

      // $FF: synthetic method
      Adapter(Object var2) {
         this();
      }

      void addView(View var1, int var2) {
         this.mViews.add(var2, var1);
         this.notifyDataSetChanged();
         ReactViewPager.this.setOffscreenPageLimit(this.mViews.size());
      }

      public void destroyItem(ViewGroup var1, int var2, Object var3) {
         var1.removeView((View)var3);
      }

      public int getCount() {
         return this.mViews.size();
      }

      public int getItemPosition(Object var1) {
         return !this.mIsViewPagerInIntentionallyInconsistentState && this.mViews.contains(var1)?this.mViews.indexOf(var1):-2;
      }

      View getViewAt(int var1) {
         return (View)this.mViews.get(var1);
      }

      public Object instantiateItem(ViewGroup var1, int var2) {
         View var3 = (View)this.mViews.get(var2);
         var1.addView(var3, 0, ReactViewPager.this.generateDefaultLayoutParams());
         return var3;
      }

      public boolean isViewFromObject(View var1, Object var2) {
         return var1 == var2;
      }

      void removeAllViewsFromAdapter(ViewPager var1) {
         this.mViews.clear();
         var1.removeAllViews();
         this.mIsViewPagerInIntentionallyInconsistentState = true;
      }

      void removeViewAt(int var1) {
         this.mViews.remove(var1);
         this.notifyDataSetChanged();
         ReactViewPager.this.setOffscreenPageLimit(this.mViews.size());
      }

      void setViews(List<View> var1) {
         this.mViews.clear();
         this.mViews.addAll(var1);
         this.notifyDataSetChanged();
         this.mIsViewPagerInIntentionallyInconsistentState = false;
      }
   }
}
