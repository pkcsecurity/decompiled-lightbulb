package android.support.design.shape;

import android.support.design.internal.Experimental;
import android.support.design.shape.CornerTreatment;
import android.support.design.shape.ShapePath;

@Experimental("The shapes API is currently experimental and subject to change")
public class CutCornerTreatment extends CornerTreatment {

   private final float size;


   public CutCornerTreatment(float var1) {
      this.size = var1;
   }

   public void getCornerPath(float var1, float var2, ShapePath var3) {
      var3.reset(0.0F, this.size * var2);
      double var4 = (double)var1;
      double var6 = Math.sin(var4);
      double var8 = (double)this.size;
      double var10 = (double)var2;
      var3.lineTo((float)(var6 * var8 * var10), (float)(Math.cos(var4) * (double)this.size * var10));
   }
}
