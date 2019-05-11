package com.facebook.react.views.modal;

import android.graphics.Point;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.views.modal.ModalHostHelper;

class ModalHostShadowNode extends LayoutShadowNode {

   public void addChildAt(ReactShadowNodeImpl var1, int var2) {
      super.addChildAt(var1, var2);
      Point var3 = ModalHostHelper.getModalHostSize(this.getThemedContext());
      var1.setStyleWidth((float)var3.x);
      var1.setStyleHeight((float)var3.y);
   }
}
