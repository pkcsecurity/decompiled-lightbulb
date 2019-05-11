package com.facebook.react.flat;

import android.annotation.TargetApi;
import android.text.SpannableStringBuilder;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.flat.AndroidView;
import com.facebook.react.flat.RCTVirtualText;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactTextUpdate;
import com.facebook.react.views.view.MeasureUtil;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNode;
import javax.annotation.Nullable;

public class RCTTextInput extends RCTVirtualText implements AndroidView, YogaMeasureFunction {

   @Nullable
   private EditText mEditText;
   private int mJsEventCount = -1;
   private int mNumberOfLines = -1;
   private boolean mPaddingChanged = false;
   @Nullable
   private String mText;


   public RCTTextInput() {
      this.forceMountToView();
      this.setMeasureFunction(this);
   }

   boolean isEditable() {
      return true;
   }

   public boolean isPaddingChanged() {
      return this.mPaddingChanged;
   }

   public boolean isVirtual() {
      return false;
   }

   public boolean isVirtualAnchor() {
      return true;
   }

   public long measure(YogaNode var1, float var2, YogaMeasureMode var3, float var4, YogaMeasureMode var5) {
      EditText var8 = (EditText)Assertions.assertNotNull(this.mEditText);
      int var7 = this.getFontSize();
      int var6 = var7;
      if(var7 == -1) {
         var6 = (int)Math.ceil((double)PixelUtil.toPixelFromSP(14.0F));
      }

      var8.setTextSize(0, (float)var6);
      if(this.mNumberOfLines != -1) {
         var8.setLines(this.mNumberOfLines);
      }

      var8.measure(MeasureUtil.getMeasureSpec(var2, var3), MeasureUtil.getMeasureSpec(var4, var5));
      return YogaMeasureOutput.make(var8.getMeasuredWidth(), var8.getMeasuredHeight());
   }

   public boolean needsCustomLayoutForChildren() {
      return false;
   }

   protected void notifyChanged(boolean var1) {
      super.notifyChanged(var1);
      this.markUpdated();
   }

   public void onCollectExtraUpdates(UIViewOperationQueue var1) {
      super.onCollectExtraUpdates(var1);
      if(this.mJsEventCount != -1) {
         ReactTextUpdate var2 = new ReactTextUpdate(this.getText(), this.mJsEventCount, false, this.getPadding(4), this.getPadding(1), this.getPadding(5), this.getPadding(3), -1);
         var1.enqueueUpdateExtraData(this.getReactTag(), var2);
      }

   }

   protected void performCollectText(SpannableStringBuilder var1) {
      if(this.mText != null) {
         var1.append(this.mText);
      }

      super.performCollectText(var1);
   }

   public void resetPaddingChanged() {
      this.mPaddingChanged = false;
   }

   public void setBackgroundColor(int var1) {}

   @ReactProp(
      name = "mostRecentEventCount"
   )
   public void setMostRecentEventCount(int var1) {
      this.mJsEventCount = var1;
   }

   @ReactProp(
      defaultInt = Integer.MAX_VALUE,
      name = "numberOfLines"
   )
   public void setNumberOfLines(int var1) {
      this.mNumberOfLines = var1;
      this.notifyChanged(true);
   }

   public void setPadding(int var1, float var2) {
      super.setPadding(var1, var2);
      this.mPaddingChanged = true;
      this.dirty();
   }

   @ReactProp(
      name = "text"
   )
   public void setText(@Nullable String var1) {
      this.mText = var1;
      this.notifyChanged(true);
   }

   @TargetApi(17)
   public void setThemedContext(ThemedReactContext var1) {
      super.setThemedContext(var1);
      this.mEditText = new EditText(var1);
      this.mEditText.setLayoutParams(new LayoutParams(-2, -2));
      this.setDefaultPadding(4, (float)this.mEditText.getPaddingStart());
      this.setDefaultPadding(1, (float)this.mEditText.getPaddingTop());
      this.setDefaultPadding(5, (float)this.mEditText.getPaddingEnd());
      this.setDefaultPadding(3, (float)this.mEditText.getPaddingBottom());
      this.mEditText.setPadding(0, 0, 0, 0);
   }

   boolean shouldAllowEmptySpans() {
      return true;
   }
}
