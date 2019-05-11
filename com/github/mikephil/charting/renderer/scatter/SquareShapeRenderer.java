package com.github.mikephil.charting.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.renderer.scatter.IShapeRenderer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class SquareShapeRenderer implements IShapeRenderer {

   public void renderShape(Canvas var1, IScatterDataSet var2, ViewPortHandler var3, float var4, float var5, Paint var6) {
      float var9 = var2.getScatterShapeSize();
      float var10 = var9 / 2.0F;
      float var8 = Utils.convertDpToPixel(var2.getScatterShapeHoleRadius());
      float var11 = (var9 - var8 * 2.0F) / 2.0F;
      float var7 = var11 / 2.0F;
      int var12 = var2.getScatterShapeHoleColor();
      if((double)var9 > 0.0D) {
         var6.setStyle(Style.STROKE);
         var6.setStrokeWidth(var11);
         var9 = var4 - var8;
         var10 = var5 - var8;
         var4 += var8;
         var5 += var8;
         var1.drawRect(var9 - var7, var10 - var7, var4 + var7, var5 + var7, var6);
         if(var12 != 1122867) {
            var6.setStyle(Style.FILL);
            var6.setColor(var12);
            var1.drawRect(var9, var10, var4, var5, var6);
            return;
         }
      } else {
         var6.setStyle(Style.FILL);
         var1.drawRect(var4 - var10, var5 - var10, var4 + var10, var5 + var10, var6);
      }

   }
}
