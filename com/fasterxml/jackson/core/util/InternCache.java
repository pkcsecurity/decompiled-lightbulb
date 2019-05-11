package com.fasterxml.jackson.core.util;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public final class InternCache extends LinkedHashMap<String, String> {

   private static final int MAX_ENTRIES = 100;
   public static final InternCache instance = new InternCache();


   private InternCache() {
      super(100, 0.8F, true);
   }

   public String intern(String param1) {
      // $FF: Couldn't be decompiled
   }

   protected boolean removeEldestEntry(Entry<String, String> var1) {
      return this.size() > 100;
   }
}
