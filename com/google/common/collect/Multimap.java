package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
public interface Multimap<K extends Object, V extends Object> {

   boolean a(@Nullable Object var1, @Nullable Object var2);

   int b();

   boolean b(@Nullable Object var1, @Nullable Object var2);

   void c();

   Map<K, Collection<V>> j();
}
