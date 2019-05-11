package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaExperimentalFeature {

   // $FF: synthetic field
   private static final YogaExperimentalFeature[] $VALUES = new YogaExperimentalFeature[]{WEB_FLEX_BASIS};
   WEB_FLEX_BASIS("WEB_FLEX_BASIS", 0, 0);
   private int mIntValue;


   private YogaExperimentalFeature(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaExperimentalFeature fromInt(int var0) {
      if(var0 != 0) {
         StringBuilder var1 = new StringBuilder();
         var1.append("Unknown enum value: ");
         var1.append(var0);
         throw new IllegalArgumentException(var1.toString());
      } else {
         return WEB_FLEX_BASIS;
      }
   }

   public int intValue() {
      return this.mIntValue;
   }
}
