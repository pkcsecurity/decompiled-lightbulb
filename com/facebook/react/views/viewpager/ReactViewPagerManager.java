package com.facebook.react.views.viewpager;

import android.view.View;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.viewpager.ReactViewPager;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(
   name = "AndroidViewPager"
)
public class ReactViewPagerManager extends ViewGroupManager<ReactViewPager> {

   public static final int COMMAND_SET_PAGE = 1;
   public static final int COMMAND_SET_PAGE_WITHOUT_ANIMATION = 2;
   protected static final String REACT_CLASS = "AndroidViewPager";


   public void addView(ReactViewPager var1, View var2, int var3) {
      var1.addViewToAdapter(var2, var3);
   }

   protected ReactViewPager createViewInstance(ThemedReactContext var1) {
      return new ReactViewPager(var1);
   }

   public View getChildAt(ReactViewPager var1, int var2) {
      return var1.getViewFromAdapter(var2);
   }

   public int getChildCount(ReactViewPager var1) {
      return var1.getViewCountInAdapter();
   }

   public Map<String, Integer> getCommandsMap() {
      return MapBuilder.of("setPage", Integer.valueOf(1), "setPageWithoutAnimation", Integer.valueOf(2));
   }

   public Map getExportedCustomDirectEventTypeConstants() {
      return MapBuilder.of("topPageScroll", MapBuilder.of("registrationName", "onPageScroll"), "topPageScrollStateChanged", MapBuilder.of("registrationName", "onPageScrollStateChanged"), "topPageSelected", MapBuilder.of("registrationName", "onPageSelected"));
   }

   public String getName() {
      return "AndroidViewPager";
   }

   public boolean needsCustomLayoutForChildren() {
      return true;
   }

   public void receiveCommand(ReactViewPager var1, int var2, @Nullable ReadableArray var3) {
      Assertions.assertNotNull(var1);
      Assertions.assertNotNull(var3);
      switch(var2) {
      case 1:
         var1.setCurrentItemFromJs(var3.getInt(0), true);
         return;
      case 2:
         var1.setCurrentItemFromJs(var3.getInt(0), false);
         return;
      default:
         throw new IllegalArgumentException(String.format("Unsupported command %d received by %s.", new Object[]{Integer.valueOf(var2), this.getClass().getSimpleName()}));
      }
   }

   public void removeViewAt(ReactViewPager var1, int var2) {
      var1.removeViewFromAdapter(var2);
   }

   @ReactProp(
      defaultFloat = 0.0F,
      name = "pageMargin"
   )
   public void setPageMargin(ReactViewPager var1, float var2) {
      var1.setPageMargin((int)PixelUtil.toPixelFromDIP(var2));
   }

   @ReactProp(
      defaultBoolean = false,
      name = "peekEnabled"
   )
   public void setPeekEnabled(ReactViewPager var1, boolean var2) {
      var1.setClipToPadding(var2 ^ true);
   }

   @ReactProp(
      defaultBoolean = true,
      name = "scrollEnabled"
   )
   public void setScrollEnabled(ReactViewPager var1, boolean var2) {
      var1.setScrollEnabled(var2);
   }
}
