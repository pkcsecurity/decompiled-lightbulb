package com.facebook.widget.accessibility.delegates;

import android.text.style.ClickableSpan;

public abstract class AccessibleClickableSpan extends ClickableSpan {

   private String mAccessibilityDescription;


   public AccessibleClickableSpan(String var1) {
      this.mAccessibilityDescription = var1;
   }

   public String getAccessibilityDescription() {
      return this.mAccessibilityDescription;
   }

   public void setAccessibilityDescription(String var1) {
      this.mAccessibilityDescription = var1;
   }
}
