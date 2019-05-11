package com.facebook.litho.sections.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.facebook.litho.annotations.Event;
import com.facebook.litho.widget.RenderInfo;

@Event(
   returnType = RenderInfo.class
)
public class RenderEvent {

   public int index;
   @Nullable
   public Bundle loggingExtras;
   public Object model;


}
