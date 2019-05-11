package com.facebook.react.views.text;

import android.text.TextUtils.TruncateAt;
import android.view.View;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.react.views.text.DefaultStyleValuesUtil;
import com.facebook.react.views.text.ReactBaseTextShadowNode;
import com.facebook.react.views.text.ReactTextView;
import com.facebook.yoga.YogaConstants;
import javax.annotation.Nullable;

public abstract class ReactTextAnchorViewManager<T extends View, C extends ReactBaseTextShadowNode> extends BaseViewManager<T, C> {

   private static final int[] SPACING_TYPES = new int[]{8, 0, 2, 1, 3};


   @ReactPropGroup(
      customType = "Color",
      names = {"borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor"}
   )
   public void setBorderColor(ReactTextView var1, int var2, Integer var3) {
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
   public void setBorderRadius(ReactTextView var1, int var2, float var3) {
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
   public void setBorderStyle(ReactTextView var1, @Nullable String var2) {
      var1.setBorderStyle(var2);
   }

   @ReactPropGroup(
      defaultFloat = Float.NaN,
      names = {"borderWidth", "borderLeftWidth", "borderRightWidth", "borderTopWidth", "borderBottomWidth"}
   )
   public void setBorderWidth(ReactTextView var1, int var2, float var3) {
      float var4 = var3;
      if(!YogaConstants.isUndefined(var3)) {
         var4 = PixelUtil.toPixelFromDIP(var3);
      }

      var1.setBorderWidth(SPACING_TYPES[var2], var4);
   }

   @ReactProp(
      defaultBoolean = false,
      name = "disabled"
   )
   public void setDisabled(ReactTextView var1, boolean var2) {
      var1.setEnabled(var2 ^ true);
   }

   @ReactProp(
      name = "ellipsizeMode"
   )
   public void setEllipsizeMode(ReactTextView var1, @Nullable String var2) {
      if(var2 != null && !var2.equals("tail")) {
         if(var2.equals("head")) {
            var1.setEllipsizeLocation(TruncateAt.START);
         } else if(var2.equals("middle")) {
            var1.setEllipsizeLocation(TruncateAt.MIDDLE);
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("Invalid ellipsizeMode: ");
            var3.append(var2);
            throw new JSApplicationIllegalArgumentException(var3.toString());
         }
      } else {
         var1.setEllipsizeLocation(TruncateAt.END);
      }
   }

   @ReactProp(
      defaultBoolean = true,
      name = "includeFontPadding"
   )
   public void setIncludeFontPadding(ReactTextView var1, boolean var2) {
      var1.setIncludeFontPadding(var2);
   }

   @ReactProp(
      defaultInt = Integer.MAX_VALUE,
      name = "numberOfLines"
   )
   public void setNumberOfLines(ReactTextView var1, int var2) {
      var1.setNumberOfLines(var2);
   }

   @ReactProp(
      name = "selectable"
   )
   public void setSelectable(ReactTextView var1, boolean var2) {
      var1.setTextIsSelectable(var2);
   }

   @ReactProp(
      customType = "Color",
      name = "selectionColor"
   )
   public void setSelectionColor(ReactTextView var1, @Nullable Integer var2) {
      if(var2 == null) {
         var1.setHighlightColor(DefaultStyleValuesUtil.getDefaultTextColorHighlight(var1.getContext()));
      } else {
         var1.setHighlightColor(var2.intValue());
      }
   }

   @ReactProp(
      name = "textAlignVertical"
   )
   public void setTextAlignVertical(ReactTextView var1, @Nullable String var2) {
      if(var2 != null && !"auto".equals(var2)) {
         if("top".equals(var2)) {
            var1.setGravityVertical(48);
         } else if("bottom".equals(var2)) {
            var1.setGravityVertical(80);
         } else if("center".equals(var2)) {
            var1.setGravityVertical(16);
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("Invalid textAlignVertical: ");
            var3.append(var2);
            throw new JSApplicationIllegalArgumentException(var3.toString());
         }
      } else {
         var1.setGravityVertical(0);
      }
   }
}
