package com.facebook.react.bridge;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public enum ReadableType {

   // $FF: synthetic field
   private static final ReadableType[] $VALUES = new ReadableType[]{Null, Boolean, Number, String, Map, Array};
   Array("Array", 5),
   Boolean("Boolean", 1),
   Map("Map", 4),
   Null("Null", 0),
   Number("Number", 2),
   String("String", 3);


   private ReadableType(String var1, int var2) {}
}
