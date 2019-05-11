package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaPositionType {

   // $FF: synthetic field
   private static final YogaPositionType[] $VALUES = new YogaPositionType[]{RELATIVE, ABSOLUTE};
   ABSOLUTE("ABSOLUTE", 1, 1),
   RELATIVE("RELATIVE", 0, 0);
   private int mIntValue;


   private YogaPositionType(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaPositionType fromInt(int var0) {
      switch(var0) {
      case 0:
         return RELATIVE;
      case 1:
         return ABSOLUTE;
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
