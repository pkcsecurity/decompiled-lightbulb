package com.facebook.react.views.image;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import javax.annotation.Nullable;

public class ImageResizeMode {

   public static ScalingUtils.ScaleType defaultValue() {
      return ScalingUtils.ScaleType.CENTER_CROP;
   }

   public static ScalingUtils.ScaleType toScaleType(@Nullable String var0) {
      if("contain".equals(var0)) {
         return ScalingUtils.ScaleType.FIT_CENTER;
      } else if("cover".equals(var0)) {
         return ScalingUtils.ScaleType.CENTER_CROP;
      } else if("stretch".equals(var0)) {
         return ScalingUtils.ScaleType.FIT_XY;
      } else if("center".equals(var0)) {
         return ScalingUtils.ScaleType.CENTER_INSIDE;
      } else if(var0 == null) {
         return defaultValue();
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("Invalid resize mode: \'");
         var1.append(var0);
         var1.append("\'");
         throw new JSApplicationIllegalArgumentException(var1.toString());
      }
   }
}
