package com.facebook.litho;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.drawable.Drawable;
import android.widget.ImageView.ScaleType;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.FastMath;
import javax.annotation.Nullable;

public final class DrawableMatrix extends Matrix {

   private boolean mShouldClipRect;


   @Nullable
   public static DrawableMatrix create(Drawable var0, ScaleType var1, int var2, int var3) {
      ScaleType var11 = var1;
      if(var1 == null) {
         var11 = ScaleType.FIT_CENTER;
      }

      if(var0 == null) {
         return null;
      } else {
         int var7 = var0.getIntrinsicWidth();
         int var8 = var0.getIntrinsicHeight();
         if(var7 > 0 && var8 > 0 && ScaleType.FIT_XY != var11) {
            if(ScaleType.MATRIX == var11) {
               return null;
            } else if(var2 == var7 && var3 == var8) {
               return null;
            } else {
               DrawableMatrix var15 = new DrawableMatrix();
               var1 = ScaleType.CENTER;
               boolean var10 = true;
               if(var1 == var11) {
                  var15.setTranslate((float)FastMath.round((float)(var2 - var7) * 0.5F), (float)FastMath.round((float)(var3 - var8) * 0.5F));
                  boolean var9 = var10;
                  if(var7 <= var2) {
                     if(var8 > var3) {
                        var9 = var10;
                     } else {
                        var9 = false;
                     }
                  }

                  var15.mShouldClipRect = var9;
                  return var15;
               } else {
                  var1 = ScaleType.CENTER_CROP;
                  float var4 = 0.0F;
                  float var5;
                  float var6;
                  if(var1 == var11) {
                     if(var7 * var3 > var2 * var8) {
                        var6 = (float)var3 / (float)var8;
                        var5 = ((float)var2 - (float)var7 * var6) * 0.5F;
                     } else {
                        var6 = (float)var2 / (float)var7;
                        var4 = ((float)var3 - (float)var8 * var6) * 0.5F;
                        var5 = 0.0F;
                     }

                     var15.setScale(var6, var6);
                     var15.postTranslate((float)FastMath.round(var5), (float)FastMath.round(var4));
                     var15.mShouldClipRect = true;
                     return var15;
                  } else if(ScaleType.CENTER_INSIDE != var11) {
                     RectF var16 = ComponentsPools.acquireRectF();
                     RectF var12 = ComponentsPools.acquireRectF();
                     var4 = (float)var7;
                     var5 = (float)var8;

                     try {
                        var16.set(0.0F, 0.0F, var4, var5);
                        var12.set(0.0F, 0.0F, (float)var2, (float)var3);
                        var15.setRectToRect(var16, var12, scaleTypeToScaleToFit(var11));
                     } finally {
                        ComponentsPools.releaseRectF(var16);
                        ComponentsPools.releaseRectF(var12);
                     }

                     return var15;
                  } else {
                     if(var7 <= var2 && var8 <= var3) {
                        var4 = 1.0F;
                     } else {
                        var4 = Math.min((float)var2 / (float)var7, (float)var3 / (float)var8);
                     }

                     var5 = (float)FastMath.round(((float)var2 - (float)var7 * var4) * 0.5F);
                     var6 = (float)FastMath.round(((float)var3 - (float)var8 * var4) * 0.5F);
                     var15.setScale(var4, var4);
                     var15.postTranslate(var5, var6);
                     return var15;
                  }
               }
            }
         } else {
            return null;
         }
      }
   }

   private static ScaleToFit scaleTypeToScaleToFit(ScaleType var0) {
      switch(null.$SwitchMap$android$widget$ImageView$ScaleType[var0.ordinal()]) {
      case 1:
         return ScaleToFit.FILL;
      case 2:
         return ScaleToFit.START;
      case 3:
         return ScaleToFit.CENTER;
      case 4:
         return ScaleToFit.END;
      default:
         throw new IllegalArgumentException("Only FIT_... values allowed");
      }
   }

   public boolean shouldClipRect() {
      return this.mShouldClipRect;
   }
}
