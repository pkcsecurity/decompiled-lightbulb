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
import java.text.DecimalFormat;

public class NumberCodec implements ObjectDeserializer, ObjectSerializer {

   public static final NumberCodec instance = new NumberCodec();
   private DecimalFormat decimalFormat;


   private NumberCodec() {
      this.decimalFormat = null;
   }

   public NumberCodec(String var1) {
      this(new DecimalFormat(var1));
   }

   public NumberCodec(DecimalFormat var1) {
      this.decimalFormat = null;
      this.decimalFormat = var1;
   }

   public <T extends Object> T deserialze(DefaultJSONParser var1, Type var2, Object var3) {
      JSONLexer var10 = var1.lexer;
      int var4 = var10.token();
      String var8;
      if(var4 == 2) {
         if(var2 != Double.TYPE && var2 != Double.class) {
            if(var2 != Float.TYPE && var2 != Float.class) {
               long var5 = var10.longValue();
               var10.nextToken(16);
               return var2 != Short.TYPE && var2 != Short.class?(var2 != Byte.TYPE && var2 != Byte.class?(var5 >= -2147483648L && var5 <= 2147483647L?Integer.valueOf((int)var5):Long.valueOf(var5)):Byte.valueOf((byte)((int)var5))):Short.valueOf((short)((int)var5));
            } else {
               var8 = var10.numberString();
               var10.nextToken(16);
               return Float.valueOf(Float.parseFloat(var8));
            }
         } else {
            var8 = var10.numberString();
            var10.nextToken(16);
            return Double.valueOf(Double.parseDouble(var8));
         }
      } else if(var4 == 3) {
         if(var2 != Double.TYPE && var2 != Double.class) {
            if(var2 != Float.TYPE && var2 != Float.class) {
               BigDecimal var9 = var10.decimalValue();
               var10.nextToken(16);
               return var2 != Short.TYPE && var2 != Short.class?(var2 != Byte.TYPE && var2 != Byte.class?var9:Byte.valueOf(var9.byteValue())):Short.valueOf(var9.shortValue());
            } else {
               var8 = var10.numberString();
               var10.nextToken(16);
               return Float.valueOf(Float.parseFloat(var8));
            }
         } else {
            var8 = var10.numberString();
            var10.nextToken(16);
            return Double.valueOf(Double.parseDouble(var8));
         }
      } else {
         Object var7 = var1.parse();
         return var7 == null?null:(var2 != Double.TYPE && var2 != Double.class?(var2 != Float.TYPE && var2 != Float.class?(var2 != Short.TYPE && var2 != Short.class?(var2 != Byte.TYPE && var2 != Byte.class?TypeUtils.castToBigDecimal(var7):TypeUtils.castToByte(var7)):TypeUtils.castToShort(var7)):TypeUtils.castToFloat(var7)):TypeUtils.castToDouble(var7));
      }
   }

   public void write(JSONSerializer var1, Object var2, Object var3, Type var4) throws IOException {
      SerializeWriter var10 = var1.out;
      if(var2 == null) {
         if((var10.features & SerializerFeature.WriteNullNumberAsZero.mask) != 0) {
            var10.write(48);
         } else {
            var10.writeNull();
         }
      } else {
         String var8;
         String var9;
         if(var2 instanceof Float) {
            float var7 = ((Float)var2).floatValue();
            if(Float.isNaN(var7)) {
               var10.writeNull();
            } else if(Float.isInfinite(var7)) {
               var10.writeNull();
            } else {
               var9 = Float.toString(var7);
               var8 = var9;
               if(var9.endsWith(".0")) {
                  var8 = var9.substring(0, var9.length() - 2);
               }

               var10.write(var8);
               if((var10.features & SerializerFeature.WriteClassName.mask) != 0) {
                  var10.write(70);
               }

            }
         } else {
            double var5 = ((Double)var2).doubleValue();
            if(Double.isNaN(var5)) {
               var10.writeNull();
            } else if(Double.isInfinite(var5)) {
               var10.writeNull();
            } else {
               if(this.decimalFormat == null) {
                  var9 = Double.toString(var5);
                  var8 = var9;
                  if(var9.endsWith(".0")) {
                     var8 = var9.substring(0, var9.length() - 2);
                  }
               } else {
                  var8 = this.decimalFormat.format(var5);
               }

               var10.append(var8);
               if((var10.features & SerializerFeature.WriteClassName.mask) != 0) {
                  var10.write(68);
               }

            }
         }
      }
   }
}
