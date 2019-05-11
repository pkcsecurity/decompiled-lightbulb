package com.facebook.react.uimanager.layoutanimation;


enum InterpolatorType {

   // $FF: synthetic field
   private static final InterpolatorType[] $VALUES = new InterpolatorType[]{LINEAR, EASE_IN, EASE_OUT, EASE_IN_EASE_OUT, SPRING};
   EASE_IN("EASE_IN", 1, "easeIn"),
   EASE_IN_EASE_OUT("EASE_IN_EASE_OUT", 3, "easeInEaseOut"),
   EASE_OUT("EASE_OUT", 2, "easeOut"),
   LINEAR("LINEAR", 0, "linear"),
   SPRING("SPRING", 4, "spring");
   private final String mName;


   private InterpolatorType(String var1, int var2, String var3) {
      this.mName = var3;
   }

   public static InterpolatorType fromString(String var0) {
      InterpolatorType[] var3 = values();
      int var2 = var3.length;

      for(int var1 = 0; var1 < var2; ++var1) {
         InterpolatorType var4 = var3[var1];
         if(var4.toString().equalsIgnoreCase(var0)) {
            return var4;
         }
      }

      StringBuilder var5 = new StringBuilder();
      var5.append("Unsupported interpolation type : ");
      var5.append(var0);
      throw new IllegalArgumentException(var5.toString());
   }

   public String toString() {
      return this.mName;
   }
}
