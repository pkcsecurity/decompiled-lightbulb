package com.facebook.react.views.scroll;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.react.views.scroll.FpsListener;
import com.facebook.react.views.scroll.ReactHorizontalScrollView;
import com.facebook.react.views.scroll.ReactScrollViewCommandHelper;
import com.facebook.react.views.scroll.ReactScrollViewHelper;
import com.facebook.yoga.YogaConstants;
import javax.annotation.Nullable;

@ReactModule(
   name = "AndroidHorizontalScrollView"
)
public class ReactHorizontalScrollViewManager extends ViewGroupManager<ReactHorizontalScrollView> implements ReactScrollViewCommandHelper.ScrollCommandHandler<ReactHorizontalScrollView> {

   protected static final String REACT_CLASS = "AndroidHorizontalScrollView";
   private static final int[] SPACING_TYPES = new int[]{8, 0, 2, 1, 3};
   @Nullable
   private FpsListener mFpsListener;


   public ReactHorizontalScrollViewManager() {
      this((FpsListener)null);
   }

   public ReactHorizontalScrollViewManager(@Nullable FpsListener var1) {
      this.mFpsListener = null;
      this.mFpsListener = var1;
   }

   public ReactHorizontalScrollView createViewInstance(ThemedReactContext var1) {
      return new ReactHorizontalScrollView(var1, this.mFpsListener);
   }

   public void flashScrollIndicators(ReactHorizontalScrollView var1) {
      var1.flashScrollIndicators();
   }

   public String getName() {
      return "AndroidHorizontalScrollView";
   }

   public void receiveCommand(ReactHorizontalScrollView var1, int var2, @Nullable ReadableArray var3) {
      ReactScrollViewCommandHelper.receiveCommand(this, var1, var2, var3);
   }

   public void scrollTo(ReactHorizontalScrollView var1, ReactScrollViewCommandHelper.ScrollToCommandData var2) {
      if(var2.mAnimated) {
         var1.smoothScrollTo(var2.mDestX, var2.mDestY);
      } else {
         var1.scrollTo(var2.mDestX, var2.mDestY);
      }
   }

   public void scrollToEnd(ReactHorizontalScrollView var1, ReactScrollViewCommandHelper.ScrollToEndCommandData var2) {
      int var3 = var1.getChildAt(0).getWidth() + var1.getPaddingRight();
      if(var2.mAnimated) {
         var1.smoothScrollTo(var3, var1.getScrollY());
      } else {
         var1.scrollTo(var3, var1.getScrollY());
      }
   }

   @ReactPropGroup(
      customType = "Color",
      names = {"borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor"}
   )
   public void setBorderColor(ReactHorizontalScrollView var1, int var2, Integer var3) {
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
   public void setBorderRadius(ReactHorizontalScrollView var1, int var2, float var3) {
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
   public void setBorderStyle(ReactHorizontalScrollView var1, @Nullable String var2) {
      var1.setBorderStyle(var2);
   }

   @ReactPropGroup(
      defaultFloat = Float.NaN,
      names = {"borderWidth", "borderLeftWidth", "borderRightWidth", "borderTopWidth", "borderBottomWidth"}
   )
   public void setBorderWidth(ReactHorizontalScrollView var1, int var2, float var3) {
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
   public void setBottomFillColor(ReactHorizontalScrollView var1, int var2) {
      var1.setEndFillColor(var2);
   }

   @ReactProp(
      name = "overScrollMode"
   )
   public void setOverScrollMode(ReactHorizontalScrollView var1, String var2) {
      var1.setOverScrollMode(ReactScrollViewHelper.parseOverScrollMode(var2));
   }

   @ReactProp(
      name = "pagingEnabled"
   )
   public void setPagingEnabled(ReactHorizontalScrollView var1, boolean var2) {
      var1.setPagingEnabled(var2);
   }

   @ReactProp(
      name = "removeClippedSubviews"
   )
   public void setRemoveClippedSubviews(ReactHorizontalScrollView var1, boolean var2) {
      var1.setRemoveClippedSubviews(var2);
   }

   @ReactProp(
      defaultBoolean = true,
      name = "scrollEnabled"
   )
   public void setScrollEnabled(ReactHorizontalScrollView var1, boolean var2) {
      var1.setScrollEnabled(var2);
   }

   @ReactProp(
      name = "scrollPerfTag"
   )
   public void setScrollPerfTag(ReactHorizontalScrollView var1, String var2) {
      var1.setScrollPerfTag(var2);
   }

   @ReactProp(
      name = "sendMomentumEvents"
   )
   public void setSendMomentumEvents(ReactHorizontalScrollView var1, boolean var2) {
      var1.setSendMomentumEvents(var2);
   }

   @ReactProp(
      name = "showsHorizontalScrollIndicator"
   )
   public void setShowsHorizontalScrollIndicator(ReactHorizontalScrollView var1, boolean var2) {
      var1.setHorizontalScrollBarEnabled(var2);
   }
}
