package com.github.mikephil.charting.components;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.utils.Utils;

public class XAxis extends AxisBase {

   private boolean mAvoidFirstLastClipping = false;
   public int mLabelHeight = 1;
   public int mLabelRotatedHeight = 1;
   public int mLabelRotatedWidth = 1;
   protected float mLabelRotationAngle = 0.0F;
   public int mLabelWidth = 1;
   private XAxis.XAxisPosition mPosition;


   public XAxis() {
      this.mPosition = XAxis.XAxisPosition.TOP;
      this.mYOffset = Utils.convertDpToPixel(4.0F);
   }

   public float getLabelRotationAngle() {
      return this.mLabelRotationAngle;
   }

   public XAxis.XAxisPosition getPosition() {
      return this.mPosition;
   }

   public boolean isAvoidFirstLastClippingEnabled() {
      return this.mAvoidFirstLastClipping;
   }

   public void setAvoidFirstLastClipping(boolean var1) {
      this.mAvoidFirstLastClipping = var1;
   }

   public void setLabelRotationAngle(float var1) {
      this.mLabelRotationAngle = var1;
   }

   public void setPosition(XAxis.XAxisPosition var1) {
      this.mPosition = var1;
   }

   public static enum XAxisPosition {

      // $FF: synthetic field
      private static final XAxis.XAxisPosition[] $VALUES = new XAxis.XAxisPosition[]{TOP, BOTTOM, BOTH_SIDED, TOP_INSIDE, BOTTOM_INSIDE};
      BOTH_SIDED("BOTH_SIDED", 2),
      BOTTOM("BOTTOM", 1),
      BOTTOM_INSIDE("BOTTOM_INSIDE", 4),
      TOP("TOP", 0),
      TOP_INSIDE("TOP_INSIDE", 3);


      private XAxisPosition(String var1, int var2) {}
   }
}
