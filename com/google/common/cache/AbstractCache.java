package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.cache.Cache;

@GwtCompatible
public abstract class AbstractCache<K extends Object, V extends Object> implements Cache<K, V> {

   public interface StatsCounter {

      void a();

      void a(long var1);

      void b(long var1);
   }
}
