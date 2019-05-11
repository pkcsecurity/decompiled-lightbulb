package com.facebook.litho;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.facebook.litho.EventDispatcherUtils;
import com.facebook.litho.EventHandler;
import com.facebook.litho.TouchEvent;

class ComponentTouchListener implements OnTouchListener {

   private EventHandler<TouchEvent> mEventHandler;


   EventHandler<TouchEvent> getEventHandler() {
      return this.mEventHandler;
   }

   public boolean onTouch(View var1, MotionEvent var2) {
      return this.mEventHandler != null && EventDispatcherUtils.dispatchOnTouch(this.mEventHandler, var1, var2);
   }

   void setEventHandler(EventHandler<TouchEvent> var1) {
      this.mEventHandler = var1;
   }
}
