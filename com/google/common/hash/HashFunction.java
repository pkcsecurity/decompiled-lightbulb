package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hasher;

@Beta
public interface HashFunction {

   Hasher a();

   <T extends Object> sl a(T var1, Funnel<? super T> var2);
}
