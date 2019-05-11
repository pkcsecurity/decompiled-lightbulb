package com.facebook.react.views.view;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import com.facebook.react.views.common.ViewHelper;
import com.facebook.react.views.view.ReactViewBackgroundDrawable;
import javax.annotation.Nullable;

public class ReactViewBackgroundManager {

   @Nullable
   private ReactViewBackgroundDrawable mReactBackgroundDrawable;
   private View mView;


   public ReactViewBackgroundManager(View var1) {
      this.mView = var1;
   }

   private ReactViewBackgroundDrawable getOrCreateReactViewBackground() {
      if(this.mReactBackgroundDrawable == null) {
         this.mReactBackgroundDrawable = new ReactViewBackgroundDrawable(this.mView.getContext());
         Drawable var1 = this.mView.getBackground();
         ViewHelper.setBackground(this.mView, (Drawable)null);
         if(var1 == null) {
            ViewHelper.setBackground(this.mView, this.mReactBackgroundDrawable);
         } else {
            LayerDrawable var2 = new LayerDrawable(new Drawable[]{this.mReactBackgroundDrawable, var1});
            ViewHelper.setBackground(this.mView, var2);
         }
      }

      return this.mReactBackgroundDrawable;
   }

   public void setBackgroundColor(int var1) {
      if(var1 != 0 || this.mReactBackgroundDrawable != null) {
         this.getOrCreateReactViewBackground().setColor(var1);
      }
   }

   public void setBorderColor(int var1, float var2, float var3) {
      this.getOrCreateReactViewBackground().setBorderColor(var1, var2, var3);
   }

   public void setBorderRadius(float var1) {
      this.getOrCreateReactViewBackground().setRadius(var1);
   }

   public void setBorderRadius(float var1, int var2) {
      this.getOrCreateReactViewBackground().setRadius(var1, var2);
   }

   public void setBorderStyle(@Nullable String var1) {
      this.getOrCreateReactViewBackground().setBorderStyle(var1);
   }

   public void setBorderWidth(int var1, float var2) {
      this.getOrCreateReactViewBackground().setBorderWidth(var1, var2);
   }
}
