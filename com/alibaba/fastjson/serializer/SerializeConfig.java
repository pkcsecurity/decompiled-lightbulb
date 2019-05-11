package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.ArrayCodec;
import com.alibaba.fastjson.serializer.ArraySerializer;
import com.alibaba.fastjson.serializer.BigDecimalCodec;
import com.alibaba.fastjson.serializer.BooleanCodec;
import com.alibaba.fastjson.serializer.CollectionCodec;
import com.alibaba.fastjson.serializer.DateCodec;
import com.alibaba.fastjson.serializer.EnumSerializer;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.JSONSerializable;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ListSerializer;
import com.alibaba.fastjson.serializer.MapSerializer;
import com.alibaba.fastjson.serializer.MiscCodec;
import com.alibaba.fastjson.serializer.NumberCodec;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.IdentityHashMap;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.AbstractSequentialList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

public class SerializeConfig {

   public static final SerializeConfig globalInstance = new SerializeConfig();
   public PropertyNamingStrategy propertyNamingStrategy;
   private final IdentityHashMap<ObjectSerializer> serializers = new IdentityHashMap(1024);
   protected String typeKey = "@type";


   public SerializeConfig() {
      this.serializers.put(Boolean.class, BooleanCodec.instance);
      this.serializers.put(Character.class, MiscCodec.instance);
      this.serializers.put(Byte.class, IntegerCodec.instance);
      this.serializers.put(Short.class, IntegerCodec.instance);
      this.serializers.put(Integer.class, IntegerCodec.instance);
      this.serializers.put(Long.class, IntegerCodec.instance);
      this.serializers.put(Float.class, NumberCodec.instance);
      this.serializers.put(Double.class, NumberCodec.instance);
      this.serializers.put(Number.class, NumberCodec.instance);
      this.serializers.put(BigDecimal.class, BigDecimalCodec.instance);
      this.serializers.put(BigInteger.class, BigDecimalCodec.instance);
      this.serializers.put(String.class, StringCodec.instance);
      this.serializers.put(Object[].class, ArrayCodec.instance);
      this.serializers.put(Class.class, MiscCodec.instance);
      this.serializers.put(SimpleDateFormat.class, MiscCodec.instance);
      this.serializers.put(Locale.class, MiscCodec.instance);
      this.serializers.put(Currency.class, MiscCodec.instance);
      this.serializers.put(TimeZone.class, MiscCodec.instance);
      this.serializers.put(UUID.class, MiscCodec.instance);
      this.serializers.put(URI.class, MiscCodec.instance);
      this.serializers.put(URL.class, MiscCodec.instance);
      this.serializers.put(Pattern.class, MiscCodec.instance);
      this.serializers.put(Charset.class, MiscCodec.instance);
   }

   public static final SerializeConfig getGlobalInstance() {
      return globalInstance;
   }

   public ObjectSerializer get(Class<?> var1) {
      ObjectSerializer var8 = (ObjectSerializer)this.serializers.get(var1);
      Object var9 = var8;
      if(var8 == null) {
         Object var12;
         IdentityHashMap var13;
         if(Map.class.isAssignableFrom(var1)) {
            var13 = this.serializers;
            var12 = new MapSerializer();
            var13.put(var1, var12);
         } else if(AbstractSequentialList.class.isAssignableFrom(var1)) {
            var13 = this.serializers;
            var12 = CollectionCodec.instance;
            var13.put(var1, var12);
         } else if(List.class.isAssignableFrom(var1)) {
            var13 = this.serializers;
            var12 = new ListSerializer();
            var13.put(var1, var12);
         } else if(Collection.class.isAssignableFrom(var1)) {
            var13 = this.serializers;
            var12 = CollectionCodec.instance;
            var13.put(var1, var12);
         } else if(Date.class.isAssignableFrom(var1)) {
            var13 = this.serializers;
            var12 = DateCodec.instance;
            var13.put(var1, var12);
         } else if(JSONAware.class.isAssignableFrom(var1)) {
            var13 = this.serializers;
            var12 = MiscCodec.instance;
            var13.put(var1, var12);
         } else if(JSONSerializable.class.isAssignableFrom(var1)) {
            var13 = this.serializers;
            var12 = MiscCodec.instance;
            var13.put(var1, var12);
         } else if(JSONStreamAware.class.isAssignableFrom(var1)) {
            var13 = this.serializers;
            var12 = MiscCodec.instance;
            var13.put(var1, var12);
         } else {
            label92: {
               if(!var1.isEnum()) {
                  Class var14 = var1.getSuperclass();
                  if(var14 == null || var14 == Object.class || !var14.isEnum()) {
                     if(var1.isArray()) {
                        var14 = var1.getComponentType();
                        ObjectSerializer var10 = this.get(var14);
                        var13 = this.serializers;
                        var12 = new ArraySerializer(var14, var10);
                        var13.put(var1, var12);
                     } else if(Throwable.class.isAssignableFrom(var1)) {
                        var12 = new JavaBeanSerializer(var1, this.propertyNamingStrategy);
                        ((JavaBeanSerializer)var12).features |= SerializerFeature.WriteClassName.mask;
                        this.serializers.put(var1, var12);
                     } else if(TimeZone.class.isAssignableFrom(var1)) {
                        var13 = this.serializers;
                        var12 = MiscCodec.instance;
                        var13.put(var1, var12);
                     } else if(Charset.class.isAssignableFrom(var1)) {
                        var13 = this.serializers;
                        var12 = MiscCodec.instance;
                        var13.put(var1, var12);
                     } else if(Enumeration.class.isAssignableFrom(var1)) {
                        var13 = this.serializers;
                        var12 = MiscCodec.instance;
                        var13.put(var1, var12);
                     } else if(Calendar.class.isAssignableFrom(var1)) {
                        var13 = this.serializers;
                        var12 = DateCodec.instance;
                        var13.put(var1, var12);
                     } else {
                        Class[] var15 = var1.getInterfaces();
                        int var7 = var15.length;
                        boolean var5 = false;
                        boolean var4 = false;
                        int var3 = 0;

                        boolean var2;
                        boolean var11;
                        while(true) {
                           boolean var6 = true;
                           var2 = var4;
                           if(var3 < var7) {
                              Class var16 = var15[var3];
                              if(!var16.getName().equals("net.sf.cglib.proxy.Factory") && !var16.getName().equals("org.springframework.cglib.proxy.Factory")) {
                                 if(var16.getName().equals("javassist.util.proxy.ProxyObject")) {
                                    var2 = var5;
                                    var11 = var6;
                                    break;
                                 }

                                 ++var3;
                                 continue;
                              }

                              var2 = true;
                           }

                           var11 = false;
                           break;
                        }

                        if(var2 || var11) {
                           var8 = this.get(var1.getSuperclass());
                           this.serializers.put(var1, var8);
                           return var8;
                        }

                        if(var1.getName().startsWith("android.net.Uri$")) {
                           var12 = MiscCodec.instance;
                        } else {
                           var12 = new JavaBeanSerializer(var1, this.propertyNamingStrategy);
                        }

                        this.serializers.put(var1, var12);
                     }
                     break label92;
                  }
               }

               var13 = this.serializers;
               var12 = new EnumSerializer();
               var13.put(var1, var12);
            }
         }

         var9 = var12;
         if(var12 == null) {
            var9 = (ObjectSerializer)this.serializers.get(var1);
         }
      }

      return (ObjectSerializer)var9;
   }

   public String getTypeKey() {
      return this.typeKey;
   }

   public boolean put(Type var1, ObjectSerializer var2) {
      return this.serializers.put(var1, var2);
   }

   public ObjectSerializer registerIfNotExists(Class<?> var1) {
      return this.registerIfNotExists(var1, var1.getModifiers(), false, true, true, true);
   }

   public ObjectSerializer registerIfNotExists(Class<?> var1, int var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      ObjectSerializer var7 = (ObjectSerializer)this.serializers.get(var1);
      if(var7 == null) {
         JavaBeanSerializer var8 = new JavaBeanSerializer(var1, var2, (Map)null, var3, var4, var5, var6, this.propertyNamingStrategy);
         this.serializers.put(var1, var8);
         return var8;
      } else {
         return var7;
      }
   }

   public void setTypeKey(String var1) {
      this.typeKey = var1;
   }
}
