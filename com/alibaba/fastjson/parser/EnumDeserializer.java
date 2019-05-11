package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;
import java.util.Arrays;

public class EnumDeserializer implements ObjectDeserializer {

   private final Class<?> enumClass;
   protected long[] enumNameHashCodes;
   protected final Enum[] enums;
   protected final Enum[] ordinalEnums;


   public EnumDeserializer(Class<?> var1) {
      this.enumClass = var1;
      this.ordinalEnums = (Enum[])var1.getEnumConstants();
      long[] var7 = new long[this.ordinalEnums.length];
      this.enumNameHashCodes = new long[this.ordinalEnums.length];

      int var2;
      int var3;
      for(var2 = 0; var2 < this.ordinalEnums.length; ++var2) {
         String var6 = this.ordinalEnums[var2].name();
         long var4 = -3750763034362895579L;

         for(var3 = 0; var3 < var6.length(); ++var3) {
            var4 = 1099511628211L * (var4 ^ (long)var6.charAt(var3));
         }

         var7[var2] = var4;
         this.enumNameHashCodes[var2] = var4;
      }

      Arrays.sort(this.enumNameHashCodes);
      this.enums = new Enum[this.ordinalEnums.length];
      var2 = 0;

      while(var2 < this.enumNameHashCodes.length) {
         var3 = 0;

         while(true) {
            if(var3 < var7.length) {
               if(this.enumNameHashCodes[var2] != var7[var3]) {
                  ++var3;
                  continue;
               }

               this.enums[var2] = this.ordinalEnums[var3];
            }

            ++var2;
            break;
         }
      }

   }

   public <T extends Object> T deserialze(DefaultJSONParser param1, Type param2, Object param3) {
      // $FF: Couldn't be decompiled
   }
}
