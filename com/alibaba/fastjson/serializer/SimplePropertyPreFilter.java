package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerialContext;
import java.util.HashSet;
import java.util.Set;

public class SimplePropertyPreFilter implements PropertyPreFilter {

   private final Class<?> clazz;
   private final Set<String> excludes;
   private final Set<String> includes;
   private int maxLevel;


   public SimplePropertyPreFilter(Class<?> var1, String ... var2) {
      this.includes = new HashSet();
      this.excludes = new HashSet();
      int var3 = 0;
      this.maxLevel = 0;
      this.clazz = var1;

      for(int var4 = var2.length; var3 < var4; ++var3) {
         String var5 = var2[var3];
         if(var5 != null) {
            this.includes.add(var5);
         }
      }

   }

   public SimplePropertyPreFilter(String ... var1) {
      this((Class)null, var1);
   }

   public boolean apply(JSONSerializer var1, Object var2, String var3) {
      if(var2 == null) {
         return true;
      } else if(this.clazz != null && !this.clazz.isInstance(var2)) {
         return true;
      } else if(this.excludes.contains(var3)) {
         return false;
      } else {
         if(this.maxLevel > 0) {
            SerialContext var5 = var1.context;

            for(int var4 = 0; var5 != null; var5 = var5.parent) {
               ++var4;
               if(var4 > this.maxLevel) {
                  return false;
               }
            }
         }

         return this.includes.size() != 0?this.includes.contains(var3):true;
      }
   }

   public Class<?> getClazz() {
      return this.clazz;
   }

   public Set<String> getExcludes() {
      return this.excludes;
   }

   public Set<String> getIncludes() {
      return this.includes;
   }

   public int getMaxLevel() {
      return this.maxLevel;
   }

   public void setMaxLevel(int var1) {
      this.maxLevel = var1;
   }
}
