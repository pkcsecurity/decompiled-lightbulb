package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.Freezable;
import java.util.ArrayList;
import java.util.Iterator;

public final class DataBufferUtils {

   @KeepForSdk
   public static final String KEY_NEXT_PAGE_TOKEN = "next_page_token";
   @KeepForSdk
   public static final String KEY_PREV_PAGE_TOKEN = "prev_page_token";


   public static <T extends Object, E extends Object & Freezable<T>> ArrayList<T> freezeAndClose(DataBuffer<E> var0) {
      ArrayList var1 = new ArrayList(var0.getCount());

      try {
         Iterator var2 = var0.iterator();

         while(var2.hasNext()) {
            var1.add(((Freezable)var2.next()).freeze());
         }
      } finally {
         var0.close();
      }

      return var1;
   }

   public static boolean hasData(DataBuffer<?> var0) {
      return var0 != null && var0.getCount() > 0;
   }

   public static boolean hasNextPage(DataBuffer<?> var0) {
      Bundle var1 = var0.getMetadata();
      return var1 != null && var1.getString("next_page_token") != null;
   }

   public static boolean hasPrevPage(DataBuffer<?> var0) {
      Bundle var1 = var0.getMetadata();
      return var1 != null && var1.getString("prev_page_token") != null;
   }
}
