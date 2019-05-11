package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

public class PieChartRenderer extends DataRenderer {

   protected Canvas mBitmapCanvas;
   private RectF mCenterTextLastBounds = new RectF();
   private CharSequence mCenterTextLastValue;
   private StaticLayout mCenterTextLayout;
   private TextPaint mCenterTextPaint;
   protected PieChart mChart;
   protected WeakReference<Bitmap> mDrawBitmap;
   protected Path mDrawCenterTextPathBuffer = new Path();
   protected RectF mDrawHighlightedRectF = new RectF();
   private Paint mEntryLabelsPaint;
   private Path mHoleCirclePath = new Path();
   protected Paint mHolePaint;
   private RectF mInnerRectBuffer = new RectF();
   private Path mPathBuffer = new Path();
   private RectF[] mRectBuffer = new RectF[]{new RectF(), new RectF(), new RectF()};
   protected Paint mTransparentCirclePaint;
   protected Paint mValueLinePaint;


   public PieChartRenderer(PieChart var1, ChartAnimator var2, ViewPortHandler var3) {
      super(var2, var3);
      this.mChart = var1;
      this.mHolePaint = new Paint(1);
      this.mHolePaint.setColor(-1);
      this.mHolePaint.setStyle(Style.FILL);
      this.mTransparentCirclePaint = new Paint(1);
      this.mTransparentCirclePaint.setColor(-1);
      this.mTransparentCirclePaint.setStyle(Style.FILL);
      this.mTransparentCirclePaint.setAlpha(105);
      this.mCenterTextPaint = new TextPaint(1);
      this.mCenterTextPaint.setColor(-16777216);
      this.mCenterTextPaint.setTextSize(Utils.convertDpToPixel(12.0F));
      this.mValuePaint.setTextSize(Utils.convertDpToPixel(13.0F));
      this.mValuePaint.setColor(-1);
      this.mValuePaint.setTextAlign(Align.CENTER);
      this.mEntryLabelsPaint = new Paint(1);
      this.mEntryLabelsPaint.setColor(-1);
      this.mEntryLabelsPaint.setTextAlign(Align.CENTER);
      this.mEntryLabelsPaint.setTextSize(Utils.convertDpToPixel(13.0F));
      this.mValueLinePaint = new Paint(1);
      this.mValueLinePaint.setStyle(Style.STROKE);
   }

   protected float calculateMinimumRadiusForSpacedSlice(MPPointF var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var12 = var7 / 2.0F;
      float var10 = var1.x;
      double var8 = (double)((var6 + var7) * 0.017453292F);
      var7 = var10 + (float)Math.cos(var8) * var2;
      var10 = var1.y + (float)Math.sin(var8) * var2;
      float var11 = var1.x;
      var8 = (double)((var6 + var12) * 0.017453292F);
      var6 = (float)Math.cos(var8);
      var12 = var1.y;
      float var13 = (float)Math.sin(var8);
      return (float)((double)(var2 - (float)(Math.sqrt(Math.pow((double)(var7 - var4), 2.0D) + Math.pow((double)(var10 - var5), 2.0D)) / 2.0D * Math.tan(0.017453292519943295D * ((180.0D - (double)var3) / 2.0D)))) - Math.sqrt(Math.pow((double)(var11 + var6 * var2 - (var7 + var4) / 2.0F), 2.0D) + Math.pow((double)(var12 + var13 * var2 - (var10 + var5) / 2.0F), 2.0D)));
   }

   protected void drawCenterText(Canvas var1) {
      CharSequence var9 = this.mChart.getCenterText();
      if(this.mChart.isDrawCenterTextEnabled() && var9 != null) {
         MPPointF var5 = this.mChart.getCenterCircleBox();
         MPPointF var6 = this.mChart.getCenterTextOffset();
         float var3 = var5.x + var6.x;
         float var4 = var5.y + var6.y;
         float var2;
         if(this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled()) {
            var2 = this.mChart.getRadius() * (this.mChart.getHoleRadius() / 100.0F);
         } else {
            var2 = this.mChart.getRadius();
         }

         RectF var7 = this.mRectBuffer[0];
         var7.left = var3 - var2;
         var7.top = var4 - var2;
         var7.right = var3 + var2;
         var7.bottom = var4 + var2;
         RectF var8 = this.mRectBuffer[1];
         var8.set(var7);
         var2 = this.mChart.getCenterTextRadiusPercent() / 100.0F;
         if((double)var2 > 0.0D) {
            var8.inset((var8.width() - var8.width() * var2) / 2.0F, (var8.height() - var8.height() * var2) / 2.0F);
         }

         if(!var9.equals(this.mCenterTextLastValue) || !var8.equals(this.mCenterTextLastBounds)) {
            this.mCenterTextLastBounds.set(var8);
            this.mCenterTextLastValue = var9;
            var2 = this.mCenterTextLastBounds.width();
            this.mCenterTextLayout = new StaticLayout(var9, 0, var9.length(), this.mCenterTextPaint, (int)Math.max(Math.ceil((double)var2), 1.0D), Alignment.ALIGN_CENTER, 1.0F, 0.0F, false);
         }

         var2 = (float)this.mCenterTextLayout.getHeight();
         var1.save();
         if(VERSION.SDK_INT >= 18) {
            Path var10 = this.mDrawCenterTextPathBuffer;
            var10.reset();
            var10.addOval(var7, Direction.CW);
            var1.clipPath(var10);
         }

         var1.translate(var8.left, var8.top + (var8.height() - var2) / 2.0F);
         this.mCenterTextLayout.draw(var1);
         var1.restore();
         MPPointF.recycleInstance(var5);
         MPPointF.recycleInstance(var6);
      }

   }

   public void drawData(Canvas var1) {
      int var2 = (int)this.mViewPortHandler.getChartWidth();
      int var3 = (int)this.mViewPortHandler.getChartHeight();
      if(this.mDrawBitmap == null || ((Bitmap)this.mDrawBitmap.get()).getWidth() != var2 || ((Bitmap)this.mDrawBitmap.get()).getHeight() != var3) {
         if(var2 <= 0 || var3 <= 0) {
            return;
         }

         this.mDrawBitmap = new WeakReference(Bitmap.createBitmap(var2, var3, Config.ARGB_4444));
         this.mBitmapCanvas = new Canvas((Bitmap)this.mDrawBitmap.get());
      }

      ((Bitmap)this.mDrawBitmap.get()).eraseColor(0);
      Iterator var4 = ((PieData)this.mChart.getData()).getDataSets().iterator();

      while(var4.hasNext()) {
         IPieDataSet var5 = (IPieDataSet)var4.next();
         if(var5.isVisible() && var5.getEntryCount() > 0) {
            this.drawDataSet(var1, var5);
         }
      }

   }

   protected void drawDataSet(Canvas var1, IPieDataSet var2) {
      PieChartRenderer var23 = this;
      IPieDataSet var24 = var2;
      float var10 = this.mChart.getRotationAngle();
      float var7 = this.mAnimator.getPhaseX();
      float var15 = this.mAnimator.getPhaseY();
      RectF var25 = this.mChart.getCircleBox();
      int var22 = var2.getEntryCount();
      float[] var26 = this.mChart.getDrawAngles();
      MPPointF var28 = this.mChart.getCenterCircleBox();
      float var11 = this.mChart.getRadius();
      boolean var19;
      if(this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled()) {
         var19 = true;
      } else {
         var19 = false;
      }

      float var6;
      if(var19) {
         var6 = this.mChart.getHoleRadius() / 100.0F * var11;
      } else {
         var6 = 0.0F;
      }

      int var20 = 0;

      int var18;
      int var21;
      for(var18 = 0; var20 < var22; var18 = var21) {
         var21 = var18;
         if(Math.abs(((PieEntry)var24.getEntryForIndex(var20)).getY()) > Utils.FLOAT_EPSILON) {
            var21 = var18 + 1;
         }

         ++var20;
      }

      float var9;
      if(var18 <= 1) {
         var9 = 0.0F;
      } else {
         var9 = this.getSliceSpace(var24);
      }

      var21 = 0;
      float var12 = 0.0F;

      MPPointF var31;
      for(var20 = var22; var21 < var20; var28 = var31) {
         float var16 = var26[var21];
         if(Math.abs(var2.getEntryForIndex(var21).getY()) > Utils.FLOAT_EPSILON && !var23.mChart.needsHighlight(var21)) {
            boolean var30;
            if(var9 > 0.0F && var16 <= 180.0F) {
               var30 = true;
            } else {
               var30 = false;
            }

            var23.mRenderPaint.setColor(var2.getColor(var21));
            float var5;
            if(var18 == 1) {
               var5 = 0.0F;
            } else {
               var5 = var9 / (var11 * 0.017453292F);
            }

            float var13 = var10 + (var12 + var5 / 2.0F) * var15;
            float var8 = (var16 - var5) * var15;
            var5 = var8;
            if(var8 < 0.0F) {
               var5 = 0.0F;
            }

            var23.mPathBuffer.reset();
            var8 = var28.x;
            double var3 = (double)(var13 * 0.017453292F);
            float var14 = var8 + (float)Math.cos(var3) * var11;
            float var17 = var28.y + (float)Math.sin(var3) * var11;
            if(var5 >= 360.0F && var5 % 360.0F <= Utils.FLOAT_EPSILON) {
               var23.mPathBuffer.addCircle(var28.x, var28.y, var11, Direction.CW);
            } else {
               var23.mPathBuffer.moveTo(var14, var17);
               var23.mPathBuffer.arcTo(var25, var13, var5);
            }

            var23.mInnerRectBuffer.set(var28.x - var6, var28.y - var6, var28.x + var6, var28.y + var6);
            PieChartRenderer var29;
            if(var19 && (var6 > 0.0F || var30)) {
               if(var30) {
                  var13 = var23.calculateMinimumRadiusForSpacedSlice(var28, var11, var16 * var15, var14, var17, var13, var5);
                  var8 = var13;
                  if(var13 < 0.0F) {
                     var8 = -var13;
                  }

                  var8 = Math.max(var6, var8);
               } else {
                  var8 = var6;
               }

               var13 = var5;
               if(var18 != 1 && var8 != 0.0F) {
                  var5 = var9 / (var8 * 0.017453292F);
               } else {
                  var5 = 0.0F;
               }

               var17 = var5 / 2.0F;
               var14 = (var16 - var5) * var15;
               var5 = var14;
               if(var14 < 0.0F) {
                  var5 = 0.0F;
               }

               var14 = (var12 + var17) * var15 + var10 + var5;
               if(var13 >= 360.0F && var13 % 360.0F <= Utils.FLOAT_EPSILON) {
                  this.mPathBuffer.addCircle(var28.x, var28.y, var8, Direction.CCW);
               } else {
                  Path var32 = this.mPathBuffer;
                  var13 = var28.x;
                  var3 = (double)(var14 * 0.017453292F);
                  var32.lineTo(var13 + (float)Math.cos(var3) * var8, var28.y + var8 * (float)Math.sin(var3));
                  this.mPathBuffer.arcTo(this.mInnerRectBuffer, var14, -var5);
               }

               var31 = var28;
               var29 = this;
            } else {
               var29 = var23;
               var31 = var28;
               if(var5 % 360.0F > Utils.FLOAT_EPSILON) {
                  if(var30) {
                     var8 = var5 / 2.0F;
                     var5 = var23.calculateMinimumRadiusForSpacedSlice(var28, var11, var16 * var15, var14, var17, var13, var5);
                     var14 = var28.x;
                     var3 = (double)((var13 + var8) * 0.017453292F);
                     var8 = (float)Math.cos(var3);
                     var13 = var28.y;
                     var17 = (float)Math.sin(var3);
                     var23.mPathBuffer.lineTo(var14 + var8 * var5, var13 + var5 * var17);
                     var29 = var23;
                     var31 = var28;
                  } else {
                     var23.mPathBuffer.lineTo(var28.x, var28.y);
                     var31 = var28;
                     var29 = var23;
                  }
               }
            }

            var29.mPathBuffer.close();
            var29.mBitmapCanvas.drawPath(var29.mPathBuffer, var29.mRenderPaint);
            var23 = var29;
         } else {
            var31 = var28;
         }

         var12 += var16 * var7;
         ++var21;
      }

      MPPointF.recycleInstance(var28);
   }

   protected void drawEntryLabel(Canvas var1, String var2, float var3, float var4) {
      var1.drawText(var2, var3, var4, this.mEntryLabelsPaint);
   }

   public void drawExtras(Canvas var1) {
      this.drawHole(var1);
      var1.drawBitmap((Bitmap)this.mDrawBitmap.get(), 0.0F, 0.0F, (Paint)null);
      this.drawCenterText(var1);
   }

   public void drawHighlighted(Canvas var1, Highlight[] var2) {
      float var7 = this.mAnimator.getPhaseX();
      float var12 = this.mAnimator.getPhaseY();
      float var13 = this.mChart.getRotationAngle();
      float[] var27 = this.mChart.getDrawAngles();
      float[] var30 = this.mChart.getAbsoluteAngles();
      MPPointF var29 = this.mChart.getCenterCircleBox();
      float var14 = this.mChart.getRadius();
      boolean var19;
      if(this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled()) {
         var19 = true;
      } else {
         var19 = false;
      }

      float var5;
      if(var19) {
         var5 = this.mChart.getHoleRadius() / 100.0F * var14;
      } else {
         var5 = 0.0F;
      }

      RectF var26 = this.mDrawHighlightedRectF;
      var26.set(0.0F, 0.0F, 0.0F, 0.0F);

      for(int var20 = 0; var20 < var2.length; ++var20) {
         int var25 = (int)var2[var20].getX();
         if(var25 < var27.length) {
            IPieDataSet var28 = ((PieData)this.mChart.getData()).getDataSetByIndex(var2[var20].getDataSetIndex());
            if(var28 != null && var28.isHighlightEnabled()) {
               int var22 = var28.getEntryCount();
               int var23 = 0;

               int var21;
               int var24;
               for(var21 = 0; var23 < var22; var21 = var24) {
                  var24 = var21;
                  if(Math.abs(((PieEntry)var28.getEntryForIndex(var23)).getY()) > Utils.FLOAT_EPSILON) {
                     var24 = var21 + 1;
                  }

                  ++var23;
               }

               float var8;
               if(var25 == 0) {
                  var8 = 0.0F;
               } else {
                  var8 = var30[var25 - 1] * var7;
               }

               float var9;
               if(var21 <= 1) {
                  var9 = 0.0F;
               } else {
                  var9 = var28.getSliceSpace();
               }

               float var15 = var27[var25];
               float var6 = var28.getSelectionShift();
               float var17 = var14 + var6;
               var26.set(this.mChart.getCircleBox());
               var6 = -var6;
               var26.inset(var6, var6);
               boolean var31;
               if(var9 > 0.0F && var15 <= 180.0F) {
                  var31 = true;
               } else {
                  var31 = false;
               }

               this.mRenderPaint.setColor(var28.getColor(var25));
               float var10;
               if(var21 == 1) {
                  var10 = 0.0F;
               } else {
                  var10 = var9 / (var14 * 0.017453292F);
               }

               if(var21 == 1) {
                  var6 = 0.0F;
               } else {
                  var6 = var9 / (var17 * 0.017453292F);
               }

               float var16 = var13 + (var8 + var10 / 2.0F) * var12;
               var10 = (var15 - var10) * var12;
               if(var10 < 0.0F) {
                  var10 = 0.0F;
               }

               float var18 = (var8 + var6 / 2.0F) * var12 + var13;
               float var11 = (var15 - var6) * var12;
               var6 = var11;
               if(var11 < 0.0F) {
                  var6 = 0.0F;
               }

               this.mPathBuffer.reset();
               double var3;
               Path var32;
               if(var10 >= 360.0F && var10 % 360.0F <= Utils.FLOAT_EPSILON) {
                  this.mPathBuffer.addCircle(var29.x, var29.y, var17, Direction.CW);
               } else {
                  var32 = this.mPathBuffer;
                  var11 = var29.x;
                  var3 = (double)(var18 * 0.017453292F);
                  var32.moveTo(var11 + (float)Math.cos(var3) * var17, var29.y + var17 * (float)Math.sin(var3));
                  this.mPathBuffer.arcTo(var26, var18, var6);
               }

               if(var31) {
                  var6 = var29.x;
                  var3 = (double)(var16 * 0.017453292F);
                  var6 = this.calculateMinimumRadiusForSpacedSlice(var29, var14, var15 * var12, (float)Math.cos(var3) * var14 + var6, var29.y + (float)Math.sin(var3) * var14, var16, var10);
               } else {
                  var6 = 0.0F;
               }

               this.mInnerRectBuffer.set(var29.x - var5, var29.y - var5, var29.x + var5, var29.y + var5);
               if(var19 && (var5 > 0.0F || var31)) {
                  if(var31) {
                     var11 = var6;
                     if(var6 < 0.0F) {
                        var11 = -var6;
                     }

                     var6 = Math.max(var5, var11);
                  } else {
                     var6 = var5;
                  }

                  if(var21 != 1 && var6 != 0.0F) {
                     var9 /= var6 * 0.017453292F;
                  } else {
                     var9 = 0.0F;
                  }

                  var16 = var9 / 2.0F;
                  var11 = (var15 - var9) * var12;
                  var9 = var11;
                  if(var11 < 0.0F) {
                     var9 = 0.0F;
                  }

                  var8 = var13 + (var8 + var16) * var12 + var9;
                  if(var10 >= 360.0F && var10 % 360.0F <= Utils.FLOAT_EPSILON) {
                     this.mPathBuffer.addCircle(var29.x, var29.y, var6, Direction.CCW);
                  } else {
                     var32 = this.mPathBuffer;
                     var10 = var29.x;
                     var3 = (double)(var8 * 0.017453292F);
                     var32.lineTo(var10 + (float)Math.cos(var3) * var6, var29.y + var6 * (float)Math.sin(var3));
                     this.mPathBuffer.arcTo(this.mInnerRectBuffer, var8, -var9);
                  }
               } else if(var10 % 360.0F > Utils.FLOAT_EPSILON) {
                  if(var31) {
                     var9 = var10 / 2.0F;
                     var8 = var29.x;
                     var3 = (double)((var16 + var9) * 0.017453292F);
                     var9 = (float)Math.cos(var3);
                     var10 = var29.y;
                     var11 = (float)Math.sin(var3);
                     this.mPathBuffer.lineTo(var8 + var9 * var6, var10 + var6 * var11);
                  } else {
                     this.mPathBuffer.lineTo(var29.x, var29.y);
                  }
               }

               this.mPathBuffer.close();
               this.mBitmapCanvas.drawPath(this.mPathBuffer, this.mRenderPaint);
            }
         }
      }

      MPPointF.recycleInstance(var29);
   }

   protected void drawHole(Canvas var1) {
      if(this.mChart.isDrawHoleEnabled() && this.mBitmapCanvas != null) {
         float var2 = this.mChart.getRadius();
         float var3 = this.mChart.getHoleRadius() / 100.0F * var2;
         MPPointF var6 = this.mChart.getCenterCircleBox();
         if(Color.alpha(this.mHolePaint.getColor()) > 0) {
            this.mBitmapCanvas.drawCircle(var6.x, var6.y, var3, this.mHolePaint);
         }

         if(Color.alpha(this.mTransparentCirclePaint.getColor()) > 0 && this.mChart.getTransparentCircleRadius() > this.mChart.getHoleRadius()) {
            int var5 = this.mTransparentCirclePaint.getAlpha();
            float var4 = this.mChart.getTransparentCircleRadius() / 100.0F;
            this.mTransparentCirclePaint.setAlpha((int)((float)var5 * this.mAnimator.getPhaseX() * this.mAnimator.getPhaseY()));
            this.mHoleCirclePath.reset();
            this.mHoleCirclePath.addCircle(var6.x, var6.y, var2 * var4, Direction.CW);
            this.mHoleCirclePath.addCircle(var6.x, var6.y, var3, Direction.CCW);
            this.mBitmapCanvas.drawPath(this.mHoleCirclePath, this.mTransparentCirclePaint);
            this.mTransparentCirclePaint.setAlpha(var5);
         }

         MPPointF.recycleInstance(var6);
      }

   }

   protected void drawRoundedSlices(Canvas var1) {
      if(this.mChart.isDrawRoundedSlicesEnabled()) {
         IPieDataSet var15 = ((PieData)this.mChart.getData()).getDataSet();
         if(var15.isVisible()) {
            float var6 = this.mAnimator.getPhaseX();
            float var7 = this.mAnimator.getPhaseY();
            MPPointF var16 = this.mChart.getCenterCircleBox();
            float var9 = this.mChart.getRadius();
            float var10 = (var9 - this.mChart.getHoleRadius() * var9 / 100.0F) / 2.0F;
            float[] var17 = this.mChart.getDrawAngles();
            float var8 = this.mChart.getRotationAngle();

            for(int var14 = 0; var14 < var15.getEntryCount(); ++var14) {
               float var11 = var17[var14];
               if(Math.abs(var15.getEntryForIndex(var14).getY()) > Utils.FLOAT_EPSILON) {
                  double var2 = (double)(var9 - var10);
                  double var4 = (double)((var8 + var11) * var7);
                  float var12 = (float)(Math.cos(Math.toRadians(var4)) * var2 + (double)var16.x);
                  float var13 = (float)(var2 * Math.sin(Math.toRadians(var4)) + (double)var16.y);
                  this.mRenderPaint.setColor(var15.getColor(var14));
                  this.mBitmapCanvas.drawCircle(var12, var13, var10, this.mRenderPaint);
               }

               var8 += var11 * var6;
            }

            MPPointF.recycleInstance(var16);
         }
      }
   }

   public void drawValues(Canvas var1) {
      MPPointF var37 = this.mChart.getCenterCircleBox();
      float var6 = this.mChart.getRadius();
      float var5 = this.mChart.getRotationAngle();
      float[] var38 = this.mChart.getDrawAngles();
      float[] var39 = this.mChart.getAbsoluteAngles();
      float var12 = this.mAnimator.getPhaseX();
      float var13 = this.mAnimator.getPhaseY();
      float var14 = this.mChart.getHoleRadius() / 100.0F;
      float var4 = var6 / 10.0F * 3.6F;
      if(this.mChart.isDrawHoleEnabled()) {
         var4 = (var6 - var6 * var14) / 2.0F;
      }

      float var15 = var6 - var4;
      PieData var35 = (PieData)this.mChart.getData();
      List var36 = var35.getDataSets();
      float var16 = var35.getYValueSum();
      boolean var33 = this.mChart.isDrawEntryLabelsEnabled();
      var1.save();
      float var17 = Utils.convertDpToPixel(5.0F);
      int var25 = 0;
      int var24 = 0;

      PieData var54;
      for(var4 = var6; var24 < var36.size(); var35 = var54) {
         IPieDataSet var43 = (IPieDataSet)var36.get(var24);
         boolean var34 = var43.isDrawValuesEnabled();
         List var50;
         MPPointF var52;
         float[] var53;
         if(!var34 && !var33) {
            var52 = var37;
            var53 = var39;
            var54 = var35;
            var50 = var36;
         } else {
            PieDataSet.ValuePosition var42 = var43.getXValuePosition();
            PieDataSet.ValuePosition var44 = var43.getYValuePosition();
            this.applyValueTextStyle(var43);
            float var18 = (float)Utils.calcTextHeight(this.mValuePaint, "Q") + Utils.convertDpToPixel(4.0F);
            IValueFormatter var47 = var43.getValueFormatter();
            int var28 = var43.getEntryCount();
            Paint var40 = this.mValueLinePaint;
            var40.setColor(var43.getValueLineColor());
            this.mValueLinePaint.setStrokeWidth(Utils.convertDpToPixel(var43.getValueLineWidth()));
            float var19 = this.getSliceSpace(var43);
            MPPointF var41 = MPPointF.getInstance(var43.getIconsOffset());
            var41.x = Utils.convertDpToPixel(var41.x);
            var41.y = Utils.convertDpToPixel(var41.y);
            byte var27 = 0;
            int var26 = var25;
            var25 = var24;
            IPieDataSet var51 = var43;

            for(var24 = var27; var24 < var28; var38 = var38) {
               PieEntry var57 = (PieEntry)var51.getEntryForIndex(var24);
               if(var26 == 0) {
                  var6 = 0.0F;
               } else {
                  var6 = var39[var26 - 1] * var12;
               }

               float var9 = var5 + (var6 + (var38[var26] - var19 / (var15 * 0.017453292F) / 2.0F) / 2.0F) * var13;
               float var7;
               if(this.mChart.isUsePercentValuesEnabled()) {
                  var7 = var57.getY() / var16 * 100.0F;
               } else {
                  var7 = var57.getY();
               }

               double var2 = (double)(var9 * 0.017453292F);
               float var10 = (float)Math.cos(var2);
               float var11 = (float)Math.sin(var2);
               boolean var29;
               if(var33 && var42 == PieDataSet.ValuePosition.OUTSIDE_SLICE) {
                  var29 = true;
               } else {
                  var29 = false;
               }

               boolean var30;
               if(var34 && var44 == PieDataSet.ValuePosition.OUTSIDE_SLICE) {
                  var30 = true;
               } else {
                  var30 = false;
               }

               boolean var48;
               if(var33 && var42 == PieDataSet.ValuePosition.INSIDE_SLICE) {
                  var48 = true;
               } else {
                  var48 = false;
               }

               boolean var31;
               if(var34 && var44 == PieDataSet.ValuePosition.INSIDE_SLICE) {
                  var31 = true;
               } else {
                  var31 = false;
               }

               float var8;
               float var20;
               if(var29 || var30) {
                  float var22 = var51.getValueLinePart1Length();
                  var6 = var51.getValueLinePart2Length();
                  var8 = var51.getValueLinePart1OffsetPercentage() / 100.0F;
                  if(this.mChart.isDrawHoleEnabled()) {
                     var20 = var4 * var14;
                     var8 = (var4 - var20) * var8 + var20;
                  } else {
                     var8 = var4 * var8;
                  }

                  if(var51.isValueLineVariableLength()) {
                     var6 = var6 * var15 * (float)Math.abs(Math.sin(var2));
                  } else {
                     var6 *= var15;
                  }

                  var20 = var37.x;
                  float var21 = var37.y;
                  float var23 = (var22 + 1.0F) * var15;
                  var22 = var37.x + var23 * var10;
                  var23 = var23 * var11 + var37.y;
                  var2 = (double)var9 % 360.0D;
                  if(var2 >= 90.0D && var2 <= 270.0D) {
                     var6 = var22 - var6;
                     this.mValuePaint.setTextAlign(Align.RIGHT);
                     if(var29) {
                        this.mEntryLabelsPaint.setTextAlign(Align.RIGHT);
                     }

                     var9 = var6;
                     var6 -= var17;
                  } else {
                     var9 = var22 + var6;
                     this.mValuePaint.setTextAlign(Align.LEFT);
                     if(var29) {
                        this.mEntryLabelsPaint.setTextAlign(Align.LEFT);
                     }

                     var6 = var9 + var17;
                  }

                  if(var51.getValueLineColor() != 1122867) {
                     var1.drawLine(var8 * var10 + var20, var8 * var11 + var21, var22, var23, this.mValueLinePaint);
                     var1.drawLine(var22, var23, var9, var23, this.mValueLinePaint);
                  }

                  if(var29 && var30) {
                     int var49 = var51.getValueTextColor(var24);
                     this.drawValue(var1, var47, var7, var57, 0, var6, var23, var49);
                     if(var24 < var35.getEntryCount() && var57.getLabel() != null) {
                        this.drawEntryLabel(var1, var57.getLabel(), var6, var23 + var18);
                     }
                  } else if(var29) {
                     if(var24 < var35.getEntryCount() && var57.getLabel() != null) {
                        this.drawEntryLabel(var1, var57.getLabel(), var6, var23 + var18 / 2.0F);
                     }
                  } else if(var30) {
                     this.drawValue(var1, var47, var7, var57, 0, var6, var23 + var18 / 2.0F, var51.getValueTextColor(var24));
                  }
               }

               MPPointF var45 = var37;
               if(var48 || var31) {
                  var6 = var15 * var10 + var37.x;
                  var8 = var15 * var11 + var37.y;
                  this.mValuePaint.setTextAlign(Align.CENTER);
                  if(var48 && var31) {
                     this.drawValue(var1, var47, var7, var57, 0, var6, var8, var51.getValueTextColor(var24));
                     if(var24 < var35.getEntryCount() && var57.getLabel() != null) {
                        this.drawEntryLabel(var1, var57.getLabel(), var6, var8 + var18);
                     }
                  } else if(var48) {
                     if(var24 < var35.getEntryCount() && var57.getLabel() != null) {
                        this.drawEntryLabel(var1, var57.getLabel(), var6, var8 + var18 / 2.0F);
                     }
                  } else if(var31) {
                     this.drawValue(var1, var47, var7, var57, 0, var6, var8 + var18 / 2.0F, var51.getValueTextColor(var24));
                  }
               }

               if(var57.getIcon() != null && var51.isDrawIconsEnabled()) {
                  Drawable var46 = var57.getIcon();
                  var6 = var41.y;
                  var45 = var37;
                  var7 = var37.x;
                  var8 = var41.y;
                  var9 = var37.y;
                  var20 = var41.x;
                  Utils.drawImage(var1, var46, (int)((var15 + var6) * var10 + var7), (int)((var15 + var8) * var11 + var9 + var20), var46.getIntrinsicWidth(), var46.getIntrinsicHeight());
               }

               ++var26;
               ++var24;
               var35 = var35;
               var37 = var45;
            }

            var52 = var37;
            var53 = var39;
            var54 = var35;
            MPPointF.recycleInstance(var41);
            var50 = var36;
            var24 = var25;
            var25 = var26;
         }

         ++var24;
         var39 = var53;
         var37 = var52;
         var36 = var50;
      }

      MPPointF.recycleInstance(var37);
      var1.restore();
   }

   public TextPaint getPaintCenterText() {
      return this.mCenterTextPaint;
   }

   public Paint getPaintEntryLabels() {
      return this.mEntryLabelsPaint;
   }

   public Paint getPaintHole() {
      return this.mHolePaint;
   }

   public Paint getPaintTransparentCircle() {
      return this.mTransparentCirclePaint;
   }

   protected float getSliceSpace(IPieDataSet var1) {
      return !var1.isAutomaticallyDisableSliceSpacingEnabled()?var1.getSliceSpace():(var1.getSliceSpace() / this.mViewPortHandler.getSmallestContentExtension() > var1.getYMin() / ((PieData)this.mChart.getData()).getYValueSum() * 2.0F?0.0F:var1.getSliceSpace());
   }

   public void initBuffers() {}

   public void releaseBitmap() {
      if(this.mBitmapCanvas != null) {
         this.mBitmapCanvas.setBitmap((Bitmap)null);
         this.mBitmapCanvas = null;
      }

      if(this.mDrawBitmap != null) {
         ((Bitmap)this.mDrawBitmap.get()).recycle();
         this.mDrawBitmap.clear();
         this.mDrawBitmap = null;
      }

   }
}
