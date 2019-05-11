package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.art.PropHelper;
import javax.annotation.Nullable;

public abstract class ARTVirtualNode extends ReactShadowNodeImpl {

   protected static final float MIN_OPACITY_FOR_DRAW = 0.01F;
   private static final float[] sMatrixData = new float[9];
   private static final float[] sRawMatrix = new float[9];
   @Nullable
   private Matrix mMatrix = new Matrix();
   protected float mOpacity = 1.0F;
   protected final float mScale;


   public ARTVirtualNode() {
      this.mScale = DisplayMetricsHolder.getWindowDisplayMetrics().density;
   }

   public abstract void draw(Canvas var1, Paint var2, float var3);

   public boolean isVirtual() {
      return true;
   }

   protected void restoreCanvas(Canvas var1) {
      var1.restore();
   }

   protected final void saveAndSetupCanvas(Canvas var1) {
      var1.save();
      if(this.mMatrix != null) {
         var1.concat(this.mMatrix);
      }

   }

   @ReactProp(
      defaultFloat = 1.0F,
      name = "opacity"
   )
   public void setOpacity(float var1) {
      this.mOpacity = var1;
      this.markUpdated();
   }

   @ReactProp(
      name = "transform"
   )
   public void setTransform(@Nullable ReadableArray var1) {
      if(var1 != null) {
         int var2 = PropHelper.toFloatArray(var1, sMatrixData);
         if(var2 == 6) {
            this.setupMatrix();
         } else if(var2 != -1) {
            throw new JSApplicationIllegalArgumentException("Transform matrices must be of size 6");
         }
      } else {
         this.mMatrix = null;
      }

      this.markUpdated();
   }

   protected void setupMatrix() {
      sRawMatrix[0] = sMatrixData[0];
      sRawMatrix[1] = sMatrixData[2];
      sRawMatrix[2] = sMatrixData[4] * this.mScale;
      sRawMatrix[3] = sMatrixData[1];
      sRawMatrix[4] = sMatrixData[3];
      sRawMatrix[5] = sMatrixData[5] * this.mScale;
      sRawMatrix[6] = 0.0F;
      sRawMatrix[7] = 0.0F;
      sRawMatrix[8] = 1.0F;
      if(this.mMatrix == null) {
         this.mMatrix = new Matrix();
      }

      this.mMatrix.setValues(sRawMatrix);
   }
}
