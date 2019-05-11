package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class FieldInfo implements Comparable<FieldInfo> {

   public final String[] alternateNames;
   public final Class<?> declaringClass;
   public final Field field;
   public final boolean fieldAccess;
   private final JSONField fieldAnnotation;
   public final Class<?> fieldClass;
   public final boolean fieldTransient;
   public final Type fieldType;
   public final String format;
   public final boolean getOnly;
   public final boolean isEnum;
   public final Method method;
   private final JSONField methodAnnotation;
   public final String name;
   public final long nameHashCode;
   private int ordinal;


   public FieldInfo(String var1, Class<?> var2, Class<?> var3, Type var4, Field var5, int var6, int var7) {
      this.ordinal = 0;
      this.name = var1;
      this.declaringClass = var2;
      this.fieldClass = var3;
      this.fieldType = var4;
      this.method = null;
      this.field = var5;
      this.ordinal = var6;
      boolean var8 = var3.isEnum();
      boolean var9 = true;
      if(var8 && !JSONAware.class.isAssignableFrom(var3)) {
         var8 = true;
      } else {
         var8 = false;
      }

      this.isEnum = var8;
      this.fieldAnnotation = null;
      this.methodAnnotation = null;
      if(var5 != null) {
         var6 = var5.getModifiers();
         var8 = var9;
         if((var6 & 1) == 0) {
            if(this.method == null) {
               var8 = var9;
            } else {
               var8 = false;
            }
         }

         this.fieldAccess = var8;
         this.fieldTransient = Modifier.isTransient(var6);
      } else {
         this.fieldAccess = false;
         this.fieldTransient = false;
      }

      this.getOnly = false;
      long var10 = -3750763034362895579L;

      for(var6 = 0; var6 < var1.length(); ++var6) {
         var10 = 1099511628211L * (var10 ^ (long)var1.charAt(var6));
      }

      this.nameHashCode = var10;
      this.format = null;
      this.alternateNames = new String[0];
   }

   public FieldInfo(String var1, Method var2, Field var3, Class<?> var4, Type var5, int var6, int var7, JSONField var8, JSONField var9, boolean var10) {
      boolean var13 = false;
      boolean var12 = false;
      this.ordinal = 0;
      this.name = var1;
      this.method = var2;
      this.field = var3;
      this.ordinal = var6;
      this.methodAnnotation = var8;
      this.fieldAnnotation = var9;
      JSONField var17 = this.getAnnotation();
      var9 = null;
      String var26;
      if(var17 != null) {
         String var29 = var17.format();
         var26 = var29;
         if(var29.trim().length() == 0) {
            var26 = null;
         }

         this.alternateNames = var17.alternateNames();
      } else {
         this.alternateNames = new String[0];
         var26 = null;
      }

      this.format = var26;
      if(var3 != null) {
         var6 = var3.getModifiers();
         boolean var11;
         if(var2 != null && ((var6 & 1) == 0 || var2.getReturnType() != var3.getType())) {
            var11 = false;
         } else {
            var11 = true;
         }

         this.fieldAccess = var11;
         if((var6 & 128) != 0) {
            var11 = true;
         } else {
            var11 = false;
         }

         this.fieldTransient = var11;
      } else {
         this.fieldAccess = false;
         this.fieldTransient = false;
      }

      long var14 = -3750763034362895579L;

      for(var6 = 0; var6 < var1.length(); ++var6) {
         var14 = 1099511628211L * (var14 ^ (long)var1.charAt(var6));
      }

      this.nameHashCode = var14;
      Class var20;
      Object var22;
      Object var23;
      Class var27;
      if(var2 != null) {
         Class[] var19 = var2.getParameterTypes();
         if(var19.length == 1) {
            var20 = var19[0];
            if(var20 != Class.class && var20 != String.class && !var20.isPrimitive() && var10) {
               var23 = var2.getGenericParameterTypes()[0];
            } else {
               var23 = var20;
            }

            this.getOnly = false;
         } else {
            var20 = var2.getReturnType();
            if(var20 != Class.class && var10) {
               var23 = var2.getGenericReturnType();
            } else {
               var23 = var20;
            }

            this.getOnly = true;
         }

         this.declaringClass = var2.getDeclaringClass();
         var22 = var23;
      } else {
         var27 = var3.getType();
         Object var21;
         if(!var27.isPrimitive() && var27 != String.class && !var27.isEnum() && var10) {
            var21 = var3.getGenericType();
         } else {
            var21 = var27;
         }

         this.declaringClass = var3.getDeclaringClass();
         this.getOnly = Modifier.isFinal(var3.getModifiers());
         var22 = var21;
         var20 = var27;
      }

      if(var4 != null && var20 == Object.class && var22 instanceof TypeVariable) {
         TypeVariable var32 = (TypeVariable)var22;
         Type[] var25;
         if(var5 instanceof ParameterizedType) {
            var25 = ((ParameterizedType)var5).getActualTypeArguments();
         } else {
            var25 = null;
         }

         Type[] var30;
         for(var27 = var4; var27 != null && var27 != Object.class && var27 != this.declaringClass; var25 = var30) {
            Type var18 = var27.getGenericSuperclass();
            var30 = var25;
            if(var18 instanceof ParameterizedType) {
               var30 = ((ParameterizedType)var18).getActualTypeArguments();
               TypeUtils.getArgument(var30, var27.getTypeParameters(), var25);
            }

            var27 = var27.getSuperclass();
         }

         var5 = var9;
         if(var25 != null) {
            TypeVariable[] var31 = this.declaringClass.getTypeParameters();
            var6 = 0;

            while(true) {
               var5 = var9;
               if(var6 >= var31.length) {
                  break;
               }

               if(var32.equals(var31[var6])) {
                  var5 = var25[var6];
                  break;
               }

               ++var6;
            }
         }

         if(var5 != null) {
            this.fieldClass = TypeUtils.getClass(var5);
            this.fieldType = var5;
            var10 = var12;
            if(var20.isEnum()) {
               var10 = var12;
               if(!JSONAware.class.isAssignableFrom(var20)) {
                  var10 = true;
               }
            }

            this.isEnum = var10;
            return;
         }
      }

      Object var24;
      Class var28;
      if(!(var22 instanceof Class)) {
         if(var5 != null) {
            var23 = var5;
         } else {
            var23 = var4;
         }

         var5 = getFieldType(var4, (Type)var23, (Type)var22);
         var28 = var20;
         var24 = var5;
         if(var5 != var22) {
            if(var5 instanceof ParameterizedType) {
               var28 = TypeUtils.getClass(var5);
               var24 = var5;
            } else {
               var28 = var20;
               var24 = var5;
               if(var5 instanceof Class) {
                  var28 = TypeUtils.getClass(var5);
                  var24 = var5;
               }
            }
         }
      } else {
         var24 = var22;
         var28 = var20;
      }

      this.fieldType = (Type)var24;
      this.fieldClass = var28;
      var10 = var13;
      if(!var28.isArray()) {
         var10 = var13;
         if(var28.isEnum()) {
            var10 = var13;
            if(!JSONAware.class.isAssignableFrom(var28)) {
               var10 = true;
            }
         }
      }

      this.isEnum = var10;
   }

   public static Type getFieldType(Class<?> var0, Type var1, Type var2) {
      if(var0 != null) {
         if(var1 == null) {
            return var2;
         } else if(var2 instanceof GenericArrayType) {
            Type var18 = ((GenericArrayType)var2).getGenericComponentType();
            Type var12 = getFieldType(var0, var1, var18);
            return (Type)(var18 != var12?Array.newInstance(TypeUtils.getClass(var12), 0).getClass():var2);
         } else if(!TypeUtils.isGenericParamType(var1)) {
            return var2;
         } else {
            if(var2 instanceof TypeVariable) {
               ParameterizedType var6 = (ParameterizedType)TypeUtils.getGenericParamType(var1);
               Class var7 = TypeUtils.getClass(var6);
               TypeVariable var8 = (TypeVariable)var2;

               for(int var3 = 0; var3 < var7.getTypeParameters().length; ++var3) {
                  if(var7.getTypeParameters()[var3].getName().equals(var8.getName())) {
                     return var6.getActualTypeArguments()[var3];
                  }
               }
            }

            if(var2 instanceof ParameterizedType) {
               ParameterizedType var19 = (ParameterizedType)var2;
               Type[] var9 = var19.getActualTypeArguments();
               TypeVariable[] var13;
               Object var16;
               if(var1 instanceof ParameterizedType) {
                  var16 = (ParameterizedType)var1;
                  var13 = var0.getTypeParameters();
               } else if(var0.getGenericSuperclass() instanceof ParameterizedType) {
                  var16 = (ParameterizedType)var0.getGenericSuperclass();
                  var13 = var0.getSuperclass().getTypeParameters();
               } else {
                  var13 = null;
                  var16 = var13;
               }

               Type[] var11 = null;
               int var4 = 0;

               boolean var14;
               Type[] var17;
               for(var14 = false; var4 < var9.length && var16 != null; var11 = var17) {
                  Type var10 = var9[var4];
                  boolean var5 = var14;
                  var17 = var11;
                  if(var10 instanceof TypeVariable) {
                     TypeVariable var20 = (TypeVariable)var10;

                     for(int var15 = 0; var15 < var13.length; var11 = var17) {
                        var17 = var11;
                        if(var13[var15].getName().equals(var20.getName())) {
                           var17 = var11;
                           if(var11 == null) {
                              var17 = ((ParameterizedType)var16).getActualTypeArguments();
                           }

                           var9[var4] = var17[var15];
                           var14 = true;
                        }

                        ++var15;
                     }

                     var17 = var11;
                     var5 = var14;
                  }

                  ++var4;
                  var14 = var5;
               }

               if(var14) {
                  return new ParameterizedTypeImpl(var9, var19.getOwnerType(), var19.getRawType());
               }
            }

            return var2;
         }
      } else {
         return var2;
      }
   }

   public int compareTo(FieldInfo var1) {
      return this.ordinal < var1.ordinal?-1:(this.ordinal > var1.ordinal?1:this.name.compareTo(var1.name));
   }

   public boolean equals(FieldInfo var1) {
      return var1 == this?true:this.compareTo(var1) == 0;
   }

   public Object get(Object var1) throws IllegalAccessException, InvocationTargetException {
      return this.fieldAccess?this.field.get(var1):this.method.invoke(var1, new Object[0]);
   }

   public JSONField getAnnotation() {
      return this.fieldAnnotation != null?this.fieldAnnotation:this.methodAnnotation;
   }

   public void set(Object var1, Object var2) throws IllegalAccessException, InvocationTargetException {
      if(this.method != null) {
         this.method.invoke(var1, new Object[]{var2});
      } else {
         this.field.set(var1, var2);
      }
   }

   public String toString() {
      return this.name;
   }
}
