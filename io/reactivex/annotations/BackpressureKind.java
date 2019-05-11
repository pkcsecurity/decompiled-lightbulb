package io.reactivex.annotations;


public enum BackpressureKind {

   // $FF: synthetic field
   private static final BackpressureKind[] $VALUES = new BackpressureKind[]{PASS_THROUGH, FULL, SPECIAL, UNBOUNDED_IN, ERROR, NONE};
   ERROR("ERROR", 4),
   FULL("FULL", 1),
   NONE("NONE", 5),
   PASS_THROUGH("PASS_THROUGH", 0),
   SPECIAL("SPECIAL", 2),
   UNBOUNDED_IN("UNBOUNDED_IN", 3);


   private BackpressureKind(String var1, int var2) {}
}
