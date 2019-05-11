package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class JavaBeanInfo {

   final Constructor<?> creatorConstructor;
   public final String[] creatorConstructorParameters;
   final Constructor<?> defaultConstructor;
   final int defaultConstructorParameterSize;
   final Method factoryMethod;
   final FieldInfo[] fields;
   final JSONType jsonType;
   boolean ordered;
   public final int parserFeatures;
   final FieldInfo[] sortedFields;
   final boolean supportBeanToArray;
   public final String typeKey;
   public final String typeName;


   JavaBeanInfo(Class<?> var1, Constructor<?> var2, Constructor<?> var3, Method var4, FieldInfo[] var5, FieldInfo[] var6, JSONType var7, String[] var8) {
      byte var12 = 0;
      this.ordered = false;
      this.defaultConstructor = var2;
      this.creatorConstructor = var3;
      this.factoryMethod = var4;
      this.fields = var5;
      this.jsonType = var7;
      if(var8 != null && var8.length == var5.length) {
         this.creatorConstructorParameters = null;
      } else {
         this.creatorConstructorParameters = var8;
      }

      int var9;
      int var10;
      int var11;
      Feature[] var18;
      if(var7 != null) {
         String var17 = var7.typeName();
         String var16;
         if(var17.length() > 0) {
            var16 = var17;
         } else {
            var16 = var1.getName();
         }

         this.typeName = var16;
         var16 = var7.typeKey();
         if(var16.length() <= 0) {
            var16 = null;
         }

         this.typeKey = var16;
         var18 = var7.parseFeatures();
         int var13 = var18.length;
         var10 = 0;
         var9 = 0;

         while(true) {
            var11 = var9;
            if(var10 >= var13) {
               break;
            }

            var9 |= var18[var10].mask;
            ++var10;
         }
      } else {
         this.typeName = var1.getName();
         this.typeKey = null;
         var11 = 0;
      }

      this.parserFeatures = var11;
      boolean var15;
      if(var7 != null) {
         var18 = var7.parseFeatures();
         var10 = var18.length;
         var9 = 0;
         boolean var14 = false;

         while(true) {
            var15 = var14;
            if(var9 >= var10) {
               break;
            }

            if(var18[var9] == Feature.SupportArrayToBean) {
               var14 = true;
            }

            ++var9;
         }
      } else {
         var15 = false;
      }

      this.supportBeanToArray = var15;
      FieldInfo[] var19 = this.computeSortedFields(var5, var6);
      FieldInfo[] var20 = var19;
      if(Arrays.equals(var5, var19)) {
         var20 = var5;
      }

      this.sortedFields = var20;
      if(var2 != null) {
         var9 = var2.getParameterTypes().length;
      } else {
         var9 = var12;
         if(var4 != null) {
            var9 = var4.getParameterTypes().length;
         }
      }

      this.defaultConstructorParameterSize = var9;
   }

   static boolean addField(List<FieldInfo> var0, FieldInfo var1, boolean var2) {
      if(!var2) {
         int var4 = var0.size();

         for(int var3 = 0; var3 < var4; ++var3) {
            FieldInfo var5 = (FieldInfo)var0.get(var3);
            if(var5.name.equals(var1.name) && (!var5.getOnly || var1.getOnly)) {
               return false;
            }
         }
      }

      var0.add(var1);
      return true;
   }

   public static JavaBeanInfo build(Class<?> var0, int var1, Type var2, boolean var3, boolean var4, boolean var5, boolean var6, PropertyNamingStrategy var7) {
      ArrayList var22 = new ArrayList();
      HashMap var18 = new HashMap();
      Constructor[] var26 = var0.getDeclaredConstructors();
      boolean var14 = TypeUtils.isKotlin(var0);
      int var11 = var1 & 1024;
      int var9;
      int var10;
      Constructor var15;
      if(var11 == 0 && (var26.length == 1 || !var14)) {
         label629: {
            try {
               var15 = var0.getDeclaredConstructor(new Class[0]);
            } catch (Exception var33) {
               var15 = null;
            }

            Constructor var16 = var15;
            if(var15 == null) {
               var16 = var15;
               if(var0.isMemberClass()) {
                  var16 = var15;
                  if((var1 & 8) == 0) {
                     var9 = var26.length;
                     var10 = 0;

                     while(true) {
                        var16 = var15;
                        if(var10 >= var9) {
                           break;
                        }

                        var16 = var26[var10];
                        Class[] var17 = var16.getParameterTypes();
                        if(var17.length == 1 && var17[0].equals(var0.getDeclaringClass())) {
                           var15 = var16;
                           break label629;
                        }

                        ++var10;
                     }
                  }
               }
            }

            var15 = var16;
         }
      } else {
         var15 = null;
      }

      Class[] var23 = null;
      Annotation[][] var25 = null;
      int var12;
      Object var20;
      ArrayList var24;
      Object var44;
      Object var49;
      if(var3) {
         var44 = null;
         var49 = var44;
      } else {
         var24 = new ArrayList();
         Class var47 = var0;

         for(var49 = null; var47 != null && var47 != Object.class; var47 = var47.getSuperclass()) {
            Method[] var19 = var47.getDeclaredMethods();
            var9 = var19.length;

            for(var10 = 0; var10 < var9; var49 = var20) {
               Method var21 = var19[var10];
               var12 = var21.getModifiers();
               if((var12 & 8) != 0) {
                  var20 = var49;
                  if(var21.isAnnotationPresent(JSONCreator.class)) {
                     if(var49 != null) {
                        throw new JSONException("multi-json creator");
                     }

                     var20 = var21;
                  }
               } else {
                  var20 = var49;
                  if((var12 & 2) == 0) {
                     var20 = var49;
                     if((var12 & 256) == 0) {
                        if((var12 & 4) != 0) {
                           var20 = var49;
                        } else {
                           var24.add(var21);
                           var20 = var49;
                        }
                     }
                  }
               }

               ++var10;
            }
         }

         var44 = new Method[var24.size()];
         var24.toArray((Object[])var44);
      }

      Field[] var76 = var0.getDeclaredFields();
      Field[] var55 = var76;
      boolean var41;
      if(!var0.isInterface() && var11 == 0) {
         var41 = false;
      } else {
         var41 = true;
      }

      JSONField var39;
      Field[] var45;
      FieldInfo[] var50;
      Object var52;
      String[] var57;
      Object var61;
      Constructor var63;
      Field var81;
      JSONField var83;
      String var95;
      if(var15 != null && !var41) {
         var61 = var49;
         var24 = null;
         var49 = var15;
         var57 = var23;
         var45 = var76;
         var63 = var24;
      } else {
         var10 = var26.length;
         var11 = 0;

         while(true) {
            if(var11 >= var10) {
               var63 = null;
               break;
            }

            var63 = var26[var11];
            if((JSONCreator)var63.getAnnotation(JSONCreator.class) != null) {
               break;
            }

            ++var11;
         }

         FieldInfo[] var64;
         if(var63 != null) {
            TypeUtils.setAccessible(var0, var63, var1);
            var23 = var63.getParameterTypes();
            if(var6) {
               var61 = var63.getGenericParameterTypes();
            } else {
               var61 = var23;
            }

            var25 = var63.getParameterAnnotations();

            for(var9 = 0; var9 < var23.length; ++var9) {
               Annotation[] var80 = var25[var9];
               var10 = var80.length;
               var11 = 0;

               while(true) {
                  if(var11 >= var10) {
                     var83 = null;
                     break;
                  }

                  Annotation var90 = var80[var11];
                  if(var90 instanceof JSONField) {
                     var83 = (JSONField)var90;
                     break;
                  }

                  ++var11;
               }

               if(var83 == null) {
                  throw new JSONException("illegal json creator");
               }

               Class var93 = var23[var9];
               Object var27 = ((Object[])var61)[var9];
               Field var28 = TypeUtils.getField(var0, var83.name(), var55, var18);
               if(var28 != null) {
                  TypeUtils.setAccessible(var0, var28, var1);
               }

               var10 = var83.ordinal();
               var11 = SerializerFeature.of(var83.serialzeFeatures());
               addField(var22, new FieldInfo(var83.name(), var0, var93, (Type)var27, var28, var10, var11), var3);
            }

            var64 = new FieldInfo[var22.size()];
            var22.toArray(var64);
            FieldInfo[] var71 = new FieldInfo[var64.length];
            System.arraycopy(var64, 0, var71, 0, var64.length);
            Arrays.sort(var71);
            if(var4) {
               JSONType var73 = (JSONType)var0.getAnnotation(JSONType.class);
            }

            String[] var78 = new String[var64.length];

            for(var9 = 0; var9 < var64.length; ++var9) {
               var78[var9] = var64[var9].name;
            }

            var61 = var49;
            var49 = var15;
            var45 = var55;
            var57 = var78;
         } else {
            Field[] var59;
            label543: {
               HashMap var69 = var18;
               if(var49 != null) {
                  TypeUtils.setAccessible(var0, (Member)var49, var1);
                  var44 = ((Method)var49).getParameterTypes();
                  if(((Object[])var44).length > 0) {
                     Object var34;
                     if(var6) {
                        var34 = ((Method)var49).getGenericParameterTypes();
                     } else {
                        var34 = var44;
                     }

                     Annotation[][] var48 = ((Method)var49).getParameterAnnotations();
                     var1 = 0;

                     while(var1 < ((Object[])var44).length) {
                        Annotation[] var38 = var48[var1];
                        var10 = var38.length;
                        var9 = 0;

                        while(true) {
                           if(var9 < var10) {
                              Annotation var51 = var38[var9];
                              if(!(var51 instanceof JSONField)) {
                                 ++var9;
                                 continue;
                              }

                              var39 = (JSONField)var51;
                           } else {
                              var39 = null;
                           }

                           if(var39 == null) {
                              throw new JSONException("illegal json creator");
                           }

                           var52 = ((Object[])var44)[var1];
                           var20 = ((Object[])var34)[var1];
                           var81 = TypeUtils.getField(var0, var39.name(), var55, var69);
                           var9 = var39.ordinal();
                           var10 = SerializerFeature.of(var39.serialzeFeatures());
                           addField(var22, new FieldInfo(var39.name(), var0, (Class)var52, (Type)var20, var81, var9, var10), var3);
                           ++var1;
                           break;
                        }
                     }

                     var50 = new FieldInfo[var22.size()];
                     var22.toArray(var50);
                     FieldInfo[] var35 = new FieldInfo[var50.length];
                     System.arraycopy(var50, 0, var35, 0, var50.length);
                     Arrays.sort(var35);
                     if(Arrays.equals(var50, var35)) {
                        var35 = var50;
                     }

                     JSONType var40;
                     if(var4) {
                        var40 = (JSONType)var0.getAnnotation(JSONType.class);
                     } else {
                        var40 = null;
                     }

                     return new JavaBeanInfo(var0, (Constructor)null, (Constructor)null, (Method)var49, var50, var35, var40, (String[])null);
                  }
               } else if(!var41) {
                  StringBuilder var36;
                  if(!var14 || var26.length <= 0) {
                     var36 = new StringBuilder();
                     var36.append("default constructor not found. ");
                     var36.append(var0);
                     throw new JSONException(var36.toString());
                  }

                  String[] var102 = TypeUtils.getKoltinConstructorParameters(var0);
                  if(var102 == null) {
                     var36 = new StringBuilder();
                     var36.append("default constructor not found. ");
                     var36.append(var0);
                     throw new JSONException(var36.toString());
                  }

                  var10 = var26.length;

                  Constructor var54;
                  for(var9 = 0; var9 < var10; var63 = var54) {
                     var54 = var26[var9];
                     Class[] var87 = var54.getParameterTypes();
                     if(var87.length > 0 && var87[var87.length - 1].getName().equals("kotlin.jvm.internal.DefaultConstructorMarker")) {
                        var54 = var63;
                     } else if(var63 != null && var63.getParameterTypes().length >= var87.length) {
                        var54 = var63;
                     }

                     ++var9;
                  }

                  var63.setAccessible(true);
                  TypeUtils.setAccessible(var0, var63, var1);
                  Class[] var86 = var63.getParameterTypes();
                  Object var91;
                  if(var6) {
                     var91 = var63.getGenericParameterTypes();
                  } else {
                     var91 = var86;
                  }

                  Annotation[][] var29 = var63.getParameterAnnotations();
                  var9 = 0;

                  for(var44 = var49; var9 < var86.length; ++var9) {
                     var95 = var102[var9];
                     Annotation[] var74 = var29[var9];
                     var11 = var74.length;
                     var10 = 0;

                     JSONField var79;
                     while(true) {
                        if(var10 >= var11) {
                           var79 = null;
                           break;
                        }

                        Annotation var98 = var74[var10];
                        if(var98 instanceof JSONField) {
                           var79 = (JSONField)var98;
                           break;
                        }

                        ++var10;
                     }

                     Class var30 = var86[var9];
                     Object var31 = ((Object[])var91)[var9];
                     Field var32 = TypeUtils.getField(var0, var95, var55, var69);
                     JSONField var100 = var79;
                     if(var32 != null) {
                        var100 = var79;
                        if(var79 == null) {
                           var100 = (JSONField)var32.getAnnotation(JSONField.class);
                        }
                     }

                     String var84;
                     if(var100 != null) {
                        var11 = var100.ordinal();
                        var84 = var95;
                        var10 = SerializerFeature.of(var100.serialzeFeatures());
                        var95 = var100.name();
                        if(var95.length() != 0) {
                           var84 = var95;
                        }
                     } else {
                        var11 = 0;
                        var10 = 0;
                        var84 = var95;
                     }

                     addField(var22, new FieldInfo(var84, var0, var30, (Type)var31, var32, var11, var10), var3);
                  }

                  var64 = new FieldInfo[var22.size()];
                  var22.toArray(var64);
                  FieldInfo[] var60 = new FieldInfo[var64.length];
                  System.arraycopy(var64, 0, var60, 0, var64.length);
                  Arrays.sort(var60);
                  var57 = new String[var64.length];

                  for(var9 = 0; var9 < var64.length; ++var9) {
                     var57[var9] = var64[var9].name;
                  }

                  var59 = var55;
                  var63 = var63;
                  var61 = var49;
                  break label543;
               }

               var61 = var49;
               var59 = var76;
               var57 = var25;
            }

            var44 = var44;
            var45 = var59;
            var49 = var15;
         }
      }

      if(var49 != null) {
         TypeUtils.setAccessible(var0, (Member)var49, var1);
      }

      String var56;
      StringBuilder var58;
      ArrayList var77;
      if(!var3) {
         Object var82 = var44;
         var11 = ((Object[])var44).length;
         var12 = 0;
         var44 = var18;

         ArrayList var53;
         for(var53 = var22; var12 < var11; var44 = var49) {
            label468: {
               Object var103 = ((Object[])var82)[var12];
               var95 = ((Method)var103).getName();
               if(var95.length() >= 4) {
                  Class var67 = ((Method)var103).getReturnType();
                  if((var67 == Void.TYPE || var67 == ((Method)var103).getDeclaringClass()) && ((Method)var103).getParameterTypes().length == 1) {
                     label613: {
                        JSONField var70;
                        if(var5) {
                           var70 = (JSONField)((Method)var103).getAnnotation(JSONField.class);
                        } else {
                           var70 = null;
                        }

                        var83 = var70;
                        if(var70 == null) {
                           var83 = var70;
                           if(var5) {
                              var83 = TypeUtils.getSupperMethodAnnotation(var0, (Method)var103);
                           }
                        }

                        label459: {
                           if(var83 != null) {
                              if(!var83.deserialize()) {
                                 break label613;
                              }

                              var10 = var83.ordinal();
                              var9 = SerializerFeature.of(var83.serialzeFeatures());
                              if(var83.name().length() != 0) {
                                 addField(var53, new FieldInfo(var83.name(), (Method)var103, (Field)null, var0, var2, var10, var9, var83, (JSONField)null, var6), var3);
                                 TypeUtils.setAccessible(var0, (Member)var103, var1);
                                 break label459;
                              }
                           } else {
                              var10 = 0;
                              var9 = 0;
                           }

                           var49 = var44;
                           if(var95.startsWith("set")) {
                              label614: {
                                 char var8 = var95.charAt(3);
                                 if(Character.isUpperCase(var8)) {
                                    if(TypeUtils.compatibleWithJavaBean) {
                                       var56 = TypeUtils.decapitalize(var95.substring(3));
                                    } else {
                                       var58 = new StringBuilder();
                                       var58.append(Character.toLowerCase(var95.charAt(3)));
                                       var58.append(var95.substring(4));
                                       var56 = var58.toString();
                                    }
                                 } else if(var8 == 95) {
                                    var56 = var95.substring(4);
                                 } else if(var8 == 102) {
                                    var56 = var95.substring(3);
                                 } else {
                                    if(var95.length() < 5 || !Character.isUpperCase(var95.charAt(4))) {
                                       break label614;
                                    }

                                    var56 = TypeUtils.decapitalize(var95.substring(3));
                                 }

                                 Field var101 = TypeUtils.getField(var0, var56, var45, (Map)var44);
                                 if(var101 == null && ((Method)var103).getParameterTypes()[0] == Boolean.TYPE) {
                                    StringBuilder var62 = new StringBuilder();
                                    var62.append("is");
                                    var62.append(Character.toUpperCase(var56.charAt(0)));
                                    var62.append(var56.substring(1));
                                    var101 = TypeUtils.getField(var0, var62.toString(), var45, (Map)var44);
                                 }

                                 JSONField var65;
                                 label439: {
                                    label438: {
                                       if(var101 != null) {
                                          if(var5) {
                                             var65 = (JSONField)var101.getAnnotation(JSONField.class);
                                          } else {
                                             var65 = null;
                                          }

                                          if(var65 != null) {
                                             var9 = var65.ordinal();
                                             var10 = SerializerFeature.of(var65.serialzeFeatures());
                                             if(var65.name().length() != 0) {
                                                String var104 = var65.name();
                                                var45 = var45;
                                                addField(var53, new FieldInfo(var104, (Method)var103, var101, var0, var2, var9, var10, var83, var65, var6), var3);
                                                break label468;
                                             }

                                             if(var83 == null) {
                                                break label439;
                                             }
                                             break label438;
                                          }
                                       }

                                       int var13 = var10;
                                       var10 = var9;
                                       var9 = var13;
                                    }

                                    var65 = var83;
                                 }

                                 String var92 = var56;
                                 if(var7 != null) {
                                    var92 = var7.translate(var56);
                                 }

                                 addField(var53, new FieldInfo(var92, (Method)var103, (Field)null, var0, var2, var9, var10, var65, (JSONField)null, var6), var3);
                                 TypeUtils.setAccessible(var0, (Member)var103, var1);
                                 var45 = var45;
                                 break label468;
                              }
                           }
                        }

                        var49 = var44;
                        break label468;
                     }
                  }
               }

               var49 = var44;
            }

            ++var12;
         }

         var49 = var49;
         var77 = var53;
         var52 = var82;
      } else {
         var52 = var44;
         var77 = var22;
      }

      var22 = new ArrayList(var45.length);
      var11 = var45.length;

      boolean var46;
      for(var9 = 0; var9 < var11; ++var9) {
         var81 = var45[var9];
         var10 = var81.getModifiers();
         if((var10 & 8) == 0) {
            if((var10 & 16) != 0) {
               Class var105 = var81.getType();
               if(!Map.class.isAssignableFrom(var105) && !Collection.class.isAssignableFrom(var105)) {
                  var46 = false;
               } else {
                  var46 = true;
               }

               if(!var46) {
                  continue;
               }
            }

            if((var81.getModifiers() & 1) != 0) {
               var22.add(var81);
            }
         }
      }

      for(Class var66 = var0.getSuperclass(); var66 != null && var66 != Object.class; var66 = var66.getSuperclass()) {
         Field[] var85 = var66.getDeclaredFields();
         var11 = var85.length;

         for(var9 = 0; var9 < var11; ++var9) {
            Field var106 = var85[var9];
            var12 = var106.getModifiers();
            if((var12 & 8) == 0) {
               if((var12 & 16) != 0) {
                  Class var97 = var106.getType();
                  if(!Map.class.isAssignableFrom(var97) && !Collection.class.isAssignableFrom(var97)) {
                     var46 = false;
                  } else {
                     var46 = true;
                  }

                  if(!var46) {
                     continue;
                  }
               }

               if((var12 & 1) != 0) {
                  var22.add(var106);
               }
            }
         }
      }

      Iterator var107 = var22.iterator();

      String var89;
      while(var107.hasNext()) {
         Field var99 = (Field)var107.next();
         var56 = var99.getName();
         var11 = var77.size();
         var9 = 0;

         for(var46 = false; var9 < var11; ++var9) {
            if(((FieldInfo)var77.get(var9)).name.equals(var56)) {
               var46 = true;
            }
         }

         if(!var46) {
            JSONField var68;
            if(var5) {
               var68 = (JSONField)var99.getAnnotation(JSONField.class);
            } else {
               var68 = null;
            }

            if(var68 != null) {
               var9 = var68.ordinal();
               var10 = SerializerFeature.of(var68.serialzeFeatures());
               if(var68.name().length() != 0) {
                  var56 = var68.name();
               }
            } else {
               var9 = 0;
               var10 = 0;
            }

            var89 = var56;
            if(var7 != null) {
               var89 = var7.translate(var56);
            }

            TypeUtils.setAccessible(var0, var99, var1);
            addField(var77, new FieldInfo(var89, (Method)null, var99, var0, var2, var9, var10, (JSONField)null, var68, var6), var3);
         }
      }

      if(!var3) {
         var9 = ((Object[])var52).length;

         for(var10 = 0; var10 < var9; ++var10) {
            Object var72 = ((Object[])var52)[var10];
            var89 = ((Method)var72).getName();
            if(var89.length() >= 4 && var89.startsWith("get") && Character.isUpperCase(var89.charAt(3)) && ((Method)var72).getParameterTypes().length == 0) {
               Class var42 = ((Method)var72).getReturnType();
               if(Collection.class.isAssignableFrom(var42) || Map.class.isAssignableFrom(var42)) {
                  if(var5) {
                     var39 = (JSONField)((Method)var72).getAnnotation(JSONField.class);
                  } else {
                     var39 = null;
                  }

                  label363: {
                     if(var39 != null) {
                        var56 = var39.name();
                        if(var56.length() > 0) {
                           break label363;
                        }
                     }

                     var58 = new StringBuilder();
                     var58.append(Character.toLowerCase(var89.charAt(3)));
                     var58.append(var89.substring(4));
                     var56 = var58.toString();
                  }

                  addField(var77, new FieldInfo(var56, (Method)var72, (Field)null, var0, var2, 0, 0, var39, (JSONField)null, var6), var3);
                  TypeUtils.setAccessible(var0, (Member)var72, var1);
               }
            }
         }
      }

      FieldInfo[] var43 = new FieldInfo[var77.size()];
      var77.toArray(var43);
      var50 = new FieldInfo[var43.length];
      System.arraycopy(var43, 0, var50, 0, var43.length);
      Arrays.sort(var50);
      JSONType var37;
      if(var4) {
         var37 = (JSONType)var0.getAnnotation(JSONType.class);
      } else {
         var37 = null;
      }

      return new JavaBeanInfo(var0, (Constructor)var49, var63, (Method)var61, var43, var50, var37, var57);
   }

   private FieldInfo[] computeSortedFields(FieldInfo[] var1, FieldInfo[] var2) {
      if(this.jsonType == null) {
         return var2;
      } else {
         String[] var7 = this.jsonType.orders();
         if(var7 != null && var7.length != 0) {
            int var3 = 0;

            int var4;
            boolean var8;
            while(true) {
               if(var3 >= var7.length) {
                  var8 = true;
                  break;
               }

               var4 = 0;

               boolean var9;
               while(true) {
                  if(var4 >= var2.length) {
                     var9 = false;
                     break;
                  }

                  if(var2[var4].name.equals(var7[var3])) {
                     var9 = true;
                     break;
                  }

                  ++var4;
               }

               if(!var9) {
                  var8 = false;
                  break;
               }

               ++var3;
            }

            if(!var8) {
               return var2;
            }

            if(var7.length == var1.length) {
               var3 = 0;

               while(true) {
                  if(var3 >= var7.length) {
                     var8 = true;
                     break;
                  }

                  if(!var2[var3].name.equals(var7[var3])) {
                     var8 = false;
                     break;
                  }

                  ++var3;
               }

               if(var8) {
                  return var2;
               }

               var1 = new FieldInfo[var2.length];
               var3 = 0;

               while(var3 < var7.length) {
                  var4 = 0;

                  while(true) {
                     if(var4 < var2.length) {
                        if(!var2[var4].name.equals(var7[var3])) {
                           ++var4;
                           continue;
                        }

                        var1[var3] = var2[var4];
                     }

                     ++var3;
                     break;
                  }
               }

               this.ordered = true;
               return var1;
            }

            var1 = new FieldInfo[var2.length];

            for(var3 = 0; var3 < var7.length; ++var3) {
               for(var4 = 0; var4 < var2.length; ++var4) {
                  if(var2[var4].name.equals(var7[var3])) {
                     var1[var3] = var2[var4];
                     break;
                  }
               }
            }

            var4 = var7.length;

            int var5;
            for(var3 = 0; var3 < var2.length; var4 = var5) {
               var5 = 0;

               boolean var6;
               while(true) {
                  if(var5 >= var1.length || var5 >= var4) {
                     var6 = false;
                     break;
                  }

                  if(var1[var3].equals(var2[var5])) {
                     var6 = true;
                     break;
                  }

                  ++var5;
               }

               var5 = var4;
               if(!var6) {
                  var1[var4] = var2[var3];
                  var5 = var4 + 1;
               }

               ++var3;
            }

            this.ordered = true;
         }

         return var2;
      }
   }
}
