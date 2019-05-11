package com.github.mikephil.charting.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.renderer.scatter.IShapeRenderer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class TriangleShapeRenderer implements IShapeRenderer {

   protected Path mTrianglePathBuffer = new Path();


   public void renderShape(Canvas var1, IScatterDataSet var2, ViewPortHandler var3, float var4, float var5, Paint var6) {
      float var13 = var2.getScatterShapeSize();
      float var12 = var13 / 2.0F;
      float var9 = (var13 - Utils.convertDpToPixel(var2.getScatterShapeHoleRadius()) * 2.0F) / 2.0F;
      int var15 = var2.getScatterShapeHoleColor();
      var6.setStyle(Style.FILL);
      Path var16 = this.mTrianglePathBuffer;
      var16.reset();
      float var11 = var5 - var12;
      var16.moveTo(var4, var11);
      float var10 = var4 + var12;
      var5 += var12;
      var16.lineTo(var10, var5);
      var12 = var4 - var12;
      var16.lineTo(var12, var5);
      double var7 = (double)var13;
      if(var7 > 0.0D) {
         var16.lineTo(var4, var11);
         var13 = var12 + var9;
         float var14 = var5 - var9;
         var16.moveTo(var13, var14);
         var16.lineTo(var10 - var9, var14);
         var16.lineTo(var4, var11 + var9);
         var16.lineTo(var13, var14);
      }

      var16.close();
      var1.drawPath(var16, var6);
      var16.reset();
      if(var7 > 0.0D && var15 != 1122867) {
         var6.setColor(var15);
         var16.moveTo(var4, var11 + var9);
         var4 = var5 - var9;
         var16.lineTo(var10 - var9, var4);
         var16.lineTo(var12 + var9, var4);
         var16.close();
         var1.drawPath(var16, var6);
         var16.reset();
      }

   }
}
