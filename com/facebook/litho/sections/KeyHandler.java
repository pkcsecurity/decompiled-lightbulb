package com.facebook.litho.sections;

import java.util.HashSet;
import java.util.Set;

public class KeyHandler {

   private final Set<String> mKnownGlobalKeys = new HashSet();


   public boolean hasKey(String var1) {
      return this.mKnownGlobalKeys.contains(var1);
   }

   public void registerKey(String var1) {
      this.mKnownGlobalKeys.add(var1);
   }
}
