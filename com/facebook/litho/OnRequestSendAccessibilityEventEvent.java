package com.facebook.litho;

import android.support.v4.view.AccessibilityDelegateCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import com.facebook.litho.annotations.Event;

@Event(
   returnType = Boolean.class
)
public class OnRequestSendAccessibilityEventEvent {

   public View child;
   public AccessibilityEvent event;
   public ViewGroup host;
   public AccessibilityDelegateCompat superDelegate;


}
