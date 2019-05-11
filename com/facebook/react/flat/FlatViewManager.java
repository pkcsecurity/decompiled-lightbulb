package com.facebook.react.flat;

import com.facebook.react.flat.FlatViewGroup;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

abstract class FlatViewManager extends ViewGroupManager<FlatViewGroup> {

   protected FlatViewGroup createViewInstance(ThemedReactContext var1) {
      return new FlatViewGroup(var1);
   }

   public void removeAllViews(FlatViewGroup var1) {
      var1.removeAllViewsInLayout();
   }

   public void setBackgroundColor(FlatViewGroup var1, int var2) {}
}
