package com.facebook.litho;

import android.support.v4.view.AccessibilityDelegateCompat;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import com.facebook.litho.annotations.Event;

@Event
public class OnPopulateAccessibilityEventEvent {

   public AccessibilityEvent event;
   public View host;
   public AccessibilityDelegateCompat superDelegate;


}
