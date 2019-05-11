package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaNodeType {

   // $FF: synthetic field
   private static final YogaNodeType[] $VALUES = new YogaNodeType[]{DEFAULT, TEXT};
   DEFAULT("DEFAULT", 0, 0),
   TEXT("TEXT", 1, 1);
   private int mIntValue;


   private YogaNodeType(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaNodeType fromInt(int var0) {
      switch(var0) {
      case 0:
         return DEFAULT;
      case 1:
         return TEXT;
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
