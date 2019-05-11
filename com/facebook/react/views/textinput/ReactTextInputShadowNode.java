package com.facebook.react.views.textinput;

import android.os.Build.VERSION;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactBaseTextShadowNode;
import com.facebook.react.views.text.ReactTextUpdate;
import com.facebook.react.views.textinput.ReactTextInputLocalData;
import com.facebook.react.views.view.MeasureUtil;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNode;
import javax.annotation.Nullable;

@VisibleForTesting
public class ReactTextInputShadowNode extends ReactBaseTextShadowNode implements YogaMeasureFunction {

   @VisibleForTesting
   public static final String PROP_TEXT = "text";
   @Nullable
   private EditText mDummyEditText;
   @Nullable
   private ReactTextInputLocalData mLocalData;
   private int mMostRecentEventCount = -1;
   @Nullable
   private String mText = null;


   public ReactTextInputShadowNode() {
      int var1 = VERSION.SDK_INT;
      this.mTextBreakStrategy = 0;
      this.setMeasureFunction(this);
   }

   @Nullable
   public String getText() {
      return this.mText;
   }

   public boolean isVirtualAnchor() {
      return true;
   }

   public boolean isYogaLeafNode() {
      return true;
   }

   public long measure(YogaNode var1, float var2, YogaMeasureMode var3, float var4, YogaMeasureMode var5) {
      EditText var6 = (EditText)Assertions.assertNotNull(this.mDummyEditText);
      if(this.mLocalData == null) {
         return YogaMeasureOutput.make(0, 0);
      } else {
         this.mLocalData.apply(var6);
         var6.measure(MeasureUtil.getMeasureSpec(var2, var3), MeasureUtil.getMeasureSpec(var4, var5));
         return YogaMeasureOutput.make(var6.getMeasuredWidth(), var6.getMeasuredHeight());
      }
   }

   public void onCollectExtraUpdates(UIViewOperationQueue var1) {
      super.onCollectExtraUpdates(var1);
      if(this.mMostRecentEventCount != -1) {
         ReactTextUpdate var2 = new ReactTextUpdate(spannedFromShadowNode(this, this.getText()), this.mMostRecentEventCount, this.mContainsImages, this.getPadding(0), this.getPadding(1), this.getPadding(2), this.getPadding(3), this.mTextAlign, this.mTextBreakStrategy);
         var1.enqueueUpdateExtraData(this.getReactTag(), var2);
      }

   }

   public void setLocalData(Object var1) {
      Assertions.assertCondition(var1 instanceof ReactTextInputLocalData);
      this.mLocalData = (ReactTextInputLocalData)var1;
      this.dirty();
   }

   @ReactProp(
      name = "mostRecentEventCount"
   )
   public void setMostRecentEventCount(int var1) {
      this.mMostRecentEventCount = var1;
   }

   public void setPadding(int var1, float var2) {
      super.setPadding(var1, var2);
      this.markUpdated();
   }

   @ReactProp(
      name = "text"
   )
   public void setText(@Nullable String var1) {
      this.mText = var1;
      this.markUpdated();
   }

   public void setTextBreakStrategy(@Nullable String var1) {
      if(VERSION.SDK_INT >= 23) {
         if(var1 != null && !"simple".equals(var1)) {
            if("highQuality".equals(var1)) {
               this.mTextBreakStrategy = 1;
            } else if("balanced".equals(var1)) {
               this.mTextBreakStrategy = 2;
            } else {
               StringBuilder var2 = new StringBuilder();
               var2.append("Invalid textBreakStrategy: ");
               var2.append(var1);
               throw new JSApplicationIllegalArgumentException(var2.toString());
            }
         } else {
            this.mTextBreakStrategy = 0;
         }
      }
   }

   public void setThemedContext(ThemedReactContext var1) {
      super.setThemedContext(var1);
      EditText var2 = new EditText(this.getThemedContext());
      this.setDefaultPadding(4, (float)var2.getPaddingStart());
      this.setDefaultPadding(1, (float)var2.getPaddingTop());
      this.setDefaultPadding(5, (float)var2.getPaddingEnd());
      this.setDefaultPadding(3, (float)var2.getPaddingBottom());
      this.mDummyEditText = var2;
      this.mDummyEditText.setPadding(0, 0, 0, 0);
      this.mDummyEditText.setLayoutParams(new LayoutParams(-2, -2));
   }
}
