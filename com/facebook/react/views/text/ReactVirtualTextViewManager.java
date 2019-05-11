package com.facebook.react.views.text;

import android.view.View;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.text.ReactVirtualTextShadowNode;

@ReactModule(
   name = "RCTVirtualText"
)
public class ReactVirtualTextViewManager extends BaseViewManager<View, ReactVirtualTextShadowNode> {

   @VisibleForTesting
   public static final String REACT_CLASS = "RCTVirtualText";


   public ReactVirtualTextShadowNode createShadowNodeInstance() {
      return new ReactVirtualTextShadowNode();
   }

   public View createViewInstance(ThemedReactContext var1) {
      throw new IllegalStateException("Attempt to create a native view for RCTVirtualText");
   }

   public String getName() {
      return "RCTVirtualText";
   }

   public Class<ReactVirtualTextShadowNode> getShadowNodeClass() {
      return ReactVirtualTextShadowNode.class;
   }

   public void updateExtraData(View var1, Object var2) {}
}
