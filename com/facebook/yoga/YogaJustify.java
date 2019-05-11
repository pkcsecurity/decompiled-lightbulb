package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaJustify {

   // $FF: synthetic field
   private static final YogaJustify[] $VALUES = new YogaJustify[]{FLEX_START, CENTER, FLEX_END, SPACE_BETWEEN, SPACE_AROUND};
   CENTER("CENTER", 1, 1),
   FLEX_END("FLEX_END", 2, 2),
   FLEX_START("FLEX_START", 0, 0),
   SPACE_AROUND("SPACE_AROUND", 4, 4),
   SPACE_BETWEEN("SPACE_BETWEEN", 3, 3);
   private int mIntValue;


   private YogaJustify(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaJustify fromInt(int var0) {
      switch(var0) {
      case 0:
         return FLEX_START;
      case 1:
         return CENTER;
      case 2:
         return FLEX_END;
      case 3:
         return SPACE_BETWEEN;
      case 4:
         return SPACE_AROUND;
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
