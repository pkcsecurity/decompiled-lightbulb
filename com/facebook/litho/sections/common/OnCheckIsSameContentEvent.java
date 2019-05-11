package com.facebook.litho.sections.common;

import com.facebook.litho.annotations.Event;

@Event(
   returnType = Boolean.class
)
public class OnCheckIsSameContentEvent {

   public Object nextItem;
   public Object previousItem;


}
