package com.facebook.react.views.text;

import android.content.res.AssetManager;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import com.facebook.react.views.text.ReactFontManager;
import javax.annotation.Nullable;

public class CustomStyleSpan extends MetricAffectingSpan {

   private final AssetManager mAssetManager;
   @Nullable
   private final String mFontFamily;
   private final int mStyle;
   private final int mWeight;


   public CustomStyleSpan(int var1, int var2, @Nullable String var3, AssetManager var4) {
      this.mStyle = var1;
      this.mWeight = var2;
      this.mFontFamily = var3;
      this.mAssetManager = var4;
   }

   private static void apply(Paint var0, int var1, int var2, @Nullable String var3, AssetManager var4) {
      Typeface var8 = var0.getTypeface();
      byte var7 = 0;
      int var6;
      if(var8 == null) {
         var6 = 0;
      } else {
         var6 = var8.getStyle();
      }

      byte var5;
      label40: {
         if(var2 != 1) {
            var5 = var7;
            if((var6 & 1) == 0) {
               break label40;
            }

            var5 = var7;
            if(var2 != -1) {
               break label40;
            }
         }

         var5 = 1;
      }

      label33: {
         if(var1 != 2) {
            var2 = var5;
            if((2 & var6) == 0) {
               break label33;
            }

            var2 = var5;
            if(var1 != -1) {
               break label33;
            }
         }

         var2 = var5 | 2;
      }

      Typeface var9;
      if(var3 != null) {
         var9 = ReactFontManager.getInstance().getTypeface(var3, var2, var4);
      } else {
         var9 = var8;
         if(var8 != null) {
            var9 = Typeface.create(var8, var2);
         }
      }

      if(var9 != null) {
         var0.setTypeface(var9);
      } else {
         var0.setTypeface(Typeface.defaultFromStyle(var2));
      }
   }

   @Nullable
   public String getFontFamily() {
      return this.mFontFamily;
   }

   public int getStyle() {
      return this.mStyle == -1?0:this.mStyle;
   }

   public int getWeight() {
      return this.mWeight == -1?0:this.mWeight;
   }

   public void updateDrawState(TextPaint var1) {
      apply(var1, this.mStyle, this.mWeight, this.mFontFamily, this.mAssetManager);
   }

   public void updateMeasureState(TextPaint var1) {
      apply(var1, this.mStyle, this.mWeight, this.mFontFamily, this.mAssetManager);
   }
}
