package com.facebook.litho;

import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;
import com.facebook.litho.annotations.Event;

@Event
public class OnInitializeAccessibilityNodeInfoEvent {

   public View host;
   public AccessibilityNodeInfoCompat info;
   public AccessibilityDelegateCompat superDelegate;


}
