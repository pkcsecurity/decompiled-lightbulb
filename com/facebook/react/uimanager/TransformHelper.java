package com.facebook.react.uimanager;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.MatrixMathHelper;

public class TransformHelper {

   private static ThreadLocal<double[]> sHelperMatrix = new ThreadLocal() {
      protected double[] initialValue() {
         return new double[16];
      }
   };


   private static double convertToRadians(ReadableMap var0, String var1) {
      ReadableType var6 = var0.getType(var1);
      ReadableType var7 = ReadableType.String;
      boolean var5 = true;
      boolean var4 = true;
      double var2;
      if(var6 == var7) {
         var1 = var0.getString(var1);
         String var8;
         if(var1.endsWith("rad")) {
            var8 = var1.substring(0, var1.length() - 3);
         } else {
            var8 = var1;
            if(var1.endsWith("deg")) {
               var8 = var1.substring(0, var1.length() - 3);
               var4 = false;
            }
         }

         var2 = (double)Float.parseFloat(var8);
      } else {
         var2 = var0.getDouble(var1);
         var4 = var5;
      }

      return var4?var2:MatrixMathHelper.degreesToRadians(var2);
   }

   public static void processTransform(ReadableArray var0, double[] var1) {
      double[] var12 = (double[])sHelperMatrix.get();
      MatrixMathHelper.resetIdentityMatrix(var1);
      int var10 = var0.size();

      for(int var8 = 0; var8 < var10; ++var8) {
         ReadableMap var14 = var0.getMap(var8);
         String var13 = var14.keySetIterator().nextKey();
         MatrixMathHelper.resetIdentityMatrix(var12);
         ReadableArray var16;
         if("matrix".equals(var13)) {
            var16 = var14.getArray(var13);

            for(int var9 = 0; var9 < 16; ++var9) {
               var12[var9] = var16.getDouble(var9);
            }
         } else if("perspective".equals(var13)) {
            MatrixMathHelper.applyPerspective(var12, var14.getDouble(var13));
         } else if("rotateX".equals(var13)) {
            MatrixMathHelper.applyRotateX(var12, convertToRadians(var14, var13));
         } else if("rotateY".equals(var13)) {
            MatrixMathHelper.applyRotateY(var12, convertToRadians(var14, var13));
         } else if(!"rotate".equals(var13) && !"rotateZ".equals(var13)) {
            double var2;
            if("scale".equals(var13)) {
               var2 = var14.getDouble(var13);
               MatrixMathHelper.applyScaleX(var12, var2);
               MatrixMathHelper.applyScaleY(var12, var2);
            } else if("scaleX".equals(var13)) {
               MatrixMathHelper.applyScaleX(var12, var14.getDouble(var13));
            } else if("scaleY".equals(var13)) {
               MatrixMathHelper.applyScaleY(var12, var14.getDouble(var13));
            } else {
               boolean var11 = "translate".equals(var13);
               var2 = 0.0D;
               if(var11) {
                  var16 = var14.getArray(var13);
                  double var4 = var16.getDouble(0);
                  double var6 = var16.getDouble(1);
                  if(var16.size() > 2) {
                     var2 = var16.getDouble(2);
                  }

                  MatrixMathHelper.applyTranslate3D(var12, var4, var6, var2);
               } else if("translateX".equals(var13)) {
                  MatrixMathHelper.applyTranslate2D(var12, var14.getDouble(var13), 0.0D);
               } else if("translateY".equals(var13)) {
                  MatrixMathHelper.applyTranslate2D(var12, 0.0D, var14.getDouble(var13));
               } else if("skewX".equals(var13)) {
                  MatrixMathHelper.applySkewX(var12, convertToRadians(var14, var13));
               } else {
                  if(!"skewY".equals(var13)) {
                     StringBuilder var15 = new StringBuilder();
                     var15.append("Unsupported transform type: ");
                     var15.append(var13);
                     throw new JSApplicationIllegalArgumentException(var15.toString());
                  }

                  MatrixMathHelper.applySkewY(var12, convertToRadians(var14, var13));
               }
            }
         } else {
            MatrixMathHelper.applyRotateZ(var12, convertToRadians(var14, var13));
         }

         MatrixMathHelper.multiplyInto(var1, var1, var12);
      }

   }
}
