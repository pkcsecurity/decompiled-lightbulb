package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaPrintOptions {

   // $FF: synthetic field
   private static final YogaPrintOptions[] $VALUES = new YogaPrintOptions[]{LAYOUT, STYLE, CHILDREN};
   CHILDREN("CHILDREN", 2, 4),
   LAYOUT("LAYOUT", 0, 1),
   STYLE("STYLE", 1, 2);
   private int mIntValue;


   private YogaPrintOptions(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaPrintOptions fromInt(int var0) {
      if(var0 != 4) {
         switch(var0) {
         case 1:
            return LAYOUT;
         case 2:
            return STYLE;
         default:
            StringBuilder var1 = new StringBuilder();
            var1.append("Unknown enum value: ");
            var1.append(var0);
            throw new IllegalArgumentException(var1.toString());
         }
      } else {
         return CHILDREN;
      }
   }

   public int intValue() {
      return this.mIntValue;
   }
}
