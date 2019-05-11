package com.facebook.react.uimanager.layoutanimation;


enum LayoutAnimationType {

   // $FF: synthetic field
   private static final LayoutAnimationType[] $VALUES = new LayoutAnimationType[]{CREATE, UPDATE, DELETE};
   CREATE("CREATE", 0, "create"),
   DELETE("DELETE", 2, "delete"),
   UPDATE("UPDATE", 1, "update");
   private final String mName;


   private LayoutAnimationType(String var1, int var2, String var3) {
      this.mName = var3;
   }

   public String toString() {
      return this.mName;
   }
}
