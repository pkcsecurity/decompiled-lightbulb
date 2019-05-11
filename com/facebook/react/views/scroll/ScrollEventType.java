package com.facebook.react.views.scroll;


public enum ScrollEventType {

   // $FF: synthetic field
   private static final ScrollEventType[] $VALUES = new ScrollEventType[]{BEGIN_DRAG, END_DRAG, SCROLL, MOMENTUM_BEGIN, MOMENTUM_END};
   BEGIN_DRAG("BEGIN_DRAG", 0, "topScrollBeginDrag"),
   END_DRAG("END_DRAG", 1, "topScrollEndDrag"),
   MOMENTUM_BEGIN("MOMENTUM_BEGIN", 3, "topMomentumScrollBegin"),
   MOMENTUM_END("MOMENTUM_END", 4, "topMomentumScrollEnd"),
   SCROLL("SCROLL", 2, "topScroll");
   private final String mJSEventName;


   private ScrollEventType(String var1, int var2, String var3) {
      this.mJSEventName = var3;
   }

   public String getJSEventName() {
      return this.mJSEventName;
   }
}
