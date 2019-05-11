package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Map;

@GwtCompatible
public interface ClassToInstanceMap<B extends Object> extends Map<Class<? extends B>, B> {
}
