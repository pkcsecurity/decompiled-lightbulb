package com.facebook.react.uimanager;

import android.view.View.MeasureSpec;

public class MeasureSpecAssertions {

   public static final void assertExplicitMeasureSpec(int var0, int var1) {
      var0 = MeasureSpec.getMode(var0);
      var1 = MeasureSpec.getMode(var1);
      if(var0 == 0 || var1 == 0) {
         throw new IllegalStateException("A catalyst view must have an explicit width and height given to it. This should normally happen as part of the standard catalyst UI framework.");
      }
   }
}
