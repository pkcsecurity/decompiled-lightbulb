package com.facebook.litho;

import android.view.View;
import android.view.View.OnLongClickListener;
import com.facebook.litho.EventDispatcherUtils;
import com.facebook.litho.EventHandler;
import com.facebook.litho.LongClickEvent;

class ComponentLongClickListener implements OnLongClickListener {

   private EventHandler<LongClickEvent> mEventHandler;


   EventHandler<LongClickEvent> getEventHandler() {
      return this.mEventHandler;
   }

   public boolean onLongClick(View var1) {
      return this.mEventHandler != null?EventDispatcherUtils.dispatchOnLongClick(this.mEventHandler, var1):false;
   }

   void setEventHandler(EventHandler<LongClickEvent> var1) {
      this.mEventHandler = var1;
   }
}
