package com.facebook.react.flat;

import android.text.SpannableStringBuilder;
import com.facebook.react.flat.FlatTextShadowNode;
import com.facebook.react.flat.StateBuilder;
import com.facebook.react.uimanager.annotations.ReactProp;
import javax.annotation.Nullable;

final class RCTRawText extends FlatTextShadowNode {

   @Nullable
   private String mText;


   protected void performApplySpans(SpannableStringBuilder var1, int var2, int var3, boolean var4) {
      var1.setSpan(this, var2, var3, 17);
   }

   protected void performCollectAttachDetachListeners(StateBuilder var1) {}

   protected void performCollectText(SpannableStringBuilder var1) {
      if(this.mText != null) {
         var1.append(this.mText);
      }

   }

   @ReactProp(
      name = "text"
   )
   public void setText(@Nullable String var1) {
      this.mText = var1;
      this.notifyChanged(true);
   }
}
