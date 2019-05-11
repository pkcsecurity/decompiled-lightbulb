package com.fasterxml.jackson.core.format;


public enum MatchStrength {

   // $FF: synthetic field
   private static final MatchStrength[] $VALUES = new MatchStrength[]{NO_MATCH, INCONCLUSIVE, WEAK_MATCH, SOLID_MATCH, FULL_MATCH};
   FULL_MATCH("FULL_MATCH", 4),
   INCONCLUSIVE("INCONCLUSIVE", 1),
   NO_MATCH("NO_MATCH", 0),
   SOLID_MATCH("SOLID_MATCH", 3),
   WEAK_MATCH("WEAK_MATCH", 2);


   private MatchStrength(String var1, int var2) {}
}
