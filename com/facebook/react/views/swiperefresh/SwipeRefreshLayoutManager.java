package com.facebook.react.views.swiperefresh;

import android.support.v4.widget.SwipeRefreshLayout;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.swiperefresh.ReactSwipeRefreshLayout;
import com.facebook.react.views.swiperefresh.RefreshEvent;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(
   name = "AndroidSwipeRefreshLayout"
)
public class SwipeRefreshLayoutManager extends ViewGroupManager<ReactSwipeRefreshLayout> {

   protected static final String REACT_CLASS = "AndroidSwipeRefreshLayout";


   protected void addEventEmitters(final ThemedReactContext var1, final ReactSwipeRefreshLayout var2) {
      var2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
         public void onRefresh() {
            ((UIManagerModule)var1.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new RefreshEvent(var2.getId()));
         }
      });
   }

   protected ReactSwipeRefreshLayout createViewInstance(ThemedReactContext var1) {
      return new ReactSwipeRefreshLayout(var1);
   }

   public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
      return MapBuilder.builder().put("topRefresh", MapBuilder.of("registrationName", "onRefresh")).build();
   }

   @Nullable
   public Map<String, Object> getExportedViewConstants() {
      return MapBuilder.of("SIZE", MapBuilder.of("DEFAULT", Integer.valueOf(1), "LARGE", Integer.valueOf(0)));
   }

   public String getName() {
      return "AndroidSwipeRefreshLayout";
   }

   @ReactProp(
      customType = "ColorArray",
      name = "colors"
   )
   public void setColors(ReactSwipeRefreshLayout var1, @Nullable ReadableArray var2) {
      int var3 = 0;
      if(var2 == null) {
         var1.setColorSchemeColors(new int[0]);
      } else {
         int[] var4;
         for(var4 = new int[var2.size()]; var3 < var2.size(); ++var3) {
            var4[var3] = var2.getInt(var3);
         }

         var1.setColorSchemeColors(var4);
      }
   }

   @ReactProp(
      defaultBoolean = true,
      name = "enabled"
   )
   public void setEnabled(ReactSwipeRefreshLayout var1, boolean var2) {
      var1.setEnabled(var2);
   }

   @ReactProp(
      customType = "Color",
      defaultInt = 0,
      name = "progressBackgroundColor"
   )
   public void setProgressBackgroundColor(ReactSwipeRefreshLayout var1, int var2) {
      var1.setProgressBackgroundColorSchemeColor(var2);
   }

   @ReactProp(
      defaultFloat = 0.0F,
      name = "progressViewOffset"
   )
   public void setProgressViewOffset(ReactSwipeRefreshLayout var1, float var2) {
      var1.setProgressViewOffset(var2);
   }

   @ReactProp(
      name = "refreshing"
   )
   public void setRefreshing(ReactSwipeRefreshLayout var1, boolean var2) {
      var1.setRefreshing(var2);
   }

   @ReactProp(
      defaultInt = 1,
      name = "size"
   )
   public void setSize(ReactSwipeRefreshLayout var1, int var2) {
      var1.setSize(var2);
   }
}
