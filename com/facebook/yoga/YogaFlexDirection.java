package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaFlexDirection {

   // $FF: synthetic field
   private static final YogaFlexDirection[] $VALUES = new YogaFlexDirection[]{COLUMN, COLUMN_REVERSE, ROW, ROW_REVERSE};
   COLUMN("COLUMN", 0, 0),
   COLUMN_REVERSE("COLUMN_REVERSE", 1, 1),
   ROW("ROW", 2, 2),
   ROW_REVERSE("ROW_REVERSE", 3, 3);
   private int mIntValue;


   private YogaFlexDirection(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaFlexDirection fromInt(int var0) {
      switch(var0) {
      case 0:
         return COLUMN;
      case 1:
         return COLUMN_REVERSE;
      case 2:
         return ROW;
      case 3:
         return ROW_REVERSE;
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
