package com.facebook.litho;

import android.view.MotionEvent;
import android.view.View;
import com.facebook.litho.annotations.Event;

@Event(
   returnType = Boolean.class
)
public class TouchEvent {

   public MotionEvent motionEvent;
   public View view;


}
