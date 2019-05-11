package com.facebook.litho;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.util.Pools;
import android.support.v4.util.SparseArrayCompat;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import com.facebook.litho.ComponentHost;
import com.facebook.litho.ComponentHostUtils;

class TouchExpansionDelegate extends TouchDelegate {

   private static final Rect IGNORED_RECT = new Rect();
   private static final Pools.SimplePool<SparseArrayCompat<TouchExpansionDelegate.InnerTouchDelegate>> sInnerTouchDelegateScrapArrayPool = new Pools.SimplePool(4);
   private final SparseArrayCompat<TouchExpansionDelegate.InnerTouchDelegate> mDelegates = new SparseArrayCompat();
   private SparseArrayCompat<TouchExpansionDelegate.InnerTouchDelegate> mScrapDelegates;


   TouchExpansionDelegate(ComponentHost var1) {
      super(IGNORED_RECT, var1);
   }

   private static SparseArrayCompat<TouchExpansionDelegate.InnerTouchDelegate> acquireScrapTouchDelegatesArray() {
      SparseArrayCompat var1 = (SparseArrayCompat)sInnerTouchDelegateScrapArrayPool.acquire();
      SparseArrayCompat var0 = var1;
      if(var1 == null) {
         var0 = new SparseArrayCompat(4);
      }

      return var0;
   }

   private void ensureScrapDelegates() {
      if(this.mScrapDelegates == null) {
         this.mScrapDelegates = acquireScrapTouchDelegatesArray();
      }

   }

   private boolean maybeUnregisterFromScrap(int var1) {
      if(this.mScrapDelegates != null) {
         var1 = this.mScrapDelegates.indexOfKey(var1);
         if(var1 >= 0) {
            TouchExpansionDelegate.InnerTouchDelegate var2 = (TouchExpansionDelegate.InnerTouchDelegate)this.mScrapDelegates.valueAt(var1);
            this.mScrapDelegates.removeAt(var1);
            var2.release();
            return true;
         }
      }

      return false;
   }

   private void releaseScrapDelegatesIfNeeded() {
      if(this.mScrapDelegates != null && this.mScrapDelegates.size() == 0) {
         releaseScrapTouchDelegatesArray(this.mScrapDelegates);
         this.mScrapDelegates = null;
      }

   }

   private static void releaseScrapTouchDelegatesArray(SparseArrayCompat<TouchExpansionDelegate.InnerTouchDelegate> var0) {
      sInnerTouchDelegateScrapArrayPool.release(var0);
   }

   void draw(Canvas var1, Paint var2) {
      for(int var3 = this.mDelegates.size() - 1; var3 >= 0; --var3) {
         var1.drawRect(((TouchExpansionDelegate.InnerTouchDelegate)this.mDelegates.valueAt(var3)).mDelegateBounds, var2);
      }

   }

   void moveTouchExpansionIndexes(int var1, int var2) {
      if(this.mDelegates.get(var2) != null) {
         this.ensureScrapDelegates();
         ComponentHostUtils.scrapItemAt(var2, this.mDelegates, this.mScrapDelegates);
      }

      ComponentHostUtils.moveItem(var1, var2, this.mDelegates, this.mScrapDelegates);
      this.releaseScrapDelegatesIfNeeded();
   }

   public boolean onTouchEvent(MotionEvent var1) {
      for(int var2 = this.mDelegates.size() - 1; var2 >= 0; --var2) {
         if(((TouchExpansionDelegate.InnerTouchDelegate)this.mDelegates.valueAt(var2)).onTouchEvent(var1)) {
            return true;
         }
      }

      return false;
   }

   void registerTouchExpansion(int var1, View var2, Rect var3) {
      this.mDelegates.put(var1, TouchExpansionDelegate.InnerTouchDelegate.acquire(var2, var3));
   }

   void unregisterTouchExpansion(int var1) {
      if(!this.maybeUnregisterFromScrap(var1)) {
         var1 = this.mDelegates.indexOfKey(var1);
         TouchExpansionDelegate.InnerTouchDelegate var2 = (TouchExpansionDelegate.InnerTouchDelegate)this.mDelegates.valueAt(var1);
         this.mDelegates.removeAt(var1);
         var2.release();
      }
   }

   static class InnerTouchDelegate {

      private static final Pools.SimplePool<TouchExpansionDelegate.InnerTouchDelegate> sPool = new Pools.SimplePool(4);
      private final Rect mDelegateBounds = new Rect();
      private final Rect mDelegateSlopBounds = new Rect();
      private View mDelegateView;
      private boolean mIsHandlingTouch;
      private int mSlop;


      static TouchExpansionDelegate.InnerTouchDelegate acquire(View var0, Rect var1) {
         TouchExpansionDelegate.InnerTouchDelegate var3 = (TouchExpansionDelegate.InnerTouchDelegate)sPool.acquire();
         TouchExpansionDelegate.InnerTouchDelegate var2 = var3;
         if(var3 == null) {
            var2 = new TouchExpansionDelegate.InnerTouchDelegate();
         }

         var2.init(var0, var1);
         return var2;
      }

      void init(View var1, Rect var2) {
         this.mDelegateView = var1;
         this.mSlop = ViewConfiguration.get(var1.getContext()).getScaledTouchSlop();
         this.mDelegateBounds.set(var2);
         this.mDelegateSlopBounds.set(var2);
         this.mDelegateSlopBounds.inset(-this.mSlop, -this.mSlop);
      }

      boolean onTouchEvent(MotionEvent var1) {
         int var3 = (int)var1.getX();
         int var4 = (int)var1.getY();
         int var5 = var1.getAction();
         boolean var2 = true;
         boolean var7 = false;
         boolean var6;
         switch(var5) {
         case 0:
            this.mIsHandlingTouch = this.mDelegateBounds.contains(var3, var4);
            var6 = this.mIsHandlingTouch;
            break;
         case 1:
         case 2:
            var6 = this.mIsHandlingTouch;
            if(this.mIsHandlingTouch && !this.mDelegateSlopBounds.contains(var3, var4)) {
               var2 = false;
            } else {
               var2 = true;
            }

            if(var1.getAction() == 1) {
               this.mIsHandlingTouch = false;
            }
            break;
         case 3:
            var6 = this.mIsHandlingTouch;
            this.mIsHandlingTouch = false;
            break;
         default:
            var6 = false;
         }

         if(var6) {
            if(var2) {
               var1.setLocation((float)(this.mDelegateView.getWidth() / 2), (float)(this.mDelegateView.getHeight() / 2));
            } else {
               var1.setLocation((float)(-(this.mSlop * 2)), (float)(-(this.mSlop * 2)));
            }

            var7 = this.mDelegateView.dispatchTouchEvent(var1);
         }

         return var7;
      }

      void release() {
         this.mDelegateView = null;
         this.mDelegateBounds.setEmpty();
         this.mDelegateSlopBounds.setEmpty();
         this.mIsHandlingTouch = false;
         this.mSlop = 0;
         sPool.release(this);
      }
   }
}
