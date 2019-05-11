package android.support.design.bottomappbar;

import android.support.design.shape.EdgeTreatment;
import android.support.design.shape.ShapePath;

public class BottomAppBarTopEdgeTreatment extends EdgeTreatment {

   private static final int ANGLE_LEFT = 180;
   private static final int ANGLE_UP = 270;
   private static final int ARC_HALF = 180;
   private static final int ARC_QUARTER = 90;
   private float cradleVerticalOffset;
   private float fabDiameter;
   private float fabMargin;
   private float horizontalOffset;
   private float roundedCornerRadius;


   public BottomAppBarTopEdgeTreatment(float var1, float var2, float var3) {
      this.fabMargin = var1;
      this.roundedCornerRadius = var2;
      this.cradleVerticalOffset = var3;
      if(var3 < 0.0F) {
         throw new IllegalArgumentException("cradleVerticalOffset must be positive.");
      } else {
         this.horizontalOffset = 0.0F;
      }
   }

   float getCradleVerticalOffset() {
      return this.cradleVerticalOffset;
   }

   public void getEdgePath(float var1, float var2, ShapePath var3) {
      if(this.fabDiameter == 0.0F) {
         var3.lineTo(var1, 0.0F);
      } else {
         float var4 = (this.fabMargin * 2.0F + this.fabDiameter) / 2.0F;
         float var5 = var2 * this.roundedCornerRadius;
         float var6 = var1 / 2.0F + this.horizontalOffset;
         var2 = this.cradleVerticalOffset * var2 + (1.0F - var2) * var4;
         if(var2 / var4 >= 1.0F) {
            var3.lineTo(var1, 0.0F);
         } else {
            float var7 = var4 + var5;
            float var9 = var2 + var5;
            float var10 = (float)Math.sqrt((double)(var7 * var7 - var9 * var9));
            var7 = var6 - var10;
            float var8 = var6 + var10;
            var9 = (float)Math.toDegrees(Math.atan((double)(var10 / var9)));
            var10 = 90.0F - var9;
            float var11 = var7 - var5;
            var3.lineTo(var11, 0.0F);
            float var12 = var5 * 2.0F;
            var3.addArc(var11, 0.0F, var7 + var5, var12, 270.0F, var9);
            var3.addArc(var6 - var4, -var4 - var2, var6 + var4, var4 - var2, 180.0F - var10, var10 * 2.0F - 180.0F);
            var3.addArc(var8 - var5, 0.0F, var8 + var5, var12, 270.0F - var9, var9);
            var3.lineTo(var1, 0.0F);
         }
      }
   }

   float getFabCradleMargin() {
      return this.fabMargin;
   }

   float getFabCradleRoundedCornerRadius() {
      return this.roundedCornerRadius;
   }

   float getFabDiameter() {
      return this.fabDiameter;
   }

   float getHorizontalOffset() {
      return this.horizontalOffset;
   }

   void setCradleVerticalOffset(float var1) {
      this.cradleVerticalOffset = var1;
   }

   void setFabCradleMargin(float var1) {
      this.fabMargin = var1;
   }

   void setFabCradleRoundedCornerRadius(float var1) {
      this.roundedCornerRadius = var1;
   }

   void setFabDiameter(float var1) {
      this.fabDiameter = var1;
   }

   void setHorizontalOffset(float var1) {
      this.horizontalOffset = var1;
   }
}
