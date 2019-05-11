package com.facebook.react.views.scroll;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.react.views.scroll.FpsListener;
import com.facebook.react.views.scroll.ReactScrollView;
import com.facebook.react.views.scroll.ReactScrollViewCommandHelper;
import com.facebook.react.views.scroll.ReactScrollViewHelper;
import com.facebook.react.views.scroll.ScrollEventType;
import com.facebook.yoga.YogaConstants;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(
   name = "RCTScrollView"
)
public class ReactScrollViewManager extends ViewGroupManager<ReactScrollView> implements ReactScrollViewCommandHelper.ScrollCommandHandler<ReactScrollView> {

   protected static final String REACT_CLASS = "RCTScrollView";
   private static final int[] SPACING_TYPES = new int[]{8, 0, 2, 1, 3};
   @Nullable
   private FpsListener mFpsListener;


   public ReactScrollViewManager() {
      this((FpsListener)null);
   }

   public ReactScrollViewManager(@Nullable FpsListener var1) {
      this.mFpsListener = null;
      this.mFpsListener = var1;
   }

   public static Map createExportedCustomDirectEventTypeConstants() {
      return MapBuilder.builder().put(ScrollEventType.SCROLL.getJSEventName(), MapBuilder.of("registrationName", "onScroll")).put(ScrollEventType.BEGIN_DRAG.getJSEventName(), MapBuilder.of("registrationName", "onScrollBeginDrag")).put(ScrollEventType.END_DRAG.getJSEventName(), MapBuilder.of("registrationName", "onScrollEndDrag")).put(ScrollEventType.MOMENTUM_BEGIN.getJSEventName(), MapBuilder.of("registrationName", "onMomentumScrollBegin")).put(ScrollEventType.MOMENTUM_END.getJSEventName(), MapBuilder.of("registrationName", "onMomentumScrollEnd")).build();
   }

   public ReactScrollView createViewInstance(ThemedReactContext var1) {
      return new ReactScrollView(var1, this.mFpsListener);
   }

   public void flashScrollIndicators(ReactScrollView var1) {
      var1.flashScrollIndicators();
   }

   @Nullable
   public Map<String, Integer> getCommandsMap() {
      return ReactScrollViewCommandHelper.getCommandsMap();
   }

   @Nullable
   public Map getExportedCustomDirectEventTypeConstants() {
      return createExportedCustomDirectEventTypeConstants();
   }

   public String getName() {
      return "RCTScrollView";
   }

   public void receiveCommand(ReactScrollView var1, int var2, @Nullable ReadableArray var3) {
      ReactScrollViewCommandHelper.receiveCommand(this, var1, var2, var3);
   }

   public void scrollTo(ReactScrollView var1, ReactScrollViewCommandHelper.ScrollToCommandData var2) {
      if(var2.mAnimated) {
         var1.smoothScrollTo(var2.mDestX, var2.mDestY);
      } else {
         var1.scrollTo(var2.mDestX, var2.mDestY);
      }
   }

   public void scrollToEnd(ReactScrollView var1, ReactScrollViewCommandHelper.ScrollToEndCommandData var2) {
      int var3 = var1.getChildAt(0).getHeight() + var1.getPaddingBottom();
      if(var2.mAnimated) {
         var1.smoothScrollTo(var1.getScrollX(), var3);
      } else {
         var1.scrollTo(var1.getScrollX(), var3);
      }
   }

   @ReactPropGroup(
      customType = "Color",
      names = {"borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor"}
   )
   public void setBorderColor(ReactScrollView var1, int var2, Integer var3) {
      float var5 = Float.NaN;
      float var4;
      if(var3 == null) {
         var4 = Float.NaN;
      } else {
         var4 = (float)(var3.intValue() & 16777215);
      }

      if(var3 != null) {
         var5 = (float)(var3.intValue() >>> 24);
      }

      var1.setBorderColor(SPACING_TYPES[var2], var4, var5);
   }

   @ReactPropGroup(
      defaultFloat = Float.NaN,
      names = {"borderRadius", "borderTopLeftRadius", "borderTopRightRadius", "borderBottomRightRadius", "borderBottomLeftRadius"}
   )
   public void setBorderRadius(ReactScrollView var1, int var2, float var3) {
      float var4 = var3;
      if(!YogaConstants.isUndefined(var3)) {
         var4 = PixelUtil.toPixelFromDIP(var3);
      }

      if(var2 == 0) {
         var1.setBorderRadius(var4);
      } else {
         var1.setBorderRadius(var4, var2 - 1);
      }
   }

   @ReactProp(
      name = "borderStyle"
   )
   public void setBorderStyle(ReactScrollView var1, @Nullable String var2) {
      var1.setBorderStyle(var2);
   }

   @ReactPropGroup(
      defaultFloat = Float.NaN,
      names = {"borderWidth", "borderLeftWidth", "borderRightWidth", "borderTopWidth", "borderBottomWidth"}
   )
   public void setBorderWidth(ReactScrollView var1, int var2, float var3) {
      float var4 = var3;
      if(!YogaConstants.isUndefined(var3)) {
         var4 = PixelUtil.toPixelFromDIP(var3);
      }

      var1.setBorderWidth(SPACING_TYPES[var2], var4);
   }

   @ReactProp(
      customType = "Color",
      defaultInt = 0,
      name = "endFillColor"
   )
   public void setBottomFillColor(ReactScrollView var1, int var2) {
      var1.setEndFillColor(var2);
   }

   @ReactProp(
      name = "overScrollMode"
   )
   public void setOverScrollMode(ReactScrollView var1, String var2) {
      var1.setOverScrollMode(ReactScrollViewHelper.parseOverScrollMode(var2));
   }

   @ReactProp(
      name = "removeClippedSubviews"
   )
   public void setRemoveClippedSubviews(ReactScrollView var1, boolean var2) {
      var1.setRemoveClippedSubviews(var2);
   }

   @ReactProp(
      defaultBoolean = true,
      name = "scrollEnabled"
   )
   public void setScrollEnabled(ReactScrollView var1, boolean var2) {
      var1.setScrollEnabled(var2);
   }

   @ReactProp(
      name = "scrollPerfTag"
   )
   public void setScrollPerfTag(ReactScrollView var1, String var2) {
      var1.setScrollPerfTag(var2);
   }

   @ReactProp(
      name = "sendMomentumEvents"
   )
   public void setSendMomentumEvents(ReactScrollView var1, boolean var2) {
      var1.setSendMomentumEvents(var2);
   }

   @ReactProp(
      name = "showsVerticalScrollIndicator"
   )
   public void setShowsVerticalScrollIndicator(ReactScrollView var1, boolean var2) {
      var1.setVerticalScrollBarEnabled(var2);
   }
}
