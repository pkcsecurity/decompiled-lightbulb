package com.facebook.react.flat;

import android.text.SpannableStringBuilder;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.flat.FlatTextShadowNode;
import com.facebook.react.flat.InlineImageSpanWithPipeline;
import com.facebook.react.flat.StateBuilder;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.imagehelper.ImageSource;
import javax.annotation.Nullable;

class RCTTextInlineImage extends FlatTextShadowNode {

   private InlineImageSpanWithPipeline mInlineImageSpan = new InlineImageSpanWithPipeline();


   private InlineImageSpanWithPipeline getMutableSpan() {
      if(this.mInlineImageSpan.isFrozen()) {
         this.mInlineImageSpan = this.mInlineImageSpan.mutableCopy();
      }

      return this.mInlineImageSpan;
   }

   protected void performApplySpans(SpannableStringBuilder var1, int var2, int var3, boolean var4) {
      this.mInlineImageSpan.freeze();
      var1.setSpan(this.mInlineImageSpan, var2, var3, 17);
   }

   protected void performCollectAttachDetachListeners(StateBuilder var1) {
      var1.addAttachDetachListener(this.mInlineImageSpan);
   }

   protected void performCollectText(SpannableStringBuilder var1) {
      var1.append("I");
   }

   @ReactProp(
      name = "src"
   )
   public void setSource(@Nullable ReadableArray var1) {
      Object var2 = null;
      String var4;
      if(var1 != null && var1.size() != 0) {
         var4 = var1.getMap(0).getString("uri");
      } else {
         var4 = null;
      }

      ImageSource var5;
      if(var4 == null) {
         var5 = null;
      } else {
         var5 = new ImageSource(this.getThemedContext(), var4);
      }

      InlineImageSpanWithPipeline var3 = this.getMutableSpan();
      ImageRequest var6;
      if(var5 == null) {
         var6 = (ImageRequest)var2;
      } else {
         var6 = ImageRequestBuilder.newBuilderWithSource(var5.getUri()).build();
      }

      var3.setImageRequest(var6);
   }

   public void setStyleHeight(float var1) {
      super.setStyleHeight(var1);
      if(this.mInlineImageSpan.getHeight() != var1) {
         this.getMutableSpan().setHeight(var1);
         this.notifyChanged(true);
      }

   }

   public void setStyleWidth(float var1) {
      super.setStyleWidth(var1);
      if(this.mInlineImageSpan.getWidth() != var1) {
         this.getMutableSpan().setWidth(var1);
         this.notifyChanged(true);
      }

   }
}
