package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.buffer.HorizontalBarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class HorizontalBarChartRenderer extends BarChartRenderer {

   private RectF mBarShadowRectBuffer = new RectF();


   public HorizontalBarChartRenderer(BarDataProvider var1, ChartAnimator var2, ViewPortHandler var3) {
      super(var1, var2, var3);
      this.mValuePaint.setTextAlign(Align.LEFT);
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
      int var9;
      int var12;
      if(this.mChart.isDrawBarShadowEnabled()) {
         this.mShadowPaint.setColor(var2.getBarShadowColor());
         float var6 = this.mChart.getBarData().getBarWidth() / 2.0F;
         var12 = Math.min((int)Math.ceil((double)((float)var2.getEntryCount() * var4)), var2.getEntryCount());

         for(var9 = 0; var9 < var12; ++var9) {
            float var7 = ((BarEntry)var2.getEntryForIndex(var9)).getX();
            this.mBarShadowRectBuffer.top = var7 - var6;
            this.mBarShadowRectBuffer.bottom = var7 + var6;
            var14.rectValueToPixel(this.mBarShadowRectBuffer);
            if(this.mViewPortHandler.isInBoundsTop(this.mBarShadowRectBuffer.bottom)) {
               if(!this.mViewPortHandler.isInBoundsBottom(this.mBarShadowRectBuffer.top)) {
                  break;
               }

               this.mBarShadowRectBuffer.left = this.mViewPortHandler.contentLeft();
               this.mBarShadowRectBuffer.right = this.mViewPortHandler.contentRight();
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
         int var17 = var9 + 3;
         if(!var19.isInBoundsTop(var15[var17])) {
            return;
         }

         var19 = this.mViewPortHandler;
         var15 = var13.buffer;
         int var18 = var9 + 1;
         if(var19.isInBoundsBottom(var15[var18])) {
            if(!var16) {
               this.mRenderPaint.setColor(var2.getColor(var9 / 4));
            }

            var4 = var13.buffer[var9];
            var5 = var13.buffer[var18];
            float[] var20 = var13.buffer;
            var12 = var9 + 2;
            var1.drawRect(var4, var5, var20[var12], var13.buffer[var17], this.mRenderPaint);
            if(var8) {
               var1.drawRect(var13.buffer[var9], var13.buffer[var18], var13.buffer[var12], var13.buffer[var17], this.mBarBorderPaint);
            }
         }
      }

   }

   protected void drawValue(Canvas var1, String var2, float var3, float var4, int var5) {
      this.mValuePaint.setColor(var5);
      var1.drawText(var2, var3, var4, this.mValuePaint);
   }

   public void drawValues(Canvas var1) {
      if(this.isDrawingValuesAllowed(this.mChart)) {
         List var23 = this.mChart.getBarData().getDataSets();
         float var2 = Utils.convertDpToPixel(5.0F);
         boolean var20 = this.mChart.isDrawValueAboveBarEnabled();

         for(int var15 = 0; var15 < this.mChart.getBarData().getDataSetCount(); ++var15) {
            IBarDataSet var31 = (IBarDataSet)var23.get(var15);
            if(this.shouldDrawValues(var31)) {
               boolean var22 = this.mChart.isInverted(var31.getAxisDependency());
               this.applyValueTextStyle(var31);
               float var10 = (float)Utils.calcTextHeight(this.mValuePaint, "10") / 2.0F;
               IValueFormatter var29 = var31.getValueFormatter();
               BarBuffer var28 = this.mBarBuffers[var15];
               float var11 = this.mAnimator.getPhaseY();
               MPPointF var27 = MPPointF.getInstance(var31.getIconsOffset());
               var27.x = Utils.convertDpToPixel(var27.x);
               var27.y = Utils.convertDpToPixel(var27.y);
               float var3;
               float var4;
               float var5;
               float var6;
               float var7;
               float var8;
               float var9;
               int var14;
               int var16;
               boolean var21;
               MPPointF var40;
               if(!var31.isStacked()) {
                  var14 = 0;
                  var7 = var10;
                  var21 = var22;
                  IValueFormatter var24 = var29;
                  BarBuffer var26 = var28;

                  MPPointF var35;
                  for(var35 = var27; (float)var14 < (float)var26.buffer.length * this.mAnimator.getPhaseX(); var14 += 4) {
                     float[] var42 = var26.buffer;
                     var16 = var14 + 1;
                     var8 = (var42[var16] + var26.buffer[var14 + 3]) / 2.0F;
                     if(!this.mViewPortHandler.isInBoundsTop(var26.buffer[var16])) {
                        break;
                     }

                     if(this.mViewPortHandler.isInBoundsX(var26.buffer[var14]) && this.mViewPortHandler.isInBoundsBottom(var26.buffer[var16])) {
                        BarEntry var49 = (BarEntry)var31.getEntryForIndex(var14 / 4);
                        var9 = var49.getY();
                        String var44 = var24.getFormattedValue(var9, var49, var15, this.mViewPortHandler);
                        var10 = (float)Utils.calcTextWidth(this.mValuePaint, var44);
                        if(var20) {
                           var4 = var2;
                        } else {
                           var4 = -(var10 + var2);
                        }

                        if(var20) {
                           var5 = -(var10 + var2);
                        } else {
                           var5 = var2;
                        }

                        var6 = var4;
                        var3 = var5;
                        if(var21) {
                           var6 = -var4 - var10;
                           var3 = -var5 - var10;
                        }

                        var4 = var6;
                        if(var31.isDrawValuesEnabled()) {
                           var6 = var26.buffer[var14 + 2];
                           if(var9 >= 0.0F) {
                              var5 = var4;
                           } else {
                              var5 = var3;
                           }

                           this.drawValue(var1, var44, var5 + var6, var8 + var7, var31.getValueTextColor(var14 / 2));
                        }

                        IValueFormatter var46 = var24;
                        var24 = var24;
                        if(var49.getIcon() != null) {
                           var24 = var46;
                           if(var31.isDrawIconsEnabled()) {
                              Drawable var37 = var49.getIcon();
                              var5 = var26.buffer[var14 + 2];
                              if(var9 < 0.0F) {
                                 var4 = var3;
                              }

                              var3 = var35.x;
                              var6 = var35.y;
                              Utils.drawImage(var1, var37, (int)(var5 + var4 + var3), (int)(var8 + var6), var37.getIntrinsicWidth(), var37.getIntrinsicHeight());
                              var24 = var46;
                           }
                        }
                     }
                  }

                  var40 = var35;
                  var3 = var2;
                  var21 = var20;
                  var23 = var23;
               } else {
                  List var39 = var23;
                  MPPointF var38 = var27;
                  Transformer var32 = this.mChart.getTransformer(var31.getAxisDependency());
                  var16 = 0;
                  var14 = 0;

                  while(true) {
                     var40 = var38;
                     var3 = var2;
                     var21 = var20;
                     var23 = var39;
                     if((float)var16 >= (float)var31.getEntryCount() * this.mAnimator.getPhaseX()) {
                        break;
                     }

                     float[] var36;
                     label205: {
                        BarEntry var47 = (BarEntry)var31.getEntryForIndex(var16);
                        int var19 = var31.getValueTextColor(var16);
                        var36 = var47.getYVals();
                        int var17;
                        float[] var30;
                        if(var36 == null) {
                           ViewPortHandler var41 = this.mViewPortHandler;
                           var30 = var28.buffer;
                           var17 = var14 + 1;
                           if(!var41.isInBoundsTop(var30[var17])) {
                              var40 = var38;
                              var3 = var2;
                              var21 = var20;
                              var23 = var39;
                              break;
                           }

                           if(!this.mViewPortHandler.isInBoundsX(var28.buffer[var14]) || !this.mViewPortHandler.isInBoundsBottom(var28.buffer[var17])) {
                              continue;
                           }

                           String var43 = var29.getFormattedValue(var47.getY(), var47, var15, this.mViewPortHandler);
                           var7 = (float)Utils.calcTextWidth(this.mValuePaint, var43);
                           if(var20) {
                              var4 = var2;
                           } else {
                              var4 = -(var7 + var2);
                           }

                           if(var20) {
                              var5 = -(var7 + var2);
                           } else {
                              var5 = var2;
                           }

                           var6 = var4;
                           var3 = var5;
                           if(var22) {
                              var6 = -var4 - var7;
                              var3 = -var5 - var7;
                           }

                           var4 = var6;
                           if(var31.isDrawValuesEnabled()) {
                              var6 = var28.buffer[var14 + 2];
                              if(var47.getY() >= 0.0F) {
                                 var5 = var4;
                              } else {
                                 var5 = var3;
                              }

                              this.drawValue(var1, var43, var6 + var5, var28.buffer[var17] + var10, var19);
                           }

                           var21 = var20;
                           if(var47.getIcon() != null) {
                              var21 = var20;
                              if(var31.isDrawIconsEnabled()) {
                                 Drawable var45 = var47.getIcon();
                                 var5 = var28.buffer[var14 + 2];
                                 if(var47.getY() < 0.0F) {
                                    var4 = var3;
                                 }

                                 var3 = var28.buffer[var17];
                                 var6 = var38.x;
                                 var7 = var38.y;
                                 Utils.drawImage(var1, var45, (int)(var5 + var4 + var6), (int)(var3 + var7), var45.getIntrinsicWidth(), var45.getIntrinsicHeight());
                                 var21 = var20;
                              }
                           }
                        } else {
                           var3 = var2;
                           var30 = var36;
                           float[] var48 = new float[var36.length * 2];
                           var4 = -var47.getNegativeSum();
                           int var18 = 0;
                           var17 = 0;

                           for(var9 = 0.0F; var18 < var48.length; var4 = var7) {
                              label189: {
                                 var8 = var30[var17];
                                 if(var8 == 0.0F) {
                                    var5 = var8;
                                    var6 = var9;
                                    var7 = var4;
                                    if(var9 == 0.0F) {
                                       break label189;
                                    }

                                    if(var4 == 0.0F) {
                                       var5 = var8;
                                       var6 = var9;
                                       var7 = var4;
                                       break label189;
                                    }
                                 }

                                 if(var8 >= 0.0F) {
                                    var5 = var9 + var8;
                                    var6 = var5;
                                    var7 = var4;
                                 } else {
                                    var7 = var4 - var8;
                                    var6 = var9;
                                    var5 = var4;
                                 }
                              }

                              var48[var18] = var5 * var11;
                              var18 += 2;
                              ++var17;
                              var9 = var6;
                           }

                           var32.pointValuesToPixel(var48);
                           var17 = 0;

                           while(true) {
                              var21 = var20;
                              if(var17 >= var48.length) {
                                 break;
                              }

                              float var12 = var30[var17 / 2];
                              String var33 = var29.getFormattedValue(var12, var47, var15, this.mViewPortHandler);
                              float var13 = (float)Utils.calcTextWidth(this.mValuePaint, var33);
                              if(var20) {
                                 var6 = var3;
                              } else {
                                 var6 = -(var13 + var3);
                              }

                              if(var20) {
                                 var7 = -(var13 + var3);
                              } else {
                                 var7 = var3;
                              }

                              var8 = var6;
                              var5 = var7;
                              if(var22) {
                                 var8 = -var6 - var13;
                                 var5 = -var7 - var13;
                              }

                              boolean var34;
                              if((var12 != 0.0F || var4 != 0.0F || var9 <= 0.0F) && var12 >= 0.0F) {
                                 var34 = false;
                              } else {
                                 var34 = true;
                              }

                              var6 = var48[var17];
                              if(var34) {
                                 var8 = var5;
                              }

                              var5 = var6 + var8;
                              var6 = (var28.buffer[var14 + 1] + var28.buffer[var14 + 3]) / 2.0F;
                              if(!this.mViewPortHandler.isInBoundsTop(var6)) {
                                 break label205;
                              }

                              if(this.mViewPortHandler.isInBoundsX(var5) && this.mViewPortHandler.isInBoundsBottom(var6)) {
                                 if(var31.isDrawValuesEnabled()) {
                                    this.drawValue(var1, var33, var5, var6 + var10, var19);
                                 }

                                 if(var47.getIcon() != null && var31.isDrawIconsEnabled()) {
                                    Drawable var50 = var47.getIcon();
                                    Utils.drawImage(var1, var50, (int)(var5 + var38.x), (int)(var6 + var38.y), var50.getIntrinsicWidth(), var50.getIntrinsicHeight());
                                 }
                              }

                              var17 += 2;
                           }
                        }

                        var20 = var21;
                     }

                     if(var36 == null) {
                        var14 += 4;
                     } else {
                        var14 += var36.length * 4;
                     }

                     ++var16;
                  }
               }

               var2 = var3;
               var20 = var21;
               MPPointF.recycleInstance(var40);
            }
         }
      }

   }

   public void initBuffers() {
      BarData var4 = this.mChart.getBarData();
      this.mBarBuffers = new HorizontalBarBuffer[var4.getDataSetCount()];

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

         var6[var1] = new HorizontalBarBuffer(var3 * 4 * var2, var4.getDataSetCount(), var5.isStacked());
      }

   }

   protected boolean isDrawingValuesAllowed(ChartInterface var1) {
      return (float)var1.getData().getEntryCount() < (float)var1.getMaxVisibleCount() * this.mViewPortHandler.getScaleY();
   }

   protected void prepareBarHighlight(float var1, float var2, float var3, float var4, Transformer var5) {
      this.mBarRect.set(var2, var1 - var4, var3, var1 + var4);
      var5.rectToPixelPhaseHorizontal(this.mBarRect, this.mAnimator.getPhaseY());
   }

   protected void setHighlightDrawPos(Highlight var1, RectF var2) {
      var1.setDraw(var2.centerY(), var2.right);
   }
}
