package com.fasterxml.jackson.core;


public enum JsonToken {

   // $FF: synthetic field
   private static final JsonToken[] $VALUES = new JsonToken[]{NOT_AVAILABLE, START_OBJECT, END_OBJECT, START_ARRAY, END_ARRAY, FIELD_NAME, VALUE_EMBEDDED_OBJECT, VALUE_STRING, VALUE_NUMBER_INT, VALUE_NUMBER_FLOAT, VALUE_TRUE, VALUE_FALSE, VALUE_NULL};
   END_ARRAY("END_ARRAY", 4, "]"),
   END_OBJECT("END_OBJECT", 2, "}"),
   FIELD_NAME("FIELD_NAME", 5, (String)null),
   NOT_AVAILABLE("NOT_AVAILABLE", 0, (String)null),
   START_ARRAY("START_ARRAY", 3, "["),
   START_OBJECT("START_OBJECT", 1, "{"),
   VALUE_EMBEDDED_OBJECT("VALUE_EMBEDDED_OBJECT", 6, (String)null),
   VALUE_FALSE("VALUE_FALSE", 11, "false"),
   VALUE_NULL("VALUE_NULL", 12, "null"),
   VALUE_NUMBER_FLOAT("VALUE_NUMBER_FLOAT", 9, (String)null),
   VALUE_NUMBER_INT("VALUE_NUMBER_INT", 8, (String)null),
   VALUE_STRING("VALUE_STRING", 7, (String)null),
   VALUE_TRUE("VALUE_TRUE", 10, "true");
   final String _serialized;
   final byte[] _serializedBytes;
   final char[] _serializedChars;


   private JsonToken(String var1, int var2, String var3) {
      if(var3 == null) {
         this._serialized = null;
         this._serializedChars = null;
         this._serializedBytes = null;
      } else {
         this._serialized = var3;
         this._serializedChars = var3.toCharArray();
         int var4 = this._serializedChars.length;
         this._serializedBytes = new byte[var4];

         for(var2 = 0; var2 < var4; ++var2) {
            this._serializedBytes[var2] = (byte)this._serializedChars[var2];
         }

      }
   }

   public byte[] asByteArray() {
      return this._serializedBytes;
   }

   public char[] asCharArray() {
      return this._serializedChars;
   }

   public String asString() {
      return this._serialized;
   }

   public boolean isNumeric() {
      return this == VALUE_NUMBER_INT || this == VALUE_NUMBER_FLOAT;
   }

   public boolean isScalarValue() {
      return this.ordinal() >= VALUE_EMBEDDED_OBJECT.ordinal();
   }
}
