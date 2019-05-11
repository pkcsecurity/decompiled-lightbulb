package com.facebook.react.flat;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.flat.FlatViewGroup;
import com.facebook.react.flat.FlatViewManager;
import com.facebook.react.flat.RCTView;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.view.ReactDrawableHelper;
import java.util.Map;
import javax.annotation.Nullable;

public final class RCTViewManager extends FlatViewManager {

   private static final int CMD_HOTSPOT_UPDATE = 1;
   private static final int CMD_SET_PRESSED = 2;
   static final String REACT_CLASS = "RCTView";
   private static final int[] TMP_INT_ARRAY = new int[2];


   private static PointerEvents parsePointerEvents(@Nullable String var0) {
      if(var0 != null) {
         byte var1 = -1;
         int var2 = var0.hashCode();
         if(var2 != -2089141766) {
            if(var2 != -2089112978) {
               if(var2 != 3005871) {
                  if(var2 == 3387192 && var0.equals("none")) {
                     var1 = 0;
                  }
               } else if(var0.equals("auto")) {
                  var1 = 1;
               }
            } else if(var0.equals("box-only")) {
               var1 = 3;
            }
         } else if(var0.equals("box-none")) {
            var1 = 2;
         }

         switch(var1) {
         case 0:
            return PointerEvents.NONE;
         case 1:
            return PointerEvents.AUTO;
         case 2:
            return PointerEvents.BOX_NONE;
         case 3:
            return PointerEvents.BOX_ONLY;
         }
      }

      return PointerEvents.AUTO;
   }

   public RCTView createShadowNodeInstance() {
      return new RCTView();
   }

   public Map<String, Integer> getCommandsMap() {
      return MapBuilder.of("hotspotUpdate", Integer.valueOf(1), "setPressed", Integer.valueOf(2));
   }

   public String getName() {
      return "RCTView";
   }

   public Class<RCTView> getShadowNodeClass() {
      return RCTView.class;
   }

   public void receiveCommand(FlatViewGroup var1, int var2, @Nullable ReadableArray var3) {
      switch(var2) {
      case 1:
         if(var3 != null && var3.size() == 2) {
            if(VERSION.SDK_INT >= 21) {
               var1.getLocationOnScreen(TMP_INT_ARRAY);
               var1.drawableHotspotChanged(PixelUtil.toPixelFromDIP(var3.getDouble(0)) - (float)TMP_INT_ARRAY[0], PixelUtil.toPixelFromDIP(var3.getDouble(1)) - (float)TMP_INT_ARRAY[1]);
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

   @ReactProp(
      name = "hitSlop"
   )
   public void setHitSlop(FlatViewGroup var1, @Nullable ReadableMap var2) {
      if(var2 == null) {
         var1.setHitSlopRect((Rect)null);
      } else {
         var1.setHitSlopRect(new Rect((int)PixelUtil.toPixelFromDIP(var2.getDouble("left")), (int)PixelUtil.toPixelFromDIP(var2.getDouble("top")), (int)PixelUtil.toPixelFromDIP(var2.getDouble("right")), (int)PixelUtil.toPixelFromDIP(var2.getDouble("bottom"))));
      }
   }

   @ReactProp(
      name = "nativeBackgroundAndroid"
   )
   public void setHotspot(FlatViewGroup var1, @Nullable ReadableMap var2) {
      Drawable var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = ReactDrawableHelper.createDrawableFromJSDescription(var1.getContext(), var2);
      }

      var1.setHotspot(var3);
   }

   @ReactProp(
      name = "needsOffscreenAlphaCompositing"
   )
   public void setNeedsOffscreenAlphaCompositing(FlatViewGroup var1, boolean var2) {
      var1.setNeedsOffscreenAlphaCompositing(var2);
   }

   @ReactProp(
      name = "pointerEvents"
   )
   public void setPointerEvents(FlatViewGroup var1, @Nullable String var2) {
      var1.setPointerEvents(parsePointerEvents(var2));
   }

   @ReactProp(
      name = "removeClippedSubviews"
   )
   public void setRemoveClippedSubviews(FlatViewGroup var1, boolean var2) {
      var1.setRemoveClippedSubviews(var2);
   }
}
