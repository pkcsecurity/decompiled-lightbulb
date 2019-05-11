package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Iterator;

@GwtCompatible
public interface PeekingIterator<E extends Object> extends Iterator<E> {

   E a();

   E next();
}
