package com.facebook.react.views.scroll;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import java.util.Map;
import javax.annotation.Nullable;

public class ReactScrollViewCommandHelper {

   public static final int COMMAND_FLASH_SCROLL_INDICATORS = 3;
   public static final int COMMAND_SCROLL_TO = 1;
   public static final int COMMAND_SCROLL_TO_END = 2;


   public static Map<String, Integer> getCommandsMap() {
      return MapBuilder.of("scrollTo", Integer.valueOf(1), "scrollToEnd", Integer.valueOf(2), "flashScrollIndicators", Integer.valueOf(3));
   }

   public static <T extends Object> void receiveCommand(ReactScrollViewCommandHelper.ScrollCommandHandler<T> var0, T var1, int var2, @Nullable ReadableArray var3) {
      Assertions.assertNotNull(var0);
      Assertions.assertNotNull(var1);
      Assertions.assertNotNull(var3);
      switch(var2) {
      case 1:
         var0.scrollTo(var1, new ReactScrollViewCommandHelper.ScrollToCommandData(Math.round(PixelUtil.toPixelFromDIP(var3.getDouble(0))), Math.round(PixelUtil.toPixelFromDIP(var3.getDouble(1))), var3.getBoolean(2)));
         return;
      case 2:
         var0.scrollToEnd(var1, new ReactScrollViewCommandHelper.ScrollToEndCommandData(var3.getBoolean(0)));
         return;
      case 3:
         var0.flashScrollIndicators(var1);
         return;
      default:
         throw new IllegalArgumentException(String.format("Unsupported command %d received by %s.", new Object[]{Integer.valueOf(var2), var0.getClass().getSimpleName()}));
      }
   }

   public interface ScrollCommandHandler<T extends Object> {

      void flashScrollIndicators(T var1);

      void scrollTo(T var1, ReactScrollViewCommandHelper.ScrollToCommandData var2);

      void scrollToEnd(T var1, ReactScrollViewCommandHelper.ScrollToEndCommandData var2);
   }

   public static class ScrollToCommandData {

      public final boolean mAnimated;
      public final int mDestX;
      public final int mDestY;


      ScrollToCommandData(int var1, int var2, boolean var3) {
         this.mDestX = var1;
         this.mDestY = var2;
         this.mAnimated = var3;
      }
   }

   public static class ScrollToEndCommandData {

      public final boolean mAnimated;


      ScrollToEndCommandData(boolean var1) {
         this.mAnimated = var1;
      }
   }
}
