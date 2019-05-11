package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaEdge {

   // $FF: synthetic field
   private static final YogaEdge[] $VALUES = new YogaEdge[]{LEFT, TOP, RIGHT, BOTTOM, START, END, HORIZONTAL, VERTICAL, ALL};
   ALL("ALL", 8, 8),
   BOTTOM("BOTTOM", 3, 3),
   END("END", 5, 5),
   HORIZONTAL("HORIZONTAL", 6, 6),
   LEFT("LEFT", 0, 0),
   RIGHT("RIGHT", 2, 2),
   START("START", 4, 4),
   TOP("TOP", 1, 1),
   VERTICAL("VERTICAL", 7, 7);
   private int mIntValue;


   private YogaEdge(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaEdge fromInt(int var0) {
      switch(var0) {
      case 0:
         return LEFT;
      case 1:
         return TOP;
      case 2:
         return RIGHT;
      case 3:
         return BOTTOM;
      case 4:
         return START;
      case 5:
         return END;
      case 6:
         return HORIZONTAL;
      case 7:
         return VERTICAL;
      case 8:
         return ALL;
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
