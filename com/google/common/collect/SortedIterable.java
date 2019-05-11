package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Comparator;

@GwtCompatible
public interface SortedIterable<T extends Object> extends Iterable<T> {

   Comparator<? super T> comparator();
}