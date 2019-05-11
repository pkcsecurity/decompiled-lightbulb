package com.facebook.react.uimanager;

import android.view.View;
import android.view.ViewParent;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.uimanager.RootView;

public class RootViewUtil {

   public static RootView getRootView(View var0) {
      while(!(var0 instanceof RootView)) {
         ViewParent var1 = var0.getParent();
         if(var1 == null) {
            return null;
         }

         Assertions.assertCondition(var1 instanceof View);
         var0 = (View)var1;
      }

      return (RootView)var0;
   }
}
