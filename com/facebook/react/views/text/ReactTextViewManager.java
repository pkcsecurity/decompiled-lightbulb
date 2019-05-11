package com.facebook.react.views.text;

import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.text.ReactTextAnchorViewManager;
import com.facebook.react.views.text.ReactTextShadowNode;
import com.facebook.react.views.text.ReactTextUpdate;
import com.facebook.react.views.text.ReactTextView;
import com.facebook.react.views.text.TextInlineImageSpan;

@ReactModule(
   name = "RCTText"
)
public class ReactTextViewManager extends ReactTextAnchorViewManager<ReactTextView, ReactTextShadowNode> {

   @VisibleForTesting
   public static final String REACT_CLASS = "RCTText";


   public ReactTextShadowNode createShadowNodeInstance() {
      return new ReactTextShadowNode();
   }

   public ReactTextView createViewInstance(ThemedReactContext var1) {
      return new ReactTextView(var1);
   }

   public String getName() {
      return "RCTText";
   }

   public Class<ReactTextShadowNode> getShadowNodeClass() {
      return ReactTextShadowNode.class;
   }

   protected void onAfterUpdateTransaction(ReactTextView var1) {
      super.onAfterUpdateTransaction(var1);
      var1.updateView();
   }

   public void updateExtraData(ReactTextView var1, Object var2) {
      ReactTextUpdate var3 = (ReactTextUpdate)var2;
      if(var3.containsImages()) {
         TextInlineImageSpan.possiblyUpdateInlineImageSpans(var3.getText(), var1);
      }

      var1.setText(var3);
   }
}
