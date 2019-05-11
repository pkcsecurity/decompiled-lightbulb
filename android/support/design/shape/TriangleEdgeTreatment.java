package android.support.design.shape;

import android.support.design.internal.Experimental;
import android.support.design.shape.EdgeTreatment;
import android.support.design.shape.ShapePath;

@Experimental("The shapes API is currently experimental and subject to change")
public class TriangleEdgeTreatment extends EdgeTreatment {

   private final boolean inside;
   private final float size;


   public TriangleEdgeTreatment(float var1, boolean var2) {
      this.size = var1;
      this.inside = var2;
   }

   public void getEdgePath(float var1, float var2, ShapePath var3) {
      float var5 = var1 / 2.0F;
      var3.lineTo(var5 - this.size * var2, 0.0F);
      float var4;
      if(this.inside) {
         var4 = this.size;
      } else {
         var4 = -this.size;
      }

      var3.lineTo(var5, var4 * var2);
      var3.lineTo(var5 + this.size * var2, 0.0F);
      var3.lineTo(var1, 0.0F);
   }
}
