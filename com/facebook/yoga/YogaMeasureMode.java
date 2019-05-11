package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaMeasureMode {

   // $FF: synthetic field
   private static final YogaMeasureMode[] $VALUES = new YogaMeasureMode[]{UNDEFINED, EXACTLY, AT_MOST};
   AT_MOST("AT_MOST", 2, 2),
   EXACTLY("EXACTLY", 1, 1),
   UNDEFINED("UNDEFINED", 0, 0);
   private int mIntValue;


   private YogaMeasureMode(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaMeasureMode fromInt(int var0) {
      switch(var0) {
      case 0:
         return UNDEFINED;
      case 1:
         return EXACTLY;
      case 2:
         return AT_MOST;
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
