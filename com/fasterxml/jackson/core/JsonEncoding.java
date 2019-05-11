package com.fasterxml.jackson.core;


public enum JsonEncoding {

   // $FF: synthetic field
   private static final JsonEncoding[] $VALUES = new JsonEncoding[]{UTF8, UTF16_BE, UTF16_LE, UTF32_BE, UTF32_LE};
   UTF16_BE("UTF16_BE", 1, "UTF-16BE", true),
   UTF16_LE("UTF16_LE", 2, "UTF-16LE", false),
   UTF32_BE("UTF32_BE", 3, "UTF-32BE", true),
   UTF32_LE("UTF32_LE", 4, "UTF-32LE", false),
   UTF8("UTF8", 0, "UTF-8", false);
   protected final boolean _bigEndian;
   protected final String _javaName;


   private JsonEncoding(String var1, int var2, String var3, boolean var4) {
      this._javaName = var3;
      this._bigEndian = var4;
   }

   public String getJavaName() {
      return this._javaName;
   }

   public boolean isBigEndian() {
      return this._bigEndian;
   }
}
