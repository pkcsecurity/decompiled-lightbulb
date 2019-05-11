package com.facebook.litho;

import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.view.View;
import com.facebook.litho.annotations.Event;

@Event(
   returnType = Boolean.class
)
public class PerformAccessibilityActionEvent {

   public int action;
   public Bundle args;
   public View host;
   public AccessibilityDelegateCompat superDelegate;


}
