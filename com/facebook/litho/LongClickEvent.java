package com.facebook.litho;

import android.view.View;
import com.facebook.litho.annotations.Event;

@Event(
   returnType = Boolean.class
)
public class LongClickEvent {

   public View view;


}
