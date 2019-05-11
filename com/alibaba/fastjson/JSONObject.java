package com.alibaba.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class JSONObject extends JSON implements Serializable, Cloneable, InvocationHandler, Map<String, Object> {

   private final Map<String, Object> map;


   public JSONObject() {
      this(16, false);
   }

   public JSONObject(int var1) {
      this(var1, false);
   }

   public JSONObject(int var1, boolean var2) {
      if(var2) {
         this.map = new LinkedHashMap(var1);
      } else {
         this.map = new HashMap(var1);
      }
   }

   public JSONObject(Map<String, Object> var1) {
      this.map = var1;
   }

   public JSONObject(boolean var1) {
      this(16, var1);
   }

   public void clear() {
      this.map.clear();
   }

   public Object clone() {
      return new JSONObject(new LinkedHashMap(this.map));
   }

   public boolean containsKey(Object var1) {
      return this.map.containsKey(var1);
   }

   public boolean containsValue(Object var1) {
      return this.map.containsValue(var1);
   }

   public Set<Entry<String, Object>> entrySet() {
      return this.map.entrySet();
   }

   public boolean equals(Object var1) {
      return this.map.equals(var1);
   }

   public Object get(Object var1) {
      return this.map.get(var1);
   }

   public BigDecimal getBigDecimal(String var1) {
      return TypeUtils.castToBigDecimal(this.get(var1));
   }

   public BigInteger getBigInteger(String var1) {
      return TypeUtils.castToBigInteger(this.get(var1));
   }

   public Boolean getBoolean(String var1) {
      Object var2 = this.get(var1);
      return var2 == null?null:TypeUtils.castToBoolean(var2);
   }

   public boolean getBooleanValue(String var1) {
      Boolean var2 = TypeUtils.castToBoolean(this.get(var1));
      return var2 == null?false:var2.booleanValue();
   }

   public Byte getByte(String var1) {
      return TypeUtils.castToByte(this.get(var1));
   }

   public byte getByteValue(String var1) {
      Byte var2 = TypeUtils.castToByte(this.get(var1));
      return var2 == null?0:var2.byteValue();
   }

   public byte[] getBytes(String var1) {
      Object var2 = this.get(var1);
      return var2 == null?null:TypeUtils.castToBytes(var2);
   }

   public Date getDate(String var1) {
      return TypeUtils.castToDate(this.get(var1));
   }

   public Double getDouble(String var1) {
      return TypeUtils.castToDouble(this.get(var1));
   }

   public double getDoubleValue(String var1) {
      Double var2 = TypeUtils.castToDouble(this.get(var1));
      return var2 == null?0.0D:var2.doubleValue();
   }

   public Float getFloat(String var1) {
      return TypeUtils.castToFloat(this.get(var1));
   }

   public float getFloatValue(String var1) {
      Float var2 = TypeUtils.castToFloat(this.get(var1));
      return var2 == null?0.0F:var2.floatValue();
   }

   public Map<String, Object> getInnerMap() {
      return this.map;
   }

   public int getIntValue(String var1) {
      Integer var2 = TypeUtils.castToInt(this.get(var1));
      return var2 == null?0:var2.intValue();
   }

   public Integer getInteger(String var1) {
      return TypeUtils.castToInt(this.get(var1));
   }

   public JSONArray getJSONArray(String var1) {
      Object var2 = this.map.get(var1);
      return var2 instanceof JSONArray?(JSONArray)var2:(var2 instanceof String?(JSONArray)JSON.parse((String)var2):(JSONArray)toJSON(var2));
   }

   public JSONObject getJSONObject(String var1) {
      Object var2 = this.map.get(var1);
      return var2 instanceof JSONObject?(JSONObject)var2:(var2 instanceof String?JSON.parseObject((String)var2):(JSONObject)toJSON(var2));
   }

   public Long getLong(String var1) {
      return TypeUtils.castToLong(this.get(var1));
   }

   public long getLongValue(String var1) {
      Long var2 = TypeUtils.castToLong(this.get(var1));
      return var2 == null?0L:var2.longValue();
   }

   public <T extends Object> T getObject(String var1, Class<T> var2) {
      return TypeUtils.castToJavaBean(this.map.get(var1), var2);
   }

   public <T extends Object> T getObject(String var1, Class<T> var2, Feature ... var3) {
      Object var6 = this.map.get(var1);
      int var5 = JSON.DEFAULT_PARSER_FEATURE;

      for(int var4 = 0; var4 < var3.length; ++var4) {
         var5 |= var3[var4].mask;
      }

      return TypeUtils.cast(var6, var2, ParserConfig.global, var5);
   }

   public Short getShort(String var1) {
      return TypeUtils.castToShort(this.get(var1));
   }

   public short getShortValue(String var1) {
      Short var2 = TypeUtils.castToShort(this.get(var1));
      return var2 == null?0:var2.shortValue();
   }

   public String getString(String var1) {
      Object var2 = this.get(var1);
      return var2 == null?null:var2.toString();
   }

   public int hashCode() {
      return this.map.hashCode();
   }

   public Object invoke(Object var1, Method var2, Object[] var3) throws Throwable {
      Class[] var6 = var2.getParameterTypes();
      int var4 = var6.length;
      String var5 = null;
      String var8;
      if(var4 != 1) {
         if(var6.length == 0) {
            if(var2.getReturnType() == Void.TYPE) {
               throw new JSONException("illegal getter");
            } else {
               JSONField var10 = (JSONField)var2.getAnnotation(JSONField.class);
               var8 = var5;
               if(var10 != null) {
                  var8 = var5;
                  if(var10.name().length() != 0) {
                     var8 = var10.name();
                  }
               }

               String var11 = var8;
               if(var8 == null) {
                  var8 = var2.getName();
                  StringBuilder var12;
                  if(var8.startsWith("get")) {
                     var8 = var8.substring(3);
                     if(var8.length() == 0) {
                        throw new JSONException("illegal getter");
                     }

                     var12 = new StringBuilder();
                     var12.append(Character.toLowerCase(var8.charAt(0)));
                     var12.append(var8.substring(1));
                     var11 = var12.toString();
                  } else {
                     if(!var8.startsWith("is")) {
                        if(var8.startsWith("hashCode")) {
                           return Integer.valueOf(this.hashCode());
                        }

                        if(var8.startsWith("toString")) {
                           return this.toString();
                        }

                        throw new JSONException("illegal getter");
                     }

                     var8 = var8.substring(2);
                     if(var8.length() == 0) {
                        throw new JSONException("illegal getter");
                     }

                     var12 = new StringBuilder();
                     var12.append(Character.toLowerCase(var8.charAt(0)));
                     var12.append(var8.substring(1));
                     var11 = var12.toString();
                  }
               }

               return TypeUtils.cast(this.map.get(var11), var2.getGenericReturnType(), ParserConfig.global);
            }
         } else {
            throw new UnsupportedOperationException(var2.toGenericString());
         }
      } else if(var2.getName().equals("equals")) {
         return Boolean.valueOf(this.equals(var3[0]));
      } else if(var2.getReturnType() != Void.TYPE) {
         throw new JSONException("illegal setter");
      } else {
         JSONField var7 = (JSONField)var2.getAnnotation(JSONField.class);
         if(var7 != null && var7.name().length() != 0) {
            var8 = var7.name();
         } else {
            var8 = null;
         }

         var5 = var8;
         if(var8 == null) {
            var8 = var2.getName();
            if(!var8.startsWith("set")) {
               throw new JSONException("illegal setter");
            }

            var8 = var8.substring(3);
            if(var8.length() == 0) {
               throw new JSONException("illegal setter");
            }

            StringBuilder var9 = new StringBuilder();
            var9.append(Character.toLowerCase(var8.charAt(0)));
            var9.append(var8.substring(1));
            var5 = var9.toString();
         }

         this.map.put(var5, var3[0]);
         return null;
      }
   }

   public boolean isEmpty() {
      return this.map.isEmpty();
   }

   public Set<String> keySet() {
      return this.map.keySet();
   }

   public Object put(String var1, Object var2) {
      return this.map.put(var1, var2);
   }

   public void putAll(Map<? extends String, ? extends Object> var1) {
      this.map.putAll(var1);
   }

   public Object remove(Object var1) {
      return this.map.remove(var1);
   }

   public int size() {
      return this.map.size();
   }

   public Collection<Object> values() {
      return this.map.values();
   }
}
