package android.support.transition;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.transition.ViewGroupOverlayApi14;
import android.support.transition.ViewGroupOverlayApi18;
import android.support.transition.ViewGroupOverlayImpl;
import android.support.transition.ViewGroupUtilsApi14;
import android.support.transition.ViewGroupUtilsApi18;
import android.view.ViewGroup;

class ViewGroupUtils {

   static ViewGroupOverlayImpl getOverlay(@NonNull ViewGroup var0) {
      return (ViewGroupOverlayImpl)(VERSION.SDK_INT >= 18?new ViewGroupOverlayApi18(var0):ViewGroupOverlayApi14.createFrom(var0));
   }

   static void suppressLayout(@NonNull ViewGroup var0, boolean var1) {
      if(VERSION.SDK_INT >= 18) {
         ViewGroupUtilsApi18.suppressLayout(var0, var1);
      } else {
         ViewGroupUtilsApi14.suppressLayout(var0, var1);
      }
   }
}
