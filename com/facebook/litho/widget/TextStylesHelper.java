package com.facebook.litho.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.TextUtils.TruncateAt;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.Output;
import com.facebook.litho.widget.SynchronizedTypefaceHelper;
import com.facebook.litho.widget.TextSpec;
import com.facebook.litho.widget.VerticalGravity;

public final class TextStylesHelper {

   public static final int DEFAULT_BREAK_STRATEGY = 0;
   public static final int DEFAULT_EMS = -1;
   public static final int DEFAULT_HYPHENATION_FREQUENCY = 0;
   public static final int DEFAULT_JUSTIFICATION_MODE = 0;
   public static final int DEFAULT_MAX_WIDTH = Integer.MAX_VALUE;
   public static final int DEFAULT_MIN_WIDTH = 0;
   private static final TruncateAt[] TRUNCATE_AT;
   public static final Alignment textAlignmentDefault;


   static {
      SynchronizedTypefaceHelper.setupSynchronizedTypeface();
      TRUNCATE_AT = TruncateAt.values();
      textAlignmentDefault = Alignment.ALIGN_NORMAL;
   }

   private static Alignment getAlignment(int var0) {
      var0 &= 8388615;
      return var0 != 1?(var0 != 3?(var0 != 5?(var0 != 8388611?(var0 != 8388613?textAlignmentDefault:Alignment.ALIGN_OPPOSITE):Alignment.ALIGN_NORMAL):Alignment.ALIGN_OPPOSITE):Alignment.ALIGN_NORMAL):Alignment.ALIGN_CENTER;
   }

   private static Alignment getAlignment(int var0, int var1) {
      switch(var0) {
      case 0:
         return getAlignment(var1);
      case 1:
         return getAlignment(var1);
      case 2:
         return Alignment.ALIGN_NORMAL;
      case 3:
         return Alignment.ALIGN_OPPOSITE;
      case 4:
         return Alignment.ALIGN_CENTER;
      case 5:
         return Alignment.ALIGN_NORMAL;
      case 6:
         return Alignment.ALIGN_OPPOSITE;
      default:
         return textAlignmentDefault;
      }
   }

   private static VerticalGravity getVerticalGravity(int var0) {
      var0 &= 112;
      return var0 != 16?(var0 != 48?(var0 != 80?TextSpec.verticalGravity:VerticalGravity.BOTTOM):VerticalGravity.TOP):VerticalGravity.CENTER;
   }

   public static void onLoadStyle(ComponentContext param0, Output<TruncateAt> param1, Output<Float> param2, Output<Boolean> param3, Output<Float> param4, Output<Integer> param5, Output<Integer> param6, Output<Integer> param7, Output<Integer> param8, Output<Integer> param9, Output<Integer> param10, Output<Boolean> param11, Output<CharSequence> param12, Output<ColorStateList> param13, Output<Integer> param14, Output<Integer> param15, Output<Integer> param16, Output<Alignment> param17, Output<Integer> param18, Output<Integer> param19, Output<Integer> param20, Output<Integer> param21, Output<Float> param22, Output<Float> param23, Output<Float> param24, Output<Integer> param25, Output<VerticalGravity> param26, Output<Typeface> param27) {
      // $FF: Couldn't be decompiled
   }

   private static void resolveStyleAttrsForTypedArray(TypedArray var0, Output<TruncateAt> var1, Output<Float> var2, Output<Boolean> var3, Output<Float> var4, Output<Integer> var5, Output<Integer> var6, Output<Integer> var7, Output<Integer> var8, Output<Integer> var9, Output<Integer> var10, Output<Boolean> var11, Output<CharSequence> var12, Output<ColorStateList> var13, Output<Integer> var14, Output<Integer> var15, Output<Integer> var16, Output<Alignment> var17, Output<Integer> var18, Output<Integer> var19, Output<Integer> var20, Output<Integer> var21, Output<Float> var22, Output<Float> var23, Output<Float> var24, Output<Integer> var25, Output<VerticalGravity> var26, Output<Typeface> var27) {
      int var30 = var0.getIndexCount();
      String var33 = null;
      int var31 = 0;
      int var29 = 0;

      int var28;
      for(var28 = 1; var31 < var30; ++var31) {
         int var32 = var0.getIndex(var31);
         if(var32 == com.facebook.litho.R.Text_android_text) {
            var12.set(var0.getString(var32));
         } else if(var32 == com.facebook.litho.R.Text_android_textColor) {
            var13.set(var0.getColorStateList(var32));
         } else if(var32 == com.facebook.litho.R.Text_android_textSize) {
            var16.set(Integer.valueOf(var0.getDimensionPixelSize(var32, 0)));
         } else if(var32 == com.facebook.litho.R.Text_android_ellipsize) {
            var32 = var0.getInteger(var32, 0);
            if(var32 > 0) {
               var1.set(TRUNCATE_AT[var32 - 1]);
            }
         } else if(VERSION.SDK_INT >= 17 && var32 == com.facebook.litho.R.Text_android_textAlignment) {
            var28 = var0.getInt(var32, -1);
            var17.set(getAlignment(var28, var29));
         } else if(var32 == com.facebook.litho.R.Text_android_gravity) {
            var29 = var0.getInt(var32, -1);
            var17.set(getAlignment(var28, var29));
            var26.set(getVerticalGravity(var29));
         } else if(var32 == com.facebook.litho.R.Text_android_includeFontPadding) {
            var3.set(Boolean.valueOf(var0.getBoolean(var32, false)));
         } else if(var32 == com.facebook.litho.R.Text_android_minLines) {
            var5.set(Integer.valueOf(var0.getInteger(var32, -1)));
         } else if(var32 == com.facebook.litho.R.Text_android_maxLines) {
            var6.set(Integer.valueOf(var0.getInteger(var32, -1)));
         } else if(var32 == com.facebook.litho.R.Text_android_singleLine) {
            var11.set(Boolean.valueOf(var0.getBoolean(var32, false)));
         } else if(var32 == com.facebook.litho.R.Text_android_textColorLink) {
            var14.set(Integer.valueOf(var0.getColor(var32, 0)));
         } else if(var32 == com.facebook.litho.R.Text_android_textColorHighlight) {
            var15.set(Integer.valueOf(var0.getColor(var32, 0)));
         } else if(var32 == com.facebook.litho.R.Text_android_textStyle) {
            var21.set(Integer.valueOf(var0.getInteger(var32, 0)));
         } else if(var32 == com.facebook.litho.R.Text_android_lineSpacingExtra) {
            var2.set(Float.valueOf((float)var0.getDimensionPixelOffset(var32, 0)));
         } else if(var32 == com.facebook.litho.R.Text_android_lineSpacingMultiplier) {
            var4.set(Float.valueOf(var0.getFloat(var32, 0.0F)));
         } else if(var32 == com.facebook.litho.R.Text_android_shadowDx) {
            var23.set(Float.valueOf(var0.getFloat(var32, 0.0F)));
         } else if(var32 == com.facebook.litho.R.Text_android_shadowDy) {
            var24.set(Float.valueOf(var0.getFloat(var32, 0.0F)));
         } else if(var32 == com.facebook.litho.R.Text_android_shadowRadius) {
            var22.set(Float.valueOf(var0.getFloat(var32, 0.0F)));
         } else if(var32 == com.facebook.litho.R.Text_android_shadowColor) {
            var25.set(Integer.valueOf(var0.getColor(var32, 0)));
         } else if(var32 == com.facebook.litho.R.Text_android_minEms) {
            var7.set(Integer.valueOf(var0.getInteger(var32, -1)));
         } else if(var32 == com.facebook.litho.R.Text_android_maxEms) {
            var8.set(Integer.valueOf(var0.getInteger(var32, -1)));
         } else if(var32 == com.facebook.litho.R.Text_android_minWidth) {
            var9.set(Integer.valueOf(var0.getDimensionPixelSize(var32, 0)));
         } else if(var32 == com.facebook.litho.R.Text_android_maxWidth) {
            var10.set(Integer.valueOf(var0.getDimensionPixelSize(var32, Integer.MAX_VALUE)));
         } else if(var32 == com.facebook.litho.R.Text_android_fontFamily) {
            var33 = var0.getString(var32);
         } else if(VERSION.SDK_INT >= 23 && var32 == com.facebook.litho.R.Text_android_breakStrategy) {
            var18.set(Integer.valueOf(var0.getInt(var32, 0)));
         } else if(VERSION.SDK_INT >= 23 && var32 == com.facebook.litho.R.Text_android_hyphenationFrequency) {
            var19.set(Integer.valueOf(var0.getInt(var32, 0)));
         } else if(VERSION.SDK_INT >= 26 && var32 == com.facebook.litho.R.Text_android_justificationMode) {
            var20.set(Integer.valueOf(var0.getInt(var32, 0)));
         }
      }

      if(var33 != null) {
         Integer var35 = (Integer)var21.get();
         if(var35 == null) {
            var28 = -1;
         } else {
            var28 = var35.intValue();
         }

         var27.set(Typeface.create(var33, var28));
      }

   }
}
