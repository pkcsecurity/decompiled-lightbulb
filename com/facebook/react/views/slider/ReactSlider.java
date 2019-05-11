package com.facebook.react.views.slider;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;
import javax.annotation.Nullable;

public class ReactSlider extends SeekBar {

   private static int DEFAULT_TOTAL_STEPS;
   private double mMaxValue = 0.0D;
   private double mMinValue = 0.0D;
   private double mStep = 0.0D;
   private double mStepCalculated = 0.0D;
   private double mValue = 0.0D;


   public ReactSlider(Context var1, @Nullable AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   private double getStepValue() {
      return this.mStep > 0.0D?this.mStep:this.mStepCalculated;
   }

   private int getTotalSteps() {
      return (int)Math.ceil((this.mMaxValue - this.mMinValue) / this.getStepValue());
   }

   private void updateAll() {
      if(this.mStep == 0.0D) {
         this.mStepCalculated = (this.mMaxValue - this.mMinValue) / (double)DEFAULT_TOTAL_STEPS;
      }

      this.setMax(this.getTotalSteps());
      this.updateValue();
   }

   private void updateValue() {
      this.setProgress((int)Math.round((this.mValue - this.mMinValue) / (this.mMaxValue - this.mMinValue) * (double)this.getTotalSteps()));
   }

   void setMaxValue(double var1) {
      this.mMaxValue = var1;
      this.updateAll();
   }

   void setMinValue(double var1) {
      this.mMinValue = var1;
      this.updateAll();
   }

   void setStep(double var1) {
      this.mStep = var1;
      this.updateAll();
   }

   void setValue(double var1) {
      this.mValue = var1;
      this.updateValue();
   }

   public double toRealProgress(int var1) {
      return var1 == this.getMax()?this.mMaxValue:(double)var1 * this.getStepValue() + this.mMinValue;
   }
}
