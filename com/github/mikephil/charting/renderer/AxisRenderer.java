package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.renderer.Renderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class AxisRenderer extends Renderer {

   protected AxisBase mAxis;
   protected Paint mAxisLabelPaint;
   protected Paint mAxisLinePaint;
   protected Paint mGridPaint;
   protected Paint mLimitLinePaint;
   protected Transformer mTrans;


   public AxisRenderer(ViewPortHandler var1, Transformer var2, AxisBase var3) {
      super(var1);
      this.mTrans = var2;
      this.mAxis = var3;
      if(this.mViewPortHandler != null) {
         this.mAxisLabelPaint = new Paint(1);
         this.mGridPaint = new Paint();
         this.mGridPaint.setColor(-7829368);
         this.mGridPaint.setStrokeWidth(1.0F);
         this.mGridPaint.setStyle(Style.STROKE);
         this.mGridPaint.setAlpha(90);
         this.mAxisLinePaint = new Paint();
         this.mAxisLinePaint.setColor(-16777216);
         this.mAxisLinePaint.setStrokeWidth(1.0F);
         this.mAxisLinePaint.setStyle(Style.STROKE);
         this.mLimitLinePaint = new Paint(1);
         this.mLimitLinePaint.setStyle(Style.STROKE);
      }

   }

   public void computeAxis(float var1, float var2, boolean var3) {
      float var5 = var1;
      float var4 = var2;
      if(this.mViewPortHandler != null) {
         var5 = var1;
         var4 = var2;
         if(this.mViewPortHandler.contentWidth() > 10.0F) {
            var5 = var1;
            var4 = var2;
            if(!this.mViewPortHandler.isFullyZoomedOutY()) {
               MPPointD var6 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
               MPPointD var7 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom());
               if(!var3) {
                  var2 = (float)var7.y;
                  var1 = (float)var6.y;
               } else {
                  var2 = (float)var6.y;
                  var1 = (float)var7.y;
               }

               MPPointD.recycleInstance(var6);
               MPPointD.recycleInstance(var7);
               var4 = var1;
               var5 = var2;
            }
         }
      }

      this.computeAxisValues(var5, var4);
   }

   protected void computeAxisValues(float var1, float var2) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
   }

   public Paint getPaintAxisLabels() {
      return this.mAxisLabelPaint;
   }

   public Paint getPaintAxisLine() {
      return this.mAxisLinePaint;
   }

   public Paint getPaintGrid() {
      return this.mGridPaint;
   }

   public Transformer getTransformer() {
      return this.mTrans;
   }

   public abstract void renderAxisLabels(Canvas var1);

   public abstract void renderAxisLine(Canvas var1);

   public abstract void renderGridLines(Canvas var1);

   public abstract void renderLimitLines(Canvas var1);
}
