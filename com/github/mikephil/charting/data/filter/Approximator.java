package com.github.mikephil.charting.data.filter;

import android.annotation.TargetApi;
import java.util.Arrays;

public class Approximator {

   float[] concat(float[] ... var1) {
      int var4 = var1.length;
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var4; ++var2) {
         var3 += var1[var2].length;
      }

      float[] var7 = new float[var3];
      int var5 = var1.length;
      var3 = 0;

      for(var2 = 0; var3 < var5; ++var3) {
         float[] var8 = var1[var3];
         int var6 = var8.length;

         for(var4 = 0; var4 < var6; ++var4) {
            var7[var2] = var8[var4];
            ++var2;
         }
      }

      return var7;
   }

   @TargetApi(9)
   public float[] reduceWithDouglasPeucker(float[] var1, float var2) {
      Approximator.Line var8 = new Approximator.Line(var1[0], var1[1], var1[var1.length - 2], var1[var1.length - 1]);
      int var6 = 2;
      float var3 = 0.0F;

      float var4;
      int var7;
      for(var7 = 0; var6 < var1.length - 2; var3 = var4) {
         float var5 = var8.distance(var1[var6], var1[var6 + 1]);
         var4 = var3;
         if(var5 > var3) {
            var7 = var6;
            var4 = var5;
         }

         var6 += 2;
      }

      if(var3 > var2) {
         float[] var9 = this.reduceWithDouglasPeucker(Arrays.copyOfRange(var1, 0, var7 + 2), var2);
         var1 = this.reduceWithDouglasPeucker(Arrays.copyOfRange(var1, var7, var1.length), var2);
         return this.concat(new float[][]{var9, Arrays.copyOfRange(var1, 2, var1.length)});
      } else {
         return var8.getPoints();
      }
   }

   class Line {

      private float dx;
      private float dy;
      private float exsy;
      private float length;
      private float[] points;
      private float sxey;


      public Line(float var2, float var3, float var4, float var5) {
         this.dx = var2 - var4;
         this.dy = var3 - var5;
         this.sxey = var2 * var5;
         this.exsy = var4 * var3;
         this.length = (float)Math.sqrt((double)(this.dx * this.dx + this.dy * this.dy));
         this.points = new float[]{var2, var3, var4, var5};
      }

      public float distance(float var1, float var2) {
         return Math.abs(this.dy * var1 - this.dx * var2 + this.sxey - this.exsy) / this.length;
      }

      public float[] getPoints() {
         return this.points;
      }
   }
}
