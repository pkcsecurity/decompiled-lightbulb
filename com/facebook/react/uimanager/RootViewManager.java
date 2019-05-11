package com.facebook.react.uimanager;

import android.view.ViewGroup;
import com.facebook.react.uimanager.SizeMonitoringFrameLayout;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

public class RootViewManager extends ViewGroupManager<ViewGroup> {

   public static final String REACT_CLASS = "RootView";


   protected ViewGroup createViewInstance(ThemedReactContext var1) {
      return new SizeMonitoringFrameLayout(var1);
   }

   public String getName() {
      return "RootView";
   }
}
