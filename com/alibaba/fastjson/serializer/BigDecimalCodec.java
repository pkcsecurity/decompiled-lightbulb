package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BigDecimalCodec implements ObjectDeserializer, ObjectSerializer {

   public static final BigDecimalCodec instance = new BigDecimalCodec();


   public <T extends Object> T deserialze(DefaultJSONParser var1, Type var2, Object var3) {
      JSONLexer var8 = var1.lexer;
      int var4 = var8.token();
      BigDecimal var6;
      if(var4 == 2) {
         if(var2 == BigInteger.class) {
            String var7 = var8.numberString();
            var8.nextToken(16);
            return new BigInteger(var7, 10);
         } else {
            var6 = var8.decimalValue();
            var8.nextToken(16);
            return var6;
         }
      } else if(var4 == 3) {
         var6 = var8.decimalValue();
         var8.nextToken(16);
         return var2 == BigInteger.class?var6.toBigInteger():var6;
      } else {
         Object var5 = var1.parse();
         return var5 == null?null:(var2 == BigInteger.class?TypeUtils.castToBigInteger(var5):TypeUtils.castToBigDecimal(var5));
      }
   }

   public void write(JSONSerializer var1, Object var2, Object var3, Type var4) throws IOException {
      SerializeWriter var5 = var1.out;
      if(var2 == null) {
         if((var5.features & SerializerFeature.WriteNullNumberAsZero.mask) != 0) {
            var5.write(48);
         } else {
            var5.writeNull();
         }
      } else if(var2 instanceof BigInteger) {
         var5.write(((BigInteger)var2).toString());
      } else {
         BigDecimal var6 = (BigDecimal)var2;
         var5.write(var6.toString());
         if((var5.features & SerializerFeature.WriteClassName.mask) != 0 && var4 != BigDecimal.class && var6.scale() == 0) {
            var5.write(46);
         }

      }
   }
}
