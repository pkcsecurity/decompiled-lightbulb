package com.alibaba.fastjson.parser;


public enum Feature {

   // $FF: synthetic field
   private static final Feature[] $VALUES = new Feature[]{AutoCloseSource, AllowComment, AllowUnQuotedFieldNames, AllowSingleQuotes, InternFieldNames, AllowISO8601DateFormat, AllowArbitraryCommas, UseBigDecimal, IgnoreNotMatch, SortFeidFastMatch, DisableASM, DisableCircularReferenceDetect, InitStringFieldAsEmpty, SupportArrayToBean, OrderedField, DisableSpecialKeyDetect, SupportNonPublicField, SupportAutoType};
   AllowArbitraryCommas("AllowArbitraryCommas", 6),
   AllowComment("AllowComment", 1),
   AllowISO8601DateFormat("AllowISO8601DateFormat", 5),
   AllowSingleQuotes("AllowSingleQuotes", 3),
   AllowUnQuotedFieldNames("AllowUnQuotedFieldNames", 2),
   AutoCloseSource("AutoCloseSource", 0),
   DisableASM("DisableASM", 10),
   DisableCircularReferenceDetect("DisableCircularReferenceDetect", 11),
   DisableSpecialKeyDetect("DisableSpecialKeyDetect", 15),
   IgnoreNotMatch("IgnoreNotMatch", 8),
   InitStringFieldAsEmpty("InitStringFieldAsEmpty", 12),
   InternFieldNames("InternFieldNames", 4),
   OrderedField("OrderedField", 14),
   SortFeidFastMatch("SortFeidFastMatch", 9),
   SupportArrayToBean("SupportArrayToBean", 13),
   SupportAutoType("SupportAutoType", 17),
   SupportNonPublicField("SupportNonPublicField", 16),
   UseBigDecimal("UseBigDecimal", 7);
   public final int mask = 1 << this.ordinal();


   private Feature(String var1, int var2) {}
}
