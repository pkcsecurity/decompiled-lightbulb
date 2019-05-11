package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeFilter;

public abstract class BeforeFilter implements SerializeFilter {

   private static final Character COMMA = Character.valueOf(',');
   private static final ThreadLocal<Character> seperatorLocal = new ThreadLocal();
   private static final ThreadLocal<JSONSerializer> serializerLocal = new ThreadLocal();


   final char writeBefore(JSONSerializer var1, Object var2, char var3) {
      serializerLocal.set(var1);
      seperatorLocal.set(Character.valueOf(var3));
      this.writeBefore(var2);
      serializerLocal.set((Object)null);
      return ((Character)seperatorLocal.get()).charValue();
   }

   public abstract void writeBefore(Object var1);

   protected final void writeKeyValue(String var1, Object var2) {
      JSONSerializer var4 = (JSONSerializer)serializerLocal.get();
      char var3 = ((Character)seperatorLocal.get()).charValue();
      var4.writeKeyValue(var3, var1, var2);
      if(var3 != 44) {
         seperatorLocal.set(COMMA);
      }

   }
}
