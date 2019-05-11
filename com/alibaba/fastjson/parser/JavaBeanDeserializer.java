package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultFieldDeserializer;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JavaBeanInfo;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessable;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class JavaBeanDeserializer implements ObjectDeserializer {

   private final Map<String, FieldDeserializer> alterNameFieldDeserializers;
   public final JavaBeanInfo beanInfo;
   protected final Class<?> clazz;
   private ConcurrentMap<String, Object> extraFieldDeserializers;
   private final FieldDeserializer[] fieldDeserializers;
   private transient long[] smartMatchHashArray;
   private transient int[] smartMatchHashArrayMapping;
   private final FieldDeserializer[] sortedFieldDeserializers;


   public JavaBeanDeserializer(ParserConfig var1, Class<?> var2, Type var3) {
      this(var1, var2, var3, JavaBeanInfo.build(var2, var2.getModifiers(), var3, false, true, true, true, var1.propertyNamingStrategy));
   }

   public JavaBeanDeserializer(ParserConfig var1, Class<?> var2, Type var3, JavaBeanInfo var4) {
      this.clazz = var2;
      this.beanInfo = var4;
      this.sortedFieldDeserializers = new FieldDeserializer[var4.sortedFields.length];
      int var8 = var4.sortedFields.length;
      byte var7 = 0;
      HashMap var16 = null;

      int var5;
      int var6;
      for(var5 = 0; var5 < var8; ++var5) {
         FieldInfo var10 = var4.sortedFields[var5];
         FieldDeserializer var11 = var1.createFieldDeserializer(var1, var2, var10);
         this.sortedFieldDeserializers[var5] = var11;
         String[] var12 = var10.alternateNames;
         int var9 = var12.length;

         HashMap var14;
         for(var6 = 0; var6 < var9; var16 = var14) {
            String var13 = var12[var6];
            var14 = var16;
            if(var16 == null) {
               var14 = new HashMap();
            }

            var14.put(var13, var11);
            ++var6;
         }
      }

      this.alterNameFieldDeserializers = var16;
      this.fieldDeserializers = new FieldDeserializer[var4.fields.length];
      var6 = var4.fields.length;

      for(var5 = var7; var5 < var6; ++var5) {
         FieldDeserializer var15 = this.getFieldDeserializer(var4.fields[var5].name);
         this.fieldDeserializers[var5] = var15;
      }

   }

   private <T extends Object> T deserialze(DefaultJSONParser param1, Type param2, Object param3, Object param4) {
      // $FF: Couldn't be decompiled
   }

   private <T extends Object> T deserialzeArrayMapping(DefaultJSONParser param1, Type param2, Object param3, Object param4) {
      // $FF: Couldn't be decompiled
   }

   private boolean parseField(DefaultJSONParser var1, String var2, Object var3, Type var4, Map<String, Object> var5) {
      JSONLexer var15 = var1.lexer;
      FieldDeserializer var14 = this.getFieldDeserializer(var2);
      FieldDeserializer var13 = var14;
      int var6;
      int var7;
      int var8;
      if(var14 == null) {
         long var9 = TypeUtils.fnv_64_lower(var2);
         if(this.smartMatchHashArray == null) {
            long[] var20 = new long[this.sortedFieldDeserializers.length];

            for(var6 = 0; var6 < this.sortedFieldDeserializers.length; ++var6) {
               var20[var6] = TypeUtils.fnv_64_lower(this.sortedFieldDeserializers[var6].fieldInfo.name);
            }

            Arrays.sort(var20);
            this.smartMatchHashArray = var20;
         }

         var6 = Arrays.binarySearch(this.smartMatchHashArray, var9);
         boolean var11;
         if(var6 < 0) {
            boolean var12 = var2.startsWith("is");
            var11 = var12;
            if(var12) {
               var9 = TypeUtils.fnv_64_lower(var2.substring(2));
               var6 = Arrays.binarySearch(this.smartMatchHashArray, var9);
               var11 = var12;
            }
         } else {
            var11 = false;
         }

         var13 = var14;
         if(var6 >= 0) {
            if(this.smartMatchHashArrayMapping == null) {
               int[] var21 = new int[this.smartMatchHashArray.length];
               Arrays.fill(var21, -1);

               for(var7 = 0; var7 < this.sortedFieldDeserializers.length; ++var7) {
                  var8 = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv_64_lower(this.sortedFieldDeserializers[var7].fieldInfo.name));
                  if(var8 >= 0) {
                     var21[var8] = var7;
                  }
               }

               this.smartMatchHashArrayMapping = var21;
            }

            var6 = this.smartMatchHashArrayMapping[var6];
            var13 = var14;
            if(var6 != -1) {
               var14 = this.sortedFieldDeserializers[var6];
               Class var16 = var14.fieldInfo.fieldClass;
               var13 = var14;
               if(var11) {
                  var13 = var14;
                  if(var16 != Boolean.TYPE) {
                     var13 = var14;
                     if(var16 != Boolean.class) {
                        var13 = null;
                     }
                  }
               }
            }
         }
      }

      var6 = Feature.SupportNonPublicField.mask;
      Object var22 = var13;
      if(var13 == null) {
         label115: {
            if((var1.lexer.features & var6) == 0) {
               var22 = var13;
               if((var6 & this.beanInfo.parserFeatures) == 0) {
                  break label115;
               }
            }

            if(this.extraFieldDeserializers == null) {
               ConcurrentHashMap var24 = new ConcurrentHashMap(1, 0.75F, 1);

               for(Class var23 = this.clazz; var23 != null && var23 != Object.class; var23 = var23.getSuperclass()) {
                  Field[] var17 = var23.getDeclaredFields();
                  var7 = var17.length;

                  for(var6 = 0; var6 < var7; ++var6) {
                     Field var18 = var17[var6];
                     String var19 = var18.getName();
                     if(this.getFieldDeserializer(var19) == null) {
                        var8 = var18.getModifiers();
                        if((var8 & 16) == 0 && (var8 & 8) == 0) {
                           var24.put(var19, var18);
                        }
                     }
                  }
               }

               this.extraFieldDeserializers = var24;
            }

            Object var26 = this.extraFieldDeserializers.get(var2);
            var22 = var13;
            if(var26 != null) {
               if(var26 instanceof FieldDeserializer) {
                  var22 = (FieldDeserializer)var26;
               } else {
                  Field var25 = (Field)var26;
                  var25.setAccessible(true);
                  FieldInfo var27 = new FieldInfo(var2, var25.getDeclaringClass(), var25.getType(), var25.getGenericType(), var25, 0, 0);
                  var22 = new DefaultFieldDeserializer(var1.config, this.clazz, var27);
                  this.extraFieldDeserializers.put(var2, var22);
               }
            }
         }
      }

      if(var22 == null) {
         this.parseExtra(var1, var3, var2);
         return false;
      } else {
         var15.nextTokenWithChar(':');
         ((FieldDeserializer)var22).parseField(var1, var3, var4, var5);
         return true;
      }
   }

   protected Object createInstance(DefaultJSONParser param1, Type param2) {
      // $FF: Couldn't be decompiled
   }

   public Object createInstance(Map<String, Object> var1, ParserConfig var2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      Constructor var5 = this.beanInfo.creatorConstructor;
      int var3 = 0;
      Object var14;
      if(var5 == null) {
         var14 = this.createInstance((DefaultJSONParser)null, (Type)this.clazz);
         Iterator var11 = var1.entrySet().iterator();

         while(var11.hasNext()) {
            Entry var16 = (Entry)var11.next();
            FieldDeserializer var15 = this.getFieldDeserializer((String)var16.getKey());
            if(var15 != null) {
               Object var17 = var16.getValue();
               Method var18 = var15.fieldInfo.method;
               if(var18 != null) {
                  var18.invoke(var14, new Object[]{TypeUtils.cast(var17, var18.getGenericParameterTypes()[0], var2)});
               } else {
                  var15.fieldInfo.field.set(var14, TypeUtils.cast(var17, var15.fieldInfo.fieldType, var2));
               }
            }
         }

         return var14;
      } else {
         FieldInfo[] var6 = this.beanInfo.fields;
         int var4 = var6.length;

         Object[] var7;
         for(var7 = new Object[var4]; var3 < var4; ++var3) {
            FieldInfo var8 = var6[var3];
            var14 = var1.get(var8.name);
            Object var12 = var14;
            if(var14 == null) {
               var12 = TypeUtils.defaultValue(var8.fieldClass);
            }

            var7[var3] = var12;
         }

         if(this.beanInfo.creatorConstructor != null) {
            try {
               Object var10 = this.beanInfo.creatorConstructor.newInstance(var7);
               return var10;
            } catch (Exception var9) {
               StringBuilder var13 = new StringBuilder();
               var13.append("create instance error, ");
               var13.append(this.beanInfo.creatorConstructor.toGenericString());
               throw new JSONException(var13.toString(), var9);
            }
         } else {
            return null;
         }
      }
   }

   public <T extends Object> T deserialze(DefaultJSONParser var1, Type var2, Object var3) {
      return this.deserialze(var1, var2, var3, (Object)null);
   }

   protected FieldDeserializer getFieldDeserializer(String var1) {
      if(var1 == null) {
         return null;
      } else {
         boolean var6 = this.beanInfo.ordered;
         int var2 = 0;
         byte var3 = 0;
         if(var6) {
            for(var2 = var3; var2 < this.sortedFieldDeserializers.length; ++var2) {
               FieldDeserializer var7 = this.sortedFieldDeserializers[var2];
               if(var7.fieldInfo.name.equalsIgnoreCase(var1)) {
                  return var7;
               }
            }

            return null;
         } else {
            int var8 = this.sortedFieldDeserializers.length - 1;

            while(var2 <= var8) {
               int var4 = var2 + var8 >>> 1;
               int var5 = this.sortedFieldDeserializers[var4].fieldInfo.name.compareTo(var1);
               if(var5 < 0) {
                  var2 = var4 + 1;
               } else {
                  if(var5 <= 0) {
                     return this.sortedFieldDeserializers[var4];
                  }

                  var8 = var4 - 1;
               }
            }

            if(this.alterNameFieldDeserializers != null) {
               return (FieldDeserializer)this.alterNameFieldDeserializers.get(var1);
            } else {
               return null;
            }
         }
      }
   }

   protected FieldDeserializer getFieldDeserializerByHash(long var1) {
      for(int var3 = 0; var3 < this.sortedFieldDeserializers.length; ++var3) {
         FieldDeserializer var4 = this.sortedFieldDeserializers[var3];
         if(var4.fieldInfo.nameHashCode == var1) {
            return var4;
         }
      }

      return null;
   }

   protected JavaBeanDeserializer getSeeAlso(ParserConfig var1, JavaBeanInfo var2, String var3) {
      if(var2.jsonType == null) {
         return null;
      } else {
         Class[] var8 = var2.jsonType.seeAlso();
         int var5 = var8.length;

         for(int var4 = 0; var4 < var5; ++var4) {
            ObjectDeserializer var6 = var1.getDeserializer(var8[var4]);
            if(var6 instanceof JavaBeanDeserializer) {
               JavaBeanDeserializer var9 = (JavaBeanDeserializer)var6;
               JavaBeanInfo var7 = var9.beanInfo;
               if(var7.typeName.equals(var3)) {
                  return var9;
               }

               var9 = this.getSeeAlso(var1, var7, var3);
               if(var9 != null) {
                  return var9;
               }
            }
         }

         return null;
      }
   }

   void parseExtra(DefaultJSONParser var1, Object var2, String var3) {
      JSONLexer var4 = var1.lexer;
      if((var1.lexer.features & Feature.IgnoreNotMatch.mask) == 0) {
         StringBuilder var9 = new StringBuilder();
         var9.append("setter not found, class ");
         var9.append(this.clazz.getName());
         var9.append(", property ");
         var9.append(var3);
         throw new JSONException(var9.toString());
      } else {
         var4.nextTokenWithChar(':');
         Type var10 = null;
         Object var5 = null;
         List var6 = var1.extraTypeProviders;
         if(var6 != null) {
            Iterator var12 = var6.iterator();

            for(var10 = (Type)var5; var12.hasNext(); var10 = ((ExtraTypeProvider)var12.next()).getExtraType(var2, var3)) {
               ;
            }
         }

         Object var11;
         if(var10 == null) {
            var11 = var1.parse();
         } else {
            var11 = var1.parseObject(var10);
         }

         if(var2 instanceof ExtraProcessable) {
            ((ExtraProcessable)var2).processExtra(var3, var11);
         } else {
            List var7 = var1.extraProcessors;
            if(var7 != null) {
               Iterator var8 = var7.iterator();

               while(var8.hasNext()) {
                  ((ExtraProcessor)var8.next()).processExtra(var2, var3, var11);
               }
            }

         }
      }
   }
}
