package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaDisplay {

   // $FF: synthetic field
   private static final YogaDisplay[] $VALUES = new YogaDisplay[]{FLEX, NONE};
   FLEX("FLEX", 0, 0),
   NONE("NONE", 1, 1);
   private int mIntValue;


   private YogaDisplay(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaDisplay fromInt(int var0) {
      switch(var0) {
      case 0:
         return FLEX;
      case 1:
         return NONE;
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
