package com.facebook.react.touch;

import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.facebook.react.touch.OnInterceptTouchEventListener;
import javax.annotation.Nullable;

public class JSResponderHandler implements OnInterceptTouchEventListener {

   private static final int JS_RESPONDER_UNSET = -1;
   private volatile int mCurrentJSResponder = -1;
   @Nullable
   private ViewParent mViewParentBlockingNativeResponder;


   private void maybeUnblockNativeResponder() {
      if(this.mViewParentBlockingNativeResponder != null) {
         this.mViewParentBlockingNativeResponder.requestDisallowInterceptTouchEvent(false);
         this.mViewParentBlockingNativeResponder = null;
      }

   }

   public void clearJSResponder() {
      this.mCurrentJSResponder = -1;
      this.maybeUnblockNativeResponder();
   }

   public boolean onInterceptTouchEvent(ViewGroup var1, MotionEvent var2) {
      int var3 = this.mCurrentJSResponder;
      boolean var4 = false;
      if(var3 != -1 && var2.getAction() != 1) {
         if(var1.getId() == var3) {
            var4 = true;
         }

         return var4;
      } else {
         return false;
      }
   }

   public void setJSResponder(int var1, @Nullable ViewParent var2) {
      this.mCurrentJSResponder = var1;
      this.maybeUnblockNativeResponder();
      if(var2 != null) {
         var2.requestDisallowInterceptTouchEvent(true);
         this.mViewParentBlockingNativeResponder = var2;
      }

   }
}
