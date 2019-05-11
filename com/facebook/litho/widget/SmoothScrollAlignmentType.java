package com.facebook.litho.widget;


public enum SmoothScrollAlignmentType {

   // $FF: synthetic field
   private static final SmoothScrollAlignmentType[] $VALUES = new SmoothScrollAlignmentType[]{DEFAULT, SNAP_TO_ANY, SNAP_TO_START, SNAP_TO_END, SNAP_TO_CENTER};
   DEFAULT("DEFAULT", 0, -5),
   SNAP_TO_ANY("SNAP_TO_ANY", 1, 0),
   SNAP_TO_CENTER("SNAP_TO_CENTER", 4, -6),
   SNAP_TO_END("SNAP_TO_END", 3, 1),
   SNAP_TO_START("SNAP_TO_START", 2, -1);
   private int value;


   private SmoothScrollAlignmentType(String var1, int var2, int var3) {
      this.value = var3;
   }

   public int getValue() {
      return this.value;
   }
}
