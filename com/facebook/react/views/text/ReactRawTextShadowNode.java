package com.facebook.react.views.text;

import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.annotations.ReactProp;
import javax.annotation.Nullable;

public class ReactRawTextShadowNode extends ReactShadowNodeImpl {

   @VisibleForTesting
   public static final String PROP_TEXT = "text";
   @Nullable
   private String mText = null;


   @Nullable
   public String getText() {
      return this.mText;
   }

   public boolean isVirtual() {
      return true;
   }

   @ReactProp(
      name = "text"
   )
   public void setText(@Nullable String var1) {
      this.mText = var1;
      this.markUpdated();
   }
}
