package com.facebook.react.animation;

import android.view.View;
import com.facebook.react.animation.AnimationPropertyUpdater;

public abstract class AbstractSingleFloatProperyUpdater implements AnimationPropertyUpdater {

   private boolean mFromSource;
   private float mFromValue;
   private float mToValue;


   protected AbstractSingleFloatProperyUpdater(float var1) {
      this.mToValue = var1;
      this.mFromSource = true;
   }

   protected AbstractSingleFloatProperyUpdater(float var1, float var2) {
      this(var2);
      this.mFromValue = var1;
      this.mFromSource = false;
   }

   protected abstract float getProperty(View var1);

   public void onFinish(View var1) {
      this.setProperty(var1, this.mToValue);
   }

   public final void onUpdate(View var1, float var2) {
      this.setProperty(var1, this.mFromValue + (this.mToValue - this.mFromValue) * var2);
   }

   public final void prepare(View var1) {
      if(this.mFromSource) {
         this.mFromValue = this.getProperty(var1);
      }

   }

   protected abstract void setProperty(View var1, float var2);
}
