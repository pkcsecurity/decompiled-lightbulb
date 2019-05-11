package com.facebook.react.views.modal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import com.facebook.infer.annotation.Assertions;

class ModalHostHelper {

   private static final Point MAX_POINT = new Point();
   private static final Point MIN_POINT = new Point();
   private static final Point SIZE_POINT = new Point();


   @TargetApi(16)
   public static Point getModalHostSize(Context var0) {
      Display var5 = ((WindowManager)Assertions.assertNotNull((WindowManager)var0.getSystemService("window"))).getDefaultDisplay();
      var5.getCurrentSizeRange(MIN_POINT, MAX_POINT);
      var5.getSize(SIZE_POINT);
      byte var2 = 0;
      boolean var4 = var0.getTheme().obtainStyledAttributes(new int[]{16843277}).getBoolean(0, false);
      Resources var6 = var0.getResources();
      int var3 = var6.getIdentifier("status_bar_height", "dimen", "android");
      int var1 = var2;
      if(var4) {
         var1 = var2;
         if(var3 > 0) {
            var1 = (int)var6.getDimension(var3);
         }
      }

      return SIZE_POINT.x < SIZE_POINT.y?new Point(MIN_POINT.x, MAX_POINT.y + var1):new Point(MAX_POINT.x, MIN_POINT.y + var1);
   }
}
