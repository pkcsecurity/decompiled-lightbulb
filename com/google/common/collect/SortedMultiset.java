package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.SortedIterable;
import com.google.common.collect.SortedMultisetBridge;

@Beta
@GwtCompatible
public interface SortedMultiset<E extends Object> extends SortedIterable<E>, SortedMultisetBridge<E> {
}
