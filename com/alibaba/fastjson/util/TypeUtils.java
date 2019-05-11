package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.AccessControlException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TypeUtils {

   public static boolean compatibleWithJavaBean;
   private static volatile Map<Class, String[]> kotlinIgnores;
   private static volatile boolean kotlinIgnores_error;
   private static volatile boolean kotlin_class_klass_error;
   private static volatile boolean kotlin_error;
   private static volatile Constructor kotlin_kclass_constructor;
   private static volatile Method kotlin_kclass_getConstructors;
   private static volatile Method kotlin_kfunction_getParameters;
   private static volatile Method kotlin_kparameter_getName;
   private static volatile Class kotlin_metadata;
   private static volatile boolean kotlin_metadata_error;
   private static final ConcurrentMap<String, Class<?>> mappings = new ConcurrentHashMap(36, 0.75F, 1);
   private static boolean setAccessibleEnable;


   static {
      mappings.put("byte", Byte.TYPE);
      mappings.put("short", Short.TYPE);
      mappings.put("int", Integer.TYPE);
      mappings.put("long", Long.TYPE);
      mappings.put("float", Float.TYPE);
      mappings.put("double", Double.TYPE);
      mappings.put("boolean", Boolean.TYPE);
      mappings.put("char", Character.TYPE);
      mappings.put("[byte", byte[].class);
      mappings.put("[short", short[].class);
      mappings.put("[int", int[].class);
      mappings.put("[long", long[].class);
      mappings.put("[float", float[].class);
      mappings.put("[double", double[].class);
      mappings.put("[boolean", boolean[].class);
      mappings.put("[char", char[].class);
      mappings.put("[B", byte[].class);
      mappings.put("[S", short[].class);
      mappings.put("[I", int[].class);
      mappings.put("[J", long[].class);
      mappings.put("[F", float[].class);
      mappings.put("[D", double[].class);
      mappings.put("[C", char[].class);
      mappings.put("[Z", boolean[].class);
      mappings.put("java.util.HashMap", HashMap.class);
      mappings.put("java.util.TreeMap", TreeMap.class);
      mappings.put("java.util.Date", Date.class);
      mappings.put("com.alibaba.fastjson.JSONObject", JSONObject.class);
      mappings.put("java.util.concurrent.ConcurrentHashMap", ConcurrentHashMap.class);
      mappings.put("java.text.SimpleDateFormat", SimpleDateFormat.class);
      mappings.put("java.lang.StackTraceElement", StackTraceElement.class);
      mappings.put("java.lang.RuntimeException", RuntimeException.class);
   }

   public static final <T extends Object> T cast(Object var0, Class<T> var1, ParserConfig var2) {
      return cast(var0, var1, var2, 0);
   }

   public static final <T extends Object> T cast(Object var0, Class<T> var1, ParserConfig var2, int var3) {
      if(var0 == null) {
         return null;
      } else if(var1 == null) {
         throw new IllegalArgumentException("clazz is null");
      } else if(var1 == var0.getClass()) {
         return var0;
      } else if(var0 instanceof Map) {
         if(var1 == Map.class) {
            return var0;
         } else {
            Map var14 = (Map)var0;
            return var1 == Object.class && !var14.containsKey("@type")?var0:castToJavaBean(var14, var1, var2, var3);
         }
      } else {
         boolean var4 = var1.isArray();
         var3 = 0;
         if(var4) {
            if(var0 instanceof Collection) {
               Collection var12 = (Collection)var0;
               var0 = Array.newInstance(var1.getComponentType(), var12.size());

               for(Iterator var13 = var12.iterator(); var13.hasNext(); ++var3) {
                  Array.set(var0, var3, cast(var13.next(), var1.getComponentType(), var2));
               }

               return var0;
            }

            if(var1 == byte[].class) {
               return castToBytes(var0);
            }
         }

         if(var1.isAssignableFrom(var0.getClass())) {
            return var0;
         } else if(var1 != Boolean.TYPE && var1 != Boolean.class) {
            if(var1 != Byte.TYPE && var1 != Byte.class) {
               if((var1 == Character.TYPE || var1 == Character.class) && var0 instanceof String) {
                  String var5 = (String)var0;
                  if(var5.length() == 1) {
                     return Character.valueOf(var5.charAt(0));
                  }
               }

               if(var1 != Short.TYPE && var1 != Short.class) {
                  if(var1 != Integer.TYPE && var1 != Integer.class) {
                     if(var1 != Long.TYPE && var1 != Long.class) {
                        if(var1 != Float.TYPE && var1 != Float.class) {
                           if(var1 != Double.TYPE && var1 != Double.class) {
                              if(var1 == String.class) {
                                 return castToString(var0);
                              } else if(var1 == BigDecimal.class) {
                                 return castToBigDecimal(var0);
                              } else if(var1 == BigInteger.class) {
                                 return castToBigInteger(var0);
                              } else if(var1 == Date.class) {
                                 return castToDate(var0);
                              } else if(var1.isEnum()) {
                                 return castToEnum(var0, var1, var2);
                              } else if(Calendar.class.isAssignableFrom(var1)) {
                                 Date var10 = castToDate(var0);
                                 Calendar var9;
                                 if(var1 == Calendar.class) {
                                    var9 = Calendar.getInstance(JSON.defaultTimeZone, JSON.defaultLocale);
                                 } else {
                                    try {
                                       var9 = (Calendar)var1.newInstance();
                                    } catch (Exception var6) {
                                       StringBuilder var11 = new StringBuilder();
                                       var11.append("can not cast to : ");
                                       var11.append(var1.getName());
                                       throw new JSONException(var11.toString(), var6);
                                    }
                                 }

                                 var9.setTime(var10);
                                 return var9;
                              } else {
                                 if(var0 instanceof String) {
                                    String var7 = (String)var0;
                                    if(var7.length() == 0) {
                                       return null;
                                    }

                                    if("null".equals(var7)) {
                                       return null;
                                    }

                                    if(var1 == Currency.class) {
                                       return Currency.getInstance(var7);
                                    }
                                 }

                                 StringBuilder var8 = new StringBuilder();
                                 var8.append("can not cast to : ");
                                 var8.append(var1.getName());
                                 throw new JSONException(var8.toString());
                              }
                           } else {
                              return castToDouble(var0);
                           }
                        } else {
                           return castToFloat(var0);
                        }
                     } else {
                        return castToLong(var0);
                     }
                  } else {
                     return castToInt(var0);
                  }
               } else {
                  return castToShort(var0);
               }
            } else {
               return castToByte(var0);
            }
         } else {
            return castToBoolean(var0);
         }
      }
   }

   public static final <T extends Object> T cast(Object var0, ParameterizedType var1, ParserConfig var2) {
      Type var5 = var1.getRawType();
      Type var3;
      Iterator var7;
      if(var5 == Set.class || var5 == HashSet.class || var5 == TreeSet.class || var5 == List.class || var5 == ArrayList.class) {
         var3 = var1.getActualTypeArguments()[0];
         if(var0 instanceof Iterable) {
            Object var9;
            if(var5 != Set.class && var5 != HashSet.class) {
               if(var5 == TreeSet.class) {
                  var9 = new TreeSet();
               } else {
                  var9 = new ArrayList();
               }
            } else {
               var9 = new HashSet();
            }

            var7 = ((Iterable)var0).iterator();

            while(var7.hasNext()) {
               ((Collection)var9).add(cast(var7.next(), var3, var2));
            }

            return var9;
         }
      }

      if(var5 == Map.class || var5 == HashMap.class) {
         var3 = var1.getActualTypeArguments()[0];
         Type var4 = var1.getActualTypeArguments()[1];
         if(var0 instanceof Map) {
            HashMap var8 = new HashMap();
            var7 = ((Map)var0).entrySet().iterator();

            while(var7.hasNext()) {
               Entry var11 = (Entry)var7.next();
               var8.put(cast(var11.getKey(), var3, var2), cast(var11.getValue(), var4, var2));
            }

            return var8;
         }
      }

      if(var0 instanceof String) {
         String var10 = (String)var0;
         if(var10.length() == 0 || "null".equals(var10)) {
            return null;
         }
      }

      if(var1.getActualTypeArguments().length == 1 && var1.getActualTypeArguments()[0] instanceof WildcardType) {
         return cast(var0, var5, var2);
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("can not cast to : ");
         var6.append(var1);
         throw new JSONException(var6.toString());
      }
   }

   public static final <T extends Object> T cast(Object var0, Type var1, ParserConfig var2) {
      if(var0 == null) {
         return null;
      } else if(var1 instanceof Class) {
         return cast(var0, (Class)var1, var2);
      } else if(var1 instanceof ParameterizedType) {
         return cast(var0, (ParameterizedType)var1, var2);
      } else {
         if(var0 instanceof String) {
            String var4 = (String)var0;
            if(var4.length() == 0 || "null".equals(var4)) {
               return null;
            }
         }

         if(var1 instanceof TypeVariable) {
            return var0;
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("can not cast to : ");
            var3.append(var1);
            throw new JSONException(var3.toString());
         }
      }
   }

   public static final BigDecimal castToBigDecimal(Object var0) {
      if(var0 == null) {
         return null;
      } else if(var0 instanceof BigDecimal) {
         return (BigDecimal)var0;
      } else if(var0 instanceof BigInteger) {
         return new BigDecimal((BigInteger)var0);
      } else {
         String var1 = var0.toString();
         return var1.length() != 0?("null".equals(var1)?null:new BigDecimal(var1)):null;
      }
   }

   public static final BigInteger castToBigInteger(Object var0) {
      if(var0 == null) {
         return null;
      } else if(var0 instanceof BigInteger) {
         return (BigInteger)var0;
      } else if(!(var0 instanceof Float) && !(var0 instanceof Double)) {
         String var1 = var0.toString();
         return var1.length() != 0?("null".equals(var1)?null:new BigInteger(var1)):null;
      } else {
         return BigInteger.valueOf(((Number)var0).longValue());
      }
   }

   public static final Boolean castToBoolean(Object var0) {
      if(var0 == null) {
         return null;
      } else if(var0 instanceof Boolean) {
         return (Boolean)var0;
      } else if(var0 instanceof Number) {
         int var1 = ((Number)var0).intValue();
         boolean var2 = true;
         if(var1 != 1) {
            var2 = false;
         }

         return Boolean.valueOf(var2);
      } else {
         if(var0 instanceof String) {
            String var3 = (String)var0;
            if(var3.length() == 0) {
               return null;
            }

            if("null".equals(var3)) {
               return null;
            }

            if("true".equalsIgnoreCase(var3) || "1".equals(var3)) {
               return Boolean.TRUE;
            }

            if("false".equalsIgnoreCase(var3) || "0".equals(var3)) {
               return Boolean.FALSE;
            }
         }

         StringBuilder var4 = new StringBuilder();
         var4.append("can not cast to int, value : ");
         var4.append(var0);
         throw new JSONException(var4.toString());
      }
   }

   public static final Byte castToByte(Object var0) {
      if(var0 == null) {
         return null;
      } else if(var0 instanceof Number) {
         return Byte.valueOf(((Number)var0).byteValue());
      } else if(var0 instanceof String) {
         String var2 = (String)var0;
         return var2.length() != 0?("null".equals(var2)?null:Byte.valueOf(Byte.parseByte(var2))):null;
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("can not cast to byte, value : ");
         var1.append(var0);
         throw new JSONException(var1.toString());
      }
   }

   public static final byte[] castToBytes(Object var0) {
      if(var0 instanceof byte[]) {
         return (byte[])var0;
      } else if(var0 instanceof String) {
         String var2 = (String)var0;
         return JSONLexer.decodeFast(var2, 0, var2.length());
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("can not cast to int, value : ");
         var1.append(var0);
         throw new JSONException(var1.toString());
      }
   }

   public static final Character castToChar(Object var0) {
      if(var0 == null) {
         return null;
      } else if(var0 instanceof Character) {
         return (Character)var0;
      } else {
         StringBuilder var1;
         if(var0 instanceof String) {
            String var2 = (String)var0;
            if(var2.length() == 0) {
               return null;
            } else if(var2.length() != 1) {
               var1 = new StringBuilder();
               var1.append("can not cast to byte, value : ");
               var1.append(var0);
               throw new JSONException(var1.toString());
            } else {
               return Character.valueOf(var2.charAt(0));
            }
         } else {
            var1 = new StringBuilder();
            var1.append("can not cast to byte, value : ");
            var1.append(var0);
            throw new JSONException(var1.toString());
         }
      }
   }

   public static final Date castToDate(Object var0) {
      if(var0 == null) {
         return null;
      } else if(var0 instanceof Calendar) {
         return ((Calendar)var0).getTime();
      } else if(var0 instanceof Date) {
         return (Date)var0;
      } else {
         long var1 = -1L;
         if(var0 instanceof Number) {
            var1 = ((Number)var0).longValue();
         }

         if(var0 instanceof String) {
            String var3 = (String)var0;
            if(var3.indexOf(45) != -1) {
               String var5;
               if(var3.length() == JSON.DEFFAULT_DATE_FORMAT.length()) {
                  var5 = JSON.DEFFAULT_DATE_FORMAT;
               } else if(var3.length() == 10) {
                  var5 = "yyyy-MM-dd";
               } else if(var3.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                  var5 = "yyyy-MM-dd HH:mm:ss";
               } else if(var3.length() == 29 && var3.charAt(26) == 58 && var3.charAt(28) == 48) {
                  var5 = "yyyy-MM-dd\'T\'HH:mm:ss.SSSXXX";
               } else {
                  var5 = "yyyy-MM-dd HH:mm:ss.SSS";
               }

               SimpleDateFormat var6 = new SimpleDateFormat(var5, JSON.defaultLocale);
               var6.setTimeZone(JSON.defaultTimeZone);

               try {
                  Date var8 = var6.parse(var3);
                  return var8;
               } catch (ParseException var4) {
                  StringBuilder var7 = new StringBuilder();
                  var7.append("can not cast to Date, value : ");
                  var7.append(var3);
                  throw new JSONException(var7.toString());
               }
            }

            if(var3.length() == 0) {
               return null;
            }

            if("null".equals(var3)) {
               return null;
            }

            var1 = Long.parseLong(var3);
         }

         if(var1 < 0L) {
            StringBuilder var9 = new StringBuilder();
            var9.append("can not cast to Date, value : ");
            var9.append(var0);
            throw new JSONException(var9.toString());
         } else {
            return new Date(var1);
         }
      }
   }

   public static final Double castToDouble(Object var0) {
      if(var0 == null) {
         return null;
      } else if(var0 instanceof Number) {
         return Double.valueOf(((Number)var0).doubleValue());
      } else if(var0 instanceof String) {
         String var2 = var0.toString();
         return var2.length() != 0?("null".equals(var2)?null:Double.valueOf(Double.parseDouble(var2))):null;
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("can not cast to double, value : ");
         var1.append(var0);
         throw new JSONException(var1.toString());
      }
   }

   public static final <T extends Object> T castToEnum(Object var0, Class<T> var1, ParserConfig var2) {
      int var3;
      Object[] var5;
      label27: {
         try {
            if(var0 instanceof String) {
               String var7 = (String)var0;
               if(var7.length() == 0) {
                  return null;
               }

               return Enum.valueOf(var1, var7);
            }

            if(var0 instanceof Number) {
               var3 = ((Number)var0).intValue();
               var5 = var1.getEnumConstants();
               if(var3 < var5.length) {
                  break label27;
               }
            }
         } catch (Exception var4) {
            StringBuilder var8 = new StringBuilder();
            var8.append("can not cast to : ");
            var8.append(var1.getName());
            throw new JSONException(var8.toString(), var4);
         }

         StringBuilder var6 = new StringBuilder();
         var6.append("can not cast to : ");
         var6.append(var1.getName());
         throw new JSONException(var6.toString());
      }

      var0 = var5[var3];
      return var0;
   }

   public static final Float castToFloat(Object var0) {
      if(var0 == null) {
         return null;
      } else if(var0 instanceof Number) {
         return Float.valueOf(((Number)var0).floatValue());
      } else if(var0 instanceof String) {
         String var2 = var0.toString();
         return var2.length() != 0?("null".equals(var2)?null:Float.valueOf(Float.parseFloat(var2))):null;
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("can not cast to float, value : ");
         var1.append(var0);
         throw new JSONException(var1.toString());
      }
   }

   public static final Integer castToInt(Object var0) {
      if(var0 == null) {
         return null;
      } else if(var0 instanceof Integer) {
         return (Integer)var0;
      } else if(var0 instanceof Number) {
         return Integer.valueOf(((Number)var0).intValue());
      } else if(var0 instanceof String) {
         String var2 = (String)var0;
         return var2.length() != 0?("null".equals(var2)?null:Integer.valueOf(Integer.parseInt(var2))):null;
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("can not cast to int, value : ");
         var1.append(var0);
         throw new JSONException(var1.toString());
      }
   }

   public static final <T extends Object> T castToJavaBean(Object var0, Class<T> var1) {
      return cast(var0, var1, ParserConfig.global);
   }

   public static final <T extends Object> T castToJavaBean(Map<String, Object> var0, Class<T> var1, ParserConfig var2) {
      return castToJavaBean(var0, var1, var2, 0);
   }

   public static final <T extends Object> T castToJavaBean(Map<String, Object> param0, Class<T> param1, ParserConfig param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   public static final Long castToLong(Object var0) {
      Calendar var3 = null;
      if(var0 == null) {
         return null;
      } else if(var0 instanceof Number) {
         return Long.valueOf(((Number)var0).longValue());
      } else {
         if(var0 instanceof String) {
            label43: {
               String var4 = (String)var0;
               if(var4.length() == 0) {
                  return null;
               }

               if("null".equals(var4)) {
                  return null;
               }

               long var1;
               try {
                  var1 = Long.parseLong(var4);
               } catch (NumberFormatException var6) {
                  JSONLexer var8 = new JSONLexer(var4);
                  if(var8.scanISO8601DateIfMatch(false)) {
                     var3 = var8.calendar;
                  }

                  var8.close();
                  if(var3 != null) {
                     return Long.valueOf(var3.getTimeInMillis());
                  }
                  break label43;
               }

               return Long.valueOf(var1);
            }
         }

         StringBuilder var7 = new StringBuilder();
         var7.append("can not cast to long, value : ");
         var7.append(var0);
         throw new JSONException(var7.toString());
      }
   }

   public static final Short castToShort(Object var0) {
      if(var0 == null) {
         return null;
      } else if(var0 instanceof Number) {
         return Short.valueOf(((Number)var0).shortValue());
      } else if(var0 instanceof String) {
         String var2 = (String)var0;
         return var2.length() != 0?("null".equals(var2)?null:Short.valueOf(Short.parseShort(var2))):null;
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("can not cast to short, value : ");
         var1.append(var0);
         throw new JSONException(var1.toString());
      }
   }

   public static final String castToString(Object var0) {
      return var0 == null?null:var0.toString();
   }

   public static List<FieldInfo> computeGetters(Class<?> var0, int var1, boolean var2, JSONType var3, Map<String, String> var4, boolean var5, boolean var6, boolean var7, PropertyNamingStrategy var8) {
      Object var15 = new LinkedHashMap();
      HashMap var24 = new HashMap();
      Object var16 = var0.getDeclaredFields();
      Object var23 = var16;
      Object var19 = var15;
      Class var22 = var0;
      int var11;
      int var12;
      JSONField var41;
      String var42;
      Field var47;
      if(!var2) {
         var2 = isKotlin(var0);
         ArrayList var49 = new ArrayList();

         int var13;
         for(Class var17 = var0; var17 != null && var17 != Object.class; var17 = var17.getSuperclass()) {
            Method[] var18 = var17.getDeclaredMethods();
            var12 = var18.length;

            for(var11 = 0; var11 < var12; ++var11) {
               Method var20 = var18[var11];
               var13 = var20.getModifiers();
               if((var13 & 8) == 0 && (var13 & 2) == 0 && (var13 & 256) == 0 && (var13 & 4) == 0 && !var20.getReturnType().equals(Void.TYPE) && var20.getParameterTypes().length == 0 && var20.getReturnType() != ClassLoader.class && var20.getDeclaringClass() != Object.class) {
                  var49.add(var20);
               }
            }
         }

         Annotation[][] var45 = (Annotation[][])null;
         Iterator var26 = var49.iterator();
         Constructor[] var51 = null;
         Object var21 = var51;
         Class var25 = var0;
         Object var44 = var24;
         Object var14 = var51;

         while(true) {
            var23 = var16;
            var19 = var15;
            var22 = var25;
            if(!var26.hasNext()) {
               break;
            }

            Method var30 = (Method)var26.next();
            String var32 = var30.getName();
            if(!var32.equals("getMetaClass") || !var30.getReturnType().getName().equals("groovy.lang.MetaClass")) {
               var19 = var21;
               JSONField var61;
               if(var6) {
                  var61 = (JSONField)var30.getAnnotation(JSONField.class);
               } else {
                  var61 = null;
               }

               JSONField var56 = var61;
               if(var61 == null) {
                  var56 = var61;
                  if(var6) {
                     var56 = getSupperMethodAnnotation(var25, var30);
                  }
               }

               if(!var2 || !isKotlinIgnore(var25, var32)) {
                  JSONField var27;
                  Object var54;
                  Constructor[] var62;
                  Object var63;
                  Annotation[][] var66;
                  label481: {
                     Constructor[] var39;
                     if(var56 == null && var2) {
                        label415: {
                           if(var51 == null) {
                              Constructor[] var55 = var0.getDeclaredConstructors();
                              if(var55.length == 1) {
                                 var45 = var55[0].getParameterAnnotations();
                                 var54 = getKoltinConstructorParameters(var0);
                                 if(var54 != null) {
                                    String[] var40 = new String[((Object[])var54).length];
                                    var11 = ((Object[])var54).length;
                                    Object var9 = 0;
                                    System.arraycopy(var54, 0, var40, 0, var11);
                                    Arrays.sort(var40);

                                    for(var19 = new short[((Object[])var54).length]; var9 < ((Object[])var54).length; var9 = (short)(var9 + 1)) {
                                       ((Object[])var19)[Arrays.binarySearch(var40, ((Object[])var54)[var9])] = var9;
                                    }

                                    var54 = var40;
                                 }

                                 var39 = var55;
                                 break label415;
                              }

                              var51 = var55;
                           }

                           var39 = var51;
                           var54 = var14;
                        }

                        var21 = var54;
                        if(var54 != null) {
                           var21 = var54;
                           if(var19 != null) {
                              var21 = var54;
                              if(var32.startsWith("get")) {
                                 String var60 = decapitalize(var32.substring(3));
                                 var13 = Arrays.binarySearch((Object[])var54, var60);
                                 var12 = var13;
                                 if(var13 < 0) {
                                    var11 = 0;

                                    while(true) {
                                       var12 = var13;
                                       if(var11 >= ((Object[])var54).length) {
                                          break;
                                       }

                                       if(var60.equalsIgnoreCase((String)((Object[])var54)[var11])) {
                                          var12 = var11;
                                          break;
                                       }

                                       ++var11;
                                    }
                                 }

                                 var21 = var54;
                                 if(var12 >= 0) {
                                    Annotation[] var64 = var45[((Object[])var19)[var12]];
                                    var21 = var54;
                                    if(var64 != null) {
                                       var12 = var64.length;
                                       var11 = 0;

                                       while(true) {
                                          var21 = var54;
                                          if(var11 >= var12) {
                                             break;
                                          }

                                          Annotation var65 = var64[var11];
                                          if(var65 instanceof JSONField) {
                                             var27 = (JSONField)var65;
                                             var63 = var19;
                                             var66 = var45;
                                             var62 = var39;
                                             var21 = var54;
                                             break label481;
                                          }

                                          ++var11;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     } else {
                        var21 = var14;
                        var39 = var51;
                     }

                     var27 = var56;
                     var62 = var39;
                     var66 = var45;
                     var63 = var19;
                  }

                  label393: {
                     label392: {
                        label467: {
                           String var50;
                           if(var27 != null) {
                              if(!var27.serialize()) {
                                 break label392;
                              }

                              var11 = var27.ordinal();
                              var12 = SerializerFeature.of(var27.serialzeFeatures());
                              if(var27.name().length() != 0) {
                                 var50 = var27.name();
                                 var42 = var50;
                                 if(var4 != null) {
                                    var50 = (String)var4.get(var50);
                                    var42 = var50;
                                    if(var50 == null) {
                                       break label392;
                                    }
                                 }

                                 setAccessible(var25, var30, var1);
                                 ((Map)var15).put(var42, new FieldInfo(var42, var30, (Field)null, var25, (Type)null, var11, var12, var27, (JSONField)null, true));
                                 break label467;
                              }
                           } else {
                              var11 = 0;
                              var12 = 0;
                           }

                           label468: {
                              var19 = var44;
                              var54 = var16;
                              Object var52 = var15;
                              char var10;
                              StringBuilder var46;
                              if(var32.startsWith("get")) {
                                 label378: {
                                    if(var32.length() >= 4) {
                                       label469: {
                                          if(var32.equals("getClass")) {
                                             break label467;
                                          }

                                          var10 = var32.charAt(3);
                                          if(Character.isUpperCase(var10)) {
                                             if(compatibleWithJavaBean) {
                                                var14 = decapitalize(var32.substring(3));
                                             } else {
                                                var46 = new StringBuilder();
                                                var46.append(Character.toLowerCase(var32.charAt(3)));
                                                var46.append(var32.substring(4));
                                                var14 = var46.toString();
                                             }
                                          } else if(var10 == 95) {
                                             var14 = var32.substring(4);
                                          } else if(var10 == 102) {
                                             var14 = var32.substring(3);
                                          } else {
                                             if(var32.length() < 5 || !Character.isUpperCase(var32.charAt(4))) {
                                                break label469;
                                             }

                                             var14 = decapitalize(var32.substring(3));
                                          }

                                          if(isJSONTypeIgnore(var25, var3, (String)var14)) {
                                             break label467;
                                          }

                                          label359: {
                                             label471: {
                                                var44 = getField(var25, (String)var14, (Field[])var16, (Map)var44);
                                                if(var44 != null) {
                                                   if(var6) {
                                                      var41 = (JSONField)((Field)var44).getAnnotation(JSONField.class);
                                                   } else {
                                                      var41 = null;
                                                   }

                                                   if(var41 != null) {
                                                      if(!var41.serialize()) {
                                                         break label471;
                                                      }

                                                      var11 = var41.ordinal();
                                                      var12 = SerializerFeature.of(var41.serialzeFeatures());
                                                      if(var41.name().length() != 0) {
                                                         String var43 = var41.name();
                                                         var14 = var43;
                                                         if(var4 != null) {
                                                            var16 = (String)var4.get(var43);
                                                            var14 = var16;
                                                            if(var16 == null) {
                                                               break label471;
                                                            }
                                                         }
                                                      }
                                                   }
                                                } else {
                                                   var41 = null;
                                                }

                                                var16 = var14;
                                                if(var8 != null) {
                                                   var16 = var8.translate((String)var14);
                                                }

                                                var14 = var16;
                                                if(var4 == null) {
                                                   break label359;
                                                }

                                                var16 = (String)var4.get(var16);
                                                var14 = var16;
                                                if(var16 != null) {
                                                   break label359;
                                                }
                                             }

                                             var15 = var16;
                                             var14 = var19;
                                             var16 = var52;
                                             break label393;
                                          }

                                          setAccessible(var25, var30, var1);
                                          ((Map)var15).put(var14, new FieldInfo((String)var14, var30, (Field)var44, var25, (Type)null, var11, var12, var27, var41, var7));
                                          break label378;
                                       }
                                    }

                                    var16 = var15;
                                    var15 = var54;
                                    var14 = var44;
                                    break label468;
                                 }
                              }

                              if(var32.startsWith("is") && var32.length() >= 3) {
                                 label340: {
                                    var10 = var32.charAt(2);
                                    if(Character.isUpperCase(var10)) {
                                       if(compatibleWithJavaBean) {
                                          var14 = decapitalize(var32.substring(2));
                                       } else {
                                          var46 = new StringBuilder();
                                          var46.append(Character.toLowerCase(var32.charAt(2)));
                                          var46.append(var32.substring(3));
                                          var14 = var46.toString();
                                       }
                                    } else if(var10 == 95) {
                                       var14 = var32.substring(3);
                                    } else {
                                       if(var10 != 102) {
                                          break label340;
                                       }

                                       var14 = var32.substring(2);
                                    }

                                    var19 = var15;
                                    var25 = var0;
                                    if(isJSONTypeIgnore(var0, var3, (String)var14)) {
                                       var14 = var16;
                                       var16 = var15;
                                       var15 = var14;
                                       var14 = var44;
                                    } else {
                                       var47 = getField(var0, (String)var14, (Field[])var16, (Map)var44);
                                       Field var58 = var47;
                                       if(var47 == null) {
                                          var58 = getField(var0, var32, (Field[])var16, (Map)var44);
                                       }

                                       label473: {
                                          JSONField var48;
                                          if(var58 != null) {
                                             if(var6) {
                                                var48 = (JSONField)var58.getAnnotation(JSONField.class);
                                             } else {
                                                var48 = null;
                                             }

                                             if(var48 != null) {
                                                if(!var48.serialize()) {
                                                   break label473;
                                                }

                                                var11 = var48.ordinal();
                                                var12 = SerializerFeature.of(var48.serialzeFeatures());
                                                if(var48.name().length() != 0) {
                                                   var50 = var48.name();
                                                   var14 = var50;
                                                   if(var4 != null) {
                                                      var52 = (String)var4.get(var50);
                                                      var14 = var52;
                                                      if(var52 == null) {
                                                         break label473;
                                                      }
                                                   }
                                                }
                                             }
                                          } else {
                                             var48 = null;
                                          }

                                          var52 = var14;
                                          if(var8 != null) {
                                             var52 = var8.translate((String)var14);
                                          }

                                          var14 = var52;
                                          if(var4 != null) {
                                             var52 = (String)var4.get(var52);
                                             var14 = var52;
                                             if(var52 == null) {
                                                break label473;
                                             }
                                          }

                                          setAccessible(var0, var58, var1);
                                          setAccessible(var0, var30, var1);
                                          ((Map)var15).put(var14, new FieldInfo((String)var14, var30, var58, var0, (Type)null, var11, var12, var27, var48, var7));
                                          var14 = var44;
                                          var15 = var16;
                                          var16 = var19;
                                          break label393;
                                       }

                                       var14 = var16;
                                       var16 = var52;
                                       var15 = var14;
                                       var14 = var44;
                                    }
                                    break label393;
                                 }
                              }

                              var14 = var16;
                              var16 = var15;
                              var15 = var14;
                              var14 = var44;
                           }

                           var25 = var0;
                           break label393;
                        }

                        var14 = var16;
                        var16 = var15;
                        var15 = var14;
                        var14 = var44;
                        break label393;
                     }

                     var14 = var16;
                     var16 = var15;
                     var15 = var14;
                     var14 = var44;
                  }

                  var19 = var16;
                  var45 = var66;
                  var51 = var62;
                  var44 = var14;
                  var14 = var21;
                  var21 = var63;
                  var16 = var15;
                  var15 = var19;
               }
            }
         }
      }

      ArrayList var67 = new ArrayList(((Object[])var23).length);
      var12 = ((Object[])var23).length;

      for(var11 = 0; var11 < var12; ++var11) {
         var15 = ((Object[])var23)[var11];
         if((((Field)var15).getModifiers() & 8) == 0 && !((Field)var15).getName().equals("this$0") && (((Field)var15).getModifiers() & 1) != 0) {
            var67.add(var15);
         }
      }

      for(var0 = var0.getSuperclass(); var0 != null && var0 != Object.class; var0 = var0.getSuperclass()) {
         Field[] var53 = var0.getDeclaredFields();
         var12 = var53.length;

         for(var11 = 0; var11 < var12; ++var11) {
            Field var57 = var53[var11];
            if((var57.getModifiers() & 8) == 0 && (var57.getModifiers() & 1) != 0) {
               var67.add(var57);
            }
         }
      }

      Iterator var59 = var67.iterator();

      while(var59.hasNext()) {
         var47 = (Field)var59.next();
         if(var6) {
            var41 = (JSONField)var47.getAnnotation(JSONField.class);
         } else {
            var41 = null;
         }

         var42 = var47.getName();
         if(var41 != null) {
            if(!var41.serialize()) {
               continue;
            }

            var11 = var41.ordinal();
            var12 = SerializerFeature.of(var41.serialzeFeatures());
            if(var41.name().length() != 0) {
               var42 = var41.name();
            }
         } else {
            var11 = 0;
            var12 = 0;
         }

         String var33 = var42;
         if(var4 != null) {
            var42 = (String)var4.get(var42);
            var33 = var42;
            if(var42 == null) {
               continue;
            }
         }

         var42 = var33;
         if(var8 != null) {
            var42 = var8.translate(var33);
         }

         if(!((Map)var19).containsKey(var42)) {
            setAccessible(var22, var47, var1);
            ((Map)var19).put(var42, new FieldInfo(var42, (Method)null, var47, var22, (Type)null, var11, var12, (JSONField)null, var41, var7));
         }
      }

      String[] var34;
      boolean var35;
      ArrayList var38;
      label273: {
         var38 = new ArrayList();
         if(var3 != null) {
            String[] var36 = var3.orders();
            var34 = var36;
            if(var36 != null) {
               var34 = var36;
               if(var36.length == ((Map)var19).size()) {
                  var11 = var36.length;
                  var1 = 0;

                  while(true) {
                     if(var1 >= var11) {
                        var35 = true;
                        var34 = var36;
                        break label273;
                     }

                     if(!((Map)var19).containsKey(var36[var1])) {
                        var34 = var36;
                        break;
                     }

                     ++var1;
                  }
               }
            }
         } else {
            var34 = null;
         }

         var35 = false;
      }

      if(var35) {
         var11 = var34.length;

         for(var1 = 0; var1 < var11; ++var1) {
            var38.add((FieldInfo)((Map)var19).get(var34[var1]));
         }
      } else {
         Iterator var37 = ((Map)var19).values().iterator();

         while(var37.hasNext()) {
            var38.add((FieldInfo)var37.next());
         }

         if(var5) {
            Collections.sort(var38);
         }
      }

      return var38;
   }

   public static String decapitalize(String var0) {
      if(var0 != null && var0.length() != 0) {
         if(var0.length() > 1 && Character.isUpperCase(var0.charAt(1)) && Character.isUpperCase(var0.charAt(0))) {
            return var0;
         } else {
            char[] var1 = var0.toCharArray();
            var1[0] = Character.toLowerCase(var1[0]);
            return new String(var1);
         }
      } else {
         return var0;
      }
   }

   public static Object defaultValue(Class<?> var0) {
      return var0 == Byte.TYPE?Byte.valueOf((byte)0):(var0 == Short.TYPE?Short.valueOf((short)0):(var0 == Integer.TYPE?Integer.valueOf(0):(var0 == Long.TYPE?Long.valueOf(0L):(var0 == Float.TYPE?Float.valueOf(0.0F):(var0 == Double.TYPE?Double.valueOf(0.0D):(var0 == Boolean.TYPE?Boolean.FALSE:(var0 == Character.TYPE?Character.valueOf('0'):null)))))));
   }

   public static long fnv_64_lower(String var0) {
      long var4 = -3750763034362895579L;

      long var6;
      for(int var1 = 0; var1 < var0.length(); var4 = var6) {
         char var3 = var0.charAt(var1);
         var6 = var4;
         if(var3 != 95) {
            if(var3 == 45) {
               var6 = var4;
            } else {
               char var2 = var3;
               if(var3 >= 65) {
                  var2 = var3;
                  if(var3 <= 90) {
                     var2 = (char)(var3 + 32);
                  }
               }

               var6 = (var4 ^ (long)var2) * 1099511628211L;
            }
         }

         ++var1;
      }

      return var4;
   }

   public static boolean getArgument(Type[] var0, TypeVariable[] var1, Type[] var2) {
      if(var2 == null) {
         return false;
      } else if(var1.length == 0) {
         return false;
      } else {
         int var3 = 0;

         boolean var5;
         boolean var6;
         for(var5 = false; var3 < var0.length; var5 = var6) {
            Type var7 = var0[var3];
            if(var7 instanceof ParameterizedType) {
               ParameterizedType var9 = (ParameterizedType)var7;
               Type[] var8 = var9.getActualTypeArguments();
               var6 = var5;
               if(getArgument(var8, var1, var2)) {
                  var0[var3] = new ParameterizedTypeImpl(var8, var9.getOwnerType(), var9.getRawType());
                  var6 = true;
               }
            } else {
               var6 = var5;
               if(var7 instanceof TypeVariable) {
                  for(int var4 = 0; var4 < var1.length; ++var4) {
                     if(var7.equals(var1[var4])) {
                        var0[var3] = var2[var4];
                        var5 = true;
                     }
                  }

                  var6 = var5;
               }
            }

            ++var3;
         }

         return var5;
      }
   }

   public static Class<?> getClass(Type var0) {
      if(var0.getClass() == Class.class) {
         return (Class)var0;
      } else if(var0 instanceof ParameterizedType) {
         return getClass(((ParameterizedType)var0).getRawType());
      } else if(var0 instanceof TypeVariable) {
         return (Class)((TypeVariable)var0).getBounds()[0];
      } else {
         if(var0 instanceof WildcardType) {
            Type[] var1 = ((WildcardType)var0).getUpperBounds();
            if(var1.length == 1) {
               return getClass(var1[0]);
            }
         }

         return Object.class;
      }
   }

   public static Class<?> getClassFromMapping(String var0) {
      return (Class)mappings.get(var0);
   }

   public static Type getCollectionItemType(Type var0) {
      if(var0 instanceof ParameterizedType) {
         Type var1 = ((ParameterizedType)var0).getActualTypeArguments()[0];
         var0 = var1;
         if(var1 instanceof WildcardType) {
            Type[] var2 = ((WildcardType)var1).getUpperBounds();
            var0 = var1;
            if(var2.length == 1) {
               var0 = var2[0];
            }
         }
      } else {
         label22: {
            if(var0 instanceof Class) {
               Class var3 = (Class)var0;
               if(!var3.getName().startsWith("java.")) {
                  var0 = getCollectionItemType(var3.getGenericSuperclass());
                  break label22;
               }
            }

            var0 = null;
         }
      }

      Object var4 = var0;
      if(var0 == null) {
         var4 = Object.class;
      }

      return (Type)var4;
   }

   public static Field getField(Class<?> var0, String var1, Field[] var2) {
      return getField(var0, var1, var2, (Map)null);
   }

   public static Field getField(Class<?> var0, String var1, Field[] var2, Map<Class<?>, Field[]> var3) {
      Field var5 = getField0(var0, var1, var2, var3);
      Field var4 = var5;
      StringBuilder var6;
      if(var5 == null) {
         var6 = new StringBuilder();
         var6.append("_");
         var6.append(var1);
         var4 = getField0(var0, var6.toString(), var2, var3);
      }

      var5 = var4;
      if(var4 == null) {
         var6 = new StringBuilder();
         var6.append("m_");
         var6.append(var1);
         var5 = getField0(var0, var6.toString(), var2, var3);
      }

      var4 = var5;
      if(var5 == null) {
         var6 = new StringBuilder();
         var6.append("m");
         var6.append(var1.substring(0, 1).toUpperCase());
         var6.append(var1.substring(1));
         var4 = getField0(var0, var6.toString(), var2, var3);
      }

      return var4;
   }

   private static Field getField0(Class<?> var0, String var1, Field[] var2, Map<Class<?>, Field[]> var3) {
      int var5 = var2.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         Field var7 = var2[var4];
         String var8 = var7.getName();
         if(var1.equals(var8)) {
            return var7;
         }

         if(var1.length() > 2) {
            char var6 = var1.charAt(0);
            if(var6 >= 97 && var6 <= 122) {
               var6 = var1.charAt(1);
               if(var6 >= 65 && var6 <= 90 && var1.equalsIgnoreCase(var8)) {
                  return var7;
               }
            }
         }
      }

      Class var10 = var0.getSuperclass();
      Field[] var9 = null;
      if(var10 != null) {
         if(var10 == Object.class) {
            return null;
         } else {
            if(var3 != null) {
               var9 = (Field[])var3.get(var10);
            }

            var2 = var9;
            if(var9 == null) {
               var9 = var10.getDeclaredFields();
               var2 = var9;
               if(var3 != null) {
                  var3.put(var10, var9);
                  var2 = var9;
               }
            }

            return getField(var10, var1, var2, var3);
         }
      } else {
         return null;
      }
   }

   public static Type getGenericParamType(Type var0) {
      Type var1 = var0;
      if(var0 instanceof Class) {
         var1 = getGenericParamType(((Class)var0).getGenericSuperclass());
      }

      return var1;
   }

   public static String[] getKoltinConstructorParameters(Class param0) {
      // $FF: Couldn't be decompiled
   }

   public static JSONField getSupperMethodAnnotation(Class<?> var0, Method var1) {
      Class[] var7 = var0.getInterfaces();
      int var5 = var7.length;

      int var2;
      int var3;
      int var4;
      for(var2 = 0; var2 < var5; ++var2) {
         Method[] var8 = var7[var2].getMethods();
         int var6 = var8.length;

         for(var3 = 0; var3 < var6; ++var3) {
            Method var9 = var8[var3];
            if(var9.getName().equals(var1.getName())) {
               Class[] var10 = var9.getParameterTypes();
               Class[] var11 = var1.getParameterTypes();
               if(var10.length == var11.length) {
                  var4 = 0;

                  boolean var14;
                  while(true) {
                     if(var4 >= var10.length) {
                        var14 = true;
                        break;
                     }

                     if(!var10[var4].equals(var11[var4])) {
                        var14 = false;
                        break;
                     }

                     ++var4;
                  }

                  if(var14) {
                     JSONField var19 = (JSONField)var9.getAnnotation(JSONField.class);
                     if(var19 != null) {
                        return var19;
                     }
                  }
               }
            }
         }
      }

      Class var15 = var0.getSuperclass();
      if(var15 == null) {
         return null;
      } else {
         if(Modifier.isAbstract(var15.getModifiers())) {
            Class[] var12 = var1.getParameterTypes();
            Method[] var16 = var15.getMethods();
            var4 = var16.length;

            for(var2 = 0; var2 < var4; ++var2) {
               Method var17 = var16[var2];
               Class[] var20 = var17.getParameterTypes();
               if(var20.length == var12.length && var17.getName().equals(var1.getName())) {
                  var3 = 0;

                  boolean var13;
                  while(true) {
                     if(var3 >= var12.length) {
                        var13 = true;
                        break;
                     }

                     if(!var20[var3].equals(var12[var3])) {
                        var13 = false;
                        break;
                     }

                     ++var3;
                  }

                  if(var13) {
                     JSONField var18 = (JSONField)var17.getAnnotation(JSONField.class);
                     if(var18 != null) {
                        return var18;
                     }
                  }
               }
            }
         }

         return null;
      }
   }

   public static boolean isGenericParamType(Type var0) {
      if(var0 instanceof ParameterizedType) {
         return true;
      } else if(var0 instanceof Class) {
         var0 = ((Class)var0).getGenericSuperclass();
         return var0 != Object.class && isGenericParamType(var0);
      } else {
         return false;
      }
   }

   private static boolean isJSONTypeIgnore(Class<?> var0, JSONType var1, String var2) {
      boolean var6 = false;
      if(var1 != null && var1.ignores() != null) {
         String[] var7 = var1.ignores();
         int var4 = var7.length;

         for(int var3 = 0; var3 < var4; ++var3) {
            if(var2.equalsIgnoreCase(var7[var3])) {
               return true;
            }
         }
      }

      var0 = var0.getSuperclass();
      boolean var5 = var6;
      if(var0 != Object.class) {
         var5 = var6;
         if(var0 != null) {
            var5 = var6;
            if(isJSONTypeIgnore(var0, (JSONType)var0.getAnnotation(JSONType.class), var2)) {
               var5 = true;
            }
         }
      }

      return var5;
   }

   public static boolean isKotlin(Class var0) {
      if(kotlin_metadata == null && !kotlin_metadata_error) {
         try {
            kotlin_metadata = Class.forName("kotlin.Metadata");
         } catch (Throwable var2) {
            kotlin_metadata_error = true;
         }
      }

      return kotlin_metadata == null?false:var0.isAnnotationPresent(kotlin_metadata);
   }

   private static boolean isKotlinIgnore(Class var0, String var1) {
      if(kotlinIgnores == null && !kotlinIgnores_error) {
         try {
            HashMap var2 = new HashMap();
            var2.put(Class.forName("btx"), new String[]{"getEndInclusive", "isEmpty"});
            var2.put(Class.forName("buc"), new String[]{"getEndInclusive", "isEmpty"});
            var2.put(Class.forName("buf"), new String[]{"getEndInclusive", "isEmpty"});
            var2.put(Class.forName("btz"), new String[]{"getEndInclusive", "isEmpty"});
            var2.put(Class.forName("bty"), new String[]{"getEndInclusive", "isEmpty"});
            kotlinIgnores = var2;
         } catch (Throwable var3) {
            kotlinIgnores_error = true;
         }
      }

      if(kotlinIgnores == null) {
         return false;
      } else {
         String[] var4 = (String[])kotlinIgnores.get(var0);
         return var4 == null?false:Arrays.binarySearch(var4, var1) >= 0;
      }
   }

   public static Class<?> loadClass(String var0, ClassLoader var1) {
      return loadClass(var0, var1, true);
   }

   public static Class<?> loadClass(String param0, ClassLoader param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public static boolean setAccessible(Class<?> var0, Member var1, int var2) {
      if(var1 != null) {
         if(!setAccessibleEnable) {
            return false;
         } else {
            var0 = var0.getSuperclass();
            if((var0 == null || var0 == Object.class) && (var1.getModifiers() & 1) != 0 && (var2 & 1) != 0) {
               return false;
            } else {
               AccessibleObject var4 = (AccessibleObject)var1;

               try {
                  var4.setAccessible(true);
                  return true;
               } catch (AccessControlException var3) {
                  setAccessibleEnable = false;
                  return false;
               }
            }
         }
      } else {
         return false;
      }
   }
}
