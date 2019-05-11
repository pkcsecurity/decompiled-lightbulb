package com.facebook.react.uimanager;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaDisplay;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaOverflow;
import com.facebook.yoga.YogaPositionType;
import com.facebook.yoga.YogaUnit;
import com.facebook.yoga.YogaWrap;
import javax.annotation.Nullable;

public class LayoutShadowNode extends ReactShadowNodeImpl {

   private final LayoutShadowNode.MutableYogaValue mTempYogaValue = new LayoutShadowNode.MutableYogaValue(null);


   private int maybeTransformLeftRightToStartEnd(int var1) {
      return !I18nUtil.getInstance().doLeftAndRightSwapInRTL(this.getThemedContext())?var1:(var1 != 0?(var1 != 2?var1:5):4);
   }

   @ReactProp(
      name = "alignContent"
   )
   public void setAlignContent(@Nullable String var1) {
      if(!this.isVirtual()) {
         if(var1 == null) {
            this.setAlignContent(YogaAlign.FLEX_START);
         } else {
            byte var2 = -1;
            switch(var1.hashCode()) {
            case -1881872635:
               if(var1.equals("stretch")) {
                  var2 = 4;
               }
               break;
            case -1720785339:
               if(var1.equals("baseline")) {
                  var2 = 5;
               }
               break;
            case -1364013995:
               if(var1.equals("center")) {
                  var2 = 2;
               }
               break;
            case -46581362:
               if(var1.equals("flex-start")) {
                  var2 = 1;
               }
               break;
            case 3005871:
               if(var1.equals("auto")) {
                  var2 = 0;
               }
               break;
            case 441309761:
               if(var1.equals("space-between")) {
                  var2 = 6;
               }
               break;
            case 1742952711:
               if(var1.equals("flex-end")) {
                  var2 = 3;
               }
               break;
            case 1937124468:
               if(var1.equals("space-around")) {
                  var2 = 7;
               }
            }

            switch(var2) {
            case 0:
               this.setAlignContent(YogaAlign.AUTO);
               return;
            case 1:
               this.setAlignContent(YogaAlign.FLEX_START);
               return;
            case 2:
               this.setAlignContent(YogaAlign.CENTER);
               return;
            case 3:
               this.setAlignContent(YogaAlign.FLEX_END);
               return;
            case 4:
               this.setAlignContent(YogaAlign.STRETCH);
               return;
            case 5:
               this.setAlignContent(YogaAlign.BASELINE);
               return;
            case 6:
               this.setAlignContent(YogaAlign.SPACE_BETWEEN);
               return;
            case 7:
               this.setAlignContent(YogaAlign.SPACE_AROUND);
               return;
            default:
               StringBuilder var3 = new StringBuilder();
               var3.append("invalid value for alignContent: ");
               var3.append(var1);
               throw new JSApplicationIllegalArgumentException(var3.toString());
            }
         }
      }
   }

   @ReactProp(
      name = "alignItems"
   )
   public void setAlignItems(@Nullable String var1) {
      if(!this.isVirtual()) {
         if(var1 == null) {
            this.setAlignItems(YogaAlign.STRETCH);
         } else {
            byte var2 = -1;
            switch(var1.hashCode()) {
            case -1881872635:
               if(var1.equals("stretch")) {
                  var2 = 4;
               }
               break;
            case -1720785339:
               if(var1.equals("baseline")) {
                  var2 = 5;
               }
               break;
            case -1364013995:
               if(var1.equals("center")) {
                  var2 = 2;
               }
               break;
            case -46581362:
               if(var1.equals("flex-start")) {
                  var2 = 1;
               }
               break;
            case 3005871:
               if(var1.equals("auto")) {
                  var2 = 0;
               }
               break;
            case 441309761:
               if(var1.equals("space-between")) {
                  var2 = 6;
               }
               break;
            case 1742952711:
               if(var1.equals("flex-end")) {
                  var2 = 3;
               }
               break;
            case 1937124468:
               if(var1.equals("space-around")) {
                  var2 = 7;
               }
            }

            switch(var2) {
            case 0:
               this.setAlignItems(YogaAlign.AUTO);
               return;
            case 1:
               this.setAlignItems(YogaAlign.FLEX_START);
               return;
            case 2:
               this.setAlignItems(YogaAlign.CENTER);
               return;
            case 3:
               this.setAlignItems(YogaAlign.FLEX_END);
               return;
            case 4:
               this.setAlignItems(YogaAlign.STRETCH);
               return;
            case 5:
               this.setAlignItems(YogaAlign.BASELINE);
               return;
            case 6:
               this.setAlignItems(YogaAlign.SPACE_BETWEEN);
               return;
            case 7:
               this.setAlignItems(YogaAlign.SPACE_AROUND);
               return;
            default:
               StringBuilder var3 = new StringBuilder();
               var3.append("invalid value for alignItems: ");
               var3.append(var1);
               throw new JSApplicationIllegalArgumentException(var3.toString());
            }
         }
      }
   }

   @ReactProp(
      name = "alignSelf"
   )
   public void setAlignSelf(@Nullable String var1) {
      if(!this.isVirtual()) {
         if(var1 == null) {
            this.setAlignSelf(YogaAlign.AUTO);
         } else {
            byte var2 = -1;
            switch(var1.hashCode()) {
            case -1881872635:
               if(var1.equals("stretch")) {
                  var2 = 4;
               }
               break;
            case -1720785339:
               if(var1.equals("baseline")) {
                  var2 = 5;
               }
               break;
            case -1364013995:
               if(var1.equals("center")) {
                  var2 = 2;
               }
               break;
            case -46581362:
               if(var1.equals("flex-start")) {
                  var2 = 1;
               }
               break;
            case 3005871:
               if(var1.equals("auto")) {
                  var2 = 0;
               }
               break;
            case 441309761:
               if(var1.equals("space-between")) {
                  var2 = 6;
               }
               break;
            case 1742952711:
               if(var1.equals("flex-end")) {
                  var2 = 3;
               }
               break;
            case 1937124468:
               if(var1.equals("space-around")) {
                  var2 = 7;
               }
            }

            switch(var2) {
            case 0:
               this.setAlignSelf(YogaAlign.AUTO);
               return;
            case 1:
               this.setAlignSelf(YogaAlign.FLEX_START);
               return;
            case 2:
               this.setAlignSelf(YogaAlign.CENTER);
               return;
            case 3:
               this.setAlignSelf(YogaAlign.FLEX_END);
               return;
            case 4:
               this.setAlignSelf(YogaAlign.STRETCH);
               return;
            case 5:
               this.setAlignSelf(YogaAlign.BASELINE);
               return;
            case 6:
               this.setAlignSelf(YogaAlign.SPACE_BETWEEN);
               return;
            case 7:
               this.setAlignSelf(YogaAlign.SPACE_AROUND);
               return;
            default:
               StringBuilder var3 = new StringBuilder();
               var3.append("invalid value for alignSelf: ");
               var3.append(var1);
               throw new JSApplicationIllegalArgumentException(var3.toString());
            }
         }
      }
   }

   @ReactProp(
      defaultFloat = Float.NaN,
      name = "aspectRatio"
   )
   public void setAspectRatio(float var1) {
      this.setStyleAspectRatio(var1);
   }

   @ReactPropGroup(
      defaultFloat = Float.NaN,
      names = {"borderWidth", "borderStartWidth", "borderEndWidth", "borderTopWidth", "borderBottomWidth", "borderLeftWidth", "borderRightWidth"}
   )
   public void setBorderWidths(int var1, float var2) {
      if(!this.isVirtual()) {
         this.setBorder(this.maybeTransformLeftRightToStartEnd(ViewProps.BORDER_SPACING_TYPES[var1]), PixelUtil.toPixelFromDIP(var2));
      }
   }

   @ReactProp(
      name = "display"
   )
   public void setDisplay(@Nullable String var1) {
      if(!this.isVirtual()) {
         if(var1 == null) {
            this.setDisplay(YogaDisplay.FLEX);
         } else {
            byte var2 = -1;
            int var3 = var1.hashCode();
            if(var3 != 3145721) {
               if(var3 == 3387192 && var1.equals("none")) {
                  var2 = 1;
               }
            } else if(var1.equals("flex")) {
               var2 = 0;
            }

            switch(var2) {
            case 0:
               this.setDisplay(YogaDisplay.FLEX);
               return;
            case 1:
               this.setDisplay(YogaDisplay.NONE);
               return;
            default:
               StringBuilder var4 = new StringBuilder();
               var4.append("invalid value for display: ");
               var4.append(var1);
               throw new JSApplicationIllegalArgumentException(var4.toString());
            }
         }
      }
   }

   @ReactProp(
      defaultFloat = 0.0F,
      name = "flex"
   )
   public void setFlex(float var1) {
      if(!this.isVirtual()) {
         super.setFlex(var1);
      }
   }

   @ReactProp(
      name = "flexBasis"
   )
   public void setFlexBasis(Dynamic var1) {
      if(!this.isVirtual()) {
         this.mTempYogaValue.setFromDynamic(var1);
         switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()]) {
         case 1:
         case 2:
            this.setFlexBasis(this.mTempYogaValue.value);
            break;
         case 3:
            this.setFlexBasisAuto();
            break;
         case 4:
            this.setFlexBasisPercent(this.mTempYogaValue.value);
         }

         var1.recycle();
      }
   }

   @ReactProp(
      name = "flexDirection"
   )
   public void setFlexDirection(@Nullable String var1) {
      if(!this.isVirtual()) {
         if(var1 == null) {
            this.setFlexDirection(YogaFlexDirection.COLUMN);
         } else {
            byte var2 = -1;
            int var3 = var1.hashCode();
            if(var3 != -1448970769) {
               if(var3 != -1354837162) {
                  if(var3 != 113114) {
                     if(var3 == 1272730475 && var1.equals("column-reverse")) {
                        var2 = 1;
                     }
                  } else if(var1.equals("row")) {
                     var2 = 2;
                  }
               } else if(var1.equals("column")) {
                  var2 = 0;
               }
            } else if(var1.equals("row-reverse")) {
               var2 = 3;
            }

            switch(var2) {
            case 0:
               this.setFlexDirection(YogaFlexDirection.COLUMN);
               return;
            case 1:
               this.setFlexDirection(YogaFlexDirection.COLUMN_REVERSE);
               return;
            case 2:
               this.setFlexDirection(YogaFlexDirection.ROW);
               return;
            case 3:
               this.setFlexDirection(YogaFlexDirection.ROW_REVERSE);
               return;
            default:
               StringBuilder var4 = new StringBuilder();
               var4.append("invalid value for flexDirection: ");
               var4.append(var1);
               throw new JSApplicationIllegalArgumentException(var4.toString());
            }
         }
      }
   }

   @ReactProp(
      defaultFloat = 0.0F,
      name = "flexGrow"
   )
   public void setFlexGrow(float var1) {
      if(!this.isVirtual()) {
         super.setFlexGrow(var1);
      }
   }

   @ReactProp(
      defaultFloat = 0.0F,
      name = "flexShrink"
   )
   public void setFlexShrink(float var1) {
      if(!this.isVirtual()) {
         super.setFlexShrink(var1);
      }
   }

   @ReactProp(
      name = "flexWrap"
   )
   public void setFlexWrap(@Nullable String var1) {
      if(!this.isVirtual()) {
         if(var1 == null) {
            this.setFlexWrap(YogaWrap.NO_WRAP);
         } else {
            byte var2 = -1;
            int var3 = var1.hashCode();
            if(var3 != -1039592053) {
               if(var3 == 3657802 && var1.equals("wrap")) {
                  var2 = 1;
               }
            } else if(var1.equals("nowrap")) {
               var2 = 0;
            }

            switch(var2) {
            case 0:
               this.setFlexWrap(YogaWrap.NO_WRAP);
               return;
            case 1:
               this.setFlexWrap(YogaWrap.WRAP);
               return;
            default:
               StringBuilder var4 = new StringBuilder();
               var4.append("invalid value for flexWrap: ");
               var4.append(var1);
               throw new JSApplicationIllegalArgumentException(var4.toString());
            }
         }
      }
   }

   @ReactProp(
      name = "height"
   )
   public void setHeight(Dynamic var1) {
      if(!this.isVirtual()) {
         this.mTempYogaValue.setFromDynamic(var1);
         switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()]) {
         case 1:
         case 2:
            this.setStyleHeight(this.mTempYogaValue.value);
            break;
         case 3:
            this.setStyleHeightAuto();
            break;
         case 4:
            this.setStyleHeightPercent(this.mTempYogaValue.value);
         }

         var1.recycle();
      }
   }

   @ReactProp(
      name = "justifyContent"
   )
   public void setJustifyContent(@Nullable String var1) {
      if(!this.isVirtual()) {
         if(var1 == null) {
            this.setJustifyContent(YogaJustify.FLEX_START);
         } else {
            byte var2 = -1;
            switch(var1.hashCode()) {
            case -1364013995:
               if(var1.equals("center")) {
                  var2 = 1;
               }
               break;
            case -46581362:
               if(var1.equals("flex-start")) {
                  var2 = 0;
               }
               break;
            case 441309761:
               if(var1.equals("space-between")) {
                  var2 = 3;
               }
               break;
            case 1742952711:
               if(var1.equals("flex-end")) {
                  var2 = 2;
               }
               break;
            case 1937124468:
               if(var1.equals("space-around")) {
                  var2 = 4;
               }
            }

            switch(var2) {
            case 0:
               this.setJustifyContent(YogaJustify.FLEX_START);
               return;
            case 1:
               this.setJustifyContent(YogaJustify.CENTER);
               return;
            case 2:
               this.setJustifyContent(YogaJustify.FLEX_END);
               return;
            case 3:
               this.setJustifyContent(YogaJustify.SPACE_BETWEEN);
               return;
            case 4:
               this.setJustifyContent(YogaJustify.SPACE_AROUND);
               return;
            default:
               StringBuilder var3 = new StringBuilder();
               var3.append("invalid value for justifyContent: ");
               var3.append(var1);
               throw new JSApplicationIllegalArgumentException(var3.toString());
            }
         }
      }
   }

   @ReactPropGroup(
      names = {"margin", "marginVertical", "marginHorizontal", "marginStart", "marginEnd", "marginTop", "marginBottom", "marginLeft", "marginRight"}
   )
   public void setMargins(int var1, Dynamic var2) {
      if(!this.isVirtual()) {
         var1 = this.maybeTransformLeftRightToStartEnd(ViewProps.PADDING_MARGIN_SPACING_TYPES[var1]);
         this.mTempYogaValue.setFromDynamic(var2);
         switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()]) {
         case 1:
         case 2:
            this.setMargin(var1, this.mTempYogaValue.value);
            break;
         case 3:
            this.setMarginAuto(var1);
            break;
         case 4:
            this.setMarginPercent(var1, this.mTempYogaValue.value);
         }

         var2.recycle();
      }
   }

   @ReactProp(
      name = "maxHeight"
   )
   public void setMaxHeight(Dynamic var1) {
      if(!this.isVirtual()) {
         this.mTempYogaValue.setFromDynamic(var1);
         int var2 = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
         if(var2 != 4) {
            switch(var2) {
            case 1:
            case 2:
               this.setStyleMaxHeight(this.mTempYogaValue.value);
            }
         } else {
            this.setStyleMaxHeightPercent(this.mTempYogaValue.value);
         }

         var1.recycle();
      }
   }

   @ReactProp(
      name = "maxWidth"
   )
   public void setMaxWidth(Dynamic var1) {
      if(!this.isVirtual()) {
         this.mTempYogaValue.setFromDynamic(var1);
         int var2 = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
         if(var2 != 4) {
            switch(var2) {
            case 1:
            case 2:
               this.setStyleMaxWidth(this.mTempYogaValue.value);
            }
         } else {
            this.setStyleMaxWidthPercent(this.mTempYogaValue.value);
         }

         var1.recycle();
      }
   }

   @ReactProp(
      name = "minHeight"
   )
   public void setMinHeight(Dynamic var1) {
      if(!this.isVirtual()) {
         this.mTempYogaValue.setFromDynamic(var1);
         int var2 = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
         if(var2 != 4) {
            switch(var2) {
            case 1:
            case 2:
               this.setStyleMinHeight(this.mTempYogaValue.value);
            }
         } else {
            this.setStyleMinHeightPercent(this.mTempYogaValue.value);
         }

         var1.recycle();
      }
   }

   @ReactProp(
      name = "minWidth"
   )
   public void setMinWidth(Dynamic var1) {
      if(!this.isVirtual()) {
         this.mTempYogaValue.setFromDynamic(var1);
         int var2 = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
         if(var2 != 4) {
            switch(var2) {
            case 1:
            case 2:
               this.setStyleMinWidth(this.mTempYogaValue.value);
            }
         } else {
            this.setStyleMinWidthPercent(this.mTempYogaValue.value);
         }

         var1.recycle();
      }
   }

   @ReactProp(
      name = "overflow"
   )
   public void setOverflow(@Nullable String var1) {
      if(!this.isVirtual()) {
         if(var1 == null) {
            this.setOverflow(YogaOverflow.VISIBLE);
         } else {
            byte var2 = -1;
            int var3 = var1.hashCode();
            if(var3 != -1217487446) {
               if(var3 != -907680051) {
                  if(var3 == 466743410 && var1.equals("visible")) {
                     var2 = 0;
                  }
               } else if(var1.equals("scroll")) {
                  var2 = 2;
               }
            } else if(var1.equals("hidden")) {
               var2 = 1;
            }

            switch(var2) {
            case 0:
               this.setOverflow(YogaOverflow.VISIBLE);
               return;
            case 1:
               this.setOverflow(YogaOverflow.HIDDEN);
               return;
            case 2:
               this.setOverflow(YogaOverflow.SCROLL);
               return;
            default:
               StringBuilder var4 = new StringBuilder();
               var4.append("invalid value for overflow: ");
               var4.append(var1);
               throw new JSApplicationIllegalArgumentException(var4.toString());
            }
         }
      }
   }

   @ReactPropGroup(
      names = {"padding", "paddingVertical", "paddingHorizontal", "paddingStart", "paddingEnd", "paddingTop", "paddingBottom", "paddingLeft", "paddingRight"}
   )
   public void setPaddings(int var1, Dynamic var2) {
      if(!this.isVirtual()) {
         var1 = this.maybeTransformLeftRightToStartEnd(ViewProps.PADDING_MARGIN_SPACING_TYPES[var1]);
         this.mTempYogaValue.setFromDynamic(var2);
         int var3 = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
         if(var3 != 4) {
            switch(var3) {
            case 1:
            case 2:
               this.setPadding(var1, this.mTempYogaValue.value);
            }
         } else {
            this.setPaddingPercent(var1, this.mTempYogaValue.value);
         }

         var2.recycle();
      }
   }

   @ReactProp(
      name = "position"
   )
   public void setPosition(@Nullable String var1) {
      if(!this.isVirtual()) {
         if(var1 == null) {
            this.setPositionType(YogaPositionType.RELATIVE);
         } else {
            byte var2 = -1;
            int var3 = var1.hashCode();
            if(var3 != -554435892) {
               if(var3 == 1728122231 && var1.equals("absolute")) {
                  var2 = 1;
               }
            } else if(var1.equals("relative")) {
               var2 = 0;
            }

            switch(var2) {
            case 0:
               this.setPositionType(YogaPositionType.RELATIVE);
               return;
            case 1:
               this.setPositionType(YogaPositionType.ABSOLUTE);
               return;
            default:
               StringBuilder var4 = new StringBuilder();
               var4.append("invalid value for position: ");
               var4.append(var1);
               throw new JSApplicationIllegalArgumentException(var4.toString());
            }
         }
      }
   }

   @ReactPropGroup(
      names = {"start", "end", "left", "right", "top", "bottom"}
   )
   public void setPositionValues(int var1, Dynamic var2) {
      if(!this.isVirtual()) {
         var1 = this.maybeTransformLeftRightToStartEnd((new int[]{4, 5, 0, 2, 1, 3})[var1]);
         this.mTempYogaValue.setFromDynamic(var2);
         int var3 = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
         if(var3 != 4) {
            switch(var3) {
            case 1:
            case 2:
               this.setPosition(var1, this.mTempYogaValue.value);
            }
         } else {
            this.setPositionPercent(var1, this.mTempYogaValue.value);
         }

         var2.recycle();
      }
   }

   @ReactProp(
      name = "onLayout"
   )
   public void setShouldNotifyOnLayout(boolean var1) {
      super.setShouldNotifyOnLayout(var1);
   }

   @ReactProp(
      name = "width"
   )
   public void setWidth(Dynamic var1) {
      if(!this.isVirtual()) {
         this.mTempYogaValue.setFromDynamic(var1);
         switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()]) {
         case 1:
         case 2:
            this.setStyleWidth(this.mTempYogaValue.value);
            break;
         case 3:
            this.setStyleWidthAuto();
            break;
         case 4:
            this.setStyleWidthPercent(this.mTempYogaValue.value);
         }

         var1.recycle();
      }
   }

   static class MutableYogaValue {

      YogaUnit unit;
      float value;


      private MutableYogaValue() {}

      // $FF: synthetic method
      MutableYogaValue(Object var1) {
         this();
      }

      void setFromDynamic(Dynamic var1) {
         if(var1.isNull()) {
            this.unit = YogaUnit.UNDEFINED;
            this.value = Float.NaN;
         } else if(var1.getType() == ReadableType.String) {
            String var3 = var1.asString();
            if(var3.equals("auto")) {
               this.unit = YogaUnit.AUTO;
               this.value = Float.NaN;
            } else if(var3.endsWith("%")) {
               this.unit = YogaUnit.PERCENT;
               this.value = Float.parseFloat(var3.substring(0, var3.length() - 1));
            } else {
               StringBuilder var2 = new StringBuilder();
               var2.append("Unknown value: ");
               var2.append(var3);
               throw new IllegalArgumentException(var2.toString());
            }
         } else {
            this.unit = YogaUnit.POINT;
            this.value = PixelUtil.toPixelFromDIP(var1.asDouble());
         }
      }
   }
}
