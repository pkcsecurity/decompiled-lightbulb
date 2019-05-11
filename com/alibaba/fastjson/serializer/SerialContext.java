package com.alibaba.fastjson.serializer;


public class SerialContext {

   public final int features;
   public final Object fieldName;
   public final Object object;
   public final SerialContext parent;


   public SerialContext(SerialContext var1, Object var2, Object var3, int var4) {
      this.parent = var1;
      this.object = var2;
      this.fieldName = var3;
      this.features = var4;
   }

   public String toString() {
      if(this.parent == null) {
         return "$";
      } else {
         StringBuilder var1;
         if(this.fieldName instanceof Integer) {
            var1 = new StringBuilder();
            var1.append(this.parent.toString());
            var1.append("[");
            var1.append(this.fieldName);
            var1.append("]");
            return var1.toString();
         } else {
            var1 = new StringBuilder();
            var1.append(this.parent.toString());
            var1.append(".");
            var1.append(this.fieldName);
            return var1.toString();
         }
      }
   }
}
