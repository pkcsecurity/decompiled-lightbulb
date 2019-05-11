package com.facebook.react.uimanager;

import android.view.View;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.LayoutShadowNode;

public abstract class SimpleViewManager<T extends View> extends BaseViewManager<T, LayoutShadowNode> {

   public LayoutShadowNode createShadowNodeInstance() {
      return new LayoutShadowNode();
   }

   public Class<LayoutShadowNode> getShadowNodeClass() {
      return LayoutShadowNode.class;
   }

   public void updateExtraData(T var1, Object var2) {}
}
