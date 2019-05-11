package com.facebook.react.views.common;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.View;

public class ViewHelper {

   public static void setBackground(View var0, Drawable var1) {
      if(VERSION.SDK_INT >= 16) {
         var0.setBackground(var1);
      } else {
         var0.setBackgroundDrawable(var1);
      }
   }
}
