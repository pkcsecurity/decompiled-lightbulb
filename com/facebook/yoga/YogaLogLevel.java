package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum YogaLogLevel {

   // $FF: synthetic field
   private static final YogaLogLevel[] $VALUES = new YogaLogLevel[]{ERROR, WARN, INFO, DEBUG, VERBOSE, FATAL};
   DEBUG("DEBUG", 3, 3),
   ERROR("ERROR", 0, 0),
   FATAL("FATAL", 5, 5),
   INFO("INFO", 2, 2),
   VERBOSE("VERBOSE", 4, 4),
   WARN("WARN", 1, 1);
   private int mIntValue;


   private YogaLogLevel(String var1, int var2, int var3) {
      this.mIntValue = var3;
   }

   public static YogaLogLevel fromInt(int var0) {
      switch(var0) {
      case 0:
         return ERROR;
      case 1:
         return WARN;
      case 2:
         return INFO;
      case 3:
         return DEBUG;
      case 4:
         return VERBOSE;
      case 5:
         return FATAL;
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
