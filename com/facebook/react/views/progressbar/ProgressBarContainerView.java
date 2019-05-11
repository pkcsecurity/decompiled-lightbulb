package com.facebook.react.views.progressbar;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.views.progressbar.ReactProgressBarViewManager;
import javax.annotation.Nullable;

class ProgressBarContainerView extends FrameLayout {

   private static final int MAX_PROGRESS = 1000;
   private boolean mAnimating = true;
   @Nullable
   private Integer mColor;
   private boolean mIndeterminate = true;
   private double mProgress;
   @Nullable
   private ProgressBar mProgressBar;


   public ProgressBarContainerView(Context var1) {
      super(var1);
   }

   private void setColor(ProgressBar var1) {
      Drawable var2;
      if(var1.isIndeterminate()) {
         var2 = var1.getIndeterminateDrawable();
      } else {
         var2 = var1.getProgressDrawable();
      }

      if(var2 != null) {
         if(this.mColor != null) {
            var2.setColorFilter(this.mColor.intValue(), Mode.SRC_IN);
         } else {
            var2.clearColorFilter();
         }
      }
   }

   public void apply() {
      if(this.mProgressBar == null) {
         throw new JSApplicationIllegalArgumentException("setStyle() not called");
      } else {
         this.mProgressBar.setIndeterminate(this.mIndeterminate);
         this.setColor(this.mProgressBar);
         this.mProgressBar.setProgress((int)(this.mProgress * 1000.0D));
         if(this.mAnimating) {
            this.mProgressBar.setVisibility(0);
         } else {
            this.mProgressBar.setVisibility(8);
         }
      }
   }

   public void setAnimating(boolean var1) {
      this.mAnimating = var1;
   }

   public void setColor(@Nullable Integer var1) {
      this.mColor = var1;
   }

   public void setIndeterminate(boolean var1) {
      this.mIndeterminate = var1;
   }

   public void setProgress(double var1) {
      this.mProgress = var1;
   }

   public void setStyle(@Nullable String var1) {
      int var2 = ReactProgressBarViewManager.getStyleFromString(var1);
      this.mProgressBar = ReactProgressBarViewManager.createProgressBar(this.getContext(), var2);
      this.mProgressBar.setMax(1000);
      this.removeAllViews();
      this.addView(this.mProgressBar, new LayoutParams(-1, -1));
   }
}
