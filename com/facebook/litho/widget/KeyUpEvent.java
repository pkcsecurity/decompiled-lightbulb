package com.facebook.litho.widget;

import android.view.KeyEvent;
import com.facebook.litho.annotations.Event;

@Event(
   returnType = Boolean.class
)
public class KeyUpEvent {

   public int keyCode;
   public KeyEvent keyEvent;


}
