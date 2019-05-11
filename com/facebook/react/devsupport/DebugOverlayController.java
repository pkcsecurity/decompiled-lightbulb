package com.facebook.react.devsupport;

import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.devsupport.FpsView;
import com.facebook.react.devsupport.WindowOverlayCompat;
import javax.annotation.Nullable;

class DebugOverlayController {

   @Nullable
   private FrameLayout mFPSDebugViewContainer;
   private final ReactContext mReactContext;
   private final WindowManager mWindowManager;


   public DebugOverlayController(ReactContext var1) {
      this.mReactContext = var1;
      this.mWindowManager = (WindowManager)var1.getSystemService("window");
   }

   public void setFpsDebugViewVisible(boolean var1) {
      if(var1 && this.mFPSDebugViewContainer == null) {
         this.mFPSDebugViewContainer = new FpsView(this.mReactContext);
         LayoutParams var2 = new LayoutParams(-1, -1, WindowOverlayCompat.TYPE_SYSTEM_OVERLAY, 24, -3);
         this.mWindowManager.addView(this.mFPSDebugViewContainer, var2);
      } else {
         if(!var1 && this.mFPSDebugViewContainer != null) {
            this.mFPSDebugViewContainer.removeAllViews();
            this.mWindowManager.removeView(this.mFPSDebugViewContainer);
            this.mFPSDebugViewContainer = null;
         }

      }
   }
}
