package com.facebook.react.uimanager;

import android.graphics.Rect;

public interface ReactClippingViewGroup {

   void getClippingRect(Rect var1);

   boolean getRemoveClippedSubviews();

   void setRemoveClippedSubviews(boolean var1);

   void updateClippingRect();
}
