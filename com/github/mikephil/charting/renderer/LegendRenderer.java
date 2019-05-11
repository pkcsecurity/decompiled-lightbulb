package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.renderer.Renderer;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LegendRenderer extends Renderer {

   protected List<LegendEntry> computedEntries = new ArrayList(16);
   protected FontMetrics legendFontMetrics = new FontMetrics();
   protected Legend mLegend;
   protected Paint mLegendFormPaint;
   protected Paint mLegendLabelPaint;
   private Path mLineFormPath = new Path();


   public LegendRenderer(ViewPortHandler var1, Legend var2) {
      super(var1);
      this.mLegend = var2;
      this.mLegendLabelPaint = new Paint(1);
      this.mLegendLabelPaint.setTextSize(Utils.convertDpToPixel(9.0F));
      this.mLegendLabelPaint.setTextAlign(Align.LEFT);
      this.mLegendFormPaint = new Paint(1);
      this.mLegendFormPaint.setStyle(Style.FILL);
   }

   public void computeLegend(ChartData<?> var1) {
      ChartData var5 = var1;
      if(!this.mLegend.isLegendCustom()) {
         this.computedEntries.clear();

         for(int var2 = 0; var2 < var1.getDataSetCount(); ++var2) {
            IDataSet var6 = var5.getDataSetByIndex(var2);
            List var7 = var6.getColors();
            int var4 = var6.getEntryCount();
            int var3;
            if(var6 instanceof IBarDataSet) {
               IBarDataSet var8 = (IBarDataSet)var6;
               if(var8.isStacked()) {
                  String[] var9 = var8.getStackLabels();

                  for(var3 = 0; var3 < var7.size() && var3 < var8.getStackSize(); ++var3) {
                     this.computedEntries.add(new LegendEntry(var9[var3 % var9.length], var6.getForm(), var6.getFormSize(), var6.getFormLineWidth(), var6.getFormLineDashEffect(), ((Integer)var7.get(var3)).intValue()));
                  }

                  if(var8.getLabel() != null) {
                     this.computedEntries.add(new LegendEntry(var6.getLabel(), Legend.LegendForm.NONE, Float.NaN, Float.NaN, (DashPathEffect)null, 1122867));
                  }
                  continue;
               }
            }

            if(var6 instanceof IPieDataSet) {
               IPieDataSet var11 = (IPieDataSet)var6;

               for(var3 = 0; var3 < var7.size() && var3 < var4; ++var3) {
                  this.computedEntries.add(new LegendEntry(((PieEntry)var11.getEntryForIndex(var3)).getLabel(), var6.getForm(), var6.getFormSize(), var6.getFormLineWidth(), var6.getFormLineDashEffect(), ((Integer)var7.get(var3)).intValue()));
               }

               if(var11.getLabel() != null) {
                  this.computedEntries.add(new LegendEntry(var6.getLabel(), Legend.LegendForm.NONE, Float.NaN, Float.NaN, (DashPathEffect)null, 1122867));
               }
            } else {
               label103: {
                  if(var6 instanceof ICandleDataSet) {
                     ICandleDataSet var12 = (ICandleDataSet)var6;
                     if(var12.getDecreasingColor() != 1122867) {
                        var3 = var12.getDecreasingColor();
                        var4 = var12.getIncreasingColor();
                        this.computedEntries.add(new LegendEntry((String)null, var6.getForm(), var6.getFormSize(), var6.getFormLineWidth(), var6.getFormLineDashEffect(), var3));
                        this.computedEntries.add(new LegendEntry(var6.getLabel(), var6.getForm(), var6.getFormSize(), var6.getFormLineWidth(), var6.getFormLineDashEffect(), var4));
                        break label103;
                     }
                  }

                  for(var3 = 0; var3 < var7.size() && var3 < var4; ++var3) {
                     String var13;
                     if(var3 < var7.size() - 1 && var3 < var4 - 1) {
                        var13 = null;
                     } else {
                        var13 = var1.getDataSetByIndex(var2).getLabel();
                     }

                     this.computedEntries.add(new LegendEntry(var13, var6.getForm(), var6.getFormSize(), var6.getFormLineWidth(), var6.getFormLineDashEffect(), ((Integer)var7.get(var3)).intValue()));
                  }
               }
            }

            var5 = var1;
         }

         if(this.mLegend.getExtraEntries() != null) {
            Collections.addAll(this.computedEntries, this.mLegend.getExtraEntries());
         }

         this.mLegend.setEntries(this.computedEntries);
      }

      Typeface var10 = this.mLegend.getTypeface();
      if(var10 != null) {
         this.mLegendLabelPaint.setTypeface(var10);
      }

      this.mLegendLabelPaint.setTextSize(this.mLegend.getTextSize());
      this.mLegendLabelPaint.setColor(this.mLegend.getTextColor());
      this.mLegend.calculateDimensions(this.mLegendLabelPaint, this.mViewPortHandler);
   }

   protected void drawForm(Canvas var1, float var2, float var3, LegendEntry var4, Legend var5) {
      if(var4.formColor != 1122868 && var4.formColor != 1122867) {
         if(var4.formColor != 0) {
            int var8 = var1.save();
            Legend.LegendForm var10 = var4.form;
            Legend.LegendForm var9 = var10;
            if(var10 == Legend.LegendForm.DEFAULT) {
               var9 = var5.getForm();
            }

            this.mLegendFormPaint.setColor(var4.formColor);
            float var6;
            if(Float.isNaN(var4.formSize)) {
               var6 = var5.getFormSize();
            } else {
               var6 = var4.formSize;
            }

            float var7 = Utils.convertDpToPixel(var6);
            var6 = var7 / 2.0F;
            switch(null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm[var9.ordinal()]) {
            case 1:
            case 2:
            default:
               break;
            case 3:
            case 4:
               this.mLegendFormPaint.setStyle(Style.FILL);
               var1.drawCircle(var2 + var6, var3, var6, this.mLegendFormPaint);
               break;
            case 5:
               this.mLegendFormPaint.setStyle(Style.FILL);
               var1.drawRect(var2, var3 - var6, var2 + var7, var3 + var6, this.mLegendFormPaint);
               break;
            case 6:
               if(Float.isNaN(var4.formLineWidth)) {
                  var6 = var5.getFormLineWidth();
               } else {
                  var6 = var4.formLineWidth;
               }

               var6 = Utils.convertDpToPixel(var6);
               DashPathEffect var11;
               if(var4.formLineDashEffect == null) {
                  var11 = var5.getFormLineDashEffect();
               } else {
                  var11 = var4.formLineDashEffect;
               }

               this.mLegendFormPaint.setStyle(Style.STROKE);
               this.mLegendFormPaint.setStrokeWidth(var6);
               this.mLegendFormPaint.setPathEffect(var11);
               this.mLineFormPath.reset();
               this.mLineFormPath.moveTo(var2, var3);
               this.mLineFormPath.lineTo(var2 + var7, var3);
               var1.drawPath(this.mLineFormPath, this.mLegendFormPaint);
            }

            var1.restoreToCount(var8);
         }
      }
   }

   protected void drawLabel(Canvas var1, float var2, float var3, String var4) {
      var1.drawText(var4, var2, var3, this.mLegendLabelPaint);
   }

   public Paint getFormPaint() {
      return this.mLegendFormPaint;
   }

   public Paint getLabelPaint() {
      return this.mLegendLabelPaint;
   }

   public void renderLegend(Canvas var1) {
      if(this.mLegend.isEnabled()) {
         Typeface var24 = this.mLegend.getTypeface();
         if(var24 != null) {
            this.mLegendLabelPaint.setTypeface(var24);
         }

         float var6;
         float var7;
         float var8;
         float var9;
         float var10;
         float var11;
         float var12;
         float var13;
         float var16;
         float var17;
         float var18;
         LegendEntry[] var25;
         Legend.LegendDirection var27;
         Legend.LegendHorizontalAlignment var28;
         Legend.LegendVerticalAlignment var30;
         Legend.LegendOrientation var33;
         label231: {
            this.mLegendLabelPaint.setTextSize(this.mLegend.getTextSize());
            this.mLegendLabelPaint.setColor(this.mLegend.getTextColor());
            var18 = Utils.getLineHeight(this.mLegendLabelPaint, this.legendFontMetrics);
            var16 = Utils.getLineSpacing(this.mLegendLabelPaint, this.legendFontMetrics) + Utils.convertDpToPixel(this.mLegend.getYEntrySpace());
            var17 = var18 - (float)Utils.calcTextHeight(this.mLegendLabelPaint, "ABC") / 2.0F;
            var25 = this.mLegend.getEntries();
            var11 = Utils.convertDpToPixel(this.mLegend.getFormToTextSpace());
            var10 = Utils.convertDpToPixel(this.mLegend.getXEntrySpace());
            var33 = this.mLegend.getOrientation();
            var28 = this.mLegend.getHorizontalAlignment();
            var30 = this.mLegend.getVerticalAlignment();
            var27 = this.mLegend.getDirection();
            var13 = Utils.convertDpToPixel(this.mLegend.getFormSize());
            var9 = Utils.convertDpToPixel(this.mLegend.getStackSpace());
            var12 = this.mLegend.getYOffset();
            var7 = this.mLegend.getXOffset();
            switch(null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[var28.ordinal()]) {
            case 1:
               if(var33 != Legend.LegendOrientation.VERTICAL) {
                  var7 += this.mViewPortHandler.contentLeft();
               }

               var6 = var7;
               if(var27 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                  var6 = var7 + this.mLegend.mNeededWidth;
               }
               break label231;
            case 2:
               if(var33 == Legend.LegendOrientation.VERTICAL) {
                  var6 = this.mViewPortHandler.getChartWidth() - var7;
               } else {
                  var6 = this.mViewPortHandler.contentRight() - var7;
               }

               var7 = var6;
               if(var27 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                  var6 -= this.mLegend.mNeededWidth;
                  break label231;
               }
               break;
            case 3:
               if(var33 == Legend.LegendOrientation.VERTICAL) {
                  var6 = this.mViewPortHandler.getChartWidth() / 2.0F;
               } else {
                  var6 = this.mViewPortHandler.contentLeft() + this.mViewPortHandler.contentWidth() / 2.0F;
               }

               if(var27 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                  var8 = var7;
               } else {
                  var8 = -var7;
               }

               var6 += var8;
               if(var33 == Legend.LegendOrientation.VERTICAL) {
                  double var4 = (double)var6;
                  double var2;
                  if(var27 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                     var2 = (double)(-this.mLegend.mNeededWidth) / 2.0D + (double)var7;
                  } else {
                     var2 = (double)this.mLegend.mNeededWidth / 2.0D - (double)var7;
                  }

                  var6 = (float)(var4 + var2);
                  break label231;
               }

               var7 = var6;
               break;
            default:
               var6 = 0.0F;
               break label231;
            }

            var6 = var7;
         }

         var8 = var11;
         float var14;
         int var19;
         switch(null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[var33.ordinal()]) {
         case 1:
            var11 = var11;
            List var29 = this.mLegend.getCalculatedLineSizes();
            List var35 = this.mLegend.getCalculatedLabelSizes();
            List var36 = this.mLegend.getCalculatedLabelBreakPoints();
            var7 = var12;
            switch(null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[var30.ordinal()]) {
            case 1:
               break;
            case 2:
               var7 = this.mViewPortHandler.getChartHeight() - var12 - this.mLegend.mNeededHeight;
               break;
            case 3:
               var7 = var12 + (this.mViewPortHandler.getChartHeight() - this.mLegend.mNeededHeight) / 2.0F;
               break;
            default:
               var7 = 0.0F;
            }

            int var32 = var25.length;
            float var15 = var7;
            var12 = var6;
            var19 = 0;
            int var31 = 0;
            var8 = var10;
            var14 = var17;
            var7 = var6;

            for(var6 = var15; var19 < var32; var6 = var10) {
               LegendEntry var37 = var25[var19];
               boolean var22;
               if(var37.form != Legend.LegendForm.NONE) {
                  var22 = true;
               } else {
                  var22 = false;
               }

               if(Float.isNaN(var37.formSize)) {
                  var15 = var13;
               } else {
                  var15 = Utils.convertDpToPixel(var37.formSize);
               }

               if(var19 < var36.size() && ((Boolean)var36.get(var19)).booleanValue()) {
                  var10 = var6 + var18 + var16;
                  var6 = var7;
               } else {
                  var10 = var6;
                  var6 = var12;
               }

               if(var6 == var7 && var28 == Legend.LegendHorizontalAlignment.CENTER && var31 < var29.size()) {
                  if(var27 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                     var12 = ((FSize)var29.get(var31)).width;
                  } else {
                     var12 = -((FSize)var29.get(var31)).width;
                  }

                  var6 += var12 / 2.0F;
                  ++var31;
               }

               boolean var23;
               if(var37.label == null) {
                  var23 = true;
               } else {
                  var23 = false;
               }

               if(var22) {
                  var12 = var6;
                  if(var27 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                     var12 = var6 - var15;
                  }

                  this.drawForm(var1, var12, var10 + var14, var37, this.mLegend);
                  var6 = var12;
                  if(var27 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                     var6 = var12 + var15;
                  }
               }

               if(!var23) {
                  var12 = var6;
                  if(var22) {
                     if(var27 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                        var12 = -var11;
                     } else {
                        var12 = var11;
                     }

                     var12 += var6;
                  }

                  var6 = var12;
                  if(var27 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                     var6 = var12 - ((FSize)var35.get(var19)).width;
                  }

                  var12 = var6;
                  this.drawLabel(var1, var6, var10 + var18, var37.label);
                  var6 = var6;
                  if(var27 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                     var6 = var12 + ((FSize)var35.get(var19)).width;
                  }

                  if(var27 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                     var12 = -var8;
                  } else {
                     var12 = var8;
                  }

                  var12 += var6;
               } else {
                  if(var27 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                     var12 = -var9;
                  } else {
                     var12 = var9;
                  }

                  var12 += var6;
               }

               ++var19;
            }

            return;
         case 2:
            switch(null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[var30.ordinal()]) {
            case 1:
               if(var28 == Legend.LegendHorizontalAlignment.CENTER) {
                  var7 = 0.0F;
               } else {
                  var7 = this.mViewPortHandler.contentTop();
               }

               var7 += var12;
               break;
            case 2:
               if(var28 == Legend.LegendHorizontalAlignment.CENTER) {
                  var7 = this.mViewPortHandler.getChartHeight();
               } else {
                  var7 = this.mViewPortHandler.contentBottom();
               }

               var7 -= this.mLegend.mNeededHeight + var12;
               break;
            case 3:
               var7 = this.mViewPortHandler.getChartHeight() / 2.0F - this.mLegend.mNeededHeight / 2.0F + this.mLegend.getYOffset();
               break;
            default:
               var7 = 0.0F;
            }

            var10 = var7;
            var19 = 0;
            var12 = 0.0F;
            boolean var20 = false;
            var7 = var9;
            var9 = var10;

            for(Legend.LegendDirection var34 = var27; var19 < var25.length; var7 = var11) {
               LegendEntry var26 = var25[var19];
               boolean var21;
               if(var26.form != Legend.LegendForm.NONE) {
                  var21 = true;
               } else {
                  var21 = false;
               }

               if(Float.isNaN(var26.formSize)) {
                  var14 = var13;
               } else {
                  var14 = Utils.convertDpToPixel(var26.formSize);
               }

               if(var21) {
                  if(var34 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                     var10 = var6 + var12;
                  } else {
                     var10 = var6 - (var14 - var12);
                  }

                  var11 = var10;
                  this.drawForm(var1, var10, var9 + var17, var26, this.mLegend);
                  var10 = var10;
                  if(var34 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                     var10 = var11 + var14;
                  }
               } else {
                  var10 = var6;
               }

               var11 = var7;
               if(var26.label != null) {
                  if(var21 && !var20) {
                     if(var34 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                        var7 = var8;
                     } else {
                        var7 = -var8;
                     }

                     var7 += var10;
                  } else if(var20) {
                     var7 = var6;
                  } else {
                     var7 = var10;
                  }

                  var10 = var7;
                  if(var34 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                     var10 = var7 - (float)Utils.calcTextWidth(this.mLegendLabelPaint, var26.label);
                  }

                  if(!var20) {
                     this.drawLabel(var1, var10, var9 + var18, var26.label);
                  } else {
                     var9 += var18 + var16;
                     this.drawLabel(var1, var10, var9 + var18, var26.label);
                  }

                  var9 += var18 + var16;
                  var7 = 0.0F;
               } else {
                  var7 = var12 + var14 + var7;
                  var20 = true;
               }

               ++var19;
               var12 = var7;
            }

            return;
         default:
         }
      }
   }
}
