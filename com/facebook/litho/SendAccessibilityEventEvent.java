package com.facebook.litho;

import android.support.v4.view.AccessibilityDelegateCompat;
import android.view.View;
import com.facebook.litho.annotations.Event;

@Event
public class SendAccessibilityEventEvent {

   public int eventType;
   public View host;
   public AccessibilityDelegateCompat superDelegate;


}
