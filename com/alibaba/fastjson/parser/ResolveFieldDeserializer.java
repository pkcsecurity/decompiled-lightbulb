package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

final class ResolveFieldDeserializer extends FieldDeserializer {

   private final Collection collection;
   private final int index;
   private final Object key;
   private final List list;
   private final Map map;
   private final DefaultJSONParser parser;


   public ResolveFieldDeserializer(DefaultJSONParser var1, List var2, int var3) {
      super((Class)null, (FieldInfo)null, 0);
      this.parser = var1;
      this.index = var3;
      this.list = var2;
      this.key = null;
      this.map = null;
      this.collection = null;
   }

   public ResolveFieldDeserializer(Collection var1) {
      super((Class)null, (FieldInfo)null, 0);
      this.parser = null;
      this.index = -1;
      this.list = null;
      this.key = null;
      this.map = null;
      this.collection = var1;
   }

   public ResolveFieldDeserializer(Map var1, Object var2) {
      super((Class)null, (FieldInfo)null, 0);
      this.parser = null;
      this.index = -1;
      this.list = null;
      this.key = var2;
      this.map = var1;
      this.collection = null;
   }

   public void parseField(DefaultJSONParser var1, Object var2, Type var3, Map<String, Object> var4) {}

   public void setValue(Object var1, Object var2) {
      if(this.map != null) {
         this.map.put(this.key, var2);
      } else if(this.collection != null) {
         this.collection.add(var2);
      } else {
         this.list.set(this.index, var2);
         if(this.list instanceof JSONArray) {
            JSONArray var4 = (JSONArray)this.list;
            Object var3 = var4.getRelatedArray();
            if(var3 != null && Array.getLength(var3) > this.index) {
               var1 = var2;
               if(var4.getComponentType() != null) {
                  var1 = TypeUtils.cast(var2, var4.getComponentType(), this.parser.config);
               }

               Array.set(var3, this.index, var1);
            }
         }

      }
   }
}
