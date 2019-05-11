package com.facebook.react.uimanager;

import com.facebook.react.bridge.ReadableMap;
import java.util.Arrays;
import java.util.HashSet;

public class ViewProps {

   public static final String ALIGN_CONTENT = "alignContent";
   public static final String ALIGN_ITEMS = "alignItems";
   public static final String ALIGN_SELF = "alignSelf";
   public static final String ALLOW_FONT_SCALING = "allowFontScaling";
   public static final String ASPECT_RATIO = "aspectRatio";
   public static final String BACKGROUND_COLOR = "backgroundColor";
   public static final String BORDER_BOTTOM_COLOR = "borderBottomColor";
   public static final String BORDER_BOTTOM_END_RADIUS = "borderBottomEndRadius";
   public static final String BORDER_BOTTOM_LEFT_RADIUS = "borderBottomLeftRadius";
   public static final String BORDER_BOTTOM_RIGHT_RADIUS = "borderBottomRightRadius";
   public static final String BORDER_BOTTOM_START_RADIUS = "borderBottomStartRadius";
   public static final String BORDER_BOTTOM_WIDTH = "borderBottomWidth";
   public static final String BORDER_COLOR = "borderColor";
   public static final String BORDER_END_COLOR = "borderEndColor";
   public static final String BORDER_END_WIDTH = "borderEndWidth";
   public static final String BORDER_LEFT_COLOR = "borderLeftColor";
   public static final String BORDER_LEFT_WIDTH = "borderLeftWidth";
   public static final String BORDER_RADIUS = "borderRadius";
   public static final String BORDER_RIGHT_COLOR = "borderRightColor";
   public static final String BORDER_RIGHT_WIDTH = "borderRightWidth";
   public static final int[] BORDER_SPACING_TYPES = new int[]{8, 4, 5, 1, 3, 0, 2};
   public static final String BORDER_START_COLOR = "borderStartColor";
   public static final String BORDER_START_WIDTH = "borderStartWidth";
   public static final String BORDER_TOP_COLOR = "borderTopColor";
   public static final String BORDER_TOP_END_RADIUS = "borderTopEndRadius";
   public static final String BORDER_TOP_LEFT_RADIUS = "borderTopLeftRadius";
   public static final String BORDER_TOP_RIGHT_RADIUS = "borderTopRightRadius";
   public static final String BORDER_TOP_START_RADIUS = "borderTopStartRadius";
   public static final String BORDER_TOP_WIDTH = "borderTopWidth";
   public static final String BORDER_WIDTH = "borderWidth";
   public static final String BOTTOM = "bottom";
   public static final String COLLAPSABLE = "collapsable";
   public static final String COLOR = "color";
   public static final String DISPLAY = "display";
   public static final String ELLIPSIZE_MODE = "ellipsizeMode";
   public static final String ENABLED = "enabled";
   public static final String END = "end";
   public static final String FLEX = "flex";
   public static final String FLEX_BASIS = "flexBasis";
   public static final String FLEX_DIRECTION = "flexDirection";
   public static final String FLEX_GROW = "flexGrow";
   public static final String FLEX_SHRINK = "flexShrink";
   public static final String FLEX_WRAP = "flexWrap";
   public static final String FONT_FAMILY = "fontFamily";
   public static final String FONT_SIZE = "fontSize";
   public static final String FONT_STYLE = "fontStyle";
   public static final String FONT_WEIGHT = "fontWeight";
   public static final String HEIGHT = "height";
   public static final String INCLUDE_FONT_PADDING = "includeFontPadding";
   public static final String JUSTIFY_CONTENT = "justifyContent";
   private static final HashSet<String> LAYOUT_ONLY_PROPS = new HashSet(Arrays.asList(new String[]{"alignSelf", "alignItems", "collapsable", "flex", "flexBasis", "flexDirection", "flexGrow", "flexShrink", "flexWrap", "justifyContent", "overflow", "alignContent", "display", "position", "right", "top", "bottom", "left", "start", "end", "width", "height", "minWidth", "maxWidth", "minHeight", "maxHeight", "margin", "marginVertical", "marginHorizontal", "marginLeft", "marginRight", "marginTop", "marginBottom", "marginStart", "marginEnd", "padding", "paddingVertical", "paddingHorizontal", "paddingLeft", "paddingRight", "paddingTop", "paddingBottom", "paddingStart", "paddingEnd"}));
   public static final String LEFT = "left";
   public static final String LINE_HEIGHT = "lineHeight";
   public static final String MARGIN = "margin";
   public static final String MARGIN_BOTTOM = "marginBottom";
   public static final String MARGIN_END = "marginEnd";
   public static final String MARGIN_HORIZONTAL = "marginHorizontal";
   public static final String MARGIN_LEFT = "marginLeft";
   public static final String MARGIN_RIGHT = "marginRight";
   public static final String MARGIN_START = "marginStart";
   public static final String MARGIN_TOP = "marginTop";
   public static final String MARGIN_VERTICAL = "marginVertical";
   public static final String MAX_HEIGHT = "maxHeight";
   public static final String MAX_WIDTH = "maxWidth";
   public static final String MIN_HEIGHT = "minHeight";
   public static final String MIN_WIDTH = "minWidth";
   public static final String NEEDS_OFFSCREEN_ALPHA_COMPOSITING = "needsOffscreenAlphaCompositing";
   public static final String NUMBER_OF_LINES = "numberOfLines";
   public static final String ON = "on";
   public static final String OPACITY = "opacity";
   public static final String OVERFLOW = "overflow";
   public static final String PADDING = "padding";
   public static final String PADDING_BOTTOM = "paddingBottom";
   public static final String PADDING_END = "paddingEnd";
   public static final String PADDING_HORIZONTAL = "paddingHorizontal";
   public static final String PADDING_LEFT = "paddingLeft";
   public static final int[] PADDING_MARGIN_SPACING_TYPES = new int[]{8, 7, 6, 4, 5, 1, 3, 0, 2};
   public static final String PADDING_RIGHT = "paddingRight";
   public static final String PADDING_START = "paddingStart";
   public static final String PADDING_TOP = "paddingTop";
   public static final String PADDING_VERTICAL = "paddingVertical";
   public static final String POINTER_EVENTS = "pointerEvents";
   public static final String POSITION = "position";
   public static final int[] POSITION_SPACING_TYPES = new int[]{4, 5, 1, 3};
   public static final String RESIZE_METHOD = "resizeMethod";
   public static final String RESIZE_MODE = "resizeMode";
   public static final String RIGHT = "right";
   public static final String START = "start";
   public static final String TEXT_ALIGN = "textAlign";
   public static final String TEXT_ALIGN_VERTICAL = "textAlignVertical";
   public static final String TEXT_BREAK_STRATEGY = "textBreakStrategy";
   public static final String TEXT_DECORATION_LINE = "textDecorationLine";
   public static final String TOP = "top";
   public static final String VIEW_CLASS_NAME = "RCTView";
   public static final String WIDTH = "width";
   public static boolean sIsOptimizationsEnabled;


   public static boolean isLayoutOnly(ReadableMap var0, String var1) {
      boolean var4 = LAYOUT_ONLY_PROPS.contains(var1);
      boolean var3 = true;
      if(var4) {
         return true;
      } else if("pointerEvents".equals(var1)) {
         String var5 = var0.getString(var1);
         if(!"auto".equals(var5)) {
            if("box-none".equals(var5)) {
               return true;
            }

            var3 = false;
         }

         return var3;
      } else if(!sIsOptimizationsEnabled) {
         return false;
      } else {
         byte var2;
         label150: {
            switch(var1.hashCode()) {
            case -1989576717:
               if(var1.equals("borderRightColor")) {
                  var2 = 5;
                  break label150;
               }
               break;
            case -1971292586:
               if(var1.equals("borderRightWidth")) {
                  var2 = 11;
                  break label150;
               }
               break;
            case -1470826662:
               if(var1.equals("borderTopColor")) {
                  var2 = 6;
                  break label150;
               }
               break;
            case -1452542531:
               if(var1.equals("borderTopWidth")) {
                  var2 = 10;
                  break label150;
               }
               break;
            case -1308858324:
               if(var1.equals("borderBottomColor")) {
                  var2 = 7;
                  break label150;
               }
               break;
            case -1290574193:
               if(var1.equals("borderBottomWidth")) {
                  var2 = 12;
                  break label150;
               }
               break;
            case -1267206133:
               if(var1.equals("opacity")) {
                  var2 = 0;
                  break label150;
               }
               break;
            case -242276144:
               if(var1.equals("borderLeftColor")) {
                  var2 = 4;
                  break label150;
               }
               break;
            case -223992013:
               if(var1.equals("borderLeftWidth")) {
                  var2 = 9;
                  break label150;
               }
               break;
            case 529642498:
               if(var1.equals("overflow")) {
                  var2 = 14;
                  break label150;
               }
               break;
            case 722830999:
               if(var1.equals("borderColor")) {
                  var2 = 3;
                  break label150;
               }
               break;
            case 741115130:
               if(var1.equals("borderWidth")) {
                  var2 = 8;
                  break label150;
               }
               break;
            case 1287124693:
               if(var1.equals("backgroundColor")) {
                  var2 = 1;
                  break label150;
               }
               break;
            case 1288688105:
               if(var1.equals("onLayout")) {
                  var2 = 13;
                  break label150;
               }
               break;
            case 1349188574:
               if(var1.equals("borderRadius")) {
                  var2 = 2;
                  break label150;
               }
            }

            var2 = -1;
         }

         switch(var2) {
         case 0:
            if(var0.getDouble("opacity") == 1.0D) {
               return true;
            }

            return false;
         case 1:
            if(var0.getInt("backgroundColor") == 0) {
               return true;
            }

            return false;
         case 2:
            if(var0.hasKey("backgroundColor") && var0.getInt("backgroundColor") != 0) {
               return false;
            } else {
               if(var0.hasKey("borderWidth") && var0.getDouble("borderWidth") != 0.0D) {
                  return false;
               }

               return true;
            }
         case 3:
            if(var0.getInt("borderColor") == 0) {
               return true;
            }

            return false;
         case 4:
            if(var0.getInt("borderLeftColor") == 0) {
               return true;
            }

            return false;
         case 5:
            if(var0.getInt("borderRightColor") == 0) {
               return true;
            }

            return false;
         case 6:
            if(var0.getInt("borderTopColor") == 0) {
               return true;
            }

            return false;
         case 7:
            if(var0.getInt("borderBottomColor") == 0) {
               return true;
            }

            return false;
         case 8:
            if(var0.getDouble("borderWidth") == 0.0D) {
               return true;
            }

            return false;
         case 9:
            if(var0.getDouble("borderLeftWidth") == 0.0D) {
               return true;
            }

            return false;
         case 10:
            if(var0.getDouble("borderTopWidth") == 0.0D) {
               return true;
            }

            return false;
         case 11:
            if(var0.getDouble("borderRightWidth") == 0.0D) {
               return true;
            }

            return false;
         case 12:
            if(var0.getDouble("borderBottomWidth") == 0.0D) {
               return true;
            }

            return false;
         case 13:
            return true;
         case 14:
            return true;
         default:
            return false;
         }
      }
   }
}
