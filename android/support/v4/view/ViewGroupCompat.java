package android.support.v4.view;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.compat.R;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

public final class ViewGroupCompat {

   public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
   public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;


   public static int getLayoutMode(@NonNull ViewGroup var0) {
      return VERSION.SDK_INT >= 18?var0.getLayoutMode():0;
   }

   public static int getNestedScrollAxes(@NonNull ViewGroup var0) {
      return VERSION.SDK_INT >= 21?var0.getNestedScrollAxes():(var0 instanceof NestedScrollingParent?((NestedScrollingParent)var0).getNestedScrollAxes():0);
   }

   public static boolean isTransitionGroup(@NonNull ViewGroup var0) {
      if(VERSION.SDK_INT >= 21) {
         return var0.isTransitionGroup();
      } else {
         Boolean var1 = (Boolean)var0.getTag(R.id.tag_transition_group);
         return var1 != null && var1.booleanValue() || var0.getBackground() != null || ViewCompat.getTransitionName(var0) != null;
      }
   }

   @Deprecated
   public static boolean onRequestSendAccessibilityEvent(ViewGroup var0, View var1, AccessibilityEvent var2) {
      return var0.onRequestSendAccessibilityEvent(var1, var2);
   }

   public static void setLayoutMode(@NonNull ViewGroup var0, int var1) {
      if(VERSION.SDK_INT >= 18) {
         var0.setLayoutMode(var1);
      }

   }

   @Deprecated
   public static void setMotionEventSplittingEnabled(ViewGroup var0, boolean var1) {
      var0.setMotionEventSplittingEnabled(var1);
   }

   public static void setTransitionGroup(@NonNull ViewGroup var0, boolean var1) {
      if(VERSION.SDK_INT >= 21) {
         var0.setTransitionGroup(var1);
      } else {
         var0.setTag(R.id.tag_transition_group, Boolean.valueOf(var1));
      }
   }
}
