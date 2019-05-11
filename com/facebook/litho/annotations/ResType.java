package com.facebook.litho.annotations;


public enum ResType {

   // $FF: synthetic field
   private static final ResType[] $VALUES = new ResType[]{NONE, STRING, STRING_ARRAY, INT, INT_ARRAY, BOOL, COLOR, DIMEN_SIZE, DIMEN_OFFSET, DIMEN_TEXT, FLOAT, DRAWABLE};
   BOOL("BOOL", 5),
   COLOR("COLOR", 6),
   DIMEN_OFFSET("DIMEN_OFFSET", 8),
   DIMEN_SIZE("DIMEN_SIZE", 7),
   DIMEN_TEXT("DIMEN_TEXT", 9),
   DRAWABLE("DRAWABLE", 11),
   FLOAT("FLOAT", 10),
   INT("INT", 3),
   INT_ARRAY("INT_ARRAY", 4),
   NONE("NONE", 0),
   STRING("STRING", 1),
   STRING_ARRAY("STRING_ARRAY", 2);


   private ResType(String var1, int var2) {}
}
