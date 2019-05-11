package com.facebook.imagepipeline.common;

import javax.annotation.Nullable;

public enum Priority {

   // $FF: synthetic field
   private static final Priority[] $VALUES = new Priority[]{LOW, MEDIUM, HIGH};
   HIGH("HIGH", 2),
   LOW("LOW", 0),
   MEDIUM("MEDIUM", 1);


   private Priority(String var1, int var2) {}

   public static Priority getHigherPriority(@Nullable Priority var0, @Nullable Priority var1) {
      return var0 == null?var1:(var1 == null?var0:(var0.ordinal() > var1.ordinal()?var0:var1));
   }
}
