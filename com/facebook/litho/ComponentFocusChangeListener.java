package com.facebook.litho;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import com.facebook.litho.EventDispatcherUtils;
import com.facebook.litho.EventHandler;
import com.facebook.litho.FocusChangedEvent;

class ComponentFocusChangeListener implements OnFocusChangeListener {

   private EventHandler<FocusChangedEvent> mEventHandler;


   EventHandler<FocusChangedEvent> getEventHandler() {
      return this.mEventHandler;
   }

   public void onFocusChange(View var1, boolean var2) {
      if(this.mEventHandler != null) {
         EventDispatcherUtils.dispatchOnFocusChanged(this.mEventHandler, var1, var2);
      }

   }

   void setEventHandler(EventHandler<FocusChangedEvent> var1) {
      this.mEventHandler = var1;
   }
}
