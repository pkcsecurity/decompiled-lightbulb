package com.facebook.react.flat;

import android.view.View;
import com.facebook.react.flat.FlatShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManager;

abstract class VirtualViewManager<C extends FlatShadowNode> extends ViewManager<View, C> {

   protected View createViewInstance(ThemedReactContext var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(this.getName());
      var2.append(" doesn\'t map to a View");
      throw new RuntimeException(var2.toString());
   }

   public void updateExtraData(View var1, Object var2) {}
}
