package com.facebook.drawee.generic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.infer.annotation.ReturnsOwnership;
import javax.annotation.Nullable;

public class GenericDraweeHierarchyInflater {

   @Nullable
   private static Drawable getDrawable(Context var0, TypedArray var1, int var2) {
      var2 = var1.getResourceId(var2, 0);
      return var2 == 0?null:var0.getResources().getDrawable(var2);
   }

   @ReturnsOwnership
   private static RoundingParams getRoundingParams(GenericDraweeHierarchyBuilder var0) {
      if(var0.getRoundingParams() == null) {
         var0.setRoundingParams(new RoundingParams());
      }

      return var0.getRoundingParams();
   }

   @Nullable
   private static ScalingUtils.ScaleType getScaleTypeFromXml(TypedArray var0, int var1) {
      switch(var0.getInt(var1, -2)) {
      case -1:
         return null;
      case 0:
         return ScalingUtils.ScaleType.FIT_XY;
      case 1:
         return ScalingUtils.ScaleType.FIT_START;
      case 2:
         return ScalingUtils.ScaleType.FIT_CENTER;
      case 3:
         return ScalingUtils.ScaleType.FIT_END;
      case 4:
         return ScalingUtils.ScaleType.CENTER;
      case 5:
         return ScalingUtils.ScaleType.CENTER_INSIDE;
      case 6:
         return ScalingUtils.ScaleType.CENTER_CROP;
      case 7:
         return ScalingUtils.ScaleType.FOCUS_CROP;
      default:
         throw new RuntimeException("XML attribute not specified!");
      }
   }

   public static GenericDraweeHierarchyBuilder inflateBuilder(Context var0, @Nullable AttributeSet var1) {
      return updateBuilder(new GenericDraweeHierarchyBuilder(var0.getResources()), var0, var1);
   }

   public static GenericDraweeHierarchy inflateHierarchy(Context var0, @Nullable AttributeSet var1) {
      return inflateBuilder(var0, var1).build();
   }

   public static GenericDraweeHierarchyBuilder updateBuilder(GenericDraweeHierarchyBuilder param0, Context param1, @Nullable AttributeSet param2) {
      // $FF: Couldn't be decompiled
   }
}
