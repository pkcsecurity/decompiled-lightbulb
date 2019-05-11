package com.alibaba.fastjson.parser;

import java.lang.reflect.Type;

public class ParseContext {

   public final Object fieldName;
   public Object object;
   public final ParseContext parent;
   private transient String path;
   public Type type;


   public ParseContext(ParseContext var1, Object var2, Object var3) {
      this.parent = var1;
      this.object = var2;
      this.fieldName = var3;
   }

   public String toString() {
      if(this.path == null) {
         if(this.parent == null) {
            this.path = "$";
         } else {
            StringBuilder var1;
            if(this.fieldName instanceof Integer) {
               var1 = new StringBuilder();
               var1.append(this.parent.toString());
               var1.append("[");
               var1.append(this.fieldName);
               var1.append("]");
               this.path = var1.toString();
            } else {
               var1 = new StringBuilder();
               var1.append(this.parent.toString());
               var1.append(".");
               var1.append(this.fieldName);
               this.path = var1.toString();
            }
         }
      }

      return this.path;
   }
}
