package com.facebook.litho.widget;


public enum EditTextStateUpdatePolicy {

   // $FF: synthetic field
   private static final EditTextStateUpdatePolicy[] $VALUES = new EditTextStateUpdatePolicy[]{NO_UPDATES, ONLY_LAZY_UPDATES, UPDATE_ON_LINE_COUNT_CHANGE, UPDATE_ON_TEXT_CHANGE};
   NO_UPDATES("NO_UPDATES", 0),
   ONLY_LAZY_UPDATES("ONLY_LAZY_UPDATES", 1),
   UPDATE_ON_LINE_COUNT_CHANGE("UPDATE_ON_LINE_COUNT_CHANGE", 2),
   UPDATE_ON_TEXT_CHANGE("UPDATE_ON_TEXT_CHANGE", 3);


   private EditTextStateUpdatePolicy(String var1, int var2) {}
}
