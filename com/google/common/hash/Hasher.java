package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;
import javax.annotation.CheckReturnValue;

@Beta
public interface Hasher extends PrimitiveSink {

   <T extends Object> Hasher a(T var1, Funnel<? super T> var2);

   @CheckReturnValue
   sl a();
}
