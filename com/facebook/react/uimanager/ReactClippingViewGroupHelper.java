package com.facebook.react.uimanager;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewParent;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class ReactClippingViewGroupHelper {

   public static final String PROP_REMOVE_CLIPPED_SUBVIEWS = "removeClippedSubviews";
   private static final Rect sHelperRect = new Rect();


   public static void calculateClippingRect(View var0, Rect var1) {
      ViewParent var2 = var0.getParent();
      if(var2 == null) {
         var1.setEmpty();
      } else {
         if(var2 instanceof ReactClippingViewGroup) {
            ReactClippingViewGroup var3 = (ReactClippingViewGroup)var2;
            if(var3.getRemoveClippedSubviews()) {
               var3.getClippingRect(sHelperRect);
               if(!sHelperRect.intersect(var0.getLeft(), var0.getTop(), var0.getRight(), var0.getBottom())) {
                  var1.setEmpty();
                  return;
               }

               sHelperRect.offset(-var0.getLeft(), -var0.getTop());
               sHelperRect.offset(var0.getScrollX(), var0.getScrollY());
               var1.set(sHelperRect);
               return;
            }
         }

         var0.getDrawingRect(var1);
      }
   }
}
