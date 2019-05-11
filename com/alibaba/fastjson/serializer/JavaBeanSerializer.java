package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.FieldSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JavaBeanSerializer implements ObjectSerializer {

   private static final char[] false_chars = new char[]{'f', 'a', 'l', 's', 'e'};
   private static final char[] true_chars = new char[]{'t', 'r', 'u', 'e'};
   protected int features;
   private final FieldSerializer[] getters;
   private final FieldSerializer[] sortedGetters;
   protected final String typeKey;
   protected final String typeName;


   public JavaBeanSerializer(Class<?> var1) {
      this(var1, (PropertyNamingStrategy)null);
   }

   public JavaBeanSerializer(Class<?> var1, int var2, Map<String, String> var3, boolean var4, boolean var5, boolean var6, boolean var7, PropertyNamingStrategy var8) {
      this.features = 0;
      Object var14 = null;
      JSONType var12;
      if(var5) {
         var12 = (JSONType)((Class)var1).getAnnotation(JSONType.class);
      } else {
         var12 = null;
      }

      Object var11;
      String var13;
      Object var15;
      label88: {
         var15 = var1;
         if(var12 != null) {
            this.features = SerializerFeature.of(var12.serialzeFeatures());
            String var16 = var12.typeName();
            if(var16.length() != 0) {
               Class var25 = ((Class)var1).getSuperclass();
               String var17 = null;

               while(true) {
                  var11 = var17;
                  if(var25 == null) {
                     break;
                  }

                  var11 = var17;
                  if(var25 == Object.class) {
                     break;
                  }

                  JSONType var24 = (JSONType)var25.getAnnotation(JSONType.class);
                  if(var24 == null) {
                     var11 = var17;
                     break;
                  }

                  var17 = var24.typeKey();
                  if(var17.length() != 0) {
                     var11 = var17;
                     break;
                  }

                  var25 = var25.getSuperclass();
               }

               Class[] var26 = ((Class)var1).getInterfaces();
               int var10 = var26.length;
               int var9 = 0;

               while(true) {
                  var1 = var11;
                  if(var9 >= var10) {
                     break;
                  }

                  JSONType var18 = (JSONType)var26[var9].getAnnotation(JSONType.class);
                  if(var18 != null) {
                     var1 = var18.typeKey();
                     var11 = var1;
                     if(((String)var1).length() != 0) {
                        break;
                     }
                  }

                  ++var9;
               }

               var13 = var16;
               var11 = var1;
               if(var1 != null) {
                  var13 = var16;
                  var11 = var1;
                  if(((String)var1).length() == 0) {
                     var11 = null;
                     var13 = var16;
                  }
               }
               break label88;
            }
         }

         var13 = null;
         var11 = var13;
      }

      this.typeName = var13;
      this.typeKey = (String)var11;
      List var27 = TypeUtils.computeGetters((Class)var1, var2, var4, var12, var3, false, var6, var7, var8);
      ArrayList var20 = new ArrayList();
      Iterator var28 = var27.iterator();

      while(var28.hasNext()) {
         var20.add(new FieldSerializer((FieldInfo)var28.next()));
      }

      this.getters = (FieldSerializer[])var20.toArray(new FieldSerializer[var20.size()]);
      String[] var22 = (String[])var14;
      if(var12 != null) {
         var22 = var12.orders();
      }

      if(var22 != null && var22.length != 0) {
         List var19 = TypeUtils.computeGetters((Class)var15, var2, var4, var12, var3, true, var6, var7, var8);
         var20 = new ArrayList();
         Iterator var21 = var19.iterator();

         while(var21.hasNext()) {
            var20.add(new FieldSerializer((FieldInfo)var21.next()));
         }

         this.sortedGetters = (FieldSerializer[])var20.toArray(new FieldSerializer[var20.size()]);
      } else {
         FieldSerializer[] var23 = new FieldSerializer[this.getters.length];
         System.arraycopy(this.getters, 0, var23, 0, this.getters.length);
         Arrays.sort(var23);
         if(Arrays.equals(var23, this.getters)) {
            this.sortedGetters = this.getters;
         } else {
            this.sortedGetters = var23;
         }
      }
   }

   public JavaBeanSerializer(Class<?> var1, PropertyNamingStrategy var2) {
      this(var1, var1.getModifiers(), (Map)null, false, true, true, true, var2);
   }

   public JavaBeanSerializer(Class<?> var1, String ... var2) {
      this(var1, var1.getModifiers(), map(var2), false, true, true, true, (PropertyNamingStrategy)null);
   }

   private static Map<String, String> map(String ... var0) {
      HashMap var3 = new HashMap();
      int var2 = var0.length;

      for(int var1 = 0; var1 < var2; ++var1) {
         String var4 = var0[var1];
         var3.put(var4, var4);
      }

      return var3;
   }

   public Map<String, Object> getFieldValuesMap(Object var1) throws Exception {
      LinkedHashMap var4 = new LinkedHashMap(this.sortedGetters.length);
      FieldSerializer[] var5 = this.sortedGetters;
      int var3 = var5.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         FieldSerializer var6 = var5[var2];
         var4.put(var6.fieldInfo.name, var6.getPropertyValue(var1));
      }

      return var4;
   }

   public void write(JSONSerializer param1, Object param2, Object param3, Type param4) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
