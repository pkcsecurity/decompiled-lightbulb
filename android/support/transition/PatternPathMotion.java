package android.support.transition;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.transition.PathMotion;
import android.util.AttributeSet;

public class PatternPathMotion extends PathMotion {

   private Path mOriginalPatternPath;
   private final Path mPatternPath = new Path();
   private final Matrix mTempMatrix = new Matrix();


   public PatternPathMotion() {
      this.mPatternPath.lineTo(1.0F, 0.0F);
      this.mOriginalPatternPath = this.mPatternPath;
   }

   public PatternPathMotion(Context param1, AttributeSet param2) {
      // $FF: Couldn't be decompiled
   }

   public PatternPathMotion(Path var1) {
      this.setPatternPath(var1);
   }

   private static float distance(float var0, float var1) {
      return (float)Math.sqrt((double)(var0 * var0 + var1 * var1));
   }

   public Path getPath(float var1, float var2, float var3, float var4) {
      var3 -= var1;
      var4 -= var2;
      float var7 = distance(var3, var4);
      double var5 = Math.atan2((double)var4, (double)var3);
      this.mTempMatrix.setScale(var7, var7);
      this.mTempMatrix.postRotate((float)Math.toDegrees(var5));
      this.mTempMatrix.postTranslate(var1, var2);
      Path var8 = new Path();
      this.mPatternPath.transform(this.mTempMatrix, var8);
      return var8;
   }

   public Path getPatternPath() {
      return this.mOriginalPatternPath;
   }

   public void setPatternPath(Path var1) {
      PathMeasure var8 = new PathMeasure(var1, false);
      float var4 = var8.getLength();
      float[] var9 = new float[2];
      var8.getPosTan(var4, var9, (float[])null);
      float var6 = var9[0];
      var4 = var9[1];
      var8.getPosTan(0.0F, var9, (float[])null);
      float var7 = var9[0];
      float var5 = var9[1];
      if(var7 == var6 && var5 == var4) {
         throw new IllegalArgumentException("pattern must not end at the starting point");
      } else {
         this.mTempMatrix.setTranslate(-var7, -var5);
         var6 -= var7;
         var4 -= var5;
         var5 = 1.0F / distance(var6, var4);
         this.mTempMatrix.postScale(var5, var5);
         double var2 = Math.atan2((double)var4, (double)var6);
         this.mTempMatrix.postRotate((float)Math.toDegrees(-var2));
         var1.transform(this.mTempMatrix, this.mPatternPath);
         this.mOriginalPatternPath = var1;
      }
   }
}
