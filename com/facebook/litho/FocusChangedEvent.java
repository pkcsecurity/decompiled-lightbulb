package com.facebook.litho;

import android.view.View;
import com.facebook.litho.annotations.Event;

@Event
public class FocusChangedEvent {

   public boolean hasFocus;
   public View view;


}
