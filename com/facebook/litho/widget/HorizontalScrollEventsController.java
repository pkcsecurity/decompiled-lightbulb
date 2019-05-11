package com.facebook.litho.widget;

import android.support.annotation.Nullable;
import com.facebook.litho.widget.HorizontalScrollSpec;

public class HorizontalScrollEventsController {

   @Nullable
   private HorizontalScrollSpec.HorizontalScrollLithoView mHorizontalScrollView;


   public void scrollTo(int var1) {
      if(this.mHorizontalScrollView != null) {
         this.mHorizontalScrollView.scrollTo(var1, 0);
      }

   }

   void setScrollableView(@Nullable HorizontalScrollSpec.HorizontalScrollLithoView var1) {
      this.mHorizontalScrollView = var1;
   }

   public void smoothScrollTo(int var1) {
      if(this.mHorizontalScrollView != null) {
         this.mHorizontalScrollView.smoothScrollTo(var1, 0);
      }

   }
}
