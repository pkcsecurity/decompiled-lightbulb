package android.support.v7.widget;

import android.os.SystemClock;
import android.support.annotation.RestrictTo;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.DropDownListView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnTouchListener;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public abstract class ForwardingListener implements OnAttachStateChangeListener, OnTouchListener {

   private int mActivePointerId;
   private Runnable mDisallowIntercept;
   private boolean mForwarding;
   private final int mLongPressTimeout;
   private final float mScaledTouchSlop;
   final View mSrc;
   private final int mTapTimeout;
   private final int[] mTmpLocation = new int[2];
   private Runnable mTriggerLongPress;


   public ForwardingListener(View var1) {
      this.mSrc = var1;
      var1.setLongClickable(true);
      var1.addOnAttachStateChangeListener(this);
      this.mScaledTouchSlop = (float)ViewConfiguration.get(var1.getContext()).getScaledTouchSlop();
      this.mTapTimeout = ViewConfiguration.getTapTimeout();
      this.mLongPressTimeout = (this.mTapTimeout + ViewConfiguration.getLongPressTimeout()) / 2;
   }

   private void clearCallbacks() {
      if(this.mTriggerLongPress != null) {
         this.mSrc.removeCallbacks(this.mTriggerLongPress);
      }

      if(this.mDisallowIntercept != null) {
         this.mSrc.removeCallbacks(this.mDisallowIntercept);
      }

   }

   private boolean onTouchForwarded(MotionEvent var1) {
      View var4 = this.mSrc;
      ShowableListMenu var5 = this.getPopup();
      if(var5 != null) {
         if(!var5.isShowing()) {
            return false;
         } else {
            DropDownListView var8 = (DropDownListView)var5.getListView();
            if(var8 != null) {
               if(!var8.isShown()) {
                  return false;
               } else {
                  MotionEvent var6 = MotionEvent.obtainNoHistory(var1);
                  this.toGlobalMotionEvent(var4, var6);
                  this.toLocalMotionEvent(var8, var6);
                  boolean var3 = var8.onForwardedEvent(var6, this.mActivePointerId);
                  var6.recycle();
                  int var2 = var1.getActionMasked();
                  boolean var7;
                  if(var2 != 1 && var2 != 3) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  return var3 && var7;
               }
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private boolean onTouchObserved(MotionEvent var1) {
      View var3 = this.mSrc;
      if(!var3.isEnabled()) {
         return false;
      } else {
         switch(var1.getActionMasked()) {
         case 0:
            this.mActivePointerId = var1.getPointerId(0);
            if(this.mDisallowIntercept == null) {
               this.mDisallowIntercept = new ForwardingListener.DisallowIntercept();
            }

            var3.postDelayed(this.mDisallowIntercept, (long)this.mTapTimeout);
            if(this.mTriggerLongPress == null) {
               this.mTriggerLongPress = new ForwardingListener.TriggerLongPress();
            }

            var3.postDelayed(this.mTriggerLongPress, (long)this.mLongPressTimeout);
            break;
         case 1:
         case 3:
            this.clearCallbacks();
            return false;
         case 2:
            int var2 = var1.findPointerIndex(this.mActivePointerId);
            if(var2 >= 0 && !pointInView(var3, var1.getX(var2), var1.getY(var2), this.mScaledTouchSlop)) {
               this.clearCallbacks();
               var3.getParent().requestDisallowInterceptTouchEvent(true);
               return true;
            }
            break;
         default:
            return false;
         }

         return false;
      }
   }

   private static boolean pointInView(View var0, float var1, float var2, float var3) {
      float var4 = -var3;
      return var1 >= var4 && var2 >= var4 && var1 < (float)(var0.getRight() - var0.getLeft()) + var3 && var2 < (float)(var0.getBottom() - var0.getTop()) + var3;
   }

   private boolean toGlobalMotionEvent(View var1, MotionEvent var2) {
      int[] var3 = this.mTmpLocation;
      var1.getLocationOnScreen(var3);
      var2.offsetLocation((float)var3[0], (float)var3[1]);
      return true;
   }

   private boolean toLocalMotionEvent(View var1, MotionEvent var2) {
      int[] var3 = this.mTmpLocation;
      var1.getLocationOnScreen(var3);
      var2.offsetLocation((float)(-var3[0]), (float)(-var3[1]));
      return true;
   }

   public abstract ShowableListMenu getPopup();

   public boolean onForwardingStarted() {
      ShowableListMenu var1 = this.getPopup();
      if(var1 != null && !var1.isShowing()) {
         var1.show();
      }

      return true;
   }

   protected boolean onForwardingStopped() {
      ShowableListMenu var1 = this.getPopup();
      if(var1 != null && var1.isShowing()) {
         var1.dismiss();
      }

      return true;
   }

   void onLongPress() {
      this.clearCallbacks();
      View var3 = this.mSrc;
      if(var3.isEnabled()) {
         if(!var3.isLongClickable()) {
            if(this.onForwardingStarted()) {
               var3.getParent().requestDisallowInterceptTouchEvent(true);
               long var1 = SystemClock.uptimeMillis();
               MotionEvent var4 = MotionEvent.obtain(var1, var1, 3, 0.0F, 0.0F, 0);
               var3.onTouchEvent(var4);
               var4.recycle();
               this.mForwarding = true;
            }
         }
      }
   }

   public boolean onTouch(View var1, MotionEvent var2) {
      boolean var6 = this.mForwarding;
      boolean var5 = true;
      boolean var3;
      boolean var4;
      if(var6) {
         if(!this.onTouchForwarded(var2) && this.onForwardingStopped()) {
            var3 = false;
         } else {
            var3 = true;
         }
      } else {
         if(this.onTouchObserved(var2) && this.onForwardingStarted()) {
            var4 = true;
         } else {
            var4 = false;
         }

         var3 = var4;
         if(var4) {
            long var7 = SystemClock.uptimeMillis();
            MotionEvent var9 = MotionEvent.obtain(var7, var7, 3, 0.0F, 0.0F, 0);
            this.mSrc.onTouchEvent(var9);
            var9.recycle();
            var3 = var4;
         }
      }

      this.mForwarding = var3;
      var4 = var5;
      if(!var3) {
         if(var6) {
            return true;
         }

         var4 = false;
      }

      return var4;
   }

   public void onViewAttachedToWindow(View var1) {}

   public void onViewDetachedFromWindow(View var1) {
      this.mForwarding = false;
      this.mActivePointerId = -1;
      if(this.mDisallowIntercept != null) {
         this.mSrc.removeCallbacks(this.mDisallowIntercept);
      }

   }

   class DisallowIntercept implements Runnable {

      public void run() {
         ViewParent var1 = ForwardingListener.this.mSrc.getParent();
         if(var1 != null) {
            var1.requestDisallowInterceptTouchEvent(true);
         }

      }
   }

   class TriggerLongPress implements Runnable {

      public void run() {
         ForwardingListener.this.onLongPress();
      }
   }
}
