package com.facebook.fbui.textlayoutbuilder;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.AttrRes;
import android.support.annotation.StyleRes;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import com.facebook.fbui.textlayoutbuilder.R;
import com.facebook.fbui.textlayoutbuilder.TextLayoutBuilder;

public class ResourceTextLayoutHelper {

   private static final int DEFAULT_TEXT_SIZE_PX = 15;


   public static void setTextAppearance(TextLayoutBuilder var0, Context var1, @StyleRes int var2) {
      TypedArray var7 = var1.obtainStyledAttributes(var2, R.styleable.TextAppearance);
      ColorStateList var6 = var7.getColorStateList(R.styleable.TextAppearance_android_textColor);
      var2 = var7.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
      int var5 = var7.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
      if(var5 != 0) {
         float var3 = var7.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0F);
         float var4 = var7.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0F);
         var0.setShadowLayer(var7.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0F), var3, var4, var5);
      }

      var5 = var7.getInt(R.styleable.TextAppearance_android_textStyle, -1);
      var7.recycle();
      if(var6 != null) {
         var0.setTextColor(var6);
      }

      if(var2 != 0) {
         var0.setTextSize(var2);
      }

      if(var5 != -1) {
         var0.setTypeface(Typeface.defaultFromStyle(var5));
      }

   }

   public static void updateFromStyleResource(TextLayoutBuilder var0, Context var1, @StyleRes int var2) {
      updateFromStyleResource(var0, var1, 0, var2);
   }

   public static void updateFromStyleResource(TextLayoutBuilder var0, Context var1, @AttrRes int var2, @StyleRes int var3) {
      updateFromStyleResource(var0, var1, (AttributeSet)null, var2, var3);
   }

   public static void updateFromStyleResource(TextLayoutBuilder var0, Context var1, AttributeSet var2, @AttrRes int var3, @StyleRes int var4) {
      TypedArray var15 = var1.obtainStyledAttributes(var2, R.styleable.TextStyle, var3, var4);
      var3 = var15.getResourceId(R.styleable.TextStyle_android_textAppearance, -1);
      if(var3 > 0) {
         setTextAppearance(var0, var1, var3);
      }

      ColorStateList var14 = var15.getColorStateList(R.styleable.TextStyle_android_textColor);
      var3 = var15.getDimensionPixelSize(R.styleable.TextStyle_android_textSize, 15);
      var4 = var15.getInt(R.styleable.TextStyle_android_shadowColor, 0);
      float var5 = var15.getFloat(R.styleable.TextStyle_android_shadowDx, 0.0F);
      float var6 = var15.getFloat(R.styleable.TextStyle_android_shadowDy, 0.0F);
      float var7 = var15.getFloat(R.styleable.TextStyle_android_shadowRadius, 0.0F);
      int var8 = var15.getInt(R.styleable.TextStyle_android_textStyle, -1);
      int var9 = var15.getInt(R.styleable.TextStyle_android_ellipsize, 0);
      boolean var13 = var15.getBoolean(R.styleable.TextStyle_android_singleLine, false);
      int var10 = var15.getInt(R.styleable.TextStyle_android_maxLines, Integer.MAX_VALUE);
      int var11 = var15.getInt(R.styleable.TextStyle_android_breakStrategy, -1);
      int var12 = var15.getInt(R.styleable.TextStyle_android_hyphenationFrequency, -1);
      var15.recycle();
      var0.setTextColor(var14);
      var0.setTextSize(var3);
      var0.setShadowLayer(var7, var5, var6, var4);
      if(var8 != -1) {
         var0.setTypeface(Typeface.defaultFromStyle(var8));
      } else {
         var0.setTypeface((Typeface)null);
      }

      if(var9 > 0 && var9 < 4) {
         var0.setEllipsize(TruncateAt.values()[var9 - 1]);
      } else {
         var0.setEllipsize((TruncateAt)null);
      }

      var0.setSingleLine(var13);
      var0.setMaxLines(var10);
      if(var11 > -1) {
         var0.setBreakStrategy(var11);
      }

      if(var12 > -1) {
         var0.setHyphenationFrequency(var12);
      }

   }
}
