package com.facebook.drawee.generic;

import android.support.annotation.ColorInt;
import com.facebook.common.internal.Preconditions;
import java.util.Arrays;

public class RoundingParams {

   private int mBorderColor;
   private float mBorderWidth;
   private float[] mCornersRadii;
   private int mOverlayColor;
   private float mPadding;
   private boolean mRoundAsCircle;
   private RoundingParams.RoundingMethod mRoundingMethod;


   public RoundingParams() {
      this.mRoundingMethod = RoundingParams.RoundingMethod.BITMAP_ONLY;
      this.mRoundAsCircle = false;
      this.mCornersRadii = null;
      this.mOverlayColor = 0;
      this.mBorderWidth = 0.0F;
      this.mBorderColor = 0;
      this.mPadding = 0.0F;
   }

   public static RoundingParams asCircle() {
      return (new RoundingParams()).setRoundAsCircle(true);
   }

   public static RoundingParams fromCornersRadii(float var0, float var1, float var2, float var3) {
      return (new RoundingParams()).setCornersRadii(var0, var1, var2, var3);
   }

   public static RoundingParams fromCornersRadii(float[] var0) {
      return (new RoundingParams()).setCornersRadii(var0);
   }

   public static RoundingParams fromCornersRadius(float var0) {
      return (new RoundingParams()).setCornersRadius(var0);
   }

   private float[] getOrCreateRoundedCornersRadii() {
      if(this.mCornersRadii == null) {
         this.mCornersRadii = new float[8];
      }

      return this.mCornersRadii;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            RoundingParams var2 = (RoundingParams)var1;
            return this.mRoundAsCircle != var2.mRoundAsCircle?false:(this.mOverlayColor != var2.mOverlayColor?false:(Float.compare(var2.mBorderWidth, this.mBorderWidth) != 0?false:(this.mBorderColor != var2.mBorderColor?false:(Float.compare(var2.mPadding, this.mPadding) != 0?false:(this.mRoundingMethod != var2.mRoundingMethod?false:Arrays.equals(this.mCornersRadii, var2.mCornersRadii))))));
         }
      } else {
         return false;
      }
   }

   public int getBorderColor() {
      return this.mBorderColor;
   }

   public float getBorderWidth() {
      return this.mBorderWidth;
   }

   public float[] getCornersRadii() {
      return this.mCornersRadii;
   }

   public int getOverlayColor() {
      return this.mOverlayColor;
   }

   public float getPadding() {
      return this.mPadding;
   }

   public boolean getRoundAsCircle() {
      return this.mRoundAsCircle;
   }

   public RoundingParams.RoundingMethod getRoundingMethod() {
      return this.mRoundingMethod;
   }

   public int hashCode() {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
   }

   public RoundingParams setBorder(@ColorInt int var1, float var2) {
      boolean var3;
      if(var2 >= 0.0F) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "the border width cannot be < 0");
      this.mBorderWidth = var2;
      this.mBorderColor = var1;
      return this;
   }

   public RoundingParams setBorderColor(@ColorInt int var1) {
      this.mBorderColor = var1;
      return this;
   }

   public RoundingParams setBorderWidth(float var1) {
      boolean var2;
      if(var1 >= 0.0F) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "the border width cannot be < 0");
      this.mBorderWidth = var1;
      return this;
   }

   public RoundingParams setCornersRadii(float var1, float var2, float var3, float var4) {
      float[] var5 = this.getOrCreateRoundedCornersRadii();
      var5[1] = var1;
      var5[0] = var1;
      var5[3] = var2;
      var5[2] = var2;
      var5[5] = var3;
      var5[4] = var3;
      var5[7] = var4;
      var5[6] = var4;
      return this;
   }

   public RoundingParams setCornersRadii(float[] var1) {
      Preconditions.checkNotNull(var1);
      boolean var2;
      if(var1.length == 8) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "radii should have exactly 8 values");
      System.arraycopy(var1, 0, this.getOrCreateRoundedCornersRadii(), 0, 8);
      return this;
   }

   public RoundingParams setCornersRadius(float var1) {
      Arrays.fill(this.getOrCreateRoundedCornersRadii(), var1);
      return this;
   }

   public RoundingParams setOverlayColor(@ColorInt int var1) {
      this.mOverlayColor = var1;
      this.mRoundingMethod = RoundingParams.RoundingMethod.OVERLAY_COLOR;
      return this;
   }

   public RoundingParams setPadding(float var1) {
      boolean var2;
      if(var1 >= 0.0F) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "the padding cannot be < 0");
      this.mPadding = var1;
      return this;
   }

   public RoundingParams setRoundAsCircle(boolean var1) {
      this.mRoundAsCircle = var1;
      return this;
   }

   public RoundingParams setRoundingMethod(RoundingParams.RoundingMethod var1) {
      this.mRoundingMethod = var1;
      return this;
   }

   public static enum RoundingMethod {

      // $FF: synthetic field
      private static final RoundingParams.RoundingMethod[] $VALUES = new RoundingParams.RoundingMethod[]{OVERLAY_COLOR, BITMAP_ONLY};
      BITMAP_ONLY("BITMAP_ONLY", 1),
      OVERLAY_COLOR("OVERLAY_COLOR", 0);


      private RoundingMethod(String var1, int var2) {}
   }
}
