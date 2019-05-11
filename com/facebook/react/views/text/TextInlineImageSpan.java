package com.facebook.react.views.text;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.style.ReplacementSpan;
import android.widget.TextView;
import javax.annotation.Nullable;

public abstract class TextInlineImageSpan extends ReplacementSpan {

   public static void possiblyUpdateInlineImageSpans(Spannable var0, TextView var1) {
      int var3 = var0.length();
      int var2 = 0;
      TextInlineImageSpan[] var5 = (TextInlineImageSpan[])var0.getSpans(0, var3, TextInlineImageSpan.class);

      for(var3 = var5.length; var2 < var3; ++var2) {
         TextInlineImageSpan var4 = var5[var2];
         var4.onAttachedToWindow();
         var4.setTextView(var1);
      }

   }

   @Nullable
   public abstract Drawable getDrawable();

   public abstract int getHeight();

   public abstract int getWidth();

   public abstract void onAttachedToWindow();

   public abstract void onDetachedFromWindow();

   public abstract void onFinishTemporaryDetach();

   public abstract void onStartTemporaryDetach();

   public abstract void setTextView(TextView var1);
}
