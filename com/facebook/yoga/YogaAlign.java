package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaAlign {

   // $FF: synthetic field
   private static final YogaAlign[] $VALUES = new YogaAlign[]{AUTO, FLEX_START, CENTER, FLEX_END, STRETCH, BASELINE, SPACE_BETWEEN, SPACE_AROUND};
   AUTO("AUTO", 0, 0),
   BASELINE("BASELINE", 5, 5),
   CENTER("CENTER", 2, 2),
   FLEX_END("FLEX_END", 3, 3),
   FLEX_START("FLEX_START", 1, 1),
   SPACE_AROUND("SPACE_AROUND", 7, 7),
   SPACE_BETWEEN("SPACE_BETWEEN", 6, 6),
   STRETCH("STRETCH", 4, 4);
   private int mIntValue;


   private YogaAlign(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaAlign fromInt(int var0) {
      switch(var0) {
      case 0:
         return AUTO;
      case 1:
         return FLEX_START;
      case 2:
         return CENTER;
      case 3:
         return FLEX_END;
      case 4:
         return STRETCH;
      case 5:
         return BASELINE;
      case 6:
         return SPACE_BETWEEN;
      case 7:
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
