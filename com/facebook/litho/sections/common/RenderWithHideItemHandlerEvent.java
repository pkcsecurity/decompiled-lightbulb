package com.facebook.litho.sections.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.facebook.litho.EventHandler;
import com.facebook.litho.annotations.Event;
import com.facebook.litho.sections.common.HideItemEvent;
import com.facebook.litho.widget.RenderInfo;

@Event(
   returnType = RenderInfo.class
)
public class RenderWithHideItemHandlerEvent {

   public EventHandler<HideItemEvent> hideItemHandler;
   public int index;
   @Nullable
   public Bundle loggingExtras;
   public Object model;


}
