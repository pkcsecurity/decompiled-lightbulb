package com.facebook.react.common;


public enum LifecycleState {

   // $FF: synthetic field
   private static final LifecycleState[] $VALUES = new LifecycleState[]{PRE_LOAD, BEFORE_CREATE, BEFORE_RESUME, RESUMED};
   BEFORE_CREATE("BEFORE_CREATE", 1),
   BEFORE_RESUME("BEFORE_RESUME", 2),
   PRE_LOAD("PRE_LOAD", 0),
   RESUMED("RESUMED", 3);


   private LifecycleState(String var1, int var2) {}
}
