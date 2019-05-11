package com.facebook.react.animation;

import android.view.View;
import com.facebook.react.animation.AnimationPropertyUpdater;

public abstract class AbstractFloatPairPropertyUpdater implements AnimationPropertyUpdater {

   private boolean mFromSource;
   private final float[] mFromValues;
   private final float[] mToValues;
   private final float[] mUpdateValues;


   protected AbstractFloatPairPropertyUpdater(float var1, float var2) {
      this.mFromValues = new float[2];
      this.mToValues = new float[2];
      this.mUpdateValues = new float[2];
      this.mToValues[0] = var1;
      this.mToValues[1] = var2;
      this.mFromSource = true;
   }

   protected AbstractFloatPairPropertyUpdater(float var1, float var2, float var3, float var4) {
      this(var3, var4);
      this.mFromValues[0] = var1;
      this.mFromValues[1] = var2;
      this.mFromSource = false;
   }

   protected abstract void getProperty(View var1, float[] var2);

   public void onFinish(View var1) {
      this.setProperty(var1, this.mToValues);
   }

   public void onUpdate(View var1, float var2) {
      this.mUpdateValues[0] = this.mFromValues[0] + (this.mToValues[0] - this.mFromValues[0]) * var2;
      this.mUpdateValues[1] = this.mFromValues[1] + (this.mToValues[1] - this.mFromValues[1]) * var2;
      this.setProperty(var1, this.mUpdateValues);
   }

   public void prepare(View var1) {
      if(this.mFromSource) {
         this.getProperty(var1, this.mFromValues);
      }

   }

   protected abstract void setProperty(View var1, float[] var2);
}
