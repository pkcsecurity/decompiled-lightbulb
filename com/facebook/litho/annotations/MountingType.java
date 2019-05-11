package com.facebook.litho.annotations;


public enum MountingType {

   // $FF: synthetic field
   private static final MountingType[] $VALUES = new MountingType[]{INFER, VIEW, DRAWABLE};
   DRAWABLE("DRAWABLE", 2),
   INFER("INFER", 0),
   VIEW("VIEW", 1);


   private MountingType(String var1, int var2) {}
}
