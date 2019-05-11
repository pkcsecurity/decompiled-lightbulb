package com.facebook.litho;

import com.facebook.litho.annotations.Event;

@Event
public class VisibilityChangedEvent {

   public float percentVisibleHeight;
   public float percentVisibleWidth;
   public int visibleHeight;
   public int visibleWidth;


}
