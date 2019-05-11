package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.Range;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class BarChartRenderer extends BarLineScatterCandleBubbleRenderer {

   protected Paint mBarBorderPaint;
   protected BarBuffer[] mBarBuffers;
   protected RectF mBarRect = new RectF();
   private RectF mBarShadowRectBuffer = new RectF();
   protected BarDataProvider mChart;
   protected Paint mShadowPaint;


   public BarChartRenderer(BarDataProvider var1, ChartAnimator var2, ViewPortHandler var3) {
      super(var2, var3);
      this.mChart = var1;
      this.mHighlightPaint = new Paint(1);
      this.mHighlightPaint.setStyle(Style.FILL);
      this.mHighlightPaint.setColor(Color.rgb(0, 0, 0));
      this.mHighlightPaint.setAlpha(120);
      this.mShadowPaint = new Paint(1);
      this.mShadowPaint.setStyle(Style.FILL);
      this.mBarBorderPaint = new Paint(1);
      this.mBarBorderPaint.setStyle(Style.STROKE);
   }

   public void drawData(Canvas var1) {
      BarData var3 = this.mChart.getBarData();

      for(int var2 = 0; var2 < var3.getDataSetCount(); ++var2) {
         IBarDataSet var4 = (IBarDataSet)var3.getDataSetByIndex(var2);
         if(var4.isVisible()) {
            this.drawDataSet(var1, var4, var2);
         }
      }

   }

   protected void drawDataSet(Canvas var1, IBarDataSet var2, int var3) {
      Transformer var14 = this.mChart.getTransformer(var2.getAxisDependency());
      this.mBarBorderPaint.setColor(var2.getBarBorderColor());
      this.mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(var2.getBarBorderWidth()));
      float var4 = var2.getBarBorderWidth();
      byte var10 = 0;
      boolean var11 = true;
      boolean var8;
      if(var4 > 0.0F) {
         var8 = true;
      } else {
         var8 = false;
      }

      var4 = this.mAnimator.getPhaseX();
      float var5 = this.mAnimator.getPhaseY();
      float var6;
      int var9;
      int var12;
      if(this.mChart.isDrawBarShadowEnabled()) {
         this.mShadowPaint.setColor(var2.getBarShadowColor());
         var6 = this.mChart.getBarData().getBarWidth() / 2.0F;
         var12 = Math.min((int)Math.ceil((double)((float)var2.getEntryCount() * var4)), var2.getEntryCount());

         for(var9 = 0; var9 < var12; ++var9) {
            float var7 = ((BarEntry)var2.getEntryForIndex(var9)).getX();
            this.mBarShadowRectBuffer.left = var7 - var6;
            this.mBarShadowRectBuffer.right = var7 + var6;
            var14.rectValueToPixel(this.mBarShadowRectBuffer);
            if(this.mViewPortHandler.isInBoundsLeft(this.mBarShadowRectBuffer.right)) {
               if(!this.mViewPortHandler.isInBoundsRight(this.mBarShadowRectBuffer.left)) {
                  break;
               }

               this.mBarShadowRectBuffer.top = this.mViewPortHandler.contentTop();
               this.mBarShadowRectBuffer.bottom = this.mViewPortHandler.contentBottom();
               var1.drawRect(this.mBarShadowRectBuffer, this.mShadowPaint);
            }
         }
      }

      BarBuffer var13 = this.mBarBuffers[var3];
      var13.setPhases(var4, var5);
      var13.setDataSet(var3);
      var13.setInverted(this.mChart.isInverted(var2.getAxisDependency()));
      var13.setBarWidth(this.mChart.getBarData().getBarWidth());
      var13.feed(var2);
      var14.pointValuesToPixel(var13.buffer);
      boolean var16;
      if(var2.getColors().size() == 1) {
         var16 = var11;
      } else {
         var16 = false;
      }

      var9 = var10;
      if(var16) {
         this.mRenderPaint.setColor(var2.getColor());
         var9 = var10;
      }

      for(; var9 < var13.size(); var9 += 4) {
         ViewPortHandler var19 = this.mViewPortHandler;
         float[] var15 = var13.buffer;
         int var17 = var9 + 2;
         if(var19.isInBoundsLeft(var15[var17])) {
            if(!this.mViewPortHandler.isInBoundsRight(var13.buffer[var9])) {
               return;
            }

            if(!var16) {
               this.mRenderPaint.setColor(var2.getColor(var9 / 4));
            }

            var4 = var13.buffer[var9];
            float[] var20 = var13.buffer;
            int var18 = var9 + 1;
            var5 = var20[var18];
            var6 = var13.buffer[var17];
            var20 = var13.buffer;
            var12 = var9 + 3;
            var1.drawRect(var4, var5, var6, var20[var12], this.mRenderPaint);
            if(var8) {
               var1.drawRect(var13.buffer[var9], var13.buffer[var18], var13.buffer[var17], var13.buffer[var12], this.mBarBorderPaint);
            }
         }
      }

   }

   public void drawExtras(Canvas var1) {}

   public void drawHighlighted(Canvas var1, Highlight[] var2) {
      BarData var8 = this.mChart.getBarData();
      int var7 = var2.length;

      for(int var5 = 0; var5 < var7; ++var5) {
         Highlight var9 = var2[var5];
         IBarDataSet var12 = (IBarDataSet)var8.getDataSetByIndex(var9.getDataSetIndex());
         if(var12 != null && var12.isHighlightEnabled()) {
            BarEntry var10 = (BarEntry)var12.getEntryForXValue(var9.getX(), var9.getY());
            if(this.isInBoundsX(var10, var12)) {
               Transformer var11 = this.mChart.getTransformer(var12.getAxisDependency());
               this.mHighlightPaint.setColor(var12.getHighLightColor());
               this.mHighlightPaint.setAlpha(var12.getHighLightAlpha());
               boolean var6;
               if(var9.getStackIndex() >= 0 && var10.isStacked()) {
                  var6 = true;
               } else {
                  var6 = false;
               }

               float var3;
               float var4;
               if(var6) {
                  if(this.mChart.isHighlightFullBarEnabled()) {
                     var3 = var10.getPositiveSum();
                     var4 = -var10.getNegativeSum();
                  } else {
                     Range var13 = var10.getRanges()[var9.getStackIndex()];
                     var3 = var13.from;
                     var4 = var13.to;
                  }
               } else {
                  var3 = var10.getY();
                  var4 = 0.0F;
               }

               this.prepareBarHighlight(var10.getX(), var3, var4, var8.getBarWidth() / 2.0F, var11);
               this.setHighlightDrawPos(var9, this.mBarRect);
               var1.drawRect(this.mBarRect, this.mHighlightPaint);
            }
         }
      }

   }

   public void drawValues(Canvas var1) {
      if(this.isDrawingValuesAllowed(this.mChart)) {
         List var22 = this.mChart.getBarData().getDataSets();
         float var4 = Utils.convertDpToPixel(4.5F);
         boolean var20 = this.mChart.isDrawValueAboveBarEnabled();

         float var2;
         for(int var13 = 0; var13 < this.mChart.getBarData().getDataSetCount(); var4 = var2) {
            IBarDataSet var30 = (IBarDataSet)var22.get(var13);
            List var23;
            if(!this.shouldDrawValues(var30)) {
               var2 = var4;
               var23 = var22;
            } else {
               this.applyValueTextStyle(var30);
               boolean var21 = this.mChart.isInverted(var30.getAxisDependency());
               float var7 = (float)Utils.calcTextHeight(this.mValuePaint, "8");
               float var5;
               if(var20) {
                  var5 = -var4;
               } else {
                  var5 = var7 + var4;
               }

               float var6;
               if(var20) {
                  var6 = var7 + var4;
               } else {
                  var6 = -var4;
               }

               float var3 = var5;
               var2 = var6;
               if(var21) {
                  var3 = -var5 - var7;
                  var2 = -var6 - var7;
               }

               BarBuffer var27 = this.mBarBuffers[var13];
               float var12 = this.mAnimator.getPhaseY();
               MPPointF var25 = MPPointF.getInstance(var30.getIconsOffset());
               var25.x = Utils.convertDpToPixel(var25.x);
               var25.y = Utils.convertDpToPixel(var25.y);
               float var8;
               int var14;
               int var15;
               int var16;
               float[] var26;
               MPPointF var40;
               if(!var30.isStacked()) {
                  var14 = 0;
                  var23 = var22;
                  BarBuffer var24 = var27;

                  MPPointF var33;
                  for(var33 = var25; (float)var14 < (float)var24.buffer.length * this.mAnimator.getPhaseX(); var14 += 4) {
                     var6 = (var24.buffer[var14] + var24.buffer[var14 + 2]) / 2.0F;
                     if(!this.mViewPortHandler.isInBoundsRight(var6)) {
                        break;
                     }

                     ViewPortHandler var37 = this.mViewPortHandler;
                     var26 = var24.buffer;
                     var15 = var14 + 1;
                     if(var37.isInBoundsY(var26[var15]) && this.mViewPortHandler.isInBoundsLeft(var6)) {
                        var16 = var14 / 4;
                        BarEntry var42 = (BarEntry)var30.getEntryForIndex(var16);
                        var7 = var42.getY();
                        if(var30.isDrawValuesEnabled()) {
                           IValueFormatter var38 = var30.getValueFormatter();
                           if(var7 >= 0.0F) {
                              var5 = var24.buffer[var15] + var3;
                           } else {
                              var5 = var24.buffer[var14 + 3] + var2;
                           }

                           this.drawValue(var1, var38, var7, var42, var13, var6, var5, var30.getValueTextColor(var16));
                        }

                        if(var42.getIcon() != null && var30.isDrawIconsEnabled()) {
                           Drawable var44 = var42.getIcon();
                           if(var7 >= 0.0F) {
                              var5 = var24.buffer[var15] + var3;
                           } else {
                              var5 = var24.buffer[var14 + 3] + var2;
                           }

                           var7 = var33.x;
                           var8 = var33.y;
                           Utils.drawImage(var1, var44, (int)(var6 + var7), (int)(var5 + var8), var44.getIntrinsicWidth(), var44.getIntrinsicHeight());
                        }
                     }
                  }

                  var5 = var4;
                  var21 = var20;
                  var40 = var33;
                  var15 = var13;
               } else {
                  MPPointF var36 = var25;
                  List var39 = var22;
                  Transformer var34 = this.mChart.getTransformer(var30.getAxisDependency());
                  var14 = 0;
                  var16 = 0;

                  while(true) {
                     var5 = var4;
                     var21 = var20;
                     var40 = var36;
                     var15 = var13;
                     var23 = var39;
                     if((float)var14 >= (float)var30.getEntryCount() * this.mAnimator.getPhaseX()) {
                        break;
                     }

                     label204: {
                        BarEntry var28 = (BarEntry)var30.getEntryForIndex(var14);
                        float[] var35 = var28.getYVals();
                        var8 = (var27.buffer[var16] + var27.buffer[var16 + 2]) / 2.0F;
                        int var18 = var30.getValueTextColor(var14);
                        float var9;
                        float[] var29;
                        if(var35 == null) {
                           if(!this.mViewPortHandler.isInBoundsRight(var8)) {
                              var5 = var4;
                              var21 = var20;
                              var40 = var36;
                              var15 = var13;
                              var23 = var39;
                              break;
                           }

                           ViewPortHandler var41 = this.mViewPortHandler;
                           var29 = var27.buffer;
                           var15 = var16 + 1;
                           if(!var41.isInBoundsY(var29[var15]) || !this.mViewPortHandler.isInBoundsLeft(var8)) {
                              var15 = var16;
                              break label204;
                           }

                           if(var30.isDrawValuesEnabled()) {
                              IValueFormatter var43 = var30.getValueFormatter();
                              var6 = var28.getY();
                              var7 = var27.buffer[var15];
                              if(var28.getY() >= 0.0F) {
                                 var5 = var3;
                              } else {
                                 var5 = var2;
                              }

                              this.drawValue(var1, var43, var6, var28, var13, var8, var7 + var5, var18);
                           }

                           if(var28.getIcon() != null && var30.isDrawIconsEnabled()) {
                              Drawable var45 = var28.getIcon();
                              var6 = var27.buffer[var15];
                              if(var28.getY() >= 0.0F) {
                                 var5 = var3;
                              } else {
                                 var5 = var2;
                              }

                              var7 = var36.x;
                              var9 = var36.y;
                              Utils.drawImage(var1, var45, (int)(var8 + var7), (int)(var6 + var5 + var9), var45.getIntrinsicWidth(), var45.getIntrinsicHeight());
                           }
                        } else {
                           var29 = var35;
                           var26 = new float[var35.length * 2];
                           var5 = -var28.getNegativeSum();
                           int var17 = 0;
                           var15 = 0;

                           float var10;
                           for(var7 = 0.0F; var17 < var26.length; var5 = var10) {
                              label163: {
                                 float var11 = var29[var15];
                                 if(var11 == 0.0F) {
                                    var6 = var11;
                                    var9 = var7;
                                    var10 = var5;
                                    if(var7 == 0.0F) {
                                       break label163;
                                    }

                                    if(var5 == 0.0F) {
                                       var6 = var11;
                                       var9 = var7;
                                       var10 = var5;
                                       break label163;
                                    }
                                 }

                                 if(var11 >= 0.0F) {
                                    var6 = var7 + var11;
                                    var9 = var6;
                                    var10 = var5;
                                 } else {
                                    var10 = var5 - var11;
                                    var9 = var7;
                                    var6 = var5;
                                 }
                              }

                              var26[var17 + 1] = var6 * var12;
                              var17 += 2;
                              ++var15;
                              var7 = var9;
                           }

                           var34.pointValuesToPixel(var26);

                           for(var15 = 0; var15 < var26.length; var15 += 2) {
                              int var19 = var15 / 2;
                              var6 = var29[var19];
                              boolean var32;
                              if((var6 != 0.0F || var5 != 0.0F || var7 <= 0.0F) && var6 >= 0.0F) {
                                 var32 = false;
                              } else {
                                 var32 = true;
                              }

                              var9 = var26[var15 + 1];
                              if(var32) {
                                 var6 = var2;
                              } else {
                                 var6 = var3;
                              }

                              var6 += var9;
                              if(!this.mViewPortHandler.isInBoundsRight(var8)) {
                                 break;
                              }

                              if(this.mViewPortHandler.isInBoundsY(var6) && this.mViewPortHandler.isInBoundsLeft(var8)) {
                                 if(var30.isDrawValuesEnabled()) {
                                    this.drawValue(var1, var30.getValueFormatter(), var29[var19], var28, var13, var8, var6, var18);
                                 }

                                 if(var28.getIcon() != null && var30.isDrawIconsEnabled()) {
                                    Drawable var31 = var28.getIcon();
                                    Utils.drawImage(var1, var31, (int)(var8 + var36.x), (int)(var6 + var36.y), var31.getIntrinsicWidth(), var31.getIntrinsicHeight());
                                 }
                              }
                           }
                        }

                        if(var35 == null) {
                           var15 = var16 + 4;
                        } else {
                           var15 = var16 + var35.length * 4;
                        }

                        ++var14;
                     }

                     var16 = var15;
                  }
               }

               var2 = var5;
               var20 = var21;
               MPPointF.recycleInstance(var40);
               var13 = var15;
            }

            ++var13;
            var22 = var23;
         }
      }

   }

   public void initBuffers() {
      BarData var4 = this.mChart.getBarData();
      this.mBarBuffers = new BarBuffer[var4.getDataSetCount()];

      for(int var1 = 0; var1 < this.mBarBuffers.length; ++var1) {
         IBarDataSet var5 = (IBarDataSet)var4.getDataSetByIndex(var1);
         BarBuffer[] var6 = this.mBarBuffers;
         int var3 = var5.getEntryCount();
         int var2;
         if(var5.isStacked()) {
            var2 = var5.getStackSize();
         } else {
            var2 = 1;
         }

         var6[var1] = new BarBuffer(var3 * 4 * var2, var4.getDataSetCount(), var5.isStacked());
      }

   }

   protected void prepareBarHighlight(float var1, float var2, float var3, float var4, Transformer var5) {
      this.mBarRect.set(var1 - var4, var2, var1 + var4, var3);
      var5.rectToPixelPhase(this.mBarRect, this.mAnimator.getPhaseY());
   }

   protected void setHighlightDrawPos(Highlight var1, RectF var2) {
      var1.setDraw(var2.centerX(), var2.top);
   }
}
