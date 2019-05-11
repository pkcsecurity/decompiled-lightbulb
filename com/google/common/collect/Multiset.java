package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Collection;
import java.util.Set;

@GwtCompatible
public interface Multiset<E extends Object> extends Collection<E> {

   Set<E> a();

   public interface Entry<E extends Object> {
   }
}
