package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import com.facebook.react.flat.AbstractDrawBorder;
import javax.annotation.Nullable;

final class DrawBorder extends AbstractDrawBorder {

   private static final int ALL_BITS_SET = -1;
   private static final int ALL_BITS_UNSET = 0;
   private static final int BORDER_BOTTOM_COLOR_SET = 16;
   private static final int BORDER_LEFT_COLOR_SET = 2;
   private static final int BORDER_PATH_EFFECT_DIRTY = 32;
   private static final int BORDER_RIGHT_COLOR_SET = 8;
   private static final int BORDER_STYLE_DASHED = 2;
   private static final int BORDER_STYLE_DOTTED = 1;
   private static final int BORDER_STYLE_SOLID = 0;
   private static final int BORDER_TOP_COLOR_SET = 4;
   private static final Paint PAINT = new Paint(1);
   private static final float[] TMP_FLOAT_ARRAY = new float[4];
   private int mBackgroundColor;
   private int mBorderBottomColor;
   private float mBorderBottomWidth;
   private int mBorderLeftColor;
   private float mBorderLeftWidth;
   private int mBorderRightColor;
   private float mBorderRightWidth;
   private int mBorderStyle = 0;
   private int mBorderTopColor;
   private float mBorderTopWidth;
   @Nullable
   private DashPathEffect mPathEffectForBorderStyle;
   @Nullable
   private Path mPathForBorder;


   private static DashPathEffect createDashPathEffect(float var0) {
      for(int var1 = 0; var1 < 4; ++var1) {
         TMP_FLOAT_ARRAY[var1] = var0;
      }

      return new DashPathEffect(TMP_FLOAT_ARRAY, 0.0F);
   }

   private void drawRectangularBorders(Canvas var1) {
      int var15 = this.getBorderColor();
      float var12 = this.getBorderWidth();
      float var2 = this.getTop();
      float var3 = resolveWidth(this.mBorderTopWidth, var12);
      float var4 = var2 + var3;
      int var17 = this.resolveBorderColor(4, this.mBorderTopColor, var15);
      float var5 = this.getBottom();
      float var6 = resolveWidth(this.mBorderBottomWidth, var12);
      float var7 = var5 - var6;
      int var16 = this.resolveBorderColor(16, this.mBorderBottomColor, var15);
      float var8 = this.getLeft();
      float var9 = resolveWidth(this.mBorderLeftWidth, var12);
      float var10 = var8 + var9;
      int var14 = this.resolveBorderColor(2, this.mBorderLeftColor, var15);
      float var11 = this.getRight();
      var12 = resolveWidth(this.mBorderRightWidth, var12);
      float var13 = var11 - var12;
      var15 = this.resolveBorderColor(8, this.mBorderRightColor, var15);
      int var18 = fastBorderCompatibleColorOrZero(var9, var3, var12, var6, var14, var17, var15, var16);
      if(var18 != 0) {
         if(Color.alpha(var18) != 0) {
            if(Color.alpha(this.mBackgroundColor) != 0) {
               PAINT.setColor(this.mBackgroundColor);
               if(Color.alpha(var18) == 255) {
                  var1.drawRect(var10, var4, var13, var7, PAINT);
               } else {
                  var1.drawRect(var8, var2, var11, var5, PAINT);
               }
            }

            PAINT.setColor(var18);
            if(var9 > 0.0F) {
               var1.drawRect(var8, var2, var10, var7, PAINT);
            }

            if(var3 > 0.0F) {
               var1.drawRect(var10, var2, var11, var4, PAINT);
            }

            if(var12 > 0.0F) {
               var1.drawRect(var13, var4, var11, var5, PAINT);
            }

            if(var6 > 0.0F) {
               var1.drawRect(var8, var7, var13, var5, PAINT);
               return;
            }
         }
      } else {
         if(this.mPathForBorder == null) {
            this.mPathForBorder = new Path();
         }

         if(Color.alpha(this.mBackgroundColor) != 0) {
            PAINT.setColor(this.mBackgroundColor);
            var1.drawRect(var8, var2, var11, var5, PAINT);
         }

         if(var3 != 0.0F && Color.alpha(var17) != 0) {
            PAINT.setColor(var17);
            updatePathForTopBorder(this.mPathForBorder, var2, var4, var8, var10, var11, var13);
            var1.drawPath(this.mPathForBorder, PAINT);
         }

         if(var6 != 0.0F && Color.alpha(var16) != 0) {
            PAINT.setColor(var16);
            updatePathForBottomBorder(this.mPathForBorder, var5, var7, var8, var10, var11, var13);
            var1.drawPath(this.mPathForBorder, PAINT);
         }

         if(var9 != 0.0F && Color.alpha(var14) != 0) {
            PAINT.setColor(var14);
            updatePathForLeftBorder(this.mPathForBorder, var2, var4, var5, var7, var8, var10);
            var1.drawPath(this.mPathForBorder, PAINT);
         }

         if(var12 != 0.0F && Color.alpha(var15) != 0) {
            PAINT.setColor(var15);
            updatePathForRightBorder(this.mPathForBorder, var2, var4, var5, var7, var11, var13);
            var1.drawPath(this.mPathForBorder, PAINT);
         }
      }

   }

   private void drawRoundedBorders(Canvas var1) {
      if(this.mBackgroundColor != 0) {
         PAINT.setColor(this.mBackgroundColor);
         var1.drawPath(this.getPathForBorderRadius(), PAINT);
      }

      this.drawBorders(var1);
   }

   private static int fastBorderCompatibleColorOrZero(float var0, float var1, float var2, float var3, int var4, int var5, int var6, int var7) {
      int var11 = -1;
      int var8;
      if(var0 > 0.0F) {
         var8 = var4;
      } else {
         var8 = -1;
      }

      int var9;
      if(var1 > 0.0F) {
         var9 = var5;
      } else {
         var9 = -1;
      }

      int var10;
      if(var2 > 0.0F) {
         var10 = var6;
      } else {
         var10 = -1;
      }

      if(var3 > 0.0F) {
         var11 = var7;
      }

      var8 = var8 & var9 & var10 & var11;
      if(var0 <= 0.0F) {
         var4 = 0;
      }

      if(var1 <= 0.0F) {
         var5 = 0;
      }

      if(var2 <= 0.0F) {
         var6 = 0;
      }

      if(var3 <= 0.0F) {
         var7 = 0;
      }

      return var8 == (var4 | var5 | var6 | var7)?var8:0;
   }

   private int resolveBorderColor(int var1, int var2, int var3) {
      return this.isFlagSet(var1)?var2:var3;
   }

   private static float resolveWidth(float var0, float var1) {
      float var2;
      if(var0 != 0.0F) {
         var2 = var0;
         if(var0 == var0) {
            return var2;
         }
      }

      var2 = var1;
      return var2;
   }

   private static void updatePathForBottomBorder(Path var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      var0.reset();
      var0.moveTo(var3, var1);
      var0.lineTo(var5, var1);
      var0.lineTo(var6, var2);
      var0.lineTo(var4, var2);
      var0.lineTo(var3, var1);
   }

   private static void updatePathForLeftBorder(Path var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      var0.reset();
      var0.moveTo(var5, var1);
      var0.lineTo(var6, var2);
      var0.lineTo(var6, var4);
      var0.lineTo(var5, var3);
      var0.lineTo(var5, var1);
   }

   private static void updatePathForRightBorder(Path var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      var0.reset();
      var0.moveTo(var5, var1);
      var0.lineTo(var5, var3);
      var0.lineTo(var6, var4);
      var0.lineTo(var6, var2);
      var0.lineTo(var5, var1);
   }

   private static void updatePathForTopBorder(Path var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      var0.reset();
      var0.moveTo(var3, var1);
      var0.lineTo(var4, var2);
      var0.lineTo(var6, var2);
      var0.lineTo(var5, var1);
      var0.lineTo(var3, var1);
   }

   public int getBackgroundColor() {
      return this.mBackgroundColor;
   }

   public int getBorderColor(int var1) {
      int var2 = this.getBorderColor();
      if(var1 != 8) {
         switch(var1) {
         case 0:
            return this.resolveBorderColor(2, this.mBorderLeftColor, var2);
         case 1:
            return this.resolveBorderColor(4, this.mBorderTopColor, var2);
         case 2:
            return this.resolveBorderColor(8, this.mBorderRightColor, var2);
         case 3:
            return this.resolveBorderColor(16, this.mBorderBottomColor, var2);
         default:
            return var2;
         }
      } else {
         return var2;
      }
   }

   public float getBorderWidth(int var1) {
      if(var1 != 8) {
         switch(var1) {
         case 0:
            return this.mBorderLeftWidth;
         case 1:
            return this.mBorderTopWidth;
         case 2:
            return this.mBorderRightWidth;
         case 3:
            return this.mBorderBottomWidth;
         default:
            return 0.0F;
         }
      } else {
         return this.getBorderWidth();
      }
   }

   @Nullable
   protected DashPathEffect getPathEffectForBorderStyle() {
      if(this.isFlagSet(32)) {
         switch(this.mBorderStyle) {
         case 1:
            this.mPathEffectForBorderStyle = createDashPathEffect(this.getBorderWidth());
            break;
         case 2:
            this.mPathEffectForBorderStyle = createDashPathEffect(this.getBorderWidth() * 3.0F);
            break;
         default:
            this.mPathEffectForBorderStyle = null;
         }

         this.resetFlag(32);
      }

      return this.mPathEffectForBorderStyle;
   }

   protected void onDraw(Canvas var1) {
      if(this.getBorderRadius() < 0.5F && this.getPathEffectForBorderStyle() == null) {
         this.drawRectangularBorders(var1);
      } else {
         this.drawRoundedBorders(var1);
      }
   }

   public void resetBorderColor(int var1) {
      if(var1 != 8) {
         switch(var1) {
         case 0:
            this.resetFlag(2);
            return;
         case 1:
            this.resetFlag(4);
            return;
         case 2:
            this.resetFlag(8);
            return;
         case 3:
            this.resetFlag(16);
            return;
         default:
         }
      } else {
         this.setBorderColor(-16777216);
      }
   }

   public void setBackgroundColor(int var1) {
      this.mBackgroundColor = var1;
   }

   public void setBorderColor(int var1, int var2) {
      if(var1 != 8) {
         switch(var1) {
         case 0:
            this.mBorderLeftColor = var2;
            this.setFlag(2);
            return;
         case 1:
            this.mBorderTopColor = var2;
            this.setFlag(4);
            return;
         case 2:
            this.mBorderRightColor = var2;
            this.setFlag(8);
            return;
         case 3:
            this.mBorderBottomColor = var2;
            this.setFlag(16);
            return;
         default:
         }
      } else {
         this.setBorderColor(var2);
      }
   }

   public void setBorderStyle(@Nullable String var1) {
      if("dotted".equals(var1)) {
         this.mBorderStyle = 1;
      } else if("dashed".equals(var1)) {
         this.mBorderStyle = 2;
      } else {
         this.mBorderStyle = 0;
      }

      this.setFlag(32);
   }

   public void setBorderWidth(int var1, float var2) {
      if(var1 != 8) {
         switch(var1) {
         case 0:
            this.mBorderLeftWidth = var2;
            return;
         case 1:
            this.mBorderTopWidth = var2;
            return;
         case 2:
            this.mBorderRightWidth = var2;
            return;
         case 3:
            this.mBorderBottomWidth = var2;
            return;
         default:
         }
      } else {
         this.setBorderWidth(var2);
      }
   }
}
