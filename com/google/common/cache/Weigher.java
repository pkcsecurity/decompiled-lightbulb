package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public interface Weigher<K extends Object, V extends Object> {

   int a(K var1, V var2);
}
