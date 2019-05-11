package com.alibaba.fastjson.serializer;


public enum SerializerFeature {

   // $FF: synthetic field
   private static final SerializerFeature[] $VALUES = new SerializerFeature[]{QuoteFieldNames, UseSingleQuotes, WriteMapNullValue, WriteEnumUsingToString, UseISO8601DateFormat, WriteNullListAsEmpty, WriteNullStringAsEmpty, WriteNullNumberAsZero, WriteNullBooleanAsFalse, SkipTransientField, SortField, WriteTabAsSpecial, PrettyFormat, WriteClassName, DisableCircularReferenceDetect, WriteSlashAsSpecial, WriteDateUseDateFormat, NotWriteRootClassName, DisableCheckSpecialChar, BeanToArray, WriteNonStringKeyAsString, NotWriteDefaultValue};
   BeanToArray("BeanToArray", 19),
   DisableCheckSpecialChar("DisableCheckSpecialChar", 18),
   DisableCircularReferenceDetect("DisableCircularReferenceDetect", 14);
   public static final SerializerFeature[] EMPTY = new SerializerFeature[0];
   NotWriteDefaultValue("NotWriteDefaultValue", 21),
   NotWriteRootClassName("NotWriteRootClassName", 17),
   PrettyFormat("PrettyFormat", 12),
   QuoteFieldNames("QuoteFieldNames", 0),
   SkipTransientField("SkipTransientField", 9),
   SortField("SortField", 10),
   UseISO8601DateFormat("UseISO8601DateFormat", 4),
   UseSingleQuotes("UseSingleQuotes", 1),
   WriteClassName("WriteClassName", 13),
   WriteDateUseDateFormat("WriteDateUseDateFormat", 16),
   WriteEnumUsingToString("WriteEnumUsingToString", 3),
   WriteMapNullValue("WriteMapNullValue", 2),
   WriteNonStringKeyAsString("WriteNonStringKeyAsString", 20),
   WriteNullBooleanAsFalse("WriteNullBooleanAsFalse", 8),
   WriteNullListAsEmpty("WriteNullListAsEmpty", 5),
   WriteNullNumberAsZero("WriteNullNumberAsZero", 7),
   WriteNullStringAsEmpty("WriteNullStringAsEmpty", 6),
   WriteSlashAsSpecial("WriteSlashAsSpecial", 15),
   @Deprecated
   WriteTabAsSpecial("WriteTabAsSpecial", 11);
   public final int mask = 1 << this.ordinal();


   private SerializerFeature(String var1, int var2) {}

   public static int of(SerializerFeature[] var0) {
      int var1 = 0;
      if(var0 == null) {
         return 0;
      } else {
         int var3 = var0.length;

         int var2;
         for(var2 = 0; var1 < var3; ++var1) {
            var2 |= var0[var1].mask;
         }

         return var2;
      }
   }
}
