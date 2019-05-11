package com.facebook.react.views.view;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.View;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.react.views.view.ReactDrawableHelper;
import com.facebook.react.views.view.ReactViewGroup;
import com.facebook.yoga.YogaConstants;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(
   name = "RCTView"
)
public class ReactViewManager extends ViewGroupManager<ReactViewGroup> {

   private static final int CMD_HOTSPOT_UPDATE = 1;
   private static final int CMD_SET_PRESSED = 2;
   @VisibleForTesting
   public static final String REACT_CLASS = "RCTView";
   private static final int[] SPACING_TYPES = new int[]{8, 0, 2, 1, 3, 4, 5};


   public void addView(ReactViewGroup var1, View var2, int var3) {
      if(var1.getRemoveClippedSubviews()) {
         var1.addViewWithSubviewClippingEnabled(var2, var3);
      } else {
         var1.addView(var2, var3);
      }
   }

   public ReactViewGroup createViewInstance(ThemedReactContext var1) {
      return new ReactViewGroup(var1);
   }

   public View getChildAt(ReactViewGroup var1, int var2) {
      return var1.getRemoveClippedSubviews()?var1.getChildAtWithSubviewClippingEnabled(var2):var1.getChildAt(var2);
   }

   public int getChildCount(ReactViewGroup var1) {
      return var1.getRemoveClippedSubviews()?var1.getAllChildrenCount():var1.getChildCount();
   }

   public Map<String, Integer> getCommandsMap() {
      return MapBuilder.of("hotspotUpdate", Integer.valueOf(1), "setPressed", Integer.valueOf(2));
   }

   public String getName() {
      return "RCTView";
   }

   public void receiveCommand(ReactViewGroup var1, int var2, @Nullable ReadableArray var3) {
      switch(var2) {
      case 1:
         if(var3 != null && var3.size() == 2) {
            if(VERSION.SDK_INT >= 21) {
               var1.drawableHotspotChanged(PixelUtil.toPixelFromDIP(var3.getDouble(0)), PixelUtil.toPixelFromDIP(var3.getDouble(1)));
               return;
            }

            return;
         }

         throw new JSApplicationIllegalArgumentException("Illegal number of arguments for \'updateHotspot\' command");
      case 2:
         if(var3 != null && var3.size() == 1) {
            var1.setPressed(var3.getBoolean(0));
            return;
         }

         throw new JSApplicationIllegalArgumentException("Illegal number of arguments for \'setPressed\' command");
      default:
      }
   }

   public void removeAllViews(ReactViewGroup var1) {
      if(var1.getRemoveClippedSubviews()) {
         var1.removeAllViewsWithSubviewClippingEnabled();
      } else {
         var1.removeAllViews();
      }
   }

   public void removeViewAt(ReactViewGroup var1, int var2) {
      if(var1.getRemoveClippedSubviews()) {
         View var3 = this.getChildAt(var1, var2);
         if(var3.getParent() != null) {
            var1.removeView(var3);
         }

         var1.removeViewWithSubviewClippingEnabled(var3);
      } else {
         var1.removeViewAt(var2);
      }
   }

   @ReactProp(
      name = "accessible"
   )
   public void setAccessible(ReactViewGroup var1, boolean var2) {
      var1.setFocusable(var2);
   }

   @ReactPropGroup(
      customType = "Color",
      names = {"borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor", "borderStartColor", "borderEndColor"}
   )
   public void setBorderColor(ReactViewGroup var1, int var2, Integer var3) {
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
      names = {"borderRadius", "borderTopLeftRadius", "borderTopRightRadius", "borderBottomRightRadius", "borderBottomLeftRadius", "borderTopStartRadius", "borderTopEndRadius", "borderBottomStartRadius", "borderBottomEndRadius"}
   )
   public void setBorderRadius(ReactViewGroup var1, int var2, float var3) {
      float var4 = var3;
      if(!YogaConstants.isUndefined(var3)) {
         var4 = var3;
         if(var3 < 0.0F) {
            var4 = Float.NaN;
         }
      }

      var3 = var4;
      if(!YogaConstants.isUndefined(var4)) {
         var3 = PixelUtil.toPixelFromDIP(var4);
      }

      if(var2 == 0) {
         var1.setBorderRadius(var3);
      } else {
         var1.setBorderRadius(var3, var2 - 1);
      }
   }

   @ReactProp(
      name = "borderStyle"
   )
   public void setBorderStyle(ReactViewGroup var1, @Nullable String var2) {
      var1.setBorderStyle(var2);
   }

   @ReactPropGroup(
      defaultFloat = Float.NaN,
      names = {"borderWidth", "borderLeftWidth", "borderRightWidth", "borderTopWidth", "borderBottomWidth", "borderStartWidth", "borderEndWidth"}
   )
   public void setBorderWidth(ReactViewGroup var1, int var2, float var3) {
      float var4 = var3;
      if(!YogaConstants.isUndefined(var3)) {
         var4 = var3;
         if(var3 < 0.0F) {
            var4 = Float.NaN;
         }
      }

      var3 = var4;
      if(!YogaConstants.isUndefined(var4)) {
         var3 = PixelUtil.toPixelFromDIP(var4);
      }

      var1.setBorderWidth(SPACING_TYPES[var2], var3);
   }

   @ReactProp(
      name = "collapsable"
   )
   public void setCollapsable(ReactViewGroup var1, boolean var2) {}

   @ReactProp(
      name = "hitSlop"
   )
   public void setHitSlop(ReactViewGroup var1, @Nullable ReadableMap var2) {
      if(var2 == null) {
         var1.setHitSlopRect((Rect)null);
      } else {
         boolean var7 = var2.hasKey("left");
         int var6 = 0;
         int var3;
         if(var7) {
            var3 = (int)PixelUtil.toPixelFromDIP(var2.getDouble("left"));
         } else {
            var3 = 0;
         }

         int var4;
         if(var2.hasKey("top")) {
            var4 = (int)PixelUtil.toPixelFromDIP(var2.getDouble("top"));
         } else {
            var4 = 0;
         }

         int var5;
         if(var2.hasKey("right")) {
            var5 = (int)PixelUtil.toPixelFromDIP(var2.getDouble("right"));
         } else {
            var5 = 0;
         }

         if(var2.hasKey("bottom")) {
            var6 = (int)PixelUtil.toPixelFromDIP(var2.getDouble("bottom"));
         }

         var1.setHitSlopRect(new Rect(var3, var4, var5, var6));
      }
   }

   @ReactProp(
      name = "nativeBackgroundAndroid"
   )
   public void setNativeBackground(ReactViewGroup var1, @Nullable ReadableMap var2) {
      Drawable var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = ReactDrawableHelper.createDrawableFromJSDescription(var1.getContext(), var2);
      }

      var1.setTranslucentBackgroundDrawable(var3);
   }

   @ReactProp(
      name = "nativeForegroundAndroid"
   )
   @TargetApi(23)
   public void setNativeForeground(ReactViewGroup var1, @Nullable ReadableMap var2) {
      Drawable var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = ReactDrawableHelper.createDrawableFromJSDescription(var1.getContext(), var2);
      }

      var1.setForeground(var3);
   }

   @ReactProp(
      name = "needsOffscreenAlphaCompositing"
   )
   public void setNeedsOffscreenAlphaCompositing(ReactViewGroup var1, boolean var2) {
      var1.setNeedsOffscreenAlphaCompositing(var2);
   }

   @ReactProp(
      name = "overflow"
   )
   public void setOverflow(ReactViewGroup var1, String var2) {
      var1.setOverflow(var2);
   }

   @ReactProp(
      name = "pointerEvents"
   )
   public void setPointerEvents(ReactViewGroup var1, @Nullable String var2) {
      if(var2 == null) {
         var1.setPointerEvents(PointerEvents.AUTO);
      } else {
         var1.setPointerEvents(PointerEvents.valueOf(var2.toUpperCase(Locale.US).replace("-", "_")));
      }
   }

   @ReactProp(
      name = "removeClippedSubviews"
   )
   public void setRemoveClippedSubviews(ReactViewGroup var1, boolean var2) {
      var1.setRemoveClippedSubviews(var2);
   }
}
