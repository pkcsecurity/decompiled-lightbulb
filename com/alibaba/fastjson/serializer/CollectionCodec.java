package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class CollectionCodec implements ObjectDeserializer, ObjectSerializer {

   public static final CollectionCodec instance = new CollectionCodec();


   public <T extends Object> T deserialze(DefaultJSONParser var1, Type var2, Object var3) {
      if(var1.lexer.token() == 8) {
         var1.lexer.nextToken(16);
         return null;
      } else if(var2 == JSONArray.class) {
         JSONArray var8 = new JSONArray();
         var1.parseArray((Collection)var8);
         return var8;
      } else {
         Type var4;
         for(var4 = var2; !(var4 instanceof Class); var4 = ((ParameterizedType)var4).getRawType()) {
            if(!(var4 instanceof ParameterizedType)) {
               throw new JSONException("TODO");
            }
         }

         Class var5 = (Class)var4;
         Object var9;
         if(var5 != AbstractCollection.class && var5 != Collection.class) {
            if(var5.isAssignableFrom(HashSet.class)) {
               var9 = new HashSet();
            } else if(var5.isAssignableFrom(LinkedHashSet.class)) {
               var9 = new LinkedHashSet();
            } else if(var5.isAssignableFrom(TreeSet.class)) {
               var9 = new TreeSet();
            } else if(var5.isAssignableFrom(ArrayList.class)) {
               var9 = new ArrayList();
            } else if(var5.isAssignableFrom(EnumSet.class)) {
               if(var2 instanceof ParameterizedType) {
                  var9 = ((ParameterizedType)var2).getActualTypeArguments()[0];
               } else {
                  var9 = Object.class;
               }

               var9 = EnumSet.noneOf((Class)var9);
            } else {
               try {
                  var9 = (Collection)var5.newInstance();
               } catch (Exception var6) {
                  StringBuilder var7 = new StringBuilder();
                  var7.append("create instane error, class ");
                  var7.append(var5.getName());
                  throw new JSONException(var7.toString());
               }
            }
         } else {
            var9 = new ArrayList();
         }

         var1.parseArray(TypeUtils.getCollectionItemType(var2), (Collection)var9, var3);
         return var9;
      }
   }

   public void write(JSONSerializer param1, Object param2, Object param3, Type param4) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
