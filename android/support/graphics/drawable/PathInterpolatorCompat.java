package android.support.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.Resources.Theme;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.RestrictTo;
import android.support.graphics.drawable.AndroidResources;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.animation.Interpolator;
import org.xmlpull.v1.XmlPullParser;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class PathInterpolatorCompat implements Interpolator {

   public static final double EPSILON = 1.0E-5D;
   public static final int MAX_NUM_POINTS = 3000;
   private static final float PRECISION = 0.002F;
   private float[] mX;
   private float[] mY;


   public PathInterpolatorCompat(Context var1, AttributeSet var2, XmlPullParser var3) {
      this(var1.getResources(), var1.getTheme(), var2, var3);
   }

   public PathInterpolatorCompat(Resources var1, Theme var2, AttributeSet var3, XmlPullParser var4) {
      TypedArray var5 = TypedArrayUtils.obtainAttributes(var1, var2, var3, AndroidResources.STYLEABLE_PATH_INTERPOLATOR);
      this.parseInterpolatorFromTypeArray(var5, var4);
      var5.recycle();
   }

   private void initCubic(float var1, float var2, float var3, float var4) {
      Path var5 = new Path();
      var5.moveTo(0.0F, 0.0F);
      var5.cubicTo(var1, var2, var3, var4, 1.0F, 1.0F);
      this.initPath(var5);
   }

   private void initPath(Path var1) {
      int var5 = 0;
      PathMeasure var8 = new PathMeasure(var1, false);
      float var2 = var8.getLength();
      int var6 = Math.min(3000, (int)(var2 / 0.002F) + 1);
      StringBuilder var9;
      if(var6 <= 0) {
         var9 = new StringBuilder();
         var9.append("The Path has a invalid length ");
         var9.append(var2);
         throw new IllegalArgumentException(var9.toString());
      } else {
         this.mX = new float[var6];
         this.mY = new float[var6];
         float[] var7 = new float[2];

         int var4;
         for(var4 = 0; var4 < var6; ++var4) {
            var8.getPosTan((float)var4 * var2 / (float)(var6 - 1), var7, (float[])null);
            this.mX[var4] = var7[0];
            this.mY[var4] = var7[1];
         }

         if((double)Math.abs(this.mX[0]) <= 1.0E-5D && (double)Math.abs(this.mY[0]) <= 1.0E-5D) {
            var7 = this.mX;
            var4 = var6 - 1;
            if((double)Math.abs(var7[var4] - 1.0F) <= 1.0E-5D && (double)Math.abs(this.mY[var4] - 1.0F) <= 1.0E-5D) {
               var4 = 0;

               for(var2 = 0.0F; var5 < var6; ++var4) {
                  float var3 = this.mX[var4];
                  if(var3 < var2) {
                     var9 = new StringBuilder();
                     var9.append("The Path cannot loop back on itself, x :");
                     var9.append(var3);
                     throw new IllegalArgumentException(var9.toString());
                  }

                  this.mX[var5] = var3;
                  ++var5;
                  var2 = var3;
               }

               if(var8.nextContour()) {
                  throw new IllegalArgumentException("The Path should be continuous, can\'t have 2+ contours");
               }

               return;
            }
         }

         var9 = new StringBuilder();
         var9.append("The Path must start at (0,0) and end at (1,1) start: ");
         var9.append(this.mX[0]);
         var9.append(",");
         var9.append(this.mY[0]);
         var9.append(" end:");
         var7 = this.mX;
         var4 = var6 - 1;
         var9.append(var7[var4]);
         var9.append(",");
         var9.append(this.mY[var4]);
         throw new IllegalArgumentException(var9.toString());
      }
   }

   private void initQuad(float var1, float var2) {
      Path var3 = new Path();
      var3.moveTo(0.0F, 0.0F);
      var3.quadTo(var1, var2, 1.0F, 1.0F);
      this.initPath(var3);
   }

   private void parseInterpolatorFromTypeArray(TypedArray var1, XmlPullParser var2) {
      if(TypedArrayUtils.hasAttribute(var2, "pathData")) {
         String var6 = TypedArrayUtils.getNamedString(var1, var2, "pathData", 4);
         Path var7 = PathParser.createPathFromPathData(var6);
         if(var7 == null) {
            StringBuilder var8 = new StringBuilder();
            var8.append("The path is null, which is created from ");
            var8.append(var6);
            throw new InflateException(var8.toString());
         } else {
            this.initPath(var7);
         }
      } else if(!TypedArrayUtils.hasAttribute(var2, "controlX1")) {
         throw new InflateException("pathInterpolator requires the controlX1 attribute");
      } else if(!TypedArrayUtils.hasAttribute(var2, "controlY1")) {
         throw new InflateException("pathInterpolator requires the controlY1 attribute");
      } else {
         float var3 = TypedArrayUtils.getNamedFloat(var1, var2, "controlX1", 0, 0.0F);
         float var4 = TypedArrayUtils.getNamedFloat(var1, var2, "controlY1", 1, 0.0F);
         boolean var5 = TypedArrayUtils.hasAttribute(var2, "controlX2");
         if(var5 != TypedArrayUtils.hasAttribute(var2, "controlY2")) {
            throw new InflateException("pathInterpolator requires both controlX2 and controlY2 for cubic Beziers.");
         } else if(!var5) {
            this.initQuad(var3, var4);
         } else {
            this.initCubic(var3, var4, TypedArrayUtils.getNamedFloat(var1, var2, "controlX2", 2, 0.0F), TypedArrayUtils.getNamedFloat(var1, var2, "controlY2", 3, 0.0F));
         }
      }
   }

   public float getInterpolation(float var1) {
      if(var1 <= 0.0F) {
         return 0.0F;
      } else if(var1 >= 1.0F) {
         return 1.0F;
      } else {
         int var4 = 0;
         int var3 = this.mX.length - 1;

         while(var3 - var4 > 1) {
            int var5 = (var4 + var3) / 2;
            if(var1 < this.mX[var5]) {
               var3 = var5;
            } else {
               var4 = var5;
            }
         }

         float var2 = this.mX[var3] - this.mX[var4];
         if(var2 == 0.0F) {
            return this.mY[var4];
         } else {
            var1 = (var1 - this.mX[var4]) / var2;
            var2 = this.mY[var4];
            return var2 + var1 * (this.mY[var3] - var2);
         }
      }
   }
}
