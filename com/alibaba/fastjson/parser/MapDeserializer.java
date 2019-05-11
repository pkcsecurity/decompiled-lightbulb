package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class MapDeserializer implements ObjectDeserializer {

   public static MapDeserializer instance = new MapDeserializer();


   public static Object parseMap(DefaultJSONParser param0, Map<Object, Object> param1, Type param2, Type param3, Object param4) {
      // $FF: Couldn't be decompiled
   }

   public static Map parseMap(DefaultJSONParser param0, Map<String, Object> param1, Type param2, Object param3) {
      // $FF: Couldn't be decompiled
   }

   protected Map<?, ?> createMap(Type var1) {
      if(var1 == Properties.class) {
         return new Properties();
      } else if(var1 == Hashtable.class) {
         return new Hashtable();
      } else if(var1 == IdentityHashMap.class) {
         return new IdentityHashMap();
      } else if(var1 != SortedMap.class && var1 != TreeMap.class) {
         if(var1 != ConcurrentMap.class && var1 != ConcurrentHashMap.class) {
            if(var1 != Map.class && var1 != HashMap.class) {
               if(var1 == LinkedHashMap.class) {
                  return new LinkedHashMap();
               } else if(var1 == JSONObject.class) {
                  return new JSONObject();
               } else if(var1 instanceof ParameterizedType) {
                  ParameterizedType var5 = (ParameterizedType)var1;
                  Type var8 = var5.getRawType();
                  return (Map)(EnumMap.class.equals(var8)?new EnumMap((Class)var5.getActualTypeArguments()[0]):this.createMap(var8));
               } else {
                  Class var2 = (Class)var1;
                  if(var2.isInterface()) {
                     StringBuilder var7 = new StringBuilder();
                     var7.append("unsupport type ");
                     var7.append(var1);
                     throw new JSONException(var7.toString());
                  } else {
                     try {
                        Map var6 = (Map)var2.newInstance();
                        return var6;
                     } catch (Exception var4) {
                        StringBuilder var3 = new StringBuilder();
                        var3.append("unsupport type ");
                        var3.append(var1);
                        throw new JSONException(var3.toString(), var4);
                     }
                  }
               }
            } else {
               return new HashMap();
            }
         } else {
            return new ConcurrentHashMap();
         }
      } else {
         return new TreeMap();
      }
   }

   public <T extends Object> T deserialze(DefaultJSONParser param1, Type param2, Object param3) {
      // $FF: Couldn't be decompiled
   }
}
