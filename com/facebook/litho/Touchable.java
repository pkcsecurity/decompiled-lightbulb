package com.facebook.litho;

import android.view.MotionEvent;
import android.view.View;

public interface Touchable {

   boolean onTouchEvent(MotionEvent var1, View var2);

   boolean shouldHandleTouchEvent(MotionEvent var1);
}
