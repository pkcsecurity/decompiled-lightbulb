package com.facebook.react.views.scroll;

import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.views.scroll.ScrollEvent;
import com.facebook.react.views.scroll.ScrollEventType;

public class ReactScrollViewHelper {

   public static final String AUTO = "auto";
   public static final long MOMENTUM_DELAY = 20L;
   public static final String OVER_SCROLL_ALWAYS = "always";
   public static final String OVER_SCROLL_NEVER = "never";


   public static void emitScrollBeginDragEvent(ViewGroup var0) {
      emitScrollEvent(var0, ScrollEventType.BEGIN_DRAG);
   }

   public static void emitScrollEndDragEvent(ViewGroup var0, float var1, float var2) {
      emitScrollEvent(var0, ScrollEventType.END_DRAG, var1, var2);
   }

   public static void emitScrollEvent(ViewGroup var0, float var1, float var2) {
      emitScrollEvent(var0, ScrollEventType.SCROLL, var1, var2);
   }

   private static void emitScrollEvent(ViewGroup var0, ScrollEventType var1) {
      emitScrollEvent(var0, var1, 0.0F, 0.0F);
   }

   private static void emitScrollEvent(ViewGroup var0, ScrollEventType var1, float var2, float var3) {
      View var4 = var0.getChildAt(0);
      if(var4 != null) {
         ((UIManagerModule)((ReactContext)var0.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(ScrollEvent.obtain(var0.getId(), var1, var0.getScrollX(), var0.getScrollY(), var2, var3, var4.getWidth(), var4.getHeight(), var0.getWidth(), var0.getHeight()));
      }
   }

   public static void emitScrollMomentumBeginEvent(ViewGroup var0) {
      emitScrollEvent(var0, ScrollEventType.MOMENTUM_BEGIN);
   }

   public static void emitScrollMomentumEndEvent(ViewGroup var0) {
      emitScrollEvent(var0, ScrollEventType.MOMENTUM_END);
   }

   public static int parseOverScrollMode(String var0) {
      if(var0 != null && !var0.equals("auto")) {
         if(var0.equals("always")) {
            return 0;
         } else if(var0.equals("never")) {
            return 2;
         } else {
            StringBuilder var1 = new StringBuilder();
            var1.append("wrong overScrollMode: ");
            var1.append(var0);
            throw new JSApplicationIllegalArgumentException(var1.toString());
         }
      } else {
         return 1;
      }
   }
}
