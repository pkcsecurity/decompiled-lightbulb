package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public final class MiscCodec implements ObjectDeserializer, ObjectSerializer {

   public static final MiscCodec instance = new MiscCodec();


   public <T extends Object> T deserialze(DefaultJSONParser var1, Type var2, Object var3) {
      if(var2 == StackTraceElement.class) {
         return this.parseStackTraceElement(var1);
      } else {
         JSONLexer var11 = var1.lexer;
         if(var1.resolveStatus == 2) {
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
            var3 = var1.parse();
            var1.accept(13);
         } else {
            var3 = var1.parse();
         }

         if(var3 == null) {
            return null;
         } else {
            String var12;
            if(var3 instanceof String) {
               var12 = (String)var3;
               if(var12.length() == 0) {
                  return null;
               } else if(var2 == UUID.class) {
                  return UUID.fromString(var12);
               } else if(var2 == Class.class) {
                  return TypeUtils.loadClass(var12, var1.config.defaultClassLoader);
               } else if(var2 == Locale.class) {
                  String[] var9 = var12.split("_");
                  return var9.length == 1?new Locale(var9[0]):(var9.length == 2?new Locale(var9[0], var9[1]):new Locale(var9[0], var9[1], var9[2]));
               } else if(var2 == URI.class) {
                  return URI.create(var12);
               } else if(var2 == URL.class) {
                  try {
                     URL var8 = new URL(var12);
                     return var8;
                  } catch (MalformedURLException var4) {
                     throw new JSONException("create url error", var4);
                  }
               } else if(var2 == Pattern.class) {
                  return Pattern.compile(var12);
               } else if(var2 == Charset.class) {
                  return Charset.forName(var12);
               } else if(var2 == Currency.class) {
                  return Currency.getInstance(var12);
               } else if(var2 == SimpleDateFormat.class) {
                  SimpleDateFormat var10 = new SimpleDateFormat(var12, var1.lexer.locale);
                  var10.setTimeZone(var1.lexer.timeZone);
                  return var10;
               } else if(var2 != Character.TYPE && var2 != Character.class) {
                  if(var2 instanceof Class && "android.net.Uri".equals(((Class)var2).getName())) {
                     try {
                        Object var7 = Class.forName("android.net.Uri").getMethod("parse", new Class[]{String.class}).invoke((Object)null, new Object[]{var12});
                        return var7;
                     } catch (Exception var5) {
                        throw new JSONException("parse android.net.Uri error.", var5);
                     }
                  } else {
                     return TimeZone.getTimeZone(var12);
                  }
               } else {
                  return TypeUtils.castToChar(var12);
               }
            } else {
               if(var3 instanceof JSONObject) {
                  JSONObject var6 = (JSONObject)var3;
                  if(var2 == Currency.class) {
                     var12 = var6.getString("currency");
                     if(var12 != null) {
                        return Currency.getInstance(var12);
                     }

                     var12 = var6.getString("currencyCode");
                     if(var12 != null) {
                        return Currency.getInstance(var12);
                     }
                  }

                  if(var2 == Entry.class) {
                     return var6.entrySet().iterator().next();
                  }
               }

               throw new JSONException("except string value");
            }
         }
      }
   }

   protected <T extends Object> T parseStackTraceElement(DefaultJSONParser var1) {
      JSONLexer var10 = var1.lexer;
      if(var10.token() == 8) {
         var10.nextToken();
         return null;
      } else {
         StringBuilder var12;
         if(var10.token() != 12 && var10.token() != 16) {
            var12 = new StringBuilder();
            var12.append("syntax error: ");
            var12.append(JSONToken.name(var10.token()));
            throw new JSONException(var12.toString());
         } else {
            String var9 = null;
            String var8 = var9;
            String var7 = var9;
            int var3 = 0;

            int var2;
            String var4;
            String var5;
            String var6;
            while(true) {
               var4 = var10.scanSymbol(var1.symbolTable);
               if(var4 == null) {
                  if(var10.token() == 13) {
                     var10.nextToken(16);
                     var4 = var9;
                     var5 = var8;
                     var6 = var7;
                     var2 = var3;
                     break;
                  }

                  if(var10.token() == 16) {
                     continue;
                  }
               }

               var10.nextTokenWithChar(':');
               if("className".equals(var4)) {
                  if(var10.token() == 8) {
                     var4 = null;
                     var5 = var8;
                     var6 = var7;
                     var2 = var3;
                  } else {
                     if(var10.token() != 4) {
                        throw new JSONException("syntax error");
                     }

                     var4 = var10.stringVal();
                     var5 = var8;
                     var6 = var7;
                     var2 = var3;
                  }
               } else if("methodName".equals(var4)) {
                  if(var10.token() == 8) {
                     var5 = null;
                     var4 = var9;
                     var6 = var7;
                     var2 = var3;
                  } else {
                     if(var10.token() != 4) {
                        throw new JSONException("syntax error");
                     }

                     var5 = var10.stringVal();
                     var4 = var9;
                     var6 = var7;
                     var2 = var3;
                  }
               } else if("fileName".equals(var4)) {
                  if(var10.token() == 8) {
                     var6 = null;
                     var4 = var9;
                     var5 = var8;
                     var2 = var3;
                  } else {
                     if(var10.token() != 4) {
                        throw new JSONException("syntax error");
                     }

                     var6 = var10.stringVal();
                     var4 = var9;
                     var5 = var8;
                     var2 = var3;
                  }
               } else if("lineNumber".equals(var4)) {
                  if(var10.token() == 8) {
                     var2 = 0;
                     var4 = var9;
                     var5 = var8;
                     var6 = var7;
                  } else {
                     if(var10.token() != 2) {
                        throw new JSONException("syntax error");
                     }

                     var2 = var10.intValue();
                     var4 = var9;
                     var5 = var8;
                     var6 = var7;
                  }
               } else if("nativeMethod".equals(var4)) {
                  if(var10.token() == 8) {
                     var10.nextToken(16);
                     var4 = var9;
                     var5 = var8;
                     var6 = var7;
                     var2 = var3;
                  } else if(var10.token() == 6) {
                     var10.nextToken(16);
                     var4 = var9;
                     var5 = var8;
                     var6 = var7;
                     var2 = var3;
                  } else {
                     if(var10.token() != 7) {
                        throw new JSONException("syntax error");
                     }

                     var10.nextToken(16);
                     var4 = var9;
                     var5 = var8;
                     var6 = var7;
                     var2 = var3;
                  }
               } else {
                  if(var4 != "@type") {
                     var12 = new StringBuilder();
                     var12.append("syntax error : ");
                     var12.append(var4);
                     throw new JSONException(var12.toString());
                  }

                  if(var10.token() == 4) {
                     String var11 = var10.stringVal();
                     var4 = var9;
                     var5 = var8;
                     var6 = var7;
                     var2 = var3;
                     if(!var11.equals("java.lang.StackTraceElement")) {
                        var12 = new StringBuilder();
                        var12.append("syntax error : ");
                        var12.append(var11);
                        throw new JSONException(var12.toString());
                     }
                  } else {
                     var4 = var9;
                     var5 = var8;
                     var6 = var7;
                     var2 = var3;
                     if(var10.token() != 8) {
                        throw new JSONException("syntax error");
                     }
                  }
               }

               var9 = var4;
               var8 = var5;
               var7 = var6;
               var3 = var2;
               if(var10.token() == 13) {
                  var10.nextToken(16);
                  break;
               }
            }

            return new StackTraceElement(var4, var5, var6, var2);
         }
      }
   }

   public void write(JSONSerializer param1, Object param2, Object param3, Type param4) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
