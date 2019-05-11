package com.facebook.react.flat;

import android.view.ViewGroup;
import com.facebook.react.uimanager.RootViewManager;

class FlatRootViewManager extends RootViewManager {

   public void removeAllViews(ViewGroup var1) {
      var1.removeAllViewsInLayout();
   }
}
