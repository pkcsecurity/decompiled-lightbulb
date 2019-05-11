package com.facebook.react.uimanager.layoutanimation;


enum AnimatedPropertyType {

   // $FF: synthetic field
   private static final AnimatedPropertyType[] $VALUES = new AnimatedPropertyType[]{OPACITY, SCALE_XY};
   OPACITY("OPACITY", 0, "opacity"),
   SCALE_XY("SCALE_XY", 1, "scaleXY");
   private final String mName;


   private AnimatedPropertyType(String var1, int var2, String var3) {
      this.mName = var3;
   }

   public static AnimatedPropertyType fromString(String var0) {
      AnimatedPropertyType[] var3 = values();
      int var2 = var3.length;

      for(int var1 = 0; var1 < var2; ++var1) {
         AnimatedPropertyType var4 = var3[var1];
         if(var4.toString().equalsIgnoreCase(var0)) {
            return var4;
         }
      }

      StringBuilder var5 = new StringBuilder();
      var5.append("Unsupported animated property : ");
      var5.append(var0);
      throw new IllegalArgumentException(var5.toString());
   }

   public String toString() {
      return this.mName;
   }
}
