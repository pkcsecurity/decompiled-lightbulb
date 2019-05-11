package com.facebook.litho;

import android.view.View;
import android.view.View.OnClickListener;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.EventDispatcherUtils;
import com.facebook.litho.EventHandler;

class ComponentClickListener implements OnClickListener {

   private EventHandler<ClickEvent> mEventHandler;


   EventHandler<ClickEvent> getEventHandler() {
      return this.mEventHandler;
   }

   public void onClick(View var1) {
      if(this.mEventHandler != null) {
         EventDispatcherUtils.dispatchOnClick(this.mEventHandler, var1);
      }

   }

   void setEventHandler(EventHandler<ClickEvent> var1) {
      this.mEventHandler = var1;
   }
}
