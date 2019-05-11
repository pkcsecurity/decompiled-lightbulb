package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.FilteredMultimap;
import com.google.common.collect.SetMultimap;

@GwtCompatible
interface FilteredSetMultimap<K extends Object, V extends Object> extends FilteredMultimap<K, V>, SetMultimap<K, V> {
}
