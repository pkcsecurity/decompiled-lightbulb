package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaOverflow {

   // $FF: synthetic field
   private static final YogaOverflow[] $VALUES = new YogaOverflow[]{VISIBLE, HIDDEN, SCROLL};
   HIDDEN("HIDDEN", 1, 1),
   SCROLL("SCROLL", 2, 2),
   VISIBLE("VISIBLE", 0, 0);
   private int mIntValue;


   private YogaOverflow(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaOverflow fromInt(int var0) {
      switch(var0) {
      case 0:
         return VISIBLE;
      case 1:
         return HIDDEN;
      case 2:
         return SCROLL;
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
