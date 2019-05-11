package android.support.design.shape;

import android.support.design.internal.Experimental;
import android.support.design.shape.CornerTreatment;
import android.support.design.shape.ShapePath;

@Experimental("The shapes API is currently experimental and subject to change")
public class RoundedCornerTreatment extends CornerTreatment {

   private final float radius;


   public RoundedCornerTreatment(float var1) {
      this.radius = var1;
   }

   public void getCornerPath(float var1, float var2, ShapePath var3) {
      var3.reset(0.0F, this.radius * var2);
      var3.addArc(0.0F, 0.0F, this.radius * 2.0F * var2, this.radius * 2.0F * var2, var1 + 180.0F, 90.0F);
   }
}
