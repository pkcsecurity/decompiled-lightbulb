package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaUnit {

   // $FF: synthetic field
   private static final YogaUnit[] $VALUES = new YogaUnit[]{UNDEFINED, POINT, PERCENT, AUTO};
   AUTO("AUTO", 3, 3),
   PERCENT("PERCENT", 2, 2),
   POINT("POINT", 1, 1),
   UNDEFINED("UNDEFINED", 0, 0);
   private int mIntValue;


   private YogaUnit(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaUnit fromInt(int var0) {
      switch(var0) {
      case 0:
         return UNDEFINED;
      case 1:
         return POINT;
      case 2:
         return PERCENT;
      case 3:
         return AUTO;
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
