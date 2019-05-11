package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaWrap {

   // $FF: synthetic field
   private static final YogaWrap[] $VALUES = new YogaWrap[]{NO_WRAP, WRAP, WRAP_REVERSE};
   NO_WRAP("NO_WRAP", 0, 0),
   WRAP("WRAP", 1, 1),
   WRAP_REVERSE("WRAP_REVERSE", 2, 2);
   private int mIntValue;


   private YogaWrap(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaWrap fromInt(int var0) {
      switch(var0) {
      case 0:
         return NO_WRAP;
      case 1:
         return WRAP;
      case 2:
         return WRAP_REVERSE;
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
