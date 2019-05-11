package com.facebook.react.views.textinput;

import android.os.Build.VERSION;
import android.text.SpannableStringBuilder;
import android.widget.EditText;

public final class ReactTextInputLocalData {

   private final int mBreakStrategy;
   private final int mInputType;
   private final int mMaxLines;
   private final int mMinLines;
   private final SpannableStringBuilder mText;
   private final float mTextSize;


   public ReactTextInputLocalData(EditText var1) {
      this.mText = new SpannableStringBuilder(var1.getText());
      this.mTextSize = var1.getTextSize();
      this.mMinLines = var1.getMinLines();
      this.mMaxLines = var1.getMaxLines();
      this.mInputType = var1.getInputType();
      if(VERSION.SDK_INT >= 23) {
         this.mBreakStrategy = var1.getBreakStrategy();
      } else {
         this.mBreakStrategy = 0;
      }
   }

   public void apply(EditText var1) {
      var1.setText(this.mText);
      var1.setTextSize(0, this.mTextSize);
      var1.setMinLines(this.mMinLines);
      var1.setMaxLines(this.mMaxLines);
      var1.setInputType(this.mInputType);
      if(VERSION.SDK_INT >= 23) {
         var1.setBreakStrategy(this.mBreakStrategy);
      }

   }
}
