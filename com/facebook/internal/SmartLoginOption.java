package com.facebook.internal;

import java.util.EnumSet;
import java.util.Iterator;

public enum SmartLoginOption {

   // $FF: synthetic field
   private static final SmartLoginOption[] $VALUES = new SmartLoginOption[]{None, Enabled, RequireConfirm};
   public static final EnumSet<SmartLoginOption> ALL = EnumSet.allOf(SmartLoginOption.class);
   Enabled("Enabled", 1, 1L),
   None("None", 0, 0L),
   RequireConfirm("RequireConfirm", 2, 2L);
   private final long mValue;


   private SmartLoginOption(String var1, int var2, long var3) {
      this.mValue = var3;
   }

   public static EnumSet<SmartLoginOption> parseOptions(long var0) {
      EnumSet var2 = EnumSet.noneOf(SmartLoginOption.class);
      Iterator var3 = ALL.iterator();

      while(var3.hasNext()) {
         SmartLoginOption var4 = (SmartLoginOption)var3.next();
         if((var0 & var4.getValue()) != 0L) {
            var2.add(var4);
         }
      }

      return var2;
   }

   public long getValue() {
      return this.mValue;
   }
}
