package android.support.design.ripple;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.graphics.ColorUtils;
import android.util.StateSet;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class RippleUtils {

   private static final int[] FOCUSED_STATE_SET;
   private static final int[] HOVERED_FOCUSED_STATE_SET;
   private static final int[] HOVERED_STATE_SET;
   private static final int[] PRESSED_STATE_SET;
   private static final int[] SELECTED_FOCUSED_STATE_SET;
   private static final int[] SELECTED_HOVERED_FOCUSED_STATE_SET;
   private static final int[] SELECTED_HOVERED_STATE_SET;
   private static final int[] SELECTED_PRESSED_STATE_SET;
   private static final int[] SELECTED_STATE_SET;
   public static final boolean USE_FRAMEWORK_RIPPLE;


   static {
      boolean var0;
      if(VERSION.SDK_INT >= 21) {
         var0 = true;
      } else {
         var0 = false;
      }

      USE_FRAMEWORK_RIPPLE = var0;
      PRESSED_STATE_SET = new int[]{16842919};
      HOVERED_FOCUSED_STATE_SET = new int[]{16843623, 16842908};
      FOCUSED_STATE_SET = new int[]{16842908};
      HOVERED_STATE_SET = new int[]{16843623};
      SELECTED_PRESSED_STATE_SET = new int[]{16842913, 16842919};
      SELECTED_HOVERED_FOCUSED_STATE_SET = new int[]{16842913, 16843623, 16842908};
      SELECTED_FOCUSED_STATE_SET = new int[]{16842913, 16842908};
      SELECTED_HOVERED_STATE_SET = new int[]{16842913, 16843623};
      SELECTED_STATE_SET = new int[]{16842913};
   }

   @NonNull
   public static ColorStateList convertToRippleDrawableColor(@Nullable ColorStateList var0) {
      int var1;
      int var2;
      int[] var9;
      int[] var10;
      if(USE_FRAMEWORK_RIPPLE) {
         var9 = SELECTED_STATE_SET;
         var1 = getColorForState(var0, SELECTED_PRESSED_STATE_SET);
         var10 = StateSet.NOTHING;
         var2 = getColorForState(var0, PRESSED_STATE_SET);
         return new ColorStateList(new int[][]{var9, var10}, new int[]{var1, var2});
      } else {
         var9 = SELECTED_PRESSED_STATE_SET;
         var1 = getColorForState(var0, SELECTED_PRESSED_STATE_SET);
         var10 = SELECTED_HOVERED_FOCUSED_STATE_SET;
         var2 = getColorForState(var0, SELECTED_HOVERED_FOCUSED_STATE_SET);
         int[] var11 = SELECTED_FOCUSED_STATE_SET;
         int var3 = getColorForState(var0, SELECTED_FOCUSED_STATE_SET);
         int[] var12 = SELECTED_HOVERED_STATE_SET;
         int var4 = getColorForState(var0, SELECTED_HOVERED_STATE_SET);
         int[] var13 = SELECTED_STATE_SET;
         int[] var14 = PRESSED_STATE_SET;
         int var5 = getColorForState(var0, PRESSED_STATE_SET);
         int[] var15 = HOVERED_FOCUSED_STATE_SET;
         int var6 = getColorForState(var0, HOVERED_FOCUSED_STATE_SET);
         int[] var16 = FOCUSED_STATE_SET;
         int var7 = getColorForState(var0, FOCUSED_STATE_SET);
         int[] var17 = HOVERED_STATE_SET;
         int var8 = getColorForState(var0, HOVERED_STATE_SET);
         return new ColorStateList(new int[][]{var9, var10, var11, var12, var13, var14, var15, var16, var17, StateSet.NOTHING}, new int[]{var1, var2, var3, var4, 0, var5, var6, var7, var8, 0});
      }
   }

   @TargetApi(21)
   @ColorInt
   private static int doubleAlpha(@ColorInt int var0) {
      return ColorUtils.setAlphaComponent(var0, Math.min(Color.alpha(var0) * 2, 255));
   }

   @ColorInt
   private static int getColorForState(@Nullable ColorStateList var0, int[] var1) {
      int var2;
      if(var0 != null) {
         var2 = var0.getColorForState(var1, var0.getDefaultColor());
      } else {
         var2 = 0;
      }

      int var3 = var2;
      if(USE_FRAMEWORK_RIPPLE) {
         var3 = doubleAlpha(var2);
      }

      return var3;
   }
}
