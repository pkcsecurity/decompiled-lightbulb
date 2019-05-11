package com.facebook.imagepipeline.image;

import com.facebook.imagepipeline.image.QualityInfo;

public class ImmutableQualityInfo implements QualityInfo {

   public static final QualityInfo FULL_QUALITY = of(Integer.MAX_VALUE, true, true);
   boolean mIsOfFullQuality;
   boolean mIsOfGoodEnoughQuality;
   int mQuality;


   private ImmutableQualityInfo(int var1, boolean var2, boolean var3) {
      this.mQuality = var1;
      this.mIsOfGoodEnoughQuality = var2;
      this.mIsOfFullQuality = var3;
   }

   public static QualityInfo of(int var0, boolean var1, boolean var2) {
      return new ImmutableQualityInfo(var0, var1, var2);
   }

   public boolean equals(Object var1) {
      if(var1 == this) {
         return true;
      } else if(!(var1 instanceof ImmutableQualityInfo)) {
         return false;
      } else {
         ImmutableQualityInfo var2 = (ImmutableQualityInfo)var1;
         return this.mQuality == var2.mQuality && this.mIsOfGoodEnoughQuality == var2.mIsOfGoodEnoughQuality && this.mIsOfFullQuality == var2.mIsOfFullQuality;
      }
   }

   public int getQuality() {
      return this.mQuality;
   }

   public int hashCode() {
      int var3 = this.mQuality;
      boolean var4 = this.mIsOfGoodEnoughQuality;
      int var2 = 0;
      int var1;
      if(var4) {
         var1 = 4194304;
      } else {
         var1 = 0;
      }

      if(this.mIsOfFullQuality) {
         var2 = 8388608;
      }

      return var3 ^ var1 ^ var2;
   }

   public boolean isOfFullQuality() {
      return this.mIsOfFullQuality;
   }

   public boolean isOfGoodEnoughQuality() {
      return this.mIsOfGoodEnoughQuality;
   }
}
