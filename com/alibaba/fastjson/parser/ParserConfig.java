package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.DefaultFieldDeserializer;
import com.alibaba.fastjson.parser.EnumDeserializer;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.JavaBeanInfo;
import com.alibaba.fastjson.parser.JavaObjectDeserializer;
import com.alibaba.fastjson.parser.ListTypeFieldDeserializer;
import com.alibaba.fastjson.parser.MapDeserializer;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.parser.ThrowableDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.ArrayCodec;
import com.alibaba.fastjson.serializer.BigDecimalCodec;
import com.alibaba.fastjson.serializer.BooleanCodec;
import com.alibaba.fastjson.serializer.CollectionCodec;
import com.alibaba.fastjson.serializer.DateCodec;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.MiscCodec;
import com.alibaba.fastjson.serializer.NumberCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

public class ParserConfig {

   public static ParserConfig global = new ParserConfig();
   public boolean autoTypeSupport;
   public ClassLoader defaultClassLoader;
   private final IdentityHashMap<ObjectDeserializer> deserializers = new IdentityHashMap(1024);
   public PropertyNamingStrategy propertyNamingStrategy;
   public final SymbolTable symbolTable = new SymbolTable(16384);


   public ParserConfig() {
      this.deserializers.put(SimpleDateFormat.class, MiscCodec.instance);
      this.deserializers.put(Date.class, DateCodec.instance);
      this.deserializers.put(Calendar.class, DateCodec.instance);
      this.deserializers.put(Map.class, MapDeserializer.instance);
      this.deserializers.put(HashMap.class, MapDeserializer.instance);
      this.deserializers.put(LinkedHashMap.class, MapDeserializer.instance);
      this.deserializers.put(TreeMap.class, MapDeserializer.instance);
      this.deserializers.put(ConcurrentMap.class, MapDeserializer.instance);
      this.deserializers.put(ConcurrentHashMap.class, MapDeserializer.instance);
      this.deserializers.put(Collection.class, CollectionCodec.instance);
      this.deserializers.put(List.class, CollectionCodec.instance);
      this.deserializers.put(ArrayList.class, CollectionCodec.instance);
      this.deserializers.put(Object.class, JavaObjectDeserializer.instance);
      this.deserializers.put(String.class, StringCodec.instance);
      this.deserializers.put(Character.TYPE, MiscCodec.instance);
      this.deserializers.put(Character.class, MiscCodec.instance);
      this.deserializers.put(Byte.TYPE, NumberCodec.instance);
      this.deserializers.put(Byte.class, NumberCodec.instance);
      this.deserializers.put(Short.TYPE, NumberCodec.instance);
      this.deserializers.put(Short.class, NumberCodec.instance);
      this.deserializers.put(Integer.TYPE, IntegerCodec.instance);
      this.deserializers.put(Integer.class, IntegerCodec.instance);
      this.deserializers.put(Long.TYPE, IntegerCodec.instance);
      this.deserializers.put(Long.class, IntegerCodec.instance);
      this.deserializers.put(BigInteger.class, BigDecimalCodec.instance);
      this.deserializers.put(BigDecimal.class, BigDecimalCodec.instance);
      this.deserializers.put(Float.TYPE, NumberCodec.instance);
      this.deserializers.put(Float.class, NumberCodec.instance);
      this.deserializers.put(Double.TYPE, NumberCodec.instance);
      this.deserializers.put(Double.class, NumberCodec.instance);
      this.deserializers.put(Boolean.TYPE, BooleanCodec.instance);
      this.deserializers.put(Boolean.class, BooleanCodec.instance);
      this.deserializers.put(Class.class, MiscCodec.instance);
      this.deserializers.put(char[].class, ArrayCodec.instance);
      this.deserializers.put(Object[].class, ArrayCodec.instance);
      this.deserializers.put(UUID.class, MiscCodec.instance);
      this.deserializers.put(TimeZone.class, MiscCodec.instance);
      this.deserializers.put(Locale.class, MiscCodec.instance);
      this.deserializers.put(Currency.class, MiscCodec.instance);
      this.deserializers.put(URI.class, MiscCodec.instance);
      this.deserializers.put(URL.class, MiscCodec.instance);
      this.deserializers.put(Pattern.class, MiscCodec.instance);
      this.deserializers.put(Charset.class, MiscCodec.instance);
      this.deserializers.put(Number.class, NumberCodec.instance);
      this.deserializers.put(StackTraceElement.class, MiscCodec.instance);
      this.deserializers.put(Serializable.class, JavaObjectDeserializer.instance);
      this.deserializers.put(Cloneable.class, JavaObjectDeserializer.instance);
      this.deserializers.put(Comparable.class, JavaObjectDeserializer.instance);
      this.deserializers.put(Closeable.class, JavaObjectDeserializer.instance);
   }

   public static ParserConfig getGlobalInstance() {
      return global;
   }

   public static boolean isPrimitive(Class<?> var0) {
      return var0.isPrimitive() || var0 == Boolean.class || var0 == Character.class || var0 == Byte.class || var0 == Short.class || var0 == Integer.class || var0 == Long.class || var0 == Float.class || var0 == Double.class || var0 == BigInteger.class || var0 == BigDecimal.class || var0 == String.class || var0 == Date.class || var0 == java.sql.Date.class || var0 == Time.class || var0 == Timestamp.class;
   }

   public Class<?> checkAutoType(String var1, Class<?> var2, int var3) {
      if(var1 == null) {
         return null;
      } else {
         StringBuilder var6;
         if(var1.length() >= 128) {
            var6 = new StringBuilder();
            var6.append("autoType is not support. ");
            var6.append(var1);
            throw new JSONException(var6.toString());
         } else {
            Class var5 = TypeUtils.getClassFromMapping(var1);
            if(var5 != null) {
               return var5;
            } else {
               var5 = this.deserializers.findClass(var1);
               if(var5 != null) {
                  return var5;
               } else {
                  var5 = TypeUtils.loadClass(var1, this.defaultClassLoader, false);
                  if(var5 != null && var2 != null && var5 != HashMap.class && !var2.isAssignableFrom(var5)) {
                     StringBuilder var7 = new StringBuilder();
                     var7.append("type not match. ");
                     var7.append(var1);
                     var7.append(" -> ");
                     var7.append(var2.getName());
                     throw new JSONException(var7.toString());
                  } else {
                     int var4 = Feature.SupportAutoType.mask;
                     if((var3 & var4) == 0 && (var4 & JSON.DEFAULT_PARSER_FEATURE) == 0) {
                        var6 = new StringBuilder();
                        var6.append("SupportAutoType : ");
                        var6.append(var1);
                        throw new JSONException(var6.toString());
                     } else {
                        return var5;
                     }
                  }
               }
            }
         }
      }
   }

   public boolean containsKey(Class var1) {
      return this.deserializers.get(var1) != null;
   }

   public FieldDeserializer createFieldDeserializer(ParserConfig var1, Class<?> var2, FieldInfo var3) {
      Class var4 = var3.fieldClass;
      return (FieldDeserializer)(var4 != List.class && var4 != ArrayList.class && (!var4.isArray() || var4.getComponentType().isPrimitive())?new DefaultFieldDeserializer(var1, var2, var3):new ListTypeFieldDeserializer(var1, var2, var3));
   }

   public ObjectDeserializer getDeserializer(Class<?> var1, Type var2) {
      ObjectDeserializer var3 = (ObjectDeserializer)this.deserializers.get(var2);
      if(var3 != null) {
         return var3;
      } else {
         Object var7 = var2;
         if(var2 == null) {
            var7 = var1;
         }

         ObjectDeserializer var6 = (ObjectDeserializer)this.deserializers.get((Type)var7);
         if(var6 != null) {
            return var6;
         } else {
            if(!isPrimitive(var1)) {
               JSONType var4 = (JSONType)var1.getAnnotation(JSONType.class);
               if(var4 != null) {
                  Class var8 = var4.mappingTo();
                  if(var8 != Void.class) {
                     return this.getDeserializer(var8, var8);
                  }
               }
            }

            if(var7 instanceof WildcardType || var7 instanceof TypeVariable || var7 instanceof ParameterizedType) {
               var6 = (ObjectDeserializer)this.deserializers.get(var1);
            }

            if(var6 != null) {
               return var6;
            } else {
               var6 = (ObjectDeserializer)this.deserializers.get((Type)var7);
               if(var6 != null) {
                  return var6;
               } else {
                  Object var5;
                  if(var1.isEnum()) {
                     var5 = new EnumDeserializer(var1);
                  } else if(var1.isArray()) {
                     var5 = ArrayCodec.instance;
                  } else if(var1 != Set.class && var1 != HashSet.class && var1 != Collection.class && var1 != List.class && var1 != ArrayList.class) {
                     if(Collection.class.isAssignableFrom(var1)) {
                        var5 = CollectionCodec.instance;
                     } else if(Map.class.isAssignableFrom(var1)) {
                        var5 = MapDeserializer.instance;
                     } else if(Throwable.class.isAssignableFrom(var1)) {
                        var5 = new ThrowableDeserializer(this, var1);
                     } else if(var1.getName().equals("android.net.Uri")) {
                        var5 = MiscCodec.instance;
                     } else {
                        var5 = new JavaBeanDeserializer(this, var1, (Type)var7);
                     }
                  } else {
                     var5 = CollectionCodec.instance;
                  }

                  this.putDeserializer((Type)var7, (ObjectDeserializer)var5);
                  return (ObjectDeserializer)var5;
               }
            }
         }
      }
   }

   public ObjectDeserializer getDeserializer(Type var1) {
      ObjectDeserializer var2 = (ObjectDeserializer)this.deserializers.get(var1);
      if(var2 != null) {
         return var2;
      } else if(var1 instanceof Class) {
         return this.getDeserializer((Class)var1, var1);
      } else if(var1 instanceof ParameterizedType) {
         Type var4 = ((ParameterizedType)var1).getRawType();
         return var4 instanceof Class?this.getDeserializer((Class)var4, var1):this.getDeserializer(var4);
      } else {
         if(var1 instanceof WildcardType) {
            Type[] var3 = ((WildcardType)var1).getUpperBounds();
            if(var3.length == 1) {
               return this.getDeserializer(var3[0]);
            }
         }

         return JavaObjectDeserializer.instance;
      }
   }

   public void putDeserializer(Type var1, ObjectDeserializer var2) {
      this.deserializers.put(var1, var2);
   }

   public ObjectDeserializer registerIfNotExists(Class<?> var1) {
      return this.registerIfNotExists(var1, var1.getModifiers(), false, true, true, true);
   }

   public ObjectDeserializer registerIfNotExists(Class<?> var1, int var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      ObjectDeserializer var7 = (ObjectDeserializer)this.deserializers.get(var1);
      if(var7 != null) {
         return var7;
      } else {
         JavaBeanDeserializer var8 = new JavaBeanDeserializer(this, var1, var1, JavaBeanInfo.build(var1, var2, var1, var3, var4, var5, var6, this.propertyNamingStrategy));
         this.putDeserializer(var1, var8);
         return var8;
      }
   }
}
