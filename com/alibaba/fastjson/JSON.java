package com.alibaba.fastjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.Map.Entry;

public abstract class JSON implements JSONAware, JSONStreamAware {

   public static int DEFAULT_GENERATE_FEATURE = SerializerFeature.QuoteFieldNames.mask | 0 | SerializerFeature.SkipTransientField.mask | SerializerFeature.WriteEnumUsingToString.mask | SerializerFeature.SortField.mask;
   public static int DEFAULT_PARSER_FEATURE = Feature.UseBigDecimal.mask | 0 | Feature.SortFeidFastMatch.mask | Feature.IgnoreNotMatch.mask;
   public static final String DEFAULT_TYPE_KEY = "@type";
   public static String DEFFAULT_DATE_FORMAT;
   public static final String VERSION = "1.1.67";
   public static Locale defaultLocale = Locale.getDefault();
   public static TimeZone defaultTimeZone = TimeZone.getDefault();


   public static final Object parse(String var0) {
      return parse(var0, DEFAULT_PARSER_FEATURE);
   }

   public static final Object parse(String var0, int var1) {
      if(var0 == null) {
         return null;
      } else {
         DefaultJSONParser var3 = new DefaultJSONParser(var0, ParserConfig.global, var1);
         Object var2 = var3.parse((Object)null);
         var3.handleResovleTask(var2);
         var3.close();
         return var2;
      }
   }

   public static Object parse(String var0, ParserConfig var1) {
      return parse(var0, var1, DEFAULT_PARSER_FEATURE);
   }

   public static Object parse(String var0, ParserConfig var1, int var2) {
      if(var0 == null) {
         return null;
      } else {
         DefaultJSONParser var3 = new DefaultJSONParser(var0, var1, var2);
         Object var4 = var3.parse();
         var3.handleResovleTask(var4);
         var3.close();
         return var4;
      }
   }

   public static Object parse(String var0, ParserConfig var1, Feature ... var2) {
      int var4 = DEFAULT_PARSER_FEATURE;

      for(int var3 = 0; var3 < var2.length; ++var3) {
         var4 |= var2[var3].mask;
      }

      return parse(var0, var1, var4);
   }

   public static final Object parse(String var0, Feature ... var1) {
      int var3 = DEFAULT_PARSER_FEATURE;
      int var4 = var1.length;

      for(int var2 = 0; var2 < var4; ++var2) {
         var3 |= var1[var2].mask;
      }

      return parse(var0, var3);
   }

   public static final Object parse(byte[] var0, Feature ... var1) {
      try {
         JSONObject var3 = parseObject(new String(var0, "UTF-8"), var1);
         return var3;
      } catch (UnsupportedEncodingException var2) {
         throw new JSONException("UTF-8 not support", var2);
      }
   }

   public static final JSONArray parseArray(String var0) {
      return parseArray(var0, new Feature[0]);
   }

   public static final JSONArray parseArray(String var0, Feature ... var1) {
      Object var4 = null;
      if(var0 == null) {
         return null;
      } else {
         int var3 = DEFAULT_PARSER_FEATURE;

         int var2;
         for(var2 = 0; var2 < var1.length; ++var2) {
            var3 |= var1[var2].mask;
         }

         DefaultJSONParser var7 = new DefaultJSONParser(var0, ParserConfig.global, var3);
         JSONLexer var5 = var7.lexer;
         var2 = var5.token();
         JSONArray var6;
         if(var2 == 8) {
            var5.nextToken();
            var6 = (JSONArray)var4;
         } else if(var2 == 20) {
            var6 = (JSONArray)var4;
         } else {
            var6 = new JSONArray();
            var7.parseArray((Collection)var6);
            var7.handleResovleTask(var6);
         }

         var7.close();
         return var6;
      }
   }

   public static final <T extends Object> List<T> parseArray(String var0, Class<T> var1) {
      Object var3 = null;
      if(var0 == null) {
         return null;
      } else {
         DefaultJSONParser var4 = new DefaultJSONParser(var0, ParserConfig.global);
         JSONLexer var5 = var4.lexer;
         int var2 = var5.token();
         ArrayList var6;
         if(var2 == 8) {
            var5.nextToken();
            var6 = (ArrayList)var3;
         } else if(var2 == 20 && var5.isBlankInput()) {
            var6 = (ArrayList)var3;
         } else {
            var6 = new ArrayList();
            var4.parseArray(var1, (Collection)var6);
            var4.handleResovleTask(var6);
         }

         var4.close();
         return var6;
      }
   }

   public static final List<Object> parseArray(String var0, Type[] var1) {
      Object var2 = null;
      if(var0 == null) {
         return null;
      } else {
         DefaultJSONParser var3 = new DefaultJSONParser(var0, ParserConfig.global);
         Object[] var4 = var3.parseArray(var1);
         List var5;
         if(var4 == null) {
            var5 = (List)var2;
         } else {
            var5 = Arrays.asList(var4);
         }

         var3.handleResovleTask(var5);
         var3.close();
         return var5;
      }
   }

   public static final JSONObject parseObject(String var0) {
      Object var3 = parse(var0);
      if(!(var3 instanceof JSONObject) && var3 != null) {
         JSONObject var2 = (JSONObject)toJSON(var3);
         boolean var1;
         if((DEFAULT_PARSER_FEATURE & Feature.SupportAutoType.mask) != 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if(var1) {
            var2.put("@type", var3.getClass().getName());
         }

         return var2;
      } else {
         return (JSONObject)var3;
      }
   }

   public static final JSONObject parseObject(String var0, Feature ... var1) {
      Object var7 = parse(var0, var1);
      if(var7 instanceof JSONObject) {
         return (JSONObject)var7;
      } else {
         JSONObject var6 = (JSONObject)toJSON(var7);
         int var2 = DEFAULT_PARSER_FEATURE;
         int var4 = Feature.SupportAutoType.mask;
         int var3 = 0;
         boolean var8;
         if((var2 & var4) != 0) {
            var8 = true;
         } else {
            var8 = false;
         }

         boolean var9 = var8;
         if(!var8) {
            int var5 = var1.length;

            while(true) {
               var9 = var8;
               if(var3 >= var5) {
                  break;
               }

               if(var1[var3] == Feature.SupportAutoType) {
                  var8 = true;
               }

               ++var3;
            }
         }

         if(var9) {
            var6.put("@type", var7.getClass().getName());
         }

         return var6;
      }
   }

   public static final <T extends Object> T parseObject(String var0, TypeReference<T> var1, Feature ... var2) {
      return parseObject(var0, var1.type, ParserConfig.global, DEFAULT_PARSER_FEATURE, var2);
   }

   public static final <T extends Object> T parseObject(String var0, Class<T> var1) {
      return parseObject(var0, var1, new Feature[0]);
   }

   public static final <T extends Object> T parseObject(String var0, Class<T> var1, ParseProcess var2, Feature ... var3) {
      return parseObject(var0, var1, ParserConfig.global, var2, DEFAULT_PARSER_FEATURE, var3);
   }

   public static final <T extends Object> T parseObject(String var0, Class<T> var1, Feature ... var2) {
      return parseObject(var0, var1, ParserConfig.global, DEFAULT_PARSER_FEATURE, var2);
   }

   public static final <T extends Object> T parseObject(String var0, Type var1, int var2, Feature ... var3) {
      if(var0 == null) {
         return null;
      } else {
         int var6 = var3.length;
         byte var5 = 0;
         int var4 = var2;

         for(var2 = var5; var2 < var6; ++var2) {
            var4 |= var3[var2].mask;
         }

         DefaultJSONParser var7 = new DefaultJSONParser(var0, ParserConfig.global, var4);
         Object var8 = var7.parseObject(var1);
         var7.handleResovleTask(var8);
         var7.close();
         return var8;
      }
   }

   public static final <T extends Object> T parseObject(String var0, Type var1, ParserConfig var2, int var3, Feature ... var4) {
      return parseObject(var0, var1, var2, (ParseProcess)null, var3, var4);
   }

   public static final <T extends Object> T parseObject(String var0, Type var1, ParserConfig var2, ParseProcess var3, int var4, Feature ... var5) {
      if(var0 == null) {
         return null;
      } else {
         int var8 = var5.length;
         byte var7 = 0;
         int var6 = var4;

         for(var4 = var7; var4 < var8; ++var4) {
            var6 |= var5[var4].mask;
         }

         DefaultJSONParser var9 = new DefaultJSONParser(var0, var2, var6);
         if(var3 instanceof ExtraTypeProvider) {
            var9.getExtraTypeProviders().add((ExtraTypeProvider)var3);
         }

         if(var3 instanceof ExtraProcessor) {
            var9.getExtraProcessors().add((ExtraProcessor)var3);
         }

         if(var3 instanceof FieldTypeResolver) {
            var9.fieldTypeResolver = (FieldTypeResolver)var3;
         }

         Object var10 = var9.parseObject(var1);
         var9.handleResovleTask(var10);
         var9.close();
         return var10;
      }
   }

   public static final <T extends Object> T parseObject(String var0, Type var1, ParseProcess var2, Feature ... var3) {
      return parseObject(var0, var1, ParserConfig.global, var2, DEFAULT_PARSER_FEATURE, var3);
   }

   public static final <T extends Object> T parseObject(String var0, Type var1, Feature ... var2) {
      return parseObject(var0, var1, ParserConfig.global, DEFAULT_PARSER_FEATURE, var2);
   }

   public static final <T extends Object> T parseObject(byte[] var0, Type var1, Feature ... var2) {
      try {
         Object var4 = parseObject(new String(var0, "UTF-8"), var1, var2);
         return var4;
      } catch (UnsupportedEncodingException var3) {
         throw new JSONException("UTF-8 not support");
      }
   }

   public static final <T extends Object> T parseObject(char[] var0, int var1, Type var2, Feature ... var3) {
      if(var0 != null && var0.length != 0) {
         int var5 = DEFAULT_PARSER_FEATURE;
         int var6 = var3.length;

         for(int var4 = 0; var4 < var6; ++var4) {
            var5 |= var3[var4].mask;
         }

         DefaultJSONParser var7 = new DefaultJSONParser(var0, var1, ParserConfig.global, var5);
         Object var8 = var7.parseObject(var2);
         var7.handleResovleTask(var8);
         var7.close();
         return var8;
      } else {
         return null;
      }
   }

   public static final Object toJSON(Object var0) {
      return toJSON(var0, SerializeConfig.globalInstance);
   }

   @Deprecated
   public static final Object toJSON(Object var0, ParserConfig var1) {
      return toJSON(var0, SerializeConfig.globalInstance);
   }

   public static Object toJSON(Object var0, SerializeConfig var1) {
      if(var0 == null) {
         return null;
      } else if(var0 instanceof JSON) {
         return (JSON)var0;
      } else {
         int var2;
         Iterator var13;
         Entry var16;
         if(var0 instanceof Map) {
            Map var14 = (Map)var0;
            var2 = var14.size();
            if(var14 instanceof LinkedHashMap) {
               var0 = new LinkedHashMap(var2);
            } else if(var14 instanceof TreeMap) {
               var0 = new TreeMap();
            } else {
               var0 = new HashMap(var2);
            }

            JSONObject var12 = new JSONObject((Map)var0);
            var13 = var14.entrySet().iterator();

            while(var13.hasNext()) {
               var16 = (Entry)var13.next();
               var12.put(TypeUtils.castToString(var16.getKey()), toJSON(var16.getValue()));
            }

            return var12;
         } else if(var0 instanceof Collection) {
            Collection var11 = (Collection)var0;
            JSONArray var7 = new JSONArray(var11.size());
            var13 = var11.iterator();

            while(var13.hasNext()) {
               var7.add(toJSON(var13.next()));
            }

            return var7;
         } else {
            Class var4 = var0.getClass();
            if(var4.isEnum()) {
               return ((Enum)var0).name();
            } else if(var4.isArray()) {
               int var3 = Array.getLength(var0);
               JSONArray var10 = new JSONArray(var3);

               for(var2 = 0; var2 < var3; ++var2) {
                  var10.add(toJSON(Array.get(var0, var2)));
               }

               return var10;
            } else if(ParserConfig.isPrimitive(var4)) {
               return var0;
            } else {
               ObjectSerializer var8 = var1.get(var4);
               if(var8 instanceof JavaBeanSerializer) {
                  JavaBeanSerializer var15 = (JavaBeanSerializer)var8;
                  JSONObject var9 = new JSONObject();

                  try {
                     Iterator var6 = var15.getFieldValuesMap(var0).entrySet().iterator();

                     while(var6.hasNext()) {
                        var16 = (Entry)var6.next();
                        var9.put((String)var16.getKey(), toJSON(var16.getValue()));
                     }

                     return var9;
                  } catch (Exception var5) {
                     throw new JSONException("toJSON error", var5);
                  }
               } else {
                  return null;
               }
            }
         }
      }
   }

   public static final byte[] toJSONBytes(Object var0, SerializeConfig var1, SerializerFeature ... var2) {
      SerializeWriter var6 = new SerializeWriter((Writer)null, DEFAULT_GENERATE_FEATURE, var2);

      byte[] var5;
      try {
         (new JSONSerializer(var6, var1)).write(var0);
         var5 = var6.toBytes("UTF-8");
      } finally {
         var6.close();
      }

      return var5;
   }

   public static final byte[] toJSONBytes(Object var0, SerializerFeature ... var1) {
      SerializeWriter var5 = new SerializeWriter((Writer)null, DEFAULT_GENERATE_FEATURE, var1);

      byte[] var4;
      try {
         (new JSONSerializer(var5, SerializeConfig.globalInstance)).write(var0);
         var4 = var5.toBytes("UTF-8");
      } finally {
         var5.close();
      }

      return var4;
   }

   public static final String toJSONString(Object var0) {
      return toJSONString(var0, SerializeConfig.globalInstance, (SerializeFilter[])null, (String)null, DEFAULT_GENERATE_FEATURE, new SerializerFeature[0]);
   }

   public static final String toJSONString(Object var0, int var1, SerializerFeature ... var2) {
      return toJSONString(var0, SerializeConfig.globalInstance, (SerializeFilter[])null, (String)null, var1, var2);
   }

   public static final String toJSONString(Object var0, SerializeConfig var1, SerializeFilter var2, SerializerFeature ... var3) {
      int var4 = DEFAULT_GENERATE_FEATURE;
      return toJSONString(var0, var1, new SerializeFilter[]{var2}, (String)null, var4, var3);
   }

   public static String toJSONString(Object param0, SerializeConfig param1, SerializeFilter[] param2, String param3, int param4, SerializerFeature ... param5) {
      // $FF: Couldn't be decompiled
   }

   public static final String toJSONString(Object var0, SerializeConfig var1, SerializeFilter[] var2, SerializerFeature ... var3) {
      return toJSONString(var0, var1, var2, (String)null, DEFAULT_GENERATE_FEATURE, var3);
   }

   public static final String toJSONString(Object var0, SerializeConfig var1, SerializerFeature ... var2) {
      return toJSONString(var0, var1, (SerializeFilter[])null, (String)null, DEFAULT_GENERATE_FEATURE, var2);
   }

   public static final String toJSONString(Object var0, SerializeFilter var1, SerializerFeature ... var2) {
      SerializeConfig var4 = SerializeConfig.globalInstance;
      int var3 = DEFAULT_GENERATE_FEATURE;
      return toJSONString(var0, var4, new SerializeFilter[]{var1}, (String)null, var3, var2);
   }

   public static final String toJSONString(Object var0, boolean var1) {
      return !var1?toJSONString(var0):toJSONString(var0, new SerializerFeature[]{SerializerFeature.PrettyFormat});
   }

   public static final String toJSONString(Object var0, SerializeFilter[] var1, SerializerFeature ... var2) {
      return toJSONString(var0, SerializeConfig.globalInstance, var1, (String)null, DEFAULT_GENERATE_FEATURE, var2);
   }

   public static final String toJSONString(Object var0, SerializerFeature ... var1) {
      return toJSONString(var0, DEFAULT_GENERATE_FEATURE, var1);
   }

   public static final String toJSONStringWithDateFormat(Object var0, String var1, SerializerFeature ... var2) {
      return toJSONString(var0, SerializeConfig.globalInstance, (SerializeFilter[])null, var1, DEFAULT_GENERATE_FEATURE, var2);
   }

   public static final String toJSONStringZ(Object var0, SerializeConfig var1, SerializerFeature ... var2) {
      return toJSONString(var0, SerializeConfig.globalInstance, (SerializeFilter[])null, (String)null, 0, var2);
   }

   public static final <T extends Object> T toJavaObject(JSON var0, Class<T> var1) {
      return TypeUtils.cast(var0, var1, ParserConfig.global);
   }

   public static final void writeJSONStringTo(Object var0, Writer var1, SerializerFeature ... var2) {
      SerializeWriter var5 = new SerializeWriter(var1, DEFAULT_GENERATE_FEATURE, var2);

      try {
         (new JSONSerializer(var5, SerializeConfig.globalInstance)).write(var0);
      } finally {
         var5.close();
      }

   }

   public String toJSONString() {
      SerializeWriter var1 = new SerializeWriter((Writer)null, DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY);

      String var2;
      try {
         (new JSONSerializer(var1, SerializeConfig.globalInstance)).write((Object)this);
         var2 = var1.toString();
      } finally {
         var1.close();
      }

      return var2;
   }

   public <T extends Object> T toJavaObject(Class<T> var1) {
      return TypeUtils.cast(this, var1, ParserConfig.getGlobalInstance());
   }

   public String toString() {
      return this.toJSONString();
   }

   public void writeJSONString(Appendable var1) {
      SerializeWriter var2 = new SerializeWriter((Writer)null, DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY);

      try {
         (new JSONSerializer(var2, SerializeConfig.globalInstance)).write((Object)this);
         var1.append(var2.toString());
      } catch (IOException var5) {
         throw new JSONException(var5.getMessage(), var5);
      } finally {
         var2.close();
      }

   }
}
