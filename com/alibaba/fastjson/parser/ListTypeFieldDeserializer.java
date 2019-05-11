package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class ListTypeFieldDeserializer extends FieldDeserializer {

   private final boolean array;
   private ObjectDeserializer deserializer;
   private final Type itemType;


   public ListTypeFieldDeserializer(ParserConfig var1, Class<?> var2, FieldInfo var3) {
      super(var2, var3, 14);
      Type var4 = var3.fieldType;
      var2 = var3.fieldClass;
      if(var2.isArray()) {
         this.itemType = var2.getComponentType();
         this.array = true;
      } else {
         this.itemType = TypeUtils.getCollectionItemType(var4);
         this.array = false;
      }
   }

   final void parseArray(DefaultJSONParser var1, Type var2, Collection var3) {
      Type var12 = this.itemType;
      ObjectDeserializer var11 = this.deserializer;
      Object var9 = var12;
      ObjectDeserializer var10 = var11;
      int var6;
      int var7;
      if(var2 instanceof ParameterizedType) {
         boolean var8 = var12 instanceof TypeVariable;
         ParameterizedType var13 = null;
         Class var21 = null;
         TypeVariable var22;
         if(var8) {
            var22 = (TypeVariable)var12;
            var13 = (ParameterizedType)var2;
            if(var13.getRawType() instanceof Class) {
               var21 = (Class)var13.getRawType();
            }

            label140: {
               if(var21 != null) {
                  var7 = var21.getTypeParameters().length;

                  for(var6 = 0; var6 < var7; ++var6) {
                     if(var21.getTypeParameters()[var6].getName().equals(var22.getName())) {
                        break label140;
                     }
                  }
               }

               var6 = -1;
            }

            var9 = var12;
            var10 = var11;
            if(var6 != -1) {
               var12 = var13.getActualTypeArguments()[var6];
               var9 = var12;
               var10 = var11;
               if(!var12.equals(this.itemType)) {
                  var10 = var1.config.getDeserializer(var12);
                  var9 = var12;
               }
            }
         } else {
            var9 = var12;
            var10 = var11;
            if(var12 instanceof ParameterizedType) {
               ParameterizedType var14 = (ParameterizedType)var12;
               Type[] var15 = var14.getActualTypeArguments();
               var9 = var12;
               var10 = var11;
               if(var15.length == 1) {
                  var9 = var12;
                  var10 = var11;
                  if(var15[0] instanceof TypeVariable) {
                     var22 = (TypeVariable)var15[0];
                     ParameterizedType var16 = (ParameterizedType)var2;
                     var21 = var13;
                     if(var16.getRawType() instanceof Class) {
                        var21 = (Class)var16.getRawType();
                     }

                     label152: {
                        if(var21 != null) {
                           var7 = var21.getTypeParameters().length;

                           for(var6 = 0; var6 < var7; ++var6) {
                              if(var21.getTypeParameters()[var6].getName().equals(var22.getName())) {
                                 break label152;
                              }
                           }
                        }

                        var6 = -1;
                     }

                     var9 = var12;
                     var10 = var11;
                     if(var6 != -1) {
                        var15[0] = var16.getActualTypeArguments()[var6];
                        var9 = new ParameterizedTypeImpl(var15, var14.getOwnerType(), var14.getRawType());
                        var10 = var11;
                     }
                  }
               }
            }
         }
      }

      JSONLexer var23 = var1.lexer;
      var11 = var10;
      if(var10 == null) {
         var11 = var1.config.getDeserializer((Type)var9);
         this.deserializer = var11;
      }

      if(var23.token != 14) {
         if(var23.token == 12) {
            var3.add(var11.deserialze(var1, (Type)var9, Integer.valueOf(0)));
         } else {
            StringBuilder var17 = new StringBuilder();
            var17.append("exepct \'[\', but ");
            var17.append(JSONToken.name(var23.token));
            String var19 = var17.toString();
            String var18 = var19;
            if(var2 != null) {
               var17 = new StringBuilder();
               var17.append(var19);
               var17.append(", type : ");
               var17.append(var2);
               var18 = var17.toString();
            }

            throw new JSONException(var18);
         }
      } else {
         var6 = 0;
         char var20 = var23.ch;
         byte var5 = 26;
         char var4;
         if(var20 == 91) {
            var7 = var23.bp + 1;
            var23.bp = var7;
            if(var7 >= var23.len) {
               var4 = 26;
            } else {
               var4 = var23.text.charAt(var7);
            }

            var23.ch = var4;
            var23.token = 14;
         } else if(var20 == 123) {
            var7 = var23.bp + 1;
            var23.bp = var7;
            if(var7 >= var23.len) {
               var4 = 26;
            } else {
               var4 = var23.text.charAt(var7);
            }

            var23.ch = var4;
            var23.token = 12;
         } else if(var20 == 34) {
            var23.scanString();
         } else if(var20 == 93) {
            var7 = var23.bp + 1;
            var23.bp = var7;
            if(var7 >= var23.len) {
               var4 = 26;
            } else {
               var4 = var23.text.charAt(var7);
            }

            var23.ch = var4;
            var23.token = 15;
         } else {
            var23.nextToken();
         }

         while(true) {
            for(; var23.token != 16; ++var6) {
               if(var23.token == 15) {
                  if(var23.ch == 44) {
                     var6 = var23.bp + 1;
                     var23.bp = var6;
                     if(var6 >= var23.len) {
                        var4 = (char)var5;
                     } else {
                        var4 = var23.text.charAt(var6);
                     }

                     var23.ch = var4;
                     var23.token = 16;
                     return;
                  }

                  var23.nextToken();
                  return;
               }

               var3.add(var11.deserialze(var1, (Type)var9, Integer.valueOf(var6)));
               if(var1.resolveStatus == 1) {
                  var1.checkListResolve(var3);
               }

               if(var23.token == 16) {
                  var20 = var23.ch;
                  if(var20 == 91) {
                     var7 = var23.bp + 1;
                     var23.bp = var7;
                     if(var7 >= var23.len) {
                        var4 = 26;
                     } else {
                        var4 = var23.text.charAt(var7);
                     }

                     var23.ch = var4;
                     var23.token = 14;
                  } else if(var20 == 123) {
                     var7 = var23.bp + 1;
                     var23.bp = var7;
                     if(var7 >= var23.len) {
                        var4 = 26;
                     } else {
                        var4 = var23.text.charAt(var7);
                     }

                     var23.ch = var4;
                     var23.token = 12;
                  } else if(var20 == 34) {
                     var23.scanString();
                  } else {
                     var23.nextToken();
                  }
               }
            }

            var23.nextToken();
         }
      }
   }

   public void parseField(DefaultJSONParser var1, Object var2, Type var3, Map<String, Object> var4) {
      JSONLexer var6 = var1.lexer;
      int var5 = var6.token();
      JSONArray var7 = null;
      if(var5 != 8 && (var5 != 4 || var6.stringVal().length() != 0)) {
         Object var10;
         if(this.array) {
            var7 = new JSONArray();
            var7.setComponentType(this.itemType);
            var10 = var7;
         } else {
            var10 = new ArrayList();
         }

         ParseContext var8 = var1.contex;
         var1.setContext(var8, var2, this.fieldInfo.name);
         this.parseArray(var1, var3, (Collection)var10);
         var1.setContext(var8);
         Object var9 = var10;
         if(this.array) {
            var9 = ((List)var10).toArray((Object[])Array.newInstance((Class)this.itemType, ((List)var10).size()));
            var7.setRelatedArray(var9);
         }

         if(var2 == null) {
            var4.put(this.fieldInfo.name, var9);
         } else {
            this.setValue(var2, var9);
         }
      } else {
         this.setValue(var2, (Object)null);
         var1.lexer.nextToken();
      }
   }
}
