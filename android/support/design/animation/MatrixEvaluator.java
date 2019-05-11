package android.support.design.animation;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;

public class MatrixEvaluator implements TypeEvaluator<Matrix> {

   private final float[] tempEndValues = new float[9];
   private final Matrix tempMatrix = new Matrix();
   private final float[] tempStartValues = new float[9];


   public Matrix evaluate(float var1, Matrix var2, Matrix var3) {
      var2.getValues(this.tempStartValues);
      var3.getValues(this.tempEndValues);

      for(int var6 = 0; var6 < 9; ++var6) {
         float var4 = this.tempEndValues[var6];
         float var5 = this.tempStartValues[var6];
         this.tempEndValues[var6] = this.tempStartValues[var6] + (var4 - var5) * var1;
      }

      this.tempMatrix.setValues(this.tempEndValues);
      return this.tempMatrix;
   }
}
