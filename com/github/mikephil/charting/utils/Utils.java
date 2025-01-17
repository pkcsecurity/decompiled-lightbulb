package com.github.mikephil.charting.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointF;
import java.util.List;

public abstract class Utils {

   public static final double DEG2RAD = 0.017453292519943295D;
   public static final double DOUBLE_EPSILON = Double.longBitsToDouble(1L);
   public static final float FDEG2RAD = 0.017453292F;
   public static final float FLOAT_EPSILON = Float.intBitsToFloat(1);
   private static final int[] POW_10 = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
   private static Rect mCalcTextHeightRect = new Rect();
   private static Rect mCalcTextSizeRect = new Rect();
   private static IValueFormatter mDefaultValueFormatter = generateDefaultValueFormatter();
   private static Rect mDrawTextRectBuffer = new Rect();
   private static Rect mDrawableBoundsCache = new Rect();
   private static FontMetrics mFontMetrics = new FontMetrics();
   private static FontMetrics mFontMetricsBuffer = new FontMetrics();
   private static int mMaximumFlingVelocity;
   private static DisplayMetrics mMetrics;
   private static int mMinimumFlingVelocity;


   public static int calcTextHeight(Paint var0, String var1) {
      Rect var2 = mCalcTextHeightRect;
      var2.set(0, 0, 0, 0);
      var0.getTextBounds(var1, 0, var1.length(), var2);
      return var2.height();
   }

   public static FSize calcTextSize(Paint var0, String var1) {
      FSize var2 = FSize.getInstance(0.0F, 0.0F);
      calcTextSize(var0, var1, var2);
      return var2;
   }

   public static void calcTextSize(Paint var0, String var1, FSize var2) {
      Rect var3 = mCalcTextSizeRect;
      var3.set(0, 0, 0, 0);
      var0.getTextBounds(var1, 0, var1.length(), var3);
      var2.width = (float)var3.width();
      var2.height = (float)var3.height();
   }

   public static int calcTextWidth(Paint var0, String var1) {
      return (int)var0.measureText(var1);
   }

   public static float convertDpToPixel(float var0) {
      if(mMetrics == null) {
         Log.e("MPChartLib-Utils", "Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertDpToPixel(...). Otherwise conversion does not take place.");
         return var0;
      } else {
         return var0 * mMetrics.density;
      }
   }

   public static int[] convertIntegers(List<Integer> var0) {
      int[] var1 = new int[var0.size()];
      copyIntegers(var0, var1);
      return var1;
   }

   public static float convertPixelsToDp(float var0) {
      if(mMetrics == null) {
         Log.e("MPChartLib-Utils", "Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertPixelsToDp(...). Otherwise conversion does not take place.");
         return var0;
      } else {
         return var0 / mMetrics.density;
      }
   }

   public static String[] convertStrings(List<String> var0) {
      String[] var2 = new String[var0.size()];

      for(int var1 = 0; var1 < var2.length; ++var1) {
         var2[var1] = (String)var0.get(var1);
      }

      return var2;
   }

   public static void copyIntegers(List<Integer> var0, int[] var1) {
      int var2;
      if(var1.length < var0.size()) {
         var2 = var1.length;
      } else {
         var2 = var0.size();
      }

      for(int var3 = 0; var3 < var2; ++var3) {
         var1[var3] = ((Integer)var0.get(var3)).intValue();
      }

   }

   public static void copyStrings(List<String> var0, String[] var1) {
      int var2;
      if(var1.length < var0.size()) {
         var2 = var1.length;
      } else {
         var2 = var0.size();
      }

      for(int var3 = 0; var3 < var2; ++var3) {
         var1[var3] = (String)var0.get(var3);
      }

   }

   public static void drawImage(Canvas var0, Drawable var1, int var2, int var3, int var4, int var5) {
      MPPointF var6 = MPPointF.getInstance();
      var6.x = (float)(var2 - var4 / 2);
      var6.y = (float)(var3 - var5 / 2);
      var1.copyBounds(mDrawableBoundsCache);
      var1.setBounds(mDrawableBoundsCache.left, mDrawableBoundsCache.top, mDrawableBoundsCache.left + var4, mDrawableBoundsCache.top + var4);
      var2 = var0.save();
      var0.translate(var6.x, var6.y);
      var1.draw(var0);
      var0.restoreToCount(var2);
   }

   public static void drawMultilineText(Canvas var0, StaticLayout var1, float var2, float var3, TextPaint var4, MPPointF var5, float var6) {
      float var7 = var4.getFontMetrics(mFontMetricsBuffer);
      float var11 = (float)var1.getWidth();
      float var12 = (float)var1.getLineCount() * var7;
      float var10 = 0.0F - (float)mDrawTextRectBuffer.left;
      float var9 = var12 + 0.0F;
      Align var13 = var4.getTextAlign();
      var4.setTextAlign(Align.LEFT);
      if(var6 != 0.0F) {
         float var8;
         label22: {
            if(var5.x == 0.5F) {
               var8 = var2;
               var7 = var3;
               if(var5.y == 0.5F) {
                  break label22;
               }
            }

            FSize var14 = getSizeOfRotatedRectangleByDegrees(var11, var12, var6);
            var8 = var2 - var14.width * (var5.x - 0.5F);
            var7 = var3 - var14.height * (var5.y - 0.5F);
            FSize.recycleInstance(var14);
         }

         var0.save();
         var0.translate(var8, var7);
         var0.rotate(var6);
         var0.translate(var10 - var11 * 0.5F, var9 - var12 * 0.5F);
         var1.draw(var0);
         var0.restore();
      } else {
         label17: {
            if(var5.x == 0.0F) {
               var7 = var10;
               var6 = var9;
               if(var5.y == 0.0F) {
                  break label17;
               }
            }

            var7 = var10 - var11 * var5.x;
            var6 = var9 - var12 * var5.y;
         }

         var0.save();
         var0.translate(var7 + var2, var6 + var3);
         var1.draw(var0);
         var0.restore();
      }

      var4.setTextAlign(var13);
   }

   public static void drawMultilineText(Canvas var0, String var1, float var2, float var3, TextPaint var4, FSize var5, MPPointF var6, float var7) {
      drawMultilineText(var0, new StaticLayout(var1, 0, var1.length(), var4, (int)Math.max(Math.ceil((double)var5.width), 1.0D), Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false), var2, var3, var4, var6, var7);
   }

   public static void drawXAxisValue(Canvas var0, String var1, float var2, float var3, Paint var4, MPPointF var5, float var6) {
      float var11 = var4.getFontMetrics(mFontMetricsBuffer);
      var4.getTextBounds(var1, 0, var1.length(), mDrawTextRectBuffer);
      float var10 = 0.0F - (float)mDrawTextRectBuffer.left;
      float var9 = -mFontMetricsBuffer.ascent + 0.0F;
      Align var13 = var4.getTextAlign();
      var4.setTextAlign(Align.LEFT);
      float var7;
      if(var6 != 0.0F) {
         float var8;
         float var12;
         label22: {
            var12 = (float)mDrawTextRectBuffer.width();
            if(var5.x == 0.5F) {
               var8 = var2;
               var7 = var3;
               if(var5.y == 0.5F) {
                  break label22;
               }
            }

            FSize var14 = getSizeOfRotatedRectangleByDegrees((float)mDrawTextRectBuffer.width(), var11, var6);
            var8 = var2 - var14.width * (var5.x - 0.5F);
            var7 = var3 - var14.height * (var5.y - 0.5F);
            FSize.recycleInstance(var14);
         }

         var0.save();
         var0.translate(var8, var7);
         var0.rotate(var6);
         var0.drawText(var1, var10 - var12 * 0.5F, var9 - var11 * 0.5F, var4);
         var0.restore();
      } else {
         label17: {
            if(var5.x == 0.0F) {
               var7 = var10;
               var6 = var9;
               if(var5.y == 0.0F) {
                  break label17;
               }
            }

            var7 = var10 - (float)mDrawTextRectBuffer.width() * var5.x;
            var6 = var9 - var11 * var5.y;
         }

         var0.drawText(var1, var7 + var2, var6 + var3, var4);
      }

      var4.setTextAlign(var13);
   }

   public static String formatNumber(float var0, int var1, boolean var2) {
      return formatNumber(var0, var1, var2, '.');
   }

   public static String formatNumber(float var0, int var1, boolean var2, char var3) {
      char[] var13 = new char[35];
      if(var0 == 0.0F) {
         return "0";
      } else {
         byte var4 = 0;
         boolean var7;
         if(var0 < 1.0F && var0 > -1.0F) {
            var7 = true;
         } else {
            var7 = false;
         }

         boolean var6;
         if(var0 < 0.0F) {
            var0 = -var0;
            var6 = true;
         } else {
            var6 = false;
         }

         int var5;
         if(var1 > POW_10.length) {
            var5 = POW_10.length - 1;
         } else {
            var5 = var1;
         }

         long var11 = (long)Math.round(var0 * (float)POW_10[var5]);
         int var9 = var13.length - 1;
         boolean var8 = false;
         var1 = var4;
         int var14 = var9;

         while(var11 != 0L || var1 < var5 + 1) {
            int var10 = (int)(var11 % 10L);
            var11 /= 10L;
            var9 = var14 - 1;
            var13[var14] = (char)(var10 + 48);
            ++var1;
            if(var1 == var5) {
               var13[var9] = 44;
               ++var1;
               var14 = var9 - 1;
               var8 = true;
            } else {
               if(var2 && var11 != 0L && var1 > var5) {
                  if(var8) {
                     if((var1 - var5) % 4 == 0) {
                        var14 = var9 - 1;
                        var13[var9] = var3;
                        ++var1;
                        continue;
                     }
                  } else if((var1 - var5) % 4 == 3) {
                     var14 = var9 - 1;
                     var13[var9] = var3;
                     ++var1;
                     continue;
                  }
               }

               var14 = var9;
            }
         }

         int var15 = var14;
         var5 = var1;
         if(var7) {
            var13[var14] = 48;
            var5 = var1 + 1;
            var15 = var14 - 1;
         }

         var1 = var5;
         if(var6) {
            var13[var15] = 45;
            var1 = var5 + 1;
         }

         var1 = var13.length - var1;
         return String.valueOf(var13, var1, var13.length - var1);
      }
   }

   private static IValueFormatter generateDefaultValueFormatter() {
      return new DefaultValueFormatter(1);
   }

   public static int getDecimals(float var0) {
      var0 = roundToNextSignificant((double)var0);
      return Float.isInfinite(var0)?0:(int)Math.ceil(-Math.log10((double)var0)) + 2;
   }

   public static IValueFormatter getDefaultValueFormatter() {
      return mDefaultValueFormatter;
   }

   public static float getLineHeight(Paint var0) {
      return getLineHeight(var0, mFontMetrics);
   }

   public static float getLineHeight(Paint var0, FontMetrics var1) {
      var0.getFontMetrics(var1);
      return var1.descent - var1.ascent;
   }

   public static float getLineSpacing(Paint var0) {
      return getLineSpacing(var0, mFontMetrics);
   }

   public static float getLineSpacing(Paint var0, FontMetrics var1) {
      var0.getFontMetrics(var1);
      return var1.ascent - var1.top + var1.bottom;
   }

   public static int getMaximumFlingVelocity() {
      return mMaximumFlingVelocity;
   }

   public static int getMinimumFlingVelocity() {
      return mMinimumFlingVelocity;
   }

   public static float getNormalizedAngle(float var0) {
      while(var0 < 0.0F) {
         var0 += 360.0F;
      }

      return var0 % 360.0F;
   }

   public static MPPointF getPosition(MPPointF var0, float var1, float var2) {
      MPPointF var3 = MPPointF.getInstance(0.0F, 0.0F);
      getPosition(var0, var1, var2, var3);
      return var3;
   }

   public static void getPosition(MPPointF var0, float var1, float var2, MPPointF var3) {
      double var4 = (double)var0.x;
      double var6 = (double)var1;
      double var8 = (double)var2;
      var3.x = (float)(var4 + Math.cos(Math.toRadians(var8)) * var6);
      var3.y = (float)((double)var0.y + var6 * Math.sin(Math.toRadians(var8)));
   }

   public static int getSDKInt() {
      return VERSION.SDK_INT;
   }

   public static FSize getSizeOfRotatedRectangleByDegrees(float var0, float var1, float var2) {
      return getSizeOfRotatedRectangleByRadians(var0, var1, var2 * 0.017453292F);
   }

   public static FSize getSizeOfRotatedRectangleByDegrees(FSize var0, float var1) {
      return getSizeOfRotatedRectangleByRadians(var0.width, var0.height, var1 * 0.017453292F);
   }

   public static FSize getSizeOfRotatedRectangleByRadians(float var0, float var1, float var2) {
      double var3 = (double)var2;
      return FSize.getInstance(Math.abs((float)Math.cos(var3) * var0) + Math.abs((float)Math.sin(var3) * var1), Math.abs(var0 * (float)Math.sin(var3)) + Math.abs(var1 * (float)Math.cos(var3)));
   }

   public static FSize getSizeOfRotatedRectangleByRadians(FSize var0, float var1) {
      return getSizeOfRotatedRectangleByRadians(var0.width, var0.height, var1);
   }

   public static void init(Context var0) {
      if(var0 == null) {
         mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
         mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
         Log.e("MPChartLib-Utils", "Utils.init(...) PROVIDED CONTEXT OBJECT IS NULL");
      } else {
         ViewConfiguration var1 = ViewConfiguration.get(var0);
         mMinimumFlingVelocity = var1.getScaledMinimumFlingVelocity();
         mMaximumFlingVelocity = var1.getScaledMaximumFlingVelocity();
         mMetrics = var0.getResources().getDisplayMetrics();
      }
   }

   @Deprecated
   public static void init(Resources var0) {
      mMetrics = var0.getDisplayMetrics();
      mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
      mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
   }

   public static double nextUp(double var0) {
      if(var0 == Double.POSITIVE_INFINITY) {
         return var0;
      } else {
         var0 += 0.0D;
         long var4 = Double.doubleToRawLongBits(var0);
         long var2;
         if(var0 >= 0.0D) {
            var2 = 1L;
         } else {
            var2 = -1L;
         }

         return Double.longBitsToDouble(var4 + var2);
      }
   }

   @SuppressLint({"NewApi"})
   public static void postInvalidateOnAnimation(View var0) {
      if(VERSION.SDK_INT >= 16) {
         var0.postInvalidateOnAnimation();
      } else {
         var0.postInvalidateDelayed(10L);
      }
   }

   public static float roundToNextSignificant(double var0) {
      if(!Double.isInfinite(var0) && !Double.isNaN(var0) && var0 != 0.0D) {
         double var2;
         if(var0 < 0.0D) {
            var2 = -var0;
         } else {
            var2 = var0;
         }

         float var4 = (float)Math.pow(10.0D, (double)(1 - (int)((float)Math.ceil((double)((float)Math.log10(var2))))));
         return (float)Math.round(var0 * (double)var4) / var4;
      } else {
         return 0.0F;
      }
   }

   public static void velocityTrackerPointerUpCleanUpIfNecessary(MotionEvent var0, VelocityTracker var1) {
      var1.computeCurrentVelocity(1000, (float)mMaximumFlingVelocity);
      int var5 = var0.getActionIndex();
      int var4 = var0.getPointerId(var5);
      float var2 = var1.getXVelocity(var4);
      float var3 = var1.getYVelocity(var4);
      int var6 = var0.getPointerCount();

      for(var4 = 0; var4 < var6; ++var4) {
         if(var4 != var5) {
            int var7 = var0.getPointerId(var4);
            if(var1.getXVelocity(var7) * var2 + var1.getYVelocity(var7) * var3 < 0.0F) {
               var1.clear();
               return;
            }
         }
      }

   }
}
