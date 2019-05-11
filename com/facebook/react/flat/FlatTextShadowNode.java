package com.facebook.react.flat;

import android.text.SpannableStringBuilder;
import com.facebook.react.flat.FlatShadowNode;
import com.facebook.react.flat.StateBuilder;
import com.facebook.react.uimanager.ReactShadowNodeImpl;

abstract class FlatTextShadowNode extends FlatShadowNode {

   private int mTextBegin;
   private int mTextEnd;


   final void applySpans(SpannableStringBuilder var1, boolean var2) {
      if(this.mTextBegin != this.mTextEnd || this.shouldAllowEmptySpans()) {
         this.performApplySpans(var1, this.mTextBegin, this.mTextEnd, var2);
      }

   }

   final void collectText(SpannableStringBuilder var1) {
      this.mTextBegin = var1.length();
      this.performCollectText(var1);
      this.mTextEnd = var1.length();
   }

   boolean isEditable() {
      return false;
   }

   public boolean isVirtual() {
      return true;
   }

   protected void notifyChanged(boolean var1) {
      ReactShadowNodeImpl var2 = this.getParent();
      if(var2 instanceof FlatTextShadowNode) {
         ((FlatTextShadowNode)var2).notifyChanged(var1);
      }

   }

   protected abstract void performApplySpans(SpannableStringBuilder var1, int var2, int var3, boolean var4);

   protected abstract void performCollectAttachDetachListeners(StateBuilder var1);

   protected abstract void performCollectText(SpannableStringBuilder var1);

   boolean shouldAllowEmptySpans() {
      return false;
   }
}
