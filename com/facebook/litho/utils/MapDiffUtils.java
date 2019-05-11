package com.facebook.litho.utils;

import android.support.annotation.Nullable;
import com.facebook.litho.CommonUtils;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class MapDiffUtils {

   public static <K extends Object, V extends Object> boolean areMapsEqual(@Nullable Map<K, V> var0, @Nullable Map<K, V> var1) {
      if(var0 == var1) {
         return true;
      } else if(var0 != null) {
         if(var1 == null) {
            return false;
         } else if(var0.size() != var1.size()) {
            return false;
         } else {
            Iterator var3 = var0.entrySet().iterator();

            Entry var2;
            do {
               if(!var3.hasNext()) {
                  return true;
               }

               var2 = (Entry)var3.next();
            } while(CommonUtils.equals(var2.getValue(), var1.get(var2.getKey())));

            return false;
         }
      } else {
         return false;
      }
   }
}
