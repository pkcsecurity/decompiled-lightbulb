package com.facebook.litho;

import android.support.annotation.Nullable;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.EventHandler;
import com.facebook.litho.FocusChangedEvent;
import com.facebook.litho.InterceptTouchEvent;
import com.facebook.litho.LongClickEvent;
import com.facebook.litho.TouchEvent;

public interface CommonProps {

   @Nullable
   EventHandler<ClickEvent> getClickHandler();

   @Nullable
   EventHandler<FocusChangedEvent> getFocusChangeHandler();

   boolean getFocusable();

   @Nullable
   EventHandler<InterceptTouchEvent> getInterceptTouchHandler();

   @Nullable
   EventHandler<LongClickEvent> getLongClickHandler();

   @Nullable
   EventHandler<TouchEvent> getTouchHandler();

   @Nullable
   String getTransitionKey();
}
