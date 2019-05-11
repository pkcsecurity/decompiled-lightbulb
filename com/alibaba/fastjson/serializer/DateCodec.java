package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateCodec implements ObjectDeserializer, ObjectSerializer {

   public static final DateCodec instance = new DateCodec();


   protected <T extends Object> T cast(DefaultJSONParser param1, Type param2, Object param3, Object param4, String param5) {
      // $FF: Couldn't be decompiled
   }

   public <T extends Object> T deserialze(DefaultJSONParser var1, Type var2, Object var3) {
      return this.deserialze(var1, var2, var3, (String)null);
   }

   public <T extends Object> T deserialze(DefaultJSONParser var1, Type var2, Object var3, String var4) {
      Object var8;
      JSONLexer var11;
      label70: {
         var11 = var1.lexer;
         int var5 = var11.token();
         Object var9;
         if(var5 == 2) {
            var8 = Long.valueOf(var11.longValue());
            var11.nextToken(16);
            var9 = var2;
         } else if(var5 == 4) {
            String var10 = var11.stringVal();
            var11.nextToken(16);
            var8 = var10;
            var9 = var2;
            if((var11.features & Feature.AllowISO8601DateFormat.mask) != 0) {
               JSONLexer var17 = new JSONLexer(var10);
               var8 = var10;
               if(var17.scanISO8601DateIfMatch(true)) {
                  Calendar var16 = var17.calendar;
                  if(var2 == Calendar.class) {
                     var17.close();
                     return var16;
                  }

                  var8 = var16.getTime();
               }

               var17.close();
               var9 = var2;
            }
         } else {
            if(var5 == 8) {
               var11.nextToken();
               var8 = null;
               break label70;
            }

            if(var5 == 12) {
               var11.nextToken();
               if(var11.token() != 4) {
                  throw new JSONException("syntax error");
               }

               var9 = var2;
               if("@type".equals(var11.stringVal())) {
                  var11.nextToken();
                  var1.accept(17);
                  String var18 = var11.stringVal();
                  Class var19 = var1.config.checkAutoType(var18, (Class)null, var11.features);
                  if(var19 != null) {
                     var2 = var19;
                  }

                  var1.accept(4);
                  var1.accept(16);
                  var9 = var2;
               }

               var11.nextTokenWithChar(':');
               var5 = var11.token();
               if(var5 != 2) {
                  StringBuilder var12 = new StringBuilder();
                  var12.append("syntax error : ");
                  var12.append(JSONToken.name(var5));
                  throw new JSONException(var12.toString());
               }

               long var6 = var11.longValue();
               var11.nextToken();
               var8 = Long.valueOf(var6);
               var1.accept(13);
            } else if(var1.resolveStatus == 2) {
               var1.resolveStatus = 0;
               var1.accept(16);
               if(var11.token() != 4) {
                  throw new JSONException("syntax error");
               }

               if(!"val".equals(var11.stringVal())) {
                  throw new JSONException("syntax error");
               }

               var11.nextToken();
               var1.accept(17);
               var8 = var1.parse();
               var1.accept(13);
               var9 = var2;
            } else {
               var8 = var1.parse();
               var9 = var2;
            }
         }

         var2 = var9;
      }

      Object var13 = this.cast(var1, (Type)var2, var3, var8, var4);
      if(var2 == Calendar.class) {
         if(var13 instanceof Calendar) {
            return var13;
         } else {
            Date var14 = (Date)var13;
            if(var14 == null) {
               return null;
            } else {
               Calendar var15 = Calendar.getInstance(var11.timeZone, var11.locale);
               var15.setTime(var14);
               return var15;
            }
         }
      } else {
         return var13;
      }
   }

   public void write(JSONSerializer var1, Object var2, Object var3, Type var4) throws IOException {
      SerializeWriter var14 = var1.out;
      if(var2 == null) {
         var14.writeNull();
      } else if((var14.features & SerializerFeature.WriteClassName.mask) != 0 && var2.getClass() != var4) {
         if(var2.getClass() == Date.class) {
            var14.write("new Date(");
            var14.writeLong(((Date)var2).getTime());
            var14.write(41);
         } else {
            var14.write(123);
            var14.writeFieldName("@type", false);
            var1.write(var2.getClass().getName());
            var14.write(44);
            var14.writeFieldName("val", false);
            var14.writeLong(((Date)var2).getTime());
            var14.write(125);
         }
      } else {
         Date var17;
         if(var2 instanceof Calendar) {
            var17 = ((Calendar)var2).getTime();
         } else {
            var17 = (Date)var2;
         }

         if((var14.features & SerializerFeature.WriteDateUseDateFormat.mask) != 0) {
            DateFormat var18 = var1.getDateFormat();
            var3 = var18;
            if(var18 == null) {
               var3 = new SimpleDateFormat(JSON.DEFFAULT_DATE_FORMAT, var1.locale);
               ((DateFormat)var3).setTimeZone(var1.timeZone);
            }

            var14.writeString(((DateFormat)var3).format(var17));
         } else {
            long var12 = var17.getTime();
            if((var14.features & SerializerFeature.UseISO8601DateFormat.mask) != 0) {
               if((var14.features & SerializerFeature.UseSingleQuotes.mask) != 0) {
                  var14.write(39);
               } else {
                  var14.write(34);
               }

               Calendar var15 = Calendar.getInstance(var1.timeZone, var1.locale);
               var15.setTimeInMillis(var12);
               int var5 = var15.get(1);
               int var6 = var15.get(2) + 1;
               int var7 = var15.get(5);
               int var8 = var15.get(11);
               int var9 = var15.get(12);
               int var10 = var15.get(13);
               int var11 = var15.get(14);
               char[] var16;
               if(var11 != 0) {
                  var16 = "0000-00-00T00:00:00.000".toCharArray();
                  SerializeWriter.getChars((long)var11, 23, var16);
                  SerializeWriter.getChars((long)var10, 19, var16);
                  SerializeWriter.getChars((long)var9, 16, var16);
                  SerializeWriter.getChars((long)var8, 13, var16);
                  SerializeWriter.getChars((long)var7, 10, var16);
                  SerializeWriter.getChars((long)var6, 7, var16);
                  SerializeWriter.getChars((long)var5, 4, var16);
               } else if(var10 == 0 && var9 == 0 && var8 == 0) {
                  var16 = "0000-00-00".toCharArray();
                  SerializeWriter.getChars((long)var7, 10, var16);
                  SerializeWriter.getChars((long)var6, 7, var16);
                  SerializeWriter.getChars((long)var5, 4, var16);
               } else {
                  var16 = "0000-00-00T00:00:00".toCharArray();
                  SerializeWriter.getChars((long)var10, 19, var16);
                  SerializeWriter.getChars((long)var9, 16, var16);
                  SerializeWriter.getChars((long)var8, 13, var16);
                  SerializeWriter.getChars((long)var7, 10, var16);
                  SerializeWriter.getChars((long)var6, 7, var16);
                  SerializeWriter.getChars((long)var5, 4, var16);
               }

               var14.write(var16);
               if((var14.features & SerializerFeature.UseSingleQuotes.mask) != 0) {
                  var14.write(39);
               } else {
                  var14.write(34);
               }
            } else {
               var14.writeLong(var12);
            }
         }
      }
   }
}
