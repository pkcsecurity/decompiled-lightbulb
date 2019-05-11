package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaDimension {

   // $FF: synthetic field
   private static final YogaDimension[] $VALUES = new YogaDimension[]{WIDTH, HEIGHT};
   HEIGHT("HEIGHT", 1, 1),
   WIDTH("WIDTH", 0, 0);
   private int mIntValue;


   private YogaDimension(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaDimension fromInt(int var0) {
      switch(var0) {
      case 0:
         return WIDTH;
      case 1:
         return HEIGHT;
      default:
         StringBuilder var1 = new StringBuilder();
         var1.append("Unknown enum value: ");
         var1.append(var0);
         throw new IllegalArgumentException(var1.toString());
      }
   }

   public int intValue() {
      return this.mIntValue;
   }
}
