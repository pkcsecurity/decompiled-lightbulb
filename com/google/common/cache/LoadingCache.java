package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.cache.Cache;

@GwtCompatible
public interface LoadingCache<K extends Object, V extends Object> extends Function<K, V>, Cache<K, V> {
}
