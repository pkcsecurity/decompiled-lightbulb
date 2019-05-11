package com.facebook.common.util;

import com.facebook.infer.annotation.Functional;

public enum TriState {

   // $FF: synthetic field
   private static final TriState[] $VALUES = new TriState[]{YES, NO, UNSET};
   NO("NO", 1),
   UNSET("UNSET", 2),
   YES("YES", 0);


   private TriState(String var1, int var2) {}

   @Functional
   public static TriState fromDbValue(int var0) {
      switch(var0) {
      case 1:
         return YES;
      case 2:
         return NO;
      default:
         return UNSET;
      }
   }

   @Functional
   public static TriState valueOf(Boolean var0) {
      return var0 != null?valueOf(var0.booleanValue()):UNSET;
   }

   @Functional
   public static TriState valueOf(boolean var0) {
      return var0?YES:NO;
   }

   @Functional
   public boolean asBoolean() {
      switch(null.$SwitchMap$com$facebook$common$util$TriState[this.ordinal()]) {
      case 1:
         return true;
      case 2:
         return false;
      case 3:
         throw new IllegalStateException("No boolean equivalent for UNSET");
      default:
         StringBuilder var1 = new StringBuilder();
         var1.append("Unrecognized TriState value: ");
         var1.append(this);
         throw new IllegalStateException(var1.toString());
      }
   }

   @Functional
   public boolean asBoolean(boolean var1) {
      switch(null.$SwitchMap$com$facebook$common$util$TriState[this.ordinal()]) {
      case 1:
         return true;
      case 2:
         return false;
      case 3:
         return var1;
      default:
         StringBuilder var2 = new StringBuilder();
         var2.append("Unrecognized TriState value: ");
         var2.append(this);
         throw new IllegalStateException(var2.toString());
      }
   }

   @Functional
   public Boolean asBooleanObject() {
      switch(null.$SwitchMap$com$facebook$common$util$TriState[this.ordinal()]) {
      case 1:
         return Boolean.TRUE;
      case 2:
         return Boolean.FALSE;
      case 3:
         return null;
      default:
         StringBuilder var1 = new StringBuilder();
         var1.append("Unrecognized TriState value: ");
         var1.append(this);
         throw new IllegalStateException(var1.toString());
      }
   }

   @Functional
   public int getDbValue() {
      switch(null.$SwitchMap$com$facebook$common$util$TriState[this.ordinal()]) {
      case 1:
         return 1;
      case 2:
         return 2;
      default:
         return 3;
      }
   }

   @Functional
   public boolean isSet() {
      return this != UNSET;
   }
}
