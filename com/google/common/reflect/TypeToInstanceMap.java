package com.google.common.reflect;

import com.google.common.annotations.Beta;
import java.util.Map;

@Beta
public interface TypeToInstanceMap<B extends Object> extends Map<Object<? extends B>, B> {
}
