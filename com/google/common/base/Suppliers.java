package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import javax.annotation.CheckReturnValue;

@CheckReturnValue
@GwtCompatible
public final class Suppliers {

   interface SupplierFunction<T extends Object> extends Function<Supplier<T>, T> {
   }
}
