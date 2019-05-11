package com.facebook.litho.widget;

import android.view.KeyEvent;
import com.facebook.litho.annotations.Event;

@Event(
   returnType = Boolean.class
)
public class EditorActionEvent {

   public int actionId;
   public KeyEvent event;


}
