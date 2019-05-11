package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.Shader.TileMode;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.art.ARTVirtualNode;
import com.facebook.react.views.art.PropHelper;
import javax.annotation.Nullable;

public class ARTShapeShadowNode extends ARTVirtualNode {

   private static final int CAP_BUTT = 0;
   private static final int CAP_ROUND = 1;
   private static final int CAP_SQUARE = 2;
   private static final int COLOR_TYPE_LINEAR_GRADIENT = 1;
   private static final int COLOR_TYPE_PATTERN = 3;
   private static final int COLOR_TYPE_RADIAL_GRADIENT = 2;
   private static final int COLOR_TYPE_SOLID_COLOR = 0;
   private static final int JOIN_BEVEL = 2;
   private static final int JOIN_MITER = 0;
   private static final int JOIN_ROUND = 1;
   private static final int PATH_TYPE_ARC = 4;
   private static final int PATH_TYPE_CLOSE = 1;
   private static final int PATH_TYPE_CURVETO = 3;
   private static final int PATH_TYPE_LINETO = 2;
   private static final int PATH_TYPE_MOVETO = 0;
   @Nullable
   private float[] mBrushData;
   @Nullable
   protected Path mPath;
   private int mStrokeCap = 1;
   @Nullable
   private float[] mStrokeColor;
   @Nullable
   private float[] mStrokeDash;
   private int mStrokeJoin = 1;
   private float mStrokeWidth = 1.0F;


   private Path createPath(float[] var1) {
      Path var15 = new Path();
      var15.moveTo(0.0F, 0.0F);
      int var11 = 0;

      while(var11 < var1.length) {
         int var12 = var11 + 1;
         var11 = (int)var1[var11];
         float var2;
         float var3;
         float var4;
         float var5;
         float var6;
         float var7;
         int var13;
         switch(var11) {
         case 0:
            var13 = var12 + 1;
            var2 = var1[var12];
            var3 = this.mScale;
            var11 = var13 + 1;
            var15.moveTo(var2 * var3, var1[var13] * this.mScale);
            break;
         case 1:
            var15.close();
            var11 = var12;
            break;
         case 2:
            var13 = var12 + 1;
            var2 = var1[var12];
            var3 = this.mScale;
            var11 = var13 + 1;
            var15.lineTo(var2 * var3, var1[var13] * this.mScale);
            break;
         case 3:
            var11 = var12 + 1;
            var2 = var1[var12];
            var3 = this.mScale;
            var12 = var11 + 1;
            var4 = var1[var11];
            var5 = this.mScale;
            var11 = var12 + 1;
            var6 = var1[var12];
            var7 = this.mScale;
            var12 = var11 + 1;
            float var8 = var1[var11];
            float var9 = this.mScale;
            var11 = var12 + 1;
            float var10 = var1[var12];
            var15.cubicTo(var2 * var3, var5 * var4, var7 * var6, var9 * var8, this.mScale * var10, var1[var11] * this.mScale);
            ++var11;
            break;
         case 4:
            var11 = var12 + 1;
            var4 = var1[var12] * this.mScale;
            var12 = var11 + 1;
            var5 = var1[var11] * this.mScale;
            var11 = var12 + 1;
            var6 = var1[var12] * this.mScale;
            var13 = var11 + 1;
            var7 = (float)Math.toDegrees((double)var1[var11]);
            var12 = var13 + 1;
            var2 = (float)Math.toDegrees((double)var1[var13]);
            boolean var17;
            if(var1[var12] != 1.0F) {
               var17 = true;
            } else {
               var17 = false;
            }

            var2 -= var7;
            if(Math.abs(var2) >= 360.0F) {
               Direction var14;
               if(var17) {
                  var14 = Direction.CCW;
               } else {
                  var14 = Direction.CW;
               }

               var15.addCircle(var4, var5, var6, var14);
            } else {
               var3 = this.modulus(var2, 360.0F);
               var2 = var3;
               if(var17) {
                  var2 = var3;
                  if(var3 < 360.0F) {
                     var2 = (360.0F - var3) * -1.0F;
                  }
               }

               var15.arcTo(new RectF(var4 - var6, var5 - var6, var4 + var6, var5 + var6), var7, var2);
            }

            var11 = var12 + 1;
            break;
         default:
            StringBuilder var16 = new StringBuilder();
            var16.append("Unrecognized drawing instruction ");
            var16.append(var11);
            throw new JSApplicationIllegalArgumentException(var16.toString());
         }
      }

      return var15;
   }

   private float modulus(float var1, float var2) {
      float var3 = var1 % var2;
      var1 = var3;
      if(var3 < 0.0F) {
         var1 = var3 + var2;
      }

      return var1;
   }

   public void draw(Canvas var1, Paint var2, float var3) {
      var3 *= this.mOpacity;
      if(var3 > 0.01F) {
         this.saveAndSetupCanvas(var1);
         if(this.mPath == null) {
            throw new JSApplicationIllegalArgumentException("Shapes should have a valid path (d) prop");
         }

         if(this.setupFillPaint(var2, var3)) {
            var1.drawPath(this.mPath, var2);
         }

         if(this.setupStrokePaint(var2, var3)) {
            var1.drawPath(this.mPath, var2);
         }

         this.restoreCanvas(var1);
      }

      this.markUpdateSeen();
   }

   @ReactProp(
      name = "fill"
   )
   public void setFill(@Nullable ReadableArray var1) {
      this.mBrushData = PropHelper.toFloatArray(var1);
      this.markUpdated();
   }

   @ReactProp(
      name = "d"
   )
   public void setShapePath(@Nullable ReadableArray var1) {
      this.mPath = this.createPath(PropHelper.toFloatArray(var1));
      this.markUpdated();
   }

   @ReactProp(
      name = "stroke"
   )
   public void setStroke(@Nullable ReadableArray var1) {
      this.mStrokeColor = PropHelper.toFloatArray(var1);
      this.markUpdated();
   }

   @ReactProp(
      defaultInt = 1,
      name = "strokeCap"
   )
   public void setStrokeCap(int var1) {
      this.mStrokeCap = var1;
      this.markUpdated();
   }

   @ReactProp(
      name = "strokeDash"
   )
   public void setStrokeDash(@Nullable ReadableArray var1) {
      this.mStrokeDash = PropHelper.toFloatArray(var1);
      this.markUpdated();
   }

   @ReactProp(
      defaultInt = 1,
      name = "strokeJoin"
   )
   public void setStrokeJoin(int var1) {
      this.mStrokeJoin = var1;
      this.markUpdated();
   }

   @ReactProp(
      defaultFloat = 1.0F,
      name = "strokeWidth"
   )
   public void setStrokeWidth(float var1) {
      this.mStrokeWidth = var1;
      this.markUpdated();
   }

   protected boolean setupFillPaint(Paint var1, float var2) {
      float[] var16 = this.mBrushData;
      int var10 = 0;
      if(var16 != null && this.mBrushData.length > 0) {
         var1.reset();
         var1.setFlags(1);
         var1.setStyle(Style.FILL);
         int var11 = (int)this.mBrushData[0];
         StringBuilder var19;
         switch(var11) {
         case 0:
            if(this.mBrushData.length > 4) {
               var2 = this.mBrushData[4] * var2 * 255.0F;
            } else {
               var2 *= 255.0F;
            }

            var1.setARGB((int)var2, (int)(this.mBrushData[1] * 255.0F), (int)(this.mBrushData[2] * 255.0F), (int)(this.mBrushData[3] * 255.0F));
            break;
         case 1:
            if(this.mBrushData.length < 5) {
               var19 = new StringBuilder();
               var19.append("[ARTShapeShadowNode setupFillPaint] expects 5 elements, received ");
               var19.append(this.mBrushData.length);
               FLog.w("ReactNative", var19.toString());
               return false;
            }

            var2 = this.mBrushData[1];
            float var3 = this.mScale;
            float var4 = this.mBrushData[2];
            float var5 = this.mScale;
            float var6 = this.mBrushData[3];
            float var7 = this.mScale;
            float var8 = this.mBrushData[4];
            float var9 = this.mScale;
            var11 = (this.mBrushData.length - 5) / 5;
            Object var17;
            int[] var20;
            if(var11 > 0) {
               var20 = new int[var11];

               for(var17 = new float[var11]; var10 < var11; ++var10) {
                  ((Object[])var17)[var10] = this.mBrushData[var11 * 4 + 5 + var10];
                  float[] var18 = this.mBrushData;
                  int var12 = var10 * 4 + 5;
                  int var13 = (int)(var18[var12 + 0] * 255.0F);
                  int var14 = (int)(this.mBrushData[var12 + 1] * 255.0F);
                  int var15 = (int)(this.mBrushData[var12 + 2] * 255.0F);
                  var20[var10] = Color.argb((int)(this.mBrushData[var12 + 3] * 255.0F), var13, var14, var15);
               }
            } else {
               var20 = null;
               var17 = var20;
            }

            var1.setShader(new LinearGradient(var2 * var3, var4 * var5, var6 * var7, var8 * var9, var20, (float[])var17, TileMode.CLAMP));
            break;
         default:
            var19 = new StringBuilder();
            var19.append("ART: Color type ");
            var19.append(var11);
            var19.append(" not supported!");
            FLog.w("ReactNative", var19.toString());
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean setupStrokePaint(Paint var1, float var2) {
      if(this.mStrokeWidth != 0.0F && this.mStrokeColor != null) {
         if(this.mStrokeColor.length == 0) {
            return false;
         } else {
            var1.reset();
            var1.setFlags(1);
            var1.setStyle(Style.STROKE);
            StringBuilder var3;
            switch(this.mStrokeCap) {
            case 0:
               var1.setStrokeCap(Cap.BUTT);
               break;
            case 1:
               var1.setStrokeCap(Cap.ROUND);
               break;
            case 2:
               var1.setStrokeCap(Cap.SQUARE);
               break;
            default:
               var3 = new StringBuilder();
               var3.append("strokeCap ");
               var3.append(this.mStrokeCap);
               var3.append(" unrecognized");
               throw new JSApplicationIllegalArgumentException(var3.toString());
            }

            switch(this.mStrokeJoin) {
            case 0:
               var1.setStrokeJoin(Join.MITER);
               break;
            case 1:
               var1.setStrokeJoin(Join.ROUND);
               break;
            case 2:
               var1.setStrokeJoin(Join.BEVEL);
               break;
            default:
               var3 = new StringBuilder();
               var3.append("strokeJoin ");
               var3.append(this.mStrokeJoin);
               var3.append(" unrecognized");
               throw new JSApplicationIllegalArgumentException(var3.toString());
            }

            var1.setStrokeWidth(this.mStrokeWidth * this.mScale);
            if(this.mStrokeColor.length > 3) {
               var2 = this.mStrokeColor[3] * var2 * 255.0F;
            } else {
               var2 *= 255.0F;
            }

            var1.setARGB((int)var2, (int)(this.mStrokeColor[0] * 255.0F), (int)(this.mStrokeColor[1] * 255.0F), (int)(this.mStrokeColor[2] * 255.0F));
            if(this.mStrokeDash != null && this.mStrokeDash.length > 0) {
               var1.setPathEffect(new DashPathEffect(this.mStrokeDash, 0.0F));
            }

            return true;
         }
      } else {
         return false;
      }
   }
}
