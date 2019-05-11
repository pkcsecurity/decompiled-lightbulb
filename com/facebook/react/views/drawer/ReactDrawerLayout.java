package com.facebook.react.views.drawer;

import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;
import android.view.View;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.events.NativeGestureUtil;

class ReactDrawerLayout extends DrawerLayout {

   public static final int DEFAULT_DRAWER_WIDTH = -1;
   private int mDrawerPosition = 8388611;
   private int mDrawerWidth = -1;


   public ReactDrawerLayout(ReactContext var1) {
      super(var1);
   }

   void closeDrawer() {
      this.closeDrawer(this.mDrawerPosition);
   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      if(super.onInterceptTouchEvent(var1)) {
         NativeGestureUtil.notifyNativeGestureStarted(this, var1);
         return true;
      } else {
         return false;
      }
   }

   void openDrawer() {
      this.openDrawer(this.mDrawerPosition);
   }

   void setDrawerPosition(int var1) {
      this.mDrawerPosition = var1;
      this.setDrawerProperties();
   }

   void setDrawerProperties() {
      if(this.getChildCount() == 2) {
         View var1 = this.getChildAt(1);
         DrawerLayout.LayoutParams var2 = (DrawerLayout.LayoutParams)var1.getLayoutParams();
         var2.gravity = this.mDrawerPosition;
         var2.width = this.mDrawerWidth;
         var1.setLayoutParams(var2);
         var1.setClickable(true);
      }

   }

   void setDrawerWidth(int var1) {
      this.mDrawerWidth = var1;
      this.setDrawerProperties();
   }
}
