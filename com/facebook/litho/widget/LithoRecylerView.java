package com.facebook.litho.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.facebook.litho.widget.HasPostDispatchDrawListener;
import com.facebook.litho.widget.PostDispatchDrawListener;

public class LithoRecylerView extends RecyclerView implements HasPostDispatchDrawListener {

   @Nullable
   private PostDispatchDrawListener mPostDispatchDrawListener;
   @Nullable
   private LithoRecylerView.TouchInterceptor mTouchInterceptor;


   public LithoRecylerView(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public LithoRecylerView(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public LithoRecylerView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   protected void dispatchDraw(Canvas var1) {
      super.dispatchDraw(var1);
      if(this.mPostDispatchDrawListener != null) {
         this.mPostDispatchDrawListener.postDispatchDraw();
      }

   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      if(this.mTouchInterceptor == null) {
         return super.onInterceptTouchEvent(var1);
      } else {
         LithoRecylerView.Result var2 = this.mTouchInterceptor.onInterceptTouchEvent(this, var1);
         switch(null.$SwitchMap$com$facebook$litho$widget$LithoRecylerView$TouchInterceptor$Result[var2.ordinal()]) {
         case 1:
            return true;
         case 2:
            return false;
         case 3:
            return super.onInterceptTouchEvent(var1);
         default:
            StringBuilder var3 = new StringBuilder();
            var3.append("Unknown TouchInterceptor.Result: ");
            var3.append(var2);
            throw new IllegalArgumentException(var3.toString());
         }
      }
   }

   public void setPostDispatchDrawListener(@Nullable PostDispatchDrawListener var1) {
      this.mPostDispatchDrawListener = var1;
   }

   public void setTouchInterceptor(@Nullable LithoRecylerView.TouchInterceptor var1) {
      this.mTouchInterceptor = var1;
   }

   public interface TouchInterceptor {

      LithoRecylerView.Result onInterceptTouchEvent(RecyclerView var1, MotionEvent var2);
   }

   public static enum Result {

      // $FF: synthetic field
      private static final LithoRecylerView.Result[] $VALUES = new LithoRecylerView.Result[]{INTERCEPT_TOUCH_EVENT, IGNORE_TOUCH_EVENT, CALL_SUPER};
      CALL_SUPER("CALL_SUPER", 2),
      IGNORE_TOUCH_EVENT("IGNORE_TOUCH_EVENT", 1),
      INTERCEPT_TOUCH_EVENT("INTERCEPT_TOUCH_EVENT", 0);


      private Result(String var1, int var2) {}
   }
}
