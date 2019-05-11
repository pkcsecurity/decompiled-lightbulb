package android.support.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.support.transition.PathMotion;
import android.support.transition.Styleable;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import org.xmlpull.v1.XmlPullParser;

public class ArcMotion extends PathMotion {

   private static final float DEFAULT_MAX_ANGLE_DEGREES = 70.0F;
   private static final float DEFAULT_MAX_TANGENT = (float)Math.tan(Math.toRadians(35.0D));
   private static final float DEFAULT_MIN_ANGLE_DEGREES = 0.0F;
   private float mMaximumAngle = 70.0F;
   private float mMaximumTangent;
   private float mMinimumHorizontalAngle = 0.0F;
   private float mMinimumHorizontalTangent = 0.0F;
   private float mMinimumVerticalAngle = 0.0F;
   private float mMinimumVerticalTangent = 0.0F;


   public ArcMotion() {
      this.mMaximumTangent = DEFAULT_MAX_TANGENT;
   }

   public ArcMotion(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mMaximumTangent = DEFAULT_MAX_TANGENT;
      TypedArray var3 = var1.obtainStyledAttributes(var2, Styleable.ARC_MOTION);
      XmlPullParser var4 = (XmlPullParser)var2;
      this.setMinimumVerticalAngle(TypedArrayUtils.getNamedFloat(var3, var4, "minimumVerticalAngle", 1, 0.0F));
      this.setMinimumHorizontalAngle(TypedArrayUtils.getNamedFloat(var3, var4, "minimumHorizontalAngle", 0, 0.0F));
      this.setMaximumAngle(TypedArrayUtils.getNamedFloat(var3, var4, "maximumAngle", 2, 70.0F));
      var3.recycle();
   }

   private static float toTangent(float var0) {
      if(var0 >= 0.0F && var0 <= 90.0F) {
         return (float)Math.tan(Math.toRadians((double)(var0 / 2.0F)));
      } else {
         throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
      }
   }

   public float getMaximumAngle() {
      return this.mMaximumAngle;
   }

   public float getMinimumHorizontalAngle() {
      return this.mMinimumHorizontalAngle;
   }

   public float getMinimumVerticalAngle() {
      return this.mMinimumVerticalAngle;
   }

   public Path getPath(float var1, float var2, float var3, float var4) {
      Path var14 = new Path();
      var14.moveTo(var1, var2);
      float var5 = var3 - var1;
      float var6 = var4 - var2;
      float var7 = var5 * var5 + var6 * var6;
      float var11 = (var1 + var3) / 2.0F;
      float var10 = (var2 + var4) / 2.0F;
      float var8 = 0.25F * var7;
      boolean var13;
      if(var2 > var4) {
         var13 = true;
      } else {
         var13 = false;
      }

      if(Math.abs(var5) < Math.abs(var6)) {
         var5 = Math.abs(var7 / (var6 * 2.0F));
         if(var13) {
            var5 += var4;
            var6 = var3;
         } else {
            var5 += var2;
            var6 = var1;
         }

         var7 = this.mMinimumVerticalTangent * var8 * this.mMinimumVerticalTangent;
      } else {
         var6 = var7 / (var5 * 2.0F);
         if(var13) {
            var5 = var2;
            var6 += var1;
         } else {
            var6 = var3 - var6;
            var5 = var4;
         }

         var7 = this.mMinimumHorizontalTangent * var8 * this.mMinimumHorizontalTangent;
      }

      float var9 = var11 - var6;
      float var12 = var10 - var5;
      var12 = var9 * var9 + var12 * var12;
      var8 = var8 * this.mMaximumTangent * this.mMaximumTangent;
      if(var12 >= var7) {
         if(var12 > var8) {
            var7 = var8;
         } else {
            var7 = 0.0F;
         }
      }

      var9 = var6;
      var8 = var5;
      if(var7 != 0.0F) {
         var7 = (float)Math.sqrt((double)(var7 / var12));
         var9 = (var6 - var11) * var7 + var11;
         var8 = var10 + var7 * (var5 - var10);
      }

      var14.cubicTo((var1 + var9) / 2.0F, (var2 + var8) / 2.0F, (var9 + var3) / 2.0F, (var8 + var4) / 2.0F, var3, var4);
      return var14;
   }

   public void setMaximumAngle(float var1) {
      this.mMaximumAngle = var1;
      this.mMaximumTangent = toTangent(var1);
   }

   public void setMinimumHorizontalAngle(float var1) {
      this.mMinimumHorizontalAngle = var1;
      this.mMinimumHorizontalTangent = toTangent(var1);
   }

   public void setMinimumVerticalAngle(float var1) {
      this.mMinimumVerticalAngle = var1;
      this.mMinimumVerticalTangent = toTangent(var1);
   }
}
