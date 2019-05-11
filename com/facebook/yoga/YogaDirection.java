package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaDirection {

   // $FF: synthetic field
   private static final YogaDirection[] $VALUES = new YogaDirection[]{INHERIT, LTR, RTL};
   INHERIT("INHERIT", 0, 0),
   LTR("LTR", 1, 1),
   RTL("RTL", 2, 2);
   private int mIntValue;


   private YogaDirection(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaDirection fromInt(int var0) {
      switch(var0) {
      case 0:
         return INHERIT;
      case 1:
         return LTR;
      case 2:
         return RTL;
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
