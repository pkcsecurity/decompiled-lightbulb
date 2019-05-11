package com.facebook.react.views.scroll;

import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.views.scroll.ReactHorizontalScrollContainerView;

@ReactModule(
   name = "AndroidHorizontalScrollContentView"
)
public class ReactHorizontalScrollContainerViewManager extends ViewGroupManager<ReactHorizontalScrollContainerView> {

   protected static final String REACT_CLASS = "AndroidHorizontalScrollContentView";


   public ReactHorizontalScrollContainerView createViewInstance(ThemedReactContext var1) {
      return new ReactHorizontalScrollContainerView(var1);
   }

   public String getName() {
      return "AndroidHorizontalScrollContentView";
   }
}
